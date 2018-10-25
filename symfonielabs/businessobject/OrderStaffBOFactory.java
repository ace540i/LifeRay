/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* 12/22/2015 - md - US1087 
 * 				1) New BO Class for Staff Orders. 
 */
/* =================================================================== */
package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.framework.util.ProxyHandler;

import java.lang.reflect.Proxy;

/**
 * . The OrderBO class which gives the object for accessing the business objects
 * of the order module.
 */
public class OrderStaffBOFactory {

	/**
	 * Method which will return a OrderBO object.
	 * 
	 * @return OrderStaffBO object
	 */
	public static OrderStaffBO getOrderStaffBO() {
		return (OrderStaffBO) Proxy.newProxyInstance(OrderBO.class.getClassLoader(),
				new Class[] { OrderStaffBO.class }, new ProxyHandler(
						new OrderStaffBOImpl()));
	}
}
