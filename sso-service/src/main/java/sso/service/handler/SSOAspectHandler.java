package sso.service.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SSOAspectHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(SSOAspectHandler.class);
	
	@Around("execution(* sso.service.controller.*.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		Object target = pjp.getTarget();
		Signature signature = pjp.getSignature();
		String methodName = signature.getName();
		String argTypes = args == null || args.length == 0 ? "" : getArgsClasses(args);
		String methodInfo = target.getClass().getName() + "." + methodName + "(" + argTypes + ")";
		LOG.info("Going to invoke " + methodInfo);
		Object result = pjp.proceed();
		LOG.info("Invoking " + methodInfo + " Completed");
		return result;
	}
	
	private String getArgsClasses(Object[] objs) {
		StringBuffer buffer = new StringBuffer();
		for (Object obj: objs) {
			if (buffer.length() == 0) {
				buffer.append(obj.getClass().getName());
			}
			buffer.append(", " + obj.getClass().getName());
		}
		return buffer.toString();
	}
	
}
