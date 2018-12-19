package sso.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sso.service.helper.DataHelper;

@RestController
public class ConfigController {

	@Autowired
	private DataHelper dataHelper;
	
	@PostMapping("/config")
	public boolean addData(@RequestParam String serviceId, @RequestParam String authURL, @RequestParam String logoutURL) {
		return dataHelper.writeProperty(serviceId, authURL + ";" + logoutURL);
	}
	
}
