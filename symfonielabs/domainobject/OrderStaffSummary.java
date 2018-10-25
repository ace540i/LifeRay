/* =================================================================== */
/* 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New Domain Object Class for Staff Order Summary. 
 */
/* =================================================================== */
package com.spectra.symfonielabs.domainobject;

import java.io.Serializable;
import java.util.Date;

/**
 * This is a domain object class which holds the order summary details of a
 * patient.
 * 
 */
public class OrderStaffSummary implements Serializable {

	/**
	 * Holds the order id.
	 */
	private long orderId;

	/**
	 * Holds the draw date.
	 */
	private Date drawDate;
	/**
	 * Holds the NumOfTubesNotReceived for Tubes Received.
	 */
	private int NumOfTubesNotReceived;
	/**
	 * Holds the requisition number.
	 */
	private String requisitionNum;

	/**
	 * Holds the number of tests.
	 */
	private int numOfTests;

	/**
	 * Holds the frequency.
	 */
	private String frequency;

	/**
	 * Holds the status.
	 */
	private String status;
	
	/**
     * Holds the duration of draw date.
     */
    private int duration;
    
    /**
     * Holds the requisition id.
     */
    private long requisition_id;

	/**
	 * Holds the PatientResult object.
	 */
	private Patient patientResult;
	
	/**
	 * Holds the accession number.
	 */
	private String accession_number;
	
	/**
	 * Holds the result test name.
	 */
	private String result_test_name;
	
	/**
	 * Holds the result.
	 */
	private int textual_result;
	
	/**
	 * Holds the unit of measure used.
	 */
	private String unit_of_measure;
	
	/**
	 * Holds the reference range.
	 */
	private long reference_range;
	
	/**
	 * Holds the result comments.
	 */
	private String result_comment;
	/**
	 * Holds start index for the order summary.
	 */
	private long startIndex;

	/**
	 * Holds end index for the order summary.
	 */
	private long endIndex;
	/**
	 * Holds list size for the order summary.
	 */
	private long listSize;
	/**
	 * Holds entity Index of type long.
	 */
	private long entityIndex;
	/**
	 * Holds the abnormal flag.
	 */
	private boolean abnormalFlag;
	/**
	 * Holds the cancelled test indicator.
	 */
	private boolean cancelledTestIndicator;
	/**
	 * Holds the facility demographics.
	 */
	private FacilityDemographics facility;

	public FacilityDemographics getFacility() {
		return facility;
	}
	
	public Date getDrawDate() {
		return drawDate;
	}
 	public int getNumOfTubesNotReceiveds() {
 		return NumOfTubesNotReceived;
 	}

	public String getFrequency() {
		return frequency;
	}

	public int getNumOfTests() {
		return numOfTests;
	}

	public long getOrderId() {
		return orderId;
	}

	public String getRequisitionNum() {
		return requisitionNum;
	}

	public String getStatus() {
		return status;
	}

	public Patient getPatientResult() {
		return patientResult;
	}

	public int getDuration() {
		return duration;
	}

	public long getRequisition_id() {
		return requisition_id;
	}

	public String getAccession_number() {
		return accession_number;
	}

	public void setAccession_number(String accession_number) {
		this.accession_number = accession_number;
	}

	public String getResult_test_name() {
		return result_test_name;
	}

	public void setResult_test_name(String result_test_name) {
		this.result_test_name = result_test_name;
	}

	public int getTextual_result() {
		return textual_result;
	}

	public void setTextual_result(int textual_result) {
		this.textual_result = textual_result;
	}

	public String getUnit_of_measure() {
		return unit_of_measure;
	}

	public void setUnit_of_measure(String unit_of_measure) {
		this.unit_of_measure = unit_of_measure;
	}

	public long getReference_range() {
		return reference_range;
	}

	public void setReference_range(long reference_range) {
		this.reference_range = reference_range;
	}

	public String getResult_comment() {
		return result_comment;
	}

	public void setResult_comment(String result_comment) {
		this.result_comment = result_comment;
	}

	public long getStartIndex() {
		return startIndex;
	}

	public long getEndIndex() {
		return endIndex;
	}

	public long getListSize() {
		return listSize;
	}

	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}

	public void setEndIndex(long endIndex) {
		this.endIndex = endIndex;
	}
	
	public long getEntityIndex() {
		return entityIndex;
	}

	public void setEntityIndex(long entityIndex) {
		this.entityIndex = entityIndex;
	}
	public boolean getAbnormalFlag() {
		return abnormalFlag;
	}

	public boolean isCancelledTestIndicator() {
		return cancelledTestIndicator;
	}
	
	public void setFacility(FacilityDemographics facility) {
		this.facility = facility;
	}
		
	public OrderStaffSummary(long orderId, Date drawDate, String requisitionNum,
			int numOfTests, String frequency, String status, int duration,
			long requisition_id, int NumOfTubesNotReceived,Patient patientResult,
			String accession_number, String result_test_name,
			int textual_result, String unit_of_measure, long reference_range,
			String result_comment, final long listSize, final long entityIndex,
			final boolean abnormalFlag, final boolean cancelledTestIndicator) {
		super();
		this.orderId = orderId;
		this.drawDate = drawDate;
		this.NumOfTubesNotReceived=NumOfTubesNotReceived;
		this.requisitionNum = requisitionNum;
		this.numOfTests = numOfTests;
		this.frequency = frequency;
		this.status = status;
		this.duration = duration;
		this.requisition_id = requisition_id;
		this.patientResult = patientResult;
		this.accession_number = accession_number;
		this.result_test_name = result_test_name;
		this.textual_result = textual_result;
		this.unit_of_measure = unit_of_measure;
		this.reference_range = reference_range;
		this.result_comment = result_comment;
		this.listSize = listSize;
		this.entityIndex = entityIndex;
		this.abnormalFlag = abnormalFlag;
		this.cancelledTestIndicator = cancelledTestIndicator;
	}

	public OrderStaffSummary() {
	}

	@Override
	public String toString() {
		return "orderId: " + orderId + "~|~" + "drawDate: " + drawDate + "~|~"
				+ "requisitionNum: " + requisitionNum + "~|~" + "numOfTests: "
				+ numOfTests + "~|~" + "frequency: " + frequency + "~|~"
				+ "status: " + status + "~|~" + "patientResult: "
				+ patientResult + "~|~" + "duration: " + duration + "~|~"
				+ "requisition_id: " + requisition_id + "~|~"
				+ "accession_number: " + accession_number + "~|~"
				+ "result_test_name: " + result_test_name + "~|~"
				+ "textual_result: " + textual_result + "~|~"
				+ "unit_of_measure: " + unit_of_measure + "~|~"
				+ "reference_range: " + reference_range + "~|~"
				+ "result_comment: " + result_comment + "~|~" + "startIndex: "
				+ startIndex + "~|~" + "endIndex: " + endIndex + "~|~"
				+ "listSize: " + listSize + "~|~" + "entityIndex: "
				+ entityIndex + "~|~" + "abnormalFlag: "+abnormalFlag + "~|~"
				+ "cancelledTestIndicator: " +cancelledTestIndicator + "~|~"
				+ "facility: " + facility 
				+ "~|~"
			 	+ "NumOfTubesNotReceived: " + NumOfTubesNotReceived ;
	}
}
