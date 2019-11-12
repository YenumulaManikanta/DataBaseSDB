package com.miscot.springmvc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.miscot.springmvc.repository.GetQueryImpl;

@Controller
public class LoginController {
	
	@Autowired 
	GetQueryImpl getQuery;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() {
		return "Login";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String user( Model model) {
		System.out.println("User Page Requested");
		//model.addAttribute("userName", user.getUserName());
		return "Login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model,HttpServletRequest request,HttpSession session) {
		/*session= request.getSession(false);
	    SecurityContextHolder.clearContext();
	         session= request.getSession(false);
	        if(session != null) {
	            session.invalidate();
	        }
	        for(Cookie cookie : request.getCookies()) {
	            cookie.setMaxAge(0);
	        }

	    return "logout";*/
		session.invalidate();
		model.addAttribute("err", "Logged Out Successfully");
		return "Login";
	}
}
