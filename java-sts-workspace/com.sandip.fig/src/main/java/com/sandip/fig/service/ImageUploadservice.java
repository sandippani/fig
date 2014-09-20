package com.sandip.fig.service;

import com.sandip.fig.exception.ImageUploadException;
import com.sandip.fig.rest.dtos.ImageUploadDto;

public interface ImageUploadservice {
	
	public void save(ImageUploadDto imageUploadDto) throws ImageUploadException ;
	
	String readImagePath(Long imageId);
	
	

}