/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.constants;

/**
 * This class contains the facility constants.
 *
 */
public final class FacilityConstants {

    /**
     * Default constructor.
     */
    private FacilityConstants() {

    }

    /**
     * Represents the facility DAO. It is used for lookUp.
     */
    public static final String FACILITY_DAO = "FacilityDAO";
    
    /**
     * Represents the action name for getting the facility details.
     */
    public static final String GET_FACILITY = "getFacilities";
    /**
  	 * Represents the action name for getting the phone call.
     */
    public static final String GET_PHONE = "getPhone";
    /**
  	 * Represents the action name for getting the phone call.
     */
    public static final String GET_USER_PHONE = "getUserPhone";
      
  /**
    * Represents the action name for hanging up the phone details.

    */
   public static final String HANG_UP = "hangUp";
   
   /**
    * Represents the action name for hanging up the phone details.

    */
   public static final String SHUT_DOWN = "shutDown";
   
    /**
     * Represents the action name for getting the dashboard details.
     */
    public static final String GET_DASHBOARD_DETAILS = "getDashboardDetails";
    
    /**
     * Represents the action name for getting the corporation details.
     */
    public static final String GET_CORPORATIONS = "getCorporations";
    
    /**
     * Represents the action name for getting the corporation details.
     */
    public static final String GET_FAC_SEARCH_RESULTS = "getFacSearchResults";
    
    /**
     * Represents the action name for getting the corporation details.
     */
    public static final String PAGINATE_FAC_SEARCH = "PaginateFacSearchResults";

    /**
     * Represents the action name for showing the facility demographics screen.
     */
    public static final String SHOW_FAC_DEMOGRAPHICS = "showFacilityDemographics";
    
    /**
     * Represents the action name for getting the facility level graph details.
     */
    public static final String GET_FAC_LEVEL_GRAPH = "getFacLevelGraphDetails"; 
    /**
     * Represents the action name for getting the facility level TABLE details.
     */
    public static final String GET_FAC_LEVEL_TABLE = "getFacRequisitionDetails"; 
    /**
     * Represents the constant for central time zone .
     */
    public static final String CENTRAL_TIME_ZONE = "CENTRAL";
    
    /**
     * Represents the constant for pacific time zone .
     */
    public static final String PACIFIC_TIME_ZONE = "PACIFIC";
    
    /**
     * Represents the constant for mountain time zone .
     */
    public static final String MOUNTAIN_TIME_ZONE = "MOUNTAIN";
    
    /**
     * Represents the constant for eastern time zone .
     */
    public static final String EASTERN_TIME_ZONE = "EASTERN";
    
    /**
     * Represents the constant for monday .
     */
    public static final String MONDAY = "MONDAY";
    
    /**
     * Represents the constant for tuesday .
     */
    public static final String TUESDAY = "TUESDAY";
    
    /**
     * Represents the constant for wednesday .
     */
    public static final String WEDNESDAY = "WEDNESDAY";
    
    /**
     * Represents the constant for thursday .
     */
    public static final String THURSDAY = "THURSDAY";
    
    /**
     * Represents the constant for friday .
     */
    public static final String FRIDAY = "FRIDAY";
    
    /**
     * Represents the constant for saturday .
     */
    public static final String SATURDAY = "SATURDAY";
    
	/**
	 * Represents the constant for facility search criteria .
	 */
	public static final String FAC_SEARCH_CRITERIA = "facSearchCriteria";
	
	/**
	 * Represents the constant for corporation search criteria .
	 */
	public static final String CORP_SEARCH_CRITERIA = "corpSearchCriteria";
	
	/**
	 * Represents the constant for HLAB Number session variable.
	 */
	public static final String HLAB_NUM = "hlabNum";	
	
	/**
	 * Represents the constant for search type session variable.
	 */
	public static final String SEARCH_TYPE = "searchType";
	
	/**
	 * Represents the constant for page to be loaded.
	 */
	public static final String PAGE_TO_BE_LOADED = "pageToBeLoadedAttr";

	/**
	 * Represents the constant for checking facility search.
	 */
	public static final String IS_FAC_SEARCH_FLOW = "isFacSearchFlow";
	
	/**
	 * Represents the constant for facility demographics.
	 */
	public static final String FAC_DEMO_SESSION = "facDemographicsSession";
	
	/**
	 * Represents the constant for facility demographics accounts list.
	 */
	public static final String FAC_DEMO_ACCOUNT_LST = "facDemoAccountlst";
	
	/**
	 * Represents the constant for facility demographics schedule list.
	 */
	public static final String FAC_DEMO_SCHEDULE_LST = "facDemoScheduleLst";
}
