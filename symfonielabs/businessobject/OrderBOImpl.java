/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/

package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.dataaccess.util.DAOFactory;
import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.constants.OrderConstants;
import com.spectra.symfonielabs.dao.OrderDAO;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.OrderSummary;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class OrderBOImpl implements OrderBO {

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
			final long spectraMRN, final Date drawDate, final OrderSummary orderSumm)
			throws BusinessException {
		OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOImpl("OrderDAO");
		List<OrderSummary> orderSummaryLst = orderDAO.getOrderSummary(
				facilityId, spectraMRN, drawDate, orderSumm);
		
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
		OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOImpl("OrderDAO");
		List<Accession> tubeSummaryLst = orderDAO
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
	public List<Results> getOrderTestDetails(final String requisitionNo, final String reportGroupVal)
			throws BusinessException {
		OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOImpl("OrderDAO");
		List<Results> lstResults = new ArrayList<Results>();
		if (OrderConstants.REPORT_HEADER.equalsIgnoreCase(reportGroupVal)) {
			lstResults = orderDAO.getReportTestDetails(requisitionNo,
					reportGroupVal);
		} else if (OrderConstants.ORDER_STATUS.equalsIgnoreCase(reportGroupVal)) {
			lstResults = orderDAO.getStatusTestDetails(requisitionNo,
					reportGroupVal);		
		} else {
			lstResults = orderDAO.getOrderTestDetails(requisitionNo,
					reportGroupVal);
		}		
		return lstResults;
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
		OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOImpl("OrderDAO");
		List<Results> lstResults = orderDAO
				.getMicroOrderTestDetails(requisitionNo);
		
		return lstResults;
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
	public RequisitionDetails getPatientReqInfo(final String facilityNum,
			final String requisitionNum) throws BusinessException {
		
		OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOImpl("OrderDAO");
		RequisitionDetails patientRes = new RequisitionDetails();
		patientRes = orderDAO.getPatientReqInfo(facilityNum,requisitionNum);
		
		if ("ENVIRONMENTAL".equalsIgnoreCase(patientRes.getPatientTypeSubGrp())) {
			
			patientRes = orderDAO.getEnvReqInfo(facilityNum,requisitionNum);
		}
		
		return patientRes;
	}
}
