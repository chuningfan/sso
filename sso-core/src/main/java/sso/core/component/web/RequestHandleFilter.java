package sso.core.component.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandleFilter implements Filter {

	private static final ThreadLocal<HttpServletRequest> REQUESTS = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> RESPONSES = new ThreadLocal<HttpServletResponse>();
	
	private static final Logger LOG = LoggerFactory.getLogger(RequestHandleFilter.class);
	
	@Override
	public void destroy() {
		LOG.info("RequestHandleFilter is destoryed!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		REQUESTS.set(req);
		RESPONSES.set(resp);
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		LOG.info("RequestHandleFilter is initialized!");
	}

	public static final HttpServletRequest getRequest() {
		return REQUESTS.get();
	}
	
	public static final HttpServletResponse getResponse() {
		return RESPONSES.get();
	}
	
}
