package rt.pojo;

public class Product {

	private String WORK_ORDER;
	private String SERIAL_NUMBER;
	private int MODEL_ID;
	private String VERSION;
	private int ROUTE_ID;
	private int PDLINE_ID;
	private int STAGE_ID;
	private int PROCESS_ID;
	private int TERMINAL_ID;
	private int NEXT_PROCESS;
	private String CURRENT_STATUS;
	private String WORK_FLAG;
	private String IN_PROCESS_TIME;
	private String OUT_PROCESS_TIME;
	private String IN_PDLINE_TIME;
	private String OUT_PDLINE_TIME;
	private int ENC_CNT;
	private String PALLET_NO;
	private String CARTON_NO;
	private String CONTAINER;
	private String QC_NO;
	private String QC_RESULT;
	private int CUSTOMER_ID;
	private String WARRANTY;
	private String REWORK_NO;
	private int EMP_ID;
	private int SHIPPING_ID;
	private String CUSTOMER_SN;
	private String RC_NO;
	private String INNERBOX_NO;
	private int WIP_PROCESS;
	private String EC;
	private String RP_STATION;
	private String RECOUNT_TYPE;
	private int WEIGHT;
	private int LAST_PROCESS_ID;
	private int LAST_TERMINAL_ID;
	private int REWORK_CNT;
	private String MODEL_NAME;
	private String ENABLED;
	private int RECID;
	
	private SysTerminal sysTerminal;
	private SysProcess sysProcess;
	private SysPdline sysPdline;
	private SysEmp sysEmp;
	private SysGWoBase sysGWoBase;
	private SysPart sysPart;
	private SysRoute sysRoute;
	private GSupplierSN gSupplierSN;
	
	public SysPart getSysPart() {
		return sysPart;
	}

	public void setSysPart(SysPart sysPart) {
		this.sysPart = sysPart;
	}

	public SysEmp getSysEmp() {
		return sysEmp;
	}

	public void setSysEmp(SysEmp sysEmp) {
		this.sysEmp = sysEmp;
	}

	public SysPdline getSysPdline() {
		return sysPdline;
	}

	public void setSysPdline(SysPdline sysPdline) {
		this.sysPdline = sysPdline;
	}

	public SysTerminal getSysTerminal() {
		return sysTerminal;
	}

	public void setSysTerminal(SysTerminal sysTerminal) {
		this.sysTerminal = sysTerminal;
	}

	public SysProcess getSysProcess() {
		return sysProcess;
	}

	public void setSysProcess(SysProcess sysProcess) {
		this.sysProcess = sysProcess;
	}

	public String getWORK_ORDER() {
		return WORK_ORDER;
	}

	public void setWORK_ORDER(String work_order) {
		WORK_ORDER = work_order;
	}

	public String getSERIAL_NUMBER() {
		return SERIAL_NUMBER;
	}

	public void setSERIAL_NUMBER(String serial_number) {
		SERIAL_NUMBER = serial_number;
	}

	public int getMODEL_ID() {
		return MODEL_ID;
	}

	public void setMODEL_ID(int model_id) {
		MODEL_ID = model_id;
	}

	public String getVERSION() {
		return VERSION;
	}

	public void setVERSION(String version) {
		VERSION = version;
	}

	public int getROUTE_ID() {
		return ROUTE_ID;
	}

	public void setROUTE_ID(int route_id) {
		ROUTE_ID = route_id;
	}

	public int getPDLINE_ID() {
		return PDLINE_ID;
	}

	public void setPDLINE_ID(int pdline_id) {
		PDLINE_ID = pdline_id;
	}

	public int getSTAGE_ID() {
		return STAGE_ID;
	}

	public void setSTAGE_ID(int stage_id) {
		STAGE_ID = stage_id;
	}

	public int getPROCESS_ID() {
		return PROCESS_ID;
	}

	public void setPROCESS_ID(int process_id) {
		PROCESS_ID = process_id;
	}

	public int getTERMINAL_ID() {
		return TERMINAL_ID;
	}

	public void setTERMINAL_ID(int terminal_id) {
		TERMINAL_ID = terminal_id;
	}

	public int getNEXT_PROCESS() {
		return NEXT_PROCESS;
	}

	public void setNEXT_PROCESS(int next_process) {
		NEXT_PROCESS = next_process;
	}

	public String getCURRENT_STATUS() {
		return CURRENT_STATUS;
	}

	public void setCURRENT_STATUS(String current_status) {
		CURRENT_STATUS = current_status;
	}

	public String getWORK_FLAG() {
		return WORK_FLAG;
	}

	public void setWORK_FLAG(String work_flag) {
		WORK_FLAG = work_flag;
	}

	public String getIN_PROCESS_TIME() {
		return IN_PROCESS_TIME;
	}

	public void setIN_PROCESS_TIME(String in_process_time) {
		IN_PROCESS_TIME = in_process_time;
	}

	public String getOUT_PROCESS_TIME() {
		return OUT_PROCESS_TIME;
	}

	public void setOUT_PROCESS_TIME(String out_process_time) {
		OUT_PROCESS_TIME = out_process_time;
	}

	public String getIN_PDLINE_TIME() {
		return IN_PDLINE_TIME;
	}

	public void setIN_PDLINE_TIME(String in_pdline_time) {
		IN_PDLINE_TIME = in_pdline_time;
	}

	public String getOUT_PDLINE_TIME() {
		return OUT_PDLINE_TIME;
	}

	public void setOUT_PDLINE_TIME(String out_pdline_time) {
		OUT_PDLINE_TIME = out_pdline_time;
	}

	public int getENC_CNT() {
		return ENC_CNT;
	}

	public void setENC_CNT(int enc_cnt) {
		ENC_CNT = enc_cnt;
	}

	public String getPALLET_NO() {
		return PALLET_NO;
	}

	public void setPALLET_NO(String pallet_no) {
		PALLET_NO = pallet_no;
	}

	public String getCARTON_NO() {
		return CARTON_NO;
	}

	public void setCARTON_NO(String carton_no) {
		CARTON_NO = carton_no;
	}

	public String getCONTAINER() {
		return CONTAINER;
	}

	public void setCONTAINER(String container) {
		CONTAINER = container;
	}

	public String getQC_NO() {
		return QC_NO;
	}

	public void setQC_NO(String qc_no) {
		QC_NO = qc_no;
	}

	public String getQC_RESULT() {
		return QC_RESULT;
	}

	public void setQC_RESULT(String qc_result) {
		QC_RESULT = qc_result;
	}

	public int getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(int customer_id) {
		CUSTOMER_ID = customer_id;
	}

	public String getWARRANTY() {
		return WARRANTY;
	}

	public void setWARRANTY(String warranty) {
		WARRANTY = warranty;
	}

	public String getREWORK_NO() {
		return REWORK_NO;
	}

	public void setREWORK_NO(String rework_no) {
		REWORK_NO = rework_no;
	}

	public int getEMP_ID() {
		return EMP_ID;
	}

	public void setEMP_ID(int emp_id) {
		EMP_ID = emp_id;
	}

	public int getSHIPPING_ID() {
		return SHIPPING_ID;
	}

	public void setSHIPPING_ID(int shipping_id) {
		SHIPPING_ID = shipping_id;
	}

	public String getCUSTOMER_SN() {
		return CUSTOMER_SN;
	}

	public void setCUSTOMER_SN(String customer_sn) {
		CUSTOMER_SN = customer_sn;
	}

	public String getRC_NO() {
		return RC_NO;
	}

	public void setRC_NO(String rc_no) {
		RC_NO = rc_no;
	}

	public String getINNERBOX_NO() {
		return INNERBOX_NO;
	}

	public void setINNERBOX_NO(String innerbox_no) {
		INNERBOX_NO = innerbox_no;
	}

	public int getWIP_PROCESS() {
		return WIP_PROCESS;
	}

	public void setWIP_PROCESS(int wip_process) {
		WIP_PROCESS = wip_process;
	}

	public String getEC() {
		return EC;
	}

	public void setEC(String ec) {
		EC = ec;
	}

	public String getRP_STATION() {
		return RP_STATION;
	}

	public void setRP_STATION(String rp_station) {
		RP_STATION = rp_station;
	}

	public String getRECOUNT_TYPE() {
		return RECOUNT_TYPE;
	}

	public void setRECOUNT_TYPE(String recount_type) {
		RECOUNT_TYPE = recount_type;
	}

	public int getWEIGHT() {
		return WEIGHT;
	}

	public void setWEIGHT(int weight) {
		WEIGHT = weight;
	}

	public int getLAST_PROCESS_ID() {
		return LAST_PROCESS_ID;
	}

	public void setLAST_PROCESS_ID(int last_process_id) {
		LAST_PROCESS_ID = last_process_id;
	}

	public int getLAST_TERMINAL_ID() {
		return LAST_TERMINAL_ID;
	}

	public void setLAST_TERMINAL_ID(int last_terminal_id) {
		LAST_TERMINAL_ID = last_terminal_id;
	}

	public int getREWORK_CNT() {
		return REWORK_CNT;
	}

	public void setREWORK_CNT(int rework_cnt) {
		REWORK_CNT = rework_cnt;
	}

	public String getMODEL_NAME() {
		return MODEL_NAME;
	}

	public void setMODEL_NAME(String model_name) {
		MODEL_NAME = model_name;
	}

	public String getENABLED() {
		return ENABLED;
	}

	public void setENABLED(String enabled) {
		ENABLED = enabled;
	}

	public int getRECID() {
		return RECID;
	}

	public void setRECID(int recid) {
		RECID = recid;
	}

	public SysGWoBase getSysGWoBase() {
		return sysGWoBase;
	}

	public void setSysGWoBase(SysGWoBase sysGWoBase) {
		this.sysGWoBase = sysGWoBase;
	}

	public SysRoute getSysRoute() {
		return sysRoute;
	}

	public void setSysRoute(SysRoute sysRoute) {
		this.sysRoute = sysRoute;
	}

	public GSupplierSN getGSupplierSN() {
		return gSupplierSN;
	}

	public void setGSupplierSN(GSupplierSN supplierSN) {
		gSupplierSN = supplierSN;
	}
}