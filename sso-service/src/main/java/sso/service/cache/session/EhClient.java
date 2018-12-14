package sso.service.cache.session;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import sso.core.service.IdentityService;

public class EhClient implements IdentityService<String, HttpSession> {

	@Override
	public HttpSession get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession save(String key, HttpSession value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession save(String key, HttpSession value, long timeout, TimeUnit timeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession set(String key, HttpSession value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession set(String key, HttpSession value, long timeout, TimeUnit timeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return false;
	}

}
