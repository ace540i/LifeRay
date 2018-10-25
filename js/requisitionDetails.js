var tubeSummaryGrid;
var tubeSummaryStore;
var orderDetailsGrid;
var orderTestDetStore;
var microOrderDetailsGrid;
var microOrderTestDetStore;
var start = 0;
var rowsPerPage = 25;
var records;
var currentPage = 1;
var commentsVal = "";
var controlReasonVal = "";
var orderTestSelModel;
var microOrderTestSelModel;
var selectedRowIndex;
var reportGroupVal;
var reportGroupCombo;
var genLabsTestCount = 0;
var microTestCount = 0;
var shValue = '';
var resValue = '';
var testSH = '';

// Function to load the details based on requisition number.
function showPagingBar() {
        Ext.QuickTips.init();
    	// Data model for tube summary list .
        Ext.define('tubeModel', {
            extend: 'Ext.data.Model',
            fields: [{
                name: 'accession'
            },{
                name: 'tubetype'
            },{
                name: 'specimen'
            },{
                name: 'recievedDetails'
            },{
                name: 'condition'
            },{
                name: 'status'
            },{
            	name: 'receivedCount'
            },{
            	name: 'totalCount'
            }]
        });
    	//Store for loading patient list screen.
        tubeSummaryStore = Ext.create('Ext.data.Store', {
            model: 'tubeModel',
            storeId: 'tubeSummaryStore',
            remoteSort: true,
            url: url,
            baseParams: {
                processorName: 'OrderProcessor',
                processorAction: 'getTubeSummary',
                start: start,
                limit: rowsPerPage,
                ajaxAction: 'true'
            },
            proxy: {
                type: 'ajax',
                url: url,
                extraParams: {
                    processorName: 'OrderProcessor',
                    processorAction: 'getTubeSummary'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'ListInfo',
                    totalProperty: 'total'
                }
            }
//            ,
//            listeners: {
//            	load: function(tubeSummaryStore, records, succ, eOpts) {
//            	 	console.log('tubeSummaryStore: ' + succ);
//            	 	console.log(tubeSummaryStore.getProxy().getReader().rawData);
//            		
//            	}
//            
//            }
        });
        var gridMaxwidth =  Ext.get("content-wrapper").getWidth()*0.985;
    	//Grid model for tube summary.
        tubeSummaryGrid = Ext.create('Ext.grid.Panel', {
            //renderTo: 'testSummaryDiv',
            id: 'tubeSummaryGrid',
            width: gridMaxwidth,
            store: tubeSummaryStore,
            disableSelection: true,
            frame: false,
            border: true,
            columnLines: true,
        	stripeRows: true,
        	viewConfig: {
            forceFit: false,
            enableTextSelection: true ,
            emptyText: '<center><b>No Results to Display</b></center>',
            deferEmptyText: false
        },
            columns: [{
                header: '<B>Accession</B>',
                width: gridMaxwidth * 0.1781,
                sortable: false,
                dataIndex: 'accession',
                style: 'font-weight: bold !important;',
                menuDisabled: true
            },
            {
                header: '<B>Tube Type</B>',
                width: gridMaxwidth * 0.20,
                dataIndex: 'tubetype',
                sortable: false,
                style: 'font-weight: bold !important;',
                tdCls: 'wrap',
                menuDisabled: true
            },
            {
                header: '<B>Specimen</B>',
                width: gridMaxwidth * 0.1548,
                dataIndex: 'specimen',
                sortable: false,
                style: 'font-weight: bold !important;',
                menuDisabled: true
            },
            {
                header: '<B>Received</B>',
                width: gridMaxwidth * 0.1843,
                dataIndex: 'recievedDetails',
                sortable: false,
                style: 'font-weight: bold !important;',
                menuDisabled: true,
                renderer: receivedDatesRenderer
            },
            {
                header: '<B>Condition</B>',
                width: gridMaxwidth * 0.1781,
                dataIndex: 'condition',
                sortable: false,
                style: 'font-weight: bold !important;',
                menuDisabled: true
            },
            {
                header: '<B>Status</B>',
                width: gridMaxwidth * 1.098,
                dataIndex: 'status',
                sortable: false,
                style: 'font-weight: bold !important;',
                menuDisabled: true
            }]
        });
        
        var tubePanel = Ext.create('Ext.panel.Panel', {
            id: 'tubeSummaryPanelId',
            width: gridMaxwidth,
            collapsible: true,
            collapsed: true,
            renderTo: 'testSummaryDiv',
            items: [tubeSummaryGrid],
            listeners: {
    		      collapse: function(){
    		    	  parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight - tubeSummaryGrid.getHeight()) + 'px';
    	      		},
    		      expand:  function(){
    		    	  var newHeight;
    		    	  var count = tubeSummaryStore.getCount();
    		    	  if(count < 3){
    		    		  newHeight =  count * 46 + 46;
    		    	  } else if(count < 5){
    		    		  newHeight =  count * 51;
    		    	  } else {
    		    		  newHeight =  count * 52;
    		    	  }
    		    	  Ext.getCmp('tubeSummaryPanelId').setHeight(newHeight);
    		    	  tubeSummaryGrid.setHeight(newHeight);
    		    	  parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight + newHeight) + 'px';
    		      	}
              }
        });
        
        // Data model for tube summary list .
        Ext.define('orderDetailsModel', {
            extend: 'Ext.data.Model',
            fields: [{
                name: 'test'
            },
	    {
	        name: 'shmsg'
	    },            
            {
                name: 'result'
            },
            {
                name: 'units'
            },
            {
                name: 'reference'
            },
            {
                name: 'comments'
            },
            {
                name: 'indicators'
            },
            {
                name: 'orderStatus'
            },
            {
                name: 'requisitionHeader'
            },{
            	name: 'abnormalFlag'
            }, {
            	name: 'orderControlReason'
            }, {
            	name: 'testNameHeader'
            }]
        });
    	//Store for loading patient list screen.
        orderTestDetStore = Ext.create('Ext.data.Store', {
            model: 'orderDetailsModel',
            storeId: 'orderTestDetStore',
			remoteSort: true,
            url: url,
            baseParams: {
                processorName: 'OrderProcessor',
                processorAction: 'getOrderTestDetails',
                start: start,
                limit: rowsPerPage,
                ajaxAction: 'true'
            },
            proxy: {
                type: 'ajax',
                url: url,
                extraParams: {
                    processorName: 'OrderProcessor',
                    processorAction: 'getOrderTestDetails'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'ListInfo',
                    totalProperty: 'total'
                }
             }
//            ,
//            listeners: {
//            	load: function(orderTestDetStore, records, succ, eOpts) {
//            	 	console.log('orderTestDetStore: ' + succ);
//            	 	console.log(orderTestDetStore.getProxy().getReader().rawData);
//            		
//            	}
//            
//            }
           
        });
        orderTestSelModel = Ext.create('Ext.selection.RowModel', {    	// <================= listeners:  timc   record.get('shmsg') 
            singleSelect: true,
            listeners: {
                select: function (sm, rec, rowIndex) {
                    var selRows = orderTestSelModel.getSelection();
                    commentsVal = rec.get('comments');
                    controlReasonVal = rec.get('orderControlReason');
                    selectedRowIndex = rowIndex;
                    shValue = rec.get('shmsg');     					// timc
                    resValue = rec.get('result');     					// timc
                    testSH = rec.get('test');     						// timc 8/26/2016
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
                width: gridMaxwidth * 0.222,
                sortable: false,
                dataIndex: 'test',
                style: 'font-weight: bold !important;',
                menuDisabled: true,
                renderer: testRenderer
            },
            {
                header: '<B>Result</B>',
                width: gridMaxwidth * 0.2098,
                dataIndex: 'result',
                sortable: false,
                style: 'font-weight: bold !important;',
                tdCls: 'x-change-cell',
                renderer: resultsRenderer,
                menuDisabled: true
            },
	 // timc 8/11/2016       
	    {
	        header: '<B>SH Msg</B>',
	        width: 0,  //gridMaxwidth * 0.1285,
	        dataIndex: 'shmsg',
	        sortable: false,
	      resizable: false,
	        renderer: shmsgRenderer,
	        //hidden: true,
	        style: 'font-weight: bold !important;',
	        tdCls: 'x-change-cell',    //'wrap',
	        menuDisabled: true 
	    },
	// timc 8/11/2016                
            {
                header: '<B>Units</B>',
                width: gridMaxwidth * 0.118,
                dataIndex: 'units',
                sortable: false,
                tdCls: 'wrap',
                style: 'font-weight: bold !important;',
                menuDisabled: true
            },
            {
                header: '<B>Reference</B>',
                width: gridMaxwidth * 0.13,
                dataIndex: 'reference',
                sortable: false,
                style: 'font-weight: bold !important;',
                menuDisabled: true
            },
            {
                header: '<B>Comments</B>',
                width: gridMaxwidth * 0.2200,
                dataIndex: 'comments',
                sortable: false,
                style: 'font-weight: bold !important;',
                menuDisabled: true,
                tdCls: 'wrap',
                renderer: commentsRenderer
            },
            {
                header: '<B>Indicators</B>',
                width: gridMaxwidth * 0.12,
                dataIndex: 'indicators',
                sortable: false,
                style: 'font-weight: bold !important;',
                hidden: true,
                menuDisabled: true
            },
            {
                header: '<B>Order Status</B>',
                width: gridMaxwidth * 0.10,
                dataIndex: 'orderStatus',
                sortable: false,
                style: 'font-weight: bold !important;',
                menuDisabled: true
            }],
            viewConfig: {
            	markDirty: false,
	            forceFit: true,
	            enableTextSelection: true ,
	            emptyText: '<center><b>No Results to Display</b></center>',
	            deferEmptyText: false,
	            getRowClass: function(record, index) {
	                var abnormalFlagVal = record.get('abnormalFlag');
	                if (abnormalFlagVal == "L" || abnormalFlagVal == "H" || abnormalFlagVal == "A") {
	                    return 'light-yellow';
	                } else if (abnormalFlagVal == "EL" || abnormalFlagVal == "EH" || abnormalFlagVal == "CE") {
	                	return 'light-orange';
	                } else if (abnormalFlagVal == "AL" || abnormalFlagVal == "AH" || abnormalFlagVal == "CA") {
	                	return 'light-red';
	                }
	            }
	        },
	        selModel: orderTestSelModel
        });
      
        var orderDetailTitle = "";
        if ("PATIENT" == patientTypeSubGrp || "STAFF" == patientTypeSubGrp || "STUDY" == patientTypeSubGrp) {
        	orderDetailTitle = 'ORDER DETAILS' + '<span id="reportGroupComboId"></span>' + '&nbsp;&nbsp;&nbsp;<span id="tooltipIcon"></span>&nbsp;&nbsp;&nbsp;<span id="testsCountId" class="titleCls"># of Tests: '+numberOfTests +'</span>' + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="frequencySpanId" class="titleCls">Frequency: '+frequency+'</span>' + '<span class="titleCls" style="float: right;margin-right: 20px;">Group By: </span>';
        } else {
        	orderDetailTitle = 'ORDER DETAILS' + '<span id="reportGroupComboId"></span>' + '&nbsp;&nbsp;&nbsp;<span id="tooltipIcon"></span>&nbsp;<span id="testsCountId" class="titleCls" style="padding-left: 13%;">Source: </span>' + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="frequencySpanId" class="titleCls" style="padding-left: 18%;">Status: </span>' + '<span class="titleCls" style="float: right;margin-right: 20px;">Group By: </span>';
        }

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
       
        var htmlContent = '<table>'
        				+ '<tr>'
        				+ '<td>'
        				+ '<div style="height: 20px; width: 20px; background-color: #FFA500; text-indent: 55px; border-radius: 10px;float:left !important;"></div>&nbsp;&nbsp;&nbsp;<span style="font-weight: bold;font-family: arial;">Exception Low / Exception High / Custom Exception</span>'
        				+ '</td>'
        				+ '</tr>'
        				+ '<tr>'
        				+ '<td>&nbsp;</td>'
        				+ '</tr>'
        				+ '<tr>'
        				+ '<td>'
        				+ '<div style="height: 20px; width: 20px; background-color: #FFDE43; text-indent: 55px; border-radius: 10px;float:left !important;"></div>&nbsp;&nbsp;&nbsp;<span style="font-weight: bold;font-family: arial;">Low / High / Abnormal (!)</span>'
        				+ '</td>'
        				+ '</tr>'
        				+ '<tr>'
        				+ '<td>&nbsp;</td>'
        				+ '</tr>'
        				+ '<tr>'
        				+ '<td>'
        				+ '<div style="height: 20px; width: 20px; background-color: #FF9B9B; text-indent: 55px; border-radius: 10px;float:left !important;"></div>&nbsp;&nbsp;&nbsp;<span style="font-weight: bold;font-family: arial;">Alert Low / Alert High / Custom Alert</span>'
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
  	                    width: 350,
  	                    height: 110,
  	                    style: 'background-color: #ffffff !important;',
  	                    mouseOffset: [0,-50],
  	                    html: htmlContent
  	                });
  	            }
  	        }
  		});
        
        // The data store containing the list of report grouping.
		var reportGroupStore = Ext.create('Ext.data.Store', {
		    fields: ['abbr', 'name'],
		    data : [
		        {"abbr":"Report Header", "name":"Report Header"},
		        {"abbr":"accession", "name":"Accession"},
		        {"abbr":"Order Status", "name":"Order Status"}
		    ]
		});
		
		//Creating the Report grouping combobox.
        reportGroupCombo = new Ext.form.ComboBox ({
			editable: false,
			id: 'reportGroupCombo',
			store: reportGroupStore,
			displayField: 'name',
			valueField: 'abbr',					
			width: 130,
			triggerAction: 'all',
			style: 'float: right;padding-right: 10px;margin-right: 46px;',
			renderTo: 'reportGroupComboId',
			listeners: {
		    	select: function(combo, record) {
		    		reportGroupVal = combo.getValue();
		    		//Load the order details grid based on the report group type.
		    		loadOrderDetails();
		    	}
			}
		});
        reportGroupCombo.setValue("Report Header");
        //Show the microbilogy panel only for patient/staff sub group. // added env fro micro panel.
        if ("PATIENT" == patientTypeSubGrp || "STAFF" == patientTypeSubGrp || "STUDY" == patientTypeSubGrp || "ENVIRONMENTAL" == patientTypeSubGrp) {
        	// Data model for Micro order details .
            Ext.define('microOrderDetailsModel', {
                extend: 'Ext.data.Model',
                fields: [{
                    name: 'test'
                },
		    {
		        name: 'shmsg'
		    },   
                {
                    name: 'result'
                },
                {
                    name: 'comments'
                },
                {
                    name: 'indicators'
                },
                {
                    name: 'orderStatus'
                },
                {
                    name: 'requisitionHeader'
                },{
                	name: 'abnormalFlag'
                }, {
                	name: 'orderControlReason'
                },
                {
                	name: 'testNameHeader'
                }]
            });
        	//Store for loading Micro order details screen.
            microOrderTestDetStore = Ext.create('Ext.data.Store', {
                model: 'microOrderDetailsModel',
                storeId: 'microOrderTestDetStore',
                remoteSort: true,
                url: url,
                baseParams: {
                    processorName: 'OrderProcessor',
                    processorAction: 'getMicroOrderTestDetails',
                    start: start,
                    limit: rowsPerPage,
                    ajaxAction: 'true'
                },
                proxy: {
                    type: 'ajax',
                    url: url,
                    extraParams: {
                        processorName: 'OrderProcessor',
                        processorAction: 'getMicroOrderTestDetails'
                    },
                    reader: {
                        type: 'json',
                        rootProperty: 'ListInfo',
                        totalProperty: 'total'
                    }
                }
//                ,
//                listeners: {
//                	load: function(microOrderTestDetStore, records, succ, eOpts) {
//                	 	console.log('microOrderTestDetStore: ' + succ);
//                	 	console.log(microOrderTestDetStore.getProxy().getReader().rawData);
//                		
//                	}
//                
//                }
            });
            microOrderTestSelModel = Ext.create('Ext.selection.RowModel', {
                singleSelect: true,
                listeners: {
                    select: function (sm, rec, rowIndex) {
                        var selRows = microOrderTestSelModel.getSelection();
                        commentsVal = rec.get('comments');
                        controlReasonVal = rec.get('orderControlReason');    
                        
                        //  commentsVal = rec.get('shmsg');     			// timc
                        shValue = rec.get('shmsg');     					// timc
                        testSH = rec.get('test');     						// timc 8/26/2016                        

                        selectedRowIndex = rowIndex;
                    }
                }
            });
            
            var microGridMaxwidth =  Ext.get("content-wrapper").getWidth()*0.960;
            
        	//Grid model for Micro order test details.
            microOrderDetailsGrid = Ext.create('Ext.grid.Panel', {
                id: 'microOrderDetailsModel',
                //width: microGridMaxwidth,
                width: gridMaxwidth,
                store: microOrderTestDetStore,
                disableSelection: false,
                frame: false,
                border: false,
            	stripeRows: true,
            	columnLines: true,
            	margin:'0 0 30 0',
            	//scrollable: true,
                columns: [{
                    header: '<B>Test</B>',
                    width: gridMaxwidth * 0.222,
                    sortable: false,
                    dataIndex: 'test',
                    style: 'font-weight: bold !important;',
                    menuDisabled: true,
                    renderer: testRenderer
                },
                {
                    header: '<B>Result</B>',
                    width: gridMaxwidth * 0.35,
                    dataIndex: 'result',
                    sortable: false,
                    style: 'font-weight: bold !important;',
                    tdCls: 'wrap',
                    renderer: microResultsRenderer,
                    menuDisabled: true
                },
        // timc 8/11/2016                 
        {
            header: '<B>SH Msg</B>',
            width: 0,  //gridMaxwidth * 0.1,
            dataIndex: 'shmsg',
            sortable: false,
            resizable: false,            
            renderer: shmsgRenderer,
            style: 'font-weight: bold !important;',
            tdCls: 'x-change-cell',    //'wrap',
            menuDisabled: true 
        },
        // timc 8/11/2016                   
                {
                    header: '<B>Units</B>',
                    width: gridMaxwidth * 0.12,
                    dataIndex: 'units',
                    sortable: false,
                    tdCls: 'wrap',
                    style: 'font-weight: bold !important;',
                    menuDisabled: true,
                    hidden: true,
                },
                {
                    header: '<B>Reference</B>',
                    width: gridMaxwidth * 0.13,
                    dataIndex: 'reference',
                    sortable: false,
                    style: 'font-weight: bold !important;',
                    menuDisabled: true,
                    hidden: true,
                },
                {
                    header: '<B>Comments</B>',
                    width: gridMaxwidth * 0.243,
                    dataIndex: 'comments',
                    sortable: false,
                    style: 'font-weight: bold !important;',
                    menuDisabled: true,
                    tdCls: 'wrap',
                    renderer: commentsRenderer
                },
                {
                    header: '<B>Indicators</B>',
                    width: gridMaxwidth * 0.12,
                    dataIndex: 'indicators',
                    sortable: false,
                    style: 'font-weight: bold !important;',
                    hidden: true,
                    menuDisabled: true
                },
                {
                    header: '<B>Order Status</B>',
                    width: gridMaxwidth * 0.1832,
                    dataIndex: 'orderStatus',
                    sortable: false,
                    style: 'font-weight: bold !important;',
                    menuDisabled: true
                }],
                viewConfig: {
                	markDirty: false,
    	            forceFit: true,
    	            enableTextSelection: true,
    	            emptyText: '<center><b>No Results to Display</b></center>',
    	            deferEmptyText: false,
    	            getRowClass: function(record, index) {
    	                var abnormalFlagVal = record.get('abnormalFlag');
    	                if (abnormalFlagVal == "L" || abnormalFlagVal == "H" || abnormalFlagVal == "A") {
    	                    return 'light-yellow';
    	                } else if (abnormalFlagVal == "EL" || abnormalFlagVal == "EH" || abnormalFlagVal == "CE") {
    	                	return 'light-orange';
    	                } else if (abnormalFlagVal == "AL" || abnormalFlagVal == "AH" || abnormalFlagVal == "CA") {
    	                	return 'light-red';
    	                }
    	            }
    	        },
    	        selModel: microOrderTestSelModel
            });
          
            var microOrderDetailTitle = 'MICROBIOLOGY ORDER DETAILS' ;     
            
            var microOrderDetailPanel = Ext.create('Ext.panel.Panel', {
                title: microOrderDetailTitle,
            	id: 'microOrderDetailPanelId',
                //width: microGridMaxwidth,
                width: gridMaxwidth,
             //   height: 415,
                collapsible: true,
               // baseCls: reqDetHeader,
                // autoScroll:true,
                renderTo: 'microOrderDetailsDiv',
                items: [microOrderDetailsGrid],
                listeners: {
      		      collapse: function(){
      		    		parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight - microOrderDetailsGrid.getHeight() ) + 'px';
      	      		},
      		      expand:  function(){
      		    	  parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight + microOrderDetailsGrid.getHeight()) + 'px';
      		      	}
                }
            });
            // To load micro order details.
          //  loadMicroOrderDetails();  
        }
    	// To load tube summary.
        loadTubeSummary();
        // To load order details.
        loadOrderDetails();
}

// Funtion to load tube summary.
function loadTubeSummary() {
    tubeSummaryStore.load({
        params: {
            processorName: 'OrderProcessor',
            processorAction: 'getTubeSummary',
            requisitionNo : requisitionNo,
            ajaxAction: 'true'
        },
        callback: function (response, options, success) {
            var totalCount = tubeSummaryStore.getAt(0).get('totalCount');
            var receivedCount = tubeSummaryStore.getAt(totalCount - 1).get('receivedCount');
            var title = 'TUBE SUMMARY' + '&nbsp;&nbsp;&nbsp;<span id="tubeSummarySpan" class="titleCls">'+receivedCount+ ' out of '+totalCount+ ' received'+'</span>';
            Ext.getCmp('tubeSummaryPanelId').setTitle(title);
            if (totalCount < 1 || receivedCount < totalCount ){
            	document.getElementById('checkImg').style.display = 'none';
            }
            else {
            	document.getElementById('checkImg').style.display = 'inline';
            }
        }  
    });
    
}

// Funtion to load order details.
function loadOrderDetails() {	
	reportGroupVal = reportGroupCombo.getValue();
	//Reset the frame height on change of selection so that the grid loads again if there are less values.
	parent.document.getElementById('reqDetFrame').style.height = "";
	orderTestDetStore.load({
        params: {
            processorName: 'OrderProcessor',
            processorAction: 'getOrderTestDetails',
            requisitionNo : requisitionNo,
            reportGroupVal: reportGroupVal,
            ajaxAction: 'true'
        },
        callback: function (response, options, success) {
        	if(success){
        		  var newHeight;
		    	  var count = orderTestDetStore.getCount();
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
        		//orderDetailsGrid.setHeight(newHeight);
        	//	Ext.getCmp('orderDetailPanelId').setHeight(newHeight);
        	//	 console.log('gen labs count: ' + count + ', newHeight: ' + newHeight + ', offset: ' + parent.document.getElementById('reqDetFrame').offsetHeight);
        		//parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight + newHeight - 685) + 'px';
        		parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight + newHeight) + 'px';
        		
				if ( (count == 0) && (microTestCount > 0) ) {	        			
					document.getElementById("orderDetailsDiv").style.display = "none";					
				} else {
					document.getElementById("orderDetailsDiv").style.display = "block";
				} 

				
        	}
        }              
    });
	loadMicroOrderDetails();
}


//Funtion to load micro order details.
function loadMicroOrderDetails() {
	microOrderTestDetStore.load({
        params: {
            processorName: 'OrderProcessor',
            processorAction: 'getMicroOrderTestDetails',
            requisitionNo : requisitionNo,
            //reportGroupVal: reportGroupVal,
            ajaxAction: 'true'
        },
        callback: function (response, options, success) {
        	if(success){
        		var newHeight;
		    	  var count = microOrderTestDetStore.getCount();
		    	  //console.log('loadMicroOrderDetails => Micro results count: ' + count);
	    	  
		    	  microTestCount = count;
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
        	//	microOrderDetailsGrid.setHeight(newHeight);    	
        	//	Ext.getCmp('microOrderDetailPanelId').setHeight(newHeight);
        		
        	//	console.log('Micro grid height: ' + Ext.getCmp('microOrderDetailsModel').getHeight() +
        		//			', panel height: ' + Ext.getCmp('microOrderDetailPanelId').getHeight() + ', newHeight: ' + newHeight);
        		//var header = Ext.getCmp('microOrderDetailPanelId').getHeader();
        		//header.baseCls='reqDetHeader';
        		
        	//	console.log('Micro count: ' + count + ', newHeight: ' + newHeight + ', offset: ' + parent.document.getElementById('reqDetFrame').offsetHeight);
        		//parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight + newHeight - 685) + 'px';
		    	  parent.document.getElementById('reqDetFrame').style.height = (parent.document.getElementById('reqDetFrame').offsetHeight + newHeight) + 'px';
				
				//console.log('loadMicroOrderDetails => Gen results count: ' + genLabsTestCount);
        		//Display Gen labs order detail section and Micro order details section based on the available data for those sections.
				if (count == 0) {	        			
					document.getElementById("microOrderDetailsDiv").style.display = "none";					
				} else {
					document.getElementById("microOrderDetailsDiv").style.display = "block";
				} 
				
				if ( (genLabsTestCount == 0) && (count > 0) ) {	        			
					document.getElementById("orderDetailsDiv").style.display = "none";
				} else {
					document.getElementById("orderDetailsDiv").style.display = "block";
				}
        		
        	}
        }
    });
}

//Function to render test name.
function testRenderer(value, metadata, record, rowIndex, colIndex, store) {

	var returnValue = '';
	//if (null != value && undefined != value && 'undefined' != value && '' != value) {
	if (!isEmptyJSONVal(value)) {
      if (null != record.get('requisitionHeader')  && record.get('requisitionHeader') == 'Y') {
    	  returnValue = '<b>'+ value +'</b>';
      } else if (null != record.get('testNameHeader')  && record.get('testNameHeader') == 'Y') {
      	returnValue =  value;
      } else {
 		returnValue =  value;
    	if (value != '&nbsp;') {
            returnValue =  returnValue + '&nbsp;&nbsp;  <img src="/Symfonie-search-portlet/images/info_icon.png" width="15" height="15" title="&nbsp;Status History&nbsp;"  onclick="setTimeout(function() {showShWin();}, 300);" />';
    	}        	
      }
	}
	
  return returnValue;
}

//Function which displays the result values.
function resultsRenderer(value, metadata, record, rowIndex, colIndex, store) {
	//console.log("%ctest%c "+  record.get("test"),'color: green; font-weight: bold;','color:blue');  
	//console.log("%cshmsg%c "+  record.get("shmsg"),'color: green; font-weight: bold;','color:blue');  
	//console.log("%cresult%c "+  record.get("result"),'color: green; font-weight: bold;','color:blue');  
	//console.log("%cunits%c " + record.get("units"),'color: green; font-weight: bold;','color:blue'); 
	//console.log("%creference%c " + record.get("reference"),'color: green; font-weight: bold;','color:blue'); 
	//console.log("%ccomment%c " + record.get("comments"),'color: green; font-weight: bold;','color:blue'); 
	//console.log("%cindicators%c " + record.get("indicators"),'color: green; font-weight: bold;','color:blue'); 
	//console.log("%corderStatus%c " + record.get("orderStatus") ,'color: green; font-weight: bold;','color:blue'); 
	//console.log("%crequisitionHeader%c "+ record.get("requisitionHeader"),'color: green; font-weight: bold;','color:blue'); 
	//console.log("%cabnormalFlag%c " + record.get("abnormalFlag"),'color: green; font-weight: bold;','color:blue'); 
	//console.log("%corderControlReason%c " + record.get("orderControlReason") ,'color: green; font-weight: bold;','color:blue'); 
	//console.log("%ctestNameHeader%c " + record.get("testNameHeader"),'color: green; font-weight: bold;','color:blue'); 
	//console.log("-----------------------------------------------------------------------");
	
	if (null != record.get('requisitionHeader')  && record.get('requisitionHeader') == 'Y') {
		
		store.each(function(record) {
			if (record.get("test")  == null) { 
				record.set('test','&nbsp;');
			}
			if (record.get("shmsg")  == null) { 
				record.set('shmsg','&nbsp;');
			}
			if (record.get("result")  == null) { 
				record.set('result','&nbsp;');
			}
			if (record.get("units")  == null) { 
				record.set('units','&nbsp;');
			}
			if (record.get("reference")  == null) { 
				record.set('reference','&nbsp;');
			}
			if (record.get("comments")  == null) { 
				record.set('comments','&nbsp;');
			}
			if (record.get("orderStatus")  == null) { 
				record.set('orderStatus','&nbsp;');
			}
		});
	}

	if (value == null) {
		value = '&nbsp;';		// timc 9/14  DO NOT REMOVE.... 
	}
	
	var resultVal = "";
	if (null != value && "" != value && (undefined != value || 'undefined' != value)) {
		resultVal = '<span>' + value + '</span>';
	} else if (isEmptyJSONVal(value)) {
		resultVal = '<span>&nbsp;</span>';
	}
	if (null != record.get('abnormalFlag') && "" != record.get('abnormalFlag') && 
			(undefined != record.get('abnormalFlag') || 'undefined' != record.get('abnormalFlag')) && "N" != record.get('abnormalFlag')) {
		if ("A" != record.get('abnormalFlag')) {
			resultVal += '<span style="float: right;">' + '(' + record.get('abnormalFlag') + ')'+ '&nbsp;&nbsp;&nbsp;</span>';
		} else {
			resultVal += '<span style="float: right;">' + '(!)'+ '&nbsp;&nbsp;&nbsp;</span>';
		}
	}
 
	if (value.length > 18) {
		resultVal = "<div id='ResultsDiv"+rowIndex+"'>" + value + "</div>";
		resultVal = resultVal.substr(0, 180) + '...' + '<a href="#"  class="tip" id="orderResultsAncId'+rowIndex+'" title="'+value+'"  onclick="setTimeout(function() {showOrderResultsWin();}, 300);">(more)</a>';
	}	
	
	return resultVal;	
}

//Function to render Status History name.
function shmsgRenderer(value, metadata, record, rowIndex, colIndex, store) {
	
	var shValue = '';
	shValue =  value;

	shValue = shValue.substr(0, 5) + '...' + '<a href="#" id="shMshgId'+rowIndex+'"  title="'+value+'"  onclick="setTimeout(function() {showShWin();}, 300);">(more)</a>'
	    	+ '&nbsp;&nbsp;&nbsp;  <img src="/Symfonie-search-portlet/images/Question_info.png" width=\"15\" height=\"15\" onclick="setTimeout(function() {showShWin();}, 300);" />'; 
	
	return shValue;
}

function showShWin(){ 
	
	var tester = testSH.replace(/&nbsp;/gi, ""); 
	var SHdateTime = ""; 	
	var finalStr = "";

	if (shValue == '-') {

		finalStr = '<br/><div>&nbsp;&nbsp;&nbsp;No Status History Available....<br/></div><br/>';     
		
	}else {

		finalStr = '<div align=CENTER style="background-color:#fff;"><br/><table width=90%  style="border: 0px solid lightgray;" >'
		+	'<tr style="background-color:#2574ab; border: 1px solid #2574ab;"><td width=35% style="border-right: 1px solid #fff;"><font size=2 color="#fff">&nbsp;&nbsp;<b>Date & Time</b></td><td style="border-right: 0px solid #2574ab;"><font size=2 color="#fff">&nbsp;&nbsp;<b>Status</b></td></tr>';

		var msgArray = new Array();
		var subArray = new Array();
		var bk = "";
		msgArray = shValue.split(",");
		for(var x=0;x<msgArray.length;x++){
			subArray = msgArray[x].split("|");
			if(x % 2 == 0) {
				bk='#f4f4f4';
			}else {
				bk='#fff';                            
			}
			SHdateTime = subArray[1].replace(/AM|PM/gi, ""); 
			finalStr = finalStr + '<tr  bgcolor='+bk+' ><td style="border: 1px solid lightgray;">&nbsp;&nbsp;'+ SHdateTime + ' </td><td style="border: 1px solid lightgray;"> &nbsp; ' +subArray[2] + '  </td></tr>';
		}
		finalStr = finalStr + '</table><br/></div>';
	}		

	var titel = shValue.substring(0, shValue.indexOf("|"));
	var alignTargetId = "shMshgId" + selectedRowIndex;
	var winSpecId = 'messageWinSH' + selectedRowIndex;
	
	if (tester === titel) {
		//alert('TEST is SAME......= ' + tester + ', and ' + titel);
	}else {
		if (titel.length > 0) {
			tester = tester + ' - ' + titel ;	
		}
	}
	
	var isIT = document.getElementById(winSpecId);   
	if (isIT == null) {
		
		Ext.create('Ext.window.Window', {
			       title: '<b>&nbsp;&nbsp;'+tester+'</b>',
			        //id: 'messageWin' + selectedRowIndex,
			        id: winSpecId, //'messageWinSH',
			        width: 450,
			        layout: 'fit',
			        modal: false,
			        //cls: 'popup pad closeCls windowCls',
			        resizable: true,
			        style: 'font-weight: bold !important;',
			        html : finalStr,
			        border: false,
			        defaultFocus:0,
			        floating: true,
			        alignTarget: alignTargetId,
			        buttons: [{
		                text: 'Close',
		                handler: function () {
		                     Ext.getCmp(winSpecId).close(); 
		                }
		             }]
			}).show();
		//Ext.getCmp('messageWin').anchorTo(document.getElementById('orderCommentsDiv'+selectedRowIndex), 'c-c', [0,0], false, true);
	}

}

function showOrderResultsWin(){   
	var finalStr = "";
	
	finalStr = resValue; // listeners:   timc !!
	
	var alignTargetId = "orderResultsAncId" + selectedRowIndex;
	Ext.create('Ext.window.Window', {
        title: '<b>Result</b>',
        id: 'messageWin',
        width: 450,
        layout: 'fit',
        modal: true,
        //cls: 'popup pad closeCls windowCls',
        resizable: false,
        html : finalStr,
        border: false,
        defaultFocus:0,
        floating: true,
        alignTarget: alignTargetId,
        buttons: [{
                   text: 'OK',
                   handler: function () {
                        Ext.getCmp('messageWin').close(); 
                   }
                }]
    }).show();
	//Ext.getCmp('messageWin').anchorTo(document.getElementById('orderCommentsDiv'+selectedRowIndex), 'c-c', [0,0], false, true);
}

//Function which displays the result values.
function microResultsRenderer(value, metadata, record, rowIndex, colIndex, store) {
	
	
	if (null != record.get('requisitionHeader')  && record.get('requisitionHeader') == 'Y') {
		
		store.each(function(record) {
			if (record.get("test")  == null) { 
				record.set('test','&nbsp;');
			}
			if (record.get("shmsg")  == null) { 
				record.set('shmsg','&nbsp;');
			}
			if (record.get("result")  == null) { 
				record.set('result','&nbsp;');
			}
			if (record.get("units")  == null) { 
				record.set('units','&nbsp;');
			}
			if (record.get("reference")  == null) { 
				record.set('reference','&nbsp;');
			}
			if (record.get("comments")  == null) { 
				record.set('comments','&nbsp;');
			}
			if (record.get("orderStatus")  == null) { 
				record.set('orderStatus','&nbsp;');
			}
		});      //indicators
	}
	
	
	var resultVal = "";
	var sensNameWidth = '200px;';
	//sensNameWidth = gridMaxwidth * 0.22 + 'px;';
	
	var sensResWidth = '80px;';
	//sensResWidth = gridMaxwidth * 0.10+ 'px;';
	
	var sensFlagWidth = '20px;';
	//sensFlagWidth = gridMaxwidth * 0.03+ 'px;';	
	
	var sensResHeadWidth = '150px;';
	//sesResHeadWidth = gridMaxwidth * 0.13+ 'px;';
	
	if ( !isEmptyJSONVal(record.get('printSensHeader') ) ) {
		
		resultVal += '<table><tr><td style="width: ' + sensNameWidth + '">&nbsp;</td>';
		resultVal += '<td style="width: ' + sensResHeadWidth + '"><b>MIC(mcg/mL)</b></td></tr>';
		resultVal += '<tr><td style="colspan: 2;">&nbsp;</td></tr></table>';	
	}	
	if ( !isEmptyJSONVal(record.get('microSensFlag')) && (record.get('microSensFlag') == "Y")) {		
		resultVal += getSensDisLine(sensNameWidth, sensResWidth, sensFlagWidth, record.get('microSensName'), record.get('result'), record.get('abnormalFlag'));
			
	} else if ( !isEmptyJSONVal(record.get('printIsoHeader') ) && "ENVIRONMENTAL" != patientTypeSubGrp) {
		//console.log('micro printIsoHeader: ' + record.get('microIsolate') + ', org: ' +  record.get('microOrg'));
		resultVal += '<table><tr><td style="width: ' + sensNameWidth + '">&nbsp;</td><td style="width: ' + sensResHeadWidth + ' ">'  + record.get('microIsolate') +  '</td></tr>';
		resultVal += '<tr><td style="width: ' + sensNameWidth + ' "> &nbsp; &nbsp;</td><td style="width: ' + sensResHeadWidth + '"><b>'  + record.get('microOrg') + '</b></td></tr></table>';
	} else if (!isEmptyJSONVal(value)) {
	 	//console.log('res name: ' + record.get('test'));
		resultVal = '<span>' + value + '</span>';
	}	
	return resultVal;
}

//Get Micro Sensitivity display Line.
function getSensDisLine(sensNameWidth, sensResWidth, sensFlagWidth, sensName, result, abnFlag) {
	var line="";
	
	line += '<span style="float: left; width: ' + sensNameWidth + '">' + sensName + '</span>';
	
	line += '<span style="float: left; width: ' + sensResWidth + '">' + getDisplayValue(result) + '</span>';
	
	
	if (!isEmptyJSONVal(abnFlag) ) {
		line += '<span style="float: left; width: ' + sensFlagWidth + '">(' + abnFlag + ')</span>';
	}
	return line;	

}

function getDisplayValue(value) {
	
	if (isEmptyJSONVal(value)) {
		value = "&nbsp;";
	}
	return value;
}

//Function to check if the value from JSON object is empty
function isEmptyJSONVal(value) {
	
	return (value == null) || (value == undefined) || (value == 'undefined') || (value.length == 0) ;

}

//Function which navigates to order summary page on click of Patient name.
function goToOrderSummaryPage() {
	if ("PATIENT" == patientTypeSubGrp || "STUDY" == patientTypeSubGrp) {
		parent.document.searchResultsForm.loadPage.value = "orderDetailPage";
		parent.document.searchResultsForm.facilityId.value = facilityIdVal;
		parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
		parent.document.searchResultsForm.processorName.value = 'OrderProcessor';
		parent.document.searchResultsForm.processorAction.value = 'getOrderSum';
		parent.document.searchResultsForm.submit();
	} else if ("STAFF" == patientTypeSubGrp) {
		parent.document.searchResultsForm.loadPage.value = "orderStaffDetailPage";
		parent.document.searchResultsForm.facilityId.value = facilityIdVal;
		parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
		parent.document.searchResultsForm.processorName.value = 'OrderStaffProcessor';
		parent.document.searchResultsForm.processorAction.value = 'getOrderSum';
		parent.document.searchResultsForm.submit();
	} else if ("ENVIRONMENTAL" == patientTypeSubGrp) {
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
	parent.document.searchResultsForm.hlabNum.value = primaryFacNumber;
	parent.document.searchResultsForm.processorName.value = 'FacilityProcessor';
	parent.document.searchResultsForm.processorAction.value = 'showFacilityDemographics';
	parent.document.searchResultsForm.userName.value = parent.userName;
	parent.document.searchResultsForm.submit();
}

//Function to render the comments section by concatinating the order control reason with the result comments.
function commentsRenderer(value, metadata, record, rowIndex, colIndex, store) {
	var commentsStr = "";
	var orderControlReason = "";
	var finalStr = "";
	var commentsValSubStr = "";
	if (null != record.get("orderControlReason") && "" != record.get("orderControlReason") && 
			(undefined != record.get("orderControlReason") || 'undefined' != record.get("orderControlReason"))) {
		if (("Canceled by OLA" == record.get("orderControlReason") || ("Conflicting or overlapping test." == record.get("orderControlReason")) 
				|| ("Data Entry error." == record.get("orderControlReason")) || ("Duplicate order." == record.get("orderControlReason")) 
				|| ("New York water" == record.get("orderControlReason")) || ("Non reportable cancellation" == record.get("orderControlReason"))
				|| ("Test canceled: not needed." == record.get("orderControlReason")) 
				|| ("Tests canceled; no tubes received for this order." == record.get("orderControlReason"))
				|| ("Urine not required." == record.get("orderControlReason")) ) ) {
			orderControlReason = "<div style='font-style: italic !important;'>"+record.get("orderControlReason");
			
		} else {
			orderControlReason = "<div>"+record.get("orderControlReason") + "</div>";
		}

	}
	if (null != value && "" != value && 
			(undefined != value || 'undefined' != value)) {
		commentsStr = "<div>" + value.replace(/\n/g,"<br/>") + "</div>";
	}
	
	if ("" != orderControlReason) {
		finalStr = orderControlReason + "</div>" + "<br/>";
	}
	finalStr = "<div id='orderCommentsDiv"+rowIndex+"'>" + finalStr + commentsStr + "</div>";
	commentsValSubStr = record.get("orderControlReason") + " " + value;
	var commentsLength = commentsValSubStr.length;
	if (commentsLength > 150) {
		
		finalStr = finalStr.substr(0, 150) + '...' + '<a href="#" id="orderCommentsAncId'+rowIndex+'"  title="'+value+'"  onclick="setTimeout(function() {showOrderCommentsWin();}, 300);">(more)</a>';
	
    }
	finalStr = finalStr + "</div>";
	return finalStr;
}

function showOrderCommentsWin(){
	var commentsStr = "";
	var orderControlReason = "";
	var finalStr = "";

	if (null != controlReasonVal && "" != controlReasonVal && 
			(undefined != controlReasonVal || 'undefined' != controlReasonVal)) {
		if (("Canceled by OLA" == controlReasonVal || ("Conflicting or overlapping test." == controlReasonVal) 
				|| ("Data Entry error." == controlReasonVal) || ("Duplicate order." == controlReasonVal) 
				|| ("New York water" == controlReasonVal) || ("Non reportable cancellation" == controlReasonVal)
				|| ("Test canceled: not needed." == controlReasonVal) 
				|| ("Tests canceled; no tubes received for this order." == controlReasonVal)
				|| ("Urine not required." == controlReasonVal) ) ) {
			orderControlReason = "<div style='font-style: italic !important;'>"+controlReasonVal;
			
		} else {
			orderControlReason = "<div>"+controlReasonVal + "</div>";
		}
	}
	if (null != commentsVal && "" != commentsVal && 
			(undefined != commentsVal || 'undefined' != commentsVal)) {
		commentsStr = "<div>" + commentsVal.replace(/[\r]/g, "<br/>" ).replace(/[\n]/g, "")  + "</div>";
	}
	if ("" != orderControlReason) {
		finalStr = orderControlReason + "</div>" + "<br/>";
	}
	finalStr = finalStr + commentsStr;
	var alignTargetId = "orderCommentsAncId" + selectedRowIndex;
	Ext.create('Ext.window.Window', {
        title: '<b>Comments</b>',
        id: 'messageWin',
        width: 450,
        layout: 'fit',
        modal: true,
        //cls: 'popup pad closeCls windowCls',
        resizable: false,
        html : finalStr,
        border: false,
        defaultFocus:0,
        floating: true,
        alignTarget: alignTargetId,
        buttons: [{
                   text: 'OK',
                   handler: function () {
                        Ext.getCmp('messageWin').close(); 
                   }
                }]
    }).show();
	//Ext.getCmp('messageWin').anchorTo(document.getElementById('orderCommentsDiv'+selectedRowIndex), 'c-c', [0,0], false, true);
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
