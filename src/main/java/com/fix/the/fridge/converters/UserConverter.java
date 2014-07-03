package com.fix.the.fridge.converters;

import com.fix.the.fridge.domain.Attachment;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.dto.UserDto;
import org.springframework.stereotype.Component;

/**
 * @author Artem Petrov
 */
@Component
public class UserConverter {

	public User convert(UserDto sourceUser) {
		if (sourceUser == null) {
			return null;
		}

		User targetUser = new User();

		targetUser.setNickName(sourceUser.getNickName());
		targetUser.setName(sourceUser.getName());
		targetUser.setEmail(sourceUser.getEmail());

		Attachment targetAttachment = new Attachment();
		targetAttachment.setId(sourceUser.getAvatarName());
		targetUser.setAvatar(targetAttachment);

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
		targetUser.setAvatarName(sourceUser.getAvatar().getId());

		return targetUser;
	}
}
