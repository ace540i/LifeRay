/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* =================================================================== */
package com.spectra.symfonielabs.domainobject;

import java.io.Serializable;
import java.util.Date;

public class Equipment implements Serializable{

	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private String machineName;
	private String serialNumber;
	private String spectraMRN;
	private String clientName;
	private String facilityNum;
	private String facilityName;
	private String facilityPhoneNumber;
	private long facilityId;
	private long startIndex;
	private long endIndex;
	private String sortDirection;
	private String sortField;
	private long listSize;
	/**
	 * Holds last draw date of type date.
	 */
	private Date lastDrawDate;

	public Date getLastDrawDate() {
		return lastDrawDate;
	}
	
	
	public String getMachineName() {
		return machineName;
	}	
	public String getSerialNumber() {
		return serialNumber;
	}
	public String getSpectraMRN() {
		return spectraMRN;
	}
	public String getClientName() {
		return clientName;
	}
	public String getFacilityNum() {
		return facilityNum;
	}
	public String getFacilityName() {
		return facilityName;
	}	
	public String getFacilityPhoneNumber() {
		return facilityPhoneNumber;
	}	
	public long getFacilityId() {
		return facilityId;
	}

	/**
	 * Holds the lab name of the patient.
	 */
	private String labName;
	
	/**
	 * Holds entity Index of type long.
	 */
	private long entityIndex;
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the labName
	 */
	public String getLabName() {
		return labName;
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
		return entityIndex;
	}


	public void setEntityIndex(long entityIndex) {
		this.entityIndex = entityIndex;
	}

	public Equipment() {
		super();
	}

	public Equipment(
			final String machineName, 
			final String serialNumber,
			final String spectraMRN,  final String labName,
			final String clientName,
			final String facilityName, 
			final long facilityId,
			final String facilityNum, 
			final String facilityPhoneNumber,
			final long startIndex,
			final long endIndex, 
			final String sortDirection,
			final String sortField, 
			final long listSize, final Date lastDrawDate) {
		super();
		this.machineName = machineName;
		this.serialNumber = serialNumber;
		this.spectraMRN = spectraMRN;
		this.labName = labName;		
		this.clientName = clientName;
		this.facilityName = facilityName;
		this.facilityId = facilityId;
		this.facilityNum = facilityNum;
		this.facilityPhoneNumber = facilityPhoneNumber;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.sortDirection = sortDirection;
		this.sortField = sortField;
		this.listSize = listSize;
		this.lastDrawDate = lastDrawDate;		
	}
	
	
	@Override
	public String toString() {
		return "machineName: " + machineName + "~|~" 
				+ "serialNumber: " + serialNumber + "~|~"
				+ "spectraMRN: " + spectraMRN + "~|~"   + "labName: " + labName + "~|~" 
				+ "clientName: " + clientName + "~|~"  
				+ "facilityName: " + facilityName + "~|~"
				+ "facilityId: " + facilityId + "~|~" 
				+ "facilityNum:"+ facilityNum + "~|~" + "spectraMRN: " + spectraMRN + "~|~"
				+ "facilityPhoneNumber: " + facilityPhoneNumber + "~|~" 
				+ "startIndex: " + startIndex + "~|~" + "sortDirection: "
				+ sortDirection + "~|~" + "sortField: " + sortField + "~|~"
				+ "listSize: " + listSize + "~|~" + "endIndex: " + endIndex
				+ "~|~" + "lastDrawDate: " + lastDrawDate;
	}
}
