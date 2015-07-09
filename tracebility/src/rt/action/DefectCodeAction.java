package rt.action;

import java.io.UnsupportedEncodingException;
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

/**
 * @author 张强
 * 不良代码处理
 */
public class DefectCodeAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -679240111635367677L;
	
	private String processID;
	private String partNO;
	private String terminalID;
	private String codeExp;
	private String p_exp_name;
	
	private IQueryDAO queryDAO;
	private List<Map<String,Object>> listParts;
	private List<Map<String,Object>> listProcesses;
	private List<Map<String,Object>> listTerminals;
	private List<LinkedHashMap<String, Object>> list;
	private int listSize;
	
	public void fillCBO(){
		queryDAO = new IQueryDAOImpl();
		listParts = queryDAO.queryPart();

		//process cbo
		if(null == partNO)
			listProcesses = new ArrayList<Map<String,Object>>();
		else{
			listProcesses = queryDAO.queryProcessByRoute(partNO);
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
			listTerminals = queryDAO.queryTerminalByProcess(processID);
		}
	}
	
	/**
	 * 初始化查询
	 * @return
	 */
	public String initDefectCode(){
		fillCBO();
		return SUCCESS;
	}

	public String qryDefectCode(){
		fillCBO();
		queryDAO = new IQueryDAOImpl();
		list = queryDAO.queryDefectCodeByProcessID(processID);
		
		ExportExcelUtil excel = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("defect_id");
//		listColumns.add("defect_code");
		listColumns.add("中文不良描述");
		listColumns.add("英文不良描述");
		listColumns.add("不良类别");
		listColumns.add("责任归属");

		if(list.size() > 0)
			codeExp = excel.exportExcelOrderly(ServletActionContext.getServletContext(), TUtil.format("yyyyMMddHHmmssssss"), list, listColumns);
		listSize = list.size();
		if(list.size() > 21)
			list = list.subList(0, 20);
		return SUCCESS;
	}
	
	public void expDCode(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		try {
			String files=new String(Struts2Utils.getRequest().getParameter("dCodeExp").getBytes("ISO-8859-1"),"UTF-8");
			excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,"不良代码"+".xlsx");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***********************公共属性声明区域****************************/
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

	public IQueryDAO getQueryDAO() {
		return queryDAO;
	}

	public void setQueryDAO(IQueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}

	public List<Map<String, Object>> getListParts() {
		return listParts;
	}

	public void setListParts(List<Map<String, Object>> listParts) {
		this.listParts = listParts;
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

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getListSize() {
		return listSize;
	}

	public List<LinkedHashMap<String, Object>> getList() {
		return list;
	}

	public void setList(List<LinkedHashMap<String, Object>> list) {
		this.list = list;
	}

	public String getP_exp_name() {
		return p_exp_name;
	}

	public void setP_exp_name(String p_exp_name) {
		this.p_exp_name = p_exp_name;
	}

	public String getCodeExp() {
		return codeExp;
	}

	public void setCodeExp(String codeExp) {
		this.codeExp = codeExp;
	}
	
}
