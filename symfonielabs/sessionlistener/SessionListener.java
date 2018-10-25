package com.spectra.symfonielabs.sessionlistener;

 
 
 
import com.spectra.symfonielabs.service.PhoneService;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
 
public class SessionListener implements HttpSessionListener {
	 private static int activeSessions = 0;
	public void sessionCreated(HttpSessionEvent event) {
		activeSessions++;
		System.out.println("A new session is created");
		getActiveSessions();
	}
 
	public void sessionDestroyed(HttpSessionEvent event) {
		  if(activeSessions > 0)
		      activeSessions--;
		  try {

	 		PhoneService obj = (PhoneService)event.getSession().getAttribute("object");
	 		event.getSession().removeAttribute("listener") ;
	         		
	 		if (obj != null){		
	 			obj.hangup();
	 			obj.shutProvider();
	 		}
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  Date today = new Date();
		System.out.println("session is destroyed " +today.toString());
		getActiveSessions();
	}
public static int getActiveSessions() {
	System.out.println("activeSessions = "+activeSessions);
    return activeSessions;
 }
}
