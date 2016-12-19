package com.univamu.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.univamu.dao.GroupPersonDao;
import com.univamu.model.Person;

public class PersonDetailsService implements UserDetailsService {
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private GroupPersonDao groupPersonDao;
	
	/**
	 * find one person from his email address and associate his group
	 * @param email
	 * @return Person
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Person person;
		
		try {
			person = groupPersonDao.findPersonByEmail(email);
		}
		catch (EmptyResultDataAccessException e) {
			logger.info("person not found");
			
			return new Person();
		}
		
		return person;
	}

}
