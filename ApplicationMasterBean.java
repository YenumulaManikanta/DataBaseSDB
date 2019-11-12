package com.miscot.springmvc.bean;

public class ApplicationMasterBean {
	 public String getTxtAppName() {
		return txtAppName;
	}
	public void setTxtAppName(String txtAppName) {
		this.txtAppName = txtAppName;
	}
	public String getTxtPassword() {
		return txtPassword;
	}
	public void setTxtPassword(String txtPassword) {
		this.txtPassword = txtPassword;
	}
	public String getTxtIPadd() {
		return txtIPadd;
	}
	public void setTxtIPadd(String txtIPadd) {
		this.txtIPadd = txtIPadd;
	}
	public String getTxtUserID() {
		return txtUserID;
	}
	public void setTxtUserID(String txtUserID) {
		this.txtUserID = txtUserID;
	}
	private String txtAppName;
	  private String txtPassword;
	  private String txtIPadd;
	  private String txtUserID;
	  private String txtUserStatus;
	public String getTxtUserStatus() {
		return txtUserStatus;
	}
	public void setTxtUserStatus(String txtUserStatus) {
		this.txtUserStatus = txtUserStatus;
	}
}
