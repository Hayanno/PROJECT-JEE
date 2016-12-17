package com.univamu.service;

import java.util.Collection;

import com.univamu.model.Person;

public interface PersonService {
	public void save(Person person);
	
	public Person findByEmail(String email);
	
	public Collection<Person> findAll(int id);
	
	public Collection<Person> findAll();
}
