/**
 * 
 */
package rt.dao;

import java.util.List;

import rt.bean.DeviceAvailability;
import rt.dao.IDeviceAvailabilityImpl.Reprot;

/**
 * @author Qiang1_Zhang
 *	设备利用率查询接口
 */
public interface IDeviceAvailability {
	public List<DeviceAvailability> lastScanTime(String start,String end);
	public List<Reprot> getDeviceAvailability(String start,String end,int cnt);
	public String getTerminalName(int terminaid);
}