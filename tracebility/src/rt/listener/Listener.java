package rt.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import rt.util.TUtil;

public class Listener implements ServletContextListener {
	private static Logger log = Logger.getLogger(Listener.class);
	public void contextDestroyed(ServletContextEvent arg0) {
		log.error(TUtil.format("yyyy-MM-dd HH-mm:ss")+"应用被关闭");
//		HttpSession session = Struts2Utils.getSession();
//		session.invalidate();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		log.info(TUtil.format("yyyy-MM-dd HH-mm:ss")+"应用已经启动");
	}

}
