package rt.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;
import rt.util.ExportExcelUtil;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

public class QueryDefectsBySNAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9154502838239200388L;

	IQueryDAO query;
	
	private List<LinkedHashMap<String,Object>> listDefectsMaps;//按机种、制程、沾点查询的不良信息  按序号也用此变量
//	private static List<LinkedHashMap<String,Object>> listDefectsMapsPages;
	private String serial;
	private String defectExpName;

//	public static void setListDefectsMapsPages(
//			List<LinkedHashMap<String, Object>> listDefectsMapsPages) {
//		QueryDefectsBySNAction.listDefectsMapsPages = listDefectsMapsPages;
//	}
	
	public String queryDefectBySN(){
		query = new IQueryDAOImpl();
		listDefectsMaps = query.queryDefectBySN(serial.trim());
		ExportExcelUtil excel = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("判定不良制程");
		listColumns.add("判定不良站点");
		listColumns.add("判定时间");
		listColumns.add("中文不良描述");
		listColumns.add("英文不良描述");
		listColumns.add("判定人员");
		listColumns.add("责任归属");
		defectExpName = excel.exportExcelOrderly(ServletActionContext.getServletContext(), TUtil.format("yyyyMMddHHmmssssss"), listDefectsMaps, listColumns);
		
		return SUCCESS;
	}
	
	/**
	 * 导出不良信息(按制程)到Excel
	 */
	public void exprotDefectsBySN(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String files = Struts2Utils.getRequest().getParameter("defectExp");
		excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,files);
	}
	
	public String getSerial() {
		return serial;
	}
	
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	public String queryDefectBySNRedirect(){
		return SUCCESS;
	}
	
	public IQueryDAO getQuery() {
		return query;
	}

	public void setQuery(IQueryDAO query) {
		this.query = query;
	}

	public List<LinkedHashMap<String, Object>> getListDefectsMaps() {
		return listDefectsMaps;
	}

	public void setListDefectsMaps(
			List<LinkedHashMap<String, Object>> listDefectsMaps) {
		this.listDefectsMaps = listDefectsMaps;
	}

//	public static List<LinkedHashMap<String, Object>> getListDefectsMapsPages() {
//		return listDefectsMapsPages;
//	}

	public String getDefectExpName() {
		return defectExpName;
	}

	public void setDefectExpName(String defectExpName) {
		this.defectExpName = defectExpName;
	}
	
}