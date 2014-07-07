package com.fix.the.fridge.converters;

import com.fix.the.fridge.domain.Status;
import com.fix.the.fridge.dto.StatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Artem Petrov
 */
@Component
public class StatusConverter {

	private static final Logger log = LoggerFactory.getLogger(StatusConverter.class);

	public Status convert(StatusDto sourceStatus) {
		if (sourceStatus == null) {
			return null;
		}

		switch (sourceStatus) {
			case IN_PROGRESS:
				return Status.IN_PROGRESS;
			case OPEN:
				return Status.OPEN;
			case REJECTED:
				return Status.REJECTED;
			case RESOLVED:
				return Status.RESOLVED;
		}
		log.warn("fail to fine appropriate Status for " + sourceStatus);
		return null;
	}
	public StatusDto convert(Status sourceStatus) {
		if (sourceStatus == null) {
			return null;
		}

		switch (sourceStatus) {
			case IN_PROGRESS:
				return StatusDto.IN_PROGRESS;
			case OPEN:
				return StatusDto.OPEN;
			case REJECTED:
				return StatusDto.REJECTED;
			case RESOLVED:
				return StatusDto.RESOLVED;
		}
		log.warn("fail to fine appropriate StatusDto for " + sourceStatus);
		return null;
	}
}
