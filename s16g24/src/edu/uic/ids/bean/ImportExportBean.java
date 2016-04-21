package edu.uic.ids.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.fileupload.UploadedFile;

@ManagedBean(name="importExportBean",eager=true)
@ViewScoped
public class ImportExportBean implements Cloneable,Serializable{
	private static final long serialVersionUID = -1995895744938198690L;
	private DatabaseAccessBean dbbean;
	private String selectedTable;
	private String selectedDatabase;
	private String exportOption;
	private List<String> databases;
	private List<String> tables;
	private List<String> tableColumns;
	private String fileLabel; 
	private UploadedFile uploadedFile;
	
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
	public String getExportOption() {
		return exportOption;
	}
	public void setExportOption(String exportOption) {
		this.exportOption = exportOption;
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
	public List<String> getTableColumns() {
		return tableColumns;
	}
	public void setTableColumns(List<String> tableColumns) {
		this.tableColumns = tableColumns;
	}
	public String getFileLabel() {
		return fileLabel;
	}
	public void setFileLabel(String fileLabel) {
		this.fileLabel = fileLabel;
	}
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
}
