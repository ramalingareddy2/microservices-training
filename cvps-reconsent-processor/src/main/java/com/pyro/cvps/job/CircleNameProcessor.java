package com.pyro.cvps.job;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pyro.cvps.model.SubscriptionInfo;

import lombok.extern.slf4j.Slf4j;

@StepScope
@Component
@Slf4j
public class CircleNameProcessor implements ItemProcessor<SubscriptionInfo, SubscriptionInfo> {

//	@Autowired
//	private JdbcTemplate template;

	@Autowired
	@Qualifier("conf")
	private PropertiesConfiguration conf;
	
	@Autowired
	@Qualifier("circleconf")
	private PropertiesConfiguration circleNamesconfiguration;

	@Override
	public SubscriptionInfo process(SubscriptionInfo info) throws Exception {
		log.info("Before : processing msisdn {}",info);
		if (info != null) {
//			String circleName = template.queryForObject(conf.getString("FETCH_CIRCLE_QUERY"),
//					String.class, info.getMsisdn());
			String sms_template=conf.getString("SMS_TEMPLATE");
			sms_template=String.format(sms_template, info.getServiceName(),info.getValidity(),info.getCharge());
			log.info("Sending sms {0} , deails {1}",sms_template , info);
			log.info("After : processing msisdn {}",sendSms(info.getMsisdn(), sms_template));
			return info;
		} else {
			return null;
		}
		
	}

	public String sendSms(String msisdn,String message_template) {		
		try {
			if(msisdn.startsWith("91"))
			{
				if(msisdn.length()==10)
				{
					msisdn="91"+msisdn;
				}
			}
			String url = conf.getString("ESME_URL");
			url = url.replace("{msisdn}", msisdn);
			url = url.replace("{sms}", message_template.replace(" ", "%20"));
			URL obj = new URL(url);
			URLConnection conn = obj.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) conn;
			int code = httpConnection.getResponseCode();
			log.info(msisdn + " ---> Response ::: " + code+ "Prop");
		} catch (Exception e) {
			log.error(" ---> Exception in sending the circle name : " + e.getMessage());
			return "FAILED";
		}
		return "SUCCESS";
	}
}
