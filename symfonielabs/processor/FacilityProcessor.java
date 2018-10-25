/* ===================================================================*/
/* � 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
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
import com.spectra.symfonielabs.businessobject.FacilityBOFactory;
import com.spectra.symfonielabs.common.processor.SearchUtilityProcessor;
import com.spectra.symfonielabs.constants.FacilityConstants;
import com.spectra.symfonielabs.constants.ScreenConstants;
import com.spectra.symfonielabs.domainobject.FacilityDemographics;
import com.spectra.symfonielabs.domainobject.FacilitySearch;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Search;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class processes all the requests from the client side related to
 * facility.
 *
 */
public class FacilityProcessor extends SearchUtilityProcessor implements
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
			.getLogger(FacilityProcessor.class.getName());

	/**
	 * Processes the facility related requests received from the screens.
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
			if (FacilityConstants.GET_FACILITY.equalsIgnoreCase(action)) {
				this.getFacilities(request, response);
			} else if (FacilityConstants.GET_DASHBOARD_DETAILS
					.equalsIgnoreCase(action)) {
				this.getDashboardDetails(request, response);
			} else if (FacilityConstants.GET_CORPORATIONS
					.equalsIgnoreCase(action)) {
				this.getCorporations(request, response);
			} else if (FacilityConstants.GET_FAC_SEARCH_RESULTS
					.equalsIgnoreCase(action)) {
				this.getFacSearchResults(request, response);
			} else if (FacilityConstants.PAGINATE_FAC_SEARCH
					.equalsIgnoreCase(action)) {
				this.PaginateFacSearchResults(request, response);
			} else if (FacilityConstants.SHOW_FAC_DEMOGRAPHICS
					.equalsIgnoreCase(action)) {
				this.showFacilityDemographics(request, response);
			} else if (FacilityConstants.GET_FAC_LEVEL_GRAPH
					.equalsIgnoreCase(action)) {
				this.getFacLevelGraphDetails(request, response);
			} else if (FacilityConstants.GET_FAC_LEVEL_TABLE
					.equalsIgnoreCase(action)) {
				this.getFacRequisitionDetails(request, response);
			}
		}
	}

	/**
	 * Gets the list of facilities based on teh entered search criteria.
	 * 
	 * @param request
	 * @param response
	 */
	private void getFacilities(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of FacilityProcessor.getFacilities");
		List<Search> facDetLst = new ArrayList<Search>();
		String facilityName = CommonConstants.EMPTY_STRING;
		List<String> jsonStrArray = new ArrayList<String>();
		//Gets the entered facility name to be searched. 
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("getFacName")))) {
			facilityName = StringUtil.valueOf(request
					.getParameter("getFacName"));
		}
		request.getSession().setAttribute(
				FacilityConstants.FAC_SEARCH_CRITERIA, facilityName);
		try {
			//Gets the list of facilities matching the search criteria.
			facDetLst = FacilityBOFactory.getFacilityBO().getfacilities(
					facilityName);
			if (null != facDetLst && !facDetLst.isEmpty()) {
				jsonStrArray = this.generateSearchFacilityJSON(request,
						facDetLst);
			}
			final String jsonFormat = CommonConstants.JSON_FORMAT_LISTINFO
					+ jsonStrArray.toString() + CommonConstants.CLOSE_BRACE;
			response.setContentType(CommonConstants.TEXT_JSON_STRING);
			response.setHeader(CommonConstants.CACHE_CONTROL,
					CommonConstants.NO_CACHE);
			response.getWriter().print(jsonFormat);
		} catch (BusinessException businessException) {
			try {
				response.getWriter().print(businessException.getMessage());
				final StringWriter stringWriter = new StringWriter();
				businessException
						.printStackTrace(new PrintWriter(stringWriter));
				LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION
						+ "\n" + stringWriter.toString());
			} catch (IOException ioException) {
				final StringWriter stringWriter = new StringWriter();
				ioException.printStackTrace(new PrintWriter(stringWriter));
				LOGGER.log(Level.SEVERE,
						ExceptionConstants.APPLICATION_EXCEPTION + "\n"
								+ stringWriter.toString());
			}
		} catch (IOException ioException) {
			final StringWriter stringWriter = new StringWriter();
			ioException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + stringWriter.toString());
		}
		PERF_LOGGER.info("End of FacilityProcessor.getFacilities");
	}

	/**
	 * This method generates json format for facility details.
	 * 
	 * @param searchDetLst
	 *            - List of specfic facility details.
	 * @return Json format String - search facility details in json format
	 *         string.
	 */
	private List<String> generateSearchFacilityJSON(
			final HttpServletRequest request, 
			final List<Search> searchDetLst) {
		PERF_LOGGER
				.info("Start of FacilityProcessor.generateSearchFacilityJSON");
		
		final List<HashMap> facMap = new ArrayList<HashMap>();
		String facName = CommonConstants.EMPTY_STRING;
		//Gets the entered facility name from the session.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getSession().getAttribute(
						FacilityConstants.FAC_SEARCH_CRITERIA)))) {
//			facName = (String) request.getSession().getAttribute(
//					FacilityConstants.FAC_SEARCH_CRITERIA);
			facName = StringUtil.valueOf(request
					.getParameter("getFacName"));
		}
		for (Search searchDet : searchDetLst) {
			final HashMap<String, Object> facSearchMap = new HashMap<String, Object>();
			String tmpSearchVal = searchDet.getSearchValue().toLowerCase();
			String searchVal = searchDet.getSearchValue();
			int index = tmpSearchVal.indexOf(facName.toLowerCase());
			String firstStr = searchVal.substring(0, index);
			String secondStr = "<b>"
					+ searchVal.substring(index, index + facName.length())
					+ "</b>";
			String finalStr = searchVal.substring(index + facName.length(),
					searchVal.length());
			// The matched occurrences from the search criteria are made bold
			// and appended to the facility names.
			String facSearchVal = firstStr + secondStr + finalStr;
			facSearchMap.put("facilityName", facSearchVal);
			facSearchMap.put("selFacName", searchVal);
			facSearchMap.put("facilityId", searchDet.getFacilityId());
			facMap.add(facSearchMap);
		}
		PERF_LOGGER.info("End of FacilityProcessor.generateSearchFacilityJSON");
		return ConstructJSONFormat.generateJsonFormat(facMap);
	}

	/**
	 * Gets the dashboard details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse
	 */
	private void getDashboardDetails(HttpServletRequest request,
			HttpServletResponse response) {
		PERF_LOGGER.info("Start of FacilityProcessor.getDashboardDetails");
		List<RequisitionDetails> reqDetailsLst = new ArrayList<RequisitionDetails>();
		String labComboVal = CommonConstants.EMPTY_STRING;
		String reqTypeComboVal = CommonConstants.EMPTY_STRING;
//		String drawDateVal = CommonConstants.EMPTY_STRING; 
		List<String> jsonStrArray = new ArrayList<String>();
		// Gets the lab combobox value.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(request
				.getParameter("labComboVal"))) {
			labComboVal = request.getParameter("labComboVal");
		}
		// Gets the requisition type combobox type value.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(request
				.getParameter("reqTypeComboVal"))) {
			reqTypeComboVal = request.getParameter("reqTypeComboVal");
		}
		try {
			// Service call for fetching the dashboard details.
			reqDetailsLst = FacilityBOFactory.getFacilityBO()
					.getDashboardDetails(labComboVal, reqTypeComboVal);
			request.setAttribute("pageToBeLoadedAttr", "dashboardPage");
			// If the list is not empty, generate the JSON format.
			if (null != reqDetailsLst && !reqDetailsLst.isEmpty()) {
				jsonStrArray = this.generateDashboardJSON(request,
						reqDetailsLst);
			}
			final String jsonFormat = CommonConstants.JSON_FORMAT_LISTINFO
					+ jsonStrArray.toString() + CommonConstants.CLOSE_BRACE;
			response.setContentType(CommonConstants.TEXT_JSON_STRING);
			response.setHeader(CommonConstants.CACHE_CONTROL,
					CommonConstants.NO_CACHE);
			response.getWriter().print(jsonFormat);
		} catch (IOException ioException) {
			final StringWriter stringWriter = new StringWriter();
			ioException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + stringWriter.toString());
		}
		PERF_LOGGER.info("End of FacilityProcessor.getDashboardDetails");
	}

	/**
	 * This method generates json format for the dashboard details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param reqDetailsLst
	 *            - Holds the requisition details list.
	 * @return Json format String - Ordering dashboard details in json format
	 *         string.
	 */
	private List<String> generateDashboardJSON(
			final HttpServletRequest request,
			final List<RequisitionDetails> reqDetailsLst) {
		PERF_LOGGER.info("Start of FacilityProcessor.generateDashboardJSON");
		final List<HashMap> requisitionMap = new ArrayList<HashMap>();
		final SimpleDateFormat simpleDateFormat = SimpleDateFormatUtil
				.getSimpleDateFormatLocale(CommonConstants.SIMPLE_DATE_FORMAT);
		List<RequisitionDetails> lstReqDetails = new ArrayList<RequisitionDetails>();
		final LinkedHashMap<Date, List<RequisitionDetails>> reqDetMap = 
				new LinkedHashMap<Date, List<RequisitionDetails>>();
		if (reqDetailsLst != null && !reqDetailsLst.isEmpty()) {
			for (RequisitionDetails reqDetail : reqDetailsLst) {
				final Date key = reqDetail.getCollectionDate();
				if (null != reqDetMap.get(key)) {
					reqDetMap.get(key).add(reqDetail);
				} else {
					final List<RequisitionDetails> reqLst = 
							new ArrayList<RequisitionDetails>();
					reqLst.add(reqDetail);
					reqDetMap.put(key, reqLst);
				}
			}
			final Iterator reqDetIterator = reqDetMap.entrySet().iterator();
			while (reqDetIterator.hasNext()) {
				final Map.Entry pairs = (Map.Entry) reqDetIterator.next();
				lstReqDetails = new ArrayList<RequisitionDetails>();
				lstReqDetails.addAll(reqDetMap.get(pairs.getKey()));
				final HashMap<String, Object> dashboardReqDetMap = new HashMap<String, Object>();
				long completeCount = 0;
				long finalCount = 0;
				long partialCount = 0;
				long inProcessCount = 0;
				long notReceivedCount = 0;

				for (RequisitionDetails reqDetail : lstReqDetails) {
					if ("F".equalsIgnoreCase(reqDetail.getStatus())) {
						finalCount += reqDetail.getRequisitionCount();
					}
					if ("COMPLETE".equalsIgnoreCase(reqDetail.getStatus())) {
						completeCount += reqDetail.getRequisitionCount();
					}
					if ("PARTIAL COMPLETE".equalsIgnoreCase(reqDetail
							.getStatus())) {
						partialCount += reqDetail.getRequisitionCount();
					}
//					if ("IN PROCESS".equalsIgnoreCase(reqDetail.getStatus())) {
					if ("P".equalsIgnoreCase(reqDetail.getStatus())) {
						inProcessCount += reqDetail.getRequisitionCount();
					}
					if ("N".equalsIgnoreCase(reqDetail.getStatus())) {
						notReceivedCount += reqDetail.getRequisitionCount();
					}
				}
				dashboardReqDetMap.put("collectionDate",simpleDateFormat.format(pairs.getKey()));
				dashboardReqDetMap.put("completeCount", completeCount);
				dashboardReqDetMap.put("partialCount", partialCount);
				dashboardReqDetMap.put("finalCount", finalCount);
				dashboardReqDetMap.put("notReceivedCount", notReceivedCount);
				dashboardReqDetMap.put("inProcessCount", inProcessCount);
				requisitionMap.add(dashboardReqDetMap);			
			}
		}
		PERF_LOGGER.info("End of FacilityProcessor.generateDashboardJSON");
		return ConstructJSONFormat.generateJsonFormat(requisitionMap);
	}
	private List<String> generateFacTableJSON(
			final HttpServletRequest request,
			final List<RequisitionDetails> reqDetailsLst) {
		PERF_LOGGER.info("Start of FacilityProcessor.generateFacTableJSON");
		final List<HashMap> requisitionMap = new ArrayList<HashMap>();
		if (reqDetailsLst != null && !reqDetailsLst.isEmpty()) {

 				for (RequisitionDetails reqDetail : reqDetailsLst) {
					final HashMap<String, Object> dashboardReqDetMap = new HashMap<String, Object>();
					dashboardReqDetMap.put("patientName",reqDetail.getPatient().getFullName().replaceAll("\"", ""));
					dashboardReqDetMap.put("drawDate",reqDetail.getDrawDate());
					dashboardReqDetMap.put("requisition_id",reqDetail.getRequisition()  );
					dashboardReqDetMap.put("requisition_status", reqDetail.getStatus());
					dashboardReqDetMap.put("draw_frequency", reqDetail.getFrequency());
					dashboardReqDetMap.put("test_count", reqDetail.getNoOfTest());
					dashboardReqDetMap.put("abnormalFlag",reqDetail.getAbnormalFlag());
					dashboardReqDetMap.put("cancelledTestIndicator", reqDetail.isCancelledTestIndicator());
					dashboardReqDetMap.put("patientType", reqDetail.getPatientTypeSubGrp());
					dashboardReqDetMap.put("facilityFk", reqDetail.getPatient().getClinicId());
					dashboardReqDetMap.put("spectraMRN", reqDetail.getPatient().getSpectraMRN());
					dashboardReqDetMap.put("numOfTubesNotRec", reqDetail.getNumOfTubesNotRec());
					dashboardReqDetMap.put("accessionCount", reqDetail.getAccessionCount());
					dashboardReqDetMap.put("tubesReceivedCount", reqDetail.getTubesReceivedCount());
				
				requisitionMap.add(dashboardReqDetMap);
 			}
		}
		PERF_LOGGER.info("End of FacilityProcessor.generateFacTableJSON");
		return ConstructJSONFormat.generateJsonFormat(requisitionMap);
	}

	/**
	 * Gets the corporations.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getCorporations(HttpServletRequest request,
			HttpServletResponse response) {
		PERF_LOGGER.info("Start of FacilityProcessor.getCorporations");
		String corporationName = CommonConstants.EMPTY_STRING;
		List<String> jsonStrArray = new ArrayList<String>();
		//Gets the entered corporation name to be searched.ara
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("corporationName")))) {
			corporationName = StringUtil.valueOf(request
					.getParameter("corporationName"));
		}
		//Sets the entered search criteria in session.
		request.getSession().setAttribute(
				FacilityConstants.CORP_SEARCH_CRITERIA, corporationName);
		try {
			//Gets the list of corporations matching the entered corporation name.
			final List<Search> corpDetailsLst = FacilityBOFactory
					.getFacilityBO().getCorporations(corporationName);
			if (null != corpDetailsLst && !corpDetailsLst.isEmpty()) {
				jsonStrArray = this.generateSearchCorpJSON(request,
						corpDetailsLst);
			}
			final String jsonFormat = CommonConstants.JSON_FORMAT_LISTINFO
					+ jsonStrArray.toString() + CommonConstants.CLOSE_BRACE;
			response.setContentType(CommonConstants.TEXT_JSON_STRING);
			response.setHeader(CommonConstants.CACHE_CONTROL,
					CommonConstants.NO_CACHE);
			response.getWriter().print(jsonFormat);
		} catch (BusinessException businessException) {
			try {
				response.getWriter().print(businessException.getMessage());
				final StringWriter stringWriter = new StringWriter();
				businessException
						.printStackTrace(new PrintWriter(stringWriter));
				LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION
						+ "\n" + stringWriter.toString());
			} catch (IOException ioException) {
				final StringWriter stringWriter = new StringWriter();
				ioException.printStackTrace(new PrintWriter(stringWriter));
				LOGGER.log(Level.SEVERE,
						ExceptionConstants.APPLICATION_EXCEPTION + "\n"
								+ stringWriter.toString());
			}
		} catch (IOException ioException) {
			final StringWriter stringWriter = new StringWriter();
			ioException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + stringWriter.toString());
		}
		PERF_LOGGER.info("End of FacilityProcessor.getCorporations");
	}

	/**
	 * This method generates json format for corporation details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param corpDetailsLst
	 *            - List of corporation details.
	 * @return Json format String - Corporation details in json format string.
	 */
	private List<String> generateSearchCorpJSON(  
			final HttpServletRequest request, 
			final List<Search> corpDetailsLst) {
		PERF_LOGGER
				.info("Start of FacilityProcessor.generateSearchFacilityJSON");
		final List<HashMap> corporationMap = new ArrayList<HashMap>();
		String corporationName = CommonConstants.EMPTY_STRING;
		//Gets the entered corporation name from the session.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getSession().getAttribute(
						FacilityConstants.CORP_SEARCH_CRITERIA)))) {
			corporationName = (String) request.getSession().getAttribute(
					FacilityConstants.CORP_SEARCH_CRITERIA);
		}
		
 		for (Search searchDet : corpDetailsLst) {  
 			String firstStr = CommonConstants.EMPTY_STRING;
 			String secondStr = CommonConstants.EMPTY_STRING;
 			String finalStr = CommonConstants.EMPTY_STRING;
 			// check if name is empty then use acronym //
			final HashMap<String, Object> facSearchMap = new HashMap<String, Object>();
//			String tmpSearchVal = searchDet.getCorporationName().toLowerCase();
			String tmpSearchVal = searchDet.getSelCorpName().toLowerCase();
//			String searchVal = searchDet.getCorporationName();
			String searchVal = searchDet.getSelCorpName();
			String selCorpName = searchDet.getSelCorpName();
			String selCorpAcronym = searchDet.getAcronym();	
		int index = tmpSearchVal.indexOf(corporationName.toLowerCase());			
		if (tmpSearchVal.contains(corporationName.toLowerCase())) {   // acronym
			  firstStr = searchVal.substring(0, index);
			  secondStr = "<b>"+ searchVal.substring(index,index + corporationName.length()) + "</b>";
			  finalStr = searchVal.substring(index + corporationName.length(), searchVal.length());
		}else {
			finalStr = searchVal;
		}
		
			// The matched occurrences from the search criteria are made bold
			// and appended to the corporation names.			
			String corpSearchVal = firstStr + secondStr + finalStr;
			facSearchMap.put("corporationName", corpSearchVal);
			facSearchMap.put("searchVal", searchVal);
			facSearchMap.put("selCorpName", selCorpName);
			facSearchMap.put("selCorpAcronym", selCorpAcronym);
			facSearchMap.put("corporationId", searchDet.getCorporationId());
			corporationMap.add(facSearchMap);
		}
		PERF_LOGGER.info("End of FacilityProcessor.generateSearchFacilityJSON");
		return ConstructJSONFormat.generateJsonFormat(corporationMap);
	}

	/**
	 * Gets the facility search results based on the corporation name and
	 * facility name entered.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getFacSearchResults(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of FacilityProcessor.getFacSearchResults");
		request.removeAttribute(FacilityConstants.IS_FAC_SEARCH_FLOW);
		request.getSession().removeAttribute(FacilityConstants.HLAB_NUM);
		long corporationId = 0L;
		int listSize = 0;
		String corporationName = CommonConstants.EMPTY_STRING;
		String acronym = CommonConstants.EMPTY_STRING;
		String selCorpName = CommonConstants.EMPTY_STRING;
		String facilityName = CommonConstants.EMPTY_STRING;
		String searchType = CommonConstants.EMPTY_STRING;
		//Gets the selected corporation Id.	
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("corporationId")))) {
			corporationId = Long.valueOf(
					request.getParameter("corporationId"));
		}
		//Gets the selected corporation name.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("corporationName")))) {
			corporationName = request.getParameter("corporationName");
		}
		//Gets the selected corporation acronym.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("corporationAcronym")))) {
			corporationName = request.getParameter("corporationAcronym");
		}
		//Gets the entered facility name.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("facilityName")))) {
			facilityName = request.getParameter("facilityName");
		}
		//Gets the selected search type.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("searchType")))) {
			searchType = request.getParameter("searchType");
		}
		final Search searchCriteria = new Search(corporationName,
				corporationId, acronym,selCorpName, 0L, facilityName, searchType);
		searchCriteria.setSearchType(searchType);
		request.getSession().setAttribute(FacilityConstants.SEARCH_TYPE,
				searchType);
		searchCriteria.setStartIndex(1);
		searchCriteria.setEndIndex(1000);
		try {
			//Gets the facility search results.
			final List<FacilitySearch> facSearchLst = FacilityBOFactory
					.getFacilityBO().getFacSearchResults(searchCriteria);
			// If the facility search results are not null, then get the total
			// count of the search results.
			if (null != facSearchLst && !facSearchLst.isEmpty()) {
				listSize = facSearchLst.size();
			}
			// If the list size is not equal to 1, then navigate to search
			// results page, else go to facility demographics page.
			if (1 != listSize) {
				request.setAttribute(FacilityConstants.PAGE_TO_BE_LOADED,
						"facSearchResultsPage");
				request.getSession().setAttribute(
						CommonConstants.SEARCH_RESULTS, facSearchLst);
			} else {
				FacilityProcessor facilityProcessor = new FacilityProcessor();
				request.setAttribute(FacilityConstants.IS_FAC_SEARCH_FLOW, 
						true);
				if (null != facSearchLst && !facSearchLst.isEmpty()) {
					request.setAttribute(FacilityConstants.HLAB_NUM,
							facSearchLst.get(0).getHlabNumber());
					request.getSession().setAttribute(
							FacilityConstants.HLAB_NUM,
							facSearchLst.get(0).getHlabNumber());
					facilityProcessor.process(request, response,
							FacilityConstants.SHOW_FAC_DEMOGRAPHICS);
				}
				request.setAttribute(FacilityConstants.PAGE_TO_BE_LOADED,
						"facDemographicsPage");
			}
			request.getSession().setAttribute(
					CommonConstants.SEARCH_PARAMETERS, searchCriteria);
			
			request.getSession().setAttribute(CommonConstants.START_INDEX, 1);
			request.getSession().setAttribute(CommonConstants.END_INDEX, 1000);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of FacilityProcessor.getFacSearchResults");
	}

	/**
	 * Paginate results for the facility search.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void PaginateFacSearchResults(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of FacilityProcessor.PaginateFacSearchResults");
		final String jsonRoot = CommonConstants.JSON_ROOT_LIST_INFO;
		final String start = request.getParameter(CommonConstants.START);
		final String strRows = request.getParameter(CommonConstants.LIMIT);
		final int rowsPerPage = Integer.parseInt(strRows);
		int listSize = 0;
		// Gets the list of patient search results in a session.
		List<FacilitySearch> lstResults = (List<FacilitySearch>) request
				.getSession().getAttribute(CommonConstants.SEARCH_RESULTS);
		if (null != request.getSession().getAttribute(
				CommonConstants.START_INDEX)) {
			// used for paginating & sort the patients.
			lstResults = this.paginateAndSortFacSearchRes(request, response);
		}
		if (null != lstResults && !lstResults.isEmpty()) {
			// Generate JSON array of facility search results for paging.
			final List<String> jsonStrArray = this
					.generateFacSearchResJSON(lstResults);
			response.setContentType(ScreenConstants.JSON_RESPONSE_TYPE);
			response.setHeader(CommonConstants.CACHE_CONTROL,
					CommonConstants.NO_CACHE);
			try {
				listSize = (int) lstResults.get(0).getListSize();
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
		PERF_LOGGER.info("End of FacilityProcessor.PaginateFacSearchResults");
	}

	/**
	 * This method generates json format for facility search results.
	 * 
	 * @param lstResults
	 *            - List of facility search results.
	 * @return Json format String - facility search results in json format
	 *         string.
	 */
	private List<String> generateFacSearchResJSON(
			final List<FacilitySearch> lstResults) {
		PERF_LOGGER.info("Start of FacilityProcessor.generateFacSearchResJSON");
		final List<HashMap> facSearchMap = new ArrayList<HashMap>();
		for (FacilitySearch searchRes : lstResults) {
			final HashMap<String, Object> facSearch = new HashMap<String, Object>();
			facSearch.put("facilityName", searchRes.getFacilityName());
			facSearch.put("corporationName", searchRes.getCorporationName());
			facSearch.put("accountType", searchRes.getAccountType());
			facSearch.put("accountStatus", searchRes.getAccountStatus());
			facSearch.put("servicingLab", searchRes.getServicingLab());
			facSearch.put("hlabNum", searchRes.getHlabNumber());
			facSearchMap.add(facSearch);
		}
		PERF_LOGGER.info("End of FacilityProcessor.generateFacSearchResJSON");
		return ConstructJSONFormat.generateJsonFormat(facSearchMap);
	}

	/**
	 * Method to show the facility demographics screen.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse
	 */
	private void showFacilityDemographics(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of FacilityProcessor.showFacilityDemographics");
		// Remove all the sessions so that latest changes are available on
		// setting it.
		request.getSession().removeAttribute(
				FacilityConstants.FAC_DEMO_SESSION);
		request.getSession().removeAttribute(
				FacilityConstants.FAC_DEMO_ACCOUNT_LST);
		request.getSession().removeAttribute(
				FacilityConstants.FAC_DEMO_SCHEDULE_LST);
		String HLABNum = CommonConstants.EMPTY_STRING;
		
		if (!CommonConstants.TRUE.equalsIgnoreCase(StringUtil.valueOf(request
				.getAttribute(FacilityConstants.IS_FAC_SEARCH_FLOW)))) {
			HLABNum = (String) request.getSession().getAttribute(
					FacilityConstants.HLAB_NUM);
		}
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("hlabNum")))) {
			HLABNum = request.getParameter("hlabNum");
		}
		try {
			//Gets the facility demographics details.
			final FacilityDemographics facilityDemographics = FacilityBOFactory
					.getFacilityBO().getFacDemographics(HLABNum);
			// Gets the facility demographics accounts list for a chosen
			// facility.
			final List<FacilityDemographics> facDemoAccountlst = FacilityBOFactory
					.getFacilityBO().getFacDemoAccountLst(
							facilityDemographics.getFacilityId());
			// Gets the facility demographics schedule list for a chosen
			//facility.
			final List<FacilityDemographics> facDemoScheduleLst = FacilityBOFactory
					.getFacilityBO().getFacDemoScheduleLst(
							facilityDemographics.getFacilityId());
			//Setting the session.
			request.getSession().setAttribute(FacilityConstants.FAC_DEMO_SESSION,
					facilityDemographics);
			request.setAttribute(FacilityConstants.PAGE_TO_BE_LOADED,
					"facDemographicsPage");
			request.getSession().setAttribute(
					FacilityConstants.FAC_DEMO_ACCOUNT_LST, facDemoAccountlst);
			request.getSession().setAttribute(
			FacilityConstants.FAC_DEMO_SCHEDULE_LST, facDemoScheduleLst);
			
			if (request.getSession().getAttribute("phoneExt") == null) {
				ActiveDirProcessor ad =  new ActiveDirProcessor();
				ad.getUserPhone(request, response);
			}
//		  }
			
		} catch (BusinessException businessException) {
			try {
				response.getWriter().print(businessException.getMessage());
				final StringWriter stringWriter = new StringWriter();
				businessException
						.printStackTrace(new PrintWriter(stringWriter));
				LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION
						+ "\n" + stringWriter.toString());
			} catch (IOException ioException) {
				final StringWriter stringWriter = new StringWriter();
				ioException.printStackTrace(new PrintWriter(stringWriter));
				LOGGER.log(Level.SEVERE,
						ExceptionConstants.APPLICATION_EXCEPTION + "\n"
								+ stringWriter.toString());
			}
		}
		PERF_LOGGER.info("End of FacilityProcessor.showFacilityDemographics");
	}

	/**
	 * Gets the facility level graph details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getFacLevelGraphDetails(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of FacilityProcessor.getFacLevelGraphDetails");
		String reqTypeComboVal = CommonConstants.EMPTY_STRING;
		String facilityIdVal = CommonConstants.EMPTY_STRING;
		String drawDate = CommonConstants.EMPTY_STRING;
		List<String> jsonStrArray = new ArrayList<String>();
		// Gets the requisition type combobox type value.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(request
				.getParameter("reqTypeComboVal"))) {
			reqTypeComboVal = request.getParameter("reqTypeComboVal");
		}
		
		
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(request
				.getParameter("drawDate"))) {
			drawDate = request.getParameter("drawDateVal");
		}
		//Gets the facility Id value.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(request
				.getParameter("facilityIdVal"))) {
			facilityIdVal = request.getParameter("facilityIdVal");
		}
		try {
			// Service call for fetching the Facility Graph details.
			final List<RequisitionDetails> graphDetailLst = FacilityBOFactory
					.getFacilityBO().getFacLevelGraphDetails(reqTypeComboVal,
							facilityIdVal,drawDate);
			// If the list is not empty, generate the JSON format.
			if (null != graphDetailLst && !graphDetailLst.isEmpty()) {
				jsonStrArray = this.generateFacGraphJSON(request,
						graphDetailLst);
			}
			final String jsonFormat = CommonConstants.JSON_FORMAT_LISTINFO
					+ jsonStrArray.toString() + CommonConstants.CLOSE_BRACE;
			response.setContentType(CommonConstants.TEXT_JSON_STRING);
			response.setHeader(CommonConstants.CACHE_CONTROL,
					CommonConstants.NO_CACHE);
			response.getWriter().print(jsonFormat);
		} catch (IOException ioException) {
			final StringWriter stringWriter = new StringWriter();
			ioException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + stringWriter.toString());
		}
		PERF_LOGGER.info("End of FacilityProcessor.getFacLevelGraphDetails");
	}
	
	/**
	 * This method generates json format for the dashboard details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param reqDetailsLst
	 *            - Holds the requisition details list.
	 * @return Json format String - Ordering dashboard details in json format
	 *         string.
	 */
	private List<String> generateFacGraphJSON(
			final HttpServletRequest request,
			final List<RequisitionDetails> reqDetailsLst) {
		PERF_LOGGER.info("Start of FacilityProcessor.generateFacGraphJSON");
		final List<HashMap> requisitionMap = new ArrayList<HashMap>();
		final SimpleDateFormat simpleDateFormat = SimpleDateFormatUtil
				.getSimpleDateFormatLocale(CommonConstants.SIMPLE_DATE_FORMAT);
		List<RequisitionDetails> lstReqDetails = new ArrayList<RequisitionDetails>();
		final LinkedHashMap<Date, List<RequisitionDetails>> reqDetMap = 
				new LinkedHashMap<Date, List<RequisitionDetails>>();
		if (reqDetailsLst != null && !reqDetailsLst.isEmpty()) {
			for (RequisitionDetails reqDetail : reqDetailsLst) {
				final Date key = reqDetail.getCollectionDate();
				if (null != reqDetMap.get(key)) {
					reqDetMap.get(key).add(reqDetail);
				} else {
					final List<RequisitionDetails> reqLst = 
							new ArrayList<RequisitionDetails>();
					reqLst.add(reqDetail);
					reqDetMap.put(key, reqLst);
				}
			}
			final Iterator reqDetIterator = reqDetMap.entrySet().iterator();
			while (reqDetIterator.hasNext()) {
				final Map.Entry pairs = (Map.Entry) reqDetIterator.next();
				lstReqDetails = new ArrayList<RequisitionDetails>();
				lstReqDetails.addAll(reqDetMap.get(pairs.getKey()));
				final HashMap<String, Object> dashboardReqDetMap = new HashMap<String, Object>();
				long finalCount = 0;
				long inProcessCount = 0;
				long notReceivedCount = 0;

				for (RequisitionDetails reqDetail : lstReqDetails) {
					if ("F".equalsIgnoreCase(reqDetail.getStatus())) {
						finalCount += reqDetail.getRequisitionCount();
					}
					if ("P".equalsIgnoreCase(reqDetail.getStatus())) {
						inProcessCount += reqDetail.getRequisitionCount();
					}
					if ("N".equalsIgnoreCase(reqDetail.getStatus())) {
						notReceivedCount += reqDetail.getRequisitionCount();
					}
					int count=0;
					count++;
				}
				dashboardReqDetMap.put("collectionDate",simpleDateFormat.format(pairs.getKey()));
				dashboardReqDetMap.put("finalCount", finalCount);
				dashboardReqDetMap.put("notReceivedCount", notReceivedCount);
				dashboardReqDetMap.put("inProcessCount", inProcessCount);
				requisitionMap.add(dashboardReqDetMap);		
			}
		}
		PERF_LOGGER.info("End of FacilityProcessor.generateDashboardJSON");
		return ConstructJSONFormat.generateJsonFormat(requisitionMap);
	}
	
	/**
	 * Gets the facility req level table details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getFacRequisitionDetails(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of FacilityProcessor.getFacReqTable");
		String reqTypeComboVal = CommonConstants.EMPTY_STRING;
		String facilityIdVal = CommonConstants.EMPTY_STRING;
		String drawDate  = CommonConstants.EMPTY_STRING;
		String patType  = CommonConstants.EMPTY_STRING;
		List<String> jsonStrArray = new ArrayList<String>();
		// Gets the requisition type combobox type value.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(request
				.getParameter("reqTypeComboVal"))) {
			reqTypeComboVal = request.getParameter("reqTypeComboVal");
		}
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(request
				.getParameter("drawDate"))) {
			drawDate = request.getParameter("DrawDate");
		}
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(request
				.getParameter("patType"))) {
			patType = request.getParameter("patType");
		}		
		//Gets the facility Id value.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(request
				.getParameter("facilityId"))) {
			facilityIdVal = request.getParameter("facilityId");
		}
		try {
			// Service call for fetching the req table details.
			final List<RequisitionDetails> graphDetailLst = FacilityBOFactory
					.getFacilityBO().getFacRequisitionDetails(reqTypeComboVal,
							facilityIdVal,drawDate,patType);
			// If the list is not empty, generate the JSON format.
			if (null != graphDetailLst && !graphDetailLst.isEmpty()) {
				jsonStrArray = this.generateFacTableJSON(request,
						graphDetailLst);
			}
			final String jsonFormat = CommonConstants.JSON_FORMAT_LISTINFO
					+ jsonStrArray.toString() + CommonConstants.CLOSE_BRACE;
			response.setContentType(CommonConstants.TEXT_JSON_STRING);
			response.setHeader(CommonConstants.CACHE_CONTROL,
					CommonConstants.NO_CACHE);
			response.getWriter().print(jsonFormat);
		} catch (IOException ioException) {
			final StringWriter stringWriter = new StringWriter();
			ioException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + stringWriter.toString());
		}
		PERF_LOGGER.info("End of FacilityProcessor.getFacRequisitionDetails");
	}
	/**
	 * Forwards the request.
	 *
	 * @param dispatcher
	 *            - RequestDispatcher to forward the request.
	 * @param request
	 *            - HttpServletRequest
	 * @param response
	 *            - HttpServletResponse
	 */
	private void forwardRequest(final RequestDispatcher dispatcher,
			final HttpServletRequest request, final HttpServletResponse response) {
		try {
			dispatcher.forward(request, response);
		} catch (ServletException servletException) {
			final StringWriter stringWriter = new StringWriter();
			servletException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + stringWriter.toString());

		} catch (IOException ioException) {
			final StringWriter stringWriter = new StringWriter();
			ioException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE, ExceptionConstants.APPLICATION_EXCEPTION
					+ "\n" + stringWriter.toString());
		}
	}
}
