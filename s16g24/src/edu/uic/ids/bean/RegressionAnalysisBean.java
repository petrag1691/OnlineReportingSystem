package edu.uic.ids.bean;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import edu.uic.ids.constants.FinalConstants;
@ManagedBean(name="regressionAnalysisBean",eager=true)
@ViewScoped
public class RegressionAnalysisBean implements Serializable,Cloneable{
	private static final long serialVersionUID = -8719067883856520538L;
	private SimpleRegression sr;
	private TDistribution tDistribution;
	private FDistribution fDistribution;
	private String regressionEquation;
	private double intercept;
	private double interceptStandardError;
	private double tStatisticIntercept;
	private double pValueIntercept;
	private double slope;
	private double slopeStandardError;
	private double tStatisticPredictor;
	private double pValuePredictor;
	private double modelError;
	private double rSquare;
	private double rSquareAdjusted;
	//Variables for analysis of variance
	private int predictorDF;
	private int residualErrorDF;
	private int totalDF;
	private double regressionSumSquares;
	private double sumSquaredErrors;
	private double totalSumSquares;
	private double meanSquare;
	private double meanSquareError;
	private double fValue;
	private double pValue;

	@PostConstruct
	public void init(){
		sr = new SimpleRegression();
	}
	public RegressionAnalysisBean clone() throws CloneNotSupportedException {
		RegressionAnalysisBean cloned = (RegressionAnalysisBean) super.clone();
		return cloned;
	}
	public String getRegressionEquation() {
		return regressionEquation;
	}
	public void setRegressionEquation(String regressionEquation) {
		this.regressionEquation = regressionEquation;
	}
	public double getIntercept() {
		return intercept;
	}
	public double getInterceptStandardError() {
		return interceptStandardError;
	}
	public double gettStatisticIntercept() {
		return tStatisticIntercept;
	}
	public double getpValueIntercept() {
		return pValueIntercept;
	}
	public double getSlope() {
		return slope;
	}
	public double getSlopeStandardError() {
		return slopeStandardError;
	}
	public double gettStatisticPredictor() {
		return tStatisticPredictor;
	}
	public double getpValuePredictor() {
		return pValuePredictor;
	}
	public double getModelError() {
		return modelError;
	}
	public double getrSquare() {
		return rSquare;
	}
	public double getrSquareAdjusted() {
		return rSquareAdjusted;
	}
	public int getPredictorDF() {
		return predictorDF;
	}
	public int getResidualErrorDF() {
		return residualErrorDF;
	}
	public int getTotalDF() {
		return totalDF;
	}
	public double getRegressionSumSquares() {
		return regressionSumSquares;
	}
	public double getSumSquaredErrors() {
		return sumSquaredErrors;
	}
	public double getTotalSumSquares() {
		return totalSumSquares;
	}
	public double getMeanSquare() {
		return meanSquare;
	}
	public double getMeanSquareError() {
		return meanSquareError;
	}
	public double getfValue() {
		return fValue;
	}
	public double getpValue() {
		return pValue;
	}
	public String generateAnalysis(double[] responseArray,double[] predictorArray){
		try {
			if(sr!=null){
				sr.clear();
			}else{
				sr = new SimpleRegression();
			}
			//System.out.println("RegressionAnalysisBean.generateAnalysis() array length are "+responseArray.length+"  "+predictorArray.length);
			if(responseArray.length>predictorArray.length){
				for(int i=0;i<predictorArray.length;i++){
					sr.addData(predictorArray[i], responseArray[i]);
				}
			}else{
				for(int i=0;i<responseArray.length;i++){
					sr.addData(predictorArray[i], responseArray[i]);
				}
			}
			//System.out.println("RegressionAnalysisBean.generateAnalysis() responsearray length is "+responseArray.length);
			totalDF = responseArray.length-1;
			tDistribution = new TDistribution(totalDF);
			intercept = FinalConstants.round(sr.getIntercept(), 100000);
			interceptStandardError=FinalConstants.round(sr.getInterceptStdErr(), 100000);
			if(interceptStandardError!=0){
				tStatisticIntercept = FinalConstants.round((double)intercept/interceptStandardError, 100000);
			}
			pValueIntercept = FinalConstants.round((double)2*tDistribution.cumulativeProbability(-Math.abs(tStatisticIntercept)), 100000);
			slope = FinalConstants.round(sr.getSlope(), 100000);
			slopeStandardError = FinalConstants.round(sr.getSlopeStdErr(), 100000);
			if(slopeStandardError!=0){
				tStatisticPredictor = FinalConstants.round((double)slope/slopeStandardError, 100000);
			}
			pValuePredictor = FinalConstants.round((double)2*tDistribution.cumulativeProbability(-Math.abs(tStatisticPredictor)), 100000);
			predictorDF = 1;
			residualErrorDF = totalDF - predictorDF;
			rSquare =FinalConstants.round(sr.getRSquare(), 100000);
			rSquareAdjusted = FinalConstants.round(rSquare-((1-rSquare)/residualErrorDF), 100000);
			modelError = FinalConstants.round(Math.sqrt(StatUtils.variance(responseArray))*(Math.sqrt(1-rSquareAdjusted)), 100000);
			regressionSumSquares = FinalConstants.round(sr.getRegressionSumSquares(), 100000);
			sumSquaredErrors = FinalConstants.round(sr.getSumSquaredErrors(), 100000);
			totalSumSquares = FinalConstants.round(sr.getTotalSumSquares(), 100000);
			if(predictorDF!=0){
				meanSquare = FinalConstants.round(regressionSumSquares/predictorDF, 100000);
			}
			if(residualErrorDF!=0){
				meanSquareError = FinalConstants.round(sumSquaredErrors/residualErrorDF, 100000);
			}
			if(meanSquareError!=0){
				fValue = FinalConstants.round(meanSquare/meanSquareError, 100000);
			}
			fDistribution = new FDistribution(predictorDF, residualErrorDF);
			//System.out.println("RegressionAnalysisBean.generateAnalysis() pValue "+(double)(1-fDistribution.cumulativeProbability(fValue)));
			pValue = FinalConstants.round((double)(1-fDistribution.cumulativeProbability(fValue)), 100000);
			//pValue = FinalConstants.round((double)2*tDistribution.cumulativeProbability(-Math.abs(fValue)), 1000000);
			//rSquareAdjusted = FinalConstants.round((1-((sumSquaredErrors*totalDF)/(totalSumSquares*residualErrorDF)))*100, 100);
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
