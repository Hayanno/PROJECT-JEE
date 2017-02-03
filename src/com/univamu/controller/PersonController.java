package com.univamu.controller;

import java.security.Principal;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.univamu.model.Group;
import com.univamu.model.Person;
import com.univamu.service.GroupService;
import com.univamu.service.PersonService;
import com.univamu.validator.PersonValidator;

@Controller
@RequestMapping("/person")
public class PersonController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	PersonService personService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	PersonValidator personValidator;
	
	/**
	 * Inject all available group inside every request
	 * @return Collection<Group>
	 */
	@ModelAttribute("groups")
	public Collection<Group> listGroups() {
		return groupService.findAll();
	}
	
	/**
	 * Display account page with every information on the currently logged in user
	 * @param principal
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView displayAccount(Principal principal) {
		String email = principal.getName();
		Person person = personService.findByEmail(email);
		person.setPassword("");
	    ModelAndView mv = new ModelAndView("/account"); 
	    
	    mv.addObject("personForm", person);
	    
	    return mv;
	}
	
	/**
	 * Display search result
	 * @param search
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView displaySearch(@RequestParam("search-value") String search) {
		Collection<Person> searchResult = personService.findByKeyword(search);
	    ModelAndView mv = new ModelAndView("/personlist"); 
	    
	    mv.addObject("h2Title", "Recherche \"" + search + "\"");
	    mv.addObject("persons", searchResult);
	    
	    return mv;
	}
	
	/**
	 * Update user informations
	 * @param personForm
	 * @param bindingResult
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public ModelAndView updateAccount(@ModelAttribute("personForm") Person personForm, BindingResult bindingResult, Principal principal) {
	    ModelAndView mv = new ModelAndView("/account");
	    
	    personForm.setId(personService.findByEmail(principal.getName()).getId());
		personValidator.validate(personForm, bindingResult);
		
		if(bindingResult.hasErrors()) {
			logger.info("bindingResult has errors, refreshing to account...");
			personForm.setPassword("");
		    mv.addObject("personForm", personForm);
		    
		    return mv;
		}

		logger.info("Updating person : " + personForm);
		
		personService.update(personForm);

		personForm.setPassword("");
	    mv.addObject("personForm", personForm);
	    
	    return mv;
	}
	
	/**
	 * Display a list of all the person registered in the app
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/list")
	public ModelAndView displayListAll() {
		Collection<Person> persons = personService.findAll();
	    ModelAndView mv = new ModelAndView("/personlist"); 
	    
	    mv.addObject("persons", persons);
	    
	    return mv;
	}
	
	/**
	 * Display a list of all the person in the given group
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/list/{group_id}")
	public ModelAndView displayListByGroupId(@PathVariable(value="group_id") int group_id) {
	    ModelAndView mv = new ModelAndView("/personlist");
		Collection<Person> persons = personService.findAll(group_id);
	    Group group = groupService.findById(group_id);
	    
	    mv.addObject("persons", persons);
	    mv.addObject("h2Title", "Groupe \"" + group.getName() + "\"");
	    
	    return mv;
	}
}
