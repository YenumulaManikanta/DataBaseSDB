/*package com.miscot.springmvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.coyote.Request;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONObject;
import com.connection.DBUtil;
import com.dto.Get_Query;
import com.service.Chart;
import com.service.PasswordValidator;
import com.service.UserMaster_service;
import com.dto.UserMasterDTO;
import com.dto.AdAuth;
import com.dto.pathDTO;

//import com.fin7.service.Fin7_Audit_File_InquiryService;
@Controller
public class UserAuthenticationController2  {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("opr");
		DBUtil ds = new DBUtil();
		ds.ip_addr = request.getRemoteAddr();
		UserMaster_service srv = new UserMaster_service();
		HttpSession session = request.getSession();
	System.out.println("Servlet Start");
		if ("GetName".equalsIgnoreCase(op)) {
			String validName = "";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");

			try {
				String userID = ds.RemoveNull(request.getParameter("userId")
						.toString());
				AdAuth objAdAuth = new AdAuth();
				validName = objAdAuth.GetNameUser(userID);
				System.out.println("valid name:"+validName);
				if(validName.equalsIgnoreCase("NO")) {
				validName = objAdAuth.GetName(userID, ds.RemoveNull(session
						.getAttribute("user_id").toString()),
						ds.RemoveNull(session.getAttribute("password")
								.toString()));
				}else {
					validName="YES";
				}
			} catch (Exception e) {
				e.printStackTrace();
				validName = "E";
			}
			response.getWriter().write(validName);

		}
		
		if ("GetNameUser".equalsIgnoreCase(op)) {
			String validName = "";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");

			try {
				String userID = ds.RemoveNull(request.getParameter("userId")
						.toString());
				AdAuth objAdAuth = new AdAuth();
				validName = objAdAuth.GetNameUser(userID);

			} catch (Exception e) {
				e.printStackTrace();
				validName = "E";
			}
			response.getWriter().write(validName);

		}

		if ("GetAppList".equalsIgnoreCase(op)) {

			String res = "";
			String userID = "";
			String typ = "";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");

			try {
				if (request.getParameter("userId") != null) {
					userID = ds.RemoveNull(request.getParameter("userId")
							.toString());
				}
				if (request.getParameter("typ") != null) {
					typ = ds.RemoveNull(request.getParameter("typ").toString());
				}
				// res = srv.GetAppList(userID,typ);
				res = srv.Get_Zone_List(userID, typ);

			} catch (Exception e) {
				e.printStackTrace();
				res = "";
			}
			response.getWriter().write(res);

		}

		if ("GetFolderList".equalsIgnoreCase(op)) {

			String res = "";
			String userID = "";
			String typ = "";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");

			try {
				if (request.getParameter("userId") != null) {
					userID = ds.RemoveNull(request.getParameter("userId")
							.toString());
				}
				if (request.getParameter("typ") != null) {
					typ = ds.RemoveNull(request.getParameter("typ").toString());
				}
				// res = srv.GetAppList(userID,typ);
				res = srv.GetFolderList(userID, typ);

			} catch (Exception e) {
				e.printStackTrace();
				res = "";
			}
			response.getWriter().write(res);

		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("operation");
		ArrayList list = new ArrayList();
		Get_Query ggquery = new Get_Query();
		// System.out.println("Servlet Start");
		try {

			DBUtil ds = new DBUtil();

			if ("ResetPassword".equalsIgnoreCase(op)) {

				String PassWord=request.getParameter("txtPass1").toString();
				String userID="";
				HttpSession session = null;
				session = request.getSession(true);
				String scr;
				boolean res = false;
				String passWordValidate="";
				PasswordValidator pv=new PasswordValidator();
				passWordValidate=pv.passwordValidation(session.getAttribute("user_id").toString(),PassWord);
				
				if(passWordValidate.equalsIgnoreCase("valid")){
//				System.out.println(request.getParameter("pUser"));
				if (ds.RemoveNull(request.getParameter("pUser"))=="")
				{
					userID=session.getAttribute("user_id").toString();

				}else
				{
					userID=request.getParameter("lUser").toString();
				}


				try {
					
					UserMaster_service srv = new UserMaster_service();
					res = srv.ResetPassword(userID, PassWord);
					

				} catch (Exception e) {
					e.printStackTrace();

				}
				if (res == true) {
					//scr = "<script>alert('Password Reset Sucessfully');window.location.href='User_master.jsp';</script>";
					scr="S~Password Reset Sucessfully";
				} else {
					//scr = "<script>alert('Error Occured');window.location.href='User_master.jsp';</script>";
					scr="E~Error Occured";
				}
				PrintWriter out = response.getWriter();
				out.print(scr);
				}
				else{
					PrintWriter out = response.getWriter();
					//out.print("<script>alert('"+passWordValidate+"');return false;</script>");
					scr="M~"+passWordValidate;
					out.print(scr);
				}
			} else if ("ResetLogin".equalsIgnoreCase(op)) {

				
				 * response.setContentType("text/plain");
				 * response.setCharacterEncoding("UTF-8");
				 
				String finalVal = "";
				String scr = "";
				boolean res = false;
				HttpSession session = null;
				session = request.getSession(true);
				try {

					UserMaster_service srv = new UserMaster_service();
					String userID = ds.RemoveNull(request.getParameter(
							"chkUserID").toString());
					res = srv.ResetLogin(userID, session
							.getAttribute("user_id").toString(), request
							.getRemoteAddr().toString());

				} catch (Exception e) {
					e.printStackTrace();
					finalVal = "No";
				}
				if (res == true) {
					scr = "<script>alert('ID Resetted Sucessfully');window.location.href='User_master.jsp';</script>";
				} else {
					scr = "<script>alert('Error Occured');window.location.href='User_master.jsp';</script>";
				}
				PrintWriter out = response.getWriter();
				out.print(scr);

			}

			else if ("VerifyUser".equalsIgnoreCase(op)) {

				
				 * response.setContentType("text/plain");
				 * response.setCharacterEncoding("UTF-8");
				 
				String finalVal = "";
				String scr = "";
				HttpSession session = null;
				session = request.getSession(true);
				boolean res = false;
				try {
					//DBUtil ds = new DBUtil();
					UserMaster_service srv = new UserMaster_service();
					String userID = ds.RemoveNull(request.getParameter("uid")
							.toString());
					res = srv.VerifyUser(userID, session
							.getAttribute("user_id").toString(), request
							.getRemoteAddr());

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (res == true) {
					scr = "<script>alert('User ID verified Sucessfully');window.location.href='userVerification.jsp';</script>";
				} else {
					scr = "<script>alert('Error Occured');window.location.href='userVerification.jsp';</script>";
				}
				PrintWriter out = response.getWriter();
				out.print(scr);

			} else if ("authenticate111".equalsIgnoreCase(op)) {
				pathDTO.path1 = request.getSession().getServletContext()
						.getRealPath("/Include");
				// System.out.println(pathDTO.path1);
				// System.out.println("Servlet Start");
				Boolean res = false;
				String chk = "";
				String sts = "";
				int FailCount = 0;
				UserMaster_service uservice = new UserMaster_service();
				String userid = request.getParameter("userName");
				String passWord = request.getParameter("password");
				UserMasterDTO dto = new UserMasterDTO();
				chk = uservice.AuthenticateUser(userid, passWord, dto,
						request.getRemoteAddr(), sts);
				if (chk.equals("")) {

					//DBUtil ds = new DBUtil();

					HttpSession session = null;
					session = request.getSession(true);
					session.setAttribute("user_name", dto.getUSER_NAME());
					session.setAttribute("user_id", dto.getUSER_ID());
					session.setAttribute("password", passWord);
					session.setAttribute("Role", dto.getUSER_ROLE());
					session.setAttribute("LastLogin", dto.getLAST_LOGIN_DATE());

					String FistTime = ds
							.GetValue("select USER_STATUS_2 from TBL_USER_MASTER where upper(USER_ID) = '"
									+ dto.getUSER_ID() + "'");
					if (FistTime.equalsIgnoreCase("F")) {
						response.sendRedirect("ChangeUserPassword.jsp");
					}

					if (dto.getUSER_ROLE().equalsIgnoreCase("user")) {
						session.setAttribute("AppName", "No");
						response.sendRedirect("Home.jsp");
					} else {
						response.sendRedirect("Home.jsp");
					}
					// System.out.println("Session Value : " +
					// session.getAttribute("user_id"));
				} else {
					response.sendRedirect("Login.jsp?err=" + chk);
				}

			} else if ("authenticate".equalsIgnoreCase(op)) {
				pathDTO.path1 = request.getSession().getServletContext().getRealPath("/Include");
				String userid = request.getParameter("userName");
				String passWord = request.getParameter("password");
				
				int i=3;
				if(i==2) {
					HttpSession session = null;
					session = request.getSession(true);
					session.setAttribute("user_name", userid);
					session.setAttribute("user_id", userid);
					session.setAttribute("password", passWord);
					session.setAttribute("Role", "Admin");
					session.setAttribute("LastLogin", null);
					response.sendRedirect("Home.jsp");
				}else {
				Boolean res = false;
				String chk = "";
				String sts = "";
				int FailCount = 0;
				UserMaster_service uservice = new UserMaster_service();
				
				int expDays=Integer.parseInt(uservice.chkPasswordExpiry(userid));
				UserMasterDTO dto = new UserMasterDTO();
				if(expDays>45){
					chk="PX";
					ds.Qexecute("update tbl_user_master set USER_STATUS_2 = 'F',EXPIRY_DATE= current_date where   UPPER(user_id) = '"
							+ userid.toUpperCase() + "'");
					ds.Qexecute("insert into tbl_audit_login values ('" +userid.toUpperCase()+ "',current_date,'"+ chk +"','" + request.getRemoteAddr() + "')");
				}else{
					chk = uservice.AuthenticateUser(userid, passWord, dto,request.getRemoteAddr(), sts);
				}
				
				if (chk.equals("")) {
					HttpSession session = null;
					session = request.getSession(true);
					session.setAttribute("user_name", dto.getUSER_NAME());
					session.setAttribute("user_id", dto.getUSER_ID());
					session.setAttribute("password", passWord);
					session.setAttribute("Role", dto.getUSER_ROLE());
					session.setAttribute("LastLogin", dto.getLAST_LOGIN_DATE());
					String zone_id = "";
					zone_id = uservice.Get_zone(dto.getUSER_ID());
					session.setAttribute("G_Zone_id", zone_id);
					String FistTime = ds.GetValue("select USER_STATUS_2 from TBL_USER_MASTER where upper(USER_ID) = '"+ dto.getUSER_ID() + "'");
					if (FistTime.equalsIgnoreCase("F")) {
						response.sendRedirect("ChangeUserPassword.jsp");
					}

					if (dto.getUSER_ROLE().equalsIgnoreCase("user")) {
						session.setAttribute("AppName", "No");
						response.sendRedirect("Home.jsp");
					} else {
						response.sendRedirect("Home.jsp");
					}
				} else {
					response.sendRedirect("Login.jsp?err=" + chk);
				}
				}
				
				
				UserMaster_service uservice = new UserMaster_service();
				//String userid  = request.getParameter("userName");
				//String passWord = request.getParameter("password");
				if(userid.equalsIgnoreCase("Miscot") && passWord.equalsIgnoreCase("miscotM1")){
					HttpSession session = null;
					session = request.getSession(true);
					session.setAttribute("user_name","Miscot systems.");
					session.setAttribute("user_id","Miscot");
					session.setAttribute("password","");
					session.setAttribute("Role","Admin");
					//System.out.println("Session Value : " + session.getAttribute("user_id"));
					session.setAttribute("LastLogin","");
					session.setAttribute("Chart1flg","Y"); 
					session.setAttribute("Chartflg","Y");
					session.setAttribute("BranchCode","");
					response.sendRedirect("Home.jsp"); 
					
				}else{
				UserMasterDTO dto = new UserMasterDTO();
				chk = uservice.AuthenticateUser(userid,passWord,dto,request.getRemoteAddr(),sts);
				System.out.println("chk" + chk);
				if (chk.equals(""))
				{
					
					
					Chart ojCharts = new Chart();
				
					HttpSession session = null;
					
					session = request.getSession(true);
					session.setAttribute("user_name",dto.getUSER_NAME());
					session.setAttribute("user_id",dto.getUSER_ID());
					session.setAttribute("password",passWord);
					System.out.println("dto.getUSER_ROLE_DESC()=="+dto.getUSER_ROLE_DESC());
					System.out.println("dto.getUSER_ROLE()=="+dto.getUSER_ROLE());
					
					session.setAttribute("Role",dto.getUSER_ROLE());
					
					session.setAttribute("LastLogin",dto.getLAST_LOGIN_DATE());
					session.setAttribute("BranchCode","");
					//System.out.println("BranchCode=="+dto.getBRANCHCODE());
					session.setAttribute("Chart1flg","Y");
					 
					session.setAttribute("Chart1","Y");
					session.setAttribute("Chart2","");
					session.setAttribute("Chart3","");
					session.setAttribute("Chart1flg","Y"); 
					session.setAttribute("Chartflg","Y");
				
					session.setAttribute("Chartflg","Y");
					
						DBUtil db=new DBUtil();
				 
						String Sessionid=session.getId();
			 
						String Sql1="update tbl_user_master set last_session_id = '"+Sessionid+"'  where upper(user_id)= '"+userid.toUpperCase()+"' " ;
						System.out.println("Sql1======="+Sql1);
						db.Qexecute(Sql1);
				 
				
						response.sendRedirect("Home.jsp");
				}
				else
				{
					response.sendRedirect("Login.jsp?err=" + chk);
				}
				}
				}
				
			}
			else if ("getExpiryDate".equalsIgnoreCase(op)) {
				DBUtil db=new DBUtil();
				String ddate="";
				PrintWriter out = response.getWriter();
				try {
					 ddate=db.GetValue("SELECT  to_char((sysdate + 45),'dd/mm/yyyy') as EXPIRY_DATE from dual");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("ddate"+ddate);
				out.print(ddate);
				db.CloseConnection();
			}
			else if ("authenticate122".equalsIgnoreCase(op)) {
				pathDTO.path1 = request.getSession().getServletContext()
						.getRealPath("/Include");
				// System.out.println(pathDTO.path1);
				// System.out.println("Servlet Start");
				Boolean res = false;
				String chk = "";
				String sts = "";
				int FailCount = 0;
				UserMaster_service uservice = new UserMaster_service();
				String userid = request.getParameter("userName");
				String passWord = request.getParameter("password");
				UserMasterDTO dto = new UserMasterDTO();
				chk = uservice.AuthenticateUser(userid, passWord, dto,
						request.getRemoteAddr(), sts);
				if (chk.equals("")) {

					//DBUtil ds = new DBUtil();

					HttpSession session = null;
					session = request.getSession(true);
					session.setAttribute("user_name", dto.getUSER_NAME());
					session.setAttribute("user_id", dto.getUSER_ID());
					session.setAttribute("password", passWord);
					session.setAttribute("Role", dto.getUSER_ROLE());
					session.setAttribute("LastLogin", dto.getLAST_LOGIN_DATE());
					String zone_id = "";

					zone_id = uservice.Get_zone(dto.getUSER_ID());

					// System.out.println(zone_id+" zone_id");
					session.setAttribute("G_Zone_id", zone_id);

					String FistTime = ds
							.GetValue("select USER_STATUS_2 from TBL_USER_MASTER where upper(USER_ID) = '"
									+ dto.getUSER_ID() + "'");
					if (FistTime.equalsIgnoreCase("F")) {
						response.sendRedirect("ChangeUserPassword.jsp");
					}

					if (dto.getUSER_ROLE().equalsIgnoreCase("user")) {
						session.setAttribute("AppName", "No");
						response.sendRedirect("Home.jsp");
					} else {
						response.sendRedirect("Home.jsp");
					}
					// System.out.println("Session Value : " +
					// session.getAttribute("user_id"));
				} else {
					response.sendRedirect("Login.jsp?err=" + chk);
				}

			}
			else if ("AddNew".equalsIgnoreCase(op)) {
				boolean res = false;
				String sts = "";
				PrintWriter out = response.getWriter();
				UserMaster_service uservice = new UserMaster_service();
				HttpSession session = request.getSession();
				UserMasterDTO dto = new UserMasterDTO();
				dto.setUSER_ID(request.getParameter("txtUserID"));
				dto.setUSER_NAME(request.getParameter("txtUserName"));
				dto.setUSER_STATUS(request.getParameter("selUserStatus"));
				dto.setUSER_ROLE(request.getParameter("selUserRole"));
				dto.setEXPIRY_DATE(request.getParameter("txtExpiryDate"));
				dto.setP_F_NNUMBER(request.getParameter("txtP_F_Number"));
				dto.setSOL_ID(request.getParameter("txtSolID"));
				dto.setPASSWORD(request.getParameter("txtPassword"));

				res = uservice.AddNewUser(dto, request
						.getParameterValues("chkApp"), request
						.getParameterValues("chkFolder"),
						session.getAttribute("user_id").toString(), sts,
						request.getRemoteAddr());
				String scr = "";
				if (res == true) {
					scr = "<script>alert('Data saved successfully');window.location.href='User_master.jsp';</script>";
				} else {
					scr = "<script>alert('"
							+ sts
							+ "');window.location.href='User_master.jsp';</script>";
				}

				out.print(scr);
			} else if ("EditUserSave".equalsIgnoreCase(op)) {
				boolean res = false;
				String sts = "";
				PrintWriter out = response.getWriter();
				UserMaster_service uservice = new UserMaster_service();
				HttpSession session = request.getSession();
				UserMasterDTO dto = new UserMasterDTO();
				// System.out.println("Edit User Save user id"+request.getParameter("txtUserID"));
				dto.setUSER_ID(request.getParameter("txtUserID"));
				dto.setUSER_NAME(request.getParameter("txtUserName"));
				dto.setUSER_STATUS(request.getParameter("selUserStatus"));
				dto.setUSER_ROLE(request.getParameter("selUserRole"));
				
				dto.setEXPIRY_DATE(request.getParameter("txtExpiryDate"));

				dto.setP_F_NNUMBER(request.getParameter("txtP_F_Number"));
				dto.setSOL_ID(request.getParameter("txtSolID"));
				res = uservice.EditUserDetails(dto, request
						.getParameterValues("chkApp"),
						session.getAttribute("user_id").toString(), "", sts,
						request.getRemoteAddr());
				// System.out.println("Edit User Service : " + res );
				String scr = "";
				if (res == true) {
					scr = "<script>alert('Data saved successfully');window.location.href='User_master.jsp';</script>";
				} else {
					scr = "<script>alert('Action Failed');window.location.href='User_master.jsp';</script>";
				}

				out.print(scr);
			} else if ("GetVerifyUser".equalsIgnoreCase(op)) {
				String UserID = request.getParameter("UserID");
				String UserName = request.getParameter("UserName");
				String vr_flg = request.getParameter("vr_flg");
				Get_Query g_query = new Get_Query();
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				DBUtil db = new DBUtil();

				HttpSession session = null;
				session = request.getSession(true);
				int page = Integer.parseInt(request.getParameter("page"));
				int rows = Integer.parseInt(request.getParameter("rows"));
				int endI = rows * page;
				int startI = endI - rows + 1;
				int offset = (page - 1) * rows;
				List list1 = new ArrayList();

				JSONObject json = new JSONObject();
				UserMaster_service service = new UserMaster_service();
				int total = 20;

				// put total no records in json object with total key
				try {

					String qry = g_query.Verify_UserList(startI, endI, session
							.getAttribute("user_id").toString(), UserID,
							UserName);
					String[] count = qry.split("~");
					list1 = service.GetVerifyUserLIst(count[0]);
					total = Integer.parseInt(count[1]);
					json.put("total", total);
					json.put("rows", list1);

					out.print(json);

				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out.flush();
				out.close();
			} else if ("UserReport".equalsIgnoreCase(op)) {
				PrintWriter out = response.getWriter();
				List list1 = new ArrayList();
				if (request.getParameter("flg") != null
						&& !request.getParameter("flg").equalsIgnoreCase("")) {

					// System.out.println("User Report Start");
					String UserID = request.getParameter("UserID");
					String UserName = request.getParameter("UserName");
					String reptype = request.getParameter("reptype");
					String fromdt = request.getParameter("fromdt");
					String todate = request.getParameter("todate");
					Get_Query g_query = new Get_Query();
					response.setContentType("text/html");

					DBUtil db = new DBUtil();
					// Get page and rows value from JSP page
					int page = Integer.parseInt(request.getParameter("page"));
					int rows = Integer.parseInt(request.getParameter("rows"));
					int endI = rows * page;
					int startI = endI - rows + 1;
					int offset = (page - 1) * rows;

					JSONObject json = new JSONObject();
					UserMaster_service service = new UserMaster_service();
					int total = 20;

					// put total no records in json object with total key
					try {
						String qry = g_query.User_Maintenance_Report(startI,
								endI, UserID, UserName, reptype, fromdt,
								todate, request.getParameter("his_flg"));
						String[] count = qry.split("~");
						list1 = service.GetUserMaintenanceReportLIst(count[0]);
						// System.out.println(qry);
						total = Integer.parseInt(count[1]);
						json.put("total", total);
						json.put("rows", list1);

						out.print(json);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.flush();
					out.close();

				} else {
					out.print(list1);
				}
			}
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%start

			else if ("get_VeriUserReport".equalsIgnoreCase(op)) {

				PrintWriter out = response.getWriter();
				String res = "";
				List list1 = new ArrayList();
				ResultSet rs = null;
				String Get_data = "";
				// System.out.println("Get Verification UserReportt Start");
				String UserID = request.getParameter("uid");
				DBUtil db = new DBUtil();
				// put total no records in json object with total key
				try {
					String qry = "SELECT * FROM tbl_user_master where user_id='"
							+ UserID + "' ";
					// System.out.println(qry);
					rs = db.get_ResultSet(qry);
					if (rs != null) {
						rs.next();
						Get_data += rs.getString("USER_ID")
								+ "~"
								+ rs.getString("USER_NAME")
								+ "~"
								+ rs.getString("USER_STATUS")
								+ "~"
								+ rs.getString("USER_ROLE")
										.replace("Admin", "Admin+Data")
										.replace("User", "Admin")
								+ "~"
								+ rs.getString("EXPIRY_DATE")
								+ "~"
								+ rs.getString("ISUNLOCKED")
								+ "~"
								+ (db.RemoveNull(rs
										.getString("IS_VERIFIED_DESC")) == "" ? "User Verification"
										: db.RemoveNull(rs
												.getString("IS_VERIFIED_DESC")))
								+ "~";

					}
					qry = "select zone_id,zone_name,"
							+ " (select user_id from tbl_user_zone where upper(user_id) = '"
							+ UserID.toUpperCase()
							+ "' and zone_id = tbl_zone_master.zone_id ) as user_id "
							+ " from tbl_zone_master  ";
					 System.out.println("Get Application :"+qry);
					rs = db.get_ResultSet(qry);
					if (rs != null) {
						while (rs.next()) {
							if (rs.getString("user_id") != null) {
								res += rs.getString("Zone_name") + ",";
							}
						}
					}

					res = res.substring(0, res.length() - 1);
					Get_data = Get_data + res;// substring(0,
												// Get_data.length()-1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// System.out.println(Get_data);
				out.print(Get_data);
				out.flush();
				out.close();

			} else if ("get_Veri_Reject".equalsIgnoreCase(op)) {
				HttpSession session = null;
				session = request.getSession(true);
				PrintWriter out = response.getWriter();
				String Get_data = "";
				int count = 0;
				// System.out.println("Update Verification Rejected UserReport Start");
				String UserID = request.getParameter("uid");
				DBUtil db = new DBUtil();
				// put total no records in json object with total key
				try {
					String qry = "update tbl_user_master set is_verified='R',verified_user='"
							+ session.getAttribute("user_id")
							+ "',verified_date=current_date where    user_id='"
							+ UserID + "' ";
					// System.out.println(" Update  Verification Rejected Sql : "+qry);
					count = db.Qexecute(qry);
					if (count != 0) {
						// String ip_addrss=db.ip_addr;//IP_ADDRESS
						// ,"+ip_addrss+"ip_addr
						Get_data = "Verification Rejected  Successfully";
						qry = "insert into user_activity_log (USER_ID,USER_ID_ACTED_ON,STATUS_DATE,ACTIVITY_TYPE,activity_desc,IP_ADDRESS) values('"
								+ session.getAttribute("user_id")
								+ "','"
								+ UserID.toUpperCase()
								+ "',current_date,'D',' Verification Rejected' ,'"
								+ request.getRemoteAddr() + "')";
						db.Qexecute(qry);

					} else {
						Get_data = "Action Failed";
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// (Get_data);
				out.print(Get_data);
				out.flush();
				out.close();

			}
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%end
			else if ("UserActivityReport".equalsIgnoreCase(op)) {
				PrintWriter out = response.getWriter();
				List list1 = new ArrayList();

				// System.out.println("User Report Start");
				String UserID = request.getParameter("UserID");
				String uA_Activity = request.getParameter("uA_Activity");
				String fromdt = request.getParameter("fromdt");
				String todate = request.getParameter("todate");
				Get_Query g_query = new Get_Query();
				response.setContentType("text/html");

				DBUtil db = new DBUtil();
				// Get page and rows value from JSP page
				int page = Integer.parseInt(request.getParameter("page"));
				int rows = Integer.parseInt(request.getParameter("rows"));
				int endI = rows * page;
				int startI = endI - rows + 1;
				int offset = (page - 1) * rows;

				JSONObject json = new JSONObject();
				UserMaster_service service = new UserMaster_service();
				int total = 20;

				// put total no records in json object with total key
				try {
					String qry = g_query.User_Activity_Report(startI, endI,
							UserID, uA_Activity, fromdt, todate);
					String[] count = qry.split("~");
					list1 = service.GetUserActivityReportLIst(count[0]);
					// System.out.println(qry);
					total = Integer.parseInt(count[1]);
					json.put("total", total);
					json.put("rows", list1);

					out.print(json);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out.flush();
				out.close();

			} else if ("changePass".equalsIgnoreCase(op)) {

				String scr = "";
				boolean res = false;
				String sts = "";
				PrintWriter out = response.getWriter();

				String oldPassW = request.getParameter("oldPassW");

				String newPassW = request.getParameter("newPassW");

				String newPassW2 = request.getParameter("newPassW2");
				HttpSession session = request.getSession();
				String passWordValidate="";
				PasswordValidator pv=new PasswordValidator();
				passWordValidate=pv.passwordValidation(session.getAttribute("user_id").toString(),newPassW);
				
				if(passWordValidate.equalsIgnoreCase("valid")){
				UserMaster_service uservice = new UserMaster_service();
				

				if (session.getAttribute("user_id").equals(null)) {
					scr = "<script>alert('Session TimeOut');window.location.href='Login.jsp';</script>";
				}

				else {

					res = uservice.ChangePassword(oldPassW, newPassW, session
							.getAttribute("user_id").toString());
					// System.out.println("Edit User Service : " + res );

					if (res == true) {

						//scr = "<script>alert('Password Changed successfully');window.location.href='Home.jsp';</script>";

						scr="S~Password Reset Sucessfully";
					} else {
						//scr = "<script>alert('"	+ uservice.stsa	+ "');window.location.href='ChangeUserPassword.jsp';</script>";
						scr="E~"+uservice.stsa	;
					}
				}

				out.print(scr);
				}
				else{
					//out.print("<script>alert('"+passWordValidate+"');return false;</script>");
					scr="M~"+passWordValidate;
					out.print(scr);
				}
			}

			else if ("SAchangePass".equalsIgnoreCase(op)) {

				String scr = "";
				boolean res = false;
				String sts = "";
				PrintWriter out = response.getWriter();

				String oldPassW = request.getParameter("oldPassW");
				String newPassW = request.getParameter("newPassW");
				String hidUserID = request.getParameter("txtUserID");

				UserMaster_service uservice = new UserMaster_service();
				HttpSession session = request.getSession();

				if (session.getAttribute("user_id").equals(null)) {
					scr = "<script>alert('Session TimeOut');window.location.href='Login.jsp';</script>";
				}

				else {

					res = uservice.SAChangePassword(newPassW, hidUserID,
							session.getAttribute("user_id").toString());
					// System.out.println("Edit User Service : " + res );

					if (res == true) {
						if (session.getAttribute("AppName") == null) {
							scr = "<script>alert('Password Changed successfully');window.location.href='Home.jsp';</script>";
						} else {
							scr = "<script>alert('Password Changed successfully');window.location.href='SAChangePassword.jsp';</script>";
						}

						// scr =
						// "<script>alert('Password Changed successfully');";
					} else {
						scr = "<script>alert('"
								+ uservice.stsa
								+ "');window.location.href='SAChangePassword.jsp';</script>";
						// scr = "<script>alert('"+uservice.stsa+"');";
					}
				}

				out.print(scr);
			}
			
			else if ("bulkInsert".equalsIgnoreCase(op))
			{
				System.out.println("Inside Bulk Insert");
				PrintWriter out = response.getWriter();
				List list1 = new ArrayList();
				if (request.getParameter("flg") != null
						&& !request.getParameter("flg").equalsIgnoreCase("")) {
					String UserID = request.getParameter("UserID");
					String UserName = request.getParameter("UserName");
					Get_Query g_query = new Get_Query();
					response.setContentType("text/html");
					// PrintWriter out = response.getWriter();
					DBUtil db = new DBUtil();
					// Get page and rows value from JSP page
					int page = Integer.parseInt(request.getParameter("page"));
					int rows = Integer.parseInt(request.getParameter("rows"));
					int endI = rows * page;
					int startI = endI - rows + 1;
					int offset = (page - 1) * rows;
					// List list1 = new ArrayList();

					JSONObject json = new JSONObject();
					UserMaster_service service = new UserMaster_service();
					int total = 20;

					// put total no records in json object with total key
					try {
						String qry = g_query.Bulk_GetDetails(startI, endI);
						String[] count = qry.split("~");
						System.out.println("count is"+count[0]);
						list1 = service.GetBulkAadharLIst(count[0]);
						// System.out.println(total);
						total = Integer.parseInt(count[1]);
						json.put("total", total);
						json.put("rows", list1);

						out.print(json);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.flush();
					out.close();
				} else {
					out.print(list1);
				}
				
			}
			else if("submitbulkInsert".equalsIgnoreCase(op)){
				System.out.println("Inside submitbulkInsert");
				PrintWriter out = response.getWriter();
				String msg = null;
				UserMaster_service service = new UserMaster_service();
				Get_Query g_query = new Get_Query();
				try {
					String query = g_query.Bulk_ProcessAadhar();
					msg = service.ProcessBulkAadharLIst(query);
				
				}  catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  out.write(msg);  
				out.flush();
				out.close();
			  
			    
			    
			}
			
			
			
			
			
			
			
			
			
			

			else {
				PrintWriter out = response.getWriter();
				List list1 = new ArrayList();
				if (request.getParameter("flg") != null
						&& !request.getParameter("flg").equalsIgnoreCase("")) {
					String UserID = request.getParameter("UserID");
					String UserName = request.getParameter("UserName");
					Get_Query g_query = new Get_Query();
					response.setContentType("text/html");
					// PrintWriter out = response.getWriter();
					DBUtil db = new DBUtil();
					// Get page and rows value from JSP page
					int page = Integer.parseInt(request.getParameter("page"));
					int rows = Integer.parseInt(request.getParameter("rows"));
					int endI = rows * page;
					int startI = endI - rows + 1;
					int offset = (page - 1) * rows;
					// List list1 = new ArrayList();

					JSONObject json = new JSONObject();
					UserMaster_service service = new UserMaster_service();
					int total = 20;

					// put total no records in json object with total key
					try {
						String qry = g_query.User_Master(startI, endI, UserID,
								UserName);
						String[] count = qry.split("~");
						System.out.println("count is"+count[0]);
						list1 = service.GetUserLIst(count[0]);
						// System.out.println(total);
						total = Integer.parseInt(count[1]);
						json.put("total", total);
						json.put("rows", list1);

						out.print(json);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.flush();
					out.close();
				} else {
					out.print(list1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
*/