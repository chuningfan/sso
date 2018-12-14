package sso.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import okhttp3.Response;
import sso.core.component.rpc.RequestClient;
import sso.core.component.web.filter.RequestHandler;
import sso.core.service.AuthService;
import sso.service.controller.Constant;
import user.service.dto.UserInfo;

public class AuthServiceImpl implements AuthService<String, UserInfo> {

	@Autowired
	private RequestClient requestClient;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public UserInfo login(String loginName, String password) throws IOException {
		String authId = RequestHandler.getRequest().getParameter(Constant.KEY.AUTH_ID.getKey());
		Map<String, String> dataMap = Maps.newHashMap();
		dataMap.put("loginName", loginName);
		dataMap.put("password", password);
		Response response = requestClient.post(getAuthUrl(authId), dataMap, null);
		if (response.isSuccessful()) {
			byte[] bytes = response.body().bytes();
			return mapper.readValue(bytes, UserInfo.class);
		}
		return null;
	}

	@Override
	public boolean logout(String key) throws IOException {
		
		return false;
	}

	@Override
	public String getAuthUrl(String authId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
