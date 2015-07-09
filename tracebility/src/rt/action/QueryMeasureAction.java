package rt.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import rt.dao.impl.IQueryMeasureDAOImpl;
import rt.dao.interfaces.IQueryMeasureDAO;
import rt.util.ExportExcelUtil;
import rt.util.Struts2Utils;
/**
 * Tiger量测数据查询
 * @author 张强
 *
 */
public class QueryMeasureAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7792264320724852135L;

	private String table;
	private String serial_number;
	private String wipExp;
	private IQueryMeasureDAO iQueryMeasureDAO;
	
	private List<String> columnList ;
	private List<List<Object>> dataList;
	
	public String initMeasureAction(){
		return SUCCESS;
	}

	public String qryMeasure(){
		iQueryMeasureDAO = new IQueryMeasureDAOImpl();
		columnList = iQueryMeasureDAO.ColumnData(table);//加载列标题
		dataList = iQueryMeasureDAO.queryMeasure(table,serial_number,columnList.size());
		List<LinkedHashMap<String,Object>> link = new ArrayList<LinkedHashMap<String,Object>>();
		for(int i=0;i<dataList.size();i++){
			LinkedHashMap<String,Object> linkMap = new LinkedHashMap<String,Object>();
			for(int j=0;j<dataList.get(i).size();j++){
				linkMap.put(i+""+j, dataList.get(i).get(j));
			}
			System.out.println();
			link.add(linkMap);
		}
		ExportExcelUtil excel = new ExportExcelUtil();
		if(dataList.size() > 0)
			wipExp = excel.exportExcelOrderly(ServletActionContext.getServletContext(), "Measure_"+serial_number, link, columnList);
		
		return SUCCESS;
	}
	
	public void downloadMeasure(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String files = Struts2Utils.getRequest().getParameter("wipExp");
		excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,files);
	}
	
	/**********************公共属性****************************/
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public List<List<Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<List<Object>> dataList) {
		this.dataList = dataList;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getWipExp() {
		return wipExp;
	}

	public void setWipExp(String wipExp) {
		this.wipExp = wipExp;
	}

}