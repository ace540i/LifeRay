/* 12/22/2015 - md - US1211
 * 			1) Added 3 methods to build Result List for Staff.
 * 				- paginageAndSortStaffs
 * 				- paginateAndSortOrdersStaff
 * 				- fetchOrderStaffData
*/

package com.spectra.symfonielabs.common.processor;


import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.spectra.symfonie.common.constants.CommonConstants;
import com.spectra.symfonie.common.util.ConstructJSONFormat;
import com.spectra.symfonie.common.util.StringUtil;
import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonie.framework.logging.ApplicationRootLogger;
import com.spectra.symfonielabs.businessobject.EquipmentBOFactory;
import com.spectra.symfonielabs.businessobject.FacilityBOFactory;
import com.spectra.symfonielabs.businessobject.OrderBOFactory;
import com.spectra.symfonielabs.businessobject.OrderSourceBOFactory;
import com.spectra.symfonielabs.businessobject.OrderStaffBOFactory;
import com.spectra.symfonielabs.businessobject.PatientBOFactory;
import com.spectra.symfonielabs.businessobject.StaffBOFactory;
import com.spectra.symfonielabs.constants.OrderConstants;
import com.spectra.symfonielabs.constants.OrderSourceConstants;
import com.spectra.symfonielabs.constants.OrderStaffConstants;
import com.spectra.symfonielabs.domainobject.Equipment;
import com.spectra.symfonielabs.domainobject.FacilitySearch;
import com.spectra.symfonielabs.domainobject.OrderEquipmentSummary;
import com.spectra.symfonielabs.domainobject.OrderSummary;
import com.spectra.symfonielabs.domainobject.OrderStaffSummary;
import com.spectra.symfonielabs.domainobject.OrderSource;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.Search;
import com.spectra.symfonielabs.domainobject.Staff;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SearchUtilityProcessor {

	
	/**
	 * Performance Logger for this class.
	 */
	private static final Logger PERF_LOGGER = ApplicationRootLogger
			.getPerformanceLogger();

/**
* The method is used to load the equipment details in paginate.
* 
* @param request -
*            HttpServletRequest.
* @param response -
*            HttpServletResponse.
* @return - return the List of PatientSearch Objects.
*/
    public List<Equipment> paginateAndSortEquipment(
		final HttpServletRequest request, final HttpServletResponse response) {
		int setStartIndex = 0;
		int setEndIndex = 0;
		int start = Integer.parseInt(String.valueOf(request
			.getParameter(CommonConstants.PAGINATION_START)));
		int rowsPerPage = Integer.parseInt(String.valueOf(request
				.getParameter(CommonConstants.PAGINATION_LIMIT)));
		List<Search> lstEquipmentResults = (List<Search>) request
				.getSession().getAttribute(CommonConstants.SEARCH_RESULTS);
		List<Equipment> tempEquipmentList = new ArrayList<Equipment>();
		int startIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.START_INDEX)));
		int endIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.END_INDEX)));
		if (lstEquipmentResults != null && !lstEquipmentResults.isEmpty()) {
			try {
				Search equipmentsearch = (Search) request
						.getSession().getAttribute(
								CommonConstants.SEARCH_PARAMETERS);
				final String sortInfo = request.getParameter("sort");
	            String sortDir = null;
				String sortField = null;
				
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(sortInfo))) {
					sortDir = ConstructJSONFormat.removeEnclosures(
							ConstructJSONFormat.getValue(sortInfo, "direction"),
							CommonConstants.DELIMITER, CommonConstants.DELIMITER);
					sortField = ConstructJSONFormat.removeEnclosures(
							ConstructJSONFormat.getValue(sortInfo, "property"),
							CommonConstants.DELIMITER, CommonConstants.DELIMITER);
				}				
	            if (sortField != null && sortDir != null) {
					equipmentsearch.setStartIndex(startIndex);
					equipmentsearch.setEndIndex(endIndex);
					equipmentsearch.setSortDirection(sortDir);
					equipmentsearch.setSortField(sortField);
					this.fetchEquipmentData(request, equipmentsearch);
				} else if (!((start + 1) >= startIndex && (start + rowsPerPage) <= endIndex)) {
					// if startindex and endindex are not in the session then
					// again a DB hit is required.
					setStartIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_START_INDEX));
					setEndIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_END_INDEX));
					equipmentsearch.setStartIndex(start + setStartIndex);
					equipmentsearch.setEndIndex(start + setEndIndex);
					this.fetchEquipmentData(request, equipmentsearch);
				}
				if (request.getSession().getAttribute(
						CommonConstants.SEARCH_RESULTS) != null) {
					List<Equipment> equipmentSearchList = (List<Equipment>) request
							.getSession().getAttribute(CommonConstants.SEARCH_RESULTS);
					// the list size is limited to 25 records else to listsize
					// if its less than 25.
					// Next 25 records are retrieved from the start value.
					for (int i = 0; i < equipmentSearchList.size(); i++) {
						Equipment searchResults = equipmentSearchList.get(i);
						// System.out.println("^^^ in for loop searchResults.getEntityIndex() " +searchResults.getEntityIndex());
						if (start + 1 == searchResults.getEntityIndex()) {
							int toIndex = (i + 25) <= equipmentSearchList.size() ? (i + 25)
									: equipmentSearchList.size();
							tempEquipmentList = equipmentSearchList.subList(i, toIndex);
						}
					}
				}
			} catch (BusinessException businessException) {
			}
		}
		return tempEquipmentList;
	}

	public List<OrderEquipmentSummary> paginateAndSortEquipmentSumm(final HttpServletRequest request, final HttpServletResponse response) {
		int setStartIndex = 0;
		int setEndIndex = 0;
		int start = Integer.parseInt(String.valueOf(request.getParameter(CommonConstants.PAGINATION_START)));
		int rowsPerPage = Integer.parseInt(String.valueOf(request.getParameter(CommonConstants.PAGINATION_LIMIT)));
		long facilityId = Long.valueOf(request.getParameter("facilityId"));
		long spectraMRN = Long.valueOf(request.getParameter("spectraMRN"));
		List<OrderEquipmentSummary> lstEquipmentResults = (List<OrderEquipmentSummary>) request.getSession().getAttribute(OrderConstants.ORDER_SUMMARY_DETAILS);
		List<OrderEquipmentSummary> tempList = new ArrayList<OrderEquipmentSummary>();
		int startIndex = Integer.parseInt(String.valueOf(request.getSession().getAttribute(CommonConstants.START_INDEX)));
		int endIndex = Integer.parseInt(String.valueOf(request.getSession().getAttribute(CommonConstants.END_INDEX)));
		if (lstEquipmentResults != null && !lstEquipmentResults.isEmpty()) {
			try {
				//OrderEquipmentSummary orderSumm = (OrderEquipmentSummary) request.getSession().getAttribute(OrderConstants.ORDER_SUMMARY_DETAILS);
				OrderEquipmentSummary orderSumm = (OrderEquipmentSummary) ((ArrayList)request.getSession().getAttribute(OrderConstants.ORDER_SUMMARY_DETAILS)).get(0);
				if (!((start + 1) >= startIndex && (start + rowsPerPage) <= endIndex)) {
					// if startindex and endindex are not in the session then
					// again a DB hit is required.
					setStartIndex = Integer.parseInt(String.valueOf(CommonConstants.SET_START_INDEX));
					setEndIndex = Integer.parseInt(String.valueOf(CommonConstants.SET_END_INDEX));
					orderSumm.setStartIndex(start + setStartIndex);
					orderSumm.setEndIndex(start + setEndIndex);
					this.fetchOrderEquipmentData(request, facilityId, spectraMRN, orderSumm);
				}
				if (request.getSession().getAttribute(OrderConstants.ORDER_SUMMARY_DETAILS) != null) {
					List<OrderEquipmentSummary> orderSummList = (List<OrderEquipmentSummary>) request.getSession().getAttribute(OrderConstants.ORDER_SUMMARY_DETAILS);
					// the list size is limited to 25 records else to listsize
					// if its less than 25.
					// Next 25 records are retrieved from the start value.
					for (int i = 0; i < orderSummList.size(); i++) {
						OrderEquipmentSummary orderSummResults = orderSummList.get(i);
						if (start + 1 == orderSummResults.getEntityIndex()) {
						int toIndex = (i + 25) <= orderSummList.size() ? (i + 25)
						: orderSummList.size();
						tempList = orderSummList.subList(i, toIndex);
						}
					}
				}
			} catch (BusinessException businessException) {
			}
		}
		return tempList;
	}

	private void fetchOrderEquipmentData(final HttpServletRequest request, final long facilityId, final long spectraMRN, OrderEquipmentSummary orderSumm)
	throws BusinessException {
		List<OrderEquipmentSummary> orderSummDetails = EquipmentBOFactory.getEquipmentBO().getOrderSummary(facilityId, spectraMRN, null, orderSumm);
		request.getSession().setAttribute(CommonConstants.START_INDEX,orderSumm.getStartIndex());
		request.getSession().setAttribute(CommonConstants.END_INDEX,orderSumm.getEndIndex());
		request.getSession().setAttribute(OrderConstants.ORDER_SUMMARY_DETAILS,
		orderSummDetails);
	}

	/**
	 * The method is used to load the patient details in paginate.
	 * 
	 * @param request -
	 *            HttpServletRequest.
	 * @param response -
	 *            HttpServletResponse.
	 * @return - return the List of PatientSearch Objects.
	 */
    public List<Patient> paginateAndSortPatients(
			final HttpServletRequest request, final HttpServletResponse response) {
    	//System.out.println(" searchUtilityProcessor.paginateAndSortPatients  SEARCH_RESULTS= "+request.getSession().getAttribute(CommonConstants.SEARCH_RESULTS).toString());
    	 
		int setStartIndex = 0;
		int setEndIndex = 0;
		int start = Integer.parseInt(String.valueOf(request
			.getParameter(CommonConstants.PAGINATION_START)));
		int rowsPerPage = Integer.parseInt(String.valueOf(request
				.getParameter(CommonConstants.PAGINATION_LIMIT)));
		List<Search> lstResults = (List<Search>) request
				.getSession().getAttribute(CommonConstants.SEARCH_RESULTS);
		List<Patient> tempList = new ArrayList<Patient>();
		int startIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.START_INDEX)));
		int endIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.END_INDEX)));
		if (lstResults != null && !lstResults.isEmpty()) {
			try {
				Search patientsearch = (Search) request
						.getSession().getAttribute(
								CommonConstants.SEARCH_PARAMETERS);
				final String sortInfo = request.getParameter("sort");
	            String sortDir = null;
				String sortField = null;
				
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(sortInfo))) {
					sortDir = ConstructJSONFormat.removeEnclosures(
							ConstructJSONFormat.getValue(sortInfo, "direction"),
							CommonConstants.DELIMITER, CommonConstants.DELIMITER);
					sortField = ConstructJSONFormat.removeEnclosures(
							ConstructJSONFormat.getValue(sortInfo, "property"),
							CommonConstants.DELIMITER, CommonConstants.DELIMITER);
				}	
	            if (sortField != null
						&& sortDir != null) {
					patientsearch.setStartIndex(startIndex);
					patientsearch.setEndIndex(endIndex);
					patientsearch.setSortDirection(sortDir);
					patientsearch.setSortField(sortField);
 				this.fetchData(request, patientsearch);
				} else if (!((start + 1) >= startIndex && (start + rowsPerPage) <= endIndex)) {
					// if startindex and endindex are not in the session then
					// again a DB hit is required.
					setStartIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_START_INDEX));
					setEndIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_END_INDEX));
					patientsearch.setStartIndex(start + setStartIndex);
					patientsearch.setEndIndex(start + setEndIndex);
					this.fetchData(request, patientsearch);
				}
				if (request.getSession().getAttribute(
						CommonConstants.SEARCH_RESULTS) != null) {
					List<Patient> patientSearchList = (List<Patient>) request
							.getSession().getAttribute(
									CommonConstants.SEARCH_RESULTS);
					// the list size is limited to 25 records else to listsize
					// if its less than 25.
					// Next 25 records are retrieved from the start value.
					for (int i = 0; i < patientSearchList.size(); i++) {
						Patient searchResults = patientSearchList.get(i);
						// System.out.println("^^^ in for loop searchResults.getEntityIndex() " +searchResults.getEntityIndex());
						if (start + 1 == searchResults.getEntityIndex()) {
							int toIndex = (i + 25) <= patientSearchList.size() ? (i + 25)
									: patientSearchList.size();
							tempList = patientSearchList.subList(i, toIndex);
						}
					}
				}
			} catch (BusinessException businessException) {
			}
		}
		return tempList;
	}
    
    //***** method for Staff ***** //
    public List<Staff> paginateAndSortStaffs(
			final HttpServletRequest request, final HttpServletResponse response) {
    	//System.out.println(" searchUtilityProcessor.paginateAndSortStaffs  SEARCH_RESULTS= "+request.getSession().getAttribute(CommonConstants.SEARCH_RESULTS).toString());
		int setStartIndex = 0;
		int setEndIndex = 0;
		int start = Integer.parseInt(String.valueOf(request
				.getParameter(CommonConstants.PAGINATION_START)));
		int rowsPerPage = Integer.parseInt(String.valueOf(request
				.getParameter(CommonConstants.PAGINATION_LIMIT)));
		List<Search> lstStaffResults = (List<Search>) request
				.getSession().getAttribute(CommonConstants.SEARCH_RESULTS);	
		List<Staff> tempStaffList = new ArrayList<Staff>();
		int startIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.START_INDEX)));
		int endIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.END_INDEX)));
		if (lstStaffResults != null && !lstStaffResults.isEmpty()) {
			try {
				Search staffSearch = (Search) request
						.getSession().getAttribute(
								CommonConstants.SEARCH_PARAMETERS);
				final String sortInfo = request.getParameter("sort");
	            String sortDir = null;
				String sortField = null;
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(sortInfo))) {
					sortDir = ConstructJSONFormat.removeEnclosures(
							ConstructJSONFormat.getValue(sortInfo, "direction"),
							CommonConstants.DELIMITER, CommonConstants.DELIMITER);
					sortField = ConstructJSONFormat.removeEnclosures(
							ConstructJSONFormat.getValue(sortInfo, "property"),
							CommonConstants.DELIMITER, CommonConstants.DELIMITER);
				}

	            if (sortField != null
						&& sortDir != null) {
	            	staffSearch.setStartIndex(startIndex);
	            	staffSearch.setEndIndex(endIndex);
	            	staffSearch.setSortDirection(sortDir);
	            	staffSearch.setSortField(sortField);
					this.fetchStaffData(request, staffSearch);
				} else if (!((start + 1) >= startIndex && (start + rowsPerPage) <= endIndex)) {
					// if startindex and endindex are not in the session then
					// again a DB hit is required.
					setStartIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_START_INDEX));
					setEndIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_END_INDEX));
					staffSearch.setStartIndex(start + setStartIndex);
					staffSearch.setEndIndex(start + setEndIndex);
					this.fetchStaffData(request, staffSearch);
				}
				if (request.getSession().getAttribute(
						CommonConstants.SEARCH_RESULTS) != null) {
					List<Staff> staffSearchList = (List<Staff>) request
							.getSession().getAttribute(
									CommonConstants.SEARCH_RESULTS);
					// the list size is limited to 25 records else to listsize
					// if its less than 25.
					// Next 25 records are retrieved from the start value.
					for (int i = 0; i < staffSearchList.size(); i++) {
						Staff searchResults = staffSearchList.get(i);
						if (start + 1 == searchResults.getEntityIndex()) {
							int toIndex = (i + 25) <= staffSearchList.size() ? (i + 25)
									: staffSearchList.size();
							tempStaffList = staffSearchList.subList(i, toIndex);
						}
					}
				}
			} 
			catch (Exception E){ 
				E.printStackTrace();
			}
		}
 	// System.out.println("tempStaffList "+tempStaffList.toString());
		return tempStaffList;

	}
    
    /**
     * This method is to fetch the details of the patient with startindex and
     * endindex.
     * 
     * @param request -
     *            HttpServletRequest request.
     * @param response -
     *            HttpServletResponse response.
     * @return PatientSearch - returns the list of PatientSearch object.
     * @throws BusinessException.
     * 
     */
    private void fetchData(final HttpServletRequest request,
            final Search patientSearch) throws BusinessException {
		List<Patient> patientSearchResults = PatientBOFactory
				.getPatientBO().getSearchResult(patientSearch);
        request.getSession().setAttribute(CommonConstants.START_INDEX,
                patientSearch.getStartIndex());
        request.getSession().setAttribute(CommonConstants.END_INDEX,
                patientSearch.getEndIndex());
        request.getSession().setAttribute(CommonConstants.SEARCH_RESULTS,
                patientSearchResults);
    }
    private void fetchEquipmentData(final HttpServletRequest request,
            final Search equipmentSearch) throws BusinessException {
	List<Equipment> equipmentSearchResults = EquipmentBOFactory
				.getEquipmentBO().getSearchResult(equipmentSearch);
        request.getSession().setAttribute(CommonConstants.START_INDEX,
        		equipmentSearch.getStartIndex());
        request.getSession().setAttribute(CommonConstants.END_INDEX,
        		equipmentSearch.getEndIndex());
        request.getSession().setAttribute(CommonConstants.SEARCH_RESULTS,
                equipmentSearchResults);
    }
      
    private void fetchStaffData(final HttpServletRequest request,
            final Search staffSearch) throws BusinessException {
		List<Staff> staffSearchResults = StaffBOFactory
				.getStaffBO().getSearchResult(staffSearch);
        request.getSession().setAttribute(CommonConstants.START_INDEX,
        		staffSearch.getStartIndex());
        request.getSession().setAttribute(CommonConstants.END_INDEX,
        		staffSearch.getEndIndex());
        request.getSession().setAttribute(CommonConstants.SEARCH_RESULTS,
                staffSearchResults);
    }
    
    public List<OrderSummary> paginateAndSortOrderSumm(
			final HttpServletRequest request, final HttpServletResponse response) {
    	int setStartIndex = 0;
		int setEndIndex = 0;
		int start = Integer.parseInt(String.valueOf(request
				.getParameter(CommonConstants.PAGINATION_START)));
		int rowsPerPage = Integer.parseInt(String.valueOf(request
				.getParameter(CommonConstants.PAGINATION_LIMIT)));
		long facilityId = Long.valueOf(request.getParameter("facilityId"));
		long spectraMRN = Long.valueOf(request.getParameter("spectraMRN"));
		List<OrderSummary> lstResults = (List<OrderSummary>) request
				.getSession()
				.getAttribute(OrderConstants.ORDER_SUMMARY_DETAILS);
		List<OrderSummary> tempList = new ArrayList<OrderSummary>();
		int startIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.START_INDEX)));
		int endIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.END_INDEX)));
		if (lstResults != null && !lstResults.isEmpty()) {
			try {
				OrderSummary orderSumm = (OrderSummary) request.getSession()
						.getAttribute("orderSearchParameters");
				if (!((start + 1) >= startIndex && (start + rowsPerPage) <= endIndex)) {
					// if startindex and endindex are not in the session then
					// again a DB hit is required.
					setStartIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_START_INDEX));
					setEndIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_END_INDEX));
					orderSumm.setStartIndex(start + setStartIndex);
					orderSumm.setEndIndex(start + setEndIndex);
					this.fetchOrderSummData(request, facilityId, spectraMRN,
							orderSumm);
				}
	            if (request.getSession().getAttribute(
						OrderConstants.ORDER_SUMMARY_DETAILS) != null) {
					List<OrderSummary> orderSummList = (List<OrderSummary>) request
							.getSession().getAttribute(
									OrderConstants.ORDER_SUMMARY_DETAILS);
					// the list size is limited to 25 records else to listsize
					// if its less than 25.
					// Next 25 records are retrieved from the start value.
					for (int i = 0; i < orderSummList.size(); i++) {
						OrderSummary orderSummResults = orderSummList.get(i);
						if (start + 1 == orderSummResults.getEntityIndex()) {
							int toIndex = (i + 25) <= orderSummList.size() ? (i + 25)
									: orderSummList.size();
							tempList = orderSummList.subList(i, toIndex);
						}
					}
				}
			} catch (BusinessException businessException) {
			}
		}
		return tempList;
	}
    
    public List<OrderSource> paginateAndSortOrderSource(
			final HttpServletRequest request, final HttpServletResponse response) {
    	int setStartIndex = 0;
		int setEndIndex = 0;
		int start = Integer.parseInt(String.valueOf(request
				.getParameter(CommonConstants.PAGINATION_START)));
		int rowsPerPage = Integer.parseInt(String.valueOf(request
				.getParameter(CommonConstants.PAGINATION_LIMIT)));
		long facilityId = Long.valueOf(request.getParameter("facilityId"));
		long spectraMRN = Long.valueOf(request.getParameter("spectraMRN"));
		String patType  = String.valueOf("patType");
		List<OrderSource> lstResults = (List<OrderSource>) request
				.getSession()
				.getAttribute(OrderSourceConstants.ORDER_SOURCE_DETAILS);
		List<OrderSource> tempList = new ArrayList<OrderSource>();
		int startIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.START_INDEX)));
		int endIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.END_INDEX)));
		if (lstResults != null && !lstResults.isEmpty()) {
			try {
				OrderSource orderSource = (OrderSource) request.getSession()
						.getAttribute("orderSourceSearchParameters");
				if (!((start + 1) >= startIndex && (start + rowsPerPage) <= endIndex)) {
					// if startindex and endindex are not in the session then
					// again a DB hit is required.
					setStartIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_START_INDEX));
					setEndIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_END_INDEX));
					orderSource.setStartIndex(start + setStartIndex);
					orderSource.setEndIndex(start + setEndIndex);
					this.fetchOrderSourceData(request, facilityId, spectraMRN,
							orderSource,patType);
				}
	            if (request.getSession().getAttribute(
						OrderSourceConstants.ORDER_SOURCE_DETAILS) != null) {
					List<OrderSource> orderSrcList = (List<OrderSource>) request
							.getSession().getAttribute(OrderSourceConstants.ORDER_SOURCE_DETAILS);
					// the list size is limited to 25 records else to listsize
					// if its less than 25.
					// Next 25 records are retrieved from the start value.
					for (int i = 0; i < orderSrcList.size(); i++) {
						OrderSource orderSrcResults = orderSrcList.get(i);
						if (start + 1 == orderSrcResults.getEntityIndex()) {
							int toIndex = (i + 25) <= orderSrcList.size() ? (i + 25)
									: orderSrcList.size();
							tempList = orderSrcList.subList(i, toIndex);
						}
					}
				}
			} catch (BusinessException businessException) {
				businessException.printStackTrace();
			}
		}
		return tempList;
	}
    
    
    public List<OrderStaffSummary> paginateAndSortOrderStaffSumm(
 			final HttpServletRequest request, final HttpServletResponse response) {
     	int setStartIndex = 0;
 		int setEndIndex = 0;
 		int start = Integer.parseInt(String.valueOf(request
 				.getParameter(CommonConstants.PAGINATION_START)));
 		int rowsPerPage = Integer.parseInt(String.valueOf(request
 				.getParameter(CommonConstants.PAGINATION_LIMIT)));
 		long facilityId = Long.valueOf(request.getParameter("facilityId"));
 		long spectraMRN = Long.valueOf(request.getParameter("spectraMRN"));
 		List<OrderStaffSummary> lstResults = (List<OrderStaffSummary>) request
 				.getSession()
 				.getAttribute(OrderConstants.ORDER_SUMMARY_DETAILS);
 		List<OrderStaffSummary> tempList = new ArrayList<OrderStaffSummary>();
 		int startIndex = Integer.parseInt(String.valueOf(request.getSession()
 				.getAttribute(CommonConstants.START_INDEX)));
 		int endIndex = Integer.parseInt(String.valueOf(request.getSession()
 				.getAttribute(CommonConstants.END_INDEX)));
 		if (lstResults != null && !lstResults.isEmpty()) {
 			try {
 				OrderStaffSummary orderSumm = (OrderStaffSummary) request.getSession()
 						.getAttribute("orderSearchParameters");
 				if (!((start + 1) >= startIndex && (start + rowsPerPage) <= endIndex)) {
 					// if startindex and endindex are not in the session then
 					// again a DB hit is required.
 					setStartIndex = Integer.parseInt(String
 							.valueOf(CommonConstants.SET_START_INDEX));
 					setEndIndex = Integer.parseInt(String
 							.valueOf(CommonConstants.SET_END_INDEX));
 					orderSumm.setStartIndex(start + setStartIndex);
 					orderSumm.setEndIndex(start + setEndIndex);
 					this.fetchOrderStaffData(request, facilityId, spectraMRN,
 							orderSumm);
 				}
 	            if (request.getSession().getAttribute(
 						OrderConstants.ORDER_SUMMARY_DETAILS) != null) {
 					List<OrderStaffSummary> orderSummList = (List<OrderStaffSummary>) request
 							.getSession().getAttribute(
 									OrderConstants.ORDER_SUMMARY_DETAILS);
 					// the list size is limited to 25 records else to listsize
 					// if its less than 25.
 					// Next 25 records are retrieved from the start value.
 					for (int i = 0; i < orderSummList.size(); i++) {
 						OrderStaffSummary orderSummResults = orderSummList.get(i);
 						if (start + 1 == orderSummResults.getEntityIndex()) {
 							int toIndex = (i + 25) <= orderSummList.size() ? (i + 25)
 									: orderSummList.size();
 							tempList = orderSummList.subList(i, toIndex);
 						}
 					}
 				}
 			} catch (BusinessException businessException) {
 			}
 		}
 		return tempList;
 	}
    
 	private void fetchOrderSummData(final HttpServletRequest request,
			final long facilityId, final long spectraMRN, OrderSummary orderSumm)
			throws BusinessException {
		List<OrderSummary> orderSummDetails = OrderBOFactory.getOrderBO()
				.getOrderSummary(facilityId, spectraMRN, null, orderSumm);
        request.getSession().setAttribute(CommonConstants.START_INDEX,
        		orderSumm.getStartIndex());
        request.getSession().setAttribute(CommonConstants.END_INDEX,
        		orderSumm.getEndIndex());
        request.getSession().setAttribute(OrderConstants.ORDER_SUMMARY_DETAILS,
        		orderSummDetails);
	}
 	private void fetchOrderSourceData(final HttpServletRequest request,
			final long facilityId, final long spectraMRN, OrderSource orderSrc, final String patType)
			throws BusinessException {
		List<OrderSource> orderSrcDetails = OrderSourceBOFactory.getOrderSourceBO()
				.getOrderSource(facilityId, spectraMRN, null, orderSrc,patType);
        request.getSession().setAttribute(CommonConstants.START_INDEX,
        		orderSrc.getStartIndex());
        request.getSession().setAttribute(CommonConstants.END_INDEX,
        		orderSrc.getEndIndex());
        request.getSession().setAttribute(OrderSourceConstants.ORDER_SOURCE_DETAILS,
        		orderSrcDetails);
	}
 	
	/**
	 * The method is used to load the facility search details in paginate.
	 * 
	 * @param request -
	 *            HttpServletRequest.
	 * @param response -
	 *            HttpServletResponse.
	 * @return - return the List of PatientSearch Objects.
	 */
    public List<FacilitySearch> paginateAndSortFacSearchRes(
			final HttpServletRequest request, final HttpServletResponse response) {
		int setStartIndex = 0;
		int setEndIndex = 0;
		int start = Integer.parseInt(String.valueOf(request
				.getParameter(CommonConstants.PAGINATION_START)));
		int rowsPerPage = Integer.parseInt(String.valueOf(request
				.getParameter(CommonConstants.PAGINATION_LIMIT)));
		List<FacilitySearch> lstResults = (List<FacilitySearch>) request
				.getSession().getAttribute(CommonConstants.SEARCH_RESULTS);
		List<FacilitySearch> tempList = new ArrayList<FacilitySearch>();
		int startIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.START_INDEX)));
		int endIndex = Integer.parseInt(String.valueOf(request.getSession()
				.getAttribute(CommonConstants.END_INDEX)));
		if (lstResults != null && !lstResults.isEmpty()) {
			try {
				Search patientsearch = (Search) request
						.getSession().getAttribute(
								CommonConstants.SEARCH_PARAMETERS);
				final String sortInfo = request.getParameter("sort");
	            String sortDir = null;
				String sortField = null;
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(sortInfo))) {
					sortDir = ConstructJSONFormat.removeEnclosures(
							ConstructJSONFormat.getValue(sortInfo, "direction"),
							CommonConstants.DELIMITER, CommonConstants.DELIMITER);
					sortField = ConstructJSONFormat.removeEnclosures(
							ConstructJSONFormat.getValue(sortInfo, "property"),
							CommonConstants.DELIMITER, CommonConstants.DELIMITER);
				}
	            if (sortField != null
						&& sortDir != null) {
					patientsearch.setStartIndex(startIndex);
					patientsearch.setEndIndex(endIndex);
					patientsearch.setSortDirection(sortDir);
					patientsearch.setSortField(sortField);
					this.fetchFacSearchData(request, patientsearch);
				} else if (!((start + 1) >= startIndex && (start + rowsPerPage) <= endIndex)) {
					// if startindex and endindex are not in the session then
					// again a DB hit is required.
					setStartIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_START_INDEX));
					setEndIndex = Integer.parseInt(String
							.valueOf(CommonConstants.SET_END_INDEX));
					patientsearch.setStartIndex(start + setStartIndex);
					patientsearch.setEndIndex(start + setEndIndex);
					this.fetchFacSearchData(request, patientsearch);
				}
				if (request.getSession().getAttribute(
						CommonConstants.SEARCH_RESULTS) != null) {
					List<FacilitySearch> patientSearchList = (List<FacilitySearch>) request
							.getSession().getAttribute(
									CommonConstants.SEARCH_RESULTS);
					// the list size is limited to 25 records else to listsize
					// if its less than 25.
					// Next 25 records are retrieved from the start value.
					for (int i = 0; i < patientSearchList.size(); i++) {
						FacilitySearch searchResults = patientSearchList.get(i);
						if (start + 1 == searchResults.getEntityIndex()) {
							int toIndex = (i + 25) <= patientSearchList.size() ? (i + 25)
									: patientSearchList.size();
							tempList = patientSearchList.subList(i, toIndex);
						}
					}
				}
			} catch (BusinessException businessException) {
			}
		}
		return tempList;
	}

	private void fetchFacSearchData(HttpServletRequest request,
			Search searchCriteria) throws BusinessException {
		List<FacilitySearch> patientSearchResults = FacilityBOFactory
				.getFacilityBO().getFacSearchResults(searchCriteria);
        request.getSession().setAttribute(CommonConstants.START_INDEX,
        		searchCriteria.getStartIndex());
        request.getSession().setAttribute(CommonConstants.END_INDEX,
        		searchCriteria.getEndIndex());
        request.getSession().setAttribute(CommonConstants.SEARCH_RESULTS,
                patientSearchResults);
	}
	//***** method for staff *******//
			private void fetchOrderStaffData(final HttpServletRequest request,
					final long facilityId, final long spectraMRN, OrderStaffSummary orderSumm)
					throws BusinessException {
				List<OrderStaffSummary> orderSummDetails = OrderStaffBOFactory.getOrderStaffBO()
						.getOrderStaffSummary(facilityId, spectraMRN, null, orderSumm);
		        request.getSession().setAttribute(CommonConstants.START_INDEX,
		        		orderSumm.getStartIndex());
		        request.getSession().setAttribute(CommonConstants.END_INDEX,
		        		orderSumm.getEndIndex());
		        request.getSession().setAttribute(OrderStaffConstants.ORDER_SUMMARY_DETAILS,
		        		orderSummDetails);
			}
}
