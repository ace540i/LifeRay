/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* =================================================================== */
package com.spectra.symfonielabs.domainobject;

import com.spectra.symfonie.framework.domainobject.BaseDO;

/**
 * This class Contains details about the search.
 * 
 */
public class Search extends BaseDO{

	/**
     * Holds the serial version Id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the search type.
     */
    private String searchType;
    
    /**
     * Holds the search value.
     */
    private String searchValue;
    
    /**
     * Holds the facility id.
     */
    private long facilityId;
    
    /**
     * Holds the facility number.
     */
    private String facilityNum;
    
    /**
     * Holds the facility name.
     */
    private String facilityName;
    
    /**
     * Holds the start index.
     */
    private int startIndex;
    
    /**
     * Holds the end index.
     */
    private int endIndex;
    
    /**
     * Holds sortDirection
     */
	private String sortDirection;
    /**
     * Holds sortField  
     */
	private String sortField;
	/**
     * Holds the Corporation Name.
     */
    private String corporationName;
    /**
     * Holds the Corporation ID.
     */
    private long corporationId; 
    /**
     * Holds the Corporation acronym.
     */
    private String acronym; 
    /**
     * Holds the Corporation acronym + corp name.
     */
    private String selCorpName;   
    
    
//timc
	/**
     * Holds the patientDateOfBirth.
     */
    private String patientDOB;
//timc

    
	public Search() {
		super();
	}

//timc
	public Search(final String searchType, final String searchValue,
			final long facilityId, final String facilityNum,
			final String facilityName, final String patientDOB) {
		super();
		this.searchType = searchType;
		this.searchValue = searchValue;
		this.facilityId = facilityId;
		this.facilityNum = facilityNum;
		this.facilityName = facilityName;
		this.patientDOB = patientDOB;
	}
//timc
	
	public Search(final String searchType, final String searchValue,
			final long facilityId, final String facilityNum,
			final String facilityName) {
		super();
		this.searchType = searchType;
		this.searchValue = searchValue;
		this.facilityId = facilityId;
		this.facilityNum = facilityNum;
		this.facilityName = facilityName;
	}

	public Search(final String corporationName, final long corporationId,final String acronym,String selCorpName,
			final long facilityId, final String facilityName,
			final String searchType) {
		super();
		this.corporationName = corporationName;
		this.corporationId = corporationId;
		this.acronym		= acronym;
		this.facilityId = facilityId;
		this.facilityName = facilityName;
		this.searchType = searchType;
		this.selCorpName = selCorpName;
	}

	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * @return the facilityId
	 */
	public long getFacilityId() {
		return facilityId;
	}
	
	/**
	 * @return the facilityName
	 */
	public String getFacilityName() {
		return facilityName;
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * @return the facilityNum
	 */
	public String getFacilityNum() {
		return facilityNum;
	}

	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getCorporationName() {
		return corporationName;
	}

//timc	
	public long getCorporationId() {
		return corporationId;
	}
	public String getAcronym() {   //acronym
		return acronym;
	}
	public String getSelCorpName() {   //acronym
		return selCorpName;
	}
	
//timc

	public String getpatientDOB() {
		return patientDOB;
	}
	
	
	@Override
	public String toString() {
		return "searchType: " + searchType + "~|~" + "searchValue: " + searchValue + 
				"~|~" + "facilityId: " + facilityId + "~|~"	+ "facilityNum: " + facilityNum + 
				"~|~" + "startIndex: " + startIndex + "~|~" + "endIndex: " + endIndex + 
				"~|~" + "CorporationName: " + corporationName + "~|~" + "CorporationId: " + corporationId +
		"~|~" + "patientDOB: " + patientDOB; 
	}
	//timc
}
