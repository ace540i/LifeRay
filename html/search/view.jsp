<!DOCTYPE html>
<!-- 12/22/2015 - mdarretta - US1211   
    			  1) added option for Staff 
    			  2) added divs (patientdiv,staffdiv) 
    			  3) input box(staffname) for Staff Name 
-->
 
<!-- 12/28/2015 - timc - US1223   
    			  1) added option for Equipment 
    			  2) added divs (patientdiv,eqpDiv) 
    			  3) input box(eqpNumber) for Equipment Name 
    			  x) Added function searchEquipment to submit Equipment search.**
    			  
-->
<%@page import="com.spectra.symfonielabs.domainobject.Search"%>
<%@page import="com.spectra.symfonie.common.util.StringUtil"%>
<%@page import="com.spectra.symfonie.common.constants.CommonConstants"%>
<%@page import="java.util.ArrayList"%>
<%@include file="/html/init.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<script type="text/javascript">
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<script type="text/javascript" src="'+contextPath+'/js/SymfonieSearch.js?'+dateValue+'"><\/script>');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/css/ext5ExtendedStyles.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/messageBox.css?'+dateValue+'">');
</script>
</head>
<portlet:actionURL name="sendRequest" var="sendRequest"></portlet:actionURL>
<portlet:resourceURL var="getFacilitiesNames" id="getFacilitiesNames" />
<portlet:resourceURL var="getCorporationNames" id="getCorporationNames" />
<%
Search prvSelCriteria = new Search();
String selSearchType = CommonConstants.EMPTY_STRING;
String selSearchVal = CommonConstants.EMPTY_STRING;
String selSeachFac = CommonConstants.EMPTY_STRING;
String selSearchCorpAcronym = CommonConstants.EMPTY_STRING; 
String selCorpName = CommonConstants.EMPTY_STRING;
long selSeachFacId = 0l;
String selSearchCorp = CommonConstants.EMPTY_STRING;
long selSearchCorpId = 0L;

String selSearchDOB = CommonConstants.EMPTY_STRING;     	//timc

if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute(CommonConstants.SEARCH_PARAMETERS)))){
	prvSelCriteria = (Search) request.getSession().getAttribute(CommonConstants.SEARCH_PARAMETERS);
	selSearchType = prvSelCriteria.getSearchType();
	selSearchVal = prvSelCriteria.getSearchValue();
	selSeachFac = prvSelCriteria.getFacilityName();
	selSeachFacId = prvSelCriteria.getFacilityId();
	selSearchCorp = prvSelCriteria.getCorporationName();
	selSearchCorpId = prvSelCriteria.getCorporationId();
	selSearchCorpAcronym = prvSelCriteria.getAcronym();
	selCorpName = prvSelCriteria.getSelCorpName();
// 	System.out.println("@@ prvSelCriteria.getAcronym() :  "+prvSelCriteria.getAcronym().toString());
	selSearchDOB = prvSelCriteria.getpatientDOB();             //timc	
}
//timc
//  System.out.println("@@ view.jsp <prvSelCriteria> (Search) :  "+prvSelCriteria.toString());
//System.out.println("@@ view.jsp <selSearchDOB> (Search) :  "+selSearchDOB);
//timc
%>
<script type="text/javascript">
var selSearchType = '<%=selSearchType%>';
var selSearchVal = '<%=selSearchVal%>';
var selSeachFac = '<%=selSeachFac%>';
var selSeachFacId = '<%=selSeachFacId%>';
var contextPath = "<%= request.getContextPath()%>";
var url = "<%= getFacilitiesNames.toString() %>";
var corporationURL = "<%=getCorporationNames.toString()%>";
var selSearchCorp = "<%=selSearchCorp%>";
var selSearchCorpId = "<%=selSearchCorpId%>";
var selSearchCorpAcronym = "<%=selSearchCorpAcronym%>";
var selCorpName = "<%=selCorpName%>";

//timc
var selSearchDOB = "<%=selSearchDOB%>";  		 
//timc 

function getfacilities(value){
	 Ext.Ajax.request({
         url: "<%= getFacilitiesNames.toString() %>",
         success: populateFacilities,
         params: {
             <portlet:namespace/>processorName: 'FacilityProcessor',
             <portlet:namespace/>processorAction: 'getFacilities',
             <portlet:namespace/>getFacName: value
         }
     });
}
//Function to get the corporation names based on the entered search criteria.
function getCorporations(value){
	 Ext.Ajax.request({
        url: "<%=getCorporationNames.toString()%>",
        success: populateCorporations,
        params: {
        	<portlet:namespace/>processorName: 'FacilityProcessor',
        	<portlet:namespace/>processorAction: 'getCorporations',
        	<portlet:namespace/>corporationName: value
        }
    });
}
//Function to search requisition.
function searchRequisition(){
	if (!validateSearch()) {
		return false;
	}
	document.getElementById("processorName").value = 'OrderProcessor';
	document.getElementById("processorAction").value = 'searchRequisition';
	document.getElementById("searchType").value = document.getElementById("selectFieldnew").value;
	document.getElementById("searchForm").submit();
}

//Function to search requisition.
function searchRequisitionSrc(){
	if (!validateSearch()) {
		return false;
	}
	document.getElementById("processorName").value = 'OrderSourceProcessor';
	document.getElementById("processorAction").value = 'searchRequisition';
	document.getElementById("searchType").value = document.getElementById("selectFieldnew").value;
	document.getElementById("searchForm").submit();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////
//Function to search Equipment.

function searchEquipment() {

	if (!validateSearch()) {
		return false;
	}
	var equipmentName = '';
	var facilityId = '';
	var facilityName = '';
	if(null != document.getElementById("eqpNumber")){
	equipmentName = document.getElementById("eqpNumber").value;
	}
	var comboValue = Ext.getCmp('facilitySearchBoxId').getValue();
	if (null == comboValue || "" == comboValue || undefined == comboValue || 'undefined' == comboValue) {
	facilityIdVal = 0;
	facilityNameVal = "";
	} else {
	if(null != Ext.getCmp('facilitySearchBoxId') && null != Ext.getCmp('facilitySearchBoxId').getSelection()){
		if (undefined == facilityIdVal || 'undefined' == facilityIdVal) {
		facilityId = Ext.getCmp('facilitySearchBoxId').getSelection().get('facilityId');
		} else {
		facilityId = facilityIdVal;
		}
		if (undefined == facilityNameVal || 'undefined' == facilityNameVal) {
		facilityName = Ext.getCmp('facilitySearchBoxId').getSelection().get('selFacName');
		} else {
		facilityName = facilityNameVal;
		}
	}
	}
	document.getElementById("processorName").value = 'EquipmentProcessor';       			// <================== PROCESSOR-NAME 
	document.getElementById("processorAction").value = 'getSearchResult';   // <========= Hmmmmmmmmmmmmm ??
	document.getElementById("facilityId").value = facilityId;
	document.getElementById("facilityName").value = facilityName;
	document.getElementById("searchType").value = document.getElementById("selectFieldnew").value;
	document.getElementById("searchValue").value = document.getElementById("eqpNumber").value;
// 	document.getElementById("equipmentSource").value = document.getElementById("equipmentSource").value;
	document.getElementById("searchForm").submit();
}

</script>
<style type="text/css">
.aui #facilitySearchBoxDiv input[type="text"] {
	border: 0px none !important;
    margin-bottom: 0px;
   	width: 100% !important;
    height: 29px;
}

 #facilitySearchBoxDiv {
 	width:30% !important;
 }
 .aui #corpSearchBoxDiv input[type="text"] {
	border: 0px none !important;
    margin-bottom: 0px;
   	width: 100% !important;
    height: 29px;
}

#corpSearchBoxDiv {
 	width:25% !important;
 }
 
 .comboCls li{
 	font-family: Arial, Source Sans Pro Regular !important;
 	font-size: 12px !important;
 }
 #selectFieldnew {
 	font-family: Arial, Source Sans Pro Regular !important;
 	font-size: 12px !important;
 }
 .custom-search-control{
 	width: 25% !important;
    height: 21px;
    margin-left: 1px;
    margin-bottom: 0px;
    display: table-cell;
    border-right: 0 !important;
 }
 .custom-search-btn{
    height: 31px;
    display: table-cell;
    position: relative;
    left: -5px;
    border: 1px solid #DDD !important;
    border-left: 0 !important;
 }
</style>
<body onload="loadScreen();">
<form method="post" name="searchForm" id="searchForm" action="<%=sendRequest%>">
<input type="hidden" id="processorName" name="<portlet:namespace/>processorName" value="" /> 
<input type="hidden" id="processorAction" name="<portlet:namespace/>processorAction" value="" />
<input type="hidden" id="facilityId" name="<portlet:namespace/>facilityId" value="" />
<input type="hidden" id="facilityName" name="<portlet:namespace/>facilityName" value="" />
<input type="hidden" id="searchType" name="<portlet:namespace/>searchType" value="" />
<input type="hidden" id="searchValue" name="searchValue" value="" />
<input type="hidden" id="corporationId" name="corporationId" value="" />
<input type="hidden" id="corporationName" name="corporationName" value="" />

<div>
	<div style="float:left">
	     <div class="input-group-btn" style="height: 31px;"> 
         	<select id="selectFieldnew" style="background-color:#f3f3f3 !important; color:#6C6C6C !important; border:1px solid #cccccc !important; padding: 7px 0px 7px 0px;" onchange="showHideDiv(this.value);">
                  <!-- <option value="Select">Select</option> -->
                  <option value="Patient">Patient</option>
                  <option value="Requisition">Requisition</option>
                  <option value="Facility">Facility</option>
                  <option value="Staff">Staff</option>
				<option value="Equipment">Equipment</option>
              </select>
          </div> 
     </div>
     <div style="width:70%; float:left;">  <!--  background:#f3f3f3;#E8F5CA;#eeeeee;"> -->
        <div id="patDiv" class="nad">
           	<span class="pull-left col-lg-8 col-md-8" style="padding:0px;" id="facilitySearchBoxDiv"></span>
   			<!-- added patientDiv to support staff and equipment search box. used in ShowHide function -->  
	        <div id="patientDiv" class="nad">
	      		<input type="text" class="form-control custom-search-control" placeholder="Patient Name" onkeydown="Javascript: if (event.keyCode==13) searchPatDet();"  style="width:25% !important;height: 21px;margin-left: 2px;margin-bottom: 0px; " id="patientName" name="<portlet:namespace/>patientName">
	          		<span class="input-group-btn">
		        	<button class="btn btn-default custom-search-btn" type="button" style="background: url(<%=request.getContextPath() %>/images/close1.png) no-repeat center;" onclick="clearSearchValues();"></button>
		      		</span>
	               	<img src="<%=request.getContextPath() %>/images/search-arrow.jpg" width="" onclick="searchPatDet();"  style="height: 31px;cursor: pointer;"/>
	      		<br>   			  
					<span style="padding-left:30%; width=25%; height: 31px;"></span>
           			<input  id="patientDOB" type="text" class="form-control custom-search-control" placeholder="DOB (mmddyyyy)"  maxlength="8"   
           			  onkeydown="Javascript: if (event.keyCode==13) searchPatDet();"
           		        onclick="Javascript: document.getElementById('dobButt').disabled = false;"
           				onkeypress="return onlyNumericNoSpace(event);"
           			  style="color:darkgray; width:25%;height: 21px; !important;margin-left:2px;margin-bottom: 0px; " name="<portlet:namespace/>patientDOB">
           			  <button  id="dobButt" class="btn btn-default custom-search-btn" type="button" style="background: url(<%=request.getContextPath() %>/images/close1.png) no-repeat center;" onclick="clearSearchValuesDOB();" ></button>
			</div>
        </div>
        <div id="staffDiv" class="nad" style="display: none;">
           	 <input type="text"   class="form-control custom-search-control"  placeholder="Staff Name" onkeydown="Javascript: if (event.keyCode==13) searchStaffDet();"  style="width:25% !important;height: 21px;margin-left: 1px; margin-bottom: 0px; " id="staffName" name="<portlet:namespace/>staffName">
           	 <span class="input-group-btn">
       		 <button class="btn btn-default custom-search-btn" type="button" style="background: url(<%=request.getContextPath() %>/images/close1.png) no-repeat center;" onclick="clearSearchValues();"></button>
      		 </span>
             <img src="<%=request.getContextPath() %>/images/search-arrow.jpg" width="" onclick="searchStaffDet();"  style="height: 31px;cursor: pointer;"/>
        </div>
        <div id="reqDiv" class="nad" style="display: none;">
           	 <input type="text" class=" search-icon form-control " placeholder="Requisition#" maxlength="7" onkeydown="Javascript: if (event.keyCode==13) searchRequisition();" onkeypress="return onlyAlphaNumericNoSpace(event);" style="background:url(<%=request.getContextPath() %>/images/search1.png) no-repeat right; height: 19px; margin-bottom: 0px" id="reqNumber" name="<portlet:namespace/>reqNumber">
             <img src="<%=request.getContextPath() %>/images/search-arrow.jpg" width="" onclick="searchRequisition();"  style="height: 31px;cursor: pointer;"/>
        </div>
        <div id="facDiv" class="nad" style="display: none;">
           	<span class="pull-left col-lg-8 col-md-8" style="padding:0px;" id="corpSearchBoxDiv"></span>
           	<input type="text" class="form-control custom-search-control" placeholder="Facility Name" onkeydown="Javascript: if (event.keyCode==13) searchFacilityDetails();"  style="width:25% !important;height: 21px;margin-left: 1px;margin-bottom: 0px; " id="facName" name="<portlet:namespace/>facilityName">
           	<span class="input-group-btn">
        	<button class="btn btn-default custom-search-btn" type="button" style="background: url(<%=request.getContextPath() %>/images/close1.png) no-repeat center;" onclick="clearSearchValues();"></button>
      		</span>
            <img src="<%=request.getContextPath() %>/images/search-arrow.jpg" width="" onclick="searchFacilityDetails();"  style="height: 31px;cursor: pointer;"/>
        </div>
           
	    <div id="eqpDiv" class="nad" style="display: none;">
          	<input type="text" class="form-control custom-search-control" placeholder="Equipment Name" maxlength="" 
          	       onkeydown="Javascript: if (event.keyCode==13) searchEquipment();" 
          	       style="width:25% !important;height: 21px;margin-left: 1px;margin-bottom: 0px; " id="eqpNumber" name="<portlet:namespace/>eqpNumber">
          	<span class="input-group-btn">
       		<button class="btn btn-default custom-search-btn" type="button" style="background: url(<%=request.getContextPath() %>/images/close1.png) no-repeat center;" onclick="clearSearchValues();"></button>
     		</span>  
            <img src="<%=request.getContextPath() %>/images/search-arrow.jpg" width=""  onclick="searchEquipment();"  style="height: 31px;cursor: pointer;"/>           
	    </div>
     </div>
</div>

</form>
	<ul class="navig">  
		  <li class="button-dropdown">
		    <a href="javascript:void(0)" class="dropdown-toggle">
		      <span title="External Links" style="padding-top:20px;"><img src="<%=request.getContextPath() %>/images/menu-img.png" width="30" height="30" alt=""></span>
		    </a>
		    <ul class="dropdown-menu">
		      
				<li>
					<a href="https://korus.spectra-labs.com" target="_blank" style="text-decoration: none;"> 
					<img src="<%=request.getContextPath() %>/images/menu1.png" width="18" height="16" alt=""/> 
					<span class="rlsCls">KORUS</span>
					</a>
				</li>
				<li>
					<a href="http://njvwtmast01p/asrweb/controllerServlet?processorName=IndexProcessor&processorAction=index&symfonieUserName=<%= user.getScreenName() %>"
					        " target="_blank" style="text-decoration: none;"> 
					<img src="<%=request.getContextPath() %>/images/menu1.png" width="18" height="16" alt=""/> 
					<span class="rlsCls">State Reporting</span>
					</a>
				</li>
				<li>
					<a href="https://athome.spectra-labs.com" target="_blank" style="text-decoration: none;"> 
						<img src="<%=request.getContextPath() %>/images/menu1.png" width="18" height="16" alt=""/>
						<span class="rlsCls">Spectra@home</span>
					</a>
				</li>
				<li><a href="http://www.spectra-labs.com" target="_blank" style="text-decoration: none;"> <img src="<%=request.getContextPath() %>/images/menu1.png" width="18" height="16" alt=""/> <span class="rlsCls">OPTIKA</span> </a></li>
				<li><a href="http://www.spectra-labs.com" target="_blank" style="text-decoration: none;"> <img src="<%=request.getContextPath() %>/images/menu1.png" width="18" height="16" alt=""/> <span class="rlsCls">PLAC</span> </a></li>
			
		    </ul>
		  </li>
		  
		</ul>
		
 <br><br>	
		
		<script src="<%= request.getContextPath()%>/js/jqueryPlugin.js"></script>
		<script>
		jQuery(document).ready(function (e) {
		    function t(t) {
		        e(t).bind("click", function (t) {
		            t.preventDefault();
		            e(this).parent().fadeOut()
		        })
		    }
		    e(".dropdown-toggle").click(function () {
		        var t = e(this).parents(".button-dropdown").children(".dropdown-menu").is(":hidden");
		        e(".button-dropdown .dropdown-menu").hide();
		        e(".button-dropdown .dropdown-toggle").removeClass("active");
		        if (t) {
		            e(this).parents(".button-dropdown").children(".dropdown-menu").toggle().parents(".button-dropdown").children(".dropdown-toggle").addClass("active")
		        }
		    });
		    e(document).bind("click", function (t) {
		        var n = e(t.target);
		        if (!n.parents().hasClass("button-dropdown")) e(".button-dropdown .dropdown-menu").hide();
		    });
		    e(document).bind("click", function (t) {
		        var n = e(t.target);
		        if (!n.parents().hasClass("button-dropdown")) e(".button-dropdown .dropdown-toggle").removeClass("active");
		    })
		});
		</script>	
</body>
</html>

