package models;

import java.util.HashMap;
import java.util.Map;

public class Group {
	private int id;
	private String name;
	private Map<Integer, Person> persons = new HashMap<Integer, Person>();
	
	/* ------ Constructor ------ */
	public Group() {}
	
	/* ------ Getters ------ */
	public int 					getId() 		{ return id; 		}
	public String 				getName() 		{ return name; 		}
	public Map<Integer, Person> getPersons() 	{ return persons; 	}

	/* ------ Setters ------ */
	public void setId(int id) 								{ this.id = id; 			}
	public void setName(String name) 						{ this.name = name; 		}
	public void setPersons(Map<Integer, Person> persons) 	{ this.persons = persons; 	}
	
	/* ------ Other ------ */
	public void addPerson(Person person) 	{ persons.put(person.getId(), person); 		}
	public void deletePerson(Person person) { persons.remove(person.getId(), person); 	}
}
