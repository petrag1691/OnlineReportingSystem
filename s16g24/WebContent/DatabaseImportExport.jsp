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
		xmlns:t="http://myfaces.apache.org/tomahawk">
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title>Import/Export Data</title>
			<link rel="stylesheet" href="bootstrap.css" type="text/css" />
		</head>
		<body class="background">
			<f:view>
				<div style="position: relative; left: 35%; top: 20%; margin: 2% auto auto auto;">
					<h2 style="position:relative;align:center;left:-1	%;">Welcome to Database Import Export Page</h2>
				</div>
				<h:form enctype="multipart/form-data">
					<pre class="prediv">
						<h:messages styleClass="prediv" style="white-space:wrap;" rendered="#{importExportActionBean.renderErrorMessages}"></h:messages>
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
						<h:selectOneMenu style="position:relative;left:43%;align:center;font-size:18px;margin: auto auto auto auto" value="#{importExportBean.selectedDatabase}" onchange="submit()" valueChangeListener="#{importExportActionBean.onDatabaseChange }">
							<f:selectItems value="#{importExportBean.databases}"></f:selectItems> 
						</h:selectOneMenu>
						<br/><br/>
						<div class="cmdButtons"  align="center">
							<h:panelGrid columns="2" >
								<h:commandButton styleClass ="btn" type="submit" value="Display Import Fields" action="#{importExportActionBean.displayImportFields}"/>
								<h:commandButton styleClass ="btn" type="submit" value="Display Export Fields" action="#{importExportActionBean.displayExportFields}" />
							</h:panelGrid>
						</div>
						<br/>
						<br/>
						<div class="selectionFields" align="center">
							<h:panelGrid columns="2" rendered="#{importExportActionBean.renderDisplay}">
								<h:outputText value="Select Table" rendered="#{importExportActionBean.renderDisplay}"/>
								<h:selectOneListbox  value="#{importExportBean.selectedTable}"  rendered="#{importExportActionBean.renderDisplay}">
									<f:selectItems value="#{importExportBean.tables}"/> 
								</h:selectOneListbox>
								<h:outputText value="Select type of document to export" rendered="#{importExportActionBean.renderExportFields}"/>
								<h:selectOneMenu value="#{importExportBean.exportOption}" rendered="#{importExportActionBean.renderExportFields}">
								   <f:selectItem itemValue="csv" itemLabel="CSV Format" />
								   <f:selectItem itemValue="xml" itemLabel="XML Format" />	   			
								</h:selectOneMenu>
								<h:outputLabel value="Select file to upload:" rendered="#{importExportActionBean.renderImportFields}" />
								<t:inputFileUpload id="fileUpload" rendered="#{importExportActionBean.renderImportFields}"
									label="File to upload" storage="default" value="#{importExportBean.uploadedFile}" size="60" />
							</h:panelGrid>
							<h:commandButton styleClass ="btn" type="submit" value="ExportTable" action="#{importExportActionBean.exportTable}" rendered="#{importExportActionBean.renderExportFields}"/>
							<h:commandButton styleClass="btn" type="submit" action="#{importExportActionBean.importTable}" value="Submit" rendered="#{importExportActionBean.renderImportFields}"/>
							<h:outputLabel value="" rendered="#{importExportActionBean.renderImportFields}" />
						</div>
						<div class="metadata" align="center">
							<h:panelGrid columns="2" rendered="#{importExportActionBean.renderImportResult}">
								<h:outputText value="SQL Query:" rendered="#{importExportActionBean.renderQueryResults}"></h:outputText>
								<h:outputText value="#{importExportActionBean.executionQuery}" rendered="#{importExportActionBean.renderQueryResults}"></h:outputText>
								<h:outputText value="Number of columns:" rendered="#{importExportActionBean.renderQueryResults}"></h:outputText>
								<h:outputText value="#{importExportActionBean.columnsCount}" rendered="#{importExportActionBean.renderQueryResults}"></h:outputText>
								<h:outputText value="Number of rows:" rendered="#{importExportActionBean.renderQueryResults}"></h:outputText>
								<h:outputText value="#{importExportActionBean.rowCount}" rendered="#{importExportActionBean.renderQueryResults}"></h:outputText>
								<h:outputText value="Number of records inserted:" rendered="#{importExportActionBean.renderImportResult}"></h:outputText>
								<h:outputText value="#{importExportActionBean.rowCount}" rendered="#{importExportActionBean.renderImportResult}"></h:outputText>
							</h:panelGrid>
							<h:commandButton styleClass ="btn" type="submit" value="DisplayTable" action="#{importExportActionBean.displayTable}" rendered = "#{importExportActionBean.renderImportResult }" />
						</div>
						<div class="displayReports" style="position:relative;left:20%;background-attachment: scroll; overflow:auto; width:600px; height:250px; background-repeat: repeat">
							<t:dataTable   value="#{importExportActionBean.result}" var="row" rendered="#{importExportActionBean.renderDataTable}"
								border="1" cellspacing="0" cellpadding="1" columnClasses="columnClass1 border"
								headerClass="headerClass" footerClass="footerClass" rowClasses="rowClass2" 
								width="400" styleClass="tdatatable" >
								<t:columns var="col" value="#{importExportBean.tableColumns}">
									<f:facet name="header">
										<t:outputText styleClass="outputHeader" value="#{col}" />
									</f:facet>
									<t:outputText styleClass="outputText" value="#{row[col]}" />
								</t:columns>
							</t:dataTable>		
						</div>
						<br/>
					</div>
				</h:form>
			</f:view>
		</body>
	</html>
</jsp:root>