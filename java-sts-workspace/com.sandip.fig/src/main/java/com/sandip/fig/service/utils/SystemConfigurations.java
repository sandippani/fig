package com.sandip.fig.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("systemConfiguration")
public class SystemConfigurations {
	
	@Value("${image.upload.directory}")
	private String imageUploadDirectory;

	public String getImageUploadDirectory() {
		return imageUploadDirectory;
	}

	public void setImageUploadDirectory(String imageUploadDirectory) {
		this.imageUploadDirectory = imageUploadDirectory;
	}
	
	

}
