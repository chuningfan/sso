package sso.client.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import okhttp3.Response;
import sso.client.sso.TokenService;
import sso.client.sso.Verifier;
import sso.common.dto.SSOKey;

public class SSOClientFilter implements Filter {

	private TokenService tokenService;

	private String SSOAddress;

	private String ssoURL;

	private String tokenServiceClass;

	private String exclusions;

	private String verifyURL;

	private List<String> exclusionList;

	private String serviceId;

	private static List<String> rewriteFilter = Lists.newArrayList();

	@Override
	public void init(FilterConfig config) throws ServletException {
		serviceId = config.getInitParameter("serviceId");
		SSOAddress = config.getInitParameter("SSOAddress");
		tokenServiceClass = config.getInitParameter("tokenServiceClass");
		check();
		ssoURL = SSOAddress + SSOKey.SSO_PATH.VERIFY_PAGE.getPath();
		verifyURL = SSOAddress + SSOKey.SSO_PATH.CLIENT_VERIFY.getPath();
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
		rewriteFilter.add(SSOKey.KEY.AUTH_ID.getKey() + "=");
		rewriteFilter.add(SSOKey.KEY.CALLBACK_URL.getKey() + "=");
		rewriteFilter.add(SSOKey.KEY.RBM.getKey() + "=");
	}

	private void check() {
		if (serviceId == null || "".equals(serviceId.trim())) {
			throwNullException("serviceId");
		}
		if (SSOAddress == null || "".equals(SSOAddress.trim())) {
			throwNullException("SSOAddress");
		}
		if (tokenServiceClass == null || "".equals(tokenServiceClass.trim())) {
			ServiceLoader<TokenService> service = ServiceLoader.load(TokenService.class);
			Iterator<TokenService> itr = service.iterator();
			while (itr.hasNext()) {
				TokenService tempService = itr.next();
				if (tempService != null) {
					tokenServiceClass = tempService.getClass().getName();
					break;
				}
			}
			if (tokenServiceClass == null || "".equals(tokenServiceClass.trim())) {
				throw new RuntimeException("tokenServiceClass cannot be null or empty! You can also use SPI to initialize it.");
			}
		}
	}

	private void throwNullException(String property) {
		throw new RuntimeException(property + " cannot be null or empty!");
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
					Map<String, String> dataMap = Maps.newHashMap();
					dataMap.put(SSOKey.KEY.AUTH_ID.getKey(), authId);
					Response res = Verifier.post(verifyURL, dataMap);
					if (Boolean.parseBoolean(res.body().string())) {
						tokenService.createToken(authId, request, response, Boolean.parseBoolean(rememberMe));
						chain.doFilter(request, response);
					}	
				}
			} else if (tokenService.isValid(request)) {
				chain.doFilter(request, response);
			} else {
				if (ssoURL == null || "".equals(ssoURL.trim())) {
					throw new RuntimeException("SSO URL cannot be null or empty.");
				}
				if (ssoURL.startsWith("http://") || ssoURL.startsWith("https://")) {
					response.sendRedirect(ssoURL + "?" + SSOKey.KEY.SERVICE_ID.getKey() + "=" + serviceId + "&" + SSOKey.KEY.CALLBACK_URL.getKey() + "=" + encodedURL);
				} else {
					response.sendRedirect("http://" + ssoURL + "?" + SSOKey.KEY.SERVICE_ID.getKey() + "=" + serviceId + "&" + SSOKey.KEY.CALLBACK_URL.getKey() + "=" + encodedURL);
				}
			}
		}
	}

//	private String rewriteURL(String queryString) {
//		if (queryString != null && queryString.trim().length() > 0) {
//			String[] parameters = queryString.split("&");
//			List<String> paramList = Lists.newArrayList(parameters);
//			Iterator<String> itr = paramList.iterator();
//			while (itr.hasNext()) {
//				String param = itr.next();
//				if (need2Rmv(param)) {
//					itr.remove();
//				}
//			}
//			if (paramList.size() > 0) {
//				StringBuilder builder = new StringBuilder();
//				paramList.stream().forEach(p -> {
//					if (builder.length() == 0) {
//						builder.append(p);
//					} else {
//						builder.append("&" + p);
//					}
//				});
//				return builder.toString();
//			}
//		}
//		return null;
//	}
//
//	private boolean need2Rmv(String str) {
//		return rewriteFilter.stream().anyMatch(s -> str.startsWith(s));
//	}

	@Override
	public void destroy() {

	}
}
