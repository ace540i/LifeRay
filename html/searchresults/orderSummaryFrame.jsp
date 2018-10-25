<!DOCTYPE html>
<%@include file="/html/init.jsp"%>
<%@page import="com.spectra.symfonielabs.domainobject.Patient"%>
<%@page import="com.spectra.symfonie.common.constants.CommonConstants"%>
<%@page import="com.spectra.symfonie.common.util.StringUtil"%>
<%@page	import="com.spectra.symfonie.framework.util.SimpleDateFormatUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Patient Info</title>
<script type="text/javascript">
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/bootstrap.min.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/symfonie-labs.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/theme_styles.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/css/ext5ExtendedStyles.css?'+dateValue+'">');
    document.write('<script type="text/javascript" src="'+contextPath+'/js/orderSummary.js?'+dateValue+'"><\/script>');
</script>
<script type="text/javascript">
var url = parent.orderSummaryURL;
var urlPath = '<%=request.getContextPath()%>';
<%Patient patientObj = new Patient();
SimpleDateFormat simpleDateFmt =  SimpleDateFormatUtil.getSimpleDateFormatLocale("MM/dd/yyyy");
String patientName = CommonConstants.EMPTY_STRING;
String gender = CommonConstants.EMPTY_STRING;
String DOB = CommonConstants.EMPTY_STRING;
String modality = CommonConstants.EMPTY_STRING;
String physician = CommonConstants.EMPTY_STRING;
String facilityName = CommonConstants.EMPTY_STRING;
String spectraMRN = CommonConstants.EMPTY_STRING;
String externalMRN = CommonConstants.EMPTY_STRING;
String patientHLABId = CommonConstants.EMPTY_STRING;
String clinicName = CommonConstants.EMPTY_STRING;
String HLABId = CommonConstants.EMPTY_STRING;
String labName = CommonConstants.EMPTY_STRING;
String facilityId = CommonConstants.EMPTY_STRING;

if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("orderSummaryInfo")))){
	patientObj =(Patient) request.getSession().getAttribute("orderSummaryInfo");
}
facilityName = StringUtil.valueOf(patientObj.getFacilityName());
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(patientObj.getFullName()))) {
	patientName = patientObj.getFullName();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(patientObj.getGender()))) {
	gender = patientObj.getGender();
}
if(null != patientObj.getDateOfBirth()) {
	DOB =  simpleDateFmt.format(patientObj.getDateOfBirth());
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(patientObj.getModality()))) {
	modality = patientObj.getModality();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(patientObj.getPhysicianName()))) {
	physician = patientObj.getPhysicianName();
}
if (0l != patientObj.getSpectraMRN()) {
	spectraMRN = StringUtil.valueOf(patientObj.getSpectraMRN());
}
if (0l != patientObj.getFacilityId()) {
	facilityId = StringUtil.valueOf(patientObj.getFacilityId());
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(patientObj.getExternalMRN()))) {
	externalMRN = patientObj.getExternalMRN();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(patientObj.getPatientHLABId()))) {
	patientHLABId = patientObj.getPatientHLABId();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(patientObj.getClinicName()))) {
	clinicName = patientObj.getClinicName();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(patientObj.getFacilityNum()))) {
	//Used facility Id as HLAB number.
	HLABId = patientObj.getFacilityNum();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(patientObj.getLabName()))) {
	labName = patientObj.getLabName();
}%>
var facilityIdValue = <%=facilityId%>;
var spectraMRNValue = <%=spectraMRN%>;
var primaryFacNumber = "<%=HLABId%>";
var acute = "<%=patientObj.getPatientType() %>";
</script>
</head>
<style type="text/css">
/* Navigation */
/*  .navigationTabPanel .x-tab-bar-default-top { */
/*     background : transparent; */
/*     background-image: none !important; */
/* } */
/* .navigationTabPanel .x-tab-bar-default-top { */
/*     border-width: 0; */
/*     /* fix for border top */ */
/* } */
/* .navigationTabPanel .x-tab-bar-body a { */
/*     background-image: none !important; */
/*     box-shadow: none; */
/*     border: 0; */
/*     border-radius : 0; */
/*     padding: 10px 25px 0; */
/* } */
/* .navigationTabPanel .x-tab-bar-body .x-table-plain td { */
/*     background : transparent !important; */
/*     background-image: none !important; */
/* } */
/* .navigationTabPanel .x-tab-bar-body .x-tab-default-top { */
/*     background : #f0f0f0 !important; */
/* } */

 
 
/*      border-width: 1px 1px 0 1px !important;   */
/*       border-style: solid;   */
 
 
  

/* .navigationTabPanel .x-tab-bar-body-default-top { */
/*     padding-bottom :0; */
/* } */
/* .navigationTabPanel .x-tab-default-top { */
/*     border-bottom : 0; */
/* } */
/* /* change the height of tabs */ */
/*  .navigationTabPanel .x-tab-bar-strip { */
/*     display : none !important; */
/*     /* no border between */ */
/* } */
/* .navigationTabPanel .x-tab-bar .x-tab-bar-body { */
/*     height: 37px !important; */
/*     /* default is 23 */ */
/*     border: 0 !important; */
/* } */
/* .navigationTabPanel .x-tab-bar .x-tab-bar-body .x-box-inner { */
/*     height: 35px !important; */
/*     /* default is 21 */ */
/* } */
/* .navigationTabPanel .x-tab-bar .x-tab-bar-body .x-box-inner .x-tab { */
/*     height: 35px !important; */
/*     box-shadow: none; */
/*     /* default is 21 */ */
/* } */
/* .navigationTabPanel .x-tab-bar .x-tab-bar-body .x-box-inner .x-tab button { */
/*     height: 37px !important; */
/*     line-height: 37px !important; */
/* } */
/* .navigationTabPanel .x-tab-center { */
/*     margin-top: -10px; */
/* } */
/*   .navigationTabPanelBody {   */
/*       background : #f7fafc;   */
/*       border-color: #ffffff;   */
/*       border-width: 0 1px 1px 1px;   */
/*       border-style: solid;   */
/*       -webkit-border-radius: 5px;   */
/*       -webkit-border-top-left-radius: 0;   */
/*        -moz-border-radius: 5px;   */
/*       -moz-border-radius-topleft: 0;   */
/*       border-radius: 5px;   */
/*       border-top-left-radius: 0;   */
/*       padding : 20px 10px 10px;   */
    
/*   }   */
/* .navigationTabPanelBody .x-panel-body-default { */
/*     background : transparent; */
/* } */


.MainPanel .x-tab-bar-strip {
    top: 44px !important; /* Default value is 20, we add 20 = 40 */
}
 .MainPanel .x-tab-bar-default-top {
    background : #ffffff;
    background-image: none !important;
    border: none;
}
.MainPanel .x-tab-bar-strip-default {
  border:none;
  color: #ffffff;
  
}

.MainPanel .x-tab-bar .x-tab-bar-body {
    height: 49px !important; /* Default value is 23, we add 20 = 43 */
    border: 0 !important; /* Overides the border that appears on resizing the grid */
}

.MainPanel .x-tab-bar .x-tab-bar-body .x-box-inner {
    height: 52px !important; /* Default value is 21, we add 20 = 41 */    
}

.MainPanel .x-tab-bar .x-tab-bar-body .x-box-inner  .x-tab {
    height: 52px !important; /* Default value is 21, we add 20 = 41 */
    border-top: solid;
    background: none;
}
.MainPanel .x-tab-inner-default {
    font-size: 14px;
    line-height: 15px;
}
.MainPanel .x-tab-default.x-tab-default-active   {
    background: purple;
    border: none;
    box-shadow: none;
}

/* .MainPanel  .x-tab .x-unselectable .x-box-item .x-tab-default .x-top .x-tab-top .x-tab-default-top .x-tab-active { */
/*       background : #f7fafc !important; */
/*     border-top-color: red !important; */
/*     border-width: 1px 1px 0 1px !important; */
/*     border-style: solid; */
/*    }  */
/* .MainPanel .x-tab-bar .x-tab-bar-body .x-box-inner .x-tab button { */
/*     height: 33px !important; /* Default value 13, we add 20 = 33 */ */
/*     line-height: 33px !important; /* Default value 13, we add 20 = 33 */    */

/* } */
/* .MainPanel .x-tab-bar .x-tab-bar-body .x-box-inner .x-tab { */
/*     color: rgb(175,0,0) !important; */
 
/*         border-style : solid; */
/*     border-top : #8BC34A; */
/* } */

</style>

<body class="theme-whbl  pace-done" onload="showOrderSummaryGrid();">

	<div id="theme-wrapper">
		<div id="content-wrapper">
			<div class="col-lg-12">
				<div class="row">
					<div class="col-md-9 col-lg-9 med"
						style="padding: 0px 6px 10px 6px !important; margin: 0px 0px 10px 0px; background-color: #fff; width: 100%;">
						<div class="conversation-content">
							<div class=" ">
								<div class="row"
									style="border-bottom: 1px solid #a1a1a1; margin: 0px 2px 0px 2px;">
									<div class="wid50 pull-left">
										<div class=" pull-left">
											<div>	
											<h3 class="h3-color" style="margin-top: 7px;">																		
												<% if (patientObj.getPatientType().equalsIgnoreCase("AC")) { %>												
													<img alt="acute" src="<%=request.getContextPath() %>/images/acute.png" style="height: 31px" title="Acute Patient">
												<%} %>
												<%=patientName%></h3>	
												
											</div>
										</div>
									</div>
									<div class="wid50 pull-left">
										<div class="pull-right" style="margin-bottom: 10px;">
											<a href="https://spectra.service-now.com" target="_blank"><span>
													<img src="<%=request.getContextPath()%>/images/fowd.jpg"
													width="100%" height="100%" alt="" />
											</span></a>
										</div>
									</div>
								</div>
								<div class="row martp25">
									<div class="border col-xs-2 col-sm-2 col-md-2">
										<div class="pull-left">
											<img src="<%=request.getContextPath()%>/images/gender.jpg"
												width="auto" height="auto" alt="" />
										</div>
										<div class="pull-left gender-txt">
											Gender: <b><%=gender%></b>
										</div>
									</div>
									<div class="border col-xs-3 col-sm-3 col-md-3">
										<div class="pull-left">
											<img src="<%=request.getContextPath()%>/images/dob.jpg"
												width="32" height="32" alt="" />
										</div>
										<div class="pull-left gender-txt">
											DOB: <b><%=DOB%></b>
										</div>
									</div>
									<div class="border col-xs-3 col-sm-3 col-md-3">
										<div class="pull-left">
											<img src="<%=request.getContextPath()%>/images/hemo.jpg"
												width="32" height="32" alt="" />
										</div>
										<div class="pull-left gender-txt">
											Modality: <b><%=modality%></b>
										</div>
									</div>
									<div class="border col-xs-4 col-sm-4 col-md-4">
										<div class="pull-left">
											<img
												src="<%=request.getContextPath()%>/images/physician.jpg"
												width="32" height="32" alt="" />
										</div>
										<div class="pull-left gender-txt">
											Physician: <b><%=physician%></b>
										</div>
									</div>
								</div>
								<div class="bg-txt">
									<div class="wid40 pull-left">
										<div class="patient-heading">
											<div class="pull-left">
												<img
													src="<%=request.getContextPath()%>/images/patient-ids.jpg"
													width="24" height="24" alt="" />
											</div>
											<div class="pull-left ids-txt">PATIENT IDS</div>
											<div class="clearfix"></div>
										</div>
										<div class="mar-box">
											<div class="pull-left wid40 ">
												<p>Lab</p>
												<p>Spectra MRN</p>
											</div>
											<div class="pull-left wid50"></div>
											<label><strong>&nbsp;<%=labName%></strong></label> </br> <label><strong>&nbsp;<%=spectraMRN%></strong></label>
											</br>
										</div>
									</div>
									<div class="wid60 pull-left">
										<div class="patient-heading">
											<div class="pull-left">
												<img
													src="<%=request.getContextPath()%>/images/clinical-information.jpg"
													width="24" height="24" alt="" />
											</div>
											<div class="pull-left ids-txt">CLINICAL INFORMATION</div>
											<div class="clearfix"></div>
										</div>
										<div class="mar-box">
											<div class="pull-left wid35">
												<p>Corporation</p>
												<p>Facility</p>
												<p>HLAB#</p>
											</div>
											<div class="pull-left wid65">
												<label><strong>&nbsp;<%=clinicName%></strong></label> <br />
												<label><a onclick="goToFacDemographicsPage();"><strong>&nbsp;<%=facilityName%></strong></a></label>
												<br /> <label><strong>&nbsp;<%=HLABId%></strong></label>
											</div>
										</div>
									</div>
								</div>
								<div class=" pull-left"  style="display:inline;">
								<h3 class="h3-color" style="margin-top: 7px;display:inline;">Order Summary</h3>
								</div>
								<div id="tabPanel" style="background-color: transparent !important;">
								<div id="summaryFilter" class="martp25">
									<div style="margin:  -42px 0px 0px -21px;">
									
<!-- 										<img -->
<%-- 											src="<%=request.getContextPath()%>/images/order-summary.jpg" --%>
<!-- 											width="149" height="47" alt="" /> -->
<!-- 									</div> -->
									<div style="margin-top: 8px;">
										<div class="pull-right marbt10 ">
											<label class="filter">Filter</label> <select class="selet"
												id="filterSelect" onchange="changeFilter();">
												<option value="1">Last 30 days</option>
												<option value="2">Last 60 days</option>
												<option value="3" selected="selected">Last 90 days</option>
												<option value="4">Last 120 days</option>
												<option value="6">Last 6 months</option>
												<option value="12">Last 12 months</option>
												<option value="24">Last 24 months</option>
											</select>
										</div>
										<script type="text/javascript">
											if (<%=request.getSession().getAttribute("dateRange")%>	!= null) {
													document.getElementById("filterSelect").value =
													<%=request.getSession().getAttribute("dateRange")%>;								
											}
										</script>
										<div class="clearfix"></div>
										
										<table class="table" id="orderSummaryTable" style="margin-bottom:0;">
											<thead>
											</thead>
											<tbody>
												<tr class="font-head1 bor-none">
													<td style="border :none; padding:0;" >
														<div id="orderSummaryGridDiv"></div>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
							      </div>
							      </div>
								<div  id="sourceFilter" class="martp25">
									<div style="margin: -42px 0px 0px -21px;">
									<div style="margin-top: 8px;">
										<div class="pull-right marbt10 ">
											<label class="filter">Filter</label> <select class="selet"
												id="filterSelectSource" onchange="changeFilterSource();">
												<option value="1">Last 30 days</option>
												<option value="2">Last 60 days</option>
												<option value="3" selected="selected">Last 90 days</option>
<!-- 												<option value="4">Last 120 days</option> -->
<!-- 												<option value="6">Last 6 months</option> -->
<!-- 												<option value="12">Last 12 months</option> -->
<!-- 												<option value="24">Last 24 months</option> -->
											</select>
										</div>
										<script type="text/javascript">
											if (<%=request.getSession().getAttribute("dateRange")%>	!= null) {
													document.getElementById("filterSelect").value =
													<%=request.getSession().getAttribute("dateRange")%>;								
											}
										</script>
										<div class="clearfix"></div>
										
										<table class="table" id="orderSummaryTable" style="margin-bottom:0;">
											<thead>
											</thead>
											<tbody>
												<tr class="font-head1 bor-none">
													<td style="border :none; padding:0;">
														<div id="orderSourceGridDiv"></div>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
	
							
								</div>
								</div>
							</div>
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
	<!-- 										<script>document.getElementById("filterSelect").value = '3';</script> -->
</body>
</html>