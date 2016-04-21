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
<title>Database Access</title>
<link rel="stylesheet" href="bootstrap.css" type="text/css" />
</head>
<body class="background">
<f:view>
	<div
		style="position: relative; left: 35%; top: 20%; margin: 2% auto auto auto;">
		<h2 style="position:relative;align:center;left:-1	%;">Welcome to Database Access Menu</h2>
	</div>
	<h:form>
		<pre class="prediv">
			<h:messages styleClass="prediv"  rendered="#{databaseActionBean.renderErrorMessages}"></h:messages>
		</pre>	
		<div class="cusdivlogin"
			style="position: relative; align: center; height: auto; width: 1000px; margin: auto auto auto auto">
			<p style="position: relative; left: 50%; margin: 0 auto auto auto; font-weight: bold;font-size:20px;">S16G24</p>
			<a class="btn btn-primary"
				style="position: relative; left: -0%; align: center;"
				href="PostLogin.jsp">Home</a>
			<h:commandButton styleClass="btn btn-primary"
				style="position:relative;left:85.5%;align:center;" type="submit"
				value="Log Out" action="#{loginBean.logout}" />
			<br />
			<h:outputLabel styleClass="label label-info"
				style="position:relative;left:44.5%;align:center;font-size:18px;margin: auto auto auto auto">Select a database</h:outputLabel>
			<br /> <br />
			<h:selectOneMenu style="position:relative;left:43%;align:center;font-size:18px;margin: auto auto auto auto" value="#{dBAccessBean.database}">
				<f:selectItems value="#{dBAccessBean.databases}"></f:selectItems> 
			</h:selectOneMenu>
			<hr/>
			<div class="cmdButtons" align="center">
				<h:panelGrid styleClass ="btn-toolbar" columns="7">
					<h:commandButton styleClass ="btn" type="submit" value="TableList" action="#{databaseActionBean.getTableNames}" />
					<h:commandButton styleClass ="btn" type="submit" value="ColumnList" action="#{databaseActionBean.getTableColumns}" />
					<h:commandButton styleClass ="btn" type="submit" value="DisplayTable" action="#{databaseActionBean.displayTable}" />
					<h:commandButton styleClass ="btn" type="submit" value="DisplaySelectedColumns" action="#{databaseActionBean.displaySelectedColumns}" />
					<h:commandButton styleClass ="btn" type="submit" value="ProcessSQLQuery" action="#{databaseActionBean.processSqlQuery}" />
					<h:commandButton styleClass ="btn" type="submit" value="CloneTable" action="#{databaseActionBean.createTable}" rendered="#{dBAccessBean.renderClonePermissions}"/>
					<h:commandButton styleClass ="btn" type="submit" value="CreateTable" action="#{databaseActionBean.createTable}" rendered="#{dBAccessBean.renderPermissions}"/>
					<h:commandButton styleClass ="btn" type="submit" value="DropTable" action="#{databaseActionBean.dropTable}" rendered="#{dBAccessBean.renderPermissions}"/>
				</h:panelGrid>
			</div>
			<div class="displayPortions" align="center">
				<h:panelGrid columns="4" >
					<h:outputText value="Tables" rendered="#{databaseActionBean.renderTables}" style="margin-left:50px;margin-right: 50px;"></h:outputText>
				    <h:outputText value="Columns" rendered="#{databaseActionBean.renderColumns}" style="margin-left: 100px;margin-right: 50px;" ></h:outputText>
				    <h:outputText value="Query" style="margin-left: 100px;"></h:outputText>
				</h:panelGrid>
				<h:panelGrid columns="3">
					<h:selectOneListbox id="tableSelection" rendered="#{databaseActionBean.renderTables}" value="#{dBAccessBean.selectedTable}" size="4" >
					    <f:selectItems value="#{dBAccessBean.tables}" />
					</h:selectOneListbox>
					<h:selectManyListbox id="columnSelection" value="#{dBAccessBean.selectedDisplayColumns}" rendered="#{databaseActionBean.renderColumns}" size="4" >
					    <f:selectItems value="#{dBAccessBean.displayColumns}"/>
					</h:selectManyListbox>
					<h:inputTextarea rows="4" cols="50" value="#{dBAccessBean.inputQuery}" readonly="false"/>
				</h:panelGrid>
			</div>
			<div style="position:relative;left:20%;width:600px;border-radius:5px;background:#FFFFFF;" class="displayMetaData">
				<h:outputText value="Query Execution results:" rendered="#{databaseActionBean.renderMetaData}"/>
				<h:panelGrid columns="2" rendered="#{databaseActionBean.renderMetaData}">
					<h:outputText value="SQL Query:"></h:outputText>
					<h:outputText value="#{dBAccessBean.inputQuery}"></h:outputText>
					<h:outputText value="Number of columns:" rendered="#{databaseActionBean.renderSelQueryResults}"></h:outputText>
					<h:outputText value="#{databaseActionBean.columnsCount}" rendered="#{databaseActionBean.renderSelQueryResults}"></h:outputText>
					<h:outputText value="Number of rows:" rendered="#{databaseActionBean.renderSelQueryResults}"></h:outputText>
					<h:outputText value="#{databaseActionBean.rowsCount}" rendered="#{databaseActionBean.renderSelQueryResults}"></h:outputText>
					<h:outputText value="Number of rows affected:" rendered="#{databaseActionBean.renderOtrQueryResults}"></h:outputText>
					<h:outputText value="#{databaseActionBean.rowsCount}" rendered="#{databaseActionBean.renderOtrQueryResults}"></h:outputText>
				</h:panelGrid>
				
			</div>
			<br/>
			<div class="displayReports" style="position:relative;left:20%;background-attachment: scroll; overflow:auto; width:600px; height:250px; background-repeat: repeat">
				<t:dataTable   value="#{databaseActionBean.result}" var="row" rendered="#{databaseActionBean.renderDataTable}"
					border="1" cellspacing="0" cellpadding="1" columnClasses="columnClass1 border"
					headerClass="headerClass" footerClass="footerClass" rowClasses="rowClass2" 
					width="400" styleClass="tdatatable" >
					<t:columns var="col" value="#{dBAccessBean.selectedColumns}">
						<f:facet name="header">
							<t:outputText styleClass="outputHeader" value="#{col}" />
						</f:facet>
						<t:outputText styleClass="outputText" value="#{row[col]}" />
					</t:columns>
				</t:dataTable>		
			</div>
			<hr/>
		</div>
	</h:form>
</f:view>
</body>
</html>
</jsp:root>