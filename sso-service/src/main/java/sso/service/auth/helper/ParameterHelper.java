package sso.service.auth.helper;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterHelper {
	
	private static final Logger LOG = LoggerFactory.getLogger(ParameterHelper.class);
	
	public static void valiateForm(String loginName, String password) throws Exception {
		if (Objects.nonNull(loginName) && loginName.trim().length() > 0 && 
				Objects.nonNull(password) && password.trim().length() > 0) {
			LOG.info("Invalid parameters received. loginName = " + loginName);
		} else {
			throw new Exception("Invalid parameters!");
		}
	}
	
	public static void valiateForm(String loginName, String password, String oldPassword) throws Exception {
		if (Objects.nonNull(loginName) && loginName.trim().length() > 0 && 
				Objects.nonNull(password) && password.trim().length() > 0 &&
				Objects.nonNull(oldPassword) && oldPassword.trim().length() > 0) {
			LOG.info("Invalid parameters received. loginName = " + loginName);
		} else {
			throw new Exception("Invalid parameters!");
		}
	}
	
}
