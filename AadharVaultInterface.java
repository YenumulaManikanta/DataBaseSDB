package com.miscot.springmvc.service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.miscot.springmvc.helper.ADHSTRRESP;
import com.miscot.springmvc.helper.DataAadhar;
import com.miscot.springmvc.helper.RTRRESP;

public interface AadharVaultInterface {

	String storeAadhar(String userid, String licenseKey, String version, String password, String aadharNo,
			String appUser) throws NoSuchAlgorithmException, NoSuchProviderException;

	String ParseStoreReq(String data);

	DataAadhar getDataAadhar(String innerText);

	String getAadharData(String appUser, String ts, String aadharNo, String password);

	String callDbVaultService(String reqxml, String testUrl);

	ADHSTRRESP decResponse(String reqXML);

	String retrieveAadhar(String userId, String licenseKey, String version, String password, String reqRef,
			String appUser, String refType) throws NoSuchAlgorithmException, NoSuchProviderException;

	String getRetrivalDetails(String appUser, String ts, String reqRef, String password, String refType);

	RTRRESP decRetriavalResponse(String reqXML) throws Exception;

}
