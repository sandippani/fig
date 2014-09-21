package com.sandip.fig.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.sandip.fig.exception.ImageUploadException;
import com.sandip.fig.rest.dtos.ImageResponseDto;
import com.sandip.fig.rest.dtos.ImageResponseListDto;
import com.sandip.fig.rest.dtos.ImageUploadDto;
import com.sandip.fig.service.ImageUploadservice;
import com.sandip.fig.service.utils.FigSystemUtils;

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
		if (imageUploadDto.getCreatedBy() == null) {
			throw new ImageUploadException("Userid not valid");
		}
		namedParameterJdbcTemplate.update(insertQuerey, parameterMap);
	}

	@Override
	public String readImagePath(Long imageId) {
		String getImage = "Select filepath from upload_images where id= :id";
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("id", imageId);
		String filePath = (String) namedParameterJdbcTemplate.queryForObject(
				getImage, param, String.class);

		return filePath;
	}

	@Override
	public Page<ImageResponseDto> listImages(int page, int pageSize) {
		String countQuery = "select count(*) from upload_images";
		String sqlQuery = "select id,comments from upload_images";
		String finalSqlQuery = sqlQuery
				+ FigSystemUtils.getLimit(page, pageSize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		long total = namedParameterJdbcTemplate.queryForObject(countQuery,
				paramMap, Long.class);
		List<ImageResponseDto> imageResponseDtos = this.namedParameterJdbcTemplate
				.query(finalSqlQuery, paramMap,
						new RowMapper<ImageResponseDto>() {
							@Override
							public ImageResponseDto mapRow(ResultSet rs,
									int arg1) throws SQLException {
								ImageResponseDto imageResponseDto = new ImageResponseDto();
								imageResponseDto.setComment(rs.getString(2));
								imageResponseDto.setImageUrl("getimage/"
										.concat(rs.getString(1)));
								return imageResponseDto;
							}
						});
		if(imageResponseDtos == null){
			imageResponseDtos = new ArrayList<ImageResponseDto>();
		}
		PageRequest pageRequest = new PageRequest(page - 1, pageSize);
		Page<ImageResponseDto> finalResult = new PageImpl<ImageResponseDto>(
				imageResponseDtos, pageRequest, total);
		return finalResult;

	}

}
