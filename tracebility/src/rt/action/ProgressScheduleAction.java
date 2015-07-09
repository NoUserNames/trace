package rt.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import rt.util.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

public class ProgressScheduleAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7410849373243370742L;

	private List<Map<String,Object>> list;
	
	
	public String initProgress(){
		String filePath = Struts2Utils.getServletContext().getRealPath("/")+"\\upload\\程式開發需求.xlsx";
		List<Map<String,Object>> dataList1= new ArrayList<Map<String,Object>>();
		Workbook wb = null;
        
            try {
				wb = new XSSFWorkbook(filePath);
				Sheet sheet = wb.getSheetAt(0);
				int rowNum = sheet.getLastRowNum();
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				for(int i=0;i<rowNum;i++){
					Map<String,Object> map = new HashMap<String,Object>();
					int a = (int) sheet.getRow(i).getCell(0).getNumericCellValue();
					map.put("a", a);
					String b = sheet.getRow(i).getCell(1).getStringCellValue();
					map.put("b", b);
					String c = sheet.getRow(i).getCell(2).getStringCellValue();
					map.put("c", c);
					String d = sheet.getRow(i).getCell(3).getStringCellValue();
					map.put("d", d);
					String e = sheet.getRow(i).getCell(4).getStringCellValue();
					map.put("e", e);
					Date date = sheet.getRow(i).getCell(5).getDateCellValue();
					map.put("f", format.format(date));
//					Object g = null;
//					if(sheet.getRow(i).getCell(6).getCellType() == 1){
//						g = sheet.getRow(i).getCell(6).getStringCellValue();
//						map.put("g", g);
//					}else{
//						g = sheet.getRow(i).getCell(6).getDateCellValue();
//						map.put("g", format.format(g));
//					}
					System.out.println(a+"\t"+b+"\t"+c+"\t"+d+"\t"+e+"\t"+date);
					dataList1.add(map);
				}
				wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
		list = dataList1;
		// 读取excel文件数据
//		ArrayList<ArrayList<String>> dataList = helper.readExcel(filePath, 0, new String[]{"a","b","c","d","e","f","g"});
//		list = new ArrayList<Map<String,Object>>();
//		for(ArrayList<?> inner : dataList){
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("a", inner.get(0));
//			map.put("b", inner.get(1));
//			map.put("c", inner.get(2));
//			map.put("d", inner.get(3));
//			map.put("e", inner.get(4));
//			map.put("f", inner.get(5));
//			map.put("g", inner.get(6));
//			list.add(map);
//		}

		return SUCCESS;
	}
	
	public String qryProcessSchedule(){
//		String filePath = Struts2Utils.getServletContext()+"\\upload\\個人事項進度追蹤表.xlsx";
//		PoiExcelHelper helper = PoiExcelTest.getPoiExcelHelper();
//		
//		// 读取excel文件数据
//		ArrayList<ArrayList<String>> dataList = helper.readExcel(filePath, 0, new String[]{"a","b","c","d","e","f","g"});
//		for(ArrayList<?> array : dataList){
//			System.out.println(array);
//		}
		return SUCCESS;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}




	


}
