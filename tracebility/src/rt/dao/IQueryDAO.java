package rt.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rt.pojo.GSnDefect;
import rt.pojo.Product;
import rt.pojo.SysProcess;
import rt.pojo.SysTerminal;

public interface IQueryDAO {
	
	/**
	 * 查询产品过站记录
	 * @param queryType 条件类别
	 * @param queryValue 查询值
	 * @return
	 */
	public List<Product> queryTravel(String queryValue);
	
	/**
	 * 查询产品当前状态
	 * @param queryType 条件类别
	 * @param queryValue 查询值
	 * @return
	 */
	public Map<String,Object> queryStatus(String queryValue);
	
	/**
	 * 维修资料
	 * @param queryType 条件类别
	 * @param queryValue 查询值
	 * @return
	 */
	public List<GSnDefect> fixData(String queryValue);
	
	/**
	 * 按箱号查询
	 * @param carton 箱号
	 * @param flag 标记：1 状态，2 过站记录
	 * @return
	 */
	public List<LinkedHashMap<String, Object>> queryByCarton(String carton,String flag, String terminalName);
	
	/**
	 * 按箱号查询
	 * @param cartonArray 多个箱号
	 * @return
	 */
	public List<LinkedHashMap<String, Object>> queryByCarton(String[] cartonArray);
	
	/**
	 * 获取不重复的箱号
	 * @return
	 */
	public List<Object> DuplicateTerminalNames();
	
	/**
	 * 按制程查询不良
	 * @param processID 制程ID
	 * @param terminalID 站点ID
	 * @param timeB 开始时间
	 * @param timeE 结束时间
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryDefectByProcess(String processID, String terminalID, String timeB, String timeE);
	
	/**
	 * 查机种信息,返回机种名称、料号、途程编号
	 * @param modelName 采集页面输入的机种关键词
	 * @return
	 */
	public List<Map<String,Object>> queryPart(String modelName);
	
	/**
	 * 查询途程所属制程清单
	 * @param routeId 途程ID
	 * @return
	 */
	public List<Map<String,Object>> queryProcessByRoute(String routeId);
	
	/**
	 * 查询制程下的站点
	 * @param processId 站点ID
	 * @return
	 */
	public List<Map<String,Object>> queryTerminalByProcess(String processId);
	
	/**
	 * 查指定序号的SN
	 * @param SN 序号
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryDefectBySN(String SN);
	
	/**
	 * 按料号查WIP
	 * @param partNO 料号
	 * @param timeB 开始时间
	 * @param timeE 结束时间
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryWIP(String partNO, String timeB, String timeE);
	
	/**
	 * 按制程/机种查WIP
	 * @param processID 制程ID
	 * @param terminalID 站点ID
	 * @param timeB 开始时间
	 * @param timeE 结束时间
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryWIP(String process, String partNO, String terminal, String timeB, String timeE);
	
	/**
	 * @param process
	 * @param partNO
	 * @param terminal
	 * @param timeB
	 * @param timeE
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryWIPDetails(String process,String partNO,String terminal, String timeB, String timeE);
	
	public SysProcess queryProcess(String processid);
	
	public SysTerminal queryTerminal(String terminalid);
	
	/**
	 * 按下拉列表查机种信息
	 * @return
	 */
	public List<Map<String,Object>> queryPart(); 

	/**
	 * 投入产出查询
	 * @param part_no
	 * @param timeB
	 * @param timeE
	 * @param processName
	 * @param terminalName
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryProcessOutput(String part_no,String timeB,String timeE,String processName,String terminalName);
	
	/**
	 * 查询投入产出明细
	 * @param part_no
	 * @param timeB
	 * @param timeE
	 * @param processName
	 * @param terminalName
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryProcessOutputDetail(String part_no,String wo_type,String timeB,String timeE,String processName,String terminalName);
	
	/**
	 * 镭雕品质检查
	 * @param timeB
	 * @param timeE
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryLaserQuality(String routeID,String part_no,String timeB,String timeE);
	
	/**
	 * 查询出货信息
	 * @param batchNum
	 * @param cartonNum
	 * @param part_no_erp
	 * @param part_no_mes
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryShipping(String queryType, String queryValue);
	
	/**
	 * APS量测数据查询
	 * @param queryType
	 * @param queryValue
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryAPS(String queryType, String queryValue);
	
	/**
	 * 查询CNC打点信息
	 * @param serialnumber
	 * @return
	 */
	public Map<String, Object> queryDot(String serialnumber);
	
	/**
	 * 自定义查表格数据--加载字段
	 * @param table
	 * @return
	 */
	public List<Object> MetaData(String table);
	
	/**
	 * 自定义查表格数据 -- 查询数据
	 * @param table 表名
	 * @param rowNum 数量
	 * @param columns 要显示的列名
	 * @return
	 */
	public List<Object> MetaData(String table, List<Object> columns, String value, int rowNum);
	
	/**
	 * 获取所有表信息
	 * @return
	 */
	public List<Map<String, Object>> showTables();
	
	/**
	 * 按制程查询不良代码
	 * @param process_id 制程ID
	 * @return
	 */
	public List<LinkedHashMap<String, Object>> queryDefectCodeByProcessID(String process_id);
	
	/**
	 * 根据二维码查询物料流转记录，适用于所有刷双方工卡的场景
	 * @param serial_number
	 * @return
	 */
	public List<LinkedHashMap<String, Object>> queryFlowBySerialNumber(String serial_number);
}