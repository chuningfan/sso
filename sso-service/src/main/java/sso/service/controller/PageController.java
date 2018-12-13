package sso.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sso.service.config.ControllerPath;

@RestController
@RequestMapping(ControllerPath.SSO_PAGE)
public class PageController {
	
	@GetMapping(ControllerPath.SSO_PAGE_LOGIN)
	public String loginPage() {
		return "login";
	}
	
}
