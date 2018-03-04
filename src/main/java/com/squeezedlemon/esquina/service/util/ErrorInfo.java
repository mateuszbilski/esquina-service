package com.squeezedlemon.esquina.service.util;


public class ErrorInfo {

	private ErrorType type;
	
	private Integer statusCode;
	
	private String requestUri;
	
	private String extraInfo;

	
	public static enum ErrorType {
		APPLICATION, RUNTIME, MVC
	}
	
	public ErrorInfo() {
		
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public ErrorType getType() {
		return type;
	}

	public void setType(ErrorType type) {
		this.type = type;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	
}
