package sso.client.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenService {
	
	void createToken(String authId, HttpServletRequest request, HttpServletResponse response, boolean rememberMe);
	
	boolean isValid(HttpServletRequest request);
	
}
