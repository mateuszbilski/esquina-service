package com.squeezedlemon.esquina.service.service.entity;

import com.squeezedlemon.esquina.service.entity.Follower;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;

public interface FollowerService extends IService<Follower, Long> {

	public Follower followUser(String userName, String followingName) throws DataRepositoryException;
	
	public void unfollowUser(String userName, String followingName) throws DataRepositoryException;
}
