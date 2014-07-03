package com.fix.the.fridge.controller;

import com.fix.the.fridge.dao.IdeaDao;
import com.fix.the.fridge.domain.Idea;
import com.fix.the.fridge.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/idea")
public class IdeasController {

	@Autowired
	private IdeaDao ideaDao;

	@RequestMapping
	public String view(ModelMap model) {
		model.addAttribute("message", "Fix the Fridge!");
		return "ideas";
	}

	@RequestMapping("get-all")
	@ResponseBody
	public List<Idea> getAll() {
		Idea idea1 = new Idea();
		idea1.setId(1);
		idea1.setDescription("this is description 1");
		idea1.setTitle("this is title 1");

		Idea idea2 = new Idea();
		idea2.setId(2);
		idea2.setDescription("this is description 2");
		idea2.setTitle("this is title 2");

		User user = new User();
		user.setName("this is name");
		user.setEmail("this.is.emale@test.com");
		user.setNickName("thisIsNik");

		idea1.setUser(user);
		idea2.setUser(user);

		return Arrays.asList(idea1, idea2);
	}

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(@RequestBody Idea idea) {
		ideaDao.save(idea);
    }

}
