package sso.service.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringHelper implements ApplicationContextAware {

	private static ApplicationContext ctx;
	
	private static ConfigurableApplicationContext cctx;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		SpringHelper.ctx = ctx;
		cctx = (ConfigurableApplicationContext) ctx;
	}

	public static final <T> T getComponent(Class<? extends T> clazz) {
		return ctx.getBean(clazz);
	}
	
	public ConfigurableApplicationContext getConfigurableContext() {
		return cctx;
	}
	
}
