package com.miscot.springmvc.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.miscot.springmvc.bean.AadharAccessLogBean;
import com.miscot.springmvc.dto.Activity_Log;

public interface AadharAccessLogInterface {

	public List<Activity_Log> GetActivityLogLIst(AadharAccessLogBean aadharAccessLogBean, HttpSession session, String operation);
	public String GetAppName();
	public String GetStatus();
	String GetActivityDesc();
}
