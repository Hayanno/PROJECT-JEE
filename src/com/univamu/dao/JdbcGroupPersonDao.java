package com.univamu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.univamu.model.Group;
import com.univamu.model.Person;

@Repository(value="jdbc")
public class JdbcGroupPersonDao implements GroupPersonDao {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String GROUP_TABLE_NAME = "group";
	private String PERSON_TABLE_NAME = "person";
	private String SELECT_ALL_GROUP_PERSON = 
		"SELECT g.id, g.name, p.id, p.firstname, p.lastname, p.email, p.website, p.birthdate, p.password, p.group_id "
		+ "FROM `" + GROUP_TABLE_NAME + "` AS g "
		+ "INNER JOIN `" + PERSON_TABLE_NAME + "` AS p "
		+ "ON g.id = p.group_id";
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	@Transactional(readOnly = true)
	public List<Group> findAllGroup() {
		return jdbcTemplate.query(SELECT_ALL_GROUP_PERSON,
			new GroupPersonExtractor());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Person> findAllPerson() {
		return jdbcTemplate.query(SELECT_ALL_GROUP_PERSON,
			new PersonGroupExtractor());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Person> findAllPerson(final long group_id) {
		return jdbcTemplate.query(SELECT_ALL_GROUP_PERSON + " WHERE g.id = ?",
			new Object[]{group_id}, new PersonGroupExtractor());
	}

	@Override
	@Transactional(readOnly = true)
	public Group findGroupById(final long id) {
		List<Person> persons = this.findAllPerson(id);
		
		if(persons.isEmpty())
			throw new EmptyResultDataAccessException(1);
		
		return persons.get(0).getGroup();
	}

	@Override
	@Transactional(readOnly = true)
	public Person findPersonById(final long id) {
		List<Person> persons = jdbcTemplate.query(
				SELECT_ALL_GROUP_PERSON
				+ " WHERE g.id = (SELECT pe.group_id FROM `" + PERSON_TABLE_NAME + "` AS pe WHERE pe.id = ?)",
				new Object[]{id}, new PersonGroupExtractor());
		
		for(Person p: persons) {
			if(p.getId() == id)
				return p;
		}
			
		throw new EmptyResultDataAccessException(1);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Person findPersonByEmail(String email) {
		List<Person> persons = jdbcTemplate.query(
				SELECT_ALL_GROUP_PERSON
				+ " WHERE g.id = (SELECT pe.group_id FROM `" + PERSON_TABLE_NAME + "` AS pe WHERE pe.email = ?)",
				new Object[]{email}, new PersonGroupExtractor());
		
		for(Person p: persons) {
			if(p.getEmail().equals(email))
				return p;
		}
			
		throw new EmptyResultDataAccessException("Email not found", 1);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Person> findPersonByKeyword(String keywords) {
		keywords = keywords.trim().replaceAll(" ", "|");
		
		logger.info("Keyword : " + keywords);
		
		List<Person> persons = jdbcTemplate.query(
				SELECT_ALL_GROUP_PERSON
				+ " WHERE p.lastname REGEXP ? OR p.firstname REGEXP ? OR p.website REGEXP ? OR g.name REGEXP ?",
				new Object[]{keywords, keywords, keywords, keywords},
				new PersonGroupExtractor());

		return persons;
	};

	@Override
	@Transactional
	public void saveGroup(Group g) {
		// First we save/update the Group
		if(g.getId() == 0) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(
				new PreparedStatementCreator() {
					@Override
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps = connection.prepareStatement("INSERT INTO `" + GROUP_TABLE_NAME + "` (name) values (?)",
			            		new String[]{"id"});
			            ps.setString(1, g.getName());
			            return ps;
			        }
			    },
				keyHolder);
			
			int id = keyHolder.getKey().intValue();
			g.setId(id);
		}
		else
			jdbcTemplate.update(
				"UPDATE `" + GROUP_TABLE_NAME + "` SET name = ? WHERE id = ?",
				g.getName(), g.getId());
		
		// Then we update all Person
		List<Person> personsToUpdate = g.getPersons().values().stream().filter(p -> p.getId() != 0).collect(Collectors.toList());
		
		if(!personsToUpdate.isEmpty())
			jdbcTemplate.batchUpdate("UPDATE `" + PERSON_TABLE_NAME + "` "
				+ "SET firstname = ?, lastname = ?, email = ?, website = ?, birthdate = ?, password = ?, group_id = ? "
				+ "WHERE id = ?",
				new PersonBatch(personsToUpdate));
		
		// Finally we save all new Person (we can't use batch insert because we can't retrieve auto-generated key from batch in JDBC)
		g.getPersons().values().stream().filter(p -> p.getId() == 0).forEach(p -> {
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(
				new PreparedStatementCreator() {
					@Override
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps = connection.prepareStatement(
			            	"INSERT INTO `" + PERSON_TABLE_NAME + "` "
			            	+ "(firstname, lastname, email, website, password, birthdate, group_id) values (?, ?, ?, ?, ?, ?, ?)",
			            	new String[]{"id"});
			            
			            ps.setString(1, p.getFirstname());
			            ps.setString(2, p.getLastname());
			            ps.setString(3, p.getEmail());
			            ps.setString(5, p.getPassword());
			            ps.setInt(7, g.getId());
			            
			            if(p.getWebsite().isEmpty())
				            ps.setString(4, null);
			            else
				            ps.setString(4, p.getWebsite());
			            
			            if(p.getBirthdate() == null)
				            ps.setDate(6, null);
			            else
				            ps.setDate(6, Date.valueOf(p.getBirthdate()));
			            return ps;
			        }
			    },
				keyHolder);
			
			p.setId(keyHolder.getKey().intValue());		
		});
	}

	@Override
	@Transactional
	public void savePerson(Person person) {
		person.getGroup().addPerson(person);
		saveGroup(person.getGroup());
	}

	@Override
	@Transactional
	public void deleteGroup(Group group) {
		jdbcTemplate.update("DELETE FROM `" + PERSON_TABLE_NAME + "` WHERE group_id = ?", group.getId());
		jdbcTemplate.update("DELETE FROM `" + GROUP_TABLE_NAME + "` WHERE id = ?", group.getId());
	}

	@Override
	@Transactional
	public void deletePerson(Person person) {
		jdbcTemplate.update("DELETE FROM `" + PERSON_TABLE_NAME + "` WHERE id = ?", person.getId());
	}
	
	
	/* ------ Helper methods ------ */
	
	private class GroupMapper implements RowMapper<Group> {

		@Override
		public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
			Group group = new Group();
			
			group.setId(rs.getInt("g.id"));
			group.setName(rs.getString("g.name"));
			
			return group;
		}
		
	}
	
	private class PersonMapper implements RowMapper<Person> {

		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person person = new Person();
			Date date = rs.getDate("p.birthdate");
			
			if(date == null)
				person.setBirthdate(null);
			else
				person.setBirthdate(date.toLocalDate());
				
			person.setId(rs.getInt("p.id"));
			person.setEmail(rs.getString("p.email"));
			person.setWebsite(rs.getString("p.website"));
			person.setPassword(rs.getString("p.password"));
			person.setLastname(rs.getString("p.lastname"));
			person.setFirstname(rs.getString("p.firstname"));
			
			return person;
		}
		
	}
	
	private class PersonGroupExtractor implements ResultSetExtractor<List<Person>> {

		@Override
		public List<Person> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<Person> persons = new ArrayList<Person>();
			Map<Integer, Group> groups = new HashMap<Integer, Group>();
			
			while(rs.next()) {
				int group_id = rs.getInt("g.id");
				Group group = groups.get(group_id);
				
				if(group == null) {
					group = new GroupMapper().mapRow(rs, 0);
					
					groups.put(group_id, group);
				}
				
				Person person = new PersonMapper().mapRow(rs, 0);
				person.setGroup(group);
				group.addPerson(person);
				
				persons.add(person);
			}
			
			return persons;
		}
		
	}
	
	private class GroupPersonExtractor implements ResultSetExtractor<List<Group>> {

		@Override
		public List<Group> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Integer, Group> groups = new HashMap<Integer, Group>();
			
			while(rs.next()) {
				int group_id = rs.getInt("g.id");
				Group group = groups.get(group_id);
				
				if(group == null) {
					group = new GroupMapper().mapRow(rs, 0);
					
					groups.put(group_id, group);
				}
				
				Person person = new PersonMapper().mapRow(rs, 0);
				person.setGroup(group);
				group.addPerson(person);
			}
			
			return new ArrayList<Group>(groups.values());
		}
		
	}
	
	private class PersonBatch implements BatchPreparedStatementSetter {
		
		private final List<Person> persons;
		
		public PersonBatch(List<Person> persons) {
			this.persons = persons;
		}
		
		@Override
		public void setValues(PreparedStatement ps, int i) throws SQLException {
			Person person = persons.get(i);

			ps.setString(1, person.getFirstname());
			ps.setString(2, person.getLastname());
			ps.setString(3, person.getEmail());
			ps.setString(4, person.getWebsite());
			ps.setString(6, person.getPassword());
			ps.setInt(7, person.getGroup().getId());
			ps.setInt(8, person.getId());			

            if(person.getBirthdate() == null)
	            ps.setDate(5, null);
            else
	            ps.setDate(5, Date.valueOf(person.getBirthdate()));
		}

		@Override
		public int getBatchSize() {
			return persons.size();
		}
		
	}
}
