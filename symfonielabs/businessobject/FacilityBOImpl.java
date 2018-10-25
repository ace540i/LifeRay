/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.dataaccess.util.DAOFactory;
import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.constants.FacilityConstants;
import com.spectra.symfonielabs.dao.FacilityDAO;
import com.spectra.symfonielabs.domainobject.FacilityDemographics;
import com.spectra.symfonielabs.domainobject.FacilitySearch;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Search;
import java.util.List;

/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class FacilityBOImpl implements FacilityBO {

	/**
	 * Gets the search results for the facility.
	 * 
	 * @param searchName
	 *            - Holds the searched facility name.
	 * @return List<Search> - Holds the list of search details.
	 * @throws BusinessException
	 */
	public List<Search> getfacilities(final String searchName)
			throws BusinessException {
		FacilityDAO facilityDAO = (FacilityDAO) DAOFactory
				.getDAOImpl(FacilityConstants.FACILITY_DAO);
		List<Search> facDetLst = facilityDAO.getfacilities(searchName);
		return facDetLst;
	}
	/**
	 * Gets the dashboard details based on the lab type and requisition type.
	 * 
	 * @param labComboVal
	 *            - Holds the laboratory type.
	 * @param reqTypeComboVal
	 *            - Holds the requisition type.
	 * @return List<RequisitionDetails> - Holds the list of search details.
	 */
	public List<RequisitionDetails> getDashboardDetails(
			final String labComboVal, final String reqTypeComboVal) {
		FacilityDAO facilityDAO = (FacilityDAO) DAOFactory
				.getDAOImpl(FacilityConstants.FACILITY_DAO);
		List<RequisitionDetails> reqDetailsLst = facilityDAO
				.getDashboardDetails(labComboVal, reqTypeComboVal);
		return reqDetailsLst;
	}
	/**
	 * Gets the list of corporations based on the entered search criteria.
	 * 
	 * @param searchName
	 *            - Holds the corporation name to be searched.
	 * @return List<Search> - Holds the list of search details.
	 * @throws BusinessException
	 */
	public List<Search> getCorporations(final String searchName) {
		FacilityDAO facilityDAO = (FacilityDAO) DAOFactory
				.getDAOImpl(FacilityConstants.FACILITY_DAO);
		List<Search> corpDetailsLst = facilityDAO.getCorporations(searchName);
		return corpDetailsLst;
	}
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
	public List<FacilitySearch> getFacSearchResults(final Search searchCriteria)
			throws BusinessException {
		FacilityDAO facilityDAO = (FacilityDAO) DAOFactory
				.getDAOImpl(FacilityConstants.FACILITY_DAO);
		List<FacilitySearch> facSearchLst = facilityDAO
				.getFacSearchResults(searchCriteria);
		return facSearchLst;
	}
	public FacilityDemographics getFacDemographics(final String hLABNum) {
		FacilityDAO facilityDAO = (FacilityDAO) DAOFactory
				.getDAOImpl(FacilityConstants.FACILITY_DAO);
		FacilityDemographics facilityDemographics = facilityDAO
				.getFacDemographics(hLABNum);
		return facilityDemographics;
	}
	public List<FacilityDemographics> getFacDemoAccountLst(final String facilityId)
			throws BusinessException {
		FacilityDAO facilityDAO = (FacilityDAO) DAOFactory
				.getDAOImpl(FacilityConstants.FACILITY_DAO);
		List<FacilityDemographics> facDemoAccLst = facilityDAO
				.getFacDemoAccountLst(facilityId);
		return facDemoAccLst;
	}
	public List<FacilityDemographics> getFacDemoScheduleLst(final String facilityId) {
		FacilityDAO facilityDAO = (FacilityDAO) DAOFactory
				.getDAOImpl(FacilityConstants.FACILITY_DAO);
		List<FacilityDemographics> facDemoScheduleLst = facilityDAO
				.getFacDemoScheduleLst(facilityId);
		return facDemoScheduleLst;
	}

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
	public List<RequisitionDetails> getFacLevelGraphDetails(
			final String reqTypeComboVal, final String facilityIdVal,final String drawDateVal) {
		FacilityDAO facilityDAO = (FacilityDAO) DAOFactory
				.getDAOImpl(FacilityConstants.FACILITY_DAO);
		List<RequisitionDetails> graphDetailLst = facilityDAO
				.getFacLevelGraphDetails(reqTypeComboVal, facilityIdVal,drawDateVal);
		return graphDetailLst;
	}
	
	/**
	 * Gets the facility level req details based on the patient type sub group
	 * (requisition type).
	 * 
	 * @param reqTypeComboVal
	 *            - Holds the requisition type.
	 * @param facilityIdVal
	 *            - Holds the facility Id Value.
	 * @return List<RequisitionDetails> - Holds the list of graph details.
	 */
	public List<RequisitionDetails> getFacRequisitionDetails(
			final String reqTypeComboVal, final String facilityIdVal,final String drawDate,final String patType) {
		FacilityDAO facilityDAO = (FacilityDAO) DAOFactory
				.getDAOImpl(FacilityConstants.FACILITY_DAO);
		List<RequisitionDetails> graphDetailLst = facilityDAO
				.getFacRequisitionDetails(reqTypeComboVal, facilityIdVal, drawDate, patType);
		return graphDetailLst;
	}
	
}

