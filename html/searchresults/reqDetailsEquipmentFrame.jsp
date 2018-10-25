<!DOCTYPE html>
<%@page import="com.spectra.symfonie.framework.util.SimpleDateFormatUtil"%>
<%@page import="com.spectra.symfonielabs.domainobject.Patient"%>
<%@page import="com.spectra.symfonie.common.constants.CommonConstants"%>
<%@page import="com.spectra.symfonie.common.util.StringUtil"%>
<%@page import="com.spectra.symfonielabs.constants.OrderConstants"%>
<%@page import="com.spectra.symfonielabs.domainobject.RequisitionDetails"%>
 <%@page import="java.util.Date"%>
 <%@page import="com.spectra.symfonie.common.util.DateUtil"%>
<%@include file="/html/init.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Search</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/searchresultscss/bootstrap.min.css">
<link href="<%=request.getContextPath()%>/searchresultscss/symfonie-labs.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/searchresultscss/theme_styles.css">
<!--<link type="text/css" rel="stylesheet" href="css/bootstrap.css">-->
<script language="javascript" type="text/javascript" src="/Resources/ext-5.1.0/build/ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="/Resources/ext-5.1.0/build/packages/ext-theme-classic/build/resources/ext-theme-classic-all.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ext5ExtendedStyles.css">
<script src="<%= request.getContextPath()%>/js/requisitionDetails.js"></script>
<script type="text/javascript">
	var url = parent.requisitionUrl;
	var requisitionNo = null;
	<%
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
		if('TRUE'==abnormalFlag.toUpperCase()) {
			document.getElementById('checkImg').style.display = 'none';
			document.getElementById('closeImg').style.display = 'inline';
		} else {
			document.getElementById('checkImg').style.display = 'inline';
			document.getElementById('closeImg').style.display = 'none';
		}
		if ('TRUE'== cancelledTestIndicator.toUpperCase()) {
			document.getElementById('closeIndImg').style.display = 'inline';
		}
	}
<%
	RequisitionDetails reqDet = new RequisitionDetails();
	Patient patient = new Patient(); 
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
	String HLABNum = StringUtil.valueOf(patient.getHLABNumber());
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
	%>
	var abnormalFlag = '<%=abnormalFlag%>';
	var cancelledTestIndicator = '<%=cancelledTestIndicator%>'
</script>
</head>
<body class="theme-whbl  pace-done" onload="onPageLoad();">
<H4 style="color:blue">searchresults/reqDetailsEquipmentFrame.jsp</H4>
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
                            <h3 class="h3-color" style="margin-top: 7px;"><span class="font-grey">#</span>&nbsp;<%=reqNum %>
                            <img id="checkImg" style="display: none" src="<%=request.getContextPath()%>/images/Normal.png" title="All Tubes Received"width="19" height="19" alt=""  class="space"/>
                            <img id="closeImg" style="display: none" src="<%=request.getContextPath()%>/images/Alert.png"  title='Alert and/or Exception' width="19" height="19" alt="" class="space"/>
                            <img id="closeIndImg" style="display: none" src="<%=request.getContextPath()%>/images/cancelled.png" title='Cancelled Test(s)' width="19" height="19" alt="" class="space"/></h3>
                          </div>
                        </div>
                      </div>
                      <div class="wid50 pull-left">
                        <div class=" pull-right" style="margin-bottom: 10px;"> <a href="https://spectra.service-now.com" target="_blank"><span> <img src="<%=request.getContextPath()%>/images/fowd.jpg" width="100%" height="100%" alt="" /> </span></a>  </div>
                      </div>
                    </div>
                    <div class="row" style=" margin: 0px 2px 0px 2px;">
               			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 font-gen font-grey1"><img src="<%=request.getContextPath()%>/images/draw.jpg" width="32" height="32" alt="" class="marrt5"/>Draw Date :<b style="font-size: 12px;"> &nbsp;<%=drawdate %></b></div>
               			<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 font-gen font-grey1"><img src="<%=request.getContextPath()%>/images/chart-icon.jpg" width="32" height="32" alt="" class="marrt5"/>Status: <b style="font-size: 12px;">&nbsp;<%=status %></b></div>
                    </div>
                    <div class="bg-txt1" style="height: 325px;">
                          <div class="wid40 pull-left">
                            <div class="patient-heading">
                              <div class="pull-left"><img src="<%=request.getContextPath()%>/images/pati.jpg" width="24" height="24" alt=""/></div>
                              <div class="pull-left ids-txt">PATIENT INFORMATION</div>
                              <div class="clearfix"></div>
                            </div>
                            <div class="mar-box">
                              <div class="pull-left wid40 ">
                                <p>Name</p>
                                <p>DOB</p>
                                <p>Gender</p>
                                <p>Modality</p>
                                <p>Physician</p>
                              </div>
                              <div class="pull-left wid50"></div>
                              <label><strong>&nbsp;<%=patientName %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=DOB %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=gender %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=modality %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=phyName %></strong></label>
                            </div>
                          </div>
                          <div class="wid60 pull-left">
                            <div class="patient-heading">
                              <div class="pull-left"><img src="<%=request.getContextPath()%>/images/clinical-information.jpg" width="24" height="24" alt=""/></div>
                              <div class="pull-left ids-txt">CLINICAL INFORMATION</div>
                              <div class="clearfix"></div>
                            </div>
                            <div class="mar-box">
                              <div class="pull-left wid35">
                                <p>Corporation</p>
                                <p>Clinic</p>
                                <p>Acct#</p>
                                <p>HLAB#</p>
                              </div>
                              <div class="pull-left wid65">
                              <label><strong>&nbsp;<%=corpName %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=clinic %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=accountId %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=HLABNum %></strong></label>
                             </div>
                            </div>
                          </div>
                          <div class="clearfix"></div>
                           <div class="wid40 pull-left">
                            <div class="patient-heading">
                              <div class="pull-left"><img src="<%=request.getContextPath()%>/images/patient-ids.jpg" width="24" height="24" alt=""/></div>
                              <div class="pull-left ids-txt">PATIENT IDS</div>
                              <div class="clearfix"></div>
                            </div>
                            <div class="mar-box">
                              <div class="pull-left wid40 ">
                                <p>LAB</p>
                                <p>Patient ID</p>
                                <p>Spectra MRN</p>
                                <!-- <p>External MRN</p> -->
                              </div>
                              <div class="pull-left wid50"></div>
                              <label><strong>&nbsp;<%=lab %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=patientHLABNum %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=spectraMRN %></strong></label>
                              </br>
                             <%--  <label><strong>&nbsp;<%=externalMRN %></strong></label>
                              </br> --%>
                            </div>
                          </div>
                          <div class="wid60 pull-left">
                            <div class="patient-heading">
                              <div class="pull-left"><img src="<%=request.getContextPath()%>/images/patient-ids.jpg" width="24" height="24" alt=""/></div>
                              <div class="pull-left ids-txt">ADDITIONAL REQUISITION DETAILS</div>
                              <div class="clearfix"></div>
                            </div>
                            <div class="mar-box">
                              <div class="pull-left wid35">
                                <!-- <p>Ordering System</p> -->
                                <p>Frequency</p>
                                <p># of Tests</p>
                              </div>
                              <div class="pull-left wid65">
                              <%-- <label><strong><%=orderSys %></strong></label>
                              </br> --%>
                              <label><strong>&nbsp;<%=frequency %></strong></label>
                              </br>
                              <label><strong>&nbsp;<%=noOfTest %></strong></label>
                             </div>
                            </div>
                          </div>
                        </div>
                    <div class="martp25">
                      <h3 class="h3-color font-h318">TUBE SUMMARY</h3>
                      <div class="clearfix"></div>
                      	<div id="testSummaryDiv"></div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="martp25">
                      <h3 class="h3-color font-h318">ORDER DETAILS</h3>
                      <!--
                       <div class="order-dts"><b>Comments:</b></br>
                        </br>
                        	Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                        </div>
                        -->
                      <div class="clearfix"></div>
                      	 <div id="orderDetailsDiv" ></div>
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
</body>
</html>