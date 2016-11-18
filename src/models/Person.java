package models;

import java.sql.Date;

public class Person {
	private Group	group;
	private int 	id;
	private String 	lastname, firstname, email, website, password;
	private Date	birthdate;
	
	public Person() {}
	
	/* ------ Getters ------ */
	public int 		getId() 		{ return id; 		}
	public String 	getLastname() 	{ return lastname; 	}
	public String 	getFirstname() 	{ return firstname; }
	public String 	getEmail() 		{ return email; 	}
	public String 	getWebsite() 	{ return website; 	}
	public Date 	getBirthdate() 	{ return birthdate; }
	public String 	getPassword() 	{ return password; 	}
	public Group	getGroup() 		{ return group; 	}
	
	/* ------ Setters ------ */
	public void setId(int id) 					{ this.id = id; 				}
	public void setLastname(String lastname) 	{ this.lastname = lastname; 	}
	public void setFirstname(String firstname) 	{ this.firstname = firstname; 	}
	public void setEmail(String email) 			{ this.email = email; 			}
	public void setWebsite(String website) 		{ this.website = website; 		}
	public void setBirthdate(Date birthdate) 	{ this.birthdate = birthdate; 	}
	public void setPassword(String password) 	{ this.password = password; 	}
	public void setGroup(Group group) 			{ this.group = group; 			}
	
	
}
