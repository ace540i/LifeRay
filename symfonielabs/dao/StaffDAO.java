/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New DAO Class for Staff. 
 */
/* ===================================================================*/
package com.spectra.symfonielabs.dao;

import com.spectra.symfonielabs.domainobject.Search;
import com.spectra.symfonielabs.domainobject.Staff;

import java.util.List;

/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public interface StaffDAO {

	/**
	 * Gets the search results for the staff.
	 * 
	 * @param facilityId
	 *            - Holds the facility Id.
	 * @param patName
	 *            - Holds the staff name.
	 * @return list of SearchResult - Holds the search details.
	 * 
	 */
	List<Staff> getSearchResult(final Search staffSearch);
	
	/**
	 * This method is used to display staff details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @return An object of StaffResult.
	 */
	Staff getStaffDetails(final long facilityId,
			final long spectraMRN);
	
	/**
	 * This method is used to display requisition staff details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of StaffResult.
	 */
	Staff getStaffReqInfo(final String facilityNum,
			final String requisitionNum);

}
