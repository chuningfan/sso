package sso.core.internal.rpc;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ClientProvider {
	
	public static final OkHttpClient getClientWithConfig() {
		return new OkHttpClient().newBuilder().callTimeout(3000, TimeUnit.MILLISECONDS)
				.readTimeout(3000, TimeUnit.MILLISECONDS)
				.writeTimeout(3000, TimeUnit.MILLISECONDS)
				.build();
	}
	
	public static final OkHttpClient getClientWithConfig(CustomizeClientProvider provider) {
		return provider.getClient();
	}
	
}
