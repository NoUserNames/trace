package rt.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;
import rt.dao.IQueryMissScanDAOImpl;
import rt.util.ExportExcelUtil;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

public class QueryMissScanAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2857849222806637952L;

	private boolean travel_falg;
	private String partNO;
	private String process;
	private String timeB;
	private String timeE;
	private String miss;
	private String processID;
	private String p_exp_name;
	
	private List<Map<String,Object>> listParts;
	private List<Map<String,Object>> listProcesses;
	private List<Map<String,Object>> listTerminals;
	private List<LinkedHashMap<String,Object>> listMaps;
	private int listMapsSize;
	
	private IQueryDAO queryDAO;
	private IQueryMissScanDAOImpl missScanDAO;
	
	public void fillCBO(){
		queryDAO = new IQueryDAOImpl();
		listParts = queryDAO.queryPart();

		//process cbo
		if(null == partNO)
			listProcesses = new ArrayList<Map<String,Object>>();
		else{
			listProcesses = queryDAO.queryProcessByRoute(partNO);
		}
		//terminal cbo
		if(null == processID)
			listTerminals = new ArrayList<Map<String,Object>>();
		else{
			listTerminals = queryDAO.queryTerminalByProcess(processID);
		}
	}
	
	public String initMissScanQry(){
		fillCBO();
		return SUCCESS;
	}
	
	public String missScan(){
		timeB = timeB.length() != 0 ? timeB : TUtil.format("yyyy-MM-dd")+" 00:00";
		timeE = timeE.length() != 0 ? timeE : TUtil.format("yyyy-MM-dd")+" 23:59";
		process = process  !=null ? process : "0";
		String partNO_S = partNO.substring(partNO.lastIndexOf(",")+1);
		
		missScanDAO = new IQueryMissScanDAOImpl();
		listMaps = missScanDAO.queryMissScan(partNO_S, process, timeB, timeE, travel_falg);
		listMapsSize = listMaps.size();
		queryDAO = new IQueryDAOImpl();
		listParts = queryDAO.queryPart();
		
		ExportExcelUtil excel = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("制程名称");
		listColumns.add("站点名称");
		listColumns.add("漏扫类别代码");
		listColumns.add("记录描述");
		listColumns.add("记录时间");
		listColumns.add("箱号");
		listColumns.add("OUT_PROCESS_TIME");
		listColumns.add("作业员工号");
		listColumns.add("作业员");

		miss = excel.exportExcelOrderly(ServletActionContext.getServletContext(), "MissScan_"+p_exp_name, listMaps, listColumns);

		listMaps = listMaps.size() > 21 ? listMaps.subList(0, 20) : listMaps;
		
		fillCBO();
		return SUCCESS;
	}
	
	public void missScanExp(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String files = Struts2Utils.getRequest().getParameter("missExp");
		excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,files);
	}
	
	/*********公共属性区***********/
	public List<Map<String, Object>> getListParts() {
		return listParts;
	}

	public void setListParts(List<Map<String, Object>> listParts) {
		this.listParts = listParts;
	}

	public boolean isTravel_falg() {
		return travel_falg;
	}

	public void setTravel_falg(boolean travel_falg) {
		this.travel_falg = travel_falg;
	}

	public String getPartNO() {
		return partNO;
	}

	public void setPartNO(String partNO) {
		this.partNO = partNO;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getTimeB() {
		return timeB;
	}

	public void setTimeB(String timeB) {
		this.timeB = timeB;
	}

	public String getTimeE() {
		return timeE;
	}

	public void setTimeE(String timeE) {
		this.timeE = timeE;
	}

	public List<LinkedHashMap<String, Object>> getListMaps() {
		return listMaps;
	}

	public void setListMaps(List<LinkedHashMap<String, Object>> listMaps) {
		this.listMaps = listMaps;
	}

	public String getMiss() {
		return miss;
	}

	public void setMiss(String miss) {
		this.miss = miss;
	}

	public List<Map<String, Object>> getListProcesses() {
		return listProcesses;
	}

	public void setListProcesses(List<Map<String, Object>> listProcesses) {
		this.listProcesses = listProcesses;
	}

	public List<Map<String, Object>> getListTerminals() {
		return listTerminals;
	}

	public void setListTerminals(List<Map<String, Object>> listTerminals) {
		this.listTerminals = listTerminals;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public int getListMapsSize() {
		return listMapsSize;
	}

	public void setListMapsSize(int listMapsSize) {
		this.listMapsSize = listMapsSize;
	}

	public String getP_exp_name() {
		return p_exp_name;
	}

	public void setP_exp_name(String p_exp_name) {
		this.p_exp_name = p_exp_name;
	}
	
}