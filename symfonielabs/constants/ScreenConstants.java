/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* =================================================================== */
package com.spectra.symfonielabs.constants;

/**
 * Holds the screen constants.
 */
public final class ScreenConstants {

	/**
	 * Default Constructor.
	 */
	private ScreenConstants() {

	}

	/**
	 * Constant represents the name of the processor.
	 */
	public static final String PROCESSOR_NAME = "processorName";

	/**
	 * Constant represents the action that has to be performed on the screen.
	 */
	public static final String PROCESSOR_ACTION = "processorAction";

	/**
	 * Constant represents the Key for the Banner which gets the Welcome tag
	 * from the Resource Bundle.
	 */
	public static final String WELCOME = "Welcome";

    /**
	 * Constants represents the common servelt used.
	 */
	public static final String CONTROLLER_SERVLET = "/controllerServlet";

	/**
	 * Constant represents the action which loads the Hello world details 
	 */
	public static final String LOAD_HELLOWORLD = "loadHelloWorld";
	
	/**
	 * Constant to hold the content type to set in the response.
	 */
	public static final String JSON_RESPONSE_TYPE = "text/json";

	/**
	 * Constant to hold the cache control set in the response header.
	 */
	public static final String CACHE_CONTROL = "Cache-Control";

	/**
	 * Constant to set the value for the cache control in the response header.
	 */
	public static final String NO_CACHE = "no-cache";
	
	/**
     * Holds the value indicating the lisinfo root element for json.
     */
    public static final String LIST_INFO = "ListInfo";
    
    /**
	 * Holds the constant representing destructive LogOff
	 */
	public static final String DESTRUCTIVE_LOGOFF = "destructiveLogOff";
	
}
