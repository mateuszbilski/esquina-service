package com.squeezedlemon.esquina.service.service.response;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.squeezedlemon.esquina.service.entity.Checkin;
import com.squeezedlemon.esquina.service.entity.Follower;
import com.squeezedlemon.esquina.service.entity.User;
import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.service.entity.CheckinService;
import com.squeezedlemon.esquina.service.service.entity.UserService;

@Service
public class CheckinResponseBuilderImpl implements CheckinResponseBuilder {

	@Autowired
	private CheckinService checkinService;
	
	@Autowired
	private LocationResponseBuilder locationResponseBuilder;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserResponseBuilder userResponseBuilder;
	
	@Override
	public Map<String, Object> checkinCreated(Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		
		return map;
	}
	
	@Override
	@Transactional
	public Map<String, Object> getActivityStream(String username) throws ApplicationException {
		
		User user = userService.findById(username);
		List<User> userList = new LinkedList<>();
		for (Follower item : user.getFollowing()) {
			userList.add(item.getFollowing());
		}
		
		List<Checkin> usersActivity = checkinService.findNewestCheckinsFromUsers(userList);
		
		List<Map<String, Object>> activityMapList = new LinkedList<>();
		for (Checkin item : usersActivity) {
			activityMapList.add(checkinDetails(item.getId()));
		}
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("activity", activityMapList);
		return responseMap;
	}
	
	@Override
	@Transactional
	public Map<String, Object> checkinDetails(Long checkinId) {
		Checkin checkin = checkinService.findById(checkinId);
		Map<String, Object> map = new HashMap<>();
		map.put("id", checkin.getId());
		map.put("score", checkin.getScore());
		map.put("comment", checkin.getComment());
		map.put("date", checkin.getDate());
		map.put("user", userResponseBuilder.userEntityDetails(checkin.getUser().getAccountName()));
		map.put("location", locationResponseBuilder.locationEntityDetails(checkin.getLocation().getId()));
		
		return map;
	}

}
