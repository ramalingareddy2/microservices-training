package com.pyro.cvps.job.content;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pyro.cvps.model.SubscriptionInfo;

import lombok.extern.slf4j.Slf4j;

@StepScope
@Component
@Slf4j
public class ContentCircleNameProcessor implements ItemProcessor<SubscriptionInfo, SubscriptionInfo> {

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private PropertiesConfiguration conf;

	@Override
	public SubscriptionInfo process(SubscriptionInfo info) throws Exception {
		log.info("content processing msisdn {}",info);
		if (info != null) {
			String circleName = template.queryForObject(conf.getString("FETCH_CIRCLE_QUERY"),
					String.class, info.getMsisdn());
			if (circleName == null) {
				circleName = "NOT FOUND";
			}
			info.setCircleName(circleName);
			return info;
		} else {
			return null;
		}
	}

}
