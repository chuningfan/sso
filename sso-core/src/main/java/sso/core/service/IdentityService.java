package sso.core.service;

public interface IdentityService<T> {
	
	T get(String key);
	
	T saveOrUpdate(String key, T value);
	
	boolean remove(String key);
}
