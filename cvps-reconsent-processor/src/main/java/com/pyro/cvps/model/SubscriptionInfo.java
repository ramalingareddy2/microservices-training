package com.pyro.cvps.model;

import java.io.Serializable;

public class SubscriptionInfo implements Serializable {

	/**
	 * @author aneeshreddy
	 */
	private static final long serialVersionUID = 1L;
	//select sd.CP_ID,sd.SCP_ID,sd.SUBSCRIPTION_ID,sd.MSISDN,sm.SERVICE_ID,sm.SERVICE_NAME,sm.service_charge,sm.VALIDITY from tbl_subscription_details sd , tbl_service_master sm where sd.service_id=sm.service_id and FIRST_SUBSCRIBED_DATE between TO_DATE('01-04-2018','dd-mm-yyyy') and TO_DATE('30-04-2018','dd-mm-yyyy') and sm.SERVICE_CHANNAL_ID=3
	private String subscriptionId;
	private String msisdn;
	private String serviceId;
	private String serviceName;
	private String charge;
	private String validity;	
	private long scpId;
	private String cpId;	
	
	

	public SubscriptionInfo(String subscriptionId, String msisdn, String serviceId, String serviceName, String charge,
			String validity, long scpId, String cpId) {
		super();
		this.subscriptionId = subscriptionId;
		this.msisdn = msisdn;
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.charge = charge;
		this.validity = validity;
		this.scpId = scpId;
		this.cpId = cpId;
	}

	public SubscriptionInfo() {
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public long getScpId() {
		return scpId;
	}

	public void setScpId(long scpId) {
		this.scpId = scpId;
	}



	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SubscriptionInfo [subscriptionId=" + subscriptionId + ", msisdn=" + msisdn + ", serviceId=" + serviceId
				+ ", serviceName=" + serviceName + ", charge=" + charge + ", validity=" + validity + ", scpId=" + scpId
				+ ", cpId=" + cpId + "]";
	}

	

}
