package rt.action;

import java.util.ArrayList;
import java.util.List;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;

import com.opensymphony.xwork2.ActionSupport;

public class QueryMetaDataAction extends ActionSupport {

	/**
	 * 自定义表格查询
	 */
	private static final long serialVersionUID = -5982424038505290310L;

	private IQueryDAO query;
	private List<Object> list;
	private List<Object> listValue;
	
	private List<Object> columns;
	private String tableName;
	private int rowNum;
	private String value;
	
	public String initMetaData(){
		query = new IQueryDAOImpl();
		list = new ArrayList<Object>();
		return SUCCESS;
	}
	
	public String qryMetaData(){
		System.out.println("qryMetaData"+columns);
		query = new IQueryDAOImpl();System.out.println("columns.size()="+columns.size());
		if(columns.size() >0 ){
			listValue = query.MetaData(tableName, columns, value, rowNum);
			list = query.MetaData(tableName);
		}else{
			list = query.MetaData(tableName);
		}
		System.out.println("qryMetaData="+columns);
		return SUCCESS;
	}
	
//	public String qryData(){
//		query = new IQueryDAOImpl();
//		list = query.MetaData(tableName,(String[])columns.toArray(),rowNum);
//		System.out.println("qryMetaData="+columns);
//		return SUCCESS;
//	}
	
	/***************************公共属性声明区域*************************/

	public String getTableName() {
		return tableName;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public List<?> getColumns() {
		return columns;
	}

	public void setColumns(List<Object> columns) {
		this.columns = columns;
	}

	public List<Object> getListValue() {
		return listValue;
	}

	public void setListValue(List<Object> listValue) {
		this.listValue = listValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}