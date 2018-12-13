package sso.core.internal.dto;

public class Result<T> {
	
	private StateCode code = StateCode.SUCCESS;
	
	private Exception exc;
	
	private String message;
	
	private T data;

	public StateCode getCode() {
		return code;
	}

	public void setCode(StateCode code) {
		this.code = code;
	}

	public Exception getExc() {
		return exc;
	}

	public void setExc(Exception exc) {
		this.exc = exc;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void turnToFailure() {
		this.code = StateCode.FAILURE;
	}
	
}
