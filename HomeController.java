package com.miscot.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miscot.springmvc.repository.GetQueryImpl;
import com.miscot.springmvc.service.HomeImpl;

@Controller
public class HomeController {
	@Autowired 
	HomeImpl uservice;
	@Autowired
	GetQueryImpl getQuery;
	
	@RequestMapping(value = "/HomeController", method = RequestMethod.POST)
	@ResponseBody
	public String UserAuthentication(ModelMap model) {
		String application_chart="";
		String Chart="";
		try {
			
			
			String Month_chart="";
			String Log_chart="";
			String State_Wise_chart="";
			
			application_chart=uservice.Application_chart("", "");
			model.addAttribute("Application_chart", application_chart);
			System.out.println("Application_chart==="+application_chart);
			Month_chart=uservice.Month_chart("", "");
			model.addAttribute("Application_chart", Month_chart);
			/*model.addAttribute("Month_chart", Month_chart);
			Log_chart=uservice.Log_chart("", "");
			model.addAttribute("Log_chart", Log_chart);
			State_Wise_chart=uservice.State_Wise_chart("", "");
			model.addAttribute("State_Wise_chart", State_Wise_chart);*/
			Chart=application_chart+"$"+Month_chart;
			System.out.println("Chart==="+Chart);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return Chart;
	}
}
