package sso.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import sso.core.annotation.EnableSSO;
import sso.service.processor.session.CheckProcessor;
import sso.service.processor.session.LoginProcessor;
import sso.service.servlet.SSOServlet;

@SpringBootApplication
@ServletComponentScan(basePackageClasses={SSOServlet.class})
@EnableSSO(processorClasses={CheckProcessor.class, LoginProcessor.class})
public class Bootstrap {

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}
	
}
