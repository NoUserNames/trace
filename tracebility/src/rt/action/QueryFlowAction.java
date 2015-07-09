package rt.action;

import java.util.LinkedHashMap;
import java.util.List;

import rt.dao.IQueryDAO;
import rt.dao.IQueryDAOImpl;

import com.opensymphony.xwork2.ActionSupport;

public class QueryFlowAction extends ActionSupport{

	/**
	 * 查转借记录
	 */
	private static final long serialVersionUID = 3612319012880983603L;

	private String flowValue;
	private List<LinkedHashMap<String, Object>> listQueryFlow;

	IQueryDAO query;
	
	public String initQueryFlow(){
		return SUCCESS;
	}
	
	public String queryFlow(){
		query = new IQueryDAOImpl();
		listQueryFlow = query.queryFlowBySerialNumber(flowValue);
		return SUCCESS;
	}
	
	/*******************公共属性**********************/
	
	public List<LinkedHashMap<String, Object>> getListQueryFlow() {
		return listQueryFlow;
	}
	
	public void setListQueryFlow(List<LinkedHashMap<String, Object>> listQueryFlow) {
		this.listQueryFlow = listQueryFlow;
	}

	public String getFlowValue() {
		return flowValue;
	}

	public void setFlowValue(String flowValue) {
		this.flowValue = flowValue;
	}
	
}
