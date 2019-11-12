package com.miscot.springmvc.service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public interface StoreRetrieveAadharServiceInterface {
	public String storeAadhar(String userId,String licenseKey,String version,String password,String aadharNo,String appUser) throws NoSuchAlgorithmException, NoSuchProviderException;

	String retrieveAadhar(String adhVlt_userID, String adhVlt_lk, String adhVlt_version, String adhVlt_password,
			String reqRef, String appUser, String refType);
}
