/* ===================================================================*/
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.dao;

import com.spectra.symfonie.common.constants.CommonConstants;
import com.spectra.symfonie.common.util.StringUtil;
import com.spectra.symfonie.dataaccess.framework.CallableParamMapper;
import com.spectra.symfonie.dataaccess.framework.DataAccessHelper;
import com.spectra.symfonie.dataaccess.framework.ParamMapper;
import com.spectra.symfonie.dataaccess.framework.ResultMapper;
import com.spectra.symfonie.framework.exception.BusinessException;
import com.spectra.symfonielabs.domainobject.FacilityDemographics;
import com.spectra.symfonielabs.domainobject.FacilitySearch;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import com.spectra.symfonielabs.domainobject.Search;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

/**
 * This interface contains methods that is used to have patient related
 * activities.
 * 
 */
public class FacilityDAOImpl implements FacilityDAO {

	/**
	 * Result mapper to map the result set to search object.
	 */
	private static final ResultMapper<Search> FAC_DET_MAPPER = 
			new ResultMapper<Search>() {
		public Search map(final ResultSet resultSet) throws SQLException {
			final Search searchDet = new Search(null,
					resultSet.getString("FACILITY_NAME"),
					resultSet.getLong("FACILITY_PK"),
					resultSet.getString("FACILITY_ID"),
					resultSet.getString("FACILITY_NAME"));
			return searchDet;
		}
	};

	/**
	 * Query to get the search details.
	 */
	
	private static final String GET_FACILITY_DETAILS = new StringBuffer()
	.append("SELECT distinct df.corporate_acronym || ' - ' || DF.DISPLAY_NAME as FACILITY_NAME, ")
	.append("DF.FACILITY_ID AS FACILITY_ID, DF.FACILITY_PK, df.name as fac_name FROM ")
	.append("IH_DW.DIM_FACILITY DF JOIN ih_dw.dim_account da ON da.facility_fk = ")
	.append("df.facility_pk JOIN ih_dw.dim_client dc ON dc.client_pk = df.client_fk ")
	.append( " WHERE UPPER(DISPLAY_NAME) LIKE ? ")
	.append( " and da.account_type IN ")
	.append( "('STUDY','DRAW STATION','IN-HOUSE EAST', 'IN-HOUSE WEST','SPECTRA WEST', ")
	.append( "'SPECTRA EAST') ")
	.append( "AND upper(da.account_status) IN ('ACTIVE','IN PROGRESS','TRANSFERRED') ")
	.append( "order by fac_name asc").toString(); 

	/**
	 * Gets the search results for the facility.
	 * 
	 * @param searchName
	 *            - Holds the search facility name.
	 * @return List<Search> - Holds the list of search details.
	 * 
	 */
	public List<Search> getfacilities(final String searchName) {

		List<Search> searchDetList = new ArrayList<Search>();
		final ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1, "%" + searchName.toUpperCase()
						+ "%");
			}
		};
		try {
			searchDetList = DataAccessHelper.executeQuery(GET_FACILITY_DETAILS,
					paramMapper, FAC_DET_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return searchDetList;
	}
	/**
	 * Query to get the dashboard details.
	 */
	private static final String GET_DASHBOARD_DETAILS = new StringBuffer()
	.append("select collection_date, upper(decode(requisition_status,'F','Complete','P','Partial Complete',requisition_status)) ")
	.append("requisition_status, patient_type_sub_group, count(*) as requisition_count, ")
	.append("sum(decode(requisition_status, 'P', 1, 0)) as partial_count, ")
	.append("sum(decode(requisition_status, 'F', 1, 0)) as complete_count ")
	.append("from ih_dw.dim_lab_order dlo join ih_dw.lov_patient_type lpt ")
	.append("on lpt.patient_type = dlo.patient_type where dlo.collection_date ")
	.append("between trunc(sysdate - 7) and trunc(sysdate) ").toString(); 
	/**
	 * Result mapper to map the result set to RequisitionDetails object.
	 */
	private static final ResultMapper<RequisitionDetails> GET_DASHBOARD_MAPPER = 
			new ResultMapper<RequisitionDetails>() {
		public RequisitionDetails map(final ResultSet resultSet) throws SQLException {
			final RequisitionDetails reqDetailObj = new RequisitionDetails(
					resultSet.getString("requisition_status"),
					resultSet.getString("patient_type_sub_group"),
					resultSet.getDate("collection_date"),
					resultSet.getLong("requisition_count"));
			return reqDetailObj;
		}
	};
	public List<RequisitionDetails> getDashboardDetails(final String labComboVal,
			final String reqTypeComboVal) {
		final ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				int counter = 0;
				if (!reqTypeComboVal.equalsIgnoreCase("All")) {
					preparedStatement.setString(++counter, reqTypeComboVal);
				}
				if (!"All".equalsIgnoreCase(labComboVal)) {
					preparedStatement.setString(++counter, labComboVal);
				}
			}
		};
		List<RequisitionDetails> reqDetailsLst = new ArrayList<RequisitionDetails>();
		StringBuffer query = new StringBuffer();
		try {
			query.append(GET_DASHBOARD_DETAILS);
			if (!"All".equalsIgnoreCase(reqTypeComboVal)) {
				query.append("and patient_type_sub_group = upper(?) ");
			}
			if (!"All".equalsIgnoreCase(labComboVal)) {
				query.append("and dlo.lab_fk = (select L.lab_pk from ih_dw.DIM_LAB L "
						+ "where L.lab_id = ?)");
			}
			query.append("group by collection_date, requisition_status, patient_type_sub_group "
					+ "order by collection_date asc");
			reqDetailsLst = DataAccessHelper.executeQuery(query.toString(), paramMapper,
					GET_DASHBOARD_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return reqDetailsLst;
	}
	private static final String GET_CORPORATIONS = new StringBuffer()
	.append( "select distinct df.corporate_acronym || ' - ' || dc.name as SelCorpName, ")
	.append( "dc.client_pk as corporation_id, ") 
	.append( "dc.name as corporation_name, dc.CORPORATE_ACRONYM as acronym from ih_dw.dim_facility df, ")
	.append( "ih_dw.dim_client dc, ih_dw.dim_account da where df.client_fk ")
	.append( "= dc.client_pk AND df.facility_pk = da.facility_fk and ")
	.append( "da.account_type IN ")
	.append( "('STUDY','DRAW STATION','IN-HOUSE EAST', 'IN-HOUSE WEST','SPECTRA WEST', ")
	.append( "'SPECTRA EAST') AND upper(da.account_status) IN ")
	.append( "('ACTIVE','IN PROGRESS','TRANSFERRED') and ")
	.append( "(upper(dc.name) like ? or upper(dc.CORPORATE_ACRONYM) like ? )") 
	.append( " order by acronym,corporation_name asc").toString();
	
	/**
	 * Result mapper to map the result set to search object.
	 */
	private static final ResultMapper<Search> CORP_DETAILS_MAPPER = 
			new ResultMapper<Search>() {
		public Search map(final ResultSet resultSet) throws SQLException {
			final Search searchDet = new Search(
					resultSet.getString("corporation_name"),
					resultSet.getLong("corporation_id"), 
					resultSet.getString("acronym"),
					resultSet.getString("selCorpName"),0L, null, null);
			return searchDet;
		}
	};
	/**
     * Gets the search results for the corporation.
     * 
     * @param searchName -
     *            Holds the search facility name.
     * @return List<Search> - Holds the list of search details.
     * 
     */
	public List<Search> getCorporations(final String searchName) {
		List<Search> searchDetList = new ArrayList<Search>();
		final ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1, "%" + searchName.toUpperCase()
						+ "%");
				preparedStatement.setString(2, "%" + searchName.toUpperCase()
						+ "%");
			}
		};
		try {
			searchDetList = DataAccessHelper.executeQuery(GET_CORPORATIONS,
					paramMapper, CORP_DETAILS_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return searchDetList;
	}
	
	/**
	 * SQL query for retreiving facility search results by one or more of the below fields
	 * 1) Corporate Acronym
	 * 2) Corporate Group Name
	 * 3) Facility
	 */
	private static final String GET_FAC_SEARCH_RESULTS = new StringBuffer()
		.append("select sl_no, total_entity_cnt, facility_id, facility_name, corporation_name, ")
		.append(	   "account_type, account_status, servicing_lab, hlab_number, acc_facility_id ")
		.append("from (")
		.append(  "select rownum sl_no, total_entity_cnt, facility_id, facility_name, ")
		.append(		 "corporation_name, account_type, account_status, servicing_lab, ")
		.append(		 "hlab_number, acc_facility_id ")
		.append(  "from (")
		.append(	"SELECT distinct f.facility_pk as facility_id, f.display_name as facility_name, ")
		.append(		   "c.name as corporation_name, a.account_type, a.account_status, ")
		.append(		   "decode(f.east_west_flag, 'SE', 'Rockleigh', 'SW', 'Milpitas', f.east_west_flag) AS servicing_lab, ")
		.append(		   "count(distinct f.facility_pk) over (partition by null) as total_entity_cnt, ")
		.append(		   "a.hlab_number, a.facility_id as acc_facility_id ")
		.append(	"FROM ih_dw.dim_facility f, ")
		.append(		 "ih_dw.dim_client c, ")
		.append(		 "ih_dw.dim_account a ")
		.append(	"WHERE f.client_fk = c.client_pk ")
		.append(	  "AND f.facility_pk = a.facility_fk ")
		.append(	  "AND a.facility_id = a.hlab_number ")
		.append(	  "AND a.account_type IN ('STUDY','DRAW STATION','IN-HOUSE EAST', 'IN-HOUSE WEST','SPECTRA WEST', 'SPECTRA EAST') ")
		.append(	  "AND upper(a.account_status) IN ('ACTIVE','IN PROGRESS','TRANSFERRED') ").toString();
	
	/**
	 * Result mapper to map the result set to FacilitySearch object.
	 */
	private static final ResultMapper<FacilitySearch> FAC_SEARCH_RESULTS_MAPPER = 
			new ResultMapper<FacilitySearch>() {
		public FacilitySearch map(final ResultSet resultSet)
				throws SQLException {
			final FacilitySearch facilitySearch = new FacilitySearch(
					resultSet.getString("facility_name"),
					resultSet.getLong("facility_id"),
					resultSet.getString("corporation_name"),
					resultSet.getString("account_type"),
					resultSet.getString("account_status"),
					resultSet.getString("servicing_lab"), 
					resultSet.getLong("total_entity_cnt"), 
					resultSet.getLong("sl_no"),
					resultSet.getString("hlab_number"));
			return facilitySearch;
		}
	};
	/**
	 * Gets the facility search results based on the entered corporation name
	 * and facility name.
	 * 
	 * @param corporationId
	 *            - Holds the corporation Id.
	 * @param corporationName
	 *            - Holds the corporation name.
	 * @param facilityName
	 *            - Holds the facility name.
	 * @return List<FacilitySearch> - Holds the list of facility search details.
	 * @throws BusinessException
	 */
	public List<FacilitySearch> getFacSearchResults(final Search searchCriteria) {
		StringBuffer query = new StringBuffer();
		List<FacilitySearch> facSearchResultsLst = new ArrayList<FacilitySearch>();
		final ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				int counter = 0;
				if (searchCriteria.getCorporationId() > 0) {
					preparedStatement.setLong(++counter, searchCriteria.getCorporationId());
				}
				if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
						.valueOf(searchCriteria.getFacilityName()))) {
					preparedStatement.setString(++counter, "%"
							+ searchCriteria.getFacilityName().toUpperCase()
							+ "%");
				}
			}
		};
		try {
			query.append(GET_FAC_SEARCH_RESULTS);
			if (searchCriteria.getCorporationId() > 0) {
				query.append("and c.client_pk = ? ");
			}
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(searchCriteria.getFacilityName()))) {
				query.append("and upper(f.display_name) like ?");
			}
			
			String sortDirection = " ASC ";
			if (searchCriteria.getSortDirection() != null
					&& searchCriteria.getSortDirection().equalsIgnoreCase("DESC")) {
				sortDirection = " DESC ";
			}			
			
			query.append(" ORDER BY ");
			
			if ( searchCriteria.getSortField() != null) {
				
				Map<String, String> columnMap = new HashMap<String, String>();
				columnMap.put("facilityName", "f.display_name");
				columnMap.put("corporationName", "c.name");
				columnMap.put("accountType", "a.account_type");
				columnMap.put("accountStatus", "a.account_status");
				columnMap.put("servicingLab", "servicing_lab");				
				
				query.append("UPPER(").append(columnMap.get(searchCriteria.getSortField())).append(") ")
				.append(sortDirection);				
				
				if (!searchCriteria.getSortField().equalsIgnoreCase("facilityName")) {					
					query.append(", UPPER(f.display_name) ASC");				
				}
				

			} else {
				query.append(" UPPER(f.display_name) ASC");				
			}			
			query.append(" ))");
			facSearchResultsLst = DataAccessHelper.executeQuery(query.toString(),
					paramMapper, FAC_SEARCH_RESULTS_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return facSearchResultsLst;
	}
	
	private static final String GET_FAC_DEMOGRAPHICS =  new StringBuffer()
				.append("SELECT F.DISPLAY_NAME AS facility_name, c.name AS ")
				.append( "corporation_name, f.facility_pk, f.corporate_acronym ")
				.append( "|| ' - ' || f.DISPLAY_NAME AS display_fac_name, ")
				.append( "decode(f.east_west_flag, 'SE', 'Rockleigh', 'SW', 'Milpitas', ")
				.append( "f.east_west_flag) AS servicing_lab, a.type_of_service, ")
				.append( "f.clinical_rep AS NCE, f.sales_rep, f.phone_number, f.state, ")
				.append( "a.facility_id as acc_facility_id, f.time_zone, f.order_system,f.patient_report, ")	
				.append( "a.CLINICAL_MANAGER,a.MEDICAL_DIRECTOR,a.ADMINISTRATOR,a.PHONE_COMMENTS,")
				.append( "DECODE(f.ami_database, 'eCube Server A', 'A', ")
				.append( "'eCube Server B', 'B', 'eCube Server C', 'C', f.east_west_flag) ")
				.append( "AS ecube_server, ")
				.append( "TO_CHAR(NVL2 (f.open_time_mo_we_fr, f.open_time_tu_th_sa, ")
				.append( "f.open_time_sa), 'HH:MI AM') AS open_time, ")
				.append( "TO_CHAR(NVL2 (f.close_time_mo_we_fr, f.close_time_tu_th_sa, ")
				.append( "f.close_time_sa), 'HH:MI AM') AS close_time,  ")
				.append( "f.draw_week, ")
				.append( "RTRIM(NVL2(f.day_draw1, f.day_draw1 ||',',NULL) || ")
				.append( "NVL2(f.day_draw2, f.day_draw2 ||',',NULL) || ")
				.append( "NVL2(f.day_draw3, f.day_draw3 ||',',NULL) || ")
				.append( "NVL2(f.day_draw4, f.day_draw4 ||',',NULL) || ")
				.append( "NVL2(f.day_draw5, f.day_draw5 ||',',NULL),',') draw_days, ")
				.append( "DECODE(f.kits_indicator, '0', 'N', '1', 'Y', f.kits_indicator) ")
				.append( "AS kit_indicator, (f.address_line1 || ' ' || f.address_line2 || ")
				.append( "', ' || f.city || ', ' || f.state || ' ' || f.zip || ' ' || f.country ) ")
				.append( "as mailing_address, (f.phys_address_line1 || ' ' || f.phys_address_line2 ")
				.append( "|| ', ' || f.phys_city || ', ' || f.phys_state || ' ' || f.phys_zip || ' ' || f.country) as ")
				.append( "physical_address, a.alert_notes as alert_info, f.kit_comments, ")
				.append( "DECODE(f.kits_custom_indicator, '0', 'N', '1', 'Y', f.kits_custom_indicator) ")
				.append( "as kits_custom_indicator, ")
				.append( "DECODE(f.kits_cust_monthly_indicator, '0', 'N', '1', 'Y', ")
				.append( "f.kits_cust_monthly_indicator) as kits_cust_monthly_indicator, ")
				.append( "DECODE(f.kits_cust_mid_indicator, '0', 'N', '1', 'Y', ")
				.append( "f.kits_cust_mid_indicator) as kits_cust_mid_indicator, ")
				.append( "DECODE(f.kits_generic_indicator, '0', 'N', '1', 'Y', ")
				.append( "f.kits_generic_indicator) as kits_generic_indicator, ")
				.append( "DECODE(f.kits_gen_monthly_indicator, '0', 'N', '1', 'Y', ")
				.append( "f.kits_gen_monthly_indicator) as kits_gen_monthly_indicator, ")
				.append( "DECODE(f.kits_gen_monthly_indicator, '0', 'N', '1', 'Y', ")
				.append( "f.kits_gen_monthly_indicator) as kits_gen_mid_indicator, ")
				.append( "f.corporate_acronym, ")
				.append( " (CASE")
				.append( " WHEN f.internal_external_flag ='I'")
				.append( " THEN")
				.append( " (SELECT SUM(patient_count)  FROM ih_dw.dim_account")
				.append( " WHERE facility_id = f.facility_id AND ((account_category <> 'Water' or account_category is null) and  (type_of_service ='FULL' or type_of_service is null) ))")
				.append( " ELSE")
				.append( " (SELECT SUM(patient_count) FROM ih_dw.dim_account")
    			.append( " WHERE facility_id = f.facility_id AND account_category  IN ('HEMO','PD','HOME HEMO'))")
    			.append( " END) AS patient_count,")
    			.append( " (CASE")
    			.append( " WHEN f.internal_external_flag ='I'")
    			.append( " THEN")
    			.append( " (SELECT SUM(hemo_count) FROM ih_dw.dim_account")
				.append( " WHERE facility_id = f.facility_id AND ((account_category <> 'Water' or account_category is null)   and  (type_of_service ='FULL' or type_of_service is null)))")
				.append( " ELSE")
				.append( " (SELECT SUM(hemo_count) FROM ih_dw.dim_account")
    			.append( " WHERE facility_id = f.facility_id AND account_category  IN ('HEMO','PD','HOME HEMO'))")
    			.append( " END) AS hemo_count,")
    			.append( " (CASE")
    			.append( " WHEN f.internal_external_flag ='I'")
    			.append( " THEN")
				.append( " (SELECT SUM(hh_count)FROM ih_dw.dim_account")
				.append( " WHERE facility_id = f.facility_id AND ((account_category <> 'Water' or account_category is null)  and  (type_of_service ='FULL' or type_of_service is null)))")
				.append( " ELSE")
				.append( " (SELECT SUM(hh_count) FROM ih_dw.dim_account")
    			.append( " WHERE facility_id = f.facility_id AND account_category  IN ('HEMO','PD','HOME HEMO'))")
    			.append( " END) AS hh_count,")
    			.append( " (CASE")
    			.append( " WHEN f.internal_external_flag ='I'")
				.append( " THEN ")
				.append( " (SELECT SUM(pd_count) FROM ih_dw.dim_account")
				.append( " WHERE facility_id = f.facility_id AND ((account_category <> 'Water' or account_category is null)  and  (type_of_service ='FULL' or type_of_service is null)))")
				.append( " ELSE")
    		    .append( " (SELECT SUM(pd_count) FROM ih_dw.dim_account")
    			.append( " WHERE facility_id = f.facility_id AND account_category  IN ('HEMO','PD','HOME HEMO'))")
    			.append( " END) AS pd_count, ")
				.append(" f.supply_depot,f.sap_number,f.supply_delivery_sch1,f.supply_delivery_sch2,f.supply_delivery_sch3,to_char(f.first_received_date,'MM/DD/YYYY') as first_received_date ,")
				.append(" to_char(f.discontinued_date,'MM/DD/YYYY') as discontinued_date ")
				.append( "FROM ih_dw.dim_facility f, ih_dw.dim_account a, ")
				.append( "ih_dw.dim_client c WHERE f.client_fk = c.client_pk AND ")
				.append( "f.facility_pk = a.facility_fk AND f.facility_id = ")
				.append( "a.hlab_number AND f.facility_id = ?").toString();
	
	private static final ResultMapper<FacilityDemographics> 
	GET_FAC_DEMOGRAPHICS_MAPPER = new ResultMapper<FacilityDemographics>() {
		public FacilityDemographics map(final ResultSet resultSet)
				throws SQLException {
			String customIndicator = resultSet
					.getString("kits_custom_indicator");
			String genericIndicator = resultSet
					.getString("kits_generic_indicator");
			String customMonthInd = resultSet
					.getString("kits_cust_monthly_indicator");
			String customMidInd = resultSet
					.getString("kits_cust_mid_indicator");
			String genericMonthInd = resultSet
					.getString("kits_gen_monthly_indicator");
			String genericMidInd = resultSet
					.getString("kits_gen_mid_indicator");
			String customIndVal = CommonConstants.EMPTY_STRING;
			String genericIndVal = CommonConstants.EMPTY_STRING;
			String finalIndicatorVal = CommonConstants.EMPTY_STRING;
			//Differentiate the custom indicator types.
			if ("Y".equalsIgnoreCase(customIndicator)) {
				if ("Y".equalsIgnoreCase(customMonthInd)
						&& "Y".equalsIgnoreCase(customMidInd)) {
					customIndVal = "Custom Monthly, Custom Mid";
				} else if ("Y".equalsIgnoreCase(customMonthInd) && 
						"N".equalsIgnoreCase(customMidInd)) {
					customIndVal = "Custom Monthly";
				} else if ("N".equalsIgnoreCase(customMonthInd) && 
						"Y".equalsIgnoreCase(customMidInd)) {
					customIndVal = "Custom Mid";
				}
			}
			//Differentiate the generic indicator types.
			if ("Y".equalsIgnoreCase(genericIndicator)) {
				if ("Y".equalsIgnoreCase(genericMonthInd)
						&& "Y".equalsIgnoreCase(genericMidInd)) {
					genericIndVal = "Generic Monthly, Generic Mid";
				} else if ("Y".equalsIgnoreCase(genericMonthInd) && 
						"N".equalsIgnoreCase(genericMidInd)) {
					genericIndVal = "Generic Monthly";
				} else if ("N".equalsIgnoreCase(genericMonthInd) && 
						"Y".equalsIgnoreCase(genericMidInd)) {
					genericIndVal = "Generic Mid";
				}
			}
			if (CommonConstants.EMPTY_STRING.equalsIgnoreCase(customIndVal)) {
				finalIndicatorVal = genericIndVal;
			} else if (CommonConstants.EMPTY_STRING
					.equalsIgnoreCase(genericIndVal)) {
				finalIndicatorVal = customIndVal;
			}
			final FacilityDemographics facDemographicsObj = new FacilityDemographics(
					resultSet.getString("facility_name"),
					resultSet.getString("corporation_name"),
					resultSet.getString("servicing_lab"),
					resultSet.getString("type_of_service"),
					resultSet.getString("NCE"),
					resultSet.getString("sales_rep"),
					resultSet.getString("phone_number"),
					resultSet.getString("state"),
					resultSet.getString("acc_facility_id"),
					resultSet.getString("time_zone"),
					resultSet.getString("order_system"),
					resultSet.getString("ecube_server"),
					resultSet.getString("patient_report"),
					resultSet.getString("draw_week"),
					resultSet.getString("draw_days"),
					resultSet.getString("kit_indicator"),
					resultSet.getString("physical_address"),
					resultSet.getString("mailing_address"),
					resultSet.getString("alert_info"), finalIndicatorVal,
					resultSet.getString("kit_comments"), 
					resultSet.getString("corporate_acronym"),
					resultSet.getLong("facility_pk"),
					resultSet.getString("display_fac_name"),
					resultSet.getString("clinical_manager"),
					resultSet.getString("medical_director"),
					resultSet.getString("administrator"),
					resultSet.getString("phone_comments"),
					resultSet.getString("patient_count"),
					resultSet.getString("hemo_count"),
					resultSet.getString("pd_count"),
					resultSet.getString("hh_count"),
					resultSet.getString("supply_depot"),
					resultSet.getString("sap_number"),
					resultSet.getString("supply_delivery_sch1"),
					resultSet.getString("supply_delivery_sch2"),
					resultSet.getString("supply_delivery_sch3"),
					resultSet.getString("first_received_date"),
					resultSet.getString("discontinued_date"));

			return facDemographicsObj;
		}
	};
	
	public FacilityDemographics getFacDemographics(final String hLABNum) {
		List<FacilityDemographics> facDemographicsLst = 
				new ArrayList<FacilityDemographics>();
		FacilityDemographics facilityDemographics = new FacilityDemographics();
		final ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1, hLABNum);
			}
		};
		try {
			facDemographicsLst = DataAccessHelper.executeQuery(GET_FAC_DEMOGRAPHICS,
					paramMapper, GET_FAC_DEMOGRAPHICS_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		if (null != facDemographicsLst && !facDemographicsLst.isEmpty()) {
			facilityDemographics = facDemographicsLst.get(0);
		}
		return facilityDemographics;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
// timc   US- 1963
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
// WAS:
//	private static final String GET_FAC_DEM_ACCOUNTS = 
//			"SELECT a.account_category, a.account_status, "
//			+ "a.account_number, a.hlab_number, a.phone_number, a.fax_number "
//			+ "FROM ih_dw.dim_account a WHERE a.facility_id = ? " 
//			+ " ORDER BY a.account_category";

	private static final String GET_FAC_DEM_ACCOUNTS =  new StringBuffer()
			.append("SELECT a.account_category, a.account_status,")
			.append("a.account_number, a.hlab_number, a.phone_number,a.fax_number")
			.append(", (CASE a.type_of_service WHEN 'FULL' THEN 0 ELSE 1 END) main_account ")	
			.append(", (CASE a.account_status WHEN 'Inactive #' THEN 1 ELSE 0 END ) status ") 
			.append(",patient_count ")
			.append("FROM ih_dw.dim_account a WHERE a.facility_id = ? " )
			.append("and a.account_type NOT IN ('HOME PATIENT', 'DRAW STATION')  " )
			.append(" ORDER BY main_account, status, a.account_status, a.account_category").toString();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//timc 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	private static final ResultMapper<FacilityDemographics> 
	GET_FAC_DEM_ACCOUNTS_MAPPER = new ResultMapper<FacilityDemographics>() {
		public FacilityDemographics map(final ResultSet resultSet)
				throws SQLException {
			final FacilityDemographics facDemographicsObj = 
					new FacilityDemographics(
					resultSet.getString("account_category"),
					resultSet.getString("account_status"),
					resultSet.getString("account_number"),
					resultSet.getString("hlab_number"), 
					resultSet.getString("phone_number"), 
					resultSet.getString("fax_number"),
					resultSet.getString("patient_count"));
			return facDemographicsObj;
		}
	};
	
	public List<FacilityDemographics> getFacDemoAccountLst(final String facilityId) {
		List<FacilityDemographics> facDemoAccLst = new ArrayList<FacilityDemographics>();
		final ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1, facilityId);
			}
		};
		try {
			facDemoAccLst = DataAccessHelper.executeQuery(GET_FAC_DEM_ACCOUNTS,
					paramMapper, GET_FAC_DEM_ACCOUNTS_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return facDemoAccLst;
	}
	
	private static final String GET_FAC_SCHEDULE = new StringBuffer()
			.append("SELECT TO_CHAR(f.open_time_mo_we_fr, 'HH:MI AM')  || ' - '  || ")
			.append( "TO_CHAR(f.close_time_mo_we_fr, 'HH:MI AM') AS mon_wed_fri, ")
			.append( "TO_CHAR(f.open_time_tu_th_sa, 'HH:MI AM')  || ' - '  || ")
			.append( "TO_CHAR(f.close_time_tu_th_sa, 'HH:MI AM') AS Tue_thu_sat, ")
			.append( "TO_CHAR(f.open_time_sa, 'HH:MI AM')  || ' - '  || ")
			.append( "TO_CHAR(f.close_time_sa, 'HH:MI AM')AS sat ")
			.append( "FROM ih_dw.dim_facility f WHERE f.facility_id= ?").toString();

	private static final ResultMapper<FacilityDemographics> 
	GET_FAC_SCHEDULE_MAPPER = new ResultMapper<FacilityDemographics>() {
		public FacilityDemographics map(final ResultSet resultSet)
				throws SQLException {
			final FacilityDemographics facDemographicsObj = 
					new FacilityDemographics(
					resultSet.getString("mon_wed_fri"),
					resultSet.getString("Tue_thu_sat"),
					resultSet.getString("sat"));
			return facDemographicsObj;
		}
	};
	
	public List<FacilityDemographics> getFacDemoScheduleLst(
			final String facilityId) {
		List<FacilityDemographics> facDemoScheduleLst = 
				new ArrayList<FacilityDemographics>();
		final ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1, facilityId);
			}
		};
		try {
			facDemoScheduleLst = DataAccessHelper.executeQuery(GET_FAC_SCHEDULE,
					paramMapper, GET_FAC_SCHEDULE_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return facDemoScheduleLst;
	}
	
	/**
	 * Result mapper to map the result set to RequisitionDetails object.
	 */
	private static final ResultMapper<RequisitionDetails> GET_FAC_LEVEL_GRAPH__MAPPER = 
			new ResultMapper<RequisitionDetails>() {
		public RequisitionDetails map(final ResultSet resultSet) throws SQLException {
			final RequisitionDetails reqDetailObj = new RequisitionDetails(
					resultSet.getString("requisition_status"),
					resultSet.getString("patient_type_sub_group"),
					resultSet.getDate("collection_date"),
					resultSet.getLong("STATUS_COUNT"));
	
			return reqDetailObj;
		}
	};
	/**
	 * Gets the facility level graph details based on the patient type sub group
	 * (requisition type).
	 * 
	 * @param reqTypeComboVal
	 *            - Holds the requisition type.
	 * @param facilityIdVal
	 *            - Holds the facility Id Value.
	 * @return List<RequisitionDetails> - Holds the list of graph details.
	 */
	public List<RequisitionDetails> getFacLevelGraphDetails(
			final String reqTypeComboVal, final String facilityIdVal,final String drawDateVal) {
		final CallableParamMapper callableparamMapper = new CallableParamMapper() {
			public void mapParam(final CallableStatement callableStatement)
					throws SQLException {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					Date drawDateVal1  = sdf.parse(drawDateVal); 
					Calendar c = Calendar.getInstance();;
					c.setTime(drawDateVal1);
					c.add(Calendar.DAY_OF_MONTH, -7);
					Date  drawDateVal2 = c.getTime();
					String  drawDateVal3 = sdf.format(drawDateVal2);
					int counter = 0;
					callableStatement.registerOutParameter(++counter, OracleTypes.CURSOR);
					callableStatement.setString(++counter, reqTypeComboVal);
					callableStatement.setString(++counter, facilityIdVal);
					callableStatement.setString(++counter,drawDateVal3);
					callableStatement.setString(++counter, drawDateVal);
			  } catch (ParseException parseException) {
			    	parseException.printStackTrace();
			}  
		  }
		};
		List<RequisitionDetails> reqDetailsLst = new ArrayList<RequisitionDetails>();
		try {		
			String query =  "{call IH_DW.specimen_tracking_pkg.requisition_status(?,?,?,?,?)}";	
		    reqDetailsLst = DataAccessHelper.executeProcedure(query, callableparamMapper, GET_FAC_LEVEL_GRAPH__MAPPER);		 
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return reqDetailsLst;
	}
	
	/**Gets the facility level table  details based on the patient type sub group **/ 
	private static final String GET_FAC_LEVEL_TABLE = 	new StringBuffer()
	.append("{call IH_DW.specimen_tracking_pkg.patient_requisition_list(?,?,?,?,?)}").toString();

	/**
	 * Result mapper to map the result set to RequisitionDetails object.
	 */
	private static final ResultMapper<RequisitionDetails> GET_FAC_LEVEL_TABLE_MAPPER = 
			new ResultMapper<RequisitionDetails>() {
		public RequisitionDetails map(final ResultSet resultSet) throws SQLException {		
			boolean abnormalFlag = false;
			boolean cancelledTestIndicator = false;
			if (0 != resultSet.getInt("ALERT_EXCEPTION_FLAG")) {
				abnormalFlag = true;
			}
			if (0 != resultSet.getInt("CANCELLED_TEST_FLAG")) {
				cancelledTestIndicator = true;
			}
			final Patient patientResult = new Patient(
					resultSet.getString("patient_name"),
					null,
					null,
					null,
					null,
					0l, 
					null,
					resultSet.getLong("spectra_mrn"), null,
					resultSet.getLong("facility_fk"),
					null,
					null,
					null,
					null,
					null, 0l,
					null,
					null, 0l, 0l, null, null, 0l,
					null,resultSet.getString("patient_type_sub_group"));
			final RequisitionDetails reqDetails = new RequisitionDetails(
					resultSet.getString("requisition_id"), patientResult,
					resultSet.getDate("collection_date"),
					resultSet.getString("REQUISITION_STATUS"), null,
					resultSet.getString("draw_frequency"),
					resultSet.getInt("TEST_COUNT"), abnormalFlag,
					cancelledTestIndicator,
					resultSet.getString("patient_type_sub_group"), null,
					resultSet.getInt("ACCESSION_COUNT"),resultSet.getInt("TUBES_RECEIVED_COUNT"),	
				    resultSet.getInt("PARTIALTUBES_STATUS_FLAG")); 
					
			return reqDetails;
		}
	};

	/**
	 * Gets the facility level req table details based on the patient type sub group
	 * (requisition type).
	 * 
	 * @param reqTypeComboVal
	 *            - Holds the requisition type.
	 * @param facilityIdVal
	 *            - Holds the facility Id Value.
	 * @return List<RequisitionDetails> - Holds the list of graph details.
	 */
	public List<RequisitionDetails> getFacRequisitionDetails(
			final String reqTypeComboVal, final String facilityIdVal,final String drawDate,final String patType) {
	   final CallableParamMapper callableparamMapper = new CallableParamMapper() {
		public void mapParam(final CallableStatement callableStatement)
					throws SQLException {
				int counter = 0;
				String patTypeParm ="ALL";
				if (patType.equalsIgnoreCase("finalCount")){ 
					patTypeParm ="F";
				}
				if (patType.equalsIgnoreCase("inProcessCount")){ 
					patTypeParm ="P";
				}
				if (patType.equalsIgnoreCase("notReceivedCount")){ 
					patTypeParm ="N";
				}
				callableStatement.registerOutParameter(++counter, OracleTypes.CURSOR);
				callableStatement.setString(++counter, reqTypeComboVal);
				callableStatement.setString(++counter,facilityIdVal);
				callableStatement.setString(++counter,drawDate);	
				callableStatement.setString(++counter,patTypeParm);				
			}
		};
		List<RequisitionDetails> reqDetailsLst = new ArrayList<RequisitionDetails>();
		try {
            reqDetailsLst = DataAccessHelper.executeProcedure(GET_FAC_LEVEL_TABLE.toString(), callableparamMapper, GET_FAC_LEVEL_TABLE_MAPPER);					
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return reqDetailsLst;
	}

}
