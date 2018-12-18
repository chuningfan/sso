package sso.service.processor.session;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import sso.common.dto.SSOKey;
import sso.common.dto.UserInfo;
import sso.core.internal.dto.Constant;
import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.core.internal.dto.StateCode;
import sso.core.internal.handler.AbstractAuthHandler;
import sso.core.internal.handler.ChainHandler;
import sso.core.internal.processor.AbstractProcessor;
import sso.service.cache.session.EhClient;
import sso.service.cache.session.RedisClient;
import sso.service.impl.AuthServiceImpl;

public class LoginProcessor extends AbstractProcessor<UserInfo, SSORequest, Result<UserInfo>> {

	@Autowired
	private EhClient ehClient;
	
	@Autowired
	private RedisClient redisClient;
	
	@Autowired
	private AuthServiceImpl authServiceImpl;
	
	@Override
	public void initChain(ChainHandler<UserInfo, SSORequest, Result<UserInfo>> handler) {
		handler.addNode(new FormChecker()).addNode(new AuthChecker());
	}

	private class FormChecker extends AbstractAuthHandler<UserInfo, SSORequest, Result<UserInfo>> {
		@Override
		public Result<UserInfo> process0(SSORequest packet, Result<UserInfo> result) throws Exception {
			String loginName = packet.getRequest().getParameter("loginName");
			String password = packet.getRequest().getParameter("password");
			if (Objects.isNull(loginName) || loginName.trim().length() == 0 || 
					Objects.isNull(password) || password.trim().length() == 0) {
				result.setCode(StateCode.FAILURE)
				.setMessage("Invalid form args!");
			}
			return result;
		}
	}
	
	private class AuthChecker extends AbstractAuthHandler<UserInfo, SSORequest, Result<UserInfo>> {
		@Override
		public Result<UserInfo> process0(SSORequest packet, Result<UserInfo> result) throws Exception {
			String loginName = packet.getRequest().getParameter("loginName");
			String password = packet.getRequest().getParameter("password");
			UserInfo info = authServiceImpl.login(loginName, password);
			if (info != null) {
				result.setData(info);
			} else {
				result.setCode(StateCode.FAILURE);
				result.setMessage("Invalid login name or password");
			}
			return result;
		}
	}
	
	/**
	 * Unused method in current class.
	 */
	@Override
	public String getLogoutUrl(String serviceId) {
		return null;
	}

	
	
}
