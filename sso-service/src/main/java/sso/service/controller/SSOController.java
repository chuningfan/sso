package sso.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sso.common.dto.LoginForm;
import sso.common.dto.RegistrationForm;
import sso.common.dto.UpdatePasswordForm;
import sso.core.service.AuthService;
import sso.service.auth.helper.ParameterHelper;
import sso.service.auth.helper.SecurityHelper;
import sso.service.config.ControllerPath;
import user.service.dto.UserInfo;

@RestController
@RequestMapping(ControllerPath.SSO)
public class SSOController {

	@Autowired
	private AuthService<UserInfo> authService;
	
	@PostMapping(ControllerPath.SSO_LOGIN)
	public UserInfo login(@RequestBody LoginForm form) throws Exception {
		ParameterHelper.valiateForm(form.getLoginName(), form.getPassword());
		String saltedPassword = SecurityHelper.addSalt(form.getPassword());
		return authService.login(form.getLoginName(), saltedPassword);
	}
	
	@PostMapping(ControllerPath.SSO_UPDATE_PASSWORD)
	public boolean updatePassword(@RequestBody UpdatePasswordForm form) throws Exception {
		ParameterHelper.valiateForm(form.getLoginName(), form.getPassword(), form.getOldPassword());
		String saltedPassword = SecurityHelper.addSalt(form.getPassword());
		String saltedOldPassword = SecurityHelper.addSalt(form.getOldPassword());
		return authService.updatePassword(form.getLoginName(), saltedPassword, saltedOldPassword);
	}
	
	@PostMapping(ControllerPath.SSO_REGISTER)
	public UserInfo register(@RequestBody RegistrationForm form) throws Exception {
		ParameterHelper.valiateForm(form.getLoginName(), form.getPassword());
		String saltedPassword = SecurityHelper.addSalt(form.getPassword());
		return authService.register(form.getLoginName(), saltedPassword);
	}
	
	@PostMapping(ControllerPath.SSO_VALIDATE_TOKEN)
	public boolean validateToken(String token) throws Exception {
		return true;
	}
	
	@PostMapping(ControllerPath.SSO_VALIDATE_SESSION)
	public boolean validateSession(HttpServletRequest request) throws Exception {
		return true;
	}
}
