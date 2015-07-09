package rt.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;





//import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;
import rt.util.ExportExcelUtil;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

public class QueryProcessOutputAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -426693049146183474L;
//	private static Logger log = Logger.getLogger(QueryProcessOutputAction.class);
	private String processID;
	private String partNO;
	private String wtype;
	private String terminalID;
	private String timeB;
	private String timeE;
	private String routeID;
	private String p;
	private String t;
	private String files;
	private String filesDetail;
	private int outputDetailSize;
	private static String timeB_S;
	private static String timeE_S;
	private List<Map<String,Object>> listParts;
	private List<Map<String,Object>> listProcesses;
	private List<Map<String,Object>> listTerminals;
	private List<Map<String,Object>> listProcess;
	private List<Map<String,Object>> listTerminal;
	private List<LinkedHashMap<String,Object>> listResult;
	private List<LinkedHashMap<String,Object>> listResultDetail;
	
	private IQueryDAO queryDAO;
	
	public void fillCBO(){
		queryDAO = new IQueryDAOImpl();
		listParts = queryDAO.queryPart();
		processID = (null !=processID ? processID : "0");
		System.out.println("processID="+processID+"\tpartNO"+partNO);
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
	
	public String initQryProcessOutput(){
		
		fillCBO();
		return SUCCESS;
	}
	
	public String qryProcessOutput(){
		queryDAO = new IQueryDAOImpl();
		
		fillCBO();
		
		routeID = (null !=routeID ? routeID : "0");
//		System.out.println("processID="+processID+"\tpartNO"+partNO+"\trouteID"+routeID);
		if(routeID.lastIndexOf(",") != -1)
			routeID = routeID.substring(0,routeID.lastIndexOf(","));
		listProcess = queryDAO.queryProcessByRoute(routeID);
		if(listProcess.size()==0){
			listProcess = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("process_id", "");
			map.put("process_name", "");
			listProcess.add(map);
		}
		
		listTerminal = queryDAO.queryTerminalByProcess(processID);
		String partNO1 = partNO.substring(partNO.lastIndexOf(",")+1);
		processID = (processID.length() !=0 ? processID : "0");
		terminalID = (terminalID.length() !=0 ? terminalID : "0");
		timeB = timeB.length() != 0 ? timeB : TUtil.format("yyyy-MM-dd")+" 00:00";
		timeE = timeE.length() != 0 ? timeE : TUtil.format("yyyy-MM-dd")+" 23:59";
		timeB_S = timeB;
		timeE_S = timeE;
		
		System.out.println("qryProcessOutput="+partNO1+"\ttimeB="+timeB+"\ttimeE="+timeE+"\tprocessID="+processID+"\tterminalID="+terminalID);
		listResult = queryDAO.queryProcessOutput(partNO1, timeB, timeE, processID, terminalID);

		ExportExcelUtil excelUtil = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("proname");
		listColumns.add("wo_type");
		listColumns.add("cnt");
		listColumns.add("ok");
		listColumns.add("ng");
		listColumns.add("seq");
		files = excelUtil.exportExcelOrderly(ServletActionContext.getServletContext(), "ProcessOutput_", listResult, listColumns);
		return SUCCESS;
	}
	
	public void expProcessOutput(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String files = Struts2Utils.getRequest().getParameter("files");
		excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,files);
	}
	
	public String qryProcessOutputDetail(){
		System.out.println("qryProcessOutputDetail="+partNO+"\ttimeB="+timeB_S+"\ttimeE="+timeE_S+"\tp="+p+"\tterminalID="+t+"\tpartNO"+partNO);
		queryDAO = new IQueryDAOImpl();
		String partNO_S = partNO.substring(partNO.lastIndexOf(",")+1);
		listResultDetail = queryDAO.queryProcessOutputDetail(partNO_S,wtype,timeB_S, timeE_S, p, t);
		outputDetailSize = listResultDetail.size();
		outputDetailSize = outputDetailSize >= 20 ? 19 : outputDetailSize - 1;
		outputDetailSize = outputDetailSize != -1 ? outputDetailSize : 0;
		ExportExcelUtil excel = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("工单类别");
		listColumns.add("产品序列号");
		listColumns.add("查询制程");
		listColumns.add("过站时间");
		listColumns.add("当前制程");
		listColumns.add("当前站点");
		listColumns.add("最后扫描时间");
		listColumns.add("当前状态");

		filesDetail = excel.exportExcelOrderly(ServletActionContext.getServletContext(), "OutputDetail_"+p, listResultDetail, listColumns);				
		return SUCCESS;
	}
	
	public void expDetail(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String files;
		try {
			files = new String(Struts2Utils.getRequest().getParameter("filesDetail").getBytes("ISO-8859-1"),"UTF-8");
			excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,files);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//
	public List<Map<String, Object>> getListParts() {
		return listParts;
	}
	public void setListParts(List<Map<String, Object>> listParts) {
		this.listParts = listParts;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public String getPartNO() {
		return partNO;
	}

	public void setPartNO(String partNO) {
		this.partNO = partNO;
	}

	public String getTerminalID() {
		return terminalID;
	}

	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
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

	public static String getTimeB_S() {
		return timeB_S;
	}

	public static void setTimeB_S(String timeB_S) {
		QueryProcessOutputAction.timeB_S = timeB_S;
	}

	public static String getTimeE_S() {
		return timeE_S;
	}

	public static void setTimeE_S(String timeE_S) {
		QueryProcessOutputAction.timeE_S = timeE_S;
	}

	public List<Map<String, Object>> getListProcess() {
		return listProcess;
	}

	public void setListProcess(List<Map<String, Object>> listProcess) {
		this.listProcess = listProcess;
	}

	public String getRouteID() {
		return routeID;
	}

	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}

	public List<Map<String, Object>> getListTerminal() {
		return listTerminal;
	}

	public void setListTerminal(List<Map<String, Object>> listTerminal) {
		this.listTerminal = listTerminal;
	}
	public List<LinkedHashMap<String, Object>> getListResult() {
		return listResult;
	}

	public void setListResult(List<LinkedHashMap<String, Object>> listResult) {
		this.listResult = listResult;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public List<LinkedHashMap<String, Object>> getListResultDetail() {
		return listResultDetail;
	}

	public void setListResultDetail(List<LinkedHashMap<String, Object>> listResultDetail) {
		this.listResultDetail = listResultDetail;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getFilesDetail() {
		return filesDetail;
	}

	public void setFilesDetail(String filesDetail) {
		this.filesDetail = filesDetail;
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

	public int getOutputDetailSize() {
		return outputDetailSize;
	}

	public void setOutputDetailSize(int outputDetailSize) {
		this.outputDetailSize = outputDetailSize;
	}

	public String getWtype() {
		return wtype;
	}

	public void setWtype(String wtype) {
		this.wtype = wtype;
	}
	
}