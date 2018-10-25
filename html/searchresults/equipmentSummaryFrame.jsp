<!DOCTYPE html>
<%@include file="/html/init.jsp" %>
<%@page import="com.spectra.symfonielabs.domainobject.Equipment"%>
<%@page import="com.spectra.symfonie.common.constants.CommonConstants"%>
<%@page import="com.spectra.symfonie.common.util.StringUtil"%>
<%@page import="com.spectra.symfonie.framework.util.SimpleDateFormatUtil"%>
<html>
<head>
<style type="text/css">
.MainPanel .x-tab-bar-strip {
    top: 52px !important; /* Default value is 20, we add 20 = 40 */
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
    border-top-color: #d0c9c9;  
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Equipment Summary</title>

<!-- Added for disabling caching. -->
<script type="text/javascript">
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<script type="text/javascript" src="'+contextPath+'/js/equipmentSummary.js?'+dateValue+'"><\/script>');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/bootstrap.min.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/symfonie-labs.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/theme_styles.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/css/ext5ExtendedStyles.css?'+dateValue+'">');
</script>
<!-- End -->

<script type="text/javascript">
var url = parent.orderSummaryURL;
//---------------------------------------------------//
// var facilityIdValue = parent.facilityId;
// var spectraMRNValue = parent.spectraMRN;
//---------------------------------------------------//
var urlPath = '<%= request.getContextPath()%>';
<%
Equipment equipmentObj = new Equipment();
SimpleDateFormat simpleDateFmt =  SimpleDateFormatUtil.getSimpleDateFormatLocale("MM/dd/yyyy");
String machineName = CommonConstants.EMPTY_STRING;
String serialNumber = CommonConstants.EMPTY_STRING;
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
String location = CommonConstants.EMPTY_STRING;
//---------------------------------------------------//
String facilityId = CommonConstants.EMPTY_STRING;
//---------------------------------------------------//

// XX EquipmentProcessor. <getOrderSum> : equipmentSummaryInfo :  machineName: 2035-N.RAMOS HOME~|~serialNumber: null~|~spectraMRN: 9876606332~|~clientName: Dialysis Clinic, Inc.~|~facilityName: Hawthorne Env~|~facilityId: 3222~|~facilityNum:A114137~|~spectraMRN: 9876606332~|~facilityPhoneNumber: (914)-592-4366~|~startIndex: 0~|~sortDirection: ~|~sortField: ~|~listSize: 0~|~endIndex: 0

if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("equipmentSummaryInfo")))){
	equipmentObj =(Equipment) request.getSession().getAttribute("equipmentSummaryInfo");
}


facilityName = StringUtil.valueOf(equipmentObj.getFacilityName());
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(equipmentObj.getMachineName()))) {
	machineName = equipmentObj.getMachineName();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(equipmentObj.getSerialNumber()))) {
	serialNumber = equipmentObj.getSerialNumber();
}
// if(null != equipmentObj.getDateOfBirth()) {
// 	DOB =  simpleDateFmt.format(equipmentObj.getDateOfBirth());
// }
// if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(equipmentObj.getFacilityNum()))) {
// 	modality = equipmentObj.getFacilityNum();
// }
// if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(equipmentObj.getClientName()))) {
// 	physician = equipmentObj.getClientName();
// }
if (null  != equipmentObj.getSpectraMRN()) {
	spectraMRN = StringUtil.valueOf(equipmentObj.getSpectraMRN());
}
//---------------------------------------------------//
if (0l != equipmentObj.getFacilityId()) {
	facilityId = StringUtil.valueOf(equipmentObj.getFacilityId());
}
//---------------------------------------------------//
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(equipmentObj.getSpectraMRN()))) {
	externalMRN = equipmentObj.getSpectraMRN();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(equipmentObj.getFacilityPhoneNumber()))) {
	patientHLABId = equipmentObj.getFacilityPhoneNumber();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(equipmentObj.getClientName()))) {
	clinicName = equipmentObj.getClientName();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(equipmentObj.getFacilityNum()))) {
	//Used facility Id as HLAB number.
	HLABId = equipmentObj.getFacilityNum();
}
if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(equipmentObj.getFacilityNum()))) {
	labName = equipmentObj.getLabName();
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

<body class="theme-whbl  pace-done" onload="showEquipmentSummaryGrid();">

<div id="theme-wrapper">
      <div id="content-wrapper">
          <div class="col-lg-12">
            <div class="row">
              <div class="col-md-9 col-lg-9 med" style="padding:0px 6px 10px 6px !important; margin:0px 0px 10px 0px;background-color:#fff;width: 100%;">
				    <div class="conversation-content">
                      <div class=" ">
                        <div class="row" style="border-bottom:1px solid #a1a1a1; margin: 0px 2px 0px 2px;">
				            <div class="mar-box">
				               <div class="pull-left wid35">
				                 <h3 class="h3-color" style="margin-top: -2px;"><%=machineName %></h3>
				               </div>
				               
<!-- 				               <div class="pull-left wid65"> -->
<%-- 				               <label><strong>Serial#&nbsp;&nbsp;<b style="font-size: 16px; color:#FF6600;"><%=serialNumber %></b></strong></label>  --%>
<!-- 				               </div> -->

			                     <div class="large-6 columns medium-6">
			                        <div class="row"><span class="content-title">
			                        <label><strong>Serial#&nbsp;<%=serialNumber %></strong></label>
			  						</span></div>
			                     </div>
<!--                      <div class="large-6 columns medium-6"> -->
<!--                         <div class="row"><span class="content-title"> -->
<%--                         <label><strong><%=corporationName %></strong></label> --%>
<!--   						</span></div> -->
<!--                      </div> -->
				              
	                          <div class="">
	                              <div class="pull-right" style="margin-bottom: 10px;margin-top: -30px;"> <a href="https://spectra.service-now.com" target="_blank"><span> <img src="<%=request.getContextPath()%>/images/fowd.jpg" width="100%" height="100%" alt="" /> </span></a>  </div>
	                         <!-- <div class="pull-right" style="margin-bottom: 20px;margin-top: -50px;"> <a href="https://spectra.service-now.com" target="_blank"><span> <img src="<%=request.getContextPath()%>/images/fowd.jpg" width="100%" height="100%" alt="" /> </span></a>  </div> -->
	                          </div>   
	                                   
				            </div>

<!--                        <div class="wid50 pull-left"> -->
<%--                           <div class="pull-right" style="margin-bottom: 10px;"> <a href="https://spectra.service-now.com" target="_blank"><span> <img src="<%=request.getContextPath()%>/images/fowd.jpg" width="100%" height="100%" alt="" /> </span></a>  </div> --%>
<!--                        </div> -->

                        </div>

                		<!--  <div class="bg-txt1" style="height: 325px;"> -->
                        <div class="bg-txt" style="height: 175px;">
                          <div class="wid40 pull-left">
                            <div class="patient-heading">
                              <div class="pull-left"><img src="<%=request.getContextPath() %>/images/patient-ids.jpg" width="24" height="24" alt=""/></div>
                              <div class="pull-left ids-txt">EQUIPMENT INFORMATION</div>
                              <div class="clearfix"></div>
                            </div>
                            <div class="mar-box">
                              <div class="pull-left wid40 ">
                                <p>Lab</p>
                                <p>Spectra MRN</p>
                           		<p>Lab Id</p>
                              </div>
                              <div class="pull-left wid50"></div>
                              <label><strong>&nbsp;<%=labName %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=spectraMRN %></strong></label>
<!--                               </br> -->
<%--                               <label><strong>&nbsp;<%=modality %></strong></label> --%>
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
                                <p>Location</p>
                              </div>
                              <div class="pull-left wid65">
                              <label><strong>&nbsp;<%=clinicName %></strong></label>
                              <br/>
                              <label><a onclick="goToFacDemographicsPage();"><strong>&nbsp;<%=facilityName %></strong></a></label>
                              <br/>
                              <label><strong>&nbsp;<%=HLABId %></strong></label>
                              <br/>
                              <label><strong>&nbsp;<%=location %></strong></label>
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
                            <script type="text/javascript">
							if (<%=request.getSession().getAttribute("dateRange")%>	!= null) {
								document.getElementById("filterSelect").value =
								<%=request.getSession().getAttribute("dateRange")%>;								
							}
							</script>
                            <div class="clearfix"></div>
                            <table class="table table-bordered" id="orderSummaryTable">
                              <thead>
                              </thead>
                              <tbody>
                                <tr class="font-head1 bor-none">
                                  <td>
                                  	<div id="orderEquipmentSummaryGridDiv"></div>
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
<script src="<%=request.getContextPath() %>/js/jquery.js"></script> 
<script src="<%=request.getContextPath() %>/js/bootstrap.js"></script> 
<script src="<%=request.getContextPath() %>/js/scripts.js"></script> 
<script src="<%=request.getContextPath() %>/js/pace.min.js"></script>
</body>
</html>