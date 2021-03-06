package sso.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import sso.common.dto.SSOKey;
import sso.common.dto.UserInfo;
import sso.core.component.rpc.CustomizeClientProvider;
import sso.core.component.rpc.RequestClient;
import sso.core.component.web.filter.RequestHandler;
import sso.core.service.AuthService;
import sso.service.helper.DataHelper;

public class AuthServiceImpl implements AuthService<String, UserInfo> {

	@Autowired
	private DataHelper dataHelper;
	
	@Autowired
	private RequestClient requestClient;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public UserInfo login(String loginName, String password) throws IOException {
		String serviceId = RequestHandler.getRequest().getParameter(SSOKey.KEY.SERVICE_ID.getKey());
		Map<String, String> dataMap = Maps.newHashMap();
		dataMap.put(SSOKey.PARAMETER_KEY.LOGIN_NAME.getKey(), loginName);
		dataMap.put(SSOKey.PARAMETER_KEY.PASSWORD.getKey(), password);
		Response response = requestClient.post(getAuthUrl(serviceId), dataMap, new CustomizeClientProvider() {
			@Override
			public OkHttpClient getClient() {
				return new OkHttpClient().newBuilder()
						.readTimeout(10, TimeUnit.SECONDS)
						.writeTimeout(10, TimeUnit.SECONDS)
						.connectTimeout(10, TimeUnit.SECONDS)
						.build();
			}
		});
		if (response.isSuccessful()) {
			byte[] bytes = response.body().bytes();
			if (bytes.length > 0) {
				return mapper.readValue(bytes, UserInfo.class);
			}
		}
		return null;
	}

	@Override
	public String getAuthUrl(String serviceId) {
		String data = dataHelper.readProperty(serviceId);
		if (data != null && !"".equals(data.trim())) {
			return data.split(";")[0];
		} else {
			throw new RuntimeException("Cannot find data by service ID " + serviceId);
		}
	}
	
}

