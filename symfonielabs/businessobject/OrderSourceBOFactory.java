/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* =================================================================== */
package com.spectra.symfonielabs.businessobject;

import com.spectra.symfonie.framework.util.ProxyHandler;

import java.lang.reflect.Proxy;

/**
 * . The OrderBO class which gives the object for accessing the business objects
 * of the order module.
 */
public class OrderSourceBOFactory {

	/**
	 * Method which will return a OrderBO object.
	 * 
	 * @return OrderBO object
	 */
	public static OrderSourceBO getOrderSourceBO() {
		return (OrderSourceBO) Proxy.newProxyInstance(OrderSourceBO.class.getClassLoader(),
				new Class[] { OrderSourceBO.class }, new ProxyHandler(
						new OrderSourceBOImpl()));
	}
}
