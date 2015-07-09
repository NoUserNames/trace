package rt.pojo;

public class SysPdline {

	  private int FACTORY_ID;
	  private int PDLINE_ID;
	  private String PDLINE_NAME;
	  private String PDLINE_DESC;
	  private int UPDATE_USERID;
	  private String UPDATE_TIME;
	  private String ENABLED;
	public int getFACTORY_ID() {
		return FACTORY_ID;
	}
	public void setFACTORY_ID(int factory_id) {
		FACTORY_ID = factory_id;
	}
	public int getPDLINE_ID() {
		return PDLINE_ID;
	}
	public void setPDLINE_ID(int pdline_id) {
		PDLINE_ID = pdline_id;
	}
	public String getPDLINE_NAME() {
		return PDLINE_NAME;
	}
	public void setPDLINE_NAME(String pdline_name) {
		PDLINE_NAME = pdline_name;
	}
	public String getPDLINE_DESC() {
		return PDLINE_DESC;
	}
	public void setPDLINE_DESC(String pdline_desc) {
		PDLINE_DESC = pdline_desc;
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
}
