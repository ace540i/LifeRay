/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New BO Class for Staff. 
 */
/* =================================================================== */

package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.framework.util.ProxyHandler;

import java.lang.reflect.Proxy;

/**.
 * The StaffBO class which gives the object for accessing
 * the business objects of the staff module.
 */
public class StaffBOFactory {

   /**
     * Method which will return a StaffBO object.
     * 
     * @return StaffBO object
     */
    public static StaffBO getStaffBO() {
        return (StaffBO) Proxy.newProxyInstance(
        		StaffBO.class.getClassLoader(),
                new Class[] {StaffBO.class }, new ProxyHandler(
                        new StaffBOImpl()));
    }
}
