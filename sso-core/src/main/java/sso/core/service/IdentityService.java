package sso.core.service;

import java.util.concurrent.TimeUnit;

public interface IdentityService<K, V> {
	
	V get(K key);
	
	V save(K key, V value);
	
	V save(K key, V value, long timeout, TimeUnit timeUnit);
	
	V set(K key, V value);
	
	V set(K key, V value, long timeout, TimeUnit timeUnit);
	
	boolean remove(K key);
}
