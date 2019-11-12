package com.miscot.springmvc.service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miscot.springmvc.configuration.PropertiesPath;

@Service
public class StoreRetrieveAadharServiceImpl implements StoreRetrieveAadharServiceInterface {
	@Autowired
	AadharVaultImpl adhVault;
	@Autowired
	PropertiesPath propertiesPath;
	@Override
	public String storeAadhar(String userId, String licenseKey, String version, String password, String aadharNo,
			String appUser) throws NoSuchAlgorithmException, NoSuchProviderException {
		String resp=null;
		String [] response=null ;
		try {
			resp=adhVault.storeAadhar(userId, licenseKey, version, password, aadharNo, appUser);
			response = resp.split("~");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	return response[2];	
	}
	@Override
	public String retrieveAadhar(String adhVlt_userID, String adhVlt_lk, String adhVlt_version, String adhVlt_password,
			String reqRef, String appUser, String refType) {
		String resp=null;
		String[] response=null;
		try {
			resp=adhVault.retrieveAadhar(adhVlt_userID,adhVlt_lk,adhVlt_version,adhVlt_password,reqRef,appUser,refType);
			response=resp.split("~");
		} catch (Exception e) {
			e.printStackTrace();
		}
	return response[0];	
	}

}
