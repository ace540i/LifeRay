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
import com.spectra.symfonielabs.constants.OrderSourceConstants;
import com.spectra.symfonielabs.constants.ScreenConstants;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.OrderSource;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;
import com.spectra.symfonielabs.domainobject.Search;
import com.spectra.symfonielabs.service.OrderSourceService;
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
import java.util.Enumeration;
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
public class OrderSourceProcessor extends SearchUtilityProcessor implements 
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
		if (OrderSourceConstants.GET_ORDER_SOURCE_DETAILS.equalsIgnoreCase(action)) {
			this.getOrderSourceDetails(request, response);
		} else if (OrderSourceConstants.GET_ORDER_TEST_DETAILS
				.equalsIgnoreCase(action)) {
			this.getOrderTestDetails(request, response);
		} else if (OrderSourceConstants.SEARCH_REQUISITION.equalsIgnoreCase(action)) {
			this.getReqSearchDetails(request, response);;	
		} else if (OrderSourceConstants.PAGINATE_ORDER_SOURCE.equalsIgnoreCase(action)) {
			this.paginateOrderSource(request, response);
		} 
		else if(OrderSourceConstants.GET_ORDER_SOURCE.equalsIgnoreCase(action)){
			this.getOrderSource(request, response);
		}
	}

	/**
	 * Gets the order source for the patient.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getOrderSourceDetails(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.getOrderSourceDetails");
		request.getSession().removeAttribute("orderSourceInfo");
		final OrderSourceService orderSourceService = new OrderSourceServiceImpl();
		final PatientService patientService = new PatientServiceImpl();
		long facilityId = 0l;
		long spectraMRN = 0l;
		String patientDOB = "";      // timc
		String patType = ""; 
		Date drawDate = null;
		int dateRange = 3;
		final SimpleDateFormat simpleDateFmt = SimpleDateFormatUtil
				.getSimpleDateFormatLocale("MM/dd/yyyy");
		final OrderSource orderSrc = new OrderSource();
		orderSrc.setStartIndex(1);
		orderSrc.setEndIndex(1000);
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
			}	
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("patType")))) {
				patType = String.valueOf(request.getParameter("patType"));
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
			//Gets the order source grid details.
			final List<OrderSource> orderSourceLst = orderSourceService.getOrderSource(facilityId, spectraMRN, drawDate, orderSrc, patType);

			//Setting the sessions for pagination purpose.
			request.getSession().setAttribute(
					OrderSourceConstants.ORDER_SOURCE_INFO, patientObj);
			request.getSession().setAttribute(
					OrderSourceConstants.ORDER_SEARCH_SOURCE_PARAMETERS, orderSrc);
			request.getSession().setAttribute(
					OrderSourceConstants.ORDER_SOURCE_DETAILS, orderSourceLst);
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
		PERF_LOGGER.info("End of OrderSourceProcessor.getOrderSourceDetails");
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
		PERF_LOGGER.info("Start of OrderSourceProcessor.getOrderTestDetails");
		List<OrderSource> lstOrderTestDetails = new ArrayList<OrderSource>();
		final OrderSourceService orderSourceService = new OrderSourceServiceImpl();
		String requisitionNo = CommonConstants.EMPTY_STRING;
		List<String> jsonStrArray = new ArrayList<String>();
		String reportGroupVal = CommonConstants.EMPTY_STRING;
//		String EquipmentSource = CommonConstants.EMPTY_STRING;
		//Gets the requisition number from the client side.
		
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("requisitionNo")))) {
			requisitionNo = (String) request.getParameter("requisitionNo");
		}
		try {
			// Gets the order test grid details, either report group details or
			// accession group details.
			lstOrderTestDetails = orderSourceService
					.getOrderTestDetails(requisitionNo);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		
		if (null != lstOrderTestDetails && !lstOrderTestDetails.isEmpty()) {
				jsonStrArray = this.generateOrderTestDetailsJSON(lstOrderTestDetails);
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
		PERF_LOGGER.info("End of OrderSourceProcessor.getOrderTestDetails");
	}
	/**
	 * Method to generate order source JSON.
	 * 
	 * @param orderSourceLst
	 *            - Holds the list of order source details.
	 * @return - List<String>.
	 */
	private List<String> generateOrderSourceJSON(
			final List<OrderSource> orderSourceLst) {
		PERF_LOGGER.info("Start of OrderSourceProcessor.generateOrderSourceJSON");
		final List<HashMap> orderMap = new ArrayList<HashMap>();
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				CommonConstants.SIMPLE_DATE_FORMAT);
		int i = 0;
		for (OrderSource OrderSource : orderSourceLst) {
			final HashMap<String, Object> orderSourceMap = 
					new HashMap<String, Object>();
			orderSourceMap.put("drawDate",
					dateFormat.format(OrderSource.getDrawDate()));
			orderSourceMap.put("requisitionNo",
					OrderSource.getRequisitionNum());
			orderSourceMap.put("testsCount", OrderSource.getNumOfTests());
			orderSourceMap.put("frequency", OrderSource.getFrequency());
			orderSourceMap.put("status", OrderSource.getStatus());
			orderSourceMap.put("abnormalFlag", 
					OrderSource.getAbnormalFlag());
			orderSourceMap.put("NumOfTubesNotReceived", 
					OrderSource.getNumOfTubesNotReceived());
			orderSourceMap.put("cancelledTestIndicator",
					OrderSource.isCancelledTestIndicator());
			orderSourceMap.put("patientType", OrderSource.getPatientType());
			
			StringBuffer fac= new StringBuffer("");
			if (!CommonConstants.EMPTY_STRING.equals(StringUtil
				.valueOf( OrderSource.getFacility().getCorporateAcronym() ))) {
				
				fac.append(OrderSource.getFacility().getCorporateAcronym()).append(" - ");
			}
			fac.append(OrderSource.getFacility().getFacilityName());			
			orderSourceMap.put("facility", fac.toString());
			fac = null;
			
			orderMap.add(orderSourceMap);
			
		}
		PERF_LOGGER.info("End of OrderSourceProcessor.generateOrderSourceJSON");
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
		PERF_LOGGER.info("Start of OrderSourceProcessor.getReqSearchDetails");
		request.getSession().removeAttribute(OrderSourceConstants.PATIENT_DETAILS);
		final OrderSourceService orderSourceService = new OrderSourceServiceImpl();
		String searchType = null;
		String searchValue = null;
		boolean isOrderSumPage = false;
		String equipmentSource = null;
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("equipmentSource")))) {
				 equipmentSource = String.valueOf(request.getParameter("equipmentSource"));
		}
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
		request.getSession().setAttribute(OrderSourceConstants.SEARCH_TYPE,
				searchType);
		request.getSession().setAttribute(OrderSourceConstants.SEARCH_CRITERIA,
				searchCriteria);
		request.getSession().setAttribute(OrderSourceConstants.REQUISITION_NO,
				searchValue); 
		if (!isOrderSumPage) {
			request.getSession().removeAttribute(
					CommonConstants.SEARCH_PARAMETERS);
			request.getSession().setAttribute(
					CommonConstants.SEARCH_PARAMETERS, searchCriteria);
		}
		try {
		final RequisitionDetails patientDet = orderSourceService
					.getPatientReqDetails("", searchValue);
			
			request.getSession().setAttribute(
					OrderSourceConstants.PATIENT_TYPE_SUB_GRP,patientDet.getPatientTypeSubGrp());
			request.getSession().setAttribute(OrderSourceConstants.PATIENT_DETAILS,
					patientDet);
			request.setAttribute(OrderSourceConstants.PAGE_TO_BE_LOADED,
					"reqDetailSourcePage");
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderSourceProcessor.getReqSearchDetails");
	}
	
	/**
	 * Method to generate order test details JSON.
	 * 
	 * @param lstOrderTestDetails
	 *            - Holds the list of test details.
	 * @return - List<String>.
	 */
	private List<String> generateOrderTestDetailsJSON(
			final List<OrderSource> lstOrderTestDetails) {
		PERF_LOGGER
				.info("Start of OrderProcessor.generateOrderTestDetailsJSON");
		String prev = null;
		String curr = null;
		boolean isNormalTest = false;
		// End
		final List<HashMap> lstOrderMap = new ArrayList<HashMap>();
		for (OrderSource orderTestDetail : lstOrderTestDetails) {
			isNormalTest = false;
			HashMap<String, Object> orderSourceMap = new HashMap<String, Object>();
			curr = orderTestDetail.getAccession_number();

				orderSourceMap.put("accession", orderTestDetail.getAccession_number());
				orderSourceMap.put("test", orderTestDetail.getOrderTestName());
				orderSourceMap.put("testCount", orderTestDetail.getNumOfTests());
				orderSourceMap.put("orderType", orderTestDetail.getOrderType());
				lstOrderMap.add(orderSourceMap);
		}
		
		PERF_LOGGER.info("End of OrderSourceProcessor.generateOrderTestDetailsJSON");
		return ConstructJSONFormat.generateJsonFormat(lstOrderMap);
	}
	/**
	 * Method to paginate the order source grid section.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest
	 * @param response
	 *            - Holds the HttpServletResponse
	 */
	private void paginateOrderSource(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderSourceProcessor.paginateOrderSource");
		final String jsonRoot = CommonConstants.JSON_ROOT_LIST_INFO;
		final String start = request.getParameter(CommonConstants.START);
		final String strRows = request.getParameter(CommonConstants.LIMIT);
		final int rowsPerPage = Integer.parseInt(strRows);
		int listSize = 0;
		List<OrderSource> orderSourceLst = (List<OrderSource>)
				request.getSession().getAttribute(
						OrderSourceConstants.ORDER_SOURCE_DETAILS);
		if (null != request.getSession().getAttribute(
				CommonConstants.START_INDEX)) {
			// used for paginating & sort the patients.
			orderSourceLst = this.paginateAndSortOrderSource(request, response);
		}
		if (null != orderSourceLst && !orderSourceLst.isEmpty()) {
			// Generate JSON array of patient search results for paging.
			final List<String> jsonStrArray = this
					.generateOrderSourceJSON(orderSourceLst);
			response.setContentType(ScreenConstants.JSON_RESPONSE_TYPE);
			response.setHeader(CommonConstants.CACHE_CONTROL,
					CommonConstants.NO_CACHE);
			try {
				listSize = (int) orderSourceLst.get(0).getListSize();
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
		PERF_LOGGER.info("End of OrderSourceProcessor.paginateOrderSource");
	}
	
	/**
	 * Gets the order source for the patient.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getOrderSource(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderSourceProcessor.getOrderSource");
		request.getSession().removeAttribute("orderSourceInfo");
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
			// timc
						if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("patientDOB")))) {
							patientDOB = String.valueOf(request.getParameter("patientDOB"));
						}
			// timc							
			// Gets the patient details for the selected facility ID and
			// spectraMRN.
			final Patient patientObj = patientService.getPatientDetails(
					facilityId, spectraMRN, patientDOB);
			request.getSession().setAttribute(
					OrderSourceConstants.ORDER_SUMMARY_INFO, patientObj);
			request.setAttribute(OrderSourceConstants.PAGE_TO_BE_LOADED,
					"orderDetailPage");
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderSourceProcessor.getOrderSource");
	}
		
}

