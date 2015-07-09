package rt.pojo;

public class GSnDefect {

	  private int RECID;
	  private String SERIAL_NUMBER;
	  private String WORK_ORDER;
	  private int MODEL_ID;
	  private String REC_TIME;
	  private int DEFECT_ID;
	  private int TERMINAL_ID;
	  private int PROCESS_ID;
	  private int STAGE_ID;
	  private int PDLINE_ID;
	  private int TEST_EMP_ID;
	  private String RP_STATUS ;
	  private String LOCATION;
	  private int SHT_RPID;
	  private int RP_CNT;
	  private String RP_TIME;
	  private String MODEL_NAME;
	  private int RESPONSIBILITY_PROCESS_ID;
	  private String RESPONSIBILITY_UNIT;
	  private String WORK_FLAG;
	  
	  private SysDefect sysDefect;
	  private SysTerminal sysTerminal;
	  private SysEmp sysEmp;
	  private SysProcess sysProcess;
	public SysProcess getSysProcess() {
		return sysProcess;
	}
	public void setSysProcess(SysProcess sysProcess) {
		this.sysProcess = sysProcess;
	}
	public int getRECID() {
		return RECID;
	}
	public void setRECID(int recid) {
		RECID = recid;
	}
	public String getSERIAL_NUMBER() {
		return SERIAL_NUMBER;
	}
	public void setSERIAL_NUMBER(String serial_number) {
		SERIAL_NUMBER = serial_number;
	}
	public String getWORK_ORDER() {
		return WORK_ORDER;
	}
	public void setWORK_ORDER(String work_order) {
		WORK_ORDER = work_order;
	}
	public int getMODEL_ID() {
		return MODEL_ID;
	}
	public void setMODEL_ID(int model_id) {
		MODEL_ID = model_id;
	}
	public String getREC_TIME() {
		return REC_TIME;
	}
	public void setREC_TIME(String rec_time) {
		REC_TIME = rec_time;
	}
	public int getDEFECT_ID() {
		return DEFECT_ID;
	}
	public void setDEFECT_ID(int defect_id) {
		DEFECT_ID = defect_id;
	}
	public int getTERMINAL_ID() {
		return TERMINAL_ID;
	}
	public void setTERMINAL_ID(int terminal_id) {
		TERMINAL_ID = terminal_id;
	}
	public int getPROCESS_ID() {
		return PROCESS_ID;
	}
	public void setPROCESS_ID(int process_id) {
		PROCESS_ID = process_id;
	}
	public int getSTAGE_ID() {
		return STAGE_ID;
	}
	public void setSTAGE_ID(int stage_id) {
		STAGE_ID = stage_id;
	}
	public int getPDLINE_ID() {
		return PDLINE_ID;
	}
	public void setPDLINE_ID(int pdline_id) {
		PDLINE_ID = pdline_id;
	}
	public int getTEST_EMP_ID() {
		return TEST_EMP_ID;
	}
	public void setTEST_EMP_ID(int test_emp_id) {
		TEST_EMP_ID = test_emp_id;
	}
	public String getRP_STATUS() {
		return RP_STATUS;
	}
	public void setRP_STATUS(String rp_status) {
		RP_STATUS = rp_status;
	}
	public String getLOCATION() {
		return LOCATION;
	}
	public void setLOCATION(String location) {
		LOCATION = location;
	}
	public int getSHT_RPID() {
		return SHT_RPID;
	}
	public void setSHT_RPID(int sht_rpid) {
		SHT_RPID = sht_rpid;
	}
	public int getRP_CNT() {
		return RP_CNT;
	}
	public void setRP_CNT(int rp_cnt) {
		RP_CNT = rp_cnt;
	}
	public String getRP_TIME() {
		return RP_TIME;
	}
	public void setRP_TIME(String rp_time) {
		RP_TIME = rp_time;
	}
	public String getMODEL_NAME() {
		return MODEL_NAME;
	}
	public void setMODEL_NAME(String model_name) {
		MODEL_NAME = model_name;
	}
	public int getRESPONSIBILITY_PROCESS_ID() {
		return RESPONSIBILITY_PROCESS_ID;
	}
	public void setRESPONSIBILITY_PROCESS_ID(int responsibility_process_id) {
		RESPONSIBILITY_PROCESS_ID = responsibility_process_id;
	}
	public String getRESPONSIBILITY_UNIT() {
		return RESPONSIBILITY_UNIT;
	}
	public void setRESPONSIBILITY_UNIT(String responsibility_unit) {
		RESPONSIBILITY_UNIT = responsibility_unit;
	}
	public String getWORK_FLAG() {
		return WORK_FLAG;
	}
	public void setWORK_FLAG(String work_flag) {
		WORK_FLAG = work_flag;
	}
	public SysDefect getSysDefect() {
		return sysDefect;
	}
	public void setSysDefect(SysDefect sysDefect) {
		this.sysDefect = sysDefect;
	}
	public SysTerminal getSysTerminal() {
		return sysTerminal;
	}
	public void setSysTerminal(SysTerminal sysTerminal) {
		this.sysTerminal = sysTerminal;
	}
	public SysEmp getSysEmp() {
		return sysEmp;
	}
	public void setSysEmp(SysEmp sysEmp) {
		this.sysEmp = sysEmp;
	}
}
