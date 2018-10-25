<!DOCTYPE html>
<%@include file="/html/init.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<script type="text/javascript">
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<script type="text/javascript" src="'+contextPath+'/js/dashboard.js?'+dateValue+'"><\/script>');
</script>
<script type="text/javascript">
var url = parent.dashboardURL;
</script>
<style type="text/css">
	#labChart div{
		border: 0px none !important;
	}
	#labChartLegend {
		right: 23px !important;
		left: auto !important;
	}
	#comboPanel-innerCt{
		margin-bottom: -28px;
		z-index: 2;
	}
	#comboPanel {
		margin-bottom: -28px;
		z-index: 2;
		padding-left: 40px;
	}
	#comboPanel-body{
		display: inline;
	}
 </style>
</head>
<body class="theme-whbl  pace-done" onload="showCharts();">
<form method="post" name="searchResultsFrameForm" id="searchResultsFrameForm" action="">

<div id="theme-wrapper">
      <div id="content-wrapper">
          <div class="col-lg-12">
            <div class="row">
              <div class="col-md-9 col-lg-9 med">
                <div class="main-box clearfix">
                  <div class="main-box-body clearfix">
                    <div class="conversation-content">
                      <div class="main-box-body clearfix">
     
                      <div class="conversation-content">
                      <div id="labComboId" class="pull-right"></div>
                       <div id="chartDiv"></div>
                        <div class="clearfix"></div>
                    </div>
                </div>
                    </div>
                  </div>
                </div>
              </div>
            
              </div>
            </div>
          </div>
        </div>
</form>
</body>
</html>