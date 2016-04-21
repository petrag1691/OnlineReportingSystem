<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html" version="2.0">
	<jsp:directive.page language="java"
		contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="ISO-8859-1" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Post Login</title>
</head>
<link rel="stylesheet" href="bootstrap.css" type="text/css" />
<body class="background">
	<div
		style="position: relative; left: 41.5%; top: 20%; margin: 10% auto auto auto;">
		<h2 style="position: relative; left: -1.5%;">World Database</h2>
	</div>
	<div class="cusdivlogin"
		style="position: relative; align: center; height: 150px; width: 800px; margin: auto auto auto auto">
		<f:view>
			<h:form>
				<h3
					style="position:relative; left:36%; margin: auto auto auto auto;">Welcome
					s16g24</h3>
				<h:commandButton styleClass="btn btn-primary"
					style="position:relative;left:90%;align:center;" type="submit"
					value="Log Out" action="#{loginBean.logout}" />
			</h:form>
			<div class="btn-toolbar">
			<a class="btn" 
				href="Reports.jsp">Reports</a>
			
			<a class="btn" 
				href="DatabaseAccess.jsp">Database Access Menu</a>
			
			<a class="btn" 
				href="DataAnalysis.jsp">Data Analysis</a>
			
			<a class="btn"
				href="DatabaseImportExport.jsp">Data Import and Export</a>
			<a class="btn"
				href="AccessLog.jsp">Access Log</a>
				</div>
		</f:view>
	</div>
</body>
	</html>
</jsp:root>