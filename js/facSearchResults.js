var facSearchResultsGrid;
var facSearchResultsCol;
var facSearchResSelModel;
Ext.onReady(function () {
	
});
//Function to show the facility search results grid.
function showFacSearchResults() {
	var gridWidth = Ext.get("content-wrapper").getWidth() * 0.98;
	facSearchResSelModel = Ext.create('Ext.selection.RowModel', {
        singleSelect: true,
        listeners: {
            select: function (sm, rec, rowIndex) {
                var selRows = facSearchResSelModel.getSelection();
            }
        }
    });
	facSearchResultsCol = [{
		header: '<b>Facility Name</b>',
        dataIndex: 'facilityName',
        width: gridWidth / 5,
        sortable: true, //false,
        renderer: linkRenderer,
        menuDisabled: true
	}, {
		header: '<b>Corporation Name</b>',
        dataIndex: 'corporationName',
        width: gridWidth / 5,
        sortable: true, //false,
        menuDisabled: true
	}, {
		header: '<b>Account Type</b>',
        dataIndex: 'accountType',
        width: gridWidth / 5,
        sortable: true, //false,
        menuDisabled: true
	}, {
		header: '<b>Account Status</b>',
        dataIndex: 'accountStatus',
        width: gridWidth / 5,
        sortable: true, //false,
        menuDisabled: true
	}, {
		header: '<b>Servicing Lab</b>',
        dataIndex: 'servicingLab',
        width: gridWidth / 5.425,
        sortable: true, //false,
        menuDisabled: true
	}, {
		header: '<b>HLAB Number</b>',
        dataIndex: 'hlabNum',
        sortable: false,
        menuDisabled: true,
        hidden: true
	}];
	facSearchResultsGrid = Ext.create('Ext.grid.Panel', {
        store: parent.facSearchResultsStore,
        columns: facSearchResultsCol,
        border: false,
        frame: false,
        width: gridWidth,
        columnLines: true,
        border: true,
        height: 650,
        viewConfig: {
            forceFit: true,
            stripeRows: true,
            enableTextSelection: true ,
            emptyText: '<center><b>No Results to Display</b></center>',
        },
        //rowLines: false,
        renderTo:'facSearchResultsGrid',
        bbar: getPagingbar('facSearchResBbar', parent.facSearchResultsStore, gridWidth),
        selModel: facSearchResSelModel
    });
	parent.loadFacSearchResults();
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
		return  "<a href='#' onclick = 'showFacilityDemographics(\"" + record.get('hlabNum') + "\")' style=''>"+val+"</a>"
	}
}
//Function to show the facility level demographics screen.
function showFacilityDemographics(val) {	
	parent.document.searchResultsForm.userName.value = parent.userName;
	parent.document.searchResultsForm.action = parent.sendFacDemographicsReqURL;
	parent.document.searchResultsForm.loadPage.value = "facDemographicsPage";
	parent.document.searchResultsForm.hlabNum.value = val;
	parent.document.searchResultsForm.processorName.value = 'FacilityProcessor';
	parent.document.searchResultsForm.processorAction.value = 'showFacilityDemographics';	
	parent.document.searchResultsForm.submit();
}