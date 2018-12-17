package sso.service.processor.session;

import java.io.IOException;
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
			boolean isRememberMe = Boolean.parseBoolean(packet.getRequest().getParameter("rememberMe"));
			UserInfo info = authServiceImpl.login(loginName, password);
			if (info != null) {
				result.setData(info);
				HttpSession session = packet.getRequest().getSession(true);
				String sessionId = session.getId();
//				redisClient.save(sessionId, session, Constant.TIME.LOGIN_ACCESSED.getTime(), Constant.TIME.LOGIN_ACCESSED.getTimeUnit());
//				ehClient.save(sessionId, session, Constant.TIME.LOGIN_ACCESSED.getTime(), Constant.TIME.LOGIN_ACCESSED.getTimeUnit());
				if (isRememberMe) {
					session.setMaxInactiveInterval(Constant.TIME.STAY_IN_ACCESSED.getTime());
//					String value = SaltDealer.getSaltedString(password) + SaltDealer.base64encrypt(loginName);
//					HttpUtil.setCookie(packet.getRequest(), packet.getResponse(), Constant.COOKIE_KEY.REMEMBER_ME.getKey(), value, Constant.TIME.STAY_IN_ACCESSED.getTime(), null);
					session.setAttribute(sessionId, info);
				}
				packet.getResponse().sendRedirect(packet.getCallback() + "?" + SSOKey.KEY.AUTH_ID + "=" + session.getId() + "&" + SSOKey.KEY.RBM + "=" + isRememberMe);
			} else {
				result.setCode(StateCode.FAILURE);
				result.setMessage("Invalid login name or password");
			}
			return result;
		}
	}
	
	public Result<UserInfo> processForChecking(HttpServletRequest request, HttpServletResponse response, String loginName, String saltedPassword) throws IOException {
		UserInfo info = authServiceImpl.login(loginName, saltedPassword);
		Result<UserInfo> result = new Result<UserInfo>();
		if (info != null) {
			result.setData(info);
			HttpSession session = request.getSession(true);
			String sessionId = session.getId();
			session.setAttribute(sessionId, info);
		} else {
			result.setCode(StateCode.FAILURE);
			result.setMessage("Invalid login name or password");
		}
		return result;
	}
	
	/**
	 * Unused method in current class.
	 */
	@Override
	public String getLogoutUrl(String authId) {
		return null;
	}

	
	
}
