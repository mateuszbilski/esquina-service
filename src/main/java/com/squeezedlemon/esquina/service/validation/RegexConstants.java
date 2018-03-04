package com.squeezedlemon.esquina.service.validation;

public class RegexConstants {
	public static final String ACCOUNT_PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct})[a-zA-Z\\d\\p{Punct}]{8,32}$";
	public static final String ACCOUNT_NAME_PATTERN = "^[A-Za-z0-9]{5,32}$";
	public static final String LANG_CODE_PATTERN = "^[a-z]{2}$";
}
