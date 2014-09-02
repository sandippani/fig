package com.sandip.fig.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sandip.fig.service.JdbcService;

@Controller
public class HomeController {
	
	@Autowired
	private JdbcService jdbcService;
	
	@RequestMapping(value="/")
	public String home(){
		jdbcService.test();
		return "index";
	}

}
