package edu.uic.ids.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="dataAnalysisBean",eager=true)
@ViewScoped
public class DataAnalysisBean implements Serializable,Cloneable{
	private static final long serialVersionUID = 7472434465737824159L;
	private DatabaseAccessBean dbbean;
	private String selectedTable;
	private String selectedDatabase;
	private String selectedDisplayColumn;
	private String selectedResponseColumn;
	private String selectedPredictorColumn;
	private String selectedChartType;
	private String selectedXChartColumn;
	private String selectedYChartColumn;
	private String selectedChartColumn;
	private String selectedGroupByOption;
	private List<String> databases;
	private List<String> tables;
	private List<String>responseColumns;
	private List<String>predictorColumns;
	private List<String>selectedPredictorColumns;
	private List<String>selectedChartColumns;
	private List<String>displayColumns;
	private List<String>selectedDisplayColumns;
	@PostConstruct
	public void init(){
		Map <String, Object> m =  FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		dbbean = (DatabaseAccessBean)m.get("databaseAccessBean");
		databases = dbbean.getDatabases();
		if(databases!=null && databases.size()>0){
			setSelectedDatabase(dbbean.getDatabases().get(0));
		}
	}
	public DatabaseAccessBean getDbbean() {
		return dbbean;
	}

	public void setDbbean(DatabaseAccessBean dbbean) {
		this.dbbean = dbbean;
	}

	public String getSelectedDisplayColumn() {
		return selectedDisplayColumn;
	}
	public void setSelectedDisplayColumn(String selectedDisplayColumn) {
		this.selectedDisplayColumn = selectedDisplayColumn;
	}
	public List<String> getDisplayColumns() {
		return displayColumns;
	}
	public void setDisplayColumns(List<String> displayColumns) {
		this.displayColumns = displayColumns;
	}
	public List<String> getSelectedDisplayColumns() {
		return selectedDisplayColumns;
	}
	public void setSelectedDisplayColumns(List<String> selectedDisplayColumns) {
		this.selectedDisplayColumns = selectedDisplayColumns;
	}
	public String getSelectedTable() {
		return selectedTable;
	}
	public void setSelectedTable(String selectedTable) {
		this.selectedTable = selectedTable;
	}
	public String getSelectedDatabase() {
		return selectedDatabase;
	}
	public void setSelectedDatabase(String selectedDatabase) {
		this.selectedDatabase = selectedDatabase;
	}
	public List<String> getDatabases() {
		return databases;
	}
	public void setDatabases(List<String> databases) {
		this.databases = databases;
	}
	public List<String> getTables() {
		return tables;
	}
	public void setTables(List<String> tables) {
		this.tables = tables;
	}
	public String getSelectedResponseColumn() {
		return selectedResponseColumn;
	}
	public void setSelectedResponseColumn(String selectedResponseColumn) {
		this.selectedResponseColumn = selectedResponseColumn;
	}
	public String getSelectedPredictorColumn() {
		return selectedPredictorColumn;
	}
	public void setSelectedPredictorColumn(String selectedPredictorColumn) {
		this.selectedPredictorColumn = selectedPredictorColumn;
	}
	public String getSelectedChartType() {
		return selectedChartType;
	}
	public void setSelectedChartType(String selectedChartType) {
		this.selectedChartType = selectedChartType;
	}
	public String getSelectedXChartColumn() {
		return selectedXChartColumn;
	}
	public void setSelectedXChartColumn(String selectedXChartColumn) {
		this.selectedXChartColumn = selectedXChartColumn;
	}
	public String getSelectedYChartColumn() {
		return selectedYChartColumn;
	}
	public void setSelectedYChartColumn(String selectedYChartColumn) {
		this.selectedYChartColumn = selectedYChartColumn;
	}
	public String getSelectedChartColumn() {
		return selectedChartColumn;
	}
	public void setSelectedChartColumn(String selectedChartColumn) {
		this.selectedChartColumn = selectedChartColumn;
	}
	public String getSelectedGroupByOption() {
		return selectedGroupByOption;
	}
	public void setSelectedGroupByOption(String selectedGroupByOption) {
		this.selectedGroupByOption = selectedGroupByOption;
	}
	public List<String> getResponseColumns() {
		return responseColumns;
	}
	public void setResponseColumns(List<String> responseColumns) {
		this.responseColumns = responseColumns;
	}
	public List<String> getPredictorColumns() {
		return predictorColumns;
	}
	public void setPredictorColumns(List<String> predictorColumns) {
		this.predictorColumns = predictorColumns;
	}
	public List<String> getSelectedPredictorColumns() {
		return selectedPredictorColumns;
	}
	public void setSelectedPredictorColumns(List<String> selectedPredictorColumns) {
		this.selectedPredictorColumns = selectedPredictorColumns;
	}
	public List<String> getSelectedChartColumns() {
		return selectedChartColumns;
	}
	public void setSelectedChartColumns(List<String> selectedChartColumns) {
		this.selectedChartColumns = selectedChartColumns;
	}
}
