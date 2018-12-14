package sso.core.service;

import java.io.IOException;

public interface AuthService<K, V> {
	
	V login(String loginName, String saltedPassword) throws IOException;
	
	boolean logout(K key) throws IOException;
	
	String getAuthUrl(String authId);
	
}
