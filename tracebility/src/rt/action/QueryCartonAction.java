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

public class QueryCartonAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7432681655823389187L;

	private String terminalName;
	private String cartons;
	private String cartonNO;
	private String type;// 状态/过站记录
	private String cartonExpName;

	private static int cartonFlag;//单个箱号 || 多个箱号,1：单箱，2：多箱
	
	private List<LinkedHashMap<String, Object>> listP;
	private List<Object> terminalNames;
	
	IQueryDAO query;
	
	/**
	 * 查箱号初始化
	 * @return
	 */
	public String initQryCarton(){
		return SUCCESS;
	}
	
	//按箱号查
	public String queryByCarton(){
		query = new IQueryDAOImpl();

		String empCarton = cartons.replaceAll("\r\n", ";");
		String[] cartonArray = empCarton.split(";");
		if(cartonArray.length > 1){//多个查询
			setCartonFlag(2);
			listP = query.queryByCarton(cartonArray);
		}else{//单箱
			setCartonFlag(1);
			terminalName = (0 != terminalName.trim().length() ? terminalName.trim() : "");
			listP = query.queryByCarton(cartonArray[0].trim(), type, terminalName);
			terminalNames = query.DuplicateTerminalNames();
		}
		
		ExportExcelUtil excel = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("工单类别");
		listColumns.add("产品序列号");
		listColumns.add("机种");
		listColumns.add("扫描站点");
		listColumns.add("箱号");
		listColumns.add("LOGO编号");
		listColumns.add("FATP标签");
		listColumns.add("扫描时间");
		
		cartonExpName = excel.exportExcelOrderly(ServletActionContext.getServletContext(), TUtil.format("yyyyMMddHHmmssssss"), listP, listColumns);
		return SUCCESS;
	}
	
	public void exprot(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String files = Struts2Utils.getRequest().getParameter("expCarton");
		excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,files);
	}

	public static int getCartonFlag() {
		return cartonFlag;
	}

	public static void setCartonFlag(int cartonFlag) {
		QueryCartonAction.cartonFlag = cartonFlag;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public List<Object> getTerminalNames() {
		return terminalNames;
	}

	public void setTerminalNames(List<Object> terminalNames) {
		this.terminalNames = terminalNames;
	}

	public String getCartons() {
		return cartons;
	}

	public void setCartons(String cartons) {
		this.cartons = cartons;
	}
	
	public String getCartonNO() {
		return cartonNO;
	}

	public void setCartonNO(String cartonNO) {
		this.cartonNO = cartonNO;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCartonExpName() {
		return cartonExpName;
	}

	public void setCartonExpName(String cartonExpName) {
		this.cartonExpName = cartonExpName;
	}

	public List<LinkedHashMap<String, Object>> getListP() {
		return listP;
	}

	public void setListP(List<LinkedHashMap<String, Object>> listP) {
		this.listP = listP;
	}
	
}