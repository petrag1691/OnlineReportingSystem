package edu.uic.ids.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import edu.uic.ids.constants.FinalConstants;
@ManagedBean(name="countryBean",eager=true)
@RequestScoped
public class CountryBean {
	private DatabaseAccessBean dbbean;
	private String tableName;
	private double totalPopulation;
	private double totalSurfaceArea;
	private double maxLifeExpectancy;
	private double minLifeExpectancy;
	private double avgLifeExpectancy;
	private double minGNP;
	private double maxGNP;
	private double avgGNP;
	private FacesContext context;
	public CountryBean(){
		this.tableName="Country";
		this.totalPopulation =0.0;
		this.totalSurfaceArea = 0.0;
		this.maxLifeExpectancy =0.0;
		this.maxGNP = 0.0;
		this.minGNP =0.0;
		this.avgGNP =0.0;
		this.minLifeExpectancy = 0.0;
		this.avgLifeExpectancy=0.0;		
	}
	@PostConstruct
	public void init(){
		this.context = FacesContext.getCurrentInstance();
		Map <String, Object> m = context.getExternalContext().getSessionMap();
		dbbean = (DatabaseAccessBean) m.get("databaseAccessBean");
	}
	public String getTableName() {
		return tableName;
	}
	public double getTotalPopulation() {
		return totalPopulation;
	}
	public double getTotalSurfaceArea() {
		return FinalConstants.round(totalSurfaceArea,100);
	}
	public double getMaxLifeExpectancy() {
		return FinalConstants.round(maxLifeExpectancy,100);
	}
	public double getMinLifeExpectancy() {
		return FinalConstants.round(minLifeExpectancy,100);
	}
	public double getAvgLifeExpectancy() {
		return FinalConstants.round(avgLifeExpectancy,100);
	}
	public double getMinGNP() {
		return FinalConstants.round(minGNP,100);
	}
	public double getMaxGNP() {
		return FinalConstants.round(maxGNP,100);
	}
	public double getAvgGNP() {
		return FinalConstants.round(avgGNP,100);
	}

	public String getReports() {
		String status = "SUCCESS";
		try {
			if(this.dbbean!=null){
				String query = "SELECT * FROM Country";
				String statusCode = this.dbbean.processQuery(query);
				if(statusCode.equals("SUCCESS")){
					ResultSet resultSet = this.dbbean.getResultSet();
					if(resultSet!=null){
						List<Double>LifeExpectancy = new ArrayList<>();
						List<Double>GNP = new ArrayList<>();
						double life = 0;
						double gnp = 0;
						while(resultSet.next()){
							if(resultSet.getString("Population")!=null){
								this.totalPopulation = this.totalPopulation+Double.parseDouble(resultSet.getString("Population"));
							}
							if(resultSet.getString("SurfaceArea")!=null){
								this.totalSurfaceArea = this.totalSurfaceArea+Double.parseDouble(resultSet.getString("SurfaceArea"));
							}
							if(resultSet.getString("LifeExpectancy")!=null){
								LifeExpectancy.add(Double.parseDouble(resultSet.getString("Lifeexpectancy")));
								life = life+Double.parseDouble(resultSet.getString("LifeExpectancy"));
							}
							if(resultSet.getString("Gnp")!=null){
								GNP.add(Double.parseDouble(resultSet.getString("Gnp")));
								gnp = gnp+Double.parseDouble(resultSet.getString("Gnp"));
							}
						}
						if(LifeExpectancy!=null && LifeExpectancy.size()>0){
							Collections.sort(LifeExpectancy);
							this.minLifeExpectancy = LifeExpectancy.get(0);
							this.maxLifeExpectancy = LifeExpectancy.get(LifeExpectancy.size()-1);
							this.avgLifeExpectancy = life/LifeExpectancy.size();
						}
						if(GNP!=null && GNP.size()>0){
							Collections.sort(GNP);
							this.minGNP = GNP.get(0);
							this.maxGNP = GNP.get(LifeExpectancy.size()-1);
							this.avgGNP = gnp/GNP.size();
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			status = "FAIL";
		}catch (Exception e) {
			status = "FAIL";
		}
		return status;
	}
}
