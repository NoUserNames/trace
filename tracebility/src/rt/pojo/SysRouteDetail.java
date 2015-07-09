package rt.pojo;

public class SysRouteDetail {

	  private int ROUTE_ID;
	  private int PROCESS_ID;
	  private int NEXT_PROCESS_ID;
	  private int RESULT;
	  private int SEQ;
	  private String PD_CODE;
	  private String NECESSARY;
	  private int STEP;
	  private int UPDATE_USERID;
	  private String UPDATE_TIME;
	  private String ENABLED;
	  private int WIP_TIME;
	public int getROUTE_ID() {
		return ROUTE_ID;
	}
	public void setROUTE_ID(int route_id) {
		ROUTE_ID = route_id;
	}
	public int getPROCESS_ID() {
		return PROCESS_ID;
	}
	public void setPROCESS_ID(int process_id) {
		PROCESS_ID = process_id;
	}
	public int getNEXT_PROCESS_ID() {
		return NEXT_PROCESS_ID;
	}
	public void setNEXT_PROCESS_ID(int next_process_id) {
		NEXT_PROCESS_ID = next_process_id;
	}
	public int getRESULT() {
		return RESULT;
	}
	public void setRESULT(int result) {
		RESULT = result;
	}
	public int getSEQ() {
		return SEQ;
	}
	public void setSEQ(int seq) {
		SEQ = seq;
	}
	public String getPD_CODE() {
		return PD_CODE;
	}
	public void setPD_CODE(String pd_code) {
		PD_CODE = pd_code;
	}
	public String getNECESSARY() {
		return NECESSARY;
	}
	public void setNECESSARY(String necessary) {
		NECESSARY = necessary;
	}
	public int getSTEP() {
		return STEP;
	}
	public void setSTEP(int step) {
		STEP = step;
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
	public int getWIP_TIME() {
		return WIP_TIME;
	}
	public void setWIP_TIME(int wip_time) {
		WIP_TIME = wip_time;
	}
}
