package sso.core.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.core.internal.dto.StateCode;

public abstract class AbstractAuthHandler<T, P extends SSORequest, R extends Result<T>> implements AuthHandler<T, P, R> {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractAuthHandler.class);
	
	protected T redirect;
	
	protected AbstractAuthHandler<T, P, R> nextHandler;
	
	protected R result;
	
	public AbstractAuthHandler(T redirect, AbstractAuthHandler<T, P, R> nextHandler, R result) throws Exception {
		if (result == null) {
			throw new Exception("Result cannot be null");
		}
		this.redirect = redirect;
		this.nextHandler = nextHandler;
		this.result = result;
	}

	public final R process(P packet) {
		if (StateCode.FAILURE == result.getCode()) {
			LOG.info("Result code is failure, return result directly.");
			return result;
		}
		beforeProcess(redirect, packet, result);
		process0(packet, result);
		if (nextHandler != null && StateCode.SUCCESS == result.getCode()) {
			result = nextHandler.process(packet);
		}
		afterProcess(redirect, packet, result);
		return result;
	}
	
	protected void beforeProcess(T data, P packet, R result) {}
	
	protected void afterProcess(T data, P packet, R result) {}
	
}
