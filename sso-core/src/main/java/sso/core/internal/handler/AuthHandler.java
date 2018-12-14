package sso.core.internal.handler;

import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;

public interface AuthHandler<T, P extends SSORequest, R extends Result<T>> {
	
	 R process0(P packet, R result) throws Exception;
	
}
