/*package com.miscot.springmvc.helper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class GenerateExcel extends AbstractExcelView{
	
 public void getExcel(String Sql) {
	 HSSFWorkbook workbook = null;
	 Sheet sheet = workbook.createSheet("Excel");
	    sheet.setDefaultColumnWidth(30);

	    Row header = sheet.createRow(0);
	    
	 List <SQLColumn> colDetails=db.getRSMetaData(Sql);
	 for(int i=0;i<colDetails.size();i++)
		{
		 header.createCell(i).setCellValue(colDetails.get(i).getName().toUpperCase().replaceAll("_", ""));
		 colDetails.get(i).getName().toUpperCase();
		 System.out.println(colDetails.get(i).getName().toUpperCase());
		}
	 int rowCount = 1;
	 for (Book aBook : listBooks) {
         HSSFRow aRow = (HSSFRow) sheet.createRow(rowCount++);
         aRow.createCell(0).setCellValue(aBook.getTitle());
         aRow.createCell(1).setCellValue(aBook.getAuthor());
         aRow.createCell(2).setCellValue(aBook.getIsbn());
         aRow.createCell(3).setCellValue(aBook.getPublishedDate());
         aRow.createCell(4).setCellValue(aBook.getPrice());
     }
 }

@Override
protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
}
}
*/