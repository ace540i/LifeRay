/* 12/22/2015 - mdarretta - US1211
 * 			1) Changed searchType condition to check for Staff on page load.
 *  		2) Changed showHideDiv function for staff.
 *   		3) Changed validateSearch to check both facilty and staff are not blank.
 *   		4) Added function searchStaffDet to submit staff search.
 *  		5) Changed clearSearchValues to clear StaffName box.
*/

/* 12/28/2015 - timc - US1223 
 * 			1) Added 'Equipment' == selSearchType.
 *  		2) Changed showHideDiv function for eqpDiv.
 *   		3) Changed validateSearch to check both facilty not blank.
 *  		4) Changed clearSearchValues to clear eqpNumber box.
 *   		
*/

var facilitySearchBox;
var facilitySearchStore;
var facilityIdVal = 0;
var facilityNameVal = "";
var corpSearchbox;
var corpSearchStore;
var corpIdVal = 0;
var corpNameVal = "";
function loadScreen(){
	Ext.onReady(function() {
		 
	//record which holds the facility search details.
    Ext.define('facilitySearchRecordsModel', {
        extend: 'Ext.data.Model',
        fields: [{
            name: 'facilityName',
            mapping: 'facilityName'
        }, {
            name: 'facilityId',
            mapping: 'facilityId'
        },{
        	name: 'selFacName',
        	mapping: 'selFacName'
        }]
    });
    facilitySearchStore = Ext.create('Ext.data.Store', {
	    model: 'facilitySearchRecordsModel',
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
	           // showError(response.responseText);
	        }
	    }
	});
    //record which holds the corporation search details.
    Ext.define('corpSearchRecordsModel', {
        extend: 'Ext.data.Model',
        fields: [{
            name: 'corporationName',
            mapping: 'corporationName'
        }, {
            name: 'corporationId',
            mapping: 'corporationId'
        },{
        	name: 'selCorpName',
        	mapping: 'selCorpName'
        }
        ,{
        	name: 'selCorpAcronym',
        	mapping: 'selCorpAcronym'
        }]
    });
    corpSearchStore = Ext.create('Ext.data.Store', {
	    model: 'corpSearchRecordsModel',
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
	           // showError(response.responseText);
	        }
	    }
//	    ,
//   	 load: function (corpSearchStore, records, successful, eOpts) {
//   		 var nd= new Date();
//	  		 console.log( nd + ', loading results.. ' + records.length );  
//	   		 console.log(corpSearchStore.getProxy().getReader().rawData ); 		           		
//        }   
	});
    facilitySearchBox = Ext.create('Ext.form.field.ComboBox', {
	    id: 'facilitySearchBoxId',
	    triggerAction: 'all',
	    lazyRender: false,
	    queryMode: 'remote',
	    enableKeyEvents: true,
	    hideTrigger: true,
	    store: facilitySearchStore,
	    width: " 100% ",
	    editable: true,
	    enableKeyEvents: true,
	    matchFieldWidth: false,
	    listConfig: {
	    	cls: 'comboCls',
            listeners: {
                beforerender: function(picker) {
                    picker.minWidth = picker.up('combobox').inputEl.getSize().width;
                }
            }
        },
	    enforceMaxLength: true,
	    maxLength: 63,
	    displayField: 'facilityName',
	    maskRe: /[0-9a-zA-Z '`-]/,
	    renderTo: 'facilitySearchBoxDiv',
	    emptyText: 'All Facilities',
	    listeners: {
	    	specialkey: function (combo, event) {
	    		if (event.getKey() == 46) {
	    		}
	    	},
	        keyup: function(combo, event, eOpts) {
	            if (undefined != Ext.getCmp('facilitySearchBoxId').getValue()) {
	                var value = Ext.getCmp('facilitySearchBoxId').getValue().trim();
	                if (event.getKey() != 13) {
	                	facilityIdVal = 0;
		                facilityNameVal = "";
	                }
	                if (value.length > 2 && event.getKey() != 38 && event.getKey() != 40 && event.getKey() != 13) {
	                	facilitySearchStore.removeAll();
	                	getfacilities(value);
	                } else if (value.length <= 2) {
	                    facilitySearchStore.removeAll();
	                    combo.collapse();
	                }
	            }
	        },
	        select: function(combo, record, eOpts) {
	        	facilityIdVal = record.get("facilityId");
	        	facilityNameVal = record.get('selFacName');
	        	Ext.getCmp('facilitySearchBoxId').setValue(facilityNameVal);
	        }
	    }
	});
    corpSearchbox = Ext.create('Ext.form.field.ComboBox', {
	    id: 'corpSearchboxId',
	    triggerAction: 'all',
	    lazyRender: false,
	    queryMode: 'remote',
	    enableKeyEvents: true,
	    hideTrigger: true,
	    store: corpSearchStore,
	    width: "100%",
	    editable: true,
	    enableKeyEvents: true,
	    matchFieldWidth: false,
	    listConfig: {
	    	//cls: 'comboCls',
            listeners: {
                beforerender: function(picker) {
                    picker.minWidth = picker.up('combobox').inputEl.getSize().width;
                }
            }
        },
	    enforceMaxLength: true,
	    maxLength: 63,
	    displayField: 'corporationName',
	    maskRe: /[0-9a-zA-Z '`-]/,
	    renderTo: 'corpSearchBoxDiv',
	    emptyText: 'All Corporations',
	    listeners: {
	    	specialkey: function (combo, event) {
	    		if (event.getKey() == 46) {
	    		}
	    	},
	        keyup: function(combo, event, eOpts) {
	            if (undefined != Ext.getCmp('corpSearchboxId').getValue()) {
	                var value = Ext.getCmp('corpSearchboxId').getValue().trim();
	                if (event.getKey() != 13) {
		                corpIdVal = 0;
		                corpNameVal = "";
	                }
	                if (value.length > 2 && event.getKey() != 38 && event.getKey() != 40 && event.getKey() != 13) {
	                	corpSearchStore.removeAll();
	                	getCorporations(value);
	                } else if (value.length <= 2) {
	                	corpSearchStore.removeAll();
	                    combo.collapse();
	                }
	            }
	        },
	        select: function(combo, record, eOpts) {	        	
	        	corpIdVal = record.get("corporationId");
	        	corpNameVal = record.get('selCorpName');
	        	corpAcronym = record.get('selCorpAcronym');
	        	selCorpName = record.get('selCorpName');
	        	Ext.getCmp('corpSearchboxId').setValue(selCorpName);
	        }
	    }
	});
	if(null != document.getElementById('selectFieldnew')){
		setSelectedValue(document.getElementById('selectFieldnew'), selSearchType);
	}
	if('Patient' == selSearchType && null != document.getElementById('patientName')){
		document.getElementById('patientName').value = selSearchVal;
		
//timc
		if( "" != document.getElementById('patientDOB') &&  null != document.getElementById('patientDOB')){
 		    document.getElementById('patientDOB').value = selSearchDOB;
		}
//timc
		showHideDiv('Patient');
		
	} if('Staff' == selSearchType && null != document.getElementById('staffName')){
		document.getElementById('staffName').value = selSearchVal;
		showHideDiv('Staff');
	} else if('Requisition' == selSearchType && null != document.getElementById('reqNumber')){
		document.getElementById('reqNumber').value = selSearchVal;
		showHideDiv('Requisition');
	} else if('Facility' == selSearchType && null != document.getElementById('facName')){
		document.getElementById('facName').value = selSeachFac;
		showHideDiv('Facility');
	} else if('Equipment' == selSearchType && null != document.getElementById('eqpNumber')){
		document.getElementById('eqpNumber').value = selSearchVal;
		showHideDiv('Equipment');
	}		
	if(null != facilitySearchBox && 'null' != selSeachFac && ' ' != selSeachFac && undefined != selSeachFac && 'undefined' != selSeachFac) {
		facilitySearchBox.getStore().loadRawData(Ext.decode('({\"total\":\"1\",\"ListInfo\":[{"facilityName":"'+selSeachFac+'","facilityId":"'+selSeachFacId+'"}]})'));
		facilitySearchBox.select(selSeachFac);
		if(null !=  Ext.getCmp('facilitySearchBoxId').getSelection()){
			facilityIdVal = Ext.getCmp('facilitySearchBoxId').getSelection().get('facilityId');
			if (facilityIdVal == 'undefined') {
				facilityIdVal = selSeachFacId;
			}
	    	facilityNameVal = Ext.getCmp('facilitySearchBoxId').getSelection().get('selFacName');
	    	if (undefined == facilityNameVal || facilityNameVal == 'undefined') {
				facilityNameVal = selSeachFac;
			}
		}
	}
	//Set the corporation field's values.
	if(null != corpSearchbox && 'null' != selSearchCorp && ' ' != selSearchCorp && undefined != selSearchCorp && 'undefined' != selSearchCorp) {
		corpSearchbox.getStore().loadRawData(Ext.decode('({\"total\":\"1\",\"ListInfo\":[{"corporationName":"'+selSearchCorp+'","corporationId":"'+selSearchCorpId+'"}]})'));
		corpSearchbox.select(selSearchCorp);
		if(null !=  Ext.getCmp('corpSearchboxId').getSelection()){
			corpIdVal = Ext.getCmp('corpSearchboxId').getSelection().get('corporationId');
			if (corpIdVal == 'undefined') {
				corpIdVal = selSearchCorpId;
			}
			corpNameVal = Ext.getCmp('corpSearchboxId').getSelection().get('selCorpName');
	    	if (undefined == corpNameVal || corpNameVal == 'undefined') {
	    		corpNameVal = selSearchCorp;
			}
		}
	}
	});
}
function populateFacilities(response, options) {
    if (response.responseText.indexOf('errorMessage') != -1) {
    } else {
    	setTimeout(function() {
        var combo = Ext.getCmp('facilitySearchBoxId');
        var comboStore = combo.getStore();
        if (undefined != Ext.getCmp('facilitySearchBoxId').getValue()) {
            var value = Ext.getCmp('facilitySearchBoxId').getValue().trim();
            if (value.length > 2) {
                comboStore.loadRawData(Ext.decode(response.responseText));
                combo.expand();
            } else {
                combo.collapse();
            }
        }
      },1000);
    }
}

function showHideDiv(searchType){
	if('Patient' == searchType){
		document.getElementById('patDiv').style.display = 'inline';
		document.getElementById('patientDiv').style.display = 'inline';
		document.getElementById('staffDiv').style.display = 'none';
		document.getElementById('staffName').value = '';
		document.getElementById('reqDiv').style.display = 'none';
		document.getElementById('reqNumber').value = '';
		//document.getElementById('searchInputDiv').value = '';
		document.getElementById('facDiv').style.display = 'none';
		//<!--  timc 
		document.getElementById('eqpDiv').style.display = 'none';	
		if(null != facilitySearchBox) {
			facilitySearchBox.setRawValue('');
		}
	}
	if('Staff' == searchType){
		document.getElementById('patDiv').style.display = 'inline';
		document.getElementById('patientDiv').style.display = 'none';
		document.getElementById('patientName').value = '';
		document.getElementById('staffDiv').style.display = 'inline';
		document.getElementById('reqDiv').style.display = 'none';
		document.getElementById('reqNumber').value = '';
		//document.getElementById('searchInputDiv').value = '';
		document.getElementById('facDiv').style.display = 'none';
		//<!--  timc
		document.getElementById('eqpDiv').style.display = 'none';
		if(null != facilitySearchBox) {
			facilitySearchBox.setRawValue('');
		}
		document.getElementById('patientDOB').value = '';  // timc
	}
	if('Requisition' == searchType){
		document.getElementById('patDiv').style.display = 'none';
		document.getElementById('reqDiv').style.display = 'inline';
		document.getElementById('patientName').value = '';
		document.getElementById('staffDiv').style.display = 'none';
		document.getElementById('staffName').value = '';
		//document.getElementById('searchInputDiv').value = '';
		document.getElementById('facDiv').style.display = 'none';
		//<!--  timc
		document.getElementById('eqpDiv').style.display = 'none';
		if(null != facilitySearchBox) {
			facilitySearchBox.setRawValue('');
		}
		document.getElementById('patientDOB').value = '';  // timc
	}
	/*if('Select' == searchType){
		document.getElementById('patDiv').style.display = 'none';
		//document.getElementById('searchDiv').style.display = 'inline';
		document.getElementById('reqDiv').style.display = 'none';
		document.getElementById('patientName').value = '';
		document.getElementById('staffDiv').style.display = 'none';
		document.getElementById('staffName').value = '';
		document.getElementById('reqNumber').value = '';
		document.getElementById('facDiv').style.display = 'none';
		//<!--  timc
		document.getElementById('eqpDiv').style.display = 'none';
		if(null != facilitySearchBox) {
			facilitySearchBox.setRawValue('');
		}
	}*/
	if ('Facility' == searchType) {
		document.getElementById('patDiv').style.display = 'none';
		document.getElementById('staffDiv').style.display = 'none';
		document.getElementById('staffName').value = '';
		document.getElementById('facDiv').style.display = 'inline';
		document.getElementById('reqDiv').style.display = 'none';
		document.getElementById('reqNumber').value = '';
		//document.getElementById('searchInputDiv').value = '';
		//<!--  timc
		document.getElementById('eqpDiv').style.display = 'none';
		if(null != corpSearchbox) {
			corpSearchbox.setRawValue('');
		}
		document.getElementById('patientDOB').value = '';  // timc
	}
	//<!--  timc
	if('Equipment' == searchType){
		document.getElementById('eqpDiv').style.display = 'inline';
		document.getElementById('patDiv').style.display = 'inline';
		document.getElementById('patientDiv').style.display = 'none';
		document.getElementById('facDiv').style.display = 'none';	
		document.getElementById('staffDiv').style.display = 'none';
		document.getElementById('reqDiv').style.display = 'none';
		document.getElementById('patientName').value = '';
		document.getElementById('reqNumber').value = '';
 		//document.getElementById('searchInputDiv').value = '';
		if(null != facilitySearchBox) {
			facilitySearchBox.setRawValue('');
		}
		document.getElementById('patientDOB').value = '';  // timc
	}
}
//Function to display the search results.
function validateSearch() {
	var searchPatient = false;
    var isValidate = true;
    var searchTypeEle = document.getElementById("selectFieldnew");
    var searchTypeVal = searchTypeEle.options[searchTypeEle.selectedIndex].value;
    
    if('Patient' == searchTypeVal){
	    var patientName = document.getElementById('patientName').value;
    	searchPatient = true;
        var inpVal = document.getElementById('patientName').value.trim().split(' ');
        if (!isValidate) {
	    	showMsgBox(title, msg, 'patientName');
	    }
        
// timc fix for entering spaces 3/21/16   
	    if ( !Ext.isEmpty(patientName) && ((patientName.length == 1 && patientName == ' ') || (patientName.length > 1 && patientName.substring(0, 2) == '  ')) ) {
	    	isValidate = false;
	    	showMsgBox('Invalid Patient Name', 'The Patient Name entered is invalid... Please try again!', 'patientName');
	    }
// timc   
    	if (Ext.isEmpty(patientName) && (null == Ext.getCmp('facilitySearchBoxId').getValue()
				|| Ext.isEmpty(Ext.getCmp('facilitySearchBoxId').getValue()))) {
			isValidate = false;
			showMsgBox('Invalid  Patient/Facility Name', 'Please enter a Facility or Patient Name', 'patientName');
		}
// timc        
        var patientDOB = '';
        if(null != document.getElementById("patientDOB") && document.getElementById("patientDOB").value.length > 0){
        	patientDOB = document.getElementById("patientDOB").value;
		    if (patientDOB.length > 0 && patientDOB.length < 7) {
		    	isValidate = false;
		    	showMsgBox('Invalid Date Of Birth', 'The Date Of Birth (mmddyyyy) entered is invalid. Please try again!', 'patientDOB');
		    } 
		    var day, 
	        month,   
	        year;   
		    month = patientDOB.substring(0, 2) - 0;  
		    day   = patientDOB.substring(2, 4) - 0; 
		    year  = patientDOB.substring(4, 8) - 0; 
		    if (year < 1000 || year > 3000 || month == 0 || month > 12) { 
		    	isValidate = false;
		    	showMsgBox('Invalid Date Of Birth', 'The Date Of Birth (mmddyyyy) entered is invalid. Please try again!', 'patientDOB');
		    } 
		    var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
		    // Adjust for leap years
		    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
		        monthLength[1] = 29;
		    if (day > 0 && day <= monthLength[month - 1]) { 
		    } else {
		    	isValidate = false;
		    	showMsgBox('Invalid Day Of Birth', 'The Day Of Birth (mmddyyyy) entered is invalid. Please try again!', 'patientDOB');
		    }
		    var d = new Date(month+"/"+day+"/"+year);
		    var today = new Date();
		    if (d > today) { 
		    	isValidate = false;
		    	showMsgBox('Invalid Day Of Birth', 'The Date Of Birth (mmddyyyy) entered is invalid... (> today)  Please try again!', 'patientDOB');
		    }		    
        }
// timc
        
    } else if('Requisition' == searchTypeVal){
    	var reqName = document.getElementById('reqNumber').value.trim();
        if (reqName.length < 7) {
        	isValidate = false;
        	showMsgBox('Invalid Requisition #', 'The Requisition # entered is invalid. Please try again!', 'reqNumber');
        } 
    } else if('Staff' == searchTypeVal){
	    var staffName = document.getElementById('staffName').value;
	    	if (Ext.isEmpty(staffName) 	&& (null == Ext.getCmp('facilitySearchBoxId').getValue()
	    								|| Ext.isEmpty(Ext.getCmp('facilitySearchBoxId').getValue()))) {
	    		isValidate = false;
	    		showMsgBox('Invalid Staff Name', 'Please enter a Facility or Staff Name', 'staffName');
	    	}
//	    }
	//<!--  timc	    
	} else if('Equipment' == searchTypeVal){
		
		if (Ext.getCmp('facilitySearchBoxId').getValue() == null) {
			title = 'Missing Facility Info';
			msg = 'Please enter a Facility Name.';   //msg = 'Please enter a facility Name.';
			isValidate = false;	
			showMsgBox(title, msg, 'eqpNumber');
		}else {
			var fName = Ext.getCmp('facilitySearchBoxId').getValue().trim();
			//if (fName == "") {
			if (Ext.isEmpty(fName)) {    	  
				title = 'Missing Facility Info';
				msg = 'Please enter a Facility Name.';   //msg = 'Please enter a facility Name.';
				isValidate = false;
			}
			if (!isValidate) {
				showMsgBox(title, msg, 'eqpNumber');
			}
		}
		
	}
    return isValidate;
}
//Function to show message box and focus the element.
function showMsgBox(title, message, element) {
    /*Ext.Msg.show({
        title: title,
        msg: message,
        shadow : false,
        buttons: Ext.Msg.OK,
        width: 400,
        //cls: 'popup pad closeCls windowCls',
        fn: function() {
            setFocus(element);
        }
    });*/
    
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
                        setFocus(element);
                   }
                }]
    }).show();
}
function setFocus(elementId) {
    if (undefined != elementId && null != elementId && '' != elementId) {
        if (undefined != Ext.getCmp(elementId) && null != Ext.getCmp(elementId)) {
            Ext.getCmp(elementId).focus();
        }else if (undefined != Ext.get(elementId) && null != Ext.get(elementId)) {
            Ext.get(elementId).focus();
        }
    }
}
function setSelectedValue(selectObj, valueToSet) {
    for (var i = 0; i < selectObj.options.length; i++) {
        if (selectObj.options[i].value== valueToSet) {
            selectObj.options[i].selected = true;
            return;
        }
    }
}
function onlyAlphaNumericNoSpace(e) {
    var keynum;
    // IE
    if (window.event) {
        keynum = e.keyCode;
    } else if (e.which) { // Netscape/Firefox/Opera
        keynum = e.which;
    }
    // Not allowing the printable special charatcers.
    if ((keynum > 31 && keynum <= 47) || (keynum >= 58 && keynum <= 64) || (keynum >= 91 && keynum <= 96) || (keynum >= 123 && keynum <= 126) || (keynum == 13) || (keynum == 3)) {
        return false;

    }
}
function onlyNumericNoSpace(e) {
    var keynum;
    // IE
    if (window.event) {
        keynum = e.keyCode;
    } else if (e.which) { // Netscape/Firefox/Opera
        keynum = e.which;
    }
    //alert ('keynum : ' + keynum);
    // Not allowing the printable special charatcers.
    //if ((keynum < 48 && keynum > 57) || (keynum == 32) ) {
    if ( (keynum != 8) && ((keynum < 48 || keynum > 57) || (keynum == 32) )  ) {
        return false;
    }
    document.getElementById("dobButt").disabled = false;
}
//Function to clear th entererd search criteria values.
function clearSearchValues() {
	if (null != document.getElementById("patientName").value) {
		document.getElementById("patientName").value = "";
	}
	if (null != document.getElementById("facName").value) {
		document.getElementById("facName").value = "";
	}
	if (null != document.getElementById("staffName").value) {
		document.getElementById("staffName").value = "";
	}
	if (null != document.getElementById("eqpNumber").value) {
		document.getElementById("eqpNumber").value = "";
	}		
}
//timc 
//Function to clear th entererd search criteria values.
function clearSearchValuesDOB() {
	if (null != document.getElementById("patientDOB").value) {
		document.getElementById("patientDOB").value = "";
	    document.getElementById("dobButt").disabled = false;
	}
}
//timc 

//Function to search Patient.
function searchPatDet() {
 	if (!validateSearch()) {
		return false;
	}
	var patientName = ''; 
	var facilityId = '';
	var facilityName = '';
	
// timc 
	var patientDOB = '';
	if(null != document.getElementById("patientDOB")){
		patientDOB = document.getElementById("patientDOB").value;
	}
	//alert("patientDOB : "+patientDOB); 
//timc 

	if(null != document.getElementById("patientName")){
		patientName = document.getElementById("patientName").value;
	}
	var comboValue = Ext.getCmp('facilitySearchBoxId').getValue();
	//alert("searchPatDet "+comboValue); 
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
	document.getElementById("processorName").value = 'PatientProcessor';
	document.getElementById("processorAction").value = 'getSearchResult';
	document.getElementById("facilityId").value = facilityId;
	document.getElementById("facilityName").value = facilityName;
	document.getElementById("searchType").value = document.getElementById("selectFieldnew").value;
	document.getElementById("searchForm").submit();
}


//Function to search Staff.
function searchStaffDet() {
 	if (!validateSearch()) {
		return false;
	}
	var patientName = '';
	var facilityId = '';
	var facilityName = '';
	if(null != document.getElementById("staffName")){
		patientName = document.getElementById("staffName").value;
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
	document.getElementById("processorName").value = 'StaffProcessor';
	document.getElementById("processorAction").value = 'getSearchResult';
	document.getElementById("facilityId").value = facilityId;
	document.getElementById("facilityName").value = facilityName;
	document.getElementById("searchType").value = document.getElementById("selectFieldnew").value;
	document.getElementById("searchForm").submit();
}

//Function which loads the corporation store.
function populateCorporations(response) {
	if (response.responseText.indexOf('errorMessage') != -1) {
    } else {
    	setTimeout(function() {
        var combo = Ext.getCmp('corpSearchboxId');
        var comboStore = combo.getStore();
        if (undefined != Ext.getCmp('corpSearchboxId').getValue()) {
            var value = Ext.getCmp('corpSearchboxId').getValue().trim();
            if (value.length > 2) {
          //  	 console.log(Ext.decode(response.responseText));
                comboStore.loadRawData(Ext.decode(response.responseText));
               
                combo.expand();
            } else {
                combo.collapse();
            }
        }
      },1000);
    }
}
//Function to load the facility search details.
function searchFacilityDetails() {
	var facilityName = '';
	var corporationId = '';
	var corporationName = '';
	if(null != document.getElementById("facName")){
		facilityName = document.getElementById("facName").value;
	}
	var comboValue = Ext.getCmp('corpSearchboxId').getValue();
	if (null == comboValue || "" == comboValue || undefined == comboValue || 'undefined' == comboValue) {
		corpIdVal = 0;
		corpNameVal = "";
	} else {
		if(null != Ext.getCmp('corpSearchboxId') && null != Ext.getCmp('corpSearchboxId').getSelection()){
			if (undefined == corpIdVal || 'undefined' == corpIdVal) {
				corporationId = Ext.getCmp('corpSearchboxId').getSelection().get('corporationId');
			} else {
				corporationId = corpIdVal;
			}
			if (undefined == corpNameVal || 'undefined' == corpNameVal) {
				corporationName = Ext.getCmp('corpSearchboxId').getSelection().get('selCorpName');
			} else {
				corporationName = corpNameVal;
			}
		}
	}
	if ((facilityName.length < 3 && corporationId <= 0)) {
		var title = 'Invalid Search Parameter';
        var  msg = 'Please enter a Corporation Name or at least 3 characters for Facility Name.';
		showMsgBox(title, msg, 'facName');
		return false
	} else if (corporationId > 0 && facilityName != "" && facilityName != null && facilityName.length < 3) {
		var title = 'Invalid Search Parameter';
        var  msg = 'Please enter at least 3 characters for Facility Name.';
		showMsgBox(title, msg, 'facName');
	} else if (corporationId > 0 || facilityName.length > 2) {
		//DB call.
		document.getElementById("processorName").value = 'FacilityProcessor';
		document.getElementById("processorAction").value = 'getFacSearchResults';
		document.getElementById("corporationId").value = corporationId;
		document.getElementById("corporationName").value = corporationName;
		document.getElementById("facilityName").value = facilityName;
		document.getElementById("searchType").value = document.getElementById("selectFieldnew").value;
		document.getElementById("userName").value = document.getElementById("userName").value;
		document.getElementById("searchForm").submit();
		
	}
}