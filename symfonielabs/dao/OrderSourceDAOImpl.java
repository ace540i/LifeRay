/* ===================================================================*/
/* � 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.  */
/* ===================================================================*/
package com.spectra.symfonielabs.dao;

import com.spectra.symfonie.dataaccess.framework.DataAccessHelper;
import com.spectra.symfonie.dataaccess.framework.ParamMapper;
import com.spectra.symfonie.dataaccess.framework.ResultMapper;
import com.spectra.symfonie.framework.logging.ApplicationRootLogger;
import com.spectra.symfonielabs.domainobject.FacilityDemographics;
import com.spectra.symfonielabs.domainobject.OrderSource;
import com.spectra.symfonielabs.domainobject.Patient;
import com.spectra.symfonielabs.domainobject.RequisitionDetails;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**f
 * This contains the implementation methods that are available in the interface.
 * 
 */
public class OrderSourceDAOImpl implements OrderSourceDAO {

	/**
	 * Logger for this class.
	 */
	private static final Logger LOGGER = ApplicationRootLogger
			.getLogger(OrderSourceDAOImpl.class.getName());
	
	/**
	 * Result mapper to map the result set to search results object.
	 */
	private static final ResultMapper<OrderSource> ORDER_DET_MAPPER = new ResultMapper<OrderSource>() {
		public OrderSource map(final ResultSet resultSet) throws SQLException {	

			final OrderSource orderResultLst = new OrderSource(					
					resultSet.getDate("collection_date"),
					resultSet.getString("requisition_id"),
					resultSet.getInt("test_count"),
					resultSet.getString("draw_frequency"),
					resultSet.getLong("total_count")
					);
			orderResultLst.setEntityIndex(resultSet.getLong("sl_no"));
			final FacilityDemographics fac = new FacilityDemographics(
					resultSet.getString("display_name"),
					resultSet.getString("corporate_acronym"));
			        orderResultLst.setFacility(fac);
			
			return orderResultLst;
		}
	};

	/**
	 * Query to get the order summary details.
	 */
	private static final String GET_ORDER_DET_SQL = new StringBuffer()
	.append("SELECT sl_no, ")
	.append("requisition_id,")  
	.append("collection_date,")
	.append("DRAW_FREQUENCY,")
	.append("corporate_acronym,")
	.append("display_name,")
	.append("TEST_COUNT,facility_pk,")
	.append("TOTAL_COUNT,patient_type_sub_group ") 
	.append(" FROM  ")
	.append("(SELECT  rownum sl_no,")
	.append("requisition_id,")
	.append("collection_date,")
	.append("DRAW_FREQUENCY,")
	.append("corporate_acronym,")
	.append("display_name,")
	.append("TEST_COUNT,facility_pk,")
	.append("TOTAL_COUNT,patient_type_sub_group from")
	.append(" (SELECT  distinct")
	.append(" so.requisition_id,")
	.append("so.collection_date,")
	.append("so.DRAW_FREQUENCY,")
	.append("dc.name AS corporate_acronym,df.facility_pk,")
	.append("df.display_name,")
	.append(" (SELECT COUNT(sod.SOURCE_ORDER_DETAILS_PK) FROM ih_dw.dim_source_order_details SOD2 WHERE so.requisition_id = SOD2.requisition_id) AS TEST_COUNT,")
	.append(" (select COUNT(DISTINCT requisition_id) from ih_dw.dim_source_order WHERE  collection_date BETWEEN TRUNC(to_date(?)) AND TRUNC(sysdate) ")
	.append(" AND  spectra_mrn = ? and order_type not in ('SF','EN','EM') AND requisition_id NOT IN")
	.append(" (SELECT requisition_id FROM ih_dw.dim_lab_order)) AS TOTAL_COUNT,order_type as patient_type_sub_group ")
	.append(" FROM")
	.append(" ih_dw.dim_source_order so")
	.append(" JOIN ih_dw.dim_source_order_details sod")
	.append(" ON  so.SOURCE_ORDER_PK = sod.SOURCE_ORDER_FK")
	.append(" LEFT OUTER JOIN ih_dw.dim_account da")
	.append(" ON so.account_fk = da.account_pk")
	.append(" LEFT OUTER JOIN ih_dw.dim_facility df")
	.append(" ON da.facility_fk = df.facility_pk")
	.append(" LEFT OUTER JOIN ih_dw.dim_client dc")
	.append(" ON df.client_fk = dc.client_pk")
	.append(" WHERE so.collection_date BETWEEN TRUNC(to_date(?)) AND TRUNC(sysdate)")
	.append(" AND so.spectra_mrn         = ? ")
	.append(" AND so.requisition_id NOT IN")
	.append(" (SELECT requisition_id FROM ih_dw.dim_lab_order)").toString(); 
	
	private static final String GET_ORDER_STAFF_DET_SQL = new StringBuffer()
	.append("SELECT sl_no, ")
	.append("requisition_id,")  
	.append("collection_date,")
	.append("DRAW_FREQUENCY,")
	.append("corporate_acronym,")
	.append("display_name,")
	.append("TEST_COUNT,")
	.append("TOTAL_COUNT,facility_pk") 
	.append(" FROM  ")
	.append("(SELECT  rownum sl_no,")
	.append("requisition_id,")
	.append("collection_date,")
	.append("DRAW_FREQUENCY,")
	.append("corporate_acronym,")
	.append("display_name,")
	.append("TEST_COUNT,facility_pk,")
	.append("TOTAL_COUNT from")
	.append(" (SELECT  distinct")
	.append(" so.requisition_id,")
	.append("so.collection_date,")
	.append("so.DRAW_FREQUENCY,")
	.append("dc.name AS corporate_acronym,df.facility_pk,")
	.append("df.display_name,")
	.append(" (SELECT COUNT(sod.SOURCE_ORDER_DETAILS_PK) FROM ih_dw.dim_source_order_details SOD2 WHERE so.requisition_id = SOD2.requisition_id) AS TEST_COUNT,")
	.append(" (select COUNT(DISTINCT requisition_id) from ih_dw.dim_source_order WHERE  collection_date BETWEEN TRUNC(to_date(?)) AND TRUNC(sysdate)")
	.append(" AND  spectra_mrn    = ? and order_type in ('SF') AND requisition_id NOT IN")
	.append(" (SELECT requisition_id FROM ih_dw.dim_lab_order)) AS TOTAL_COUNT")
	.append(" FROM")
	.append(" ih_dw.dim_source_order so")
	.append(" JOIN ih_dw.dim_source_order_details sod")
	.append(" ON  so.SOURCE_ORDER_PK = sod.SOURCE_ORDER_FK")
	.append(" LEFT OUTER JOIN ih_dw.dim_account da")
	.append(" ON so.account_fk = da.account_pk")
	.append(" LEFT OUTER JOIN ih_dw.dim_facility df")
	.append(" ON da.facility_fk = df.facility_pk")
	.append(" LEFT OUTER JOIN ih_dw.dim_client dc")
	.append(" ON df.client_fk = dc.client_pk")
	.append(" WHERE so.collection_date BETWEEN TRUNC(to_date(?)) AND TRUNC(sysdate)")
	.append(" AND so.spectra_mrn         = ?  ")
	.append(" AND so.requisition_id NOT IN")
	.append(" (SELECT requisition_id FROM ih_dw.dim_lab_order)").toString(); 
	
	private static final String GET_ORDER_EQUIP_DET_SQL = new StringBuffer()
	.append("SELECT sl_no, ")
	.append("requisition_id,")  
	.append("collection_date,")
	.append("DRAW_FREQUENCY,")
	.append("corporate_acronym,")
	.append("display_name,")
	.append("TEST_COUNT,")
	.append("TOTAL_COUNT,facility_pk") 
	.append(" FROM  ")
	.append("(SELECT  rownum sl_no,")
	.append("requisition_id,")
	.append("collection_date,")
	.append("DRAW_FREQUENCY,")
	.append("corporate_acronym,")
	.append("display_name,")
	.append("TEST_COUNT,")
	.append("TOTAL_COUNT,facility_pk from")
	.append(" (SELECT  distinct")
	.append(" so.requisition_id,")
	.append("so.collection_date,")
	.append("so.DRAW_FREQUENCY,")
	.append("dc.name AS corporate_acronym,df.facility_pk, ")
	.append("df.display_name,")
	.append(" (SELECT COUNT(sod.SOURCE_ORDER_DETAILS_PK) FROM ih_dw.dim_source_order_details SOD2 WHERE so.requisition_id = SOD2.requisition_id) AS TEST_COUNT,")
	.append(" (select COUNT(DISTINCT requisition_id) from ih_dw.dim_source_order WHERE  collection_date BETWEEN TRUNC(to_date(?)) AND TRUNC(sysdate)")
	.append(" AND  spectra_mrn    = ? and order_type in ('EN','ES') AND requisition_id NOT IN")
	.append(" (SELECT requisition_id FROM ih_dw.dim_lab_order)) AS TOTAL_COUNT ")
	.append(" FROM")
	.append(" ih_dw.dim_source_order so")
	.append(" JOIN ih_dw.dim_source_order_details sod")
	.append(" ON  so.SOURCE_ORDER_PK = sod.SOURCE_ORDER_FK")
	.append(" LEFT OUTER JOIN ih_dw.dim_account da")
	.append(" ON so.account_fk = da.account_pk")
	.append(" LEFT OUTER JOIN ih_dw.dim_facility df")
	.append(" ON da.facility_fk = df.facility_pk")
	.append(" LEFT OUTER JOIN ih_dw.dim_client dc")
	.append(" ON df.client_fk = dc.client_pk")
	.append(" WHERE so.collection_date BETWEEN TRUNC(to_date(?)) AND TRUNC(sysdate)")
	.append(" AND so.spectra_mrn         = ?  ")
	.append(" AND so.requisition_id NOT IN")
	.append(" (SELECT requisition_id FROM ih_dw.dim_lab_order)").toString(); 

	/**
	 * This method is used to get all the order details. for Summary Page.
	 * 
	 * @param facilityId
	 *            - Holds the facility id.
	 * @param spectraMRN
	 *            - Holds the spectra MRN.
	 * @param drawDate
	 *            - Holds the draw date.
	 * @return A list of OrderSource object.
	 */
	public List<OrderSource> getOrderSource(final long facilityId,
			final long spectraMRN, final Date drawDate, final OrderSource orderSource, String patType) {
		List<OrderSource> orderSourceLst = new ArrayList<>();
		StringBuffer orderSrcQuery = new StringBuffer();
		if (patType.equalsIgnoreCase("SF")){
			orderSrcQuery.append(GET_ORDER_STAFF_DET_SQL);
			orderSrcQuery.append(" and so.order_type in ('SF')");
		} else if (patType.equalsIgnoreCase("EN")){
			orderSrcQuery.append(GET_ORDER_EQUIP_DET_SQL);
			orderSrcQuery.append(" and so.order_type in ('EN','ES')");
		} else	{ 
			orderSrcQuery.append(GET_ORDER_DET_SQL);
			orderSrcQuery.append(" and so.order_type not in ('SF','EN','EM')");
		}
		  Format formatter = new SimpleDateFormat("dd-MMM-yy");
		    final String drawDateF = formatter.format(drawDate);
		final String spectramrnVal= Long.toString(spectraMRN);
		ParamMapper paramMapper = new ParamMapper() {
			public void mapParam(final PreparedStatement preparedStatement)
					throws SQLException {
				preparedStatement.setString(1,drawDateF);
				preparedStatement.setString(2, spectramrnVal);
				preparedStatement.setString(3,drawDateF);
				preparedStatement.setString(4, spectramrnVal);
			}
		};
		if (orderSource.getStartIndex() != 0 && orderSource.getEndIndex() != 0) {
			orderSrcQuery.append(" and ROWNUM BETWEEN "
                    + orderSource.getStartIndex() + " AND  "
                    + orderSource.getEndIndex() + " order by collection_date DESC))");
		}
		try {
			LOGGER.log(Level.FINE, "orderSrcQuery: " +  orderSrcQuery.toString());
			System.out.println("orderSrcQuery "+orderSrcQuery.toString());
			orderSourceLst = DataAccessHelper.executeQuery(orderSrcQuery.toString(),paramMapper, ORDER_DET_MAPPER);
//			System.out.println("@@@  ORDER_DET_MAPPER **** SourceLst.isEmpty " +orderSourceLst.isEmpty()+" List size "+orderSourceLst.size());
			} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		LOGGER.log(Level.FINE, "orderSourceLst size: " +  orderSourceLst.size());
		return orderSourceLst;
	}

	private static final ResultMapper<OrderSource> ORDER_REQ_DET_MAPPER = new ResultMapper<OrderSource>() {
		public OrderSource map(final ResultSet resultSet)
				throws SQLException {
			final OrderSource srcDetails = new OrderSource(
					resultSet.getString("ORDER_TEST_NAME"),
					resultSet.getInt("TEST_COUNT"),
					resultSet.getString("ORDER_TYPE"));
			return srcDetails;
		}	
	};
	
	private static final String GET_ORDER_REQ_DET_SQL   = new StringBuffer("select ")	
   .append("sl_no,TEST_COUNT, ORDER_TEST_NAME,ORDER_TYPE FROM ")
  .append("(SELECT rownum sl_no,")
  .append("TEST_COUNT,")
  .append("ORDER_TEST_NAME,ORDER_TYPE ")
  .append("FROM ")
  .append("(SELECT DISTINCT ") 
  .append("(SELECT COUNT(sod.SOURCE_ORDER_DETAILS_PK) ")
  .append("FROM ih_dw.dim_source_order_details SOD2 ")
  .append("WHERE so.requisition_id = SOD2.requisition_id) ") 
  .append("AS TEST_COUNT,")
  .append("ORDER_TEST_NAME,ORDER_TYPE ")   
  .append(" FROM ih_dw.dim_source_order_details sod ")
  .append("JOIN ih_dw.dim_source_order so ")
  .append("ON so.SOURCE_ORDER_PK = sod.SOURCE_ORDER_FK ")
  .append("  WHERE so.REQUISITION_ID = ? ").toString();
	
		/**
		 * This method is used to get the order details based on requisition number.
		 * 
		 * @param requisitionNo
		 *            - Holds the requisition number.
		 * @return A list of Results.
		 */
		public List<OrderSource> getOrderTestDetails(final String requisitionNo) {
			List<OrderSource> lstResults = new ArrayList<OrderSource>();
			ParamMapper paramMapper = new ParamMapper() {
				public void mapParam(final PreparedStatement preparedStatement)
						throws SQLException {
					preparedStatement.setString(1, requisitionNo);						// timc 8/11/2016
//					preparedStatement.setString(2, requisitionNo);						// timc 8/11/2016
				}
			};
			try {
				

				StringBuffer query = new StringBuffer();
				query.append(GET_ORDER_REQ_DET_SQL);
				query.append("AND ROWNUM BETWEEN 1 AND 1000 ORDER BY order_test_name))");
				
				LOGGER.log(Level.FINE, "GET_ORDER_REQ_DET_SQL: " +  GET_ORDER_REQ_DET_SQL);
				System.out.println("GET_ORDER_REQ_DET_SQL "+query.toString());
				lstResults = DataAccessHelper.executeQuery(query.toString(),
						paramMapper, ORDER_REQ_DET_MAPPER);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			LOGGER.log(Level.FINE, "lstResults size: " +  lstResults.size());
			return lstResults;
		}
		public List<OrderSource> getOrderEquipTestDetails(final String requisitionNo) {
			List<OrderSource> lstResults = new ArrayList<OrderSource>();
			ParamMapper paramMapper = new ParamMapper() {
				public void mapParam(final PreparedStatement preparedStatement)
						throws SQLException {
					preparedStatement.setString(1, requisitionNo);						// timc 8/11/2016
//					preparedStatement.setString(2, requisitionNo);						// timc 8/11/2016
				}
			};
			try {
				LOGGER.log(Level.FINE, "GET_ORDER_REQ_DET_SQL: " +  GET_ORDER_REQ_DET_SQL);
				System.out.println("GET_ORDER_REQ_DET_SQL "+GET_ORDER_REQ_DET_SQL);
				lstResults = DataAccessHelper.executeQuery(GET_ORDER_REQ_DET_SQL,
						paramMapper, ORDER_REQ_DET_MAPPER);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			LOGGER.log(Level.FINE, "lstResults size: " +  lstResults.size());
			return lstResults;
		}



		
		/**
		 * Result mapper to map the result set to search results object. 
		 * Page Header.
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
						resultSet.getString("account_name"),
						resultSet.getLong("facility_pk"),
				 		null,
						resultSet.getLong("spectra_mrn"), null, 0l,
						resultSet.getString("modality_code"),
						resultSet.getString("external_patient_id"),
						resultSet.getString("spectra_mrn"),
						resultSet.getString("DEST_LAB"),
						resultSet.getString("corporation_name"), 0l,
						resultSet.getString("account_number"),
						resultSet.getString("hlab_number"), 0l, 0l, null, null, 0l,null,
						resultSet.getString("order_type"),
						resultSet.getString("patient_type"));

				final RequisitionDetails reqDetails = new RequisitionDetails(
						resultSet.getString("requisition_id"), patientResult,
						resultSet.getDate("collection_date"),
 						resultSet.getString("env_serial_number"),
						resultSet.getString("draw_frequency"),
						resultSet.getInt("test_count"), abnormalFlag,
						cancelledTestIndicator,
						resultSet.getString("location_name"),
						resultSet.getString("location_type"),
						resultSet.getString("collected_by"),
 						resultSet.getString("collection_time"),null
 						);
				return reqDetails;
			}
		};

	/**
	 * Query to get the patient requisition details.
	 * Page Header.
	 */	
	private static final String GET_PATIENT_REQ_DETAILS = new StringBuffer("select ")
	.append("sl_no,")
	.append("requisition_id,")
	.append("collection_date,env_serial_number, ")
	.append("patient_name,")
	.append("PATIENT_DOB,")
	.append("gender,")
	.append("external_patient_id,")
	.append("spectra_mrn,")
	.append("modality_code,")
	.append("ORDERING_PHYSICIAN_NAME,")
	.append("destination_lab,")
	.append("DRAW_FREQUENCY,")
	.append("corporation_name,")
	.append("display_name,")
	.append("TEST_COUNT,location_name,location_type,collected_by,collection_time,")
	.append("ORDER_TEST_NAME,")
	.append("DEST_LAB,")
	.append("account_name,")
	.append("account_number,")
	.append("hlab_number,order_type,patient_type,facility_pk ")
	.append("FROM ")
	.append("(SELECT rownum sl_no,")
	.append("requisition_id,")
	.append("collection_date,env_serial_number, ")
	.append("patient_name,")
	.append("PATIENT_DOB,")
	.append("gender,")
	.append("external_patient_id,")
	.append("spectra_mrn,")
	.append("modality_code,")
	.append("ORDERING_PHYSICIAN_NAME,")
	.append("destination_lab,")
	.append("DRAW_FREQUENCY,")
	.append("corporation_name,")
	.append("display_name,")
	.append("TEST_COUNT,location_name,location_type,collected_by,collection_time,")
	.append("ORDER_TEST_NAME,")
	.append("DEST_LAB,")
	.append("account_name,")
	.append("account_number,")
	.append("hlab_number,order_type,patient_type,facility_pk ")
	.append("FROM ")
	.append("(SELECT DISTINCT so.requisition_id,")
	.append("so.collection_date,env_serial_number, ")
	.append("so.patient_name,")
	.append("so.PATIENT_DOB,")
	.append("so.gender,")
	.append("so.external_patient_id,")
	.append("so.spectra_mrn,")
//	.append("so.modality_code,")
	.append("NVL(DM.MODALITY_DESCRIPTION, so.MODALITY_CODE) as MODALITY_CODE,")
	.append("so.ORDERING_PHYSICIAN_NAME,")
	.append("so.destination_lab,")
	.append("so.DRAW_FREQUENCY,")
	.append("dc.name AS corporation_name,")
	.append("df.display_name,")
	.append("(SELECT COUNT(sod.SOURCE_ORDER_DETAILS_PK) ")
	.append("FROM ih_dw.dim_source_order_details SOD2 ")
	.append("WHERE so.requisition_id = SOD2.requisition_id) AS TEST_COUNT,location_name,location_type,collected_by,TO_CHAR(so.collection_date_time,'YYYY-MM-DD HH:MM:SS.FF1') as collection_time,")
	.append("ORDER_TEST_NAME,")
	.append("SOD.DESTINATION_LAB DEST_LAB, ")
	.append("da.name as account_name,")     
	.append("da.account_number as account_number,") 
	.append("da.facility_id as hlab_number,so.order_type,dp.patient_type,df.facility_pk ")
	.append("FROM ih_dw.dim_source_order so ")
	.append("JOIN ih_dw.dim_source_order_details sod ")
	.append("ON so.SOURCE_ORDER_PK = sod.SOURCE_ORDER_FK ")
	.append("JOIN IH_DW.DIM_MODALITY DM ")
    .append("ON DM.MODALITY_CODE = so.MODALITY_CODE ")
	.append("LEFT OUTER JOIN ih_dw.dim_account da ")
	.append("ON so.account_fk = da.account_pk ")
	.append("LEFT OUTER JOIN ih_dw.dim_facility df ")
	.append("ON da.facility_fk = df.facility_pk ")
	.append("LEFT OUTER JOIN ih_dw.dim_client dc ")
	.append("ON df.client_fk = dc.client_pk ")
	.append("LEFT OUTER JOIN ih_dw.SPECTRA_MRN_MASTER sm on so.spectra_mrn_fk = sm.spectra_mrn_pk ")
    .append("LEFT OUTER JOIN ih_dw.DIM_PATIENT dp on sm.spectra_mrn_pk = dp.spectra_mrn_fk ")
	.append("WHERE so.REQUISITION_ID  = ? ")
	.append("AND so.requisition_id NOT IN ")
	.append("(SELECT requisition_id FROM ih_dw.dim_lab_order) ").toString();

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
			LOGGER.log(Level.FINE, "GET_PATIENT_REQ_DETAILS: " +  GET_PATIENT_REQ_DETAILS);
			
			StringBuffer query = new StringBuffer();
			query.append(GET_PATIENT_REQ_DETAILS);

			query.append(" AND ROWNUM BETWEEN 1 AND 1000 ORDER BY order_test_name))");
//			System.out.println("GET_PATIENT_REQ_DETAILS "+query.toString());
			patientResultLst = DataAccessHelper.executeQuery(query.toString(), paramMapper,PATIENT_REQ_DETAILS_MAPPER);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		if (null != patientResultLst && !patientResultLst.isEmpty()) {
			patientResult = patientResultLst.get(0);
		}
		LOGGER.log(Level.FINE, "patientResultLst size: " +  patientResultLst.size());
		return patientResult;
	}	
}
