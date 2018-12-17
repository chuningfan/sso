package sso.common.dto;

public interface SSOKey {

	enum KEY {

		CALLBACK_URL("callback"), AUTH_ID("authId"), CLIENT_PASSPORT("sso_passport"), RBM("rbm"), VERIFIER_CALLBACK("verifier_callback"), CLIENT_VERIFY("verified");;

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

	enum SSO_TYPE {
		
		SSO_TYPE_SESSION("sessionType"), SSO_TYPE_TOKEN("tokenType");
		
		private String type;

		private SSO_TYPE(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
	
	enum SSO_PATH {
		
		CLIENT_VERIFY("/verify");
		
		private String path;

		private SSO_PATH(String path) {
			this.path = path;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
		
	}
	
}
