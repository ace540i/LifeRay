<!DOCTYPE html>
<%@page import="com.spectra.symfonie.framework.util.SimpleDateFormatUtil"%>
<%@page import="com.spectra.symfonielabs.domainobject.Patient"%>
<%@page import="com.spectra.symfonie.common.constants.CommonConstants"%>
<%@page import="com.spectra.symfonie.common.util.StringUtil"%>
<%@page import="com.spectra.symfonielabs.constants.OrderConstants"%>
<%@page import="com.spectra.symfonielabs.domainobject.RequisitionDetails"%>
<%@page import="com.spectra.symfonielabs.domainobject.EnvRequisitionDetails"%>
 <%@page import="java.util.Date"%>
 <%@page import="com.spectra.symfonie.common.util.DateUtil"%>
<%@include file="/html/init.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search</title>

<style type="text/css">
         .x-window-header,  
        .x-window-tc, .x-window-tr, .x-window-tl, 
         .x-window-ml, .x-window-mr,  
         .x-window-bc, .x-window-br, .x-window-bl
         { 
             background: #2574ab; 
         } 
</style>  

<script type="text/javascript">
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/bootstrap.min.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/symfonie-labs.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/theme_styles.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/css/ext5ExtendedStyles.css?'+dateValue+'">');
    document.write('<script type="text/javascript" src="'+contextPath+'/js/requisitionSrcDetails.js?'+dateValue+'"><\/script>');
</script>
<script type="text/javascript">
	var url = parent.requisitionUrl;
	var requisitionNo = null;
	<%
	request.getSession().removeAttribute("hlabNum");
	String searchType = CommonConstants.EMPTY_STRING;
	String requisitionNo = CommonConstants.EMPTY_STRING;
	if(null != request.getSession().getAttribute(OrderConstants.REQUISITION_NO)){
		requisitionNo = (String)(request.getSession().getAttribute(OrderConstants.REQUISITION_NO));
	}
	%>
	requisitionNo = '<%=requisitionNo%>';
	//Function for page load actions.
	function onPageLoad() {
		Ext.onReady(function () {  
			showPagingBar();
		});	
	//	document.getElementById('checkImg').style.display = 'inline';
		if('TRUE'==abnormalFlag.toUpperCase()) {
		//	document.getElementById('checkImg').style.display = 'none';
			document.getElementById('closeImg').style.display = 'inline';
		} else {
		//	document.getElementById('checkImg').style.display = 'inline';
			document.getElementById('closeImg').style.display = 'none';
		}
		if ('TRUE'== cancelledTestIndicator.toUpperCase()) {
			document.getElementById('closeIndImg').style.display = 'inline';
		}
	}
<%
	RequisitionDetails reqDet = new RequisitionDetails();
	Patient patient = new Patient(); 
	EnvRequisitionDetails envRequisitionDetails = new EnvRequisitionDetails();
	if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("patientDet")))){
		reqDet =(RequisitionDetails) request.getSession().getAttribute("patientDet");
		if(null !=  reqDet.getPatient()){
			patient = reqDet.getPatient();
		}
	}
	String drawdate = CommonConstants.EMPTY_STRING;
	String DOB = CommonConstants.EMPTY_STRING;
	String spectraMRN = CommonConstants.EMPTY_STRING;
	String noOfTest = CommonConstants.EMPTY_STRING;
	SimpleDateFormat simpleDateFmt =  SimpleDateFormatUtil.getSimpleDateFormatLocale("MM/dd/yyyy");
	String reqNum = StringUtil.valueOf(reqDet.getRequisition());
	if(null != reqDet.getDrawDate()) {
		drawdate =  simpleDateFmt.format(reqDet.getDrawDate());
	}
	String status = StringUtil.valueOf(reqDet.getStatus());
	String patientName = StringUtil.valueOf(patient.getFullName());
	if(null != patient.getDateOfBirth()) {
		DOB =  simpleDateFmt.format(patient.getDateOfBirth());
	}
	String gender = StringUtil.valueOf(patient.getGender());
	String modality = StringUtil.valueOf(patient.getModality());
	String phyName = StringUtil.valueOf(patient.getPhysicianName());
	String corpName = StringUtil.valueOf(patient.getCorpName());
  	String clinic = StringUtil.valueOf(patient.getFacilityName());
	String accountId =  StringUtil.valueOf(patient.getAccountId());
	String lab = StringUtil.valueOf(patient.getLabName());
	String patientHLABNum = StringUtil.valueOf(patient.getPatientHLABId());
	String primaryFacNumber = StringUtil.valueOf(patient.getFacilityNum());
	String HLABNum = StringUtil.valueOf(patient.getHLABNumber());
  	String patientType = StringUtil.valueOf(patient.getPatientType());
  	String orderType = StringUtil.valueOf(patient.getOrderType());
 
// 	String serialNum = StringUtil.valueOf(envRequisitionDetails.getSerialNum());
    String serialNum = StringUtil.valueOf(reqDet.getEnvSerialNumber());
	String locationType = StringUtil.valueOf(reqDet.getLocationType());
	String location = StringUtil.valueOf(reqDet.getLocation());
	String collectedBy = StringUtil.valueOf(reqDet.getCollectedBy());
	String collectionTime = StringUtil.valueOf(reqDet.getCollectionTime());
  	
	if(0 != patient.getSpectraMRN()){
		spectraMRN = StringUtil.valueOf(patient.getSpectraMRN());
	}
	String frequency = StringUtil.valueOf(reqDet.getFrequency());
	if(0 != reqDet.getNoOfTest()) {
		noOfTest = StringUtil.valueOf(reqDet.getNoOfTest());
	}
	String externalMRN = StringUtil.valueOf(patient.getExternalMRN());
	String orderSys = StringUtil.valueOf(reqDet.getOrderSystem());
	String abnormalFlag = StringUtil.valueOf(reqDet.getAbnormalFlag());
	String cancelledTestIndicator = StringUtil.valueOf(reqDet.isCancelledTestIndicator());  
	request.getSession().setAttribute("hlabNum", HLABNum);
	//Added to identify whether the page is rendered from staff order summary page.
	String patientTypeSubGrp = reqDet.getPatientTypeSubGrp();
	%>
	var abnormalFlag = '<%=abnormalFlag%>';
	var cancelledTestIndicator = '<%=cancelledTestIndicator%>'
	var numberOfTests = '<%=noOfTest%>';
	var frequency = '<%=frequency%>';
	var patientName = '<%=patientName%>';
	var facId = '<%=patient.getFacilityId()%>';
	var spectraMRNVal = '<%=spectraMRN%>';
	var facilityIdVal = '<%=patient.getFacilityId()%>';
	var patientHLABNum = '<%=HLABNum%>';
    var patientTypeSubGrp = parent.patientTypeSubGrp;
	var primaryFacNumber = '<%=primaryFacNumber%>';
<%-- 	var patientType = '<%=patientType%>'; --%>
</script>
</head>
<body class="theme-whbl  pace-done" onload="onPageLoad();">
<div id="theme-wrapper">
      <div id="content-wrapper">
          <div class="col-lg-12">
            <div class="row">
              <div class="col-md-9 col-lg-9 med" style="padding:0px 6px 10px 6px !important; margin:0px 0px 10px 0px;background-color:#fff;width: 100%;">
                <div class="conversation-content">
                  <div class=" ">
                    <div class="row" style="border-bottom:1px solid #a1a1a1; margin: 0px 2px 0px 2px;">
                      <div class="wid20 pull-left" style="width: 25%;">
                        <div class=" pull-left">
                          <div>
                            <h3 class="h3-color" style="margin-top: 7px;">                         
                            <span class="font-grey">#</span>&nbsp;<%=reqNum %>
                            <img id="checkImg" style="display: none" src="<%=request.getContextPath()%>/images/Normal.png"  title="All Tubes Received"width="19" height="19" alt=""  class="space"/>
                            <img id="closeImg" style="display: none" src="<%=request.getContextPath()%>/images/Alert.png" title='Alert and/or Exception' width="19" height="19" alt="" class="space"/>
                            <img id="closeIndImg" style="display: none" src="<%=request.getContextPath()%>/images/cancelled.png" title='Cancelled Test(s)' width="19" height="19" alt="" class="space"/>
<%--                             <%  if (reqDet.getPatientType().equalsIgnoreCase("AC")) { %> --%>
<%--                                  	<img src="<%=request.getContextPath()%>/images/acute.png" title="Acute Requisition" width='17' height='17'  alt="acute" class="space"/>	 --%>
<%--                             <% } %> --%>
                            </h3>
                          </div>
                        </div>
                      </div>

<!-- // timc  -->
                      <div class="wid35 pull-left" style="width: 33%;">
                      	<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 font-gen font-grey1"><font style="font-size: 16px;">Draw Date :</font><b style="font-size: 16px;"> &nbsp;<%=drawdate %></b></div>
                      </div>
                      
                      
                      <div class="wid25 pull-left" style="width: 21%;">
                      	<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 font-gen font-grey1"><font style="font-size: 16px;">Status:</font><b style="font-size: 16px; color:red;">&nbsp;Not Received</b></div>
                      </div>
                      
                      
                      
                      
<!--                       <div class="wid25 pull-left col-lg-8 col-md-8 col-sm-8 col-xs-8" style="width: 20%;"> -->
<!--                       </div> -->
<!-- // timc  -->
                                                                      
                      <div class="wid20 pull-left">
                        <div class="pull-right" style="margin-bottom: 10px;"> <a href="https://spectra.service-now.com" target="_blank"><span> <img src="<%=request.getContextPath()%>/images/fowd.jpg" width="100%" height="100%" alt="" /> </span></a>  </div>
                      </div>
                    </div>
                    
                    <div class="bg-txt1" style="height: auto;">
                          <div class="wid40 pull-left">
                            <div class="mar-box">
								<div class="pull-left wid40 ">
									<%if ("ES".equalsIgnoreCase(orderType) || "NE".equalsIgnoreCase(orderType)) {%>
										<p>Patient Name
                                    <% if (patientType.equalsIgnoreCase("AC")) { %>
                                        	<img src="<%=request.getContextPath()%>/images/acute.png" title="Acute Patient" width='17' height='17' alt="acute" class="space"/>	
                                        	</p>
                                        <% }%>
                                         <% } else  
                                       if ("EN".equalsIgnoreCase(orderType) || "EM".equalsIgnoreCase(orderType)) {%>
										<p>Sample ID</p>
									  <%}
									   if ("SF".equalsIgnoreCase(orderType)) {%>
										<p>Staff Name
										<img src="<%=request.getContextPath()%>/images/staff.png" title="Staff" alt="staff" class="space"/>
										</p>
									  <%} %>
								</div>
								<div class="pull-left wid50">
									<label><a onclick="goToOrderSummaryPage('<%=orderType %>');"><strong style="font-size: 14px;">&nbsp;<%=patientName %></strong></a></label>
								</div>
								
								<div class="clearfix"></div>
								<div class="pull-left wid40 ">
								<%if ("ES".equalsIgnoreCase(orderType) || "NE".equalsIgnoreCase(orderType) || "SF".equalsIgnoreCase(orderType)) {%>		
									<p>DOB</p>
								</div>
								<div class="pull-left wid50">
									<label><strong>&nbsp;<%=DOB %></strong></label>
								</div>
								<div class="clearfix"></div>
								<div class="pull-left wid40 ">
									<p>Gender</p>
								</div>
								<div class="pull-left wid50">
									<label><strong>&nbsp;<%=gender %></strong></label>
								</div>
								
								<div class="clearfix"></div>
								<div class="pull-left wid40 ">
									<%if ("ES".equalsIgnoreCase(orderType) || "NE".equalsIgnoreCase(orderType)) {%>
									<p>Modality</p>
									<%} %>
								</div>
								<div class="pull-left wid50">
									<%if ("ES".equalsIgnoreCase(orderType) || "NE".equalsIgnoreCase(orderType)) {%>
									  <label><strong>&nbsp;<%=modality %></strong></label>
									  <br/>
									  <%} %>
								</div>
								
								<div class="clearfix"></div>
								<div class="pull-left wid40 ">
									<p>Patient ID</p>
								</div>
								<div class="pull-left wid50">
									<label><strong>&nbsp;<%=patientHLABNum %></strong></label>
								</div>
								
								<div class="clearfix"></div>
								<div class="pull-left wid40 ">
									<p>Spectra MRN</p>
								</div>
								<div class="pull-left wid50">
									<label><strong>&nbsp;<%=spectraMRN %></strong></label>
								</div>
							    <%} else if ("EN".equalsIgnoreCase(orderType) || "EM".equalsIgnoreCase(orderType)) { %> 
								<p>Serial #</p>
								</div>
								<div class="pull-left wid50">
									<label><strong>&nbsp;<%=serialNum %></strong></label>
								</div>
								<div class="clearfix"></div>
							
								<div class="pull-left wid40 ">
								  <p>Location Type</p>
								</div>
								<div class="pull-left wid50">
								  <label><strong>&nbsp;<%=locationType %></strong></label>
								</div>
								<div class="clearfix"></div>
								
								<div class="pull-left wid40 ">
								  <p>Location</p>
								</div>
								<div class="pull-left wid50">
								  <label><strong>&nbsp;<%=location %></strong></label>
								</div>
								<div class="clearfix"></div>
							
								<div class="pull-left wid40 ">
								  <p>Collected By</p>
								</div>
								<div class="pull-left wid50">
								  <label><strong>&nbsp;<%=collectedBy %></strong></label>
								</div>
								<div class="clearfix"></div>
							
								<div class="pull-left wid40 ">
								  <p>Collection Time</p>
								</div>
								<div class="pull-left wid50">
								  <label><strong>&nbsp;<%=collectionTime %></strong></label>
								</div>
								<div class="clearfix"></div>
							<%} %>
								<div class="clearfix"></div>
							  
                            </div>
                          </div>
                          								
							<div class="wid60 pull-left">
                            <div class="mar-box">
								<div class="pull-left wid35 ">
									<p>Facility</p>
								</div>
								<div class="pull-left wid65">
									<label><a onclick="goToFacDemographicsPage();"><strong style="font-size: 14px;">&nbsp;<%=clinic %></strong></a></label>
								</div>
							
								<div class="clearfix"></div>
								<div class="pull-left wid35 ">
									<p>Corporation</p>
								</div>
								<div class="pull-left wid65">
									<label><strong>&nbsp;<%=corpName %></strong></label>
								</div>
								
								<div class="clearfix"></div>
								<div class="pull-left wid35 ">
									<p>Acct#</p>
								</div>
								<div class="pull-left wid65">
									<label><strong>&nbsp;<%=accountId %></strong></label>
								</div>
								
								<div class="clearfix"></div>
								<div class="pull-left wid35 ">
									<p>LAB</p>
								</div>
								<div class="pull-left wid65">
									<label><strong>&nbsp;<%=lab %></strong></label>
								</div>
								
								<div class="clearfix"></div>
								<div class="pull-left wid35 ">
									<p>HLAB#</p>
								</div>
								<div class="pull-left wid65">
									<label><strong>&nbsp;<%=HLABNum %></strong></label>
								</div>
								
								<div class="clearfix"></div>
								<%if ("ES".equalsIgnoreCase(orderType) || "NE".equalsIgnoreCase(orderType)) {%>
									<div class="pull-left wid35 ">
										<p>Physician</p>
									</div>							
									<div class="pull-left wid65">
										<label><strong>&nbsp;<%=phyName %></strong></label>
									</div>
							   <%} %>
								<div class="clearfix"></div>
							
                            </div>
                          </div>
                          <div class="clearfix"></div>
					</div>
                    <div class="martp25">
                      <div class="clearfix"></div>
                      <div id="testSummaryDiv"></div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="martp25">
                      <div class="clearfix"></div>
                      <div id="orderDetailsDiv" ></div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="martp25">                    	 
                      <div class="clearfix"></div>
                      <div id="microOrderDetailsDiv" ></div>
                    </div>
                    
                  </div>
                </div>
              </div>
            </div>
		</div>
	</div>
</div>
<script src="<%=request.getContextPath()%>/js/jquery.js"></script> 
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script> 
<script src="<%=request.getContextPath()%>/js/scripts.js"></script> 
<script src="<%=request.getContextPath()%>/js/pace.min.js"></script>
</body>
</html>