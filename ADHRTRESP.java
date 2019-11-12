package com.miscot.springmvc.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name="adhrtresp")
@XmlAccessorType(XmlAccessType.FIELD)
public class ADHRTRESP {
	  public String getRETCODE() {
		return retcode;
	}
	public void setRETCODE(String rETCODE) {
		retcode = rETCODE;
	}
	public String getRETM() {
		return retm;
	}
	public void setRETM(String rETM) {
		retm = rETM;
	}
	public AuthAdhar getAUTH() {
		return auth;
	}
	public void setAUTH(AuthAdhar aUTH) {
		auth = aUTH;
	}
	public DataResponse getDATA() {
		return data;
	}
	public void setDATA(DataResponse dATA) {
		data = dATA;
	}
	public SkeyAadhar getSKEY() {
		return skey;
	}
	public void setSKEY(SkeyAadhar sKEY) {
		skey = sKEY;
	}
	public HmacAadhar getHMAC() {
		return hmac;
	}
	public void setHMAC(HmacAadhar hMAC) {
		hmac = hMAC;
	}
	public String retcode;
	  public String retm;
	  public AuthAdhar auth;
	  public DataResponse data;
	  public SkeyAadhar skey;
	  public HmacAadhar hmac;
	  

}

