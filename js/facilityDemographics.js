var facAccountsColModel;
var facilitySelModel;
var facAccountsGrid;
var facilityReqStore;
var reqTypeStore;
var reqTypeCombobox;
var reqTypeComboVal = "";
var d = new Date();
var drawDateVal = d.toLocaleDateString();
var url;
var facilityIdVal;
var spectraMRNVal;
var orderMRN;
var start = 0;
var currentPage = 1;
var rowsPerPage = 25;
var tubesReceivedCount = 0;
var accessionCount = 0;
var tubeExpCount = 0;
var patientType = "";
var blankImagePath = "/Resources/ext-5.1.0/packages/ext-theme-classic/resources/images/tree/s.gif";
var loadHtml = '<table width="80px" height="10px" border="0" cellpadding="0" cellspacing="0">  <tr><td><img src="'+blankImagePath+'" style="padding-right:2px;"/></td><td style = "padding-top:3px;">Loading...</td></tr>';
function showFacilityGraph() {	
    var htmlContent = '<table>'
		+ '<tr>'
		+ '<td>'
		+ '<span style="font-weight: bold;font-family: arial;">Recent Lab Activity Chart  shows the facility`s HLAB requisition volume 7 days at a time.</span>'
		+ '</td>'
		+ '</tr>'
		+ '<tr>'
		+ '<td>'
		+ '<div style="height: 20px; width: 20px; background-color: #C0504D; text-indent: 55px; border-radius: 10px;float:left !important;"></div>&nbsp;&nbsp;&nbsp;<span style="font-weight: bold;font-family: arial;">Not Received - The order has been sent &nbsp;&nbsp;&nbsp;from Korus but is not in HLAB yet or it is &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;in HLAB with no Tubes Received and the &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;status is not Final</span>'
		+ '</td>'
		+ '</tr>'
		+ '<tr>'
		+ '<td>'
		+ '<div style="height: 20px; width: 20px; background-color: #ff7f00; text-indent: 55px; border-radius: 10px;float:left !important;"></div>&nbsp;&nbsp;&nbsp;<span style="font-weight: bold;font-family: arial;">In Process - The order is in HLAB with &nbsp;&nbsp;&nbsp;tubes received but does not have a &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;requisition status of Final</span>'
		+ '</td>'
		+ '</tr>'
		+ '<tr>'
		+ '<td>'
		+ '<div style="height: 20px; width: 20px; background-color: #9BBB59; text-indent: 55px; border-radius: 10px;float:left !important;"></div>&nbsp;&nbsp;&nbsp;<span style="font-weight: bold;font-family: arial;">Final - Requisition has a status of Final</span>'
		+ '</td>'
		+ '</tr>'
		+ '</table>';
var tooltipIcon = Ext.create('Ext.Img',{
src: '/Symfonie-search-portlet/images/Question_info.png',
cls: 'questionBtn',
width: 15,
height: 15,
renderTo: 'tooltipIcon',
listeners: {
  render: function(cmp) {
      Ext.create('Ext.tip.ToolTip', {
          target: cmp.el,
          width: 360,
          height: 267,
          style: 'background-color: #ffffff !important;',
          mouseOffset: [0,-50],
          html: htmlContent
      });
  }
}
});
	
	Ext.define('facLevelGraphModel', {
        extend: 'Ext.data.Model',
        fields: [{
        	name: 'drawDate',
        	mapping: 'drawDate'
        },{
        	name: 'notReceivedCount',
        	mapping: 'notReceivedCount'
        },{
        	name: 'inProcessCount',
        	mapping: 'inProcessCount'
        },{
        	name: 'finalCount',
        	mapping: 'finalCount'
        }]
    });

	chartStore = Ext.create('Ext.data.Store', {
	    model: 'facLevelGraphModel',
	    proxy: {
	        type: 'ajax',
	        url: url,
	        reader: {
	            type: 'json',
	            rootProperty: 'ListInfo',
	            totalProperty: 'total'
	        }
	    },
	    listeners: {
	        loadException: function(proxy, options, response) {
	        }
	    } 	  
	});
	
	// The data store containing the list of requisition types..
	reqTypeStore = Ext.create('Ext.data.Store', {
	    fields: ['type', 'reqTypeVal'],
	    data : [
	        {"type":"All", "reqTypeVal":"All"},
	        {"type":"Patient", "reqTypeVal":"Patient"},
	        {"type":"Staff", "reqTypeVal":"Staff"},
	        {"type":"Environmental", "reqTypeVal":"Equipment"},
	    ]
	});
	
	reqTypeCombobox = new Ext.form.ComboBox ({
		editable: false,
		id: 'reqTypeCombobox',
		store: reqTypeStore,
		displayField: 'reqTypeVal',
		valueField: 'type',					
		width: 100,
		triggerAction: 'all',
		style: 'padding-left: 10px;',
		listeners: {
		    	select: function(combo, record) {
		    		reqTypeComboVal = combo.getValue();
		    		vals = comboPanel.getValues();
		    		drawDateVal = vals.drawDate;
		    		loadFacilityGraph(reqTypeComboVal,drawDateVal);
		    		facGridPanel.getStore().removeAll();
		    		facGridPanel.getStore().sync();
				 	document.getElementById('headingDiv1').style.display = 'none';
					document.getElementById('headingDiv1').value = '';
					document.getElementById('headingDiv2').style.display = 'none';
					document.getElementById('headingDiv2').value = '';
		    	}
		}
	});
	//Set ALL as default value on load.
	reqTypeCombobox.setValue("All");
	
	var comboPanel = Ext.create('Ext.form.Panel', {
        width: 900,
        height: 22,
        id: 'comboPanel',
        border: false,
        frame: false,
        layout: 'hbox',
        engine: 'Ext.draw.engine.Svg',
        renderTo: 'patientTypeSubGrpId',
        items: [
        {
	        xtype: 'datefield',
	        maxValue: new Date(),
	        editable : false,
//	        flex : 1,
//	        anchor: '100%',
            fieldLabel: 'Start Draw Date',
	        name: 'drawDate',
	        style: 'padding-left: 20px;',
	        value: new Date(),  // defaults to today
            listeners: {
              change: function(combo, record) {
		    		vals = comboPanel.getValues();
		    		drawDateVal = vals.drawDate;
		    		loadFacilityGraph(reqTypeComboVal,drawDateVal);
		    		facGridPanel.getStore().removeAll();
		    		facGridPanel.getStore().sync();
				 	document.getElementById('headingDiv1').style.display = 'none';
					document.getElementById('headingDiv1').value = '';
					document.getElementById('headingDiv2').style.display = 'none';
					document.getElementById('headingDiv2').value = '';
              }
            }
	    },{
        	xtype: 'label',
        	style: 'padding-left: 20px;',
        	html:  'Requisition Type'
        },
        reqTypeCombobox]
    });	 
	
	var vals = comboPanel.getValues();
	  drawDateVal = vals.drawDate;

	var gridMaxwidth =  Ext.get("content").getWidth()*0.985;
	
	
	var chart = Ext.create('Ext.chart.Chart', {	
	   plugins: {
		  ptype: 'chartitemevents',
		  moveEvents: true
	    },
        id: 'labChart',
        renderTo: 'chartDiv',
        width: gridMaxwidth / 1.86,
        height: 645,
        border: false,
        background: '#FFFFFF',
        xtype: 'cartesian',
       colors: ["#C0504D", "#ff7f00", "#9BBB59"],      
        legend: {
        	id: 'labChartLegend',
            docked: 'top',
            scrollable :false
        },
        insetPadding: {
	    	left: -20,
            right: 40,
            bottom: -20
        }, 
        bodyStyle: 'border:0px none;',            
        store: chartStore,              
        axes: [{
            type: 'numeric',
            position: 'left',
            titleMargin: 50, 
            grid :{
				odd: {
					stroke: '#ccc'
				},
				even: {
					stroke: '#ccc'
				}
            },
            adjustByMajorUnit: true,
            fields: ['notReceivedCount', 'inProcessCount','finalCount'],
            label: {
            	fontSize: 13
  		      },  
            title: {
	           text: 'Req Count',
	           fontSize: 15,
	           fontFamilies: 'Arial',
	           fontWeight:'bold'
            },
            minimum: 0
        }, {
            type: 'category',
            position: 'bottom',
            titleMargin: 70, 
            label: {
                fontSize: 13
             },
            title: {
            	text: 'Draw Date',
            	fontSize: 15,
	            fontFamilies: 'Arial',
	            fontWeight:'bold'
            },
            fields: ['collectionDate']
        }],        
        
        series: [{
            type: 'bar',
            interactions: ['itemhighlight'],
            axis: 'left',
            title: [ '&nbsp;Not Received', '&nbsp;In Process', '&nbsp;Final'],
            xField: 'collectionDate',
//            yField: ['completeCount', 'partialCount', 'inProcessCount', 'scheduledCount'],
            yField: ['notReceivedCount', 'inProcessCount', 'finalCount'],
            stacked: false,
            style: {
                maxBarWidth: 60
            },
		     label: {
                  display: 'outside',
                  field: [ 'notReceivedCount', 'inProcessCount','finalCount'],
                  orientation: 'horizontal',                 
                  fontSize: 12,
                  fontWeight:'bold',
                  calloutColor: 'rgba(0,0,0,0)',
                  fill: '#666666',
                   
              },
              listeners: { // Listen to itemclick events on all series.
                  itemclick: function (chart, item, event,obj) {
                	var status = null; 
  					if (item.field == "notReceivedCount"){status = "Not Received" }
  					if (item.field == "inProcessCount"){status = "In Process" }
  					if (item.field == "finalCount"){status = "Final" }
  					document.getElementById('headingDiv1').innerHTML = "&nbsp&nbsp;Draw Date  <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp;"+item.record.data['collectionDate'] + "</b>" ;
  					document.getElementById('headingDiv1').setAttribute("style", "text-align: left;");
     				document.getElementById("headingDiv1").style.paddingLeft= "4.1%";
//     				document.getElementById('headingDiv1').setAttribute("style","width:300px;padding-left:20");
     				document.getElementById('headingDiv2').innerHTML = "&nbsp&nbsp;Requisition Type<b>&nbsp&nbsp; "+ reqTypeComboVal + "</b></br>&nbsp&nbsp;Status: <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp;&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp;"+status +"</b>";
  					document.getElementById('headingDiv2').setAttribute("style", "text-align: left;");
     				document.getElementById("headingDiv2").style.paddingLeft= "4.1%";
                	loadRequisitionTable(item,chartStore);	  
                  }
              }, 
             tooltip: {
            	trackMouse: true,
                style: 'background: #CDDFF2;border-color: #99BBE8;',
                border: true,
                bodyStyle: 'font: bold 12px Arial, Verdana, Helvetica, sans-serif; color:#15428B;',
                
                renderer: function(storeItem, item) {            	
  					if (item.field == "notReceivedCount"){status = "Not Received" }
  					if (item.field == "inProcessCount"){status = "In Process" }
  					if (item.field == "finalCount"){status = "Final" }    
                    this.setHtml(status+ " - "+storeItem.get(item.field)+ "</br>"+"Draw Date: "+item.record.data['collectionDate']);            
                }
            } 
            
        }]	
    });
	var newHeight = 600;
	if (accountsSize < 3) {
		newHeight = 600;
	} else if (accountsSize <= 6) {
		newHeight = newHeight + 120;
	} else if (accountsSize > 6) {
		newHeight = newHeight  + 150;
	}
	parent.document.getElementById('facDemoFrame').style.height = (parent.document.getElementById('facDemoFrame').offsetHeight + newHeight) + 'px';
	loadFacilityGraph("",drawDateVal);
}

//Function to load the facility demographics graph.
function loadFacilityGraph(reqTypeVal,drawDateVal) {
	showLoading(true);
	if (reqTypeComboVal == "") {
		reqTypeComboVal = "All";
	}
	if (drawDateVal == "") {
		drawDateVal = new Date();
	}
	Ext.Ajax.request({
        url: url,
        success: loadChartValues,
        failure: function (conn, response, options) {
        	showLoading(false);
        },
        params: {       	
            processorName: 'FacilityProcessor',
            processorAction: 'getFacLevelGraphDetails',
            reqTypeComboVal: reqTypeComboVal,
            facilityIdVal: facilityIdVal,
            drawDateVal: drawDateVal
        }
    });
}
 
Ext.onReady(function() {

        var gridWidth = Ext.get("theme-wrapper").getWidth() ;
     
		facilityRecords = Ext.define('facilityRecords', {
		    extend: 'Ext.data.Model',
 		    layout: 'fit',
		    fields: [{
		        name: 'requisition_id',
		        mapping: 'requisition_id',
		        sortable: true
		    },{
		        name: 'patientName',
		        mapping: 'patientName',
		        sortable: true
		    }, {
		        name: 'spectraMRN',
		        mapping: 'spectraMRN',
		    },{
		        name: 'dateOfBirth',
		        mapping: 'dateOfBirth',
		        sortType: 'asDate',
		        sortable: true
		    },{
		        name: 'test_count',
		        mapping: 'test_count',
		        sortable: true 
		    },{
		        name: 'draw_frequency',
		        mapping: 'draw_frequency',
		        sortable: true,
		    },{
			    name: 'abnormalFlag',
			    mapping: 'abnormalFlag'
		    },{
		        name: 'cancelledTestIndicator',
		        mapping: 'cancelledTestIndicator'  
		    },{
		        name: 'NumOfTubesNotReceived',
		        mapping: 'numOfTubesNotRec'   
		    }]
 
    });
		
		
     facilitySelModel = Ext.create('Ext.selection.RowModel', {
		      singleSelect: true,
		      listeners: {
		          select: function (sm, rec, rowIndex) {
		              var selRows = facilitySelModel.getSelection();
		              requisitionVal = rec.get('requisition_id');
		              facilityFkVal = rec.get('facilityFk');
		              spectraMRNVal = rec.get('spectraMRN'); 
		              patientTypeVal = rec.get('patientType');
		          }
		      }
			});
		facilityReqStore = Ext.create('Ext.data.Store', {
			  model: 'facilityRecords',
		    autoSync: true,
		    baseParams: {
		    	processorName: 'FacilityProcessor',
		    	processorAction: 'PaginateFacSearchResults',
		    	ajaxAction: 'true'
		    },
		    proxy: {
		            type: 'ajax',
		            url: url,
		            reader: {
		                type: 'json',
		                rootProperty: 'ListInfo',
		                totalProperty: 'total'
		            }
		        },extraParams: {
	            	processorName: 'FacilityProcessor',
	                processorAction: 'PaginateFacSearchResults',
	                ajaxAction: 'true'
	            },
        listeners : {
            exception : function(proxy, response, operation) {
                alert('Got exception');
            }
        }
//      ,
//     listeners: {
//      	load: function(facilityReqStore) {
//     	 	console.log('********** facilityReqStore ************ ');
//      	 	console.log(facilityReqStore.getProxy().getReader().rawData);
//    	 	console.log('********** ChartStore ************ ');
//      	 	console.log(chartStore.getProxy().getReader().rawData);
//      	}    
//     }
		});
	facilityGridCol = [{
		header: '<b unselectable="off">Requisition#</b>',
        dataIndex: 'requisition_id',
        width: gridWidth / 9.1,
        menuDisabled: true,
        cellWrap: false,
        renderer: linkReq
    },{
         header: '<b unselectable="off">Patient Name</b>',
         dataIndex: 'patientName',
         width: gridWidth / 5.09,
         menuDisabled: true,
         cellWrap: false,
        renderer: linkRenderer
     },{
		header: '<b>#Tests</b>',
        dataIndex: 'test_count',
        width: gridWidth / 16.9,
        cellWrap: false,
        menuDisabled: true
	},{
		header: '<b>Freq</b>',
        dataIndex: 'draw_frequency',
        width: gridWidth / 15.0,
        cellWrap: false,
        menuDisabled: true
	}
//	,{
//		header: '<b>accessionCount</b>',
//        dataIndex: 'accessionCount',
//        width: gridWidth / 15.5,
//        cellWrap: false,
//        menuDisabled: true}
	];

	facGridPanel = Ext.create('Ext.grid.Panel', {
		ID:"facGridPanel",
        store: facilityReqStore,
        columns: facilityGridCol,
        cls: 'customized', // base class to prevent global overrides of the styling
        autoScroll: true,
        border: true,
        frame: true,
        width: gridWidth / 2.282,
        columnLines: true,
        height: 490,
     	padding: '0 0 0 0',
     	margin:'20 2 30 8',
       style: 'font-weight: bold !important; ',
       bufferedRenderer:false,
        viewConfig: {  
            forceFit: true,
            enableTextSelection: true ,
            deferEmptyText: false,
   	        emptyText: '<center><b></br></br></br></br></br></br></br></br></br></br></br>Make a Selection on the graph to<br/>view the requisition list.</b></center>',
   	     listeners : {
   	        cellclick : function(view, cell, cellIndex, record,row, rowIndex, e) {
   	                tubeExpCount = record.get('accessionCount');
//   	                console.log("status "+status);
//   	                console.log("tubeCount "+tubeExpCount);
   	       	if (record.get("requisition_status") == 'N' ) {
   	     		status = "Not Received";
   	     	}
   	     	if (record.get("requisition_status") == 'P' ) {
   	     		status = "In Process";
   	     	}
   	     	if (record.get("requisition_status") == 'F' ) {
   	     		status = "Final";
   	     	}
   	          }
   	     }
        },
        renderTo:'reqDivTable',
         selModel: facilitySelModel
    });

});

function loadRequisitionTable(item,chartStore) {   
	var patTypeVal = item.field;
    tubesReceivedCount = 0;
	accessionCount = 0;
    var drawDateVal = item.record.data['collectionDate'];
	facilityReqStore.load({
        params: {
            processorName: 'FacilityProcessor',
            processorAction: 'getFacRequisitionDetails',
            facilityId: facilityIdVal,
            reqTypeComboVal: reqTypeComboVal,
            patType:patTypeVal,
            DrawDate:drawDateVal,
            spectraMRN:spectraMRNVal
        }
});

}
function paginateRes() {
	Ext.onReady(function () {
		facilityReqStore.load({
	        params: {
	        	processorName: 'FacilityProcessor',
	            processorAction: 'PaginateFacSearchResults'
	        }
	    });
	});
}

	showLoading(true);

//Function to load the chart values.
function loadChartValues(response) {
	chartStore.loadRawData(Ext.decode(response.responseText));
	showLoading(false);
}

//Function to render hyperlink.
function linkRenderer(val, metadata, record, rowIndex, colIndex, store) {
	
	if (val != "" && val != null) {
		 return '<a href="#" onClick = "setTimeout(function() {getOrderSummaryDetails();}, 300);">'+val+'</a>';
	}
}
function linkReq(val, metadata, record, rowIndex, colIndex, store) {	
	status = "";

	if (record.get("requisition_status") == 'N' ) {
		status = "Not Received";
	}
	if (record.get("requisition_status") == 'P' ) {
		status = "In Process";
	}
	if (record.get("requisition_status") == 'F' ) {
		status = "Final";
	}

	if (val != "" && val != null) {	
//		tubeExpCount = record.get('accessionCount');
			var retString = '<a href="#" onClick = "setTimeout(function() {getRequisitionDetails();}, 300);" >'+val+'</a>';
			if (record.get('NumOfTubesNotReceived') == 0 && record.get('requisition_status') != 'N') {  
				retString = retString + "&nbsp;<img src='"+urlPath+"/images/Normal.png'  title='All Tubes Received' width='16' height='16' alt=''/>";
			 }
			if (record.get('abnormalFlag') == "true") {
				retString = retString + "&nbsp;<img src='"+urlPath+"/images/Alert.png' title='Alert and/or Exception' width='16' height='16' alt=''/>";
			}
			if (record.get("cancelledTestIndicator") == "true") {
				retString = retString + "&nbsp;<img src='"+urlPath+"/images/cancelled.png' title='Cancelled Test(s)' width='16' height='16' alt=''/>";
			}
			tubesReceivedCount = tubesReceivedCount + record.get('tubesReceivedCount');
			accessionCount = accessionCount + record.get('accessionCount');	
			document.getElementById('tubeSumDiv').innerHTML = "Tube Summary:<b>&nbsp;"+ tubesReceivedCount  + "</b> out of<b>&nbsp; "+  accessionCount + "</b>";
			if (record.get('requisition_status') == 'N') {
				document.getElementById('tubeSumDiv').style.display = 'none';
				document.getElementById('tubeSumDiv').value = '';
			}else {
				document.getElementById('tubeSumDiv').style.display = 'inline';
			}
			return retString;
		}
	}

function getOrderSummaryDetails() {	
	patientType = patientTypeVal;
if ("PATIENT" == patientType || "STUDY" == patientType ) {
	parent.document.searchResultsForm.loadPage.value = "orderDetailPage";
	parent.document.searchResultsForm.facilityId.value = facilityFkVal;
	parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
	parent.document.searchResultsForm.processorName.value = 'OrderProcessor';
	parent.document.searchResultsForm.processorAction.value = 'getOrderSum';
	parent.document.searchResultsForm.submit();
} else if ("STAFF" == patientType) {
	parent.document.searchResultsForm.loadPage.value = "orderStaffDetailPage";
	parent.document.searchResultsForm.facilityId.value = facilityFkVal;
	parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
	parent.document.searchResultsForm.processorName.value = 'OrderStaffProcessor';
	parent.document.searchResultsForm.processorAction.value = 'getOrderSum';
	parent.document.searchResultsForm.submit();
} else if ("ENVIRONMENTAL" == patientType) {
	parent.document.searchResultsForm.loadPage.value = "equipmentDetailPage";
	parent.document.searchResultsForm.facilityId.value = facilityFkVal;
	parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
	parent.document.searchResultsForm.processorName.value = 'EquipmentProcessor';
	parent.document.searchResultsForm.processorAction.value = 'getOrderSum';
	parent.document.searchResultsForm.submit();
 }
}

//Function to navigate to reqiuisition page.
function getRequisitionDetails(){
	parent.document.searchResultsForm.action = parent.requisitionActionURL;
	parent.document.searchResultsForm.processorName.value = 'OrderProcessor';
	parent.document.searchResultsForm.processorAction.value = 'searchRequisition';
	parent.document.searchResultsForm.isOrderSumPage.value = 'true';
	if(status =="Not Received" && tubeExpCount == 0){
		parent.document.searchResultsForm.loadPage.value = "reqDetailSourcePage";
	}
	else {
		parent.document.searchResultsForm.loadPage.value = "reqDetailPage";
	}
	parent.document.searchResultsForm.reqNumber.value = requisitionVal;
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
//Function which navigates to patient/staff/equipment search results 
function goToSearchResults(searchType) {
	if ("Patient" == searchType) {
		parent.document.searchResultsForm.processorName.value = 'PatientProcessor';
		parent.document.searchResultsForm.processorAction.value = 'getSearchResult';
	} else if ("Staff" == searchType) {
		parent.document.searchResultsForm.processorName.value = 'StaffProcessor';
		parent.document.searchResultsForm.processorAction.value = 'getSearchResult';
	} else if ("Equipment" == searchType) {
		parent.document.searchResultsForm.processorName.value = 'EquipmentProcessor';
		parent.document.searchResultsForm.processorAction.value = 'getSearchResult';
	}
	parent.document.searchResultsForm.action = parent.sendFacDemographicsReqURL;
	parent.document.searchResultsForm.facilityId.value = facilityPkVal;
	parent.document.searchResultsForm.facilityName.value = displayFacilityName;
	parent.document.searchResultsForm.searchType.value = searchType;
	parent.document.searchResultsForm.userName.value = userName;
	parent.document.searchResultsForm.submit();
}