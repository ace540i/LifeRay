/* =================================================================== */
/* 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New Domain Object Class for Staff Requisition Details. 
 */
/* =================================================================== */
package com.spectra.symfonielabs.domainobject;

import java.io.Serializable;
import java.util.Date;


/**
 * This is a domain object class which holds the tube type summary details.
 * 
 */
public class StaffRequisitionDetails implements Serializable  {

	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Holds the accession number.
	 */
	private String requisition;

	/**
	 * Holds the staff patient.
	 */
	private Staff staff;
	
	/**
	 * Holds the draw date.
	 */
	private Date drawDate;
	
	/**
	 * Holds the ordering system.
	 */
	private String orderSystem;
	
	/**
	 * Holds the frequency.
	 */
	private String frequency;
	
	/**
	 * Holds the number of test.
	 */
	private int noOfTest;

	/**
	 * Holds the status.
	 */
	private String status;
	
	/**
	 * Holds the abnormal flag.
	 */
	private boolean abnormalFlag;
	/**
	 * Holds the cancelled test indicator.
	 */
	private boolean cancelledTestIndicator;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrderSystem() {
		return orderSystem;
	}

	public String getFrequency() {
		return frequency;
	}

	public int getNoOfTest() {
		return noOfTest;
	}


	public String getRequisition() {
		return requisition;
	}

	public Staff getStaff() {
		return staff;
	}

	public Date getDrawDate() {
		return drawDate;
	}

	public String getStatus() {
		return status;
	}

	public StaffRequisitionDetails() {
		super();
	}
	
	public boolean getAbnormalFlag() {
		return abnormalFlag;
	}
	
	public boolean isCancelledTestIndicator() {
		return cancelledTestIndicator;
	}

	public StaffRequisitionDetails(final String requisition, final Staff staff,
			final Date drawDate, final String status, final String orderSystem,
			final String frequency, final int noOfTest, 
			final boolean abnormalFlag, final boolean cancelledTestIndicator) {
		super();
		this.requisition = requisition;
		this.staff = staff;
		this.drawDate = drawDate;
		this.status = status;
		this.orderSystem = orderSystem;
		this.frequency = frequency;
		this.noOfTest = noOfTest;
		this.abnormalFlag = abnormalFlag;
		this.cancelledTestIndicator = cancelledTestIndicator;
	}

	@Override
	public String toString() {
		return "requisition: " + requisition + "~|~" + "patient: " + staff
				+ "~|~" + "drawDate: " + drawDate + "~|~" + "status: " + status
				+ "~|~" + "orderSystem: " + orderSystem + "~|~" + "frequency: "
				+ frequency + "~|~" + "noOfTest: " + noOfTest + "~|~"
				+ "abnormalFlag: " + abnormalFlag + "~|~"
				+ "cancelledTestIndicator: " + cancelledTestIndicator;
	}
}
