/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.service;

import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.businessobject.EquipmentBOFactory;
//import com.spectra.symfonielabs.businessobject.OrderBOFactory;
import com.spectra.symfonielabs.domainobject.Accession;
//import com.spectra.symfonielabs.businessobject.PatientBOFactory;
import com.spectra.symfonielabs.domainobject.Equipment;
//import com.spectra.symfonielabs.domainobject.OrderSummary;		//<===================
import com.spectra.symfonielabs.domainobject.OrderEquipmentSummary;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;
//import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.Search;

import java.util.Date;
import java.util.List;

/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class EquipmentServiceImpl implements EquipmentService {

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
		
		// System.out.println("## 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7#");
	    // System.out.println("@@ EquipmentServiceImpl. <getSearchResult> : [[[EquipmentBOFactory]]] equipmentSearch = " + equipmentSearch);
		// System.out.println("## 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7#");
								
		List<Equipment> searchRes = EquipmentBOFactory.getEquipmentBO().getSearchResult(equipmentSearch);
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
	public Equipment getEquipmentDetails(final long facilityId,	final String spectraMRN) throws BusinessException {
		
		// System.out.println("## 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5#");
	    // System.out.println("@@ EquipmentServiceImpl. <getEquipmentDetails> : [[[EquipmentBOFactory]]] spectraMRN = "+spectraMRN);
		// System.out.println("## 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5#");
		
		Equipment equipmentRes = EquipmentBOFactory.getEquipmentBO().getEquipmentDetails(facilityId, spectraMRN);
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
	public Equipment getEquipmentReqInfo(final String facilityNum,final String requisitionNum) throws BusinessException {
		
		// System.out.println("## 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5#");
	    // System.out.println("@@ EquipmentServiceImpl. <getEquipmentReqInfo> : [[[EquipmentBOFactory]]] requisitionNum = "+requisitionNum);
		// System.out.println("## 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5#");
		
		Equipment equipmentRes = EquipmentBOFactory.getEquipmentBO().getEquipmentReqInfo(facilityNum, requisitionNum);
		return equipmentRes;
	}
	
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
		
		// System.out.println("## E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E#");
	    // System.out.println("@@ EquipmentServiceImpl. <getOrderSummary> : facilityId, spectraMRN, drawDate = "+facilityId + ", "+ spectraMRN+", "+drawDate);
		
		List<OrderEquipmentSummary> orderSummaryLst = EquipmentBOFactory.getEquipmentBO().getOrderSummary(facilityId, spectraMRN, drawDate, orderSumm);
		
		// List<OrderSummary> orderSummaryLst = OrderBOFactory.getOrderBO().getOrderSummary(facilityId, spectraMRN, drawDate, orderSumm);
	    // System.out.println("@@ EquipmentServiceImpl. <getOrderSummary> : orderSummaryLst = "+ orderSummaryLst.toString());
		// System.out.println("## E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E E#");

		return orderSummaryLst;
	}

	/**
	 * This method is used to get the tube summary details.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of object OrderSummary.
	 */
	public List<Accession> getTubeSummary(final String requisitionNo) throws BusinessException { 
		
		List<Accession> tubeSummaryLst = EquipmentBOFactory.getEquipmentBO().getTubeSummary(requisitionNo);
		
		// List<Accession> tubeSummaryLst = OrderBOFactory.getOrderBO().getTubeSummary(requisitionNo);
		
		return tubeSummaryLst;
	}

	/**
	 * This method is used to get the order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results object.
	 */
	public List<Results> getOrderTestDetails(final String requisitionNo) throws BusinessException {
		
		List<Results> resultsLst = EquipmentBOFactory.getEquipmentBO().getOrderTestDetails(requisitionNo);
		
		// List<Results> resultsLst = OrderBOFactory.getOrderBO().getOrderTestDetails(requisitionNo);
	
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
	public RequisitionDetails getPatientReqDetails(final String facilityNum, final String requisitionNum) throws BusinessException{
		
		RequisitionDetails patientRes = EquipmentBOFactory.getEquipmentBO().getPatientReqInfo(facilityNum, requisitionNum);
		//RequisitionDetails patientRes = OrderBOFactory.getOrderBO().getPatientReqInfo(facilityNum, requisitionNum);
			
		return patientRes;
	};	
	
}
