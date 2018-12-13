package sso.core.internal.rpc;

import okhttp3.OkHttpClient;

public interface CustomizeClientProvider {
	
	OkHttpClient getClient();
	
}
