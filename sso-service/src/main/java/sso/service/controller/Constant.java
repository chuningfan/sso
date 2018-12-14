package sso.service.controller;

public interface Constant {
	
	enum SSO {
		
		SSO_LOGIN("/sso/login"), SSO_LOGOUT("/sso/logout");
		
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
		
		CALLBACK_URL("callback"), AUTH_ID("authId");
		
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
	
}
