package rt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rt.dao.ICustomizeDAO;
import rt.dao.ICustomizeDAOImpl;
import rt.util.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

public class SpecialReturnAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1368376956793569936L;
	private static Logger log = Logger.getLogger(SpecialReturnAction.class);

	private long r_worder_number;
	private String r_type;//类别，区分是按单号还是序列号
	private String r_worder_reason;
	private long terminal_id;
	
	private String s_s_list;
	
	private String r_serial_number;
	private String r_serial_reason;
	private boolean r_serial_result;
	
	private List<Map<String,Object>> mapList;
	private Map<String,Object> mapSample;//样品单片信息
	private List<Map<String,Object>> SNList;
	
	private ICustomizeDAO customizeDAO ;
	
	public String initClearSN(){
		return SUCCESS;
	}
	
	public String qrySampleBySN(){
		customizeDAO = new ICustomizeDAOImpl();
		log.info("单片销帐处理");
		r_type = r_type.equals("serial_number") ? "serial_number" : "worder_number";
		mapList = customizeDAO.qrySampleBySN(r_serial_number.trim(),r_type);
		mapSample = mapList.size() != 0 ? mapList.get(0) : new HashMap<String,Object>();
		return SUCCESS;
	}
	
	/**
	 * 销单片
	 * @return
	 */
	public String clearSN(){
		customizeDAO = new ICustomizeDAOImpl();
		String uid = Struts2Utils.getSession().getAttribute("username").toString();
		uid = uid.substring(0, uid.indexOf(","));
//		System.out.println("r_type="+r_type);
		r_serial_result = customizeDAO.updateStatusBySN(r_serial_reason, uid, r_serial_number.trim(),terminal_id,r_type);
		return SUCCESS;
	}
	
	public String initClearWorder(){
		return SUCCESS;
	}

	public String qrySampleWorder(){
		System.out.println("无二维码");
		customizeDAO = new ICustomizeDAOImpl();
		mapSample = customizeDAO.qrySampleByWorderNum(r_worder_number);
		return SUCCESS;
	}
	
	/**
	 * 销单号
	 * @return
	 */
	public String clearWorder(){
		customizeDAO = new ICustomizeDAOImpl();
		String uid = Struts2Utils.getSession().getAttribute("username").toString();
		uid = uid.substring(0, uid.indexOf(","));
//		System.out.println("uid="+uid+"\tr_worder_reason="+r_worder_reason+"\tr_worder_number="+r_worder_number);
		r_serial_result = customizeDAO.updateStatusByWorderNum(r_worder_reason, uid, r_worder_number);
		return SUCCESS;
	}
	
	/***********************************公共属性区域***********************************/
	
	public Map<String, Object> getMapSample() {
		return mapSample;
	}

	public void setMapSample(Map<String, Object> mapSample) {
		this.mapSample = mapSample;
	}

	public long getR_worder_number() {
		return r_worder_number;
	}

	public void setR_worder_number(long r_worder_number) {
		this.r_worder_number = r_worder_number;
	}

	public String getR_serial_number() {
		return r_serial_number;
	}

	public void setR_serial_number(String r_serial_number) {
		this.r_serial_number = r_serial_number;
	}

	public String getS_s_list() {
		return s_s_list;
	}

	public void setS_s_list(String s_s_list) {
		this.s_s_list = s_s_list;
	}

	public String getR_serial_reason() {
		return r_serial_reason;
	}

	public void setR_serial_reason(String r_serial_reason) {
		this.r_serial_reason = r_serial_reason;
	}

	public long getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(long terminal_id) {
		this.terminal_id = terminal_id;
	}

	public boolean isR_serial_result() {
		return r_serial_result;
	}

	public void setR_serial_result(boolean r_serial_result) {
		this.r_serial_result = r_serial_result;
	}

	public String getR_type() {
		return r_type;
	}

	public void setR_type(String r_type) {
		this.r_type = r_type;
	}

	public List<Map<String, Object>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}

	public List<Map<String, Object>> getSNList() {
		return SNList;
	}

	public void setSNList(List<Map<String, Object>> sNList) {
		SNList = sNList;
	}

	public String getR_worder_reason() {
		return r_worder_reason;
	}

	public void setR_worder_reason(String r_worder_reason) {
		this.r_worder_reason = r_worder_reason;
	}
	
}