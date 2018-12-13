package sso.service.web.validateChain.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import sso.core.internal.AbstractAuthHandler;
import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.service.web.validateChain.AbstractValidator;

public class SessionValidator extends AbstractValidator {

	public SessionValidator(String handlerInfo, AbstractAuthHandler<String, SSORequest, Result<String>> nextHandler,
			Result<String> result) throws Exception {
		super(handlerInfo, nextHandler, result);
	}

	@Override
	public Result<String> process0(SSORequest packet, Result<String> result) {
		HttpServletRequest request = packet.getRequest();
		HttpSession session = request.getSession(false);
		if (session == null) {
			nextHandler = null;
			result.setData(redirect);
		} else {
			
		}
		return null;
	}

}
