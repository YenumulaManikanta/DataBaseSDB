package com.miscot.springmvc.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name = "store")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoreData {

	
@XmlElement(name="password")
String password;
@XmlElement(name="aadharno")
String aadharno;
@XmlElement(name="appuser")
String appuser;
@XmlAttribute(name="ts")
String ts;
@XmlElement
public
String Status=null;


public String getPassword() {
	return password;
}
public void setPassword(String Password) {
	password = Password;
}
public String getAadharno() {
	return aadharno;
}
public void setAadharno(String Aadharno) {
	aadharno = Aadharno;
}
public String getAPPUSER() {
	return appuser;
}
public void setAPPUSER(String aPPUSER) {
	appuser = aPPUSER;
}
public String getTS() {
	return ts;
}
public void setTS(String tS) {
	ts = tS;
}

public String getStatus() {
	return Status;
}
public void setStatus(String status) {
	Status = status;
}
}

