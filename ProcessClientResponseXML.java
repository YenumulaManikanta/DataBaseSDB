package com.miscot.springmvc.service;

import org.bouncycastle.crypto.InvalidCipherTextException;

import com.miscot.springmvc.helper.ADHSTRRESP;
import com.miscot.springmvc.helper.DataAadhar;
import com.miscot.springmvc.helper.RTRRESP;
import com.miscot.springmvc.helper.STRRESP;
import com.miscot.springmvc.helper.StoreData;
import com.miscot.springmvc.helper.StoreReq;

public interface ProcessClientResponseXML {

	byte[] hexStringToByteArray(String data);

	StoreReq ParseStoreReq(String data);

	StoreData ParseDATAReq(String data);

	DataAadhar getData(byte[] SessionKey, String TS, byte[] inputData)
			throws IllegalStateException, InvalidCipherTextException, Exception;

	ADHSTRRESP decryptResponse(String data) throws Exception;

	STRRESP ParseDATARes(String data);

	RTRRESP ParseDATARTRRes(String data);

	RTRRESP decryptRetriavalResponse(String data) throws Exception;

}
