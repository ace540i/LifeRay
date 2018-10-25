/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.dao;

import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.domainobject.FacilityDemographics;
import com.spectra.symfonielabs.domainobject.FacilitySearch;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Search;

import java.util.List;

/**
 * This contains the implementation methods that are available in the 
 * interface. 
 * 
 */
public interface FacilityDAO {

	/**
     * Gets the search results for the facility.
     * 
     * @param searchName -
     *            Holds the search facility name.
     * @return List<Search> - Holds the list of search details.
     * 
     */
	List<Search> getfacilities(final String searchName);
	/**
	 * Gets the dashboard details based on the lab type and requisition type.
	 * 
	 * @param labComboVal
	 *            - Holds the laboratory type.
	 * @param reqTypeComboVal
	 *            - Holds the requisition type.
	 * @return List<RequisitionDetails> - Holds the list of search details.
	 */
	List<RequisitionDetails> getDashboardDetails(final String labComboVal,
			final String reqTypeComboVal);
	/**
	 * Gets the list of corporations based on the entered search criteria.
	 * 
	 * @param searchName
	 *            - Holds the corporation name to be searched.
	 * @return List<Search> - Holds the list of search details.
	 * @throws BusinessException
	 */
	List<Search> getCorporations(final String searchName);
	/**
	 * Gets the facility search results based on the entered corporation name
	 * and facility name.
	 * 
	 * @param corporationId
	 *            - Holds the corporation Id.
	 * @param corporationName
	 *            - Holds the corporation name.
	 * @param facilityName
	 *            - Holds the facility name.
	 * @return List<FacilitySearch> - Holds the list of facility search details.
	 * @throws BusinessException
	 */
	List<FacilitySearch> getFacSearchResults(final Search searchCriteria);
	
	FacilityDemographics getFacDemographics(final String hLABNum);

	List<FacilityDemographics> getFacDemoAccountLst(final String facilityId);
	
	List<FacilityDemographics> getFacDemoScheduleLst(final String facilityId);

	/**
	 * Gets the facility level graph details based on the patient type sub group
	 * (requisition type).
	 * 
	 * @param reqTypeComboVal
	 *            - Holds the requisition type.
	 * @param facilityIdVal
	 *            - Holds the facility Id Value.
	 * @return List<RequisitionDetails> - Holds the list of graph details.
	 */
	List<RequisitionDetails> getFacLevelGraphDetails(
			final String reqTypeComboVal, final String facilityIdVal,final String drawDateVal);
	
	List<RequisitionDetails> getFacRequisitionDetails(
			final String reqTypeComboVal, final String facilityIdVal,final String drawDate,final String patType);
}
