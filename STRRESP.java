package com.miscot.springmvc.helper;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name = "strresp")
public class STRRESP {
	public String ts;
	 public String refno;
@XmlAttribute(name="ts")
public String getTS() {
	return ts;
}
@XmlElement
public String getREFNO() {
	return refno;
}

}

