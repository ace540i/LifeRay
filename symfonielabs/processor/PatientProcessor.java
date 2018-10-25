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
import com.spectra.symfonielabs.common.processor.SearchUtilityProcessor;
import com.spectra.symfonielabs.constants.PatientConstants;
import com.spectra.symfonielabs.constants.ScreenConstants;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.Search;
import com.spectra.symfonielabs.service.PatientService;
import com.spectra.symfonielabs.service.PatientServiceImpl;
import com.spectra.symfonielabs.transformer.CommonTransformer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class PatientProcessor extends SearchUtilityProcessor implements
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
	 * Processes the patient related requests received from the screens.
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
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//	Enumeration params = request.getParameterNames(); 
		//	while(params.hasMoreElements()){
		//	String paramName = (String)params.nextElement();
		//	System.out.println(new java.util.Date()+" - @@@@ PatientProcessor.process @@@@   #-> Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		//	}	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		if (action != null) {
			if (PatientConstants.GET_SEARCH_RESULTS.equalsIgnoreCase(action)) {
				this.getSearchResult(request, response);
			} else if (PatientConstants.PAGINATE_SEARCH_RESULTS
					.equalsIgnoreCase(action)) {
				this.paginatePatientList(request, response);
			}
		}
	}

	/**
	 * Gets the search results for the patient.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getSearchResult(final HttpServletRequest request, final HttpServletResponse response) {
		PERF_LOGGER.info("Start of PatientProcessor.getSearchResult");
		
		//timc
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//	Enumeration params = request.getParameterNames(); 
		//	while(params.hasMoreElements()){
		//	String paramName = (String)params.nextElement();
		//	System.out.println(new java.util.Date()+" - @@@@ PatientProcessor.getSearchResult @@@@   #-> Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		//	}	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		request.getSession().removeAttribute(CommonConstants.SEARCH_RESULTS);
		request.getSession().removeAttribute(CommonConstants.SEARCH_PARAMETERS);
		request.getSession().removeAttribute(PatientConstants.SPECTRA_MRN);
		request.getSession().removeAttribute("dateRange");
		//timc;
		request.getSession().removeAttribute("patientDOB");	
		final PatientService patientService = new PatientServiceImpl();
		long facilityId = 0L;
		String facilityName = CommonConstants.EMPTY_STRING;
		String patientName = CommonConstants.EMPTY_STRING;
//timc
		String patientDOB = CommonConstants.EMPTY_STRING;	
	
		int listSize = 0;
		//Gets the selected facility Id.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("facilityId")))) {
			facilityId = Long.valueOf((String) request.getParameter("facilityId"));
		}
		//Gets the entered patient name.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("patientName")))) {
			patientName = (String) request.getParameter("patientName");
		}
		//Gets the selected facility name.
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("facilityName")))) {
			facilityName = (String) request.getParameter("facilityName");
			
		}
		//timc		
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("patientDOB")))) {
					patientDOB = (String) request.getParameter("patientDOB");
					
				}
				
			
//				Search searchCriteria = new Search("Patient", patientName, facilityId, CommonConstants.EMPTY_STRING, facilityName);
		//timc		
			final Search searchCriteria = new Search("Patient", patientName, facilityId, CommonConstants.EMPTY_STRING, facilityName, patientDOB);
				
				
		String searchType = null;
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getParameter("searchType")))) {
			searchType = (String) request.getParameter("searchType");
			
		}
		searchCriteria.setSearchType(searchType);
		request.getSession().setAttribute(PatientConstants.SEARCH_TYPE,
				searchType);
		searchCriteria.setStartIndex(1);
		searchCriteria.setEndIndex(1000);
		try {
			// Gets the patient search results based on the entered search
			// criteria.
			final List<Patient> searchRes = patientService
					.getSearchResult(searchCriteria);
			// If the patient search results are not null, then get the total
			// count of the search results.
			if (null != searchRes && !searchRes.isEmpty()) {
				listSize = searchRes.size();
			}
			// If the list size is not equal to 1, then navigate to search
			// results page, else go to order summary page.
			if (1 != listSize) {
				request.setAttribute(PatientConstants.PAGE_TO_BE_LOADED,
						"searchResPage");
				request.getSession().setAttribute(CommonConstants.SEARCH_RESULTS, searchRes);
			} else {
				OrderProcessor orderProcessor = new OrderProcessor();
				request.setAttribute(PatientConstants.IS_PATIENT_SRCH_FLOW,
						true);
				if (null != searchRes && !searchRes.isEmpty()) {
					request.setAttribute(PatientConstants.SPECTRA_MRN,
							searchRes.get(0).getSpectraMRN());
					orderProcessor.process(request, response, "getOrderSum");
					request.getSession().setAttribute(
							PatientConstants.SPECTRA_MRN,
							searchRes.get(0).getSpectraMRN());
					request.getSession().setAttribute(
							PatientConstants.FACILITY_ID,
							searchRes.get(0).getFacilityId());
				}
				request.setAttribute(PatientConstants.PAGE_TO_BE_LOADED,
						"orderDetailPage");
			}
			request.getSession().setAttribute(
					CommonConstants.SEARCH_PARAMETERS, searchCriteria);
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
		PERF_LOGGER.info("End of PatientProcessor.getSearchResult");
	}

	/**
	 * This method generates json format for patient details.
	 * 
	 * @param searchResLst
	 *            - List of specfic patient details.
	 * @return Json format String - patient details in json format string.
	 */
	private List<String> generateSearchPatJSON(
			final List<Patient> searchResLst) {
		PERF_LOGGER
				.info("Start of PatientProcessor.generateSearchPatJSON");
		final List<HashMap> patResMap = new ArrayList<HashMap>();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy/MM/dd");
		for (Patient searchRes : searchResLst) {
			final HashMap<String, Object> patRes = new HashMap<String, Object>();
			patRes.put("fullName", CommonTransformer.parseToJSON(
					searchRes.getFullName()));
			patRes.put("gender", searchRes.getGender());
			if (null != searchRes.getDateOfBirth()) {
				patRes.put("dateOfBirth",
						simpleDateFormat.format(searchRes.getDateOfBirth()));
			}			
			patRes.put("facilityName",searchRes.getFacilityName());
			patRes.put("facilityId", searchRes.getFacilityNum());
			patRes.put("spectraMRN", searchRes.getSpectraMRN());
			patRes.put("modality", searchRes.getModality());
			patRes.put("patientHLABId", searchRes.getPatientHLABId());
			patRes.put("facility_fk", searchRes.getFacilityId());
			if (null != searchRes.getLastDrawDate()) {
				patRes.put("lastDrawDate",
						simpleDateFormat.format(searchRes.getLastDrawDate()));
			}
			patResMap.add(patRes);					
		}
		PERF_LOGGER.info("End of PatientProcessor.generateSearchPatJSON");
		return ConstructJSONFormat.generateJsonFormat(patResMap);
	}


	/**
	 * Paginate results for the patient.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void paginatePatientList(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of PatientProcessor.paginatePatientList");
		final String jsonRoot = CommonConstants.JSON_ROOT_LIST_INFO;
		final String start = request.getParameter(CommonConstants.START);
		final String strRows = request.getParameter(CommonConstants.LIMIT);
		final int rowsPerPage = Integer.parseInt(strRows);
		int listSize = 0;
		// Gets the list of patient search results in a session.
		List<Patient> lstResults = (List<Patient>) request
				.getSession().getAttribute(
						CommonConstants.SEARCH_RESULTS);
		if (null != request.getSession().getAttribute(
				CommonConstants.START_INDEX)) {
			// used for paginating & sort the patients.
			lstResults = this.paginateAndSortPatients(request, response);
		}
		if (null != lstResults && !lstResults.isEmpty()) {
			// Generate JSON array of patient search results for paging.
			final List<String> jsonStrArray = this
					.generateSearchPatJSON(lstResults);
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
		PERF_LOGGER.info("End of PatientProcessor.paginatePatientList");
	}
//	
//	/**
//	 * This method is used to get patient details.
//	 * 
//	 * @param request
//	 *            - Holds the HttpServletRequest.
//	 * @param response
//	 *            - Holds the HttpServletResponse.
//	 */
//	private void getPatientDetails(final HttpServletRequest request,
//			final HttpServletResponse response) {
////		System.out.println("$$$ get patient details $$ ");
//		PERF_LOGGER.info("Start of PatientProcessor.getPatientDetails");
//		PatientServiceImpl patientService = new PatientServiceImpl();
//		Patient patResult = new Patient();
//		long facilityId = 0l;
//		long spectraMRN = 0l;
//		try {
//			patResult = patientService
//					.getPatientDetails(facilityId, spectraMRN);
//		} catch (BusinessException businessException) {
//			final StringWriter stringWriter = new StringWriter();
//			businessException.printStackTrace(new PrintWriter(stringWriter));
//			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
//					+ stringWriter.toString());
//		}
//		PERF_LOGGER.info("Start of PatientProcessor.getPatientDetails");
//	}

	/**
	 * This method is used to get the patient requisition details.
	 * 
	 * @param request
	 *            - Holds the HttpServletRequest.
	 * @param response
	 *            - Holds the HttpServletResponse.
	 */
	private void getPatientReqInfo(final HttpServletRequest request,
			final HttpServletResponse response) {
		PERF_LOGGER.info("Start of PatientProcessor.getPatientDetails");
		PatientServiceImpl patientService = new PatientServiceImpl();
		Patient patResult = new Patient();
		String facilityNum = "";
		String requisitionNum = "";
		try {
			patResult = patientService.getPatientReqInfo(facilityNum,
					requisitionNum);
		} catch (BusinessException businessException) {
			final StringWriter stringWriter = new StringWriter();
			businessException.printStackTrace(new PrintWriter(stringWriter));
			LOGGER.log(Level.INFO, ExceptionConstants.BUSINESS_EXCEPTION + "\n"
					+ stringWriter.toString());
		}
		PERF_LOGGER.info("Start of PatientProcessor.getPatientDetails");
	}
}
