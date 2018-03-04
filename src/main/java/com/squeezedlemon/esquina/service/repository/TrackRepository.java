package com.squeezedlemon.esquina.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squeezedlemon.esquina.service.entity.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {

}
