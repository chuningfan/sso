package sso.core.internal;

import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;

interface AuthHandler<T, P extends SSORequest, R extends Result<T>> {
	
	 R process0(P packet, R result);
	
}
