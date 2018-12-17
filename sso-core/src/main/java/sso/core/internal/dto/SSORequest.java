package sso.core.internal.dto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SSORequest {
	
	public static final String CALLBACK = "callback";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String sessionId;
	
	private String callback;
	
	private Cookie[] cookies;

	public SSORequest(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.sessionId = request.getSession(false) != null ? request.getSession().getId() : null;
		this.callback = request.getParameter(CALLBACK);
		this.cookies = request.getCookies();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public Cookie[] getCookies() {
		return cookies;
	}

	public void setCookies(Cookie[] cookies) {
		this.cookies = cookies;
	}
	
	public void rebuildSessionId() {
		this.sessionId = request.getSession(false) != null ? request.getSession().getId() : null;
	}
	
}
