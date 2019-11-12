package com.miscot.springmvc.controller;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miscot.springmvc.bean.ApplicationMasterBean;
import com.miscot.springmvc.bean.LoginBean;
import com.miscot.springmvc.bean.StoreRetrieveAadharBean;
import com.miscot.springmvc.bean.UserMasterBean;
import com.miscot.springmvc.configuration.PropertiesPath;
import com.miscot.springmvc.service.StoreRetrieveAadharServiceImpl;

@Controller
public class StoreRetrievalAadharController {
	@Autowired
	StoreRetrieveAadharServiceImpl storeRetrieveService;
	@Autowired
	PropertiesPath propertiesPath;
	
	
	@RequestMapping(value = "/storeRetriveAadharData", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> storeRetrieveAadharDetails(HttpSession session,HttpServletRequest request,
			@RequestParam String operation,@Validated @RequestBody StoreRetrieveAadharBean storeRetrieveAadharBean,ModelMap model) throws NoSuchAlgorithmException, NoSuchProviderException
	{
		//System.out.println("Inside storeRetriveAadharData"+storeRetrieveAadharBean.getAdh_no());
	
		String msg=null;
		
	
		if(operation.equalsIgnoreCase("storeAadhar"))
		{
			 String res=storeRetrieveService.storeAadhar(propertiesPath.getAdhVlt_userID(), propertiesPath.getAdhVlt_lk(), propertiesPath.getAdhVlt_version(), propertiesPath.getAdhVlt_password(), storeRetrieveAadharBean.getAdh_no(), session.getAttribute("user_id").toString());
			msg=res;
		}
		else if(operation.equalsIgnoreCase("retrieveAadhar"))
		{
			 String resp=null;
			 String adhar_no=storeRetrieveAadharBean.getAdh_no();
				adhar_no=adhar_no.trim();
				
				String refno=storeRetrieveAadharBean.getRefNo();
				refno=refno.trim();
			if(adhar_no==null || adhar_no.equalsIgnoreCase("")) {
		    	    resp= storeRetrieveService.retrieveAadhar(propertiesPath.getAdhVlt_userID(), propertiesPath.getAdhVlt_lk(), propertiesPath.getAdhVlt_version(), propertiesPath.getAdhVlt_password(),refno, session.getAttribute("user_id").toString(), "R");
		    	    msg=resp;  
			}
		       else if(refno==null || refno.equalsIgnoreCase("")) {
		    	   resp= storeRetrieveService.retrieveAadhar(propertiesPath.getAdhVlt_userID(), propertiesPath.getAdhVlt_lk(), propertiesPath.getAdhVlt_version(), propertiesPath.getAdhVlt_password(),adhar_no, session.getAttribute("user_id").toString(), "A");
		    	   msg=resp;  
		    	   // resp= retrieveAadhar( userId, licenseKey, version, password, adhar_no, UserId, "A");
		       }
		}
		
		System.out.println("Message is"+msg);
		 return new ResponseEntity<String>(msg, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/storeRetriveAadharData1", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> storeRetrieveAadharDetails1(HttpServletRequest request,
			@RequestParam String operation,@Validated @RequestBody StoreRetrieveAadharBean storeRetrieveAadharBean,ModelMap model) throws NoSuchAlgorithmException, NoSuchProviderException
	{
		String msg=null;
		if(operation.equalsIgnoreCase("storeAadhar"))
		{
			 String res=storeRetrieveService.storeAadhar(propertiesPath.getAdhVlt_userID(), propertiesPath.getAdhVlt_lk(), propertiesPath.getAdhVlt_version(), propertiesPath.getAdhVlt_password(), storeRetrieveAadharBean.getAdh_no(), "TestUser");
			msg=res;
		}
		else if(operation.equalsIgnoreCase("retrieveAadhar"))
		{
			 String resp=null;
			 String adhar_no=storeRetrieveAadharBean.getAdh_no();
			 String adhtype=storeRetrieveAadharBean.getAdhtype();
			 //System.out.println(adhtype);
			 //System.out.println(adhar_no);
			 adhar_no=adhar_no.trim();
				
			if(adhtype.equalsIgnoreCase("R")) {
		    	    resp= storeRetrieveService.retrieveAadhar(propertiesPath.getAdhVlt_userID(), propertiesPath.getAdhVlt_lk(), propertiesPath.getAdhVlt_version(), propertiesPath.getAdhVlt_password(),adhar_no,  "TestUser", "R");
		    	    System.out.println(resp);
		    	    msg=resp.replace("Aadhar No is ", "");  
			}
		       else if(adhtype.equalsIgnoreCase("A")) {
		    	   resp= storeRetrieveService.retrieveAadhar(propertiesPath.getAdhVlt_userID(), propertiesPath.getAdhVlt_lk(), propertiesPath.getAdhVlt_version(), propertiesPath.getAdhVlt_password(),adhar_no,  "TestUser", "A");
		    	   System.out.println(resp);
		    	   msg=resp.replace("Refernce No is ", "");  
		    }
		}
		return new ResponseEntity<String>(msg, HttpStatus.OK);
		
	}
	@RequestMapping(value = "/storeRetriveAadharData2", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> storeRetrieveAadharDetails2(HttpServletRequest request,
			@RequestParam String operation, StoreRetrieveAadharBean storeRetrieveAadharBean,ModelMap model) throws NoSuchAlgorithmException, NoSuchProviderException
	{
		String msg=null;
		if(operation.equalsIgnoreCase("storeAadhar"))
		{
			 String res=storeRetrieveService.storeAadhar(propertiesPath.getAdhVlt_userID(), propertiesPath.getAdhVlt_lk(), propertiesPath.getAdhVlt_version(), propertiesPath.getAdhVlt_password(), storeRetrieveAadharBean.getAdh_no(), "TestUser");
			msg=res;
		}
		else if(operation.equalsIgnoreCase("retrieveAadhar"))
		{
			 String resp=null;
			 String adhar_no=storeRetrieveAadharBean.getAdh_no();
			 String adhtype=storeRetrieveAadharBean.getAdhtype();
			 System.out.println(adhtype);
			 System.out.println(adhar_no);
			 adhar_no=adhar_no.trim();
				
			if(adhtype.equalsIgnoreCase("R")) {
		    	    resp= storeRetrieveService.retrieveAadhar(propertiesPath.getAdhVlt_userID(), propertiesPath.getAdhVlt_lk(), propertiesPath.getAdhVlt_version(), propertiesPath.getAdhVlt_password(),adhar_no,  "TestUser", "R");
		    	    System.out.println(resp);
		    	    msg=resp.replace("Aadhar No is ", "");  
			}
		       else if(adhtype.equalsIgnoreCase("A")) {
		    	   resp= storeRetrieveService.retrieveAadhar(propertiesPath.getAdhVlt_userID(), propertiesPath.getAdhVlt_lk(), propertiesPath.getAdhVlt_version(), propertiesPath.getAdhVlt_password(),adhar_no,  "TestUser", "A");
		    	   System.out.println(resp);
		    	   msg=resp.replace("Refernce No is ", "");  
		    }
		}
		return new ResponseEntity<String>(msg, HttpStatus.OK);
		
	}
	}

