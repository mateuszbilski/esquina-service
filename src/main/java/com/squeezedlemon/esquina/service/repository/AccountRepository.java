package com.squeezedlemon.esquina.service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.squeezedlemon.esquina.service.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
	
	public Account findByAccountNameAndEnabledTrueAndDeletedNull(String accountName);
	
	public List<Account> findByEnabledTrueAndDeletedNull();
	
	public Page<Account> findByEnabledTrueAndDeletedNull(Pageable pageable);

}
