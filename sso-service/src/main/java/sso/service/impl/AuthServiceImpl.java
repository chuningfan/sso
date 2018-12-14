package sso.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import okhttp3.Response;
import sso.common.util.Auth;
import sso.core.component.rpc.RequestClient;
import sso.core.component.web.filter.RequestHandler;
import sso.core.service.AuthService;
import user.service.dto.UserInfo;

public class AuthServiceImpl implements AuthService<UserInfo> {

	@Autowired
	private RequestClient requestClient;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public UserInfo login(String loginName, String saltedPassword) throws IOException {
		String url = RequestHandler.getRequest().getAttribute(Auth.AUTH_LOGIN_URL).toString();
		Map<String, String> dataMap = Maps.newHashMap();
		dataMap.put("loginName", loginName);
		dataMap.put("saltedPassword", saltedPassword);
		Response response = requestClient.post(url, dataMap, null);
		if (response.isSuccessful()) {
			byte[] bytes = response.body().bytes();
			return mapper.readValue(bytes, UserInfo.class);
		}
		return null;
	}

	@Override
	public boolean logout(UserInfo data) throws IOException {
		String url = RequestHandler.getRequest().getAttribute(Auth.AUTH_LOGOUT_URL).toString();
		Response response = requestClient.post(url, requestClient.toRequestBody(data), null);
		if (response.isSuccessful()) {
			return Boolean.parseBoolean(response.body().string());
		}
		return false;
	}

}
