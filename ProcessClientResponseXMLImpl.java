package com.miscot.springmvc.service;

import java.io.IOException;
import java.io.StringReader;
import java.security.interfaces.RSAPrivateKey;
import java.sql.ResultSet;
import java.util.Properties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.miscot.springmvc.configuration.PropertiesPath;
import com.miscot.springmvc.helper.ADHSTRRESP;
import com.miscot.springmvc.helper.AuthAdhar;
import com.miscot.springmvc.helper.DataAadhar;
import com.miscot.springmvc.helper.DataResponse;
import com.miscot.springmvc.helper.RTRRESP;
import com.miscot.springmvc.helper.STRRESP;
import com.miscot.springmvc.helper.SkeyAadhar;
import com.miscot.springmvc.helper.StoreData;
import com.miscot.springmvc.helper.StoreReq;


@Service
public class ProcessClientResponseXMLImpl implements  ProcessClientResponseXML{
	@Autowired
	StoreReq StoreReqobj;
	
	@Autowired
	StoreData StoreDataobj ;
	@Autowired
	DataAadhar data=new DataAadhar();//<------------------
	@Autowired
	AESCipherImpl aesCipher;
	@Autowired
	ADHSTRRESP ADHSTRRESPobj;
	
	@Autowired
	AuthAdhar auth;
	@Autowired
	 DataResponse data1;
	@Autowired
	    SkeyAadhar sk ;
	@Autowired
		 STRRESP response;
	
	@Autowired
	PropertiesPath propertiesPath;
	@Autowired
	RSAImpl RSA;
	@Autowired
	RTRRESP RTRRESPobj;
	@Autowired
	STRRESP strRespObj ;
	
	
@Override
	public  byte[] hexStringToByteArray(String data) {
		int k = 0;
		byte[] results = new byte[data.length() / 2];
		for (int i = 0; i < data.length();) {
			results[k] = (byte) (Character.digit(data.charAt(i++), 16) << 4);
			results[k] += (byte) (Character.digit(data.charAt(i++), 16));
			k++;
		}
		return results;
	}
@Override	
	public StoreReq ParseStoreReq(String data)
	{		
		try 
		{
		DocumentBuilder dbp = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(data));
	    Document doc = dbp.parse(is);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("auth");
		  for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) nNode;
					StoreReqobj.setUSERID(eElement.getAttribute("userid"));
					StoreReqobj.setLK(eElement.getAttribute("lk"));
					StoreReqobj.setTXN(eElement.getAttribute("txn"));
					StoreReqobj.setVersion(eElement.getAttribute("version"));
					StoreReqobj.setTS(eElement.getAttribute("ts"));
				}		
			}

			NodeList hintElmntLst = doc.getElementsByTagName("data");
		    if (hintElmntLst.getLength() > 0) {
          	   StoreReqobj.setDATA(doc.getElementsByTagName("data").item(0).getTextContent());
          	   StoreReqobj.setHMAC(doc.getElementsByTagName("hmac").item(0).getTextContent());
		    }else{
		    	StoreReqobj.setRETM(doc.getElementsByTagName("retm").item(0).getTextContent());
		    }
			  
			   StoreReqobj.setSKEY(doc.getElementsByTagName("skey").item(0).getTextContent());
					
			NodeList nList1 = doc.getElementsByTagName("skey");
				for (int temp = 0; temp < nList1.getLength(); temp++) {
					Node nNode = nList1.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
					{
						Element eElement = (Element) nNode;
						StoreReqobj.setCi(eElement.getAttribute("ci"));
					}
				}
			
				StoreReqobj.status="Y";
		} catch (Exception e) {
			e.printStackTrace();
			StoreReqobj.status="N";
		}
		
		return StoreReqobj;
	}
	
	@Override
	public StoreData ParseDATAReq(String data)
	{
		try 
		{
		DocumentBuilder dbp = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(data));
	    Document doc = dbp.parse(is);
		doc.getDocumentElement().normalize();
		  NodeList nList = doc.getElementsByTagName("store");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) nNode;
					StoreDataobj.setTS(eElement.getAttribute("ts"));
				}		
			}
			StoreDataobj.setPassword(doc.getElementsByTagName("password").item(0).getTextContent());
			StoreDataobj.setAadharno(doc.getElementsByTagName("aadharno").item(0).getTextContent());
			StoreDataobj.setAPPUSER(doc.getElementsByTagName("appuser").item(0).getTextContent());
			StoreDataobj.Status="Y";
		} catch (Exception e) {
			e.printStackTrace();
			StoreDataobj.Status="N";
		}
		return StoreDataobj;
	}
@Override
	public DataAadhar getData(byte[] SessionKey,String TS, byte[] inputData) throws IllegalStateException, InvalidCipherTextException, Exception
	{
		byte[] cipherTextWithTS = aesCipher.encrypt(inputData, SessionKey, TS);
	    data.innerText=aesCipher.byteArrayToHexString(cipherTextWithTS);
		return data;
	}
	
	@Override
	public ADHSTRRESP  decryptResponse(String data) throws Exception {
		ADHSTRRESPobj.retcode="00";
		StoreReqobj=ParseStoreReq(data);
		ADHSTRRESPobj.retm=StoreReqobj.getRETM();
		StoreReqobj.retm=null;
	    String decTxt=null;
	    byte[] sessionKey=null;
	    String CertPath=propertiesPath.getCertpath();
		if (StoreReqobj.getStatus().equalsIgnoreCase("Y"))
		{
				 auth= new AuthAdhar();
				 auth.set_lk(StoreReqobj.lk);
				 auth.set_txn(StoreReqobj.txn);
				 auth.setUserId(StoreReqobj.userid);
				 auth.setVer(StoreReqobj.version);
				 auth.setTs(StoreReqobj.ts);    
				 ADHSTRRESPobj.auth=auth;
				try
		    	{
		    		RSAPrivateKey privateKey=RSAImpl.getPrivateKeyAadharVault(CertPath); // for decrypt the data request coming from client this is dbvault pvt key
		    		sessionKey	=RSAImpl.decrypt1(StoreReqobj.skey, privateKey);
		    		sk.innerText=new String(sessionKey);
				}catch (Exception e) {
					e.printStackTrace();
					ADHSTRRESPobj.retcode="11";
					ADHSTRRESPobj.retm="FAIL";
				} 	
		    	if(StoreReqobj.data != null){
		    	try
		    	{
		    		 decTxt=aesCipher.getActualDecryptedData(Base64.decodeBase64(StoreReqobj.data),sessionKey,Base64.decodeBase64(StoreReqobj.hmac));
					 //String decryptedTextACtual= new String(decTxt);//
		    	}
		    	catch (Exception e) {
					e.printStackTrace();
					ADHSTRRESPobj.retcode="12";
					ADHSTRRESPobj.retm="FAIL";
				}
		    	if (ADHSTRRESPobj.retcode.equalsIgnoreCase("00"))
				{
					response =  ParseDATARes(decTxt);
				    data1.strresp=response;
				}
		    	ADHSTRRESPobj.data=data1;
				}
		}
		else{
			ADHSTRRESPobj.retcode="100";
			ADHSTRRESPobj.retm="FAIL";
		}
		return ADHSTRRESPobj;
	}

	@Override
	public STRRESP ParseDATARes(String data) {
		
	
		
		try 
		{
		DocumentBuilder dbp = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(data));
	    Document doc = dbp.parse(is);
		doc.getDocumentElement().normalize();
		  NodeList nList = doc.getElementsByTagName("strresp");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) nNode;
					
					strRespObj.ts=eElement.getAttribute("ts");
				
				}		
			}

			strRespObj.refno=(doc.getElementsByTagName("refno").item(0).getTextContent());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRespObj;
	}


	@Override
	public RTRRESP ParseDATARTRRes(String data) {
		try 
		{
		DocumentBuilder dbp = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(data));
	    Document doc = dbp.parse(is);
		doc.getDocumentElement().normalize();
		  NodeList nList = doc.getElementsByTagName("rtresp");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) nNode;
					
					RTRRESPobj.ts=eElement.getAttribute("ts");
				
				}		
			}
			
			RTRRESPobj.reftype=(doc.getElementsByTagName("reftype").item(0).getTextContent());
			//System.out.println("RTRRESPobj.REFTYPE"+RTRRESPobj.REFTYPE);	
			if(RTRRESPobj.reftype.equalsIgnoreCase("A")){
				RTRRESPobj.aadharno=(doc.getElementsByTagName("aadharno").item(0).getTextContent());
			}else if(RTRRESPobj.reftype.equalsIgnoreCase("R")){
				RTRRESPobj.refno=(doc.getElementsByTagName("refno").item(0).getTextContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RTRRESPobj;
	}

		@Override
		public RTRRESP  decryptRetriavalResponse(String data) throws Exception {
			RTRRESP RTRRESP=new RTRRESP();
			RTRRESP.retcode="00";
			StoreReq StoreReqobj=ParseStoreReq(data);
			RTRRESP.RETM=StoreReqobj.getRETM(); //for show msg in output xml
			StoreReqobj.retm=null; //to restrict tag in output xml again
			AuthAdhar auth=null;
		    String decTxt=null;
		    byte[] sessionKey=null;
		    DataResponse data1 = new DataResponse();
			SkeyAadhar sk =new SkeyAadhar();
			RTRRESP response = new RTRRESP();
			String CertPath=propertiesPath.getCertpath();
			if (StoreReqobj.getStatus().equalsIgnoreCase("Y"))
			{
				auth= new AuthAdhar();
				auth.set_lk(StoreReqobj.lk);
				auth.set_txn(StoreReqobj.txn);
				auth.setUserId(StoreReqobj.userid);
				auth.setVer(StoreReqobj.version);
				auth.setTs(StoreReqobj.ts);
				try
			    	{
						RSAPrivateKey privateKey=RSA.getPrivateKeyRetirval(CertPath); // for decrypt the data request coming from client this is dbvault pvt key
			    		sessionKey	=RSAImpl.decrypt1(StoreReqobj.skey, privateKey);
			    		sk.innerText=new String(sessionKey);
			    	}catch (Exception e) {
						e.printStackTrace();
						RTRRESP.retcode="11";
						RTRRESP.RETM="FAIL";
					} 	
			    	if(StoreReqobj.data != null){
			    	try
			    	{
			    		 decTxt=aesCipher.getActualDecryptedData(Base64.decodeBase64(StoreReqobj.data),sessionKey,Base64.decodeBase64(StoreReqobj.hmac));
			    	}
			    	catch (Exception e) {
						e.printStackTrace();
						RTRRESP.retcode="12";
						RTRRESP.RETM="FAIL";
					}
			    	
			    	if (RTRRESP.retcode.equalsIgnoreCase("00"))
					{
						response =  ParseDATARTRRes(decTxt);
					    data1.rtrresp=response;
					    RTRRESP.DATA=data1;
					 }
			    	RTRRESP.DATA=data1;
			    }
			}
			else{
				RTRRESP.retcode="100";
				RTRRESP.RETM="FAIL";
			}
			return RTRRESP;
		}
}