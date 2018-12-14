package sso.core.internal.processor;

import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.core.internal.handler.ChainHandler;

public abstract class AbstractProcessor<T, P extends SSORequest, R extends Result<T>> implements Processor<T, P, R> {
	
	private ChainHandler<T, P, R> chain;
	
	public AbstractProcessor() {
		chain = new ChainHandler<T, P, R>();
		initChain(chain);
	}

	@Override
	public R process0(P packet, R result) throws Exception {
		return chain.start(packet, result);
	}
	
	public abstract String getLogoutUrl(String authId);
	
}
