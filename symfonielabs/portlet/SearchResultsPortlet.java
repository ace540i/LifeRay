/* 12/22/2015 - md - US1087
 * 				1) Added condition for getStaffDetails.
 *  			2) Added condition for orderStaffDetailPage pageToBeLoaded.
*/
package com.spectra.symfonielabs.portlet;

import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.spectra.symfonie.common.constants.CommonConstants;
import com.spectra.symfonie.common.util.StringUtil;
import com.spectra.symfonie.framework.constants.ExceptionConstants;
import com.spectra.symfonie.framework.logging.ApplicationRootLogger;
import com.spectra.symfonielabs.processor.PatientProcessor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.ProcessEvent;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

/**
 * Portlet implementation class SearchResultsPortlet
 */
public class SearchResultsPortlet extends MVCPortlet {
	
	
	/**
	 * Logger for this class.
	 */
	private static final Logger LOGGER = ApplicationRootLogger
			.getLogger(PatientProcessor.class.getName());
	
	@ProcessEvent(qname = "{PatientSearchDet}PatientSearchDet")
	public void getRequest(final EventRequest request,
			final EventResponse response) {
		Event event = request.getEvent();
		String value = (String) event.getValue();
		HttpServletRequest httpRequest = PortalUtil
				.getHttpServletRequest(request);
		httpRequest.setAttribute("pageToBeLoadedAttr", value);
	}
	
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException, IOException {
		try {
			if ("getPatDetails".equalsIgnoreCase(resourceRequest
					.getResourceID())) {
				HttpServletRequest httpRequest = PortalUtil
						.getHttpServletRequest(resourceRequest);
				HttpServletResponse httpResponse = PortalUtil
						.getHttpServletResponse(resourceResponse);
				RequestDispatcher reqDispatcher = httpRequest
						.getRequestDispatcher("/ControllerServlet");
				reqDispatcher.include(httpRequest, httpResponse);
			} else if ("requisitionDetails".equalsIgnoreCase(resourceRequest
					.getResourceID())) {
				HttpServletRequest httpRequest = PortalUtil
						.getHttpServletRequest(resourceRequest);
				HttpServletResponse httpResponse = PortalUtil
						.getHttpServletResponse(resourceResponse);
				RequestDispatcher reqDispatcher = httpRequest
						.getRequestDispatcher("/ControllerServlet");
				reqDispatcher.include(httpRequest, httpResponse);
			} else if ("orderSummaryDetails".equalsIgnoreCase(resourceRequest
					.getResourceID())) {
				HttpServletRequest httpRequest = PortalUtil
						.getHttpServletRequest(resourceRequest);
				HttpServletResponse httpResponse = PortalUtil
						.getHttpServletResponse(resourceResponse);
				RequestDispatcher reqDispatcher = httpRequest
						.getRequestDispatcher("/ControllerServlet");
				reqDispatcher.include(httpRequest, httpResponse);
			} else if ("dashboardDetails".equalsIgnoreCase(resourceRequest
					.getResourceID())) {
				HttpServletRequest httpRequest = PortalUtil
						.getHttpServletRequest(resourceRequest);
				HttpServletResponse httpResponse = PortalUtil
						.getHttpServletResponse(resourceResponse);
				RequestDispatcher reqDispatcher = httpRequest
						.getRequestDispatcher("/ControllerServlet");
				reqDispatcher.include(httpRequest, httpResponse);
			} else if ("getFacSearchDet".equalsIgnoreCase(resourceRequest
					.getResourceID())) {
				HttpServletRequest httpRequest = PortalUtil
						.getHttpServletRequest(resourceRequest);
				HttpServletResponse httpResponse = PortalUtil
						.getHttpServletResponse(resourceResponse);
				RequestDispatcher reqDispatcher = httpRequest
						.getRequestDispatcher("/ControllerServlet");
				reqDispatcher.include(httpRequest, httpResponse);
			} else if ("getFacDemographics".equalsIgnoreCase(resourceRequest
					.getResourceID())) {
				HttpServletRequest httpRequest = PortalUtil
						.getHttpServletRequest(resourceRequest);
				HttpServletResponse httpResponse = PortalUtil
						.getHttpServletResponse(resourceResponse);
				RequestDispatcher reqDispatcher = httpRequest
						.getRequestDispatcher("/ControllerServlet");
				reqDispatcher.include(httpRequest, httpResponse);
			}// added for staff
			else if ("getStaffDetails".equalsIgnoreCase(resourceRequest
					.getResourceID())) {
				HttpServletRequest httpRequest = PortalUtil
						.getHttpServletRequest(resourceRequest);
				HttpServletResponse httpResponse = PortalUtil
						.getHttpServletResponse(resourceResponse);
				RequestDispatcher reqDispatcher = httpRequest
						.getRequestDispatcher("/ControllerServlet");
				reqDispatcher.include(httpRequest, httpResponse);
			}// added for Equipment
			else if ("getEquipmentDetails".equalsIgnoreCase(resourceRequest.getResourceID())) {
			// System.out.println("000-> SearchResultsPortlet.serveResource: equipment : processorName=  " + resourceRequest.getParameter("processorName"));
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(resourceRequest);
			// System.out.println(httpRequest.getParameter("processorName"));
			HttpServletResponse httpResponse = PortalUtil.getHttpServletResponse(resourceResponse);
			RequestDispatcher reqDispatcher = httpRequest.getRequestDispatcher("/ControllerServlet");
			reqDispatcher.include(httpRequest, httpResponse);
			// System.out.println("000-> SearchResultsPortlet.serveResource: getEquipmentDetails   00 5 in process action ");
			}  	else if ("getPhone".equalsIgnoreCase(resourceRequest
					.getResourceID())) {
				HttpServletRequest httpRequest = PortalUtil
						.getHttpServletRequest(resourceRequest);
				HttpServletResponse httpResponse = PortalUtil
						.getHttpServletResponse(resourceResponse);
				RequestDispatcher reqDispatcher = httpRequest
						.getRequestDispatcher("/ControllerServlet");
				reqDispatcher.include(httpRequest, httpResponse);
			}	
			else if ("getUserPhone".equalsIgnoreCase(resourceRequest
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
	
	@ProcessAction(name = "sendResultsRequest")
	public void sendResultsRequest(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
		HttpServletRequest httpRequest = PortalUtil
				.getHttpServletRequest(actionRequest);
		HttpServletResponse httpResponse = PortalUtil
				.getHttpServletResponse(actionResponse);
		RequestDispatcher reqDispatcher = httpRequest
				.getRequestDispatcher("/ControllerServlet");
		httpRequest.removeAttribute("facilityId");
		httpRequest.removeAttribute("spectraMRN");
		String pageToBeLoaded = actionRequest.getParameter("loadPage");
		if("orderDetailPage".equalsIgnoreCase(pageToBeLoaded)){
			try{
				String facilityId = CommonConstants.EMPTY_STRING;
				String spectraMRN = CommonConstants.EMPTY_STRING;
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(actionRequest.getParameter("facilityId")))) {
					facilityId = actionRequest.getParameter("facilityId");
				}
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(actionRequest.getParameter("spectraMRN")))) {
					spectraMRN = actionRequest.getParameter("spectraMRN");
				}
				httpRequest.setAttribute("facilityId", facilityId);
				httpRequest.setAttribute("spectraMRN", spectraMRN);
				reqDispatcher.include(httpRequest, httpResponse);
			} catch (ServletException exception) {
				final StringWriter stringWriter = new StringWriter();
				exception.printStackTrace(new PrintWriter(stringWriter));
				LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
						+ "\n" + ExceptionConstants.EXCEPTION_STACK_TRACE
						+ stringWriter.toString());
			}
		}
		if ("orderStaffDetailPage".equalsIgnoreCase(pageToBeLoaded)) { // added condition for staff
			try {
				String facilityId = CommonConstants.EMPTY_STRING;
				String spectraMRN = CommonConstants.EMPTY_STRING;
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(actionRequest.getParameter("facilityId")))) {
					facilityId = actionRequest.getParameter("facilityId");
				}
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(actionRequest.getParameter("spectraMRN")))) {
					spectraMRN = actionRequest.getParameter("spectraMRN");
				}
				httpRequest.setAttribute("facilityId", facilityId);
				httpRequest.setAttribute("spectraMRN", spectraMRN);
				reqDispatcher.include(httpRequest, httpResponse);
			} catch (ServletException exception) {
				final StringWriter stringWriter = new StringWriter();
				exception.printStackTrace(new PrintWriter(stringWriter));
				LOGGER.log(Level.SEVERE,
						ExceptionConstants.APPLICATION_EXCEPTION + "\n"
								+ ExceptionConstants.EXCEPTION_STACK_TRACE
								+ stringWriter.toString());
			}
		}

		if("equipmentDetailPage".equalsIgnoreCase(pageToBeLoaded)){  // added condition for equipment
			// System.out.println("YYYYYYYYYYYYYYYYYYYY  [SearchResultsPortlet]  -- <equipmentDetailPage>  YYYYYYYYYYYYYYYYYYYYYYYY ");
			try{
				String facilityId = CommonConstants.EMPTY_STRING;
				String spectraMRN = CommonConstants.EMPTY_STRING;
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(actionRequest.getParameter("facilityId")))) {
					facilityId = actionRequest.getParameter("facilityId");
				}
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(actionRequest.getParameter("spectraMRN")))) {
					spectraMRN = actionRequest.getParameter("spectraMRN");
				}
				// System.out.println("YYYYYYYYYYYYYYYYYYYY  [SearchResultsPortlet]  -- [ httpRequest.setAttribute('facilityId', facilityId) ] : "+facilityId);
				// System.out.println("YYYYYYYYYYYYYYYYYYYY  [SearchResultsPortlet]  -- [ httpRequest.setAttribute('spectraMRN', spectraMRN) ] : "+spectraMRN);
				httpRequest.setAttribute("facilityId", facilityId);
				httpRequest.setAttribute("spectraMRN", spectraMRN);
				reqDispatcher.include(httpRequest, httpResponse);
			} catch (ServletException exception) {
				final StringWriter stringWriter = new StringWriter();
				exception.printStackTrace(new PrintWriter(stringWriter));
				LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
						+ "\n" + ExceptionConstants.EXCEPTION_STACK_TRACE
						+ stringWriter.toString());
			}
			// System.out.println("YYYYYYYYYYYYYYYYYYYY  [SearchResultsPortlet]  -- <equipmentDetailPage> BYE BYE BYE..............  ");
		}	
		httpRequest.setAttribute("pageToBeLoadedAttr", pageToBeLoaded);
	}
	

		@ProcessAction(name = "sendRequisitionRequestEquipment")
		public void sendRequisitionRequestEquipment(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException, ServletException {
			// System.out.println("WWWWWWWWWWWWWWWWWWWWW [SearchResultsPortlet]  -- <sendRequisitionRequestEquipment>  WWWWWWWWWWWWWWWWWWWWWWWW");
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(actionRequest);
			String pageToBeLoaded = actionRequest.getParameter("loadPage");
			// System.out.println("WWWWWWWWWWWWWWWWWWWWW [SearchResultsPortlet]  -- loadPage:  " + pageToBeLoaded);
			String processorName = actionRequest.getParameter("processorName");
			// System.out.println("WWWWWWWWWWWWWWWWWWWWW [SearchResultsPortlet]  -- processorName:  " + processorName);
			String processorAction = actionRequest.getParameter("processorAction");
			//System.out.println("WWWWWWWWWWWWWWWWWWWWW [SearchResultsPortlet]  -- processorAction:  " + processorAction);
			HttpServletResponse httpResponse = PortalUtil.getHttpServletResponse(actionResponse);
			RequestDispatcher reqDispatcher = httpRequest.getRequestDispatcher("/ControllerServlet");
			reqDispatcher.include(httpRequest, httpResponse);
			httpRequest.setAttribute("pageToBeLoadedAttr", pageToBeLoaded);
		}	

		
	@ProcessAction(name = "sendRequisitionRequest")
	public void sendRequisitionRequest(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException,
			PortletException, ServletException {
		HttpServletRequest httpRequest = PortalUtil
				.getHttpServletRequest(actionRequest);
		String pageToBeLoaded = actionRequest.getParameter("loadPage");
		HttpServletResponse httpResponse = PortalUtil
				.getHttpServletResponse(actionResponse);
		RequestDispatcher reqDispatcher = httpRequest
				.getRequestDispatcher("/ControllerServlet");
		reqDispatcher.include(httpRequest, httpResponse);
		httpRequest.setAttribute("pageToBeLoadedAttr", pageToBeLoaded);
	}
	@ProcessAction(name = "sendFacDemographicsReq")
	public void sendFacDemographicsReq(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException,
			PortletException, ServletException {
		try {
			HttpServletRequest httpRequest = PortalUtil
					.getHttpServletRequest(actionRequest);
			HttpServletResponse httpResponse = PortalUtil
					.getHttpServletResponse(actionResponse);
			String searchType =  actionRequest.getParameter("searchType");
		    httpRequest.setAttribute("searchType",searchType);
			RequestDispatcher reqDispatcher = httpRequest
					.getRequestDispatcher("/ControllerServlet");
			reqDispatcher.include(httpRequest, httpResponse);
			QName qName = new QName("PatientSearchDet", "PatientSearchDet");
			actionResponse.setEvent(qName,
					(String) httpRequest.getAttribute("pageToBeLoadedAttr"));
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
