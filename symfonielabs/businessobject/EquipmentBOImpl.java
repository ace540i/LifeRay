/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.businessobject;


import com.spectra.symfonie.dataaccess.util.DAOFactory;
import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.constants.EquipmentConstants;
import com.spectra.symfonielabs.dao.EquipmentDAO;
//import com.spectra.symfonielabs.dao.OrderDAO;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.Equipment;
import com.spectra.symfonielabs.domainobject.OrderEquipmentSummary;
//import com.spectra.symfonielabs.domainobject.OrderSummary;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;
//import com.spectra.symfonielabs.constants.PatientConstants;
//import com.spectra.symfonielabs.dao.PatientDAO;
//import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.Search;

import java.util.Date;
import java.util.List;


/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class EquipmentBOImpl implements EquipmentBO {

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
	public List<OrderEquipmentSummary> getOrderSummary(final long facilityId, final long spectraMRN, final Date drawDate, final OrderEquipmentSummary orderSumm)
			throws BusinessException {

		//		// System.out.println("## F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F#");
		//	    // System.out.println("@@ OrderBOImpl. <getOrderSummary> : facilityId, spectraMRN, drawDate = "+facilityId + ", "+ spectraMRN+", "+drawDate);
		//		
		//		OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOImpl("OrderDAO");
		//		List<OrderSummary> orderSummaryLst = orderDAO.getOrderSummary(
		//				facilityId, spectraMRN, drawDate, orderSumm);
		//		
		//	    // System.out.println("@@ OrderBOImpl. <getOrderSummary> : orderSummaryLst = "+orderSummaryLst.toString());
		//		// System.out.println("## F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F#");
		// System.out.println("## #");		
		// System.out.println("## F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F#");
	    // System.out.println("@@ EquipmentBOImpl. <getOrderSummary> : facilityId, spectraMRN, drawDate = "+facilityId + ", "+ spectraMRN+", "+drawDate);
		
	    EquipmentDAO equipmentDAO = (EquipmentDAO) DAOFactory.getDAOImpl("EquipmentDAO");
		List<OrderEquipmentSummary> orderSummaryLst = equipmentDAO.getOrderSummary(facilityId, spectraMRN, drawDate, orderSumm);

	    // System.out.println("@@ EquipmentBOImpl. <getOrderSummary> : orderSummaryLst = "+orderSummaryLst.toString());
		// System.out.println("## F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F F#");		
		// System.out.println("## #");		
		
		return orderSummaryLst;
	}	
	
	
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
	public List<Equipment> getSearchResult(final Search equipmentSearch) throws BusinessException {
		
	//	 System.out.println("## WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW ##");
	//     System.out.println("@@ START : EquipmentBOImpl. <getSearchResult> : searchType:  " + equipmentSearch );
	//	 System.out.println("## WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW ##");
	    
		EquipmentDAO equipmentDAO = (EquipmentDAO) DAOFactory.getDAOImpl(EquipmentConstants.EQUIPMENT_DAO);
		List<Equipment> searchRes = equipmentDAO.getSearchResult(equipmentSearch);
		
	//	 System.out.println("## WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW ##");
	 //    System.out.println("@@ END  : EquipmentBOImpl. <getSearchResult> : searchType:  " + equipmentSearch );
	//	 System.out.println("## WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW ##");
		
		return searchRes;
	}
	
    
	/**
	 * This method is used to display patient details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @return An object of PatientResult.
	 */
	public Equipment getEquipmentDetails(final long facilityId, final String spectraMRN) throws BusinessException {
		
		// System.out.println("## WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW ##");
	    // System.out.println("@@ START : EquipmentBOImpl. <getEquipmentDetails> : facilityId, spectraMRN:  " + facilityId +", "+spectraMRN );
		// System.out.println("## WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW ##");
		
		EquipmentDAO equipmentDAO = (EquipmentDAO) DAOFactory.getDAOImpl(EquipmentConstants.EQUIPMENT_DAO);
		Equipment equipmentRes = equipmentDAO.getEquipmentDetails(facilityId, spectraMRN);
		
	    // System.out.println("@@ START : EquipmentBOImpl. <getEquipmentDetails> : equipmentRes :  " + equipmentRes  );
		// System.out.println("## WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW ##");
		
		return equipmentRes;
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
	public Equipment getEquipmentReqInfo(final String facilityNum, final String requisitionNum) throws BusinessException {
		
		// System.out.println("## WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW ##");
	    // System.out.println("@@ START : EquipmentBOImpl. <getEquipmentReqInfo> : facilityId, requisitionNum:  " + facilityNum +", "+requisitionNum );
		// System.out.println("## WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW ##");
		
		EquipmentDAO equipmentDAO = (EquipmentDAO) DAOFactory.getDAOImpl(EquipmentConstants.EQUIPMENT_DAO);
		Equipment equipmentRes = equipmentDAO.getEquipmentReqInfo(facilityNum,requisitionNum);
		return equipmentRes;
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
			EquipmentDAO orderDAO = (EquipmentDAO) DAOFactory.getDAOImpl("OrderDAO");
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
		public List<Results> getOrderTestDetails(final String requisitionNo)
				throws BusinessException {
			EquipmentDAO orderDAO = (EquipmentDAO) DAOFactory.getDAOImpl("OrderDAO");
			List<Results> lstResults = orderDAO
					.getOrderTestDetails(requisitionNo);
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
			EquipmentDAO orderDAO = (EquipmentDAO) DAOFactory.getDAOImpl("EquipmentDAO");
			RequisitionDetails patientRes = orderDAO
					.getPatientReqInfo(facilityNum,requisitionNum);
			return patientRes;
		}

}
