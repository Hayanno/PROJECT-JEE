package com.univamu.service;

import java.util.Collection;

import com.univamu.model.Group;

public interface GroupService {
	
	/**
	 * retrieve all Group and associate each Person with it
	 * @return Collection<Group>
	 */
	public Collection<Group> findAll();
	
	/**
	 * find one group from his key id and associate each Person with it
	 * @param id
	 * @return Group
	 */
	public Group findById(int id);
}
