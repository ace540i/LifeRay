<!DOCTYPE html>
<%@page import="com.spectra.symfonielabs.domainobject.EnvRequisitionDetails"%>
<%@page import="com.spectra.symfonie.framework.util.SimpleDateFormatUtil"%>
<%@page import="com.spectra.symfonielabs.domainobject.Patient"%>
<%@page import="com.spectra.symfonie.common.constants.CommonConstants"%>
<%@page import="com.spectra.symfonie.common.util.StringUtil"%>
<%@page import="com.spectra.symfonielabs.constants.OrderConstants"%>
<%@page import="com.spectra.symfonielabs.domainobject.RequisitionDetails"%>
 <%@page import="com.spectra.symfonie.common.util.DateUtil"%>
<%@include file="/html/init.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Search</title>
<script type="text/javascript">
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/bootstrap.min.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/symfonie-labs.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/theme_styles.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/css/ext5ExtendedStyles.css?'+dateValue+'">');
    document.write('<script type="text/javascript" src="'+contextPath+'/js/requisitionDetails.js?'+dateValue+'"><\/script>');
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
	
<%  RequisitionDetails reqDet = new RequisitionDetails();
	Patient patient = new Patient(); 
	EnvRequisitionDetails envRequisitionDetails = new EnvRequisitionDetails();
	if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("patientDet")))){
		reqDet =(RequisitionDetails) request.getSession().getAttribute("patientDet");
		if(null !=  reqDet.getPatient()){
			patient = reqDet.getPatient();
		}
		if (null != reqDet.getEnvRequisitionDetails()) {
			envRequisitionDetails = reqDet.getEnvRequisitionDetails();
		}
	}
	String drawdate = CommonConstants.EMPTY_STRING;
	String spectraMRN = CommonConstants.EMPTY_STRING;
	String noOfTest = CommonConstants.EMPTY_STRING;
	SimpleDateFormat simpleDateFmt =  SimpleDateFormatUtil.getSimpleDateFormatLocale("MM/dd/yyyy");
	String reqNum = StringUtil.valueOf(reqDet.getRequisition());
	if(null != reqDet.getDrawDate()) {
		drawdate =  simpleDateFmt.format(reqDet.getDrawDate());
	}
	String status = StringUtil.valueOf(reqDet.getStatus());
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
	
	String primaryFacNumber = StringUtil.valueOf(patient.getFacilityNum());
	String abnormalFlag = StringUtil.valueOf(reqDet.getAbnormalFlag());
	String cancelledTestIndicator = StringUtil.valueOf(reqDet.isCancelledTestIndicator());
	
	//Added for environmental demographic details.
	String machineName = StringUtil.valueOf(envRequisitionDetails.getMachineName());
	String serialNum = StringUtil.valueOf(envRequisitionDetails.getSerialNum());
	String locationType = StringUtil.valueOf(envRequisitionDetails.getLocationType());
	String location = StringUtil.valueOf(envRequisitionDetails.getLocation());
	String collectedBy = StringUtil.valueOf(envRequisitionDetails.getCollectedBy());
	String collectionTime = StringUtil.valueOf(envRequisitionDetails.getCollectionTime());
	String source = StringUtil.valueOf(envRequisitionDetails.getSource());
	String equipmentStatus = StringUtil.valueOf(envRequisitionDetails.getEquipmentStatus());
	
	request.getSession().setAttribute("hlabNum", HLABNum);
	String patientTypeSubGrp = reqDet.getPatientTypeSubGrp();
	%>
	var abnormalFlag = '<%=abnormalFlag%>';
	var cancelledTestIndicator = '<%=cancelledTestIndicator%>'
		var numberOfTests = '<%=noOfTest%>';
	var frequency = '<%=frequency%>';
	var facId = '<%=patient.getFacilityId()%>';
	var spectraMRNVal = '<%=spectraMRN%>';
	var facilityIdVal = '<%=patient.getFacilityId()%>';
	var patientHLABNum = '<%=HLABNum%>';
	var patientTypeSubGrp = parent.patientTypeSubGrp;
	var primaryFacNumber = '<%=primaryFacNumber%>';

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
                            <h3 class="h3-color" style="margin-top: 7px;"><span class="font-grey">#</span>&nbsp;<%=reqNum %><img id="checkImg" style="display: none" src="<%=request.getContextPath()%>/images/Normal.png" title="All Tubes Received"width="19" height="19" alt=""  class="space" />
                            <img id="closeImg" style="display: none" src="<%=request.getContextPath()%>/images/Alert.png" title='Alert and/or Exception' width="19" height="19" alt="" class="space"/>
                            <img id="closeIndImg" style="display: none" src="<%=request.getContextPath()%>/images/cancelled.png" title='Cancelled Test(s)' width="19" height="19" alt="" class="space" /></h3>
                          </div>
                        </div>
                      </div>
 
<!-- // timc  -->                      
                      <div class="wid35 pull-left" style="width: 35%;">
                      	<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 font-gen font-grey1"><font style="font-size: 16px;">Draw Date :</font><b style="font-size: 16px;"> &nbsp;<%=drawdate %></b></div>
                      </div>
                      
                      <div class="wid25 pull-left" style="width: 20%;">
                      	<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 font-gen font-grey1"><font style="font-size: 16px;">Status:</font><b style="font-size: 16px;">&nbsp;<%=status %></b></div>
                      </div>
<!-- // timc  -->                      
                                            
                      <div class="wid20 pull-left">
                        <div class=" pull-right" style="margin-bottom: 10px;"> <a href="https://spectra.service-now.com" target="_blank"><span> <img src="<%=request.getContextPath()%>/images/fowd.jpg" width="100%" height="100%" alt="" /> </span></a>  </div>
                      </div>
                    </div>
                    
                    <div class="bg-txt1" style="height: auto;">
                          <div class="wid50 pull-left">
                            <div class="mar-box">
                              <div class="pull-left wid30 ">
							  <p>Sample ID</p>
							</div>
							<div class="pull-left wid70">
							  <label><a onclick="goToOrderSummaryPage();"><strong style="font-size: 14px;">&nbsp;<%=machineName %></strong></a></label>
							</div>
							<div class="clearfix"></div>
							
							<div class="pull-left wid30 ">
							  <p>Serial #</p>
							</div>
							<div class="pull-left wid70">
							  <label><strong>&nbsp;<%=serialNum %></strong></label>
							</div>
							<div class="clearfix"></div>
							
							<div class="pull-left wid30 ">
							  <p>Location Type</p>
							</div>
							<div class="pull-left wid70">
							  <label><strong>&nbsp;<%=locationType %></strong></label>
							</div>
							<div class="clearfix"></div>
							
							<div class="pull-left wid30 ">
							  <p>Location</p>
							</div>
							<div class="pull-left wid70">
							  <label><strong>&nbsp;<%=location %></strong></label>
							</div>
							<div class="clearfix"></div>
							
							<div class="pull-left wid30 ">
							  <p>Collected By</p>
							</div>
							<div class="pull-left wid70">
							  <label><strong>&nbsp;<%=collectedBy %></strong></label>
							</div>
							<div class="clearfix"></div>
							
							<div class="pull-left wid30 ">
							  <p>Collection Time</p>
							</div>
							<div class="pull-left wid70">
							  <label><strong>&nbsp;<%=collectionTime %></strong></label>
							</div>
							<div class="clearfix"></div>
							</div>
                          </div>
                          <div class="wid50 pull-left">
                            <div class="mar-box">
							  <div class="pull-left wid40">
								<p>Facility</p>
							  </div>
							  <div class="pull-left wid60">
								<label><a onclick="goToFacDemographicsPage();"><strong style="font-size: 14px;">&nbsp;<%=clinic %></strong></a></label>
							  </div>
							  <div class="clearfix"></div>
							  
							  <div class="pull-left wid40">
								<p>Corporation</p>
							  </div>
							  <div class="pull-left wid60">
								<label><strong>&nbsp;<%=corpName %></strong></label>
							  </div>
							  <div class="clearfix"></div>
							  
							  <div class="pull-left wid40">
								<p>Acct#</p>
							  </div>
							  <div class="pull-left wid60">
								<label><strong>&nbsp;<%=accountId %></strong></label>
							  </div>
							  <div class="clearfix"></div>
							  
							  <div class="pull-left wid40">
								<p>LAB</p>
							  </div>
							  <div class="pull-left wid60">
								<label><strong>&nbsp;<%=lab %></strong></label>
							  </div>
							  <div class="clearfix"></div>
							  
							  <div class="pull-left wid40">
								<p>HLAB#</p>
							  </div>
							  <div class="pull-left wid60">
								<label><strong>&nbsp;<%=HLABNum %></strong></label>
							  </div>
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