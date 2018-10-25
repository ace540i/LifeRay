/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.processor;

import com.spectra.symfonie.common.constants.CommonConstants;
import com.spectra.symfonie.common.util.ConstructJSONFormat;
import com.spectra.symfonie.common.util.PaginationHelper;
import com.spectra.symfonie.common.util.StringUtil;
import com.spectra.symfonie.framework.constants.ExceptionConstants;
import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonie.framework.logging.ApplicationRootLogger;
import com.spectra.symfonie.framework.processor.Processor;
import com.spectra.symfonie.framework.util.SimpleDateFormatUtil;
import com.spectra.symfonielabs.common.processor.SearchUtilityProcessor;
import com.spectra.symfonielabs.constants.OrderConstants;
import com.spectra.symfonielabs.constants.OrderSourceConstants;
import com.spectra.symfonielabs.constants.ScreenConstants;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.OrderSummary;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;
import com.spectra.symfonielabs.domainobject.Search;
import com.spectra.symfonielabs.service.OrderService;
import com.spectra.symfonielabs.service.OrderSourceService;
import com.spectra.symfonielabs.service.OrderServiceImpl;
import com.spectra.symfonielabs.service.OrderSourceServiceImpl;
import com.spectra.symfonielabs.service.PatientService;
import com.spectra.symfonielabs.service.PatientServiceImpl;
import com.spectra.symfonielabs.transformer.CommonTransformer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class processes all the requests from the client side related to the
 * orders.
 *
 */
public class OrderProcessor extends SearchUtilityProcessor implements 
	Processor {

	/**
	 * Performance Logger for this class.
	 */
	private static final Logger PERF_LOGGER = ApplicationRootLogger
			.getPerformanceLogger();
	/**
	 * Logger for this class.
	 */
	private static final Logger LOGGER = ApplicationRootLogger
			.getLogger(PatientProcessor.class.getName());

	/**
	 * Processes the order related requests of a patient received from the
	 * screens.
	 *
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 * @param action
	 *            - Holds the action to be performed.
	 */
	public final void process(final HttpServletRequest request,
			final HttpServletResponse response, final String action) {
		if (OrderConstants.GET_ORDER_SUMMARY_DETAILS.equalsIgnoreCase(action)) {
			this.getOrderSummaryDetails(request, response);
		} else if (OrderConstants.GET_TUBE_SUMMARY.equalsIgnoreCase(action)) {
			this.getTubeSummary(request, response);
		} else if (OrderConstants.GET_ORDER_TEST_DETAILS
				.equalsIgnoreCase(action)) {
			this.getOrderTestDetails(request, response);
		} else if (OrderConstants.GET_MICRO_ORDER_TEST_DETAILS
				.equalsIgnoreCase(action)) {
			this.getMicroOrderTestDetails(request, response);
		} else if (OrderConstants.SEARCH_REQUISITION.equalsIgnoreCase(action)) {
			this.getReqSearchDetails(request, response);			
		} else if (OrderConstants.PAGINATE_ORDER_SUMMARY.equalsIgnoreCase(action)) {
			this.paginateOrderSummary(request, response);
		} else if(OrderConstants.GET_ORDER_SUM.equalsIgnoreCase(action)){
			this.getOrderSum(request, response);
		}
	}

	/**
	 * Gets the order summary for the patient.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getOrderSummaryDetails(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.getOrderSummaryDetails");
		request.getSession().removeAttribute("orderSummaryInfo");
		final OrderService orderService = new OrderServiceImpl();
		final PatientService patientService = new PatientServiceImpl();
		long facilityId = 0l;
		long spectraMRN = 0l;
		String patientDOB = "";      // timc
		Date drawDate = null;
		int dateRange = 3;
		final SimpleDateFormat simpleDateFmt = SimpleDateFormatUtil
				.getSimpleDateFormatLocale("MM/dd/yyyy");
		final OrderSummary orderSumm = new OrderSummary();
		orderSumm.setStartIndex(1);
		orderSumm.setEndIndex(1000);
		try {
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(request.getParameter("facilityId")))) {
				facilityId = Long.valueOf(request.getParameter("facilityId"));
			}
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(request.getParameter("spectraMRN")))) {
				spectraMRN = Long.valueOf(request.getParameter("spectraMRN"));
			}
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(request.getParameter("dateRange"))) {			
				dateRange = Integer.valueOf(request.getParameter("dateRange"));
				request.getSession().setAttribute("dateRange",dateRange);
			}
 		 	if (null != request.getSession().getAttribute("dateRange")) {				
 		 	request.setAttribute("dateRange", request.getSession().getAttribute("dateRange"));
 		 	dateRange=Integer.valueOf(request.getSession().getAttribute("dateRange").toString());
 		 	}
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("patientDOB")))) {
				patientDOB = String.valueOf(request.getParameter("patientDOB"));
			 	//System.out.println(new java.util.Date()+" - XXXX OrderProcessor.getOrderSummaryDetails @@@@   #-> patientDOB - "+patientDOB);
			}			
			
			//Substract days to current date.
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -dateRange);
			final String strDrawDate = simpleDateFmt.format(cal.getTime());
			try {
				drawDate = simpleDateFmt.parse(strDrawDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// Gets the patient details which will be displayed at the top
			// section.
			final Patient patientObj = patientService.getPatientDetails(
					facilityId, spectraMRN, patientDOB);
			//Gets the order summary grid details.
			final List<OrderSummary> orderSummaryLst = orderService
					.getOrderSummary(facilityId, spectraMRN, drawDate,
							orderSumm);
			//Setting the sessions for pagination purpose.
			request.getSession().setAttribute(
					OrderConstants.ORDER_SUMMARY_INFO, patientObj);
			request.getSession().setAttribute(
					OrderConstants.ORDER_SEARCH_PARAMETERS, orderSumm);
			request.getSession().setAttribute(
					OrderConstants.ORDER_SUMMARY_DETAILS, orderSummaryLst);
			request.getSession().setAttribute(CommonConstants.START_INDEX,
	        		1);
	        request.getSession().setAttribute(CommonConstants.END_INDEX,
	        		1000);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderProcessor.getOrderSummaryDetails");
	}

	/**
	 * Gets the tube summary details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getTubeSummary(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.getTubeSummary");
		List<Accession> lstTubeSummary = new ArrayList<Accession>();
		final OrderService orderService = new OrderServiceImpl();
		String requisitionNo = CommonConstants.EMPTY_STRING;
		List<String> jsonStrArray = new ArrayList<String>();
		//Gets the requisition number passed from the client side.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("requisitionNo")))) {
			requisitionNo = (String) request.getParameter("requisitionNo");
		}
		try {
			//Gets the tube summary grid details.
			lstTubeSummary = orderService
					.getTubeSummary(requisitionNo);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		//Generates JSON format to display the necessary fields.
		if (null != lstTubeSummary && !lstTubeSummary.isEmpty()) {
			jsonStrArray = this
					.generateTubeSummaryJSON(request, lstTubeSummary);
		}
		final String jsonFormat = CommonConstants.JSON_FORMAT_LISTINFO
				+ jsonStrArray.toString() + CommonConstants.CLOSE_BRACE;
		response.setContentType(CommonConstants.TEXT_JSON_STRING);
		response.setHeader(CommonConstants.CACHE_CONTROL,
				CommonConstants.NO_CACHE);
		try {
			response.getWriter().print(jsonFormat);
		} catch (IOException ioException) {
			final StringWriter stringWriter = new StringWriter();
			ioException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderProcessor.getTubeSummary");
	}

	/**
	 * Gets the order test details for the requisition number.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getOrderTestDetails(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.getOrderTestDetails");
		List<Results> lstOrderTestDetails = new ArrayList<Results>();
		final OrderService orderService = new OrderServiceImpl();
		String requisitionNo = CommonConstants.EMPTY_STRING;
		List<String> jsonStrArray = new ArrayList<String>();
		String reportGroupVal = CommonConstants.EMPTY_STRING;
		//Gets the requisition number from the client side.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("requisitionNo")))) {
			requisitionNo = (String) request.getParameter("requisitionNo");
		}
		//Gets the report grouping value from the client side.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("reportGroupVal")))) {
			reportGroupVal = (String) request.getParameter("reportGroupVal")
					.toUpperCase();
		}
		try {
			// Gets the order test grid details, either report group details or
			// accession group details.
			lstOrderTestDetails = orderService
					.getOrderTestDetails(requisitionNo, reportGroupVal);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		// Generates JSON format for the order test details (Report
		// grouping/Acession grouping).
		if (null != lstOrderTestDetails && !lstOrderTestDetails.isEmpty()) {
			if (OrderConstants.REPORT_HEADER.equalsIgnoreCase(reportGroupVal)) {
				jsonStrArray = this.generateReportTestDetailsJSON(lstOrderTestDetails);
			} else  if 	(OrderConstants.ORDER_STATUS.equalsIgnoreCase(reportGroupVal)) {
					jsonStrArray = this.generateStatusTestDetailsJSON(lstOrderTestDetails);
				
			} else jsonStrArray = this.generateOrderTestDetailsJSON(lstOrderTestDetails);
		}
		final String jsonFormat = CommonConstants.JSON_FORMAT_LISTINFO
				+ jsonStrArray.toString() + CommonConstants.CLOSE_BRACE;
		response.setContentType(CommonConstants.TEXT_JSON_STRING);
		response.setHeader(CommonConstants.CACHE_CONTROL,
				CommonConstants.NO_CACHE);
		try {
			response.getWriter().print(jsonFormat);
		} catch (IOException ioException) {
			final StringWriter stringWriter = new StringWriter();
			ioException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderProcessor.getOrderTestDetails");
	}

	/**
	 * Gets the micro order test details for the requisition number.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getMicroOrderTestDetails(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.getMicroOrderTestDetails");
		List<Results> lstOrderTestDetails = null;
		OrderService orderService = new OrderServiceImpl();
		String requisitionNo = null;
		List<String> jsonStrArray = new ArrayList<String>();
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("requisitionNo")))) {
			requisitionNo = (String) request.getParameter("requisitionNo");
		}
		try {
			lstOrderTestDetails = orderService
					.getMicroOrderTestDetails(requisitionNo);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		if (null != lstOrderTestDetails && !lstOrderTestDetails.isEmpty()) {
			jsonStrArray = this.generateMicroOrderTestDetailsJSON(lstOrderTestDetails);
		}
		final String jsonFormat = CommonConstants.JSON_FORMAT_LISTINFO
				+ jsonStrArray.toString() + CommonConstants.CLOSE_BRACE;
		response.setContentType(CommonConstants.TEXT_JSON_STRING);
		response.setHeader(CommonConstants.CACHE_CONTROL,
				CommonConstants.NO_CACHE);
		try {
			response.getWriter().print(jsonFormat);
		} catch (IOException ioException) {
			final StringWriter stringWriter = new StringWriter();
			ioException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderProcessor.getMicroOrderTestDetails");
	}

	/**
	 * Method to generate order summary JSON.
	 * 
	 * @param orderSummaryLst
	 *            - Holds the list of order summary details.
	 * @return - List<String>.
	 */
	private List<String> generateOrderSummaryJSON(
			final List<OrderSummary> orderSummaryLst) {
		PERF_LOGGER.info("Start of OrderProcessor.generateOrderSummaryJSON");
		final List<HashMap> orderMap = new ArrayList<HashMap>();
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				CommonConstants.SIMPLE_DATE_FORMAT);
		for (OrderSummary orderSummary : orderSummaryLst) {
			final HashMap<String, Object> orderSummaryMap = 
					new HashMap<String, Object>();
//			System.out.println("ordersum "+orderSummaryLst.get(i)); i++;
					
			orderSummaryMap.put("drawDate",
					dateFormat.format(orderSummary.getDrawDate()));
			orderSummaryMap.put("requisitionNo",
					orderSummary.getRequisitionNum());
			orderSummaryMap.put("testsCount", orderSummary.getNumOfTests());
			orderSummaryMap.put("frequency", orderSummary.getFrequency());
			orderSummaryMap.put("status", orderSummary.getStatus());
			orderSummaryMap.put("abnormalFlag", 
					orderSummary.getAbnormalFlag());
			orderSummaryMap.put("NumOfTubesNotReceived", 
					orderSummary.getNumOfTubesNotReceived());
			orderSummaryMap.put("cancelledTestIndicator",
					orderSummary.isCancelledTestIndicator());
			orderSummaryMap.put("patientType", orderSummary.getPatientType());
			
			StringBuffer fac= new StringBuffer("");
			if (!CommonConstants.EMPTY_STRING.equals(StringUtil
				.valueOf( orderSummary.getFacility().getCorporateAcronym() ))) {
				
				fac.append(orderSummary.getFacility().getCorporateAcronym()).append(" - ");
			}
			fac.append(orderSummary.getFacility().getFacilityName());			
			orderSummaryMap.put("facility", fac.toString());
			fac = null;
			
			orderMap.add(orderSummaryMap);
			
		}
		PERF_LOGGER.info("End of OrderProcessor.generateOrderSummaryJSON");
		return ConstructJSONFormat.generateJsonFormat(orderMap);
	}
	
	/**
	 * Gets the order details for the requisition number.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getReqSearchDetails(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.getReqSearchDetails");
		request.getSession().removeAttribute(OrderConstants.PATIENT_DETAILS);
		final OrderService orderService = new OrderServiceImpl();
		
		
		String searchType = null;
		String searchValue = null;
		boolean isOrderSumPage = false;
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("searchType")))) {
			searchType = (String) request.getParameter("searchType");
		}
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("reqNumber")))) {
			searchValue = (String) request.getParameter("reqNumber");
		}
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("reqNumber")))) {
			isOrderSumPage = Boolean.valueOf(request
					.getParameter("isOrderSumPage"));
		}
		final Search searchCriteria = new Search(searchType, searchValue, 0L,
				null, null);
		searchCriteria.setSearchType(searchType);
		request.getSession().setAttribute(OrderConstants.SEARCH_TYPE,
				searchType);
		request.getSession().setAttribute(OrderConstants.SEARCH_CRITERIA,
				searchCriteria);
		request.getSession().setAttribute(OrderConstants.REQUISITION_NO,
				searchValue); 
		if (!isOrderSumPage) {
			request.getSession().removeAttribute(
					CommonConstants.SEARCH_PARAMETERS);
			request.getSession().setAttribute(
					CommonConstants.SEARCH_PARAMETERS, searchCriteria);
		}
		try {
		  RequisitionDetails patientDet = orderService.getPatientReqDetails("", searchValue);		
			//** order source **//	
			if (patientDet.getRequisition() == null) {
				final OrderSourceService orderSourceService = new OrderSourceServiceImpl();
				  patientDet = orderSourceService.getPatientReqDetails("", searchValue);	
				OrderSourceProcessor osp = new OrderSourceProcessor(); 
				osp.process(request, response, "searchRequisition");
				request.setAttribute(OrderSourceConstants.PAGE_TO_BE_LOADED, "reqDetailSourcePage");
				request.setAttribute("pageToBeLoadedAttr", "reqDetailSourcePage");
			} else {	
				request.getSession().setAttribute(OrderConstants.PATIENT_TYPE_SUB_GRP,patientDet.getPatientTypeSubGrp());
				request.getSession().setAttribute(OrderConstants.PATIENT_DETAILS,patientDet);
				request.setAttribute(OrderConstants.PAGE_TO_BE_LOADED,"reqDetailPage");	
				request.setAttribute("pageToBeLoadedAttr", "reqDetailPage");
			} 
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderProcessor.getReqSearchDetails");
	}
	
	/**
	 * Method to generate order test details JSON.
	 * 
	 * @param lstOrderTestDetails
	 *            - Holds the list of test details.
	 * @return - List<String>.
	 */
	private List<String> generateOrderTestDetailsJSON(
			final List<Results> lstOrderTestDetails) {
		PERF_LOGGER
				.info("Start of OrderProcessor.generateOrderTestDetailsJSON");
		String prev = null;
		String curr = null;
		// Start - Added for order analyte test grouping
		String prevOrderTestName = null;
		String currOrderTestName = null;
		String currResultTestName = null;
		boolean isNormalTest = false;
		// End
		final List<HashMap> lstOrderMap = new ArrayList<HashMap>();
		for (Results orderTestDetail : lstOrderTestDetails) {
			isNormalTest = false;
			HashMap<String, Object> orderSummaryMap = 
					new HashMap<String, Object>();
			curr = orderTestDetail.getAccession();
			if (null != curr && !curr.equalsIgnoreCase(prev)) {
				orderSummaryMap.put("test", orderTestDetail.getAccession());
				orderSummaryMap.put("requisitionHeader", CommonConstants.TRUE);
				orderSummaryMap.put("testNameHeader", CommonConstants.FALSE);
				lstOrderMap.add(orderSummaryMap);
				prevOrderTestName = null;
			}
			// Start - Added for order analyte test grouping
			currOrderTestName = orderTestDetail.getOrderTestName()
					.toUpperCase();
			currResultTestName = orderTestDetail.getResultTestName()
					.toUpperCase();
			if (null != currOrderTestName
				&& !currOrderTestName.equalsIgnoreCase(prevOrderTestName)
				&& !(currOrderTestName.equalsIgnoreCase(currResultTestName))) {
				orderSummaryMap = new HashMap<String, Object>();
				if (orderTestDetail.getParentTestCount() > 1) {
					orderSummaryMap.put("test",
							orderTestDetail.getOrderTestName());
					orderSummaryMap.put("testNameHeader", 
							CommonConstants.TRUE);
					lstOrderMap.add(orderSummaryMap);
					isNormalTest = false;
				} else {
					orderSummaryMap.put("test",
							orderTestDetail.getOrderTestName());
					orderSummaryMap.put("testNameHeader", 
							CommonConstants.TRUE);
					lstOrderMap.add(orderSummaryMap);
					isNormalTest = false;
				}
			}
			if (currOrderTestName.equalsIgnoreCase(currResultTestName)) {
				if (!currOrderTestName.equalsIgnoreCase(prevOrderTestName)
						&& orderTestDetail.getParentTestCount() > 1) {
					orderSummaryMap = new HashMap<String, Object>();
					orderSummaryMap.put("test",
							orderTestDetail.getOrderTestName());
					orderSummaryMap.put("testNameHeader", 
							CommonConstants.TRUE);
					lstOrderMap.add(orderSummaryMap);
				}
				if (orderTestDetail.getParentTestCount() > 1) {
					isNormalTest = false;
				} else {
					isNormalTest = true;
					orderSummaryMap.put("testNameHeader", 
							CommonConstants.FALSE);
				}
			}
			// End
			orderSummaryMap = new HashMap<String, Object>();
			orderSummaryMap.put("requisitionHeader", CommonConstants.FALSE);
			String resultTestName = orderTestDetail.getResultTestName();
			if (!isNormalTest) {
				resultTestName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ resultTestName;
			}
			orderSummaryMap.put("test", resultTestName);
			orderSummaryMap.put("result", CommonTransformer.escapeToJSON(orderTestDetail.getTextualResult()));
			orderSummaryMap.put("units", orderTestDetail.getUOM());
			orderSummaryMap.put("reference",
					orderTestDetail.getReferenceRange());
			String comments = CommonConstants.EMPTY_STRING;
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(orderTestDetail.getResultComment()))) {
				comments = orderTestDetail.getResultComment();
				comments = comments.replaceAll("\n", "\\\\n")
						.replaceAll("\r", "\\\\r\\\\n")
						.replaceAll("\"", 
						CommonConstants.ESCAPE_DOUBLE_QUOTES);
			}
			orderSummaryMap.put("comments", comments);
			String indicators = CommonConstants.EMPTY_STRING;
			if ("H".equalsIgnoreCase(orderTestDetail.getIndicator())) {
				indicators = "High";
			} else if ("L".equalsIgnoreCase(orderTestDetail.getIndicator())) {
				indicators = "Low";
			}
			orderSummaryMap.put("abnormalFlag", 
					orderTestDetail.getIndicator());
			orderSummaryMap.put("indicators", indicators);
			orderSummaryMap
					.put("orderStatus", orderTestDetail.getOrderStatus());
			String orderControlReason = CommonConstants.EMPTY_STRING;
			if (null != orderTestDetail.getOrderControlReason()) {
				orderControlReason = orderTestDetail.getOrderControlReason();
				orderControlReason = orderControlReason.replaceAll("\\n|\\r",
						CommonConstants.EMPTY_STRING).replaceAll("\"",
						CommonConstants.ESCAPE_DOUBLE_QUOTES);
			}
			orderSummaryMap.put("orderControlReason", orderControlReason); 			
			orderSummaryMap.put("shmsg", orderTestDetail.getShmsg());		
			lstOrderMap.add(orderSummaryMap);
			prev = curr;
			prevOrderTestName = currOrderTestName;
			
		}
		
		PERF_LOGGER.info("End of OrderProcessor.generateOrderTestDetailsJSON");
		return ConstructJSONFormat.generateJsonFormat(lstOrderMap);
	}
	
	/**
	 * Method to generate report order test details JSON.
	 * 
	 * @param lstOrderTestDetails
	 *            - Holds the list of test details.
	 * @return - List<String>.
	 */
	private List<String> generateReportTestDetailsJSON(
			final List<Results> lstOrderTestDetails) {
		PERF_LOGGER
				.info("Start of OrderProcessor.generateReportTestDetailsJSON");
		String prev = null;
		String curr = null;
		String prevOT = null;
		String currOT = null;
		String currRTN = null;
		boolean isNormalTest = false;
		final List<HashMap> lstOrderMap = new ArrayList<HashMap>();
		for (Results orderTestDetail : lstOrderTestDetails) {
			isNormalTest = false;
			HashMap<String, Object> orderSummaryMap = new HashMap<String, Object>();
			curr = orderTestDetail.getReportGrpName();
			// If the report group name is not same, then make the report group
			// name as a header.
			if (null != curr && !curr.equalsIgnoreCase(prev)) {
				orderSummaryMap.put("test", orderTestDetail.getReportGrpName());
				orderSummaryMap.put("requisitionHeader", CommonConstants.TRUE);
				orderSummaryMap.put("testNameHeader", CommonConstants.FALSE);
				lstOrderMap.add(orderSummaryMap);
			}
			
			// Start - Added for order analyte test grouping for Other Tests
			if ( (curr != null) && (curr.equalsIgnoreCase("Other Tests")) ) {
				currOT = orderTestDetail.getOrderTestName();								
				currRTN = orderTestDetail.getResultTestName();
				if (currOT != null
						&& !currOT.equalsIgnoreCase(prevOT)
						&& !(currOT.equalsIgnoreCase(currRTN))) {
						orderSummaryMap = new HashMap<String, Object>();
						if (orderTestDetail.getParentTestCount() > 1) {
							orderSummaryMap.put("test",
									orderTestDetail.getOrderTestName());
							orderSummaryMap.put("testNameHeader", 
									CommonConstants.TRUE);
							lstOrderMap.add(orderSummaryMap);
							isNormalTest = false;
						} else {
							orderSummaryMap.put("test",
									orderTestDetail.getOrderTestName());
							orderSummaryMap.put("testNameHeader", 
									CommonConstants.TRUE);
							lstOrderMap.add(orderSummaryMap);
							isNormalTest = false;
						}
					}
					if (currOT.equalsIgnoreCase(currRTN)) {
						if (!currOT.equalsIgnoreCase(prevOT)
								&& orderTestDetail.getParentTestCount() > 1) {
							orderSummaryMap = new HashMap<String, Object>();
							orderSummaryMap.put("test",
									orderTestDetail.getOrderTestName());
							orderSummaryMap.put("testNameHeader", 
									CommonConstants.TRUE);
							lstOrderMap.add(orderSummaryMap);
						}
						if (orderTestDetail.getParentTestCount() > 1) {
							isNormalTest = false;
						} else {
							isNormalTest = true;
							orderSummaryMap.put("testNameHeader", 
									CommonConstants.FALSE);
						}
					}
				
				// End
				prevOT = currOT;
			}
			
			orderSummaryMap = new HashMap<String, Object>();
			orderSummaryMap.put("requisitionHeader", CommonConstants.FALSE);
			String resultTestName = orderTestDetail.getResultTestName();
			if (!isNormalTest) {
				resultTestName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ resultTestName;
			}
			orderSummaryMap.put("test", resultTestName);	
			String result=orderTestDetail.getTextualResult();
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(result))) {
				result = result.replaceAll("\n", "\\\\n")
						.replaceAll("\r", "\\\\r\\\\n")
						.replaceAll("\"", 
						CommonConstants.ESCAPE_DOUBLE_QUOTES);
			}			
			orderSummaryMap.put("result", CommonTransformer.escapeToJSON(orderTestDetail.getTextualResult()));		
			orderSummaryMap.put("units", orderTestDetail.getUOM());
			orderSummaryMap.put("reference",
					orderTestDetail.getReferenceRange());
			String comments = CommonConstants.EMPTY_STRING;
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(orderTestDetail.getResultComment()))) {
				comments = orderTestDetail.getResultComment();
				comments = comments.replaceAll("\n", "\\\\n")
						.replaceAll("\r", "\\\\r\\\\n")
						.replaceAll("\"", 
						CommonConstants.ESCAPE_DOUBLE_QUOTES);
			}
			orderSummaryMap.put("comments", comments);
			String indicators = CommonConstants.EMPTY_STRING;
			if ("H".equalsIgnoreCase(orderTestDetail.getIndicator())) {
				indicators = "High";
			} else if ("L".equalsIgnoreCase(orderTestDetail.getIndicator())) {
				indicators = "Low";
			}
			orderSummaryMap.put("abnormalFlag", 
					orderTestDetail.getIndicator());
			orderSummaryMap.put("indicators", indicators);
			orderSummaryMap
					.put("orderStatus", orderTestDetail.getOrderStatus());
			String orderControlReason = CommonConstants.EMPTY_STRING;
			if (null != orderTestDetail.getOrderControlReason()) {
				orderControlReason = orderTestDetail.getOrderControlReason();
				orderControlReason = orderControlReason.replaceAll("\\n|\\r",
						CommonConstants.EMPTY_STRING).replaceAll("\"",
						CommonConstants.ESCAPE_DOUBLE_QUOTES);
			}
			orderSummaryMap.put("orderControlReason", orderControlReason);				
			orderSummaryMap.put("shmsg", orderTestDetail.getShmsg());
						
			lstOrderMap.add(orderSummaryMap);
			prev = curr;
		}
		PERF_LOGGER.
			info("End of OrderProcessor.generateReportTestDetailsJSON");
		return ConstructJSONFormat.generateJsonFormat(lstOrderMap);
	}
	/**
	 * Method to generate report order test details JSON.
	 * 
	 * @param lstOrderTestDetails
	 *            - Holds the list of test details.
	 * @return - List<String>.
	 */
	private List<String> generateStatusTestDetailsJSON(
			final List<Results> lstOrderTestDetails) {
		PERF_LOGGER
				.info("Start of OrderProcessor.generateStatusTestDetailsJSON");
		String prev = null;
		String curr = null;
		final List<HashMap> lstOrderMap = new ArrayList<HashMap>();
		for (Results orderTestDetail : lstOrderTestDetails) {
			HashMap<String, Object> orderSummaryMap = new HashMap<String, Object>();
			curr = orderTestDetail.getReportGrpName();
			// If the report group name is not same, then make the report group
			// name as a header.
			if (null != curr && !curr.equalsIgnoreCase(prev)) {
				orderSummaryMap.put("test", orderTestDetail.getReportGrpName());
				orderSummaryMap.put("requisitionHeader", CommonConstants.TRUE);
				orderSummaryMap.put("testNameHeader", CommonConstants.FALSE);
				lstOrderMap.add(orderSummaryMap);
			}
			orderSummaryMap = new HashMap<String, Object>();
			orderSummaryMap.put("requisitionHeader", CommonConstants.FALSE);
			String resultTestName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ orderTestDetail.getResultTestName();
			orderSummaryMap.put("test", resultTestName);
			String result=orderTestDetail.getTextualResult();
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(result))) {
				result = result.replaceAll("\n", "\\\\n")
						.replaceAll("\r", "\\\\r\\\\n")
						.replaceAll("\"", 
						CommonConstants.ESCAPE_DOUBLE_QUOTES);
			}			
			orderSummaryMap.put("result", CommonTransformer.escapeToJSON(orderTestDetail.getTextualResult()));
			orderSummaryMap.put("units", orderTestDetail.getUOM());
			orderSummaryMap.put("reference",
					orderTestDetail.getReferenceRange());
			String comments = CommonConstants.EMPTY_STRING;
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(orderTestDetail.getResultComment()))) {
				comments = orderTestDetail.getResultComment();
				comments = comments.replaceAll("\n", "\\\\n")
						.replaceAll("\r", "\\\\r\\\\n")
						.replaceAll("\"", 
						CommonConstants.ESCAPE_DOUBLE_QUOTES);
			}
			orderSummaryMap.put("comments", comments);
			String indicators = CommonConstants.EMPTY_STRING;
			if ("H".equalsIgnoreCase(orderTestDetail.getIndicator())) {
				indicators = "High";
			} else if ("L".equalsIgnoreCase(orderTestDetail.getIndicator())) {
				indicators = "Low";
			}
			orderSummaryMap.put("abnormalFlag", 
					orderTestDetail.getIndicator());
			orderSummaryMap.put("indicators", indicators);
			orderSummaryMap
					.put("orderStatus", orderTestDetail.getOrderStatus());
			String orderControlReason = CommonConstants.EMPTY_STRING;
			if (null != orderTestDetail.getOrderControlReason()) {
				orderControlReason = orderTestDetail.getOrderControlReason();
				orderControlReason = orderControlReason.replaceAll("\\n|\\r",
						CommonConstants.EMPTY_STRING).replaceAll("\"",
						CommonConstants.ESCAPE_DOUBLE_QUOTES);
			}
			orderSummaryMap.put("orderControlReason", orderControlReason);				
			orderSummaryMap.put("shmsg", orderTestDetail.getShmsg());
						
			lstOrderMap.add(orderSummaryMap);
			prev = curr;
		}
		PERF_LOGGER.
			info("End of OrderProcessor.generateStatusTestDetailsJSON");
		return ConstructJSONFormat.generateJsonFormat(lstOrderMap);
	}
	
	/**
	 * Method to generate order test details JSON.
	 * 
	 * @param lstOrderTestDetails
	 *            - Holds the list of test details.
	 * @return - List<String>.
	 */
	private List<String> generateMicroOrderTestDetailsJSON(
			List<Results> lstOrderTestDetails) {
		PERF_LOGGER.info("Start of OrderProcessor.generateMicroOrderTestDetailsJSON");
		String prevAcsn = null;
		String curAcsn = null;
		//Start - Added for order analyte test grouping
		String prevOrderTestName = null;
		String currOrderTestName = null;
		String currResultTestName = null;
		boolean isNormalTest = false;
		boolean printSensHeader = false;
		StringBuffer sensResult = null;
		boolean isolateHeader = false;
		//End
		final List<HashMap> lstOrderMap = new ArrayList<HashMap>();
		int row;
		int isoRow=-2; //setting it to -1 will match row=0 record.
		String isoName="";
		String orgName="";
		String isoRes="";
		Results orderTestDetail = null;
		HashMap<String, Object> orderSummaryMap;
		for (row=0; row<lstOrderTestDetails.size(); row++) {
			orderTestDetail = lstOrderTestDetails.get(row);
			
			isNormalTest = false;
			
			//check if we need to add a row for previous isolate result.
			if ( (row==(isoRow+1)) && 
					( (!isoName.equals("")) && (!isoName.equals(StringUtil.valueOf(orderTestDetail.getMicroIsolate())))) &&
					(!orderTestDetail.getPatientTypeSubGroup().equalsIgnoreCase("ENVIRONMENTAL")) &&
					(!orgName.equals(isoRes)) ) {
				orderSummaryMap = new HashMap<String, Object>();
	
				orderSummaryMap.put("result", CommonTransformer.parseToJSON(isoRes));
				lstOrderMap.add(orderSummaryMap);	
				isoRow=-2;
				isoName="";
				orgName="";
				isoRes="";				
			}
			
			orderSummaryMap =
					new HashMap<String, Object>();
			//Get the Accession header row populated
			curAcsn = orderTestDetail.getAccession();
			if ( (curAcsn != null) && !curAcsn.equalsIgnoreCase(prevAcsn)) {
				orderSummaryMap.put("test", curAcsn);
				orderSummaryMap.put("requisitionHeader", CommonConstants.TRUE);
				orderSummaryMap.put("testNameHeader", CommonConstants.FALSE);
				lstOrderMap.add(orderSummaryMap);
				prevOrderTestName = null;
			}
			
			//Start - Added for order analyte test grouping
			currOrderTestName = orderTestDetail.getOrderTestName().toUpperCase();
			currResultTestName = orderTestDetail.getResultTestName().toUpperCase();
			StringBuffer SpecSource = null;
			if ( (currOrderTestName != null) 
					&& (!currOrderTestName.equalsIgnoreCase(prevOrderTestName)) ) {					
				
				orderSummaryMap = new HashMap<String, Object>();
				
				SpecSource = new StringBuffer("<br>Specimen/Source: ").append(orderTestDetail.getSpecimenMethodDesc());
				
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(orderTestDetail.getSpecimenSourceDesc()))) {
					
					SpecSource.append("/").append(orderTestDetail.getSpecimenSourceDesc());
					
				}
				orderSummaryMap.put("test", orderTestDetail.getOrderTestName() + SpecSource.toString());
				orderSummaryMap.put("testNameHeader", CommonConstants.TRUE);				
				orderSummaryMap.put("comments", CommonTransformer.parseToJSON(orderTestDetail.getReportNotes()));	
				orderSummaryMap.put("shmsg", orderTestDetail.getShmsg());
				lstOrderMap.add(orderSummaryMap);
				isNormalTest = false;				
		}			
			
			if (!CommonConstants.EMPTY_STRING.equals(StringUtil.valueOf(orderTestDetail.getMicroIsolate())) ) {
				isoRow=row;
				isoName=orderTestDetail.getMicroIsolate();			
				orgName=orderTestDetail.getMicroOrganismName();
			} 		
			if (orderTestDetail.getResultTestName().startsWith("Isolate #")) {
				isoRes=orderTestDetail.getTextualResult();				
			}			
			if (StringUtil.valueOf(orderTestDetail.getMicroSensitivityFlag()).equalsIgnoreCase("Y")) {				
				orderSummaryMap = new HashMap<String, Object>();			
				if (!printSensHeader) {
					orderSummaryMap.put("printSensHeader", CommonConstants.TRUE);
					printSensHeader = true;
				}			
				orderSummaryMap.put("microSensFlag", orderTestDetail.getMicroSensitivityFlag());
				orderSummaryMap.put("result", CommonTransformer.parseToJSON(orderTestDetail.getTextualResult()));
				orderSummaryMap.put("abnormalFlag", orderTestDetail.getIndicator());
				orderSummaryMap.put("microIsolate", orderTestDetail.getMicroIsolate());
				orderSummaryMap.put("microOrg", orderTestDetail.getMicroOrganismName());
				orderSummaryMap.put("microSensName", orderTestDetail.getMicroSensitivityName());
				orderSummaryMap.put("shmsg", orderTestDetail.getShmsg());			
				lstOrderMap.add(orderSummaryMap);
				prevAcsn = curAcsn;
				prevOrderTestName = currOrderTestName;
				continue;
				
			} else {
				printSensHeader = false;			
			}			
			orderSummaryMap =
					new HashMap<String, Object>();
			orderSummaryMap.put("requisitionHeader", CommonConstants.FALSE);
			String resultTestName = orderTestDetail.getResultTestName();
			if (!isNormalTest) {
				resultTestName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+resultTestName;
			} 
			orderSummaryMap.put("test",resultTestName);
			if (CommonConstants.EMPTY_STRING.equals(StringUtil.valueOf(orderTestDetail.getMicroIsolate()))) {
				orderSummaryMap.put("result", CommonTransformer.parseToJSON(orderTestDetail.getTextualResult()));
			} else {
				//just display the organism name for the Isolate result
				orderSummaryMap.put("result", orderTestDetail.getMicroOrganismName());				
			}

			
			
			orderSummaryMap.put("units", orderTestDetail.getUOM());
			orderSummaryMap.put("reference", orderTestDetail
					.getReferenceRange());
			orderSummaryMap.put("comments", CommonTransformer.parseToJSON(orderTestDetail.getResultComment()));
			String indicators = CommonConstants.EMPTY_STRING;
			if("H".equalsIgnoreCase(orderTestDetail.getIndicator())){
				indicators = "High";
			}else if("L".equalsIgnoreCase(orderTestDetail
					.getIndicator())){
				indicators = "Low";
			}
			orderSummaryMap.put("abnormalFlag", orderTestDetail.getIndicator());
			orderSummaryMap.put("indicators", indicators);
			orderSummaryMap.put("orderStatus", orderTestDetail
					.getOrderStatus());
			orderSummaryMap.put("orderControlReason", CommonTransformer.parseToJSON(orderTestDetail.getOrderControlReason()));
			orderSummaryMap.put("microSensFlag", orderTestDetail.getMicroSensitivityFlag());
			orderSummaryMap.put("microIsolate", orderTestDetail.getMicroIsolate());
			orderSummaryMap.put("microOrg", orderTestDetail.getMicroOrganismName());		
			orderSummaryMap.put("shmsg", orderTestDetail.getShmsg());
			lstOrderMap.add(orderSummaryMap);
			
			prevAcsn = curAcsn;
			prevOrderTestName = currOrderTestName;
		}	
		LOGGER.fine("row: " + row + ", isoRow: " + isoRow + ", orgName: " + orgName + ", isoRes: " + isoRes);
		if ( (row==(isoRow+1)) && 				
				(!orderTestDetail.getPatientTypeSubGroup().equalsIgnoreCase("ENVIRONMENTAL")) &&
				(!orgName.equals(isoRes)) ) {
		orderSummaryMap = new HashMap<String, Object>();		
		orderSummaryMap.put("result", CommonTransformer.parseToJSON(isoRes));
		lstOrderMap.add(orderSummaryMap);				
		}		
		PERF_LOGGER.info("End of OrderProcessor.generateMicroOrderTestDetailsJSON");
		return ConstructJSONFormat.generateJsonFormat(lstOrderMap);
	}
	/**
	 * Method to generate tube summary JSON.
	 * @param request 
	 * 
	 * @param lstOrderTestDetails
	 *            - Holds the list of test details.
	 * @return - List<String>.
	 */
	private List<String> generateTubeSummaryJSON(
			final HttpServletRequest request,
			final List<Accession> lstTubeSummary) {
		PERF_LOGGER.info("Start of OrderProcessor.generateTubeSummaryJSON");
		final List<HashMap> lstTubeSummaryMap = new ArrayList<HashMap>();
		final SimpleDateFormat simpleDateFormat = SimpleDateFormatUtil
				.getSimpleDateFormatLocale(CommonConstants.
						DATE_FORMAT_WITH_TIME);
		int receivedCount = 0;
		final int totalCount = lstTubeSummary.size();
		for (Accession tubeSummary : lstTubeSummary) {
			final HashMap<String, Object> tubeSummaryMap =
					new HashMap<String, Object>();
			tubeSummaryMap.put("accession", tubeSummary.getAccession());
			tubeSummaryMap.put("tubetype", tubeSummary.getTubetype());
			tubeSummaryMap.put("specimen", tubeSummary.getSpecimen());
			tubeSummaryMap.put("labFk", tubeSummary.getLabfk());
			String strReceivedDate = CommonConstants.EMPTY_STRING;
			if (null != tubeSummary.getReceivedDate()) {
				strReceivedDate = simpleDateFormat.format(tubeSummary
						.getReceivedDate());
				++receivedCount;
			}
			tubeSummaryMap.put("recievedDetails", strReceivedDate);
			tubeSummaryMap.put("condition", tubeSummary.getCondition());
			tubeSummaryMap.put("status", tubeSummary.getStatus());
			tubeSummaryMap.put("receivedCount", receivedCount);
			tubeSummaryMap.put("totalCount", totalCount);
			lstTubeSummaryMap.add(tubeSummaryMap);
		}
		PERF_LOGGER.info("End of OrderProcessor.generateTubeSummaryJSON");
		return ConstructJSONFormat.generateJsonFormat(lstTubeSummaryMap);
	}

	/**
	 * Method to paginate the order summary grid section.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest
	 * @param response
	 *            - Holds the HttpServletResponse
	 */
	private void paginateOrderSummary(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.paginateOrderSummary");
		final String jsonRoot = CommonConstants.JSON_ROOT_LIST_INFO;
		final String start = request.getParameter(CommonConstants.START);
		final String strRows = request.getParameter(CommonConstants.LIMIT);
		final int rowsPerPage = Integer.parseInt(strRows);
		int listSize = 0;
		List<OrderSummary> orderSummLst = (List<OrderSummary>)
				request.getSession().getAttribute(
				OrderConstants.ORDER_SUMMARY_DETAILS);
		if (null != request.getSession().getAttribute(
				CommonConstants.START_INDEX)) {
			// used for paginating & sort the patients.
			orderSummLst = this.paginateAndSortOrderSumm(request, response);
		}
		if (null != orderSummLst && !orderSummLst.isEmpty()) {
			// Generate JSON array of patient search results for paging.
			final List<String> jsonStrArray = this
					.generateOrderSummaryJSON(orderSummLst);
			response.setContentType(ScreenConstants.JSON_RESPONSE_TYPE);
			response.setHeader(CommonConstants.CACHE_CONTROL,
					CommonConstants.NO_CACHE);
			try {
				listSize = (int) orderSummLst.get(0).getListSize();
				// if the start index is not null then paginate with index.
				if (null != request.getSession().getAttribute(
						CommonConstants.START_INDEX)) {
					response.getWriter().print(
							PaginationHelper.paginateWithIndex(jsonRoot,
									jsonStrArray, listSize));
				} else {
					response.getWriter().print(
							PaginationHelper.paginate(jsonRoot, jsonStrArray,
									Integer.valueOf(start), rowsPerPage));
				}
			} catch (IOException ioException) {
				final StringWriter stringWriter = new StringWriter();
				ioException.printStackTrace(new PrintWriter(stringWriter));
			}
		} else {
			try {
				// Print the response generated from the paginate method.
				response.getWriter().print(
						CommonConstants.EMPTY_PAGINATION_CONTENT);
			} catch (IOException ioException) {
				final StringWriter stringWriter = new StringWriter();
				ioException.printStackTrace(new PrintWriter(stringWriter));
			}
		}
		PERF_LOGGER.info("End of OrderProcessor.paginateOrderSummary");
	}	
	/**
	 * Gets the order summary for the patient.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getOrderSum(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.getOrderSum");
		request.getSession().removeAttribute("orderSummaryInfo");
		final PatientService patientService = new PatientServiceImpl();
		long facilityId = 0l;
		long spectraMRN = 0l;
		String patientDOB = "";
		try {
			//Gets the selected facility ID.
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(request.getParameter("facilityId")))) {
				facilityId = Long.valueOf(request.getParameter("facilityId"));
			}
			//Gets the selected spectraMRN value.
			if (!CommonConstants.TRUE.equalsIgnoreCase(StringUtil
					.valueOf(request.getAttribute("isPatientSrhFlow")))
					&& !CommonConstants.EMPTY_STRING
					         .equalsIgnoreCase(StringUtil.valueOf(request
					        		 .getParameter("spectraMRN")))) {
				spectraMRN = Long.valueOf(request.getParameter("spectraMRN"));
			} else if (!CommonConstants.EMPTY_STRING
					.equalsIgnoreCase(StringUtil.valueOf(request
							.getAttribute("spectraMRN")))) {
				spectraMRN = Long.valueOf(String.valueOf(request
						.getAttribute("spectraMRN")));
			}
						if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("patientDOB")))) {
							patientDOB = String.valueOf(request.getParameter("patientDOB"));
						}						
			// Gets the patient details for the selected facility ID and
			// spectraMRN.
			final Patient patientObj = patientService.getPatientDetails(
					facilityId, spectraMRN, patientDOB);
			request.getSession().setAttribute(
					OrderConstants.ORDER_SUMMARY_INFO, patientObj);
			request.setAttribute(OrderConstants.PAGE_TO_BE_LOADED,
					"orderDetailPage");
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderProcessor.getOrderSum");
	}
		
}
