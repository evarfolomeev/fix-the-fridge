package com.fix.the.fridge.converters;

import com.fix.the.fridge.dto.Attachment;
import org.springframework.stereotype.Component;

/**
 * @author Artem Petrov
 */
@Component
public class AttachmentConverter {
	public Attachment convert(com.fix.the.fridge.domain.Attachment sourceAttachment) {
		Attachment targetAttachment = new Attachment();

		targetAttachment.setName(sourceAttachment.getId());
		targetAttachment.setType(sourceAttachment.getType());

		return targetAttachment;
	}
}
