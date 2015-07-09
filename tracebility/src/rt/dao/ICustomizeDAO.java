package rt.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ICustomizeDAO {

	/**
	 * 维护WIP状态
	 * @param serials
	 * @return
	 */
	public boolean MaintainWIP(ArrayList<ArrayList<String>> serials,String handle);
	
	/**
	 * @return 查看产品状态
	 * @param serial 二维码
	 */
	public String qryStatus(String serial);
	
	/**
	 * 还回前返回单片出借信息
	 * @param serial_number 产品序列号
	 * @return
	 */
	public List<Map<String,Object>> qrySampleBySN(String serial_number,String r_type);
	
	/**
	 * 还回前返回单号出借信息
	 * @param worder_number 产品序列号
	 * @return
	 */
	public Map<String,Object> qrySampleByWorderNum(long worder_number);
	
	/**
	 * 按序列号销帐(有二维码)
	 * @param reason 销帐原因
	 * @param imp_emp 销帐人
	 * @param serial_number 产品序列号
	 * @return true || false
	 */
	public boolean updateStatusBySN(String reason,String emp,String serial_number,long terminal_id,String r_type);
	
	/**
	 * 按单号销帐
	 * @param reason 销帐原因
	 * @param imp_emp 销帐人
	 * @param worderNumber 出借单号
	 * @return
	 */
	public boolean updateStatusByWorderNum(String reason,String imp_emp,long worderNumber);
}
