var orderDetailsGrid;
var orderTestDetStore;
var start = 0;
var rowsPerPage = 25;
var records;
var currentPage = 1;
var commentsVal = "";
var controlReasonVal = "";
var orderTestSelModel;
var microOrderTestSelModel;
var selectedRowIndex;
var genLabsTestCount = 0;
var microTestCount = 0;
var shValue = '';
var resValue = '';
var testSH = '';

// Function to load the details based on requisition number.

//Store for loading patient list screen.
function showPagingBar() {
	 Ext.QuickTips.init();
    // Data model for tube summary list .
	  var gridMaxwidth =  Ext.get("content-wrapper").getWidth()*0.985;
    Ext.define('orderDetailsModel', {
        extend: 'Ext.data.Model',
        fields: [{
            name: 'test'
        },
        {
        	name: 'testCount'
        },
        {
        	name: 'order_type'
        }]
    });
        orderTestDetStore = Ext.create('Ext.data.Store', {
            model: 'orderDetailsModel',
            storeId: 'orderTestDetStore',
            url: url,
            baseParams: {
                processorName: 'OrderSourceProcessor',
                processorAction: 'getOrderTestDetails',
                start: start,
                limit: rowsPerPage,
                ajaxAction: 'true'
            },
            proxy: {
                type: 'ajax',
                url: url,
                extraParams: {
                    processorName: 'OrderSourceProcessor',
                    processorAction: 'getOrderTestDetails',
                },
                reader: {
                    type: 'json',
                    rootProperty: 'ListInfo',
                    totalProperty: 'total'
                }
             }
            ,
            listeners: {
            	load: function(orderTestDetStore, records, succ, eOpts) {
            	 	console.log('orderTestDetStore: ' + succ);
            	 	console.log(orderTestDetStore.getProxy().getReader().rawData);
            	}
            
            }
           
        });
    	//Grid model for order test details.
        orderDetailsGrid = Ext.create('Ext.grid.Panel', {		
            id: 'orderDetailsModel',
            width: gridMaxwidth,
            store: orderTestDetStore,
            disableSelection: false,
            frame: false,          
            border: false,
        	stripeRows: true,
        	columnLines: true,
        	margin:'0 0 30 0',
        	columns: [{
                header: '<B>Test</B>',
                width: gridMaxwidth * 0.999,
                sortable: true,
                dataIndex: 'test',
                style: 'font-weight: bold !important;',
                menuDisabled: true
//                renderer: testRenderer
            }],
            viewConfig: {
            	markDirty: false,
	            forceFit: true,
	            enableTextSelection: true ,
	            emptyText: '<center><b>No Results to Display</b></center>',
	            deferEmptyText: false
	        },
	        selModel: orderTestSelModel
        });
      
       var orderDetailTitle = "ORDER DETAIL";       
        var orderDetailPanel = Ext.create('Ext.panel.Panel', {
            title: orderDetailTitle,
            id: 'orderDetailPanelId',
            width: gridMaxwidth,
            // height: 415,
            collapsible: true,
            renderTo: 'orderDetailsDiv',
            //autoScroll:true,
            items: [orderDetailsGrid],
            listeners: {
  		      collapse: function(){
  		    		parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight - orderDetailsGrid.getHeight() ) + 'px';
  	      		},
  		      expand:  function(){
  		    	  parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight + orderDetailsGrid.getHeight()) + 'px';
  		      	}
            }
      });        

        loadOrderDetails();

}
// Funtion to load order details.
function loadOrderDetails() {	
//	reportGroupVal = reportGroupCombo.getValue();
	//Reset the frame height on change of selection so that the grid loads again if there are less values.
	parent.document.getElementById('reqDetFrame').style.height = "";
	orderTestDetStore.load({
        params: {
            processorName: 'OrderSourceProcessor',
            processorAction: 'getOrderTestDetails',
            requisitionNo : requisitionNo,
//            reportGroupVal: reportGroupVal,
            ajaxAction: 'true'
        },
        callback: function (response, options, success) {
        	if(success){
        		  var newHeight;
		    	  var count = orderTestDetStore.getCount();
//		    	  var orderType = orderTestDetStore.getOrderType();
//		    	  alert(orderType);
		    	  genLabsTestCount = count;
		    	  if (count == 0 || count == 1) {
		    		  newHeight =  180;
		    	  } else if(count < 5){
		    		  newHeight =  count * 50 + 139;
		    	  } else if (count < 10) {
		    		  newHeight = count * 60 + 100;
		    	  } else if(count < 20){
		    		  newHeight = count * 60 + 500;
		    	  } else {
		    		  newHeight = count * 39 + 350;		    		  
		    	  }
        		parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight + newHeight) + 'px';
        		
				if ( (count == 0) && (microTestCount > 0) ) {	        			
					document.getElementById("orderDetailsDiv").style.display = "none";					
				} else {
					document.getElementById("orderDetailsDiv").style.display = "block";
				} 

				
        	}
        }              
    });
//	loadMicroOrderDetails();
}
//Function which navigates to order summary page on click of Patient name.
function goToOrderSummaryPage(orderType) {
//	if ("PATIENT" == patientTypeSubGrp || "STUDY" == patientTypeSubGrp) {
		if ("ES" == orderType || "NE" == orderType) {
		parent.document.searchResultsForm.loadPage.value = "orderDetailPage";
		parent.document.searchResultsForm.facilityId.value = facilityIdVal;
		parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
		parent.document.searchResultsForm.processorName.value = 'OrderSourceProcessor';
		parent.document.searchResultsForm.processorAction.value = 'getOrderSource';
		parent.document.searchResultsForm.submit();
	} else if ("SF" == orderType ) {
		parent.document.searchResultsForm.loadPage.value = "orderStaffDetailPage";
		parent.document.searchResultsForm.facilityId.value = facilityIdVal;
		parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
		parent.document.searchResultsForm.processorName.value = 'OrderStaffProcessor';
		parent.document.searchResultsForm.processorAction.value = 'getOrderSum';
		parent.document.searchResultsForm.submit();
	} else if ("EN" == orderType || "EM" == orderType) {
		parent.document.searchResultsForm.loadPage.value = "equipmentDetailPage";
		parent.document.searchResultsForm.facilityId.value = facilityIdVal;
		parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
		parent.document.searchResultsForm.processorName.value = 'EquipmentProcessor';
		parent.document.searchResultsForm.processorAction.value = 'getOrderSum';
		parent.document.searchResultsForm.submit();
	}
}
//Function which navigates to facility demographics screen on click of facility name.
function goToFacDemographicsPage() {
	parent.document.searchResultsForm.action = parent.sendFacDemographicsReqURL;
	parent.document.searchResultsForm.loadPage.value = "facDemographicsPage";
	parent.document.searchResultsForm.hlabNum.value =  primaryFacNumber;
	parent.document.searchResultsForm.processorName.value = 'FacilityProcessor';
	parent.document.searchResultsForm.processorAction.value = 'showFacilityDemographics';
	parent.document.searchResultsForm.userName.value = parent.userName;  
	parent.document.searchResultsForm.submit();
}
//Function to render the received dates.
function receivedDatesRenderer(value, metadata, record, rowIndex, colIndex, store) {
	var receivedDatesStr = "";
	var timeZone ="";
	if (null != value && "" != value && 
			(undefined != value || 'undefined' != value)) {	
		if (record.get('labFk')  == 4 || record.get('labFk') == 6 ) {
			timeZone = 'PT';
	}	else {
			timeZone ='ET'
	}
		receivedDatesStr = value;
		receivedDatesStr = receivedDatesStr + ' ' + timeZone
	} else {
		receivedDatesStr = "Not Received";
	}
	return receivedDatesStr;
}
