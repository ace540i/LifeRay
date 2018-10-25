/* 12/22/2015 - mdarretta - US1087
 * 			1) Added var for staffGridCol.
 *  		2) Added function showStaffResGrid for ext grid.
 *   		3) Added function linkRendererStaff for staff name hyperlink to call getOrderStaffDetails. 
 *   		4) Added function getOrderStaffDetails to submit form and call 
 *   			OrderStaffProcessor.getOrderSum(orderStaffDetailPage) method. 
*/
/* 12/28/2015 - timc - US1223 
 * 			1) Added var for equipmentGridCol.
 *  		2) Added function showEquipmentResGrid for ext grid.
 *  		3) Added function loadPaginateResEquipment.
 *   		4) Added function linkRendererEquipment for Equipment name hyperlink to call getOrderSummaryDetailsEquipment. 
 *   		5) Added function getOrderSummaryDetailsEquipment to submit form and call 
 *   			EquipmentProcessor.getOrderSum(equipmentDetailPage) method. 
*/

var patientGridCol;
var staffGridCol;  //staff
var equipmentGridCol; //equipment
var facilityIdVal;
var spectraMRNVal;
Ext.onReady(function () {

});

function showEquipmentResGrid(){        

	var gridWidth = Ext.get("content-wrapper").getWidth() * 0.98;
	equipmentGridCol = 
	[
	{
      header: '<b>Equipment Name</b>',
      dataIndex: 'fullName',
      width: gridWidth / 4,
      menuDisabled: true,
      renderer: linkRendererEquipment
  },
  {
      header: '<b>Serial Number</b>',
      dataIndex: 'serialNumber',
      width: gridWidth / 6,
      sortType:Ext.data.SortTypes.asUCString,
      menuDisabled: true
  },
  {
      header: '<b>Facility</b>',
      dataIndex: 'facilityName',
      width: gridWidth / 4,
      sortType:Ext.data.SortTypes.asUCString,
      menuDisabled: true
  },
  {
      header: '<b>Last Draw Date</b>',
      dataIndex: 'lastDrawDate',
      width: gridWidth / 6,
      xtype: 'datecolumn', // use xtype instead of renderer
      format: 'm-d-Y' ,
      menuDisabled: true
  },
  {
      header: '<b>Spectra MRN</b>',
      dataIndex: 'spectraMRN',
      width: gridWidth / 6.8,
      menuDisabled: true
  }
  ];

	equipmentSelModel = Ext.create('Ext.selection.RowModel', {
      singleSelect: true,
      listeners: {
          select: function (sm, rec, rowIndex) {
              var selRows = equipmentSelModel.getSelection();
         //   facilityIdVal = rec.get('facilityId');
              facilityIdVal = rec.get('facility_fk');
          	spectraMRNVal = rec.get('spectraMRN');            	
          }
      }
	});
	
///////////////////////////////////////////////////////////////////////////////  	
	equipmentGridPanel = Ext.create('Ext.grid.Panel', {
      store: parent.equipmentStore,
      columns: equipmentGridCol,
      border: false, //true, 
      frame:  false,
      width: gridWidth,
      columnLines: true,
      height: 710, //650,
   enableHdMenu: false,
   loadMask: true,
   enableColumnMove: false,
      viewConfig: {
          forceFit: true,
          stripeRows: true,		
          enableTextSelection: true ,
	      emptyText: '<center><b>No Results to Display</b></center>'
		  //,deferEmptyText: false
      },
      renderTo:'equipmentSearchGrid',
      bbar: getPagingbar('equipmentSearchBBar', parent.equipmentStore, gridWidth),
      selModel: equipmentSelModel
  });
	
	parent.paginateResEquipment();
}

function loadPaginateResEquipment(response, options){
    if (response.responseText.indexOf('errorMessage') != -1) {
    } else {
    	parent.equipmentStore.loadRawData(Ext.decode(response.responseText));
    }
} 
//Function to render hyperlink.
function linkRendererEquipment(val, metadata, record, rowIndex, colIndex, store) {
	if (val != "" && val != null) {
		 return '<a href="#" onClick = "setTimeout(function() {getOrderSummaryDetailsEquipment();}, 300);" style="">'+val+'</a>';
	}
}
function getOrderSummaryDetailsEquipment() {
	parent.document.searchResultsForm.loadPage.value = "equipmentDetailPage";      
	parent.document.searchResultsForm.facilityId.value = facilityIdVal;
	parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
	parent.document.searchResultsForm.processorName.value = 'EquipmentProcessor';   
	parent.document.searchResultsForm.processorAction.value = 'getOrderSum';
	parent.document.searchResultsForm.submit();
}

function showPatientResGrid(){
	var gridWidth = Ext.get("content-wrapper").getWidth() * 0.98;
	patientGridCol = [{
        header: '<b unselectable="off">Patient Name</b>',
        dataIndex: 'fullName',
        width: gridWidth / 4.5,
        menuDisabled: true,
        renderer: linkRenderer
    },{
        header: '<b>DOB</b>',
        dataIndex: 'dateOfBirth',
        width: gridWidth / 9,
        xtype: 'datecolumn', // use xtype instead of renderer
        format: 'm-d-Y' ,
        sortType: 'asDate',
        menuDisabled: true
    },{
        header: '<b>Gender</b>',
        dataIndex: 'gender',
        width: gridWidth / 9.5,
        menuDisabled: true
    },{
        header: '<b>Modality</b>',
        dataIndex: 'modality',
        width: gridWidth / 9,
        menuDisabled: true
    },{
        header: '<b>Facility</b>',
        dataIndex: 'facilityName',
        width: gridWidth / 5,
        menuDisabled: true,
        sortType:Ext.data.SortTypes.asUCString
    },{
        header: '<b>Last Draw Date</b>',
        dataIndex: 'lastDrawDate',
        width: gridWidth / 9,
        xtype: 'datecolumn', // use xtype instead of renderer
        format: 'm-d-Y' ,
        menuDisabled: true
    },{
        header: '<b>Spectra MRN</b>',
        dataIndex: 'spectraMRN',
        width: gridWidth / 8,
        sortType: 'asInt',
        menuDisabled: true
    },{
        header: '<b>Patient ID</b>',
        dataIndex: 'patientHLABId',
        width: gridWidth / 6,
        menuDisabled: true,
        hidden: true
    }];
	
	patientSelModel = Ext.create('Ext.selection.RowModel', {
        singleSelect: true,
        listeners: {
            select: function (sm, rec, rowIndex) {
                var selRows = patientSelModel.getSelection();
                facilityIdVal = rec.get('facility_fk');
            	spectraMRNVal = rec.get('spectraMRN');
            }
        }
    });
	
	patGridPanel = Ext.create('Ext.grid.Panel', {
        store: parent.patientStore,
        columns: patientGridCol,
        border: false,
        frame: false,
        width: gridWidth,
        columnLines: true,
        height: 650,
        viewConfig: {
            forceFit: true,
            stripe: true,
            enableTextSelection: true ,
   	        emptyText: '<center><b>No Results to Display</b></center>'
        },
        renderTo:'patientSearchGrid',
        bbar: getPagingbar('patientSearchBBar', parent.patientStore, gridWidth),
        selModel: patientSelModel
    });
	parent.paginateRes();
}
//*** added function for staff *** //
function showStaffResGrid(){
	var gridWidth = Ext.get("content-wrapper").getWidth() * 0.98;
	staffGridCol = [{
        header: '<b>Staff Name</b>',
        dataIndex: 'fullName',
        width: gridWidth / 5,
        menuDisabled: true,
        renderer: linkRendererStaff
    },{
        header: '<b>DOB</b>',
        dataIndex: 'dateOfBirth',
        width: gridWidth / 8,
        xtype: 'datecolumn', // use xtype instead of renderer
        format: 'm-d-Y' ,
        sortType: 'asDate',
        menuDisabled: true
    },{
        header: '<b>Gender</b>',
        dataIndex: 'gender',
        width: gridWidth / 10.4,
        menuDisabled: true
    },{
        header: '<b>Facility</b>',
        dataIndex: 'facilityName',
        width: gridWidth / 4.6,
        sortType:Ext.data.SortTypes.asUCString,
        menuDisabled: true
    },{
        header: '<b>Last Draw Date</b>',
        dataIndex: 'lastDrawDate',
        width: gridWidth / 5,
        menuDisabled: true,
        xtype: 'datecolumn', // use xtype instead of renderer
        format: 'm-d-Y'  
    },{
        header: '<b>Spectra MRN</b>',
        dataIndex: 'spectraMRN',
        width: gridWidth / 6,
        menuDisabled: true
    },{
        header: '<b>Patient ID</b>',
        dataIndex: 'patientHLABId',
        width: gridWidth / 6,
        menuDisabled: true,
        hidden: true
    }];
	
	staffSelModel = Ext.create('Ext.selection.RowModel', {
        singleSelect: true,
        listeners: {
            select: function (sm, rec, rowIndex) {
                var selRows = staffSelModel.getSelection();
                facilityIdVal = rec.get('facility_fk');
            	spectraMRNVal = rec.get('spectraMRN'); 	
            }
        }
    });
	
	staffGridPanel = Ext.create('Ext.grid.Panel', {
        store: parent.staffStore,  // using staff store 
        columns: staffGridCol,
        border: false,
        frame: false,
        width: gridWidth,
        columnLines: true,
        height: 650,
        viewConfig: {
            forceFit: true,
            stripeRows: true,
            enableTextSelection: true ,
      	      emptyText: '<center><b>No Results to Display</b></center>'
        },
        renderTo:'staffSearchGrid',
        bbar: getPagingbar('staffSearchBBar', parent.staffStore, gridWidth),
        selModel: staffSelModel
    });
	
	parent.paginateStaffRes();  
}
function loadPaginateRes(response, options){
    if (response.responseText.indexOf('errorMessage') != -1) {
    } else {
    	console.log(Ext.decode(response.responseText));
    	parent.patientStore.loadRawData(Ext.decode(response.responseText));
    }
} 
//Function to create paginng bar.
function getPagingbar(id, store, width){
    if (null == width || undefined == width) {
         width = Ext.get("mainContainer").getWidth() - 750;
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

//Function to render hyperlink.
function linkRenderer(val, metadata, record, rowIndex, colIndex, store) {
	if (val != "" && val != null) {
		 return '<a href="#" onClick = "setTimeout(function() {getOrderSummaryDetails();}, 300);" style="">'+val+'</a>';
	}
}
//Function to render staff hyperlink.
function linkRendererStaff(val, metadata, record, rowIndex, colIndex, store) {
	if (val != "" && val != null) {
		 return '<a href="#" onClick = "setTimeout(function() {getOrderStaffDetails();}, 300);" style="">'+val+'</a>';
	}
}
function getOrderSummaryDetails() {
	parent.document.searchResultsForm.loadPage.value = "orderDetailPage";
	parent.document.searchResultsForm.facilityId.value = facilityIdVal;
	parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
	parent.document.searchResultsForm.processorName.value = 'OrderProcessor';
	parent.document.searchResultsForm.processorAction.value = 'getOrderSum';
	parent.document.searchResultsForm.submit();
}
//**** added function for Staff ***//
function getOrderStaffDetails() {
//	parent.document.searchResultsForm.loadPage.value = "orderDetailPage";
	parent.document.searchResultsForm.loadPage.value = "orderStaffDetailPage";
	parent.document.searchResultsForm.facilityId.value = facilityIdVal;
	parent.document.searchResultsForm.spectraMRN.value = spectraMRNVal;
	parent.document.searchResultsForm.processorName.value = 'OrderStaffProcessor';
	parent.document.searchResultsForm.processorAction.value = 'getOrderSum';  
	parent.document.searchResultsForm.submit();
}
//Function to show message box and focus the element.
function showMsgBox(title, message, element) {
    Ext.create('Ext.window.Window', {
        title: '<b>'+title+'</b>',
        id: 'errorMsgWin',
        width: 400,
        layout: 'fit',
        modal: true,
        cls: 'popup pad closeCls windowCls',
        resizable: false,
        html : '<br/><div style="padding-left: 10px;"><p class="popupContent">'+message+'</p><br/></div>',
        border: false,
        defaultFocus:0,
        buttons: [{
                   text: 'OK',
                   handler: function () {
                        Ext.getCmp('errorMsgWin').close(); 
                   }
                }]
    }).show();
}