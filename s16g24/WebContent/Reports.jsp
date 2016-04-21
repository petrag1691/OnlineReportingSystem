<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:t="http://myfaces.apache.org/tomahawk" version="2.0">
	<jsp:directive.page language="java"
		contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="ISO-8859-1" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml"
		xmlns:f="http://java.sun.com/jsf/core"
		xmlns:h="http://java.sun.com/jsf/html"
		xmlns:t="http://myfaces.apache.org/tomahawk">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Reports</title>
</head>
<body class="background">
	<link rel="stylesheet" href="bootstrap.css" type="text/css" />
	<f:view>
		<div
			style="position: relative; left: 35%; top: 20%; margin: 5% auto auto auto;">
			<h2 style="position:relative;align:center;left:-1	%;">Welcome to World Reports</h2>
		</div>
		<h:form>
			<div class="cusdivlogin"
				style="position: relative; align: center; height: 400px; width: 400px; margin: auto auto auto auto">
				<p style="position: relative; left: 40%; margin: 0 auto auto auto; font-weight: bold;font-size:20px;">S16G24</p>
				<a class="btn btn-primary"
					style="position: relative; left: 0%; align: center;"
					href="PostLogin.jsp">Home</a>
				<h:commandButton styleClass="btn btn-primary"
					style="position:relative;left:65%;align:center;" type="submit"
					value="Log Out" action="#{loginBean.logout}" />
				<p></p>
				<br />
				<h:outputLabel styleClass="label label-info"
					style="position:relative;left:24%;align:center;font-size:18px;margin: auto auto auto auto">Select a report to view</h:outputLabel>
				<br /> <br />
				<h:selectOneMenu style="position:relative;left:24%;align:center;"
					id="reportSelection" value="#{reportActionBean.reportNumber}">
					<f:selectItem itemValue="1" itemLabel="City Population Report" />
					<f:selectItem itemValue="2" itemLabel="Country Life Expectancy Report" />
					<f:selectItem itemValue="3" itemLabel="Country GNP Report" />
					<f:selectItem itemValue="4" itemLabel="CountryLangauage Report" />
				</h:selectOneMenu>
				<br /> <br />
				<h:commandButton styleClass="btn"
					style="position:relative;left:35%;align:center;" type="submit"
					value="Generate Report" action="#{reportActionBean.getResults}" />
				<br /> <br />
				<h:dataTable styleClass="table table-condensed"
					value="#{reportActionBean.resultMap.entrySet().toArray()}"
					var="entry" rendered="#{reportActionBean.renderList}" border="1"
					cellspacing="0" cellpadding="1">
					<h:column id="column1">
						<h:outputText value="#{entry.key}"></h:outputText>
					</h:column>
					<h:column id="column2">
						<h:outputText value="#{entry.value}"></h:outputText>
					</h:column>`
			   </h:dataTable>
			</div>
		</h:form>
	</f:view>
</body>
	</html>
</jsp:root>