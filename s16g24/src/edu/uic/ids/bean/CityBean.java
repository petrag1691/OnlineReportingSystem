package edu.uic.ids.bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@SuppressWarnings("serial")
@ManagedBean(name="cityBean",eager=true)
@RequestScoped
public class CityBean implements Cloneable,Serializable{
	private DatabaseAccessBean dbbean;
	private String tableName;
	private String name;
	private String district;
	private int population;
	private String countryCode;
	private FacesContext context;
	private int countryCodesCount;
	private int totalPopulation;
	
	public CityBean(){
		this.tableName = "City";
		this.countryCodesCount=0;
		this.totalPopulation=0;
	}
	public String getName() {
		return name;
	}
	public String getDistrict() {
		return district;
	}
	public int getPopulation() {
		return population;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public int getCountryCodesCount() {
		return countryCodesCount;
	}
	public void setCountryCodesCount(int countryCodesCount) {
		this.countryCodesCount = countryCodesCount;
	}
	public int getTotalPopulation() {
		return totalPopulation;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@PostConstruct
	public void init(){
		this.context = FacesContext.getCurrentInstance();
		Map <String, Object> m = context.getExternalContext().getSessionMap();
		dbbean = (DatabaseAccessBean) m.get("databaseAccessBean");
	}
	public String getResults(){
		String status = "SUCCESS";
		try {
				String query = "SELECT * FROM City";
				String statusCode = this.dbbean.processQuery(query);
				System.out.println("CityBean.getResults() statusCode is "+statusCode);
				if(statusCode.equals("SUCCESS")){
					ResultSet resultSet = this.dbbean.getResultSet();
					if(resultSet!=null){
						while(resultSet.next()){
							if(resultSet.getString("population")!=null){
								this.totalPopulation = this.totalPopulation+Integer.parseInt(resultSet.getString("population"));
							}
							if(resultSet.getString("CountryCode")!=null){
								this.countryCodesCount++;
							}
						}
					}
				}
		} catch (SQLException e) {
			status = "FAIL";
			e.printStackTrace();
		}catch (Exception e){
			status = "FAIL";
			e.printStackTrace();
		}
		return status;
	}
}
