package com.miscot.springmvc.controller;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
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

import com.miscot.springmvc.bean.AadharAccessLogBean;
import com.miscot.springmvc.bean.UserMasterBean;
import com.miscot.springmvc.dto.Activity_Log;
import com.miscot.springmvc.service.AadharAccessLogImpl;

@Controller
public class AadharAccessLogController {
	@Autowired 
	AadharAccessLogImpl service;
	JSONObject json = new JSONObject();
	@RequestMapping(value = "/AadharAccessLogController", method = RequestMethod.POST)
	public String UserAuthentication(HttpSession session,HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("aadharAccessLogBean") AadharAccessLogBean aadharAccessLogBean,@RequestParam String operation,ModelMap model,
			BindingResult result) {
		
		String view=null;
		if(operation.equalsIgnoreCase("getAppName")) {
			try {
				PrintWriter out = response.getWriter();
				String Res=service.GetAppName();
				model.addAttribute("Res", Res);
				//return Res;
				out.print(Res);
				out.flush();
				out.close();
			
				}  catch (Exception e) {
					e.printStackTrace();
				}
			/*String Res=service.GetAppName();
			model.addAttribute("Res", Res);
			return Res;*/
		}
		else if(operation.equalsIgnoreCase("accessLog")){
			List<Activity_Log> activityLogList = new ArrayList<Activity_Log>();
			int total=20;
			try {
			PrintWriter out = response.getWriter();
			activityLogList = service.GetActivityLogLIst(aadharAccessLogBean,session,operation);
			System.out.println("Total is"+aadharAccessLogBean.getCount());
			System.out.println("Size"+activityLogList.size());
			total=aadharAccessLogBean.getCount();
			json.put("total", total);
			json.put("rows", activityLogList);
			System.out.println("json"+json);
			out.print(json);
			out.flush();
			out.close();
		
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(operation.equalsIgnoreCase("allLog")){
			List<Activity_Log> activityLogList = new ArrayList<Activity_Log>();
			int total=20;
			try {
			PrintWriter out = response.getWriter();
			activityLogList = service.GetActivityLogLIst(aadharAccessLogBean,session,operation);
			total=aadharAccessLogBean.getCount();
			json.put("total", total);
			json.put("rows", activityLogList);
			out.print(json);
			out.flush();
			out.close();
		
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
		 
		return view;
		
	}
	
	@RequestMapping(value = "/getAppName", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getAppName(HttpSession session,HttpServletRequest request,ModelMap model)
	{
		String Res=service.GetAppName();
		return new ResponseEntity<String>(Res, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getStatus(HttpSession session,HttpServletRequest request,ModelMap model)
	{
		String Res=service.GetStatus();
		return new ResponseEntity<String>(Res, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getActivityDesc", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getActivityDesc(HttpSession session,HttpServletRequest request,ModelMap model)
	{
		String Res=service.GetActivityDesc();
		return new ResponseEntity<String>(Res, HttpStatus.OK);
		
	}
}