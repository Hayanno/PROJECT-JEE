package com.univamu.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.univamu.model.Group;
import com.univamu.service.GroupService;

public class GroupIdToGroupConverter implements Converter<String, Group> {
	
	@Autowired
	GroupService groupService;
	
	/**
	 * Convert a given group id in a Group
	 * @param String
	 * @return Group
	 */
	@Override
	public Group convert(String id) {
		return groupService.findById(Integer.parseInt(id));
	}
}
