package com.miscot.springmvc.service;


import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miscot.springmvc.helper.Home;
import com.miscot.springmvc.repository.GetQueryImpl;
@Service
public class HomeImpl implements HomeInterface {
	@Autowired 
	GetQueryImpl getQuery;

	@Override
	public String Application_chart(String Sol_id, String User_ID) {
		String jstr = "";
		//int i = 0;
		String sql = "";
		long cnt = 0;

		try
		{
			//chart_id=1
			sql="SELECT FIELD_NAME,FIELD_VALUE FROM TBL_DASHBOARD where chart_id=2  "+//and key_field=1" +
					//"WHERE CHART_ID=1 and KEY_FIELD in (select   zone_id from [TBL_USER_ZONE] where upper(user_id)='" + User_ID + "')" +
					"";		
			//JSONObject obj = new JSONObject();
			
			List<Home> home= null;

			home= getQuery.listResultSet(sql);
			System.out.println("sql::"+sql);

			
			/*JSONArray arr = new JSONArray();
            HashMap<String, JSONObject> map = new HashMap<String, JSONObject>();
            for(int i = 0 ; i < home.size() ; i++) {
              JSONObject json=new JSONObject();
              json.put("Desc",home.get(i).getFIELD_NAME());
              json.put("doccount",home.get(i).getTOTAL());
              map.put("json" + i, json);
              arr.put(map.get("json" + i));
            }
            System.out.println("The json string is " + arr.toString());

            try (FileWriter file = new FileWriter("E:\\chart.json")) {
            	 
                file.write(arr.toString());
                file.flush();
     
            } catch (IOException e) {
                e.printStackTrace();
            }*/
			
			
			
			
			
					
			for(int i=0;i<home.size();i++)			
			{
				//if (i == 0)
				//{
					jstr += "data["+i+"] ={label: \"" + home.get(i).getFIELD_NAME() + "\",data: " + home.get(i).getTOTAL() + "};|";
				//}
				/*else
				{
					jstr += "|"+"{\"label\": \"" +home.get(i).getFIELD_NAME() + "\",\"data\": " + home.get(i).getTOTAL() + "};";
				}*/
				//i = i + 1;
			}

			//jstr += "var chart; var legend;" + "var chartData = " + "[";
			
			/*jstr +="[";
			for(int i=0;i<home.size();i++)			
			{
				if (i == 0)
				{
					jstr += "{\"Desc\": \"" + home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				else
				{
					jstr += ",{\"Desc\": \"" +home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				i = i + 1;
			}

			jstr +="]";*/
			//jstr += " ];" + "AmCharts.ready(function () {" + "chart = new AmCharts.AmPieChart();" + "chart.dataProvider = chartData;" + "chart.outlineColor = \"#FFFFFF\";" + "chart.outlineAlpha = 0.8;chart.titleField = \"Desc\"; chart.valueField = \"doccount\";" + "legend = new AmCharts.AmLegend();" + "legend.align = \"center\";" + "legend.markerType = \"circle\";chart.addLegend(legend,'legenddiv');" + "chart.outlineThickness = 2;chart.autoMargins = false; " + " chart.marginTop = 0;chart.marginBottom = 0;chart.marginLeft = 0;chart.marginRight = 0;" + "chart.pullOutRadius = 15;chart.balloonText = \"[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>\";    chart.depth3D = 15;   chart.angle = 30; chart.write(\"chartdiv\");" + "});";
			
			
		}
		catch (RuntimeException ex)
		{

			jstr = "";
			ex.printStackTrace();
		} 
		return jstr;
	}

	@Override
	public String Month_chart(String Sol_id, String User_ID) {
		String jstr = "";
		//int i = 0;
		String sql = "";
		long cnt = 0;
		try
		{

			sql="SELECT FIELD_NAME,FIELD_VALUE FROM TBL_DASHBOARD where chart_id=3  "+//and key_field=1" +
					//"WHERE CHART_ID=2 and KEY_FIELD in (select   zone_id from [TBL_USER_ZONE] where upper(user_id)='" + User_ID + "') group by FIELD_NAME" +
					"";	


			/*List<Home> home= null;

			home= getQuery.listResultSet(sql);
			System.out.println("sql::"+sql);


			jstr += "var chart2; var legend;" + "var chartData2 = " + "[";

			for(int i=0;i<home.size();i++)
			{
				if (i == 0)
				{
					jstr += "{\"Desc\": \"" + home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				else
				{
					jstr += ",{\"Desc\": \"" +home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				i = i + 1;
				//cnt = cnt +  rs.getString("CHR_HOR");
			}



			jstr += " ];" + "AmCharts.ready(function () {" + "chart2 = new AmCharts.AmPieChart();" + "chart2.dataProvider = chartData2;" + "chart2.outlineColor = \"#FFFFFF\";" + "chart2.outlineAlpha = 0.8;chart2.titleField = \"Desc\"; chart2.valueField = \"doccount\";" + "legend2= new AmCharts.AmLegend();" + "legend2.align = \"center\";" + "legend2.markerType = \"circle\";chart2.addLegend(legend2,'legenddiv2');" + "chart2.outlineThickness = 2;chart2.autoMargins = false; " + " chart2.marginTop = 0;chart2.marginBottom = 0;chart2.marginLeft = 0;chart2.marginRight = 0;" + "chart2.pullOutRadius = 15;chart2.balloonText = \"[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>\";    chart2.depth3D = 15;   chart2.angle = 30; chart2.write(\"chartdiv\");" + "});";

			//	jstr += "$('#divTemplateSummary').height('200px');$('#legenddiv').height('200px');";
*/
			List<Home> home= null;

			home= getQuery.listResultSet(sql);
			System.out.println("sql::"+sql);
					
			for(int i=0;i<home.size();i++)			
			{
				jstr += "data["+i+"] ={label: \"" + home.get(i).getFIELD_NAME() + "\",data: " + home.get(i).getTOTAL() + "};|";
			}
		}
		catch (RuntimeException ex)
		{

			jstr = "";
		} 
		return jstr;
	}

	@Override
	public String Log_chart(String Sol_id, String User_ID) {
		String jstr = "";
		//int i = 0;
		String sql = "";
		long cnt = 0;
		try
		{

			sql="SELECT FIELD_NAME,FIELD_VALUE FROM TBL_DASHBOARD where chart_id=5  "+//and key_field=1" +
					//"WHERE CHART_ID=2 and KEY_FIELD in (select   zone_id from [TBL_USER_ZONE] where upper(user_id)='" + User_ID + "') group by FIELD_NAME" +
					"";	

			List<Home> home= null;

			home= getQuery.listResultSet(sql);
			System.out.println("sql::"+sql);


			jstr += "var chart3; var legend;" + "var chartData3 = " + "[";

			for(int i=0;i<home.size();i++)
			{
				if (i == 0)
				{
					jstr += "{\"Desc\": \"" + home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				else
				{
					jstr += ",{\"Desc\": \"" +home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				i = i + 1;
				//cnt = cnt +  rs.getString("CHR_HOR");
			}



			jstr += " ];" + "AmCharts.ready(function () {" + "chart3 = new AmCharts.AmPieChart();" + "chart3.dataProvider = chartData3;" + "chart3.outlineColor = \"#FFFFFF\";" + "chart3.outlineAlpha = 0.8;chart3.titleField = \"Desc\"; chart3.valueField = \"doccount\";" + "legend2= new AmCharts.AmLegend();" + "legend2.align = \"center\";" + "legend2.markerType = \"circle\";chart3.addLegend(legend2,'legenddiv2');" + "chart3.outlineThickness = 2;chart3.autoMargins = false; " + " chart3.marginTop = 0;chart3.marginBottom = 0;chart3.marginLeft = 0;chart3.marginRight = 0;" + "chart3.pullOutRadius = 15;chart3.balloonText = \"[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>\";    chart3.depth3D = 15;   chart3.angle = 30; chart3.write(\"Actitvitydiv\");" + "});";

			//	jstr += "$('#divTemplateSummary').height('200px');$('#legenddiv').height('200px');";

		}
		catch (RuntimeException ex)
		{

			jstr = "";
		} 
		return jstr;
	}

	@Override
	public String State_Wise_chart(String Sol_id, String User_ID) {
		String jstr = "";
		//int i = 0;
		String sql = "";
		long cnt = 0;
		try
		{

			sql="SELECT FIELD_NAME,FIELD_VALUE FROM TBL_DASHBOARD where chart_id=3  "+//and key_field=1" +
					//"WHERE CHART_ID=2 and KEY_FIELD in (select   zone_id from [TBL_USER_ZONE] where upper(user_id)='" + User_ID + "') group by FIELD_NAME" +
					"";	

			List<Home> home= null;

			home= getQuery.listResultSet(sql);
			System.out.println("sql::"+sql);


			jstr += "var chart4; var legend;" + "var chartData4 = " + "[";

			for(int i=0;i<home.size();i++)
			{
				if (i == 0)
				{
					jstr += "{\"Desc\": \"" + home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				else
				{
					jstr += ",{\"Desc\": \"" +home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				i = i + 1;
				//cnt = cnt +  rs.getString("CHR_HOR");
			}

			jstr += " ];" + "AmCharts.ready(function () {" + "chart4 = new AmCharts.AmPieChart();" + "chart4.dataProvider = chartData4;" + "chart4.outlineColor = \"#FFFFFF\";" + "chart4.outlineAlpha = 0.8;chart4.titleField = \"Desc\"; chart4.valueField = \"doccount\";" + "legend2= new AmCharts.AmLegend();" + "legend2.align = \"center\";" + "legend2.markerType = \"circle\";chart4.addLegend(legend2,'legenddiv2');" + "chart4.outlineThickness = 2;chart4.autoMargins = false; " + " chart4.marginTop = 0;chart4.marginBottom = 0;chart4.marginLeft = 0;chart4.marginRight = 0;" + "chart4.pullOutRadius = 15;chart4.balloonText = \"[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>\";    chart4.depth3D = 15;   chart4.angle = 30; chart4.write(\"Statewisediv\");" + "});";

			//	jstr += "$('#divTemplateSummary').height('200px');$('#legenddiv').height('200px');";

		}
		catch (RuntimeException ex)
		{

			jstr = "";
		} 

		return jstr;
	}

	@Override
	public void main() {
		// TODO Auto-generated method stub

	}

	/*public String Application_chart(String Sol_id,String User_ID) {

		String jstr = "";
		int i = 0;
		String sql = "";
		long cnt = 0;

		try
		{

			sql="SELECT FIELD_NAME,FIELD_VALUE total FROM TBL_DASHBOARD where chart_id=1  "+//and key_field=1" +
					//"WHERE CHART_ID=1 and KEY_FIELD in (select   zone_id from [TBL_USER_ZONE] where upper(user_id)='" + User_ID + "')" +
					"";		

			ResultSet rs= null;

			rs= ds.get_ResultSet(sql);
			System.out.println("sql::"+sql);


			jstr += "var chart; var legend;" + "var chartData = " + "[";

			for(int i=0;i<home.size();i++)
			{
				if (i == 0)
				{
					jstr += "{\"Desc\": \"" + home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				else
				{
					jstr += ",{\"Desc\": \"" +home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				i = i + 1;
			}



			jstr += " ];" + "AmCharts.ready(function () {" + "chart = new AmCharts.AmPieChart();" + "chart.dataProvider = chartData;" + "chart.outlineColor = \"#FFFFFF\";" + "chart.outlineAlpha = 0.8;chart.titleField = \"Desc\"; chart.valueField = \"doccount\";" + "legend = new AmCharts.AmLegend();" + "legend.align = \"center\";" + "legend.markerType = \"circle\";chart.addLegend(legend,'legenddiv');" + "chart.outlineThickness = 2;chart.autoMargins = false; " + " chart.marginTop = 0;chart.marginBottom = 0;chart.marginLeft = 0;chart.marginRight = 0;" + "chart.pullOutRadius = 15;chart.balloonText = \"[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>\";    chart.depth3D = 15;   chart.angle = 30; chart.write(\"divTemplateSummary\");" + "});";

		}
		catch (RuntimeException ex)
		{

			jstr = "";
		}

		return jstr;

	}

	public String  Month_chart(String Sol_id,String User_ID) throws Exception {
		String jstr = "";
		int i = 0;
		String sql = "";
		long cnt = 0;
		try
		{

			sql="SELECT FIELD_NAME,FIELD_VALUE as total FROM TBL_DASHBOARD where chart_id=2  "+//and key_field=1" +
					//"WHERE CHART_ID=2 and KEY_FIELD in (select   zone_id from [TBL_USER_ZONE] where upper(user_id)='" + User_ID + "') group by FIELD_NAME" +
					"";	




			DBUtil ds= new DBUtil();

			ResultSet rs= null;

			rs= ds.get_ResultSet(sql);
			System.out.println("sql::"+sql);


			jstr += "var chart2; var legend;" + "var chartData2 = " + "[";

			for(int i=0;i<home.size();i++)
			{
				if (i == 0)
				{
					jstr += "{\"Desc\": \"" + home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				else
				{
					jstr += ",{\"Desc\": \"" +home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				i = i + 1;
				//cnt = cnt +  rs.getString("CHR_HOR");
			}



			jstr += " ];" + "AmCharts.ready(function () {" + "chart2 = new AmCharts.AmPieChart();" + "chart2.dataProvider = chartData2;" + "chart2.outlineColor = \"#FFFFFF\";" + "chart2.outlineAlpha = 0.8;chart2.titleField = \"Desc\"; chart2.valueField = \"doccount\";" + "legend2= new AmCharts.AmLegend();" + "legend2.align = \"center\";" + "legend2.markerType = \"circle\";chart2.addLegend(legend2,'legenddiv2');" + "chart2.outlineThickness = 2;chart2.autoMargins = false; " + " chart2.marginTop = 0;chart2.marginBottom = 0;chart2.marginLeft = 0;chart2.marginRight = 0;" + "chart2.pullOutRadius = 15;chart2.balloonText = \"[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>\";    chart2.depth3D = 15;   chart2.angle = 30; chart2.write(\"chartdiv\");" + "});";

			//	jstr += "$('#divTemplateSummary').height('200px');$('#legenddiv').height('200px');";

		}
		catch (RuntimeException ex)
		{

			jstr = "";
		}

		return jstr;

	}

	public String  Log_chart(String Sol_id,String User_ID) throws Exception {String jstr = "";
	int i = 0;
	String sql = "";
	long cnt = 0;
	try
	{

		sql="SELECT FIELD_NAME,FIELD_VALUE as total FROM TBL_DASHBOARD where chart_id=5  "+//and key_field=1" +
				//"WHERE CHART_ID=2 and KEY_FIELD in (select   zone_id from [TBL_USER_ZONE] where upper(user_id)='" + User_ID + "') group by FIELD_NAME" +
				"";	




		DBUtil ds= new DBUtil();

		ResultSet rs= null;

		rs= ds.get_ResultSet(sql);
		System.out.println("sql::"+sql);


		jstr += "var chart3; var legend;" + "var chartData3 = " + "[";

		for(int i=0;i<home.size();i++)
		{
			if (i == 0)
			{
				jstr += "{\"Desc\": \"" + home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
			}
			else
			{
				jstr += ",{\"Desc\": \"" +home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
			}
			i = i + 1;
			//cnt = cnt +  rs.getString("CHR_HOR");
		}



		jstr += " ];" + "AmCharts.ready(function () {" + "chart3 = new AmCharts.AmPieChart();" + "chart3.dataProvider = chartData3;" + "chart3.outlineColor = \"#FFFFFF\";" + "chart3.outlineAlpha = 0.8;chart3.titleField = \"Desc\"; chart3.valueField = \"doccount\";" + "legend2= new AmCharts.AmLegend();" + "legend2.align = \"center\";" + "legend2.markerType = \"circle\";chart3.addLegend(legend2,'legenddiv2');" + "chart3.outlineThickness = 2;chart3.autoMargins = false; " + " chart3.marginTop = 0;chart3.marginBottom = 0;chart3.marginLeft = 0;chart3.marginRight = 0;" + "chart3.pullOutRadius = 15;chart3.balloonText = \"[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>\";    chart3.depth3D = 15;   chart3.angle = 30; chart3.write(\"Actitvitydiv\");" + "});";

		//	jstr += "$('#divTemplateSummary').height('200px');$('#legenddiv').height('200px');";

	}
	catch (RuntimeException ex)
	{

		jstr = "";
	}

	return jstr;
	}
	public String  State_Wise_chart(String Sol_id,String User_ID) throws Exception {
		String jstr = "";
		int i = 0;
		String sql = "";
		long cnt = 0;
		try
		{

			sql="SELECT FIELD_NAME,FIELD_VALUE as total FROM TBL_DASHBOARD where chart_id=3  "+//and key_field=1" +
					//"WHERE CHART_ID=2 and KEY_FIELD in (select   zone_id from [TBL_USER_ZONE] where upper(user_id)='" + User_ID + "') group by FIELD_NAME" +
					"";	




			DBUtil ds= new DBUtil();

			ResultSet rs= null;

			rs= ds.get_ResultSet(sql);
			System.out.println("sql::"+sql);


			jstr += "var chart4; var legend;" + "var chartData4 = " + "[";

			for(int i=0;i<home.size();i++)
			{
				if (i == 0)
				{
					jstr += "{\"Desc\": \"" + home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				else
				{
					jstr += ",{\"Desc\": \"" +home.get(i).getFIELD_NAME() + "\",\"doccount\": " + home.get(i).getTOTAL() + "}";
				}
				i = i + 1;
				//cnt = cnt +  rs.getString("CHR_HOR");
			}



			jstr += " ];" + "AmCharts.ready(function () {" + "chart4 = new AmCharts.AmPieChart();" + "chart4.dataProvider = chartData4;" + "chart4.outlineColor = \"#FFFFFF\";" + "chart4.outlineAlpha = 0.8;chart4.titleField = \"Desc\"; chart4.valueField = \"doccount\";" + "legend2= new AmCharts.AmLegend();" + "legend2.align = \"center\";" + "legend2.markerType = \"circle\";chart4.addLegend(legend2,'legenddiv2');" + "chart4.outlineThickness = 2;chart4.autoMargins = false; " + " chart4.marginTop = 0;chart4.marginBottom = 0;chart4.marginLeft = 0;chart4.marginRight = 0;" + "chart4.pullOutRadius = 15;chart4.balloonText = \"[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>\";    chart4.depth3D = 15;   chart4.angle = 30; chart4.write(\"Statewisediv\");" + "});";

			//	jstr += "$('#divTemplateSummary').height('200px');$('#legenddiv').height('200px');";

		}
		catch (RuntimeException ex)
		{

			jstr = "";
		}

		return jstr;

	}
	public static void main(String[] args) throws Exception {


		Chart as= new Chart();



	}
	 */
}
