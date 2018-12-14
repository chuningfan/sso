package sso.core.internal.processor;

import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.core.internal.handler.AuthHandler;
import sso.core.internal.handler.ChainHandler;

public interface Processor<T, P extends SSORequest, R extends Result<T>> extends AuthHandler<T, P, R> {

	void initChain(ChainHandler<T, P, R> handler);
	
}
