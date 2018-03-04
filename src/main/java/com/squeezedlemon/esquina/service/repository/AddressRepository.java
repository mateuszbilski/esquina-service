package com.squeezedlemon.esquina.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squeezedlemon.esquina.service.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
