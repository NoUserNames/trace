package rt.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;
import rt.util.ExportExcelUtil;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

public class QueryDefectsByProcessAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2395677785373302065L;

	IQueryDAO query;
	
	private String modelName;
	private String processID;//按制程ID查站点，查询不良函数使用
	private String terminalID;//站点ID，查询不良函数使用
	private String timeB;//开始时间，查询不良函数使用
	private String timeE;//结束时间，查询不良函数使用
	private String partNO;
	private String defectP;
	private int detailSize;
	
	private List<Map<String,Object>> listParts;
	private List<Map<String,Object>> listProcesses;
	private List<Map<String,Object>> listTerminals;
	private List<Map<String,Object>> listProcessMaps;
	private List<Map<String,Object>> listTerminalMaps;
	private List<Map<String,Object>> listPartMaps;
	private List<LinkedHashMap<String,Object>> listDefectsMaps;
	
	/**
	 * 填充机种、制程、站点下拉列表
	 */
	public void fillCBO(){
		listParts = query.queryPart();
			
		//process cbo
		if(null == partNO){
			listProcesses = new ArrayList<Map<String,Object>>();
		}else{
			listProcesses = query.queryProcessByRoute(partNO);
			List<Map<String,Object>> Processes = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> m : listProcesses){
				if(m.get("process_name").toString().endsWith("QC")){
					Processes.add(m);
				}
				listProcesses = Processes;
			}
		}
		//terminal cbo
		if(null == processID)
			listTerminals = new ArrayList<Map<String,Object>>();
		else{
			listTerminals = query.queryTerminalByProcess(processID);
		}
	}
	
	public String initQryDefect(){
		query = new IQueryDAOImpl();
		listParts = query.queryPart();
		
		fillCBO();

		return SUCCESS;
	}
	
	/**
	 * 查询不良信息
	 * @return
	 */
	public String queryDefectsMaps(){
		query = new IQueryDAOImpl();
		timeB = timeB.length() != 0 ? timeB : TUtil.format("yyyy-MM-dd")+" 00:00";
		timeE = timeE.length() != 0 ? timeE : TUtil.format("yyyy-MM-dd")+" 23:59";
		listDefectsMaps = query.queryDefectByProcess(processID, terminalID, timeB, timeE);
		detailSize = listDefectsMaps.size();
		detailSize = detailSize >= 20 ? 19 : detailSize - 1;
		detailSize = detailSize != -1 ? detailSize : 0;
		
		fillCBO();
		
		ExportExcelUtil excel = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("产品序列号");
		listColumns.add("中文不良描述");
		listColumns.add("英文不良描述");
		listColumns.add("不良判定时间");
		listColumns.add("判定状态");
		listColumns.add("判定人员");
		listColumns.add("责任归属");

		defectP = excel.exportExcelOrderly(ServletActionContext.getServletContext(), "Defect_"+processID, listDefectsMaps, listColumns);
		
		return SUCCESS;
	}
	
	/**
	 * 导出不良信息(按制程)到Excel
	 */
	public void exprotDefects(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String files = Struts2Utils.getRequest().getParameter("defectP");
		excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,files);
	}

	public IQueryDAO getQuery() {
		return query;
	}

	public void setQuery(IQueryDAO query) {
		this.query = query;
	}
	
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
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

	public List<Map<String, Object>> getListProcessMaps() {
		return listProcessMaps;
	}

	public void setListProcessMaps(List<Map<String, Object>> listProcessMaps) {
		this.listProcessMaps = listProcessMaps;
	}

	public List<Map<String, Object>> getListTerminalMaps() {
		return listTerminalMaps;
	}

	public void setListTerminalMaps(List<Map<String, Object>> listTerminalMaps) {
		this.listTerminalMaps = listTerminalMaps;
	}

	public List<Map<String, Object>> getListPartMaps() {
		return listPartMaps;
	}

	public void setListPartMaps(List<Map<String, Object>> listPartMaps) {
		this.listPartMaps = listPartMaps;
	}

	public List<LinkedHashMap<String, Object>> getListDefectsMaps() {
		return listDefectsMaps;
	}

	public void setListDefectsMaps(
			List<LinkedHashMap<String, Object>> listDefectsMaps) {
		this.listDefectsMaps = listDefectsMaps;
	}

	public List<Map<String, Object>> getListParts() {
		return listParts;
	}

	public void setListParts(List<Map<String, Object>> listParts) {
		this.listParts = listParts;
	}

	public String getDefectP() {
		return defectP;
	}

	public void setDefectP(String defectP) {
		this.defectP = defectP;
	}

	public String getPartNO() {
		return partNO;
	}

	public void setPartNO(String partNO) {
		this.partNO = partNO;
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

	public int getDetailSize() {
		return detailSize;
	}

	public void setDetailSize(int detailSize) {
		this.detailSize = detailSize;
	}
	
}