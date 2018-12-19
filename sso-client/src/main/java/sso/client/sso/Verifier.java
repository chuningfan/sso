package sso.client.sso;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Verifier {

	public static final byte METHOD_GET = 0;
	public static final byte METHOD_POST = 1;

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private static final Builder getBuilder(byte method, RequestBody requestBody) {
		Builder builder = new Request.Builder();
		switch (method) {
		case METHOD_POST:
			builder = builder.post(requestBody);
			break;
		default:
			builder = builder.get();
			break;
		}
		return builder;
	}
	
	public static final Response post(String url, Map<String, String> dataMap) throws IOException {
		String params = objectMapper.writeValueAsString(dataMap);
		MediaType JSON = MediaType.parse("application/json;charset=utf-8");
		RequestBody requestBody = RequestBody.create(JSON, params);
		OkHttpClient okHttpClient = getClientWithConfig();
		Builder builder = getBuilder(METHOD_POST, requestBody);
		builder.header("content-type", "application/json;charset=utf-8");
		final Request request = builder.url(url).build();
		final Call call = okHttpClient.newCall(request);
		return call.execute();    
	}
	
	private static final OkHttpClient getClientWithConfig() {
		return new OkHttpClient().newBuilder()
				.readTimeout(5000, TimeUnit.MILLISECONDS)
				.writeTimeout(5000, TimeUnit.MILLISECONDS)
				.build();
	}
	
}
