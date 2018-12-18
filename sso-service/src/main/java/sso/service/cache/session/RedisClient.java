package sso.service.cache.session;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import sso.common.dto.UserInfo;
import sso.core.service.IdentityService;

@Component
public class RedisClient implements IdentityService<String, HttpSession> {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public HttpSession get(String key) {
		return null;
	}

	@Override
	public HttpSession save(String key, HttpSession value) {
		redisTemplate.opsForValue().set(key, value);
		return value;
	}

	@Override
	public HttpSession save(String key, HttpSession value, long timeout, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
		return value;
	}

	@Override
	public HttpSession set(String key, HttpSession value) {
		save(key, value);
		return value;
	}

	@Override
	public HttpSession set(String key, HttpSession value, long timeout, TimeUnit timeUnit) {
		save(key, value, timeout, timeUnit);
		return value;
	}

	@Override
	public boolean remove(String key) {
		redisTemplate.delete(key);
		return true;
	}

	public boolean hasLogged(String key) {
		return redisTemplate.hasKey("spring:session:sessions:" + key);
	}
	
}
