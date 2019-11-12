package com.miscot.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miscot.springmvc.bean.AadharAccessLogBean;
import com.miscot.springmvc.dto.Activity_Log;
import com.miscot.springmvc.dto.ApplicationMaster;
import com.miscot.springmvc.repository.GetQueryImpl;
@Service
@Transactional
public class AadharAccessLogImpl implements AadharAccessLogInterface {
	
	@Autowired 
	GetQueryImpl getQuery;
	@Override
	public List<Activity_Log> GetActivityLogLIst(AadharAccessLogBean aadharAccessLogBean, HttpSession session,String operation) {


		String User_ID=aadharAccessLogBean.getHdn_userID();
		String From_Date=aadharAccessLogBean.getHdn_frmDate();
		String To_Date=aadharAccessLogBean.getHdn_toDate();
		String adhNo=aadharAccessLogBean.getHdn_adhNo();
		String refNo=aadharAccessLogBean.getHdn_refNo();
		String hdn_AppName=aadharAccessLogBean.getHdn_AppName();
		
		int page = Integer.parseInt(aadharAccessLogBean.getPage());
		int rows = Integer.parseInt(aadharAccessLogBean.getRows());
		int endI = rows * page;
		int startI = endI - rows + 1;
		int offset = (page - 1) * rows;
		List<Activity_Log> activityLogList=new ArrayList<Activity_Log>();
		try
		{
			String qry ="";
			if(operation.equalsIgnoreCase("allLog")){
			qry = getQuery.ActivityLogQuery(startI, endI, User_ID, From_Date, To_Date, adhNo, refNo, hdn_AppName);
			}
			else if(operation.equalsIgnoreCase("accessLog")){
				qry = getQuery.AcessLogQuery(startI, endI, User_ID, From_Date, To_Date, adhNo, refNo, hdn_AppName);
			}
			String[] query = qry.split("~");
			aadharAccessLogBean.setCount(Integer.parseInt(query[1]));
			activityLogList=getQuery.listActivityLog(query[0],operation);
			
			return activityLogList;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return activityLogList;
	}
	
	@Override
	public String GetAppName() {
		try
		{
			List<ApplicationMaster> list = getQuery.GetAllAppName();
			String res="<option value=''>---SELECT---</option>";
			for(int i=0;i<list.size();i++)
			{
				res+="<option value="+list.get(i).getUSER_ID()+">"+list.get(i).getAPPLICATION_NAME()+"</option>";
			}
			return res;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	@Override
	public String GetStatus() {
		try
		{
			List<ApplicationMaster> list = getQuery.GetStatus();
			String res="<option value=''>---SELECT---</option>";
			for(int i=0;i<list.size();i++)
			{
				res+="<option value="+list.get(i).getSTATUS_ID()+">"+list.get(i).getSTATUS_DES()+"</option>";
			}
			return res;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	@Override
	public String GetActivityDesc() {
		try
		{
			List<ApplicationMaster> list = getQuery.GetActivityDesc();
			String res="<option value=''>---SELECT---</option>";
			for(int i=0;i<list.size();i++)
			{
				res+="<option value="+list.get(i).getOP_TYPE()+">"+list.get(i).getOP_DESC()+"</option>";
			}
			return res;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
