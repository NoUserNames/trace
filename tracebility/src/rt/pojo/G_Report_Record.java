/**
 * 
 */
package rt.pojo;

/**
 * 4小时报表数据记录表
 * @author 张强
 *
 */
public class G_Report_Record {

	private String report_record_id;
	private String stage_report_setting_id;
	private String time_zone;
	private String update_date;
	private int time_type;
	private int target_day;
	private int input_cnt;
	private int output_cnt;
	private int input_ng_cnt;
	private int process_ng_cnt;
	private int input_scrapt_cnt;
	private int process_scrapt_cnt;
	private String emp_no;

	public String getReport_record_id() {
		return report_record_id;
	}
	public void setReport_record_id(String report_record_id) {
		this.report_record_id = report_record_id;
	}
	public String getStage_report_setting_id() {
		return stage_report_setting_id;
	}
	public void setStage_report_setting_id(String stage_report_setting_id) {
		this.stage_report_setting_id = stage_report_setting_id;
	}
	public String getTime_zone() {
		return time_zone;
	}
	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}

	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
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

}
