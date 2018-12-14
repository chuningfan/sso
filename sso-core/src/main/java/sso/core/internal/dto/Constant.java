package sso.core.internal.dto;

import java.util.concurrent.TimeUnit;

public interface Constant {
	
	public static final String URL_LOGIN = "/sso/login";
	
	public static final String URL_LOGOUT = "/sso/logout";
	
	enum SSO {
		
		SSO_LOGIN(URL_LOGIN), SSO_LOGOUT(URL_LOGOUT);
		
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
		
		PAGE_LOGIN("/sso/page/login"), PAGE_ERROR("/sso/page/error");
		
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
		
		STAY_IN_ACCESSED(7, TimeUnit.DAYS), LOGIN_ACCESSED(30, TimeUnit.MINUTES);
		
		private long time;
		
		private TimeUnit timeUnit;

		private TIME(long time, TimeUnit timeUnit) {
			this.time = time;
			this.timeUnit = timeUnit;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public TimeUnit getTimeUnit() {
			return timeUnit;
		}

		public void setTimeUnit(TimeUnit timeUnit) {
			this.timeUnit = timeUnit;
		}
		
	}
	
}
