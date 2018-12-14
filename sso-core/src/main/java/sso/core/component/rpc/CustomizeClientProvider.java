package sso.core.component.rpc;

import okhttp3.OkHttpClient;

public interface CustomizeClientProvider {
	
	OkHttpClient getClient();
	
}
