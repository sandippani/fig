package com.sandip.fig.controllers;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sandip.fig.rest.dtos.SampleDto;

@Controller
@RequestMapping(value = "/mobile/api")
public class MobileController {

	@RequestMapping(value = "/sample",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SampleDto sample() {
		return new SampleDto("sandip", 30);
	}
	@RequestMapping( value= "/postsample",   method = RequestMethod.POST)
	//produces =MediaType.TEXT_PLAIN_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE,
	public @ResponseBody String createSample(@RequestBody SampleDto s)
	{
		System.out.println(s.getAge() );
		System.out.println(s.getName() );
		return "Testing working";
		
	}
	}


