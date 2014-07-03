package com.fix.the.fridge.controller;

import com.fix.the.fridge.dao.AttachmentDao;
import com.fix.the.fridge.domain.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;


/**
 * @author Atrem Petrov
 */
@Controller
@RequestMapping("/attachment")
public class AttachmentController {

	@Autowired
	private AttachmentDao attachmentDao;

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public void get(HttpServletResponse response, @PathVariable String id) throws IOException {
		Attachment attachment = attachmentDao.get(id);

		if (attachment == null) {
			throw new RuntimeException("Все пропало");
		}

		response.setContentType(attachment.getType());
		response.setContentLength(attachment.getData().length);
		FileCopyUtils.copy(attachment.getData(), response.getOutputStream());

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = request.getFile(itr.next());

		Attachment attachment = new Attachment();
		attachment.setData(mpf.getBytes());
		attachment.setType(mpf.getContentType());
		attachment.setName(mpf.getOriginalFilename());

		attachmentDao.save(attachment);

//        return String.valueOf(attachment.getId());  //todo we can return attachment with out data
		return "<img src='http://localhost:8080/fix-the-fridge/api/attachment/get/" + attachment.getId() + "' />";

	}

}