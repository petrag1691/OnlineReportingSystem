package edu.uic.ids.bean;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
//Session scoped Bean instantiates on click of login in login.jsp
public class LoginBean implements Serializable,Cloneable{
	private static final long serialVersionUID = -3436113952451511288L;
	@ManagedProperty(value="#{databaseAccessBean}")
	private DatabaseAccessBean databaseAccessBean;
	@ManagedProperty(value="#{ipbean}")
	private IPAccessLogBean ipbean;
	private String status;
	
	@PostConstruct
	public void init(){
		Map <String, Object> m =  FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		databaseAccessBean = (DatabaseAccessBean)m.get("databaseAccessBean");
		ipbean=(IPAccessLogBean)m.get("ipbean");
	}
	public DatabaseAccessBean getDatabaseAccessBean() {
		return databaseAccessBean;
	}
	public void setDatabaseAccessBean(DatabaseAccessBean databaseAccessBean) {
		this.databaseAccessBean = databaseAccessBean;
	}
	public IPAccessLogBean getIpbean() {
		return ipbean;
	}
	public void setIpbean(IPAccessLogBean ipbean) {
		this.ipbean = ipbean;
	}
	//Invokes when Login button is clicked
	public String processLogin(){
		status = "FAIL";
		if(databaseAccessBean!=null){ 
			if(databaseAccessBean.connect().equalsIgnoreCase("SUCCESS")){
				status = "SUCCESS";
				if(!ipbean.createIPLog().equalsIgnoreCase("SUCCESS")){
					status = "FAIL";
				}
			}
		}
		return status;
	}
	//invoked when logout button is clicked in post login pages
	public String logout() {
		 FacesContext context = FacesContext.getCurrentInstance();
		 ipbean.updateLogoutTime();
		 databaseAccessBean.closeDBResources();
		 context.addMessage(null,new FacesMessage("Successfully logged out"));
	     context.getExternalContext().invalidateSession();
	     return "LOGOUT";
	}
}
