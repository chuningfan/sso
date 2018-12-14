package sso.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import sso.core.annotation.EnableSSO;
import sso.service.processor.session.CheckProcessor;
import sso.service.servlet.SSOServlet;

@SpringBootApplication
@ServletComponentScan(basePackageClasses={SSOServlet.class})
@EnableSSO(processorClass=CheckProcessor.class)
public class Bootstrap {

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}
	
}
