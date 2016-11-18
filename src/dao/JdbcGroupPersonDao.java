package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import models.Group;
import models.Person;

@Repository
public class JdbcGroupPersonDao implements GroupPersonDao {
	
	private String GROUP_TABLE_NAME = "group";
	private String PERSON_TABLE_NAME = "person";
	private String SELECT_ALL_GROUP_PERSON = 
		"SELECT g.id, g.name, p.id, p.firstname, p.lastname, p.email, p.website, p.birthdate, p.password, p.group_id "
		+ "FROM `" + GROUP_TABLE_NAME + "` AS g "
		+ "LEFT JOIN `" + PERSON_TABLE_NAME + "` AS p "
		+ "ON g.id = p.group_id";
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public List<Group> findAllGroup() {
		return jdbcTemplate.query(SELECT_ALL_GROUP_PERSON,
			new GroupPersonExtractor());
	}
	
	@Override
	public List<Person> findAllPerson(long group_id) {
		return jdbcTemplate.query(SELECT_ALL_GROUP_PERSON + " WHERE g.id = ?",
			new Object[]{group_id}, new PersonGroupExtractor());
	}

	@Override
	public Group findGroupById(long id) {
		List<Person> persons = this.findAllPerson(id);
		
		return persons.get(0).getGroup();
	}

	@Override
	public Person findPersonById(long id) {
		Group group = jdbcTemplate.queryForObject("SELECT g.id WHERE p.id = ?",
				new Object[]{id}, new GroupMapper());
		
		return findGroupById(group.getId()).getPersons().get(id);
	}

	@Override
	public void saveGroup(Group g) {
		// First we save/update the Group
		if(g.getId() == 0) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(
				"INSERT INTO `" + GROUP_TABLE_NAME + "` (name) values (?)",
				g.getName(), keyHolder);
			
			int id = keyHolder.getKey().intValue();
			g.setId(id);
		}
		else
			jdbcTemplate.update(
				"UPDATE `" + GROUP_TABLE_NAME + "` SET name = ? WHERE id = ?",
				g.getName(), g.getId());
		
		// Then we update all Person
		List<Person> personsToUpdate = g.getPersons().values().stream().filter(p -> p.getId() != 0).collect(Collectors.toList());
		
		jdbcTemplate.batchUpdate("UPDATE `" + PERSON_TABLE_NAME + "` "
					+ "SET firstname = ?, lastname = ?, email = ?, website = ?, birthdate = ?, password = ?, group_id = ? "
					+ "WHERE id = ?",
					new PersonBatch(personsToUpdate));
		
		// Finally we save all new Person (we can't use batch insert because we can't retrieve auto-generated key from batch in JDBC)
		g.getPersons().values().stream().filter(p -> p.getId() == 0).forEach(p -> {
				KeyHolder keyHolder = new GeneratedKeyHolder();
				
				jdbcTemplate.update(
					"INSERT INTO `" + PERSON_TABLE_NAME + "` "
					+ "(firstname, lastname, email, website, birthdate, password, group_id) values (?, ?, ?, ?, ?, ?, ?)",
					p.getFirstname(), p.getLastname(), p.getEmail(), p.getWebsite(),
					p.getBirthdate(), p.getPassword(), g.getId(),
					keyHolder);
				
				p.setId(keyHolder.getKey().intValue());		
		});
	}

	@Override
	public void savePerson(Person person) {
		saveGroup(person.getGroup());
	}

	@Override
	public void deleteGroup(Group group) {
		jdbcTemplate.update("DELETE FROM `" + PERSON_TABLE_NAME + "` WHERE group_id = ?", group.getId());
		jdbcTemplate.update("DELETE FROM `" + GROUP_TABLE_NAME + "` WHERE id = ?", group.getId());
	}

	@Override
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
			
			person.setId(rs.getInt("p.id"));
			person.setEmail(rs.getString("p.email"));
			person.setWebsite(rs.getString("p.website"));
			person.setPassword(rs.getString("p.password"));
			person.setLastname(rs.getString("p.lastname"));
			person.setBirthdate(rs.getDate("p.birthdate"));
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
			ps.setString(5, person.getPassword());
			ps.setDate(6, person.getBirthdate());
			ps.setInt(7, person.getGroup().getId());
			ps.setInt(8, person.getId());
		}

		@Override
		public int getBatchSize() {
			return persons.size();
		}
		
	};
}
