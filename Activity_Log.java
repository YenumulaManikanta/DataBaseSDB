package com.miscot.springmvc.dto;

public class Activity_Log {
	 private String  USER_ID;
	 private String REPORTNAME;
	 private String APPNAME;
	 private String DATE;
	 private String TIME;
	 private String IP_ADDRESS;
	 private String TASK_PERFORMED;
	 private String DESCPTION;
	 private String REFNO;
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getREPORTNAME() {
		return REPORTNAME;
	}
	public void setREPORTNAME(String rEPORTNAME) {
		REPORTNAME = rEPORTNAME;
	}
	public String getAPPNAME() {
		return APPNAME;
	}
	public void setAPPNAME(String aPPNAME) {
		APPNAME = aPPNAME;
	}
	
	public String getREFNO() {
		return REFNO;
	}
	public void setREFNO(String aREFNO) {
		REFNO = aREFNO;
	}
	
	public String getDATE() {
		return DATE;
	}
	public void setDATE(String dATE) {
		DATE = dATE;
	}
	public String getTIME() {
		return TIME;
	}
	public void setTIME(String tIME) {
		TIME = tIME;
	}
	public String getIP_ADDRESS() {
		return IP_ADDRESS;
	}
	public void setIP_ADDRESS(String iP_ADDRESS) {
		IP_ADDRESS = iP_ADDRESS;
	}
	public String getTASK_PERFORMED() {
		return TASK_PERFORMED;
	}
	public void setTASK_PERFORMED(String tASK_PERFORMED) {
		TASK_PERFORMED = tASK_PERFORMED;
	}
	public String getDESCPTION() {
		return DESCPTION;
	}
	public void setDESCPTION(String dESCPTION) {
		DESCPTION = dESCPTION;
	}
}
