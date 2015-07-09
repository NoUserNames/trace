package rt.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;
import rt.util.ExportExcelUtil;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

public class QueryLaserQualityAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9151155981721381432L;

	private IQueryDAO queryDAO;
	
	private String timeB;
	private String timeE;
	private String partNO;
	private String laserQtyExp;
	private int detailSize;
	
	private List<LinkedHashMap<String,Object>> listMaps;
	private List<Map<String,Object>> listParts;
	private static List<Map<String,Object>> listParts_S;
	
	public String initQryLaserQuality(){
		queryDAO = new IQueryDAOImpl();
		listParts = queryDAO.queryPart();
		listParts_S = listParts;
		return SUCCESS;
	}
	
	public String qryLaserQuality(){
		queryDAO = new IQueryDAOImpl();
		String partNO_S = partNO.substring(partNO.lastIndexOf(",")+1);
		String routeID = partNO.substring(0,partNO.lastIndexOf(","));
		timeB = timeB.length() != 0 ? timeB : TUtil.format("yyyy-MM-dd")+" 00";
		timeE = timeE.length() != 0 ? timeE : TUtil.format("yyyy-MM-dd")+" 23";
		listMaps = queryDAO.queryLaserQuality(routeID,partNO_S,timeB, timeE);
		detailSize = listMaps.size();
		detailSize = detailSize >= 50 ? 49 : detailSize - 1;
		detailSize = detailSize != -1 ? detailSize : 0;
		listParts = listParts_S;
		
		ExportExcelUtil excel = new ExportExcelUtil();
		List<String> listColumns = new ArrayList<String>();
		listColumns.add("工单号");
		listColumns.add("产品二维码");
		listColumns.add("镭雕条码等级");
		listColumns.add("镭雕时间");
		laserQtyExp = excel.exportExcelOrderly(ServletActionContext.getServletContext(), "LaserQtyQuery_", listMaps, listColumns);
		return SUCCESS;
	}

	public void laserQtyExp(){
		ExportExcelUtil excelUtil = new ExportExcelUtil();
		String files = Struts2Utils.getRequest().getParameter("laserQtyExp");
		excelUtil.download(Struts2Utils.getServletContext().getRealPath("/")+"excel\\"+files,files);
	}
	
	public String getTimeB() {
		return timeB;
	}

	public void setTimeB(String timeB) {
		this.timeB = timeB;
	}

	public String getTimeE() {
		return timeE;
	}

	public void setTimeE(String timeE) {
		this.timeE = timeE;
	}

	public List<LinkedHashMap<String, Object>> getListMaps() {
		return listMaps;
	}

	public void setListMaps(List<LinkedHashMap<String, Object>> listMaps) {
		this.listMaps = listMaps;
	}

	public List<Map<String, Object>> getListParts() {
		return listParts;
	}

	public void setListParts(List<Map<String, Object>> listParts) {
		this.listParts = listParts;
	}

	public String getPartNO() {
		return partNO;
	}

	public void setPartNO(String partNO) {
		this.partNO = partNO;
	}

	public String getLaserQtyExp() {
		return laserQtyExp;
	}

	public void setLaserQtyExp(String laserQtyExp) {
		this.laserQtyExp = laserQtyExp;
	}

	public int getDetailSize() {
		return detailSize;
	}

	public void setDetailSize(int detailSize) {
		this.detailSize = detailSize;
	}
	
}