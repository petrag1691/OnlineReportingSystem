package edu.uic.ids.bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import edu.uic.ids.constants.FinalConstants;

@ManagedBean
@ViewScoped
//Controller bean for databaseaccess page
public class DatabaseActionBean implements Serializable,Cloneable{
	private static final long serialVersionUID = 1639244009908352185L;
	private DatabaseAccessBean dbbean;
	@ManagedProperty(value="#{dBAccessBean}")
	private DBAccessBean dBAccessBean;
	@ManagedProperty(value="#{errorMessageBean}")
	private ErrorMessageBean errorMessageBean;
	private boolean renderTables;
	private boolean renderColumns;
	private boolean renderMetaData;
	private boolean renderOtrQueryResults;
	private boolean renderSelQueryResults;
	private boolean renderDataTable;
	private boolean renderErrorMessages;
	private int columnsCount;
	private int rowsCount;
	private Result result;
	private ResultSet resultSet;
	private ResultSetMetaData rsmd;
	@PostConstruct
	public void init(){
		Map <String, Object> m =  FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		dbbean = (DatabaseAccessBean) m.get("databaseAccessBean");
		renderTables = false;
		renderColumns = false;
	}
	public DatabaseAccessBean getDbbean() {
		return dbbean;
	}
	public void setDbbean(DatabaseAccessBean dbbean) {
		this.dbbean = dbbean;
	}
	public DBAccessBean getdBAccessBean() {
		return dBAccessBean;
	}
	public void setdBAccessBean(DBAccessBean dBAccessBean) {
		this.dBAccessBean = dBAccessBean;
	}
	public ErrorMessageBean getErrorMessageBean() {
		return errorMessageBean;
	}
	public void setErrorMessageBean(ErrorMessageBean errorMessageBean) {
		this.errorMessageBean = errorMessageBean;
	}
	public boolean isRenderColumns() {
		return renderColumns;
	}
	public boolean isRenderTables() {
		return renderTables;
	}
	public void setRenderTables(boolean renderTables) {
		this.renderTables = renderTables;
	}
	public void setRenderColumns(boolean renderColumns) {
		this.renderColumns = renderColumns;
	}
	public boolean isRenderMetaData() {
		return renderMetaData;
	}
	public void setRenderMetaData(boolean renderMetaData) {
		this.renderMetaData = renderMetaData;
	}
	public boolean isRenderOtrQueryResults() {
		return renderOtrQueryResults;
	}
	public void setRenderOtrQueryResults(boolean renderOtrQueryResults) {
		this.renderOtrQueryResults = renderOtrQueryResults;
	}
	public boolean isRenderSelQueryResults() {
		return renderSelQueryResults;
	}
	public void setRenderSelQueryResults(boolean renderSelQueryResults) {
		this.renderSelQueryResults = renderSelQueryResults;
	}
	public boolean isRenderDataTable() {
		return renderDataTable;
	}
	public void setRenderDataTable(boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}
	public boolean isRenderErrorMessages() {
		return renderErrorMessages;
	}
	public void setRenderErrorMessages(boolean renderErrorMessages) {
		this.renderErrorMessages = renderErrorMessages;
	}
	public int getColumnsCount() {
		return columnsCount;
	}
	public void setColumnsCount(int columnsCount) {
		this.columnsCount = columnsCount;
	}
	public int getRowsCount() {
		return rowsCount;
	}
	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}
	public Result getResult() {
		return result;
	}
	public ResultSet getResultSet() {
		return resultSet;
	}
	public ResultSetMetaData getRsmd() {
		return rsmd;
	}
	
	//Retrieves tables to display on the jsp page
	public String getTableNames(){
		if(!dBAccessBean.getDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			changeSchema();
			getTableNames();
		}else{
			if(dbbean.getTablesFromDatabase().equalsIgnoreCase("SUCCESS")){
				if(dbbean.getTables()!=null && dbbean.getTables().size()>0){
					this.dBAccessBean.setTables(dbbean.getTables());
					renderTables = true;
					renderErrorMessages = false;
				}else{
					setErrorMessage(799, "No tables found in the selected database");
					renderTables = false;
					renderErrorMessages = true;
				}
				dBAccessBean.setSelectedTable("");
				dBAccessBean.setInputQuery("");
				clearSelectedColumns();
				renderColumns = false;
				renderMetaData = false;
				renderDataTable = false;
			}else{
				setErrorMessage(800,"");
			}
		}
		return "SUCCESS";
	}
	
	//
	public String getTableColumns(){
		//System.out.println("DatabaseActionBean.getTableColumns() selected table is "+dBAccessBean.getSelectedTable());
		if(!dBAccessBean.getDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			changeSchema();
		}else{
			if(dBAccessBean.getSelectedTable()!=null && !dBAccessBean.getSelectedTable().trim().equalsIgnoreCase("")){
				clearSelectedColumns();
				//System.out.println("DatabaseActionBean.getColumns() returning selected columns for table"+this.dBAccessBean.getSelectedTable());
				if(dbbean.getTableColumns(dBAccessBean.getSelectedTable()).equalsIgnoreCase("SUCCESS")){
					if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0){
						this.dBAccessBean.setColumns(dbbean.getColumns());
						//System.out.println("DatabaseActionBean.getTableColumns() columns in view "+dBAccessBean.getColumns());
						generateDisplayColumns();
						//System.out.println("DatabaseActionBean.getTableColumns() columns are "+dbbean.getColumns());
						this.renderColumns= true;
						renderErrorMessages = false;
						//System.out.println("DatabaseActionBean.getTableColumns() renderColumns is "+renderColumns);
					}else{
						renderColumns = false;
						renderErrorMessages = true;
						setErrorMessage(850, "No columns exist for the selected table");
					}
					dBAccessBean.setInputQuery("");
					renderDataTable = false;
					renderMetaData = false;
				}else{
					setErrorMessage(801,"");
				}
			}else{
				setErrorMessage(900,"No table selected.Please select table");
			}
		}
		return "SUCCESS";
	}


	public String displayTable(){
		if(!dBAccessBean.getDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			changeSchema();
		}else{
			if(dBAccessBean.getSelectedTable()!=null && !dBAccessBean.getSelectedTable().trim().equalsIgnoreCase("")){
				clearSelectedColumns();
				if(dbbean.getTableColumns(dBAccessBean.getSelectedTable()).equalsIgnoreCase("SUCCESS")){
					dBAccessBean.setSelectedColumns(dbbean.getColumns());
					dBAccessBean.setInputQuery("SELECT * FROM "+dBAccessBean.getSelectedTable());
					String status = dbbean.processQuery(dBAccessBean.getInputQuery());
					if(status.equalsIgnoreCase("SUCCESS")){
						this.resultSet = dbbean.getResultSet();
						generateMetaData();
						renderColumns = false;
						renderErrorMessages = false;
					}else{
						setErrorMessage(802, "");
					}
				}else{
					setErrorMessage(802, "");
				}
			}else{
				setErrorMessage(900,"No table selected.Please select table");
			}
		}
		return "SUCCESS";
	}
	public String displaySelectedColumns(){
		if(!dBAccessBean.getDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			changeSchema();
		}else{
			if(dBAccessBean.getSelectedTable()!=null && !dBAccessBean.getSelectedTable().trim().equalsIgnoreCase("")){
				if(dBAccessBean.getSelectedDisplayColumns()!=null && dBAccessBean.getSelectedDisplayColumns().size()>0){
					String col = getCommaSeparatedString();
					if(col!=null){
						dBAccessBean.setInputQuery("SELECT "+col+" FROM "+dBAccessBean.getSelectedTable());
					}else{
						dBAccessBean.setInputQuery("SELECT * FROM "+dBAccessBean.getSelectedTable());
					}
					String status = dbbean.processQuery(dBAccessBean.getInputQuery());
					if(status.equalsIgnoreCase("SUCCESS")){
						this.resultSet = dbbean.getResultSet();
						generateMetaData();
					}else{
						setErrorMessage(803, "");
					}
				}else{
					setErrorMessage(901,"No columns selected.Please select columns");
				}
			}else{
				setErrorMessage(901,"No table selected.Please select table");
			}
		}
		return "SUCCESS";
	}
	public String processSqlQuery(){
		if(!dBAccessBean.getDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			changeSchema();
		}else{
			clearSelectedColumns();
			if(dBAccessBean.getInputQuery()!=null && !dBAccessBean.getInputQuery().trim().equalsIgnoreCase("")){
				String status = dbbean.processQuery(dBAccessBean.getInputQuery());
				if(status.equalsIgnoreCase("SUCCESS")){
					String[] inputArray = dBAccessBean.getInputQuery().split(" ");
					String queryType = inputArray[0];
					switch (queryType.toLowerCase()) {
					case "select":
						this.resultSet = dbbean.getResultSet();
						String queryCols = dBAccessBean.getInputQuery().split(" ")[1];
						if(queryCols.contains("*")){
							dBAccessBean.setSelectedTable(inputArray[3]);
							if(dBAccessBean.getSelectedTable().contains(";")){
								dBAccessBean.setSelectedTable(dBAccessBean.getSelectedTable().split(";")[0]);
							}
							dbbean.getTableColumns(dBAccessBean.getSelectedTable());
							dBAccessBean.setSelectedColumns(dbbean.getColumns());
						}else if(queryCols.contains(",")){
							String[] cols = queryCols.split(",");
							if(cols!=null && cols.length>0){
								if(dBAccessBean.getSelectedColumns()!=null){
									dBAccessBean.getSelectedColumns().clear();
								}
								List<String> tempList = new ArrayList<String>();
								for(int i=0;i<cols.length;i++){
									tempList.add(cols[i]);
								}
								dBAccessBean.setSelectedColumns(tempList);
							}
						}
						generateMetaData();
						break;
					case "insert":
						generateMetaDataOthers();
						break;
					case "update":
						generateMetaDataOthers();
						break;
					case "delete":
						generateMetaDataOthers();
						break;
					case "drop":
						if(checkSchema()){
							String dropTable = extractTableFromQuery();
							if(dropTable!=null){
								if(dBAccessBean.getTables()!=null && dBAccessBean.getTables().size()>0){
									List<String> tempList = new ArrayList<>();
									for(int i=0;i<dBAccessBean.getTables().size();i++){
										if(!dBAccessBean.getTables().get(i).equalsIgnoreCase(dropTable)){
											tempList.add(dBAccessBean.getTables().get(i));
										}
									}
									dBAccessBean.setTables(tempList);
									renderTables = true;
									renderMetaData = false;
									renderDataTable = false;
									renderColumns = false;
									setErrorMessage(0, "Successfully deleted the table: "+dropTable);
									//display view 
								}
							}
						}
						break;
					case "create":
						if(checkSchema()){
							setErrorMessage(0, "Successfully created table");
							String addTable = extractTableFromQuery();
							if(addTable!=null){
								if(dBAccessBean.getTables()!=null && dBAccessBean.getTables().size()>0){
									List<String> tempList = new ArrayList<>();
									for(int i=0;i<dBAccessBean.getTables().size();i++){
										if(!dBAccessBean.getTables().get(i).equalsIgnoreCase(addTable)){
											tempList.add(dBAccessBean.getTables().get(i));
										}
									}
									tempList.add(addTable);
									dBAccessBean.setTables(tempList);
									renderTables = true;
									renderMetaData = false;
									renderDataTable = false;
									renderColumns = false;
									setErrorMessage(0, "Successfully add the table: "+addTable);
									//display view 
								}
							}
						}
						break;
					default:
						break;
					}
					renderColumns = false;
				}else{
					setErrorMessage(804, "");
				}
			}else{
				setErrorMessage(902, "No Query Entered.Please enter a query");
			}
		}
		return "SUCCESS";
	}

	public String createTable(){
		if(!dBAccessBean.getDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			changeSchema();
		}else{
			clearSelectedColumns();
			if(dBAccessBean.getDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
				if(dBAccessBean.getDatabase().toLowerCase().equalsIgnoreCase("world")){
					if(dBAccessBean.getSelectedTable()!=null && !dBAccessBean.getSelectedTable().trim().equalsIgnoreCase("")){
						dBAccessBean.setInputQuery("SELECT * FROM "+dBAccessBean.getSelectedTable());
						if(dbbean.processQuery(dBAccessBean.getInputQuery()).equalsIgnoreCase("SUCCESS")){
							this.resultSet = dbbean.getResultSet();
							this.rsmd = dbbean.getResultSetMetaData();
							if(dbbean.getTableColumns(dBAccessBean.getSelectedTable()).equalsIgnoreCase("SUCCESS")){
								dbbean.getUserBean().setDb_schema(getCurrentDatabase());
								//dbbean.closeDBResources();
								if(dbbean.connect().equalsIgnoreCase("SUCCESS")){
									if(executeCreateTableQuery().equalsIgnoreCase("SUCCESS")){
										resetView();
										setErrorMessage(0, "Table "+dBAccessBean.getSelectedTable()+" created successfully in user database");
										//show s16g24 table view
									}else{
										//System.out.println("DatabaseActionBean.createTable() query execution failed");
										resetConnection();
										renderErrorMessages = true;
										//setErrorMessage(805,"");
									}
								}else{
									setErrorMessage(802, "");
								}
							}else{
								setErrorMessage(100,dbbean.getErrorMessage());
							}
						}else{
							setErrorMessage(810, "");
						}
					}else{
						//System.out.println("DatabaseActionBean.createTable() select a table to clone");
						setErrorMessage(808,"Select a table to clone");
					}
				}else{
					if(dBAccessBean.getInputQuery()!=null && !dBAccessBean.getInputQuery().trim().equalsIgnoreCase("") && dBAccessBean.getInputQuery().toLowerCase().contains("create")){
						if(dbbean.processQuery(dBAccessBean.getInputQuery()).equalsIgnoreCase("SUCCESS")){
							dbbean.getTablesFromDatabase();
							dBAccessBean.setTables(dbbean.getTables());
							renderColumns = false;
							renderDataTable = false;
						}else{
							//System.out.println("DatabaseActionBean.createTable() query execution failed");
							setErrorMessage(805,"");
						}
					}else{
						//System.out.println("DatabaseActionBean.createTable() given query is not a create query please check");
						setErrorMessage(808,"Entered query is not a create query.");
					}
				}
			}
		}
		return "SUCCESS";
	}
	public String dropTable(){
		if(!dBAccessBean.getDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			changeSchema();
		}else{
			clearSelectedColumns();
			if(!dBAccessBean.getDatabase().toLowerCase().equalsIgnoreCase("world")){
				//in the user database
				if(dBAccessBean.getSelectedTable()!=null && !dBAccessBean.getSelectedTable().trim().equalsIgnoreCase("")){
					if(FinalConstants.tablesDropMap.containsKey(dBAccessBean.getSelectedTable())){
						String query = FinalConstants.tablesDropMap.get(dBAccessBean.getSelectedTable());
						//query = query.replaceAll("world", dbbean.getUserBean().getUsername().toLowerCase());
						query = query.replaceAll("world",getCurrentDatabase());
						dBAccessBean.setInputQuery(query);
					}else{
						dBAccessBean.setInputQuery("DROP TABLE IF EXISTS "+dBAccessBean.getSelectedTable());
					}
					if(dbbean.processQuery(dBAccessBean.getInputQuery()).equalsIgnoreCase("SUCCESS")){
						if(dbbean.getTablesFromDatabase().equalsIgnoreCase("SUCCESS")){
							if(dbbean.getTables()!=null && dbbean.getTables().size()>0){
								this.dBAccessBean.setTables(dbbean.getTables());
								renderTables = true;
							}else{
								setErrorMessage(799, "No tables found in the selected database");
								renderTables = false;
							}
							dBAccessBean.setInputQuery("");
							renderColumns = false;
							renderMetaData = false;
							renderDataTable = false;
							renderErrorMessages = true;
						}else{
							setErrorMessage(800,"");
						}
						setErrorMessage(0,"Table deleted successfully");
					}else{
						setErrorMessage(806, "");
					}
				}else{
					setErrorMessage(904,"Select table to delete");
				}
			}else{
				setErrorMessage(905,"You don't have required permissions to perform the selected operation for world database");
			}
		}

		return "SUCCESS";
	}
	private String getCommaSeparatedString() {
		StringBuffer finalString = new StringBuffer();
		if(dBAccessBean.getSelectedDisplayColumns()!=null && dBAccessBean.getSelectedDisplayColumns().size()>0){
			if(dBAccessBean.getSelectedColumns()!=null){
				dBAccessBean.getSelectedColumns().clear();
			}
			List<String>tempList = new ArrayList<String>();
			for(int i=0;i<dBAccessBean.getSelectedDisplayColumns().size();i++){
				String appendString = dBAccessBean.getSelectedDisplayColumns().get(i).split("\\(")[0];
				tempList.add(appendString);
				if(i<dBAccessBean.getSelectedDisplayColumns().size()-1){
					//finalString.append(list.get(i)+",");
					finalString.append(appendString+",");
				}else{
					finalString.append(appendString);
				}
			}
			dBAccessBean.setSelectedColumns(tempList);
		}
		//System.out.println("DatabaseActionBean.getCommaSeparatedString() final String is "+finalString.toString());
		return finalString.toString();
	}
	private void generateMetaData(){
		if(dbbean.getResultSet()!=null){
			rsmd = dbbean.getResultSetMetaData();
			result = ResultSupport.toResult(resultSet);
			rowsCount = result.getRowCount();
			try {
				columnsCount = rsmd.getColumnCount();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			renderMetaData = true;
			renderDataTable = true;
			renderOtrQueryResults = false;
			renderSelQueryResults = true;
			renderErrorMessages = false;
		}
	}
	private void generateMetaDataOthers() {
		rowsCount = dbbean.getChangedRows();
		renderDataTable = false;
		renderMetaData = true;
		renderSelQueryResults = false;
		renderOtrQueryResults = true;
		renderErrorMessages = false;
	}
	private void generateDisplayColumns() {
		if(this.dBAccessBean.getColumns()!=null && this.dbbean.getColumnsType()!=null && this.dBAccessBean.getColumns().size()>0){
			if(this.dBAccessBean.getDisplayColumns()!=null){
				this.dBAccessBean.getDisplayColumns().clear();
			}
			List<String> tempList = new ArrayList<String>();
			for(int i=0;i<dBAccessBean.getColumns().size();i++){
				tempList.add(dBAccessBean.getColumns().get(i)+"("+this.dbbean.getColumnsType().get(i)+")");
			}
			this.dBAccessBean.setDisplayColumns(tempList);
		}
		
	}
	private void changeSchema() {
		this.dbbean.getUserBean().setDb_schema(dBAccessBean.getDatabase());
		this.dbbean.closeDBResources();
		try{
			if(this.dbbean.getConnection()!=null){
				if(dbbean.connect().equalsIgnoreCase("SUCCESS")){
					renderTables = false;
					renderColumns = false;
					renderMetaData = false;
					renderDataTable = false;
					if(dBAccessBean.getInputQuery()!=null && !dBAccessBean.getInputQuery().trim().equalsIgnoreCase("")){
						dBAccessBean.setInputQuery("");
					}
					clearSelectedColumns();
					dBAccessBean.setSelectedTable("");
					setErrorMessage(0, "Schema changed.Perform desired action now");
					setPermissions();
				}
			}else{
				setErrorMessage(811, "Connection expired");
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	private String executeCreateTableQuery() {
		String status = "FAIL";
		String query = "";
		if(FinalConstants.tablesDropMap.get(dBAccessBean.getSelectedTable()) != null){
			query = FinalConstants.tablesDropMap.get(dBAccessBean.getSelectedTable());
			query = query.replaceAll("world",getCurrentDatabase());
		}else{
			query = "DROP TABLE IF EXISTS "+getCurrentDatabase()+"."+dBAccessBean.getSelectedTable()+" ";
		}
		//query = query.replaceAll("world", dbbean.getUserBean().getUsername().toLowerCase());
		//System.out.println("DatabaseActionBean.executeCreateTableQuery() query is "+query);
		dBAccessBean.setInputQuery(query);
		if(dbbean.processQuery(dBAccessBean.getInputQuery()).equalsIgnoreCase("SUCCESS")){
			if(FinalConstants.tablesCreateMap.get(dBAccessBean.getSelectedTable()) != null){
				query = FinalConstants.tablesCreateMap.get(dBAccessBean.getSelectedTable());
				//query = query.replaceAll("world", dbbean.getUserBean().getUsername().toLowerCase());
				query = query.replaceAll("world",getCurrentDatabase());
				dBAccessBean.setInputQuery(query);
			}else{
				StringBuilder sb = new StringBuilder( 1024 );
				if(this.rsmd!=null){
					try {
						if(rsmd.getColumnCount()>0){
							sb.append("CREATE TABLE "+getCurrentDatabase()+"."+dBAccessBean.getSelectedTable()+" (");
							for(int i=1;i<rsmd.getColumnCount();i++){
								if (i>1) sb.append(",");
								sb.append(rsmd.getColumnLabel(i)).append(" ").append(rsmd.getColumnTypeName(i));
								if(rsmd.getPrecision(i)!=0 && !rsmd.getColumnTypeName(i).equalsIgnoreCase("date")){
									if(rsmd.getColumnTypeName(i).equalsIgnoreCase("double")){
										sb.append( "(" ).append(rsmd.getPrecision(i)).append(",5)");
									}else{
										sb.append( "(" ).append(rsmd.getPrecision(i)).append( ")" );
									}
									
								}
							}
							sb.append( " )" );
							//System.out.println("DatabaseActionBean.executeCreateTableQuery() query is "+sb.toString());
							dBAccessBean.setInputQuery(sb.toString());
						}
					} catch (SQLException e) {
						setErrorMessage(e.getErrorCode(), "");
						e.printStackTrace();
					}
				}
			}
			if(dbbean.processQuery(dBAccessBean.getInputQuery()).equalsIgnoreCase("SUCCESS")){
				//show other db schema view
				String useMultipleInsert = "NO";
				if(resultSet!=null){
					if(getNumberOfRows()<=0){
						useMultipleInsert = "none";
					}
				}
				if(useMultipleInsert.equalsIgnoreCase("other")){
					if(resultSet!=null){
						try {
							while(resultSet.next()){
								if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0 && dbbean.getColumnsType()!=null && dbbean.getColumnsType().size()>0){
									String insertQuery = "INSERT INTO "+dBAccessBean.getSelectedTable()+" VALUES(";
									for(int i=0;i<dbbean.getColumns().size();i++){
										//System.out.println("DatabaseActionBean.executeCreateTableQuery() column name:"+dbbean.getColumns().get(i)+" columntype:"+dbbean.getColumnsType().get(i));
										switch(dbbean.getColumnsType().get(i).toLowerCase()){
										case "int":
											if(i<dbbean.getColumns().size()-1){
												insertQuery = insertQuery+"'"+resultSet.getInt(i+1)+"',";
											}else{
												insertQuery = insertQuery+"'"+resultSet.getInt(i+1)+"')";
											}
											break;
										case "smallint":
											if(i<dbbean.getColumns().size()-1){
												insertQuery = insertQuery+"'"+resultSet.getInt(i+1)+"',";
											}else{
												insertQuery = insertQuery+"'"+resultSet.getInt(i+1)+"')";
											}
											break;
										case "double":
											if(i<dbbean.getColumns().size()-1){
												insertQuery = insertQuery+"'"+resultSet.getDouble(i+1)+"',";
											}else{
												insertQuery = insertQuery+"'"+resultSet.getDouble(i+1)+"')";
											}
											break;
										case "float":
											if(i<dbbean.getColumns().size()-1){
												insertQuery = insertQuery+"'"+resultSet.getFloat(i+1)+"',";
											}else{
												insertQuery = insertQuery+"'"+resultSet.getFloat(i+1)+"')";;
											}
											break;
										case "varchar":
											String rValue = resultSet.getString(i+1);
											rValue = rValue.replaceAll("'", "");
											rValue = removeSpecialChars(rValue);
											//System.out.println("DatabaseActionBean.executeCreateTableQuery() value is "+resultValue);
											if(i<dbbean.getColumns().size()-1){
												insertQuery = insertQuery+"'"+rValue+"',";
											}else{
												insertQuery = insertQuery+"'"+rValue+"')";
											}
											break;
										case "char":
											String resultValue = resultSet.getString(i+1);
											resultValue = resultValue.replaceAll("'", "");
											resultValue = removeSpecialChars(resultValue);
											//System.out.println("DatabaseActionBean.executeCreateTableQuery() value is "+resultValue);
											if(i<dbbean.getColumns().size()-1){
												insertQuery = insertQuery+"'"+resultValue+"',";
											}else{
												insertQuery = insertQuery+"'"+resultValue+"')";
											}
											break;
										default:
											String resValue = resultSet.getString(i+1);
											resValue = resValue.replaceAll("'", "");
											resValue = removeSpecialChars(resValue);
											//System.out.println("DatabaseActionBean.executeCreateTableQuery() value is "+resValue);
											if(i<dbbean.getColumns().size()-1){
												insertQuery = insertQuery+"'"+resValue+"',";
											}else{
												insertQuery = insertQuery+"'"+resValue+"')";
											}
											break;
										}
									}
									//System.out.println("DatabaseActionBean.executeCreateTableQuery() query is "+insertQuery);
									if(!dbbean.processQuery(insertQuery).equalsIgnoreCase("SUCCESS")){
										setErrorMessage(100,dbbean.getErrorMessage());
										status = "FAIL";
										break;
									}else{
										status ="SUCCESS";
									}
								}
							}
						} catch (SQLException e) {
							setErrorMessage(100, e.getMessage());
							e.printStackTrace();
							status = "FAIL";
						}
					}
				}else if(useMultipleInsert.equalsIgnoreCase("NO")){
					dBAccessBean.setInputQuery("INSERT INTO "+dbbean.getUserBean().getDb_schema()+"."+dBAccessBean.getSelectedTable()+" SELECT * FROM world."+dBAccessBean.getSelectedTable());
					if(dbbean.processQuery(dBAccessBean.getInputQuery()).equalsIgnoreCase("SUCCESS")){
						status = "SUCCESS";
					}else{
						setErrorMessage(100,dbbean.getErrorMessage());
						status = "SUCCESS";
					}
				}else if(useMultipleInsert.equalsIgnoreCase("YES")){
					if(resultSet!=null){
						try{
							while(resultSet.next()){
								if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0 && dbbean.getColumnsType()!=null && dbbean.getColumnsType().size()>0){
									String insertQuery = "INSERT INTO "+dBAccessBean.getSelectedTable()+" VALUES(";
									for(int i=0;i<dbbean.getColumns().size();i++){
										if(i<dbbean.getColumns().size()-1){
											insertQuery = insertQuery+"?,";
										}else{
											insertQuery = insertQuery+"?)";
										}
									}
									//System.out.println("DatabaseActionBean.executeCreateTableQuery() insertquery is "+insertQuery);
									if(dbbean.getPrepStatement(insertQuery).equalsIgnoreCase("SUCCESS")){
										for(int i=0;i<dbbean.getColumns().size();i++){
											switch(dbbean.getColumnsType().get(i).toLowerCase()){
											case "int":
												dbbean.getPreparedStatement().setInt(i+1,resultSet.getInt(i+1));
												break;
											case "smallint":
												dbbean.getPreparedStatement().setInt(i+1,resultSet.getInt(i+1));
												break;
											case "float":
												dbbean.getPreparedStatement().setFloat(i+1,resultSet.getFloat(i+1));
												break;
											case "double":
												dbbean.getPreparedStatement().setDouble(i+1,resultSet.getDouble(i+1));
												break;
											case "date":
												dbbean.getPreparedStatement().setDate(i+1, resultSet.getDate(i+1));
												break;
											case "datetime":
												dbbean.getPreparedStatement().setDate(i+1, resultSet.getDate(i+1));
												break;
											case "char":
												String resultValue = removeSpecialChars(resultSet.getString(i+1));
												dbbean.getPreparedStatement().setString(i+1,resultValue);
												break;
											case "varchar":
												String rValue = removeSpecialChars(resultSet.getString(i+1));
												dbbean.getPreparedStatement().setString(i+1,rValue);
												break;
											default:
												String resValue = removeSpecialChars(resultSet.getString(i+1));
												dbbean.getPreparedStatement().setString(i+1,resValue);
												break;
											}
										}
										if(dbbean.processQueryPreparedStatement(insertQuery).equalsIgnoreCase("SUCCESS")){
											status = "SUCCESS";
										}else{
											setErrorMessage(100,dbbean.getErrorMessage());
											status = "FAIL";
											break;
										}
									}else{
										setErrorMessage(100,dbbean.getErrorMessage());
										status = "FAIL";
										break;
									}
								}
							}
						}catch (SQLException e) {
							setErrorMessage(100, e.getMessage());
							e.printStackTrace();
							status = "FAIL";
						}
					}
				}else{
					status = "SUCCESS";
					setErrorMessage(0,"No records in the cloning table");
				}
			}else{
				status = "FAIL";
				setErrorMessage(809, dbbean.getErrorMessage()); 
			}
		}else{
			setErrorMessage(0, dbbean.getErrorMessage()); 
			status = "FAIL";
		}
		return status;
	}
	private void resetConnection() {
		dbbean.getUserBean().setDb_schema("world");
		if(!dbbean.connect().equalsIgnoreCase("SUCCESS")){
			setErrorMessage(810, "");
		}else{
			getTableNames();
		}
		setPermissions();
	}
	private void resetView() {
		//dBAccessBean.swap(0,1);
		dBAccessBean.setDatabase(dbbean.getUserBean().getDb_schema());
		renderTables = false;
		renderColumns = false;
		renderMetaData = false;
		renderDataTable = false;
		dBAccessBean.setInputQuery("");
		dBAccessBean.setSelectedTable("");
		if(dBAccessBean.getDisplayColumns()!=null && dBAccessBean.getDisplayColumns().size()>0){
			dBAccessBean.getDisplayColumns().clear();
		}
		setPermissions();
	}
	private void setErrorMessage(int i,String message) {
		this.errorMessageBean.setErrorCode(i);
		if(message.equalsIgnoreCase("")){
			this.errorMessageBean.setErrorMessage(dbbean.getErrorMessage());
		}else{
			this.errorMessageBean.setErrorMessage(message);
		}
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(this.errorMessageBean.getErrorMessage()));
		renderErrorMessages = true;
	}
	
	private void setPermissions(){
		//if(dbbean.getUserBean().getDb_schema().equalsIgnoreCase(dbbean.getUserBean().getUsername())){
		if(!dbbean.getUserBean().getDb_schema().equalsIgnoreCase("world")){
			dBAccessBean.setRenderPermissions(true);
			dBAccessBean.setRenderClonePermissions(false);
		}else{
			dBAccessBean.setRenderPermissions(false);
			dBAccessBean.setRenderClonePermissions(true);
		}
	}
	private boolean checkSchema() {
		/*if(dbbean.getUserBean().getDb_schema().equalsIgnoreCase(dbbean.getUserBean().getUsername())){
			return true;
		}*/
		if(!dbbean.getUserBean().getDb_schema().equalsIgnoreCase("world")){
			return true;
		}
		return false;
	}
	private String extractTableFromQuery() {
		if(dBAccessBean.getInputQuery()!=null && !dBAccessBean.getInputQuery().trim().equalsIgnoreCase("")){
			String query = dBAccessBean.getInputQuery().split(" ")[2];
			//System.out.println("DatabaseActionBean.extractTableFromQuery() query is "+query);
			return query;
		}
		return null;
	}
	private String removeSpecialChars(String input){
		StringBuilder sb = new StringBuilder();
		if(input!=null && input.length()>0){
			for(int i=0;i<input.length();i++){
				char ch = input.charAt(i);
			    if (!Character.isHighSurrogate(ch) && !Character.isLowSurrogate(ch)) {
			        sb.append(ch);
			    }
			}
		}
		return sb.toString();
	}
	private String getCurrentDatabase(){
		StringBuilder sb = new StringBuilder();
		if(dBAccessBean.getDatabases()!=null && dBAccessBean.getDatabases().size()>0){
			for(int i=0;i<dBAccessBean.getDatabases().size();i++){
				if(!dBAccessBean.getDatabases().get(i).equalsIgnoreCase("world")){
					sb.append(dBAccessBean.getDatabases().get(i));
					break;
				}
			}
		}
		return sb.toString();
	}
	/*private void clearErrorMessages(){
		Iterator<FacesMessage> itr = FacesContext.getCurrentInstance().getMessages();
		while(itr.hasNext()){
			itr.next();
			itr.remove();
		}
	}*/
	private int getNumberOfRows(){
		int count=0;
		try {
			while(resultSet.next()){
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	private void clearSelectedColumns(){
		if(dBAccessBean.getSelectedColumns()!=null && dBAccessBean.getSelectedColumns().size()>0){
			dBAccessBean.getSelectedColumns().clear();
		}
		if(dBAccessBean.getSelectedDisplayColumns()!=null && dBAccessBean.getSelectedDisplayColumns().size()>0){
			dBAccessBean.getSelectedDisplayColumns().clear();
		}
	}
}
