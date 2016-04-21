package edu.uic.ids.bean;
//Session scoped bean for handling database access
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.mysql.jdbc.DatabaseMetaData;

import edu.uic.ids.constants.FinalConstants;
@ManagedBean(name="databaseAccessBean",eager=true)
@SessionScoped
public class DatabaseAccessBean implements Cloneable,Serializable{
	private static final long serialVersionUID = -1019277925586607534L;
	@ManagedProperty(value="#{userBean}")
	private UserBean userBean;
	@ManagedProperty(value="#{errorMessageBean}")
	private ErrorMessageBean errorMessageBean;
	private Connection connection;
	private ResultSet resultSet;
	private Statement statement;
	private PreparedStatement preparedStatement;
	private ResultSetMetaData resultSetMetaData;
	private DatabaseMetaData databaseMetaData;
	private ArrayList<String>databases;
	private ArrayList<String>tables;
	private ArrayList<String>columns;
	private ArrayList<String>columnsType;
	private List<String>primaryKeys;
	private List<String>foreignKeys;
	private int changedRows;
	private String errorMessage;
	private String selectedDatabase;
	private String userName;
	private String password;
	public DatabaseAccessBean(){
		
	}
	@PostConstruct
	public void init(){
		Map <String, Object> m =  FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		this.userBean = (UserBean)m.get("userBean");
		selectedDatabase=userBean.getDb_schema();
		userName = userBean.getUsername();
		password = userBean.getPassword();
		//System.out.println("DatabaseAccessBean.init() selected database is "+selectedDatabase);
		//this.databaseActionBean = (DatabaseActionBean)m.get("databaseActionBean");
	}
	public UserBean getUserBean() {
		return userBean;
	}
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	public ErrorMessageBean getErrorMessageBean() {
		return errorMessageBean;
	}
	public void setErrorMessageBean(ErrorMessageBean errorMessageBean) {
		this.errorMessageBean = errorMessageBean;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public ResultSet getResultSet() {
		return resultSet;
	}
	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}
	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}
	public ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}
	public DatabaseMetaData getDatabaseMetaData() {
		return databaseMetaData;
	}
	public ArrayList<String> getDatabases() {
		return databases;
	}
	public ArrayList<String> getTables() {
		return tables;
	}
	public ArrayList<String> getColumns() {
		return columns;
	}
	public ArrayList<String> getColumnsType() {
		return columnsType;
	}
	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}
	public List<String> getForeignKeys() {
		return foreignKeys;
	}
	public int getChangedRows() {
		return changedRows;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public String getSelectedDatabase() {
		return selectedDatabase;
	}
	public void setSelectedDatabase(String selectedDatabase) {
		this.selectedDatabase = selectedDatabase;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	
	//validates user credentials and sets up database connection
	public String connect(){
		String host="";
		String status = "SUCCESS";
		try{
			switch (this.userBean.getDbms()) {
			case "mysql":
				Class.forName(FinalConstants.MYSQLDRIVER);
				if(this.userBean.getPort()==null || this.userBean.getPort().trim().equals("")){
					this.userBean.setPort(FinalConstants.MYSQLPORT);
				}
				host = "jdbc:"+this.userBean.getDbms()+"://"+this.userBean.getUrl()+":"+this.userBean.getPort()+"/"+this.userBean.getDb_schema();
				break;
			case "db2":
				Class.forName(FinalConstants.DB2DRIVER);
				if(this.userBean.getPort()==null || this.userBean.getPort().trim().equals("")){
					this.userBean.setPort(FinalConstants.DB2PORT);
				}
				host = "jdbc:"+this.userBean.getDbms()+"://"+this.userBean.getUrl()+":"+this.userBean.getPort()+"/"+this.userBean.getDb_schema();
				break;
			case "oracle":
				Class.forName(FinalConstants.ORACLEDRIVER);
				if(this.userBean.getPort()==null || this.userBean.getPort().trim().equals("")){
					this.userBean.setPort(FinalConstants.ORACLEPORT);
				}
				host = "jdbc:"+this.userBean.getDbms()+":thin:@"+this.userBean.getUrl()+":"+this.userBean.getPort()+this.userBean.getDb_schema();
				break;
			case "sql":
				Class.forName(FinalConstants.SQLDRIVER);
				if(this.userBean.getPort()==null || this.userBean.getPort().trim().equals("")){
					this.userBean.setPort(FinalConstants.SQLPORT);
				}
				host = "jdbc:"+this.userBean.getDbms()+":thin:@"+this.userBean.getUrl()+":"+this.userBean.getPort()+this.userBean.getDb_schema();
				break;
			default:
				host = null;
			}
		}catch(ClassNotFoundException e){
			status = "FAIL";
			e.printStackTrace();
			this.errorMessage = e.getMessage();
		}catch(NullPointerException ex){
			status = "FAIL";
			this.errorMessage = ex.getMessage();
		}
		if(status.equals("FAIL")){
			this.errorMessageBean.setErrorCode(200);
			this.errorMessageBean.setErrorMessage("Selected DBMS has not been set up for the selected host");
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(this.errorMessageBean.getErrorMessage()));
			return status;
		}
		try{
			if(host!=null && host!=""){
				this.connection = DriverManager.getConnection(host, this.userBean.getUsername().trim(), this.userBean.getPassword().trim());
				if(this.connection!=null){
					this.statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					this.databaseMetaData = (DatabaseMetaData) this.connection.getMetaData();
					
					if(this.databaseMetaData!=null){
						getTablesFromDatabase();
						this.resultSet = this.databaseMetaData.getCatalogs();
					}
					generateDatabases();
				}
			}
		}catch(SQLException e){
			status = "FAIL";
			this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
			e.printStackTrace();
		}catch(Exception e){
			status = "FAIL";
			this.errorMessage = e.getMessage();
			e.printStackTrace();
		}
		if(status.equals("FAIL")){
			this.errorMessageBean.setErrorCode(100);
			this.errorMessageBean.setErrorMessage("Invalid Credentials");
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(this.errorMessageBean.getErrorMessage()));
		}
		return status;
	}
	
	//Executes the requested query and generates resultset/changedrows
	public String processQuery(String query){
		String status = "FAIL";
		try{
			if(this.connection==null){
				connect();
			}
		}catch(NullPointerException e){
			this.errorMessage = e.getMessage();
		}
		String queryType = query.split(" ")[0];
		//System.out.println("DatabaseAccessBean.processQuery() querytype is "+queryType);
		switch (queryType.toLowerCase()) {
		case "select":
			try {
				this.resultSet = this.statement.executeQuery(query);
				if(this.resultSet!=null){
					this.resultSetMetaData = this.resultSet.getMetaData();
					getColumnNames();
					status = "SUCCESS";
				}
			} catch (SQLException e) {
				this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
				e.printStackTrace();
			}
			break;
		case "insert":
			try{
				changedRows= this.statement.executeUpdate(query);
				status = "SUCCESS";
			}catch (SQLException e) {
				this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
				e.printStackTrace();
			}
			break;
		case "update":
			try{
				changedRows= this.statement.executeUpdate(query);
				status = "SUCCESS";
			}catch (SQLException e) {
				this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
				e.printStackTrace();
			}
			break;
		case "delete":
			try{
				changedRows = this.statement.executeUpdate(query);
				status = "SUCCESS";
			}catch (SQLException e) {
				this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
				e.printStackTrace();
			}
			break;
		case "create":
			try{
				this.statement.executeUpdate(query);
				status = "SUCCESS";
			}catch (SQLException e) {
				this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
				e.printStackTrace();
			}
			break;
		case "drop":
			try{
				this.statement.executeUpdate(query);
				status = "SUCCESS";
			}catch (SQLException e) {
				this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
				e.printStackTrace();
			}
			break;
		default:
			try{
				this.statement.execute(query);
			}catch (SQLException e) {
				this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
				e.printStackTrace();
			}
			break;
		}
		return status;
	}
	
	//processQuery using Prepared Statement
	public String processQueryPreparedStatement(String query){
		String status = "FAIL";
		try{
			if(this.connection==null){
				connect();
			}
		}catch(NullPointerException e){
			this.errorMessage = e.getMessage();
		}
		String queryType = query.split(" ")[0];
		//System.out.println("DatabaseAccessBean.processQuery() querytype is "+queryType);
		switch (queryType.toLowerCase()) {
		case "select":
			break;
		case "insert":
			try{
				if(this.preparedStatement!=null){
					changedRows= this.preparedStatement.executeUpdate();
					status = "SUCCESS";
				}
			}catch (SQLException e) {
				this.errorMessage = "SQL State:"+e.getSQLState()+" Error Code:"+e.getErrorCode()+"Error:"+e.getMessage();
				e.printStackTrace();
			}
			break;
		case "update":
			try{
				if(this.preparedStatement!=null){
					changedRows= this.preparedStatement.executeUpdate();
					status = "SUCCESS";
				}
			}catch (SQLException e) {
				this.errorMessage = "SQL State:"+e.getSQLState()+" Error Code:"+e.getErrorCode()+"Error:"+e.getMessage();
				e.printStackTrace();
			}
			break;
		case "delete":
			break;
		case "create":
			break;
		case "drop":
			break;
		default:
			break;
		}
		return status;
	}
	
	//retrieve columns from the table
	private void getColumnNames(){
		try {
			if(this.resultSet!=null){
				this.resultSetMetaData = this.resultSet.getMetaData();
				int columnCount = this.resultSetMetaData.getColumnCount();
				for(int i=1;i<=columnCount;i++){
					//System.out.println("DatabaseAccessBean.getColumnNames() columns are "+this.resultSetMetaData.getColumnName(i));
				}	
			}
		}catch (SQLException e) {
			this.errorMessage = "SQL State:"+e.getSQLState()+" Error Code:"+e.getErrorCode()+"Error:"+e.getMessage();
			e.printStackTrace();
		}
	}
	
	//Populates tables list from the database to the tables instance variable
	public String getTablesFromDatabase(){
		String status = "FAIL";
		try {
			if(this.databaseMetaData!=null){
				this.resultSet = this.databaseMetaData.getTables(null, null, "%", null);
				if(this.resultSet!=null){
					if(tables!=null){
						tables.clear();
					}
					tables = new ArrayList<>();
					while (this.resultSet.next()) {
					  //System.out.println("tables are "+this.resultSet.getString(3));
					  tables.add(this.resultSet.getString(3));
					}
					status = "SUCCESS";
				}
			}
		} catch (SQLException e) {
			this.errorMessage = "SQL State:"+e.getSQLState()+" Error Code:"+e.getErrorCode()+"Error:"+e.getMessage();
			e.printStackTrace();
		}catch(Exception e){
			this.errorMessage = e.getMessage();
			e.printStackTrace();
		}
		return status;
	}
	
	//Retrieves columns for a given table
	public String getTableColumns(String table) {
		String status = "FAIL";
		try {
			this.resultSet= this.databaseMetaData.getColumns(null, null, table, null);
			if(this.resultSet!=null){
				if(columns!=null){
					columns.clear();
				}
				if(columnsType!=null){
					columnsType.clear();
				}
				this.columns = new ArrayList<String>();
				this.columnsType = new ArrayList<String>();
			    while (resultSet.next()) {
				      String name = resultSet.getString("COLUMN_NAME");
				      String type = resultSet.getString("TYPE_NAME");
				      //int size = resultSet.getInt("COLUMN_SIZE");
				      this.columns.add(name);
				      this.columnsType.add(type);
				      //System.out.println("Column name: [" + name + "]; type: [" + type + "]; size: [" + size + "]");
				}
			    status = "SUCCESS";
			}
		} catch (SQLException e) {
			this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
			e.printStackTrace();
		}
		return status;
	}
	
	//closes all database objects
	public void closeDBResources(){
		try{
			if(this.resultSet!=null){
				this.resultSet.close();
			}
			if(this.statement!=null){
				this.statement.close();
			}
			if(this.preparedStatement!=null){
				this.preparedStatement.close();
			}
			if(this.connection!=null){
				this.connection.close();
			}
		}catch(SQLException ex){
			this.errorMessage =  "SQL State:"+ex.getSQLState()+"\nError Code:"+ex.getErrorCode()+"\nError:"+ex.getMessage();
			ex.printStackTrace();
		}catch (Exception e) {
			this.errorMessage = e.getMessage();
			e.printStackTrace();
		}
	}
	
	//Retrieves all the databases present in the database server
	private String generateDatabases() {
		String status = "FAIL";
		if(this.databases!=null && this.databases.size()>0){
			this.databases.clear();
		}else{
			this.databases = new ArrayList<String>();
		}
		databases.add(this.userBean.getDb_schema());
		if(this.resultSet!=null){
			try {
				while(resultSet.next()){
					//System.out.println("DatabaseAccessBean.generateDatabases()"+resultSet.getString("TABLE_CAT"));
					//System.out.println("DatabaseAccessBean.generateDatabases()"+resultSet.getString(2));
					if(!databases.contains(resultSet.getString("TABLE_CAT")) && !resultSet.getString("TABLE_CAT").equalsIgnoreCase("information_schema")){
						this.databases.add(resultSet.getString("TABLE_CAT"));
					}
				}
				status = "SUCCESS";
			} catch (SQLException e) {
				this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
				e.printStackTrace();
			}
		}
		return status;
	}
	
	//Generates prepared statement for a given query
	public String getPrepStatement(String query) {
		String status = "FAIL";
		try{
			if(this.connection==null){
				connect();
			}
		}catch(NullPointerException e){
			this.errorMessage = e.getMessage();
		}
		try {
			//this.preparedStatement = this.connection.prepareStatement(query);
			this.preparedStatement = this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			status = "SUCCESS";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
			e.printStackTrace();
		}
		
		return status;
	}
	
	//Retrieves constraints present in a table
	public String getConstraints(String table){
		String status = "FAIL";
		try{
			if(this.connection==null){
				connect();
			}
		}catch(NullPointerException e){
			this.errorMessage = e.getMessage();
		}
		try{
			this.databaseMetaData = (DatabaseMetaData) this.connection.getMetaData();
			if(databaseMetaData!=null){
				this.resultSet = databaseMetaData.getPrimaryKeys(null, null, table);
				if(resultSet!=null){
					if(primaryKeys!=null){
						primaryKeys.clear();
					}else{
						primaryKeys = new ArrayList<>();
					}
					while(resultSet.next()){
						primaryKeys.add(resultSet.getString(1));
					}
				}
				this.resultSet = databaseMetaData.getExportedKeys(null, null, table);
				if(resultSet!=null){
					if(foreignKeys!=null){
						foreignKeys.clear();
					}else{
						foreignKeys = new ArrayList<>();
					}
					while(resultSet.next()){
						foreignKeys.add(resultSet.getString(1));
					}
				}
			}
		}catch(SQLException e){
			this.errorMessage = "SQL State:"+e.getSQLState()+"\nError Code:"+e.getErrorCode()+"\nError:"+e.getMessage();
			e.printStackTrace();
		}
		return status;
	}
}
