package sso.core.internal.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.core.internal.dto.StateCode;

public abstract class AbstractAuthHandler<T, P extends SSORequest, R extends Result<T>> implements AuthHandler<T, P, R> {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractAuthHandler.class);
	
	protected T data;
	
	protected AbstractAuthHandler<T, P, R> nextHandler;
	
	public final R process(P packet, R result) {
		if (StateCode.FAILURE == result.getCode()) {
			LOG.info("Result code is failure, return result directly.");
			return result;
		}
		beforeProcess(data, packet, result);
		try {
			result = process0(packet, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(StateCode.FAILURE).setExc(e).setMessage(e.getMessage());
			return result;
		}
		if (nextHandler != null && StateCode.SUCCESS == result.getCode()) {
			result = nextHandler.process(packet, result);
		}
		afterProcess(data, packet, result);
		return result;
	}
	
	protected void beforeProcess(T data, P packet, R result) {}
	
	protected void afterProcess(T data, P packet, R result) {}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public AbstractAuthHandler<T, P, R> getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(AbstractAuthHandler<T, P, R> nextHandler) {
		this.nextHandler = nextHandler;
	}
	
}
