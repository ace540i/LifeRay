/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* 12/22/2015 - md - US1087 
 * 				1) New BO Impl Class for Staff Orders. 
 */
/* ===================================================================*/

package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.dataaccess.util.DAOFactory;
import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.dao.OrderStaffDAO;
import com.spectra.symfonielabs.domainobject.OrderStaffSummary;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.Results;
import com.spectra.symfonielabs.domainobject.StaffRequisitionDetails;

import java.util.Date;
import java.util.List;

/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class OrderStaffBOImpl implements OrderStaffBO {

	/**
	 * This method is used to get all the order details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 *            - Holds the draw date.
	 * @return A list of OrderStaffSummary object.
	 */
	public List<OrderStaffSummary> getOrderStaffSummary(final long facilityId,
			final long spectraMRN, final Date drawDate, final OrderStaffSummary orderSumm)
			throws BusinessException {
		OrderStaffDAO orderStaffDAO = (OrderStaffDAO) DAOFactory.getDAOImpl("OrderStaffDAO");
		List<OrderStaffSummary> orderStaffSummaryLst = orderStaffDAO.getOrderStaffSummary(
				facilityId, spectraMRN, drawDate, orderSumm);
		return orderStaffSummaryLst;
	}

	/**
	 * This method is used to get the tube summary details.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of object OrderStaffSummary.
	 */
	public List<Accession> getTubeStaffSummary(final String requisitionNo)
			throws BusinessException {		
		OrderStaffDAO orderStaffDAO = (OrderStaffDAO) DAOFactory.getDAOImpl("OrderStaffDAO");
		List<Accession> tubeSummaryLst = orderStaffDAO
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
		OrderStaffDAO orderStaffDAO = (OrderStaffDAO) DAOFactory.getDAOImpl("OrderStaffDAO");
		List<Results> lstResults = orderStaffDAO
				.getOrderStaffTestDetails(requisitionNo);
		return lstResults;
	}
	
	/**
	 * This method is used to display requisition staff details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of StaffResult.
	 */
	public StaffRequisitionDetails getReqStaffInfo(final String facilityNum,
			final String requisitionNum) throws BusinessException {
		OrderStaffDAO orderStaffDAO = (OrderStaffDAO) DAOFactory.getDAOImpl("OrderStaffDAO");
		StaffRequisitionDetails staffRes = orderStaffDAO
				.getStaffReqInfo(facilityNum,requisitionNum);
		return staffRes;
	}

 
}
