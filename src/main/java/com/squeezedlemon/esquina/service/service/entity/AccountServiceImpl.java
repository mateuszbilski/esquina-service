package com.squeezedlemon.esquina.service.service.entity;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.Account;
import com.squeezedlemon.esquina.service.entity.User;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.exception.application.InvalidEntityException;
import com.squeezedlemon.esquina.service.repository.AccountRepository;
import com.squeezedlemon.esquina.service.util.ApplicationRole;
import com.squeezedlemon.esquina.service.validation.RegexConstants;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	private static final String ACCOUNT_NAME_EXISTS_MESSAGE_CODE = "exception.duplicateEntityException.account.nameExists";

	private static final String ACCOUNT_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.account";

	private static final String WEAK_PASSWORD_MESSAGE_CODE = "error.valid.account.weakPassword";
	
	private static final String SAME_PASSWORD_MESSAGE_CODE = "error.valid.account.samePassword";

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Account createAccount(User user, String password) throws DataRepositoryException {

		//Chcek password strength
		if (!isPasswordStrong(password)) {
			throw new InvalidEntityException(WEAK_PASSWORD_MESSAGE_CODE);
		}
		
		Account acc = new Account();
		acc.setAccountName(user.getAccountName());
		acc.setEncryptedPassword(passwordEncoder.encode(password));
		acc.setRole(ApplicationRole.USER.getRoleName());
		acc.setEnabled(false);
		acc.setCreated(Calendar.getInstance().getTime());
		acc.setUser(user);
		
		user.setAccount(acc);
		
		return create(acc);
	}
	
	@Override
	public void changePassword(String username, String newPassword) throws DataRepositoryException {
		
		if (!isPasswordStrong(newPassword)) {
			throw new InvalidEntityException(WEAK_PASSWORD_MESSAGE_CODE);
		}
		
		Account acc = findById(username);
		if (acc != null) {
			if (!passwordEncoder.matches(newPassword, acc.getEncryptedPassword())) {
				acc.setEncryptedPassword(passwordEncoder.encode(newPassword));
				update(acc);
			} else {
				throw new InvalidEntityException(SAME_PASSWORD_MESSAGE_CODE);
			}
		} else {
			throw new EntityNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
	@Override
	public void activateAccount(String username) throws DataRepositoryException {
		
		//Methods findById filters those account, which are disabled. So that you should use repository
		//method to find account entity without filtering
		Account acc = repository.findOne(username);
		if (acc != null) {
			acc.setEnabled(true);
			update(acc);
		} else {
			throw new EntityNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
	@Override
	public void deactivateAccount(String username) throws DataRepositoryException {
		
		Account acc = findById(username);	
		if (acc != null) {
			acc.setDeleted(Calendar.getInstance().getTime());
			update(acc);
		} else {
			throw new EntityNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
	/* Methods from IService */
	
	@Override
	public Account create(Account t) throws DataRepositoryException {
		if (!repository.exists(t.getAccountName())) {
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(ACCOUNT_NAME_EXISTS_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(String id) throws DataRepositoryException {
		if (repository.exists(id)) {
			repository.delete(id);
		} else {
			throw new EntityNotFoundException();
		}
		
	}

	@Override
	public List<Account> findAll() {
		return repository.findByEnabledTrueAndDeletedNull();
	}
	
	
	@Override
	public Page<Account> findAll(Pageable pageable) {
		return repository.findByEnabledTrueAndDeletedNull(pageable);
	}

	@Override
	public Account findById(String id) {
		return repository.findByAccountNameAndEnabledTrueAndDeletedNull(id);
	}

	@Override
	public Account update(Account t) throws DataRepositoryException {
		if (repository.exists(t.getAccountName())) {
			return repository.save(t);
		} else {
			throw new EntityNotFoundException();
		}
	}
	
	/* Additional private methods */
	
	private Boolean isPasswordStrong(String password) {
		return password.matches(RegexConstants.ACCOUNT_PASSWORD_PATTERN);
	}
	
}
