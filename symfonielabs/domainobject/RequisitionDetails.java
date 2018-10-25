/* =================================================================== */
/* � 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* =================================================================== */
package com.spectra.symfonielabs.domainobject;

import java.io.Serializable;
import java.util.Date;


/**
 * This is a domain object class which holds the tube type summary details.
 * 
 */
public class RequisitionDetails implements Serializable  {

	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Holds the accession number.
	 */
	private String requisition;

	/**
	 * Holds the patient.
	 */
	private Patient patient;
	
	/**
	 * Holds the draw date.
	 */
	private Date drawDate;
	/**
	 * Holds the envSerialNumber  .
	 */
	private String envSerialNumber;	
	
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
	 * Holds the number of Num Of Tubes NoT Received.
	 */
	private int numOfTubesNotRec;

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
	/**
	 * Holds the collecition date.
	 */
	private Date collectionDate;
	/**
	 * Holds the requisition count.
	 */
	private long requisitionCount;
	/**
	 * Holds the patient type sub group.
	 */
	private String patientTypeSubGrp;
	/**
	 * Holds the Accession Count for Req.
	 */
	private int accessionCount;
	/**
	 * Holds the Tubes Recv count for req.
	 */
	private int tubesReceivedCount;
	/**
	 * Holds the cource location
	 */
	private String location;
	/**
	 * Holds the source locationType .
	 */
	private String locationType;
	/**
	 * Holds the source collectedBy.
	 */
	private String collectedBy;	
	/**
	 * Holds the source collected Time.
	 */
	private String collectionTime;	

	
	
	/**
	 * Holds the patient type sub group.
	 */
	private String patientType;
	
	/**
	 * Holds the Environmental requisition details.
	 */
	private EnvRequisitionDetails envRequisitionDetails;
	
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
	public int getNumOfTubesNotRec() {
		return numOfTubesNotRec;
	}

	public void setNumOfTubesNotRec(int numOfTubesNotRec) {
		this.numOfTubesNotRec = numOfTubesNotRec;
	}

	public String getRequisition() {
		return requisition;
	}

	public Patient getPatient() {
		return patient;
	}

	public Date getDrawDate() {
		return drawDate;
	}
	 
	public String getEnvSerialNumber() {
		return status;
	}
	public String getStatus() {
		return status;
	}

	public RequisitionDetails() {
		super();
	}
	
	public boolean getAbnormalFlag() {
		return abnormalFlag;
	}
	
	public boolean isCancelledTestIndicator() {
		return cancelledTestIndicator;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public long getRequisitionCount() {
		return requisitionCount;
	}

	public String getPatientTypeSubGrp() {
		return patientTypeSubGrp;
	}
	public int getAccessionCount() {
		return accessionCount;
	}
	public int getTubesReceivedCount() {
		return tubesReceivedCount;
	}
	public String getLocation() {
		return location;
	}
	public String getLocationType() {
		return locationType;
	}
	public String getCollectedBy() {
		return collectedBy;
	}
	public String getCollectionTime() {
		return collectionTime;
	}	
	
	public String getPatientType() {
		return patientType;
	}

	public EnvRequisitionDetails getEnvRequisitionDetails() {
		return envRequisitionDetails;
	}

	public RequisitionDetails(final String requisition, final Patient patient,
			final Date drawDate, final String status, final String orderSystem,
			final String frequency, final int noOfTest, 
			final boolean abnormalFlag, final boolean cancelledTestIndicator,
			final String patientTypeSubGrp,
			final EnvRequisitionDetails envRequisitionDetails, final String patientType) {
		super();
		this.requisition = requisition;
		this.patient = patient;
		this.drawDate = drawDate;
		this.status = status;
		this.orderSystem = orderSystem;
		this.frequency = frequency;
		this.noOfTest = noOfTest;
		this.abnormalFlag = abnormalFlag;
		this.cancelledTestIndicator = cancelledTestIndicator;
		this.patientTypeSubGrp = patientTypeSubGrp;
		this.envRequisitionDetails = envRequisitionDetails;
		this.patientType = patientType;
	}
	public RequisitionDetails(final String requisition, final Patient patient,
			final Date drawDate, final String status, final String orderSystem,
			final String frequency, final int noOfTest, 
			final boolean abnormalFlag, final boolean cancelledTestIndicator,
			final String patientTypeSubGrp,
			final EnvRequisitionDetails envRequisitionDetails, final int accessionCount,final int tubesReceivedCount,final int NumOfTubesNotReceived) {
		super();
		this.requisition = requisition;
		this.patient = patient;
		this.drawDate = drawDate;
		this.status = status;
		this.orderSystem = orderSystem;
		this.frequency = frequency;
		this.noOfTest = noOfTest;
		this.abnormalFlag = abnormalFlag;
		this.cancelledTestIndicator = cancelledTestIndicator;
		this.patientTypeSubGrp = patientTypeSubGrp;
		this.envRequisitionDetails = envRequisitionDetails;
		this.numOfTubesNotRec = NumOfTubesNotReceived;
		this.accessionCount = accessionCount;
		this.tubesReceivedCount = tubesReceivedCount;
	}
	
	
	/** new constructor for Source Orders ***/
//	public RequisitionDetails(final String requisition, final Patient patient,Results results,
	public RequisitionDetails(final String requisition, final Patient patient,
			final Date drawDate, final String envSerialNumber,
			final String frequency, final int noOfTest,final boolean abnormalFlag, final boolean cancelledTestIndicator,final String location, final String locationType, 
			final String collectedBy, final String collectionTime, final String patientTypeSubGrp) {
		super();
		this.requisition = requisition;
		this.patient = patient;
		this.drawDate = drawDate;
		this.envSerialNumber = envSerialNumber;
		this.frequency = frequency;
		this.noOfTest = noOfTest;
		this.patientTypeSubGrp = patientTypeSubGrp;
		this.location = location;
		this.locationType = locationType;
		this.collectedBy = collectedBy;
		this.collectionTime = collectionTime;
	
	}	
	
	public RequisitionDetails(String status, String patientTypeSubGrp,
			Date collectionDate, long requisitionCount) {
		super();
		this.status = status;
		this.patientTypeSubGrp = patientTypeSubGrp;
		this.collectionDate = collectionDate;
		this.requisitionCount = requisitionCount;
	}

	@Override
	public String toString() {
		return "requisition: " + requisition + "~|~" + "patient: " + patient
				+ "~|~" + "drawDate: " + drawDate + "~|~" + "status: " + status
				+ "~|~" + "orderSystem: " + orderSystem + "~|~" + "frequency: "
				+ frequency + "~|~" + "noOfTest: " + noOfTest + "~|~"
				+ "abnormalFlag: " + abnormalFlag + "~|~"
				+ "cancelledTestIndicator: " + cancelledTestIndicator + "~|~"
				+ "collectionDate: " + collectionDate + "~|~"
				+ "requisitionCount: " + requisitionCount + "~|~"
				+ "patientTypeSubGrp: " + patientTypeSubGrp + "~|~"
				+ "location: " + location + "~|~"
				+ "locationType: " + locationType + "~|~"
				+ "collectedBy: " + collectedBy + "~|~"
				+ "collectionTime: " + collectionTime + "~|~"
				+ "envRequisitionDetails: " + envRequisitionDetails + "~|~"
				+ "numOfTubesNotRec: " + numOfTubesNotRec + "~|~"
				+ "envSerialNumber: " + envSerialNumber + "~|~"
				+ "patientType: " + patientType + "~|~"
				+ "accessionCount: " + accessionCount + "~|~"
				+ "tubesReceivedCount: " + tubesReceivedCount;
	}
	 

}
