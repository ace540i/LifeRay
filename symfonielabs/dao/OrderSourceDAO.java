/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.dao;

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
public interface OrderSourceDAO {

	/**
	 * This method is used to get all the order details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @param drawDate
	 *            - Holds the draw date.
	 * @return A list of OrderSource object.
	 */
	List<OrderSource> getOrderSource(final long facilityId,
			final long spectraMRN, final Date drawDate, final OrderSource orderSource, final String patType);
	
	/**
	 * This method is used to get the tube source details.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of object OrderSource.
	 */
//	List<Accession> getTubeSource(final String requisitionNo);
	
	/**
	 * This method is used to get the order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results.
	 */
 	List<OrderSource> getOrderTestDetails(final String requisitionNo);
 	
	/**
	 * This method is used to get the order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results.
	 */
//	List<Results> getMicroOrderTestDetails(final String requisitionNo);

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
			final String requisitionNum);

//	RequisitionDetails getEnvReqInfo(final String facilityNum,
//			final String requisitionNum);

//	List<Results> getReportTestDetails(final String requisitionNo,
//			final String reportGroupVal);
	
//	List<Results> getStatusTestDetails(final String requisitionNo,
//			final String reportGroupVal);
}
