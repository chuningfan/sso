package sso.core.component.rpc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestClient {

	public static final byte METHOD_GET = 0;
	public static final byte METHOD_POST = 1;

	private Builder getBuilder(byte method, RequestBody requestBody) {
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

	
	public Response get(String url, CustomizeClientProvider customizeClientProvider) throws IOException {
		OkHttpClient okHttpClient = customizeClientProvider == null ? getClientWithConfig() : customizeClientProvider.getClient();
		Builder builder = getBuilder(METHOD_GET, null);
		final Request request = builder.url(url).build();
		final Call call = okHttpClient.newCall(request);
		return call.execute();
	}
	
	public Response post(String url, RequestBody requestBody, CustomizeClientProvider customizeClientProvider) throws IOException {
		OkHttpClient okHttpClient = customizeClientProvider == null ? getClientWithConfig() : customizeClientProvider.getClient();
		Builder builder = getBuilder(METHOD_POST, requestBody);
		final Request request = builder.url(url).build();
		final Call call = okHttpClient.newCall(request);
		return call.execute();
	}
	
	public Response post(String url, Map<String, String> dataMap, CustomizeClientProvider customizeClientProvider) throws IOException {
		OkHttpClient okHttpClient = customizeClientProvider == null ? getClientWithConfig() : customizeClientProvider.getClient();
		FormBody.Builder formbody = new FormBody.Builder();
		for (String key : dataMap.keySet()) {
            formbody.add(key, dataMap.get(key));
        }
        FormBody body = formbody.build();
        Builder builder = getBuilder(METHOD_POST, body);
        final Request request = builder.url(url).build();
        Call call = okHttpClient.newCall(request);
        return call.execute();    
	}
	
	public RequestBody toRequestBody(Object obj) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);) {
			oos.writeObject(obj);
			oos.flush();
			byte[] content = baos.toByteArray();
			return RequestBody.create(MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE), content);
		}
	}
	
	public static final OkHttpClient getClientWithConfig() {
		return new OkHttpClient().newBuilder()
				.readTimeout(3000, TimeUnit.MILLISECONDS)
				.writeTimeout(3000, TimeUnit.MILLISECONDS)
				.build();
	}

}
