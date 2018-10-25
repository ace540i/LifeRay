<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.liferay.portal.kernel.template.TemplateHandler" %>
<%@ page import="com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil" %>
<%@ page import="com.liferay.portal.kernel.exception.PortalException" %>
<%@ page import="com.liferay.portal.kernel.exception.SystemException" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.model.Group"%>
<%@ page import="com.liferay.portal.security.permission.ActionKeys"%>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil" %>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/Resources/ext-5.1.0/build/packages/ext-theme-classic/build/resources/ext-theme-classic-all.css" />
<link rel="stylesheet" type="text/css" href="/Resources/ext-5.1.0/build/packages/sencha-charts/build/classic/resources/sencha-charts-all.css" />
<script language="javascript" type="text/javascript" src="/Resources/ext-5.1.0/build/ext-all.js"></script>
<script language="javascript" type="text/javascript" src="/Resources/ext-5.1.0/packages/ext-charts/build/ext-charts.js"></script>
<script language="javascript" type="text/javascript" src="/Resources/ext-5.1.0/build/packages/sencha-charts/build/sencha-charts.js"></script>

<script type="text/javascript">
	var contextPath = "<%=request.getContextPath()%>";
    var timestamp = new Date().getTime();
    <%
    Date dateValue = new Date();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    String dateStr = dateFormat.format(dateValue);
    %>
    var dateValue = '<%=dateStr.toString()%>';
    //Append timestamp to the JavaScript/CSS file name so the file is not cached by the browser and everytime the file is picked up.
    document.write('<link rel="stylesheet" type="text/css" href="'+contextPath+'/searchresultscss/messageBox.css?'+dateValue+'">');
</script>

<liferay-theme:defineObjects />

<portlet:defineObjects />
</head>
</html>