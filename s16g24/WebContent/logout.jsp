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
	<html xmlns="http://www.w3.org/1999/xhtml"
		xmlns:f="http://java.sun.com/jsf/core"
		xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Welcome to S16g24 project</title>
<link rel="stylesheet" href="bootstrap.css" type="text/css" />
</head>
<body class="background">
	<div class="cusdivlogin" style="margin: 10% auto auto auto">
		<h2>Welcome to S16G24 group project</h2>
		<h3>Successfully logged out of Database</h3>
		<f:view>
			<h:form>
				<!--<h:outputText styleClass="cusdiv1">Hit Login button to go to login page</h:outputText> -->
				<h:commandButton styleClass="btn" style="position:relative;left:37%"
					value="Go to Login Page" action="login.jsp"></h:commandButton>
			</h:form>
		</f:view>
	</div>
</body>
	</html>
</jsp:root>