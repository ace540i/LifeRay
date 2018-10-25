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
public class OrderBOFactory {

	/**
	 * Method which will return a OrderBO object.
	 * 
	 * @return OrderBO object
	 */
	public static OrderBO getOrderBO() {
		return (OrderBO) Proxy.newProxyInstance(OrderBO.class.getClassLoader(),
				new Class[] { OrderBO.class }, new ProxyHandler(
						new OrderBOImpl()));
	}
}
