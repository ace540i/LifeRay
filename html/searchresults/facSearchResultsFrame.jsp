<!DOCTYPE html>
<%@include file="/html/init.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Search</title>
<script type="text/javascript">
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/bootstrap.min.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/symfonie-labs.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/theme_styles.css?'+dateValue+'">');
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/css/ext5ExtendedStyles.css?'+dateValue+'">');
    document.write('<script type="text/javascript" src="'+contextPath+'/js/facSearchResults.js?'+dateValue+'"><\/script>');
</script>
<script type="text/javascript">
function getResults(){
	parent.document.searchResultsForm.loadPage.value = "reqDetailPage";
	parent.document.searchResultsForm.submit();
}
var url = parent.facSearchResultsURL;

</script>
</head>
<body class="theme-whbl  pace-done" onload="showFacSearchResults();">

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
                        <div>
                       
                          <div id="facSearchResultsGrid"></div>
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
</body>
</html>