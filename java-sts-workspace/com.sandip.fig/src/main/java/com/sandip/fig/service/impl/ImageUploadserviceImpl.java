package com.sandip.fig.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.sandip.fig.exception.ImageUploadException;
import com.sandip.fig.rest.dtos.ImageUploadDto;
import com.sandip.fig.service.ImageUploadservice;
@Service("imageUploadservice")
public class ImageUploadserviceImpl implements ImageUploadservice {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Override
	public void save(ImageUploadDto imageUploadDto) throws ImageUploadException {
		Map<String, Object> parameterMap = new HashMap<String, Object>(4);
		String insertQuerey = "Insert into upload_images(filepath, uploaded_by,latitude,longitude,comments) values(:filepath,:uploaded_by,:latitude,:longitude,:comments)";
		parameterMap.put("filepath", imageUploadDto.getFilepath());
		parameterMap.put("uploaded_by", imageUploadDto.getCreatedBy());
		parameterMap.put("latitude", imageUploadDto.getLatitude());
		parameterMap.put("longitude", imageUploadDto.getLongitude());
		parameterMap.put("comments", imageUploadDto.getComments());
		if(imageUploadDto.getCreatedBy()== null)
		{
			throw new ImageUploadException("Userid not valid");
		}
		namedParameterJdbcTemplate.update(insertQuerey, parameterMap);
	}
	
	@Override
	public String readImagePath(Long imageId) {
		String getImage="Select filepath from upload_images where id= :id";
		Map<String, Long> param=new HashMap<String, Long>();
		param.put("id", imageId);
		String filePath=(String)namedParameterJdbcTemplate.queryForObject (getImage, param, String.class);
		
		return filePath;
	}

}
