package sso.client.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import okhttp3.Response;
import sso.client.sso.TokenService;
import sso.client.sso.Verifier;
import sso.common.dto.SSOKey;

public class SSOClientFilter implements Filter {

	private static TokenService tokenService;

	private String ssoURL;

	private String tokenServiceClass;

	private String exclusions;

	private static List<String> exclusionList;

	@Override
	public void init(FilterConfig config) throws ServletException {
		ssoURL = config.getInitParameter("ssoURL");
		tokenServiceClass = config.getInitParameter("tokenServiceClass");
		exclusions = config.getInitParameter("exclusions");
		if (exclusionList == null && exclusions != null && exclusions.trim().length() > 0) {
			String splitter = ";";
			if (exclusions.indexOf(",") > -1) {
				splitter = ",";
			}
			String[] exclusionArray = exclusions.split(splitter);
			exclusionList = Lists.newArrayList(exclusionArray);
		}
		if (tokenService == null) {
			try {
				tokenService = (TokenService) Class.forName(tokenServiceClass).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
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
		String rememberMe = request.getParameter(SSOKey.KEY.RBM.getKey());
		if (exclusionList.contains(request.getRequestURI())) {
			chain.doFilter(request, response);
		} else {
			if (authId != null) {
				if (tokenService.isValid(request)) {
					chain.doFilter(request, response);
				} else {
					if (authId != null) {
						String url = request.getParameter(SSOKey.KEY.CALLBACK_URL.getKey());
						Map<String, String> dataMap = Maps.newHashMap();
						dataMap.put(SSOKey.KEY.AUTH_ID.getKey(), authId);
						Response res = Verifier.post(url, dataMap);
						if (Boolean.parseBoolean(res.body().string())) {
							tokenService.createToken(authId, request, response, Boolean.parseBoolean(rememberMe));						}
					}
				}
			} else if (tokenService.isValid(request)) {
				chain.doFilter(request, response);
			} else {
				if (ssoURL == null || "".equals(ssoURL.trim())) {
					throw new RuntimeException("SSO URL cannot be null or empty.");
				}
				if (ssoURL.startsWith("http://") || ssoURL.startsWith("https://")) {
					response.sendRedirect(ssoURL + "?" + SSOKey.KEY.CALLBACK_URL + "=" + encodedURL);
				} else {
					response.sendRedirect("http://" + ssoURL + "?" + SSOKey.KEY.CALLBACK_URL + "=" + encodedURL);
				}
			}
			// if (authId != null) {
			// String url =
			// request.getParameter(SSOKey.KEY.CALLBACK_URL.getKey());
			//// Map<String, String> dataMap = Maps.newHashMap();
			//// dataMap.put(SSOKey.KEY.AUTH_ID.getKey(), authId);
			//// Verifier.post(url, dataMap);
			// response.sendRedirect(url + "?" + SSOKey.KEY.AUTH_ID.getKey() +
			// "=" + authId);
			// } else if (request.getMethod().equals("POST") &&
			// verifyURI.equals(request.getRequestURI())) {
			// // request from SSO service
			// if (request.getParameter(SSOKey.KEY.AUTH_ID.getKey()) != null
			// &&
			// "true".equals(request.getParameter(SSOKey.KEY.CLIENT_VERIFY.getKey())))
			// {
			// tokenService.createToken(authId, request, response);
			// chain.doFilter(request, response);
			// }
			// } else {
			// if (tokenService.isValid(authId, request)) {
			// chain.doFilter(request, response);
			// } else {
			// if (ssoURL == null || "".equals(ssoURL.trim())) {
			// throw new RuntimeException("SSO URL cannot be null or empty.");
			// }
			// if (ssoURL.startsWith("http://") ||
			// ssoURL.startsWith("https://")) {
			// response.sendRedirect(ssoURL + "?" + SSOKey.KEY.CALLBACK_URL +
			// "=" + encodedURL);
			// } else {
			// response.sendRedirect("http://" + ssoURL + "?" +
			// SSOKey.KEY.CALLBACK_URL + "=" + encodedURL);
			// }
			// }
			// }
		}
	}

}
