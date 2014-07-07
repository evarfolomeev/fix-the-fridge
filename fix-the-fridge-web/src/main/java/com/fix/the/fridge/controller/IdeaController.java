package com.fix.the.fridge.controller;

import com.fix.the.fridge.converters.IdeaConverter;
import com.fix.the.fridge.converters.UserConverter;
import com.fix.the.fridge.dao.AttachmentDao;
import com.fix.the.fridge.dao.IdeaDao;
import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.domain.Attachment;
import com.fix.the.fridge.domain.Idea;
import com.fix.the.fridge.domain.User;
import com.fix.the.fridge.dto.IdeaDto;
import com.fix.the.fridge.dto.UserDto;
import com.fix.the.fridge.dto.comparators.HottestIdeaComparator;
import com.fix.the.fridge.dto.comparators.NewestIdeaComparator;
import com.fix.the.fridge.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/idea")
@Transactional
public class IdeaController {
	@Autowired
	private IdeaDao ideaDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private IdeaConverter ideaConverter;
	@Autowired
	private Security security;

	@RequestMapping
	public String view(ModelMap model) {
		UserDto currentUser = security.getCurrentUser();
		model.addAttribute("ideas", getAllNewest());
		model.addAttribute("nickName", currentUser.getNickName());
		String avatar = "";
		if (!StringUtils.isEmpty(currentUser.getAvatarName())) {
			avatar = "/fix-the-fridge-web/api/attachment/get/" + currentUser.getAvatarName();
		}
		model.addAttribute("avatar", avatar);
		return "ideas";
	}

	@RequestMapping("get-all-newest")
	@ResponseBody
	public List<IdeaDto> getAllNewest() {
		List<Idea> allIdeas = ideaDao.findAll();
		List<IdeaDto> convertedIdeas = new ArrayList<>();

		for (Idea idea : allIdeas) {
			convertedIdeas.add(ideaConverter.convert(idea));
		}

		Collections.sort(convertedIdeas, new NewestIdeaComparator());
		return convertedIdeas;
	}

	@RequestMapping("get-all-hottest")
	@ResponseBody
	public List<IdeaDto> getAllHottest() {
		List<Idea> allIdeas = ideaDao.findAll();
		List<IdeaDto> convertedIdeas = new ArrayList<>();

		for (Idea idea : allIdeas) {
			convertedIdeas.add(ideaConverter.convert(idea));
		}

		Collections.sort(convertedIdeas, new HottestIdeaComparator());
		return convertedIdeas;
	}

	@RequestMapping("get-my")
	@ResponseBody
	public List<IdeaDto> getMy() {
		String currentUserNickName = Security.getCurrentUserNickName();
		List<Idea> myIdeasEntities = ideaDao.findByUser(currentUserNickName);

		List<IdeaDto> convertedIdeaDtos = new ArrayList<>();
		for (Idea myIdeasEntity : myIdeasEntities) {
			convertedIdeaDtos.add(ideaConverter.convert(myIdeasEntity));
		}
		return convertedIdeaDtos;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public IdeaDto save(@RequestBody IdeaDto idea) {
		idea.setUser(security.getCurrentUser());

		idea.setId(null);
		Idea entity = ideaConverter.convert(idea);
		ideaDao.save(entity);
		return ideaConverter.convert(entity);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public IdeaDto update(@RequestBody IdeaDto idea) {
		String authorNickName = ideaDao.findById(idea.getId()).getUser().getNickName();
		if (!authorNickName.equals(Security.getCurrentUserNickName())) {
			throw new RuntimeException("user not allowed to comment edit not their comments");
		}

		ideaDao.update(ideaConverter.convert(idea));
		return idea;
	}

	@RequestMapping(value = "vote", method = RequestMethod.POST)
	@ResponseBody
	public IdeaDto vote(@RequestParam Long ideaId) {
		String userNickName = Security.getCurrentUserNickName();

		Idea idea = ideaDao.findById(ideaId);
			if (idea == null) {
				throw new RuntimeException("Fail to get Idea by id: " + ideaId);
		}

		User user = userDao.find(userNickName);
		if (user == null) {
			throw new RuntimeException("Fail to get user by nick name: " + userNickName);
		}

		if (idea.getVoters().contains(user)) {
			//try to unvote
			idea.getVoters().remove(user);
		} else {
			//try to vote
			idea.getVoters().add(user);
		}
		ideaDao.update(idea);

		return ideaConverter.convert(idea);
	}
}
