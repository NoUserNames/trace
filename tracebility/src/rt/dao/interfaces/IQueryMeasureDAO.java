package rt.dao.interfaces;

import java.util.List;

/**
 * @author 张强
 * 量测数据查询处理处理类
 */
public interface IQueryMeasureDAO {

	/**
	 * 获取列名
	 * @param table 表名
	 * @return
	 */
	public List<String> ColumnData(String table);
	
	/**
	 * 查询量测数据
	 * @param table 量测表名
	 * @return
	 */
	public List<List<Object>> queryMeasure(String table, String serial_number,int columnSize);
}
