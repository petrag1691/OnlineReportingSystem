package edu.uic.ids.bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import edu.uic.ids.constants.FinalConstants;

@ManagedBean(name="ipbean",eager=true)
@SessionScoped
//Backing bean for Access Log
public class IPAccessLogBean implements Serializable,Cloneable{
	private static final long serialVersionUID = 2140856222589025639L;
	@ManagedProperty(value="#{databaseAccessBean}")
	private DatabaseAccessBean dbbean;
	private String lastLoginTime;
	private String lastLogoutTime;
	private String ipAddress;
	private String loginTime;
	private String status;
	private long lastUpdatedRow;
	private SimpleDateFormat dateFormat;
	private ResultSet resultSet;
	@PostConstruct
	public void init(){
		Map <String, Object> m =  FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		this.dbbean = (DatabaseAccessBean)m.get("databaseAccessBean");
		//System.out.println("IPAccessLogBean.init() dbbean is "+dbbean);
		dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	}

	public DatabaseAccessBean getDbbean() {
		return dbbean;
	}

	public void setDbbean(DatabaseAccessBean dbbean) {
		this.dbbean = dbbean;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(String lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public long getLastUpdatedRow() {
		return lastUpdatedRow;
	}

	public void setLastUpdatedRow(long lastUpdatedRow) {
		this.lastUpdatedRow = lastUpdatedRow;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	//invoked after successful establishment of connection with the user credentials. Inserts the current session details into database
	public String createIPLog() {
		status = "FAIL";
		String executionQuery = "";
		setCurrentSessionDetails();
		if(dbbean.getUserBean().getDb_schema().equalsIgnoreCase(FinalConstants.DEFAULT_USER)){
			if(dbbean.getTablesFromDatabase().equalsIgnoreCase("SUCCESS")){
				if(dbbean.getTables()!=null && dbbean.getTables().size()>0){
					if(dbbean.getTables().contains("iplog")){
						getLastSessionDetails();
						executionQuery = "INSERT INTO iplog(ipaddress,logintime,username,dbms,dbschema,host) VALUES(?,?,?,?,?,?)";
						insertLog(executionQuery);
					}else{
						executionQuery = FinalConstants.logCreateMap.get("iplog");
						if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
							executionQuery = "INSERT INTO iplog(ipaddress,logintime,username,dbms,dbschema,host) VALUES(?,?,?,?,?,?)";
							insertLog(executionQuery);
						}
					}
				}else{
					executionQuery = FinalConstants.logCreateMap.get("iplog");
					if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
						executionQuery = "INSERT INTO iplog(ipaddress,logintime,username,dbms,schema,host) VALUES(?,?,?,?,?,?)";
						insertLog(executionQuery);
					}
				}
			}
		}else{
			if(this.dbbean.getDatabases().contains(dbbean.getUserBean().getUsername())){
				dbbean.getUserBean().setDb_schema(FinalConstants.DEFAULT_USER);
				dbbean.getUserBean().setUsername(FinalConstants.DEFAULT_USER);
				dbbean.getUserBean().setPassword(FinalConstants.DEFAULT_PASSWORD);
				if(dbbean.connect().equalsIgnoreCase("SUCCESS")){
					if(dbbean.getTablesFromDatabase().equalsIgnoreCase("SUCCESS")){
						if(dbbean.getTables()!=null && dbbean.getTables().size()>0){
							if(dbbean.getTables().contains("iplog")){
								/*executionQuery = "DROP TABLE IF EXISTS iplog";
								if(processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
									System.out.println("DatabaseAccessBean.createIPLog() table deleted successfully");
								}else{*/
									getLastSessionDetails();
									executionQuery = "INSERT INTO iplog(ipaddress,logintime,username,dbms,dbschema,host) VALUES(?,?,?,?,?,?)";
									insertLog(executionQuery);
								//}
							}else{
								executionQuery = FinalConstants.logCreateMap.get("iplog");
								if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
									executionQuery = "INSERT INTO iplog(ipaddress,logintime,username,dbms,dbschema,host) VALUES(?,?,?,?,?,?)";
									if(insertLog(executionQuery).equalsIgnoreCase("SUCCESS")){
										//System.out.println("DatabaseAccessBean.createIPLog() inserted current record into database");
									}else{
										//System.out.println("DatabaseAccessBean.createIPLog() insertion failed");
									}
								}
							}
						}
					}
				}
				if(resetConnection().equalsIgnoreCase("SUCCESS")){
					status = "SUCCESS";
				}else{
					setErrorMessage("");
					status = "FAIL";
				}
			}else{
				//System.out.println("DatabaseAccessBean.createIPLog() cannot create access log");
			}
		}
		return status;
	}
	//Used for retrieving the last session details
	private void getLastSessionDetails() {
		String executionQuery ="SELECT logintime,logouttime FROM iplog ORDER BY logintime desc LIMIT 1";
		//System.out.println("DatabaseAccessBean.getLastSessionDetails() executionQuery is "+executionQuery);
		dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
			resultSet = dbbean.getResultSet();
			if(this.resultSet!=null){
				try {
					while(resultSet.next()){
						////System.out.println("DatabaseAccessBean.getLastSessionDetails()"+resultSet.getTimestamp("logintime"));
						//ipbean.setLastLoginTime(dtformat.format(resultSet.getTimestamp("logintime")));
						if(resultSet.getTimestamp("logintime")!=null){
							lastLoginTime = dateFormat.format(resultSet.getTimestamp("logintime"));
						}else{
							lastLoginTime = "First Login";	
						}
						if(resultSet.getTimestamp("logouttime")!=null){
							//ipbean.setLastLogoutTime(dtformat.format(resultSet.getTimestamp("logouttime")));
							lastLogoutTime = dateFormat.format(dbbean.getResultSet().getTimestamp("logouttime"));
						}else{
							lastLogoutTime = "Logout action not performed";
							//ipbean.setLastLogoutTime("");
						}
					}
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			//System.out.println("DatabaseAccessBean.getLastSessionDetails() unable to retrieve login details");
		}
		
	}
	
	//sets the current session details to the backing bean
	private void setCurrentSessionDetails() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request.getHeader("X-FORWARDED-FOR") == null) {
		    //ipbean.setIpAddress(request.getRemoteAddr());
			ipAddress = request.getRemoteAddr();
		}else{
			//ipbean.setIpAddress(request.getHeader("X-FORWARDED-FOR"));
			ipAddress = request.getHeader("X-FORWARDED-FOR");
		}
		loginTime = dateFormat.format(new Date());
	}
	
	//Inserts current login details into the database
	private String insertLog(String executionQuery) {
		String status = "FAIL";
		//System.out.println("IPAccessLogBean.insertLog() execution query is "+executionQuery);
		try {
			if(dbbean.getPrepStatement(executionQuery).equalsIgnoreCase("SUCCESS")){
				dbbean.getPreparedStatement().setString(1,ipAddress);
				dbbean.getPreparedStatement().setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
				dbbean.getPreparedStatement().setString(3, dbbean.getUserBean().getUsername());
				dbbean.getPreparedStatement().setString(4, dbbean.getUserBean().getDbms());
				dbbean.getPreparedStatement().setString(5, dbbean.getSelectedDatabase());
				dbbean.getPreparedStatement().setString(6, dbbean.getUserBean().getUrl());
				if(dbbean.processQueryPreparedStatement(executionQuery).equalsIgnoreCase("SUCCESS")){
					//System.out.println("IPAccessLogBean.insertLog()"+dbbean.getChangedRows());
					resultSet = dbbean.getPreparedStatement().getGeneratedKeys();
			            while(resultSet.next()) {
			            	//System.out.println("DatabaseAccessBean.insertLog() updated row is "+resultSet.getLong(1));
			            	lastUpdatedRow = resultSet.getLong(1);
			                //ipbean.setLastUpdatedRow(resultSet.getLong(1));
			            }
					status = "SUCCESS";
				}else{
					//System.out.println("DatabaseAccessBean.insertLog() insertion failed");
					setErrorMessage("");
				}
			}else{
				setErrorMessage("");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	//invoked when login is clicked to update the logout time in database
	public void updateLogoutTime(){
		dbbean.getUserBean().setDb_schema(dbbean.getUserBean().getUsername());
		if(dbbean.connect().equalsIgnoreCase("SUCCESS")){
			String executionQuery = "UPDATE iplog SET logouttime=? WHERE id=?";
			//System.out.println("DatabaseAccessBean.updateLogoutTime() updating logout time "+executionQuery);
			try {
				if(dbbean.getPrepStatement(executionQuery).equalsIgnoreCase("SUCCESS")){
					dbbean.getPreparedStatement().setTimestamp(1,new Timestamp(new Date().getTime()));
					dbbean.getPreparedStatement().setInt(2,(int)lastUpdatedRow);
					if(dbbean.processQueryPreparedStatement(executionQuery).equalsIgnoreCase("SUCCESS")){
						//System.out.println("DatabaseAccessBean.updateLogoutTime() logout time updated");
					}
				}else{
					setErrorMessage("");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			//System.out.println("DatabaseAccessBean.updateLogoutTime() unable to connect to user database");
		}
	}
	
	//resets the connection to the original details
	private String resetConnection(){ 
		dbbean.getUserBean().setDb_schema(dbbean.getSelectedDatabase());
		dbbean.getUserBean().setUsername(dbbean.getUserName());
		dbbean.getUserBean().setPassword(dbbean.getPassword());
		if(dbbean.connect().equalsIgnoreCase("SUCCESS")){
			return "SUCCESS";
		}else{
			return "FAIL";
		}
	}
	
	//Sets the error message to be displayed on the jsp page
	public void setErrorMessage(String message){
		if(message.equalsIgnoreCase("")){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(dbbean.getErrorMessage()));
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(message));
		}
	}
}
