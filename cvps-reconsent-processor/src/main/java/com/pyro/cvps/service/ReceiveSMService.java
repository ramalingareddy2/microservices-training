package com.pyro.cvps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyro.cvps.dao.ReceiveSMDao;

@Service
public class ReceiveSMService {
	@Autowired
	private ReceiveSMDao dao;
	
	public String handleRequest(String msisdn,String message,String shortcode){
		//Write the logic to check if the requested msisdn is in Subscriptiondetails and sentsm flag should be enabled
		//Then update the consent status
		if(dao.checkResentStatusForMsisdn(msisdn)){
			
		}
		return "0:SUCCESS";
	}
}
