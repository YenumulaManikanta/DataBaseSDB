package com.miscot.springmvc.helper;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name="skey")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkeyAadhar {
{
		
	}

	@XmlValue
	public String innerText;
		@XmlAttribute(name="ci")
	 private String _ci;
		@XmlAttribute(name="ki")
	 private String _ki;
		public void setInnerText(String InnerText) {
			this.innerText = InnerText;
		}
			
		public String ci() {
			return _ci;
		}
			
		public void setCi(String Ci) {
			this._ci = Ci;
		}
			
		public String ki() {
			return _ki;
		}
			
		public void setKi(String Ki) {
			this._ki = Ki;
		}
		
}
