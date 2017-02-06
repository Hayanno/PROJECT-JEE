package com.univamu.service;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.univamu.dao.GroupPersonDao;
import com.univamu.model.Person;

@Service
public class SPersonService implements PersonService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private GroupPersonDao groupPersonDao;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(Person person) {
		person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
		
		groupPersonDao.savePerson(person);
	}
	
	@Override
	public Person find(long id) {
		return groupPersonDao.findPersonById(id);
	}

	@Override
	public Person findByEmail(String email) {
		Person person;
		
		try {
			person = groupPersonDao.findPersonByEmail(email);
		}
		catch (EmptyResultDataAccessException e) {
			// LOG quelque chose ici
			
			return new Person();
		}
		
		return person;
	}

	@Override
	public Collection<Person> findAll(int id) {
		return groupPersonDao.findAllPerson(id);
	}

	@Override
	public Collection<Person> findAll() {
		return groupPersonDao.findAllPerson();
	}

	@Override
	public void update(Person person) {
		person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
		
		groupPersonDao.savePerson(person);
	}

	@Override
	public Collection<Person> findByKeyword(String keyword) {
		return groupPersonDao.findPersonByKeyword(keyword);
	}
}
