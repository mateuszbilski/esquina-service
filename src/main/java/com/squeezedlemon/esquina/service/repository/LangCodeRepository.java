package com.squeezedlemon.esquina.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squeezedlemon.esquina.service.entity.LangCode;

public interface LangCodeRepository extends JpaRepository<LangCode, String> {
	
}
