package com.spectra.symfonielabs.domainobject;

import java.io.Serializable;

public class EnvRequisitionDetails implements Serializable  {

	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Holds the machine name.
	 */
	private String machineName;
	
	/**
	 * Holds the serial number.
	 */
	private String serialNum;
	
	/**
	 * Holds the location type.
	 */
	private String locationType;
	
	/**
	 * Holds the location.
	 */
	private String location;
	
	/**
	 * Holds the collected by.
	 */
	private String collectedBy;
	
	/**
	 * Holds the collectionTime.
	 */
	private String collectionTime;
	
	/**
	 * Holds the source.
	 */
	private String source;
	
	/**
	 * Holds the equipment status.
	 */
	private String equipmentStatus;

	public EnvRequisitionDetails() {
		super();
	}

	public EnvRequisitionDetails(final String machineName,
			final String serialNum, final String locationType,
			final String location, final String collectedBy,
			final String collectionTime, final String source,
			final String equipmentStatus) {
		super();
		this.machineName = machineName;
		this.serialNum = serialNum;
		this.locationType = locationType;
		this.location = location;
		this.collectedBy = collectedBy;
		this.collectionTime = collectionTime;
		this.source = source;
		this.equipmentStatus = equipmentStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMachineName() {
		return machineName;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public String getLocationType() {
		return locationType;
	}

	public String getLocation() {
		return location;
	}

	public String getCollectedBy() {
		return collectedBy;
	}

	public String getCollectionTime() {
		return collectionTime;
	}

	public String getSource() {
		return source;
	}

	public String getEquipmentStatus() {
		return equipmentStatus;
	}

	@Override
	public String toString() {
		return "EnvRequisitionDetails [machineName=" + machineName
				+ ", serialNum=" + serialNum + ", locationType=" + locationType
				+ ", location=" + location + ", collectedBy=" + collectedBy
				+ ", collectionTime=" + collectionTime + ", source=" + source
				+ ", equipmentStatus=" + equipmentStatus + "]";
	}
	
}
