package com.pyro.cvps.job;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.pyro.cvps.model.SubscriptionInfo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CircleBatchSetter implements ItemPreparedStatementSetter<SubscriptionInfo>{

	@Override
	public void setValues(SubscriptionInfo info, PreparedStatement ps) throws SQLException {
		log.info("setting values for update {} ",info);
		//WHERE SCP_ID = ? AND MSISDN = ? AND SERVICE_ID=? and SUBSCRIPTION_ID=?
		ps.setLong(1, info.getScpId());
		ps.setString(2, info.getMsisdn());
		ps.setString(3, info.getServiceId());
		ps.setString(4, info.getSubscriptionId());		
		
	}

}
