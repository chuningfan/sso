package sso.service.web.validateChain;

import sso.core.internal.AbstractAuthHandler;
import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;

public abstract class AbstractValidator extends AbstractAuthHandler<String, SSORequest, Result<String>> {

	public AbstractValidator(String handlerInfo, AbstractAuthHandler<String, SSORequest, Result<String>> nextHandler,
			Result<String> result) throws Exception {
		super(handlerInfo, nextHandler, result);
	}

	protected boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	
}
