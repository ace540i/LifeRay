package com.spectra.symfonielabs.processor;
import com.liferay.portal.kernel.util.StringUtil;
import com.spectra.symfonie.common.constants.CommonConstants;
import com.spectra.symfonie.framework.processor.Processor;
import com.spectra.symfonielabs.activedirectory.GetProperties;
import com.spectra.symfonielabs.activedirectory.SsoClass;
import com.spectra.symfonielabs.activedirectory.SsoWClass;
import com.spectra.symfonielabs.common.processor.SearchUtilityProcessor;
import com.spectra.symfonielabs.constants.FacilityConstants;

import java.util.Properties;

import javax.naming.directory.Attributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActiveDirProcessor extends SearchUtilityProcessor implements Processor {
   	
	public final void process(final HttpServletRequest request,
			final HttpServletResponse response, final String action)  {	
		if (action != null) {
			if (FacilityConstants.GET_USER_PHONE.equalsIgnoreCase(action)) {
				this.getUserPhone(request, response);
		
			}
		 }
		}
		
	public  void getUserPhone(final HttpServletRequest request,
			final HttpServletResponse response) {	
		String username =  null;
		   username = request.getSession().getAttribute("userName").toString();
  	 String ext = CommonConstants.EMPTY_STRING; 	
 	 GetProperties getProp = new GetProperties();
	 Properties prop = getProp.getProperties();
 	 
  	  	  String cn = "";
  	  	  String eastWest = null;
          SsoClass sso   = new SsoClass();	
          SsoWClass ssow = new SsoWClass();
          Attributes att = sso.get_attributes(username,prop);     
          eastWest = "e";
          
          if (att == null)
          	{    
                eastWest = "w";
                att = ssow.get_attributes(username,prop);
               
          	}
      if (att.get("telephonenumber") != null){
          if (eastWest.equalsIgnoreCase("e")){
        	  cn = sso.parse_attributes();
       } else{
    	   	  cn = ssow.parse_attributes();
       }
     }else 
 	{
       System.out.println("User-id (network-id) is invalid or has no Phone Extention...");  
 	}
      ext = (String) request.getParameter("ext");
           	  if (null == request.getSession().getAttribute("phoneExt") || CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("phoneExt")))) {
           		  request.getSession().setAttribute("phoneExt", cn);       		  
        	  }  
        	  if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getParameter("ext"))) && !CommonConstants.EMPTY_STRING.equalsIgnoreCase(ext) && ext != null) {     			  
        		  request.getSession().setAttribute("phoneExt", ext);
        	 }
        	   
          sso = null;      
    } 
}
