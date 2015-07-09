package rt.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	private static int sessionCounter = 0;

	public SessionListener() {
//		System.out.println("OnlineCounter initialized."+sessionCounter);
	}

	public void sessionCreated(HttpSessionEvent se) {
		sessionCounter++;
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		sessionCounter--;
	}

	public static int getOnlineSession() {
		sessionCounter = sessionCounter < 1 ? 1 : sessionCounter;
		return sessionCounter;
	}
}