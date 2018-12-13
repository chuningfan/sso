package sso.core.service;

public interface AuthService<T> {
	
	T login(String loginName, String saltedPassword);
	
	boolean isLocked(String loginName);
	
	boolean updatePassword(String loginName, String saltedPassword, String saltedOldPassword);
	
	T register(String loginName, String saltedPassword);
	
}
