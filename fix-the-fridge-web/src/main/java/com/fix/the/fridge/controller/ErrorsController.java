package com.fix.the.fridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class ErrorsController {
	@RequestMapping("pageNotFound")
	public String login(Model model) {
		return "pageNotFound";
	}
}
