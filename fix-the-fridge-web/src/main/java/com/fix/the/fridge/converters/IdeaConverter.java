package com.fix.the.fridge.converters;

import com.fix.the.fridge.dao.AttachmentDao;
import com.fix.the.fridge.dao.IdeaDao;
import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.domain.Attachment;
import com.fix.the.fridge.domain.Idea;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.dto.IdeaDto;
import com.fix.the.fridge.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Artem Petrov
 */
@Component
public class IdeaConverter {
	@Autowired
	private UserConverter userConverter;
	@Autowired
	private StatusConverter statusConverter;
	@Autowired
	private IdeaDao ideaDao;
	@Autowired
	private AttachmentDao attachmentDao;
	@Autowired
	private UserDao userDao;

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
		if (sourceIdea.getId() != null) {
			Idea originalIdea = ideaDao.findById(sourceIdea.getId());
			targetIdea.setAttachment(originalIdea.getAttachment());
			targetIdea.setVoters(originalIdea.getVoters());
			targetIdea.setComments(originalIdea.getComments());
		} else {
			if (sourceIdea.getAttachmentName() != null) {
				Attachment attachment = attachmentDao.get(sourceIdea.getAttachmentName());
				targetIdea.setAttachment(attachment);
			}
			targetIdea.setCreatingData(new Date());
		}

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
		targetIdea.setCreationDate(sourceIdea.getCreatingData());
		targetIdea.setComments(sourceIdea.getComments().size());
		targetIdea.setAttachmentName(sourceIdea.getAttachment() == null ? null : sourceIdea.getAttachment().getId());

		User currentUser = userDao.find(Security.getCurrentUserNickName());
		if (sourceIdea.getVoters().contains(currentUser)) {
			targetIdea.setVotedByCurrentUser(true);
		}

		return targetIdea;

	}
}
