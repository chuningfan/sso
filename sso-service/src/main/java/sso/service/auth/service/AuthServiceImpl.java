package sso.service.auth.service;

import org.springframework.stereotype.Service;

import sso.core.service.AuthService;
import user.service.dto.UserInfo;

@Service
public class AuthServiceImpl implements AuthService<UserInfo> {

	@Override
	public UserInfo login(String loginName, String saltedPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLocked(String loginName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updatePassword(String loginName, String saltedPassword, String saltedOldPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserInfo register(String loginName, String saltedPassword) {
		// TODO Auto-generated method stub
		return null;
	}


}
