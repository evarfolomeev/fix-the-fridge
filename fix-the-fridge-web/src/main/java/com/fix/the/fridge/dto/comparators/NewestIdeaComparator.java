package com.fix.the.fridge.dto.comparators;

import com.fix.the.fridge.dto.IdeaDto;

import java.util.Comparator;

/**
 * @author Artem Petrov
 */
public class NewestIdeaComparator implements Comparator<IdeaDto> {
	@Override
	public int compare(IdeaDto o1, IdeaDto o2) {
		if (o1 == null && o2 == null) {
			return 0;
		}
		if (o1 == null) {
			return 1;
		}
		if (o2 == null) {
			return -1;
		}
		if (o1.getCreationDate() == null && o2.getCreationDate() == null) {
			return 0;
		}
		if (o1.getCreationDate() == null) {
			return 1;
		}
		if (o2.getCreationDate() == null) {
			return -1;
		}
		return o2.getCreationDate().compareTo(o1.getCreationDate());
	}
}
