package com.miscot.springmvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.miscot.springmvc.bean.ApplicationMasterBean;
import com.miscot.springmvc.bean.LoginBean;
import com.miscot.springmvc.bean.UserMasterBean;
import com.miscot.springmvc.dto.ApplicationMaster;
import com.miscot.springmvc.dto.UserMaster;
import com.miscot.springmvc.dto.pathDTO;
import com.miscot.springmvc.helper.GenerateLicenceKey;
import com.miscot.springmvc.repository.DBUtil;
import com.miscot.springmvc.repository.GetQueryImpl;
import com.miscot.springmvc.service.AdAuthImpl;
import com.miscot.springmvc.service.UserMasterImpl;
import com.miscot.springmvc.service.UserMasterInterface;


@Controller
public class UserAuthenticationController {
@Autowired 
UserMasterImpl uservice;
@Autowired
GenerateLicenceKey gen;
@Autowired
AdAuthImpl objAdAuth;


JSONObject json = new JSONObject();
	@RequestMapping(value = "/UserAuthenticationController", method = RequestMethod.POST)
	public String UserAuthentication(HttpSession session,HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("loginBean") LoginBean loginBean,
			@ModelAttribute("userMasterBean") UserMasterBean userMasterBean,@RequestParam String operation,ModelMap model,
			BindingResult result) {
		
		String view=null;
		
		Boolean res = false;
		String chk = "";
		String sts = "";
		int FailCount = 0;
		
		if("authenticate".equalsIgnoreCase(operation))
		{
			String userid = loginBean.getUserName();
			String passWord = loginBean.getPassword();
			if(userid.equalsIgnoreCase("Miscot") && passWord.equalsIgnoreCase("miscotM1")){
				session = request.getSession(true);
				session.setAttribute("user_name","Miscot systems.");
				session.setAttribute("user_id","Miscot");
				session.setAttribute("password","");
				session.setAttribute("Role","Admin");
				session.setAttribute("User_IP", request.getRemoteAddr());
				session.setAttribute("LastLogin","");
				session.setAttribute("Chart1flg","Y"); 
				session.setAttribute("Chartflg","Y");
				session.setAttribute("BranchCode","");
				view="redirect:/Home";
				return view;
				
			}else{
			
			chk = uservice.AuthenticateUser(userid,passWord,userMasterBean,request.getRemoteAddr(),sts);
			System.out.println("chk" + chk);
			if (chk.equals(""))
			{
				session = request.getSession(true);
				session.setAttribute("user_name",userMasterBean.getUserName());
				session.setAttribute("user_id",userMasterBean.getUserID());
				session.setAttribute("password",passWord);
				session.setAttribute("User_IP", request.getRemoteAddr());
				session.setAttribute("Role",userMasterBean.getUSER_ROLE());
				session.setAttribute("LastLogin",userMasterBean.getLAST_LOGIN_DATE());
				session.setAttribute("BranchCode","");
				session.setAttribute("Chart1flg","Y");
				session.setAttribute("Chart1","Y");
				session.setAttribute("Chart2","");
				session.setAttribute("Chart3","");
				session.setAttribute("Chart1flg","Y"); 
				session.setAttribute("Chartflg","Y");
			    session.setAttribute("Chartflg","Y");
					String Sessionid=session.getId();
		 			int count=uservice.updateUserMaster(Sessionid,userid);
					view="redirect:/Home";
				return view;
			}
			else
			{
				String msg="";
				
			    if (chk!= null )
			    {
			    	if (chk.equals("N"))
			    	{
			    	
			    		msg="User Name and Password do not match.";
			    	
			    	}
			    	else if (chk.equals("A"))
			    	{
				    
			    		msg="Already Logged in.";
				    	
				    	}
			    	else if (chk.equals("D"))
			    	{
				    	
			    		msg="User Disabled. Contact Administrator.";
				    	
				    	}
			    	else if (chk.equals("I"))
			    	{
				    	
			    		msg="User Inactive. Contact Administrator.";
				    	
				    	}
			    	else if (chk.equals("L"))
			    	{
				    	
			    		msg="Account Locked. 3 consecutive login attempts failed";
				    	
				    	}
			    	else if (chk.equals("V"))
			    	{
				    	
			    		msg="Account not yet verified.";
				    	
				    		}
			    	
			    	else if (chk.equals("X"))
			    	{
				    	
			    		msg="User Expired. Contact Administrator.";
				    	
				    	}
			    }
			    System.out.println(msg);
			    	model.addAttribute("err", msg);
			    	view="Login";
					return view;
		
			}
			}
		}
		else if("searchUser".equalsIgnoreCase(operation))
		{
			
			System.out.println("Inside else"+userMasterBean.getUserID()+"========="+userMasterBean.getUserName()+"=========="+userMasterBean.getPage()+"======"+userMasterBean.getRows());
			List<UserMaster> userList = new ArrayList<UserMaster>();
			
			
			int total=20;
		
			try {
				PrintWriter out = response.getWriter();
				
				userList = uservice.GetUserLIst(userMasterBean);
				
				System.out.println("Total is"+userMasterBean.getCount());
				System.out.println("Size"+userList.size());
				total=userMasterBean.getCount();
				json.put("total", total);
				json.put("rows", userList);
				System.out.println("json"+json);
				
				out.print(json);
				out.flush();
				out.close();
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("GetVerifyUser".equalsIgnoreCase(operation))
		{
			
			List<UserMaster> verifyUserList = new ArrayList<UserMaster>();
			
			int total=20;
			try {
			
			PrintWriter out = response.getWriter();
			verifyUserList = uservice.GetVerifyUserLIst(userMasterBean,session);
			System.out.println("Total is"+userMasterBean.getCount());
			System.out.println("Size"+verifyUserList.size());
			total=userMasterBean.getCount();
			json.put("total", total);
			json.put("rows", verifyUserList);
			System.out.println("json"+json);
			out.print(json);
			out.flush();
			out.close();
		
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		else if("GetModifyUser".equalsIgnoreCase(operation))
		{
			
			List<ApplicationMaster> modifyUserList = new ArrayList<ApplicationMaster>();
			
			int total=20;
			try {
			
			PrintWriter out = response.getWriter();
			modifyUserList = uservice.getModifyUserList(userMasterBean);
			System.out.println("Total is"+userMasterBean.getCount());
			System.out.println("Size"+modifyUserList.size());
			total=userMasterBean.getCount();
			json.put("total", total);
			json.put("rows", modifyUserList);
			System.out.println("json"+json);
			out.print(json);
			out.flush();
			out.close();
		
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		else if("UserAccessLog".equalsIgnoreCase(operation))
		{
			List<UserMaster> userList = new ArrayList<UserMaster>();
			int total=20;
			try {
				PrintWriter out = response.getWriter();
				userList = uservice.GetUserAcessLogLIst(userMasterBean);
				total=userMasterBean.getCount();
				json.put("total", total);
				json.put("rows", userList);
				out.print(json);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("UserModificationLog".equalsIgnoreCase(operation))
		{
			List<UserMaster> userList = new ArrayList<UserMaster>();
			int total=20;
			try {
				PrintWriter out = response.getWriter();
				userList = uservice.GetUserModificationLogLIst(userMasterBean);
				total=userMasterBean.getCount();
				json.put("total", total);
				json.put("rows", userList);
				out.print(json);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("AppModificationLog".equalsIgnoreCase(operation))
		{
			List<UserMaster> userList = new ArrayList<UserMaster>();
			int total=20;
			try {
				PrintWriter out = response.getWriter();
				userList = uservice.GetUserAppModificationLogLIst(userMasterBean);
				total=userMasterBean.getCount();
				json.put("total", total);
				json.put("rows", userList);
				out.print(json);
				out.flush();
				out.close();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return view;
	}
	@RequestMapping(value = "/UserAuthenticationController", method = RequestMethod.GET)
	public String getUserAuthentication(HttpSession session,HttpServletRequest request,
			@ModelAttribute("loginBean") LoginBean loginBean,@RequestParam String operation,ModelMap model,
			BindingResult result) {
			
		
		return "Home";
	}
	
	@RequestMapping(value = "/userAccount", method = RequestMethod.POST ,headers="Accept=*/*",produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> editUserDetails(ModelMap model,@RequestParam String uid,@RequestParam String action) {

		List<UserMaster> userMasterList = null;
		if(action.equalsIgnoreCase("Edit"))
		{
			userMasterList=uservice.GetEditUserList(uid);
		}
		if(action.equalsIgnoreCase("get_VeriUserReport"))
		{
			userMasterList=uservice.GetVerifyUserList(uid);
		}
		Gson gson=new Gson();
		String jsonData = new Gson().toJson(userMasterList );
		System.out.println(jsonData);
		return new ResponseEntity<String>(jsonData, HttpStatus.OK);
		
		}
	
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> searchUser(HttpSession session,HttpServletRequest request,
			@ModelAttribute("userMasterBean") UserMasterBean userMasterBean,ModelMap model)
	{
		List<UserMaster> userList = new ArrayList<UserMaster>();
		int total=20;
		try {
			userList = uservice.GetUserLIst(userMasterBean);
			System.out.println("Total is"+userMasterBean.getCount());
			System.out.println("Size"+userList.size());
			total=userMasterBean.getCount();
			json.put("total", total);
			json.put("rows", userList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson=new Gson();
		String jsonData = new Gson().toJson(userList );
		 return new ResponseEntity<String>(jsonData, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/userEditAccount", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> saveeditUser(HttpSession session,HttpServletRequest request,
			@Validated @RequestBody UserMasterBean userMasterBean,ModelMap model)
	{
		System.out.println("Inside EditUserSave"+userMasterBean.getTxtUserID());
		boolean resultEditUser = false;
		String msg=null;
		resultEditUser=uservice.EditUserDetails(userMasterBean,session.getAttribute("user_id").toString(),request.getRemoteAddr());
		if (resultEditUser == true) {
			msg = "Date Saved Successfully";
		} else {
			msg = "Action Failed";
		}
		System.out.println("Message is"+msg);
		 return new ResponseEntity<String>(msg, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/VerifyUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> verifyUser(HttpSession session,HttpServletRequest request,
			UserMasterBean userMasterBean,ModelMap model,@RequestParam String operation)
	{
		String scr = "";
		if ("verifyUser".equalsIgnoreCase(operation)) {
			session = request.getSession(true);
			boolean res = false;
			try {
				scr=uservice.checkUserCreated(userMasterBean.getTxt_User_id(),session.getAttribute("user_id").toString());
				if(scr.equalsIgnoreCase(""))
				{
					res =uservice.VerifyUser(userMasterBean,session.getAttribute("user_id").toString(),request.getRemoteAddr());
					if (res == true) {
						scr = "User ID verified Sucessfully";
					} else {
						scr = "Error Occured";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("get_Veri_Reject".equalsIgnoreCase(operation)) {
			session = request.getSession(true);
			boolean res = false;
			try {
				res =uservice.RejectVerifyUser(userMasterBean,session.getAttribute("user_id").toString(),request.getRemoteAddr());

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res == true) {
				scr = "Verification Rejected  Successfully";
			} else {
				scr = "Error Occured";
			}
		}
		 return new ResponseEntity<String>(scr, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/userResetAccount", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> resetUser(HttpSession session,HttpServletRequest request,
			@Validated @RequestBody UserMasterBean userMasterBean,ModelMap model)
	{
		System.out.println("Inside ResetUser"+userMasterBean.getChkUserID());
		boolean result = false;
		String msg="";
		result=uservice.ResetLogin(userMasterBean.getChkUserID(), session
				.getAttribute("user_id").toString(), request
				.getRemoteAddr().toString());
		if (result == true) {
			msg = "ID Resetted Sucessfully";
		} else {
			msg = "Error Occured";
		}
		System.out.println("Message is"+msg);
		 return new ResponseEntity<String>(msg, HttpStatus.OK);
		
	}
	@RequestMapping(value = "/addApplication", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addModifiedApplication(HttpSession session,HttpServletRequest request,
			@Validated @RequestBody ApplicationMasterBean applicationMasterBean,ModelMap model)
	{
		System.out.println("Inside addModifiedApplication"+applicationMasterBean.getTxtUserID());
		String result = null;
		String msg="";
		
		String lk=gen.lk(applicationMasterBean.getTxtUserID());
		System.out.println(lk);
		result=uservice.addApplication(applicationMasterBean,lk,session.getAttribute("user_id").toString());
		if(result.equalsIgnoreCase("S")){
			msg="Added Successfully..";
		}else if(result.equalsIgnoreCase("F")){
			msg="Failed to Add";
		}
		System.out.println("Message is"+msg);
		 return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addUserData(HttpSession session,HttpServletRequest request,
			@Validated @RequestBody UserMasterBean userMasterBean,ModelMap model)
	{
		String sts="";
		sts =  uservice.AddNewUser(userMasterBean, session.getAttribute("user_id").toString(), sts,request.getRemoteAddr());
		
		if (sts.equalsIgnoreCase("")) {
			sts = "Data saved successfully";
		}
	
		 return new ResponseEntity<String>(sts, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getmodifyApplicationDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getmodifyApplicationDetail(HttpSession session,HttpServletRequest request,
			@RequestParam String uid,ModelMap model)
	{
		System.out.println("Inside getmodifyApplicationDetails"+uid);
		String result = null;
		String msg="Data Saved Successfully";
		
		List<ApplicationMaster> modifyUserList = new ArrayList<ApplicationMaster>();
		try {
			modifyUserList = uservice.getModifyUpdateDetails(uid);
		System.out.println("Size"+modifyUserList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Message is"+msg);
		Gson gson=new Gson();
		String jsonData = new Gson().toJson(modifyUserList );
		System.out.println("jsonData"+jsonData);
		 return new ResponseEntity<String>(jsonData, HttpStatus.OK);
		
	}
	@RequestMapping(value = "/editmodifyApplication", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> editModifyApplication(HttpSession session,HttpServletRequest request,
			@Validated @RequestBody ApplicationMasterBean applicationMasterBean,ModelMap model)
	{
		System.out.println("Inside EditUserSave"+applicationMasterBean.getTxtUserID());
		String resultEditApplication = null;
		String msg="";
		resultEditApplication=uservice.editApplicationDetails(applicationMasterBean,session.getAttribute("user_id").toString());
		if(resultEditApplication.equalsIgnoreCase("S")){
			msg="Updated Successfully..";
		}else if(resultEditApplication.equalsIgnoreCase("F")){
			msg="Failed to Update";
		}
		System.out.println("Message is"+msg);
		 return new ResponseEntity<String>(msg, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/GetADUserName", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getADUserVer(HttpSession session,HttpServletRequest request,
			 UserMasterBean userMasterBean,ModelMap model)
	{
		String validName = "";
		try {
			String userID = userMasterBean.getTxtUserID();
			validName = objAdAuth.GetNameUser(userID);
			if(validName.equalsIgnoreCase("NO")) {
			validName = objAdAuth.GetName(userID);
			}else {
				validName="YES";
			}
		} catch (Exception e) {
			e.printStackTrace();
			validName = "E";
		}
	
		 return new ResponseEntity<String>(validName, HttpStatus.OK);
		
	}
	@RequestMapping(value = "/checkIsAdoAuth", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> checAdAuth(ModelMap model)
	{
		String isAdoAuth = "";
		try {
			isAdoAuth=objAdAuth.getIsAdoAuthValue();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
		 return new ResponseEntity<String>(isAdoAuth, HttpStatus.OK);
		
	}
	
	
	
}
