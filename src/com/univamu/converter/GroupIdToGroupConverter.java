package com.univamu.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.univamu.model.Group;
import com.univamu.service.GroupService;

public class GroupIdToGroupConverter implements Converter<String, Group> {
	
	@Autowired
	GroupService groupService;
	
	@Override
	public Group convert(String id) {
		return groupService.findById(Integer.parseInt(id));
	}
}
