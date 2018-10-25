/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New Service Impl Class for Staff Orders. 
 */
/* ===================================================================*/
package com.spectra.symfonielabs.service;

import com.spectra.symfonie.framework.exception.BusinessException;
//import com.spectra.symfonielabs.businessobject.OrderBOFactory;
import com.spectra.symfonielabs.businessobject.OrderStaffBOFactory;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.OrderStaffSummary;
//import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;
import com.spectra.symfonielabs.domainobject.StaffRequisitionDetails;

import java.util.Date;
import java.util.List;

/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class OrderStaffServiceImpl implements OrderStaffService {

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
	public List<OrderStaffSummary> getOrderStaffSummary(final long facilityId,
			final long spectraMRN, final Date drawDate,
			final OrderStaffSummary orderSumm)
			throws BusinessException {
		List<OrderStaffSummary> orderStaffSummaryLst = OrderStaffBOFactory.getOrderStaffBO()
				.getOrderStaffSummary(facilityId, spectraMRN, drawDate, orderSumm);
		return orderStaffSummaryLst;
	}

	/**
	 * This method is used to get the tube summary details.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of object OrderSummary.
	 */
	public List<Accession> getStaffTubeSummary(final String requisitionNo)
			throws BusinessException {
		List<Accession> tubeSummaryLst = OrderStaffBOFactory.getOrderStaffBO()
				.getTubeStaffSummary(requisitionNo);
		return tubeSummaryLst;
	}

	/**
	 * This method is used to get the order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results object.
	 */
	public List<Results> getOrderStaffTestDetails(final String requisitionNo)
			throws BusinessException {
		List<Results> resultsLst = OrderStaffBOFactory.getOrderStaffBO()
				.getOrderStaffTestDetails(requisitionNo);
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
	public StaffRequisitionDetails getPatientStaffReqDetails(final String facilityNum,
			final String requisitionNum) throws BusinessException{
		StaffRequisitionDetails staffRes = OrderStaffBOFactory.getOrderStaffBO()
				.getReqStaffInfo(facilityNum, requisitionNum);
		return staffRes;
	}

 
}
