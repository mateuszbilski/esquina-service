package com.squeezedlemon.esquina.service.service.entity;

import com.squeezedlemon.esquina.service.entity.Account;
import com.squeezedlemon.esquina.service.entity.User;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;

public interface AccountService extends IService<Account, String> {
	
	public Account createAccount(User user, String password) throws DataRepositoryException;
	
	public void changePassword(String username, String newPassword) throws DataRepositoryException;
	
	public void activateAccount(String username) throws DataRepositoryException;
	
	public void deactivateAccount(String username) throws DataRepositoryException;
}
