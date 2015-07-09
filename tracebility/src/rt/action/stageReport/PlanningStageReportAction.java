package rt.action.stageReport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import rt.dao.impl.IPlanningStageReportDAOImpl;
import rt.dao.interfaces.IPlanningStageReportDAO;
import rt.pojo.G_Report_Record;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 阶段报表
 * @author 张强
 *
 */
public class PlanningStageReportAction extends ActionSupport {

	private static final long serialVersionUID = -2182954298262295383L;

	IPlanningStageReportDAO iStageReportDAO;
	
	private String report_record_id;
	private String stage_report_setting_id;
	private String time_zone;
	private Date update_date;
	private int time_type;
	private int target_day;
	private int input_cnt;
	private int output_cnt;
	private int input_ng_cnt;
	private int process_ng_cnt;
	private int input_scrapt_cnt;
	private int process_scrapt_cnt;
	private String emp_no;
	
	private int append_input;
	private String append_id;
	private String comment;
	private int original_cnt;
	
	private String field_name;
	
	List<Map<String, Object>> listReport;
	
	private List<Map<String,Object>> listParts;
	private Map<String,Object>[] listTmp;
	private List<Map<String,Object>[]> listMapArray;
	private String listTmpJSON;
	private List<Map<String,Object>> listReportSetting;
	private String listReportSettingJSON;
	private List<Map<String, Object>> listERPProcessID;
	private List<Map<String, Object>> listModelNames;
	
	private String setting_id;
	private String erp_process_id;
	private String model_name;
	public String datetime1;
	private String datetime2;
	
	List<Map<String,Object>> listTotalReport;
	List<List<Map<String,Object>>> listTotal;
	String emp_no_session = Struts2Utils.getSession().getAttribute("username").toString();
	private String yyyy;
	private String MM;
	private String dd;
	private String HH;
	private String mm;
	
	private List<Map<String,Object>> listId;
	private List<String> dates;
	
	private String str;
	
	private List<List<Map<String,Object>>> listO;
	
	public String initReport(){
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		yyyy = TUtil.format("yyyy");
		MM = TUtil.format("MM");
		dd = TUtil.format("dd");
		HH = TUtil.format("HH");
		mm = TUtil.format("mm");
		listReportSetting = iStageReportDAO.query4hReportSetting(stage_report_setting_id, emp_no_session.substring(0, emp_no_session.lastIndexOf(",")));
		listReportSettingJSON = JSONArray.fromObject(listReportSetting).toString();
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public void queryReport(){
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		listReport = iStageReportDAO.query4hReport(emp_no_session.substring(0, emp_no_session.lastIndexOf(",")),stage_report_setting_id);
		listTmp = new HashMap[6];
		if(listReport.size()!=0){
			for(int i=0;i<listReport.size();i++){
				listTmp[Integer.parseInt(listReport.get(i).get("TIME_INDEX").toString()) - 1] = listReport.get(i);
			}
		} else {

		}
		listTmpJSON = JSONArray.fromObject(listTmp).toString();
		try {
			Struts2Utils.getResponse().getWriter().write(listTmpJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveReport(){
		G_Report_Record g_report_record = new G_Report_Record();
		g_report_record.setReport_record_id(TUtil.format("yyyyMMddHHmmssssss"));
		g_report_record.setStage_report_setting_id(stage_report_setting_id);
		g_report_record.setTime_zone(time_zone);
		g_report_record.setTime_type(4);
		g_report_record.setTarget_day(1000);
		g_report_record.setInput_cnt(input_cnt);
		g_report_record.setOutput_cnt(output_cnt);
		g_report_record.setInput_ng_cnt(input_ng_cnt);
		g_report_record.setProcess_ng_cnt(process_ng_cnt);
		g_report_record.setInput_scrapt_cnt(input_scrapt_cnt);
		g_report_record.setProcess_scrapt_cnt(process_scrapt_cnt);
		g_report_record.setUpdate_date(TUtil.format("yyyy-MM-dd"));
		g_report_record.setEmp_no(emp_no_session.substring(0, emp_no_session.lastIndexOf(",")));
		double flag = Double.parseDouble(TUtil.format("yyyyMMdd"+"0830"));
		double today = Double.parseDouble(TUtil.format("yyyyMMddHHmm"));
		String timestamp = today > flag ? TUtil.GetDay(0) : TUtil.GetDay(-1);
		g_report_record.setUpdate_date(timestamp);
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		String result;
		if(iStageReportDAO.saveReport(g_report_record))
			result = g_report_record.getStage_report_setting_id();
		else
			result = "ng";
		setting_id = result;
		try {
			Struts2Utils.getResponse().getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//补差
	public void saveAppen(){
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		try {
			String result = (iStageReportDAO.saveAppend(report_record_id, append_id, append_input, original_cnt, emp_no_session.substring(0, emp_no_session.lastIndexOf(","))) == true ? "数量补差成功" : "数量补差失败，请重试。");
			Struts2Utils.getResponse().getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveComment(){
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		try {
			Struts2Utils.getResponse().getWriter().print(iStageReportDAO.saveComment(report_record_id, append_id, comment,emp_no_session));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void queryComment() throws IOException{
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		Struts2Utils.getResponse().getWriter().print(iStageReportDAO.queryComment(report_record_id, field_name));
	}

	public void queryAppend() throws IOException{
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		Struts2Utils.getResponse().getWriter().print(iStageReportDAO.queryAppend(report_record_id, field_name));
	}
	
	public void fillModel() throws IOException{
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		listModelNames = iStageReportDAO.queryModelName("model_name","sj.sys_stage_report_setting");
		listERPProcessID = iStageReportDAO.queryERPProcessID("erp_process_id", "sj.sys_stage_report_setting", "model_name", model_name);
		Struts2Utils.getResponse().getWriter().print(JSONArray.fromObject(listERPProcessID).toString());
	}
	
	public void fillErpProcess() throws IOException{
		fillModel();
	}
	
	public String initQueryReport() throws IOException{
		fillModel();
		return SUCCESS;
	}
	
	public String queryTotalReport() throws IOException{
		iStageReportDAO = new IPlanningStageReportDAOImpl();
		fillModel();
		listO = new ArrayList<List<Map<String,Object>>>();
		if(null == datetime1 && null == datetime2)
			return SUCCESS;
		erp_process_id = (erp_process_id != null) ? erp_process_id : "0";		

		List<Map<String,Object>> listP = iStageReportDAO.queryReportBySetId(model_name, erp_process_id, datetime1, datetime2);
//		List<Map<String,Object>> listP = iStageReportDAO.queryReportBySetId("AV9 TC", erp_process_id, "2015-06-17", "2015-06-25");
		List<Object> setids = new ArrayList<Object>();

		dates = new ArrayList<String>();
		for(Map<String,Object> map : listP){
			setids.add(map.get("STAGE_REPORT_SETTING_ID").toString());
			dates.add(map.get("UPDATE_DATE").toString());
		}
		TUtil util = new TUtil();
		setids = util.removeDuplicate(setids);
		dates = util.distinctList(dates);
		Collections.sort(dates);//对日期进行排序
//		System.out.println(dates);
		listId = new ArrayList<Map<String,Object>>();

		Map<String,Object> baseInfo = null;
		for(Object id : setids){
			baseInfo = new HashMap<String,Object>();
			baseInfo.put("setid", id.toString()) ;
			listId.add(baseInfo);
		}

		List<Map<String,Object>> tt = null;
		
		for(Object id : setids){
//			System.out.println(id);
			tt = new ArrayList<Map<String,Object>>();
			for(int i =0;i<dates.size() * 7;i++){
				tt.add(new HashMap<String,Object>());
			}
			for(Map<String,Object> map : listP){
				for(int i=0;i<dates.size();i++){//循环日期，创建列索引 6.24新增
					int arrayIndex = 0;
					if(id.equals(map.get("STAGE_REPORT_SETTING_ID").toString())
							&& dates.get(i).equals(map.get("UPDATE_DATE").toString())){
						arrayIndex = Integer.parseInt(map.get("TIME_INDEX").toString()) + 7 * i - 1;
//						System.out.println("arrayIndex="+arrayIndex+"\ti="+i+"\t"+Integer.parseInt(map.get("TIME_INDEX").toString())%6);
						tt.set(arrayIndex, map);
					}
				}
			}
			listO.add(tt);
		}
		return SUCCESS;
	}
	
	/**************************************************************/
	
	public String getStage_report_setting_id() {
		return stage_report_setting_id;
	}

	public List<List<Map<String, Object>>> getListO() {
		return listO;
	}

	public void setListO(List<List<Map<String, Object>>> listO) {
		this.listO = listO;
	}

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	public List<Map<String, Object>> getListId() {
		return listId;
	}

	public void setListId(List<Map<String, Object>> listId) {
		this.listId = listId;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public List<List<Map<String, Object>>> getListTotal() {
		return listTotal;
	}

	public void setListTotal(List<List<Map<String, Object>>> listTotal) {
		this.listTotal = listTotal;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getMM() {
		return MM;
	}

	public void setMM(String mM) {
		MM = mM;
	}

	public String getDd() {
		return dd;
	}

	public void setDd(String dd) {
		this.dd = dd;
	}

	public String getHH() {
		return HH;
	}

	public void setHH(String hH) {
		HH = hH;
	}

	public String getYyyy() {
		return yyyy;
	}

	public void setYyyy(String yyyy) {
		this.yyyy = yyyy;
	}

	public String getErp_process_id() {
		return erp_process_id;
	}

	public void setErp_process_id(String erp_process_id) {
		this.erp_process_id = erp_process_id;
	}

	public String getDatetime1() {
		return datetime1;
	}

	public void setDatetime1(String datetime1) {
		this.datetime1 = datetime1;
	}

	public String getDatetime2() {
		return datetime2;
	}

	public void setDatetime2(String datetime2) {
		this.datetime2 = datetime2;
	}

	public List<Map<String, Object>[]> getListMapArray() {
		return listMapArray;
	}

	public void setListMapArray(List<Map<String, Object>[]> listMapArray) {
		this.listMapArray = listMapArray;
	}

	public List<Map<String, Object>> getListTotalReport() {
		return listTotalReport;
	}

	public void setListTotalReport(List<Map<String, Object>> listTotalReport) {
		this.listTotalReport = listTotalReport;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	public List<Map<String, Object>> getListModelNames() {
		return listModelNames;
	}

	public void setListModelNames(List<Map<String, Object>> listModelNames) {
		this.listModelNames = listModelNames;
	}

	public List<Map<String, Object>> getListERPProcessID() {
		return listERPProcessID;
	}

	public void setListERPProcessID(List<Map<String, Object>> listERPProcessID) {
		this.listERPProcessID = listERPProcessID;
	}

	public String getSetting_id() {
		return setting_id;
	}

	public void setSetting_id(String setting_id) {
		this.setting_id = setting_id;
	}

	public int getOriginal_cnt() {
		return original_cnt;
	}

	public void setOriginal_cnt(int original_cnt) {
		this.original_cnt = original_cnt;
	}

	public String getListTmpJSON() {
		return listTmpJSON;
	}

	public void setListTmpJSON(String listTmpJSON) {
		this.listTmpJSON = listTmpJSON;
	}

	public String getEmp_no_session() {
		return emp_no_session;
	}

	public void setEmp_no_session(String emp_no_session) {
		this.emp_no_session = emp_no_session;
	}

	public Map<String, Object>[] getListTmp() {
		return listTmp;
	}

	public void setListTmp(Map<String, Object>[] listTmp) {
		this.listTmp = listTmp;
	}

	public String getListReportSettingJSON() {
		return listReportSettingJSON;
	}

	public void setListReportSettingJSON(String listReportSettingJSON) {
		this.listReportSettingJSON = listReportSettingJSON;
	}

	public List<Map<String, Object>> getListReportSetting() {
		return listReportSetting;
	}

	public void setListReportSetting(List<Map<String, Object>> listReportSetting) {
		this.listReportSetting = listReportSetting;
	}

	public List<Map<String, Object>> getListParts() {
		return listParts;
	}

	public void setListParts(List<Map<String, Object>> listParts) {
		this.listParts = listParts;
	}

	public void setStage_report_setting_id(String stage_report_setting_id) {
		this.stage_report_setting_id = stage_report_setting_id;
	}

	public String getReport_record_id() {
		return report_record_id;
	}

	public void setReport_record_id(String report_record_id) {
		this.report_record_id = report_record_id;
	}

	public String getTime_zone() {
		return time_zone;
	}

	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public int getTime_type() {
		return time_type;
	}

	public void setTime_type(int time_type) {
		this.time_type = time_type;
	}

	public int getTarget_day() {
		return target_day;
	}

	public void setTarget_day(int target_day) {
		this.target_day = target_day;
	}

	public int getInput_cnt() {
		return input_cnt;
	}

	public void setInput_cnt(int input_cnt) {
		this.input_cnt = input_cnt;
	}

	public int getOutput_cnt() {
		return output_cnt;
	}

	public void setOutput_cnt(int output_cnt) {
		this.output_cnt = output_cnt;
	}

	public int getInput_ng_cnt() {
		return input_ng_cnt;
	}

	public void setInput_ng_cnt(int input_ng_cnt) {
		this.input_ng_cnt = input_ng_cnt;
	}

	public int getProcess_ng_cnt() {
		return process_ng_cnt;
	}

	public void setProcess_ng_cnt(int process_ng_cnt) {
		this.process_ng_cnt = process_ng_cnt;
	}

	public int getInput_scrapt_cnt() {
		return input_scrapt_cnt;
	}

	public void setInput_scrapt_cnt(int input_scrapt_cnt) {
		this.input_scrapt_cnt = input_scrapt_cnt;
	}

	public int getProcess_scrapt_cnt() {
		return process_scrapt_cnt;
	}

	public void setProcess_scrapt_cnt(int process_scrapt_cnt) {
		this.process_scrapt_cnt = process_scrapt_cnt;
	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	public int getAppend_input() {
		return append_input;
	}

	public void setAppend_input(int append_input) {
		this.append_input = append_input;
	}

	public String getAppend_id() {
		return append_id;
	}

	public void setAppend_id(String append_id) {
		this.append_id = append_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Map<String, Object>> getListReport() {
		return listReport;
	}

	public void setListReport(List<Map<String, Object>> listReport) {
		this.listReport = listReport;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	
}