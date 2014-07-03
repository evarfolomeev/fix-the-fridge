package com.fix.the.fridge.controller;

import com.fix.the.fridge.converters.IdeaConverter;
import com.fix.the.fridge.dao.IdeaDao;
import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.domain.Idea;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.dto.IdeaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/idea")
@Transactional
public class IdeasController {

	@Autowired
	private IdeaDao ideaDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private IdeaConverter ideaConverter;

	@RequestMapping
	public String view(ModelMap model) {
		model.addAttribute("message", "Fix the Fridge!");
		return "ideas";
	}

	@RequestMapping("get-all")
	@ResponseBody
	public List<IdeaDto> getAll() {
		List<Idea> allIdeas = ideaDao.findAll();
		List<IdeaDto> convertedIdeas = new ArrayList<>();

		for (Idea idea : allIdeas) {
			convertedIdeas.add(ideaConverter.convert(idea));
		}

		return convertedIdeas;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public IdeaDto save(@RequestBody IdeaDto idea) {
		ideaDao.save(ideaConverter.convert(idea));
		return idea;
	}

	@RequestMapping("vote")
	@ResponseBody
	public void vote(@RequestParam Long ideaId, @RequestParam String userNickName) {
		Idea idea = ideaDao.findById(ideaId);
		if (idea == null) {
			return;
		}

		User user = userDao.find(userNickName);
		if (user == null) {
			return;
		}

		idea.getVoters().add(user);
		ideaDao.save(idea);
	}
}
