package sso.client.sso;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Verifier {

	public static final byte METHOD_GET = 0;
	public static final byte METHOD_POST = 1;

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
		OkHttpClient okHttpClient = getClientWithConfig();
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
	
	private static final OkHttpClient getClientWithConfig() {
		return new OkHttpClient().newBuilder()
				.readTimeout(3000, TimeUnit.MILLISECONDS)
				.writeTimeout(3000, TimeUnit.MILLISECONDS)
				.build();
	}
	
}
