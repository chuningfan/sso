package sso.core.component.web.filter;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

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

import sso.core.internal.dto.Constant;

public class RequestHandler implements Filter {

	private static final ThreadLocal<HttpServletRequest> REQUESTS = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> RESPONSES = new ThreadLocal<HttpServletResponse>();
	
	private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);
	
	@Override
	public void destroy() {
		LOG.info("RequestHandleFilter is destoryed!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				try {
					req.getRequestDispatcher(Constant.PAGE.PAGE_ERROR.getPath()).forward(req, resp);
				} catch (ServletException | IOException e1) {
					LOG.error("When catching error, occurred another exception!");
					e1.printStackTrace();
				}
			}
		});
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
