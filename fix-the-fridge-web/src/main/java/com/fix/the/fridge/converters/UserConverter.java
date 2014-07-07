package com.fix.the.fridge.converters;

import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.domain.Attachment;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Artem Petrov
 */
@Component
public class UserConverter {
	@Autowired
	private UserDao userDao;

	public User convert(UserDto sourceUser) {
		if (sourceUser == null) {
			return null;
		}

		User targetUser = new User();

		targetUser.setNickName(sourceUser.getNickName());
		targetUser.setName(sourceUser.getName());
		targetUser.setEmail(sourceUser.getEmail());

		/*if (sourceUser.getNickName() != null) {
			User originalUser = userDao.find(sourceUser.getNickName());
			targetUser.setPassword(originalUser.getPassword());
			targetUser.setEnabled(originalUser.isEnabled());
		}*/

		if (sourceUser.getAvatarName() != null) {
			Attachment targetAttachment = new Attachment();
			targetAttachment.setId(sourceUser.getAvatarName());
			targetUser.setAvatar(targetAttachment);
		}

		return targetUser;
	}

	public UserDto convert(User sourceUser) {
		if (sourceUser == null) {
			return null;
		}

		UserDto targetUser = new UserDto();

		targetUser.setNickName(sourceUser.getNickName());
		targetUser.setName(sourceUser.getName());
		targetUser.setEmail(sourceUser.getEmail());
		targetUser.setAvatarName(sourceUser.getAvatar() == null ? null : sourceUser.getAvatar().getId());

		return targetUser;
	}
}
