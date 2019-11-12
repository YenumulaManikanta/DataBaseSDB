package com.miscot.springmvc.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.Document;
import com.miscot.springmvc.bean.ApplicationMasterBean;
import com.miscot.springmvc.bean.UserMasterBean;
import com.miscot.springmvc.dto.ApplicationMaster;
import com.miscot.springmvc.dto.UserMaster;



public interface UserMasterInterface {
	List<UserMaster> GetUserLIst(UserMasterBean userMasterBean);
	List GetUserMaintenanceReportLIst(String Qry);
	List GetUserActivityReportLIst(String Qry);
	List<UserMaster> GetVerifyUserLIst(UserMasterBean userMasterBean,HttpSession session);
	UserMaster GetUserDetails(String userid);
	String Get_Zone_List(String usr,String typ);
	String  GetFolderList(String usr,String typ);
	String GetAppList1(String usr,String typ);
	String Dar_Get_Branch_List();
	String Dar_Get_Branch_List_fin7();
	String CheckUserExistLogin(String User_id,UserMasterBean userMasterBean);
	String CheckUserExistNew(String User_id);
	String Get_zone(String User_id);
	String chkPasswordExpiry(String userid);
	String chkLockedUserTime(String userid);
	String AuthenticateUser(String userid,String passWord,UserMasterBean userMasterBean ,String ipAddr,String sts);
	String AuthenticateUserDB(String userid,String passWord,UserMaster dto,String ipAddr,String sts) ;
	int UserFailCount(String userid);
	boolean ResetPassword(String UserId,String Password);
	boolean ResetLogin(String userid,String CurrentUser ,String ip_addrss);
	boolean VerifyUser(UserMasterBean userMasterBean,String currentUser,String ip_addrss);
	String AddNewUser(UserMasterBean userMasterBean,String createdUser,String sts,String ip_addrss);
	boolean EditUserDetails(UserMasterBean userMasterBean, String usrLock, String ip_addrss);
	boolean ChangePassword(String oldPassW, String newPassW, String user_id);
	boolean SAChangePassword(String newPassW, String user_id,String SaUserID);
	List GetBulkAadharLIst(String Qry);
	String ProcessBulkAadharLIst(String query);
	public int updateUserMaster(String sessionid, String userid);
	public List<UserMaster> GetVerifyUserList(String uid);
	List<ApplicationMaster> getModifyUserList(UserMasterBean userMasterBean);
	public List<UserMaster> GetUserModificationLogLIst(UserMasterBean userMasterBean);
	public List<UserMaster> GetUserAppModificationLogLIst(UserMasterBean userMasterBean);
	public List<UserMaster> GetUserAcessLogLIst(UserMasterBean userMasterBean);
	public String addApplication(ApplicationMasterBean applicationMasterBean, String lk, String sessionUserId);
	List<ApplicationMaster> getModifyUpdateDetails(String uid);
	String editApplicationDetails(ApplicationMasterBean applicationMasterBean, String string);
	public XSSFWorkbook GetUserModificationLogExcel(UserMasterBean userMasterBean,XSSFWorkbook workbook);
	public XSSFWorkbook GetUserAppModificationLogExcel(UserMasterBean userMasterBean,XSSFWorkbook workbook);
	public XSSFWorkbook GetUserAcessLogExcel(UserMasterBean userMasterBean, XSSFWorkbook workbook);
	public Document GetUserModificationLogPDF(UserMasterBean userMasterBean,Document document);
	public Document GetUserAppModificationLogPDF(UserMasterBean userMasterBean,Document document);
	public Document GetUserAcessLogPDF(UserMasterBean userMasterBean, Document document);
	String checkUserCreated(String txt_User_id, String string);
	
}
