/**
 * 现场阶段报表
 */
package rt.dao.interfaces;

import java.util.List;
import java.util.Map;

import rt.pojo.G_Report_Record;
import rt.pojo.SYS_STAGE_REPORT_SETTING;

/**
 * @author 张强
 *
 */
public interface IPlanningStageReportDAO {
	
	/**
	 * 查当天4小时报表数据
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public List<Map<String,Object>> query4hReport(String emp_no,String stage_report_setting_id);
	
	/**
	 * 查询报表对应配置信息
	 * @param emp_no
	 * @return
	 */
	public List<Map<String,Object>> query4hReportSetting(String erp_process_id, String emp_no);
	
	/**
	 * 查询工号对应部门名称
	 * @param emp_no
	 * @return
	 */
	String getDeptName(String emp_no);
	
	/**
	 * 记录4小时报表
	 * @param g_report_record
	 * @return
	 */
	boolean saveReport(G_Report_Record g_report_record);
	
	/**
	 * 数量补差
	 * @param report_record_id
	 * @param field_id
	 * @param field_value
	 * @param original_cnt
	 * @param emp_no
	 * @return
	 */
	boolean saveAppend(String report_record_id, String field_id, int field_value, int original_cnt, String emp_no);
	
	/**
	 * 批注
	 * @param field_id
	 * @param comment
	 * @return
	 */
	int saveComment(String report_record_id, String field_id, String comment, String emp_no);
	
	/**
	 * 查询批注
	 * @param alt
	 * @param field_name
	 */
	String queryComment(String alt,String field_name);
	
	/**
	 * 查询补差
	 * @param alt
	 * @param field_name
	 */
	String queryAppend(String alt,String field_name);
	
	/**
	 * 4小时报表工序设定
	 * @param SYS_STAGE_REPORT_SETTING
	 * @return
	 */
	boolean stageReportSettingAdd(SYS_STAGE_REPORT_SETTING SYS_STAGE_REPORT_SETTING);
	
	/**
	 * 企划4小时报表工序修改前的查询
	 * @return
	 */
	List<Map<String,Object>> queryReprotSetting(String settingid);
	
	boolean updatePlanSetting(SYS_STAGE_REPORT_SETTING SYS_STAGE_REPORT_SETTING);
	
	/**
	 * 删除企划4小时报表设定档
	 * @param stage_report_setting_id
	 * @return
	 */
	boolean deletePlanSetting(String stage_report_setting_id);
	
	/**
	 * 工序-人员记录 授权操作
	 * @param stage_report_setting_id
	 * @param emp_no
	 * @return
	 */
	boolean reprot_setting_emp(String stage_report_setting_id, String emp_no);

	String queryReportSettingEmp(String stage_report_setting_id);
	
	List<Map<String,Object>> queryModelName(String column, String table);
	
	List<Map<String,Object>> queryERPProcessID(String column, String table, String c, String v);
	
	/**
	 * 汇总查询,按ERP料号汇总。日期段内相同ERP料号会汇总显示
	 * @return
	 */
	List<Map<String,Object>> queryReport(String model_name, String datetime1, String datetime2);
	
	/**
	 * 汇总查询，按日期汇总，同一ERP制程会按明细显示，不汇总。
	 * @param setid
	 * @param datetime1
	 * @param datetime2
	 * @return
	 */
	List<Map<String,Object>> queryReportBySetId(String modeName, String erpProcessId, String datetime1, String datetime2);
	
	/**
	 * 
	 * @param modeName
	 * @param erpProcessId
	 * @param datetime1
	 * @param datetime2
	 * @return
	 */
//	List<String> queryDistinctSetid(String modeName, String erpProcessId, String datetime1, String datetime2);
}
