package sso.service.controller;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import sso.common.dto.SSOKey;
import sso.common.dto.UserInfo;
import sso.core.internal.dto.Constant;
import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.service.processor.session.LoginProcessor;

@RestController
public class AuthController {
	
	@Autowired
	private LoginProcessor loginProcessor; 
	
	@PostMapping(Constant.URL_LOGIN)
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result<UserInfo> result = new Result<UserInfo>();
		result = loginProcessor.process0(new SSORequest(request, response), result);
		// TODO return data to jquery, then redirect.
	}
	
}
