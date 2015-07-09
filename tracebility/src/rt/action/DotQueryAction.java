package rt.action;

import java.util.Map;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;

import com.opensymphony.xwork2.ActionSupport;

public class DotQueryAction extends ActionSupport {

	private IQueryDAO queryDAO;
	private String serial_number;
	private Map<String, Object> CNCMap;
	/**
	 * CNC打点信息查询
	 * @author 张强
	 */
	private static final long serialVersionUID = -6052374287078659386L;

	public String initDotQry(){
		return SUCCESS;
	}
	
	public String dotQry(){
		queryDAO = new IQueryDAOImpl();
		CNCMap = queryDAO.queryDot(serial_number);
		return SUCCESS;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public Map<String, Object> getCNCMap() {
		return CNCMap;
	}

	public void setCNCMap(Map<String, Object> cNCMap) {
		CNCMap = cNCMap;
	}
	
}