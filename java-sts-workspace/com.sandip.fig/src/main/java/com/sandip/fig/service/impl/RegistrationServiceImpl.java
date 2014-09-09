package com.sandip.fig.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sandip.fig.exception.RegistartionException;
import com.sandip.fig.rest.dtos.RegistrationDto;
import com.sandip.fig.service.RegistrationService;
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Override
	public void create(RegistrationDto registartionDto) throws RegistartionException {
		// TODO Auto-generated method stub
		Map<String, Object> parameterMap= new HashMap<String, Object>(4);
		String selectQuery = "SELECT email from registration where email = :email";
		parameterMap.put("email", registartionDto.getEmail());
        List<String> emailaddress = namedParameterJdbcTemplate.queryForList(selectQuery, parameterMap, String.class);
        if(!CollectionUtils.isEmpty(emailaddress)){
        	throw new RegistartionException("Email Id is not unique");
        }
        parameterMap.clear();
        String insertQuery=	"INSERT INTO registration (active,username,password,email) values(:active,:username,:password,:email)";
		parameterMap.put("active", registartionDto.isActive());
		parameterMap.put("username", registartionDto.getUsername());
		parameterMap.put("password", registartionDto.getPassword());
		parameterMap.put("email", registartionDto.getEmail());
		namedParameterJdbcTemplate.update(insertQuery, parameterMap);
        
	}

	@Override
	public void update(RegistrationDto registartionDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disable(Long id) {
		// TODO Auto-generated method stub

	}

}
