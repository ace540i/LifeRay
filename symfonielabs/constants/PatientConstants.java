/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.constants;

/**
 * This class contains the patient constants.
 *
 */
public final class PatientConstants {

    /**
     * Default constructor.
     */
    private PatientConstants() {

    }

    /**
     * Represents the patient DAO. It is used for lookUp.
     */
    public static final String PATIENT_DAO = "PatientDAO";
    
    /**
     * Represents the get search results string.
     */
    public static final String GET_SEARCH_RESULTS = "getSearchResult";
    
    /**
     * Represents the paginate search results string.
     */
    public static final String PAGINATE_SEARCH_RESULTS = "paginatePatientList";
    
    /**
     * Represents the get patient results string.
     */
    public static final String GET_PATIENT_RESULTS = "getPatientDetails";
    
    /**
     * Represents the get patient requisiton details string.
     */
    public static final String GET_PATIENT_REQ_DET = "getPatientReqInfo";
    
    /**
     * Represents the spectraMRN variable.
     */
    public static final String SPECTRA_MRN = "spectraMRN";
    
    /**
     * Represents the facility ID variable.
     */
    public static final String FACILITY_ID = "facilityId";
    
    /**
	 * Represents the constant for search type session variable.
	 */
	public static final String SEARCH_TYPE = "searchType";
	
	/**
	 * Represents the constant for page to be loaded.
	 */
	public static final String PAGE_TO_BE_LOADED = "pageToBeLoadedAttr";
	
	/**
	 * Represents the constant for checking the patient search flow.
	 */
	public static final String IS_PATIENT_SRCH_FLOW = "isPatientSrhFlow";
}
