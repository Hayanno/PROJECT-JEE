package com.univamu.service;

import java.util.Collection;

import com.univamu.model.Person;

public interface PersonService {
	
	/**
	 *  save one person and his group
	 *  @param p
	 */
	public void save(Person person);
	
	/**
	 * find one person from his email address and associate his group
	 * @param email
	 * @return Person
	 */
	public Person findByEmail(String email);
	
	/**
	 * retrieve all Person corresponding to the given id and associate each Group with it
	 * @param group_id
	 * @return Collection<Person>
	 */
	public Collection<Person> findAll(int id);
	
	/**
	 * retrieve all Person and associate each Group with it
	 * @param group_id
	 * @return Collection<Person>
	 */
	public Collection<Person> findAll();
	
	/**
	 *  update one person and his group
	 *  @param p
	 */
	public void update(Person person);

	/**
	 * retrieve all Person with the given keyword and associate each Group with it
	 * @param keyword
	 * @return Collection<Person>
	 */
	public Collection<Person> findByKeyword(String keyword);
}
