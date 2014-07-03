package com.fix.the.fridge.service;

import com.fix.the.fridge.converters.UserConverter;
import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

/**
 * (c) Swissquote 7/3/14
 *
 * @author dpitiriakov
 */
@Component
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserConverter userConverter;


	public void save(UserDto userDto) {
		userDao.save(userConverter.convert(userDto));
	}

	public void update(UserDto userDto) {
		userDao.update(userConverter.convert(userDto));
	}

	public void remove(UserDto userDto) {
		userDao.remove(userConverter.convert(userDto));
	}

	public List<UserDto> findAll() {
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : userDao.findAll()) {
			userDtos.add(userConverter.convert(user));
		}
		return userDtos;
	}

}
