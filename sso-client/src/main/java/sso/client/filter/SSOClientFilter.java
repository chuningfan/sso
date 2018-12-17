package sso.client.filter;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
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

	private static TokenService tokenService;

	private String verifyURI;

	private String ssoURL;

	private String tokenServiceClass;
	
	private String loginURI;

	@Override
	public void init(FilterConfig config) throws ServletException {
		verifyURI = config.getInitParameter("verifyURI");
		ssoURL = config.getInitParameter("ssoURL");
		tokenServiceClass = config.getInitParameter("tokenServiceClass");
		loginURI = config.getInitParameter("loginURI");
		if (tokenService == null)
			try {
				tokenService = (TokenService) Class.forName(tokenServiceClass).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String currentURL = request.getRequestURL().toString();
		String encodedURL = URLEncoder.encode(currentURL, "UTF-8");
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
				chain.doFilter(request, response);
			}
		} else {
			if (!loginURI.equals(request.getRequestURI())) {
				if (tokenService.isValid(authId, request)) {
					chain.doFilter(request, response);
				} else {
					if (ssoURL == null || "".equals(ssoURL.trim())) {
						throw new RuntimeException("SSO URL cannot be null or empty.");
					}
					if(ssoURL.startsWith("http://") || ssoURL.startsWith("https://")) {
						response.sendRedirect(ssoURL + "?" + SSOKey.KEY.CALLBACK_URL + "=" + encodedURL);
					} else {
						response.sendRedirect("http://" + ssoURL + "?" + SSOKey.KEY.CALLBACK_URL + "=" + encodedURL);
					} 
				}
			} else {
				chain.doFilter(request, response);
			}
		}
	}

}
