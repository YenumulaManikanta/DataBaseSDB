package com.miscot.springmvc.helper;

import org.springframework.stereotype.Component;

@Component
public class StoreReq {
	
	public String userid=null;
	 public String lk=null;
	 public String txn=null;
	 public String version=null;
	 public String ts=null;
	 public  String data=null;
	 public String skey=null;
	 public String retm=null;
	 
	public String getUSERID() {
		return userid;
	}
	
	public void setUSERID(String uSERID) {
		userid = uSERID;
	}
	public String getLK() {
		return lk;
	}
	public void setLK(String lK) {
		lk = lK;
	}
	public String getTXN() {
		return txn;
	}
	public void setTXN(String tXN) {
		txn = tXN;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTS() {
		return ts;
	}
	public void setTS(String tS) {
		ts = tS;
	}
	public String getDATA() {
		return data;
	}
	public void setDATA(String dATA) {
		data = dATA;
	}
	public String getSKEY() {
		return skey;
	}
	public void setSKEY(String sKEY) {
		skey = sKEY;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getHMAC() {
		return hmac;
	}
	public void setHMAC(String hMAC) {
		hmac = hMAC;
	}

	 public String getStatus() {
		return status;
	}

	public void setStatus(String Status) {
		status = Status;
	}

	public String getRETM() {
		return retm;
	}

	public void setRETM(String rETM) {
		retm = rETM;
	}

	public String ci=null;
	public String hmac=null;
	public String status=null;
	@Override
	public String toString() {
		return "StoreReq [userid=" + userid + ", lk=" + lk + ", txn=" + txn
				+ ", version=" + version + ", ts=" + ts + ", data=" + data
				+ ", skey=" + skey + ", retm=" + retm + ", ci=" + ci
				+ ", hmac=" + hmac + ", status=" + status + "]";
	}
	
	
	
	
	
}
