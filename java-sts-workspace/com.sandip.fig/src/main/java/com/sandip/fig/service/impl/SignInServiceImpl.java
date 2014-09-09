package com.sandip.fig.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sandip.fig.exception.SignInException;
import com.sandip.fig.service.SignInService;
@Service("signInService")
public class SignInServiceImpl implements SignInService {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Override
	public void signin(String email, String password) throws SignInException {
		// TODO Auto-generated method stub
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		String selectQuery ="Select email from registration where email=:email and password=:password";
		paramaterMap.put("email", email);
		paramaterMap.put("password", password);
		List<String> emailaddress = namedParameterJdbcTemplate.queryForList(selectQuery, paramaterMap, String.class);
		if(CollectionUtils.isEmpty(emailaddress))
		{
			throw new SignInException("Either Email or password is not matching");
		}

	}

}
