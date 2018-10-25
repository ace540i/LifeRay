/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.service;

import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.businessobject.OrderBOFactory;
import com.spectra.symfonielabs.businessobject.PatientBOFactory;
import com.spectra.symfonielabs.domainobject.Accession;
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
public class OrderServiceImpl implements OrderService {

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
	public List<OrderSummary> getOrderSummary(final long facilityId,
			final long spectraMRN, final Date drawDate,
			final OrderSummary orderSumm)
			throws BusinessException {
		List<OrderSummary> orderSummaryLst = OrderBOFactory.getOrderBO()
				.getOrderSummary(facilityId, spectraMRN, drawDate, orderSumm);
		return orderSummaryLst;
	}

	/**
	 * This method is used to get the tube summary details.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of object OrderSummary.
	 */
	public List<Accession> getTubeSummary(final String requisitionNo)
			throws BusinessException {
		List<Accession> tubeSummaryLst = OrderBOFactory.getOrderBO()
				.getTubeSummary(requisitionNo);
		return tubeSummaryLst;
	}

	/**
	 * This method is used to get the order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results object.
	 */
	public List<Results> getOrderTestDetails(final String requisitionNo,
			final String reportGroupVal)
			throws BusinessException {
		List<Results> resultsLst = OrderBOFactory.getOrderBO()
				.getOrderTestDetails(requisitionNo, reportGroupVal);
		return resultsLst;
	}
	
	/**
	 * This method is used to get the micro order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results object.
	 */
	public List<Results> getMicroOrderTestDetails(final String requisitionNo)
			throws BusinessException {
		List<Results> resultsLst = OrderBOFactory.getOrderBO()
				.getMicroOrderTestDetails(requisitionNo);
		return resultsLst;
	}	
	
	/**
	 * This method is used to display requisition patient details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of PatientResult.
	 */
	public RequisitionDetails getPatientReqDetails(final String facilityNum,
			final String requisitionNum) throws BusinessException{
		RequisitionDetails patientRes = OrderBOFactory.getOrderBO()
				.getPatientReqInfo(facilityNum, requisitionNum);
		return patientRes;
	};
}
