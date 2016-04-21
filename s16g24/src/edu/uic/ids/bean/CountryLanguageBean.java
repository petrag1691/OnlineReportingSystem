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
@ManagedBean(name="countryLanguageBean",eager=true)
@RequestScoped
public class CountryLanguageBean {
	private DatabaseAccessBean dbbean;
	private String tableName;
	private double maxPercentage;
	private double minPercentage;
	private double avgPercentage;
	private FacesContext context;
	public CountryLanguageBean(){
		this.tableName="CountryLanguage";
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
	public double getMaxPercentage() {
		return FinalConstants.round(maxPercentage,100);
	}
	public double getMinPercentage() {
		return FinalConstants.round(minPercentage,100);
	}
	public double getAvgPercentage() {
		return FinalConstants.round(avgPercentage,100);
	}
	public String getReports(){
		String status = "SUCCESS";
		try {
			if(this.dbbean!=null){
				String query = "SELECT * FROM CountryLanguage";
				String statusCode = this.dbbean.processQuery(query);
				if(statusCode.equals("SUCCESS")){
					ResultSet resultSet = this.dbbean.getResultSet();
					if(resultSet!=null){
						List<Double>percentages = new ArrayList<>();
						double percent = 0;
						while(resultSet.next()){
							if(resultSet.getString("percentage")!=null){
								percentages.add(Double.parseDouble(resultSet.getString("percentage")));
								percent = percent+Double.parseDouble(resultSet.getString("percentage"));
							}
						}
						if(percentages!=null && percentages.size()>0){
							Collections.sort(percentages);
							this.minPercentage = percentages.get(0);
							this.maxPercentage = percentages.get(percentages.size()-1);
							this.avgPercentage = percent/percentages.size();
						}
					}
				}
			}
		} catch (SQLException e) {
			status = "FAIL";
			e.printStackTrace();
		}catch (Exception e) {
			status = "FAIL";
			e.printStackTrace();
		}
		return status;
	}
}
