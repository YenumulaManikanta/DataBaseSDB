package com.miscot.springmvc.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name="adhstreq")
@XmlAccessorType(XmlAccessType.FIELD)
public class ADHSTREQ {
	public AuthAdhar auth;
    public SkeyAadhar skey;
    public DataAadhar data;
    public HmacAadhar hmac;
	 public AuthAdhar getAuth() {
		return auth;
	}
	public void setAuth(AuthAdhar Auth) {
		auth = Auth;
	}
	public SkeyAadhar getSkey() {
		return skey;
	}
	public void setSkey(SkeyAadhar Skey) {
		skey = Skey;
	}
	public DataAadhar getData() {
		return data;
	}
	public void setData(DataAadhar Data) {
		data = Data;
	}
	public HmacAadhar getHmac() {
		return hmac;
	}
	public void setHmac(HmacAadhar Hmac) {
		hmac = Hmac;
	}


}
