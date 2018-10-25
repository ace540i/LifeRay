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
import com.spectra.symfonielabs.constants.EquipmentConstants;
import com.spectra.symfonielabs.constants.ScreenConstants;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.Equipment;
import com.spectra.symfonielabs.domainobject.OrderEquipmentSummary;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;
import com.spectra.symfonielabs.domainobject.Search;
import com.spectra.symfonielabs.service.EquipmentService;
import com.spectra.symfonielabs.service.EquipmentServiceImpl;





import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
//import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class processes all the requests from the client side related to
 * patients.
 *
 */
public class EquipmentProcessor extends SearchUtilityProcessor implements Processor {
	      
	/**
	 * Performance Logger for this class.
	 */
	private static final Logger PERF_LOGGER = ApplicationRootLogger
			.getPerformanceLogger();

	/**
	 * Logger for this class.
	 */
	private static final Logger LOGGER = ApplicationRootLogger
			.getLogger(EquipmentProcessor.class.getName());

	/**
	 * Processes the patient related requests recieved from the screens.
	 *
	 * @param action
	 * @param request
	 * @param response
	 */
	public final void process(final HttpServletRequest request,	final HttpServletResponse response, final String action) {
		
		// // System.out.println("## 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4#");
	    // // System.out.println("@@ EquipmentProcessor. <process> : action:  "+action);		
	    // // System.out.println("@@ EquipmentProcessor. <process> : searchType:  "+request.getParameter("searchType"));
	    // // System.out.println("@@ EquipmentProcessor. <process> : searchValue:  "+request.getParameter("searchValue"));
	    // // System.out.println("@@ EquipmentProcessor. <process> : processorName:  "+request.getParameter("processorName"));
	    // // System.out.println("@@ EquipmentProcessor. <process> : processorAction:  "+request.getParameter("processorAction"));
		// // System.out.println("## 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4#");
				
		//		## 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5#
		//		@@ EquipmentProcessor. <process> : searchType:  Equipment
		//		@@ EquipmentProcessor. <process> : searchValue:  null
		//		@@ EquipmentProcessor. <process> : processorName:  EquipmentProcessor
		//		@@ EquipmentProcessor. <process> : processorAction:  getSearchResult
		//		@@ EquipmentProcessor. <process> : action:  getSearchResult
		//		## 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5#
				
		//	Enumeration params = request.getParameterNames(); 
		//	while(params.hasMoreElements()){
		//	 String paramName = (String)params.nextElement();
		//	 // // System.out.println("@@@@@@@@   #-> Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		//	}	
		
		
		if (action != null) {
			
			if (EquipmentConstants.GET_SEARCH_RESULTS.equalsIgnoreCase(action)) {				// <========================= ! (1)
				
				this.getSearchResult(request, response);
			} 
			else if (EquipmentConstants.GET_EQUIPMENT_RESULTS.equalsIgnoreCase(action)) {	
				this.getEquipmentDetails(request, response); 
			} 
			else if (EquipmentConstants.GET_EQUIPMENT_REQ_DET.equalsIgnoreCase(action)) {	
				this.getEquipmentReqInfo(request, response);
			} 
			else if (EquipmentConstants.PAGINATE_EQUIPMENT_RESULTS.equalsIgnoreCase(action)) { 
				this.paginateEquipmentList(request, response);
			}
			else if (EquipmentConstants.SEARCH_REQUISITION.equalsIgnoreCase(action)) {			// <================ New  (5)  searchRequisition (N-O-T  U-S-E-D!!!)
			    this.getReqSearchDetails(request, response);
			}
			else if("paginateOrderSummary".equalsIgnoreCase(action)){                   		// <================ New  (4)   
				this.paginateOrderSummary(request, response);
			}			
			else if (EquipmentConstants.GET_ORDER_SUMMARY_DETAILS.equalsIgnoreCase(action)) {  	// <================ New  (3)   getOrderSummaryDetails ?
				this.getOrderSummaryDetails(request, response);			
			}
			else if("getOrderSum".equalsIgnoreCase(action)){                   					// <================ New  (2)   
				this.getOrderSum(request, response);
			}
			//			else if (OrderConstants.PAGINATE_ORDER_SUMMARY.equalsIgnoreCase(action)) {
			//				this.paginateOrderSummary(request, response);
			//			}
		}
	}

	/**
	 * Gets the order details for the requisition number.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getReqSearchDetails(final HttpServletRequest request, final HttpServletResponse response) {
		PERF_LOGGER.info("Start of EquipmentProcessor.getReqSearchDetails");
		
	    // // System.out.println("@@ EquipmentProcessor. <getReqSearchDetails> :  <=========================== ");
	    
		request.getSession().removeAttribute("patientDet");
		Search searchCriteria = new Search();
		EquipmentService orderService = new EquipmentServiceImpl();
		String searchType = null;
		String searchValue = null;
		boolean isOrderSumPage = false;
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("searchType")))) {
			searchType = (String) request.getParameter("searchType");
		}
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("reqNumber")))) {
			searchValue = (String) request.getParameter("reqNumber");
		}
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("reqNumber")))) {
			isOrderSumPage = Boolean.valueOf(request.getParameter("isOrderSumPage"));
		}
		// // System.out.println("@@ EquipmentProcessor. <getReqSearchDetails> : searchType, searchValue, isOrderSumPage = "+searchType+", "+ searchValue+", "+isOrderSumPage);
		
		searchCriteria = new Search(searchType, searchValue, 0L, null, null);
		searchCriteria.setSearchType(searchType);
		request.getSession().setAttribute("searchType", searchType);
		request.getSession().setAttribute("searchCriteria", searchCriteria);
		request.getSession().setAttribute(EquipmentConstants.REQUISITION_NO, searchValue); 
		if(!isOrderSumPage){
			request.getSession().removeAttribute(CommonConstants.SEARCH_PARAMETERS);
			request.getSession().setAttribute(CommonConstants.SEARCH_PARAMETERS, searchCriteria);
		}
		// // System.out.println("@@ EquipmentProcessor. <getReqSearchDetails> :  GOING INTO [Try.....] ");

		try {
			RequisitionDetails patientDet = orderService.getPatientReqDetails("", searchValue);
			request.getSession().setAttribute("patientDet", patientDet);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of EquipmentProcessor.getReqSearchDetails");
	}
	
	/**
	 * Method to generate order test details JSON.
	 * 
	 * @param lstOrderTestDetails
	 *            - Holds the list of test details.
	 * @return - List<String>.
	 */
	private List<String> generateOrderTestDetailsJSON(List<Results> lstOrderTestDetails) {
		PERF_LOGGER.info("Start of OrderProcessor.generateOrderSummaryJSON");
		String prev = null;
		String curr = null;
		final List<HashMap> lstOrderMap = new ArrayList<HashMap>();
		for (Results orderTestDetail : lstOrderTestDetails) {
			HashMap<String, Object> orderSummaryMap = new HashMap<String, Object>();
			curr = orderTestDetail.getAccession();
			if (null != curr && !curr.equalsIgnoreCase(prev)) {
				orderSummaryMap.put("test", orderTestDetail.getAccession());
				lstOrderMap.add(orderSummaryMap);
				orderSummaryMap.put("requisitionHeader", CommonConstants.TRUE);
			}
			orderSummaryMap = new HashMap<String, Object>();
			orderSummaryMap.put("requisitionHeader", CommonConstants.FALSE);
			orderSummaryMap.put("test", orderTestDetail.getResultTestName());
			orderSummaryMap.put("result", orderTestDetail.getTextualResult());
			orderSummaryMap.put("units", orderTestDetail.getUOM());
			orderSummaryMap.put("reference", orderTestDetail.getReferenceRange());
			String comments = CommonConstants.EMPTY_STRING;
			if (null != orderTestDetail.getResultComment()) {
				comments = orderTestDetail.getResultComment();
				comments = comments.replaceAll("\\n|\\r", CommonConstants.EMPTY_STRING);
			}
			orderSummaryMap.put("comments", comments);
			String indicators = CommonConstants.EMPTY_STRING;
			if("H".equalsIgnoreCase(orderTestDetail.getIndicator())){
				indicators = "High";
			}else if("L".equalsIgnoreCase(orderTestDetail
					.getIndicator())){
				indicators = "Low";
			}
			orderSummaryMap.put("indicators", indicators);
			orderSummaryMap.put("orderStatus", orderTestDetail
					.getOrderStatus());
			lstOrderMap.add(orderSummaryMap);
			prev = curr;
		}
		PERF_LOGGER.info("End of OrderProcessor.generateOrderSummaryJSON");
		return ConstructJSONFormat.generateJsonFormat(lstOrderMap);
	}
	
	/**
	 * Method to generate order test details JSON.
	 * 
	 * @param lstOrderTestDetails
	 *            - Holds the list of test details.
	 * @return - List<String>.
	 */
	private List<String> generateTubeSummaryJSON(List<Accession> lstTubeSummary) {
		PERF_LOGGER.info("Start of EquipmentProcessor.generateOrderSummaryJSON");
		final List<HashMap> lstTubeSummaryMap = new ArrayList<HashMap>();
		String strReceivedDate = CommonConstants.EMPTY_STRING;
		SimpleDateFormat simpleDateFormat = SimpleDateFormatUtil.getSimpleDateFormatLocale(CommonConstants.DATE_FORMAT_WITH_TIME);
		for (Accession tubeSummary : lstTubeSummary) {
			final HashMap<String, Object> tubeSummaryMap = new HashMap<String, Object>();
			tubeSummaryMap.put("accession", tubeSummary.getAccession());
			tubeSummaryMap.put("tubetype", tubeSummary.getTubetype());
			tubeSummaryMap.put("specimen", tubeSummary.getSpecimen());
			tubeSummaryMap.put("labFk", tubeSummary.getLabfk());
			if (null != tubeSummary.getReceivedDate()) {
				strReceivedDate = simpleDateFormat.format(tubeSummary.getReceivedDate());
			}
			tubeSummaryMap.put("recievedDetails", strReceivedDate);
			tubeSummaryMap.put("condition", tubeSummary.getCondition());
			tubeSummaryMap.put("status", tubeSummary.getStatus());
			lstTubeSummaryMap.add(tubeSummaryMap);
		}
		PERF_LOGGER.info("End of OrderProcessor.generateOrderSummaryJSON");
		return ConstructJSONFormat.generateJsonFormat(lstTubeSummaryMap);
	}
	
	
	private void paginateOrderSummary(final HttpServletRequest request,  final HttpServletResponse response) {
		PERF_LOGGER.info("Start of OrderProcessor.paginateOrderSummary");
		final String jsonRoot = CommonConstants.JSON_ROOT_LIST_INFO;
		final String start = request.getParameter(CommonConstants.START);
		final String strRows = request.getParameter(CommonConstants.LIMIT);
		final int rowsPerPage = Integer.parseInt(strRows);
		int listSize = 0;
		List<OrderEquipmentSummary> orderSummLst = (List<OrderEquipmentSummary>) request.getSession().getAttribute(EquipmentConstants.ORDER_SUMMARY_DETAILS);
		if (null != request.getSession().getAttribute(
				CommonConstants.START_INDEX)) {
			// used for paginating & sort the patients.
			orderSummLst = this.paginateAndSortEquipmentSumm(request, response);
		}
		if (null != orderSummLst && !orderSummLst.isEmpty()) {
			// Generate JSON array of patient search results for paging.
			final List<String> jsonStrArray = this.generateOrderSummaryJSON(orderSummLst);
			response.setContentType(ScreenConstants.JSON_RESPONSE_TYPE);
			response.setHeader(CommonConstants.CACHE_CONTROL, CommonConstants.NO_CACHE);
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
	
	private List<String> generateOrderSummaryJSON(List<OrderEquipmentSummary> orderSummaryLst) {
		PERF_LOGGER
				.info("Start of OrderProcessor.generateOrderSummaryJSON");
		final List<HashMap> orderMap = new ArrayList<HashMap>();
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				CommonConstants.SIMPLE_DATE_FORMAT);
		for (OrderEquipmentSummary orderSummary : orderSummaryLst) {
			final HashMap<String, Object> orderSummaryMap = new HashMap<String, Object>();
			orderSummaryMap.put("drawDate",	dateFormat.format(orderSummary.getDrawDate()));
			orderSummaryMap.put("requisitionNo", orderSummary.getRequisitionNum());
			orderSummaryMap.put("testsCount", orderSummary.getNumOfTests());
			orderSummaryMap.put("frequency", orderSummary.getFrequency());
			orderSummaryMap.put("status", orderSummary.getStatus());
			orderSummaryMap.put("abnormalFlag", orderSummary.getAbnormalFlag());
			orderSummaryMap.put("NumOfTubesNotReceived", orderSummary.getNumOfTubesNotReceived());
			orderSummaryMap.put("cancelledTestIndicator", orderSummary.isCancelledTestIndicator());			
			orderSummaryMap.put("specimenMethodDesc", orderSummary.getSpecimenMethodDesc());		
			orderMap.add(orderSummaryMap);
		}
		PERF_LOGGER.info("End of OrderProcessor.generateOrderSummaryJSON");
		return ConstructJSONFormat.generateJsonFormat(orderMap);
	}


		/**
		 * Gets the order summary for the patient.
		 * 
		 * @param request
		 *            - Holds the HttpServletRequest.
		 * @param response
		 *            - Holds the HttpServletResponse.
		 */
		private void getOrderSummaryDetails(final HttpServletRequest request, final HttpServletResponse response) { 
			
		    // // System.out.println("YY EquipmentProcessor. <getOrderSummaryDetails> : processorName:  "+request.getParameter("processorName"));	    
		    // // System.out.println("YY EquipmentProcessor. <getOrderSummaryDetails> : processorAction:  "+request.getParameter("processorAction"));
		    // // System.out.println("aaaaaaaaaaaaaaaaaaaaa facilityId = "+ Long.valueOf(request.getParameter("facilityId")) );
		    
			//	Enumeration params = request.getParameterNames(); 
			//	while(params.hasMoreElements()){
			//	 String paramName = (String)params.nextElement();
			//	 // // System.out.println("@@@@@@@@   #-> Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
			//	}	
	
			PERF_LOGGER.info("Start of EquipmentProcessor.getOrderSummaryDetails");
			request.getSession().removeAttribute("orderSummaryInfo");
			
			//OrderService orderService = new OrderServiceImpl();
			//PatientService patientService = new PatientServiceImpl();
			//Patient patientObj = new Patient();
			
			EquipmentService equipmentService = new EquipmentServiceImpl();              // <<=========================
			Equipment equipmentObj = new Equipment();
			
			long facilityId = 0l;
			long spectraMRN = 0l;
			String STRINGspectraMRN ="";
			Date drawDate = null;
			
			int dateRange = 3;
			//int dateRange = 12;	    // 12 Months																		// <========= drawDate 
			SimpleDateFormat simpleDateFmt = SimpleDateFormatUtil.getSimpleDateFormatLocale("MM/dd/yyyy");

			
			OrderEquipmentSummary orderSumm = new OrderEquipmentSummary();
			orderSumm.setStartIndex(1);
			orderSumm.setEndIndex(1000);
 			
			try {
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(request.getParameter("facilityId")))) {
					facilityId = Long.valueOf(request.getParameter("facilityId"));
				}
				
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("spectraMRN")))) {
					STRINGspectraMRN = (request.getParameter("spectraMRN"));
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
				cal.add(Calendar.MONTH, -dateRange);									// <=============== var dateRange;  
				String strDrawDate = simpleDateFmt.format(cal.getTime());
				try {
					drawDate = simpleDateFmt.parse(strDrawDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			    // // System.out.println("YY EquipmentProcessor. <getOrderSummaryDetails> : drawDate:  "+drawDate);
			    
				//patientObj = patientService.getPatientDetails(facilityId, spectraMRN);
				//List<OrderSummary> orderSummaryLst = orderService.getOrderSummary(facilityId, spectraMRN, drawDate, orderSumm);
				
			    // // System.out.println("YY EquipmentProcessor. <getOrderSum> : GOING --IN-- equipmentService.getEquipmentDetails  ");	    
			    // // System.out.println("YY EquipmentProcessor. <getOrderSum> : facilityId, spectraMRN:  "+facilityId +", " + spectraMRN);	    
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//patientObj = patientService.getPatientDetails(facilityId, spectraMRN);
				
				equipmentObj = equipmentService.getEquipmentDetails(facilityId, STRINGspectraMRN);     // <============================================
				List<OrderEquipmentSummary> orderSummaryLst = equipmentService.getOrderSummary(facilityId, spectraMRN, drawDate, orderSumm);
				

				//List<OrderSummary> orderSummaryLst = orderService.getOrderSummary(facilityId, spectraMRN, drawDate, orderSumm);
			    // // System.out.println("YY EquipmentProcessor. <getOrderSummary> : GOING --IN-- equipmentService.getOrderSummary  ");	    
				// List<OrderEquipmentSummary> orderSummaryLst = equipmentService.getOrderSummary(facilityId, spectraMRN, drawDate, orderSumm);
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			    // // System.out.println("YY EquipmentProcessor. <getOrderSummary> : COMING --OUT-- equipmentService.getOrderSummary  ");	    
			    // // System.out.println("YY EquipmentProcessor. <orderSummaryLst> : orderSummaryInfo :  "+equipmentObj.toString());	 				
			    // // System.out.println("YY EquipmentProcessor. <orderSummaryLst> : orderSummaryLst :  "+orderSummaryLst.toString());	 				

				//request.getSession().setAttribute("orderSummaryInfo", equipmentObj);

				request.getSession().setAttribute("orderSummaryInfo", equipmentObj);
				request.getSession().setAttribute(EquipmentConstants.ORDER_SUMMARY_DETAILS, orderSummaryLst);   // <====== orderSummaryLst
				request.getSession().setAttribute(
						"orderSearchParameters", orderSumm);
	 			
			} catch (BusinessException businessException) {
				final StringWriter stringWriter = new StringWriter();
				businessException.printStackTrace(new PrintWriter(stringWriter));
				LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
						+ stringWriter.toString());
			}
		    // // System.out.println("YY EquipmentProcessor. <orderSummaryLst> : DOOOOOOOOOOOOONNNNNEEEEEEEEEEEEE :  ");	 

			PERF_LOGGER.info("End of OrderProcessor.getOrderSummaryDetails");
		}
		
	
	/**
	 * Gets the order details for the requisition number.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getOrderSum(final HttpServletRequest request, final HttpServletResponse response) {
		
	    // // System.out.println("XX EquipmentProcessor. <getOrderSum> : processorName:  "+request.getParameter("processorName"));	    
	    // // System.out.println("XX EquipmentProcessor. <getOrderSum> : processorAction:  "+request.getParameter("processorAction"));	    
	 	
		//PERF_LOGGER.info("Start of OrderProcessor.getOrderSum");
	    
		request.getSession().removeAttribute("equipmentSummaryInfo");

		EquipmentService equipmentService = new EquipmentServiceImpl();              // <<=========================
		Equipment equipmentObj = new Equipment();
		
		//PatientService patientService = new PatientServiceImpl();                  // <<=========================
		//Patient patientObj = new Patient();
		
		long facilityId = 0l;
		//long spectraMRN = 0l;
		String spectraMRN ="";
		try {
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("facilityId")))) {
				facilityId = Long.valueOf(request.getParameter("facilityId"));
			}
			
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("spectraMRN")))) {
				spectraMRN = (request.getParameter("spectraMRN"));
			}
			
			//	if (!CommonConstants.TRUE.equalsIgnoreCase(StringUtil.valueOf(request.getAttribute("isPatientSrhFlow")))
			//			&& !CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("spectraMRN")))) {
			//		spectraMRN = Long.valueOf(request.getParameter("spectraMRN"));
			//		
			//	} else if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getAttribute("spectraMRN")))) {
			//		spectraMRN = Long.valueOf(String.valueOf(request.getAttribute("spectraMRN")));
			//	}
			
		    // // System.out.println("XX EquipmentProcessor. <getOrderSum> : GOING --IN-- equipmentService.getEquipmentDetails  ");	    
		    // // System.out.println("XX EquipmentProcessor. <getOrderSum> : facilityId, spectraMRN:  "+facilityId +", " + spectraMRN);	    
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// equipmentObj = equipmentService.getPatientDetails(facilityId, spectraMRN);     // <============================================
			equipmentObj = equipmentService.getEquipmentDetails(facilityId, spectraMRN);     // <============================================
			request.getSession().setAttribute("equipmentSummaryInfo", equipmentObj);
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    // // System.out.println("XX EquipmentProcessor. <getOrderSum> : COMING --OUT-- equipmentService.getEquipmentDetails  ");	    
		    // // System.out.println("XX EquipmentProcessor. <getOrderSum> : equipmentSummaryInfo :  "+equipmentObj);	    
			
			//x		patientObj = patientService.getPatientDetails(facilityId, spectraMRN);
			//		List<OrderSummary> orderSummaryLst = orderService.getOrderSummary(facilityId, spectraMRN, drawDate, orderSumm);
			//		request.getSession().setAttribute("orderSummaryInfo", patientObj);
			//		request.getSession().setAttribute(
			//		OrderConstants.ORDER_SUMMARY_DETAILS, orderSummaryLst);
			
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of OrderProcessor.getOrderSum");
	}	
	
	
	/**
	 * Gets the search results for the patient.
	 * 
	 * @param request
	 * @param response
	 */
	private void getSearchResult(final HttpServletRequest request,final HttpServletResponse response) {
		
		// // System.out.println("## 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6#");
	    // // System.out.println("@@ EquipmentProcessor. <getSearchResult> : searchType:  "+request.getParameter("searchType"));
	    // // System.out.println("@@ EquipmentProcessor. <getSearchResult> : searchValue:  "+request.getParameter("searchValue"));
	    // // System.out.println("@@ EquipmentProcessor. <getSearchResult> : processorName:  "+request.getParameter("processorName"));
	    // // System.out.println("@@ EquipmentProcessor. <getSearchResult> : processorAction:  "+request.getParameter("processorAction"));
		// // System.out.println("## 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6#");
		
		PERF_LOGGER.info("Start of EquipmentProcessor.getSearchResult");
		request.getSession().removeAttribute(CommonConstants.SEARCH_RESULTS);
		request.getSession().removeAttribute(CommonConstants.SEARCH_PARAMETERS);
		request.getSession().removeAttribute("spectraMRN");
		request.getSession().removeAttribute("facilityId");	
		request.getSession().removeAttribute("dateRange");
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		// timc 12/18/2015  GOT IT............ 		
		
//		request.getSession().removeAttribute(CommonConstants.START_INDEX);
		
		// @@ EquipmentProcessor. <paginateEquipmentList> ??? START_INDEX : null   GOOD.........!!
		//request.getSession().setAttribute(CommonConstants.START_INDEX, "");
		// @@ EquipmentProcessor. <paginateEquipmentList> ??? START_INDEX : 1      BAD..........!!	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		


		EquipmentService equipmentService = new EquipmentServiceImpl();			// <---------------------------------------
		List<Equipment> searchRes = new ArrayList<Equipment>();					// <---------------------------------------
		
		
		long facilityId = 01;
		String facilityName = CommonConstants.EMPTY_STRING;
		String patientName = CommonConstants.EMPTY_STRING;
		int listSize = 0;
		
		// // System.out.println("OOOOOOOOOOOOOOO ==  @@ EquipmentProcessor. <getSearchResult> : facilityId:  "+ Long.valueOf(request.getParameter("facilityId")).toString() );
		
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("facilityId")))) {
			facilityId = Long.valueOf(request.getParameter("facilityId"));
		}
		
		//		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("facilityId")))) {
		//			facilityId = Long.valueOf((String) request.getParameter("facilityId"));
		//		}
		//		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("patientName")))) {
		//			patientName = (String) request.getParameter("patientName");
		//		}
		
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("eqpNumber")))) {
			patientName = (String) request.getParameter("eqpNumber");
		}
		// // System.out.println("  @@ EquipmentProcessor. <getSearchResult> : patientName:  "+ patientName );

		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("facilityName")))) {
			facilityName = (String) request.getParameter("facilityName");
			
		}
		
		Search searchCriteria = new Search("Equipment", patientName, facilityId, CommonConstants.EMPTY_STRING, facilityName);
		String searchType = null;
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("searchType")))) {
			searchType = (String) request.getParameter("searchType");
			
		}
		
		searchCriteria.setSearchType(searchType);
		request.getSession().setAttribute("searchType",searchType);
		searchCriteria.setStartIndex(1);
		searchCriteria.setEndIndex(1000);
		try {
			searchRes = equipmentService.getSearchResult(searchCriteria);
			if(null != searchRes && !searchRes.isEmpty()){
				listSize = searchRes.size();
				// // System.out.println("  @@ ----------------------------------------------------"+ listSize );
				// // System.out.println("  @@ EquipmentProcessor. <getSearchResult> : listSize :  "+ listSize );
				// // System.out.println("  @@ EquipmentProcessor. <getSearchResult> : listSize :  "+ listSize );
				// // System.out.println("  @@ ----------------------------------------------------"+ listSize );
			}
			
			//	if(1 != listSize) {																								// <<========================= ????
			// // System.out.println("  @@ EquipmentProcessor. <getSearchResult> : (1 != listSize):  "+ patientName );
			// 	request.setAttribute("pageToBeLoadedAttr", "searchResPage");       		// <======  searchEquipmentPage 
			  	request.setAttribute("pageToBeLoadedAttr", "searchEquipmentPage"); 		// <======  searchEquipmentPage
				request.getSession().setAttribute(CommonConstants.SEARCH_RESULTS, searchRes);   
				// System.out.println("request.getSession().setAttribute(CommonConstants.SEARCH_RESULTS "+request.getSession().getAttribute(CommonConstants.SEARCH_RESULTS));
//				request.getSession().setAttribute(CommonConstants.START_INDEX,  // mike 3/13/16 added set start and end index to session.
//		        		1);
//		        request.getSession().setAttribute(CommonConstants.END_INDEX,
//		        		1000);

			//	} else {
			//		
			//		// // System.out.println("  @@ EquipmentProcessor. <getSearchResult> : ELSE --- >>> (1 != listSize):  "+ patientName );
			//		
			//		OrderProcessor orderProcessor = new OrderProcessor();
			//		request.setAttribute("isPatientSrhFlow", true);
			//		if(null != searchRes && !searchRes.isEmpty()){
			//			request.setAttribute("spectraMRN", searchRes.get(0)
			//					.getSpectraMRN());
			//			orderProcessor.process(request, response, "getOrderSum");
			//			request.getSession().setAttribute("spectraMRN",
			//					searchRes.get(0).getSpectraMRN());
			//			request.getSession().setAttribute("facilityId",
			//					searchRes.get(0).getFacilityId());
			//		}
			//		request.setAttribute("pageToBeLoadedAttr", "orderDetailPage");
			//	}

				searchCriteria.setStartIndex(1);
				searchCriteria.setEndIndex(1000);
				request.getSession().setAttribute(
						CommonConstants.SEARCH_PARAMETERS, searchCriteria);
				request.getSession().setAttribute(CommonConstants.START_INDEX,
		        		1);
		        request.getSession().setAttribute(CommonConstants.END_INDEX,
		        		1000);
//			request.getSession().setAttribute(CommonConstants.SEARCH_PARAMETERS, searchCriteria);
				request.getSession().setAttribute(
						CommonConstants.SEARCH_PARAMETERS, searchCriteria);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of PatientProcessor.getSearchResult");
	}

	/**
	 * This method generates json format for patient details.
	 * 
	 * @param searchDetLst
	 *            - List of specfic patient details.
	 * @return Json format String - patient details in json format
	 *         string.
	 */
	private List<String> generateSearchPatJSON(List<Equipment> searchResLst) {
		
		// // System.out.println("## 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4#");
	    // // System.out.println("@@ EquipmentProcessor. <generateSearchPatJSON> : ");
		// // System.out.println("## 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4#");
		
		
		PERF_LOGGER.info("Start of PatientProcessor.generateSearchPatJSON");
		final List<HashMap> patResMap = new ArrayList<HashMap>();
//		SimpleDateFormat simpleDateFormat = SimpleDateFormatUtil.getSimpleDateFormatLocale(CommonConstants.SIMPLE_DATE_FORMAT);
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy/MM/dd");
		String serial_num="";
		// System.out.println("### Equip generateSearchPatJSON ");
		for (Equipment searchRes : searchResLst) {
			final HashMap<String, Object> patRes = new HashMap<String, Object>();
			
			serial_num=searchRes.getSerialNumber();
			if (searchRes.getSerialNumber() != null) {
				//serial_num = searchRes.getSerialNumber().substring(0,1);
				serial_num = searchRes.getSerialNumber().trim();

				serial_num = serial_num.replace("\"", "");
				//serial_num = serial_num.replace("&", "");
				//serial_num = serial_num.replace("*", "");
				// HD Machine 10B-Outlet	178-987*" &	9876819921	Validation Test Corporation	A113830	Validation Test	(800)-433-3773	3934	4
				// // System.out.println("@@ EquipmentProcessor. <generateSearchPatJSON> serialNumber: "+serial_num);

			}else {
				serial_num = "";
			}
			
			patRes.put("fullName", searchRes.getMachineName());
			patRes.put("serialNumber", serial_num);
			//	if (null != searchRes.getDateOfBirth()) {
			//		patRes.put("dateOfBirth",
			//				simpleDateFormat.format(searchRes.getDateOfBirth()));
			//	}
			patRes.put("facilityName", searchRes.getFacilityName());
			patRes.put("facilityId", searchRes.getFacilityNum());
			patRes.put("spectraMRN", searchRes.getSpectraMRN());
			patRes.put("client_name", searchRes.getClientName());
			patRes.put("facility_phone_number", searchRes.getFacilityPhoneNumber());
			patRes.put("facility_fk", searchRes.getFacilityId());
			
			if (null != searchRes.getLastDrawDate()) {
				patRes.put("lastDrawDate",
						simpleDateFormat.format(searchRes.getLastDrawDate()));
			}
			
			patResMap.add(patRes);
			
		   // // System.out.println("@@ EquipmentProcessor. <generateSearchPatJSON> patRes: "+patRes);
		   // // System.out.println("@@ EquipmentProcessor. <generateSearchPatJSON> patResMap: "+patResMap);
		}
	    
		PERF_LOGGER.info("End of PatientProcessor.generateSearchPatJSON");
 
 

		return ConstructJSONFormat.generateJsonFormat(patResMap);
	}


	/**
	 *Paginate results for the patient.
	 * 
	 * @param request
	 * @param response
	 */
	private void paginateEquipmentList(final HttpServletRequest request,final HttpServletResponse response) {
		
		// // System.out.println("## 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4#");
	    // // System.out.println("@@ EquipmentProcessor. <paginateEquipmentList> : ");
		// // System.out.println("## 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4#");
		// System.out.println("*****************paginateEquipmentList " );
//	    String queries = "";
//	    Enumeration e = request.getParameterNames();
//
//	    if ( e.hasMoreElements() )
//	    {
//	        queries += "<h4>Recent Queries</h4><ul>";
//	    }
//
//	    while ( e.hasMoreElements() )
//	    {
//	        String name = (String) e.nextElement();
//	        String value = (String) request.getParameter(name);
//	        // System.out.println(value +" name= " + name );
//	    }
		PERF_LOGGER.info("Start of PatientProcessor.paginatePatientList");
		final String jsonRoot = CommonConstants.JSON_ROOT_LIST_INFO;
		
		final String start = request.getParameter(CommonConstants.START);
		// System.out.println("START = "+start);
		//		final int st = Math.abs(Integer.valueOf(request.getParameter(CommonConstants.START)));
		//		final String start = Integer.toString(st);

		final String strRows = request.getParameter(CommonConstants.LIMIT);
		final int rowsPerPage = Integer.parseInt(strRows);
		int listSize = 0;
		// System.out.println("jsonRoot= "+jsonRoot);
		// System.out.println("start= "+start);
		// System.out.println("strRows= "+strRows);
		// System.out.println("rowsPerPage= "+rowsPerPage);
	
		// Gets the list of patient search results in a session.
		List<Equipment> lstEquipmentResults = (List<Equipment>) request.getSession().getAttribute(CommonConstants.SEARCH_RESULTS);		
		
		//	List<OrderSummary> orderSummLst = (List<OrderSummary>) request.getSession().getAttribute(OrderConstants.ORDER_SUMMARY_DETAILS);
	    // // System.out.println("@@ EquipmentProcessor. <paginateEquipmentList> ??? START : "+request.getParameter(CommonConstants.START));
	    // // System.out.println("@@ EquipmentProcessor. <paginateEquipmentList> ??? : "+request.getSession().getAttribute(CommonConstants.SEARCH_RESULTS));
	    // // System.out.println("@@ EquipmentProcessor. <paginateEquipmentList> ??? START_INDEX : "+request.getSession().getAttribute(CommonConstants.START_INDEX));
	    // // System.out.println("@@ EquipmentProcessor. <paginateEquipmentList> ??? start, strRows, rowsPerPage : "+start+","+strRows+", "  +rowsPerPage);

	    
		// @@ EquipmentProcessor. <paginateEquipmentList> ??? START_INDEX : null   GOOD.........!!
		// @@ EquipmentProcessor. <paginateEquipmentList> ??? START_INDEX : 1      BAD..........!!

		 // System.out.println("session.start_index ===== "+request.getSession().getAttribute(
			//		CommonConstants.START_INDEX));
		 // System.out.println("session.end_index ===== "+request.getSession().getAttribute(
			//		CommonConstants.END_INDEX));
				if (null != request.getSession().getAttribute(CommonConstants.START_INDEX)) {
					// used for paginating & sort the patients.
					lstEquipmentResults = this.paginateAndSortEquipment(request, response);
				}
//					else {																// Mike -3/12/16 ** added else to set start_index so on sort it will call paginageAndSortEquipment
//				 request.getSession().setAttribute(CommonConstants.START,1);
//					}
////					request.getSession().setAttribute(CommonConstants.START_INDEX,
////			        		1);
////			        request.getSession().setAttribute(CommonConstants.END_INDEX,
////			        		1000);
////					
////				}
				
		// @@ EquipmentProcessor. <paginateEquipmentList> ??? START_INDEX : null   GOOD.........!!
		// @@ EquipmentProcessor. <paginateEquipmentList> ??? START_INDEX : 1      BAD..........!!	
		
		
		if (null != lstEquipmentResults && !lstEquipmentResults.isEmpty()) {
			// // System.out.println("@@ EquipmentProcessor. <paginateEquipmentList> ??? -->IN<-- lstEquipmentResults && !lstEquipmentResults.isEmpty() : == "+lstEquipmentResults );
			// Generate JSON array of patient search results for paging.
			final List<String> jsonStrArray = this.generateSearchPatJSON(lstEquipmentResults);
			
			// // System.out.println("@@ EquipmentProcessor. <paginateEquipmentList> {{{jsonStrArray}}} : "+jsonStrArray);
			
			response.setContentType(ScreenConstants.JSON_RESPONSE_TYPE);
			response.setHeader(CommonConstants.CACHE_CONTROL, CommonConstants.NO_CACHE);

			try {
				listSize = (int) lstEquipmentResults.get(0).getListSize();           // <<<<<<<<<<<<<<<<========================  !!!
 	 			
			     // System.out.println("@@ EquipmentProcessor. <paginateEquipmentList> {{{listSize}}} : "+listSize);
				// if the start index is not null then paginate with index.
				if (null != request.getSession().getAttribute(CommonConstants.START_INDEX)) {
//				if (start.equalsIgnoreCase("0")) {
					
					  // System.out.println("@@ EquipmentProcessor. <paginateEquipmentList> {{{response.getWriter(a)}}} : "+listSize);
//					response.getWriter().print(PaginationHelper.paginate(jsonRoot, jsonStrArray,Integer.valueOf(start), rowsPerPage));
					response.getWriter().print(PaginationHelper.paginateWithIndex(jsonRoot,	jsonStrArray, listSize));
					
					
//					response.getWriter().print(PaginationHelper.paginateWithIndex(jsonRoot,jsonStrArray, listSize));
					
				} else {
					  // System.out.println(" session start is null @@ EquipmentProcessor. <paginateEquipmentList> {{{response.getWriter(b)}}} : "+listSize);
					response.getWriter().print(PaginationHelper.paginate(jsonRoot, jsonStrArray,Integer.valueOf(start), rowsPerPage));
					// System.out.println(" *** start index is null! *** ");
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

		
	    // // System.out.println("@@ EquipmentProcessor. <paginateEquipmentList> xxxxxxxxxxxxxx---------- DONE ---------xxxxxxxxxxxxxxxx: ");		
		
		PERF_LOGGER.info("End of EquipmentProcessor.paginatePatientList");
	}
	
	/**
	 * This method is used to get patient details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getEquipmentDetails(final HttpServletRequest request,final HttpServletResponse response) {
		
		// // System.out.println("## 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4#");
	    // // System.out.println("@@ EquipmentProcessor. <getEquipmentDetails> :  [[[EquipmentServiceImpl()]]] <------------");
		// // System.out.println("## 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4#");
				
		PERF_LOGGER.info("Start of EquipmentProcessor.getPatientDetails");
		EquipmentServiceImpl equipmentService = new EquipmentServiceImpl();
		Equipment patResult = new Equipment();
		long facilityId = 0l;
		//long spectraMRN = 0l;
		String spectraMRN="";
		try {
			patResult = equipmentService
					.getEquipmentDetails(facilityId, spectraMRN);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("Start of EquipmentProcessor.getPatientDetails");
	}

	/**
	 * This method is used to get the patient requisition details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getEquipmentReqInfo(final HttpServletRequest request,final HttpServletResponse response) {
		
		// // System.out.println("## 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4#");
	    // // System.out.println("@@ EquipmentProcessor. <getEquipmentReqInfo> :  [[[EquipmentServiceImpl()]]] <------------");
		// // System.out.println("## 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4#");
						
		PERF_LOGGER.info("Start of EquipmentProcessor.getPatientDetails");
		EquipmentServiceImpl equipmentService = new EquipmentServiceImpl();
		Equipment patResult = new Equipment();
		String facilityNum = "";
		String requisitionNum = "";
		try {
			patResult = equipmentService.getEquipmentReqInfo(facilityNum,
					requisitionNum);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("Start of EquipmentProcessor.getPatientDetails");
	}
}
