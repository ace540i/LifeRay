
package com.spectra.symfonielabs.processor;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.telephony.CallEvent;
import javax.telephony.ConnectionEvent;
import javax.telephony.MetaEvent;
import javax.telephony.TerminalConnectionEvent;
import javax.telephony.TerminalConnectionListener;

import com.spectra.symfonie.common.util.StringUtil;
import com.spectra.symfonie.framework.processor.Processor;
import com.spectra.symfonielabs.service.PhoneService; 
import com.spectra.symfonielabs.activedirectory.GetProperties;
import com.spectra.symfonielabs.common.processor.SearchUtilityProcessor;
import com.spectra.symfonielabs.constants.FacilityConstants;
import com.spectra.symfonielabs.domainobject.PhoneCallData;

 
public class PhoneProcessor extends SearchUtilityProcessor implements Processor, Serializable  {
	
	private static final long serialVersionUID = 1L;
	public static final String DATA = "data";
	public static final String LISTENER = "listener";
	public static final String SERVICE = "service";
 	public static final String SERVICES = "services";
	GetProperties getProp = new GetProperties();
	Properties prop = getProp.getProperties();
 
	/**
	 * Processes the facility Phone related requests received from the screens.
	 * 
	 * @param action
	 *            - Holds the action name.
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	public final void process(final HttpServletRequest request,
			final HttpServletResponse response, final String action) {	
		if (action != null) {
			try {			
			if (FacilityConstants.GET_PHONE.equalsIgnoreCase(action)) {
				this.getPhone(request, response);
		
			} else if (FacilityConstants.HANG_UP.equalsIgnoreCase(action)) {	
					this.hangUp(request, response);
			} else if (FacilityConstants.SHUT_DOWN.equalsIgnoreCase(action)) {	
					this.shutDown(request, response);
				} 
			}
		 	catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		 }
		}
		
	public void getPhone(final HttpServletRequest request,
				final HttpServletResponse response) {
		try {
		 	Map<String, String> errors = saveSession(request, response);
	 	    errors = saveCallData(request, response);

	 	if (errors ==null){
				PhoneCallData data= new PhoneCallData();
				String user = "";
				String pwd = "";
				String server = "";
				if (data!=null){
					user = 	 data.getLogin()!=null?data.getLogin():"";
					pwd  = 	 data.getPassword()!=null?data.getPassword():"";
					server = data.getServer()!=null?data.getServer():"";
				}
					PhoneService phoneService = (PhoneService) request.getSession().getAttribute("service");
					request.getSession().setAttribute("object", phoneService); 

				Listener listener = (Listener)request.getSession().getAttribute(LISTENER);
				if (!listener.isLoggedIn()){
					try {
						phoneService.getProvider();
						listener.setLoggedIn(true);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
				if (!listener.isConnected()){
					phoneService.makeCall();      
				}
				else{
					phoneService.hangup();
					phoneService.shutdown();
				}
	 	}
		   	else{
		 		System.out.println("error "+errors.toString());
		 	}

			}
			catch (Exception e) {
				e.printStackTrace();
				return;
			}
	}
 public void hangUp(HttpServletRequest request,  
	HttpServletResponse response) throws ServletException, IOException {
	 PhoneService obj = (PhoneService)request.getSession().getAttribute("object");
	 obj.hangup();
 }
 public void shutDown(HttpServletRequest request,  
	HttpServletResponse response) throws ServletException, IOException {
	 PhoneService obj = (PhoneService)request.getSession().getAttribute("object");
	 obj.hangup();
	 obj.shutProvider();
 }
 private Map<String, String> saveCallData(HttpServletRequest request, HttpServletResponse response) {
	 String caller = "";
	 String callee = "";
	 if(request.getParameter("phoneNumber").length() > 4){
		 callee = "8"+"1"+StringUtil.valueOf(request.getParameter("phoneNumber"));
	 } else 
	 {
		 callee =  StringUtil.valueOf(request.getParameter("phoneNumber"));
	 }
	 caller =  StringUtil.valueOf(request.getParameter("caller"));
	 String service = prop.getProperty("service");
		if (request.getSession().getAttribute(DATA)==null){
			request.getSession().setAttribute(DATA, new PhoneCallData());   // call data   
		}
		PhoneCallData data = (PhoneCallData)request.getSession().getAttribute(DATA);
		data.setCaller(caller);
		data.setCallee(callee);
		data.setService(service);
		Map<String, String> errors = data.errors();
		if (errors.get("caller") == null && errors.get("callee") == null && errors.get("service") == null){
			if (request.getSession().getAttribute(LISTENER)==null){
				request.getSession().setAttribute(LISTENER, new Listener());
			}
			Listener listener = (Listener)request.getSession().getAttribute(LISTENER);
			if (request.getSession().getAttribute(SERVICE)==null){
				request.getSession().setAttribute(SERVICE, new PhoneService(data,   listener));
			}
			return null;
		}
		else{
			return errors;
		}
	}

	private Map<String, String> saveSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String user = prop.getProperty("login");
		String password = prop.getProperty("password");
	    String server = prop.getProperty("server");
		
		if (request.getSession().getAttribute(DATA)==null){
			request.getSession().setAttribute(DATA, new PhoneCallData());
		}
		PhoneCallData data = (PhoneCallData)request.getSession().getAttribute(DATA);
	 	data.setLogin(user);
	 	data.setPassword(password);
		data.setServer(server);
		Map<String, String> errors = data.errors();
		if (errors.get("server") == null){
			System.setProperty("com.avaya.jtapi.tsapi.servers", server);
			return null;
		}
		else{
			return errors;
		}
	}
	
	public static class Listener implements TerminalConnectionListener{
		private boolean connected = false;
		private boolean loggedIn = false;
		public Listener(){
			
		}
		public void terminalConnectionActive(TerminalConnectionEvent arg0) {
			connected = true;
		}
		public void terminalConnectionCreated(TerminalConnectionEvent arg0) {
		}
		public void terminalConnectionDropped(TerminalConnectionEvent arg0) {
			connected = false;
		}
		public void terminalConnectionPassive(TerminalConnectionEvent arg0) {
		}
		public void terminalConnectionRinging(TerminalConnectionEvent arg0) {
		}
		public void terminalConnectionUnknown(TerminalConnectionEvent arg0) {
		}
		public void connectionAlerting(ConnectionEvent arg0) {
		}
		public void connectionConnected(ConnectionEvent arg0) {
		}
		public void connectionCreated(ConnectionEvent arg0) {
		}
		public void connectionDisconnected(ConnectionEvent arg0) {
		}
		public void connectionFailed(ConnectionEvent arg0) {
		}
		public void connectionInProgress(ConnectionEvent arg0) {
		}
		public void connectionUnknown(ConnectionEvent arg0) {
		}
		public void callActive(CallEvent arg0) {
		}
		public void callEventTransmissionEnded(CallEvent arg0) {
			connected = false;
		}
		public void callInvalid(CallEvent arg0) {
		}
		public void multiCallMetaMergeEnded(MetaEvent arg0) {
		}
		public void multiCallMetaMergeStarted(MetaEvent arg0) {
//			log.append("Received MultiCallMetaMergeStarted Event\n");
		}
		public void multiCallMetaTransferEnded(MetaEvent arg0) {
//			log.append("Received MultiCallMetaTransferEnded Event\n");
		}

		public void multiCallMetaTransferStarted(MetaEvent arg0) {
//			log.append("Received MultiCallMetaTransferStarted Event\n");
		}
		public void singleCallMetaProgressEnded(MetaEvent arg0) {
//			log.append("Received SingleCallMetaProgressEnded Event\n");
		}
		public void singleCallMetaProgressStarted(MetaEvent arg0) {
//			log.append("Received SingleCallMetaProgressStarted Event\n");
		}

		public void singleCallMetaSnapshotEnded(MetaEvent arg0) {
//			log.append("Received SingleCallMetaSnapshotEnded Event\n");
		}
		public void singleCallMetaSnapshotStarted(MetaEvent arg0) {
//			log.append("Received SingleCallMetaSnapshotStarted Event\n");
		}
		public boolean isConnected() {
			return connected;
		}
		public boolean isLoggedIn() {
			return loggedIn;
		}
		public void setLoggedIn(boolean loggedIn) {
			this.loggedIn = loggedIn;
		}
	}
}
