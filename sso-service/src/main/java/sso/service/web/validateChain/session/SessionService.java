package sso.service.web.validateChain.session;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import sso.core.service.IdentityService;

@Service
public class SessionService implements IdentityService<HttpSession> {

	@Override
	public HttpSession get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession saveOrUpdate(String key, HttpSession value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return true;
	}

}
