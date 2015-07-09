package rt.action;

import java.io.IOException;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import rt.bean.DeviceAvailability;
import rt.dao.IDeviceAvailabilityImpl;
import rt.dao.IDeviceAvailability;

import com.opensymphony.xwork2.ActionSupport;

public class DeviceAction extends ActionSupport {

	/**
	 * 设备使用率查询
	 * @author 张强
	 */
	private static final long serialVersionUID = 10833601692361502L;
	
	public List<DeviceAvailability> da=null;
	public List<?> listR = null;
	public String start;
	public String end;
	private String params;
	public int cnt;
	IDeviceAvailability deviceDAO = null;
	
	public String deviceLastScanTime(){
		deviceDAO = new IDeviceAvailabilityImpl();
		da = deviceDAO.lastScanTime(start,end);
		return SUCCESS;
	}
	
	public String lastScanTime(){
		return SUCCESS;
	}
	
	public String DeviceAvailability(){
		deviceDAO = new IDeviceAvailabilityImpl();
		listR = deviceDAO.getDeviceAvailability(start, end, cnt);

		return SUCCESS;
	}
	
	public String availability(){
		return SUCCESS;
	}
	
	public void getTerminalName(){
		deviceDAO = new IDeviceAvailabilityImpl();
		String terName = deviceDAO.getTerminalName(Integer.parseInt(params));
		try {
			ServletActionContext.getResponse().getWriter().write(terName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<DeviceAvailability> getDa() {
		return da;
	}
	public void setDa(List<DeviceAvailability> da) {
		this.da = da;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public void setEnd(String end) {
		this.end = end;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}