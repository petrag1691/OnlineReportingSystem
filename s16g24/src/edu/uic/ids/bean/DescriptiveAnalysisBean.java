package edu.uic.ids.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.math3.stat.StatUtils;

import edu.uic.ids.constants.FinalConstants;
@ManagedBean(name="descriptiveAnalysisBean",eager=true)
@ViewScoped
public class DescriptiveAnalysisBean implements Serializable,Cloneable{
	private static final long serialVersionUID = -6263792574991732333L;
	//private double[] analysisValues;
	private double minValue;
	private double maxValue;
	private double mean;
	private double variance;
	private double standardDeviation ;
	private double median ;
	private double firstQuartile;
	private double thirdQuartile ;
	private double interQuartileRange;
	private double range;
	private String column;
	public DescriptiveAnalysisBean(){
	}
	@PostConstruct
	public void init(){
		minValue =0.0;
		maxValue =0.0;
		mean =0.0;
		variance =0.0;
		standardDeviation =0.0;
		median  =0.0;
		firstQuartile =0.0;
		thirdQuartile =0.0;
		interQuartileRange =0.0;
		range =0.0;
		column = "";
	}
	public DescriptiveAnalysisBean clone() throws CloneNotSupportedException {
		DescriptiveAnalysisBean cloned = (DescriptiveAnalysisBean) super.clone();
		return cloned;
	}
	public double getMinValue() {
		return minValue;
	}
	public double getMaxValue() {
		return maxValue;
	}
	public double getMean() {
		return mean;
	}
	public double getVariance() {
		return variance;
	}
	public double getStandardDeviation() {
		return standardDeviation;
	}
	public double getMedian() {
		return median;
	}
	public double getFirstQuartile() {
		return firstQuartile;
	}
	public double getThirdQuartile() {
		return thirdQuartile;
	}
	public double getInterQuartileRange() {
		return interQuartileRange;
	}
	public double getRange() {
		return range;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public boolean generateStats(double[] tempArray){
		if(tempArray!=null && tempArray.length>0){
			minValue = FinalConstants.round(StatUtils.min(tempArray), 100);
			maxValue =  FinalConstants.round(StatUtils.max(tempArray), 100);
			mean =  FinalConstants.round(StatUtils.mean(tempArray), 100);
			variance =  FinalConstants.round(StatUtils.variance(tempArray, mean), 100);
			standardDeviation = FinalConstants.round(Math.sqrt(variance), 100);
			median =  FinalConstants.round(StatUtils.percentile(tempArray, 50.0), 100);
			firstQuartile =  FinalConstants.round(StatUtils.percentile(tempArray, 25.0), 100);
			thirdQuartile =  FinalConstants.round(StatUtils.percentile(tempArray, 75.0), 100);
			interQuartileRange =  FinalConstants.round(thirdQuartile - firstQuartile, 100);
			range =  FinalConstants.round(maxValue - minValue, 100);
			return true;
		}else{
			return false;
		}
	}
	
}
