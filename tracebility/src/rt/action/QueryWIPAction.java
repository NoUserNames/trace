package rt.action;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;
import rt.util.ExportExcelUtil;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

public class QueryWIPAction extends ActionSupport {

	private static final long serialVersionUID = 1203556610053754542L;

	private static Logger log = Logger.getLogger(QueryWIPAction.class);
	
	private String processWIP;
	private String partWIP;
	private String terminalWIP;
	private String timeB;
	private String timeE;
	private String wipExp;
	private int detailSize;
	
	private List<Map<String,Object>> listParts;
	private List<Map<String,Object>> listProcesses;
	private List<Map<String,Object>> listTerminals;
	private List<LinkedHashMap<String,Object>> listWIP;
	private List<LinkedHashMap<String,Object>> listWIPDetail;
	
	private IQueryDAO queryDAO;

	public void fillCBO(){
		queryDAO = new IQueryDAOImpl();
		listParts = queryDAO.queryPart();

		//process cbo
		if(null == partWIP)
			listProcesses = new ArrayList<Map<String,Object>>();
		else{
			listProcesses = queryDAO.queryProcessByRoute(partWIP);
		}
		//terminal cbo
		if(null == processWIP)
			listTerminals = new ArrayList<Map<String,Object>>();
		else{
			listTerminals = queryDAO.queryTerminalByProcess(processWIP);
		}
	}
	
	public String initQueryWIP(){
		fillCBO();
		return SUCCESS;
	}
	
	public String queryWIP(){
		queryDAO = new IQueryDAOImpl();
		timeB = timeB.length() != 0 ? timeB : TUtil.format("yyyy-MM-dd")+" 00:00";
		timeE = timeE.length() != 0 ? timeE : TUtil.format("yyyy-MM-dd")+" 23:59";

		processWIP = processWIP.length() !=0 ? processWIP : "0";
		terminalWIP = terminalWIP.length() !=0 ? terminalWIP : "0";

		Struts2Utils.getRequest().setAttribute("partNO", partWIP);
		if(!processWIP.equals("0")){// 有制程/机种
			listWIP = queryDAO.queryWIP(processWIP, partWIP, terminalWIP, timeB, timeE);
		}else{//按料号查
			listWIP = queryDAO.queryWIP(partWIP, timeB, timeE);
		}
		fillCBO();
		return SUCCESS;
	}

	public String queryWIPDetail(){
		String p_id = ServletActionContext.getRequest().getParameter("p_id");
		String t_id = ServletActionContext.getRequest().getParameter("t_id");
		String part_no = ServletActionContext.getRequest().getParameter("part_no");
		queryDAO = new IQueryDAOImpl();

		p_id = p_id.length() !=0 ? p_id : "0";
		t_id = t_id.length() !=0 ? t_id : "0";

		listWIPDetail = queryDAO.queryWIPDetails(p_id, part_no,t_id, timeB, timeE);
		detailSize = listWIPDetail.size();
		detailSize = detailSize >= 20 ? 19 : detailSize - 1;
		detailSize = detailSize != -1 ? detailSize : 0;
		ExportExcelUtil excel = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("\u5E8F\u5217\u53F7");
		listColumns.add("作业站");
		listColumns.add("最后扫描时间");
		listColumns.add("箱号");
		listColumns.add("FATP条码");
		listColumns.add("状态");
		listColumns.add("作业员");

		wipExp = excel.exportExcelOrderly(ServletActionContext.getServletContext(), TUtil.format("yyyyMMddHHmmssssss"), listWIPDetail, listColumns);
		return SUCCESS;
	}
	
	public void exp(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String sourceFile;
		try {
			String download = new String(Struts2Utils.getRequest().getParameter("expName").getBytes("ISO-8859-1"),"UTF-8");
			sourceFile = new String(Struts2Utils.getRequest().getParameter("wipExp").getBytes("ISO-8859-1"),"UTF-8");
			excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+sourceFile,"滞留"+download+".xlsx");
		} catch (UnsupportedEncodingException e) {
			log.warn("WIP明细导出文件名转码异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String formatDouble(double s){
		DecimalFormat fmt = new DecimalFormat("##.##");
		return fmt.format(s);
	}
	
	/*
	 * 公共属性声明区域
	 */
	
	public String getProcessID() {
		return processWIP;
	}

	public void setProcessID(String processID) {
		this.processWIP = processID;
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

	public List<LinkedHashMap<String, Object>> getListWIP() {
		return listWIP;
	}

	public void setListWIP(List<LinkedHashMap<String, Object>> listWIP) {
		this.listWIP = listWIP;
	}

	public List<LinkedHashMap<String, Object>> getListWIPDetail() {
		return listWIPDetail;
	}

	public void setListWIPDetail(List<LinkedHashMap<String, Object>> listWIPDetail) {
		this.listWIPDetail = listWIPDetail;
	}

	public List<Map<String, Object>> getListParts() {
		return listParts;
	}

	public void setListParts(List<Map<String, Object>> listParts) {
		this.listParts = listParts;
	}

	public String getWipExp() {
		return wipExp;
	}

	public void setWipExp(String wipExp) {
		this.wipExp = wipExp;
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

	public String getProcessWIP() {
		return processWIP;
	}

	public void setProcessWIP(String processWIP) {
		this.processWIP = processWIP;
	}

	public String getPartWIP() {
		return partWIP;
	}

	public void setPartWIP(String partWIP) {
		this.partWIP = partWIP;
	}

	public String getTerminalWIP() {
		return terminalWIP;
	}

	public void setTerminalWIP(String terminalWIP) {
		this.terminalWIP = terminalWIP;
	}
	
}