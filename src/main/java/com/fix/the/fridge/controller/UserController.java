package com.fix.the.fridge.controller;

import com.fix.the.fridge.dto.UserDto;
import com.fix.the.fridge.service.UserService;
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
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;



	@RequestMapping("get-all")
	@ResponseBody
	public List<UserDto> getAll() {
		return userService.findAll();
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(@RequestBody UserDto userDto) {
		userService.save(userDto);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(@RequestBody UserDto userDto) {
		userService.update(userDto);
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public void remove(@RequestBody UserDto userDto) {
		userService.remove(userDto);
	}



}
