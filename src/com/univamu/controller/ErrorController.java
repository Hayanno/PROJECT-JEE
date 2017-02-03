package com.univamu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

@RequestMapping(value="/error")
    public ModelAndView display() {
	    ModelAndView mv = new ModelAndView("/error"); 
	    
	    return mv;
	}

}