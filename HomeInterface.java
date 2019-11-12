package com.miscot.springmvc.service;

public interface HomeInterface {
	String Application_chart(String Sol_id,String User_ID);
	String Month_chart(String Sol_id,String User_ID);
	String Log_chart(String Sol_id,String User_ID);
	String State_Wise_chart(String Sol_id,String User_ID);
	void main();
}
