package com.miscot.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.miscot.springmvc.bean.UserMasterBean;
import com.miscot.springmvc.service.UserMasterImpl;

@Controller
public class GenerateDocController {
	@Autowired
	UserMasterImpl uservice;

	@RequestMapping(value = "/downloadDoc", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("userMasterBean") UserMasterBean userMasterBean, BindingResult result,
			@RequestParam String operation) {
		String view = "";
		String fileType = userMasterBean.getFileType();
		if (fileType.equalsIgnoreCase("Excel")) {
			try {
				String fileName = "";
				XSSFWorkbook workbook = new XSSFWorkbook();
				if (operation.equalsIgnoreCase("UserAccessLog")) {
					workbook = uservice.GetUserAcessLogExcel(userMasterBean, workbook);
					fileName = "User Access Log";
				} else if (operation.equalsIgnoreCase("UserModificationLog")) {
					workbook = uservice.GetUserModificationLogExcel(userMasterBean, workbook);
					fileName = "User Modification Log";
				} else if (operation.equalsIgnoreCase("AppModificationLog")) {
					workbook = uservice.GetUserAppModificationLogExcel(userMasterBean, workbook);
					fileName = "App Modification Log";
				}
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
				workbook.write(response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (fileType.equalsIgnoreCase("PDF")) {
			try {
				String fileName = "";
				Document document = new Document();
				if (operation.equalsIgnoreCase("UserAccessLog")) {
					response.setContentType("application/pdf");
					fileName = "User Access Log";
					response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
					PdfWriter.getInstance(document, response.getOutputStream());
					document = uservice.GetUserAcessLogPDF(userMasterBean, document);
				} else if (operation.equalsIgnoreCase("UserModificationLog")) {
					response.setContentType("application/pdf");
					fileName = "User Modification Log";
					response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
					PdfWriter.getInstance(document, response.getOutputStream());
					document = uservice.GetUserModificationLogPDF(userMasterBean, document);
				} else if (operation.equalsIgnoreCase("AppModificationLog")) {
					response.setContentType("application/pdf");
					fileName = "App Modification Log";
					response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
					PdfWriter.getInstance(document, response.getOutputStream());
					document = uservice.GetUserAppModificationLogPDF(userMasterBean, document);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return view;
	}
}
