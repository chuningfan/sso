package sso.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import sso.core.internal.dto.Constant;
import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.service.processor.session.LoginProcessor;
import user.service.dto.UserInfo;

@RestController
public class AuthController {
	
	@Autowired
	private LoginProcessor loginProcessor; 
	
	@PostMapping(Constant.URL_LOGIN)
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result<UserInfo> result = new Result<UserInfo>();
		result = loginProcessor.process0(new SSORequest(request, response), result);
		if (result.getData() == null && result.getMessage() != null) {
			
		}
	}
	
}
