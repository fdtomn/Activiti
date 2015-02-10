package com.nyx.maven.web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	static Logger logger = Logger.getLogger(HomeController.class);
	
	@RequestMapping("")
	public String goHome(){
		logger.info("home page.....");
		return "index";
	}
}
