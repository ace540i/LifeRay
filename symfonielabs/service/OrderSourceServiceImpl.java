/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.service;

import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.businessobject.OrderSourceBOFactory;
import com.spectra.symfonielabs.businessobject.PatientBOFactory;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.OrderSource;
import com.spectra.symfonielabs.domainobject.OrderSummary;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;

import java.util.Date;
import java.util.List;

/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class OrderSourceServiceImpl implements OrderSourceService {

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
	public List<OrderSource> getOrderSource(final long facilityId,
			final long spectraMRN, final Date drawDate,
			final OrderSource orderSumm,final String patType)
			throws BusinessException {
		List<OrderSource> orderSourceLst = OrderSourceBOFactory.getOrderSourceBO()
				.getOrderSource(facilityId, spectraMRN, drawDate, orderSumm,patType);
		return orderSourceLst;
	}

	/**
	 * This method is used to get the tube summary details.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of object OrderSummary.
	 */
//	public List<Accession> getTubeSource(final String requisitionNo)
//			throws BusinessException {
//		List<Accession> tubeSummaryLst = OrderSourceBOFactory.getOrderSourceBO()
//				.getTubeSource(requisitionNo);
//		return tubeSummaryLst;
//	}

	/**
	 * This method is used to get the order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results object.
	 */
	public List<OrderSource> getOrderTestDetails(final String requisitionNo)
			throws BusinessException {
		List<OrderSource> resultsLst = OrderSourceBOFactory.getOrderSourceBO()
				.getOrderTestDetails(requisitionNo);
		return resultsLst;
	}
	
	/**
	 * This method is used to get the micro order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results object.
	 */
//	public List<Results> getMicroOrderTestDetails(final String requisitionNo)
//			throws BusinessException {
//		List<Results> resultsLst = OrderSourceBOFactory.getOrderSourceBO()
//				.getMicroOrderTestDetails(requisitionNo);
//		return resultsLst;
//	}	
	
	/**
	 * This method is used to display requisition patient details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of PatientResult.
//	 */
	public RequisitionDetails getPatientReqDetails(final String facilityNum,
			final String requisitionNum) throws BusinessException{
		RequisitionDetails patientRes = OrderSourceBOFactory.getOrderSourceBO()
				.getPatientReqInfo(facilityNum, requisitionNum);
		return patientRes;
	};
}
