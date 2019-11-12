package com.miscot.springmvc.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name = "rtresp")
@XmlAccessorType(XmlAccessType.FIELD)
public class RTRRESP {
	@XmlAttribute
	public
	 String ts;
	@XmlElement(name="refno")
	public
	 String refno;
	@XmlElement(name="reftype")
	public
	 String reftype;
	@XmlElement(name="aadharno")
	public
	 String aadharno;
	@XmlElement(name="retcode")
	public String retcode;
	public DataResponse getDATA() {
		return DATA;
	}
	public void setDATA(DataResponse dATA) {
		DATA = dATA;
	}
	@XmlElement(name="retm")
	public String RETM;
	@XmlElement(name="data")
	public DataResponse DATA;
	
	public String getRETM() {
		return RETM;
	}
	public void setRETM(String rETM) {
		RETM = rETM;
	}
	public String getRETCODE() {
		return retcode;
	}
	public void setRETCODE(String rETCODE) {
		retcode = rETCODE;
	}
	public String getTS() {
		return ts;
	}
	public void setTS(String tS) {
		ts = tS;
	}
	public String getREFNO() {
		return refno;
	}
	public void setREFNO(String rEFNO) {
		refno = rEFNO;
	}
	public String getREFTYPE() {
		return reftype;
	}
	public void setREFTYPE(String rEFTYPE) {
		reftype = rEFTYPE;
	}
	public String getAADHARNO() {
		return aadharno;
	}
	public void setAADHARNO(String aADHARNO) {
		aadharno = aADHARNO;
	}

	
	
	
}

