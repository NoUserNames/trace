package rt.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class Struts2Utils{

	public static HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();		
	}
	
	public static HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();		
	}
	
	public static HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	
	public static ServletContext getServletContext(){
		return ServletActionContext.getServletContext();
	}
}
