package rt.action;

import java.io.IOException;
import java.util.List;

import rt.dao.CheckCarton;
import rt.util.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

public class CheckCartonAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1193490229304552515L;

	private String carton_no;
	String result ="正常";
	static CheckCarton chk = new CheckCarton();
	static List<String> list = chk.listCarton_no();
	
	public String initCarton(){
		return SUCCESS;
	}
	
	public String chkCarton(){
		if(null != list){
			if(list.contains(carton_no)){
				result = "FATP违反规则";
			}
		}
//		try {
//			Struts2Utils.getResponse().getWriter().write(result);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return SUCCESS;
	}
	
	public String getCarton_no() {
		return carton_no;
	}

	public void setCarton_no(String carton_no) {
		this.carton_no = carton_no;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	
	
}
