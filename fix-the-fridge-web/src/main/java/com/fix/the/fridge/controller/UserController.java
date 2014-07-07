package com.fix.the.fridge.controller;

import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.dto.UserDto;
import com.fix.the.fridge.service.UserService;
import com.fix.the.fridge.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	@Autowired
	private Security security;

	@RequestMapping("get-all")
	@ResponseBody
	public List<UserDto> getAll() {
		return userService.findAll();
	}
	@RequestMapping("get-current")
	@ResponseBody
	@Transactional
	public UserDto getCurrent() {
		return security.getCurrentUser();
	}

	@RequestMapping(value = "signup")
	public String signUp() {
		return "signup";
	}

	//It might look weird but the mobile side needs such a method
	@RequestMapping(value = "login")
	public String login(){
		return "ok";
	}

	@RequestMapping(value = "do-sign-up", method = RequestMethod.POST)
	public String doSignUp(
			@RequestParam(value = "nickname", required = true) final String nickname,
			@RequestParam(value = "password", required = true) final String password,
			@RequestParam(value = "email", required = true) String email) {
		UserDto userDto = new UserDto();
		userDto.setNickName(nickname);
		userDto.setEmail(email);

		userService.registerUser(userDto, password);

		return "redirect:/login.jsp";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public UserDto save(@RequestBody UserDto userDto) {
		userService.save(userDto);
		return userDto;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public UserDto update(@RequestBody UserDto userDto) {
		userService.update(userDto);
		return userDto;
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public void remove(@RequestBody UserDto userDto) {
		userService.remove(userDto);
	}


	@RequestMapping("update-avatar")
	@ResponseBody
	public UserDto updateAvatar(@RequestParam String avatarId) {
		return userService.updateAvatar(avatarId);
	}
}
