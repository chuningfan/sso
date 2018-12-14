package sso.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import sso.core.internal.processor.AbstractProcessor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SSOImporter.class)
public @interface EnableSSO {
	
	@SuppressWarnings("rawtypes")
	Class< ? extends AbstractProcessor> processorClass();
	
}
