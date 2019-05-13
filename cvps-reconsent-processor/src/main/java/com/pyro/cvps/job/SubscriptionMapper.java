package com.pyro.cvps.job;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pyro.cvps.model.SubscriptionInfo;

@Component
public class SubscriptionMapper implements RowMapper<SubscriptionInfo>{

	@Override
	public SubscriptionInfo mapRow(ResultSet rs, int rowNum) throws SQLException {	
		System.out.println("Row Mapper*****************");
		SubscriptionInfo info = new SubscriptionInfo();
		info.setCpId(rs.getString("CP_ID"));		
		info.setScpId(rs.getLong("SCP_ID"));
		info.setSubscriptionId(rs.getString("SUBSCRIPTION_ID"));		
		info.setMsisdn(rs.getString("MSISDN"));
		info.setServiceId(rs.getString("SERVICE_ID"));
		info.setServiceName(rs.getString("SERVICE_NAME"));
		info.setCharge(rs.getString("SERVICE_CHARGE"));
		info.setValidity(rs.getString("VALIDITY"));		
		return info;
	}

}
