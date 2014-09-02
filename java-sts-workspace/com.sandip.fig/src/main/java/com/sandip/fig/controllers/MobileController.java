package com.sandip.fig.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sandip.fig.rest.dtos.RegistrationDto;
import com.sandip.fig.rest.dtos.ResponseDto;
import com.sandip.fig.rest.dtos.ResponseStatus;
import com.sandip.fig.rest.dtos.SampleDto;
import com.sandip.fig.service.utils.SystemConfigurations;

@Controller
@RequestMapping(value = "/mobile/api")
public class MobileController {
	
	@Autowired
	private SystemConfigurations systemConfigurations;

	@RequestMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SampleDto sample() {
		return new SampleDto("sandip", 30);
	}

	@RequestMapping(value = "/postsample", method = RequestMethod.POST)
	// produces =MediaType.TEXT_PLAIN_VALUE, consumes
	// =MediaType.APPLICATION_JSON_VALUE,
	@ResponseBody
	public String createSample(@RequestBody SampleDto s) {
		System.out.println(s.getAge());
		System.out.println(s.getName());
		return "Testing working";

	}
	@RequestMapping(value="/upload/image",method=RequestMethod.POST)
	@ResponseBody
	public String uploadImage(@RequestParam("image")MultipartFile image,@RequestParam("comment")String comment){
		
		String basePath = systemConfigurations.getImageUploadDirectory();
		String filename = image.getOriginalFilename();
		File finalFile = new File(basePath, System.nanoTime()+filename);
		try {
			image.transferTo(finalFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Image uploaded sucessfully with:"+comment;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	// produces =MediaType.TEXT_PLAIN_VALUE, consumes
	// =MediaType.APPLICATION_JSON_VALUE,
	@ResponseBody
	public ResponseDto createSample(@RequestBody RegistrationDto registrationDto) {
		
		return new ResponseDto("done", ResponseStatus.SUCCESS);

	}

}
