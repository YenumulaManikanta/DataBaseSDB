package com.miscot.springmvc.repository;

import java.sql.SQLException;
import java.util.List;

import com.miscot.springmvc.bean.ApplicationMasterBean;
import com.miscot.springmvc.dto.Activity_Log;
import com.miscot.springmvc.dto.ApplicationMaster;
import com.miscot.springmvc.dto.UserMaster;
import com.miscot.springmvc.helper.Home;


public interface GetQueryInterface {
	int update_tbl_user_master1(String ipAddr,String userid);
	int insert_tbl_audit_login1(String ipAddr,String userid);
	int insert_tbl_audit_login1(String ipAddr,String userid,String chk);
	String get_chkuser(String user,String pass);
	int update_tbl_user_master(String Sessionid,String userid);
	String User_Master2_Cnt(int startI, int endI, String User_id,String User_name) throws SQLException, Exception;
	List<UserMaster> listUserMaster(String qry,String flag);
	String tempFailCount();
	int insert_tbl_audit_login1(String userid);
	String isAlreadyExisit(String userid);
	int addNew(String USER_ID,String uname,String USER_STATUS,String USER_ROLE,String createdUser,String date,String P_F_NNUMBER,String SOL_ID);
	int addNew_tbl_audit(String createdUser,String USER_ID,String ip_addrss,String activity_desc);
	String Verify_UserList(int startI, int endI, String currentUser);
	List<UserMaster> Verify_UserList(String Qry);
	String getEditUserQuery(String uid);
	String getEditUserDetails(String txtUserID);
	void updateEditUserMaster(String selUserRole, String selUserStatus, String txtUserID, String modifiedUser, String expiryDate, String userName);
	void updateEditUserMasterNotVerified(String selUserRole, String selUserStatus, String txtUserID,String modifiedUser, String txtExpiryDate, String string);
	void insertActivityLog(String modifiedUser, String txtUserID, String activity_dec, String ip_addrss);
	String getVerifyQuery(String uid);
	int update_tbl_user_master2(String currentUser, String userid);
	int insert_tbl_audit_login2(String currentUser, String userid, String ip_addrss);
	int update_tbl_user_master3(String currentUser, String userid);
	int insert_tbl_audit_login3(String currentUser, String userid, String ip_addrss);
	String AcessLogQuery(int startI, int endI, String User_id, String From_Date, String To_Date, String adhNo,String refNo, String hdn_AppName);
	List<Activity_Log> listActivityLog(String qry, String flag);
	List<ApplicationMaster> GetAllAppName();
	public List<ApplicationMaster> listModifyAppMaster(String string,String flag);
	String modifyUserList(int startI, int endI);
	String ActivityLogQuery(int startI, int endI, String User_id, String From_Date, String To_Date, String adhNo,String refNo, String hdn_AppName);
	int update_tbl_user_master4(String userid);
	void insertActivityLog1(String userid, String currentUser, String ip_addrss);
	List<ApplicationMaster> GetStatus();
	String UserAccessLogSearchSQL(int startI, int endI, String userID, String frmDate, String toDate, String status);
	List<ApplicationMaster> GetActivityDesc();
	String UserModificationLogSearchSQL(int startI, int endI, String userID, String frmDate, String toDate,String status, String acted_on_user);
	String AppModificationLogSearchSQL(int startI, int endI, String userID, String frmDate, String toDate,String status, String acted_on_user);
	String insertApplication(ApplicationMasterBean applicationMasterBean, String lk, String sessionUserId);
	String modifyAppList(String uid);
	String editModifyApplication(ApplicationMasterBean applicationMasterBean, String sessionUserId,String activity_dec);
	String modifyEditAppList(String txtUserID);
	String getValuefromDual();
	List<Home> listResultSet(String sql);
	String GetUserName(String userId);
	String checkCreatedUser(String txt_User_id);

}
