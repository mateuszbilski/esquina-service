package com.squeezedlemon.esquina.service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.squeezedlemon.esquina.service.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	public User findByAccountNameAndAccountEnabledTrueAndAccountDeletedNull(String accountName);
	
	public List<User> findByAccountEnabledTrueAndAccountDeletedNull();
	
	public Page<User> findByAccountEnabledTrueAndAccountDeletedNull(Pageable pageable);
	
	public List<User> findByAccountNameContainingOrLastNameIgnoreCaseContaining(String accountName, String lastName);
}
