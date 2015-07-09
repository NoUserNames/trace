package rt.action.stageReport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;
import rt.dao.IUserDAO;
import rt.dao.IUserDAOImpl;
import rt.dao.impl.IPlanningStageReportDAOImpl;
import rt.dao.interfaces.IPlanningStageReportDAO;
import rt.pojo.SYS_STAGE_REPORT_SETTING;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 4小时报表工序管理
 * @author 张强
 *
 */
public class PlanningStageReportSettingAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3354712281542494362L;
	
	private String process_stage_report_add;
	private String part_stage_report_add;
	private String process;
	private String dept_name;
	private String erp_part_no;
	private String model_name;
	
	private List<Map<String,Object>> listParts;
	private List<Map<String,Object>> listProcesses;
	private String jsonPart;
	private List<Map<String,Object>> listTerminals;
	
	private IQueryDAO queryDAO;
	private IPlanningStageReportDAO iStageReportDAO;
	private String emp_no;
	private String emp_no_add;
	
	private List<Map<String, Object>> listSettings;
	
	private int numPerPage;
	private int totalCount;
	private int pageNumShown;
	private int pageNum;
	
	private String stage_report_setting_id;
	
	private int settingAddResult;

	public void test(){
		queryDAO = new IQueryDAOImpl();
		listParts = queryDAO.queryPart();

		try {
			Struts2Utils.getResponse().getWriter().print(jsonPart);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fillCBO(){
		queryDAO = new IQueryDAOImpl();
		listParts = queryDAO.queryPart();

		//process cbo
		if(null == part_stage_report_add){
			listProcesses = new ArrayList<Map<String,Object>>();
		} else {
			listProcesses = queryDAO.queryProcessByRoute(part_stage_report_add);
		}
		//terminal cbo
		if(null == process_stage_report_add)
			listTerminals = new ArrayList<Map<String,Object>>();
		else{
			listTerminals = queryDAO.queryTerminalByProcess(process_stage_report_add);
		}
	}
	
	public String initStageReportSetting(){
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		pageNum = pageNum < 1 ? 1 : pageNum;
		numPerPage = numPerPage < 1 ? 20 : numPerPage;
		listSettings = iStageReportDAO.queryReprotSetting(null);
		totalCount = listSettings.size();
		pageNumShown = totalCount % numPerPage == 0 ? totalCount / numPerPage : totalCount / numPerPage + 1;
		return SUCCESS;
	}
	
	public String initStageReportSettingAdd(){
		fillCBO();
		return SUCCESS;
	}

	public void getEmpName(){
		IUserDAO userDAO = new IUserDAOImpl();
		try {
			Struts2Utils.getResponse().getWriter().write(userDAO.getEmpName(emp_no.trim().toUpperCase()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stageReportSettingAdd() throws IOException{
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		SYS_STAGE_REPORT_SETTING sys_stage_report_setting = new SYS_STAGE_REPORT_SETTING();
		sys_stage_report_setting.setSTAGE_REPORT_SETTING_ID(TUtil.format("yyyyMMddHHmmssssss"));
		sys_stage_report_setting.setPART_NO(part_stage_report_add);
		sys_stage_report_setting.setPROCESS_ID(Integer.parseInt(process_stage_report_add));
		sys_stage_report_setting.setPROCESS(process);
		sys_stage_report_setting.setDEPT_NAME(dept_name);
		sys_stage_report_setting.setERP_PROCESS_ID(erp_part_no);
		sys_stage_report_setting.setMODEL_NAME(model_name);System.out.println(emp_no_add);
		if(iStageReportDAO.stageReportSettingAdd(sys_stage_report_setting) && iStageReportDAO.reprot_setting_emp(sys_stage_report_setting.getSTAGE_REPORT_SETTING_ID(), emp_no_add.trim().toUpperCase()))//&& 
			Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"工序添加成功\"}");
		else
			Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"工序添加失败，请重试。\"}");
	}
	
	public void reportSettingEmpAdd(){
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		iStageReportDAO.reprot_setting_emp(stage_report_setting_id, emp_no);
	}
	
	public String queryPalnReportSetting(){
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		listSettings = iStageReportDAO.queryReprotSetting(stage_report_setting_id);
		return SUCCESS;
	}
	
	/**
	 * 更新工序设定档
	 */
	public void updatePlanSetting(){
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		try {
			SYS_STAGE_REPORT_SETTING SYS_STAGE_REPORT_SETTING = new SYS_STAGE_REPORT_SETTING();
			SYS_STAGE_REPORT_SETTING.setSTAGE_REPORT_SETTING_ID(stage_report_setting_id);
			SYS_STAGE_REPORT_SETTING.setPROCESS(process);
			SYS_STAGE_REPORT_SETTING.setERP_PROCESS_ID(erp_part_no);
			SYS_STAGE_REPORT_SETTING.setMODEL_NAME(model_name);

			if (iStageReportDAO.updatePlanSetting(SYS_STAGE_REPORT_SETTING))
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"工序信息更新成功\",\"navTabId\":\"\",\"rel\":\"\",\"callbackType\":\"closeCurrent\",\"forwardUrl\":\"\",\"confirmMsg\":\"\"}");
			else
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"工序添加失败，请重试。\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deletePlanSetting(){
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		try {
			if (iStageReportDAO.deletePlanSetting(stage_report_setting_id))
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\", \"message\":\"删除工序操作成功。\"}");
			else
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"工序删除失败，请重试。\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 为工序更新可操作工号
	 */
	public void assignReprotSettingEmp(){
		try {
			iStageReportDAO = new IPlanningStageReportDAOImpl();
			emp_no = new String(Struts2Utils.getRequest().getParameter("emp_no").getBytes("ISO-8859-1"),"UTF-8");
			if(iStageReportDAO.reprot_setting_emp(stage_report_setting_id.trim(), emp_no.trim().toUpperCase()))
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"权限更新成功\",\"navTabId\":\"\",\"rel\":\"\",\"forwardUrl\":\"\",\"confirmMsg\":\"\"}");
			else 
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"权限更新失败，请重试。\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按工序查询可操作工号
	 */
	public void queryReportSettingEmp(){
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		try {
			Struts2Utils.getResponse().getWriter().print(iStageReportDAO.queryReportSettingEmp(stage_report_setting_id.trim()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**********************************************************/
	
	public String getStage_report_setting_id() {
		return stage_report_setting_id;
	}
	
	public String getEmp_no_add() {
		return emp_no_add;
	}

	public void setEmp_no_add(String emp_no_add) {
		this.emp_no_add = emp_no_add;
	}

	public int getSettingAddResult() {
		return settingAddResult;
	}

	public void setSettingAddResult(int settingAddResult) {
		this.settingAddResult = settingAddResult;
	}

	public void setStage_report_setting_id(String stage_report_setting_id) {
		this.stage_report_setting_id = stage_report_setting_id;
	}

	public List<Map<String, Object>> getListParts() {
		return listParts;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageNumShown() {
		return pageNumShown;
	}

	public void setPageNumShown(int pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public List<Map<String, Object>> getListSettings() {
		return listSettings;
	}

	public void setListSettings(List<Map<String, Object>> listSettings) {
		this.listSettings = listSettings;
	}

	public String getErp_part_no() {
		return erp_part_no;
	}

	public void setErp_part_no(String erp_part_no) {
		this.erp_part_no = erp_part_no;
	}

//	public String getEmp_info() {
//		return emp_info;
//	}
//
//	public void setEmp_info(String emp_info) {
//		this.emp_info = emp_info;
//	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getJsonPart() {
		return jsonPart;
	}

	public void setJsonPart(String jsonPart) {
		this.jsonPart = jsonPart;
	}

	public String getProcess_stage_report_add() {
		return process_stage_report_add;
	}

	public void setProcess_stage_report_add(String process_stage_report_add) {
		this.process_stage_report_add = process_stage_report_add;
	}

	public String getPart_stage_report_add() {
		return part_stage_report_add;
	}

	public void setPart_stage_report_add(String part_stage_report_add) {
		this.part_stage_report_add = part_stage_report_add;
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

	public IQueryDAO getQueryDAO() {
		return queryDAO;
	}

	public void setQueryDAO(IQueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}
	
}
