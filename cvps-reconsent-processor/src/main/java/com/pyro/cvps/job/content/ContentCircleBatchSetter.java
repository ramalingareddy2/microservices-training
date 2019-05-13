package com.pyro.cvps.job.content;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.pyro.cvps.model.SubscriptionInfo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ContentCircleBatchSetter implements ItemPreparedStatementSetter<SubscriptionInfo>{

	@Override
	public void setValues(SubscriptionInfo info, PreparedStatement ps) throws SQLException {
		log.info("content setting values for update {} ",info);
		
		ps.setString(1, info.getCircleName());
		ps.setLong(2, info.getScpId());
		ps.setString(3, info.getMsisdn());
		
	}

}
