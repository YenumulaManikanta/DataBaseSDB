package com.miscot.springmvc.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name="rtreq")
@XmlAccessorType(XmlAccessType.FIELD)
public class RTREQ {
	@XmlAttribute(name="ts")
	private String ts;
	@XmlElement(name="password")
	private String password;
	@XmlElement(name="reqref")
	private String reqRef;
	@XmlElement(name="reftype")
	private String refType;
	@XmlElement(name="appuser")
	private String appUser;
	//public String innerText;
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReqRef() {
		return reqRef;
	}
	public void setReqRef(String reqRef) {
		this.reqRef = reqRef;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public String getAppUser() {
		return appUser;
	}
	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}
	

}
