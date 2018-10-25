/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.dao;

import com.spectra.symfonie.common.util.DateUtil;
import com.spectra.symfonie.dataaccess.framework.DataAccessHelper;
import com.spectra.symfonie.dataaccess.framework.ParamMapper;
import com.spectra.symfonie.dataaccess.framework.ResultMapper;
import com.spectra.symfonie.framework.constants.ExceptionConstants;
import com.spectra.symfonie.framework.exception.ApplicationException;
import com.spectra.symfonielabs.domainobject.Accession;
import com.spectra.symfonielabs.domainobject.Equipment;
import com.spectra.symfonielabs.domainobject.FacilityDemographics;
//import com.spectra.symfonielabs.domainobject.OrderSummary;
import com.spectra.symfonielabs.domainobject.OrderEquipmentSummary;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Results;
//import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.Search;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This interface contains methods that is used to have patient related
 * activities.
 * 
 */
public class EquipmentDAOImpl implements EquipmentDAO {

	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<Equipment> EQP_DET_MAPPER = new ResultMapper<Equipment>() {
		public Equipment map(final ResultSet resultSet) throws SQLException {
			final Equipment searchResultLst = new Equipment(
					resultSet.getString("machine_name"),
					resultSet.getString("serial_number"),  
					resultSet.getString("spectra_mrn"),
					resultSet.getString("lab"),
					resultSet.getString("client_name"),
					resultSet.getString("facility_name"),
					resultSet.getLong("facility_fk"),
					resultSet.getString("facility_id"),
					resultSet.getString("phone_number"),
					0l,0l, "", "", resultSet.getLong("total_entity_cnt"),  
					resultSet.getDate("last_draw_date"));
			      searchResultLst.setEntityIndex(resultSet.getLong("sl_no"));

			return searchResultLst;
		}
	};

	
	public static final String GET_EQP_DET_SQL = 
			"select "
			+"sl_no,"
			+"machine_name,"
			+"serial_number,"
			+"spectra_mrn,"
			+"name CLIENT_NAME,"
			+"facility_id,"
			+"name NAME,"
			+"phone_number,"
			+"facility_fk,"
			+"lab,"
			+"FACILITY_NAME,"
			+"last_draw_date,"
			+"total_entity_cnt"
			+" FROM"
			+" (SELECT ROWNUM sl_no,"
			+"machine_name,"
			+"serial_number,"
			+"spectra_mrn,"
			+"name CLIENT_NAME,"
			+"facility_id,"
			+"name NAME,"
			+"phone_number,"
			+"facility_fk,"
			+"lab,"
			+"FACILITY_NAME,"
			+"last_draw_date,"
			+ "total_entity_cnt"
			+ " FROM"
			+ " (SELECT ROW_NUMBER() OVER (ORDER BY rownum asc) sl_no,"
			+"de.machine_name,"
			+"de.serial_number,"
			+"de.spectra_mrn,"
			+"dc.name CLIENT_NAME,"
			+"df.facility_id,"
			+" df.name NAME,"
			+"df.phone_number,"
			+"de.facility_fk,	"		  
			+"DECODE(df.east_west_flag,'SE','Rockleigh','SW','Milpitas',df.east_west_flag) AS lab,"
			+"dc.corporate_acronym"
			+" || ' - '"
			+" || df.display_name FACILITY_NAME,"
			+"dd.day AS last_draw_date,"
			+"COUNT (de.SPECTRA_MRN) OVER (PARTITION BY NULL) total_entity_cnt"
			+" FROM ih_dw.dim_equipment de"
			+" JOIN ih_dw.dim_facility df"
			+" ON df.facility_pk = de.facility_fk"
			+" JOIN ih_dw.dim_client dc"
			+" ON dc.client_pk = df.client_fk"
			+" JOIN ih_dw.AGG_EQUIPMENT_MAX_LAB_MV amax"
			+" ON amax.equipment_fk = de.equipment_pk"
			+" JOIN ih_dw.dim_date dd"
			+" ON dd.date_pk = amax.collection_date_fk ";
			

			//+ " JOIN ih_dw.dim_client dc   on  dc.client_pk = df.client_fk ";	

   
	/**
	 * Gets the search results for the patient.
	 * 
	 * @param facilityId
	 *            - Holds the facility Id.
	 * @param patName
	 *            - Holds the patient name.
	 * @return list of SearchResult - Holds the search details.
	 * 
	 */
	public List<Equipment> getSearchResult(final Search equipmentSearch) {

		List<Equipment> equipmentList;
		ParamMapper paramMapper = new ParamMapper() {

			public void mapParam(PreparedStatement preparedStatement)  throws SQLException {
				
			/**
			 * EQUIPMENT S-e-a-r-c-h   R-e-s-u-l-t-s  
			 * 
			 */
				
				int i = 0;
				if (equipmentSearch.getSearchValue() != null) {
					
					// // System.out.println("@@ EquipmentDAOImpl. --2  equipmentSearch.getSearchValue() :  "+equipmentSearch.getSearchValue());
					// = searchType: Equipment~|~searchValue: HD~|~facilityId: 3934~|~facilityNum: ~|~startIndex: 0~|~endIndex: 0
					//		if (equipmentSearch.getSearchValue().split(" ").length > 1) {
					//			String searchParams[] = equipmentSearch.getSearchValue().split(" ");
					//			
					//			// // System.out.println("@@ EquipmentDAOImpl. --3  equipmentSearch.getSearchValue() :  "+searchParams);
					//			
					//			preparedStatement.setString(++i, searchParams[0].trim().toUpperCase().concat("%"));
					//			//preparedStatement.setString(++i, searchParams[1].trim().toUpperCase().concat("%"));
					//			//preparedStatement.setString(++i, searchParams[1].trim().toUpperCase().concat("%"));
					//			//preparedStatement.setString(++i, searchParams[0].trim().toUpperCase().concat("%"));
					//			
					//		} else {
// timc 2/18/2016
					//preparedStatement.setString(++i, equipmentSearch.getSearchValue().trim().toUpperCase().concat("%"));
					//preparedStatement.setString(++i, "%"+equipmentSearch.getSearchValue().trim().toUpperCase().concat("%"));
					//preparedStatement.setString(++i, equipmentSearch.getSearchValue().replaceFirst("\\s+$", "").replace(" ", "%").trim().toUpperCase().concat("%"));

					preparedStatement.setString(++i, equipmentSearch.getSearchValue().replaceAll("\\s+", " ").replaceAll("\\s", "%").trim().toUpperCase().concat("%"));

					//	replaceAll("\\s+", " ").replaceAll("\\s", "%")
					//	// System.out.println("@@ EquipmentDAOImpl: (1)  equipmentSearch.getSearchValue() : "+ equipmentSearch.getSearchValue().replaceFirst("\\s+$", "").replace(" ", "%").trim().toUpperCase().concat("%"));
					//	value.replaceFirst("\\s+$", "");
//timc 2/18/2016
					//	preparedStatement.setString(++i, equipmentSearch.getSearchValue().trim().toUpperCase().concat("%"));
					//	preparedStatement.setString(++i, equipmentSearch.getSearchValue().trim().toUpperCase().concat("%"));
					// }
				}

				if (equipmentSearch.getFacilityId() > 0) {
					
					preparedStatement.setLong(++i,	equipmentSearch.getFacilityId());
				}
			}

		};
		
		StringBuffer query = new StringBuffer();
		query.append(GET_EQP_DET_SQL);
		
		// WHERE  (UPPER(de.machine_name) LIKE 'HD%')  AND  de.facility_fk = 7607  ORDER BY UPPER(de.machine_name) ASC  -- ) ) 
		
		StringBuffer whereClause = new StringBuffer();
		if (null != equipmentSearch.getSearchValue()) {
			//	if (equipmentSearch.getSearchValue().split(" ").length > 1) {
			//		whereClause.append(" (( UPPER (de.machine_name) LIKE ? ) AND ");
			//		//		+ " AND UPPER (DP.FIRST_NAME) LIKE ? ) OR ( UPPER ");
			//		//      whereClause.append("(DP.LAST_NAME) LIKE ? AND UPPER "
			//		//		+ " (DP.FIRST_NAME) LIKE ? )) AND ");
			//	} else {
			
			whereClause.append(" (UPPER(de.machine_name) LIKE ? )");
			
			//	+ " OR UPPER(DP.FIRST_NAME) LIKE ? "
			//	+ " OR UPPER(DP.FULL_NAME) LIKE ?)");
			//  }
		}

		if (equipmentSearch.getFacilityId() > 0) {
			if(null != whereClause && whereClause.length() > 0){
				whereClause.append(" AND ");
			}
			whereClause.append(" de.facility_fk = ? ");
		}
		if(null != whereClause && whereClause.length() > 0){
			query.append(" WHERE "+whereClause);
		}
  
		String sortDirection = " ASC ";
		if (equipmentSearch.getSortDirection() != null
				&& equipmentSearch.getSortDirection().equalsIgnoreCase("DESC")) {
			sortDirection = " DESC ";
		}
		if (equipmentSearch.getSortField() != null) {
			if (equipmentSearch.getSortField().equalsIgnoreCase("fullName")) {
				query.append(" ORDER BY UPPER(de.machine_name) ").append(sortDirection) .append(", to_date(last_Draw_Date,'dd-mon-yy') DESC ");
			}  else if (equipmentSearch.getSortField().equalsIgnoreCase("lastDrawDate")) {
				query.append(" order by to_date(last_draw_date,'dd-mon-yy')").append(sortDirection)
					.append(" , UPPER(de.machine_name) ").append(" ASC ");		
			} else if (equipmentSearch.getSortField().equalsIgnoreCase("serialNumber")) {
				query.append(" ORDER BY UPPER(de.serial_number) ").append(sortDirection)
					.append(" , UPPER(de.machine_name) ").append(" ASC ");				
			} else if (equipmentSearch.getSortField().equalsIgnoreCase("spectraMRN")) {
				query.append(" ORDER BY TO_NUMBER(de.spectra_mrn) ").append(sortDirection)
					.append(" , UPPER(de.machine_name) ").append(" ASC ");			
			}
 
			//		if (equipmentSearch.getSortField() != null) {
			//			if (equipmentSearch.getSortField().equalsIgnoreCase("machineName")) {
			//				query.append(" ORDER BY UPPER (de.machine_name) ").append(sortDirection).append(" ,UPPER (de.machine_name)").append(sortDirection);
			//			} else if (equipmentSearch.getSortField().equalsIgnoreCase("inmateId")) {
			//				query.append(" ORDER BY UPPER (de.machine_name) ")
			//						.append(sortDirection).append(" ,UPPER (de.machine_name) ")
			//						.append(" ASC ").append(" ,UPPER (de.machine_name)")
			//						.append(" ASC ");
			//			} else if (equipmentSearch.getSortField().equalsIgnoreCase(
			//					"dateofBirth")) {
			//				query.append(" ORDER BY UPPER (p.date_of_birth) ").append(sortDirection)
			//						.append(" ,UPPER (DP.LAST_NAME) ").append(" ASC ")
			//						.append(" ,UPPER (DP.FIRST_NAME)").append(" ASC ");
			//			}
			//
			//		} else {
						/*query.append(" ORDER BY UPPER (DP.LAST_NAME) ").append(sortDirection)
								.append(" ,UPPER (DP.FIRST_NAME)").append(sortDirection);*/
		} else {
			query.append(" ORDER BY UPPER(de.machine_name)").append(sortDirection).append(", to_date(last_draw_date,'dd-mon-yy') DESC ");
		}
		//	query.append(" ORDER BY UPPER(DP.FULL_NAME) ").append(sortDirection)
		//	.append(",UPPER(last_general_lab_date) DESC " );
		// .append(",to_date(last_general_lab_date,'dd-mon-yy') DESC ");

			
			//		}

	 	query.append(" )) ");         
		
		//		if (equipmentSearch.getStartIndex() != 0  && equipmentSearch.getEndIndex() != 0) {
		//			
		//		    // // System.out.println("wwwwwwwwwwwww 03 03 03 03 03 @@ EquipmentDAOImpl. <getSearchResult> : getStartIndex ====>> :  "+equipmentSearch.getStartIndex());
		//		    // // System.out.println("wwwwwwwwwwwww 03 03 03 03 03 @@ EquipmentDAOImpl. <getSearchResult> : getEndIndex  ====>> :  "+equipmentSearch.getEndIndex());
		//		    
		//			query.append(" WHERE SL_NO BETWEEN "  + equipmentSearch.getStartIndex() + " AND  " + equipmentSearch.getEndIndex());
		//        }
 		
		try {
			equipmentList = DataAccessHelper.executeQuery(query.toString(),	paramMapper, EQP_DET_MAPPER);
			
		} catch (SQLException exception) {
			// Construct a HashMap holding parameters of this method and
			// pass it to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("equipmentSearch", equipmentSearch.toString());
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,	exception, 0L, 0L, hashMap);
		}
 		
		return equipmentList;
	}

	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<Equipment> EQUIPMENT_RESULT_DETAILS_MAPPER = new ResultMapper<Equipment>() {
		public Equipment map(final ResultSet resultSet) throws SQLException {
			final Equipment equipmentResult = new Equipment(
					resultSet.getString("machine_name"),
					resultSet.getString("serial_number"),  
					resultSet.getString("spectra_mrn"),
					resultSet.getString("lab"),
					resultSet.getString("client_name"),
					resultSet.getString("facility_name"),//					final String facilityName, 
					resultSet.getLong("facility_fk"),    //					final long facilityId,
					resultSet.getString("facility_id"), //					final String facilityNum,
					resultSet.getString("phone_number"),
					0l,0l, "", "", 0,  resultSet.getDate("last_draw_date"));	

			return equipmentResult;
		}
	};

	/**
	 * Query to get the patient details.
	 */
	public static final String GET_EQUIPMENT_RESULT_DETAILS = 
			"SELECT de.machine_name, de.serial_number,  de.spectra_mrn, dc.name CLIENT_NAME, "
			+ " df.facility_id, df.name FACILITY_NAME, df.phone_number, de.facility_fk"
			+ " ,ROW_NUMBER() OVER (ORDER BY  UPPER(de.machine_name)) sl_no "
			+ " ,DECODE(df.east_west_flag,'SE','Rockleigh','SW','Milpitas',df.east_west_flag) AS lab"
	+ " ,dd.day AS last_draw_date "			
			+ " FROM ih_dw.dim_equipment de "
			+ " JOIN ih_dw.dim_facility df on  df.facility_pk = de.facility_fk "
			+ " JOIN ih_dw.dim_client dc   on  dc.client_pk = df.client_fk "	
	+ " JOIN ih_dw.AGG_EQUIPMENT_MAX_LAB_MV amax  ON  amax.equipment_fk = de.equipment_pk "
	+ " JOIN ih_dw.dim_date dd  ON  dd.date_pk = amax.collection_date_fk  "			
			+ " WHERE  (UPPER(de.spectra_mrn) LIKE ? ) AND  de.facility_fk = ? ";


	
	/**
	 * This method is used to display patient details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @return An object of PatientResult.
	 */
	 
	
	public Equipment getEquipmentDetails(final long facilityId, final String spectraMRN) {
		
		/**
		 * EQUIPMENT INFORMATION, O-R-D-E-R   S-U-M-M-A-R-Y > 
		 * 
		 */
		
		Equipment equipmentResult = null;
		List<Equipment> equipmentResultLst = new ArrayList<>();
		ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, spectraMRN.trim().toUpperCase().concat("%"));
				preparedStatement.setLong(2, facilityId);		
			}
		};
		try {
		//	System.out.println("GET_EQUIPMENT_RESULT_DETAILS "+GET_EQUIPMENT_RESULT_DETAILS);
			equipmentResultLst = DataAccessHelper.executeQuery(	GET_EQUIPMENT_RESULT_DETAILS, paramMapper,	EQUIPMENT_RESULT_DETAILS_MAPPER);  // <====
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		if (null != equipmentResultLst && !equipmentResultLst.isEmpty()) {
			equipmentResult = equipmentResultLst.get(0);
		}
			
		return equipmentResult;
	}
	
	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<Equipment> EQUIPMENT_REQ_DETAILS_MAPPER = new ResultMapper<Equipment>() {
		public Equipment map(final ResultSet resultSet) throws SQLException {
			final Equipment equipmentResult = new Equipment(
					resultSet.getString("machine_name"),
					resultSet.getString("serial_number"),  
					resultSet.getString("spectra_mrn"),
					resultSet.getString("lab"),
					resultSet.getString("client_name"),
					resultSet.getString("facility_name"),
					resultSet.getLong("facility_fk"),
					resultSet.getString("facility_id"),
					resultSet.getString("phone_number"),
					0l,0l, "", "", 0,  resultSet.getDate("last_draw_date"));	

			return equipmentResult;
		}
	};

	
	/**
	 * Query to get the patient requisition details.
	 */
	
	public static final String GET_EQUIPMENT_REQ_DETAILS = 
			"SELECT de.machine_name, de.serial_number,  de.spectra_mrn, dc.name CLIENT_NAME, df.facility_id, df.name NAME, df.phone_number, de.facility_fk"
			+ " ,ROW_NUMBER() OVER (ORDER BY  UPPER(de.machine_name)) sl_no "
			+ " ,DECODE(df.east_west_flag,'SE','Rockleigh','SW','Milpitas',df.east_west_flag) AS lab, dc.corporate_acronym	|| ' - ' || df.display_name FACILITY_NAME"					
	+ " ,dd.day AS last_draw_date "			
			+ " FROM ih_dw.dim_equipment de "
			+ " JOIN ih_dw.dim_facility df on  df.facility_pk = de.facility_fk "
			+ " JOIN ih_dw.dim_client dc   on  dc.client_pk = df.client_fk "
	+ " JOIN ih_dw.AGG_EQUIPMENT_MAX_LAB_MV amax  ON  amax.equipment_fk = de.equipment_pk "
	+ " JOIN ih_dw.dim_date dd  ON  dd.date_pk = amax.collection_date_fk  "			
			+ " WHERE  (UPPER(de.machine_name) LIKE ? ) AND  de.facility_fk = ? ";		

	
	/**
	 * This method is used to display requisition patient details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of PatientResult.
	 */
	public Equipment getEquipmentReqInfo(final String facilityNum,
			final String requisitionNum) {
		Equipment equipmentResult = null;
		List<Equipment> equipmentResultLst = new ArrayList<>();
		ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1, requisitionNum);
			}
		};
		try {
		//	System.out.println("GET_EQUIPMENT_REQ_DETAILS "+GET_EQUIPMENT_REQ_DETAILS);
			equipmentResultLst = DataAccessHelper.executeQuery(
					GET_EQUIPMENT_REQ_DETAILS, paramMapper,
					EQUIPMENT_REQ_DETAILS_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		if (null != equipmentResultLst && !equipmentResultLst.isEmpty()) {
			equipmentResult = equipmentResultLst.get(0);
		}
		
		return equipmentResult;
	}
	

	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<OrderEquipmentSummary> ORDER_DET_MAPPER = new ResultMapper<OrderEquipmentSummary>() {
		public OrderEquipmentSummary map(final ResultSet resultSet) throws SQLException {
			boolean abnormalFlag = false;
			boolean cancelledTestIndicator = false;
			if (0 != resultSet.getInt("alert_exception_ind")) {
				abnormalFlag = true;
			}
			if (0 != resultSet.getInt("cancelled_test_ind")) {
				cancelledTestIndicator = true;
			}		
			final OrderEquipmentSummary orderResultLst = new OrderEquipmentSummary(0L,
					resultSet.getDate("collection_date"),
					resultSet.getString("requisition_id"),
					resultSet.getInt("test_count"),
					resultSet.getString("draw_frequency"),
					resultSet.getString("requisition_status"), 0, 0L, 
					resultSet.getInt("NumOfTubesNotReceived"),
					null,null, null, 0, null, 0L, null,
					resultSet.getLong("total_count"),
					resultSet.getLong("sl_no"),
					abnormalFlag, cancelledTestIndicator, 
					resultSet.getString("SPECIMEN_METHOD_DESC"));
			final FacilityDemographics fac = new FacilityDemographics(
					resultSet.getString("display_name"),
					resultSet.getString("corporate_acronym"));
			
			orderResultLst.setFacility(fac);
			
			return orderResultLst;
		}
	};

	
	/**
	 * Query to get the order summary details.               // for EQUIPMENT REQUISITION DETAIL or ORDER SUMMARY ?
	 */
	private static final String GET_ORDER_DET_SQL = new StringBuffer()
	.append("SELECT sl_no, total_count, requisition_id, collection_date, requisition_status, draw_frequency, test_count, " )
	.append("cancelled_test_ind, alert_exception_ind, SPECIMEN_METHOD_DESC,corporate_acronym, display_name, NumOfTubesNotReceived " )
	.append("FROM " )
	.append("	(SELECT rownum sl_no, total_count, requisition_id, collection_date, requisition_status, draw_frequency, test_count, cancelled_test_ind, alert_exception_ind, corporate_acronym,SPECIMEN_METHOD_DESC,display_name, NumOfTubesNotReceived ")
	.append("	FROM ")
	.append("  		( ")
	.append("SELECT COUNT (DISTINCT dlo.requisition_id) over (PARTITION BY NULL) total_count, dlo.requisition_id, ") 
	.append("dlo.collection_date, ")
	.append("DECODE(dlo.requisition_status,'F','Final', 'P','Partial',dlo.requisition_status) AS requisition_status, ")
	.append("dlo.draw_frequency, COUNT(DISTINCT dlod.lab_order_details_pk) test_count, ")
	.append(" SUM( CASE WHEN (dlod.order_detail_status = 'X' OR dlod.clinical_status = 'CM') ")
	.append(" AND dlod.order_control_reason IS NOT NULL")
	.append(" AND ((SELECT COUNT(order_control_reason_code)")  
	.append(" FROM ih_dw.lov_order_control_reason") 
	.append(" WHERE order_control_reason  = dlod.order_control_reason ") 
	.append(" AND reportable_flag='1') > 0)") 	
	.append(" THEN 1 ELSE 0 END) cancelled_test_ind,")
	.append("SUM( CASE WHEN r.derived_abnormal_flag IN ('AH','AL','EH','EL','CA','CE') ")
	.append("THEN 1 ELSE 0 END ) alert_exception_ind, dlod.SPECIMEN_METHOD_DESC, ")
	.append("df.corporate_acronym, df.display_name, ")
	.append("NumOfTubesNotReceived ")
	.append("FROM ih_dw.dim_lab_order dlo ")
	.append("JOIN ih_dw.spectra_mrn_associations sma ")
	.append("ON dlo.spectra_mrn_assc_fk = sma.spectra_mrn_assc_pk ")
	.append("JOIN ih_dw.dim_facility df ")
	.append("ON sma.facility_fk = df.facility_pk ")
	.append("JOIN ih_dw.spectra_mrn_master sm ")
	.append("ON sma.spectra_mrn_fk = sm.spectra_mrn_pk ")
	.append("JOIN ih_dw.dim_lab_order_details dlod ")
	.append("ON dlod.lab_order_fk = dlo.lab_order_pk ")
	.append("LEFT OUTER JOIN ih_dw.results r ")
	.append("ON r.lab_order_fk = dlo.lab_order_pk ")
	.append("AND r.lab_order_details_fk = dlod.lab_order_details_pk ")
	.append("LEFT OUTER JOIN  ( select requisition_id, (sum(1) - sum(case when specimen_received_date_time is null then 0 else 1 end)) NumOfTubesNotReceived ")
	.append("from ")
	.append("(WITH accession_tbl AS ")
	.append("(SELECT dlod2.requisition_id, dlod2.accession_number, MIN(dlod2.specimen_received_date_time) specimen_received_date_time ")
	.append("FROM ih_dw.dim_lab_order_details dlod2 ")
	.append("WHERE dlod2.test_category NOT IN ('MISC','PENDING', 'PAT') ")
	.append("GROUP BY dlod2.requisition_id, dlod2.accession_number), ")
	.append("container_tbl AS ")
	.append("(SELECT dlod2.requisition_id, dlod2.accession_number, dlod2.specimen_container_desc, ")
	.append("dlod2.specimen_method_desc ")
	.append("FROM ih_dw.dim_lab_order_details dlod2 ")
	.append("WHERE dlod2.test_category NOT IN ('MISC','PENDING','CALC', 'PAT') ")
	.append("GROUP BY dlod2.requisition_id, dlod2.accession_number, ")
	.append("dlod2.specimen_container_desc, dlod2.specimen_method_desc) ")
	.append("SELECT accession_tbl.requisition_id, accession_tbl.accession_number, container_tbl.specimen_container_desc, ")
	.append("container_tbl.specimen_method_desc, accession_tbl.specimen_received_date_time ")
	.append("FROM accession_tbl, container_tbl ")
	.append("WHERE accession_tbl.requisition_id = container_tbl.requisition_id (+) ")
	.append("AND accession_tbl.accession_number = container_tbl.accession_number (+)) tubesTbl1 "  )   
	.append("     group by tubesTbl1.requisition_id ) tubesTbl ")   
	.append("  	ON tubesTbl.requisition_id = dlo.requisition_id ")
	.append("			WHERE trunc(dlo.collection_date) between trunc(?) and trunc(sysdate) ")
	.append("	AND dlod.test_category NOT IN ('MISC','PENDING') ")
	.append("				AND sm.spectra_mrn = ? ")
	.append("  				AND sma.facility_fk = ? ")
	.append("		GROUP BY dlo.requisition_id, dlo.collection_date, NumOfTubesNotReceived, df.corporate_acronym, df.display_name,")
	.append("			DECODE(dlo.requisition_status,'F','Final','P','Partial',dlo.requisition_status) ,")
	.append("	dlo.draw_frequency,  ")
	.append("  dlod.SPECIMEN_METHOD_DESC ")
	.append("ORDER BY dlo.collection_date DESC))  ").toString();  
	/**
	 * This method is used to get all the order details.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @param drawDate
	 *            - Holds the draw date.
	 * @return A list of OrderSummary object.
	 */
	public List<OrderEquipmentSummary> getOrderSummary(final long facilityId, final long spectraMRN, final Date drawDate, final OrderEquipmentSummary orderSumm) {
		List<OrderEquipmentSummary> orderSummaryLst = new ArrayList<>();
		StringBuffer orderSummQuery = new StringBuffer();
		orderSummQuery.append(GET_ORDER_DET_SQL);
		ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setDate(1,
						DateUtil.convertUtilDateToSqlDate(drawDate));
				preparedStatement.setLong(2, spectraMRN);
				preparedStatement.setLong(3, facilityId);
			}
		};
		if (orderSumm.getStartIndex() != 0 && orderSumm.getEndIndex() != 0) {
			orderSummQuery.append(" WHERE SL_NO BETWEEN "
                    + orderSumm.getStartIndex() + " AND  "
                    + orderSumm.getEndIndex());
		}
		
		try {
		//	System.out.println("ORDER_DET_MAPPER "+ORDER_DET_MAPPER);
			orderSummaryLst = DataAccessHelper.executeQuery(
					orderSummQuery.toString(), paramMapper, ORDER_DET_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return orderSummaryLst;
	}

	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<Accession> TUBE_DET_MAPPER = new ResultMapper<Accession>() {
		public Accession map(final ResultSet resultSet) throws SQLException {
			final Accession tubeTypeDetLst = new Accession(
					resultSet.getString("accession_number"),
					resultSet.getString("specimen_container_desc"),
					resultSet.getString("specimen_method_desc"),
					resultSet.getDate("specimen_received_date_time"),
					resultSet.getString("condition"),
					resultSet.getString("derived_status"),
					resultSet.getInt("lab_fk"));
			
			return tubeTypeDetLst;
		}
	};

	/**
	 * Query to get the tube type summary details.
	 */
	private static final String GET_TUBE_DET_SQL = "select "
			+ "accession_number, specimen_container_desc,"
			+ "specimen_method_desc, specimen_received_date_time,"
			+ " (select max(order_test_name ) from ih_dw.dim_lab_order_details "
			+ "where requisition_id = main_tbl.requisition_id "
			+ "and accession_number = main_tbl.accession_number and "
			+ "test_category = 'MISC') condition, CASE WHEN "
			+ "(in_process_count > 0 AND specimen_received_date_time is null ) "
			+ "THEN 'Intransit' WHEN scheduled_count = test_count THEN "
			+ "'Scheduled' WHEN in_process_count > 0 THEN 'In Process' WHEN "
			+ "cancel_count = test_count THEN 'Cancelled' WHEN "
			+ "complete_count = test_count THEN 'Complete' WHEN "
			+ "done_count > 0 THEN 'Partial Complete' ELSE "
			+ "null END as derived_status,lab_fk FROM ( WITH accession_tbl AS "
			+ "(SELECT dlod.requisition_id, dlod.accession_number, "
			+ "min(dlod.specimen_received_date_time) "
			+ "specimen_received_date_time, count(*) test_count, " 
			+ "sum(CASE WHEN clinical_status = 'S' THEN 1 ELSE 0 END) "
			+ "scheduled_count, "
			+ "sum(CASE WHEN clinical_status in ( 'A', 'T', 'V') "
			+ "THEN 1 ELSE 0 END) in_process_count, "
			+ "sum(CASE WHEN clinical_status = 'D' THEN 1 ELSE 0 END) done_count, "
			+ "sum(CASE WHEN clinical_status in ('CM', 'D', 'X') THEN 1 ELSE 0 END) "
			+ "complete_count, sum(CASE WHEN clinical_status IN ('CM', 'X') THEN 1 ELSE 0 END) "
			+ "cancel_count,dlod.lab_fk FROM ih_dw.dim_lab_order_details dlod "		
			+ " WHERE "
			+ "dlod.requisition_id = UPPER(?) AND dlod.test_category "
			+ "not in ('MISC','PENDING', 'PAT')"
			+ " GROUP BY dlod.requisition_id, "
			+ "dlod.accession_number,dlod.lab_fk), container_tbl AS "
			+ "(SELECT dlod.requisition_id, dlod.accession_number,dlod.lab_fk, "
			+ "dlod.specimen_container_desc, dlod.specimen_method_desc "
			+ "FROM ih_dw.dim_lab_order_details dlod"
			+ " WHERE "
			+ "dlod.requisition_id = UPPER(?) AND dlod.test_category "
			+ "NOT IN ('MISC','PENDING','CALC', 'PAT') "
			+ " GROUP BY "
			+ "dlod.requisition_id, dlod.accession_number, "
			+ "dlod.specimen_container_desc, "
			+ "dlod.specimen_method_desc,dlod.lab_fk) SELECT accession_tbl.requisition_id, "
			+ "accession_tbl.accession_number, container_tbl.specimen_container_desc,accession_tbl.lab_fk, "
			+ "container_tbl.specimen_method_desc, "
			+ "accession_tbl.specimen_received_date_time, accession_tbl.test_count, "
			+ "accession_tbl.scheduled_count, accession_tbl.in_process_count, "
			+ "accession_tbl.done_count, accession_tbl.complete_count, "
			+ "accession_tbl.cancel_count FROM accession_tbl, "
			+ "container_tbl WHERE accession_tbl.requisition_id = "
			+ "container_tbl.requisition_id (+) AND "
			+ "accession_tbl.accession_number = "
			+ "container_tbl.accession_number (+) ) main_tbl "
			+ "ORDER BY accession_number";

	/**
	 * This method is used to get the tube summary details.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of object OrderSummary.
	 */
	public List<Accession> getTubeSummary(final String requisitionNo) {
		List<Accession> tubeSummaryLst = new ArrayList<Accession>();
		ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1, requisitionNo);
			}
		};
		try {
			tubeSummaryLst = DataAccessHelper.executeQuery(GET_TUBE_DET_SQL,
					paramMapper, TUBE_DET_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return tubeSummaryLst;
	}
	
	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<Results> ORDER_REQ_DET_MAPPER =
			new ResultMapper<Results>() {
		public Results map(final ResultSet resultSet) throws SQLException {
			final Results results = new Results(null,
					resultSet.getString("accession_number"),
					resultSet.getString("patient_type_sub_group"),
					resultSet.getString("result_test_name"),
					resultSet.getString("textual_result_full"),
					resultSet.getString("unit_of_measure"),
					resultSet.getString("reference_range"),
					resultSet.getString("result_comment"),
					resultSet.getString("abnormal_flag"),
					resultSet.getString("clinical_status"), 
					resultSet.getString("order_control_reason"),
					resultSet.getString("order_test_name"), null,  //, resultSet.getString("sh_msg"), timc 8/16/2016 
					resultSet.getInt("parent_test_count"));	
 
			return results;
		}
	};

	/**
	 * Query to get the order requisition details.
	 */
	private static final String GET_ORDER_REQ_DET_SQL = "select r.accession_number,"
			+ " r.result_test_name, r.textual_result, r.unit_of_measure, "
			+ "r.reference_range, r.derived_abnormal_flag as abnormal_flag, r.result_comment, "
			+ "NVL(lcs.clinical_status_def, dlod.clinical_status) clinical_status"
			+ " FROM ih_dw.results r JOIN ih_dw.dim_lab_order_details dlod "
			+ "on dlod.lab_order_details_pk = r.lab_order_details_fk "
			+ "LEFT OUTER JOIN ih_dw.lov_clinical_status lcs on "
			+ "lcs.clinical_status = dlod.clinical_status "
			+ "WHERE r.requisition_id = ? ORDER BY r.accession_number";
	
	/**
	 * This method is used to get the order details based on requisition number.
	 * 
	 * @param requisitionNo
	 *            - Holds the requisition number.
	 * @return A list of Results.
	 */
	public List<Results> getOrderTestDetails(final String requisitionNo) {
		List<Results> lstResults = new ArrayList<Results>();
		ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1, requisitionNo);
			}
		};
		try {
		//	System.out.println("Equipment: GET_ORDER_REQ_DET_SQL "+GET_ORDER_REQ_DET_SQL);
			lstResults = DataAccessHelper.executeQuery(GET_ORDER_REQ_DET_SQL,
					paramMapper, ORDER_REQ_DET_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return lstResults;
	}
	
	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<RequisitionDetails> PATIENT_REQ_DETAILS_MAPPER = new ResultMapper<RequisitionDetails>() {
		public RequisitionDetails map(final ResultSet resultSet)
				throws SQLException {
			boolean abnormalFlag = false;
			boolean cancelledTestIndicator = false;
			final Patient patientResult = new Patient(
					resultSet.getString("patient_name"),
					resultSet.getString("gender"),
					resultSet.getString("ordering_physician_name"),
					resultSet.getDate("patient_dob"),
					resultSet.getString("facility_name"), 0l, null,
					resultSet.getLong("spectra_mrn"), null, 0l,
					resultSet.getString("modality"),
					resultSet.getString("initiate_id"),
					resultSet.getString("external_mrn"),
					resultSet.getString("lab_name"),
					resultSet.getString("corporation_name"), 0l,
					resultSet.getString("account_number"),
					resultSet.getString("hlab_number"), 0l, 0l, null, null, 0l, new Date(),null );
			if (0 != resultSet.getInt("alert_exception_ind")) {
				abnormalFlag = true;
			}
			if (0 != resultSet.getInt("cancelled_test_ind")) {
				cancelledTestIndicator = true;
			}
			final RequisitionDetails reqDetails = new RequisitionDetails(
					resultSet.getString("requisition_id"), patientResult,
					resultSet.getDate("collection_date"),
					resultSet.getString("requisition_status"), "",
					resultSet.getString("draw_frequency"),
					resultSet.getInt("test_count"), abnormalFlag,
					cancelledTestIndicator, null, null,null);
								
			return reqDetails;
		}
	};

	/**
	 * Query to get the patient requisition details.
	 */
	private static final String GET_ENV_REQ_DETAILS = new StringBuffer()
		.append("select dlo.requisition_id,")
		.append(" dlo.collection_date, DECODE(dlo.requisition_status,'F'," )
		.append("'Final','P','Partial',dlo.requisition_status) as ")
		.append("requisition_status, dlo.patient_name, dlo.patient_dob,")
		.append("dlo.gender, NVL(dm.modality_description, dlo.modality_code ) ")
		.append("modality , dlo.initiate_id, sm.spectra_mrn, dlo.chart_num,")
		.append(" dlo.external_mrn, dlo.ordering_physician_name, ")
		.append("da.account_number, da.hlab_number, da.name account_name,")
		.append(" df.facility_id primary_facility_number, df.name facility_name,")
		.append(" dc.name corporation_name, dlo.draw_frequency, ")
		.append(" DECODE(df.EAST_WEST_FLAG,'SW','Milpitas','SE','Rockleigh',")
		.append("df.EAST_WEST_FLAG) as lab_name,")
		.append("count(distinct dlod.lab_order_details_pk) test_count,")
		.append(" SUM( CASE WHEN (dlod.order_detail_status = 'X' OR dlod.clinical_status = 'CM') ")
		.append(" AND dlod.order_control_reason IS NOT NULL")
		.append(" AND ((SELECT COUNT(order_control_reason_code)")  
		.append(" FROM ih_dw.lov_order_control_reason") 
		.append(" WHERE order_control_reason = dlod.order_control_reason") 
		.append(" AND reportable_flag='1') > 0)") 	
		.append(" THEN 1 ELSE 0 END) cancelled_test_ind,")
		.append(" sum( CASE WHEN r.derived_abnormal_flag in ")
		.append("('AH','AL','EH','EL','CA','CE') THEN 1 ELSE 0 END) ")
		.append("alert_exception_ind from ih_dw.dim_lab_order dlo ")
		.append("JOIN ih_dw.spectra_mrn_associations sma ON ")
		.append("dlo.spectra_mrn_assc_fk = sma.spectra_mrn_assc_pk ")
		.append("JOIN ih_dw.spectra_mrn_master sm ON ")
		.append("sma.spectra_mrn_fk = sm.spectra_mrn_pk ")
		.append("JOIN ih_dw.dim_account da ON dlo.account_fk = da.account_pk ")
		.append("JOIN ih_dw.dim_facility df ON da.facility_fk = df.facility_pk ")
		.append("JOIN ih_dw.dim_client dc ON df.client_fk = dc.client_pk ")
		.append("JOIN ih_dw.dim_modality dm ON dm.modality_code = dlo.modality_code ")
		.append("JOIN ih_dw.dim_lab_order_details dlod on")
		.append(" dlod.lab_order_fk = dlo.lab_order_pk LEFT OUTER ")
		.append("JOIN ih_dw.results r on r.lab_order_fk = dlo.lab_order_pk ")
		.append("and r.lab_order_details_fk = dlod.lab_order_details_pk ")
		.append("WHERE dlo.requisition_id = ? AND dlod.test_category not in ")
		.append("('MISC','PENDING') group by dlo.requisition_id,")
		.append(" dlo.collection_date, DECODE(dlo.requisition_status,'F'")
		.append(",'Final','P','Partial',dlo.requisition_status), dlo.patient_name,")
		.append(" dlo.patient_dob, dlo.gender, NVL(dm.modality_description, ")
		.append(" dlo.modality_code )  , dlo.initiate_id, sm.spectra_mrn, ")
		.append("dlo.chart_num, dlo.external_mrn, dlo.alternate_patient_id, ")
		.append("dlo.ordering_physician_name, da.account_number, da.hlab_number, ")
		.append("da.name , df.facility_id, df.name , dc.name,dlo.draw_frequency,")
		.append(" DECODE(df.EAST_WEST_FLAG,'SW','Milpitas','SE','Rockleigh',")
		.append(" df.EAST_WEST_FLAG)").toString();

	/**
	 * This method is used to display requisition patient details.
	 * 
	 * @param facilityNum
	 *            - Holds the facility number.
	 * @param requisitionNum
	 *            - Holds the requisition number.
	 * @return An object of RequisitionDetails.
	 */
	public RequisitionDetails getPatientReqInfo(final String facilityNum,
			final String requisitionNum) {
		RequisitionDetails patientResult = new RequisitionDetails();
		List<RequisitionDetails> patientResultLst = new ArrayList<>();
		ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1, requisitionNum);
			}
		};
		try {
			patientResultLst = DataAccessHelper.executeQuery(
					GET_ENV_REQ_DETAILS, paramMapper,
					PATIENT_REQ_DETAILS_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		if (null != patientResultLst && !patientResultLst.isEmpty()) {
			patientResult = patientResultLst.get(0);
		}
		
		return patientResult;
	}
	
}
