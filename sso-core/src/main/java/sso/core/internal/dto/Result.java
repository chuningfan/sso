package sso.core.internal.dto;

public class Result<T> {
	
	private StateCode code = StateCode.SUCCESS;
	
	private Exception exc;
	
	private String message;
	
	private T data;

	public StateCode getCode() {
		return code;
	}

	public Result<T> setCode(StateCode code) {
		this.code = code;
		return this;
	}

	public Exception getExc() {
		return exc;
	}

	public Result<T> setExc(Exception exc) {
		this.exc = exc;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Result<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}

	public Result<T> turnToFailure() {
		this.code = StateCode.FAILURE;
		return this;
	}
	
}
