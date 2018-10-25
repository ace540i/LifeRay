<!doctype html>
<%@page import="com.spectra.symfonielabs.constants.FacilityConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.ParseException"%>
<%@include file="/html/init.jsp"%>
<%@page import="com.spectra.symfonie.common.util.StringUtil"%>
<%@page import="com.spectra.symfonie.common.constants.CommonConstants"%>
<%@page	import="com.spectra.symfonielabs.domainobject.FacilityDemographics"%>
<%@page import="com.spectra.symfonielabs.domainobject.PhoneCallData"%>
<html class="no-js" lang="en">
<head>

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Requisition Info</title>
<script type="text/javascript">    
	    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
	    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/facilityDemographics.css?'+dateValue+'">');
	    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/foundationcss/css/foundation.css?'+dateValue+'">');
	    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/foundationcss/fonts/foundation-icons/foundation-icons.css?'+dateValue+'">');
	    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/facDemo_themeStyles.css?'+dateValue+'">');
	    document.write('<script type="text/javascript" src="'+contextPath+'/js/facilityDemographics.js?'+dateValue+'"><\/script>');
	    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/css/ext5ExtendedStyles.css?'+dateValue+'">');
	  </script>
<script src="<%=request.getContextPath()%>/js/vendor/modernizr.js"></script>
<style type="text/css">
#labChart div {
	border: 0px none !important;
}
#labChartLegend {
	right: 26px !important;
	left: auto !important;
}
#comboPanel-innerCt {
	margin-bottom: -28px;
	z-index: 2;
	width: 300px;
}
#subGrpSelect {
	margin-bottom: -28px;
	z-index: 2;
	padding-left: 40px;
}
#comboPanel-body {
	display: inline;
}
#myModal1 {
	width: 80%;
}
#noteDiv {
	display: inline-block;
	border-bottom: 1px solid #a5abaf;
	/* text-decoration:underline; */
	padding-left: 4.46%;
	font-size: 14px;
	font-weight: bold;
	color: #005f92;
}
#reqDivTable {
	padding-left: 3.8%;
}
.x-grid-with-row-lines .x-grid-cell-inner {
	line-height: 0px;
	padding: 0px;
	margin: -6px 0px -14px 0px;
}
.x-grid-item {
	padding: 0px;
	margin: 0px 0px 0px 0px;
}
.customized .x-grid-empty {
	color: #005f92;
	font-style: bold;
	font-size: 14px;
}
#headingDiv1 {
	font-weight: normal;
	width :300px;
}
#headingDiv2 {
	font-weight: normal;
}
#tubeSumDiv {
	font-weight: normal;
	 width :260px;
	 padding-left: 66px;
}
</style>
<script type="text/javascript">
      var url = parent.getFacDemographicsURL;
      var urlPath = '<%=request.getContextPath()%>';
     <%FacilityDemographics facDemographics = new FacilityDemographics();
   	  String facilityName = CommonConstants.EMPTY_STRING;
   	  String corporationName = CommonConstants.EMPTY_STRING;
   	  String labName = CommonConstants.EMPTY_STRING;
   	  String recvDate = CommonConstants.EMPTY_STRING;
   	  String discontinuedDate = CommonConstants.EMPTY_STRING;
   	  String mainPhoneNumber = CommonConstants.EMPTY_STRING;
   	  String mainPhoneNumberVal = "Not Available";
   	  String state = CommonConstants.EMPTY_STRING;
   	  String typeOfService = CommonConstants.EMPTY_STRING;
   	  String timeZoneVal = CommonConstants.EMPTY_STRING;
   	  String timeZoneDisplayVal = "Not Available";
   	  String orderingSystem = CommonConstants.EMPTY_STRING;
   	  String serverType = CommonConstants.EMPTY_STRING;
      String patientReporting = CommonConstants.EMPTY_STRING;
   	  String NCE = CommonConstants.EMPTY_STRING;
   	  String salesRepresentative = CommonConstants.EMPTY_STRING;
   	  String drawWeek = CommonConstants.EMPTY_STRING;
   	  String drawDays = CommonConstants.EMPTY_STRING;
   	  String kitIndicator = CommonConstants.EMPTY_STRING;
   	  String facilityId = CommonConstants.EMPTY_STRING;
   	  String clinicalManager = CommonConstants.EMPTY_STRING;
   	  String medicalDirector = CommonConstants.EMPTY_STRING;
   	  String administrator = CommonConstants.EMPTY_STRING;
      String phoneComments = CommonConstants.EMPTY_STRING;
   	  String physicalAddress = CommonConstants.EMPTY_STRING;
   	  String mailingAddress = CommonConstants.EMPTY_STRING;
   	  String alertInformation = CommonConstants.EMPTY_STRING;
   	  String phoneNumber = CommonConstants.EMPTY_STRING;
   	  String faxNumber = CommonConstants.EMPTY_STRING;
   	  String indicatorVal = CommonConstants.EMPTY_STRING;
   	  String kitComments = CommonConstants.EMPTY_STRING;
   	  String corporateAcronym = CommonConstants.EMPTY_STRING;
   	  String supplyDepot = CommonConstants.EMPTY_STRING;
   	  String sapNumber = CommonConstants.EMPTY_STRING;
   	  String SupplyDeliverySch = CommonConstants.EMPTY_STRING;
   	  long facilityPk = 0L;
   	  String displayFacilityName = CommonConstants.EMPTY_STRING;
   	  boolean isSamePhoneNumber = false;
      boolean isSamePhoneNumber2 = false;
   	  List<FacilityDemographics> accountList = new ArrayList<FacilityDemographics>();
   	  List<FacilityDemographics> facDemoScheduleLst = 
			new ArrayList<FacilityDemographics>();
   	  String openCloseVal = CommonConstants.EMPTY_STRING;
   	  SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
   	  String today = newDateFormat.format(new Date());
   	  Date strToday = newDateFormat.parse(today);
   	  newDateFormat.applyPattern("EEEE");
   	  String todayVal = newDateFormat.format(strToday).toUpperCase();

   	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
   	TimeZone tz = TimeZone.getTimeZone("GMT-05:00");
   	Calendar calobj = Calendar.getInstance(tz);
    String currentTime = String.format("%02d" , calobj.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d" , calobj.get(Calendar.MINUTE));  	  
	  if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getSession().getAttribute("facDemographicsSession")))) {
		facDemographics = (FacilityDemographics) request.getSession().getAttribute("facDemographicsSession");
		facilityName = facDemographics.getFacilityName();
		corporationName = facDemographics.getCorporationName();
		corporateAcronym = facDemographics.getCorporateAcronym();
		labName = facDemographics.getServicingLab();
		recvDate = facDemographics.getFirstReceivedDate();
		discontinuedDate = facDemographics.getDiscontinuedDate();
		facilityPk = facDemographics.getFacilityPk();
		displayFacilityName = facDemographics.getDisplayFacilityName();
		if (null != facDemographics.getState()) {
			state = facDemographics.getState();			
		} else {
			state = "Not Available";
		}
		if (null != facDemographics.getTypeOfService()) {
			typeOfService = facDemographics.getTypeOfService() + " Service";			
		} else {
			typeOfService = "Not Available";
		}
		if (null != facDemographics.getTimeZone()) {
			timeZoneVal = facDemographics.getTimeZone();
			if (FacilityConstants.CENTRAL_TIME_ZONE.equalsIgnoreCase(timeZoneVal)) {
				timeZoneVal = "CT";
			} else if (FacilityConstants.PACIFIC_TIME_ZONE.equalsIgnoreCase(timeZoneVal)) {
				timeZoneVal = "PT";
			} else if (FacilityConstants.MOUNTAIN_TIME_ZONE.equalsIgnoreCase(timeZoneVal)) {
				timeZoneVal = "MT";
			} else if (FacilityConstants.EASTERN_TIME_ZONE.equalsIgnoreCase(timeZoneVal)) {
				timeZoneVal = "ET";
			}
			timeZoneDisplayVal = timeZoneVal;
		}
		if (null != facDemographics.getOrderingSystem()) {
			orderingSystem = facDemographics.getOrderingSystem();
		} else {
			orderingSystem = "Not Available";
		}
		if (null != facDemographics.getServerType()) {
			serverType = facDemographics.getServerType();
		} else {
			serverType = "Not Available";
		}
		if (null != facDemographics.getPatientReporting()) {
			patientReporting = facDemographics.getPatientReporting();
		} else {
			patientReporting = "Not Available";
		}
		if (null != facDemographics.getNCE()) {
			NCE = facDemographics.getNCE();
		} else {
			NCE = "Not Available";
		}
		if (null != facDemographics.getSalesRepresentative()) {
			salesRepresentative = facDemographics.getSalesRepresentative();
		} else {
			salesRepresentative = "Not Available";
		}
		if (null != facDemographics.getDrawWeek()) {
			drawWeek = String.valueOf(facDemographics.getDrawWeek());
		} else {
			drawWeek = "Not Available";
		}
		if (null != facDemographics.getDrawDays()) {
			drawDays = facDemographics.getDrawDays();			
		} else {
			drawDays = "Not Available";
		}	
		if (null != facDemographics.getKitIndicator()) {
			kitIndicator = facDemographics.getKitIndicator();
		} else {
			kitIndicator = "Not Available";
		}
		facilityId = facDemographics.getFacilityId();		
		if (null != facDemographics.getClinicalManager()) {
			clinicalManager = facDemographics.getClinicalManager();
		} else {
			clinicalManager = "Not Available";
		}
		if (null != facDemographics.getMedicalDirector()) {
			medicalDirector = facDemographics.getMedicalDirector();
		} else {
			medicalDirector = "Not Available";
		}
		if (null != facDemographics.getAdministrator()) {
			administrator = facDemographics.getAdministrator();
		} else {
			administrator = "Not Available";
		}
		if (null != facDemographics.getPhoneComments()) {
			phoneComments = facDemographics.getPhoneComments();
		} else {
			phoneComments = "Not Available";
		}
		
		if (null != facDemographics.getPhysicalAddress()) {
			physicalAddress = facDemographics.getPhysicalAddress();
		} else {
			physicalAddress = "Not Available";
		}
		if (null != facDemographics.getMailingAddress()) {
			mailingAddress = facDemographics.getMailingAddress();
		} else {
			mailingAddress = "Not Available";
		}
		if (null != facDemographics.getAlertInformation()) {
			alertInformation = facDemographics.getAlertInformation();			
		} else {
			alertInformation = "Not Available";
		}
		if (null != facDemographics.getSupplyDepot()) {
			supplyDepot = facDemographics.getSupplyDepot();			
		} else {
			supplyDepot = "Not Available";
		}
		if (null != facDemographics.getsapNumber()) {
			sapNumber = facDemographics.getsapNumber();			
		} else {
			sapNumber = "Not Available";
		}
		if (null != facDemographics.getSupplyDeliverySch1()) {
			SupplyDeliverySch =  facDemographics.getSupplyDeliverySch1();	
		}
	    if (null != facDemographics.getSupplyDeliverySch2()) {
	    	SupplyDeliverySch = SupplyDeliverySch + " - " + facDemographics.getSupplyDeliverySch2();
	    }
		if (null != facDemographics.getSupplyDeliverySch3()) {
			SupplyDeliverySch =  SupplyDeliverySch + " - " + facDemographics.getSupplyDeliverySch3();	
		} else {
			SupplyDeliverySch = "Not Available";
		}		
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(facDemographics.getIndicatorVal()))) {
			indicatorVal = facDemographics.getIndicatorVal();
		}
		 else {
			 indicatorVal = "Not Available";
			}
		if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(facDemographics.getKitComments()))) {
			kitComments = facDemographics.getKitComments();
		}
		 else {
			 kitComments = "Not Available";
			}
	  }
	  if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getSession().getAttribute("facDemoAccountlst")))) {
		  accountList = (List<FacilityDemographics>) request.getSession().getAttribute("facDemoAccountlst");
	  }
	  //Gets the main phone number from the master account.
	  for (FacilityDemographics facDemObj: accountList) {
		if (facilityId.equalsIgnoreCase(facDemObj.getLabAccountNumber())) {
			if (null != facDemObj.getPhoneNumber()) {
				mainPhoneNumber = facDemObj.getPhoneNumber();
			}
		}
		if (null != mainPhoneNumber && !CommonConstants.EMPTY_STRING.equalsIgnoreCase(mainPhoneNumber) && 
				null != facDemObj.getPhoneNumber() && 
				mainPhoneNumber.equalsIgnoreCase(facDemObj.getPhoneNumber())) {
			isSamePhoneNumber = true;
		} else {
			isSamePhoneNumber = false;
		}
	  }

	  if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(mainPhoneNumber)) {
		  mainPhoneNumberVal = mainPhoneNumber;
	  }
	  if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
				.valueOf(request.getSession().getAttribute("facDemoScheduleLst")))) {
		  facDemoScheduleLst = (List<FacilityDemographics>) request.getSession().getAttribute("facDemoScheduleLst");
		  if (FacilityConstants.MONDAY.equalsIgnoreCase(todayVal) || 
				  FacilityConstants.WEDNESDAY.equalsIgnoreCase(todayVal) || 
				  FacilityConstants.FRIDAY.equalsIgnoreCase(todayVal)) {
			  String tempDateVal = StringUtil.valueOf(facDemoScheduleLst.get(0).getOpenCloseMonWedFr().toUpperCase());
			  if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(tempDateVal) && !" - ".equalsIgnoreCase(tempDateVal) && !"CLOSED".equalsIgnoreCase(tempDateVal)) {
				  String openCloseTime = validateTime(tempDateVal, currentTime, timeZoneVal);
				  openCloseVal = openCloseTime;
			  } else {
				  if (" - ".equalsIgnoreCase(tempDateVal)) {
					  tempDateVal = "";
				  }
				  openCloseVal = "Schedule Unavailable";
			  }
		  } else if (FacilityConstants.TUESDAY.equalsIgnoreCase(todayVal) || 
				  FacilityConstants.THURSDAY.equalsIgnoreCase(todayVal)) {
			  String tempDateVal = StringUtil.valueOf(facDemoScheduleLst.get(0).getOpenCloseTuThSa());
			  if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(tempDateVal) && !" - ".equalsIgnoreCase(tempDateVal) && !"CLOSED".equalsIgnoreCase(tempDateVal)) {
				  String openCloseTime = validateTime(tempDateVal, currentTime, timeZoneVal);
				  openCloseVal = openCloseTime;
			  } else {
				  if (" - ".equalsIgnoreCase(tempDateVal)) {
					  tempDateVal = "";
				  }
				  openCloseVal = "Schedule Unavailable";
			  }
		  } else if (FacilityConstants.SATURDAY.equalsIgnoreCase(todayVal)) {
			  String tempDateVal = StringUtil.valueOf(facDemoScheduleLst.get(0).getOpenCloseSat());
			  if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(tempDateVal) && !" - ".equalsIgnoreCase(tempDateVal) && !"CLOSED".equalsIgnoreCase(tempDateVal)) {
				  String openCloseTime = validateTime(tempDateVal, currentTime, timeZoneVal);
				  openCloseVal = openCloseTime;
			  } else {
				  if (" - ".equalsIgnoreCase(tempDateVal)) {
					  tempDateVal = "";
				  }
				  openCloseVal = "Schedule Unavailable";
			  }
		  } else {
			  openCloseVal = "Closed for Patients";
		  }
	  }%>
	  <%!private static final String validateTime(final String tempDateVal,
			final String currentTime, final String timeZoneVal) {
		SimpleDateFormat TWELVE_TF = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat TWENTY_FOUR_TF = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		String tempTime = tempDateVal;
		String[] splitTime = tempTime.split("-");
		String openCloseStr = CommonConstants.EMPTY_STRING;
		String openTimeStr = CommonConstants.EMPTY_STRING;
		String closeTimeStr = CommonConstants.EMPTY_STRING;
		try {
			Date userDate = parser.parse(currentTime);
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(splitTime[0].trim()))) {
				openTimeStr = TWENTY_FOUR_TF.format(TWELVE_TF
						.parse(splitTime[0]));
			}
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil
					.valueOf(splitTime[1].trim()))) {
				closeTimeStr = TWENTY_FOUR_TF.format(TWELVE_TF
						.parse(splitTime[1]));
			}
			if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(openTimeStr)
					&& !CommonConstants.EMPTY_STRING
							.equalsIgnoreCase(closeTimeStr)) {
				Date openTime = parser.parse(openTimeStr);
				Date closeTime = parser.parse(closeTimeStr);
				if (userDate.before(openTime) || userDate.after(closeTime)) {
					openCloseStr = "Opens Today at " + splitTime[0];
				} else if ((userDate.after(openTime) && userDate
						.before(closeTime))
						|| (userDate.compareTo(openTime) == 0 || (userDate
								.compareTo(closeTime) == 0))) {
					openCloseStr = "Open Today Until " + splitTime[1] + " "
							+ timeZoneVal;
				}
			} else {
				openCloseStr = "Schedule Unavailable";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return openCloseStr;
	}%>
	  var facilityIdVal ="<%=facilityId%>";
	  var accountsSize = <%=accountList.size()%>;
	  var facilityPkVal = "<%=facilityPk%>";
	  var displayFacilityName = "<%=displayFacilityName%>";
	  
	 
	  
      var userName = parent.userName;         
      var ext = "";          
</script>
</head>
<body class="theme-whbl  pace-done" onload="showFacilityGraph();">
	<div id="theme-wrapper">
		<!-- Main Content Starts -->
		<div class="row margin-clear full-width">
			<div class="large-12 columns medium-12" id="content">
				<div class="row">
					<div class="large-12 columns medium-12">

						<%-- <div class="row centWidth border-grey-bottom">
                     <div class="large-6 columns medium-6 paddingTopBottom">

						<h3 class="h3-color" style="margin-top: 7px;"><%=facilityName %></h3>
                     </div>
                     <div class="clearfix"></div>
                     <div class="large-6 columns medium-6">
                        <span class="content-title">
                        <label><strong><%=corporationName %></strong></label>
                        </span>
                     </div>
                     <div class="large-6 columns medium-6">
                        <div class="right"> <label>Lab: <strong><%=labName %></strong></label> </div>
                     </div>
                  </div> --%>

						<div class="row centWidth border-grey-bottom">
							<div class="paddingTopBottom">
								<h3 class="h3-color" style="margin-top: 7px; display: inline;"><%=facilityName%></h3>
								<span class="right recvDate"
									style="padding-right: 12px; display: inline; font-weitht: normal; font-size: 0.875rem;">First
									date rec'd blood: <strong><%=recvDate%></strong>
								</span>
							</div>
							<div class="clearfix"></div>
							<div class="large-6 columns medium-6">
								<div class="row">
									<span class="content-title"> <label><strong><%=corporationName%></strong></label>
									</span>
								</div>
							</div>
							<div class="large-6 columns medium-6">
								<div class="right">
									<label>Lab: <strong><%=labName%></strong></label>

								</div>
							</div>
						</div>


						<div class="row centWidth bg-txt">
							<div class="large-6 columns medium-6">
								<div class="row margin-clear">
									<div class="patient-heading-new">
										<div class="left">
											<img src="<%=request.getContextPath()%>/images/pati.png"
												width="24" height="24" alt="">
										</div>
										<div class="left ids-txt">ACCOUNT INFORMATION</div>
										<div class="clearfix"></div>
									</div>
									<div class="mar-box patient-ids-info">
										<div class=" large-5 columns medium-5 ">
											<p>Type</p>
											<p>NCE</p>
											<p>Sales Rep</p>
											<p class="patcount">Patient Count</p>
										</div>
										<div class=" large-7 columns medium-7 ">
											<label><strong><%=typeOfService%></strong></label> <label><strong><%=NCE%></strong></label>
											<label><strong><%=salesRepresentative%></strong></label> <a
												class="patcount" href="#" data-reveal-id="patientCountModal">
												<strong><%=facDemographics.getPatientCount()%></strong>
											</a>
										</div>

									</div>
								</div>
							</div>
							<div class="reveal-modal small" id="patientCountModal"
								data-reveal="">
								<h4 class="fontBold">Patient Counts</h4>
								<hr>
								<table class="table table-bordered bordered-table" width="100%;">
									<thead>
										<tr class="font-head">
											<th>Total Patient Count</th>
											<th><%=facDemographics.getPatientCount()%></th>
										</tr>
									</thead>
									<!-- 									<tr style="background-color: #EDF5FA;"> -->
									<td style="color: #4d4d4d"><strong>Hemo</strong></td>
									<td style="color: #4d4d4d"><strong><%=facDemographics.getHemoCount()%></strong></td>
									<tr>
										<td style="color: #4d4d4d"><strong>PD</strong></td>
										<td style="color: #4d4d4d"><strong><%=facDemographics.getPdCount()%></strong></td>
									</tr>
									<tr>
										<td style="color: #4d4d4d"><strong>HH</strong></td>
										<td style="color: #4d4d4d"><strong><%=facDemographics.getHhCount()%></strong></td>
									</tr>
								</table>
								<a class="close-reveal-modal">×</a>
							</div>
							<div class="large-6 columns medium-6">
								<div class="patient-heading-new">
									<div class="left">
										<img
											src="<%=request.getContextPath()%>/images/clinical-information.png"
											width="24" height="24" alt="">
									</div>
									<div class="left ids-txt">CONTACT INFORMATION</div>
									<div class="clearfix"></div>
								</div>
								<div class="mar-box patient-ids-info">
									<div>
										<p>
											<strong><%=openCloseVal%></strong> <label
												class="right bornone colo" onClick="enterPhoneExt();"
												title="Change your Ext"
												style="display: inline; margin-left: 50%; top: -3px; position: relative;">My Ext : <span id="phoneExt"></span>
											</label>
										</p>
									</div>
									<div>
										<a href="#" data-reveal-id="myModal1">
											<p>Full Schedule</p>
										</a>
									</div>
									<div class="reveal-modal medium" id="myModal1" data-reveal="">
										<h4 class="fontBold">Full Schedule</h4>
										<hr>
										</hr>
										<table class="table table-bordered bordered-table"
											width="100%;"
											style="background-color: #EDF5FA !important; border-top: 1px solid #a5abaf !important; border-left: 0px !important; border-right: 0px !important; border-bottom: 0px !important; border-collapse: collapse;">
											<thead>
												<tr class="font-head">
													<th>Monday</th>
													<th>Tuesday</th>
													<th>Wednesday</th>
													<th>Thursday</th>
													<th>Friday</th>
													<th>Saturday</th>
												</tr>
											</thead>
											<tbody>
												<%
													for (FacilityDemographics facDemSchedule : facDemoScheduleLst)  {
												%>
												<tr class="font-head1 bor-none">
													<td><%=StringUtil.valueOf(facDemSchedule.getOpenCloseMonWedFr())%>&nbsp;&nbsp;<%=timeZoneVal%></td>
													<td><%=StringUtil.valueOf(facDemSchedule.getOpenCloseTuThSa())%>&nbsp;&nbsp;<%=timeZoneVal%></td>
													<td><%=StringUtil.valueOf(facDemSchedule.getOpenCloseMonWedFr())%>&nbsp;&nbsp;<%=timeZoneVal%></td>
													<td><%=StringUtil.valueOf(facDemSchedule.getOpenCloseTuThSa())%>&nbsp;&nbsp;<%=timeZoneVal%></td>
													<td><%=StringUtil.valueOf(facDemSchedule.getOpenCloseMonWedFr())%>&nbsp;&nbsp;<%=timeZoneVal%></td>
													<td><%=StringUtil.valueOf(facDemSchedule.getOpenCloseSat())%>
														&nbsp;&nbsp;<%=timeZoneVal%></td>
												</tr>
												<%
													}
												%>
											</tbody>
										</table>


										<a class="close-reveal-modal">×</a>
									</div>
									<div class="large-6 columns medium-6">
										<p>Main Phone Number</p>
										<p>State</p>
										<p>Time Zone</p>
									</div>

									<div class="large-6 columns medium-6">
										<label class='phone' title="Click to Call"><strong><%=mainPhoneNumberVal%></strong>
											<span class=""
											style="margin-left: 10px; top: -3px; position: relative;">
												<%
													if (!isSamePhoneNumber && !CommonConstants.EMPTY_STRING.equalsIgnoreCase(mainPhoneNumber)) {
												%>
														<img data-reveal-id="callInformation"
														src="<%=request.getContextPath()%>/images/call.png"
														width="18" height="18" alt=""> <%
 													} else {
														%> <img
														src="<%=request.getContextPath()%>/images/call.png"
														width="18" height="18" alt=""> <%
 													} %>
										</span> </label> <a id="hangup" style="display: none; color: red">Hang Up
											<img src="<%=request.getContextPath()%>/images/call.png"
											width="18" height="18" alt="">
										</a>

										<script	src="<%=request.getContextPath()%>/js/vendor/jquery.js"></script>
										<%	String phoneNum = "";  	%>
										<script type="text/javascript">
											
										</script>
										<label><strong><%=state%></strong></label> <label><strong><%=timeZoneDisplayVal%></strong></label>
									</div>
									<div class="clearfix"></div>
									<div>
										<a href="#" data-reveal-id="myModal">
											<p>Full Contact Information</p>
										</a>
									</div>

									<div class="reveal-modal small" id="callInformation"
										data-reveal="" style="">
										<div class="row">
											<h4 class="fontBold">Select Phone Number</h4>
											<hr>
											<div>
												<table cellspacing="0" cellpadding="0"
													class=" bornone table table-bordered bordered-table"
													width="100%;">
													<thead>
														<tr class="bornone colo font-head phone">
															<th>Type</th>
															<th>Phone Number</th>
														</tr>
													</thead>
													<tbody>
														<% int phoneCount1 = 0;
															for (FacilityDemographics facDemObj: accountList) {
																if (facDemObj.getPhoneNumber().matches(".*\\d+.*")){
																phoneCount1++;
														%>
														<tr class="font-head1 bor-none phone">
															<td><%=StringUtil.valueOf(facDemObj.getAccountType())%></td>
															<td id="phoneno_'+<%=phoneCount1 %>+'" class="phone2" style="cursor: pointer";
															    value="<%=StringUtil.valueOf(facDemObj.getPhoneNumber()) %>";
															  	title="Click to Call"><%=StringUtil.valueOf(facDemObj.getPhoneNumber())%>&nbsp;
																<img src="<%=request.getContextPath()%>/images/call.png" width="18" height="18" alt="">
															</td>
														</tr>
														<%		}
															}
														%>
													</tbody>
												</table>
											</div>
											<a class="close-reveal-modal">×</a>
										</div>
									</div>
									<div class="reveal-modal large medium" id="myModal"
										data-reveal="" style="">
										<div class="row">
											<h4 class="fontBold">Full Contact Information</h4>
											<hr>

											<div>
												<div class="row">
													<p>
													<div class="large-2 columns medium-2 small-6">Clinical
														Manager</div>
													<div class="large-2 columns medium-2 small-4">
														<strong><%=clinicalManager%></strong>
													</div>
													<div class="padtlt20 fontsize12">
														Physical Address: <b><%=physicalAddress%></b>
													</div>
													</p>

												</div>
												<div class="row">
													<p>
													<div class="large-2 columns medium-2 small-6">Medical
														Director</div>
													<div class="large-2 columns medium-2 small-4">
														<strong><%=medicalDirector%></strong>
													</div>
													<div class="padtlt20 fontsize12">
														Mailing Address : <b><%=mailingAddress%></b>
													</div>
													</p>
												</div>
												<div class="row">
													<p>
													<div class="large-2 columns medium-2 small-6">Administrator</div>
													<div class="large-2 columns medium-2 small-4">
														<strong><%=administrator%></strong>
													</div>
													</p>
												</div>
											</div>
											</br>
											<div class="padtlt20 ids-txt">Phone & FAX</div>
											<div>
												<table cellspacing="0" cellpadding="0"
													class=" bornone table table-bordered bordered-table"
													style="background-color: #EDF5FA !important; border-top: 1px solid #a5abaf !important; border-left: 0px !important; border-right: 0px !important; border-bottom: 0px !important; border-collapse: collapse;">
													<thead>
														<tr class="bornone colo font-head ">
															<th>Type</th>
															<th>Account Number</th>
															<th>Phone Number</th>
															<th>Fax Number</th>
														</tr>
													</thead>
													<tbody>

														<% int phoneCount = 0;
															for (FacilityDemographics facDemObj: accountList) {					
																phoneCount++;
														%>

														<tr class="font-head1 bor-none">
															<td><%=StringUtil.valueOf(facDemObj.getAccountType())%></td>
															<td><%=StringUtil.valueOf(facDemObj.getAccountStatus())%></td>
															<%if (facDemObj.getPhoneNumber().matches(".*\\d+.*")){ %>
															<td id="phoneno_'+<%=phoneCount %>+'" class="phone2" style="cursor: pointer";
															    value="<%=StringUtil.valueOf(facDemObj.getPhoneNumber()) %>";
															  	title="Click to Call"><%=StringUtil.valueOf(facDemObj.getPhoneNumber())%>&nbsp;
																<img src="<%=request.getContextPath()%>/images/call.png" width="18" height="18" alt="">
															</td>
															<td><%=StringUtil.valueOf(facDemObj.getFaxNumber())%></td>
															<% }  %>
														</tr>
														<% } %>
																								

													</tbody>
												</table>
											</div>
											<div class="padtlt20 fontsize12">
												Phone Comments: <b><%=phoneComments%></b>
											</div>
											<br />
											<div class="padtlt20 fontsize12">
												After Hours/ Alert Call Information: <b><%=alertInformation%></b>
											</div>
											<br />
											<a class="close-reveal-modal">×</a>
										</div>
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="large-6 columns medium-6">
								<div class="">
									<div class="left"></div>
									<div class="left ids-txt"></div>
									<div class="clearfix"></div>
								</div>
								<div class="mar-box row patient-ids-info">
									<div class="large-12 columns medium-12">
										<table border="0" cellpadding="0" cellspacing="0"
											width="100%;"
											style="background-color: #EDF5FA !important; border-top: 1px solid #a5abaf !important; border-left: 0px !important; border-right: 0px !important; border-bottom: 0px !important; border-collapse: collapse;">
											<tr style="border-bottom: 1px solid #a5abaf;">
												<td><b>Type</b></td>
												<td><b>Status</b></td>
												<td><b>Account Number</b></td>
												<td><b>Lab Account Number</b></td>
											</tr>
											<%
												for (FacilityDemographics facDemObj: accountList) {
											%>
											<tr style="background-color: #EDF5FA;">
												<%
													if (facilityId.equalsIgnoreCase(facDemObj.getLabAccountNumber())) {
												%>
												<td style="color: #4d4d4d"><strong><%=StringUtil.valueOf(facDemObj.getAccountType())%></strong></td>
												<td style="color: #4d4d4d"><strong><%=StringUtil.valueOf(facDemObj.getAccountStatus())%></strong></td>
												<td style="color: #4d4d4d"><strong><%=StringUtil.valueOf(facDemObj.getAccountNumber())%></strong></td>
												<td style="color: #4d4d4d"><strong><%=StringUtil.valueOf(facDemObj.getLabAccountNumber())%></strong></td>
												<%
													} else {
												%>
												<td><p><%=StringUtil.valueOf(facDemObj.getAccountType())%></p></td>
												<td><p><%=StringUtil.valueOf(facDemObj.getAccountStatus())%></p></td>
												<td><p><%=StringUtil.valueOf(facDemObj.getAccountNumber())%></p></td>
												<td><p><%=StringUtil.valueOf(facDemObj.getLabAccountNumber())%></p></td>
												<% } %>
											</tr>
											<% } %>
										</table>
									</div>
								</div>
							</div>
							<div class="large-6 columns medium-6">
								<div class="patient-heading-new">
									<div class="left">
										<img
											src="<%=request.getContextPath()%>/images/patient-ids.png"
											width="24" height="24" alt="">
									</div>
									<div class="left ids-txt">ORDERING & REPORTING
										INFORMATION</div>
									<div class="clearfix"></div>
								</div>
								<div class="mar-box patient-ids-info">
									<div class="large-6 columns medium-6">
										<p>Ordering System:</p>
										<%
											if ("FMC".equalsIgnoreCase(corporateAcronym) || "FMCWATER".equalsIgnoreCase(corporateAcronym)) {
										%>
										<p>eCUBE Server:</p>
										<% } %>
										<p>Patient Reporting:</p>
									</div>
									<div class="large-6 columns medium-6">
										<label><strong><%=orderingSystem%></strong></label>
										<%
											if ("FMC".equalsIgnoreCase(corporateAcronym) || "FMCWATER".equalsIgnoreCase(corporateAcronym)) {
										%>
										<label><strong><%=serverType%></strong></label>
										<% } %>
										<label><strong><%=patientReporting%></strong></label>
									</div>
									<div class="reveal-modal medium" id="kitModal" data-reveal="">
										<h4 class="fontBold">Kit Information</h4>
										<hr>
										<table class="table table-bordered bordered-table"
											width="100%;">
											<thead>
												<tr class="font-head">
													<th>Kits</th>
													<th>Kits Comments</th>
												</tr>
											</thead>
											<tbody>
												<tr class="font-head1 bor-none">
													<td><%=indicatorVal%></td>
													<td><%=kitComments%></td>
												</tr>
											</tbody>
										</table>
										<a class="close-reveal-modal">×</a>
									</div>
									<div class="reveal-modal medium" id="courierModal"
										data-reveal="">
										<h4 class="fontBold">Courier Information</h4>
										<hr>
										<table class="table table-bordered bordered-table"
											width="100%;">
											<thead>
												<tr class="font-head">
													<th>Supply Depot</th>
													<th>Delivery Schedulets</th>
													<th>SAP #</th>
												</tr>
											</thead>
											<tbody>
												<tr class="font-head1 bor-none">
													<td><%=supplyDepot%></td>
													<td><%=SupplyDeliverySch%></td>
													<td><%=sapNumber%></td>
												</tr>
											</tbody>
										</table>
										<a class="close-reveal-modal">×</a>
									</div>
									<div class="reveal-modal medium" id="supplyModal"
										data-reveal="">
										<h4 class="fontBold">Supply Information</h4>
										<hr>
										<table class="table table-bordered bordered-table"
											width="100%;">
											<thead>
												<tr class="font-head">
													<th>Supply Depot</th>
													<th>Delivery Schedulets</th>
													<th>SAP #</th>
												</tr>
											</thead>
											<tbody>
												<tr class="font-head1 bor-none">
													<td><%=supplyDepot%></td>
													<td><%=SupplyDeliverySch%></td>
													<td><%=sapNumber%></td>
												</tr>
											</tbody>
										</table>
										<a class="close-reveal-modal">×</a>
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="patient-heading-new">
									<div class="left">
										<img
											src="<%=request.getContextPath()%>/images/draw-schedule.png"
											width="24" height="24" alt="">
									</div>
									<div class="left ids-txt">BLOOD DRAW AND TRANSPORT</div>
									<div class="clearfix"></div>
								</div>
								<div class="mar-box patient-ids-info"></div>
								<div class="mar-box patient-ids-info">
									<div class="large-6 columns medium-6">
										<p>Draw Week:</p>
										<p>Draw Days:</p>
										<p>In Center Hemo Kits:</p>
										<p class="supplyDepot">Supply Depot:</p>
									</div>
									<div class="large-6 columns medium-6">
										<label><strong><%=drawWeek%></strong></label>
										<%
											if (CommonConstants.EMPTY_STRING.equalsIgnoreCase(drawDays)) {
										%>
										<label><strong>&nbsp;</strong></label>
										<%
											} else {
										%>
										<label><strong><%=drawDays%></strong></label>
										<%
											}
										%>
										<label> <a href="#" data-reveal-id="kitModal"> <strong>
													<%=kitIndicator%></strong>
										</a>
										</label> <label class="supplyDepot"> <a href="#"
											data-reveal-id="supplyModal"> <strong><%=facDemographics.getSupplyDepot()%></strong>
										</a>
										</label>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="large-6 columns medium-6"></div>
							<div class="large-6 columns medium-6"></div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<table style="border: none !important; margin-top: 20px;">
					<tr>
						<td><b>View:</b></td>
						<td><a href="#" class="myButton"
							onclick="goToSearchResults('Patient');">Patient</a></td>
						<td><a href="#" class="myButton"
							onclick="goToSearchResults('Staff');">Staff</a></td>
						<td><a href="#" class="myButton"
							onclick="goToSearchResults('Equipment');">Equipment</a></td>
					</tr>
				</table>
				<div class="row">
					<div class="h3-color"
						style="padding-left: 20px; font-size: 24px; top: 3px;">
						<strong>Recent Lab Activity</strong>&nbsp;&nbsp;&nbsp;<span
							id="tooltipIcon"></span>
					</div>
					<div id="patientTypeSubGrpId" style="position: relative; top: 3px;"></div>
					<div id="chartDiv" class="large-6 columns medium-6  small-4"></div>
					<br />
					<div id="noteDiv" class="large-6 columns medium-6 small-4">
						<b>REQUISITION LIST</b>
					</div>
					<div id="headingDiv1" class="columns medium-2 small-2"></div>
					<div id="tubeSumDiv"  class="columns medium-2 small-2" ></div>
					<div id="headingDiv2" class="large-6 columns medium-6 small-4"></div>
					<div id="reqDivTable" class="large-6 columns medium-6  small-4"></div>
				</div>
			</div>
		</div>
		<!-- Main Content Ends -->
		<script src="<%=request.getContextPath()%>/js/vendor/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/js/foundation.min.js"></script>
		<script>

	$(document).ready(function() {
		      
		<%if (null == facDemographics.getSupplyDepot() ) {%>
			$('.supplyDepot').hide();
		<%}       	
             if (null == facDemographics.getPatientCount() ) {%>
			$('.patcount').hide();
		<%}        	
        	 if (null == recvDate) {%>
			$('.recvDate').hide();
		<%}%>
			$('.searchSelect').on('change',	function() {
													var selectedVal = $(
															'.searchSelect option:selected')
															.val();
													$('.search-input-box')
															.hide();
													$('#' + selectedVal).show();

												});
								$("#chartDiv").click(function() {
													facGridPanel.getStore().removeAll();
													facGridPanel.getStore().sync();
													document.getElementById('headingDiv1').style.display = 'none';
													document.getElementById('headingDiv1').value = '';
													document.getElementById('headingDiv2').style.display = 'none';
													document.getElementById('headingDiv2').value = '';
												});
								Ext.Ajax.request({
											url : parent.ADURL,
											success: extToSession,
											params : {
												<portlet:namespace/>processorName : 'ActiveDirProcessor',
												<portlet:namespace/>processorAction : 'getUserPhone',
												<portlet:namespace/>userName : userName,
												<portlet:namespace/>ext : ext,
											}
										});

							         $(".phone").click(function() {							        	
							        	 if (document.getElementById("phoneExt").innerHTML == "Enter here") {
							        		 enterPhoneExt();
							        	  }else {
							        	 
													var isSamePhoneNumber =	<%=isSamePhoneNumber%>;
													if (!isSamePhoneNumber) {
														$(document)
																.foundation();
														$('#callInformation')
																.foundation(
																		'reveal',
																		'open');
													} else {
														var phoneNumber = <%=mainPhoneNumberVal.toString().replaceAll("[^0-9]+","")%>;
														var caller = document.getElementById("phoneExt").innerHTML;
														$("#hangup").css(
																'display',
																'inline-block');
														$(".phone").hide();
														Ext.Ajax
																.request({
																	url : parent.phoneURL,
																	params : {
																		<portlet:namespace/>processorName : 'PhoneProcessor',
																		<portlet:namespace/>processorAction : 'getPhone',
																		<portlet:namespace/>phoneNumber : phoneNumber,
																		<portlet:namespace/>caller : caller
																	}
																});
													}
							        	  }
												});
							         
							         $(document).on('click','td[id^="phoneno_"]',function(){  
									var phoneNumber = $(this).attr('value').replace(/\D/g,'');
													var caller = document.getElementById("phoneExt").innerHTML;
													$(document).foundation();
													$('#callInformation').foundation('reveal','close');
													$("#hangup").css('display','inline-block');
													$(".phone").hide();
													Ext.Ajax.request({
																url : parent.phoneURL,
																params : {
																	<portlet:namespace/>processorName : 'PhoneProcessor',
																	<portlet:namespace/>processorAction : 'getPhone',
																	<portlet:namespace/>phoneNumber : phoneNumber,
																	<portlet:namespace/>caller : caller
																}
															});
												});
								$("#hangup").click(function() {
													Ext.Ajax
															.request({
																url : parent.phoneURL,
																params : {
																	<portlet:namespace/>processorName : 'PhoneProcessor',
																	<portlet:namespace/>processorAction : 'hangUp'
																}
															});
													$("#hangup").hide();
													$("#ac").hide();
													$(".phone").show();
												});						      
								
								});
	     function extToSession(){  
	      if ("<%=request.getSession().getAttribute("phoneExt")%>" != "") 
	      {  
		      document.getElementById("phoneExt").innerHTML = "<%=request.getSession().getAttribute("phoneExt")%>";
		      document.getElementById("phoneExt").style = "color:#1E91D0;"
	      } else {
	    	  document.getElementById("phoneExt").innerHTML = "Enter here"
	      }
	    	  document.getElementById("phoneExt").style = "color:#1E91D0;" 
		   }
			function enterPhoneExt(){
			    var saveExt = function(btn) {
			     	 if (!Ext.getCmp("phoneExtNew").value ||(Ext.getCmp("phoneExtNew").getValue().length < 4 || Ext.getCmp("phoneExtNew").getValue().length > 4)){
			     		 	var msg = Ext.Msg.show({   
			     			 title:'Phone Extension',
			     			 msg:'Please enter a valid Phone Extension!',
			     			 buttons: Ext.Msg.OK,
			     			 fn: function(btn){
			     			 }
			     		 	});
			    	Ext.Msg.setXY([756,230],false);					    	
			    	 }
			     	if (Ext.getCmp("phoneExtNew").value && Ext.getCmp("phoneExtNew").getValue().length == 4 ){
			     		document.getElementById("phoneExt").innerHTML= Ext.getCmp("phoneExtNew").value;
						ext = Ext.getCmp("phoneExtNew").value;			     		
						Ext.Ajax.request({
							url : parent.ADURL,
							params : {
								<portlet:namespace/>processorName : 'ActiveDirProcessor',
								<portlet:namespace/>processorAction : 'getUserPhone',
								<portlet:namespace/>userName : userName,
								<portlet:namespace/>ext : ext
							},
						});			     		
			     		createWindow.close();
			     	}
			     }
			    var enterPhoneExt = new Ext.FormPanel({
			        labelWidth   : 75,
			        frame        : true,
			        width        : 200,
			        autoHeight   : true,        
			        defaultType  : 'textfield',
			        monitorValid : true,
			        animCollapse : true,
			        shadow		 : true,
			        defaults     : {
			        anchor : '-10'
			        },
			        items:[{
			            fieldLabel : 'Phone Ext',
			            id         : 'phoneExtNew',
			            allowBlank : false,
// 			            emptyText  : document.getElementById("phoneExt").innerHTML,
						emptyText  : "4 digit Ext",
			            enforceMaxLength:true,
			            minLength:4,
			            maxLength:4,
			            maskRe: /[z0-9]/
			        }],
			        buttons:[{
			            text : 'Save',
			            handler:saveExt
			        }]
			    });
			    createWindow = new Ext.Window({
			        title      : 'Enter Phone Extention',
			        width      :  220,
			        autoHeight : true,
			        modal      : true,
			        border     : false,
			        layout     : 'fit',
			        items      : enterPhoneExt
			    });
			    createWindow.setPosition(900, 150, true);
			    createWindow.show();
			    Ext.getCmp("phoneExtNew").focus(false, 200);
			    }			
			$(document).foundation();			
		</script>
	</div>
</body>
</html>