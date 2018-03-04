package com.squeezedlemon.esquina.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squeezedlemon.esquina.service.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
