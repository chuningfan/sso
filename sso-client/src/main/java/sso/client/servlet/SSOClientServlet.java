package sso.client.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;

import sso.client.sso.TokenService;
import sso.client.sso.Verifier;
import sso.common.dto.SSOKey;

public class SSOClientServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static TokenService tokenService;

	private String verifyURI;

	private String ssoURL;

	private String tokenServiceClass;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String authId = request.getParameter(SSOKey.KEY.AUTH_ID.getKey());
		if (request.getMethod().equals("GET") && authId != null) {
			String url = request.getParameter(SSOKey.KEY.CALLBACK_URL.getKey());
			Map<String, String> dataMap = Maps.newHashMap();
			Verifier.post(url, dataMap);
		} else if (request.getMethod().equals("POST") && verifyURI.equals(request.getRequestURI())) {
			// request from SSO service
			if (request.getParameter(SSOKey.KEY.AUTH_ID.getKey()) != null
					&& "true".equals(request.getParameter(SSOKey.KEY.CLIENT_VERIFY.getKey()))) {
				tokenService.createToken(authId, request, response);
			}
		} else {
			if (tokenService.isValid(request)) {
			} else {
				response.sendRedirect(ssoURL);
			}
		}
	}

	@Override
	public void init() throws ServletException {
		verifyURI = getInitParameter("verifyURI");
		ssoURL = getInitParameter("ssoURL");
		tokenServiceClass = getInitParameter("tokenServiceClass");
		if (tokenService == null)
			try {
				tokenService = (TokenService) Class.forName(tokenServiceClass).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
	}

	
	
	
}
