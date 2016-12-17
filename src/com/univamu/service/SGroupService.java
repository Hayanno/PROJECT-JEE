package com.univamu.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.univamu.dao.GroupPersonDao;
import com.univamu.model.Group;

@Service
public class SGroupService implements GroupService {

	@Autowired
	GroupPersonDao groupPersonDao;
	
	@Override
	public Collection<Group> findAll() {
		return groupPersonDao.findAllGroup();
	}

	@Override
	public Group findById(int id) {
		return groupPersonDao.findGroupById(id);
	}
}
