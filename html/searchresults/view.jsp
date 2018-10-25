<!DOCTYPE html>
<!-- 12/22/2015 - md - US1087   
    			  1) added resourceURL var="getStaffDetails" id="getStaffDetails" 
    			  2) added jsp condition for staff "orderStaffDetailPage"
    			  3) added jsp var staffURL = "getStaffDetails.toString()".
    			  4) added js  var staffStore and var staffRecords and var searchType.
    			  5) added staffRecords for ext model in onReady function.
    			  6) added function paginateStaffRes to set params for
    			  		processorName: 'StaffProcessor', processorAction: 'paginateStaffList'.
    			  7) added condition for staffResPage to set pageToBeLoaded searchresults/orderStaffFrame.jsp.
-->
<!-- 12/28/2015 - timc - US1223   
    			  1) added resourceURL var="getEquipmentDetails"  
    			  2) added actionURL  name="sendRequisitionRequestEquipment"  
    			  2) added jsp condition var searchType for Equipment 
    			  3) added jsp var requisitionActionURL = "sendRequisitionRequestEquipment".
    			  4) added js  var equipmentStore and var equipmentRecords.
    			  5) added equipmenRecords for ext model in onReady function.
    			  6) added function paginateResEquipment to set params for
    			     processorName: 'EquipmentProcessor', processorAction: 'paginateEquipmentList'.
    			  7) added condition for searchEquipmentPage to set pageToBeLoaded searchresults/searchEquipmentPage.jsp.
    			  8) added condition for equipmentDetailPage to set pageToBeLoaded searchresults/equipmentSummaryFrame.jsp.
-->
<%@page import="com.spectra.symfonie.common.constants.CommonConstants"%>
<%@page import="com.spectra.symfonie.common.util.StringUtil"%>
<%@include file="/html/init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<script type="text/javascript">
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<script type="text/javascript" src="'+contextPath+'/js/SearchResult.js?'+dateValue+'"><\/script>');
</script>
</head>
<portlet:resourceURL var="getPatDetails" id="getPatDetails" />
<portlet:resourceURL var="getStaffDetails" id="getStaffDetails" /> 
<portlet:actionURL name="sendResultsRequest" var="sendResultsRequest"></portlet:actionURL>
<portlet:actionURL name="sendRequisitionRequest" var="sendRequisitionRequest"></portlet:actionURL>
<portlet:actionURL name="sendFacDemographicsReq" var="sendFacDemographicsReq"></portlet:actionURL>  
<portlet:resourceURL var="requisitionDetails" id="requisitionDetails" /> 
<portlet:resourceURL var="orderSummaryDetails" id="orderSummaryDetails" />
<portlet:resourceURL var="dashboardDetails" id="dashboardDetails" />
<portlet:resourceURL var="getFacSearchDet" id="getFacSearchDet" />
<portlet:resourceURL var="getFacDemographics" id="getFacDemographics" />
<portlet:resourceURL var="getEquipmentDetails" id="getEquipmentDetails" /> 
<portlet:actionURL name="sendRequisitionRequestEquipment" var="sendRequisitionRequestEquipment"></portlet:actionURL>    
<portlet:resourceURL var="getPhone" id="getPhone" />
<portlet:resourceURL var="getUserPhone" id="getUserPhone" />
<script type="text/javascript">
 
<%
String pageToBeLoaded = "";
long facilityId = 0L;
long spectraMRN = 0L;
//////////////////////////////////////////////////////////////////////////
// if(null != request.getAttribute("pageToBeLoadedAttr")  ){
// 	pageToBeLoaded = request.getAttribute("pageToBeLoadedAttr").toString();
// }
//////////////////////////////////////////////////////////////////////////
String searchType = CommonConstants.EMPTY_STRING;
if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("searchType")))){
	searchType = StringUtil.valueOf(request.getSession().getAttribute("searchType"));
}

if (null != request.getAttribute("pageToBeLoadedAttr")) {
	if("Requisition".equalsIgnoreCase(searchType)){
		pageToBeLoaded = "reqDetailPage";
	}
	pageToBeLoaded = request.getAttribute("pageToBeLoadedAttr").toString();
}
if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getAttribute("facilityId")))){
	facilityId = Long.valueOf(StringUtil.valueOf(request.getAttribute("facilityId")));
}
if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getAttribute("spectraMRN")))){
	spectraMRN = Long.valueOf(StringUtil.valueOf(request.getAttribute("spectraMRN")));
}
if("orderDetailPage".equalsIgnoreCase(pageToBeLoaded)){
	if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("facilityId")))){
		facilityId = Long.valueOf(StringUtil.valueOf(request.getSession().getAttribute("facilityId")));
	}
	if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("spectraMRN")))){
		spectraMRN = Long.valueOf(StringUtil.valueOf(request.getSession().getAttribute("spectraMRN")));
	}
}
//added condition for staff
if("orderStaffDetailPage".equalsIgnoreCase(pageToBeLoaded)){  
	if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("facilityId")))){
		facilityId = Long.valueOf(StringUtil.valueOf(request.getSession().getAttribute("facilityId")));
	}
	if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("spectraMRN")))){
		spectraMRN = Long.valueOf(StringUtil.valueOf(request.getSession().getAttribute("spectraMRN")));
	}
}

String patientTypeSubGrp = CommonConstants.EMPTY_STRING;
if(!CommonConstants.EMPTY_STRING.equalsIgnoreCase(StringUtil.valueOf(request.getSession().getAttribute("patientTypeSubGrp")))){
	patientTypeSubGrp = StringUtil.valueOf(request.getSession().getAttribute("patientTypeSubGrp"));
}
%>
var url = "<%=getPatDetails.toString()%>";
var staffURL = "<%=getStaffDetails.toString()%>"; // staff
var requisitionUrl = "<%=requisitionDetails.toString()%>";
var orderSummaryURL = "<%=orderSummaryDetails.toString()%>";
var userName ="<%= user.getScreenName() %>";
var phoneURL = "<%=getPhone.toString()%>";
var ADURL = "<%=getUserPhone.toString()%>";
var facilityId = "<%=facilityId%>";
var spectraMRN = "<%=spectraMRN%>";
var patientStore;
var patientRecords;
var staffStore;  // staff
var staffRecords; // staff
var requisitionActionURL = "<%=sendRequisitionRequest.toString()%>";
var dashboardURL = "<%=dashboardDetails.toString()%>";
var facSearchResultsURL = "<%=getFacSearchDet.toString()%>";
var facSearchResultsStore;
var sendFacDemographicsReqURL = "<%=sendFacDemographicsReq.toString()%>";
var getFacDemographicsURL = "<%=getFacDemographics.toString()%>";
var facAccountsStore;
var patientTypeSubGrp = "<%=patientTypeSubGrp%>";
var pageToBeLoaded = "<%=pageToBeLoaded%>";
//added searchType var	to check Staff or patient
var searchType = "<%=searchType%>";  

//timc
if (searchType == 'Equipment') {
	var requisitionActionURL = "<%=sendRequisitionRequestEquipment.toString()%>";	
} else {
	var requisitionActionURL = "<%=sendRequisitionRequest.toString()%>";
}
var equipmentStore;
var equipmentRecords;

// Ext.apply(Ext.data.SortTypes, {
//     asPerson: function(fullName){
//         // expects an object with a first and last name property
//         return fullName.toUpperCase();
//     }    
// });
Ext.onReady(function () {
patientRecords = Ext.define('patientRecords', {
	    extend: 'Ext.data.Model',
	    fields: [{
	        name: 'fullName',
	        mapping: 'fullName',
        sortType:Ext.data.SortTypes.asUCString
// 	        sortType: 'asPerson'
	    },
	    {
	        name: 'gender',
	        mapping: 'gender'
	    },
	    {
	        name: 'dateOfBirth',
	        mapping: 'dateOfBirth',
	        sortType: 'asDate'
	    },
	    {
	        name: 'facilityName',
	        mapping: 'facilityName',
	        sortType:Ext.data.SortTypes.asUCString
	    },
	    {
	        name: 'facilityId',
	        mapping: 'facilityId'
	    },
	    {
	        name: 'spectraMRN',
	        mapping: 'spectraMRN'
	    },
	    {
	        name: 'modality',
	        mapping: 'modality'
	    },
	    {
	        name: 'patientHLABId',
	        mapping: 'patientHLABId'
	    },
	    {
	        name: 'facility_fk',
	        mapping: 'facility_fk'
	    }]
	});
patientStore = Ext.create('Ext.data.Store', {
    model: 'patientRecords',
    pageSize:25,
    remoteSort: true,
    simpleSortMode : true,
    //sorters:[{property:'fullName',direction:'asc'}],
    baseParams: {
    	processorName: 'PatientProcessor',
    	processorAction: 'paginatePatientList',
    	ajaxAction: 'true'
    },
    proxy: {
            type: 'ajax',
            url: url,
            method: 'POST',
            directionParam : 'sort.direction',
            sortproperty : 'sort.property',
            reader: {
                type: 'json',
                rootProperty: 'ListInfo',
                totalProperty: 'total'
            },
            extraParams:{
            	processorName: 'PatientProcessor',
            	processorAction: 'paginatePatientList',
            	ajaxAction: 'true'
    		}
        }
//     ,
//      listeners: { 
//        	 beforeload: function(store, operation, options){
//         	 	//Enable below log only while troubleshooting
//   			console.log(store.proxy.extraParams );
//         	 },
//         	 load: function (patientStore, records, successful, eOpts) {
//         		 var nd= new Date();
//  	  		 console.log( nd + ', loading results.. ' + records.length );  
//  	   		 console.log(patientStore.getProxy().getReader().rawData ); 		           		

//              }   
//      }
	});
equipmentRecords = Ext.define('equipmentRecords', {
    extend: 'Ext.data.Model',
    fields: [
    {
        name: 'fullName',
        mapping: 'fullName',   
        sortType:Ext.data.SortTypes.asUCString
    },
    {
        name: 'serialNumber',
        mapping: 'serialNumber'
    },
    {
        name: 'facilityId',
        mapping: 'facilityId'
    },
    {
        name: 'facilityName',
        mapping: 'facilityName',
       	sortType:Ext.data.SortTypes.asUCString
    },
    {
        name: 'lastDrawDate',
        mapping: 'lastDrawDate'
    },
    {
        name: 'spectraMRN',
        mapping: 'spectraMRN'
    }
    ]	});
 
equipmentStore = Ext.create('Ext.data.Store', {
model: 'equipmentRecords',
//     autoLoad:{start:0, limit:25},
    pageSize:25,
    remoteSort: true,
    simpleSortMode : false,
    sorters:[{property:'fullName',direction:'asc'}],  
baseParams: {
	processorName: 'EquipmentProcessor',
	processorAction: 'paginateEquipmentList',
	ajaxAction: 'true'
},
proxy: {
        type: 'ajax',
        url: '<%=getEquipmentDetails.toString()%>',
        method: 'POST',
        directionParam : 'sort.direction',
        sortproperty : 'sort.property',
        reader: {
            type: 'json',
            rootProperty: 'ListInfo',
            totalProperty: 'total'
        },
        extraParams:{
        	processorName: 'EquipmentProcessor',
        	processorAction: 'paginateEquipmentList',
        	ajaxAction: 'true'
		}   
}
// ,
// listeners: { 
// 	 beforeload: function(store, operation, options){
// 	 	//Enable below log only while troubleshooting
// 			console.log(store.proxy.extraParams );
// 	 },
// 	 load: function (equipmentStore, records, successful, eOpts) {
// 		 var nd= new Date();
//    	 console.log( nd + ', loading results.. ' + records.length );  
// 		 console.log( equipmentStore.getProxy().getReader().rawData ); 	
// // 		 var total = equipmentGridPanel.equipmentStore.reader.jsonData.total;
// // 		 console.log( "total is this " +  total);
//     }
// }
});

		staffRecords = Ext.define('staffRecords', {
			    extend: 'Ext.data.Model',
			    fields: [{
			        name: 'fullName',
			        mapping: 'fullName',
			        sortType:Ext.data.SortTypes.asUCString
			    },
			    {
			        name: 'gender',
			        mapping: 'gender'
			    },
			    {
			        name: 'dateOfBirth',
			        mapping: 'dateOfBirth'
			    },
			    {
			        name: 'facilityName',
			        mapping: 'facilityName'
			    },
			    {
			        name: 'facilityId',
			        mapping: 'facilityId'
			    },
			    {
			        name: 'spectraMRN',
			        mapping: 'spectraMRN'
			    },
			    {
			        name: 'last_draw',
			        mapping: 'last_draw'
			    },
			    {
			        name: 'patientHLABId',
			        mapping: 'patientHLABId'
			    },
			    {
			        name: 'facility_fk',
			        mapping: 'facility_fk'
			    }]
			});
		staffStore = Ext.create('Ext.data.Store', {
		    model: 'staffRecords',
		    pageSize:25,
		    remoteSort: true,
		    simpleSortMode : true,
		    sorters:[{property:'fullName',direction:'asc'}],  
		    baseParams: {
		    	processorName: 'StaffProcessor',
		    	processorAction: 'paginateStaffList',
		    	ajaxAction: 'true'	    	
		    },
		    proxy: {
		            type: 'ajax',
		            url: staffURL,
		            method: 'POST',
		            directionParam : 'sort.direction',
		            sortproperty : 'sort.property',
		            reader: {
		                type: 'json',
		                rootProperty: 'ListInfo',
		                totalProperty: 'total'
		            },
		            extraParams:{
		            	processorName: 'StaffProcessor',
		            	processorAction: 'paginateStaffList',
		            	ajaxAction: 'true'
		    		},
		    }
// 		    ,
// 		            listeners: { 
// 		           	 beforeload: function(store, operation, options){
// 		           	 	//Enable below log only while troubleshooting
//             				console.log(store.proxy.extraParams );
// 		           	 },
// 		           	 load: function (staffStore, records, successful, eOpts) {
// 		           		 var nd= new Date();
//  			    		 console.log( nd + ', loading results.. ' + records.length );  
//  			    		 console.log( staffStore.getProxy().getReader().rawData ); 		           		

// 		                }
		            
// 		            }
		        
			});	
	
Ext.define('facSearchResultsModel', {
    extend: 'Ext.data.Model',
    fields: [{
    	name: 'facilityName',
    	mapping: 'facilityName',
    	sortType:Ext.data.SortTypes.asUCString
    },{
    	name: 'corporationName',
    	mapping: 'corporationName',
    	sortType:Ext.data.SortTypes.asUCString
    },{
    	name: 'accountType',
    	mapping: 'accountType'
    },{
    	name: 'accountStatus',
    	mapping: 'accountStatus'
    },{
    	name: 'servicingLab',
    	mapping: 'servicingLab'
    },{
        name: 'hlabNum',
        mapping: 'hlabNum'
    }]
});
facSearchResultsStore = Ext.create('Ext.data.Store', {
    model: 'facSearchResultsModel',
    remoteSort: true,
    simpleSortMode : true,
    sorters:[{property:'facilityName',direction:'asc'}],
    baseParams: {
        processorName: 'FacilityProcessor',
        processorAction: 'PaginateFacSearchResults',
        ajaxAction: 'true'
    },
    proxy: {
            type: 'ajax',
            url: facSearchResultsURL,
            directionParam : 'sort.direction',
            sortproperty : 'sort.property',            
            extraParams: {
            	processorName: 'FacilityProcessor',
                processorAction: 'PaginateFacSearchResults',
                ajaxAction: 'true'
            },
            method: 'POST',
            reader: {
                type: 'json',
                rootProperty: 'ListInfo',
                totalProperty: 'total'
            }
        }
});	

Ext.define('facAccountsModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'accountType',
        mapping: 'accountType'
    },{
        name: 'accountStatus',
        mapping: 'accountStatus'
    },{
        name: 'accountNumber',
        mapping: 'accountNumber'
    },{
        name: 'labAccountNumber',
        mapping: 'labAccountNumber'
    }]
});
facAccountsStore = Ext.create('Ext.data.Store', {
    model: 'facAccountsModel',
    storeId: 'facAccountsStoreId',
    remoteSort: true,
    url: getFacDemographicsURL,
    proxy: {
        type: 'ajax',
        url: getFacDemographicsURL,
        reader: {
            type: 'json',
            rootProperty: 'ListInfo',
            totalProperty: 'total'
        }
    }
});
});
function paginateRes() {
	Ext.onReady(function () {
		patientStore.load({
	        params: {
	        	processorName: 'PatientProcessor',
	            processorAction: 'paginatePatientList'
	        }
	    });
	});
}
function paginateStaffRes() {       // function used by    staff from SearchResults.jsp 
	Ext.onReady(function () {
		staffStore.load({
	        params: {
	        	processorName: 'StaffProcessor',
            	processorAction: 'paginateStaffList'
	        }
	    });
	});
}

function paginateResEquipment() { 
	Ext.onReady(function () {
		equipmentStore.load({
	        params: {
	        	processorName:  'EquipmentProcessor',    
	            processorAction: 'paginateEquipmentList'   
	        }
	    });
	});
}

//Function to load the facility search results store.
function loadFacSearchResults() {
	facSearchResultsStore.load({
        params: {
        	processorName: 'FacilityProcessor',
            processorAction: 'PaginateFacSearchResults',
            ajaxAction: 'true'
        }
    });
}
</script>
<body>
<form method="post" name="searchResultsForm" id="searchResultsForm" action="<%=sendResultsRequest%>">
<input type="hidden" id="loadPage" name="<portlet:namespace/>loadPage" value="" />
<input type="hidden" id="facilityId" name="<portlet:namespace/>facilityId" value="" /> 
<input type="hidden" id="spectraMRN" name="<portlet:namespace/>spectraMRN" value="" />
<input type="hidden" id="processorName" name="<portlet:namespace/>processorName" value="" /> 
<input type="hidden" id="processorAction" name="<portlet:namespace/>processorAction" value="" />
<input type="hidden" id="isOrderSumPage" name="<portlet:namespace/>isOrderSumPage" value="" />
<input type="hidden" id="reqNumber" name="<portlet:namespace/>reqNumber" value="" />
<input type="hidden" id="hlabNum" name="<portlet:namespace/>hlabNum" value="" />
<input type="hidden" id="staffSummaryPage" name="<portlet:namespace/>staffSummaryPage" value="" />
<input type="hidden" id="equipmentSource" name="<portlet:namespace/>equipmentSource" value="" />
<input type="hidden" id="searchType" name="<portlet:namespace/>searchType" value="" />
<input type="hidden" id="facilityName" name="<portlet:namespace/>facilityName" value="" /> 


<input type="hidden" id="userName" name="<portlet:namespace/>userName" value="<%= user.getScreenName() %>" /> 

<%
request.getSession().setAttribute("userName" , user.getScreenName());
if("reqDetailPage".equalsIgnoreCase(pageToBeLoaded)) {%>
	<%if ("PATIENT".equalsIgnoreCase(patientTypeSubGrp) || "STAFF".equalsIgnoreCase(patientTypeSubGrp) || "STUDY".equalsIgnoreCase(patientTypeSubGrp)) {
	%>
	<iframe id="reqDetFrame" src="<%=request.getContextPath()%>/html/searchresults/reqDetailsFrame.jsp" width="100%" height="1100px" frameBorder="0" scrolling="no"></iframe>
	<%} else if ("ENVIRONMENTAL".equalsIgnoreCase(patientTypeSubGrp)) {
	%>
		<iframe id="reqDetFrame" src="<%=request.getContextPath()%>/html/searchresults/envReqDetailsFrame.jsp" width="100%" height="1100px" frameBorder="0" scrolling="no"></iframe>	
	<%} else {%>
		<script type="text/javascript">
		setTimeout(function() {showMsgBox("No Match Found", "The requisition number you entered did not match any records. Please try again!", "");}, 2000);
		</script>
	<%}%>
	
	<% }else if("reqDetailSourcePage".equalsIgnoreCase(pageToBeLoaded)) {%>
		<iframe id="reqDetFrame" src="<%=request.getContextPath()%>/html/searchresults/reqDetailsSrcFrame.jsp" width="100%" height="1100px" frameBorder="0" scrolling="no"></iframe>

<% } else if ("orderDetailPage".equalsIgnoreCase(pageToBeLoaded)) {%>
	<iframe src="<%=request.getContextPath()%>/html/searchresults/orderSummaryFrame.jsp" width="100%" height="1070px" frameBorder="0" scrolling="no"></iframe>
<%} else if ("searchResPage".equalsIgnoreCase(pageToBeLoaded)) {%>
	<iframe src="<%=request.getContextPath()%>/html/searchresults/searchResultsFrame.jsp" width="100%" height="750px" frameBorder="0" scrolling="no"></iframe>
<%} else if ("facSearchResultsPage".equalsIgnoreCase(pageToBeLoaded)) {%>
	<iframe src="<%=request.getContextPath()%>/html/searchresults/facSearchResultsFrame.jsp" width="100%" height="750px" frameBorder="0" scrolling="no"></iframe>
<%} else if ("facDemographicsPage".equalsIgnoreCase(pageToBeLoaded)) {%>
	<iframe id="facDemoFrame" src="<%=request.getContextPath()%>/html/searchresults/facDemographicsFrame.jsp" width="100%" height="750px" frameBorder="0" scrolling="no"></iframe>
<%} //added for staff
	else if ("staffResPage".equalsIgnoreCase(pageToBeLoaded)) {%>
	<iframe src="<%=request.getContextPath()%>/html/searchresults/staffResultsFrame.jsp" width="100%" height="750px" frameBorder="0" scrolling="no">
	</iframe>

<% } else if ("equipmentDetailPage".equalsIgnoreCase(pageToBeLoaded)) {%>
	<iframe src="<%=request.getContextPath()%>/html/searchresults/equipmentSummaryFrame.jsp" width="100%" height="1070px" frameBorder="0" scrolling="no"></iframe>
<%} else if ("searchEquipmentPage".equalsIgnoreCase(pageToBeLoaded)) {%>
	<iframe src="<%=request.getContextPath()%>/html/searchresults/searchEquipmentPage.jsp" width="100%" height="750px" frameBorder="0" scrolling="no"></iframe>
	
<%} else if ("orderStaffDetailPage".equalsIgnoreCase(pageToBeLoaded)) {%>
	<iframe src="<%=request.getContextPath()%>/html/searchresults/orderStaffFrame.jsp" width="100%" height="750px" frameBorder="0" scrolling="no">
	</iframe>	
<%} else {%>
	<iframe src="<%=request.getContextPath()%>/html/searchresults/dashboard.jsp" width="100%" height="750px" frameBorder="0" scrolling="no"></iframe>
<%}%>
</form>
</body>
</html>