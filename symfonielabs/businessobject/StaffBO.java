/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New BO Class for Staff. 
 */
/* ===================================================================*/
package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.domainobject.Search;
import com.spectra.symfonielabs.domainobject.Staff;

import java.util.List;

/**
 * This interface contains methods that is used to have staff related
 * activities.
 * 
 */
public interface StaffBO {

	/**
	 * Gets the search results for the staff.
	 * 
	 * @param facilityId
	 *            - Holds the facility Id.
	 * @param patName
	 *            - Holds the staff name.
	 * @return list of SearchResult - Holds the search details.
	 * @throws BusinessException
	 */
	List<Staff> getSearchResult(final Search staffSearch) throws BusinessException;
	
	/**
	 * This method is used to display staff details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @return An object of StaffResult.
	 */
	public Staff getStaffDetails(final long facilityId,
			final long spectraMRN) throws BusinessException;
	
	/**
	 * This method is used to display requisition staff details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of StaffResult.
	 */
	public Staff getStaffReqInfo(final String facilityNum,
			final String requisitionNum) throws BusinessException;

}
