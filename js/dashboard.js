
var labStore;
var labCombobox;
var reqTypeStore;
var reqTypeCombobox;
var labComboVal = "";
var reqTypeComboVal = "";
var chartStore;
var blankImagePath = "/Resources/ext-5.1.0/packages/ext-theme-classic/resources/images/tree/s.gif";
var loadHtml = '<table width="80px" height="10px" border="0" cellpadding="0" cellspacing="0">  <tr><td><img src="'+blankImagePath+'" style="padding-right:2px;"/></td><td style = "padding-top:3px;">Loading...</td></tr>';
//Function to show the charts.
function showCharts() {
	Ext.define('dashboardModel', {
        extend: 'Ext.data.Model',
        fields: [{
        	name: 'collectionDate',
        	mapping: 'collectionDate'
        },{
        	name: 'partialCount',
        	mapping: 'partialCount'
        },{
        	name: 'completeCount',
        	mapping: 'completeCount'
        }]
    });
	
	chartStore = Ext.create('Ext.data.Store', {
	    model: 'dashboardModel',
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
		
		// The data store containing the list of labs.
		labStore = Ext.create('Ext.data.Store', {
		    fields: ['abbr', 'name'],
		    data : [
		        {"abbr":"All", "name":"All"},
		        {"abbr":"HE", "name":"Rockleigh"},
		        {"abbr":"HW", "name":"Milpitas"}
		    ]
		});
		
		labCombobox = new Ext.form.ComboBox ({
			editable: false,
			id: 'labCombobox',
			store: labStore,
			displayField: 'name',
			valueField: 'abbr',					
			width: 100,
			triggerAction: 'all',
			style: 'padding-left: 10px;',
			//renderTo: 'labComboId',
			listeners: {
			    	select: function(combo, record) {
			    		labComboVal = combo.getValue();
			    		loadCharts(labComboVal, reqTypeComboVal);

			    	}
			}
		});
		//Set ALL as default value on load.
		labCombobox.setValue("All");
		// The data store containing the list of requisition types..
		reqTypeStore = Ext.create('Ext.data.Store', {
		    fields: ['type', 'reqTypeVal'],
		    data : [
		        {"type":"All", "reqTypeVal":"All"},
		        {"type":"Patient", "reqTypeVal":"Patient"},
		        {"type":"Staff", "reqTypeVal":"Staff"},
		        {"type":"Environmental", "reqTypeVal":"Equipment"}
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
			//renderTo: 'reqTypeId',
			listeners: {
			    	select: function(combo, record) {
			    		reqTypeComboVal = combo.getValue();
			    		loadCharts(labComboVal, reqTypeComboVal);
			    	}
			}
		});
		//Set ALL as default value on load.
		reqTypeCombobox.setValue("All");
		var barPanel = Ext.create('Ext.form.Panel', {
            width: 500,
            height: 22,
            id: 'comboPanel',
            border: false,
            frame: false,
            layout: 'hbox',
            engine: 'Ext.draw.engine.Svg',
            renderTo: 'labComboId',
            items: [{
            	xtype: 'label',
            	html: 'Laboratory'
            },
            labCombobox,
            {
            	xtype: 'label',
            	style: 'padding-left: 20px;',
            	html: 'Req Type'
            },
            reqTypeCombobox]
        });
		var gridMaxwidth =  Ext.get("content-wrapper").getWidth()*0.985;
		
		var chart = Ext.create('Ext.chart.Chart', {
            id: 'labChart',
            renderTo: 'chartDiv',
            width: gridMaxwidth,
            height: 600,
            border: false,
            background: '#FFFFFF',
            xtype: 'cartesian',
            colors: ["#4F81BD", "#C0504D"],
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
                fields: ['completeCount', 'partialCount'],
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
                titleMargin: 50, 
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
                axis: 'left',
                title: [ '&nbsp;Final', '&nbsp;Partial'],
                xField: 'collectionDate',
                yField: ['completeCount', 'partialCount'],
                stacked: false,
                style: {
                    maxBarWidth: 60
                },
    		     label: {
                      display: 'outside',
                      field: [ 'completeCount', 'partialCount' ],
                      orientation: 'horizontal',                 
                      fontSize: 12,
                      fontWeight:'bold',
                      calloutColor: 'rgba(0,0,0,0)',
                      fill: '#666666'
                  },
                tooltip: {
                	trackMouse: true,
                    style: 'background: #CDDFF2;border-color: #99BBE8;',
                    border: true,
                    bodyStyle: 'font: bold 12px Arial, Verdana, Helvetica, sans-serif; color:#15428B;',
                    renderer: function(storeItem, item) {
                        this.setHtml(storeItem.get(item.field));
                    }
                }
            }]
        });
		//load the default chart values.
		loadCharts(labComboVal, reqTypeComboVal);
}
//Function to load the chart.
function loadCharts(labComboVal, reqTypeComboVal) {
	showLoading(true);
	if (labComboVal == "") {
		labComboVal = "All";
	}
	if (reqTypeComboVal == "") {
		reqTypeComboVal = "All";
	}
	Ext.Ajax.request({
        url: url,
        success: loadChartValues,
        failure: function (conn, response, options) {
        	showLoading(false);
        },
        params: {
            processorName: 'FacilityProcessor',
            processorAction: 'getDashboardDetails',
            labComboVal: labComboVal,
            reqTypeComboVal: reqTypeComboVal
        }
    });
}
//Function to load the chart values.
function loadChartValues(response) {
	chartStore.loadRawData(Ext.decode(response.responseText));
	showLoading(false);
}
//Function to show/hide loading window.
function showLoading(show) {
    if (show) {
        Ext.getBody().mask(loadHtml);
    } else {
        Ext.getBody().unmask();
    }
}