/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* =================================================================== */
package com.spectra.symfonielabs.domainobject;

import java.io.Serializable;

/**
 * This is a domain object class which holds the order summary details of a
 * patient.
 * 
 */
public class Results implements Serializable {
	
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Holds the requisition id.
	 */
	private String requisition;

	/**
	 * Holds the accession number.
	 */
	private String accession;
	/**
	 * Holds the patientTypeSubGroup.
	 */
	private String patientTypeSubGroup;

	/**
	 * Holds the result test name.
	 */
	private String resultTestName;

	/**
	 * Holds the result.
	 */
	private String textualResult;

	/**
	 * Holds the unit of measure used.
	 */
	private String UOM;

	/**
	 * Holds the reference range.
	 */
	private String referenceRange;

	/**
	 * Holds the result comments.
	 */
	private String resultComment;
	
	/**
	 * Holds the reference range.
	 */
	private String indicator;
	
	/**
	 * Holds the order status.
	 */
	private String orderStatus;
	
	/**
	 * Holds the order control reason.
	 */
	private String orderControlReason;

	/**
	 * Holds the order test name.
	 */
	private String orderTestName;
	
	/**
	 * Holds the parent test count for grouping of child tests.
	 */
	private int parentTestCount;
	/**
	 * Holds the Report group name.
	 */
	private String reportGrpName;

	/**
	 * holds the comments at Requisition/Test level. Currently Hub stores both Requisition level and Test level from HLAB at Test level.
	 */
	private String reportNotes;	

	/**
	 * holds the Specimen method
	 */
	private String specimenMethodDesc;	

	/**
	 * holds the specimen source
	 */
	private String specimenSourceDesc;	

	/**
	 * holds the display sorting sequence
	 */
	private String seqNum;	
	
	/**
	 * holds the micro Isolate info
	 */
	private String microIsolate;	
	
	/**
	 * holds the Micro organism name
	 */
	private String microOrganismName;	
	
	/**
	 * holds the micro sensitivity name
	 */
	private String microSensitivityName;		

	/**
	 * holds the micro sensitivity flag
	 */
	private String microSensitivityFlag;		

	// timc 8/11/2016
	/**
	 * holds the message string for Status History
	 */
	private String shmsg;		

	public String getShmsg() {
		return shmsg;
	}
	public void setShmsg(String shmsg) {
		this.shmsg = shmsg;
	}
	// timc 8/11/2016
	
	public String getRequisition() {
		return requisition;
	}

	public String getAccession() {
		return accession;
	}
	public String getPatientTypeSubGroup() {
		return patientTypeSubGroup;
	}
	public String getResultTestName() {
		return resultTestName;
	}

	public String getTextualResult() {
		return textualResult;
	}

	public String getUOM() {
		return UOM;
	}

	public String getReferenceRange() {
		return referenceRange;
	}

	public String getResultComment() {
		return resultComment;
	}

	
	public String getIndicator() {
		return indicator;
	}


	public String getOrderStatus() {
		return orderStatus;
	}

	public String getOrderControlReason() {
		return orderControlReason;
	}

	public String getOrderTestName() {
		return orderTestName;
	}

	public int getParentTestCount() {
		return parentTestCount;
	}

	public String getReportGrpName() {
		return reportGrpName;
	}

	public void setReportGrpName(String reportGrpName) {
		this.reportGrpName = reportGrpName;
	}

	public String getReportNotes() {
		return reportNotes;
	}

	public String getSpecimenMethodDesc() {
		return specimenMethodDesc;
	}

	public String getSpecimenSourceDesc() {
		return specimenSourceDesc;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public String getMicroIsolate() {
		return microIsolate;
	}

	public String getMicroOrganismName() {
		return microOrganismName;
	}

	public String getMicroSensitivityName() {
		return microSensitivityName;
	}

	public String getMicroSensitivityFlag() {
		return microSensitivityFlag;
	}


	public Results(final String requisition, final String accession,final String patientTypeSubGroup,
			final String resultTestName, final String textualResult,
			final String UOM, final String referenceRange,
			final String resultComment, final String indicator,
			final String orderStatus, final String orderControlReason,
			final String orderTestName, final String shmsg, final int parentTestCount) {
		super();
		this.requisition = requisition;
		this.accession = accession;
		this.patientTypeSubGroup = patientTypeSubGroup;
		this.resultTestName = resultTestName;
		this.textualResult = textualResult;
		this.UOM = UOM;
		this.referenceRange = referenceRange;
		this.resultComment = resultComment;
		this.indicator = indicator;
		this.orderStatus = orderStatus;
		this.orderControlReason = orderControlReason;
		this.orderTestName = orderTestName;
		this.parentTestCount = parentTestCount;
		this.shmsg = shmsg;	
	}
		
	
	/**
	 * Manually generated using Studio Source menu. Constructor with all fields 
	 */
	public Results(String requisition, String accession, String patientTypeSubGroup, String resultTestName,
			String textualResult, String uOM, String referenceRange,
			String resultComment, String indicator, String orderStatus,
			String orderControlReason, String orderTestName,   String shmsg,  
			int parentTestCount, String reportNotes, String specimenMethodDesc,
			String specimenSourceDesc, String seqNum, String microIsolate,
			String microOrganismName, String microSensitivityName,
			String microSensitivityFlag) {  
		super();
		this.requisition = requisition;
		this.accession = accession;
		this.patientTypeSubGroup = patientTypeSubGroup;
		this.resultTestName = resultTestName;
		this.textualResult = textualResult;
		UOM = uOM;
		this.referenceRange = referenceRange;
		this.resultComment = resultComment;
		this.indicator = indicator;
		this.orderStatus = orderStatus;
		this.orderControlReason = orderControlReason;
		this.orderTestName = orderTestName;
		this.parentTestCount = parentTestCount;
		this.reportNotes = reportNotes;
		this.specimenMethodDesc = specimenMethodDesc;
		this.specimenSourceDesc = specimenSourceDesc;
		this.seqNum = seqNum;
		this.microIsolate = microIsolate;
		this.microOrganismName = microOrganismName;
		this.microSensitivityName = microSensitivityName;
		this.microSensitivityFlag = microSensitivityFlag;
		this.shmsg = shmsg;	   
	}

	/**
	 * Manually generated using Studio Source menu. 
	 */
	@Override
	public String toString() {
		return "Results [requisition=" + requisition + ", accession="
				+ accession + ", patientTypeSubGroup =" + patientTypeSubGroup + ", resultTestName=" + resultTestName
				+ ", textualResult=" + textualResult + ", UOM=" + UOM
				+ ", referenceRange=" + referenceRange + ", resultComment="
				+ resultComment + ", indicator=" + indicator + ", orderStatus="
				+ orderStatus + ", orderControlReason=" + orderControlReason
				+ ", orderTestName=" + orderTestName + ", parentTestCount="
				+ parentTestCount + ", reportGrpName=" + reportGrpName
				+ ", reportNotes=" + reportNotes + ", specimenMethodDesc="
				+ specimenMethodDesc + ", specimenSourceDesc="
				+ specimenSourceDesc + ", seqNum=" + seqNum + ", microIsolate="
				+ microIsolate + ", microOrganismName=" + microOrganismName
				+ ", microSensitivityName=" + microSensitivityName
				+ ", microSensitivityFlag=" + microSensitivityFlag 
				+ ", shmsg=" + shmsg + "]";
	}
	

	

	
}
