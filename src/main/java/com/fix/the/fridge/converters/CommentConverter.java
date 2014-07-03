package com.fix.the.fridge.converters;

import com.fix.the.fridge.domain.Comment;
import com.fix.the.fridge.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Artem Petrov
 */
@Component
public class CommentConverter {
	@Autowired
	private UserConverter userConverter;

	public Comment convert(CommentDto sourceComment) {
		if (sourceComment == null) {
			return null;
		}

		Comment targetComment = new Comment();

		targetComment.setId(sourceComment.getId());
		targetComment.setText(sourceComment.getText());
		targetComment.setAuthor(userConverter.convert(sourceComment.getAuthor()));

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

		return targetComment;
	}
}
