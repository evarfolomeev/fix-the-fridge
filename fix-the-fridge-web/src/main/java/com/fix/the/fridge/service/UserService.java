package com.fix.the.fridge.service;

import com.fix.the.fridge.converters.UserConverter;
import com.fix.the.fridge.dao.AttachmentDao;
import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.domain.Attachment;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.domain.UserRole;
import com.fix.the.fridge.dto.UserDto;
import com.fix.the.fridge.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * (c) Swissquote 7/3/14
 *
 * @author dpitiriakov
 */
@Component
@Transactional
public class UserService {
	@Autowired
	private AttachmentDao attachmentDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private BCryptPasswordEncoder encoder;


	public void registerUser(UserDto userDto, String unencodedPassword) {
		User user = userConverter.convert(userDto);
		user.setPassword(encoder.encode(unencodedPassword));
		user.setEnabled(true);
		// Admin accounts created by script
		user.addRole(UserRole.USER);

		userDao.save(user);
	}

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

	public UserDto updateAvatar(String avatarId) {
		String currentUserNickName = Security.getCurrentUserNickName();
		User user = userDao.find(currentUserNickName);
		Attachment attachment = attachmentDao.get(avatarId);
		if (attachment == null) {
			return userConverter.convert(user);
		}
		user.setAvatar(attachment);
		userDao.update(user);
		return userConverter.convert(user);
	}
}
