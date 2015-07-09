package rt.pojo;

public class GSupplierSN {

	private String SERIAL_NUMBER;
	private String SUPPLIER_SN;
	private String VALUE;
	private int TERMINAL_ID;
	private int UPDATE_USERID;
	private String UPDATE_TIME;
	private String USED_FLAG;

	public String getSERIAL_NUMBER() {
		return SERIAL_NUMBER;
	}

	public void setSERIAL_NUMBER(String serial_number) {
		SERIAL_NUMBER = serial_number;
	}

	public String getSUPPLIER_SN() {
		return SUPPLIER_SN;
	}

	public void setSUPPLIER_SN(String supplier_sn) {
		SUPPLIER_SN = supplier_sn;
	}

	public String getVALUE() {
		return VALUE;
	}

	public void setVALUE(String value) {
		VALUE = value;
	}

	public int getTERMINAL_ID() {
		return TERMINAL_ID;
	}

	public void setTERMINAL_ID(int terminal_id) {
		TERMINAL_ID = terminal_id;
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

	public String getUSED_FLAG() {
		return USED_FLAG;
	}

	public void setUSED_FLAG(String used_flag) {
		USED_FLAG = used_flag;
	}
}
