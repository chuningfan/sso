package sso.core.internal.dto;

import java.util.concurrent.TimeUnit;

public interface Constant {
	
	public static final String URL_LOGIN = "/sso/login";
	
	public static final String URL_LOGOUT = "/sso/logout";
	
	public static final String URL_VERIFY = "/sso/verify";
	
	public static final String LOGIN_PAGE = "/sso/page/login";
	
	public static final String VERIFY_PAGE = "/sso/page/verify";
	
	public static final String ERROR_PAGE = "/sso/page/error";
	
	enum SSO {
		
		SSO_LOGIN(URL_LOGIN), SSO_LOGOUT(URL_LOGOUT), SSO_VERIFY(URL_VERIFY);
		
		private String path;

		private SSO(String path) {
			this.path = path;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
		
	}
	
	enum PAGE {
		
		PAGE_LOGIN(LOGIN_PAGE), PAGE_ERROR(ERROR_PAGE), PAGE_VERIFY(VERIFY_PAGE);
		
		private String path;

		private PAGE(String path) {
			this.path = path;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
		
	}
	
	enum KEY {
		
		DIRECT_HEADER("direct:");
		
		private String key;

		private KEY(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
		
	}
	
	enum TIME {
		
		STAY_IN_ACCESSED(3600 * 24 * 7, TimeUnit.SECONDS), LOGIN_ACCESSED(30, TimeUnit.MINUTES);
		
		private int time;
		
		private TimeUnit timeUnit;

		private TIME(int time, TimeUnit timeUnit) {
			this.time = time;
			this.timeUnit = timeUnit;
		}

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
			this.time = time;
		}

		public TimeUnit getTimeUnit() {
			return timeUnit;
		}

		public void setTimeUnit(TimeUnit timeUnit) {
			this.timeUnit = timeUnit;
		}
		
	}
	
	enum COOKIE_KEY {
		
		REMEMBER_ME("rm"), TOKEN("wand");
		
		private String key;

		private COOKIE_KEY(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
		
	}
	
}
