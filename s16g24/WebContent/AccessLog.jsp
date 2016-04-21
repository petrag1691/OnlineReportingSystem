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
	<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html"
		xmlns:t="http://myfaces.apache.org/tomahawk">>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title>Access Log</title>
			<link rel="stylesheet" href="bootstrap.css" type="text/css" />
			
		</head>
		<body class="background">
			<f:view>
				<div style="position: relative; left: 35%; top: 20%; margin: 2% auto auto auto;">
					<h2 style="position:relative;align:center;left:-1	%;">Welcome to Database Analysis Menu</h2>
				</div>
				<h:form>
					<pre class="prediv">
						<h:messages styleClass="prediv" style="white-space:wrap;" rendered="#{dataAnalysisActionBean.renderErrorMessages}"></h:messages>
					</pre>	
					<div class="cusdivlogin"
						style="position: relative; align: center; height: auto; width: 1000px; margin: auto auto auto auto">
						<p style="position: relative; left: 46.5%; margin: 0 auto auto auto; font-weight: bold;font-size:20px;">S16G24</p>
						<a class="btn btn-primary"
							style="position: relative; left: -0%; align: center;"
							href="PostLogin.jsp">Home</a>
						<h:commandButton styleClass="btn btn-primary"
							style="position:relative;left:85.5%;align:center;" type="submit"
							value="Log Out" action="#{loginBean.logout}" />
						<br />
						<div align="center">
						<p style="position: relative; left: 0%; margin: 0 auto auto auto; font-weight: bold;font-size:20px;">Access Log</p>
							<br/>
							<h:panelGrid styleClass="table table-condensed tablesp" columns="2" border='1'>
								<h:outputText value="Username"/>
								<h:outputText value="#{userBean.username}"/>
								<h:outputText value="Host"/>
								<h:outputText value="#{userBean.url}"/>
								<h:outputText value="DBMS"/>
								<h:outputText value="#{userBean.dbms}"/>
								<h:outputText value="Schema"/>
								<h:outputText value="#{databaseAccessBean.selectedDatabase}"/>
								<h:outputText value ="IP Address"/>
								<h:outputText value ="#{ipbean.ipAddress}"/>
								<h:outputText value="Logged in at"/>
								<h:outputText value="#{ipbean.loginTime}"/>
								<h:outputText value="Last Logged in at"/>
								<h:outputText value="#{ipbean.lastLoginTime}"/>
								<h:outputText value="Last Logged out at"/>
								<h:outputText value="#{ipbean.lastLogoutTime}"/>
							</h:panelGrid>
						</div>
					</div>
				</h:form>
			</f:view>
		</body>
	</html>
</jsp:root>