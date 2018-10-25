/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* =================================================================== */
package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.framework.util.ProxyHandler;

import java.lang.reflect.Proxy;

/**.
 * The FacilityBO class which gives the object for accessing
 * the business objects of the facility module.
 */
public class FacilityBOFactory {

   /**
     * Method which will return a FacilityBO object.
     * 
     * @return FacilityBO object
     */
    public static FacilityBO getFacilityBO() {
        return (FacilityBO) Proxy.newProxyInstance(
        		FacilityBO.class.getClassLoader(),
                new Class[] {FacilityBO.class }, new ProxyHandler(
                        new FacilityBOImpl()));
    }
}
