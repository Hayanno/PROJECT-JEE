package dao;

import java.util.Collection;

import models.Group;
import models.Person;

public interface GroupPersonDao {	
	/**
	 * save one group
	 * @param g
	 */
	void saveGroup(Group g);
	
	/**
	 *  save one person
	 *  @param p
	 */
	void savePerson(Person p);
	
	/**
	 * delete one group
	 * @param g
	 */
	void deleteGroup(Group g);
	
	/**
	 * delete on person
	 * @param p
	 */
	void deletePerson(Person p);

	/**
	 * find one group from his key id and associate each Person with it
	 * @param id
	 * @return Group
	 */
	Group findGroupById(long id);

	/** 
	 * find one person from his key id and associate his group
	 * @param id
	 * @return Person
	 */
	Person findPersonById(long id);

	/**
	 * retrieve all Group and associate each Person with it
	 * @return Collection<Group>
	 */
	Collection<Group> findAllGroup();

	/**
	 * retrieve all Person and associate each Group with it
	 * @param group_id
	 * @return Collection<Person>
	 */
	Collection<Person> findAllPerson(long id);
}
