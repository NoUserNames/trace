package rt.intercepter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.apache.log4j.Logger;

import rt.util.Struts2Utils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptor extends AbstractInterceptor {

//	private static Logger log = Logger.getLogger(SessionInterceptor.class);
	
	/**
	 * 拦截器
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		String userInfo = (String) session.get("username");
		HttpServletRequest request = Struts2Utils.getRequest();
		if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")) || request.getParameter("ajax") != null) {
            if(null == Struts2Utils.getSession() || null == userInfo){
//            	log.error("IP:"+request.getRemoteAddr()+",机器名："+request.getRemoteHost()+" 非法访问");
            	Struts2Utils.getResponse().setContentType("text/html;Charset=utf-8");
            	String errorMsg = "{\"statusCode\":\"301\", \"message\":\"会话已过期，请重新登录！\"}";//Session Timeout! Please re-sign in!
                Struts2Utils.getResponse().getWriter().write(errorMsg);
                return null;
            }  
        }
		Struts2Utils.getResponse().setContentType("text/html;Charset=utf-8");Struts2Utils.getResponse().setCharacterEncoding("UTF-8");
		return invocation.invoke();
	}
}