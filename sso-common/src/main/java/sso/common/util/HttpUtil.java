package sso.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpUtil {

    public static final String IP_ADDRESS = "ip_address";
    private static final String UNKNOWN_IP = "unknown";
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String getIp(HttpServletRequest request) {
        logger.debug("*** HttpUtil.class Starting getIp() ***");
        
        String ip = request.getHeader("X-Cluster-Client-Ip");
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            logger.debug("header is X-Forwarded-For");
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            logger.debug("header is Proxy-Client-IP");
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            logger.debug("header is WL-Proxy-Client-IP");
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            logger.debug("header is HTTP_CLIENT_IP");
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            logger.debug("header is HTTP_X_FORWARDED_FOR");
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isBlank(ip)) {
            return UNKNOWN_IP;
        }
        return ip;
    }

    public static final String getFromCookie(String key, HttpServletRequest request) throws Exception {
    	return getCookie(request, key) == null ? null : getCookie(request, key).getValue();
    }
    
    public static final void setCookie(HttpServletRequest request, HttpServletResponse response, String key, String value, int expireSeconds, String domain) {
    	Cookie cookie = new Cookie(key, value);
    	cookie.setDomain(domain);
    	cookie.setMaxAge(expireSeconds);
    	if (request.isSecure()) {
    		cookie.setSecure(true);
    		cookie.setHttpOnly(false);
    	}
    	response.addCookie(cookie);
    }
    
    public static final Cookie getCookie(HttpServletRequest request, String key) throws Exception {
    	Cookie[] cookies = request.getCookies();
    	if (cookies == null || cookies.length == 0) {
    		throw new Exception("No cookie was found!");
    	}
    	for (Cookie c: cookies) {
    		if(key.equals(c.getName())) {
    			return c;
    		}
    	}
    	return null;
    }
    
}
