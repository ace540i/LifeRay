/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.service;

import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.OrderSource;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;

import java.util.Date;
import java.util.List;

/**
 * This interface contains methods that is used to have order related
 * activities.
 * 
 */
public interface OrderSourceService {

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
	List<OrderSource> getOrderSource(final long facilityId,
			final long spectraMRN, final Date drawDate, final OrderSource orderSource,final String patType)
			throws BusinessException;

	/**
	 * This method is used to get the tube summary details.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of object OrderSummary.
	 */
//	List<Accession> getTubeSource(final String requisitionNo)
//			throws BusinessException;

	/**
	 * This method is used to get the order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results.
	 */
	List<OrderSource> getOrderTestDetails(final String requisitionNo)
			throws BusinessException;
	
//	/**
//	 * This method is used to get the micro order details based on requisition number.
//	 * 
//	 * @param requisitionNo
//	 *            - Holds the requisition number.
//	 * @return A list of Results.
//	 */
//	List<Results> getMicroOrderTestDetails(final String requisitionNo)
//			throws BusinessException;
	
	/**
	 * This method is used to display requisition patient details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of PatientResult.
	 */
	RequisitionDetails getPatientReqDetails(final String facilityNum,
			final String requisitionNum) throws BusinessException;
}
