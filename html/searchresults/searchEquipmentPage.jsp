<!DOCTYPE html>
<%@include file="/html/init.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Search</title>
<portlet:actionURL name="sendResultsRequest" var="sendResultsRequest"></portlet:actionURL>  
<script type="text/javascript">
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/bootstrap.min.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/symfonie-labs.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/theme_styles.css?'+dateValue+'">');
    document.write('<script type="text/javascript" src="'+contextPath+'/js/SearchResult.js?'+dateValue+'"><\/script>');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/css/ext5ExtendedStyles.css?'+dateValue+'">');
</script>
<script type="text/javascript">
	function getResults(){
		  parent.document.searchResultsForm.loadPage.value = "searchEquipmentPage";  
	}
</script>

</head>
<body class="theme-whbl  pace-done" onload="showEquipmentResGrid();"> 		 

<form method="post" name="searchEquipmentFrameForm" id="searchEquipmentFrameForm" action="<%=sendResultsRequest%>">

<div id="theme-wrapper">
		<div id="content-wrapper">
          <div class="col-lg-12">
            <div class="row">
              <div class="col-md-9 col-lg-9 med">
                <div class="main-box clearfix">
                  <div class="main-box-body clearfix">
                    <div class="conversation-content">
<!-- 					<header class="main-box-header clearfix"> -->
<%--                      <h2 class="orange-h1" style="margin: 0px;"><img src="<%=request.getContextPath()%>/images/search-results.jpg" width="20" height="20" alt=""/>&nbsp;Search Results</h2> --%>
<!--                     </header> -->
                    <div class="main-box-body clearfix">
                    <div class="conversation-content">
                      <!--     <div class="  martp25"> -->
                          <div id="equipmentSearchGrid"></div>
                      <!--     </div> -->
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
<script src="<%=request.getContextPath()%>/js/jquery.js"></script> 
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script> 
<script src="<%=request.getContextPath()%>/js/scripts.js"></script> 
<script src="<%=request.getContextPath()%>/js/pace.min.js"></script>
</form>
</body>
</html>