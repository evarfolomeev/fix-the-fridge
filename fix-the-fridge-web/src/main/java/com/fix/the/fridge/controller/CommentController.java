package com.fix.the.fridge.controller;

import com.fix.the.fridge.converters.CommentConverter;
import com.fix.the.fridge.dao.CommentDao;
import com.fix.the.fridge.dao.IdeaDao;
import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.domain.Comment;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.dto.CommentDto;
import com.fix.the.fridge.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) Swissquote 7/3/14
 *
 * @author dpitiriakov
 */

@Controller
@RequestMapping("/comment")
@Transactional
public class CommentController {
	@Autowired
	private CommentConverter commentConverter;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private Security security;
	@Autowired
	private IdeaDao ideaDao;

	@RequestMapping("get-by-idea")
	@ResponseBody
	public List<CommentDto> getByIdea(@RequestParam Long ideaId) {
		List<CommentDto> commentDtos = new ArrayList<>();
		for (Comment comment : ideaDao.findById(ideaId).getComments()) {
			commentDtos.add(commentConverter.convert(comment));
		}
		return commentDtos;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public CommentDto save(@RequestBody CommentDto commentDto) {
		commentDto.setAuthor(security.getCurrentUser());

		Comment entity = commentConverter.convert(commentDto);
		commentDao.save(entity);
		return commentConverter.convert(entity);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public CommentDto update(@RequestBody CommentDto commentDto) {
		String authorNickName = commentDao.findById(commentDto.getId()).getAuthor().getNickName();
		if (!authorNickName.equals(Security.getCurrentUserNickName())) {
			throw new RuntimeException("user not allowed to comment edit not their comments");
		}

		Comment entity = commentConverter.convert(commentDto);
		commentDao.update(entity);
		return commentConverter.convert(entity);
	}

	@RequestMapping(value = "vote", method = RequestMethod.POST)
	@ResponseBody
	public CommentDto vote(@RequestParam Long commentId) {
		String userNickName = Security.getCurrentUserNickName();

		User user = userDao.find(userNickName);
		if (user == null) {
			throw new RuntimeException("Fail to get comment by id: " + commentId);
		}

		Comment comment = commentDao.findById(commentId);
		if (comment == null) {
			throw new RuntimeException("Fail to get user by nick name: " + userNickName);
		}

		if (comment.getVoters().contains(user)) {
			//try to unvote
			comment.getVoters().remove(user);
		} else {
			//try to vote
			comment.getVoters().add(user);
		}
		commentDao.update(comment);

		return commentConverter.convert(comment);
	}
}
