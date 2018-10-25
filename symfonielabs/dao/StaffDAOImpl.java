/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* 
 * 12/22/2015 - md - US1087 
 * 				1) New DAO Impl Class for Staff. 
 * 				2) Uses Dim_Staff table.
 */
/* ===================================================================*/
package com.spectra.symfonielabs.dao;

import com.spectra.symfonie.dataaccess.framework.DataAccessHelper;
import com.spectra.symfonie.dataaccess.framework.ParamMapper;
import com.spectra.symfonie.dataaccess.framework.ResultMapper;
import com.spectra.symfonie.framework.constants.ExceptionConstants;
import com.spectra.symfonie.framework.exception.ApplicationException;
import com.spectra.symfonielabs.domainobject.Staff;
import com.spectra.symfonielabs.domainobject.Search;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This interface contains methods that is used to have staff related
 * activities.
 * 
 */
public class StaffDAOImpl implements StaffDAO {

	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<Staff> STAFF_DET_MAPPER = new ResultMapper<Staff>() {
		public Staff map(final ResultSet resultSet) throws SQLException {
			 
			final Staff searchResultLst = new Staff(
					resultSet.getString("FULL_NAME"),
					resultSet.getString("GENDER"), 
					resultSet.getDate("DOB"),
					resultSet.getString("FACILITY_NAME"),
					resultSet.getLong("FACILITY_FK"),
					resultSet.getString("FACILITY_ID"),
					resultSet.getLong("SPECTRA_MRN"),
					resultSet.getString("FACILITY_NAME"),
					resultSet.getLong("FACILITY_FK"),
					resultSet.getString("SPECTRA_MRN"),
					resultSet.getString("EXTERNAL_ID"),
					resultSet.getString("corp_name"),
					"", 0l, "", "",
					0l, 0l, "", "",resultSet.getLong("total_entity_cnt"),
					resultSet.getDate("last_general_lab_date"));
			searchResultLst.setEntityIndex(resultSet.getLong("sl_no"));
			return searchResultLst;
		}
	};
	
	/**
	 * Query to get the staff details.
	 */
	public static final String GET_STAFF_DET_SQL = 
			"SELECT sl_no,"
			+ " total_entity_cnt,"
			+ "  rtrim(spectra_mrn) spectra_mrn,"
			+ " corp_name,"
			+ " FACILITY_FK,"
			+ " facility_name, "
			+ " facility_id, "
			+ " fmc_number,"
			+ " FULL_NAME,"
			+ " DOB, "
			+ " GENDER ,"
			+ " last_general_lab_date,"
			+ " EXTERNAL_ID  "
			+ " FROM (SELECT ROWNUM sl_no, "
			+ " total_entity_cnt, "
			+ " corp_name,"
			+ " facility_name,"
			+ " facility_id, "
			+ " fmc_number, "
			+ " FACILITY_FK, "
			+ " FULL_NAME,"
			+ " DOB, "
			+ " GENDER , "
			+ " last_general_lab_date ,"
			+ " SPECTRA_MRN,"
			+ " EXTERNAL_ID "
			+ " FROM "
			+ " (SELECT dc.name corp_name,"
			+ " dc.corporate_acronym	|| ' - ' || df.display_name facility_name,"
			+ " DP.SPECTRA_MRN, "
			+ " DP.FACILITY_FK, "
			+ " DP.FULL_NAME, "
			+ " DP.DOB,  "
			+ " DP.GENDER ,"
			+ " dd.day AS last_general_lab_date, "
			+ " DP.EXTERNAL_ID,"
			+ " DF.FACILITY_ID, "
			+ " df.fmc_number, "
			+ " COUNT (DP.SPECTRA_MRN) "
			+ " OVER (PARTITION BY null) total_entity_cnt "			
			+ " FROM ih_dw.dim_staff dp join "
			+ " ih_dw.agg_staff_max_general_lab_mv amax ON "
			+ " dp.staff_pk = amax.staff_fk"
			+ " JOIN ih_dw.dim_date dd "
			+ " ON dd.date_pk = amax.collection_date_fk "
			+ " JOIN ih_dw.dim_facility df on "			
			+ " df.facility_pk = dp.facility_fk JOIN ih_dw.dim_client dc on "
			+ " dc.client_pk = df.client_fk "
			+ " JOIN ih_dw.spectra_mrn_master sm "
			+ " ON dp.spectra_mrn_fk = sm.spectra_mrn_pk ";
			

	
	/**
	 * Gets the search results for the staff.
	 * 
	 * @param facilityId
	 *            - Holds the facility Id.
	 * @param patName
	 *            - Holds the patient name.
	 * @return list of SearchResult - Holds the search details.
	 * 
	 */
	public List<Staff> getSearchResult(final Search staffSearch) {

		List<Staff> staffList;
		ParamMapper paramMapper = new ParamMapper() {

			public void mapParam(PreparedStatement preparedStatement)
					throws SQLException {
				int i = 0;
				if (staffSearch.getSearchValue() != null) {

					// timc 2/24/2016  :  replaceAll("\\s+", " ").replaceAll("\\s", "%")
					//preparedStatement.setString(++i, staffSearch.getSearchValue().trim().toUpperCase().concat("%"));
					preparedStatement.setString(++i, staffSearch.getSearchValue().replaceAll("\\s+", " ").replaceAll("\\s", "%").trim().toUpperCase().concat("%"));
					//System.out.println("@@ StaffDAOImpl: (1) getSearchResult() : "+ staffSearch.getSearchValue().replaceAll("\\s+", " ").replaceAll("\\s", "%").trim().toUpperCase().concat("%"));
					// timc 2/24/2016 					
					
				}
				if (staffSearch.getFacilityId() > 0) {
					preparedStatement.setLong(++i,
							staffSearch.getFacilityId());
				}
			}

		};

		StringBuffer query = new StringBuffer();
		query.append(GET_STAFF_DET_SQL);
		
		StringBuffer whereClause = new StringBuffer();
		if (null != staffSearch.getSearchValue()) {
			whereClause.append("UPPER(DP.FULL_NAME) LIKE ?");
		}

		if (staffSearch.getFacilityId() > 0) {
			if(null != whereClause && whereClause.length() > 0){
				whereClause.append(" AND ");
			}
			whereClause.append(" DP.FACILITY_FK = ? ");
		}
		if(null != whereClause && whereClause.length() > 0){
			query.append(" WHERE "+whereClause +" AND sm.mrn_status = 'ACTIVE' ");
		}
		query.append(" AND EXISTS (SELECT 1 FROM ih_dw.dim_account da "
				+ "WHERE da.facility_fk = df.facility_pk AND "
				+ "da.account_type IN "
				+ "('STUDY','DRAW STATION','IN-HOUSE EAST', 'IN-HOUSE WEST','SPECTRA WEST', "
				+ "'SPECTRA EAST') and upper(da.account_status) IN ('ACTIVE','IN PROGRESS','TRANSFERRED')) ");
		String sortDirection = " ASC ";
		if (staffSearch.getSortDirection() != null
				&& staffSearch.getSortDirection().equalsIgnoreCase("DESC")) {
			sortDirection = " DESC ";
		}
		if (staffSearch.getSortField() != null) {
//			if (staffSearch.getSortField().equalsIgnoreCase("patientName")) {
			if (staffSearch.getSortField().equalsIgnoreCase("fullName")) {
				query.append(" ORDER BY UPPER(DP.FULL_NAME) ").append(sortDirection) .append(", to_date(last_general_lab_date,'dd-mon-yy') DESC ");
			} else if (staffSearch.getSortField()
					.equalsIgnoreCase("inmateId")) {
				query.append(" ORDER BY UPPER (p.facility_patient_id) ")
						.append(sortDirection).append(" , UPPER(DP.FULL_NAME) ")
						.append(" ASC ");
			} else if (staffSearch.getSortField().equalsIgnoreCase(
					"dateOfBirth")) {
//				query.append(" ORDER BY UPPER (p.date_of_birth) ").append(sortDirection)
				query.append(" ORDER BY dob ").append(sortDirection)
					.append(" , UPPER(DP.FULL_NAME) ").append(" ASC ");
				
			}
			else if (staffSearch.getSortField().equalsIgnoreCase(
					"lastDrawDate")) {
				query.append(" order by to_date(last_general_lab_date,'dd-mon-yy')").append(sortDirection)
					.append(" , UPPER(DP.FULL_NAME) ").append(" ASC ");		
			//	System.out.println(query);
			}
			 else if (staffSearch.getSortField().equalsIgnoreCase(
						"gender")) {
					query.append(" ORDER BY UPPER(gender) ").append(sortDirection)
						.append(" , UPPER(DP.FULL_NAME) ").append(" ASC ");				
				}
			 else if (staffSearch.getSortField().equalsIgnoreCase(
						"modality")) {
					query.append(" ORDER BY UPPER(modality) ").append(sortDirection)
						.append(" , UPPER(DP.FULL_NAME) ").append(" ASC ");
					
				}
			 else if (staffSearch.getSortField().equalsIgnoreCase(
						"facilityName")) {
					query.append(" ORDER BY UPPER(facility_name) ").append(sortDirection)
						.append(" , UPPER(DP.FULL_NAME) ").append(" ASC ");
					
				}
			 else if (staffSearch.getSortField().equalsIgnoreCase(
						"spectraMRN")) {
					query.append("ORDER BY TO_NUMBER(spectra_mrn) ").append(sortDirection)
						.append(" , UPPER(DP.FULL_NAME) ").append(" ASC ");
					
				}		

		} else {

			
//			query.append(" ORDER BY UPPER(DP.FULL_NAME)").append(sortDirection)
			query.append(" ORDER BY UPPER(DP.LAST_NAME),UPPER(DP.FIRST_NAME)").append(sortDirection)
//			.append(",UPPER(last_general_lab_date) DESC " );
			.append(",to_date(last_general_lab_date,'dd-mon-yy') DESC ");
		}
//		if (staffSearch.getSortField() != null) {
//			if (staffSearch.getSortField().equalsIgnoreCase("patientName")) {
//				query.append(" ORDER BY UPPER(DP.FULL_NAME) ").append(sortDirection);
//			} else if (staffSearch.getSortField()
//					.equalsIgnoreCase("inmateId")) {
//				query.append(" ORDER BY UPPER (p.facility_patient_id) ")
//						.append(sortDirection).append(" , UPPER(DP.FULL_NAME) ")
//						.append(" ASC ");
//			} else if (staffSearch.getSortField().equalsIgnoreCase(
//					"dateofBirth")) {
//				query.append(" ORDER BY UPPER (p.date_of_birth) ").append(sortDirection)
//						.append(" , UPPER(DP.FULL_NAME) ").append(" ASC ");
//			}
//
//		} else {
//			query.append(" ORDER BY UPPER(DP.FULL_NAME) ").append(sortDirection)
////			     .append(",UPPER(last_general_lab_date) DESC " );
//			 .append(",to_date(last_general_lab_date,'dd-mon-yy') DESC ");
//		}
	//	System.out.println(query);
		query.append(" )) ");
//		if (staffSearch.getStartIndex() != 0
//                && staffSearch.getEndIndex() != 0) {
//			query.append(" WHERE SL_NO BETWEEN "
//                    + staffSearch.getStartIndex() + " AND  "
//                    + staffSearch.getEndIndex());
//        }
		try {
			staffList = DataAccessHelper.executeQuery(query.toString(),
					paramMapper, STAFF_DET_MAPPER);
		} catch (SQLException exception) {
			// Construct a HashMap holding parameters of this method and
			// pass it to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("staffSearch", staffSearch.toString());
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,
					exception, 0L, 0L, hashMap);
		}
		return staffList;
	}

	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<Staff> STAFF_RESULT_DETAILS_MAPPER = new ResultMapper<Staff>() {
		
		public Staff map(final ResultSet resultSet) throws SQLException {
			final Staff staffResult = new Staff(
					resultSet.getString("full_name"),
					resultSet.getString("gender"),
					resultSet.getDate("dob"),
					resultSet.getString("facility_name"), //0L, //					final String facilityName,  : timc 2/3/2016
					resultSet.getLong("facility_fk"), 			//					final long facilityId,		: timc 2/3/2016
					resultSet.getString("facility_id"),    		//					final String facilityNum,	: timc 2/3/2016
					resultSet.getLong("spectra_mrn"),
					resultSet.getString("client_name"), 0L,
					null,
					resultSet.getString("external_id"),
					resultSet.getString("lab"),
					null,0l, "", "", 0l, 0l, null,
					null, 0l, null);
// : timc 2/3/2016
//			public Staff(final String fullName, final String gender,
//					final Date dateOfBirth,
//					final String facilityName, final long facilityId,
//					final String facilityNum, final long spectraMRN,
//					final String clinicName, final long clinicId,
//					final String patientHLABId,
//					final String externalID, final String labName,
//					final String corpName, final long corpId, final String accountId,
//					final String HLABNumber, final long startIndex,
//					final long endIndex, final String sortDirection,
//					final String sortField, final long listSize,			
//					final Date lastDrawDate) {			
			
			
//			System.out.println("facility_id ::::::::::: "+resultSet.getString("facility_id"));
//			System.out.println("facility_name ::::::::::: "+resultSet.getString("facility_name"));
//			System.out.println("spectra_mrn    ::::::::::: "+resultSet.getString("spectra_mrn"));
//			System.out.println("facility_fk ::::::::::: "+resultSet.getLong("facility_fk"));
//			
//			System.out.println("## 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1#");
//			System.out.println("@@ StaffDAOImpl. <ResultMapper> :   "+staffResult.toString());
//			System.out.println("## 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1#");
			 
			return staffResult;
		}
	};

	/**
	 * Query to get the staff details.
	 */
	private static final String GET_STAFF_RESULT_DETAILS = "SELECT dp.full_name, "
			+ "dp.dob, dp.gender, "
			+ " dp.spectra_mrn, dp.external_id, dc.name as client_name, "
			+ " df.facility_id, df.name as facility_name, df.phone_number, dp.facility_fk, "
			+ "DECODE(df.east_west_flag,'SE','Rockleigh','SW','Milpitas',df.east_west_flag) "
			+ "AS lab FROM ih_dw.dim_staff dp JOIN"
			+ " ih_dw.dim_facility df on df.facility_pk = dp.facility_fk"
			+ " JOIN ih_dw.dim_client dc on dc.client_pk = df.client_fk "
			+ "WHERE dp.spectra_mrn = ? and dp.facility_fk = ?";						// TIMC
	// + "WHERE dp.spectra_mrn = ? ";
	
		//	+ " df.facility_id, df.name as facility_name, df.phone_number,  "      				<================== WAS: timc 2/3/2016
		//  + " df.facility_id, df.name as facility_name, df.phone_number, dp.facility_fk, " 	<================== NOW: timc 2/3/2016

	
	/**
	 * Query to get the staff details.
	 */
	private static final String GET_STAFF_RESULT_DETAILS_NFF = "SELECT dp.full_name, "  	 // TIMC
			+ "dp.dob, dp.gender, "
			+ " dp.spectra_mrn, dp.external_id, dc.name as client_name, "
			+ " df.facility_id, df.name as facility_name, df.phone_number, dp.facility_fk, "
			+ "DECODE(df.east_west_flag,'SE','Rockleigh','SW','Milpitas',df.east_west_flag) "
			+ "AS lab FROM ih_dw.dim_staff dp JOIN"
			+ " ih_dw.dim_facility df on df.facility_pk = dp.facility_fk"
			+ " JOIN ih_dw.dim_client dc on dc.client_pk = df.client_fk "
	//		+ "WHERE dp.spectra_mrn = ? and dp.facility_fk = ?";							// TIMC
	+ "WHERE dp.spectra_mrn = ? ";
	
	
	/**
	 * This method is used to display staff details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @return An object of StaffResult.
	 */
	public Staff getStaffDetails(final long facilityId,
			final long spectraMRN) {
		
		// System.out.println("@@ StaffDAOImpl. <getStaffDetails> :  "+facilityId +", " + spectraMRN);
		
		Staff staffResult = null;
		List<Staff> staffResultLst = new ArrayList<>();
		ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setLong(1, spectraMRN);
				// TIMC
				if (facilityId == 0)  {
					//System.out.println("@@ #############  <getStaffDetails> : facilityId :  "+facilityId);
				} else {
					preparedStatement.setLong(2, facilityId);					 
				}
				// TIMC
//				preparedStatement.setLong(2, facilityId);						 
			}
		};
		
		
		try {

	    	//System.out.println("@@ StaffDAOImpl. <getStaffDetails> : GET_STAFF_RESULT_DETAILS:  "+GET_STAFF_RESULT_DETAILS);
			
	    	// TIMC
			if (facilityId == 0)  {
				staffResultLst = DataAccessHelper.executeQuery(	GET_STAFF_RESULT_DETAILS_NFF, paramMapper,	STAFF_RESULT_DETAILS_MAPPER);
			} else {
				staffResultLst = DataAccessHelper.executeQuery(	GET_STAFF_RESULT_DETAILS, paramMapper,	STAFF_RESULT_DETAILS_MAPPER);					 
			}
			// TIMC
			
			// TIMC	staffResultLst = DataAccessHelper.executeQuery(	GET_STAFF_RESULT_DETAILS, paramMapper,	STAFF_RESULT_DETAILS_MAPPER);
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		if (null != staffResultLst && !staffResultLst.isEmpty()) {
			staffResult = staffResultLst.get(0);
		}
		 
		return staffResult;
	}
	
	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<Staff> STAFF_REQ_DETAILS_MAPPER = new ResultMapper<Staff>() {
		public Staff map(final ResultSet resultSet) throws SQLException {
			final Staff staffResult = new Staff(
					resultSet.getString("patient_name"),
					resultSet.getString("gender"),
					resultSet.getDate("patient_dob"),
					resultSet.getString("facility_name"), 0l, null,
					resultSet.getLong("spectra_mrn"), null, 0l,
				//	resultSet.getString("modality"),
					resultSet.getString("hlab_number"),
					resultSet.getString("external_id"), null,
					resultSet.getString("corporation_name"), 0l, "", "", 0l, 0l,
					null, null, 0l, null);
			 			
			return staffResult;
		}
	};

	/**
	 * Query to get the patient requisition details.
	 */
	private static final String GET_STAFF_REQ_DETAILS = "select dlo.requisition_id,"
			+ " dlo.collection_date, DECODE(dlo.requisition_status,'F',"
			+ "'Final','P','Partial',dlo.requisition_status) as "
			+ "requisition_status, dlo.patient_name, dlo.patient_dob,"
			+ "dlo.gender, "
			+ " dlo.initiate_id, sm.spectra_mrn, dlo.chart_num,"
			+ " CASE WHEN dlo.external_mrn = sm.spectra_mrn THEN null ELSE "         
			+ "dlo.external_id END external_id, "
			+ "da.account_number, da.hlab_number, da.name account_name,"
			+ " df.facility_id primary_facility_number, df.name facility_name,"
			+ "  dc.name corporation_name, dlo.draw_frequency, "
			+ "count(distinct dlod.lab_order_details_pk) test_count,"
			+ " sum( CASE WHEN dlod.order_detail_status = 'X' THEN 1 ELSE 0 END)"
			+ " cancelled_test_ind, sum( CASE WHEN r.abnormal_flag in "
			+ "('AH','AL','EH','EL','CA','CE') THEN 1 ELSE 0 END) "
			+ "alert_exception_ind from ih_dw.dim_lab_order dlo "
			+ "JOIN ih_dw.spectra_mrn_associations sma ON "
			+ "dlo.spectra_mrn_assc_fk = sma.spectra_mrn_assc_pk "
			+ "JOIN ih_dw.spectra_mrn_master sm ON "
			+ "sma.spectra_mrn_fk = sm.spectra_mrn_pk "
			+ "JOIN ih_dw.dim_account da ON dlo.account_fk = da.account_pk "
			+ "JOIN ih_dw.dim_facility df ON da.facility_fk = df.facility_pk "
			+ "JOIN ih_dw.dim_client dc ON df.client_fk = dc.client_pk "
			+ "JOIN ih_dw.dim_lab_order_details dlod on"
			+ " dlod.lab_order_fk = dlo.lab_order_pk LEFT OUTER "
			+ "JOIN ih_dw.results r on r.lab_order_fk = dlo.lab_order_pk "
			+ "and r.lab_order_details_fk = dlod.lab_order_details_pk "
			+ "WHERE dlo.requisition_id = ? AND dlod.test_category not in ('MISC','PENDING')"
			+ " group by dlo.requisition_id,"
			+ " dlo.collection_date, DECODE(dlo.requisition_status,'F'"
			+ ",'Final','P','Partial',dlo.requisition_status), dlo.patient_name,"
			+ " dlo.patient_dob, dlo.gender,"
			+ ", dlo.initiate_id,"
			+ " sm.spectra_mrn, "
			+ "dlo.chart_num, dlo.external_mrn, dlo.alternate_patient_id, "
			+ "dlo.ordering_physician_name, da.account_number, da.hlab_number, "
			+ "da.name , df.facility_id, df.name , dc.name,dlo.draw_frequency";

	/**
	 * This method is used to display requisition staff details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of StaffResult.
	 */
	public Staff getStaffReqInfo(final String facilityNum,
			final String requisitionNum) {
		Staff staffResult = null;
		List<Staff> staffResultLst = new ArrayList<>();
		ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1, requisitionNum);
			}
		};
		try {
			staffResultLst = DataAccessHelper.executeQuery(
					GET_STAFF_REQ_DETAILS, paramMapper,
					STAFF_REQ_DETAILS_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		if (null != staffResultLst && !staffResultLst.isEmpty()) {
			staffResult = staffResultLst.get(0);
		}
		return staffResult;
	}
}
