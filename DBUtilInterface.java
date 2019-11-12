package com.miscot.springmvc.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.miscot.springmvc.dto.Activity_Log;
import com.miscot.springmvc.dto.ApplicationMaster;
import com.miscot.springmvc.helper.Home;


public interface DBUtilInterface {
String encrypt(String Data);
String getSingleValues(String query);
String RemoveNull(String Val) throws Exception;
String decrypt(String encryptedData);
int invalidate_user_session1(JdbcTemplate jdbcTemplate2, String user_id);
int invalidate_user_session2(JdbcTemplate jdbcTemplate2, String user_id, String User_IP);
List<Activity_Log> getActivityLogLIst(String SQL, String flg);
List<ApplicationMaster> getModifyApplication(String SQL, String flag);
public List<Home> listResultSet(String sql);
}
