package rt.pojo;

public class SysProcess {

	  private int FACTORY_ID;
	  private int PROCESS_ID;
	  private String PROCESS_NAME;
	  private int STAGE_ID;
	  private String PROCESS_CODE;
	  private String PROCESS_DESC;
	  private int OPERATE_ID;
	  private int UPDATE_USERID;
	  private String UPDATE_TIME;
	  private String ENABLED;
	  private int UPPER_LIMIT;
	  private int LOWER_LIMIT;
	public int getFACTORY_ID() {
		return FACTORY_ID;
	}
	public void setFACTORY_ID(int factory_id) {
		FACTORY_ID = factory_id;
	}
	public int getPROCESS_ID() {
		return PROCESS_ID;
	}
	public void setPROCESS_ID(int process_id) {
		PROCESS_ID = process_id;
	}
	public String getPROCESS_NAME() {
		return PROCESS_NAME;
	}
	public void setPROCESS_NAME(String process_name) {
		PROCESS_NAME = process_name;
	}
	public int getSTAGE_ID() {
		return STAGE_ID;
	}
	public void setSTAGE_ID(int stage_id) {
		STAGE_ID = stage_id;
	}
	public String getPROCESS_CODE() {
		return PROCESS_CODE;
	}
	public void setPROCESS_CODE(String process_code) {
		PROCESS_CODE = process_code;
	}
	public String getPROCESS_DESC() {
		return PROCESS_DESC;
	}
	public void setPROCESS_DESC(String process_desc) {
		PROCESS_DESC = process_desc;
	}
	public int getOPERATE_ID() {
		return OPERATE_ID;
	}
	public void setOPERATE_ID(int operate_id) {
		OPERATE_ID = operate_id;
	}
	public int getUPDATE_USERID() {
		return UPDATE_USERID;
	}
	public void setUPDATE_USERID(int update_userid) {
		UPDATE_USERID = update_userid;
	}
	public String getUPDATE_TIME() {
		return UPDATE_TIME;
	}
	public void setUPDATE_TIME(String update_time) {
		UPDATE_TIME = update_time;
	}
	public String getENABLED() {
		return ENABLED;
	}
	public void setENABLED(String enabled) {
		ENABLED = enabled;
	}
	public int getUPPER_LIMIT() {
		return UPPER_LIMIT;
	}
	public void setUPPER_LIMIT(int upper_limit) {
		UPPER_LIMIT = upper_limit;
	}
	public int getLOWER_LIMIT() {
		return LOWER_LIMIT;
	}
	public void setLOWER_LIMIT(int lower_limit) {
		LOWER_LIMIT = lower_limit;
	}
}
