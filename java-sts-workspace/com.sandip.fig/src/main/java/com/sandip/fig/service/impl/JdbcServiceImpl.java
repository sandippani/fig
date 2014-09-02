package com.sandip.fig.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.sandip.fig.service.JdbcService;

@Service("jdbcService")
public class JdbcServiceImpl implements JdbcService {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public void test(){
		List<Map<String, Object>> value= namedParameterJdbcTemplate.queryForList("select * from test", new HashMap<String, Object>());
		System.out.println("....Map..."+value);
	}

	
	
}
