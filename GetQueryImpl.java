package com.miscot.springmvc.repository;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miscot.springmvc.bean.ApplicationMasterBean;
import com.miscot.springmvc.dto.Activity_Log;
import com.miscot.springmvc.dto.ApplicationMaster;
import com.miscot.springmvc.dto.UserMaster;
import com.miscot.springmvc.helper.Home;
import com.miscot.springmvc.service.EncryptionDecryption;

@Service
public class GetQueryImpl implements GetQueryInterface {

	@Autowired  DBUtil ds;

	String strSQL1;
	String strSQL;
	String where = "";
	String Count="0";
	String res = "";
	@Override
	public int update_tbl_user_master1(String ipAddr,String userid) {
		int res=ds.updateOrInsertValues("update tbl_user_master set last_login_date = current_timestamp,login_check_date = CURRENT_TIMESTAMP,"+ "last_login_ip='" + ipAddr + "',isActive='Y',isUnlocked='N' where upper(user_id)='"+userid.toUpperCase()+"' "+ "and user_status='E'  ");
		return res;
	}

	@Override
	public int insert_tbl_audit_login1(String ipAddr,String userid) {
		int res=ds.updateOrInsertValues("insert into tbl_audit_login values ('" +userid.toUpperCase()+ "',CURRENT_TIMESTAMP,'S','" + ipAddr + "')");
		return res;
	}

	@Override
	public int insert_tbl_audit_login1(String ipAddr, String userid, String chk) {
		int res=ds.updateOrInsertValues("insert into tbl_audit_login values ('" +userid.toUpperCase()+ "',current_timestamp,'"
				+ chk.trim() +"','" + ipAddr + "')");
		return res;
	}

	@Override
	public String get_chkuser(String user, String pass) {
		String chkuser=ds.getSingleValues("select count(*) from TBL_USER_MASTER where upper(USER_ID) = '"+ user.toUpperCase() + "' and passwd='"+ ds.encrypt(pass) +"'");
		return chkuser;
	}

	@Override
	public int update_tbl_user_master(String Sessionid, String userid) {
		int res=ds.updateOrInsertValues("update tbl_user_master set last_session_id = '"+Sessionid+"'  where upper(user_id)= '"+userid.toUpperCase()+"' " );
		return res;
	}

	@Override
	public int update_tbl_user_master2(String currentUser, String userid) {
		int res=ds.updateOrInsertValues("update TBL_USER_MASTER set verified_user='"+currentUser+"',is_verified='Y',verified_date=sysdate where upper(user_id) = '" + userid + "' and  is_verified='N' ");
		return res;
	}

	@Override
	public int insert_tbl_audit_login2(String currentUser, String userid, String ip_addrss) {
		int res=ds.updateOrInsertValues("insert into user_activity_log (USER_ID,USER_ID_ACTED_ON,STATUS_DATE,ACTIVITY_TYPE,IP_ADDRESS) values('" + currentUser + "','" +  userid + "',sysdate,'V','"+ip_addrss+"')");
		return res;
	}
	@Override
	public int update_tbl_user_master3(String currentUser, String userid) {
		int res=ds.updateOrInsertValues("update tbl_user_master set is_verified='R',verified_user='"+ currentUser+ "',verified_date=current_date where    user_id='"+ userid + "' ");
		return res;
	}

	@Override
	public int insert_tbl_audit_login3(String currentUser, String userid, String ip_addrss) {
		int res=ds.updateOrInsertValues("insert into user_activity_log (USER_ID,USER_ID_ACTED_ON,STATUS_DATE,ACTIVITY_TYPE,activity_desc,IP_ADDRESS) values('"+ currentUser	+ "','"	+ userid+ "',current_date,'D',' Verification Rejected' ,'"+ ip_addrss + "')");
		return res;
	}

	@Override
	public String User_Master2_Cnt(int startI, int endI, String User_id,
			String User_name) throws SQLException, Exception {

		String res = "";
		String Count = "0";
		String strSQL1 = "select /*+ parallel */ a.*,last_login_date as LoginDate, CASE when isactive = 'N' then 'Inactive' else 'Active' end as IsActiveS, "
				+ "case when isactive = 'Y' then '<a href=# onclick=chkReset(''' || User_ID || ''');>Reset</a>' else '' end as Reset , "
				+ "ROW_NUMBER() OVER ( ORDER BY User_id ) AS rn  from v_userDetails a where del_flag = 'N'  ";

		String where="";
		if (User_id != null && !(User_id.equalsIgnoreCase(""))) {
			if (User_id.indexOf("*") > 0) {
				where += " and  upper(User_id) like '%"
						+ User_id.replace("*", "").toUpperCase() + "%'";

			} else {

				where += " and  upper(User_id) = '"
						+ User_id.replace("*", "").toUpperCase() + "'";
			}
		}

		if (User_name != null && !(User_name.equalsIgnoreCase(""))) {

			if (User_name.indexOf("*") > 0) {
				where += " and  upper(User_name) like '%"
						+ User_name.replace("*", "").toUpperCase() + "%'";

			} else {
				where += " and  upper(User_name) = '"
						+ User_name.replace("*", "").toUpperCase() + "'";
			}
		}

		strSQL1 += where;

		String strSQL = "Select * from (" + strSQL1 + ") b where rn between " + startI
				+ " and " + endI + "";
		Count=ds.getSingleValues("select count(*) from (" + strSQL1 + ") b");
		res = strSQL+ "~" + Count ;//+ "~" + Count;

		return res;
	}

	@Override
	public String UserAccessLogSearchSQL(int startI, int endI ,String userID,String frmDate,String toDate , String status) {

		String res = "";
		String strSQL1="";
		try {
			strSQL1 = "select  a.USER_ID,to_char(a.LOGIN_DATE,'dd-mon-yyyy hh:mi:ss AM') as LOGIN_DATE_d,a.LOGIN_DATE as LOGIN_DATE_f,c.STATUS_DES ,	"
					+ "	a.LOGIN_IP,b.USER_NAME from		tbl_audit_login a, TBL_USER_MASTER b,TBL_USER_LOGIN_STATUS c where a.USER_ID=b.USER_ID and a.LOGIN_STATUS=c.STATUS_ID " ;
			if (!ds.RemoveNull(userID).equalsIgnoreCase("")) {
				strSQL1+= " and a.USER_ID='"+userID+"' " ;
			}

			if (!ds.RemoveNull(frmDate).equalsIgnoreCase("") && !ds.RemoveNull(toDate).equalsIgnoreCase("")) {
				 strSQL1+= " and trunc(a.LOGIN_DATE) between to_date('"+frmDate+"','DD/MM/YYYY') and  to_date('"+toDate+"','DD/MM/YYYY') " ;
			}
			else if (!ds.RemoveNull(frmDate).equalsIgnoreCase("")) {
				 strSQL1+= " and trunc(a.LOGIN_DATE)>=to_date('"+frmDate+"','DD/MM/YYYY') " ;
			}
			else if (!ds.RemoveNull(toDate).equalsIgnoreCase("")) {
				 strSQL1+= " and trunc(a.LOGIN_DATE)<=to_date('"+toDate+"','DD/MM/YYYY') " ;
			}
			if (!ds.RemoveNull(status).equalsIgnoreCase("")) {
				 strSQL1+= " and a.LOGIN_STATUS='"+status+"' " ;
			}
			if(endI!=99999999) {
			Count = ds.getSingleValues(" select count(*) from (" + strSQL1 + ") b ");
			}
			strSQL = " select * from  (Select rownum as rn,b.* from (" + strSQL1 + "  order by LOGIN_DATE_f desc ) b )c ";
		res = strSQL+" where rn between "+startI +" and "+endI + "  ~" + Count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<UserMaster> listUserMaster(String qry, String flag) {
		List<UserMaster> userMasters=ds.listUserMaster(qry,flag);
		return userMasters;
	}


	@Override
	public String tempFailCount() {
		String res=ds.getSingleValues("select param_value from tbl_system_settings where upper(param_name) = 'FAILCOUNT'");
		return res;
	}

	@Override
	public int insert_tbl_audit_login1(String userid) {
		int res=ds.updateOrInsertValues("update tbl_user_master set user_status = 'L',isunlocked='N',unlock_date=null where   UPPER(user_id) = '"
										+ userid + "'");
		return res;
	}

	@Override
	public String isAlreadyExisit(String userid) {
		String res=ds.getSingleValues("select count(*) from tbl_user_master where upper(user_id) = '" +userid + "' and del_flag = 'N'");
		return res;
	}

	@Override
	public int addNew(String USER_ID, String uname, String USER_STATUS, String USER_ROLE, String createdUser,
			String date, String P_F_NNUMBER, String SOL_ID) {
		int res=ds.updateOrInsertValues("insert into tbl_user_master ("
				+ "user_id,user_name,user_status,user_role,created_time,created_user,expiry_date,del_flag,P_F_NUMBER,SOL_ID,PASSWD,USER_STATUS_2,IS_VERIFIED,ISACTIVE) "
				+ "values('" + USER_ID + "','" + uname + "','" + USER_STATUS + "','" + USER_ROLE + "',"
				+ "sysdate,'" + createdUser + "',TO_DATE('" + date+"','dd/mm/yyyy'),'N','" + P_F_NNUMBER + "','" +
				SOL_ID + "','O2HAAApFlOLXyC5oFXcv3g=="+ "','F','N','N')");
		return res;
	}

	@Override
	public int addNew_tbl_audit(String createdUser, String USER_ID, String ip_addrss, String activity_desc) {
		int res=ds.updateOrInsertValues("insert into user_activity_log (USER_ID,USER_ID_ACTED_ON,STATUS_DATE,ACTIVITY_TYPE,IP_ADDRESS,ACTIVITY_DESC) "
		 		+ "values('" + createdUser + "','" +   USER_ID + "',"+ "sysdate,'A','"+ip_addrss+"','"+activity_desc+"')");
		return res;
	}

	@Override
	public String Verify_UserList(int startI, int endI, String currentUser) {

		String res = "";
		String where = "";
		String Count = "0";
		String strSQL = "";
		String strSQL1 = "SELECT /*+ parallel */ a.*,last_login_date as LoginDate, CASE when isactive = 'N' then 'Inactive' else 'Active' end as IsActiveS ,"
				+ "USER_ROLE as USER_ROLE_DESC "
				+ "FROM TBL_USER_MASTER a "
				+ "WHERE del_flag = 'N' and is_verified  ='N' OR nvl(is_verified,'N')='N'"
				/*+ "AND modified_user IS NOT NULL "
				+ "AND modified_user! ='"
				+ currentUser.trim()
				+ "' "
				+ "UNION ALL "
				+ "SELECT a.*,last_login_date, CASE when isactive = 'N' then 'Inactive' else 'Active' end as IsActiveS ,USER_ROLE as USER_ROLE_DESC "
				+ "FROM TBL_USER_MASTER a "
				+ "WHERE del_flag = 'N'  "
				+ "AND nvl(is_verified,'N')    ='N'"*/
				+ " ";
		strSQL1 += where;

		strSQL = "select c.* from (Select B.*,ROW_NUMBER() OVER ( ORDER BY User_id )as rn from (" + strSQL1
				+ ") b) c where rn between " + startI + " and " + endI + "";
		Count = ds.getSingleValues(" select count(*) from (" + strSQL1 + ") b ");
		res = strSQL + "~" + Count;

		System.out.println(strSQL + where);

		return res;
	}

	@Override
	public List<UserMaster> Verify_UserList(String Qry) {
		List<UserMaster> userMasters=ds.getVerifyUserLIst(Qry);
		return userMasters;
	}

	@Override
	public String getEditUserQuery(String uid)
	{
		String qry = "select USER_ID,USER_NAME,USER_STATUS,USER_ROLE,CREATED_TIME,CREATED_USER,LAST_LOGIN_DATE," +
				"to_char(expiry_date, 'dd/MM/yyyy') as expiry_date,LAST_LOGIN_IP,User_status_desc,P_F_NUMBER,SOL_ID from v_userDetails where " +
					" upper(user_id) ='"+ uid.toUpperCase() +"' " ;
		return qry;
	}
	@Override
	public String getEditUserDetails(String txtUserID) {
		String sql= "SELECT  user_status||'~'||COALESCE(USER_ROLE,'')||'~'||to_char(expiry_date,'dd/mm/yyyy') ||'~'|| COALESCE((CASE when ISUNLOCKED='N' then 'No' when ISUNLOCKED='Y' then 'Yes' end ),'') as reco " +
				" FROM   TBL_USER_MASTER where   upper(user_id)='"+ txtUserID.toUpperCase() +"'";
	           String vb= " union " +
	            " SELECT user_status||'~'||COALESCE(USER_ROLE,'')||'~'||to_char(expiry_date,'dd/mm/yyyy') ||'~'|| COALESCE((CASE when ISUNLOCKED='N' then 'No' when ISUNLOCKED='Y' then 'Yes' end ),'') as reco FROM   TBL_USER_MASTER  " +
			    " WHERE    COALESCE( " +
			    " (SELECT COUNT(1) " +
			    " FROM TBL_USER_MASTER " +
			    " WHERE upper(user_id)  ='"+ txtUserID.toUpperCase() +"' " +
			    " AND is_verified in('N','R') " +
			    " ),0) = 1 " +
			    " and  upper(user_id)='"+ txtUserID.toUpperCase() +"' " ;

	           String res=ds.getSingleValues(sql);
		return res;
	}
	@Override
	public void updateEditUserMaster(String selUserRole, String selUserStatus, String txtUserID, String modifiedUser, String expiryDate, String userName) {
		// TODO Auto-generated method stub
		System.out.println("selUserRole======"+selUserRole);
		System.out.println("expiryDate======"+expiryDate+"userName"+userName);
		String sql="update tbl_user_master set is_verified='N',user_name='"+userName+"',user_role = '" + selUserRole + "',unlock_date = current_timestamp,  login_check_date=current_timestamp,user_status = '" + selUserStatus + "',expiry_date = to_date('" + expiryDate + "','DD/MM/YYYY'),modified_time=current_timestamp,modified_user='" + modifiedUser + "',isunlocked = 'Y' where  upper(user_id) = '" + txtUserID.toUpperCase() + "' ";
		ds.updateOrInsertValues(sql);

	}


	@Override
	public void updateEditUserMasterNotVerified(String selUserRole, String selUserStatus, String txtUserID,
			String modifiedUser, String txtExpiryDate, String userName) {
		// TODO Auto-generated method stub
		String sql="update tbl_user_master set is_verified='N',user_name='"+userName+"',user_role = '" + selUserRole + "',unlock_date = current_timestamp,expiry_date = to_date('" + txtExpiryDate + "','DD/MM/YYYY'),user_status = '" + selUserStatus + "',modified_time=current_timestamp,modified_user='" + modifiedUser + "',isunlocked = 'Y' where  user_status='E' and upper(user_id) = '" + txtUserID.toUpperCase() + "' ";
		ds.updateOrInsertValues(sql);

	}
	@Override
	public void insertActivityLog(String modifiedUser, String txtUserID, String activity_dec, String ip_addrss) {
		// TODO Auto-generated method stub
		String sql="insert into user_activity_log (USER_ID,USER_ID_ACTED_ON,STATUS_DATE,ACTIVITY_TYPE,ACTIVITY_DESC,IP_ADDRESS) values('" + modifiedUser + "','" + txtUserID.toUpperCase() + "',current_timestamp,'M','"+activity_dec+"','"+ip_addrss+"')";
		ds.updateOrInsertValues(sql);
	}

	@Override
	public String getVerifyQuery(String uid)
	{
		String qry = "SELECT USER_ID,USER_NAME,USER_STATUS,USER_ROLE,CREATED_TIME,CREATED_USER,LAST_LOGIN_DATE,LAST_LOGIN_IP,to_char(EXPIRY_DATE,'dd/mm/yyyy') as EXPIRY_DATE,ISUNLOCKED FROM tbl_user_master where user_id='"+ uid.toUpperCase() +"' " ;
		return qry;
	}

	@Override
	public String AcessLogQuery(int startI, int endI, String User_id,
			String From_Date,String To_Date,String adhNo,String refNo, String hdn_AppName) {
		String res = "";
		try {

		String strSQL1 = "select rownum as rn,AcessLog.* from AcessLog where User_id is not null " ;
				if (User_id != null && !(User_id.equalsIgnoreCase(""))) {
					strSQL1  += " and  User_id='"+User_id+"'";
				}
			if (adhNo != null && !(adhNo.equalsIgnoreCase(""))) {

					strSQL1  += " and  encAdhNo='"+EncryptionDecryption.encrypt(adhNo, "Miscot") +"'";
			}
			if (hdn_AppName != null && !(hdn_AppName.equalsIgnoreCase(""))) {
				strSQL1  += " and  appname='"+hdn_AppName+"'";
			}
			if (refNo != null && !(refNo.equalsIgnoreCase(""))) {
				strSQL1  += " and  REFNO='"+refNo+"'";
			}
			if (From_Date != null && !(From_Date.equalsIgnoreCase("")) && (ds.RemoveNull(To_Date).equalsIgnoreCase(""))) {
				strSQL1  += " and  Date_f=to_date('"+(From_Date)+"','DD/MM/YYYY')";
			}
			if (To_Date != null && !(To_Date.equalsIgnoreCase(""))  && (ds.RemoveNull(From_Date).equalsIgnoreCase(""))) {
				strSQL1  += " and  Date_f=to_date('"+(To_Date)+"','DD/MM/YYYY')";
			}
			if (From_Date != null && !(From_Date.equalsIgnoreCase("")) && To_Date != null && !(To_Date.equalsIgnoreCase(""))) {
				strSQL1  += " and  Date_f  between to_date('"+(From_Date)+"','DD/MM/YYYY') and to_date('"+(To_Date)+"','DD/MM/YYYY')";
			}

		String strSQL = "Select * from (" + strSQL1 + " order by DATE_f desc ) b where rn between " + startI
				+ " and " + endI + "  ";
		String Count = ds.getSingleValues(" select count(*) from (" + strSQL1 + ") b ");
		System.out.println(strSQL1);
		// strSQL = strSQL + " order by user_name ";
		res = strSQL + "~" + Count;

		System.out.println(strSQL);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return res;
	}
	@Override
	public String ActivityLogQuery(int startI, int endI, String User_id,
			String From_Date,String To_Date,String adhNo,String refNo, String hdn_AppName) {
		String res = "";
		try {
		strSQL1 = "select ActivityLog2.* from ActivityLog2 where User_id is not null " ;
				if (User_id != null && !(User_id.equalsIgnoreCase(""))) {
					strSQL1  += " and  User_id='"+User_id+"'";
				}
			if (adhNo != null && !(adhNo.equalsIgnoreCase(""))) {
				strSQL1  += " and  encAdhNo='"+EncryptionDecryption.encrypt(adhNo, "Miscot") +"'";
			}
			if (hdn_AppName != null && !(hdn_AppName.equalsIgnoreCase(""))) {
				strSQL1  += " and  appname='"+hdn_AppName+"'";
			}
			if (refNo != null && !(refNo.equalsIgnoreCase(""))) {
				strSQL1  += " and  refNo='"+refNo+"'";
			}
			if (From_Date != null && !(From_Date.equalsIgnoreCase("")) && (ds.RemoveNull(To_Date).equalsIgnoreCase(""))) {
				strSQL1  += " and  Date_f=to_date('"+(From_Date)+"','DD/MM/YYYY')";
			}
			if (To_Date != null && !(To_Date.equalsIgnoreCase(""))  && (ds.RemoveNull(From_Date).equalsIgnoreCase(""))) {
				strSQL1  += " and  Date_f=to_date('"+(To_Date)+"','DD/MM/YYYY')";
			}
			if (From_Date != null && !(From_Date.equalsIgnoreCase("")) && To_Date != null && !(To_Date.equalsIgnoreCase(""))) {
				strSQL1  += " and  Date_f  between to_date('"+(From_Date)+"','DD/MM/YYYY') and to_date('"+(To_Date)+"','DD/MM/YYYY')";
			}

			Count = ds.getSingleValues(" select count(*) from (" + strSQL1 + ") b ");
			strSQL = " select * from  (Select rownum as rn,b.* from (" + strSQL1 + "  order by DATE_F desc) b )c where rn between " + startI +
									" and " + endI ;
		res = strSQL + "~" + Count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		return res;
	}

	@Override
	public List<Activity_Log> listActivityLog(String qry, String flag) {
		List<Activity_Log> activityLogList=ds.getActivityLogLIst(qry,flag);
		return activityLogList;
	}

	@Override
	public List<ApplicationMaster> GetAllAppName() {
		String qry="select distinct (APPLICATION_NAME) as USER_ID,APPLICATION_NAME from tbl_app_user";
		List<ApplicationMaster> activityLogList=ds.getModifyApplication(qry,"getAppName");
		return activityLogList;
	}
	@Override
	public List<ApplicationMaster> GetStatus() {
		String qry="select STATUS_ID,STATUS_DES from TBL_USER_LOGIN_STATUS order by STATUS_DES ";
		List<ApplicationMaster> activityLogList=ds.getModifyApplication(qry,"getStatus");
		return activityLogList;
	}
	@Override
	public List<ApplicationMaster> GetActivityDesc() {
		String qry="select OP_TYPE,OP_DESC from TBL_MODIFICATION_OP_DESC order by OP_DESC ";
		List<ApplicationMaster> activityLogList=ds.getModifyApplication(qry,"getActivityDesc");
		return activityLogList;
	}
	@Override
	public String modifyUserList(int startI, int endI) {
		// TODO Auto-generated method stub

		strSQL1 = "SELECT * from tbl_app_user  "
				+ "WHERE USER_STATUS <> 'X'  ";
		strSQL1 += where;

		strSQL = "select c.* from (Select B.*,ROW_NUMBER() OVER ( ORDER BY User_id )as rn from (" + strSQL1
				+ ") b) c where rn between " + startI + " and " + endI + "";
		Count = ds.getSingleValues(" select count(*) from (" + strSQL1 + ") b ");
		res = strSQL + "~" + Count;

		return res;
	}
	@Override
	public List<ApplicationMaster> listModifyAppMaster(String sql, String flag) {
		// TODO Auto-generated method stub
		List<ApplicationMaster> appMaster=ds.getModifyApplication(sql,flag);
				return appMaster;
	}

	@Override
	public int update_tbl_user_master4(String userid) {
		// TODO Auto-generated method stub
		int res=ds.updateOrInsertValues("update tbl_user_master set isActive = 'N' where upper(user_id) = '" + userid.toUpperCase() + "'");
		return res;
	}

	@Override
	public void insertActivityLog1(String userid, String currentUser, String ip_addrss) {
		// TODO Auto-generated method stub
		String sql="insert into user_activity_log (USER_ID,USER_ID_ACTED_ON,STATUS_DATE,ACTIVITY_TYPE,IP_ADDRESS) values('" + currentUser + "','" +  userid.toUpperCase() + "',current_date,'R','"+ip_addrss+"')";
		ds.updateOrInsertValues(sql);
	}

	@Override
	public String UserModificationLogSearchSQL(int startI, int endI ,
			String userID,String frmDate,String toDate , String status , String acted_on_user
			)  {

		String res = "";
		String strSQL1="";
		try {
		strSQL1= "select  user_id, USER_ID_ACTED_ON ,to_char(a.STATUS_DATE,'dd-mon-yyyy hh:mi:ss AM')  as STATUS_DATE_D, STATUS_DATE, ACTIVITY_TYPE, ACTIVITY_DESC , OP_DESC, IP_ADDRESS from  user_activity_log a,TBL_MODIFICATION_OP_DESC b where b.OP_TYPE = a.activity_type";
			if (!ds.RemoveNull(userID).equalsIgnoreCase("")) {
				strSQL1+= " and a.USER_ID='"+userID+"' " ;
			}
			else if (!ds.RemoveNull(frmDate).equalsIgnoreCase("") && !ds.RemoveNull(toDate).equalsIgnoreCase("")) {
				 strSQL1+= " and trunc(a.STATUS_DATE) between to_date('"+frmDate+"','DD/MM/YYYY') and  to_date('"+toDate+"','DD/MM/YYYY') " ;
			}
			else if (!ds.RemoveNull(frmDate).equalsIgnoreCase("")) {
				 strSQL1+= " and trunc(a.STATUS_DATE)>=to_date('"+frmDate+"','DD/MM/YYYY') " ;
			}
			else if (!ds.RemoveNull(toDate).equalsIgnoreCase("")) {
				 strSQL1+= " and trunc(a.STATUS_DATE)<=to_date('"+toDate+"','DD/MM/YYYY') " ;
			}

			if (!ds.RemoveNull(status).equalsIgnoreCase("")) {
				 strSQL1+= " and a.ACTIVITY_TYPE='"+status+"' " ;
			}
			if (!ds.RemoveNull(acted_on_user).equalsIgnoreCase("")) {
				 strSQL1+= " and upper(a.USER_ID_ACTED_ON)=upper('"+acted_on_user+"') " ;
			}
			if(endI!=99999999) {
			Count = ds.getSingleValues(" select count(*) from (" + strSQL1 + ") b ");
			}
			strSQL = " select * from  (Select rownum as rn,b.* from (" + strSQL1 + "  order by STATUS_DATE desc) b )c ";
		res = strSQL+" where rn between "+startI +" and "+endI + "  ~" + Count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@Override
	public String AppModificationLogSearchSQL(int startI, int endI ,String userID,String frmDate,String toDate , String status , String acted_on_user)  {
		String res = "";
		String strSQL1="";
		try {
			strSQL1= "select a.user_id, APP_USER_ID ,APPLICATION_NAME, to_char(a.STATUS_DATE,'dd-mon-yyyy hh:mi:ss AM')  as STATUS_DATE_D, STATUS_DATE, ACTIVITY_TYPE, ACTIVITY_DESC , OP_DESC, IP_ADDRESS from  app_mod_log a, TBL_MODIFICATION_OP_DESC b, tbl_app_user c where b.OP_TYPE = a.activity_type and c.USER_ID= a.APP_USER_ID";
			if (!ds.RemoveNull(userID).equalsIgnoreCase("")) {
				strSQL1+= " and a.USER_ID='"+userID+"' " ;
			}
			else if (!ds.RemoveNull(frmDate).equalsIgnoreCase("") && !ds.RemoveNull(toDate).equalsIgnoreCase("")) {
				 strSQL1+= " and trunc(a.STATUS_DATE) between to_date('"+frmDate+"','DD/MM/YYYY') and  to_date('"+toDate+"','DD/MM/YYYY') " ;
			}
			else if (!ds.RemoveNull(frmDate).equalsIgnoreCase("")) {
				 strSQL1+= " and trunc(a.STATUS_DATE)>=to_date('"+frmDate+"','DD/MM/YYYY') " ;
			}
			else if (!ds.RemoveNull(toDate).equalsIgnoreCase("")) {
				 strSQL1+= " and trunc(a.STATUS_DATE)<=to_date('"+toDate+"','DD/MM/YYYY') " ;
			}
			if (!ds.RemoveNull(status).equalsIgnoreCase("")) {
				 strSQL1+= " and a.ACTIVITY_TYPE='"+status+"' " ;
			}
			if (!ds.RemoveNull(acted_on_user).equalsIgnoreCase("")) {
				 strSQL1+= " and upper(a.APP_USER_ID)=upper('"+acted_on_user+"') " ;
			}
			if(endI!=99999999) {
				Count = ds.getSingleValues(" select count(*) from (" + strSQL1 + ") b ");
			}

		strSQL = " select * from  (Select rownum as rn,b.* from (" + strSQL1 + "  order by STATUS_DATE desc) b )c ";
		res = strSQL+" where rn between "+startI +" and "+endI + "  ~" + Count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@Override
	public String insertApplication(ApplicationMasterBean applicationMasterBean, String lk, String sessionUserId) {
		// TODO Auto-generated method stub
		String result=null;
		int res;
		strSQL="INSERT INTO TBL_DASHBOARD(chart_id,FIELD_NAME,FIELD_VALUE,FIELD_DESC) VALUES" +
				"(2,'"+applicationMasterBean.getTxtUserID()+"',0,'"+applicationMasterBean.getTxtAppName()+"')";
		res=ds.updateOrInsertValues(strSQL);
		if(res==1)
		{
			res=0;
			strSQL="INSERT INTO TBL_DASHBOARD(chart_id,FIELD_NAME,FIELD_VALUE,FIELD_DESC) VALUES" +
					"(3,'"+applicationMasterBean.getTxtUserID()+"',0,'"+applicationMasterBean.getTxtAppName()+"')";
			res=ds.updateOrInsertValues(strSQL);
			if(res==1)
			{
				res=0;
				strSQL="INSERT INTO tbl_app_user(USER_ID,IP_ADDTRESS,APPLICATION_NAME,LICENCE_KEY,CREATED_TIME,CREATED_BY,UPDATED_TIME,UPDATED_BY,USER_STATUS,password1)     VALUES" +
						"('"+applicationMasterBean.getTxtUserID()+"','"+applicationMasterBean.getTxtIPadd()+"','"+applicationMasterBean.getTxtAppName()+"','"+lk+"',sysdate,'"+applicationMasterBean.getTxtUserID()+"'," +
								"null,null,'E','"+applicationMasterBean.getTxtPassword()+"')";
				res=ds.updateOrInsertValues(strSQL);
				if(res==1)
				{
					res=0;
					String activity_desc="Application Added User ID:"+applicationMasterBean.getTxtUserID()+" IP Address:"+applicationMasterBean.getTxtIPadd()+" Application Name:"+applicationMasterBean.getTxtAppName()+" Licence Key:"+lk +" Password:"+applicationMasterBean.getTxtPassword() ;
					/*dtcon.Qexecute("INSERT INTO ActivityLog(USER_ID,APPNAME,DATE_D,TIME_T,IP_ADDRESS,TASK_PERFORMED,DESCPTION)     " +
							"VALUES('"+sesUserID+"','SwiftDB',sysdate,'"+time+"','192.168.1.201','UserADD','created  User from SwiftDB Vault');");*/
					strSQL="insert into APP_MOD_LOG(USER_ID,APP_USER_ID,STATUS_DATE,ACTIVITY_TYPE,IP_ADDRESS,ACTIVITY_DESC) values"
							+ "('"+sessionUserId+"','"+applicationMasterBean.getTxtUserID()+"',sysdate,'A','"+applicationMasterBean.getTxtIPadd()+"','"+activity_desc+"')";
					res=ds.updateOrInsertValues(strSQL);
				}
				else
				{
					result="F";

				}


			}
			else
			{
				result="F";

			}

		}
		else
		{
			result="F";

		}
		if(res==1){
			result="S";
		}else{
			result="F";
		}

		return result;
	}

	@Override
	public String modifyAppList(String uid) {
		// TODO Auto-generated method stub
		strSQL = "select *  from tbl_app_user where User_id='"+uid+"' and USER_STATUS <> 'X'  ";
		return strSQL;
	}

	@Override
	public String modifyEditAppList(String txtUserID) {
		// TODO Auto-generated method stub
		strSQL = "select *  from tbl_app_user where User_id='"+txtUserID+"' ";
		return strSQL;
	}
	@Override
	public String editModifyApplication(ApplicationMasterBean applicationMasterBean, String sessionUserId, String activity_dec) {
		// TODO Auto-generated method stub
		String result=null;
		int res;
		strSQL="UPDATE tbl_app_user   SET " +
				//" USER_ID = '"+USER_ID+"'," +
				" IP_ADDTRESS = '"+applicationMasterBean.getTxtIPadd()+"',APPLICATION_NAME = '"+applicationMasterBean.getTxtAppName()+"'," +
				"UPDATED_TIME = sysdate, UPDATED_BY = '"+sessionUserId+"',USER_STATUS = '"+applicationMasterBean.getTxtUserStatus()+"',password1='"+applicationMasterBean.getTxtPassword()+"' WHERE USER_ID='"+applicationMasterBean.getTxtUserID()+"'" ;
		res=ds.updateOrInsertValues(strSQL);
		if(res==1)
		{
			res=0;
			strSQL="insert into APP_MOD_LOG(USER_ID,APP_USER_ID,STATUS_DATE,ACTIVITY_TYPE,IP_ADDRESS,ACTIVITY_DESC) values"
					+ "('"+sessionUserId+"','"+applicationMasterBean.getTxtUserID()+"',sysdate,'M','"+applicationMasterBean.getTxtIPadd()+"','"+activity_dec+"')";
			res=ds.updateOrInsertValues(strSQL);
			if(res==1)
			{
				res=0;
				strSQL="UPDATE TBL_DASHBOARD   SET " +
						" FIELD_DESC = '"+applicationMasterBean.getTxtAppName()+"' WHERE FIELD_NAME='"+applicationMasterBean.getTxtUserID()+"'" ;
				res=ds.updateOrInsertValues(strSQL);




			}
			else
			{
				result="F";

			}

		}
		else
		{
			result="F";

		}
		if(res>=1){
			result="S";
		}else{
			result="F";
		}
		System.out.println("result"+result);
		return result;
	}
	@Override
	public String getValuefromDual() {
		strSQL = "Select GENERATE_AADHARVAULT_TXN.nextval from dual ";
		String res=ds.getSingleValues(strSQL);
		return res;
	}
	@Override
		public List<Home> listResultSet(String sql) {
			List<Home> home=ds.listResultSet(sql);
			return home;
	}
	
	@Override
	public String GetUserName(String userId) {
		String res=ds.RemoveNull(ds.getSingleValues("select User_Name from tbl_User_master where lower(user_Id)= lower('"+ userId + "')"));
		return res;
	}
@Override
	public String checkCreatedUser(String txt_User_id) {
		// TODO Auto-generated method stub
		String CREATED_USER=ds.RemoveNull(ds.getSingleValues("select CREATED_USER from tbl_User_master where lower(user_Id)= lower('"+ txt_User_id + "')"));
		return CREATED_USER;
	}
}
