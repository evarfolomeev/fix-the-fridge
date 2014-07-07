package com.fix.the.fridge.utils;

import com.fix.the.fridge.converters.UserConverter;
import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Artem Petrov
 */
@Service
public class Security {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserConverter userConverter;

	public static String getCurrentUserNickName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	public UserDto getCurrentUser() {
		User user = userDao.find(getCurrentUserNickName());
		return userConverter.convert(user);
	}
}
