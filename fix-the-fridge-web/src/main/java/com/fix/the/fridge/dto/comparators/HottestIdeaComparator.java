package com.fix.the.fridge.dto.comparators;

import com.fix.the.fridge.dto.IdeaDto;

import java.util.Comparator;

/**
 * @author Artem Petrov
 */
public class HottestIdeaComparator implements Comparator<IdeaDto> {
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
		if (o1.getVoters() == null && o2.getVoters() == null) {
			return 0;
		}
		if (o1.getVoters() == null) {
			return 1;
		}
		if (o2.getVoters() == null) {
			return -1;
		}
		return o2.getVoters().compareTo(o1.getVoters());
	}
}
