package sso.core.internal.dto;

public enum StateCode {
		
		SUCCESS(1), FAILURE(-1);
		
		private int code;

		private StateCode(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}
		
	}