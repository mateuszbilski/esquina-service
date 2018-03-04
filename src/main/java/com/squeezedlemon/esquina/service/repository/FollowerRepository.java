package com.squeezedlemon.esquina.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squeezedlemon.esquina.service.entity.Follower;
import com.squeezedlemon.esquina.service.entity.User;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

	public Follower findByUserAndFollowing(User user, User following);
	
}
