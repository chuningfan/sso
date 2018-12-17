package sso.client.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;

import sso.client.sso.TokenService;
import sso.client.sso.Verifier;
import sso.common.dto.SSOKey;

public class SSOClientFilter implements Filter {


	private TokenService tokenService;
	
	private String verifyURI;
	
	private String ssoURL;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String authId = request.getParameter(SSOKey.KEY.AUTH_ID.getKey());
		if (request.getMethod().equals("GET") && authId != null) {
			String url = request.getParameter(SSOKey.KEY.CALLBACK_URL.getKey());
			Map<String, String> dataMap = Maps.newHashMap();
			Verifier.post(url, dataMap);
		} else if (request.getMethod().equals("POST") && verifyURI.equals(request.getRequestURI())) {
			// request from SSO service
			if (request.getParameter(SSOKey.KEY.AUTH_ID.getKey()) != null && "true".equals(request.getParameter(SSOKey.KEY.CLIENT_VERIFY.getKey()))) {
				tokenService.createToken(authId, request, response);
			}
		} else {
			if (tokenService.isValid(authId, request)) {
				chain.doFilter(request, response);
			} else {
				response.sendRedirect(ssoURL);
			}
		}
		chain.doFilter(request, response);
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

}
