/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New Domain Object Class for Staff. 
 */
/* =================================================================== */
package com.spectra.symfonielabs.domainobject;

import java.io.Serializable;
import java.util.Date;

public class Staff implements Serializable{
	
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
 
	/**
	 * Holds the full name of the patient.
	 */
	private String fullName;

	/**
	 * Holds the gender of the patient.
	 */
	private String gender;

	/**
	 * Holds the physician name.
	 */
//	private String physicianName;

	/**
	 * Holds the date of birth of the patient.
	 */
	private Date dateOfBirth;

	/**
	 * Holds the facility name.
	 */
	private String facilityName;

	/**
	 * Holds the facility id.
	 */
	private long facilityId;

	/**
	 * Holds the facility number.
	 */
	private String facilityNum;

	/**
	 * Holds the spectra MRN of the patient.
	 */
	private long spectraMRN;

	/**
	 * Holds the clinic name of the patient.
	 */
	private String clinicName;

	/**
	 * Holds the clinic id of the patient.
	 */
	private long clinicId;

	/**
	 * Holds the modality of the patient.
	 */
	//private String modality;

	/**
	 * Holds the HLAB id of the patient.
	 */
	private String patientHLABId;

	/**
	 * Holds the external ID of the patient.
	 */
	private String externalID;

	/**
	 * Holds the lab name of the patient.
	 */
	private String labName;

	/**
	 * Holds the corporation name of the patient.
	 */
	private String corpName;

	/**
	 * Holds the corporation id of the patient.
	 */
	private long corpId;

	/**
	 * Holds the account id of the patient.
	 */
	private String accountId;
	
	/**
	 * Holds the HLAB number.
	 */
	private String HLABNumber;

	/**
	 * Holds start index for the patient search.
	 */
	private long startIndex;

	/**
	 * Holds end index for the patient search.
	 */
	private long endIndex;

	/**
	 * Holds sort direction for the patient search.
	 */
	private String sortDirection;

	/**
	 * Holds sort field for the patient search.
	 */
	private String sortField;

	/**
	 * Holds list size for the patient search.
	 */
	private long listSize;

	/**
	 * Holds entity Index of type long.
	 */
	private long entityIndex;

	/**
	 * Holds last draw date of type date.
	 */
	private Date lastDrawDate;
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the physicianName
	 */
//	public String getPhysicianName() {
//		return physicianName;
//	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @return the facilityName
	 */
	public String getFacilityName() {
		return facilityName;
	}

	/**
	 * @return the facilityId
	 */
	public long getFacilityId() {
		return facilityId;
	}

	/**
	 * @return the facilityNum
	 */
	public String getFacilityNum() {
		return facilityNum;
	}

	/**
	 * @return the spectraMRN
	 */
	public long getSpectraMRN() {
		return spectraMRN;
	}

	/**
	 * @return the clinicName
	 */
	public String getClinicName() {
		return clinicName;
	}

	/**
	 * @return the clinicId
	 */
	public long getClinicId() {
		return clinicId;
	}

	/**
	 * @return the modality
	 */
//	public String getModality() {
//		return modality;
//	}

	/**
	 * @return the patientHLABId
	 */
	public String getPatientHLABId() {
		return patientHLABId;
	}

	/**
	 * @return the externalID
	 */
	public String getExternalID() {
		return externalID;
	}

	/**
	 * @return the labName
	 */
	public String getLabName() {
		return labName;
	}

	/**
	 * @return the corpName
	 */
	public String getCorpName() {
		return corpName;
	}

	/**
	 * @return the corpId
	 */
	public long getCorpId() {
		return corpId;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @return the startIndex
	 */
	public long getStartIndex() {
		return startIndex;
	}

	/**
	 * @return the endIndex
	 */
	public long getEndIndex() {
		return endIndex;
	}

	/**
	 * @return the sortDirection
	 */
	public String getSortDirection() {
		return sortDirection;
	}

	/**
	 * @return the sortField
	 */
	public String getSortField() {
		return sortField;
	}

	/**
	 * @return the listSize
	 */
	public long getListSize() {
		return listSize;
	}

	
	public long getEntityIndex() {
//		entityIndex=1;
		return entityIndex;
	}

	public String getHLABNumber() {
		return HLABNumber;
	}

	public Date getLastDrawDate() {
		return lastDrawDate;
	}
	
	public void setEntityIndex(long entityIndex) {
		this.entityIndex = entityIndex;
	}

	public Staff() {
		super();
	}

	public Staff(final String fullName, final String gender,
//			final String physicianName, 
			final Date dateOfBirth,
			final String facilityName, final long facilityId,
			final String facilityNum, final long spectraMRN,
			final String clinicName, final long clinicId,
//			final String modality, 
			final String patientHLABId,
			final String externalID, final String labName,
			final String corpName, final long corpId, final String accountId,
			final String HLABNumber, final long startIndex,
			final long endIndex, final String sortDirection,
			final String sortField, final long listSize,			
			final Date lastDrawDate) {
		
		super();
		this.fullName = fullName;
		this.gender = gender;
//		this.physicianName = physicianName;
		this.dateOfBirth = dateOfBirth;
		this.facilityName = facilityName;
		this.facilityId = facilityId;
		this.facilityNum = facilityNum;
		this.spectraMRN = spectraMRN;
		this.clinicName = clinicName;
		this.clinicId = clinicId;
//		this.modality = modality;
		this.patientHLABId = patientHLABId;
		this.externalID = externalID;
		this.labName = labName;
		this.corpName = corpName;
		this.corpId = corpId;
		this.accountId = accountId;
		this.HLABNumber = HLABNumber;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.sortDirection = sortDirection;
		this.sortField = sortField;
		this.listSize = listSize;
		this.lastDrawDate = lastDrawDate;
	}

	@Override
	public String toString() {
		return "fullName: " + fullName + "~|~" + "gender: " + gender + "~|~"
				+  "dateOfBirth: " 
				+ dateOfBirth + "~|~" + "facilityName: " + facilityName + "~|~"
				+ "facilityId: " + facilityId + "~|~" + "facilityNum:"
				+ facilityNum + "~|~" + "spectraMRN: " + spectraMRN + "~|~"
				+ "clinicName: " + clinicName + "~|~" + "clinicId: " + clinicId
				+ "~|~" + "patientHLABId: "
				+ patientHLABId + "~|~" + "externalID: " + externalID + "~|~"
				+ "labName: " + labName + "~|~" + "corpName: " + corpName
				+ "~|~" + "corpId: " + corpId + "~|~" + "accountId: "
				+ accountId + "~|~" + "HLABNumber: " + HLABNumber + "~|~"
				+ "startIndex: " + startIndex + "~|~" + "sortDirection: "
				+ sortDirection + "~|~" + "sortField: " + sortField + "~|~"
				+ "listSize: " + listSize + "~|~" + "endIndex: " + endIndex
     			+ "~|~" + "lastDrawDate: " + lastDrawDate;
	}
	
//	public String toString() {
//		return "fullName: " + fullName + "~|~" + "gender: " + gender + "~|~"
//				+ "physicianName: " + physicianName + "~|~" + "dateOfBirth: "
//				+ dateOfBirth + "~|~" + "facilityName: " + facilityName + "~|~"
//				+ "facilityId: " + facilityId + "~|~" + "facilityNum:"
//				+ facilityNum + "~|~" + "spectraMRN: " + spectraMRN + "~|~"
//				+ "clinicName: " + clinicName + "~|~" + "clinicId: " + clinicId
//				+ "~|~" + "modality: " + modality + "~|~" + "patientHLABId: "
//				+ patientHLABId + "~|~" + "externalMRN: " + externalID + "~|~"
//				+ "labName: " + labName + "~|~" + "corpName: " + corpName
//				+ "~|~" + "corpId: " + corpId + "~|~" + "accountId: "
//				+ accountId + "~|~" + "HLABNumber: " + HLABNumber + "~|~"
//				+ "startIndex: " + startIndex + "~|~" + "sortDirection: "
//				+ sortDirection + "~|~" + "sortField: " + sortField + "~|~"
//				+ "listSize: " + listSize + "~|~" + "endIndex: " + endIndex;
//	}

}
