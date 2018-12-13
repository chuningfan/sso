package sso.service.web;

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

public class TokenFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(TokenFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.info(filterConfig.getFilterName() + " is initialized!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
	}

	@Override
	public void destroy() {
		LOG.info("SSOFilter is destroyed!");
	}

}
