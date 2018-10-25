<!DOCTYPE html>
<!-- 12/22/2015 - md - US1087   
    			  1) New jsp Frame for Staff Orders.
-->
<%@include file="/html/init.jsp" %>
<%@page import="com.spectra.symfonielabs.domainobject.Staff"%>
<%@page import="com.spectra.symfonie.common.constants.CommonConstants"%>
<%@page import="com.spectra.symfonie.common.util.StringUtil"%>
<%@page import="com.spectra.symfonie.framework.util.SimpleDateFormatUtil"%>
<html>
<head>
<!-- Start of JSP search/orderStaffFrame.jsp -->
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
    document.write('<script type="text/javascript" src="'+contextPath+'/js/orderSummaryStaff.js?'+dateValue+'"><\/script>');
</script>
<script type="text/javascript">
var url = parent.orderSummaryURL;
//---------------------------------------------------//
var facilityIdValue = parent.facilityId;
var spectraMRNValue = parent.spectraMRN;
//---------------------------------------------------//
var urlPath = '<%= request.getContextPath()%>';
<%
Staff staffObj = new Staff();
SimpleDateFormat simpleDateFmt =  SimpleDateFormatUtil.getSimpleDateFormatLocale("MM/dd/yyyy");
String patientName = CommonConstants.EMPTY_STRING;
String gender = CommonConstants.EMPTY_STRING;
String DOB = CommonConstants.EMPTY_STRING;
// String modality = CommonConstants.EMPTY_STRING;
// String physician = CommonConstants.EMPTY_STRING;
String facilityName = CommonConstants.EMPTY_STRING;
String spectraMRN = CommonConstants.EMPTY_STRING;
String externalMRN = CommonConstants.EMPTY_STRING;
String patientHLABId = CommonConstants.EMPTY_STRING;
String clinicName = CommonConstants.EMPTY_STRING;
String HLABId = CommonConstants.EMPTY_STRING;
String labName = CommonConstants.EMPTY_STRING;
//---------------------------------------------------//
String facilityId = CommonConstants.EMPTY_STRING;
//---------------------------------------------------//
if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("orderStaffSummaryInfo")))){
	staffObj =(Staff) request.getSession().getAttribute("orderStaffSummaryInfo");
}
facilityName = StringUtil.valueOf(staffObj.getFacilityName());
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(staffObj.getFullName()))) {
	patientName = staffObj.getFullName();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(staffObj.getGender()))) {
	gender = staffObj.getGender();
}
if(null != staffObj.getDateOfBirth()) {
	DOB =  simpleDateFmt.format(staffObj.getDateOfBirth());
}
// if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(staffObj.getModality()))) {
// 	modality = staffObj.getModality();
// }
// if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(staffObj.getPhysicianName()))) {
// 	physician = staffObj.getPhysicianName();
// }
if (0l != staffObj.getSpectraMRN()) {
	spectraMRN = StringUtil.valueOf(staffObj.getSpectraMRN());
}
//---------------------------------------------------//
if (0l != staffObj.getFacilityId()) {
	facilityId = StringUtil.valueOf(staffObj.getFacilityId());
}
//---------------------------------------------------//

// if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(staffObj.getExternalMRN()))) {
// 	externalMRN = staffObj.getExternalMRN();
// }
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(staffObj.getPatientHLABId()))) {
	patientHLABId = staffObj.getPatientHLABId();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(staffObj.getClinicName()))) {
	clinicName = staffObj.getClinicName();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(staffObj.getFacilityNum()))) {
	//Used facility Id as HLAB number.
	HLABId = staffObj.getFacilityNum();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(staffObj.getLabName()))) {
	labName = staffObj.getLabName();
}
%>
//---------------------------------------------------//
var facilityIdValue = <%=facilityId%>;
var spectraMRNValue = <%=spectraMRN%>;
var primaryFacNumber = "<%= HLABId %>";
//---------------------------------------------------//
</script>
</head>
<style type="text/css">
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
</style>

<body class="theme-whbl  pace-done" onload="showSummaryStaffGrid();">

<div id="theme-wrapper">
      <div id="content-wrapper">
          <div class="col-lg-12">
            <div class="row">
              <div class="col-md-9 col-lg-9 med" style="padding:0px 6px 10px 6px !important; margin:0px 0px 10px 0px;background-color:#fff;width: 100%;">
				    <div class="conversation-content">
                      <div class=" ">
                        <div class="row" style="border-bottom:1px solid #a1a1a1; margin: 0px 2px 0px 2px;">
                          <div class="wid50 pull-left">
                            <div class=" pull-left">
                              <div>
                                <h3 class="h3-color" style="margin-top: 7px;"><img alt="Staff" src="<%=request.getContextPath() %>/images/staff.png" style="height: 31px" title="Staff"><%=patientName %></h3>
                              </div>
                            </div>
                          </div>
                          <div class="wid50 pull-left">
                            <div class="pull-right" style="margin-bottom: 10px;"> <a href="https://spectra.service-now.com" target="_blank"><span> <img src="<%=request.getContextPath()%>/images/fowd.jpg" width="100%" height="100%" alt="" /> </span></a>  </div>
                          </div>
                        </div>
                        <div class="row martp25">
                          <div class="border col-xs-2 col-sm-2 col-md-2">
                            <div class="pull-left"><img src="<%=request.getContextPath() %>/images/gender.jpg" width="auto" height="auto" alt=""/></div>
                            <div class="pull-left gender-txt">Gender: <b><%=gender %></b></div>
                          </div>
                          <div class="border col-xs-3 col-sm-3 col-md-3">
                            <div class="pull-left"><img src="<%=request.getContextPath() %>/images/dob.jpg" width="32" height="32" alt=""/></div>
                            <div class="pull-left gender-txt">DOB: <b><%=DOB %></b></div>
                          </div>
                        </div>
                        <div class="bg-txt">
                          <div class="wid40 pull-left">
                            <div class="patient-heading">
                              <div class="pull-left"><img src="<%=request.getContextPath() %>/images/patient-ids.jpg" width="24" height="24" alt=""/></div>
                              <div class="pull-left ids-txt">PATIENT IDS</div>
                              <div class="clearfix"></div>
                            </div>
                            <div class="mar-box">
                              <div class="pull-left wid40 ">
                                <p>Lab</p>
                                <p>Spectra MRN</p>
                              </div>
                              <div class="pull-left wid50"></div>
                              <label><strong>&nbsp;<%=labName %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=spectraMRN %></strong></label>
                              </br>
                            </div>
                          </div>
                          <div class="wid60 pull-left">
                            <div class="patient-heading">
                              <div class="pull-left"><img src="<%=request.getContextPath() %>/images/clinical-information.jpg" width="24" height="24" alt=""/></div>
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
                              <label><strong>&nbsp;<%=clinicName %></strong></label>
                              <br/>
                              <label><a onclick="goToFacDemographicsPage();"><strong>&nbsp;<%=facilityName %></strong></a></label>
                              <br/>
                              <label><strong>&nbsp;<%=HLABId %></strong></label>
                             </div>
                            </div>
                          </div>
                        </div>
                        <div class=" pull-left"  style=display:inline;">
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
                         					     <option value="3" >Last 90 days</option>
                          					     <option value="4">Last 120 days</option>
                               					<option value="6">Last 6 months</option>
                                				<option value="12" selected="selected" >Last 12 months</option>
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
                         					     <option value="3" >Last 90 days</option>
                          					     <option value="4">Last 120 days</option>
                               					<option value="6">Last 6 months</option>
                                				<option value="12" selected="selected" >Last 12 months</option>
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
													<td style="border :none; padding:0;">
														<div id="orderSourceGridDiv"></div>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								</div>
<!------------------------------------------------------------------------------------------------------------------->
							</div>
                        
<!--                         <div class="martp25 table-bor"> -->
<%--                           <div style="margin:-66px 0px 0px -21px;"><img src="<%=request.getContextPath() %>/images/order-summary.jpg" width="149" height="47" alt=""/></div> --%>
<!--                           <div style="margin-top: 12px;"> -->
<!--                             <div class="pull-right marbt10 "> -->
<!--                            <label class="filter">Filter</label> -->
<!--                               <select class="selet" id="filterSelect" onchange="changeFilter();"> -->
<!--                                 <option value="1">Last 30 days</option> -->
<!--                                 <option value="2">Last 60 days</option> -->
<!--                                 <option value="3" >Last 90 days</option> -->
<!--                                 <option value="4">Last 120 days</option> -->
<!--                                 <option value="6">Last 6 months</option> -->
<!--                                 <option value="12" selected="selected" >Last 12 months</option> -->
<!--                                 <option value="24">Last 24 months</option>  -->
<!--                               </select>                                                      -->
<!--                             </div> -->
<!--                         	<script type="text/javascript"> -->
<%-- 							if (<%=request.getSession().getAttribute("dateRange")%>	!= null) { --%>
								
<%-- 							document.getElementById("filterSelect").value =	<%=request.getSession().getAttribute("dateRange")%>;			}					 --%>
 							
<!-- 							</script> -->
<!--                             <div class="clearfix"></div> -->
<!--                             <table class="table table-bordered" id="orderSummaryTable"> -->
<!--                               <thead> -->
<!--                               </thead> -->
<!--                               <tbody> -->
<!--                                 <tr class="font-head1 bor-none"> -->
<!--                                   <td> -->
<!--                                   	<div id="orderSummaryGridDiv"></div> -->
<!--                                   </td> -->
<!--                                 </tr> -->
<!--                               </tbody> -->
<!--                             </table> -->
<!--                           </div> -->
<!--                         </div> -->
                      </div>
                    </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
<script src="<%=request.getContextPath() %>/js/jquery.js"></script> 
<script src="<%=request.getContextPath() %>/js/bootstrap.js"></script> 
<script src="<%=request.getContextPath() %>/js/scripts.js"></script> 
<script src="<%=request.getContextPath() %>/js/pace.min.js"></script>
</body>
</html>