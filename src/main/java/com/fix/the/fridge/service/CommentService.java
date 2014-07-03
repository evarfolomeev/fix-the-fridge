package com.fix.the.fridge.service;

import com.fix.the.fridge.converters.CommentConverter;
import com.fix.the.fridge.dao.CommentDao;
import com.fix.the.fridge.domain.Comment;
import com.fix.the.fridge.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) Swissquote 7/3/14
 *
 * @author dpitiriakov
 */
@Component
public class CommentService {

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private CommentConverter commentConverter;


	public void save(CommentDto commentDto) {
		commentDao.save(commentConverter.convert(commentDto));
	}

	public void update(CommentDto commentDto) {
		commentDao.update(commentConverter.convert(commentDto));
	}

	public void remove(CommentDto commentDto) {
		commentDao.remove(commentConverter.convert(commentDto));
	}

	public List<CommentDto> findAll() {
		List<CommentDto> commentDtos = new ArrayList<>();
		for (Comment comment : commentDao.findAll()) {
			commentDtos.add(commentConverter.convert(comment));
		}
		return commentDtos;
	}
}
