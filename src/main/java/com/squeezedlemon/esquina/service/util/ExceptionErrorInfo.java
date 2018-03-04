package com.squeezedlemon.esquina.service.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionErrorInfo extends ErrorInfo {

	@JsonProperty("exception")
	private ExceptionInfo exceptionInfo;

	public ExceptionErrorInfo() {
		super();
	}
	
	public static class ExceptionInfo {
		
		private Class<?> exceptionClass;
		
		private String exceptionMessage;
		
		private String localizedMessage;
		
		public ExceptionInfo() {
			
		}
		
		public Class<?> getExceptionClass() {
			return exceptionClass;
		}
		
		public void setExceptionClass(Class<?> exceptionClass) {
			this.exceptionClass = exceptionClass;
		}
		
		public String getExceptionMessage() {
			return exceptionMessage;
		}
		
		public void setExceptionMessage(String exceptionMessage) {
			this.exceptionMessage = exceptionMessage;
		}
		
		public String getLocalizedMessage() {
			return localizedMessage;
		}
		
		public void setLocalizedMessage(String localizedMessage) {
			this.localizedMessage = localizedMessage;
		}
		
	}
	
	public ExceptionInfo getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(ExceptionInfo exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	
}
