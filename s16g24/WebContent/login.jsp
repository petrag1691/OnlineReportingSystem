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
<!--  xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html">-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Login Page</title>
<link rel="stylesheet" href="bootstrap.css" type="text/css" />
</head>
<body class="background">

	<div class="cusdivlogin" style="margin: 10% auto auto auto;">
		<h3>Welcome to S16G24 group project</h3>
			<p style="position: relative; left: 25%; align: center;">Please
			enter the Database Credentials to login</p>
	 <div style="position:relative;left:20%;align:center;">
			<f:view>
				<h:form>
					<a class="btn btn-primary"
					style="position: relative; left: -20%; align: center;"
					href="index.html">Home</a>
				<h:outputLink style="position:relative;left:10%;color: #026DA2;
    text-decoration: underline;font-size: large;" value="#{request.contextPath}/resources/USERGUIDE.pdf" target="_blank">User Documentation</h:outputLink>
					<h:messages style="position:relative;align:left;color:red;word-wrap:break-word;"></h:messages>
					<h:panelGrid columns="2">
						<h:outputText>Host:</h:outputText>
						<h:selectOneMenu id="url" value="#{userBean.url}">
							<f:selectItem itemValue="localhost" itemLabel="localhost" />
							<f:selectItem itemValue="131.193.209.54" itemLabel="54 Server" />
							<f:selectItem itemValue="131.193.209.57" itemLabel="57 Server" />
						</h:selectOneMenu>
						<h:outputText>DBMS:</h:outputText>
						<h:selectOneMenu id="dbms" value="#{userBean.dbms}">
							<f:selectItem itemValue="mysql" itemLabel="MYSQL" />
							<f:selectItem itemValue="sql" itemLabel="SQL" />
							<f:selectItem itemValue="db2" itemLabel="DB2" />
							<f:selectItem itemValue="oracle" itemLabel="Oracle" />
						</h:selectOneMenu>
						<h:outputText>Port:</h:outputText>
						<h:inputText id="port" value="#{userBean.port}" />
						<h:outputText>*DB Schema:</h:outputText>
						<h:inputText id="dbschema" value="#{userBean.db_schema}"
							required="true" requiredMessage="db schema is a required field"></h:inputText>
						<h:outputText>*DB Username:</h:outputText>
						<h:inputText id="dbuser" value="#{userBean.username}"
							required="true" requiredMessage="username is a required field"></h:inputText>
						<h:outputText>*DB Password:</h:outputText>
						<h:inputSecret id="dbpassword" value="#{userBean.password}"
							required="true" requiredMessage="password is a required field"></h:inputSecret>
					</h:panelGrid>
					<div style="position: relative; left: 25%; align: center;">
						<h:commandButton styleClass="btn btn-primary" type="submit"
							value="Login" action="#{loginBean.processLogin}"></h:commandButton>
					</div>
				</h:form>
				
			</f:view>
		</div>
	</div>
</body>
	</html>
</jsp:root>