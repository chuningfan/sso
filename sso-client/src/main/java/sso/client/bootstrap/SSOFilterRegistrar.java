package sso.client.bootstrap;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import sso.client.filter.SSOClientFilter;
import sso.client.sso.TokenService;

public class SSOFilterRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		
		String serviceId = (String) metadata.getAnnotationAttributes("sso.client.bootstrap.EnableSSOClient").get("serviceId");
		
		String SSOAddress = (String) metadata.getAnnotationAttributes("sso.client.bootstrap.EnableSSOClient").get("SSOAddress");
		
		String[] exclusions = (String[]) metadata.getAnnotationAttributes("sso.client.bootstrap.EnableSSOClient").get("exclusions");
		
		String tokenServiceClass = (String) metadata.getAnnotationAttributes("sso.client.bootstrap.EnableSSOClient").get("tokenServiceClass");
		
		if (serviceId == null || "".equals(serviceId.trim())) {
			throwNullException("serviceId");
		}
		if (SSOAddress == null || "".equals(SSOAddress.trim())) {
			throwNullException("SSOAddress");
		}
		if (tokenServiceClass == null || "".equals(tokenServiceClass.trim())) {
			ServiceLoader<TokenService> service = ServiceLoader.load(TokenService.class);
			Iterator<TokenService> itr = service.iterator();
			while (itr.hasNext()) {
				TokenService tempService = itr.next();
				if (tempService != null) {
					tokenServiceClass = tempService.getClass().getName();
					break;
				}
			}
			if (tokenServiceClass == null || "".equals(tokenServiceClass.trim())) {
				throw new RuntimeException("tokenServiceClass cannot be null or empty! You can also use SPI to initialize it.");
			}
		}
		SSOClientFilter filter = new SSOClientFilter();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(FilterRegistrationBean.class);
		builder.addPropertyValue("filter", filter);
		builder.addPropertyValue("urlPatterns", Lists.newArrayList("/*"));
		builder.addPropertyValue("order", 0);
		builder.addPropertyValue("name", filter.getClass().getName());
		Map<String, String> initParams = Maps.newLinkedHashMap();
		initParams.put("serviceId", serviceId);
		initParams.put("SSOAddress", SSOAddress);
		StringBuilder exclusionsString = new StringBuilder();
		if (exclusions != null && exclusions.length > 0) {
			for (String str: exclusions) {
				if (exclusionsString.length() == 0) {
					exclusionsString.append(str);
				} else {
					exclusionsString.append(";" + str);
				}
			}
		}
		initParams.put("exclusions", exclusionsString.toString());
		initParams.put("tokenServiceClass", tokenServiceClass);
		builder.addPropertyValue("initParameters", initParams);
		builder.setScope("singleton");
		registry.registerBeanDefinition("requestHandler", builder.getRawBeanDefinition());
	}

	private void throwNullException(String property) {
		throw new RuntimeException(property + " cannot be null or empty!");
	}
}
