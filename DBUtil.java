package com.miscot.springmvc.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import org.springframework.stereotype.Repository;

import com.miscot.springmvc.dto.Activity_Log;
import com.miscot.springmvc.dto.ApplicationMaster;
import com.miscot.springmvc.dto.SQLColumn;
import com.miscot.springmvc.dto.UserMaster;
import com.miscot.springmvc.helper.Home;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


@Repository
public class DBUtil implements DBUtilInterface {
	public Connection conn = null;
	static String ConnectionStatus = "N";
	static String LastError = "";

	public static String ip_addr = "";
	@Autowired
    JdbcTemplate jdbcTemplate;




	public String retrieveUid(String encAdhNo) {
		// TODO Auto-generated method stub
		String query = "select uuid from tbl_vault where encAdhNo='" + encAdhNo + "'";
		String result = getSingleValues(query);
		//System.out.println("Result is" + result);
		return result;
	}

	public String getSingleValues(final String query) {
		// TODO Auto-generated method stub

		return (String) jdbcTemplate.query(query, new Object[] {}, new ResultSetExtractor<String>() {

			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				String Status = null;
				String Res = "";
				//System.out.println("Query" + query + "rs" + rs);

				if (rs != null) {
					while (rs.next()) {
						try {
							Res = RemoveNull(rs.getString(1));
							return Res;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				// set other properties

				return Res;
			}
		});

	}

	public int updateOrInsertValues(String query) {
		int i = jdbcTemplate.update(query);
		return i;
	}

	String algorithm = "DESede";
	String strPassword = "password12345678";
	public String encrypt(String Data){

		String encryptedValue = "";
		try {
		if (!Data.equalsIgnoreCase(null)) {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec key = new SecretKeySpec(strPassword.getBytes(), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(
					strPassword.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			byte[] ecrypted = cipher.doFinal(Data.getBytes());
			encryptedValue = new BASE64Encoder().encode(ecrypted);
		}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return encryptedValue;
	}

	public String decrypt(String encryptedData) {
		String decryptedValue = "";
		try {
		if (!encryptedData.equalsIgnoreCase(null)) {
			SecretKeySpec key = new SecretKeySpec(strPassword.getBytes(), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(
					strPassword.getBytes());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			byte[] output = new BASE64Decoder().decodeBuffer(encryptedData);
			byte[] decrypted = cipher.doFinal(output);
			decryptedValue = new String(decrypted);

		}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return decryptedValue;
	}
	public String RemoveNull(String Val){
		String Res = "";
		if (Val == null) {
			Res = "";
		}
		else if (Val.equalsIgnoreCase("null")) {
			Res = "";
		}
		else {
			Res = Val;
		}
		return Res;
	}

	public List<UserMaster> listUserMaster(String SQL, String flag) {

		      List <UserMaster> userMaster = jdbcTemplate.query(SQL,
		         new ResultSetExtractor<List<UserMaster>>(){

		         public List<UserMaster> extractData(
		            ResultSet rs) throws SQLException, DataAccessException {

		            List<UserMaster> list = new ArrayList<UserMaster>();
		            while(rs.next()){
		            	UserMaster userMaster = new UserMaster();
		            	if(flag.equalsIgnoreCase("checkExistingUser"))
		            	{
		            		userMaster.setUSER_ID (rs.getString("USER_ID"));
			            	userMaster.setUSER_NAME(rs.getString("USER_NAME"));
			            	userMaster.setUSER_STATUS (rs.getString("USER_STATUS"));
			            	userMaster.setUSER_ROLE (rs.getString("USER_ROLE"));
			            	userMaster.setUSER_ROLE_DESC(rs.getString("USER_ROLE_DESC"));
			            	userMaster.setCREATED_TIME (rs.getString("CREATED_TIME"));
			            	userMaster.setCREATED_USER (rs.getString("CREATED_USER"));
			            	userMaster.setLAST_LOGIN_DATE (rs.getString("LoginDate"));
			            	userMaster.setLAST_LOGIN_IP (rs.getString("LAST_LOGIN_IP"));
			            	userMaster.setISACTIVE (rs.getString("Isactive"));
			            	userMaster.setISVERIFIED(rs.getString("is_verified"));
			               list.add(userMaster);
		            	}
		            	else if(flag.equalsIgnoreCase("searchUser"))
		            	{
		            		//userMaster.setUSER_ID ("<a href=EditUser.jsp?action=Edit&uid=" + rs.getString("USER_ID") + ">" + rs.getString("USER_ID") + "</a>");
		            		userMaster.setUSER_ID ("<a href=EditUser?action=Edit&uid=" + rs.getString("USER_ID") + ">" + rs.getString("USER_ID") + "</a>");
		            		userMaster.setUSER_NAME(rs.getString("USER_NAME"));
		            		userMaster.setPASSWORD_RESET("<a href=passwordreset.jsp?action=user&uid=" + rs.getString("USER_ID") + ">Reset</a>");

		            		userMaster.setUSER_STATUS (rs.getString("user_status_desc"));

		    				try {
								if (RemoveNull(rs.getString("IS_VERIFIED")).equals("Y"))
								{
									userMaster.setISVERIFIED("Verified");
								} else
									try {
										if (RemoveNull(rs.getString("IS_VERIFIED")).equals("N"))
										{
											userMaster.setISVERIFIED("Pending");
										} else
											try {
												if (RemoveNull(rs.getString("IS_VERIFIED")).equals("R"))
												{
													userMaster.setISVERIFIED("Rejected");
												}
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		    				userMaster.setUSER_ROLE (rs.getString("USER_ROLE"));
		    				userMaster.setUSER_ROLE_DESC(rs.getString("USER_ROLE_DESC"));
		    				userMaster.setCREATED_TIME (rs.getString("CREATED_TIME"));
		    				userMaster.setCREATED_USER (rs.getString("CREATED_USER"));
		    				userMaster.setLAST_LOGIN_DATE (rs.getString("LAST_LOGIN_DATE"));
		    				userMaster.setLAST_LOGIN_IP (rs.getString("LAST_LOGIN_IP"));
		    				userMaster.setLOGIN_STATUS(rs.getString("ISACTIVES"));
		    				userMaster.setLOGIN_RESET(rs.getString("Reset"));
		    				list.add(userMaster);
		            	}
		            	else if(flag.equalsIgnoreCase("getEditUser")) {

		            		userMaster.setUSER_ID (rs.getString("USER_ID"));
		            		userMaster.setUSER_NAME(rs.getString("USER_NAME"));
		            		userMaster.setUSER_STATUS (rs.getString("USER_STATUS"));
		            		userMaster.setUSER_ROLE (rs.getString("USER_ROLE"));
		            		userMaster.setCREATED_TIME (rs.getString("CREATED_TIME"));
		            		userMaster.setCREATED_USER (rs.getString("CREATED_USER"));
		            		userMaster.setLAST_LOGIN_DATE (rs.getString("LAST_LOGIN_DATE"));
		            		userMaster.setEXPIRY_DATE(rs.getString("expiry_date"));
		            		userMaster.setLAST_LOGIN_IP (rs.getString("LAST_LOGIN_IP"));
		            		userMaster.setUser_status_desc(rs.getString("User_status_desc"));
		            		try {
								userMaster.setP_F_NNUMBER(RemoveNull(rs.getString("P_F_NUMBER")));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            		try {
								userMaster.setSOL_ID(RemoveNull(rs.getString("SOL_ID")));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            		list.add(userMaster);
		            	}
		            	else if(flag.equalsIgnoreCase("verifyUserList"))
		            	{
		            		userMaster.setUSER_ID("<a href=#  onclick=display_user('"+rs.getString("USER_ID")+"')>"+rs.getString("USER_ID")+"</a>");
			    			userMaster.setUSER_NAME(rs.getString("USER_NAME"));
			    			if (rs.getString("user_status").equals("E"))
			    			{
			    				userMaster.setUSER_STATUS ("Enabled");
			    			}
			    			else if (rs.getString("user_status").equals("D"))
			    			{
			    				userMaster.setUSER_STATUS ("Disabled");
			    			}
			    			userMaster.setUSER_ROLE (rs.getString("USER_ROLE"));
			    			userMaster.setUSER_ROLE_DESC (rs.getString("USER_ROLE_DESC"));
			    			userMaster.setCREATED_TIME (rs.getString("CREATED_TIME"));
			    			userMaster.setCREATED_USER (rs.getString("CREATED_USER"));
			    			userMaster.setLAST_LOGIN_DATE (rs.getString("LAST_LOGIN_DATE"));
			    			userMaster.setLAST_LOGIN_IP (rs.getString("LAST_LOGIN_IP"));
			    			userMaster.setLOGIN_STATUS(rs.getString("ISACTIVES"));
			    			userMaster.setMODIFIED_USER(rs.getString("modified_user"));
			    			userMaster.setMODIFIED_TIME(rs.getString("modified_time"));
			    			userMaster.setUSER_HISTORY("<a href=# onclick=chkHistory('"+rs.getString("USER_id")+"')>View History</a>");
			    			userMaster.setUSER_VERIFY("<a href=# onclick=chkVerify('"+rs.getString("USER_id")+"')>Verify</a>");
			    			list.add(userMaster);
		            	}
		            	else if(flag.equalsIgnoreCase("getVerifyUser"))
		            	{
		            			userMaster.setUSER_ID(rs.getString("USER_ID"));
		            			userMaster.setUSER_NAME(rs.getString("USER_NAME"));
		            			userMaster.setUSER_STATUS (rs.getString("user_status"));
		            			userMaster.setUSER_ROLE (rs.getString("USER_ROLE"));
		            			userMaster.setCREATED_TIME (rs.getString("CREATED_TIME"));
		            			userMaster.setCREATED_USER (rs.getString("CREATED_USER"));
		            			userMaster.setLAST_LOGIN_DATE (rs.getString("LAST_LOGIN_DATE"));
		            			userMaster.setLAST_LOGIN_IP (rs.getString("LAST_LOGIN_IP"));
		            			userMaster.setEXPIRY_DATE(rs.getString("EXPIRY_DATE"));
		            			userMaster.setISUNLOCKED(rs.getString("ISUNLOCKED"));
		            			list.add(userMaster);
		            	}
		            	else if(flag.equalsIgnoreCase("UserAccessLog"))
		            	{
		            			userMaster.setUSER_ID(RemoveNull(rs.getString("USER_ID")));
		            			userMaster.setLOGIN_DATE(RemoveNull(rs.getString("LOGIN_DATE_D")));
		            			userMaster.setIP_ADDRESS(RemoveNull(rs.getString("LOGIN_IP")));
		            			userMaster.setUSER_NAME(RemoveNull(rs.getString("USER_NAME")));
		            			userMaster.setSTATUS_DES(RemoveNull(rs.getString("STATUS_DES")));
		            			userMaster.setUSER_NAME(RemoveNull(rs.getString("USER_NAME")));
		            			list.add(userMaster);
		            	}
		            	else if(flag.equalsIgnoreCase("UserModificationLog"))
		            	{
		            		userMaster.setUSER_ID(RemoveNull(rs.getString("USER_ID")));
		            		userMaster.setUSER_ID_ACTED_ON(RemoveNull(rs.getString("USER_ID_ACTED_ON")));
		            		userMaster.setACTION_DATE(RemoveNull(rs.getString("STATUS_DATE_D")));
		            		userMaster.setACTION_DESCRIPTION(RemoveNull(rs.getString("ACTIVITY_DESC")));
		            		userMaster.setOPERATION(RemoveNull(rs.getString("OP_DESC")));
		            		userMaster.setIP_ADDRESS(RemoveNull(rs.getString("IP_ADDRESS")));
		            			list.add(userMaster);
		            	}
		            	else if(flag.equalsIgnoreCase("AppModificationLog"))
		            	{
		            		userMaster.setUSER_ID(RemoveNull(rs.getString("USER_ID")));
		            		userMaster.setUSER_ID_ACTED_ON(RemoveNull(rs.getString("APP_USER_ID")));
		            		userMaster.setACTION_DATE(RemoveNull(rs.getString("STATUS_DATE_D")));
		            		userMaster.setACTION_DESCRIPTION(RemoveNull(rs.getString("ACTIVITY_DESC")));
		            		userMaster.setOPERATION(RemoveNull(rs.getString("OP_DESC")));
		            		userMaster.setIP_ADDRESS(RemoveNull(rs.getString("IP_ADDRESS")));
		            			list.add(userMaster);
		            	}

		            }
		            return list;
		         }
		      });
		   return userMaster;
		}
	public List<UserMaster> getVerifyUserLIst(String SQL) {

	      List <UserMaster> userMaster = jdbcTemplate.query(SQL,
	         new ResultSetExtractor<List<UserMaster>>(){

	         public List<UserMaster> extractData(
	            ResultSet rs) throws SQLException, DataAccessException {

	            List<UserMaster> list = new ArrayList<UserMaster>();
	            while(rs.next()){
	            	UserMaster userMaster = new UserMaster();
	            	userMaster.setUSER_ID("<a href=#  onclick=display_user('"+rs.getString("USER_ID")+"')>"+rs.getString("USER_ID")+"</a>");
	    			//dto.setUSER_ID (rs.getString("USER_ID"));
	            	userMaster.setUSER_NAME(rs.getString("USER_NAME"));
	    			if (rs.getString("user_status").equals("E"))
	    			{
	    				userMaster.setUSER_STATUS ("Enabled");
	    			}
	    			else if (rs.getString("user_status").equals("D"))
	    			{
	    				userMaster.setUSER_STATUS ("Disabled");
	    			}
	    			userMaster.setUSER_ROLE (rs.getString("USER_ROLE"));
	    			userMaster.setUSER_ROLE_DESC (rs.getString("USER_ROLE_DESC"));
	    			userMaster.setCREATED_TIME (rs.getString("CREATED_TIME"));
	    			userMaster.setCREATED_USER (rs.getString("CREATED_USER"));
	    			userMaster.setLAST_LOGIN_DATE (rs.getString("LAST_LOGIN_DATE"));
	    			userMaster.setLAST_LOGIN_IP (rs.getString("LAST_LOGIN_IP"));
	    			userMaster.setLOGIN_STATUS(rs.getString("ISACTIVES"));
	    			userMaster.setMODIFIED_USER(rs.getString("modified_user"));
	    			userMaster.setMODIFIED_TIME(rs.getString("modified_time"));
	    			userMaster.setUSER_HISTORY("<a href=# onclick=chkHistory('"+rs.getString("USER_id")+"')>View History</a>");
	    			userMaster.setUSER_VERIFY("<a href=# onclick=chkVerify('"+rs.getString("USER_id")+"')>Verify</a>");

	    			list.add(userMaster);
	            }
	            return list;
	         }
	      });
	   return userMaster;
	}
	/*public ResultSet get_ResultSet(String query) {
		return jdbcTemplate.query(
				query,
				new ResultSetExtractor<ResultSet>() {


					public ResultSet extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						while (rs.next()) {

						}
						return rs;
					}
				});

	}*/
	@Override
	public List<Activity_Log> getActivityLogLIst(String SQL,String flg) {

	      List <Activity_Log> activityLog = jdbcTemplate.query(SQL,
	         new ResultSetExtractor<List<Activity_Log>>(){

	         public List<Activity_Log> extractData(
	            ResultSet rs) throws SQLException, DataAccessException {

	            List<Activity_Log> list = new ArrayList<Activity_Log>();
	            while(rs.next()){
	            	Activity_Log activityLog = new Activity_Log();
	            	activityLog.setUSER_ID(RemoveNull(rs.getString("USER_ID")));
					activityLog.setAPPNAME(RemoveNull(rs.getString("APPNAME")));
	            	activityLog.setDATE(RemoveNull(rs.getString("DATE_D")));
					activityLog.setTIME(RemoveNull(rs.getString("TIME_T")));
					activityLog.setIP_ADDRESS(RemoveNull(rs.getString("IP_ADDRESS")));
					activityLog.setTASK_PERFORMED(RemoveNull(rs.getString("TASK_PERFORMED")));
					activityLog.setDESCPTION(RemoveNull(rs.getString("DESCPTION")));
					activityLog.setREFNO(RemoveNull(rs.getString("REFNO")));
	    			list.add(activityLog);
	            }
	            return list;
	         }
	      });
	   return activityLog;
	}
	@Override
	public int invalidate_user_session1(JdbcTemplate jdbcTemplate2, String user_id) {
		int res=jdbcTemplate2.update("update tbl_user_master set isActive = 'N' where user_id = '" +user_id +"'");
		return res;
	}

	@Override
	public int invalidate_user_session2(JdbcTemplate jdbcTemplate2, String user_id, String User_IP) {
		int res=jdbcTemplate2.update("insert into tbl_audit_login (user_id,login_date,login_status,LOGIN_IP) values('" + user_id  +"',current_date,'O','"+User_IP +"')");
		return res;
	}


	public List <SQLColumn> getRSMetaData(String Sql) {
		List <SQLColumn> column =jdbcTemplate.query(Sql,new ResultSetExtractor<List <SQLColumn>>() {

		    @Override
		    public List <SQLColumn> extractData(ResultSet rs) throws SQLException, DataAccessException {
		    	List <SQLColumn> columns= new ArrayList<SQLColumn>();
		        ResultSetMetaData rsmd = rs.getMetaData();
		    int columnCount = rsmd.getColumnCount();
		    for(int i = 1 ; i <= columnCount ; i++){
		        SQLColumn column = new SQLColumn();
		    column.setName(rsmd.getColumnName(i));
		    column.setAutoIncrement(rsmd.isAutoIncrement(i));
		    column.setType(rsmd.getColumnTypeName(i));
		    column.setTypeCode(rsmd.getColumnType(i));
		    //column.setTableName(sqlTable.getName().toUpperCase());
		    columns.add(column);
		    }

		    return columns;
		}
		});
		return column;
	}

	@Override
	public List<ApplicationMaster> getModifyApplication(String SQL, String flag) {
		// TODO Auto-generated method stub
		   List <ApplicationMaster> appMaster = jdbcTemplate.query(SQL,
			         new ResultSetExtractor<List<ApplicationMaster>>(){

			         public List<ApplicationMaster> extractData(
			            ResultSet rs) throws SQLException, DataAccessException {

			            List<ApplicationMaster> list = new ArrayList<ApplicationMaster>();
			            while(rs.next()){
			            	ApplicationMaster dto = new ApplicationMaster();
			            	if(flag.equalsIgnoreCase("modifyUser"))
			            	{
			            		dto.setUSER_ID("<a href=#  onclick=display_user('"+rs.getString("USER_ID")+"')>"+rs.getString("USER_ID")+"</a>");
			    				dto.setACTION ("<a href=#  onclick=doDelete('"+rs.getString("USER_ID")+"')>Delete</a>");
			    				if (rs.getString("USER_STATUS").equals("E"))
			    				{
			    					dto.setUSER_STATUS ("Enabled");
			    				}
			    				else if (rs.getString("user_status").equals("D"))
			    				{
			    					dto.setUSER_STATUS ("Disabled");
			    				}
			    				else if (rs.getString("user_status").equals("L"))
			    				{
			    					dto.setUSER_STATUS ("Locked");
			    				}
			    				else if (rs.getString("user_status").equals("X"))
			    				{
			    					dto.setUSER_STATUS ("Deleted");
			    				}
			    				dto.setIP_ADDTRESS (rs.getString("IP_ADDTRESS"));
			    				dto.setAPPLICATION_NAME (rs.getString("APPLICATION_NAME"));
			    				dto.setCREATED_TIME (rs.getString("CREATED_TIME"));
			    				dto.setLICENCE_KEY (rs.getString("LICENCE_KEY"));
			    				dto.setCREATED_TIME (rs.getString("CREATED_TIME"));
			    				dto.setCREATED_BY (rs.getString("CREATED_BY"));
			    				dto.setUPDATED_TIME(rs.getString("UPDATED_TIME"));
			    				dto.setUPDATED_BY(rs.getString("UPDATED_BY"));
			    				list.add(dto);
			            	}
			            	else if(flag.equalsIgnoreCase("getAppName"))
			            	{
			            		dto.setUSER_ID(rs.getString("USER_ID"));
			    				dto.setAPPLICATION_NAME (rs.getString("APPLICATION_NAME"));
			    				list.add(dto);
			            	}
			            	else if(flag.equalsIgnoreCase("getStatus"))
			            	{
			            		dto.setSTATUS_ID(rs.getString("STATUS_ID"));
			    				dto.setSTATUS_DES (rs.getString("STATUS_DES"));
			    				list.add(dto);
			            	}
			            	else if(flag.equalsIgnoreCase("getActivityDesc"))
			            	{
			            		dto.setOP_TYPE(rs.getString("OP_TYPE"));
			    				dto.setOP_DESC (rs.getString("OP_DESC"));
			    				list.add(dto);
			            	}
			            	else if(flag.equalsIgnoreCase("modifyAppEditUser"))
			            	{
			            		//String res=rs.getString("USER_ID")+"~"+rs.getString("IP_ADDTRESS")+"~"+rs.getString("APPLICATION_NAME")+"~"+rs.getString("user_status")+"~"+rs.getString("password1");
			            		dto.setUSER_ID(rs.getString("USER_ID"));
			    				dto.setIP_ADDTRESS (rs.getString("IP_ADDTRESS"));
			    				dto.setAPPLICATION_NAME(rs.getString("APPLICATION_NAME"));
			    				dto.setUSER_STATUS(rs.getString("user_status"));
			    				dto.setPassword1(rs.getString("password1"));
			    				list.add(dto);
			            	}

			            }
			            return list;
			         }
			      });
			   return appMaster;
	}
	@Override
		public List<Home> listResultSet(String sql) {
			List <Home> home = jdbcTemplate.query(sql,
			         new ResultSetExtractor<List<Home>>(){

			         public List<Home> extractData(
			            ResultSet rs) throws SQLException, DataAccessException {

			            List<Home> list = new ArrayList<Home>();
			            while(rs.next()){
			            	Home home = new Home();
			            	home.setFIELD_NAME(rs.getString("FIELD_NAME"));
			            	home.setTOTAL(rs.getString("FIELD_VALUE"));
			               list.add(home);
			            }
			            return list;
			         }
			      });

			return home;
	}
}
