package com.fix.the.fridge.controller;

import com.fix.the.fridge.dto.CommentDto;
import com.fix.the.fridge.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * (c) Swissquote 7/3/14
 *
 * @author dpitiriakov
 */

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;



	@RequestMapping("get-all")
	@ResponseBody
	public List<CommentDto> getAll() {
		return commentService.findAll();
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(@RequestBody CommentDto commentDto) {
		commentService.save(commentDto);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(@RequestBody CommentDto commentDto) {
		commentService.update(commentDto);
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public void remove(@RequestBody CommentDto commentDto) {
		commentService.remove(commentDto);
	}
}
