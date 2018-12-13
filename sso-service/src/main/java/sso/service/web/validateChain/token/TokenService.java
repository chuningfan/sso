package sso.service.web.validateChain.token;

import org.springframework.stereotype.Service;

import sso.core.service.IdentityService;

@Service
public class TokenService implements IdentityService<String> {

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveOrUpdate(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return true;
	}

}
