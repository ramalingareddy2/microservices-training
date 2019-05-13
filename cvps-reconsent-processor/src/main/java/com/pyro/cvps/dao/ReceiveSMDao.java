package com.pyro.cvps.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReceiveSMDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean checkResentStatusForMsisdn(String msisdn) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
