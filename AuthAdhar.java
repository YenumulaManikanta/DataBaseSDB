package com.miscot.springmvc.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
@Component
@XmlRootElement(name="auth")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthAdhar {
	@XmlAttribute(name="txn")
	private String txn;
	@XmlAttribute(name="lk")
	private String lk;
	@XmlAttribute(name="ver")
	private String version;
	@XmlAttribute(name="userid")
	private String _userId;
	@XmlAttribute(name="ts")
	private String _ts;
	public String get_txn() {
		return txn;
	}
	public void set_txn(String _txn) {
		this.txn = _txn;
	}
	public String get_lk() {
		return lk;
	}
	public void set_lk(String _lk) {
		this.lk = _lk;
	}
	public String getVer() {
		return version;
	}
	public void setVer(String ver) {
		this.version = ver;
	}
	public String getUserId() {
		return _userId;
	}
	public void setUserId(String userId) {
		this._userId = userId;
	}
	public String getTs() {
		return _ts;
	}
	public void setTs(String ts) {
		this._ts = ts;
	}

}
