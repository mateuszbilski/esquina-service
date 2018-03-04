package com.squeezedlemon.esquina.service.service.response;

import java.util.Map;

import com.squeezedlemon.esquina.service.entity.Account;

public interface AccountResponseBuilder {
	
	public Map<String, Object> userAccountCreated(String accountName);
}
