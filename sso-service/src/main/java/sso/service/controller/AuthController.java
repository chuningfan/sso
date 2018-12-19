package sso.service.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import sso.common.dto.SSOKey;
import sso.common.dto.UserInfo;
import sso.core.internal.dto.Constant;
import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.core.internal.dto.StateCode;
import sso.service.cache.session.RedisClient;
import sso.service.processor.session.LoginProcessor;

@RestController
public class AuthController {
	
	@Autowired
	private LoginProcessor loginProcessor;
	
	@Autowired
	private RedisClient redisClient;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@PostMapping(Constant.URL_LOGIN)
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result<UserInfo> result = new Result<UserInfo>();
		boolean isRememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));
		result = loginProcessor.process0(new SSORequest(request, response), result);
		String serviceId = request.getParameter(SSOKey.KEY.SERVICE_ID.getKey());
		// TODO return data to jquery, then redirect.
		if (StateCode.SUCCESS == result.getCode() && result.getData() != null) {
			HttpSession session = request.getSession();
			session.setAttribute(session.getId(), result.getData());
			if (isRememberMe) {
				session.setMaxInactiveInterval(Constant.TIME.STAY_IN_ACCESSED.getTime());
			}
			@SuppressWarnings("unchecked")
			Set<String> logoutSet = session.getAttribute(Constant.KEY.LOGOUT_URLS.getKey()) == null ?
					null : (Set<String>)session.getAttribute(Constant.KEY.LOGOUT_URLS.getKey());
			if(logoutSet == null) {
				logoutSet = Sets.newHashSet();
			}
			logoutSet.add(loginProcessor.getLogoutUrl(serviceId));
			String url = URLDecoder.decode(request.getParameter(SSOKey.KEY.CALLBACK_URL.getKey()), "UTF-8") + "?" + SSOKey.KEY.AUTH_ID.getKey() + "=" + session.getId() + "&" + 
					SSOKey.KEY.RBM.getKey() + "=" + (request.getParameter("rememberMe"));
			Map<String, String> res = Maps.newHashMap();
			res.put("url", url);
			try (PrintWriter writer = response.getWriter();) {
				writer.write(mapper.writeValueAsString(res));
				writer.flush();
			}
		}
	}
	
	@PostMapping(Constant.URL_VERIFY)
	public boolean verify(HttpServletRequest request, @RequestBody Map<String, String> dataMap) throws Exception {
		String authId = dataMap.get(SSOKey.KEY.AUTH_ID.getKey());
		return redisClient.hasLogged(authId);
	}
	
}
