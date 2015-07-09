package rt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class AccessFilter implements Filter {

	private static Logger log = Logger.getLogger(AccessFilter.class);
	
	@Override
	public void destroy() {
		System.out.println("AccessFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null && request.getRequestURI().indexOf("login.jsp") == -1) {
			log.error("过滤器捕获到 IP:"+request.getRemoteAddr()+",机器名："+request.getRemoteHost()+" 非正常方式访问");
//			System.out.println("IP:"+request.getRemoteAddr()+"\t机器名："+request.getRemoteHost()+" 试图非法访问");
			response.sendRedirect("login.jsp");
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("AccessFilter init");
	}

}