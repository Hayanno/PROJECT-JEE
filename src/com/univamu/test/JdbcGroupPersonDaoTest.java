package com.univamu.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.univamu.dao.JdbcGroupPersonDao;
import com.univamu.model.Group;
import com.univamu.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:WebContent/WEB-INF/spring/dao-config.xml"})
public class JdbcGroupPersonDaoTest {
	
	@Autowired
	@Qualifier("jdbc")
	JdbcGroupPersonDao jdbcGroupPersonDao;

	@Test
	public void findAllGroupPersonTest() {
		List<Group> groups = new ArrayList<Group>();
		
		groups = jdbcGroupPersonDao.findAllGroup();
		
		Group group0 = groups.get(0);
		Group group1 = groups.get(1);
		
		Map<Integer, Person> personsGroup0 = group0.getPersons();
		Map<Integer, Person> personsGroup1 = group1.getPersons();
		
		Person personGroup0 = personsGroup0.get(1);
		Person personGroup1 = personsGroup1.get(2);
		
		LocalDate date1 = LocalDate.of(1992, 02, 02);
				
		// get Name Test
		assertEquals(1, group0.getId());
		assertEquals(2, group1.getId());
		
		// get Id Test
		assertEquals("M2 Informatique Luminy", group0.getName());
		assertEquals("M1 Biologie Saint-Jérome", group1.getName());
		
		// get Persons Test
		//assertEquals(1, personsGroup0.size());
		//assertEquals(2, personsGroup1.size());
		
		// get Persons From HashMap Test
		assertNotNull(personGroup0);
		assertNotNull(personGroup1);
		
		// get Person Firstname Test
		assertEquals("Nicolas", personGroup0.getFirstname());	
		assertEquals("Paul", personGroup1.getFirstname());
		
		// get Person Lastname Test
		assertEquals("Léotier", personGroup0.getLastname());	
		assertEquals("Gades", personGroup1.getLastname());
		
		// get Person Email Test
		assertEquals("nicolas@leotier.fr", personGroup0.getEmail());	
		assertEquals("paul.gades@gmail.com", personGroup1.getEmail());
		
		// get Person Website Test
		assertEquals("leotier.fr", personGroup0.getWebsite());	
		assertNull(personGroup1.getWebsite());
		
		// get Person Birthdate Test
		assertEquals(date1, personGroup0.getBirthdate());	
		assertNull(personGroup1.getBirthdate());
		
		// get Person Password Test
		assertEquals("pass", personGroup0.getPassword());	
		assertEquals("pass", personGroup1.getPassword());
	}
	
	@Test
	public void findAllPersonTest() {
		LocalDate date = LocalDate.of(1992, 02, 02);
		List<Person> persons;
		int group_id = 1;
		
		persons = jdbcGroupPersonDao.findAllPerson(group_id);
		
		//assertEquals(1, persons.size());
		
		Person person = persons.get(0);
		
		assertNotNull(person);
		assertEquals(group_id, person.getGroup().getId());

		assertEquals(1, person.getId());
		assertEquals("Léotier", person.getLastname());
		assertEquals("Nicolas", person.getFirstname());
		assertEquals("nicolas@leotier.fr", person.getEmail());
		assertEquals("leotier.fr", person.getWebsite());
		assertEquals(date, person.getBirthdate());
		assertEquals("pass", person.getPassword());
		assertEquals(1, person.getGroup().getId());		
	}
	
	@Test
	public void findGroupByIdTest() {
		LocalDate date = LocalDate.of(1992, 02, 02);
		int group_id = 1;
		
		Group group = jdbcGroupPersonDao.findGroupById(group_id);
		
		assertEquals(1, group.getId());
		assertEquals("M2 Informatique Luminy", group.getName());
		
		assertFalse(group.getPersons().isEmpty());
		
		Person person = group.getPerson(1);

		assertNotNull(person);
		assertEquals(group_id, person.getGroup().getId());

		assertEquals(1, person.getId());
		assertEquals("Léotier", person.getLastname());
		assertEquals("Nicolas", person.getFirstname());
		assertEquals("nicolas@leotier.fr", person.getEmail());
		assertEquals("leotier.fr", person.getWebsite());
		assertEquals(date, person.getBirthdate());
		assertEquals("pass", person.getPassword());
		assertEquals(1, person.getGroup().getId());
	}
	
	@Test
	public void findPersonByIdTest() {
		LocalDate date = LocalDate.of(1992, 05, 31);
		int person_id = 3;
		Person person = jdbcGroupPersonDao.findPersonById(person_id);

		assertNotNull(person);
		assertEquals(person_id, person.getId());
		assertEquals("Costard", person.getLastname());
		assertEquals("Quentin", person.getFirstname());
		assertEquals("quentin.costard@hotmail.fr", person.getEmail());
		assertNull(person.getWebsite());
		assertEquals(date, person.getBirthdate());
		assertEquals("blabla", person.getPassword());
		assertEquals(2, person.getGroup().getId());
		
		Group group = person.getGroup();
		
		assertNotNull(group);		
		assertEquals(2, group.getId());
		assertEquals("M1 Biologie Saint-Jérome", group.getName());
		
		assertFalse(group.getPersons().isEmpty());
		assertEquals(2, group.getPersons().size());
	}
	
	@Test
	public void findPersonByKeywordTest() {
		String keyword = "Nicolas";
		List<Person> persons = jdbcGroupPersonDao.findPersonByKeyword(keyword);

		assertNotNull(persons);
		assertNotEquals(0, persons.size());
		assertEquals("Nicolas", persons.get(0).getFirstname());
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void saveUpdateDeleteGroupTest() {
		LocalDate date = LocalDate.of(1992, 02, 02);
		Group g = new Group();
		g.setName("Groupe Test");
		
		Person p = new Person();
		p.setFirstname("Personne Test");
		p.setLastname("Personne Test");
		p.setEmail("Personne Test");
		p.setWebsite("Personne Test");
		p.setBirthdate(date);
		p.setPassword("Personne Test");
		p.setGroup(g);
		g.addPerson(p);
		
		// Save
		jdbcGroupPersonDao.saveGroup(g);
		int idTest = g.getId();
		assertNotNull(idTest);
		
		// Update
		g.setName("Groupe Test 2");
		jdbcGroupPersonDao.saveGroup(g);
		assertNotNull(g.getId());
		assertEquals(idTest, g.getId());
		assertEquals("Groupe Test 2", g.getName());
		
		// Delete
		jdbcGroupPersonDao.deleteGroup(g);
		jdbcGroupPersonDao.findGroupById(idTest); // this should throw emptyResultException
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void saveUpdateDeletePersonTest() {
		LocalDate date = LocalDate.of(1992, 02, 02);
		Group g = new Group();
		g.setName("Groupe Test");
		jdbcGroupPersonDao.saveGroup(g);
		int idGroupTest = g.getId();
		assertNotNull(idGroupTest);
		
		// Save
		Person p = new Person();
		p.setFirstname("Personne Test");
		p.setLastname("Personne Test");
		p.setEmail("Personne Test");
		p.setWebsite("Personne Test");
		p.setBirthdate(date);
		p.setPassword("Personne Test");
		p.setGroup(g);
		jdbcGroupPersonDao.savePerson(p);
		int idPersonTest = p.getId();
		assertNotNull(idPersonTest);

		// Update
		p.setLastname("Personne Test 2");
		jdbcGroupPersonDao.savePerson(p);
		assertEquals(idPersonTest, p.getId());
		assertEquals("Personne Test 2", p.getLastname());
		
		// Delete
		jdbcGroupPersonDao.deletePerson(p);
		jdbcGroupPersonDao.deleteGroup(g);
		jdbcGroupPersonDao.findPersonById(idPersonTest); // this should throw EmptyResultDataAccessException
	}
}
