/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/

package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.dataaccess.util.DAOFactory;
import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.constants.OrderConstants;
import com.spectra.symfonielabs.dao.OrderSourceDAO;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.OrderSource;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class OrderSourceBOImpl implements OrderSourceBO {

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
	public List<OrderSource> getOrderSource(final long facilityId,
			final long spectraMRN, final Date drawDate, final OrderSource orderSource, final String patType)
			throws BusinessException {
		OrderSourceDAO orderSourceDAO = (OrderSourceDAO) DAOFactory.getDAOImpl("OrderSourceDAO");
		List<OrderSource> orderSourceLst = orderSourceDAO.getOrderSource(
				facilityId, spectraMRN, drawDate, orderSource,patType);
		
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
//		OrderSourceDAO orderSourceDAO = (OrderSourceDAO) DAOFactory.getDAOImpl("OrderSourceDAO");
//		List<Accession> tubeSummaryLst = orderSourceDAO
//				.getTubeSource(requisitionNo);
//		
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
		OrderSourceDAO orderSourceDAO = (OrderSourceDAO) DAOFactory.getDAOImpl("OrderSourceDAO");
		List<OrderSource> lstResults = new ArrayList<OrderSource>();
//		if (OrderConstants.REPORT_HEADER.equalsIgnoreCase(reportGroupVal)) {
//			lstResults = orderSourceDAO.getOrderTestDetails(requisitionNo,
//					reportGroupVal);
////		} else if (OrderConstants.ORDER_STATUS.equalsIgnoreCase(reportGroupVal)) {
////			lstResults = orderSourceDAO.getStatusTestDetails(requisitionNo,
////					reportGroupVal);		
//		} else {
			lstResults = orderSourceDAO.getOrderTestDetails(requisitionNo);
//		}		
		return lstResults;
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
//		OrderSourceDAO orderSourceDAO = (OrderSourceDAO) DAOFactory.getDAOImpl("OrderSourceDAO");
//		List<Results> lstResults = orderSourceDAO
//				.getMicroOrderTestDetails(requisitionNo);
//		
//		return lstResults;
//	}	
	
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
		
		OrderSourceDAO orderSourceDAO = (OrderSourceDAO) DAOFactory.getDAOImpl("OrderSourceDAO");
		RequisitionDetails patientRes = new RequisitionDetails();
		patientRes = orderSourceDAO.getPatientReqInfo(facilityNum,requisitionNum);		
//		if ("ENVIRONMENTAL".equalsIgnoreCase(patientRes.getPatientTypeSubGrp())) {
//			
//			patientRes = orderSourceDAO.getEnvReqInfo(facilityNum,requisitionNum);
//		}
		
		return patientRes;
	}
}
