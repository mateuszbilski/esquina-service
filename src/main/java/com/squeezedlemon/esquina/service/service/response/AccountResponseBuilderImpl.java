package com.squeezedlemon.esquina.service.service.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.Account;

@Service
public class AccountResponseBuilderImpl implements AccountResponseBuilder {

	private static final String ACCOUNT_NAME_PROPERTY = "accountName";

	@Override
	public Map<String, Object> userAccountCreated(String accountName) {
		Map<String, Object> map = new HashMap<>();
		map.put(ACCOUNT_NAME_PROPERTY, accountName);
		
		return map;
	}

}
