package edu.uic.ids.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SuppressWarnings("serial")
@ManagedBean(name="userBean")
@SessionScoped
public class UserBean implements Serializable,Cloneable{
	private String url;
	private String dbms;
	private String db_schema;
	private String port;
	private String username;
	private String password;
	public UserBean(){
		url = "";
		dbms = "";
		db_schema = "";
		port = "";
		username="";
		password = "";
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDbms() {
		return dbms;
	}
	public void setDbms(String dbms) {
		this.dbms = dbms;
	}
	public String getDb_schema() {
		return db_schema;
	}
	public void setDb_schema(String db_schema) {
		this.db_schema = db_schema;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
