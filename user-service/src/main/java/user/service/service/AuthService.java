package user.service.service;

import user.service.dto.RegistrationInfo;
import user.service.dto.UserInfo;

public interface AuthService {
	
	UserInfo login(String loginName, String saltedPassword);
	
	boolean isLocked(String loginName);
	
	int lockUser(Long userId);
	
	int unlockUser(Long userId);
	
	int inactivateUser(Long userId);
	
	int activateUser(Long userId);
	
	UserInfo regUser(RegistrationInfo regInfo);
	
	int updatePassword(String loginName, String saltedPassword, String newSaltedPassword);
	
}
