package sso.service.web.validateChain.session;

import sso.core.internal.AbstractAuthHandler;
import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.service.web.validateChain.AbstractValidator;

public class BasicSessionValidator extends AbstractValidator {

	public BasicSessionValidator(String handlerInfo,
			AbstractAuthHandler<String, SSORequest, Result<String>> nextHandler, Result<String> result)
			throws Exception {
		super(handlerInfo, nextHandler, result);
	}

	@Override
	public Result<String> process0(SSORequest packet, Result<String> result) {
		String callback = packet.getCallback();
		if (isEmpty(callback)) {
			Exception exc = new Exception("Callback URL is NULL, cannot redirect to previous page.");
			result.turnToFailure();
			result.setExc(exc);
		}
		return result;
	}

}
