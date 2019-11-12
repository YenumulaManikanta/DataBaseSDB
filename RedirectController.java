package com.miscot.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.miscot.springmvc.bean.LoginBean;

@Controller
public class RedirectController {
	@RequestMapping(value = "/{redirectId}", method = RequestMethod.GET)
	public String redirectingPage(HttpSession session,HttpServletRequest request,@PathVariable String redirectId) {
				return redirectId;
	}
	}

	