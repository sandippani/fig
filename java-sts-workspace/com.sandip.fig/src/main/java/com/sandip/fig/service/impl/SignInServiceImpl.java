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
	public Long signin(String email, String password) throws SignInException {
		// TODO Auto-generated method stub
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		String selectQuery ="Select id from registration where email=:email and password=:password";
		paramaterMap.put("email", email);
		paramaterMap.put("password", password);
		List<Long> userId = namedParameterJdbcTemplate.queryForList(selectQuery, paramaterMap, Long.class);
		if(CollectionUtils.isEmpty(userId))
		{
			throw new SignInException("Either Email or password is not matching");
		}
		return userId.get(0);

	}

}
