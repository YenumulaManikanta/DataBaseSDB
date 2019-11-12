package com.miscot.springmvc.service;

import com.miscot.springmvc.helper.HmacAadhar;
import com.miscot.springmvc.helper.RTREQ;
import com.miscot.springmvc.helper.SkeyAadhar;
import com.miscot.springmvc.helper.Store;

public interface EncryptionInterface {

	

	String EncryptRetrivalAadharVault(String ts, String aadharData, String encryptioncer, SkeyAadhar skey, RTREQ store,
			HmacAadhar hmac, byte[] sessionK, String Password);

	void EncryptAadharVault(String ts, String aadharData, String encryptioncer, SkeyAadhar skey, Store store,
			HmacAadhar hmac, byte[] sessionK, String Password);
}
