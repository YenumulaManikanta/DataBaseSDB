package com.miscot.springmvc.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


import com.miscot.springmvc.configuration.PropertiesPath;
import com.miscot.springmvc.helper.ADHRTREQ;
import com.miscot.springmvc.helper.ADHSTREQ;
import com.miscot.springmvc.helper.ADHSTRRESP;
import com.miscot.springmvc.helper.AuthAdhar;
import com.miscot.springmvc.helper.DataAadhar;
import com.miscot.springmvc.helper.HmacAadhar;
import com.miscot.springmvc.helper.RTREQ;
import com.miscot.springmvc.helper.RTRRESP;
import com.miscot.springmvc.helper.SkeyAadhar;
import com.miscot.springmvc.helper.Store;
import com.miscot.springmvc.helper.StoreReq;
import com.miscot.springmvc.repository.GetQueryImpl;
import com.miscot.springmvc.service.AESCipherImpl;
@Service
public class AadharVaultImpl implements AadharVaultInterface{
	
	@Autowired
	PropertiesPath propertiesPath;
	@Autowired
	AESCipherImpl aesCipher;
	@Autowired
	EncryptionImpl encryption;
	
		@Autowired
		SkeyAadhar skey;
		@Autowired
	    Store store;
		@Autowired
	    HmacAadhar hmac ;
		@Autowired
	    AuthAdhar auth ;
		@Autowired
	    ADHSTREQ adth;
		@Autowired
	    ADHRTREQ adrth;
		@Autowired
		ADHSTRRESP res;
		@Autowired
		DataAadhar data;
		@Autowired
		GetQueryImpl getQuery;
		@Autowired
		StoreReq StoreReqobj;
		@Autowired
		RTREQ retReq;
		@Autowired
		ProcessClientResponseXMLImpl prx;
		
		@Autowired
		  RTRRESP rtrRes;
		@Autowired
		ADHSTRRESP ADHSTRRESPobj;
		@Autowired
     	RTRRESP ADHRTRESPPobj;
	
	

public AadharVaultImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
@Override
	public String storeAadhar(String userid, String licenseKey, String version, String password, String aadharNo, String appUser) throws NoSuchAlgorithmException, NoSuchProviderException {
		//System.out.println("Inside storeAadhar========");

		 String miscotEnc=propertiesPath.getStoreAadhar();
		 //System.out.println("miscotEnc======="+miscotEnc);
		byte[] sessionK = aesCipher.generateSessionKey();
	    String txn=null;
	    String xmlString = null;
	    String uuid=null;
	  
	    try
	    {
	    	 SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss"); 
		  		String ts = df.format(new Date());
	    	//System.out.println("Inside Auth getAuthXmlOtp try========"+ts+"======"+propertiesPath.getCertpath()+"========="+miscotEnc);
	    	//System.out.println("XmlString0==========="+getAadharData(appUser,ts,aadharNo,password));
	    	
	       
	  		
	    	encryption.EncryptAadharVault(ts,getAadharData(appUser,ts,aadharNo,password),propertiesPath.getAadharPublicCertificate() , skey, store, hmac, sessionK, "public");
	    	//System.out.println("InnerTest===="+store.innerText);
	    	 data = getDataAadhar(store.innerText);
	    	 
	      		try {
	      			
	      			txn = getQuery.getValuefromDual();
	      			/*System.out.println("Aadhar Txn ===="+txn);
	      			System.out.println("hmac===="+hmac.innerText);
	      			System.out.println("skey===="+skey.ci());*/
	      			
	      			//SSLCertificateDemo.disableSslVerification();
	      		}
	        catch (Exception e) {
				// TODO Auto-generated catch block
	        	
				e.printStackTrace();
				}
	      		//System.out.println("After try=======");
	    	auth.set_lk(licenseKey);
	    	auth.set_txn(txn);
	    	auth.setTs(ts);
	    	auth.setUserId(userid);
	    	auth.setVer(version);
	    	//System.out.println("Auth is"+auth);
	    	adth.auth=auth;
	    	adth.data=data;
	    	adth.hmac=hmac;
	    	adth.skey=skey;
	    	//System.out.println("adth is"+adth.skey);
	        StringWriter sw = new StringWriter();
	    			JAXBContext context = JAXBContext.newInstance(ADHSTREQ.class);
	    	        Marshaller m = context.createMarshaller();
	    	        //for pretty-print XML in JAXB
	    	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    	        m.marshal(adth, sw);
	    	   		String xmlString1 = sw.toString();
	    	   		//System.out.println("xmlString1"+xmlString1);
	    	   	 String output= callDbVaultService(xmlString1, miscotEnc);
	    	   	  String retCode=ParseStoreReq(output);
	    	   		//System.out.println("retCode"+retCode);
	    		     if(retCode.equals("00"))
	    		     {
	    		    	 res=decResponse(output);
		    		     //System.out.println("Decrypted Response is"+res.data.strresp.refno);
		    		     uuid=res.data.strresp.refno+"~"+"00~Aadhar Successfully saved with reference no: "+res.data.strresp.refno; 
	    		     }
	    		     else if(retCode.equals("01"))
	    		     {
	    		    	 res=decResponse(output);
		    		     //System.out.println("Decrypted Response is"+res.data.strresp.refno);
		    		     uuid=res.data.strresp.refno+"~"+"01~Aadhar No Already Exist in Vault with reference no: "+res.data.strresp.refno; 
	    		     }
	    		     else
	    		     {
	    		    	
		    		     //System.out.println("Inside else Aadhar Vault Error Occured");
		    		     uuid=" ~"+retCode+"~"+getErrorMessage(retCode);
	    		    	 
	    		     }

	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	System.out.println("Exception Occured"+e.getMessage());
	    }
		return uuid;
		// TODO Auto-generated method stub
		
	}
@Override
	public String ParseStoreReq(String data)
	{
		String retCode=null;
		try 
		{
		DocumentBuilder dbp = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(data));
	    Document doc = dbp.parse(is);
		doc.getDocumentElement().normalize();
		retCode=doc.getElementsByTagName("retcode").item(0).getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retCode;
	}
@Override
	public DataAadhar getDataAadhar(String innerText) {
		try
	    {
	        data.setType(propertiesPath.getDatatype()); //Convert.ToString(ConfigurationManager.AppSettings["datatype"]);
	        data.setInnerText(innerText);//innerText = ;
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return data;
	}
@Override
	public String getAadharData(String appUser,String ts, String aadharNo, String password) {
		String xmlString = null;
	    try
	    {
	       store.setTs(ts);
	       store.setAadharNo(aadharNo);
	       store.setAppUser(appUser);
	       store.setPassword(password);
	       StringWriter sw = new StringWriter();
	       JAXBContext.newInstance(Store.class).createMarshaller().marshal(store, sw);
	       xmlString = sw.toString();
	   	   xmlString=xmlString.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>","");
	   	}
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	   return xmlString;
	}
@Override	
public  String  callDbVaultService(String reqxml,String testUrl){
		String output ="";
		String outputData="";
		BufferedReader br=null;
		  try {
				URL url = new URL(testUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/xml");
				String input = reqxml;
				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
				}
				 br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
				 while ((output = br.readLine()) != null) {
					outputData+=output;
				};
				conn.disconnect();
			  } catch (MalformedURLException e) {
				e.printStackTrace();
			  } catch (IOException e) {
				e.printStackTrace();
			 }
		return outputData;
	}
@Override
	public ADHSTRRESP decResponse(String reqXML) {
		try {
			ADHSTRRESPobj=prx.decryptResponse(reqXML);
	    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    	return ADHSTRRESPobj;
	}
	private String getErrorMessage(String retCode) {
		String errorMsg=null;
		switch(retCode) {
		   case "02" :
			   errorMsg="Already Exists";
		      break; // optional
		   
		   case "03" :
		      // Statements
			   errorMsg="Format Error";
		      break; // optional
		   case "04" :
			      // Statements
				   errorMsg="Invalid User ID";
			      break; // optional
		   case "05" :
			      // Statements
				   errorMsg="Invalid License Key";
			      break; // optional
		   case "06" :
			      // Statements
				   errorMsg="Invalid key info in digital signature (this means that certificate used for signing the request is not valid – \r\n" + 
				   		"  it is either expired, or does not belong to the SWIFTDBVAULT or is not created by a CA) ";
			      break; // optional
		   case "07" :
			      // Statements
				   errorMsg="Digital signature verification failed";
			      break; // optional
		   case "08" :
			      // Statements
				   errorMsg="Invalid “DATA” XML format";
			      break; // optional
		   case "09" :
			      // Statements
				   errorMsg="Invalid IP Address";
			      break; // optional
		   case "11" :
			      // Statements
				   errorMsg="Session key Decryption Failed";
			      break; // optional
		   case "12" :
			      // Statements
				   errorMsg="DATA Decryption Failed";
			      break; // optional
		   case "13" :
			      // Statements
				   errorMsg="Password Does not Match";
			      break; // optional
		   case "99" :
			      // Statements
				   errorMsg="Unknown Error";
			      break; // optional
		   case "98" :
			      // Statements
				   errorMsg="Technical Failure";
			      break; // optional
		   case "10" :
			      // Statements
				   errorMsg="Invalid IP Address";
			      break; // optional
		   case "16" :
			      // Statements
				   errorMsg="Aadhar No or Refrence No Does not Exist";
			      break; // optional
		   // You can have any number of case statements.
		   default : // Optional
		      // Statements
		}
		return errorMsg;
	}
@Override
	public String retrieveAadhar(String userId, String licenseKey, String version, String password, String reqRef,
			String appUser, String refType) throws NoSuchAlgorithmException, NoSuchProviderException {
			String retrivalUrl=propertiesPath.getRetrieveAadhar();
			byte[] sessionK = aesCipher.generateSessionKey();
		    String txn=null;
		    String uuid=null;
		    String output=null;
		    SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss"); 
			String ts = df.format(new Date());
		    try
		    {
		    	String innerText=encryption.EncryptRetrivalAadharVault(ts,getRetrivalDetails(appUser,ts,reqRef,password,refType),propertiesPath.getAadharPublicCertificate() , skey, retReq, hmac, sessionK, "public");
		    	//System.out.println("retReq.innerText:::::::::::::"+retReq.innerText);
		    	data = getDataAadhar(innerText);
		    		try {
		      			txn=getQuery.getValuefromDual();
		      		}
		        catch (Exception e) {
					e.printStackTrace();
					}
		      	auth.set_lk(licenseKey);
		    	auth.set_txn(txn);
		    	auth.setTs(ts);
		    	auth.setUserId(userId);
		    	auth.setVer(version);
		    	adrth.auth=auth;
		    	adrth.data=data;
		    	adrth.hmac=hmac;
		    	adrth.skey=skey;
		    	StringWriter sw = new StringWriter();
		    			//JAXBContext context = JAXBContext.newInstance(ADHSTREQ.class);<--------------
		    			JAXBContext context = JAXBContext.newInstance(ADHRTREQ.class);
		    	        Marshaller m = context.createMarshaller();
		    	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    	        m.marshal(adrth, sw);
		    	   		String xmlString1 = sw.toString();
		    	    	output= callDbVaultService(xmlString1, retrivalUrl);
		    	    	System.out.println("output:::::::"+output);
		    	   	    String retCode=ParseStoreReq(output);
		    	   		//String retCodeRetrival=ParseStoreReq(output);<----
		    	   		String returnMessage = null;
		    		     if(retCode.equals("00"))
		    		     {
		    		    	 rtrRes=decRetriavalResponse(output);
		    		    	 if(rtrRes.DATA.rtrresp.reftype.equals("R"))
		    		    	 {
		    		    		 returnMessage="Refernce No is "+rtrRes.DATA.rtrresp.refno;
		    		    	 }
		    		    	 else if(rtrRes.DATA.rtrresp.reftype.equals("A"))
		    		    	 {
		    		    		 returnMessage="Aadhar No is "+rtrRes.DATA.rtrresp.aadharno;
		    		    	 }
		    		    	 uuid=returnMessage+" ~"+"00~Success"; 
		    		     }
		    		     else
		    		     {
		    		         uuid=getErrorMessage(retCode)+" ~"+retCode+"~"+getErrorMessage(retCode);
		    		     }
		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		return uuid;
	}
@Override
	public String getRetrivalDetails(String appUser,String ts, String reqRef, String password,String refType)
	{
		String xmlString = null;
	    try
	    {
	    	retReq.setPassword(password);
	    	retReq.setAppUser(appUser);
	    	retReq.setRefType(refType);
	    	retReq.setReqRef(reqRef);
	    	retReq.setTs(ts);
	        StringWriter sw = new StringWriter();
	        JAXBContext.newInstance(RTREQ.class).createMarshaller().marshal(retReq, sw);
	        xmlString = sw.toString();
	   		xmlString=xmlString.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>","");
	   	}
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	    System.out.println("getRetrivalDetails xmlString::::::::"+xmlString);
	   return xmlString;
	}
@Override
	public RTRRESP decRetriavalResponse( String reqXML ) throws Exception{
    	ADHRTRESPPobj=prx.decryptRetriavalResponse(reqXML);
    	return ADHRTRESPPobj;
	}
}