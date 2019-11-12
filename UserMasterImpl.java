package com.miscot.springmvc.service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.miscot.springmvc.bean.ApplicationMasterBean;
import com.miscot.springmvc.bean.UserMasterBean;
import com.miscot.springmvc.dto.ApplicationMaster;
import com.miscot.springmvc.dto.UserMaster;
import com.miscot.springmvc.repository.DBUtil;
import com.miscot.springmvc.repository.GetQueryImpl;

@Service
@Transactional
public class UserMasterImpl implements UserMasterInterface {
	@Autowired 
	GetQueryImpl getQuery;
	@Autowired 
	AdAuthImpl objAdAuth;

	
	
	public List<UserMaster> GetUserLIst(UserMasterBean userMasterBean) {
		// TODO Auto-generated method stub
		String flag="searchUser";
		String UserID = userMasterBean.getUserID();
		String UserName = userMasterBean.getUserName();
		int page = Integer.parseInt(userMasterBean.getPage());
		int rows = Integer.parseInt(userMasterBean.getRows());
		int endI = rows * page;
		int startI = endI - rows + 1;
		int offset = (page - 1) * rows;
		List<UserMaster> userMasterList=new ArrayList<UserMaster>();
		try
		{
			String qry = getQuery.User_Master2_Cnt(startI, endI, UserID,UserName);
			String[] query = qry.split("~");
			userMasterBean.setCount(Integer.parseInt(query[1]));
			userMasterList=getQuery.listUserMaster(query[0],flag);
			
			return userMasterList;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return userMasterList;
	}
	
	public List<UserMaster> GetUserAcessLogLIst(UserMasterBean userMasterBean) {
		// TODO Auto-generated method stub
		String flag="UserAccessLog";
		String userID = userMasterBean.getUserID();
		String frmDate = userMasterBean.getFromdate();
		String toDate = userMasterBean.getTodate();
		String status = userMasterBean.getStatus();
		int page = Integer.parseInt(userMasterBean.getPage());
		int rows = Integer.parseInt(userMasterBean.getRows());
		int endI = rows * page;
		int startI = endI - rows + 1;
		int offset = (page - 1) * rows;
		List<UserMaster> userMasterList=new ArrayList<UserMaster>();
		try
		{
			String qry = getQuery.UserAccessLogSearchSQL(startI, endI, userID, frmDate, toDate, status);
			String[] query = qry.split("~");
			userMasterBean.setCount(Integer.parseInt(query[1]));
			userMasterList=getQuery.listUserMaster(query[0],flag);
			return userMasterList;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return userMasterList;
	}
	public XSSFWorkbook GetUserAcessLogExcel(UserMasterBean userMasterBean, XSSFWorkbook workbook) {
		List<UserMaster> userMasterList=new ArrayList<UserMaster>();
		try
		{
			String flag="UserAccessLog";
			String userID = userMasterBean.getUserID();
			String frmDate = userMasterBean.getFromdate();
			String toDate = userMasterBean.getTodate();
			String status = userMasterBean.getStatus();
			String qry = getQuery.UserAccessLogSearchSQL(1, 99999999, userID, frmDate, toDate, status);
			String[] query = qry.split("~");
			userMasterList=getQuery.listUserMaster(query[0],flag);
			XSSFSheet sheet = workbook.createSheet("User Access Log");

		      XSSFRow header = sheet.createRow(0);
		      header.createCell(0).setCellValue("User ID");
		      header.createCell(1).setCellValue("User name");
		      header.createCell(2).setCellValue("Login date");
		      header.createCell(3).setCellValue("Status");
		      header.createCell(4).setCellValue("IP Address");
		      int rowNum = 1;
		      for(int i=0;i<userMasterList.size();i++)
				{
		    	  XSSFRow row = sheet.createRow(rowNum++);
		          row.createCell(0).setCellValue(userMasterList.get(i).getUSER_ID().toUpperCase());
		    	  row.createCell(1).setCellValue(userMasterList.get(i).getUSER_NAME().toUpperCase());
		    	  row.createCell(2).setCellValue(userMasterList.get(i).getLOGIN_DATE().toUpperCase());
		    	  row.createCell(3).setCellValue(userMasterList.get(i).getSTATUS_DES().toUpperCase());
		    	  row.createCell(4).setCellValue(userMasterList.get(i).getIP_ADDRESS().toUpperCase());
				}
		    return workbook;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public Document GetUserAcessLogPDF(UserMasterBean userMasterBean, Document document) {
		try {
			List<UserMaster> userMasterList=new ArrayList<UserMaster>();
			String flag="UserAccessLog";
			String userID = userMasterBean.getUserID();
			String frmDate = userMasterBean.getFromdate();
			String toDate = userMasterBean.getTodate();
			String status = userMasterBean.getStatus();
			String qry = getQuery.UserAccessLogSearchSQL(1, 99999999, userID, frmDate, toDate, status);
			String[] query = qry.split("~");
			userMasterList=getQuery.listUserMaster(query[0],flag);
			document.open();
            PdfPTable table = new PdfPTable(5);
            /*table.setTotalWidth(new float[]{ 72, 216, 12, 23, 89 });
            table.setLockedWidth(true);*/
            PdfPCell 
            c1 = new PdfPCell(new Phrase("User ID"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("User name"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Login date"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Status"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("IP Address"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            table.setHeaderRows(1);
            for(int i=0;i<userMasterList.size();i++)
			{
            	table.addCell(userMasterList.get(i).getUSER_ID().toUpperCase());
            	table.addCell(userMasterList.get(i).getUSER_NAME().toUpperCase());
            	table.addCell(userMasterList.get(i).getLOGIN_DATE().toUpperCase());
            	table.addCell(userMasterList.get(i).getSTATUS_DES().toUpperCase());
            	table.addCell(userMasterList.get(i).getIP_ADDRESS().toUpperCase());
			}
            document.add(table);
            document.close();
            return document;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	public List<UserMaster> GetUserModificationLogLIst(UserMasterBean userMasterBean) {
		String flag="UserModificationLog";
		String userID = userMasterBean.getUserID();
		String acted_on_user = userMasterBean.getUser_id_acted_on();
		String frmDate = userMasterBean.getFromdate();
		String toDate = userMasterBean.getTodate();
		String status = userMasterBean.getStatus();
		int page = Integer.parseInt(userMasterBean.getPage());
		System.out.println("Page is"+page);
		int rows = Integer.parseInt(userMasterBean.getRows());
		System.out.println("Rows is"+rows);
		int endI = rows * page;
		int startI = endI - rows + 1;
		int offset = (page - 1) * rows;
		List<UserMaster> userMasterList=new ArrayList<UserMaster>();
		try
		{
			String qry = getQuery.UserModificationLogSearchSQL(startI, endI, userID, frmDate, toDate, status, acted_on_user);
			String[] query = qry.split("~");
			userMasterBean.setCount(Integer.parseInt(query[1]));
			userMasterList=getQuery.listUserMaster(query[0],flag);
			return userMasterList;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return userMasterList;
	}
	public XSSFWorkbook GetUserModificationLogExcel(UserMasterBean userMasterBean,XSSFWorkbook workbook) {
		List<UserMaster> userMasterList=new ArrayList<UserMaster>();
		try
		{
			String flag="UserModificationLog";
			String userID = userMasterBean.getUserID();
			String acted_on_user = userMasterBean.getUser_id_acted_on();
			String frmDate = userMasterBean.getFromdate();
			String toDate = userMasterBean.getTodate();
			String status = userMasterBean.getStatus();
			String qry = getQuery.UserModificationLogSearchSQL(1, 99999999, userID, frmDate, toDate, status, acted_on_user);
			String[] query = qry.split("~");
			userMasterList=getQuery.listUserMaster(query[0],flag);
			XSSFSheet sheet = workbook.createSheet("User Modification Log");

		      XSSFRow header = sheet.createRow(0);
		      header.createCell(0).setCellValue("User ID");
		      header.createCell(1).setCellValue("User Acted On");
		      header.createCell(2).setCellValue("Login date");
		      header.createCell(3).setCellValue("Operation");
		      header.createCell(4).setCellValue("Action Description");
		      header.createCell(5).setCellValue("IP Address");
		      int rowNum = 1;
		      for(int i=0;i<userMasterList.size();i++)
				{
		    	  XSSFRow row = sheet.createRow(rowNum++);
		          row.createCell(0).setCellValue(userMasterList.get(i).getUSER_ID().toUpperCase());
		    	  row.createCell(1).setCellValue(userMasterList.get(i).getUSER_ID_ACTED_ON().toUpperCase());
		    	  row.createCell(2).setCellValue(userMasterList.get(i).getACTION_DATE().toUpperCase());
		    	  row.createCell(3).setCellValue(userMasterList.get(i).getOPERATION().toUpperCase());
		    	  row.createCell(4).setCellValue(userMasterList.get(i).getACTION_DESCRIPTION().toUpperCase());
		    	  row.createCell(5).setCellValue(userMasterList.get(i).getIP_ADDRESS().toUpperCase());
				}
		    return workbook;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public Document GetUserModificationLogPDF(UserMasterBean userMasterBean, Document document) {
		try {
			List<UserMaster> userMasterList=new ArrayList<UserMaster>();
			String flag="UserModificationLog";
			String userID = userMasterBean.getUserID();
			String acted_on_user = userMasterBean.getUser_id_acted_on();
			String frmDate = userMasterBean.getFromdate();
			String toDate = userMasterBean.getTodate();
			String status = userMasterBean.getStatus();
			String qry = getQuery.UserModificationLogSearchSQL(1, 99999999, userID, frmDate, toDate, status, acted_on_user);
			String[] query = qry.split("~");
			userMasterList=getQuery.listUserMaster(query[0],flag);
			document.open();
			PdfPTable table = new PdfPTable(6);
            PdfPCell 
            c1 = new PdfPCell(new Phrase("User ID"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("User Acted On"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Login date"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Operation"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Action Description"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("IP Address"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            table.setHeaderRows(1);
            for(int i=0;i<userMasterList.size();i++)
			{
            	table.addCell(userMasterList.get(i).getUSER_ID().toUpperCase());
            	table.addCell(userMasterList.get(i).getUSER_ID_ACTED_ON().toUpperCase());
            	table.addCell(userMasterList.get(i).getACTION_DATE().toUpperCase());
            	table.addCell(userMasterList.get(i).getOPERATION().toUpperCase());
            	table.addCell(userMasterList.get(i).getACTION_DESCRIPTION().toUpperCase());
            	table.addCell(userMasterList.get(i).getIP_ADDRESS().toUpperCase());
			}
            document.add(table);
            document.close();
            return document;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	public List<UserMaster> GetUserAppModificationLogLIst(UserMasterBean userMasterBean) {
		String flag="AppModificationLog";
		String userID = userMasterBean.getUserID();
		String acted_on_user = userMasterBean.getUser_id_acted_on();
		String frmDate = userMasterBean.getFromdate();
		String toDate = userMasterBean.getTodate();
		String status = userMasterBean.getStatus();
		int page = Integer.parseInt(userMasterBean.getPage());
		System.out.println("Page is"+page);
		int rows = Integer.parseInt(userMasterBean.getRows());
		System.out.println("Rows is"+rows);
		int endI = rows * page;
		int startI = endI - rows + 1;
		int offset = (page - 1) * rows;
		List<UserMaster> userMasterList=new ArrayList<UserMaster>();
		try
		{
			String qry = getQuery.AppModificationLogSearchSQL(startI, endI, userID, frmDate, toDate, status, acted_on_user);
			String[] query = qry.split("~");
			userMasterBean.setCount(Integer.parseInt(query[1]));
			userMasterList=getQuery.listUserMaster(query[0],flag);
			return userMasterList;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return userMasterList;
	}
	public XSSFWorkbook GetUserAppModificationLogExcel(UserMasterBean userMasterBean,XSSFWorkbook workbook) {
		List<UserMaster> userMasterList=new ArrayList<UserMaster>();
		try
		{
			String flag="AppModificationLog";
			String userID = userMasterBean.getUserID();
			String acted_on_user = userMasterBean.getUser_id_acted_on();
			String frmDate = userMasterBean.getFromdate();
			String toDate = userMasterBean.getTodate();
			String status = userMasterBean.getStatus();
			String qry = getQuery.AppModificationLogSearchSQL(1, 99999999, userID, frmDate, toDate, status, acted_on_user);
			String[] query = qry.split("~");
			userMasterList=getQuery.listUserMaster(query[0],flag);
			XSSFSheet sheet = workbook.createSheet("App Modification Log");

		      XSSFRow header = sheet.createRow(0);
		      header.createCell(0).setCellValue("User ID");
		      header.createCell(1).setCellValue("Application Name");
		      header.createCell(2).setCellValue("Action date");
		      header.createCell(3).setCellValue("Action Description");
		      header.createCell(4).setCellValue("Operation");
		      header.createCell(5).setCellValue("IP Address");
		      int rowNum = 1;
		      for(int i=0;i<userMasterList.size();i++)
				{
		    	  XSSFRow row = sheet.createRow(rowNum++);
		          row.createCell(0).setCellValue(userMasterList.get(i).getUSER_ID().toUpperCase());
		    	  row.createCell(1).setCellValue(userMasterList.get(i).getUSER_ID_ACTED_ON().toUpperCase());
		    	  row.createCell(2).setCellValue(userMasterList.get(i).getACTION_DATE().toUpperCase());
		    	  row.createCell(3).setCellValue(userMasterList.get(i).getACTION_DESCRIPTION().toUpperCase());
		    	  row.createCell(4).setCellValue(userMasterList.get(i).getOPERATION().toUpperCase());
		    	  row.createCell(5).setCellValue(userMasterList.get(i).getIP_ADDRESS().toUpperCase());
				}
		    return workbook;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public Document GetUserAppModificationLogPDF(UserMasterBean userMasterBean, Document document) {
		try {
			List<UserMaster> userMasterList=new ArrayList<UserMaster>();
			String flag="AppModificationLog";
			String userID = userMasterBean.getUserID();
			String acted_on_user = userMasterBean.getUser_id_acted_on();
			String frmDate = userMasterBean.getFromdate();
			String toDate = userMasterBean.getTodate();
			String status = userMasterBean.getStatus();
			String qry = getQuery.AppModificationLogSearchSQL(1, 99999999, userID, frmDate, toDate, status, acted_on_user);
			String[] query = qry.split("~");
			userMasterList=getQuery.listUserMaster(query[0],flag);
			document.open();
			PdfPTable table = new PdfPTable(6);
            PdfPCell 
            c1 = new PdfPCell(new Phrase("User ID"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Application Name"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Action date"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Action Description"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Operation"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            c1 = new PdfPCell(new Phrase("IP Address"));c1.setHorizontalAlignment(Element.ALIGN_CENTER);table.addCell(c1);
            table.setHeaderRows(1);
            for(int i=0;i<userMasterList.size();i++)
			{
            	table.addCell(userMasterList.get(i).getUSER_ID().toUpperCase());
            	table.addCell(userMasterList.get(i).getUSER_ID_ACTED_ON().toUpperCase());
            	table.addCell(userMasterList.get(i).getACTION_DATE().toUpperCase());
            	table.addCell(userMasterList.get(i).getACTION_DESCRIPTION().toUpperCase());
            	table.addCell(userMasterList.get(i).getOPERATION().toUpperCase());
            	table.addCell(userMasterList.get(i).getIP_ADDRESS().toUpperCase());
			}
            document.add(table);
            document.close();
            return document;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	public List GetUserMaintenanceReportLIst(String Qry) {
		// TODO Auto-generated method stub
		return null;
	}

	public List GetUserActivityReportLIst(String Qry) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserMaster> GetVerifyUserLIst(UserMasterBean userMasterBean, HttpSession session) {
	
		int page = Integer.parseInt(userMasterBean.getPage());
		int rows = Integer.parseInt(userMasterBean.getRows());
		int endI = rows * page;
		int startI = endI - rows + 1;
		int offset = (page - 1) * rows;
		List<UserMaster> userMasterVerifyList=new ArrayList<UserMaster>();
		String flag="verifyUserList";
	
		int total = 20;
		
		try {
			String qry = getQuery.Verify_UserList(startI, endI, session.getAttribute("user_id").toString());
			String[] query = qry.split("~");
			System.out.println("query is"+query[0]);
			System.out.println("Count is"+query[1]);
			userMasterBean.setCount(Integer.parseInt(query[1]));
			userMasterVerifyList=getQuery.listUserMaster(query[0],flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userMasterVerifyList;
	}

	public UserMaster GetUserDetails(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	public String Get_Zone_List(String usr, String typ) {
		// TODO Auto-generated method stub
		return null;
	}

	public String GetFolderList(String usr, String typ) {
		// TODO Auto-generated method stub
		return null;
	}

	public String GetAppList1(String usr, String typ) {
		// TODO Auto-generated method stub
		return null;
	}

	public String Dar_Get_Branch_List() {
		// TODO Auto-generated method stub
		return null;
	}

	public String Dar_Get_Branch_List_fin7() {
		// TODO Auto-generated method stub
		return null;
	}

	public String CheckUserExistLogin(String User_id, UserMasterBean userMasterBean) {

		boolean res  = false;
		String sts = "";
		System.out.println("CheckUserExistLogin");
		String query = "";

		int chkINT = 0;
		String queryString;
		String queryArra[];
		String flag="checkExistingUser";
		try {


			if (User_id != null && User_id != "")
			{
				
				queryString=getQuery.User_Master2_Cnt(1, 1, User_id,"");
				queryArra=queryString.split("~");
				chkINT =Integer.parseInt( queryArra[1]);
				System.out.println("CheckLoginCount : " + chkINT);
				if (chkINT > 0)
				{
					query = queryArra[0];
					List<UserMaster> userMasterList=getQuery.listUserMaster(query,flag);
					for(int i=0;i<userMasterList.size();i++)
					{
						if (userMasterList.get(i).getISVERIFIED().equals("N") ||userMasterList.get(i).getISVERIFIED().equals("R") )
						{
							res = false;
							sts = "V";
						}
						else if (userMasterList.get(i).getUSER_STATUS().equals("X"))
						{
							res = false;
							sts = "X";
						}
						else if (userMasterList.get(i).getISACTIVE().equals("Y"))
						{
							res = false;
							sts = "A";
						}
						else if (userMasterList.get(i).getUSER_STATUS().equals("D"))
						{
							res = false;
							sts = "D";
						}
						else if (userMasterList.get(i).getUSER_STATUS().equals("L"))
						{
							res = false;
							sts = "L";
						}
						else if (userMasterList.get(i).getUSER_STATUS().equals("I"))
						{
							res = false;
							sts = "I";
						}
						else
						{
							if (userMasterBean != null)
							{
								userMasterBean.setUserID (userMasterList.get(i).getUSER_ID());
								userMasterBean.setUserName(userMasterList.get(i).getUSER_NAME());
								userMasterBean.setUSER_STATUS (userMasterList.get(i).getUSER_STATUS());
								userMasterBean.setUSER_ROLE (userMasterList.get(i).getUSER_ROLE());
								userMasterBean.setUSER_ROLE_DESC(userMasterList.get(i).getUSER_ROLE_DESC());
								userMasterBean.setCREATED_TIME (userMasterList.get(i).getCREATED_TIME());
								userMasterBean.setCREATED_USER (userMasterList.get(i).getCREATED_USER());
								userMasterBean.setLAST_LOGIN_DATE (userMasterList.get(i).getLAST_LOGIN_DATE());
								userMasterBean.setLAST_LOGIN_IP (userMasterList.get(i).getLAST_LOGIN_IP());
							}
							res = true;
						}
					}
				}
				else{
					//System.out.println("Not available in checkLogin");
					res = false;
					sts = "N";
				}


			}
			else{
				res = false;
				sts = "E";
			}




		if (res == true)
		{
			sts = "";
		}




		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = false;
		}


		finally {

		}
		return sts ;


	}

	public String CheckUserExistNew(String User_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String Get_zone(String User_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String chkPasswordExpiry(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	public String chkLockedUserTime(String userid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String AuthenticateUser(String userid, String passWord, UserMasterBean userMasterBean, String ipAddr, String sts) {
		
		boolean res = true;
		
		String chk = "";
		String qry = "";
		try
		{

			if (userid == null || passWord==null)
			{
				res = false;
			}
			else
			{
			
				
			
					int auth =0;				
				  ipAddr=	InetAddress.getLocalHost().getHostAddress();
				  System.out.println(ipAddr);
				  auth=objAdAuth.ISauthenticate(userid,passWord);
					 if (auth == 1)
					 {
						chk = CheckUserExistLogin(userid,userMasterBean);					
						if (chk.equals(""))
						{
							getQuery.update_tbl_user_master1(ipAddr, userid.toUpperCase());
							getQuery.insert_tbl_audit_login1(ipAddr, userid.toUpperCase());
							res = true;
						}
						else if ((chk.equalsIgnoreCase("N"))) {
							
					 
						//System.out.println("AD Authentication : " + auth);
					    }					
						else
						{
							getQuery.insert_tbl_audit_login1(ipAddr, userid, chk);
							res = false;					
						}
		
					 } 
					 
					 else
					 {
						 	int FailCount = 0;
							int ValidFailCnt = 0;
							
							String tempFailCount=getQuery.tempFailCount();
							ValidFailCnt = (tempFailCount==""?0:Integer.parseInt(tempFailCount));
							
							if (ValidFailCnt == 0) {
								ValidFailCnt = 3;
							}
							FailCount = UserFailCount(userid);
							
							chk = "N";
							if (FailCount >= ValidFailCnt) {
								getQuery.insert_tbl_audit_login1(userid.toUpperCase());
								chk = "L";
								getQuery.insert_tbl_audit_login1(userid.toUpperCase() , ipAddr);
							}
						res = false;
					 }
					 
			}

		}

		catch(Exception e )
		{
			e.printStackTrace();
			res = false;
		}
		
		if(res == true)
		{
			chk = "";
		}
		return chk;
	}

	public String AuthenticateUserDB(String userid, String passWord, UserMaster dto, String ipAddr, String sts) {
		// TODO Auto-generated method stub
		return null;
	}

	public int UserFailCount(String userid) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean ResetPassword(String UserId, String Password) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean RejectVerifyUser(UserMasterBean userMasterBean, String currentUser, String ip_addrss) {
		boolean res;
		try
		{
			if (userMasterBean.getTxt_User_id() == null || userMasterBean.getTxt_User_id().equals(null))
			{
				res = false;
			}
			else
			{
				int tmp = 0;

				tmp = getQuery.update_tbl_user_master3(currentUser,userMasterBean.getTxt_User_id().toUpperCase());
				if (tmp > 0 )
				{
					getQuery.insert_tbl_audit_login3(currentUser , userMasterBean.getTxt_User_id().toUpperCase(),ip_addrss);
					res = true;
				}
				else
				{
					res = false;
				}

			}

		}
		catch(Exception e )
		{
			e.printStackTrace();
			res = false;
		}
		return res;
	}
	public boolean VerifyUser(UserMasterBean userMasterBean, String currentUser, String ip_addrss) {
		boolean res;
		try
		{
			if (userMasterBean.getTxt_User_id() == null || userMasterBean.getTxt_User_id().equals(null))
			{
				res = false;
			}
			else
			{
				int tmp = 0;

				tmp = getQuery.update_tbl_user_master2(currentUser,userMasterBean.getTxt_User_id().toUpperCase());
				if (tmp > 0 )
				{
					getQuery.insert_tbl_audit_login2(currentUser , userMasterBean.getTxt_User_id().toUpperCase(),ip_addrss);
					res = true;
				}
				else
				{
					res = false;
				}

			}

		}
		catch(Exception e )
		{
			e.printStackTrace();
			res = false;
		}
		return res;
	}

	public String AddNewUser(UserMasterBean userMasterBean, String createdUser, String sts,
			String ip_addrss) {
		try
		{
				int tmp = 0;
				String cnt;
				System.out.println(userMasterBean.getTxtUserID().toUpperCase());
				cnt = getQuery.isAlreadyExisit( userMasterBean.getTxtUserID().toUpperCase());
				if (Integer.parseInt(cnt) > 0)
				{
					sts = "User Already exist";
				}
				else
				{
					String uname = "";
					uname = userMasterBean.getTxtUserName().replace("'","''");
					String date[]=userMasterBean.getTxtExpiryDate().toString().split(" ");
					System.out.println(userMasterBean.getTxtExpiryDate());
					System.out.println(date[0]);
					getQuery.addNew(userMasterBean.getTxtUserID().toUpperCase(),uname,userMasterBean.getSelUserStatus(),
							userMasterBean.getSelUserRole(),createdUser,userMasterBean.getTxtExpiryDate(),userMasterBean.getSelUserRole(),userMasterBean.getTxtSolID());
					 String activity_desc=" Added :"+userMasterBean.getTxtUserID().toUpperCase()+" with User Role => "+userMasterBean.getSelUserRole()+" , staus => "+userMasterBean.getSelUserStatus()+" and Expiry Date =>"+userMasterBean.getTxtExpiryDate();
						
					 tmp=getQuery.addNew_tbl_audit(createdUser,userMasterBean.getTxtUserID().toUpperCase(),ip_addrss,activity_desc);
					 		if (tmp == 1)
							{
					 			sts = "";
							}
							else
							{
								sts = "Action Failed";
							}
				}


		}
		catch(Exception e )
		{
			e.printStackTrace();
			sts = "Action Failed";
		}
		return sts;
}

	public boolean EditUserDetails(UserMasterBean userMasterBean, String modifiedUser, String ip_addrss) {
		// TODO Auto-generated method stub
		String query="";
		String querArray[]=null;
		String activity_dec="";
		boolean res=false;
		try
		{
			query=getQuery.getEditUserDetails(userMasterBean.getTxtUserID());
			if(query!=null && !query.equals(""))
			{
				querArray=query.split("~");
				String status="";
				status=userMasterBean.getSelUserStatus().trim();
				if(!querArray[0].equals(status))
				{
					activity_dec+="User Status : "+querArray[0]+" => "+status+"<br>";
				}
				
				if(!querArray[2].equals(userMasterBean.getSelUserRole()))
				{
					activity_dec+="User Role : "+querArray[1]+" => "+userMasterBean.getSelUserRole()+"<br>";
				}
				
				if(!querArray[2].equals(userMasterBean.getTxtExpiryDate()))
				{
					activity_dec+="Expiry Date : "+querArray[2]+" => "+userMasterBean.getTxtExpiryDate()+"<br>";
				}
			}
		
			if (userMasterBean.getSelUserStatus().equals("E"))
			{
				getQuery.updateEditUserMaster(userMasterBean.getSelUserRole(),userMasterBean.getSelUserStatus(),userMasterBean.getTxtUserID(),modifiedUser,userMasterBean.getTxtExpiryDate(),userMasterBean.getTxtUserName());
					
			}
			else
			{

				getQuery.updateEditUserMasterNotVerified(userMasterBean.getSelUserRole(),userMasterBean.getSelUserStatus(),userMasterBean.getTxtUserID(),modifiedUser,userMasterBean.getTxtExpiryDate(),userMasterBean.getTxtUserName());

			}
			

		System.out.println("activity_dec::"+activity_dec);
		
			String old_app= "";
			int j=0;
			getQuery.insertActivityLog(modifiedUser,userMasterBean.getTxtUserID(),activity_dec,ip_addrss);
			
			res = true;
		}
		catch(Exception e){
			e.printStackTrace();
			res=false;
		}
		System.out.println("Result is"+res);
		return res;
	}

	public boolean ChangePassword(String oldPassW, String newPassW, String user_id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean SAChangePassword(String newPassW, String user_id, String SaUserID) {
		// TODO Auto-generated method stub
		return false;
	}

	public List GetBulkAadharLIst(String Qry) {
		// TODO Auto-generated method stub
		return null;
	}

	public String ProcessBulkAadharLIst(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserMaster> GetEditUserList(String uid) {
		// TODO Auto-generated method stub
		String flag="getEditUser";
		
		List<UserMaster> editUserMasterList=null;
		
		try
		{
			String query = getQuery.getEditUserQuery(uid);
			
			editUserMasterList=getQuery.listUserMaster(query,flag);
			return editUserMasterList;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return editUserMasterList;
	}

	public int updateUserMaster(String sessionid, String userid) {
		// TODO Auto-generated method stub
		int i=getQuery.update_tbl_user_master(sessionid,userid);
		return i;
	}
	public List<UserMaster> GetVerifyUserList(String uid) {
		String flag="getVerifyUser";
		List<UserMaster> getVerifyUserMasterList=null;
		try
		{
			String query = getQuery.getVerifyQuery(uid);
			getVerifyUserMasterList=getQuery.listUserMaster(query,flag);
			return getVerifyUserMasterList;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return getVerifyUserMasterList;
}
	public List<ApplicationMaster> getModifyUserList(UserMasterBean userMasterBean) {
		// TODO Auto-generated method stub
		String flag="modifyUser";
		int page = Integer.parseInt(userMasterBean.getPage());
		int rows = Integer.parseInt(userMasterBean.getRows());
		int endI = rows * page;
		int startI = endI - rows + 1;
		int offset = (page - 1) * rows;
		List<ApplicationMaster> userMasterModifyList=new ArrayList<ApplicationMaster>();
		
	
		int total = 20;
		
		try {
			String qry = getQuery.modifyUserList(startI, endI);
			String[] query = qry.split("~");
			System.out.println("query is"+query[0]);
			System.out.println("Count is"+query[1]);
			userMasterBean.setCount(Integer.parseInt(query[1]));
			userMasterModifyList=getQuery.listModifyAppMaster(query[0],flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userMasterModifyList;
	}
	
	public boolean ResetLogin(String userid, String CurrentUser, String ip_addrss) {
		boolean res;
		String chk = "";

		try
		{
			if (userid == null || userid.equals(null))
			{
				res = false;
			}
			else
			{
				DBUtil ds = new DBUtil();
				//ip_addrss=ds.ip_addr;//IP_ADDRESS
				int tmp = 0;
				tmp = getQuery.update_tbl_user_master4(userid);
						
				if (tmp > 0 )
				{
					
					
					getQuery.insertActivityLog1(userid, CurrentUser,ip_addrss);
					/*String qry="insert into user_activity_log (USER_ID,USER_ID_ACTED_ON,STATUS_DATE,ACTIVITY_TYPE,IP_ADDRESS) values('" + CurrentUser + "','" +  userid.toUpperCase() + "',current_date,'R','"+ip_addrss+"')";
					ds.Qexecute(qry);*/
					res = true;
				}
				else
				{
					res = false;
				}

			}

		}
		catch(Exception e )
		{
			e.printStackTrace();
			res = false;
		}
		return res;
	}
	
	public String addApplication(ApplicationMasterBean applicationMasterBean, String lk, String sessionUserId) {
		// TODO Auto-generated method stub
		String result=null;
		result=getQuery.insertApplication(applicationMasterBean,lk,sessionUserId);
		return result;
	}
	
	public List<ApplicationMaster> getModifyUpdateDetails(String uid) {
		// TODO Auto-generated method stub
		String flag="modifyAppEditUser";
		List<ApplicationMaster> getAppModifyList=new ArrayList<ApplicationMaster>();
		
		
		int total = 20;
		
		try {
			String qry = getQuery.modifyAppList(uid);
			
			getAppModifyList=getQuery.listModifyAppMaster(qry,flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getAppModifyList;
	}
	
	public String editApplicationDetails(ApplicationMasterBean applicationMasterBean, String sessionId) {
		// TODO Auto-generated method stub
		String flag="modifyAppEditUser";
		List<ApplicationMaster> getAppModifyList=new ArrayList<ApplicationMaster>();
		String activity_dec=null;
		String res=null;
		try {
			String qry = getQuery.modifyEditAppList(applicationMasterBean.getTxtUserID());
			
			getAppModifyList=getQuery.listModifyAppMaster(qry,flag);
			System.out.println("getAppModifyList.size()"+getAppModifyList.size());
			System.out.println(getAppModifyList.get(0).getUSER_ID());
			for(int i=0;i<getAppModifyList.size();i++)
			{
				if(!getAppModifyList.get(i).getUSER_ID().equals(applicationMasterBean.getTxtUserID()))
				{
					activity_dec+="User ID : "+getAppModifyList.get(i).getUSER_ID()+" => "+applicationMasterBean.getTxtUserID()+"<br>";
				}
				if(!getAppModifyList.get(i).getIP_ADDTRESS().equals(applicationMasterBean.getTxtIPadd()))
				{
					activity_dec+="IP Address : "+getAppModifyList.get(i).getIP_ADDTRESS()+" => "+applicationMasterBean.getTxtIPadd()+"<br>";
				}
				if(!getAppModifyList.get(i).getAPPLICATION_NAME().equals(applicationMasterBean.getTxtAppName()))
				{
					activity_dec+="Application Name : "+getAppModifyList.get(i).getAPPLICATION_NAME()+" => "+applicationMasterBean.getTxtAppName()+"<br>";
				}
				if(!getAppModifyList.get(i).getPassword1().equals(applicationMasterBean.getTxtPassword()))
				{
					activity_dec+=" Password :"+getAppModifyList.get(i).getPassword1()+" => "+applicationMasterBean.getTxtPassword()+"<br>";
				}
				if(!getAppModifyList.get(i).getUSER_STATUS().equals(applicationMasterBean.getTxtUserStatus()))
				{
					activity_dec+=" User Status :"+getAppModifyList.get(i).getUSER_STATUS()+" => "+applicationMasterBean.getTxtUserStatus()+"<br>";
				}
			}
			 res=getQuery.editModifyApplication(applicationMasterBean,sessionId,activity_dec);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
@Override
	public String checkUserCreated(String txt_User_id, String sessionUserId) {
		// TODO Auto-generated method stub
	String CREATED_USER=getQuery.checkCreatedUser(txt_User_id);
	String msg="";
	if(CREATED_USER.equalsIgnoreCase(sessionUserId)) {
		msg="Created User cannot Verified added User";
	}
		return msg;
	}

}
