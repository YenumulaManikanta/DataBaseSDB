package com.miscot.springmvc.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name="Data")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAadhar {
	@XmlValue
	public String innerText;
	@XmlAttribute(name="type")
	public String _type;
	
	

	public void setInnerText(String InnerText) {
		this.innerText = InnerText;
	}
	
	public String type() {
		return _type;
	}

	public void setType(String Type) {
		this._type = Type;
	}
}
