package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.JdbcGroupPersonDao;
import models.Group;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class JdbcGroupPersonDaoTest {
	@Autowired
	JdbcGroupPersonDao jdbcGroupPersonDao;

	@Test
	public void findAllGroupTest() {
		List<Group> groups = new ArrayList<Group>();
		
		groups = jdbcGroupPersonDao.findAllGroup();
		
		assertEquals(1, groups.get(0).getId());
		assertEquals("M2 Informatique Luminy", groups.get(0).getName());
		assertEquals(2, groups.get(1).getId());
		assertEquals("M1 Biologie Saint-Jérome", groups.get(1).getName());
	}
	
	@Test
	public void findGroupTest() {
		Group g = new Group();
		
		g = jdbcGroupPersonDao.findGroupById(1);
		
		assertEquals(1, g.getId());
		assertEquals("M2 Informatique Luminy", g.getName());
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void saveUpdateDeleteGroupTest() {
		Group g = new Group();
		g.setName("Groupe Test");
		jdbcGroupPersonDao.saveGroup(g);
		int idTest = g.getId();
		
		assertNotNull(idTest);
		
		g.setName("Groupe Test 2");
		jdbcGroupPersonDao.saveGroup(g);
		
		assertNotNull(g.getId());
		assertEquals(idTest, g.getId());
		
		jdbcGroupPersonDao.deleteGroup(g);
		jdbcGroupPersonDao.findGroupById(idTest); // this should throw emptyResultException
	}
}
