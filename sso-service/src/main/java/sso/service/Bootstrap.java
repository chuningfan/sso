package sso.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sso.core.annotation.EnableSSO;
import sso.service.processor.session.CheckProcessor;

@SpringBootApplication
@EnableSSO(processorClass=CheckProcessor.class)
public class Bootstrap {

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}
	
}
