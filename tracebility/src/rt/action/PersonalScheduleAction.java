package rt.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import rt.util.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

public class PersonalScheduleAction extends ActionSupport {

	/**
	 * 个人工作进度跟踪
	 * @author 张强
	 */
	private static final long serialVersionUID = -1712714524846162930L;
	private List<Map<String,Object>> list;
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	public String initPersonalProgress(){
		HttpServletResponse response = Struts2Utils.getResponse();
		response.reset();//少了这一句 
		  response.setContentType("application/ms-excel;charset=UTF-8"); 
		  response.setHeader("Content-Disposition", "inline;filename=sheet1.xls"); 
		  try { 
		   HSSFWorkbook wb = new HSSFWorkbook(); 
		      HSSFSheet sheet = wb.createSheet("sheet1"); 

		      HSSFRow row = sheet.createRow((short)0); 
		      HSSFCell cell = row.createCell((short)0); 
		      cell.setCellValue(1); 

		     
		      row.createCell((short)1).setCellValue(1.2); 
		      row.createCell((short)2).setCellValue("This is a string"); 
		      row.createCell((short)3).setCellValue(true); 

		     
		      OutputStream fileOut = response.getOutputStream(); 
		      wb.write(fileOut); 
		      fileOut.flush(); 
		      fileOut.close(); 
		  } catch (IOException e1) { 
			   
			   e1.printStackTrace(); 
			  } 
		
//		String filePath = Struts2Utils.getServletContext().getRealPath("/")+"\\upload\\個人事項進度追蹤表.xlsx";
//		List<Map<String,Object>> dataList1= new ArrayList<Map<String,Object>>();
//		boolean flag = false;
//		Workbook wb = null;
//        if(flag){//2003
//        	System.out.println(2003);
//            File f = new File("");
//             
//            FileInputStream is;
//			try {
//				is = new FileInputStream(f);
//				POIFSFileSystem fs = new POIFSFileSystem(is);   
//				wb = new HSSFWorkbook(fs);
//				is.close();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}finally {
//				try {
//					wb.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//        }else{//2007
//            try {System.out.println(2007);
//				wb = new XSSFWorkbook(filePath);
//				Sheet sheet = wb.getSheetAt(0);
//				int rowNum = sheet.getLastRowNum();
//				
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//				for(int i=2;i<rowNum;i++){
//					Map<String,Object> map = new HashMap<String,Object>();
//					int a = (int) sheet.getRow(i).getCell(0).getNumericCellValue();
//					map.put("a", a);
//					String b = sheet.getRow(i).getCell(1).getStringCellValue();
//					map.put("b", b);
//					String c = sheet.getRow(i).getCell(2).getStringCellValue();
//					map.put("c", c);
//					String d = sheet.getRow(i).getCell(3).getStringCellValue();
//					map.put("d", d);
//					String e = sheet.getRow(i).getCell(4).getStringCellValue();
//					map.put("e", e);
//					Date date = sheet.getRow(i).getCell(5).getDateCellValue();
//					map.put("f", format.format(date));
//					Object g = null;
//					if(sheet.getRow(i).getCell(6).getCellType() == 1){
//						g = sheet.getRow(i).getCell(6).getStringCellValue();
//						map.put("g", g);
//					}else{
//						g = sheet.getRow(i).getCell(6).getDateCellValue();
//						map.put("g", format.format(g));
//					}
//					String h = sheet.getRow(i).getCell(7).getStringCellValue();
//					map.put("h", h);
////					System.out.println(a+"\t"+b+"\t"+c+"\t"+d+"\t"+e+"\t"+date+"\t"+g+"\t"+h);
//					dataList1.add(map);
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}finally {
//				try {
//					wb.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//        }
//		list = dataList1;
		return null;
	}
}