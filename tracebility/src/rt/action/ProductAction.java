package rt.action;

import java.util.List;
import java.util.Map;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;
import rt.pojo.GSnDefect;
import rt.pojo.Product;

import com.opensymphony.xwork2.ActionSupport;

public class ProductAction extends ActionSupport {

	/**
	 * 过站记录查询
	 * @author 张强
	 */
	private static final long serialVersionUID = -693553461625921016L;
	
	private String queryValue;
	private List<Product> listP ;
	private List<GSnDefect> listFixData;
//	private Product product;
	private Map<String,Object> productStatus;//产品当前状态
	IQueryDAO query;
	
	//过站记录
	public String queryQuery(){
		query = new IQueryDAOImpl();
		listP = query.queryTravel(queryValue.trim());
		listFixData = query.fixData(queryValue.trim());
		productStatus = query.queryStatus(queryValue.trim());
		return SUCCESS;
	}
	
	public String queryQueryRedirect(){
		return SUCCESS;
	}
	
	/**********************公共属性构造区域******************/

	public List<Product> getListP() {
		return listP;
	}

	public void setListP(List<Product> listP) {
		this.listP = listP;
	}

	public List<GSnDefect> getListFixData() {
		return listFixData;
	}

	public void setListFixData(List<GSnDefect> listFixData) {
		this.listFixData = listFixData;
	}

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public Map<String, Object> getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Map<String, Object> productStatus) {
		this.productStatus = productStatus;
	}
	
}