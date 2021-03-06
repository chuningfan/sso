package sso.common.dto;

import java.io.Serializable;

public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = -4024428067740208872L;

	private Long userId;
	
	private String givenName;
	
	private String familyName;
	
	private String role;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
