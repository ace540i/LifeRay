/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New Constants Class for Staff. 
 */
/* ===================================================================*/
package com.spectra.symfonielabs.constants;

/**
 * This class contains the staff constants.
 *
 */
public final class StaffConstants {

    /**
     * Default constructor.
     */
    private StaffConstants() {

    }

    /**
     * Represents the staff DAO. It is used for lookUp.
     */
    public static final String STAFF_DAO = "StaffDAO";
    
    /**
     * Represents the get search results string.
     */
    public static final String GET_SEARCH_RESULTS = "getSearchResult";
    
    /**
     * Represents the paginate search results string.
     */
    public static final String PAGINATE_SEARCH_RESULTS = "paginateStaffList";
    
    /**
     * Represents the get staff results string.
     */
    public static final String GET_STAFF_RESULTS = "getStaffDetails";
    
    /**
     * Represents the get staff requisiton details string.
     */
    public static final String GET_STAFF_REQ_DET = "getStaffReqInfo";

}
