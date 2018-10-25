/* ===================================================================*/
/* 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New Processor Class for Staff Orders. 
 * 01/20/2016 	1) changed default dateRange from 1 to 12 months.
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
import com.spectra.symfonielabs.constants.OrderStaffConstants;
import com.spectra.symfonielabs.constants.ScreenConstants;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.OrderStaffSummary;
import com.spectra.symfonielabs.domainobject.Results;
import com.spectra.symfonielabs.domainobject.Search;
import com.spectra.symfonielabs.domainobject.Staff;
import com.spectra.symfonielabs.domainobject.StaffRequisitionDetails;
import com.spectra.symfonielabs.service.OrderStaffService;
import com.spectra.symfonielabs.service.OrderStaffServiceImpl;
import com.spectra.symfonielabs.service.StaffService;
import com.spectra.symfonielabs.service.StaffServiceImpl;

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
public class OrderStaffProcessor extends SearchUtilityProcessor implements Processor {

	/**
	 * Performance Logger for this class.
	 */
	private static final Logger PERF_LOGGER = ApplicationRootLogger
			.getPerformanceLogger();
	/**
	 * Logger for this class.
	 */
	private static final Logger LOGGER = ApplicationRootLogger
			.getLogger(StaffProcessor.class.getName());

	/**
	 * Processes the order related requests of a patient recieved from the
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
		if (OrderStaffConstants.GET_ORDER_SUMMARY_DETAILS.equalsIgnoreCase(action)) {
			this.getOrderSummaryDetails(request, response);
		} else if (OrderStaffConstants.GET_TUBE_SUMMARY.equalsIgnoreCase(action)) {
			this.getTubeStaffSummary(request, response);
		} else if (OrderStaffConstants.GET_ORDER_TEST_DETAILS
				.equalsIgnoreCase(action)) {
			this.getOrderTestDetails(request, response);
		} else if (OrderStaffConstants.SEARCH_REQUISITION.equalsIgnoreCase(action)) {
			this.getReqSearchDetails(request, response);
		} else if (OrderStaffConstants.PAGINATE_ORDER_SUMMARY.equalsIgnoreCase(action)) {
			this.paginateOrderSummary(request, response);
		} else if("getOrderSum".equalsIgnoreCase(action)){
			this.getOrderSum(request, response);
		}
	}

	/**
	 * Gets the order summary for the staff.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getOrderSummaryDetails(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderStaffProcessor.getOrderSummaryDetails");
		request.getSession().removeAttribute("orderStaffSummaryInfo");
		OrderStaffService orderStaffService = new OrderStaffServiceImpl();
		 StaffService  staffService = new  StaffServiceImpl();
		
		Staff staffObj = new Staff();

		long facilityId = 0l;
		long spectraMRN = 0l;
		Date drawDate = null;
		int dateRange = 12;  
		SimpleDateFormat simpleDateFmt = SimpleDateFormatUtil
				.getSimpleDateFormatLocale("MM/dd/yyyy");
		OrderStaffSummary orderStaffSumm = new OrderStaffSummary();
		orderStaffSumm.setStartIndex(1);
		orderStaffSumm.setEndIndex(1000);
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
			//Substract days to current date.
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -dateRange);
			String strDrawDate = simpleDateFmt.format(cal.getTime());

			try {
				drawDate = simpleDateFmt.parse(strDrawDate);
//				drawDate = simpleDateFmt.parse("11/01/2013");

			} catch (ParseException e) {
				e.printStackTrace();
			}
			staffObj = staffService.getStaffDetails(facilityId, spectraMRN);
		 
			List<OrderStaffSummary> orderStaffSummaryLst = orderStaffService.getOrderStaffSummary(
					facilityId, spectraMRN, drawDate, orderStaffSumm);
			request.getSession().setAttribute("orderStaffSummaryInfo", staffObj);
			request.getSession().setAttribute(
					OrderStaffConstants.ORDER_SUMMARY_DETAILS, orderStaffSummaryLst);
			request.getSession().setAttribute(
					"orderSearchParameters", orderStaffSumm);
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
	private void getTubeStaffSummary(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.getTubeSummary");
		List<Accession> lstTubeSummary = null;
		OrderStaffService orderStaffService = new OrderStaffServiceImpl();
		String requisitionNo = null;
		List<String> jsonStrArray = new ArrayList<String>();
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("requisitionNo")))) {
			requisitionNo = (String) request.getParameter("requisitionNo");
		}
		try {
			lstTubeSummary = orderStaffService
					.getStaffTubeSummary(requisitionNo);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		if (null != lstTubeSummary && !lstTubeSummary.isEmpty()) {
			jsonStrArray = this.generateTubeSummaryJSON(lstTubeSummary);
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
		PERF_LOGGER.info("Start of OrderProcessor.getOrderReqDetails");
		List<Results> lstOrderTestDetails = null;
		OrderStaffService orderStaffService = new OrderStaffServiceImpl();
		String requisitionNo = null;
		List<String> jsonStrArray = new ArrayList<String>();
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("requisitionNo")))) {
			requisitionNo = (String) request.getParameter("requisitionNo");
		}
		try {
			lstOrderTestDetails = orderStaffService
					.getOrderStaffTestDetails(requisitionNo);
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
		PERF_LOGGER.info("End of OrderProcessor.getOrderReqDetails");
	}
	
	private List<String> generateOrderSummaryJSON(
			List<OrderStaffSummary> orderStaffSummaryLst) {
		PERF_LOGGER
				.info("Start of OrderProcessor.generateOrderSummaryJSON");
		final List<HashMap> orderMap = new ArrayList<HashMap>();
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				CommonConstants.SIMPLE_DATE_FORMAT);
		for (OrderStaffSummary orderStaffSummary : orderStaffSummaryLst) {
			final HashMap<String, Object> orderStaffSummaryMap = new HashMap<String, Object>();
			orderStaffSummaryMap.put("drawDate",
					dateFormat.format(orderStaffSummary.getDrawDate()));
			orderStaffSummaryMap.put("requisitionNo", orderStaffSummary.getRequisitionNum());
			orderStaffSummaryMap.put("testsCount", orderStaffSummary.getNumOfTests());
			orderStaffSummaryMap.put("frequency", orderStaffSummary.getFrequency());
			orderStaffSummaryMap.put("status", orderStaffSummary.getStatus());
			orderStaffSummaryMap.put("abnormalFlag", orderStaffSummary.getAbnormalFlag());
			orderStaffSummaryMap.put("NumOfTubesNotReceived", orderStaffSummary.getNumOfTubesNotReceiveds());
			orderStaffSummaryMap.put("cancelledTestIndicator", orderStaffSummary.isCancelledTestIndicator());
			
			StringBuffer fac= new StringBuffer("");
			if (!CommonConstants.EMPTY_STRING.equals(StringUtil
				.valueOf( orderStaffSummary.getFacility().getCorporateAcronym() ))) {
				
				fac.append(orderStaffSummary.getFacility().getCorporateAcronym()).append(" - ");
			}
			fac.append(orderStaffSummary.getFacility().getFacilityName());			
			orderStaffSummaryMap.put("facility", fac.toString());
			fac = null;
			
			orderMap.add(orderStaffSummaryMap);
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
		PERF_LOGGER.info("Start of OrderStaffProcessor.getReqSearchDetails");
		request.getSession().removeAttribute("staffDet");
		Search searchCriteria = new Search();
		OrderStaffService orderStaffService = new OrderStaffServiceImpl();
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
		searchCriteria = new Search(searchType, searchValue, 0L, null, null);
		searchCriteria.setSearchType(searchType);
		request.getSession().setAttribute("searchType", searchType);
		request.getSession().setAttribute("searchCriteria", searchCriteria);
		request.getSession().setAttribute(OrderStaffConstants.REQUISITION_NO,
				searchValue); 
		if(!isOrderSumPage){
			request.getSession().removeAttribute(CommonConstants.SEARCH_PARAMETERS);
			request.getSession().setAttribute(CommonConstants.SEARCH_PARAMETERS,
					searchCriteria);
		}
		try {
			StaffRequisitionDetails staffDet = orderStaffService.getPatientStaffReqDetails("",
					searchValue);
			request.getSession().setAttribute("staffDet", staffDet);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderStaffProcessor.getReqSearchDetails");
	}
	
	/**
	 * Method to generate order test details JSON.
	 * 
	 * @param lstOrderTestDetails
	 *            - Holds the list of test details.
	 * @return - List<String>.
	 */
	private List<String> generateOrderTestDetailsJSON(
			List<Results> lstOrderTestDetails) {
		PERF_LOGGER.info("Start of OrderStaffProcessor.generateOrderSummaryJSON");
		String prev = null;
		String curr = null;
		final List<HashMap> lstOrderMap = new ArrayList<HashMap>();
		for (Results orderTestDetail : lstOrderTestDetails) {
			HashMap<String, Object> orderStaffSummaryMap =
					new HashMap<String, Object>();
			curr = orderTestDetail.getAccession();
			if (null != curr && !curr.equalsIgnoreCase(prev)) {
				orderStaffSummaryMap.put("test", orderTestDetail.getAccession());
				lstOrderMap.add(orderStaffSummaryMap);
				orderStaffSummaryMap.put("requisitionHeader", CommonConstants.TRUE);
			}
			orderStaffSummaryMap =
					new HashMap<String, Object>();
			orderStaffSummaryMap.put("requisitionHeader", CommonConstants.FALSE);
			orderStaffSummaryMap.put("test", orderTestDetail.getResultTestName());
			orderStaffSummaryMap.put("result", orderTestDetail.getTextualResult());
			orderStaffSummaryMap.put("units", orderTestDetail.getUOM());
			orderStaffSummaryMap.put("reference", orderTestDetail
					.getReferenceRange());
			String comments = CommonConstants.EMPTY_STRING;
			if (null != orderTestDetail.getResultComment()) {
				comments = orderTestDetail.getResultComment();
				comments = comments.replaceAll("\\n|\\r",
						CommonConstants.EMPTY_STRING);
			}
			orderStaffSummaryMap.put("comments", comments);
			String indicators = CommonConstants.EMPTY_STRING;
			if("H".equalsIgnoreCase(orderTestDetail.getIndicator())){
				indicators = "High";
			}else if("L".equalsIgnoreCase(orderTestDetail
					.getIndicator())){
				indicators = "Low";
			}
			orderStaffSummaryMap.put("indicators", indicators);
			orderStaffSummaryMap.put("orderStatus", orderTestDetail
					.getOrderStatus());
			lstOrderMap.add(orderStaffSummaryMap);
			prev = curr;
		}
		PERF_LOGGER.info("End of OrderStaffProcessor.generateOrderSummaryJSON");
		return ConstructJSONFormat.generateJsonFormat(lstOrderMap);
	}
	
	/**
	 * Method to generate order test details JSON.
	 * 
	 * @param lstOrderTestDetails
	 *            - Holds the list of test details.
	 * @return - List<String>.
	 */
	private List<String> generateTubeSummaryJSON(
			List<Accession> lstTubeSummary) {
		PERF_LOGGER.info("Start of OrderStaffProcessor.generateOrderSummaryJSON");
		final List<HashMap> lstTubeSummaryMap = new ArrayList<HashMap>();
		String strReceivedDate = CommonConstants.EMPTY_STRING;
		SimpleDateFormat simpleDateFormat = SimpleDateFormatUtil
				.getSimpleDateFormatLocale(CommonConstants.
						DATE_FORMAT_WITH_TIME);
		for (Accession tubeSummary : lstTubeSummary) {
			final HashMap<String, Object> tubeSummaryMap =
					new HashMap<String, Object>();
			tubeSummaryMap.put("accession", tubeSummary.getAccession());
			tubeSummaryMap.put("tubetype", tubeSummary.getTubetype());
			tubeSummaryMap.put("specimen", tubeSummary.getSpecimen());
			tubeSummaryMap.put("labFk", tubeSummary.getLabfk());
			if (null != tubeSummary.getReceivedDate()) {
				strReceivedDate = simpleDateFormat.format(tubeSummary
						.getReceivedDate());
			}
			tubeSummaryMap.put("recievedDetails", strReceivedDate);
			tubeSummaryMap.put("condition", tubeSummary.getCondition());
			tubeSummaryMap.put("status", tubeSummary.getStatus());
			lstTubeSummaryMap.add(tubeSummaryMap);
		}
		PERF_LOGGER.info("End of OrderStaffProcessor.generateOrderSummaryJSON");
		return ConstructJSONFormat.generateJsonFormat(lstTubeSummaryMap);
	}
	private void paginateOrderSummary(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.paginateOrderSummary");
		final String jsonRoot = CommonConstants.JSON_ROOT_LIST_INFO;
		final String start = request.getParameter(CommonConstants.START);
		final String strRows = request.getParameter(CommonConstants.LIMIT);
		final int rowsPerPage = Integer.parseInt(strRows);
		int listSize = 0;
		List<OrderStaffSummary> orderSummLst = (List<OrderStaffSummary>)
				request.getSession().getAttribute(
				OrderStaffConstants.ORDER_SUMMARY_DETAILS);
		if (null != request.getSession().getAttribute(
				CommonConstants.START_INDEX)) {
			// used for paginating & sort the patients.
			orderSummLst = this.paginateAndSortOrderStaffSumm(request, response);
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
		PERF_LOGGER.info("End of OrderStaffProcessor.paginateOrderSummary");
	}
	
	/**
	 * Gets the order details for the requisition number.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getOrderSum(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderStaffProcessor."
				+ "getOrderStaffSum");
	
		request.getSession().removeAttribute("orderStaffSummaryInfo");
		StaffService staffService = new StaffServiceImpl();
		Staff staffObj = new Staff();

		long facilityId = 0l;
		long spectraMRN = 0l;
		try {
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(request.getParameter("facilityId")))) {
				facilityId = Long.valueOf(request.getParameter("facilityId"));
			}
			if (!CommonConstants.TRUE.equalsIgnoreCase(StringUtil
					.valueOf(request.getAttribute("isStaffSrhFlow")))
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
			staffObj = staffService.getStaffDetails(facilityId, spectraMRN);
			request.getSession().setAttribute("orderStaffSummaryInfo", staffObj);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderProcessor.getOrderStaffSum");
	}
		
}
