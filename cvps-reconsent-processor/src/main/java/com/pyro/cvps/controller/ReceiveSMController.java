package com.pyro.cvps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pyro.cvps.service.ReceiveSMService;

@RestController
public class ReceiveSMController {
	@Autowired
	ReceiveSMService service;
	
	//request_type=sms_msg&msisdn=&message=&shortcode=
	//@GetMapping("/receivesm")
	@GetMapping("/cvps")
	public String handleDeliverSM(@RequestParam("msisdn")String msisdn,@RequestParam("message")String message,@RequestParam("shortcode")String shortcode){
		String response="0:SUCCESS";		
		if("yes".equalsIgnoreCase(message)){
			service.handleRequest(msisdn, message,shortcode);
		}
		return response;
	}
}
