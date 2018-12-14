package sso.core.annotation;

import java.util.Iterator;
import java.util.Objects;
import java.util.ServiceLoader;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.google.common.collect.Lists;

import sso.core.component.rpc.RequestClient;
import sso.core.component.web.filter.RequestHandler;
import sso.core.internal.processor.AbstractProcessor;
import sso.core.service.AuthService;
import sso.core.service.IdentityService;

public class SSOImporter implements ImportBeanDefinitionRegistrar {

	private static final Logger LOG = LoggerFactory.getLogger(SSOImporter.class);
	
	private static final String SCOPE_SINGLETON = "singleton";
	
	@Override
	@SuppressWarnings("rawtypes")
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		@SuppressWarnings("unchecked")
		Class<? extends AbstractProcessor> processorClass = (Class<? extends AbstractProcessor>) metadata.getAnnotationAttributes("sso.core.annotation.EnableSSO").get("processorClass");
		ClassLoader externalClassLoader = null;
		try {
			externalClassLoader = getThreadClassLoader();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("cannot find external class loader, try to get current class loader to load services");
			externalClassLoader = SSOImporter.class.getClassLoader();
		}
		ServiceLoader<AuthService> authServiceLoader = ServiceLoader.load(AuthService.class, externalClassLoader);
		Iterator<AuthService> authItr = authServiceLoader.iterator();
		ServiceLoader<IdentityService> identityServiceLoader = ServiceLoader.load(IdentityService.class, externalClassLoader);
		Iterator<IdentityService> idenItr = identityServiceLoader.iterator();
		AuthService authService = null;
		while (authItr.hasNext()) {
			authService = authItr.next();
			if (authService != null) {
				break;
			}
		}
		IdentityService identityService = null;
		while (idenItr.hasNext()) {
			identityService = idenItr.next();
			if (identityService != null) {
				registry.registerBeanDefinition(identityService.getClass().getName(), getBeanDefinition(identityService.getClass(), SCOPE_SINGLETON));
			}
		}
		if (Objects.isNull(authService)) {
			try {
				throw new Exception("Must provide an implementation for [" + AuthService.class.getName() + "] by SPI");
			} catch (Exception e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		registry.registerBeanDefinition("authService", getBeanDefinition(authService.getClass(), SCOPE_SINGLETON));
		registry.registerBeanDefinition("requestHandler", getFilterBeanDefinition(new RequestHandler()).getRawBeanDefinition());
		registry.registerBeanDefinition("requestClient", getBeanDefinition(RequestClient.class, SCOPE_SINGLETON));
		registry.registerBeanDefinition("authProcessor", getBeanDefinition(processorClass, SCOPE_SINGLETON));
		
	}

	private ClassLoader getThreadClassLoader() throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			throw new Exception("No class loader is found for finding available services.");
		}
		return classLoader;
	}
	
	private AbstractBeanDefinition getBeanDefinition(Class<?> clazz, String beanScope) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
		builder.setScope(beanScope);
		return builder.getRawBeanDefinition();
	}
	
	private BeanDefinitionBuilder getFilterBeanDefinition(Filter filter) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(FilterRegistrationBean.class);
		builder.addPropertyValue("filter", filter);
		builder.addPropertyValue("urlPatterns", Lists.newArrayList("/*"));
		builder.addPropertyValue("order", 0);
		builder.addPropertyValue("name", filter.getClass().getName());
		builder.setScope(SCOPE_SINGLETON);
		return builder;
	}
	
}
