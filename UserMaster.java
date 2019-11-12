package com.miscot.springmvc.dto;

import org.springframework.stereotype.Component;

@Component
public class UserMaster {
	private String USER_ID;
	private String USER_NAME;
	private String USER_STATUS;
	private String USER_ROLE;
	private String USER_ROLE_DESC;
	
	
	private String CREATED_TIME;
	private String CREATED_USER;
	private String LAST_LOGIN_DATE;
	private String LAST_LOGIN_IP;
	private String LOGIN_STATUS;
	private String LOGIN_RESET;
	private String MODIFIED_USER;
	private String MODIFIED_TIME;
	private String USER_VERIFY;
	private String USER_HISTORY;
	private String ISVERIFIED;
	private String EXPIRY_DATE;
	private String User_status_desc;
	private String P_F_NNUMBER;
	private String SOL_ID;
	private String PASSWORD;
	private String PASSWORD_RESET;
	private String ISUNLOCKED;
	
	private String LOGIN_DATE;
	private String IP_ADDRESS;
	private String STATUS_DES;
	
	private String USER_ID_ACTED_ON;
	private String ACTION_DATE;
	private String ACTION_DESCRIPTION;
	private String OPERATION;
	public String getUSER_ID_ACTED_ON() {
		return USER_ID_ACTED_ON;
	}
	public void setUSER_ID_ACTED_ON(String uSER_ID_ACTED_ON) {
		USER_ID_ACTED_ON = uSER_ID_ACTED_ON;
	}
	public String getACTION_DATE() {
		return ACTION_DATE;
	}
	public void setACTION_DATE(String aCTION_DATE) {
		ACTION_DATE = aCTION_DATE;
	}
	public String getACTION_DESCRIPTION() {
		return ACTION_DESCRIPTION;
	}
	public void setACTION_DESCRIPTION(String aCTION_DESCRIPTION) {
		ACTION_DESCRIPTION = aCTION_DESCRIPTION;
	}
	public String getOPERATION() {
		return OPERATION;
	}
	public void setOPERATION(String oPERATION) {
		OPERATION = oPERATION;
	}
	public String getLOGIN_DATE() {
		return LOGIN_DATE;
	}
	public void setLOGIN_DATE(String lOGIN_DATE) {
		LOGIN_DATE = lOGIN_DATE;
	}
	public String getIP_ADDRESS() {
		return IP_ADDRESS;
	}
	public void setIP_ADDRESS(String iP_ADDRESS) {
		IP_ADDRESS = iP_ADDRESS;
	}
	public String getSTATUS_DES() {
		return STATUS_DES;
	}
	public void setSTATUS_DES(String sTATUS_DES) {
		STATUS_DES = sTATUS_DES;
	}
	public String getISUNLOCKED() {
		return ISUNLOCKED;
	}
	public void setISUNLOCKED(String iSUNLOCKED) {
		ISUNLOCKED = iSUNLOCKED;
	}
	public String getISACTIVE() {
		return ISACTIVE;
	}
	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}
	private String ISACTIVE;
	
	public String getP_F_NNUMBER() {
		return P_F_NNUMBER;
	}
	public String getPASSWORD_RESET() {
		return PASSWORD_RESET;
	}
	public void setPASSWORD_RESET(String pASSWORD_RESET) {
		PASSWORD_RESET = pASSWORD_RESET;
	}
	public void setP_F_NNUMBER(String p_F_NNUMBER) {
		P_F_NNUMBER = p_F_NNUMBER;
	}
	public String getSOL_ID() {
		return SOL_ID;
	}
	public void setSOL_ID(String sOL_ID) {
		SOL_ID = sOL_ID;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
		
	
	
	public String getUSER_ROLE_DESC() {
		return USER_ROLE_DESC;
	}
	public void setUSER_ROLE_DESC(String uSER_ROLE_DESC) {
		USER_ROLE_DESC = uSER_ROLE_DESC;
	}
	
	public String getUser_status_desc() {
		return User_status_desc;
	}
	public void setUser_status_desc(String userStatusDesc) {
		User_status_desc = userStatusDesc;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSERID) {
		USER_ID = uSERID;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSERNAME) {
		USER_NAME = uSERNAME;
	}
	public String getUSER_STATUS() {
		return USER_STATUS;
	}
	public void setUSER_STATUS(String uSERSTATUS) {
		USER_STATUS = uSERSTATUS;
	}
	public String getUSER_ROLE() {
		return USER_ROLE;
	}
	public void setUSER_ROLE(String uSERROLE) {
		USER_ROLE = uSERROLE;
	}
	public String getCREATED_TIME() {
		return CREATED_TIME;
	}
	public void setCREATED_TIME(String cREATEDTIME) {
		CREATED_TIME = cREATEDTIME;
	}
	public String getCREATED_USER() {
		return CREATED_USER;
	}
	public void setCREATED_USER(String cREATEDUSER) {
		CREATED_USER = cREATEDUSER;
	}
	public String getLAST_LOGIN_DATE() {
		return LAST_LOGIN_DATE;
	}
	public void setLAST_LOGIN_DATE(String lASTLOGINDATE) {
		LAST_LOGIN_DATE = lASTLOGINDATE;
	}
	public String getLAST_LOGIN_IP() {
		return LAST_LOGIN_IP;
	}
	public void setLAST_LOGIN_IP(String lASTLOGINIP) {
		LAST_LOGIN_IP = lASTLOGINIP;
	}
	public String getLOGIN_STATUS() {
		return LOGIN_STATUS;
	}
	public void setLOGIN_STATUS(String LOGINSTATUS) {
		LOGIN_STATUS = LOGINSTATUS;
		
	}
	public String getLOGIN_RESET() {
		return LOGIN_RESET;
	}
	public void setLOGIN_RESET(String LOGINRESET) {
		LOGIN_RESET = LOGINRESET;
		
	}
	public String getMODIFIED_USER() {
		return MODIFIED_USER;
	}
	public void setMODIFIED_USER(String mODIFEDUSER) {
		MODIFIED_USER = mODIFEDUSER;
	}
	public String getMODIFIED_TIME() {
		return MODIFIED_TIME;
	}
	public void setMODIFIED_TIME(String mODIFIEDTIME) {
		MODIFIED_TIME = mODIFIEDTIME;
	}	
	public String getUSER_VERIFY() {
		return USER_VERIFY;
	}
	public void setUSER_VERIFY(String uSER_VERIFY) {
		USER_VERIFY = uSER_VERIFY;
	}
	public String getUSER_HISTORY() {
		return USER_HISTORY;
	}
	public void setUSER_HISTORY(String uSERHISTORY) {
		USER_HISTORY = uSERHISTORY;
	}
	public String getISVERIFIED() {
		return ISVERIFIED;
	}
	public void setISVERIFIED(String iSVERIFIED) {
		ISVERIFIED = iSVERIFIED;
	}	
	
	public String getEXPIRY_DATE() {
		return EXPIRY_DATE;
	}
	public void setEXPIRY_DATE(String eXPIRYDATE) {
		EXPIRY_DATE = eXPIRYDATE;
	}
	

}
