package sso.service.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import sso.core.service.IdentityService;

public class AccessRedisClient implements IdentityService<String, Set<String>> {

	@Override
	public Set<String> get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> save(String key, Set<String> value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> save(String key, Set<String> value, long timeout, TimeUnit timeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> set(String key, Set<String> value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> set(String key, Set<String> value, long timeout, TimeUnit timeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
