package rt.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;
import rt.util.ExportExcelUtil;
import rt.util.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

public class QueryAPSAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6473911086974689296L;

	private String queryType;
	private String queryValue;
	private String APSExp;
	
	private List<LinkedHashMap<String,Object>> listAPS;
	
	private IQueryDAO queryDAO;
	
	public String initQryAPS(){
		return SUCCESS;
	}
	
	public String qryAPS(){
		queryDAO = new IQueryDAOImpl();
		listAPS = queryDAO.queryAPS(queryType, queryValue);
		ExportExcelUtil excel = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("产品序列号");
		listColumns.add("颜色");
		listColumns.add("BIN");
		listColumns.add("镭雕内腔编码");
		listColumns.add("FATP条码");
		listColumns.add("原材供应商");
		listColumns.add("ECELL号");
//		this.addFieldError("download.tip", ReadProperties.ReadTip("download.tip"));
		APSExp = excel.exportExcelOrderly(ServletActionContext.getServletContext(), "aspQry_", listAPS, listColumns);
		return SUCCESS;
	}

	public void apsExp(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String files = Struts2Utils.getRequest().getParameter("apsExp");
		excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,files);
	}
	
	/************公共属性声明区域***************/
	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public List<LinkedHashMap<String, Object>> getListAPS() {
		return listAPS;
	}

	public void setListAPS(List<LinkedHashMap<String, Object>> listAPS) {
		this.listAPS = listAPS;
	}

	public String getAPSExp() {
		return APSExp;
	}

	public void setAPSExp(String aPSExp) {
		APSExp = aPSExp;
	}
	
	
}