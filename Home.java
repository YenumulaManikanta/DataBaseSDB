package com.miscot.springmvc.helper;

import org.springframework.stereotype.Component;

@Component
public class Home {
	private String FIELD_NAME;
	private String TOTAL;
	private String res1;
	private String res2;
	public String getRes1() {
		return res1;
	}
	public void setRes1(String res1) {
		this.res1 = res1;
	}
	public String getRes2() {
		return res2;
	}
	public void setRes2(String res2) {
		this.res2 = res2;
	}
	public String getFIELD_NAME(){
		return FIELD_NAME;
	}
	public void setFIELD_NAME(String FIELDNAME){
		FIELD_NAME = FIELDNAME;
	}
	public String getTOTAL(){
		return TOTAL;
	}
	public void setTOTAL(String total){
		TOTAL = total;
	}
	
}
