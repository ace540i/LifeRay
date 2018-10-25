var orderSummarStore;
var orderSourceStore;
var orderSummaryColMod;
var orderSourceColMod
var orderSummaryGrid;
var orderSourceGrid;
var start = 0;
var rowsPerPage = 25;
var dateRange;
var blankImagePath = "/Resources/ext-5.1.0/packages/ext-theme-classic/resources/images/tree/s.gif";
var loadHtml = '<table width="80px" height="10px" border="0" cellpadding="0" cellspacing="0">  <tr><td><img src="'+blankImagePath+'" style="padding-right:2px;"/></td><td style = "padding-top:3px;">Loading...</td></tr>';
//Function to show the order summary grid.
function showSummaryStaffGrid() {
	document.getElementById('sourceFilter').style.display = 'none';
	var gridMaxwidth =  Ext.get("content-wrapper").getWidth() *0.983;
	Ext.onReady(function() {
		Ext.define('orderSummaryModel', {
		    extend: 'Ext.data.Model',
		    fields: [{
		        name: 'drawDate',
		        mapping: 'drawDate'
		    },{
		        name: 'requisitionNo',
		        mapping: 'requisitionNo'
		    },{
		        name: 'facility',
		        mapping: 'facility'
		    },{
		        name: 'testsCount',
		        mapping: 'testsCount'
		    },{
		        name: 'frequency',
		        mapping: 'frequency'
		    },{
		        name: 'status',
		        mapping: 'status'
		    },{
		        name: 'abnormalFlag',
		        mapping: 'abnormalFlag'
		    },{
		        name: 'cancelledTestIndicator',
		        mapping: 'cancelledTestIndicator'
		    }]
		});
		orderSummarStore = Ext.create('Ext.data.Store', {
		    model: 'orderSummaryModel',
		    baseParams: {
                processorName: 'OrderStaffProcessor',
                processorAction: 'paginateOrderSummary',
                start: start,
                limit: rowsPerPage,
                facilityId: facilityIdValue,
                spectraMRN: spectraMRNValue,
                ajaxAction: 'true'
            },
		    proxy: {
		            type: 'ajax',
		            url: url,
		            extraParams: {
	                    processorName: 'OrderStaffProcessor',
	                    processorAction: 'paginateOrderSummary',
	                    facilityId: facilityIdValue,
	                    spectraMRN: spectraMRNValue
	                },
		            method: 'POST',
		            reader: {
		                type: 'json',
		                rootProperty: 'ListInfo',
		                totalProperty: 'total'
		            }
		        }
		});
		
		//var colWidthArray = [0.20, 0.20, 0.30, 0.20, 0.20, 0.20];
		var colWidthArray = [0.20, 0.20, 0.30, 0.20, 0.20, 0.20];
		orderSummaryColMod = [{
			header: '<b>Draw Date</b>',
            dataIndex: 'drawDate',
            width: (gridMaxwidth * colWidthArray[0]),
            sortable: true,
            menuDisabled: true
		}, {
			header: '<b>Requisition#</b>',
            dataIndex: 'requisitionNo',
            width: (gridMaxwidth * colWidthArray[1]),
            sortable: true,
            renderer: linkRenderer,
            menuDisabled: true
		}, {
			header: '<b>Facility</b>',
            dataIndex: 'facility',
            width: (gridMaxwidth * colWidthArray[2]),
            sortable: true,
            menuDisabled: true,
            hidden: true
		}, {
			header: '<b># Of Tests</b>',
            dataIndex: 'testsCount',
            width: (gridMaxwidth * colWidthArray[3]),
            sortable: true,
            menuDisabled: true
		}, {
			header: '<b>Frequency</b>',
            dataIndex: 'frequency',
            width: (gridMaxwidth * colWidthArray[4]),
            sortable: true,
            menuDisabled: true
		}, {
			header: '<b>Status</b>',
            dataIndex: 'status',
            width: (gridMaxwidth * colWidthArray[5]),
            sortable: true,
            menuDisabled: true
		}, {
			header: '<b>Abnormal Flag</b>',
            dataIndex: 'abnormalFlag',
            hidden: true
		}];
		orderSummaryGrid = Ext.create('Ext.grid.Panel', {
		    enableHdMenu: false,
		    renderTo: 'orderSummaryGridDiv',
		    width: gridMaxwidth,
		    height: 350,
		    store: orderSummarStore,
		    columns: orderSummaryColMod,
		    stripeRows: true,
		    columnLines: true,
		    loadMask: false,
		    enableColumnMove: false,
		    frame :false,
		    border: false,
		    bbar: getPagingbar('pagingId', orderSummarStore, gridMaxwidth),
		    viewConfig: {
		        forceFit: false,
		        enableTextSelection: true ,
		        emptyText: '<center><b>No Results to Display</b></center>',
		        deferEmptyText: false
		    }
		});
		
		Ext.define('orderSourceModel', {
		    extend: 'Ext.data.Model',
		    fields: [{
		        name: 'drawDate',
		        mapping: 'drawDate'
		    },{
		        name: 'requisitionNo',
		        mapping: 'requisitionNum'
		    },
//		    { name:  'accessionnumber'
//		      mapping: 'accession_number'	
//		    }
		    {
		        name: 'facility',
		        mapping: 'facility'
		    },
		    {
		        name: 'testsCount',
		        mapping: 'testsCount'
		    },
		    {
		        name: 'frequency',
		        mapping: 'frequency'
	    }]
		});
		orderSourceStore = Ext.create('Ext.data.Store', {
		    model: 'orderSourceModel',
		    baseParams: {
                processorName: 'OrderSourceProcessor',
                processorAction: 'paginateOrderSource',
                start: start,
                limit: rowsPerPage,
                facilityId: facilityIdValue,
                spectraMRN: spectraMRNValue,
                ajaxAction: 'true'
            },
		    proxy: {
		            type: 'ajax',
		            url: url,
		            extraParams: {
	                    processorName: 'OrderSourceProcessor',
	                    processorAction: 'paginateOrderSource',
	                    facilityId: facilityIdValue,
	                    spectraMRN: spectraMRNValue
	                },
		            method: 'POST',
		            reader: {
		                type: 'json',
		                rootProperty: 'ListInfo',
		                totalProperty: 'total'
		            }
		        }
//            ,
//            listeners: {
//            	load: function(orderSourceStore, records, succ, eOpts) {
//            	 	console.log('orderSourceStore: ' + succ);
//            	 	console.warn(orderSourceStore.getProxy().getReader().rawData);
//            	}        
//            }
		});
		var colWidthArray = [0.15, 0.17, 0.32, 0.15, 0.21, 0.13];
		orderSourceColMod = [{
			header: '<b>Draw Date</b>',
            dataIndex: 'drawDate',
            width: (gridMaxwidth * colWidthArray[0]),
            sortable: true,
            menuDisabled: true
		}, {
			header: '<b>Requisition#</b>',
            dataIndex: 'requisitionNo',
            width: (gridMaxwidth * colWidthArray[1]),
            sortable: true,
            renderer: linkRendererSrc,
            menuDisabled: true
		}
		, {
			header: '<b>Facility</b>',
            dataIndex: 'facility',
            width: (gridMaxwidth * colWidthArray[2]),
            sortable: true,
            menuDisabled: true
		}, {
			header: '<b># Of Tests</b>',
            dataIndex: 'testsCount',
            width: (gridMaxwidth * colWidthArray[3]),
            sortable: true,
            menuDisabled: true
		}, {
			header: '<b>Freq</b>',
            dataIndex: 'frequency',
            width: (gridMaxwidth * colWidthArray[4]),
            sortable: true,
            menuDisabled: true
		}];
		orderSourceGrid = Ext.create('Ext.grid.Panel', {
		    enableHdMenu: false,
// 		    renderTo: 'orderSourceGridDiv',
		    width: gridMaxwidth,
		    height: 350,
		    store: orderSourceStore,
		    columns: orderSourceColMod,
		    stripeRows: true,
		    columnLines: true,
		    loadMask: false,
		    enableColumnMove: false,
		    frame :false,
		    border: false,
		    bbar: getPagingbarSource('pagingSourceId', orderSourceStore, gridMaxwidth),
		    viewConfig: {
		        forceFit: false,
		        enableTextSelection: true ,
		        emptyText: '<center><b>No Results to Display</b></center>',
		        deferEmptyText: false
		    }
		});
		//Ajax call to load the order summary details.
		loadOrderSummaryDetails();
		loadOrderSourceDetails();
		 Ext.QuickTips.init();
		 
	
	Ext.create('Ext.tab.Panel', {
	 	renderTo: 'tabPanel',
	 	 cls: "MainPanel",
        bodyCls: 'navigationTabPanelBody',
       removePanelHeader: true,      	
	   items: [
	  {
		  items : orderSummaryGrid,
			tabConfig: {
                listeners: {
                    click: function(tabs) {
                    	 document.getElementById('summaryFilter').style.display = 'inline';
                    	 document.getElementById('sourceFilter').style.display = 'none';
                    	 document.getElementById('tab2').style.borderTopColor = '#d0c9c9';
                    	 document.getElementById('tab1').style.borderTopColor = '#8bc34a';
                    }
                },
                style: {
                	   borderTopColor: '#8bc34a',
                       backGroundColor:'none'
                },
                id:"tab1",
                title: 'Lab Orders',
                tooltip: 'Requisitions in HLAB'               
            },
	   },
	   {
		 items : orderSourceGrid,
			 items : orderSourceGrid,
	 			tabConfig: {
	                listeners: {
	                    click: function(tab) {
	                     document.getElementById('summaryFilter').style.display = 'none';
	                     document.getElementById('sourceFilter').style.display = 'inline';
	                   	 document.getElementById('tab1').style.borderTopColor = '#d0c9c9';
	                	 document.getElementById('tab2').style.borderTopColor = '#8bc34a';
	                    }
	                },
	                style: {
	                	   borderTopColor: '#8bc34a',
	                        backGroundColor:'none'
	                },
	                id:"tab2",
	                title: 'Sent Orders',
	                tooltip: 'Requisitions Not in HLAB'       
	            },
	   }]
	});
	});

}
Ext.Ajax.setTimeout(60000);
//Function to create paginng bar.
function getPagingbar(id, store, width) {
    if (null == width || undefined == width) {
         width = gridMaxwidth;
    }
    if (null == id || '' == id || undefined == id) {
         id = 'pagingId';
    }
      var pagingBar = Ext.create('Ext.PagingToolbar', {
           id: id,
           store: store,
           displayInfo: true,
           displayMsg: '<span style="color:#666666;"> Showing {0} - {1} of {2} Search results </span>',
           emptyMsg: '<span style="color:#666666;"> No results to display </span>',
           style: 'background:#E6E7E8 !important;margin-left: 0px; padding-left:100px !important;',
           width: width
       });
   pagingBar.down('#refresh').hide();
   return pagingBar;
}
//Function to load the order summary details.
function loadOrderSummaryDetails() {
	showLoading(true);
	Ext.Ajax.request({
        url: url,
        success: loadOrderSummaryStore,
        failure: function (conn, response, options) {
            showLoading(false);
        },
        params: {
            processorName: 'OrderStaffProcessor',
            processorAction: 'getOrderSummaryDetails',
  //          processorAction: 'getOrderStaffDetails',
            facilityId: facilityIdValue,
            spectraMRN: spectraMRNValue,
            dateRange: dateRange
        }
    });
}
//Function to load the order summary store.
function loadOrderSummaryStore(response) {
	orderSummarStore.load({
        params: {
            processorName: 'OrderStaffProcessor',
            processorAction: 'paginateOrderSummary',
            ajaxAction: 'true'
        },
        callback: function (response, options, success) {
        	showLoading(false);
        }
    });
}
function getPagingbarSource(id, store, width) {
    if (null == width || undefined == width) {
         width = gridMaxwidth;
    }
    if (null == id || '' == id || undefined == id) {
         id = 'pagingSourceId';
    }
      var pagingBarSource = Ext.create('Ext.PagingToolbar', {
           id: id,
           store: store,
           displayInfo: true,
           displayMsg: '<span style="color:#666666;"> Showing {0} - {1} of {2} Search results </span>',
           emptyMsg: '<span style="color:#666666;"> No results to display </span>',
           style: 'background:#E6E7E8 !important;margin-left: 0px; padding-left:100px !important;',
           width: width
       });
   pagingBarSource.down('#refresh').hide();
   return pagingBarSource;
}
//Function to load the order summary details.
function loadOrderSourceDetails() {
	showLoading(true);
	Ext.Ajax.request({
        url: url,
        success: loadOrderSourceStore,
        failure: function (conn, response, options) {
            showLoading(false);
        },
        params: {
            processorName: 'OrderSourceProcessor',
            processorAction: 'getOrderSourceDetails',
            facilityId: facilityIdValue,
            spectraMRN: spectraMRNValue,
            dateRange: dateRange,
            patType: 'SF'
        }
    });
}
//Function to load the order summary store.
function loadOrderSourceStore(response) {
	orderSourceStore.load({
        params: {
            processorName: 'OrderSourceProcessor',
            processorAction: 'paginateOrderSource',
            ajaxAction: 'true'
        },
        callback: function (response, options, success) {
        	showLoading(false);
        }
    });
}
//Function to change the filter.
function changeFilter() {
	var selectedVal = document.getElementById("filterSelect").value;
	dateRange = selectedVal;
	loadOrderSummaryDetails();
}
//Function to change the filter.
function changeFilterSource() {
	var selectedVal = document.getElementById("filterSelectSource").value;	
	dateRange = selectedVal;
	loadOrderSourceDetails();
}
//Function to render hyperlink.
function linkRenderer(val, metadata, record, rowIndex, colIndex, store) {
	if (val != "" && val != null) {
		var retString = "<a href='#' onclick = 'getRequisitionDetails(\"" + record.get('requisitionNo') + "\")' style=''>"+val+"</a>"
		if (record.get('NumOfTubesNotReceived') == 0) {   // tubes received for req 
			retString = retString + "&nbsp;&nbsp;&nbsp;<img src='"+urlPath+"/images/Normal.png'  title='All Tubes Received' width='17' height='17' alt=''/>";
		 }
		if (record.get('abnormalFlag') == "true") {
			retString = retString + "&nbsp;&nbsp;&nbsp;<img src='"+urlPath+"/images/Alert.png' title='Alert and/or Exception' width='17' height='17' alt=''/>";
		}		
		if (record.get("cancelledTestIndicator") == "true") {
			retString = retString + "&nbsp;&nbsp;&nbsp;<img src='"+urlPath+"/images/cancelled.png' title='Cancelled Test(s)' width='17' height='17' alt=''/>";
		}
		return retString;
	}
}

//Function to render hyperlink Source ReqDetails.
function linkRendererSrc(val, metadata, record, rowIndex, colIndex, store) {
	if (val != "" && val != null) {
			var retString = "<a href='#' onclick = 'getRequisitionSrcDetails(\"" + record.get('requisitionNo') + "\")' style=''>"+val+"</a>"
     return retString;
  }
}

//Function to navigate to reqiuisition page.
function getRequisitionDetails(val){
	parent.document.searchResultsForm.action = parent.requisitionActionURL;
	parent.document.searchResultsForm.processorName.value = 'OrderProcessor';
	parent.document.searchResultsForm.processorAction.value = 'searchRequisition';
	parent.document.searchResultsForm.isOrderSumPage.value = 'true';
	parent.document.searchResultsForm.loadPage.value = "reqDetailPage";
	parent.document.searchResultsForm.reqNumber.value = val;
	parent.document.searchResultsForm.staffSummaryPage.value = "true";
	parent.document.searchResultsForm.submit();
}
//Function to navigate to reqiuisition for Source Orders not in HLAB page.
function getRequisitionSrcDetails(val){
	parent.document.searchResultsForm.action = parent.requisitionActionURL;
	parent.document.searchResultsForm.processorName.value = 'OrderSourceProcessor';
	parent.document.searchResultsForm.processorAction.value = 'searchRequisition';
	parent.document.searchResultsForm.isOrderSumPage.value = 'true';
	parent.document.searchResultsForm.loadPage.value = "reqDetailSourcePage";
//	parent.document.getElementById("equipmentSource").value = 'patient';
	parent.document.searchResultsForm.staffSummaryPage.value = "false";
	parent.document.searchResultsForm.reqNumber.value = val;
	parent.document.searchResultsForm.submit();
}

//Function to show/hide loading window.
function showLoading(show) {
    if (show) {
        Ext.getBody().mask(loadHtml);
    } else {
        Ext.getBody().unmask();
    }
}

//Function which navigates to facility demographics screen on click of facility name.
function goToFacDemographicsPage() {
	parent.document.searchResultsForm.action = parent.sendFacDemographicsReqURL;
	parent.document.searchResultsForm.loadPage.value = "facDemographicsPage";
	parent.document.searchResultsForm.hlabNum.value = primaryFacNumber;
	parent.document.searchResultsForm.processorName.value = 'FacilityProcessor';
	parent.document.searchResultsForm.processorAction.value = 'showFacilityDemographics';
	parent.document.searchResultsForm.submit();
}
