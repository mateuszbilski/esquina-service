package com.squeezedlemon.esquina.service.service.entity;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.Follower;
import com.squeezedlemon.esquina.service.entity.User;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.exception.application.InvalidEntityException;
import com.squeezedlemon.esquina.service.repository.FollowerRepository;

@Service
public class FollowerServiceImpl implements FollowerService {

	private static final String FOLLOWER_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.follower";
	
	private static final String FOLLOWER_DUPLICATED_MESSAGE_CODE = "exception.duplicateEntityException.follower";
	
	private static final String FOLLOWER_DUPLICATED_DETAILED_MESSAGE_CODE = "exception.duplicateEntityException.follower.detailed";
	
	private static final String USER_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.user";
	
	private static final String INVALID_ENTITY_USER_SAME_AS_FOLLOWING_MESSAGE_CODE = "error.valid.following.userSameAsFollowingUser";
	
	
	
	@Autowired
	private FollowerRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Follower followUser(String userName, String followingName) throws DataRepositoryException {
		
		User following = userService.findById(followingName);
		User user = userService.findById(userName);
		
		if (following == null || user == null) {
			throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE_CODE, new Object[] {
					(following == null ? followingName : userName)});
					
		}
		
		if (userName.equals(followingName)) {
			throw new InvalidEntityException(INVALID_ENTITY_USER_SAME_AS_FOLLOWING_MESSAGE_CODE);
		}
		
		if (repository.findByUserAndFollowing(user, following) != null) {
			throw new DuplicateEntityException(FOLLOWER_DUPLICATED_DETAILED_MESSAGE_CODE, new Object[] {userName, followingName});
		}
		
		Follower t = new Follower();
		t.setDate(Calendar.getInstance().getTime());
		t.setUser(user);
		t.setFollowing(following);
		return create(t);
	}
	
	@Override
	public void unfollowUser(String userName, String followingName) throws DataRepositoryException {
		
		User user = userService.findById(userName);
		User following = userService.findById(followingName);
		
		Follower t = repository.findByUserAndFollowing(user, following);
		if (t != null) {
			delete(t.getId());
		} else {
			throw new EntityNotFoundException(FOLLOWER_NOT_FOUND_MESSAGE_CODE);
		}
		
	}
	
	//Methods from IService
	
	@Override
	public Follower create(Follower t) throws DataRepositoryException {
		if(t.getId() == null || !repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(FOLLOWER_DUPLICATED_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(Long id) throws DataRepositoryException {
		if (repository.exists(id)) {
			repository.delete(id);
		} else {
			throw new EntityNotFoundException(FOLLOWER_NOT_FOUND_MESSAGE_CODE);
		}
	}

	@Override
	public List<Follower> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Follower> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Follower findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Follower update(Follower t) throws DataRepositoryException {
		if (repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new EntityNotFoundException(FOLLOWER_NOT_FOUND_MESSAGE_CODE);
		}
	}

}
