package sso.core.service;

import java.io.IOException;

public interface AuthService<T> {
	
	T login(String loginName, String saltedPassword) throws IOException;
	
	boolean logout(T data) throws IOException;
	
}
