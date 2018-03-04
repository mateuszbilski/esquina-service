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
import com.squeezedlemon.esquina.service.service.entity.UserService;

@Service
public class UserResponseBuilderImpl implements UserResponseBuilder {

	private static final String SEARCH_RESULT_PROPERTY = "result";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CheckinResponseBuilder checkinResponseBuilder;
	
	@Transactional
	@Override
	public Map<String, Object> searchDetails(String query) throws ApplicationException {
		List<User> users = userService.search(query);
		List<Map<String, Object>> userListJson = new LinkedList<>();
		for (User item : users) {
			userListJson.add(userEntityJson(item));
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put(SEARCH_RESULT_PROPERTY, userListJson);
		return response;
	}
	
	@Transactional
	@Override
	public Map<String, Object> userDetails(String accountName) {
		User user = userService.findById(accountName);
		Map<String, Object> map = userEntityJson(user);
		
		List<Map<String, Object>> followings = new LinkedList<>();
		for (Follower item : user.getFollowing()) {
			followings.add(userEntityJson(item.getFollowing()));
		}
		map.put("following", followings);
		
		List<Map<String, Object>> followers = new LinkedList<>();
		for (Follower item : user.getFollowers()) {
			followers.add(userEntityJson(item.getUser()));
		}
		map.put("followers", followers);
		
		List<Map<String, Object>> checkins = new LinkedList<>();
		for (Checkin item : user.getCheckins()) {
			Map<String, Object> checkinDetailsMap = checkinResponseBuilder.checkinDetails(item.getId());
			checkinDetailsMap.remove("user");
			checkins.add(checkinDetailsMap);
		}
		map.put("checkins", checkins);
		
		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> followingCreated(Long followingId) {
		Map<String, Object> map = new HashMap<>();
		map.put("followingId", followingId);
		return map;
	}

	@Transactional
	@Override
	public Map<String,Object> userEntityDetails(String accountName) {
		User user = userService.findById(accountName);
		return userEntityJson(user);
	};
	
	public Map<String, Object> userEntityJson(User user) {
		Map<String, Object> map = new HashMap<>();
		map.put("accountName", user.getAccountName());
		map.put("firstName", user.getFirstName());
		map.put("middleName", user.getMiddleName());
		map.put("lastName", user.getLastName());
		map.put("birthDate", user.getBirthDate());
		map.put("gender", user.getGender());
		map.put("aboutMe", user.getAboutMe());
		map.put("icon", (user.getIcon() != null ? user.getIcon().getId() : null) );
		map.put("backgroundImage",(user.getBackgroundImage() != null ? user.getBackgroundImage().getId() : null) );
		map.put("defaultLanguage", user.getLangCode().getLangCode());
		return map;
	}
}
