package com.spectra.symfonielabs.domainobject;

import java.io.Serializable;

public class FacilitySearch implements Serializable {

	/**
	 * Holds the serial version Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Holds the facility name.
	 */
	private String facilityName;
	
	/**
	 * Holds the facility Id.
	 */
	private long facilityId;
	
	/**
	 * Holds the corporation name.
	 */
	private String corporationName;
	
	/**
	 * Holds the account type.
	 */
	private String accountType;
	
	/**
	 * Holds the account status.
	 */
	private String accountStatus;
	
	/**
	 * Holds the servicing lab.
	 */
	private String servicingLab;
	
	/**
	 * Holds the HLAB Number.
	 */
	private String hlabNumber;
	
	/**
	 * Holds start index for the facility search.
	 */
	private long startIndex;

	/**
	 * Holds end index for the facility search.
	 */
	private long endIndex;
	
	/**
	 * Holds list size for the facility search.
	 */
	private long listSize;

	/**
	 * Holds entity Index of type long.
	 */
	private long entityIndex;

	public FacilitySearch(final String facilityName, final long facilityId,
			final String corporationName, final String accountType,
			final String accountStatus, final String servicingLab,
			final long listSize, final long entityIndex, final String hlabNumber) {
		super();
		this.facilityName = facilityName;
		this.facilityId = facilityId;
		this.corporationName = corporationName;
		this.accountType = accountType;
		this.accountStatus = accountStatus;
		this.servicingLab = servicingLab;
		this.listSize = listSize;
		this.entityIndex = entityIndex;
		this.hlabNumber = hlabNumber;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public long getFacilityId() {
		return facilityId;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public String getServicingLab() {
		return servicingLab;
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

	public long getEntityIndex() {
		return entityIndex;
	}
	
	public String getHlabNumber() {
		return hlabNumber;
	}

	@Override
	public String toString() {
		return "FacilitySearch [facilityName: " + facilityName + "~|~"
				+ "facilityId: " + facilityId + "~|~" + "hlabNumber: "
				+ hlabNumber + "corporationName: " + corporationName + "~|~"
				+ "accountType: " + accountType + "~|~" + "accountStatus: "
				+ accountStatus + "~|~" + "servicingLab: " + servicingLab
				+ "~|~" + "startIndex: " + startIndex + "endIndex: " + endIndex
				+ "~|~" + "entityIndex: " + entityIndex + "~|~" + "listSize: "
				+ listSize + "]";
	}
	
}
