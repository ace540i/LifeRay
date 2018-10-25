/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.Equipment;
import com.spectra.symfonielabs.domainobject.OrderEquipmentSummary;
//import com.spectra.symfonielabs.domainobject.OrderSummary;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;
import com.spectra.symfonielabs.domainobject.Search;

import java.util.Date;
import java.util.List;

/**
 * This interface contains methods that is used to have patient related
 * activities.
 * 
 */
public interface EquipmentBO {

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
	List<Equipment> getSearchResult(final Search equipmentSearch) throws BusinessException;
	
	/**
	 * This method is used to display patient details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @return An object of PatientResult.
	 */
	
	//	public Equipment getEquipmentDetails(final long facilityId,
	//			final long spectraMRN) throws BusinessException;
	
	public Equipment getEquipmentDetails(final long facilityId,
			final String spectraMRN) throws BusinessException;
	
	
	/**
	 * This method is used to display requisition patient details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of PatientResult.
	 */
	public Equipment getEquipmentReqInfo(final String facilityNum,
			final String requisitionNum) throws BusinessException;
	
	/**
	 * This method is used to get all the order details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @param drawDate
	 *            - Holds the draw date.
	 * @return A list of OrderSummary object.
	 */
	List<OrderEquipmentSummary> getOrderSummary(final long facilityId,
			final long spectraMRN, final Date drawDate, final OrderEquipmentSummary orderSumm)
			throws BusinessException;

	/**
	 * This method is used to get the tube summary details.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition id.
	 * @return A list of object OrderSummary.
	 */
	List<Accession> getTubeSummary(final String requisitionNo)
			throws BusinessException;

	/**
	 * This method is used to get the order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results object.
	 */
	List<Results> getOrderTestDetails(final String requisitionNo)
			throws BusinessException;
	
	/**
	 * This method is used to display requisition patient details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of RequisitionDetails.
	 */
	RequisitionDetails getPatientReqInfo(final String facilityNum,
			final String requisitionNum) throws BusinessException;	

}
