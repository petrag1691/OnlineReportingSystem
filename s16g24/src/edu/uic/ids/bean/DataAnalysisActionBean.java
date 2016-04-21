package edu.uic.ids.bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
@ManagedBean
@ViewScoped
public class DataAnalysisActionBean implements Serializable,Cloneable{
	private static final long serialVersionUID = 7525697971994837497L;
	private DatabaseAccessBean dbbean;
	@ManagedProperty(value="#{dataAnalysisBean}")
	private DataAnalysisBean dataAnalysisBean;
	@ManagedProperty(value="#{errorMessageBean}")
	private ErrorMessageBean errorMessageBean;
	@ManagedProperty(value="#{descriptiveAnalysisBean}")
	private DescriptiveAnalysisBean dABean;
	@ManagedProperty(value="#{regressionAnalysisBean}")
	private RegressionAnalysisBean regBean;
	@ManagedProperty(value="#{chartsBean}")
	private ChartsBean chartsBean;
	private List<DescriptiveAnalysisBean> dABeanList;
	private boolean renderDescriptiveAnalysis;
	private boolean renderRegressionAnalysis;
	private boolean renderTables;
	private boolean renderDescriptiveColumns;
	private boolean renderResponseColumns;
	private boolean renderPredictorColumns;
	private boolean renderChartColumns;
	private boolean renderDescriptiveResults;
	private boolean renderRegressionResults;
	private boolean renderChartResults;
	private boolean renderCharts;
	private boolean renderChartTypes;
	private boolean renderChartVariables;
	private boolean renderErrorMessages;
	private boolean renderPieChart;
	private boolean renderBarChart;
	private boolean renderXYChart;
	private boolean renderTimeSeriesChart;
	private boolean renderCategoricalChartColumns;
	private String executionQuery;
	private String status;
	private ResultSet resultSet;
	private boolean tableChanged;
	@PostConstruct
	public void init(){
		Map <String, Object> m =  FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		dbbean = (DatabaseAccessBean) m.get("databaseAccessBean");
		dABeanList = new ArrayList<>();
		renderTables = false;
		renderDescriptiveColumns = false;
		renderResponseColumns = false;
		renderPredictorColumns = false;
		renderChartColumns = false;
		renderDescriptiveResults = false;
		renderDescriptiveAnalysis = false;
		renderRegressionAnalysis = false;
		renderErrorMessages = false;
		renderCharts = false;
		renderChartResults = false;
		renderChartTypes = false;
		renderChartVariables= false;
		renderCategoricalChartColumns =false;
		//renderTimeChartColumns=false;
		renderPieChart = false;
		renderBarChart = false;
		renderXYChart = false;
		renderTimeSeriesChart = false;
		status = "SUCCESS";
		executionQuery = "";
		tableChanged = false;
	}

	public DatabaseAccessBean getDbbean() {
		return dbbean;
	}

	public void setDbbean(DatabaseAccessBean dbbean) {
		this.dbbean = dbbean;
	}

	public DataAnalysisBean getDataAnalysisBean() {
		return dataAnalysisBean;
	}

	public void setDataAnalysisBean(DataAnalysisBean dataAnalysisBean) {
		this.dataAnalysisBean = dataAnalysisBean;
	}

	public ErrorMessageBean getErrorMessageBean() {
		return errorMessageBean;
	}

	public void setErrorMessageBean(ErrorMessageBean errorMessageBean) {
		this.errorMessageBean = errorMessageBean;
	}

	public DescriptiveAnalysisBean getdABean() {
		return dABean;
	}

	public void setdABean(DescriptiveAnalysisBean dABean) {
		this.dABean = dABean;
	}

	public RegressionAnalysisBean getRegBean() {
		return regBean;
	}

	public void setRegBean(RegressionAnalysisBean regBean) {
		this.regBean = regBean;
	}

	public ChartsBean getChartsBean() {
		return chartsBean;
	}

	public void setChartsBean(ChartsBean chartsBean) {
		this.chartsBean = chartsBean;
	}

	public List<DescriptiveAnalysisBean> getdABeanList() {
		return dABeanList;
	}

	public void setdABeanList(List<DescriptiveAnalysisBean> dABeanList) {
		this.dABeanList = dABeanList;
	}

	public boolean isRenderDescriptiveAnalysis() {
		return renderDescriptiveAnalysis;
	}

	public void setRenderDescriptiveAnalysis(boolean renderDescriptiveAnalysis) {
		this.renderDescriptiveAnalysis = renderDescriptiveAnalysis;
	}
	public boolean isRenderTables() {
		return renderTables;
	}

	public void setRenderTables(boolean renderTables) {
		this.renderTables = renderTables;
	}

	public boolean isRenderDescriptiveColumns() {
		return renderDescriptiveColumns;
	}

	public void setRenderDescriptiveColumns(boolean renderDescriptiveColumns) {
		this.renderDescriptiveColumns = renderDescriptiveColumns;
	}
	public boolean isRenderResponseColumns() {
		return renderResponseColumns;
	}

	public void setRenderResponseColumns(boolean renderResponseColumns) {
		this.renderResponseColumns = renderResponseColumns;
	}

	public boolean isRenderPredictorColumns() {
		return renderPredictorColumns;
	}

	public void setRenderPredictorColumns(boolean renderPredictorColumns) {
		this.renderPredictorColumns = renderPredictorColumns;
	}

	public boolean isRenderChartColumns() {
		return renderChartColumns;
	}

	public void setRenderChartColumns(boolean renderChartColumns) {
		this.renderChartColumns = renderChartColumns;
	}

	public boolean isRenderDescriptiveResults() {
		return renderDescriptiveResults;
	}

	public void setRenderDescriptiveResults(boolean renderDescriptiveResults) {
		this.renderDescriptiveResults = renderDescriptiveResults;
	}

	public boolean isRenderRegressionResults() {
		return renderRegressionResults;
	}

	public void setRenderRegressionResults(boolean renderRegressionResults) {
		this.renderRegressionResults = renderRegressionResults;
	}

	public boolean isRenderCharts() {
		return renderCharts;
	}

	public void setRenderCharts(boolean renderCharts) {
		this.renderCharts = renderCharts;
	}

	public boolean isRenderChartTypes() {
		return renderChartTypes;
	}

	public void setRenderChartTypes(boolean renderChartTypes) {
		this.renderChartTypes = renderChartTypes;
	}

	public boolean isRenderRegressionAnalysis() {
		return renderRegressionAnalysis;
	}

	public void setRenderRegressionAnalysis(boolean renderRegressionAnalysis) {
		this.renderRegressionAnalysis = renderRegressionAnalysis;
	}
	public boolean isRenderChartResults() {
		return renderChartResults;
	}

	public void setRenderChartResults(boolean renderChartResults) {
		this.renderChartResults = renderChartResults;
	}

	public boolean isRenderChartVariables() {
		return renderChartVariables;
	}

	public void setRenderChartVariables(boolean renderChartVariables) {
		this.renderChartVariables = renderChartVariables;
	}

	public boolean isRenderErrorMessages() {
		return renderErrorMessages;
	}

	public boolean isRenderPieChart() {
		return renderPieChart;
	}

	public boolean isRenderBarChart() {
		return renderBarChart;
	}

	public boolean isRenderXYChart() {
		return renderXYChart;
	}

	public boolean isRenderTimeSeriesChart() {
		return renderTimeSeriesChart;
	}

	public boolean isRenderCategoricalChartColumns() {
		return renderCategoricalChartColumns;
	}

	public String getExecutionQuery() {
		return executionQuery;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}
	
	//Used to display descriptive statistics on the jsp page
	public String displayDescriptiveStatistics(){
		if(!dataAnalysisBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onDatabaseChange().equalsIgnoreCase("SUCCESS")){
				displayDescriptiveStatistics();
			}else{
				setErrorMessage("");
			}
		}else{
			status = "FAIL";
			resetOptionFlags();
			if(dbbean.getTablesFromDatabase().equalsIgnoreCase("SUCCESS")){
				resetAnalysisFlags();
				resetTables();
				if(dbbean.getTables()!=null && dbbean.getTables().size()>0){
					this.dataAnalysisBean.setTables(dbbean.getTables());
					this.dataAnalysisBean.setSelectedTable(dbbean.getTables().get(0));
					clearSelectedColumns();
					renderTables = true;
					if(dbbean.getTableColumns(dataAnalysisBean.getSelectedTable()).equalsIgnoreCase("SUCCESS")){
						if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0){
							//this.dataAnalysisBean.setColumns(dbbean.getColumns());
							generateDescriptiveColumns();
							renderDescriptiveColumns = true;
							renderDescriptiveAnalysis = true;
							status = "SUCCESS";
						}else{
							//error handling
							setErrorMessage("The default selected table has no columns");
						}
					}else{
						//handle errors properly
						setErrorMessage("");
					}
				}else{
					setErrorMessage( "No tables found in the selected database");
				}
			}else{
				setErrorMessage("");
			}
		}
		return status;
	}

	//Performs analysis on the quantitative data and generates mean,median and other statistical details
	public void getDescriptiveStatistics(){
		if(!dataAnalysisBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onDatabaseChange().equalsIgnoreCase("SUCCESS")){
				resetAnalysisFlags();
				//getDescriptiveStatistics();
				setErrorMessage("Database changed.Please select the analysis to be performed");
			}else{
				setErrorMessage("");
			}
		}else{
			if(dataAnalysisBean.getSelectedDisplayColumns()!=null && dataAnalysisBean.getSelectedDisplayColumns().size()>0){
				List<String>columnsType = getQuantitativeColumns();
				if(columnsType.size()!=dataAnalysisBean.getSelectedDisplayColumns().size()){
					setErrorMessage("Categorical column has been selected.Please select quantitative columns");
				}else{
					if(dABeanList!=null && dABeanList.size()>0){
						dABeanList.clear();
					}
					for(int i=0;i<dataAnalysisBean.getSelectedDisplayColumns().size();i++){
						String column = getSelectedColumn(dataAnalysisBean.getSelectedDisplayColumns().get(i));
						executionQuery = "SELECT "+column+" FROM "+dataAnalysisBean.getSelectedTable();
						//System.out.println("DataAnalysisActionBean.getDescriptiveStatistics() query is "+executionQuery);
						if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
							try{
								this.resultSet = dbbean.getResultSet();
								List<Double> tempList = new ArrayList<>();
								if(resultSet!=null){
									while(resultSet.next()){
										switch(columnsType.get(i).toLowerCase()){
										case "int":
											tempList.add((double) resultSet.getInt(1));
											break;
										case "smallint":
											tempList.add((double) resultSet.getInt(1));
											break;
										case "float":
											tempList.add((double) resultSet.getFloat(1));
											break;
										case "double":
											tempList.add((double) resultSet.getDouble(1));
											break;
										case "long":
											tempList.add((double) resultSet.getLong(1));
											break;
										default:
											tempList.add((double) resultSet.getDouble(1));
											break;
										}
									}
									if(tempList!=null && tempList.size()>0){
										double[] temp = new double[tempList.size()];
										for(int j=0;j<tempList.size();j++){
											temp[j] = tempList.get(j).doubleValue();
										}
										dABean.setColumn(dataAnalysisBean.getSelectedDisplayColumns().get(i));
										if(dABean.generateStats(temp)){
											DescriptiveAnalysisBean dabean = (DescriptiveAnalysisBean) dABean.clone();
											dABeanList.add(dabean);
											renderDescriptiveResults = true;
										}else{
											renderDescriptiveResults = false;
											setErrorMessage("Unable to generate Descriptive statistics");
										}
									}else{
										renderDescriptiveResults = false;
										setErrorMessage("No data present in the database for the selected column");
									}
								}else{
									//System.out.println("DataAnalysisActionBean.getDescriptiveStatistics() no records in the current column");
								}
							}catch(SQLException e){
								e.printStackTrace();
								setErrorMessage(e.getMessage());
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}else{
							setErrorMessage("");
							break;
						}
					}
				}
			}else{
				setErrorMessage("No columns have been selected.Please select quantitative columns");
			}
		}
	}

	//Invoked to display regression ui details on the jsp page
	public String displayRegressionElements(){
		if(!dataAnalysisBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onDatabaseChange().equalsIgnoreCase("SUCCESS")){
				resetAnalysisFlags();
				displayRegressionElements();
			}else{
				setErrorMessage("");
			}
		}else{
			status = "FAIL";
			resetOptionFlags();
			if(dbbean.getTablesFromDatabase().equalsIgnoreCase("SUCCESS")){
				resetAnalysisFlags();
				resetTables();
				if(dbbean.getTables()!=null && dbbean.getTables().size()>0){
					this.dataAnalysisBean.setTables(dbbean.getTables());
					this.dataAnalysisBean.setSelectedTable(dbbean.getTables().get(0));
					clearSelectedColumns();
					renderTables = true;
					if(dbbean.getTableColumns(dataAnalysisBean.getSelectedTable()).equalsIgnoreCase("SUCCESS")){
						if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0){
							//this.dataAnalysisBean.setColumns(dbbean.getColumns());
							generateRegressionColumns();
							renderRegressionAnalysis = true;
							status = "SUCCESS";
						}else{
							//error handling
							setErrorMessage("The default selected table has no columns");
						}
					}else{
						//handle errors properly
						setErrorMessage("");
					}
				}else{
					setErrorMessage( "No tables found in the selected database");
				}
			}else{
				setErrorMessage("");
			}
		}
		return status;
	}

	//Invokes regression analysis functions to provide analysis about the predictor and response columns
	public void performRegressionAnalysis(){
		if(!dataAnalysisBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onDatabaseChange().equalsIgnoreCase("SUCCESS")){
				resetAnalysisFlags();
				setErrorMessage("Database changed.Please select the analysis to be performed");
			}else{
				setErrorMessage("");
			}
		}else{
			if(renderResponseColumns && renderPredictorColumns){
				if(dataAnalysisBean.getSelectedResponseColumn()!=null && !dataAnalysisBean.getSelectedResponseColumn().trim().isEmpty()){
					//if(dataAnalysisBean.getSelectedPredictorColumns()!=null && dataAnalysisBean.getSelectedPredictorColumns().size()>0){
					if(dataAnalysisBean.getSelectedPredictorColumn()!=null && !dataAnalysisBean.getSelectedPredictorColumn().trim().isEmpty()){
						if(!dataAnalysisBean.getSelectedPredictorColumn().trim().equalsIgnoreCase(dataAnalysisBean.getSelectedResponseColumn().trim())){
							executionQuery = "SELECT "+dataAnalysisBean.getSelectedResponseColumn()+" FROM "+dataAnalysisBean.getSelectedTable();
							//System.out.println("DataAnalysisActionBean.performRegressionAnalysis() execution query is "+executionQuery);
							if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
								double[] responseVals = getDataFromResultSet();
								//double[][] predictorVals = new double[dataAnalysisBean.getSelectedPredictorColumns().size()][];
								double[] predictorVals = null;
								if(responseVals!=null){
									status = "SUCCESS";
									executionQuery = "SELECT "+dataAnalysisBean.getSelectedPredictorColumn()+" FROM "+dataAnalysisBean.getSelectedTable();
									//System.out.println("DataAnalysisActionBean.performRegressionAnalysis() execution query is "+executionQuery);
									if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
										predictorVals = getDataFromResultSet();
										status = "SUCCESS";
										if(predictorVals==null){
											status = "FAIL";
											setErrorMessage("No values in the database for the predictor variable "+dataAnalysisBean.getSelectedPredictorColumn());
										}
									}else{
										status = "FAIL";
										setErrorMessage("");
									}
								}else{
									setErrorMessage("No values in the database for the response variable "+dataAnalysisBean.getSelectedResponseColumn());
									status = "FAIL";
								}
								if(status.equalsIgnoreCase("SUCCESS")){
									status = regBean.generateAnalysis(responseVals, predictorVals);
									if(status.equalsIgnoreCase("SUCCESS")){
										//System.out.println("DataAnalysisActionBean.performRegressionAnalysis() regression equation is "+dataAnalysisBean.getSelectedResponseColumn()+"="+regBean.getIntercept()+"+"+regBean.getIntercept()+"*"+dataAnalysisBean.getSelectedPredictorColumn());
										regBean.setRegressionEquation(dataAnalysisBean.getSelectedResponseColumn()+"="+regBean.getIntercept()+"+"+regBean.getSlope()+"*"+dataAnalysisBean.getSelectedPredictorColumn());
										renderRegressionResults = true;
									}else{
										setErrorMessage(status);
									}
								}
							}else{
								setErrorMessage("");
							}
						}else{
							setErrorMessage("Same column selected. Please change the response column");
						}
					}else{
						setErrorMessage("Select a predictor variable");
					}
				}else{
					setErrorMessage("Select a response column");
				}
			}else{
				setErrorMessage("No Quantitative columns present in the table");
			}
		}
	}
	
	// Generates a double array to provide as input to the RegressionAnalysisBean
	private double[] getDataFromResultSet() {
		double[] result;
		List<Double> tempList = new ArrayList<>();
		resultSet = dbbean.getResultSet();
		if(resultSet!=null){
			try {
				while(resultSet.next()){
					switch(dataAnalysisBean.getSelectedResponseColumn().toLowerCase()){
					case "int":
						tempList.add((double) resultSet.getInt(1));
						break;
					case "smallint":
						tempList.add((double) resultSet.getInt(1));
						break;
					case "float":
						tempList.add((double) resultSet.getFloat(1));
						break;
					case "double":
						tempList.add((double) resultSet.getDouble(1));
						break;
					case "long":
						tempList.add((double) resultSet.getLong(1));
						break;
					default:
						tempList.add((double) resultSet.getDouble(1));
						break;
					}
				}
			}catch(SQLException e){
				setErrorMessage("SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage());
				result = null;
			}
			if(tempList!=null && tempList.size()>0){
				result = new double[tempList.size()];
				for(int j=0;j<tempList.size();j++){
					result[j] = tempList.get(j).doubleValue();
				}
			}else{
				setErrorMessage("ResultSet is empty");
				result = null;
			}
		}else{
			setErrorMessage("ResultSet is empty");
			result = null;
		}	
		return result;
	}

	//Invoked to display chart elements
	public void displayChartElements(){
		if(!dataAnalysisBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onDatabaseChange().equalsIgnoreCase("SUCCESS")){
				resetAnalysisFlags();
				setErrorMessage("Database changed.Please select the analysis to be performed");
			}else{
				setErrorMessage("");
			}
		}else{
			status = "FAIL";
			resetOptionFlags();
			if(dbbean.getTablesFromDatabase().equalsIgnoreCase("SUCCESS")){
				resetAnalysisFlags();
				resetTables();
				if(dbbean.getTables()!=null && dbbean.getTables().size()>0){
					this.dataAnalysisBean.setTables(dbbean.getTables());
					this.dataAnalysisBean.setSelectedTable(dbbean.getTables().get(0));
					renderTables = true;
					renderChartTypes = true;
					renderCharts = true;
					dataAnalysisBean.setSelectedChartType("pie");
					if(dbbean.getTableColumns(dataAnalysisBean.getSelectedTable()).equalsIgnoreCase("SUCCESS")){
						if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0){
							generateDescriptiveColumns();
							renderChartColumns = true;
							dataAnalysisBean.setSelectedChartColumn(dataAnalysisBean.getDisplayColumns().get(0));
							if(dataAnalysisBean.getSelectedChartColumn().contains("CATEGORICAL")){
								renderCategoricalChartColumns = true;
							}else{
								renderCategoricalChartColumns = false;
							}
						}else{
							setErrorMessage("No columns exist for the selected table "+dataAnalysisBean.getSelectedTable());
							renderChartColumns = false;
						}
					}else{
						setErrorMessage("");
					}
				}else{
					setErrorMessage("No tables for the selected database");
				}
			}else{
				setErrorMessage("");
			}
		}
	}
	
	//Invoked to generate charts
	public String generateCharts(){
		status = "FAIL";
		if(!dataAnalysisBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onDatabaseChange().equalsIgnoreCase("SUCCESS")){
				resetAnalysisFlags();
				setErrorMessage("Database changed.Please select the analysis to be performed");
			}else{
				setErrorMessage("");
			}
		}else{
			resetChartRender();
			if(dataAnalysisBean.getSelectedTable()!=null && !dataAnalysisBean.getSelectedTable().isEmpty()){
				if(dataAnalysisBean.getSelectedChartType()!=null && !dataAnalysisBean.getSelectedChartType().trim().isEmpty()){
					switch (dataAnalysisBean.getSelectedChartType().toLowerCase()) {
					case "pie":
						if(dataAnalysisBean.getSelectedChartColumn()!=null && !dataAnalysisBean.getSelectedChartColumn().trim().isEmpty()){
							//System.out.println("DataAnalysisActionBean.generateCharts() selected chart column "+dataAnalysisBean.getSelectedChartColumn());
							if(dataAnalysisBean.getSelectedChartColumn().contains("CATEGORICAL")){
								generateCategoricalChart("pie");
							}else{
								generateQuantitativeChart("pie");
							}
						}else{
							setErrorMessage("Select a column to generate charts");
						}
						break;
					case "bar":
						if(dataAnalysisBean.getSelectedChartColumn()!=null && !dataAnalysisBean.getSelectedChartColumn().trim().isEmpty()){
							//System.out.println("DataAnalysisActionBean.generateCharts() selected chart column "+dataAnalysisBean.getSelectedChartColumn());
							if(dataAnalysisBean.getSelectedChartColumn().contains("CATEGORICAL")){
								generateCategoricalChart("bar");
							}else{
								generateQuantitativeChart("bar");
							}
						}else{
							setErrorMessage("Select a categorical column to generate charts");
						}
						break;
					case "x-y":
						if(dataAnalysisBean.getSelectedXChartColumn()!=null && !dataAnalysisBean.getSelectedXChartColumn().trim().isEmpty()){
							if(dataAnalysisBean.getSelectedYChartColumn()!=null && !dataAnalysisBean.getSelectedYChartColumn().trim().isEmpty()){
								generateXYPlot("x-y");
							}else{
								setErrorMessage("Select a response column");
							}
						}else{
							setErrorMessage("Select a predictor column");
						}
						break;
					case "tm":
						if(dataAnalysisBean.getSelectedXChartColumn()!=null && !dataAnalysisBean.getSelectedXChartColumn().trim().isEmpty()){
							if(dataAnalysisBean.getSelectedYChartColumn()!=null && !dataAnalysisBean.getSelectedYChartColumn().trim().isEmpty()){
								generateXYPlot("tm");
							}else{
								setErrorMessage("Select a response column");
							}
						}else{
							setErrorMessage("Select a predictor column");
						}
						break;
					default:
						break;
					}
				}else{
					setErrorMessage("Please select a chart type to generate charts");
				}
			}else{
				setErrorMessage("Please select a table to generate charts");
			}
		}
		return status;
	}
	
	//Invoked when chart type is changed in the jsp page
	public void onChartChange(ValueChangeEvent e){
		if(!dataAnalysisBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onDatabaseChange().equalsIgnoreCase("SUCCESS")){
				resetAnalysisFlags();
				setErrorMessage("Database changed.Please select the analysis to be performed");
			}else{
				setErrorMessage("");
			}
		}else{
			clearSelectedColumns();
			resetChartRender();
			if(dataAnalysisBean.getSelectedTable()!=null && !dataAnalysisBean.getSelectedTable().trim().isEmpty()){
				//System.out.println("DataAnalysisActionBean.onChartChange() selected chartype is "+this.dataAnalysisBean.getSelectedChartType()+" or new value "+e.getNewValue() );
				if(this.dataAnalysisBean.getSelectedChartType()!=null && !dataAnalysisBean.getSelectedChartType().trim().isEmpty()){
					switch(e.getNewValue().toString().toLowerCase()){
					case "pie":
						generateDescriptiveColumns();
						dataAnalysisBean.setSelectedChartColumn(dataAnalysisBean.getDisplayColumns().get(0));
						if(dataAnalysisBean.getSelectedChartColumn().contains("CATEGORICAL")){
							renderCategoricalChartColumns = true;
						}else{
							renderCategoricalChartColumns = false;
						}
						renderChartColumns = true;
						renderChartVariables = false;
						//renderTimeChartColumns = false;
						break;
					case "bar":
						generateDescriptiveColumns();
						dataAnalysisBean.setSelectedChartColumn(dataAnalysisBean.getDisplayColumns().get(0));
						if(dataAnalysisBean.getSelectedChartColumn().contains("CATEGORICAL")){
							renderCategoricalChartColumns = true;
						}else{
							renderCategoricalChartColumns = false;
						}
						renderChartColumns = true;
						renderChartVariables = false;
						//renderTimeChartColumns = false;
						break;
					case "x-y":
						generateChartColumns();
						renderChartColumns = false;
						renderCategoricalChartColumns = false;
						//renderTimeChartColumns = false;
						if(dataAnalysisBean.getSelectedChartColumns()!=null && dataAnalysisBean.getSelectedChartColumns().size()>1){
							renderChartVariables = true;
						}else{
							setErrorMessage("Insufficient quantitative variables present in the table");
							renderChartVariables = false;
						}
						break;
					case "tm":
						generateChartColumns();
						renderChartColumns = false;
						renderCategoricalChartColumns = false;
						//renderTimeChartColumns = false;
						if(dataAnalysisBean.getSelectedChartColumns()!=null && dataAnalysisBean.getSelectedChartColumns().size()>1){
							renderChartVariables = true;
						}else{
							setErrorMessage("Insufficient quantitative variables present in the table");
							renderChartVariables = false;
						}
						break;
					default:
						setErrorMessage("Select a chart type");
						break;
					}
				}else{
					setErrorMessage("select a chart type");
				}
			}else{
				renderChartColumns =false;
				renderChartResults = false;
				renderChartVariables = false;
				setErrorMessage("No tables selected..Please select a table");
			}
		}
	}
	
	//Invoked when a column is changed in pie or bar chart
	public void onCategoryChange(ValueChangeEvent e){
		if(!dataAnalysisBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onDatabaseChange().equalsIgnoreCase("SUCCESS")){
				resetAnalysisFlags();
				setErrorMessage("Database changed.Please select the analysis to be performed");
			}else{
				setErrorMessage("");
			}
		}else{
			resetChartRender();
			//System.out.println("DataAnalysisActionBean.onCategoryChange() e is "+e.getNewValue());
			if(e.getNewValue()!=null){
				if(e.getNewValue().toString().contains("CATEGORICAL")){
					//System.out.println("DataAnalysisActionBean.onCategoryChange() new value is a categorical value");
					renderCategoricalChartColumns = true;
				}else{
					renderCategoricalChartColumns = false;
				}
			}
		}
	}
	
	//Invoked when database is changed on the jsp page
	private String onDatabaseChange(){
		status = "FAIL";
		dbbean.getUserBean().setDb_schema(dataAnalysisBean.getSelectedDatabase());
		this.dbbean.closeDBResources();
		try{
			if(this.dbbean.getConnection()!=null){
				if(dbbean.connect().equalsIgnoreCase("SUCCESS")){
					status = "SUCCESS";
				}else{
					//connection failed
					setErrorMessage("");
				}
			}else{
				setErrorMessage("Connection Expired");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	
	//Invoked when the table is changed in the dataanalysis.jsp page
	public void onTableChange(ValueChangeEvent event){
		//public void onTableChange(){
		if(!dataAnalysisBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onDatabaseChange().equalsIgnoreCase("SUCCESS")){
				//System.out.println("DataAnalysisActionBean.onTableChange() resetting flags");
				resetAnalysisFlags();
				setErrorMessage("Database changed.Please select the analysis to be performed");
			}else{
				setErrorMessage("");
			}
		}else{
			tableChanged = true;
			if(dbbean.getTableColumns(event.getNewValue().toString()).equalsIgnoreCase("SUCCESS")){
			//System.out.println("DataAnalysisActionBean.onTableChange() selected table is "+dataAnalysisBean.getSelectedTable());
			//if(dbbean.getTableColumns(dataAnalysisBean.getSelectedTable()).equalsIgnoreCase("SUCCESS")){
				clearSelectedColumns();
				renderDescriptiveResults = false;
				renderRegressionResults = false;
				renderChartResults = false;
				if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0){
					if(renderDescriptiveAnalysis){
						generateDescriptiveColumns();
						renderDescriptiveColumns = true;
					}else if(renderRegressionAnalysis){
						generateRegressionColumns();
					}else if(renderCharts){
						//System.out.println("DataAnalysisActionBean.onTableChange() in charts"+dataAnalysisBean.getSelectedChartType());
						dataAnalysisBean.setSelectedChartType("pie");
						generateDescriptiveColumns();
						dataAnalysisBean.setSelectedChartColumn(dataAnalysisBean.getDisplayColumns().get(0));
						//System.out.println("DataAnalysisActionBean.onTableChange() selected chart column is "+dataAnalysisBean.getSelectedChartColumn());
						if(dataAnalysisBean.getSelectedChartColumn().contains("CATEGORICAL")){
							renderCategoricalChartColumns = true;
						}else{
							renderCategoricalChartColumns = false;
						}
						renderChartColumns = true;
						renderChartTypes = true;
						renderChartVariables = false;
						//renderTimeChartColumns = false;
						resetChartRender();
					}
				}else{
					renderDescriptiveColumns = false;
					renderResponseColumns = false;
					renderPredictorColumns = false;
					renderChartColumns = false;
					renderChartVariables = false;
					//renderTimeChartColumns = false;
					renderCategoricalChartColumns = false;
					setErrorMessage( "No columns exist for the selected table");
				}
			}else{
				setErrorMessage("");
			}
		}
	}
	
	//Resets the display of charts
	private void resetChartRender() {
		renderPieChart = false;
		renderBarChart = false;
		renderXYChart = false;
		renderTimeSeriesChart = false;
	}

	//Invoked when column is changed in the dataanalysis.jsp for RegressionAnalysis
	public void onColumnChange(ValueChangeEvent event){
		if(!dataAnalysisBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onDatabaseChange().equalsIgnoreCase("SUCCESS")){
				//System.out.println("DataAnalysisActionBean.onTableChange() resetting flags");
				resetAnalysisFlags();
				setErrorMessage("Database changed.Please select the analysis to be performed");
			}else{
				setErrorMessage("");
			}
		}else{
			if(dataAnalysisBean.getResponseColumns()!=null && dataAnalysisBean.getResponseColumns().size()>0){
				if(dataAnalysisBean.getPredictorColumns()!=null){
					dataAnalysisBean.getPredictorColumns().clear();
				}
				List<String>tempList = new ArrayList<>();
				if(tableChanged){
					dataAnalysisBean.setSelectedResponseColumn(dataAnalysisBean.getResponseColumns().get(0));
					tableChanged = false;
				}else{
					dataAnalysisBean.setSelectedResponseColumn(event.getNewValue().toString());
				}
				//System.out.println("DataAnalysisActionBean.onColumnChange() response column is "+dataAnalysisBean.getSelectedResponseColumn());
				for(int i =0;i<dataAnalysisBean.getResponseColumns().size();i++){
					if(!dataAnalysisBean.getResponseColumns().get(i).equalsIgnoreCase(dataAnalysisBean.getSelectedResponseColumn())){
						tempList.add(dataAnalysisBean.getResponseColumns().get(i));
					}
				}
				dataAnalysisBean.setPredictorColumns(tempList);
				//tempList.clear();
			}
		}
	}
	
	//Invoked when schema is changed
	public String onSchemaChange(ValueChangeEvent event){
		status = "FAIL";
		dbbean.getUserBean().setDb_schema(event.getNewValue().toString());
		this.dbbean.closeDBResources();
		try{
			if(this.dbbean.getConnection()!=null){
				if(dbbean.connect().equalsIgnoreCase("SUCCESS")){
					status = "SUCCESS";
					if(!renderDescriptiveAnalysis && !renderDescriptiveAnalysis && !renderCharts){
						setErrorMessage("Please select any of the analysis options");
					}else{
						setErrorMessage("Schema changed.Select any action to perform analysis");
					}
					resetAnalysisFlags();
					resetOptionFlags();
					resetTables();
					clearSelectedColumns();
				}else{
					//connection failed
					setErrorMessage("");
				}
			}else{
				setErrorMessage("Connection Expired");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	
	//Generates pie/bar chart for categorical columns
	private String generateCategoricalChart(String chartType){
		status = "FAIL";
		//System.out.println("DataAnalysisActionBean.generateCharts() selected chart column "+dataAnalysisBean.getSelectedChartColumn());
		String chartColumn = getValueFromDisplay(dataAnalysisBean.getSelectedChartColumn().trim());
		//System.out.println("DataAnalysisActionBean.generateCharts() selected column is "+chartColumn);
		if(!dataAnalysisBean.getSelectedGroupByOption().equalsIgnoreCase("CountryCode")){
			if(!dataAnalysisBean.getSelectedTable().equalsIgnoreCase("Country")){
				executionQuery = "SELECT "+dataAnalysisBean.getSelectedTable()+"."+chartColumn+","+"Country."+dataAnalysisBean.getSelectedGroupByOption()+" FROM "+dataAnalysisBean.getSelectedTable()+",Country WHERE Country.Code="+dataAnalysisBean.getSelectedTable()+".CountryCode";
			}else{
				executionQuery = "SELECT "+dataAnalysisBean.getSelectedTable()+"."+chartColumn+","+dataAnalysisBean.getSelectedGroupByOption()+" FROM "+dataAnalysisBean.getSelectedTable();
			}
		}else{
			if(!dataAnalysisBean.getSelectedTable().equalsIgnoreCase("Country")){
				executionQuery = "SELECT "+dataAnalysisBean.getSelectedTable()+"."+chartColumn+",CountryCode FROM "+dataAnalysisBean.getSelectedTable();
			}else{
				setErrorMessage("Please select other group by options for country table");
				return status;
			}
		}
		//executionQuery = "SELECT "+pieColumn+",CountryCode FROM "+dataAnalysisBean.getSelectedTable();
		//System.out.println("DataAnalysisActionBean.generateCharts() execution query sis "+executionQuery);
		if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
			Map<String,Integer> chartMap= new HashMap<>();
			if(dbbean.getResultSet()!=null){
				resultSet = dbbean.getResultSet();
				try {
					int rowCount = 0;
					while(resultSet.next()){
						rowCount++;
						//System.out.println("DataAnalysisActionBean.generateCharts() column:"+resultSet.getString(chartColumn)+" groupbyoption:"+resultSet.getString(dataAnalysisBean.getSelectedGroupByOption()));
						if (chartMap.containsKey(resultSet.getString(dataAnalysisBean.getSelectedGroupByOption()))) {
							chartMap.put(resultSet.getString(dataAnalysisBean.getSelectedGroupByOption()), chartMap.get(resultSet.getString(dataAnalysisBean.getSelectedGroupByOption())) + 1);
					    } else {
					    	chartMap.put(resultSet.getString(dataAnalysisBean.getSelectedGroupByOption()),1);
					    }
					}
					//System.out.println("DataAnalysisActionBean.generateCharts() map size is "+chartMap.size());
					//System.out.println("DataAnalysisActionBean.generateCharts() map size is "+rowCount);
					//status = chartsBean.generatePieChart(chartMap, rowCount,chartColumn,dataAnalysisBean.getSelectedTable(),dataAnalysisBean.getSelectedGroupByOption(),dbbean.getUserBean().getUsername());
					status = chartsBean.generateCategoricalChart(chartMap, rowCount,chartColumn,dataAnalysisBean.getSelectedTable(),dataAnalysisBean.getSelectedGroupByOption(),dbbean.getUserBean().getUsername(),chartType);
					if(status.equalsIgnoreCase("SUCCESS")){
						status = "SUCCESS";
						//renderChartResults = true;
						if(chartType.equals("pie")){
							resetChartRender();
							renderPieChart = true;
						}else{
							resetChartRender();
							renderBarChart = true;
						}
					}else{
						setErrorMessage(status);
						status = "FAIL";
					}
					
				}catch(SQLException e){
					setErrorMessage("SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage());
				}
			}
		}else{
			status = "FAIL";
			setErrorMessage("");
		}
		return status;
	}
	
	//generates pie/bar chart for quantitative variables
	private String generateQuantitativeChart(String chartType){
		status = "FAIL";
		String chartColumn = getValueFromDisplay(dataAnalysisBean.getSelectedChartColumn().trim());
		//System.out.println("DataAnalysisActionBean.generateCharts() selected column is "+chartColumn);
		executionQuery = "SELECT "+chartColumn+" FROM "+dataAnalysisBean.getSelectedTable();
		//System.out.println("DataAnalysisActionBean.generateCharts() execution query sis "+executionQuery);
		if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
			try{
				this.resultSet = dbbean.getResultSet();
				List<Double> tempList = new ArrayList<>();
				if(resultSet!=null){
					while(resultSet.next()){
						switch(getColumnType(chartColumn)){
						case "int":
							tempList.add((double) resultSet.getInt(1));
							break;
						case "smallint":
							tempList.add((double) resultSet.getInt(1));
							break;
						case "float":
							tempList.add((double) resultSet.getFloat(1));
							break;
						case "double":
							tempList.add((double) resultSet.getDouble(1));
							break;
						case "long":
							tempList.add((double) resultSet.getLong(1));
							break;
						default:
							tempList.add((double) resultSet.getDouble(1));
							break;
						}
					}
					if(tempList!=null && tempList.size()>0){
						double[] temp = new double[tempList.size()];
						for(int j=0;j<tempList.size();j++){
							temp[j] = tempList.get(j).doubleValue();
						}
						dABean.setColumn(chartColumn);
						if(dABean.generateStats(temp)){
							status = chartsBean.generateQuantitativeChart(dABean,tempList,chartType,chartColumn,dbbean.getUserBean().getUsername(),dataAnalysisBean.getSelectedTable());
							if(status.equalsIgnoreCase("SUCCESS")){
								status = "SUCCESS";
								//renderChartResults = true;
								if(chartType.equals("pie")){
									renderPieChart = true;
								}else{
									renderBarChart = true;
								}
							}else{
								setErrorMessage(status);
								status = "FAIL";
							}
						}else{
							setErrorMessage("Unable to generate Descriptive statistics");
						}
					}else{
						setErrorMessage("No data present in the database for the selected column");
					}
				}else{
					setErrorMessage("ResultSet is empty");
				}
			}catch (SQLException e) {
				setErrorMessage("SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage());
			}
		}else{
			status = "FAIL";
			setErrorMessage("");
		}
		return status;
	}
	
	//Generates time series or XY plot for quantitative columns
	private String generateXYPlot(String chartType){
		status = "FAIL";
		if(!dataAnalysisBean.getSelectedXChartColumn().equalsIgnoreCase(dataAnalysisBean.getSelectedYChartColumn())){
			executionQuery = "SELECT "+dataAnalysisBean.getSelectedXChartColumn()+","+dataAnalysisBean.getSelectedYChartColumn()+" FROM "+dataAnalysisBean.getSelectedTable();
			//System.out.println("DataAnalysisActionBean.generateCharts() execution query sis "+executionQuery);
			if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
				resultSet = dbbean.getResultSet();
				chartsBean.clearDataSets(dataAnalysisBean.getSelectedXChartColumn(), dataAnalysisBean.getSelectedYChartColumn(),chartType);
				if(this.resultSet!=null){
					try {
						double rowCount =1;
						while(resultSet.next()){
							//System.out.println("DataAnalysisActionBean.generateCharts() column 1 value is "+resultSet.getString(dataAnalysisBean.getSelectedXChartColumn()));
							//System.out.println("DataAnalysisActionBean.generateCharts() column 1 value is "+resultSet.getString(dataAnalysisBean.getSelectedYChartColumn()));
							if(resultSet.getString(dataAnalysisBean.getSelectedXChartColumn())!=null && resultSet.getString(dataAnalysisBean.getSelectedYChartColumn())!=null){
								if(chartType.equalsIgnoreCase("x-y")){
									chartsBean.scatterData(Double.parseDouble(resultSet.getString(dataAnalysisBean.getSelectedXChartColumn())), Double.parseDouble(resultSet.getString(dataAnalysisBean.getSelectedYChartColumn())));
								}else{
									chartsBean.timeSeriesData(Double.parseDouble(resultSet.getString(dataAnalysisBean.getSelectedXChartColumn())), Double.parseDouble(resultSet.getString(dataAnalysisBean.getSelectedYChartColumn())),rowCount);
								}
							}else if(resultSet.getString(dataAnalysisBean.getSelectedXChartColumn())==null && resultSet.getString(dataAnalysisBean.getSelectedYChartColumn())!=null){
								if(chartType.equalsIgnoreCase("x-y")){
									chartsBean.scatterData(0.0, Double.parseDouble(resultSet.getString(dataAnalysisBean.getSelectedYChartColumn())));
								}else{
									chartsBean.timeSeriesData(0.0, Double.parseDouble(resultSet.getString(dataAnalysisBean.getSelectedYChartColumn())),rowCount);
								}
							}else if(resultSet.getString(dataAnalysisBean.getSelectedYChartColumn())==null && resultSet.getString(dataAnalysisBean.getSelectedXChartColumn())!=null){
								if(chartType.equalsIgnoreCase("x-y")){
									chartsBean.scatterData(Double.parseDouble(resultSet.getString(dataAnalysisBean.getSelectedXChartColumn())),0.0);
								}else{
									chartsBean.timeSeriesData(Double.parseDouble(resultSet.getString(dataAnalysisBean.getSelectedXChartColumn())),0.0,rowCount);
								}
							}else if(resultSet.getString(dataAnalysisBean.getSelectedXChartColumn())==null && resultSet.getString(dataAnalysisBean.getSelectedYChartColumn())==null){
								if(chartType.equalsIgnoreCase("x-y")){
									chartsBean.scatterData(0.0,0.0);
								}else{
									chartsBean.timeSeriesData(0.0,0.0,rowCount);
								}
							}
							rowCount++;
						}
						if(chartType.equalsIgnoreCase("x-y")){
							status = chartsBean.generateScatterPlot(dataAnalysisBean.getSelectedXChartColumn(),dataAnalysisBean.getSelectedYChartColumn(),dbbean.getUserBean().getUsername());
							if(status.equalsIgnoreCase("SUCCESS")){
								renderXYChart = true;
							}else{
								renderXYChart = false;
							}
						}else{
							status = chartsBean.generateTimesSeriesPlot(dataAnalysisBean.getSelectedXChartColumn(),dataAnalysisBean.getSelectedYChartColumn(),dbbean.getUserBean().getUsername());
							if(status.equalsIgnoreCase("SUCCESS")){
								renderTimeSeriesChart = true;
							}else{
								renderTimeSeriesChart = false;
							}
						}
					} catch (SQLException e) {
						setErrorMessage("SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage());
						e.printStackTrace();
					}
				}
				
			}else{
				setErrorMessage("");
			}
		}else{
			setErrorMessage("select different predictor and response columns");
		}
		return status;
	}
	
	//Reset the display of analysis page
	private void resetOptionFlags() {
		renderDescriptiveAnalysis = false;
		renderRegressionAnalysis = false;
		renderCharts = false;
	}
	
	//resets all render flags
	private void resetAnalysisFlags() {
		renderTables = false;
		renderDescriptiveColumns = false;
		renderDescriptiveResults = false;
		renderRegressionResults = false;
		renderChartResults = false;
		renderChartColumns = false;
		renderChartTypes = false;
		renderChartVariables = false;
		renderPredictorColumns = false;
		renderResponseColumns = false;
		renderCategoricalChartColumns = false;
		//renderTimeChartColumns = false;
		executionQuery = "";
		tableChanged = false;
		renderPieChart = false;
		renderBarChart = false;
		renderXYChart = false;
		renderTimeSeriesChart = false;

	}
	
	//resets selected table and tables list
	private void resetTables(){
		if(dataAnalysisBean.getSelectedTable()!=null && !dataAnalysisBean.getSelectedTable().isEmpty()){
			dataAnalysisBean.setSelectedTable("");
		}
		if(dataAnalysisBean.getTables()!=null && dataAnalysisBean.getTables().size()>0){
			dataAnalysisBean.getTables().clear();
		}
	}
	
	// Retrieves quantitative columns from a database tables list
	private List<String> getQuantitativeColumns() {
		List<String> tempList = new ArrayList<>();
		if(dataAnalysisBean.getSelectedDisplayColumns()!=null && dataAnalysisBean.getSelectedDisplayColumns().size()>0){
			for(int i=0;i<dataAnalysisBean.getSelectedDisplayColumns().size();i++){
				String colType = getColumnType(dataAnalysisBean.getSelectedDisplayColumns().get(i)).toLowerCase();
				if(colType.equalsIgnoreCase("smallint") || colType.equalsIgnoreCase("int") || colType.equalsIgnoreCase("long") || colType.equalsIgnoreCase("float") ||colType.equalsIgnoreCase("double")){
					tempList.add(colType);
				}
			}
		}
		return tempList;
	}
	
	//Generates columns for descriptive analysis
	private void generateDescriptiveColumns() {
		if(this.dbbean.getColumns()!=null && this.dbbean.getColumnsType()!=null && this.dbbean.getColumns().size()>0 && this.dbbean.getColumnsType().size()>0){
			if(this.dataAnalysisBean.getDisplayColumns()!=null){
				this.dataAnalysisBean.getDisplayColumns().clear();
			}
			List<String> tempList = new ArrayList<String>();
			for(int i=0;i<dbbean.getColumns().size();i++){
				if(dbbean.getColumnsType().get(i).equalsIgnoreCase("int") || dbbean.getColumnsType().get(i).equalsIgnoreCase("float") || dbbean.getColumnsType().get(i).equalsIgnoreCase("double") || dbbean.getColumnsType().get(i).equalsIgnoreCase("smallint") || dbbean.getColumnsType().get(i).equalsIgnoreCase("long")){
					tempList.add(dbbean.getColumns().get(i)+"(QUANTITATIVE)");
				}else{
					tempList.add(dbbean.getColumns().get(i)+"(CATEGORICAL)");
				}
			}
			this.dataAnalysisBean.setDisplayColumns(tempList);
		}
		
	}
	
	//Generates columns for regression analysis
	private void generateRegressionColumns() {
		if(this.dbbean.getColumns()!=null && this.dbbean.getColumnsType()!=null && this.dbbean.getColumns().size()>0 && this.dbbean.getColumnsType().size()>0){
			if(dataAnalysisBean.getResponseColumns()!=null){
				dataAnalysisBean.getResponseColumns().clear();
			}
			if(dataAnalysisBean.getPredictorColumns()!=null){
				dataAnalysisBean.getPredictorColumns().clear();
			}
			List<String> tempList = new ArrayList<String>();
			for(int i=0;i<dbbean.getColumns().size();i++){
				if(dbbean.getColumnsType().get(i).equalsIgnoreCase("int") || dbbean.getColumnsType().get(i).equalsIgnoreCase("float") || dbbean.getColumnsType().get(i).equalsIgnoreCase("double") || dbbean.getColumnsType().get(i).equalsIgnoreCase("smallint") || dbbean.getColumnsType().get(i).equalsIgnoreCase("long")){
					tempList.add(dbbean.getColumns().get(i));
				}
			}
			//System.out.println("DataAnalysisActionBean.generateRegressionColumns() selected response column is "+dataAnalysisBean.getResponseColumns().get(0));
			dataAnalysisBean.setResponseColumns(tempList);
			List<String>tempPredList = new ArrayList<>();
			if(dataAnalysisBean.getResponseColumns()!=null && dataAnalysisBean.getResponseColumns().size()>1){
				dataAnalysisBean.setSelectedResponseColumn(dataAnalysisBean.getResponseColumns().get(0));
				for(int i=0;i<dataAnalysisBean.getResponseColumns().size();i++){
					if(!dataAnalysisBean.getResponseColumns().get(i).equalsIgnoreCase(dataAnalysisBean.getSelectedResponseColumn())){
						tempPredList.add(dataAnalysisBean.getResponseColumns().get(i));
					}
				}
				renderResponseColumns = true;
				renderPredictorColumns = true;
				dataAnalysisBean.setPredictorColumns(tempPredList);
				dataAnalysisBean.setSelectedPredictorColumn("");
			}else{
				renderResponseColumns = false;
				renderPredictorColumns = false;
				clearSelectedColumns();
				if(dataAnalysisBean.getResponseColumns().size()==1){
					setErrorMessage("Only one quantitative column present in the table.");
				}else{
					setErrorMessage("No quantitative columns present in the table.");
				}
			}
		}
	}
	
	//Generates chart columns for Charts from database bean
	private void generateChartColumns() {
		if(this.dbbean.getColumns()!=null && this.dbbean.getColumnsType()!=null && this.dbbean.getColumns().size()>0 && this.dbbean.getColumnsType().size()>0){
			if(dataAnalysisBean.getSelectedChartColumns()!=null){
				dataAnalysisBean.getSelectedChartColumns().clear();
			}
			List<String> tempList = new ArrayList<String>();
			for(int i=0;i<dbbean.getColumns().size();i++){
				if(dbbean.getColumnsType().get(i).equalsIgnoreCase("int") || dbbean.getColumnsType().get(i).equalsIgnoreCase("float") || dbbean.getColumnsType().get(i).equalsIgnoreCase("double") || dbbean.getColumnsType().get(i).equalsIgnoreCase("smallint") || dbbean.getColumnsType().get(i).equalsIgnoreCase("long")){
					tempList.add(dbbean.getColumns().get(i));
				}
			}
			dataAnalysisBean.setSelectedChartColumns(tempList);
		}
	}

	//Clears all selected columns in the dataanalysis backing bean
	private void clearSelectedColumns(){
		if(dataAnalysisBean.getSelectedDisplayColumn()!=null && !dataAnalysisBean.getSelectedDisplayColumn().isEmpty()){
			dataAnalysisBean.setSelectedDisplayColumn("");
		}
		if(dataAnalysisBean.getSelectedResponseColumn()!=null && !dataAnalysisBean.getSelectedResponseColumn().isEmpty()){
			dataAnalysisBean.setSelectedResponseColumn("");
		}
		if(dataAnalysisBean.getSelectedPredictorColumn()!=null && !dataAnalysisBean.getSelectedPredictorColumn().isEmpty()){
			dataAnalysisBean.setSelectedPredictorColumn("");
		}
		if(dataAnalysisBean.getPredictorColumns()!=null &&  dataAnalysisBean.getPredictorColumns().size()>0){
			dataAnalysisBean.getPredictorColumns().clear();
		}
		if(dataAnalysisBean.getSelectedDisplayColumns()!=null && dataAnalysisBean.getSelectedDisplayColumns().size()>0){
			dataAnalysisBean.getSelectedDisplayColumns().clear();
		}
		if(dataAnalysisBean.getResponseColumns()!=null && dataAnalysisBean.getResponseColumns().size()>0){
			dataAnalysisBean.getResponseColumns().clear();
		}
		if(dataAnalysisBean.getSelectedChartColumns()!=null && dataAnalysisBean.getSelectedChartColumns().size()>0){
			dataAnalysisBean.getSelectedChartColumns().clear();
		}
		if(dataAnalysisBean.getSelectedXChartColumn()!=null && !dataAnalysisBean.getSelectedXChartColumn().trim().isEmpty()){
			dataAnalysisBean.setSelectedXChartColumn("");;
		}
		if(dataAnalysisBean.getSelectedYChartColumn()!=null && !dataAnalysisBean.getSelectedYChartColumn().trim().isEmpty()){
			dataAnalysisBean.setSelectedYChartColumn("");;
		}
	}
	
	//retrieve the column value from the selected column in the dataanalysis.jsp page
	private String getSelectedColumn(String column) {
		String[] colData = column.split("\\(");
		if(colData[1].contains("QUANTITATIVE")){
			return colData[0];
		}
		return null;
	}
	
	//Retrieves the datatype for the selected column in the database
	private String getColumnType(String selectedColumn) {
		StringBuilder sb = new StringBuilder();
		String selColumn = selectedColumn.split("\\(")[0];
		//System.out.println("DataAnalysisActionBean.getColumnType() selected column is "+selColumn);
		if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0 && dbbean.getColumnsType()!=null && dbbean.getColumnsType().size()>0){
			for(int i=0;i<dbbean.getColumns().size();i++){
				if(dbbean.getColumns().get(i).equalsIgnoreCase(selColumn)){
					sb.append(dbbean.getColumnsType().get(i));
					break;
				}
			}
		}
		return sb.toString();
	}
	
	//generates the column name from the display column
	private String getValueFromDisplay(String value){
		StringBuilder sb = new StringBuilder();
		String[] valArray = value.split("\\(");
		if(valArray[1].contains("QUANTITATIVE")){
			sb.append(valArray[0]);
		}else{
			sb.append(valArray[0]);
		}
		return sb.toString();
	}
	
	//Sets the error message to the dataanalysis.jsp page
	private void setErrorMessage(String message){
		if(message.equalsIgnoreCase("")){
			this.errorMessageBean.setErrorMessage(dbbean.getErrorMessage());
		}else{
			this.errorMessageBean.setErrorMessage(message);
		}
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(this.errorMessageBean.getErrorMessage()));
		renderErrorMessages = true;
	}
}
