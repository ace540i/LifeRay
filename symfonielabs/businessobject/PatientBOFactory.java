/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* =================================================================== */

package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.framework.util.ProxyHandler;

import java.lang.reflect.Proxy;

/**.
 * The PatientBO class which gives the object for accessing
 * the business objects of the patient module.
 */
public class PatientBOFactory {

   /**
     * Method which will return a PatientBO object.
     * 
     * @return PatientBO object
     */
    public static PatientBO getPatientBO() {
        return (PatientBO) Proxy.newProxyInstance(
        		PatientBO.class.getClassLoader(),
                new Class[] {PatientBO.class }, new ProxyHandler(
                        new PatientBOImpl()));
    }
}
