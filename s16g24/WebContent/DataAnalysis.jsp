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
			<title>Data Analysis</title>
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
						<h:selectOneMenu style="position:relative;left:43%;align:center;font-size:18px;margin: auto auto auto auto" value="#{dataAnalysisBean.selectedDatabase}" onchange="submit()" valueChangeListener="#{dataAnalysisActionBean.onSchemaChange }">
							<f:selectItems value="#{dataAnalysisBean.databases}"></f:selectItems> 
						</h:selectOneMenu>
						<br/><br/>
						<div class="cmdButtons"  align="center">
							<h:panelGrid columns="3" >
								<h:commandButton styleClass ="btn" type="submit" value="Descriptive Statistics" action="#{dataAnalysisActionBean.displayDescriptiveStatistics}" />
								<h:commandButton styleClass ="btn" type="submit" value="Regression Analysis" action="#{dataAnalysisActionBean.displayRegressionElements}" />
								<h:commandButton styleClass ="btn" type="submit" value="Charts" action="#{dataAnalysisActionBean.displayChartElements}" />
							</h:panelGrid>
						</div>
						<br/>
						<div class="table-borderless" align="center">
							<h:outputText rendered ="#{dataAnalysisActionBean.renderDescriptiveAnalysis}" value="Select Quantitative Variables only to get Descriptive statistics"></h:outputText>
							<h:panelGrid columns="2">
								<h:outputText value="Select Table" rendered="#{dataAnalysisActionBean.renderTables}"/>
								<h:selectOneListbox  value="#{dataAnalysisBean.selectedTable}" onchange="submit()" rendered="#{dataAnalysisActionBean.renderTables}"
									valueChangeListener="#{dataAnalysisActionBean.onTableChange}" size="4">
									<f:selectItems value="#{dataAnalysisBean.tables}"></f:selectItems> 
								</h:selectOneListbox>
				    			<h:outputText value="Select Column" rendered="#{dataAnalysisActionBean.renderDescriptiveColumns}" />
				    			<h:selectManyListbox  value ="#{dataAnalysisBean.selectedDisplayColumns}" rendered="#{dataAnalysisActionBean.renderDescriptiveColumns}" size="4">
									<f:selectItems value="#{dataAnalysisBean.displayColumns}"></f:selectItems> 
								</h:selectManyListbox>
								<h:outputText value="Select Response Column" rendered="#{dataAnalysisActionBean.renderResponseColumns}" />
								<h:selectOneListbox  value ="#{dataAnalysisBean.selectedResponseColumn}" onchange="submit()" rendered="#{dataAnalysisActionBean.renderResponseColumns}" 
									valueChangeListener="#{dataAnalysisActionBean.onColumnChange}" size="3">
									<f:selectItems value="#{dataAnalysisBean.responseColumns}"></f:selectItems> 
								</h:selectOneListbox>
								<h:outputText value="Select Predictor Columns" rendered="#{dataAnalysisActionBean.renderPredictorColumns}" />
								<h:selectOneListbox  value ="#{dataAnalysisBean.selectedPredictorColumn}" rendered="#{dataAnalysisActionBean.renderPredictorColumns}" size="3">
									<f:selectItems value="#{dataAnalysisBean.predictorColumns}"></f:selectItems> 
								</h:selectOneListbox>
								<h:outputText value="Select type of chart" rendered="#{dataAnalysisActionBean.renderChartTypes}" />
								<h:selectOneListbox  value ="#{dataAnalysisBean.selectedChartType}" onchange="submit()" rendered="#{dataAnalysisActionBean.renderChartTypes}" 
									valueChangeListener="#{dataAnalysisActionBean.onChartChange}">
									<f:selectItem  itemLabel="Pie Chart" itemValue="pie"/>
									<f:selectItem  itemLabel="Bar Chart" itemValue="bar"/>
									<f:selectItem  itemLabel="X-Y Chart" itemValue="x-y"/>
									<f:selectItem  itemLabel="Time Series Chart" itemValue="tm"/> 
								</h:selectOneListbox>
								<h:outputText value="Select Column" rendered="#{dataAnalysisActionBean.renderChartColumns}" />
				    			<h:selectOneListbox  value ="#{dataAnalysisBean.selectedChartColumn}" onchange="submit()" rendered="#{dataAnalysisActionBean.renderChartColumns}" valueChangeListener="#{dataAnalysisActionBean.onCategoryChange}" size="3">
									<f:selectItems value="#{dataAnalysisBean.displayColumns}"></f:selectItems> 
								</h:selectOneListbox>
								<h:outputText value="Select Category by " rendered="#{dataAnalysisActionBean.renderCategoricalChartColumns}" />
								<h:selectOneMenu  value ="#{dataAnalysisBean.selectedGroupByOption}" rendered="#{dataAnalysisActionBean.renderCategoricalChartColumns}" >
								<f:selectItem itemLabel="Country" itemValue="CountryCode"/>
									<f:selectItem itemLabel="Continent" itemValue="Continent"/>
									<f:selectItem itemLabel="Region" itemValue="Region"/> 
								</h:selectOneMenu>
								<h:outputText value="Select Predictor variable" rendered="#{dataAnalysisActionBean.renderChartVariables}" />
								<h:selectOneMenu  value ="#{dataAnalysisBean.selectedXChartColumn}" rendered="#{dataAnalysisActionBean.renderChartVariables}" >
									<f:selectItems value="#{dataAnalysisBean.selectedChartColumns}"/> 
								</h:selectOneMenu>
								<h:outputText value="Select Response variable" rendered="#{dataAnalysisActionBean.renderChartVariables}" />
								<h:selectOneMenu  value ="#{dataAnalysisBean.selectedYChartColumn}" rendered="#{dataAnalysisActionBean.renderChartVariables}" >
									<f:selectItems value="#{dataAnalysisBean.selectedChartColumns}"/> 
								</h:selectOneMenu>
							</h:panelGrid>
							<h:commandButton styleClass ="btn btn-primary" type="submit" value="Generate Descriptive Statistics" action="#{dataAnalysisActionBean.getDescriptiveStatistics}" rendered="#{dataAnalysisActionBean.renderDescriptiveAnalysis}" />
							<h:commandButton styleClass ="btn btn-primary" type="submit" value="Generate Regression Analysis" action="#{dataAnalysisActionBean.performRegressionAnalysis}" rendered="#{dataAnalysisActionBean.renderRegressionAnalysis}" />
							<h:commandButton styleClass ="btn btn-primary" type="submit" value="GenerateCharts" action="#{dataAnalysisActionBean.generateCharts}" rendered="#{dataAnalysisActionBean.renderCharts}" />
						</div>
						<br/>
						<div class="charts" align="center">
							<h:graphicImage alt="Pie Chart" value="#{chartsBean.pieChartPath}" width="600" height="600" rendered="#{dataAnalysisActionBean.renderPieChart}"></h:graphicImage>
							<h:graphicImage alt="Bar Chart" value="#{chartsBean.barChartPath}" width="600" height="600" rendered="#{dataAnalysisActionBean.renderBarChart}"></h:graphicImage>
							<h:graphicImage alt="XY Chart" value="#{chartsBean.xyChartPath}" width="600" height="600" rendered="#{dataAnalysisActionBean.renderXYChart}"></h:graphicImage>
							<h:graphicImage alt="Time Series Chart" value="#{chartsBean.timeSeriesChartPath}" width="600" height="600" rendered="#{dataAnalysisActionBean.renderTimeSeriesChart}"></h:graphicImage>
						</div>
						<div>
							<t:dataTable styleClass="table table-condensed" value="#{dataAnalysisActionBean.dABeanList}" var="row" rendered="#{dataAnalysisActionBean.renderDescriptiveResults}" >
								<h:column>
									<f:facet name="header">
										<h:outputText value="Column"/>
									</f:facet>
										<h:outputText value="#{row.column}"/>
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Minimum Value"/>
									</f:facet>
									<h:outputText value="#{row.minValue}"/>
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Maximum Value"/>
									</f:facet>
									<h:outputText value="#{row.maxValue}"/>
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Mean"/>
									</f:facet>
									<h:outputText value="#{row.mean}"/>
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Variance"/>
									</f:facet>
									<h:outputText value="#{row.variance}"/>
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Standard Deviation"/>
									</f:facet>
									<h:outputText value="#{row.standardDeviation}"/>
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Median"/>
									</f:facet>
									<h:outputText value="#{row.median}"/>
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="First Quartile"/>
									</f:facet>
									<h:outputText value="#{row.firstQuartile}"/>
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Third Quartile"/>
									</f:facet>
									<h:outputText value="#{row.thirdQuartile}"/>
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Inter Quartile Range"/>
									</f:facet>
									<h:outputText value="#{row.interQuartileRange}"/>
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Range"/>
									</f:facet>
									<h:outputText value="#{row.range}"/>
								</h:column>
							</t:dataTable> 
						</div> 
						
						<div class="table-condensed" align="center">
						<br/>
							<h:outputText style= "font-weight:bold;line-height: 20px;color: black;text-decoration: underline;" value="Regression Equation" rendered="#{dataAnalysisActionBean.renderRegressionResults}"></h:outputText>
							<h:outputText style="font-weight:bold;font-size:24px" rendered="#{dataAnalysisActionBean.renderRegressionResults}"></h:outputText><br/>
							<h:outputText styleClass= "aov" value="#{regressionAnalysisBean.regressionEquation}" rendered="#{dataAnalysisActionBean.renderRegressionResults}"></h:outputText><br/> <br/>
							<h:outputText styleClass= "aov" value="Regression Model:" rendered="#{dataAnalysisActionBean.renderRegressionResults}"></h:outputText>
							<h:panelGrid columns="5" rendered="#{dataAnalysisActionBean.renderRegressionResults}" border="1" >
								<h:outputText value="Predictor"/>
								<h:outputText value="Co-efficient"/>
								<h:outputText value="Standard Error Co-efficient"/>
								<h:outputText value="T-Statistic"/>
								<h:outputText value="P-Value"/>
								<h:outputText value="Constant"/>
								<h:outputText value="#{regressionAnalysisBean.intercept}"/>
								<h:outputText value="#{regressionAnalysisBean.interceptStandardError}"/>
								<h:outputText value="#{regressionAnalysisBean.tStatisticIntercept }"/>
								<h:outputText value="#{regressionAnalysisBean.pValueIntercept }"/>
								<h:outputText value="#{dataAnalysisBean.selectedPredictorColumn}"/>
								<h:outputText value="#{regressionAnalysisBean.slope}"/>
								<h:outputText value="#{regressionAnalysisBean.slopeStandardError}"/>
								<h:outputText value="#{regressionAnalysisBean.tStatisticPredictor }"/>
								<h:outputText value="#{regressionAnalysisBean.pValuePredictor }"/>
							</h:panelGrid>
							<br/>
							<br/>
							<h:panelGrid columns="2" rendered="#{dataAnalysisActionBean.renderRegressionResults}" border="1">
								<h:outputText value="Standard Error of Model S"/>
								<h:outputText value="#{regressionAnalysisBean.modelError}"/>
								<h:outputText value="R Square(Co-efficient of Determination)"/>
								<h:outputText value="#{regressionAnalysisBean.rSquare}"/>
								<h:outputText value="R Square Adjusted(Co-efficient of Determination)"/>
								<h:outputText value="#{regressionAnalysisBean.rSquareAdjusted}"/>
							</h:panelGrid>
							<br/>
							<br/>
							<h:outputText styleClass="aov" value="Analysis of Variance"  rendered="#{dataAnalysisActionBean.renderRegressionResults}"/> 
							<br/>
							<h:panelGrid columns="6" rendered="#{dataAnalysisActionBean.renderRegressionResults}" border="1" >
								<h:outputText value="Source"/>
								<h:outputText value="Degrees of Freedom(DF)"/>
								<h:outputText value="Sum of Squares"/>
								<h:outputText value="Mean of Squares"/>
								<h:outputText value="F-Statistic"/>
								<h:outputText value="P-Value"/>
								<h:outputText value="Regression"/>
								<h:outputText value="#{regressionAnalysisBean.predictorDF}"/>
								<h:outputText value="#{regressionAnalysisBean.regressionSumSquares}"/>
								<h:outputText value="#{regressionAnalysisBean.meanSquare }"/>
								<h:outputText value="#{regressionAnalysisBean.fValue }"/>
								<h:outputText value="#{regressionAnalysisBean.pValue}"/>
								<h:outputText value="Residual Error"/>
								<h:outputText value="#{regressionAnalysisBean.residualErrorDF}"/>
								<h:outputText value="#{regressionAnalysisBean.sumSquaredErrors }"/>
								<h:outputText value="#{regressionAnalysisBean.meanSquareError }"/>
								<h:outputText value=""/>
								<h:outputText value=""/>
								<h:outputText value="Total"/>
								<h:outputText value="#{regressionAnalysisBean.totalDF}"/>
								<h:outputText value="#{regressionAnalysisBean.totalSumSquares}"/>
							</h:panelGrid>
							<br/>
						</div>
					</div>
				</h:form>		
			</f:view>
		</body>
	</html>
</jsp:root>