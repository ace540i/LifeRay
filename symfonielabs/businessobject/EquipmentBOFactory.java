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
public class EquipmentBOFactory {

   /**
     * Method which will return a PatientBO object.
     * 
     * @return PatientBO object
     */
    public static EquipmentBO getEquipmentBO() {
        return (EquipmentBO) Proxy.newProxyInstance(
        		EquipmentBO.class.getClassLoader(),
                new Class[] {EquipmentBO.class }, new ProxyHandler(
                        new EquipmentBOImpl()));
    }
}
