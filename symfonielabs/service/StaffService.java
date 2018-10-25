/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New Service Class for Staff. 
 */
/* ===================================================================*/
package com.spectra.symfonielabs.service;

import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.Search;
import com.spectra.symfonielabs.domainobject.Staff;

import java.util.List;

/**
 * This interface contains methods that is used to have patient related
 * activities.
 * 
 */
public interface StaffService {

	/**
	 * Gets the search results for the patient.
	 * 
	 * @param facilityId
	 *            - Holds the facility Id.
	 * @param patName
	 *            - Holds the patient name.
	 * @return list of SearchResult - Holds the search details.
	 * @throws BusinessException
	 */
	List<Staff> getSearchResult(final Search staffSearch)
			throws BusinessException;
	
	/**
	 * This method is used to display patient details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @return An object of PatientResult.
	 */
	public Staff getStaffDetails(final long facilityId,
			final long spectraMRN) throws BusinessException;
	
	/**
	 * This method is used to display requisition patient details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of PatientResult.
	 */
	public Staff getStaffReqInfo(final String facilityNum,
			final String requisitionNum) throws BusinessException;
}
