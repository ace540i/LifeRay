/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* =================================================================== */
package com.spectra.symfonielabs.domainobject;

import java.io.Serializable;
import java.util.Date;


/**
 * This is a domain object class which holds the tube type summary details.
 * 
 */
public class Accession implements Serializable {

	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Holds the accession number.
	 */
	private String accession;

	/**
	 * Holds the tube type.
	 */
	private String tubetype;
	
	/**
	 * Holds the specimen.
	 */
	private String specimen;
	
	/**
	 * Holds the specimen.
	 */
	private Date receivedDate;
	
	/**
	 * Holds the condition.
	 */
	private String condition;
	
	/**
	 * Holds the status.
	 */
	private String status;

	private int lab_fk;
	
	public int getLabfk(){
		return lab_fk;
	}
	
	public String getAccession() {
		return accession;
	}

	public String getTubetype() {
		return tubetype;
	}

	public String getSpecimen() {
		return specimen;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public String getCondition() {
		return condition;
	}

	public String getStatus() {
		return status;
	}

	public Accession(final String accession, final String tubetype,
			final String specimen, final Date receivedDate,
			final String condition, final String status,final int lab_fk) {
		super();
		this.accession = accession;
		this.tubetype = tubetype;
		this.specimen = specimen;
		this.receivedDate = receivedDate;
		this.condition = condition;
		this.status = status;
		this.lab_fk = lab_fk;
	}
	
	@Override
	public String toString() {
		return "accession: " + accession + "~|~" + "tubetype: " + tubetype
				+ "~|~"	+ "receivedDate: " + receivedDate + "~|~" + "specimen: "
				+ specimen + "~|~" + "condition: " + condition + "~|~"
				+ "status: " + status 
				+ "lab_fk: " + lab_fk;
	}
	
}
