package com.spectra.symfonielabs.portlet;

import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.spectra.symfonie.framework.constants.ExceptionConstants;
import com.spectra.symfonie.framework.logging.ApplicationRootLogger;
import com.spectra.symfonielabs.processor.PatientProcessor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
//import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

/**
 * Portlet implementation class SearchPortlet
 */
public class SearchPortlet extends MVCPortlet {
	
	/**
	 * Logger for this class.
	 */
	private static final Logger LOGGER = ApplicationRootLogger
			.getLogger(PatientProcessor.class.getName());
	
	@ProcessAction(name="sendRequest")
	public void sendRequest(final ActionRequest request,
			final ActionResponse response) {
		
		//timc
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//	Enumeration params = request.getParameterNames(); 
		//	while(params.hasMoreElements()){
		//	 String paramName = (String)params.nextElement();
		//	 System.out.println("@@@@ SearchPortle.sendRequestt @@@@   #-> Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		//	}	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		try {
			HttpServletRequest httpRequest = PortalUtil
					.getHttpServletRequest(request);
			HttpServletResponse httpResponse = PortalUtil
					.getHttpServletResponse(response);
			String searchType =  request.getParameter("searchType");
		    httpRequest.setAttribute("searchType",searchType);
			RequestDispatcher reqDispatcher = httpRequest
					.getRequestDispatcher("/ControllerServlet");
			reqDispatcher.include(httpRequest, httpResponse);
			QName qName = new QName("PatientSearchDet", "PatientSearchDet");
		    response.setEvent(qName, (String)httpRequest.getAttribute("pageToBeLoadedAttr"));
		} catch (ServletException exception) {
			final StringWriter stringWriter = new StringWriter();
			exception.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + ExceptionConstants.EXCEPTION_STACK_TRACE
					+ stringWriter.toString());
		} catch (IOException exception) {
			final StringWriter stringWriter = new StringWriter();
			exception.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + ExceptionConstants.EXCEPTION_STACK_TRACE
					+ stringWriter.toString());
		}
	}
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException, IOException {
		try {
			if ("getFacilitiesNames".equalsIgnoreCase(resourceRequest
					.getResourceID())) {
				HttpServletRequest httpRequest = PortalUtil
						.getHttpServletRequest(resourceRequest);
				HttpServletResponse httpResponse = PortalUtil
						.getHttpServletResponse(resourceResponse);
				RequestDispatcher reqDispatcher = httpRequest
						.getRequestDispatcher("/ControllerServlet");
				reqDispatcher.include(httpRequest, httpResponse);
			} else if ("getCorporationNames".equalsIgnoreCase(resourceRequest
					.getResourceID())) {
				HttpServletRequest httpRequest = PortalUtil
						.getHttpServletRequest(resourceRequest);
				HttpServletResponse httpResponse = PortalUtil
						.getHttpServletResponse(resourceResponse);
				RequestDispatcher reqDispatcher = httpRequest
						.getRequestDispatcher("/ControllerServlet");
				reqDispatcher.include(httpRequest, httpResponse);
			}
		} catch (ServletException exception) {
			final StringWriter stringWriter = new StringWriter();
			exception.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + ExceptionConstants.EXCEPTION_STACK_TRACE
					+ stringWriter.toString());
		} catch (IOException exception) {
			final StringWriter stringWriter = new StringWriter();
			exception.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + ExceptionConstants.EXCEPTION_STACK_TRACE
					+ stringWriter.toString());
		}
	}
}
