package edu.uic.ids.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@SuppressWarnings("serial")
@ManagedBean
@RequestScoped
public class ReportActionBean implements Cloneable,Serializable{
	@ManagedProperty(value="#{cityBean}")
	private CityBean cityBean;
	@ManagedProperty(value="#{countryBean}")
	private CountryBean countryBean;
	@ManagedProperty(value="#{countryLanguageBean}")
	private CountryLanguageBean countryLanguageBean;
	private int reportNumber;
	private boolean renderList;
	private Map<String,String> resultMap;
	public ReportActionBean(){
		this.renderList = false;
		this.resultMap=new TreeMap<>();
	}
	public int getReportNumber() {
		return reportNumber;
	}
	public void setReportNumber(int reportNumber) {
		this.reportNumber = reportNumber;
	}
	public CityBean getCityBean() {
		return cityBean;
	}
	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}
	public CountryBean getCountryBean() {
		return countryBean;
	}
	public void setCountryBean(CountryBean countryBean) {
		this.countryBean = countryBean;
	}
	public CountryLanguageBean getCountryLanguageBean() {
		return countryLanguageBean;
	}
	public void setCountryLanguageBean(CountryLanguageBean countryLanguageBean) {
		this.countryLanguageBean = countryLanguageBean;
	}
	public boolean isRenderList() {
		return renderList;
	}
	public void setRenderList(boolean renderList) {
		this.renderList = renderList;
	}
	public Map<String, String> getResultMap() {
		return resultMap;
	}
	public String getResults(){
		String status = "SUCCESS";
		if(this.resultMap.size()>0){
			resultMap.clear();
		}
			switch (this.reportNumber) {
			case 1:
				status = this.cityBean.getResults();
				if(status.equals("SUCCESS")){
					resultMap.put("Total Population", Integer.toString(this.cityBean.getTotalPopulation()));
					resultMap.put("Country Codes Count",Integer.toString(this.cityBean.getCountryCodesCount()));
					this.renderList = true;
				}
				break;
			case 2:
				status = this.countryBean.getReports();
				if(status.equals("SUCCESS")){
					resultMap.put("Total Population", Double.toString(this.countryBean.getTotalPopulation()));
					resultMap.put("Total Surface Area",Double.toString(this.countryBean.getTotalSurfaceArea()));
					resultMap.put("Maximum Life Expectancy",Double.toString(this.countryBean.getMaxLifeExpectancy()));
					resultMap.put("Minimum Life Expectancy",Double.toString(this.countryBean.getMinLifeExpectancy()));
					resultMap.put("Average Life Expectancy",Double.toString(this.countryBean.getAvgLifeExpectancy()));
					this.renderList = true;
				}
				break;
			case 3:
				status = this.countryBean.getReports();
				if(status.equals("SUCCESS")){
					resultMap.put("Total Population", Double.toString(this.countryBean.getTotalPopulation()));
					resultMap.put("Total Surface Area",Double.toString(this.countryBean.getTotalSurfaceArea()));
					resultMap.put("Maximum GNP",Double.toString(this.countryBean.getMaxGNP()));
					resultMap.put("Minimum GNP",Double.toString(this.countryBean.getMinGNP()));
					resultMap.put("Average GNP",Double.toString(this.countryBean.getAvgGNP()));
					this.renderList = true;
				}
				break;
			case 4:
				status = this.countryLanguageBean.getReports();
				if(status.equals("SUCCESS")){
					resultMap.put("Maximum Percentage", Double.toString(this.countryLanguageBean.getMaxPercentage()));
					resultMap.put("Minimum Percentage",Double.toString(this.countryLanguageBean.getMinPercentage()));
					resultMap.put("Average Percentage",Double.toString(this.countryLanguageBean.getAvgPercentage()));
					this.renderList = true;
				}
				break;
			default:
				break;
			}
		//}
		return status;
	}
}

