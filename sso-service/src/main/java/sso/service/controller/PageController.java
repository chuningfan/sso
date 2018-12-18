package sso.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sso.core.internal.dto.Constant;

@Controller
public class PageController {
	
	@RequestMapping(Constant.LOGIN_PAGE)
	public String gotoLogin() {
		return "login";
	}
	
	@RequestMapping(Constant.VERIFY_PAGE)
	public String gotoVerify() {
		return "verify";
	}
	
}
