package com.sandip.fig.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sandip.fig.exception.ImageUploadException;
import com.sandip.fig.exception.RegistartionException;
import com.sandip.fig.exception.SignInException;
import com.sandip.fig.rest.dtos.ImageUploadDto;
import com.sandip.fig.rest.dtos.RegistrationDto;
import com.sandip.fig.rest.dtos.ResponseDto;
import com.sandip.fig.rest.dtos.ResponseStatus;
import com.sandip.fig.rest.dtos.SampleDto;
import com.sandip.fig.service.ImageUploadservice;
import com.sandip.fig.service.RegistrationService;
import com.sandip.fig.service.SignInService;
import com.sandip.fig.service.utils.SystemConfigurations;

@Controller
@RequestMapping(value = "/mobile/api")
public class MobileController {
	
	@Autowired
	private SystemConfigurations systemConfigurations;
	@Autowired
	private RegistrationService registrationService;
	@Autowired 
	private SignInService signInService;
	@Autowired
	private ImageUploadservice imageUploadservice;

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
	public String uploadImage(@RequestParam("image")MultipartFile image,ImageUploadDto imageUploadDto){
		
		String msg = "Image uploaded sucessfully";
		String basePath = systemConfigurations.getImageUploadDirectory();
		String filename = image.getOriginalFilename();
		File finalFile = new File(basePath, System.nanoTime()+filename);
		
		try {
			image.transferTo(finalFile);
			imageUploadDto.setFilepath(finalFile.getAbsolutePath());
			imageUploadservice.save(imageUploadDto);
			
		} catch (IllegalStateException e) {
			msg=e.getMessage();
		} catch (IOException e) {
			msg=e.getMessage();
		} catch (ImageUploadException e) {
			msg=e.getMessage();
		}
		return msg;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto registerUser(@RequestBody RegistrationDto registrationDto) {
		//JdbcServiceImpl j = new JdbcServiceImpl();
		//j.test();
		try {
			registrationService.create(registrationDto);
			return new ResponseDto("done", ResponseStatus.SUCCESS);
		} catch (RegistartionException e) {
			return new ResponseDto(e.getMessage(), ResponseStatus.FAIL);
		}

	}
	@RequestMapping(value = "/signin", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto signinUser(@RequestBody RegistrationDto registrationDto)
	{
		try {
			Long userId = signInService.signin(registrationDto.getEmail(), registrationDto.getPassword());
			return new ResponseDto(userId, ResponseStatus.SUCCESS);
		} catch (SignInException e) {
			return new ResponseDto(e.getMessage(), ResponseStatus.FAIL);
		}
	}
	
	@RequestMapping(value = "/getimage/{imageid}")
	@ResponseBody
	public void getImageFile(@PathVariable(value="imageid") Long imageId, HttpServletResponse response  ) throws IOException{
		
		String filepath=imageUploadservice.readImagePath(imageId);
		
		File file = new File(filepath);
        response.setContentType("image/jpg");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition",
                "inline; filename=\"" + file.getName() + "\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(file));
            output = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ignore) {
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignore) {
                }
            }
        }
	}
}
