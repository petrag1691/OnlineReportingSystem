package edu.uic.ids.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="dBAccessBean",eager=true)
@ViewScoped
public class DBAccessBean implements Serializable,Cloneable{
	private static final long serialVersionUID = -5815026046239219674L;
	private DatabaseAccessBean dbbean;
	private String database;
	private String selectedTable;
	private String inputQuery;
	private List<String> databases;
	private List<String> tables;
	private List<String> columns;
	private List<String> displayColumns;
	private List<String> selectedColumns;
	private List<String> selectedDisplayColumns;
	private boolean renderPermissions;
	private boolean renderClonePermissions;
	@PostConstruct
	public void init(){
		Map <String, Object> m =  FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		dbbean = (DatabaseAccessBean)m.get("databaseAccessBean");
		databases = dbbean.getDatabases();
		if(dbbean.getUserBean().getDb_schema().equalsIgnoreCase(dbbean.getUserBean().getUsername())){
			renderPermissions = true;
			renderClonePermissions = false;
		}else{
			renderPermissions = false;
			renderClonePermissions = true;
		}
	}
	public DatabaseAccessBean getDbbean() {
		return dbbean;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getInputQuery() {
		return inputQuery;
	}
	public void setInputQuery(String inputQuery) {
		this.inputQuery = inputQuery;
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
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public List<String> getDisplayColumns() {
		return displayColumns;
	}
	public void setDisplayColumns(List<String> displayColumns) {
		this.displayColumns = displayColumns;
	}
	public String getSelectedTable() {
		return selectedTable;
	}
	public void setSelectedTable(String selectedTable) {
		this.selectedTable = selectedTable;
	}
	public List<String> getSelectedColumns() {
		return selectedColumns;
	}
	public void setSelectedColumns(List<String> selectedColumns) {
		this.selectedColumns = selectedColumns;
	}
	public List<String> getSelectedDisplayColumns() {
		return selectedDisplayColumns;
	}
	public void setSelectedDisplayColumns(List<String> selectedDisplayColumns) {
		this.selectedDisplayColumns = selectedDisplayColumns;
	}
	public boolean isRenderPermissions() {
		return renderPermissions;
	}
	public void setRenderPermissions(boolean renderPermissions) {
		this.renderPermissions = renderPermissions;
	}
	public boolean isRenderClonePermissions() {
		return renderClonePermissions;
	}
	public void setRenderClonePermissions(boolean renderClonePermissions) {
		this.renderClonePermissions = renderClonePermissions;
	}
	public void swap(int firstInd, int secondInd )
	{
	   String temp = this.databases.get( firstInd ) ;
	   this.databases.set( firstInd, this.databases.get( secondInd ) ) ;
	   this.databases.set( secondInd, temp ) ;
	}
}
