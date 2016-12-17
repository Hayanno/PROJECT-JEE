package com.univamu.service;

import java.util.Collection;

import com.univamu.model.Group;

public interface GroupService {
	
	public Collection<Group> findAll();
	
	public Group findById(int id);
}
