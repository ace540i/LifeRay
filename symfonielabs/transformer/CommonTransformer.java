package com.spectra.symfonielabs.transformer;

import java.util.logging.Logger;

import com.spectra.symfonie.common.constants.CommonConstants;
import com.spectra.symfonie.common.util.StringUtil;
import com.spectra.symfonie.framework.logging.ApplicationRootLogger;

/**
 * 
 * @author SKudithipudi
 * Transforms data from one format to another.
 *
 */
public class CommonTransformer {

    /**
     * Logger for this class.
     */
    private static final Logger logger = ApplicationRootLogger
            .getLogger(CommonTransformer.class.getName());
    
    /**
     * Transforms given value to JSON format.
     */
    public static final String parseToJSON(String value) {
    
    	String tValue = StringUtil.valueOf(value);
    	//translate only if the value is not null or empty
		if (!CommonConstants.EMPTY_STRING.equals(tValue)) {
			// All the new line and carriage return characters are entered together in the result comments. 
			// So, parsing one of these two characters yields the same results in GUI.
			// Some of the report notes has comma's and ExtJS is throwing "Unable to parse JSON returned by Server." 
			value = value.replaceAll("\\n", CommonConstants.EMPTY_STRING)
				.replaceAll("\\r", "<br>")
				.replaceAll(",", "\\,")						
				.replaceAll("\"", CommonConstants.ESCAPE_DOUBLE_QUOTES);
		}
    	
    	return value;
    
    }

    /**
     * Transforms given value to JSON format.
     */
    public static final String escapeToJSON(String value) {
    
    	String tValue = StringUtil.valueOf(value);
    	//translate only if the value is not null or empty
		if (!CommonConstants.EMPTY_STRING.equals(tValue)) {
			// All the new line and carriage return characters are entered together in the result comments. 
			// So, parsing one of these two characters yields the same results in GUI.
			// Some of the report notes has comma's and ExtJS is throwing "Unable to parse JSON returned by Server." 
			value = value.replaceAll("\n", "\\\\n")
				.replaceAll("\r", "\\\\r\\\\n")
				.replaceAll("\"", CommonConstants.ESCAPE_DOUBLE_QUOTES);
		}
    	
    	return value;
    
    }

}
