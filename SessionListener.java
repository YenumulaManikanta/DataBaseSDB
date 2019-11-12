package com.miscot.springmvc.Listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.miscot.springmvc.repository.DBUtil;

 
public class SessionListener implements HttpSessionListener {
	
	private int sessionCount = 0;
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("==== Session is created ====");
        synchronized (this) {
			sessionCount++;
		}
		HttpSession ss=event.getSession();
		System.out.println("Session Created: " + event.getSession().getId());
		System.out.println("Total Sessions: " + sessionCount);
		event.getSession().setMaxInactiveInterval(15*60);
    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
		synchronized (this) {
			sessionCount--;
		}
		ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(event.getSession().getServletContext());
		JdbcTemplate jdbcTemplate=(JdbcTemplate) ctx.getBean("jdbcTemplate");
		
		System.out.println("==== Session is destroyed ====");
		try
		{
			if (event.getSession().getAttribute("user_id") != null)
			{
				String user_id=event.getSession().getAttribute("user_id").toString();
				String ip_address=event.getSession().getAttribute("User_IP").toString();
				
				DBUtil db=new DBUtil();
				db.invalidate_user_session1(jdbcTemplate,user_id);
				db.invalidate_user_session2(jdbcTemplate,user_id ,ip_address);
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
	