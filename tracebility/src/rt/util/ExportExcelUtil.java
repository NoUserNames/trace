package rt.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportExcelUtil {

	private static Logger log = Logger.getLogger(ExportExcelUtil.class);
	private XSSFWorkbook wb = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	
	private FileOutputStream out = null;
	
	/**
	 * 利用List<LinkedHashMap>导出Excel，保证顺序
	 * @param context 上下文会话对象
	 * @param filePrefix 文件前缀名
	 * @param listMaps Map类数据集
	 * @return 输出filePath 读入文件目录，fileName 文件显示名称
	 */
	public String exportExcelOrderly(ServletContext context, String filePrefix, List<LinkedHashMap<String,Object>> listMaps,List<String> column){
		wb = new XSSFWorkbook();
		sheet = wb.createSheet();
		row = sheet.createRow(0);
		createColumnTitle(row,column);

		String fileName = (filePrefix != null) ? filePrefix : TUtil.format("yyyy_MM_dd_HHmmss");
		fileName += ".xlsx";
		String filePath = null ;
		int columns = 0;
		try {
			for(int i = 1;i<=listMaps.size(); i++){
				row = sheet.createRow(i);
				LinkedHashMap<String,Object> map = listMaps.get(i-1);
				Set<String> set = map.keySet();
				int index =0;
				for(Iterator<String> iter = set.iterator(); iter.hasNext();){//取所有键
					String key = (String)iter.next();
					String value = (String)map.get(key);//根据键取值
					row.createCell(index).setCellValue(value);//设置到cell中的值
					index ++;
				}
				columns = index;
			}
			for(int i =0;i<columns ;i++){//设置自动列宽
				sheet.autoSizeColumn(i);
			}
			String mes = context.getRealPath("/");
			filePath = mes+"excel\\"+fileName;//文件存放路径
			out = new FileOutputStream(filePath);
			wb.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			try {
				out.close();
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;//返回下载结果
	}
	
	/**
	 * 创建标题列
	 * @param row 标题行
	 * @param columns 标头集合
	 */
	public void createColumnTitle(XSSFRow row,List<String> columns){
		for(int i = 0; i < columns.size(); i++){
			row.createCell(i).setCellValue(columns.get(i));
		}		
	}
	
	/**
	 * 通用下载方法
	 * @param fileName 读入文件目录
	 * @param fileDisplayName 下载时默认显示文件名
	 */
	public boolean download(String fileName, String fileDisplayName){
		InputStream bis = null;
		OutputStream out = null;
		try {
			Struts2Utils.getResponse().setContentType("application/x-download");
			fileDisplayName = URLDecoder.decode(fileDisplayName, "utf-8");
			fileDisplayName= java.net.URLEncoder.encode(fileDisplayName,"utf-8");
			fileDisplayName = fileDisplayName.replaceAll("\\+", "%20");
			
			Struts2Utils.getResponse().setHeader("Content-Disposition","attachment;filename=\""+ new String(fileDisplayName.getBytes(),"UTF-8") +"\"");
			bis = new FileInputStream(fileName);
			out = Struts2Utils.getResponse().getOutputStream();
			byte[] buff = new byte[2048];
	        int bytesRead;

	        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	        	out.write(buff,0,bytesRead);
	        }
		} catch (IOException e) {
			log.error("expProcessOutput Exception:"+e.getMessage());
			e.printStackTrace();
			return false;
		}finally{
			if(null != bis){
				try {
					bis.close();
				} catch (IOException e) {
					log.error("关闭输入流异常："+e.getMessage());
					e.printStackTrace();
				}
			}
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					log.error("关闭输出流异常："+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}