package rt.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;
import rt.util.ExportExcelUtil;
import rt.util.ReadProperties;
import rt.util.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

public class QueryShippingAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8929552115751449926L;

	private String queryType;
//	private String qt;
	private String queryValue;
	private int detailSize;
//	private String timeB;
//	private String timeE;
	private String shippingExp;
//	private static String timeB_S;
//	private static String timeE_S;
	
	private List<LinkedHashMap<String,Object>> listShipping;
	
	private IQueryDAO queryDAO;
	
	public String initQryShipping(){
		return SUCCESS;
	}
	
	public String qryShipping(){
		queryDAO = new IQueryDAOImpl();
		listShipping = queryDAO.queryShipping(queryType, queryValue);
		detailSize = listShipping.size();
		detailSize = detailSize >= 50 ? 49 : detailSize - 1;
		detailSize = detailSize != -1 ? detailSize : 0;
		ExportExcelUtil excel = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("箱号");
		listColumns.add("产品二维码");
		listColumns.add("出货标签");
		listColumns.add("更新时间");
		listColumns.add("批次号");
		listColumns.add("MES料号");
		listColumns.add("ERP料号");
		this.addFieldError("download.tip", ReadProperties.ReadTip("download.tip"));
		shippingExp = excel.exportExcelOrderly(ServletActionContext.getServletContext(), "shippingQry_", listShipping, listColumns);
		
		return SUCCESS;
	}
	
	public void shippingExp(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String files = Struts2Utils.getRequest().getParameter("shippingExp");
		excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,files);
	}

	/* 公共属性声明区域 */

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public List<LinkedHashMap<String, Object>> getListShipping() {
		return listShipping;
	}

	public void setListShipping(List<LinkedHashMap<String, Object>> listShipping) {
		this.listShipping = listShipping;
	}

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public String getShippingExp() {
		return shippingExp;
	}

	public void setShippingExp(String shippingExp) {
		this.shippingExp = shippingExp;
	}

//	public String getQt() {
//		return qt;
//	}
//
//	public void setQt(String qt) {
//		this.qt = qt;
//	}

	public int getDetailSize() {
		return detailSize;
	}

	public void setDetailSize(int detailSize) {
		this.detailSize = detailSize;
	}
	
	
}
