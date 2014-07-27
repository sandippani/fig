package com.sandip.fig.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
