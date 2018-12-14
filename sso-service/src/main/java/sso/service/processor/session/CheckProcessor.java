package sso.service.processor.session;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.assertj.core.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sso.core.internal.dto.Constant;
import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.core.internal.dto.StateCode;
import sso.core.internal.handler.AbstractAuthHandler;
import sso.core.internal.handler.ChainHandler;
import sso.core.internal.processor.AbstractProcessor;
import sso.service.cache.AccessRedisClient;
import sso.service.cache.session.EhClient;
import sso.service.cache.session.RedisClient;

public class CheckProcessor extends AbstractProcessor<String, SSORequest, Result<String>> {

	private static final Logger LOG = LoggerFactory.getLogger(CheckProcessor.class);
	
	private static final String ACCESS_URL_PREFIX = "access_url_";
	
	@Autowired
	private EhClient ehClient;
	
	@Autowired
	private RedisClient redisClient;
	
	@Autowired
	private AccessRedisClient accessRedisClient;
	
	@Override
	public void initChain(ChainHandler<String, SSORequest, Result<String>> handler) {
		handler.addNode(new BlankChecker()).addNode(new SessionChecker());
	}

	private class BlankChecker extends AbstractAuthHandler<String, SSORequest, Result<String>> {
		@Override
		public Result<String> process0(SSORequest packet, Result<String> result) {
			if (packet.getSessionId() == null) {
				result.setCode(StateCode.FAILURE).setData(Constant.PAGE.PAGE_LOGIN.getPath());
			}
			return result;
		}
	}
	
	private class SessionChecker extends AbstractAuthHandler<String, SSORequest, Result<String>> {
		@Override
		public Result<String> process0(SSORequest packet, Result<String> result) throws IOException {
			String authId = packet.getRequest().getParameter(Constant.KEY.AUTH_ID.getKey());
			HttpSession session = packet.getRequest().getSession(false);
			if (session != null && session.getId().equalsIgnoreCase(packet.getSessionId())) {
				LOG.info("Valid session: from server.");
			} else {
				// check local cache
				session = ehClient.get(packet.getSessionId());
				if (session != null) {
					LOG.info("Valid session: from local cache.");
				} else {
					session = redisClient.get(packet.getSessionId());
					if (session != null) {
						LOG.info("Valid session: from remote cache.");
					} else {
						result.setCode(StateCode.FAILURE);
						result.setData(Constant.PAGE.PAGE_LOGIN.getPath());
					}
				}
				if (session != null) {
					String accessKey = ACCESS_URL_PREFIX + packet.getSessionId();
					Set<String> set = accessRedisClient.get(accessKey);
					if (set == null) {
						set = Sets.newHashSet();
					}
					set.add(getLogoutUrl(authId));
					if (set.size() == 1) {
						accessRedisClient.save(accessKey, set);
					} else {
						accessRedisClient.set(accessKey, set);
					}
					result.setData(Constant.KEY.DIRECT_HEADER.getKey() + packet.getCallback());
				}
			}
			return result;
		}
	}

	@Override
	public String getLogoutUrl(String authId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
