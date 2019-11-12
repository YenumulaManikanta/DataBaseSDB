package com.miscot.springmvc.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name="HMAC")
@XmlAccessorType(XmlAccessType.FIELD)
public class HmacAadhar {
	@XmlValue
	public String innerText;

	public String getInnerText() {
		return innerText;
	}

	public void setInnerText(String innerText) {
		this.innerText = innerText;
	}
}
