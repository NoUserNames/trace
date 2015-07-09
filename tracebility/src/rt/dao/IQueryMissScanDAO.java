package rt.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface IQueryMissScanDAO {

	/**
	 * 卡关漏扫查询
	 * @param partNO 料号
	 * @param process 制程
	 * @param timeB 开始时间
	 * @param timeE 结束时间
	 * @param travel_falg 是否选择查询过站历史记录
	 * @return List<LinkedHashMap<String,Object>>
	 */
	public List<LinkedHashMap<String,Object>> queryMissScan(String partNO,String process,String timeB,String timeE,boolean travel_falg);
}