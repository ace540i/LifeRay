/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New Processor Class for Staff. 
 */
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
import com.spectra.symfonielabs.common.processor.SearchUtilityProcessor;
import com.spectra.symfonielabs.constants.ScreenConstants;
import com.spectra.symfonielabs.constants.StaffConstants;
import com.spectra.symfonielabs.domainobject.Search;
import com.spectra.symfonielabs.domainobject.Staff;
import com.spectra.symfonielabs.service.StaffService;
import com.spectra.symfonielabs.service.StaffServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class processes all the requests from the client side related to
 * staffs.
 *
 */
public class StaffProcessor extends SearchUtilityProcessor implements Processor {

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
	 * Processes the staff related requests recieved from the screens.
	 *
	 * @param action
	 * @param request
	 * @param response
	 */
	public final void process(final HttpServletRequest request,
			final HttpServletResponse response, final String action) {
		if (action != null) {			
			if (StaffConstants.GET_SEARCH_RESULTS.equalsIgnoreCase(action)) {
				this.getSearchResult(request, response);
			} else if (StaffConstants.GET_STAFF_RESULTS
					.equalsIgnoreCase(action)) {
				this.getStaffDetails(request, response);
			} else if (StaffConstants.GET_STAFF_REQ_DET
					.equalsIgnoreCase(action)) {
				this.getStaffReqInfo(request, response);
			}else if (StaffConstants.PAGINATE_SEARCH_RESULTS
					.equalsIgnoreCase(action)) {
				this.paginateStaffList(request, response);
			}			
		}
	}

	/**
	 * Gets the search results for the staff.
	 * 
	 * @param request
	 * @param response
	 */
	private void getSearchResult(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of StaffProcessor.getSearchResult");
		request.getSession().removeAttribute(CommonConstants.SEARCH_RESULTS);
		request.getSession().removeAttribute(CommonConstants.SEARCH_PARAMETERS);
		request.getSession().removeAttribute("spectraMRN");
		request.getSession().removeAttribute("facilityId");
		request.getSession().removeAttribute("dateRange");
		request.getSession().removeAttribute(CommonConstants.START_INDEX);
		StaffService staffService = new StaffServiceImpl();
		List<Staff> searchRes = new ArrayList<Staff>();	
		long facilityId = 0L; 
		String facilityName = CommonConstants.EMPTY_STRING;
		String staffName = CommonConstants.EMPTY_STRING;
		int listSize = 0;
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("facilityId")))) {
			facilityId = Long.valueOf((String) request
					.getParameter("facilityId"));
		}
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("staffName")))) {
			staffName = (String) request.getParameter("staffName");
		}
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("facilityName")))) {
			facilityName = (String) request.getParameter("facilityName");
		} 
		Search searchCriteria = new Search("Staff", staffName, facilityId,
				CommonConstants.EMPTY_STRING, facilityName);
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
			searchRes = staffService.getSearchResult(searchCriteria);
			if(null != searchRes && !searchRes.isEmpty()){
				listSize = searchRes.size();
			}
			if(1 != listSize) {
			
			request.setAttribute("pageToBeLoadedAttr", "staffResPage");
			request.getSession().setAttribute(CommonConstants.SEARCH_RESULTS,
					searchRes);		
			
//			request.getSession().setAttribute(
//					CommonConstants.SEARCH_PARAMETERS, searchCriteria);
//			request.getSession().setAttribute(CommonConstants.START_INDEX,
//	        		1);
//	        request.getSession().setAttribute(CommonConstants.END_INDEX,
//	        		1000);
			
			
			} else {
				
				OrderStaffProcessor orderStaffProcessor = new OrderStaffProcessor();
				request.setAttribute("isStaffSrhFlow", true);
				if(null != searchRes && !searchRes.isEmpty()){
					request.setAttribute("spectraMRN", searchRes.get(0)
							.getSpectraMRN());
					orderStaffProcessor.process(request, response, "getOrderSum");
					request.getSession().setAttribute("spectraMRN",
							searchRes.get(0).getSpectraMRN());
					request.getSession().setAttribute("facilityId",
							searchRes.get(0).getFacilityId());
				}
				request.setAttribute("pageToBeLoadedAttr", "orderStaffDetailPage");
			}
			
//			
//			searchCriteria.setSearchType(searchType);
//			request.getSession().setAttribute("searchType",searchType);
			searchCriteria.setStartIndex(1);
			searchCriteria.setEndIndex(1000);
			request.getSession().setAttribute(
					CommonConstants.SEARCH_PARAMETERS, searchCriteria);
			request.getSession().setAttribute(CommonConstants.START_INDEX,
	        		1);
	        request.getSession().setAttribute(CommonConstants.END_INDEX,
	        		1000);
//			request.getSession().setAttribute(
//					CommonConstants.SEARCH_PARAMETERS, searchCriteria);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("End of StaffProcessor.getSearchResult");
	}

	/**
	 * This method generates json format for staff details.
	 * 
	 * @param searchDetLst
	 *            - List of specfic staff details.
	 * @return Json format String - staff details in json format
	 *         string.
	 */
	private List<String> generateSearchPatJSON(List<Staff> searchResLst) {
		PERF_LOGGER
				.info("Start of StaffProcessor.generateSearchPatJSON");
		final List<HashMap> patResMap = new ArrayList<HashMap>();
//		SimpleDateFormat simpleDateFormat = SimpleDateFormatUtil
//				.getSimpleDateFormatLocale(CommonConstants.
//						SIMPLE_DATE_FORMAT);
		
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy/MM/dd");

		for (Staff searchRes : searchResLst) {
			final HashMap<String, Object> patRes = new HashMap<String, Object>();
			patRes.put("fullName", searchRes.getFullName());
			patRes.put("gender", searchRes.getGender());
			
			if (null != searchRes.getDateOfBirth()) {
				patRes.put("dateOfBirth",
						simpleDateFormat.format(searchRes.getDateOfBirth()));
			}
			patRes.put("facilityName", searchRes.getFacilityName());
			patRes.put("facilityId", searchRes.getFacilityNum());
			patRes.put("spectraMRN", searchRes.getSpectraMRN());
//			patRes.put("modality", searchRes.getModality());
			patRes.put("staffHLABId", searchRes.getPatientHLABId());
			patRes.put("facility_fk", searchRes.getFacilityId());
			if (null != searchRes.getLastDrawDate()) {
				patRes.put("lastDrawDate",
						simpleDateFormat.format(searchRes.getLastDrawDate()));
			}
			patResMap.add(patRes);
		}
		PERF_LOGGER.info("End of StaffProcessor.generateSearchPatJSON");
		return ConstructJSONFormat.generateJsonFormat(patResMap);
	}

	/**
	 *Paginate results for the staff.
	 * 
	 * @param request
	 * @param response
	 */
	private void paginateStaffList(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of StaffProcessor.paginateStaffList");
		final String jsonRoot = CommonConstants.JSON_ROOT_LIST_INFO;
		final String start = request.getParameter(CommonConstants.START);
		final String strRows = request.getParameter(CommonConstants.LIMIT);
		final int rowsPerPage = Integer.parseInt(strRows);		
		int listSize = 0;
		// Gets the list of staff search results in a session.
		List<Staff> lststaffResults = (List<Staff>) request
				.getSession().getAttribute(
						CommonConstants.SEARCH_RESULTS);
		if (null != request.getSession().getAttribute(
				CommonConstants.START_INDEX)) {
			// used for paginating & sort the staffs.
			lststaffResults = this.paginateAndSortStaffs(request, response);
		}
		if (null != lststaffResults && !lststaffResults.isEmpty()) {
			// Generate JSON array of staff search results for paging.
			final List<String> jsonStrArray = this
					.generateSearchPatJSON(lststaffResults);
			response.setContentType(ScreenConstants.JSON_RESPONSE_TYPE);
			response.setHeader(CommonConstants.CACHE_CONTROL,
					CommonConstants.NO_CACHE);
			try {
			listSize = (int) lststaffResults.get(0).getListSize();
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
				ioException.printStackTrace();
			}
		}
		PERF_LOGGER.info("End of StaffProcessor.paginateStaffList");
	}
	
	/**
	 * This method is used to get staff details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getStaffDetails(final HttpServletRequest requoest,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of StaffProcessor.getStaffDetails");
		StaffServiceImpl staffService = new StaffServiceImpl();
		Staff patResult = new Staff();
		long facilityId = 0l;
		long spectraMRN = 0l;
		try {
			patResult = staffService
					.getStaffDetails(facilityId, spectraMRN);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("Start of StaffProcessor.getStaffDetails");
	}

	/**
	 * This method is used to get the staff requisition details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getStaffReqInfo(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of StaffProcessor.getStaffDetails");
		StaffServiceImpl staffService = new StaffServiceImpl();
		Staff patResult = new Staff();
		String facilityNum = "";
		String requisitionNum = "";
		try {
			patResult = staffService.getStaffReqInfo(facilityNum,
					requisitionNum);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("Start of StaffProcessor.getStaffDetails");
	}
}
