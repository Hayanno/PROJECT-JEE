package com.univamu.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.univamu.model.Group;
import com.univamu.service.GroupService;

@Controller
@RequestMapping("/group")
public class GroupController {
	
	@Autowired
	GroupService groupService;
	
	/**
	 * Display a list of all the group registered in the app
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/list")
	public ModelAndView displayListAll() {
		Collection<Group> groups = groupService.findAll();
	    ModelAndView mv = new ModelAndView("/grouplist"); 
	    mv.addObject("groups", groups);
	    
	    return mv;
	}
}
