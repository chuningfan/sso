package sso.service.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import sso.core.service.IdentityService;

@Component
public class AccessRedisClient implements IdentityService<String, Set<String>> {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> get(String key) {
		return (Set<String>) redisTemplate.opsForSet().pop(key);
	}

	@Override
	public Set<String> save(String key, Set<String> value) {
		redisTemplate.opsForSet().add(key, value);
		return value;
	}

	@Override
	public Set<String> save(String key, Set<String> value, long timeout, TimeUnit timeUnit) {
		save(key, value);
		redisTemplate.expire(key, timeout, timeUnit);
		return value;
	}

	@Override
	public Set<String> set(String key, Set<String> value) {
		save(key, value);
		return value;
	}

	@Override
	public Set<String> set(String key, Set<String> value, long timeout, TimeUnit timeUnit) {
		save(key, value, timeout, timeUnit);
		return value;
	}

	@Override
	public boolean remove(String key) {
		redisTemplate.delete(key);
		return true;
	}

	

}
