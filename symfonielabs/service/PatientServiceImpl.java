/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.service;

import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.businessobject.PatientBOFactory;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.Search;

import java.util.List;

/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class PatientServiceImpl implements PatientService {

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
	public List<Patient> getSearchResult(final Search patientSearch)
			throws BusinessException {
		
		//timc
		// System.out.println(new java.util.Date()+" - ZZZZ PatientServiceImpl.getSearchResult @@@@   #-> patientSearch - "+patientSearch.toString());
				
		List<Patient> searchRes = PatientBOFactory.getPatientBO().getSearchResult(patientSearch);
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
//timc	
//	public Patient getPatientDetails(final long facilityId,	final long spectraMRN) throws BusinessException {
	public Patient getPatientDetails(final long facilityId,	final long spectraMRN, final String patientDOB) throws BusinessException {
		
		//System.out.println(new java.util.Date()+" - ZZZZ PatientServiceImpl.getPatientDetails @@@@   #-> facilityId - "+facilityId+", spectraMRN - "+spectraMRN+", patientDOB - "+patientDOB);
		
		Patient patientRes = PatientBOFactory.getPatientBO().getPatientDetails(facilityId, spectraMRN, patientDOB);
		return patientRes;
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
	public Patient getPatientReqInfo(final String facilityNum,
			final String requisitionNum) throws BusinessException {
		Patient patientRes = PatientBOFactory.getPatientBO()
				.getPatientReqInfo(facilityNum, requisitionNum);
		return patientRes;
	}
}
