package com.miscot.springmvc.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name="data")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataResponse {
	 String innerText;
	 @XmlElement(name="strresp")
	public
	 STRRESP strresp;
	 @XmlElement(name="rtrresp")
	public
	 RTRRESP rtrresp;

public STRRESP getStrresp() {
	return strresp;
}

public void setStrresp(STRRESP strresp) {
	this.strresp = strresp;
}
}
