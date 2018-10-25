package com.spectra.symfonielabs.domainobject;

import java.io.Serializable;

public class FacilityDemographics implements Serializable {

	/**
	 * Holds the serial version Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Holds the facility name.
	 */
	private String facilityName;
	
	/**
	 * Holds the corporation name.
	 */
	private String corporationName;
	
	/**
	 * Holds the servicing lab.
	 */
	private String servicingLab;
	
	/**
	 * Holds the type of service.
	 */
	private String typeOfService;
	
	/**
	 * Holds the NCE.
	 */
	private String NCE;
	
	/**
	 * Holds the Sales representative.
	 */
	private String salesRepresentative;
	
	/**
	 * Holds the main phone number.
	 */
	private String mainPhoneNumber;
	
	/**
	 * Holds the state.
	 */
	private String state;
	
	/**
	 * Holds the account type.
	 */
	private String accountType;
	
	/**
	 * Holds the account status.
	 */
	private String accountStatus;
	
	/**
	 * Holds the account number.
	 */
	private String accountNumber;
	
	/**
	 * Holds the lab account number.
	 */
	private String labAccountNumber;
	
	/**
	 * Holds the account facility number.
	 */
	private String facilityId;
	
	/**
	 * Holds the time zone.
	 */
	private String timeZone;
	
	/**
	 * Holds the ordering system.
	 */
	private String orderingSystem;
	
	/**
	 * Holds the eCUBE server typed.
	 */
	private String serverType;
	/**
	 * Holds the patientReporting type.
	 */
	private String patientReporting;
	/**
	 * Holds the Draw Week.
	 */
	private String drawWeek;
	
	/**
	 * Holds the draw days.
	 */
	private String drawDays;
	
	/**
	 * Holds the kit indicator.
	 */
	private String kitIndicator;
	
	/**
	 * Holds the open and close time for monday, wednesday, friday.
	 */
	private String openCloseMonWedFr;
	
	/**
	 * Holds the open and close time for tuesday, thursday, saturday.
	 */
	private String openCloseTuThSa;
	
	/**
	 * Holds the open and close time for saturday.
	 */
	private String openCloseSat;
	
	/**
	 * Holds the physical address for a facility.
	 */
	private String physicalAddress;
	
	/**
	 * Holds the mailing address for a facility.
	 */
	private String mailingAddress;
	
	/**
	 * Holds the Alert call information.
	 */
	private String alertInformation;
	
	/**
	 * Holds the phone number for a facility.
	 */
	private String phoneNumber;
	/**
	 * Holds the phone number for a Symfonie user.
	 */
	private String UserphoneNumber;
	
	/**
	 * Holds the phone comments for a Symfonie user.
	 */
	private String phoneComments;
	
	/**
	 * Holds the fax number for a facility.
	 */
	private String faxNumber;
	
	/**
	 * Holds the patient count for a facility.
	 */
	private String patientCount;
	/**
	 * Holds the patient count for a facility.
	 */
	private String hemoCount;
	/**
	 * Holds the patient count for a facility.
	 */
	private String pdCount;
	/**
	 * Holds the patient count for a facility.
	 */
	private String hhCount;
	/**
	 * Holds the supply depot for a facility.
	 */
	private String supplyDepot;
	/**
	 * Holds the sap number for a facility.
	 */
	private String sapNumber;
	/**
	 * Holds the supplyDeliverySch1 for a facility.
	 */
	private String supplyDeliverySch1;
	/**
	 * Holds the supplyDeliverySch2 for a facility.
	 */
	private String supplyDeliverySch2;
	/**
	 * Holds the supplyDeliverySch3 for a facility.
	 */
	private String supplyDeliverySch3;
	
	/**
	 * Holds the first_received_date for a facility.
	 */
	private String firstReceivedDate;
	/**
	 * Holds the discontinued_date for a facility.
	 */
	private String discontinuedDate;	
	/**
	 * Holds the clinical_manager for a facility.
	 */
	private String clinicalManager;
	
	/**
	 * Holds the medical_director for a facility.
	 */
	private String medicalDirector;
	/**
	 * Holds the administrator for a facility.
	 */
	private String administrator;	
	/**
	 * Holds the custom/generic indicator value for a facility.
	 */
	private String indicatorVal;
	
	/**
	 * Holds the kit comments for a facility.
	 */
	private String kitComments;
	
	/**
	 * Holds the corporate acronym.
	 */
	private String corporateAcronym;
	
	/**
	 * Holds the facility ID primary key.
	 */
	private long facilityPk;
	
	/**
	 * Holds the display facility name navigated from the facility demographics
	 * flow.
	 */
	private String displayFacilityName;


	public FacilityDemographics() {
		super();
	}

	public FacilityDemographics(final String facilityName,
			final String corporationName, final String servicingLab,
			final String typeOfService, final String NCE,
			final String salesRepresentative, final String mainPhoneNumber,
			final String state, final String facilityId, final String timeZone,
			final String orderingSystem, final String serverType,
			final String patientReporting,
			final String drawWeek, final String drawDays,
			final String kitIndicator, final String physicalAddress,
			final String mailingAddress, final String alertInformation,
			final String indicatorVal, final String kitComments,
			final String corporateAcronym, final long facilityPk,
			final String displayFacilityName,final String clinical_manager,
			final String medical_director,final String administrator,final String phoneComments,final String patientCount,final String hemoCount,final String pdCount,final String hhCount,final String supplyDepot,final String sapNumber,
			final String supplyDeliverySch1,final String supplyDeliverySch2,final String supplyDeliverySch3,final String firstReceivedDate,final String discontinuedDate) {
		super();
		this.facilityName = facilityName;
		this.corporationName = corporationName;
		this.servicingLab = servicingLab;
		this.typeOfService = typeOfService;
		this.NCE = NCE;
		this.salesRepresentative = salesRepresentative;
		this.mainPhoneNumber = mainPhoneNumber;
		this.phoneComments = phoneComments;
		this.state = state;
		this.facilityId = facilityId;
		this.timeZone = timeZone;
		this.orderingSystem = orderingSystem;
		this.serverType = serverType;
		this.patientReporting = patientReporting;
		this.drawWeek = drawWeek;
		this.drawDays = drawDays;
		this.kitIndicator = kitIndicator;
		this.physicalAddress = physicalAddress;
		this.mailingAddress = mailingAddress;
		this.alertInformation = alertInformation;
		this.indicatorVal = indicatorVal;
		this.kitComments = kitComments;
		this.corporateAcronym = corporateAcronym;
		this.facilityPk = facilityPk;
		this.displayFacilityName = displayFacilityName;
		this.clinicalManager = clinical_manager;
		this.medicalDirector = medical_director;
		this.administrator = administrator;
		this.patientCount = patientCount;
		this.hemoCount = hemoCount;
		this.pdCount = pdCount;
		this.hhCount = hhCount;
		this.supplyDepot = supplyDepot;
		this.sapNumber = sapNumber;
		this.supplyDeliverySch1 = supplyDeliverySch1;
		this.supplyDeliverySch2 = supplyDeliverySch2;
		this.supplyDeliverySch3 = supplyDeliverySch3;
		this.firstReceivedDate = firstReceivedDate;
		this.discontinuedDate = discontinuedDate;
	}

	public FacilityDemographics(final String accountType,
			final String accountStatus, final String accountNumber,
			final String labAccountNumber, final String phoneNumber,
			final String faxNumber,final String patientCount) {
		super();
		this.accountType = accountType;
		this.accountStatus = accountStatus;
		this.accountNumber = accountNumber;
		this.labAccountNumber = labAccountNumber;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.patientCount = patientCount;
	}

	public FacilityDemographics(String openCloseMonWedFr,
			String openCloseTuThSa, String openCloseSat) {
		super();
		this.openCloseMonWedFr = openCloseMonWedFr;
		this.openCloseTuThSa = openCloseTuThSa;
		this.openCloseSat = openCloseSat;
	}
	
	public FacilityDemographics(String facilityName, String corporateAcronym) {
		super();
		this.facilityName = facilityName;
		this.corporateAcronym = corporateAcronym;		
	}	

	public String getFacilityName() {
		return facilityName;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public String getServicingLab() {
		return servicingLab;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public String getNCE() {
		return NCE;
	}

	public String getSalesRepresentative() {
		return salesRepresentative;
	}

	public String getMainPhoneNumber() {
		return mainPhoneNumber;
	}

	public String getState() {
		return state;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getLabAccountNumber() {
		return labAccountNumber;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public String getOrderingSystem() {
		return orderingSystem;
	}

	public String getServerType() {
		return serverType;
	}
	public String getPatientReporting() {
		return patientReporting;
	}
	public String getDrawWeek() {
		return drawWeek;
	}

	public String getDrawDays() {
		return drawDays;
	}

	public String getKitIndicator() {
		return kitIndicator;
	}

	public String getOpenCloseMonWedFr() {
		return openCloseMonWedFr;
	}

	public String getOpenCloseTuThSa() {
		return openCloseTuThSa;
	}

	public String getOpenCloseSat() {
		return openCloseSat;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public String getAlertInformation() {
		return alertInformation;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getUserPhoneNumber() {
		return UserphoneNumber;
	}
	public String getPhoneComments() {
		return  phoneComments;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public String getPatientCount() {
		return patientCount;
	}
	public String getHemoCount() {
		return hemoCount;
	}
	public String getPdCount() {
		return pdCount;
	}
	public String getHhCount() {
		return hhCount;
	}
	public String getSupplyDepot() {
		return supplyDepot;
	}
	public String getsapNumber() {
		return sapNumber;
	}
	public String getSupplyDeliverySch1() {
		return supplyDeliverySch1;
	}
	public String getSupplyDeliverySch2() {
		return supplyDeliverySch2;
	}
	public String getSupplyDeliverySch3() {
		return supplyDeliverySch3;
	}
	public String getFirstReceivedDate() {
		return firstReceivedDate;
	}
	public String getDiscontinuedDate() {
		return discontinuedDate;
	}
	
	public String getClinicalManager() {
		return clinicalManager;
	}
	public String getMedicalDirector() {
		return medicalDirector;
	}
	public String getAdministrator() {
		return administrator;
	}
	public String getIndicatorVal() {
		return indicatorVal;
	}

	public String getKitComments() {
		return kitComments;
	}

	public String getCorporateAcronym() {
		return corporateAcronym;
	}
	
	public long getFacilityPk() {
		return facilityPk;
	}

	public String getDisplayFacilityName() {
		return displayFacilityName;
	}

	@Override
	public String toString() {
		return "FacilityDemographics [facilityName=" + facilityName
				+ ", corporationName=" + corporationName + ", servicingLab="
				+ servicingLab + ", typeOfService=" + typeOfService + ", NCE="
				+ NCE + ", salesRepresentative=" + salesRepresentative
				+ ", mainPhoneNumber=" + mainPhoneNumber + ", state=" + state
				+ ", accountType=" + accountType + ", accountStatus="
				+ accountStatus + ", accountNumber=" + accountNumber
				+ ", labAccountNumber=" + labAccountNumber + ", facilityId="
				+ facilityId + ", timeZone=" + timeZone + ", orderingSystem="
				+ orderingSystem + ", serverType=" + serverType + ", drawWeek="
				+ drawWeek + ", drawDays=" + drawDays + ", kitIndicator="
				+ kitIndicator + ", openCloseMonWedFr=" + openCloseMonWedFr
				+ ", openCloseTuThSa=" + openCloseTuThSa + ", openCloseSat="
				+ openCloseSat + ", physicalAddress=" + physicalAddress
				+ ", mailingAddress=" + mailingAddress + ", alertInformation="
				+ alertInformation + ", phoneNumber=" + phoneNumber
				+ ", faxNumber=" + faxNumber + ", indicatorVal=" + indicatorVal
				+ ", clinicalManager=" + clinicalManager + ", medicalDirector=" + medicalDirector
				+ ", administrator=" + administrator  + ", patientCount=" + patientCount
				+ ", kitComments=" + kitComments + ", corporateAcronym="
				+ corporateAcronym + ", facilityPk=" + facilityPk
				+ ", displayFacilityName=" + displayFacilityName + "]";
	}
}
