<!DOCTYPE html>
<!-- 12/22/2015 - md - US1087   
    			  1) New jsp Frame for Staff Results.
-->
<%@include file="/html/init.jsp" %>
<html>
<head>
<!-- Start of JSP search/staffResults.jsp -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Search</title>
<portlet:actionURL name="sendResultsRequest" var="sendResultsRequest"></portlet:actionURL>  
<portlet:resourceURL var="getStaffDetails" id="getStaffDetails" />
<script type="text/javascript">
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/bootstrap.min.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/symfonie-labs.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/theme_styles.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/css/ext5ExtendedStyles.css?'+dateValue+'">');
    document.write('<script type="text/javascript" src="'+contextPath+'/js/SearchResult.js?'+dateValue+'"><\/script>');
</script>
<script type="text/javascript">

function getResults(){
	parent.document.searchResultsForm.loadPage.value = "reqDetailPage";	
	parent.document.searchResultsForm.submit();
}

</script>
</head>
<body class="theme-whbl  pace-done" onload="showStaffResGrid();">
<form method="post" name="searchResultsFrameForm" id="searchResultsFrameForm" action="<%=sendResultsRequest%>">

<div id="theme-wrapper">
		<div id="content-wrapper">
          <div class="col-lg-12">
            <div class="row">
              <div class="col-md-9 col-lg-9 med">
                <div class="main-box clearfix">
                  <div class="main-box-body clearfix">
                    <div class="conversation-content">
					<header class="main-box-header clearfix">
<%--                     <h2 class="orange-h1" style="margin: 0px;"><img src="<%=request.getContextPath()%>/images/search-results.jpg" width="24" height="23" alt=""/>Search Results</h2> --%>
                  </header>
                      <div class="main-box-body clearfix">
     
                      <div class="conversation-content">
                        <div class="  martp25">
                       
                          <div id="staffSearchGrid"></div>
                        </div>
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