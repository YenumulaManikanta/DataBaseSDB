package com.miscot.springmvc.bean;

public class LoginBean {
	public LoginBean(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	private String userName;
	private String password;
	private String operation;
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	//private String operation;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginBean [userName=" + userName + ", password=" + password + "]";
	}
	public LoginBean() {
		super();
		// TODO Auto-generated constructor stub
	}
}
