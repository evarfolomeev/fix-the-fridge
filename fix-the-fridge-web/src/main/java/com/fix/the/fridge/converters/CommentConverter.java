package com.fix.the.fridge.converters;

import com.fix.the.fridge.dao.CommentDao;
import com.fix.the.fridge.dao.IdeaDao;
import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.domain.Comment;
import com.fix.the.fridge.domain.Idea;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.dto.CommentDto;
import com.fix.the.fridge.dto.IdeaDto;
import com.fix.the.fridge.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Artem Petrov
 */
@Component
public class CommentConverter {
	@Autowired
	private UserConverter userConverter;
	@Autowired
	private IdeaConverter ideaConverter;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private IdeaDao ideaDao;

	public Comment convert(CommentDto sourceComment) {
		if (sourceComment == null) {
			return null;
		}

		Comment targetComment = new Comment();

		targetComment.setId(sourceComment.getId());
		targetComment.setText(sourceComment.getText());
		targetComment.setAuthor(userConverter.convert(sourceComment.getAuthor()));

		if (sourceComment.getId() != null) {
			Comment originalComment = commentDao.findById(sourceComment.getId());
			targetComment.setVoters(originalComment.getVoters());
			targetComment.setIdea(originalComment.getIdea());
		}

		if (sourceComment.getIdeaDto() != null && sourceComment.getIdeaDto().getId() != null) {
			Idea idea = ideaDao.findById(sourceComment.getIdeaDto().getId());
			targetComment.setIdea(idea);
		}

		if (sourceComment.getId() == null) {
			targetComment.setCreatingData(new Date());
		}

		return targetComment;
	}

	public CommentDto convert(Comment sourceComment) {
		if (sourceComment == null) {
			return null;
		}

		CommentDto targetComment = new CommentDto();

		targetComment.setId(sourceComment.getId());
		targetComment.setText(sourceComment.getText());
		targetComment.setAuthor(userConverter.convert(sourceComment.getAuthor()));
		targetComment.setVoters(sourceComment.getVoters().size());
		targetComment.setCreationDate(sourceComment.getCreatingData());
		targetComment.setIdeaDto(ideaConverter.convert(sourceComment.getIdea()));

		User currentUser = userDao.find(Security.getCurrentUserNickName());
		if (sourceComment.getVoters().contains(currentUser)) {
			targetComment.setVotedByCurrentUser(true);
		}

		return targetComment;
	}
}
