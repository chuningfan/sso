package sso.core.service;

import java.io.IOException;

public interface AuthService<K, V> {
	
	V login(String loginName, String saltedPassword) throws IOException;
	
	String getAuthUrl(String serviceId);
	
}
