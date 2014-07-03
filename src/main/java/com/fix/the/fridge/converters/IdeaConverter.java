package com.fix.the.fridge.converters;

import com.fix.the.fridge.domain.Attachment;
import com.fix.the.fridge.domain.Idea;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.dto.IdeaDto;
import com.fix.the.fridge.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author Artem Petrov
 */
@Component
public class IdeaConverter {
	@Autowired
	private UserConverter userConverter;
	@Autowired
	private StatusConverter statusConverter;

	public Idea convert(IdeaDto sourceIdea) {
		if (sourceIdea == null) {
			return null;
		}

		Idea targetIdea = new Idea();

		targetIdea.setId(sourceIdea.getId());
		targetIdea.setUser(userConverter.convert(sourceIdea.getUser()));
		targetIdea.setTitle(sourceIdea.getTitle());
		targetIdea.setDescription(sourceIdea.getDescription());
		targetIdea.setStatus(statusConverter.convert(sourceIdea.getStatus()));

		return targetIdea;
	}

	public IdeaDto convert(Idea sourceIdea) {
		if (sourceIdea == null) {
			return null;
		}

		IdeaDto targetIdea = new IdeaDto();

		targetIdea.setId(sourceIdea.getId());
		targetIdea.setUser(userConverter.convert(sourceIdea.getUser()));
		targetIdea.setTitle(sourceIdea.getTitle());
		targetIdea.setDescription(sourceIdea.getDescription());
		targetIdea.setVoters(sourceIdea.getVoters().size());
		targetIdea.setStatus(statusConverter.convert(sourceIdea.getStatus()));

		ArrayList<String> attachmentsNames = new ArrayList<>();
		for (Attachment attachment : sourceIdea.getAttachments()) {
			attachmentsNames.add(attachment.getId());
		}
		targetIdea.setAttachmentNames(attachmentsNames);

		return targetIdea;

	}
}
