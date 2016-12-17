package com.univamu.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.univamu.model.Group;
import com.univamu.model.Person;
import com.univamu.service.GroupService;
import com.univamu.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	PersonService personService;
	
	@Autowired
	GroupService groupService;
	
	@RequestMapping(value = "/list")
	public ModelAndView displayListAll() {
		Collection<Person> persons = personService.findAll();
	    ModelAndView mv = new ModelAndView("/personlist"); 
	    mv.addObject("persons", persons);
	    
	    return mv;
	}
	
	@RequestMapping(value = "/list/{group_id}")
	public ModelAndView displayListByGroupId(@PathVariable(value="group_id") int group_id) {
	    ModelAndView mv = new ModelAndView("/personlist");
		Collection<Person> persons = personService.findAll(group_id);
	    Group group = groupService.findById(group_id);
	    
	    mv.addObject("persons", persons);
	    mv.addObject("group", group);
	    
	    return mv;
	}
}
