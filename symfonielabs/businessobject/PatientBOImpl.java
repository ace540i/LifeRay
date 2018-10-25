/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.dataaccess.util.DAOFactory;
import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.constants.PatientConstants;
import com.spectra.symfonielabs.dao.PatientDAO;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.Search;

import java.util.List;

/**
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class PatientBOImpl implements PatientBO {

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
		//System.out.println(new java.util.Date()+" - YYYY PatientBOImpl.getSearchResult @@@@   #-> patientSearch - "+patientSearch.toString());
		
		PatientDAO patientDAO = (PatientDAO) DAOFactory
				.getDAOImpl(PatientConstants.PATIENT_DAO);
		List<Patient> searchRes = patientDAO
				.getSearchResult(patientSearch);
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
//	public Patient getPatientDetails(final long facilityId, final long spectraMRN) throws BusinessException {
	public Patient getPatientDetails(final long facilityId, final long spectraMRN, final String patientDOB) throws BusinessException {

		
		//timc
		//System.out.println(new java.util.Date()+" - YYYY PatientBOImpl.getPatientDetails @@@@   #-> facilityId - "+facilityId);
		//System.out.println(new java.util.Date()+" - YYYY PatientBOImpl.getPatientDetails @@@@   #-> spectraMRN - "+spectraMRN);
	 	//System.out.println(new java.util.Date()+" - YYYY PatientBOImpl.getPatientDetails @@@@   #-> patientDOB - "+patientDOB);
		
		PatientDAO patientDAO = (PatientDAO) DAOFactory.getDAOImpl(PatientConstants.PATIENT_DAO);
		Patient patientRes = patientDAO.getPatientDetails(facilityId, spectraMRN, patientDOB);
	//	Patient patientRes = patientDAO.getPatientDetails(facilityId, spectraMRN);

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
		PatientDAO patientDAO = (PatientDAO) DAOFactory
				.getDAOImpl(PatientConstants.PATIENT_DAO);
		Patient patientRes = patientDAO
				.getPatientReqInfo(facilityNum,requisitionNum);
		return patientRes;
	}

}
