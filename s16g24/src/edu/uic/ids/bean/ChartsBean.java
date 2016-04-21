package edu.uic.ids.bean;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@ManagedBean(name="chartsBean",eager=true)
@ViewScoped
//Bean for handling charts generation
public class ChartsBean implements Serializable,Cloneable{

	private static final long serialVersionUID = -7489182203022705276L;
	private DefaultPieDataset pieDataset;
	private DefaultCategoryDataset barDataSet;
	private XYSeriesCollection xyDataset;
	private XYSeries scatterSet;
	private XYSeries predictorSet;
	private XYSeries responseSet;
	private JFreeChart chart;
	private String filePath;
	private File generatedChart;
	private File chartDirectory;
	private String pieChartPath;
	private String barChartPath;
	private String xyChartPath;
	private String timeSeriesChartPath;
	private double percentage;
	private Map<String,Integer> sortedMap;
	private int displayCount;
	private HashMap<String,Integer>sortedHashMap;
	private String status;
	@PostConstruct
	public void init(){
		/*scatterSet = new XYSeries("Regression Analysis");
		scatterDataset = new XYSeriesCollection();*/
		filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/images");
		chartDirectory = new File(filePath);
		if(!chartDirectory.exists()){
			chartDirectory.mkdir();
		}
	}

	public DefaultPieDataset getPieDataset() {
		return pieDataset;
	}

	public void setPieDataset(DefaultPieDataset pieDataset){
		this.pieDataset = pieDataset;
	}
	
	public DefaultCategoryDataset getBarDataSet() {
		return barDataSet;
	}

	public void setBarDataSet(DefaultCategoryDataset barDataSet) {
		this.barDataSet = barDataSet;
	}

	public XYSeries getScatterSet() {
		return scatterSet;
	}

	public void setScatterSet(XYSeries scatterSet) {
		this.scatterSet = scatterSet;
	}

	public XYSeries getPredictorSet() {
		return predictorSet;
	}

	public void setPredictorSet(XYSeries predictorSet) {
		this.predictorSet = predictorSet;
	}

	public XYSeries getResponseSet() {
		return responseSet;
	}

	public void setResponseSet(XYSeries responseSet) {
		this.responseSet = responseSet;
	}

	public String getPieChartPath() {
		return pieChartPath;
	}

	public String getBarChartPath() {
		return barChartPath;
	}

	public String getXyChartPath() {
		return xyChartPath;
	}

	public String getTimeSeriesChartPath() {
		return timeSeriesChartPath;
	}
	// Generates pie chart
	public String generatePieChart(Map<String,Integer>pieMap,int rowCount,String column,String table,String groupByColumn,String user){
		if(pieMap!=null && pieMap.size()>0){
			pieDataset = new DefaultPieDataset();
			displayCount = 0;
			percentage =0.0;
			sortMap(pieMap);
			if(sortedHashMap!=null && sortedHashMap.size()>0){
				for (Entry<String, Integer> entry : sortedHashMap.entrySet())
				{
				    //System.out.println(entry.getKey() + "/" + entry.getValue());
				    displayCount++;
				    if(displayCount>20){
				    	pieDataset.setValue("Others",100-percentage);
				    	break;
				    }
				    //System.out.println("ChartsBean.generatePieChart() row percent is "+(double)entry.getValue()/(double)rowCount);
				    percentage = percentage+(double)((double)entry.getValue()/(double)rowCount)*100;
				    //System.out.println("ChartsBean.generatePieChart() percentage is "+percentage);
				    pieDataset.setValue(entry.getKey(),(double)((double)entry.getValue()/(double)rowCount)*100.0);
				}
			}
			chart = ChartFactory.createPieChart("Pie Chart for number of "+table+"."+column+" by "+groupByColumn, pieDataset);
			generatedChart = new File(filePath+"/"+user+"_"+column+"_piechart.png");
			pieChartPath = "/images/"+user+"_"+column+"_piechart.png";
			//System.out.println("ChartsBean.generatePieChart() file path is "+pieChartPath);
			if(generatedChart.exists()){
				generatedChart.delete();
			}
			try {
				ChartUtilities.saveChartAsPNG(generatedChart, chart, 800, 800);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return e.getMessage();
			}
		}
		return "SUCCESS";
	}
	
	//Generates bar chart
	public String generateBarChart(Map<String,Integer>barMap,int rowCount,String column,String user){
		if(barMap!=null && barMap.size()>0){
			displayCount = 0;
			percentage =0.0;
			sortMap(barMap);
			if(sortedHashMap!=null && sortedHashMap.size()>0){
				for (Entry<String, Integer> entry : sortedHashMap.entrySet()){
					//System.out.println(entry.getKey() + "/" + entry.getValue());
				    displayCount++;
				    if(displayCount>6){
				    	//pieDataset.setValue("Others",100-percentage);
				    	break;
				    }
				    //System.out.println("ChartsBean.generatePieChart() row percent is "+entry.getValue()/rowCount);
				    percentage = percentage+(double)(entry.getValue()/rowCount)*100;
				    //System.out.println("ChartsBean.generatePieChart() percentage is "+percentage);
				    //pieDataset.setValue(entry.getKey(),(double)((double)entry.getValue()/(double)rowCount)*100.0);
				   //barDataSet.addValue(value, rowKey, columnKey);
				}
			}
			chart = ChartFactory.createPieChart("Pie Chart for "+column, pieDataset);
			generatedChart = new File(filePath+"/"+user+"_"+column+"_barchart.png");
			barChartPath = "/images/"+user+"_"+column+"_barchart.png";
			//System.out.println("ChartsBean.generatePieChart() file path is "+barChartPath);
			if(generatedChart.exists()){
				generatedChart.delete();
			}
			try {
				ChartUtilities.saveChartAsPNG(generatedChart, chart, 800, 800);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return e.getMessage();
			}
		}
		return "SUCCESS";
	}
	
	//Clears xytimeseries dataset instance variables
	public void clearDataSets(String predictor,String response,String chartType) {
		if(chartType.equalsIgnoreCase("x-y")){
			if(scatterSet==null){
				scatterSet = new XYSeries(predictor+"_"+response+"_Regression Analysis");
			}else{
				scatterSet.clear();
			}
		}else if(chartType.equalsIgnoreCase("tm")){
			if(predictorSet==null){
				predictorSet = new XYSeries("Observation_"+predictor+"_Comparison analysis");
			}else{
				predictorSet.clear();
			}
			if(responseSet==null){
				responseSet = new XYSeries("Observation_"+response+"_Comparison analysis");
			}else{
				responseSet.clear();
			}
		}
	}
	
	//adds one record to xytimeseries of scatter plot
	public void scatterData(double predVal,double responseVal){
		scatterSet.add(predVal,responseVal);
	}
	
	//adds one record each to predictor and response xyseries  datasets
	public void timeSeriesData(double xColumnValue,double yColumnValue, double observationNumber) {
		predictorSet.add(observationNumber, xColumnValue);
		responseSet.add(observationNumber, yColumnValue);
	}
	
	//Generates scatter plot
	public String generateScatterPlot(String predictor, String response,String user){
		status = "FAIL";
		xyDataset = new XYSeriesCollection();
		if(scatterSet!=null){
			xyDataset.addSeries(scatterSet);
			chart = ChartFactory.createScatterPlot("Scatter Plot", predictor, response, xyDataset);
			generatedChart = new File(filePath+"/"+user+"_"+predictor+"_"+response+"_xychart.png");
			xyChartPath = "/images/"+user+"_"+predictor+"_"+response+"_xychart.png";
			//System.out.println("ChartsBean.generatePieChart() file path is "+xyChartPath);
			if(generatedChart.exists()){
				generatedChart.delete();
			}
			try {
				ChartUtilities.saveChartAsPNG(generatedChart, chart, 800, 800);
				status = "SUCCESS";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				status = e.getMessage();
			}
		}
		
		return status;
	}
	
	//Generates Time Series plot
	public String generateTimesSeriesPlot(String predictor,String response,String user){
		status ="FAIL";
		xyDataset = new XYSeriesCollection();
		if(predictorSet!=null && responseSet!=null){
			xyDataset.addSeries(predictorSet);
			xyDataset.addSeries(responseSet);
			chart = ChartFactory.createXYLineChart("Time Series Plot", "Number of Observations", "Observations", xyDataset);
			generatedChart = new File(filePath+"/"+user+"_"+predictor+"_"+response+"_timeserieschart.png");
			timeSeriesChartPath = "/images/"+user+"_"+predictor+"_"+response+"_timeserieschart.png";
			//System.out.println("ChartsBean.generatePieChart() file path is "+timeSeriesChartPath);
			if(generatedChart.exists()){
				generatedChart.delete();
			}
			try {
				ChartUtilities.saveChartAsPNG(generatedChart, chart, 800, 800);
				status = "SUCCESS";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				status = e.getMessage();
			}
		} 
		return status;
	}
	
	//Logic to sort a map 
	private HashMap<String,Integer> sortMap(Map<String, Integer> pieMap) {
		Set<Entry<String, Integer>> set = pieMap.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        if(sortedHashMap!=null && sortedHashMap.size()>0){
        	sortedHashMap.clear();
        }else{
        	sortedHashMap = new LinkedHashMap<>();	
        }
        for (Iterator<Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
               Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) it.next();
               //System.out.println("ChartsBean.sortMap() key and value are "+entry.getKey()+"/"+entry.getValue());
               sortedHashMap.put(entry.getKey(), entry.getValue());
        } 
		return sortedHashMap;
	}
	
	//Generates categorical bar/pie chart
	public String generateCategoricalChart(Map<String, Integer> chartMap, int rowCount, String chartColumn,
			String selectedTable, String selectedGroupByOption, String username,String chartType) {
		status = "FAIL";
		if(chartType.equalsIgnoreCase("pie")){
			if(pieDataset!=null){
				pieDataset.clear();
			}else{
				pieDataset = new DefaultPieDataset();
			}
		}else if (chartType.equalsIgnoreCase("bar")){
			if(barDataSet!=null){
				barDataSet.clear();
			}else{
				barDataSet = new DefaultCategoryDataset(); 
			}
		}
		displayCount = 0;
		percentage =0.0;
		sortMap(chartMap);
		if(sortedHashMap!=null && sortedHashMap.size()>0){
			for (Entry<String, Integer> entry : sortedHashMap.entrySet())
			{
			    //System.out.println(entry.getKey() + "/" + entry.getValue());
			    displayCount++;
			    if(displayCount>20){
			    	if(chartType.equalsIgnoreCase("pie")){
			    		pieDataset.setValue("Others",100-percentage);
					}else{
						barDataSet.addValue(100-percentage, selectedGroupByOption, "Count");
					}
			    	break;
			    }
			    //System.out.println("ChartsBean.generatePieChart() row percent is "+(double)entry.getValue()/(double)rowCount);
			    percentage = percentage+(double)((double)entry.getValue()/(double)rowCount)*100;
			    //System.out.println("ChartsBean.generatePieChart() percentage is "+percentage);
			    if(chartType.equalsIgnoreCase("pie")){
			    	pieDataset.setValue(entry.getKey(),(double)((double)entry.getValue()/(double)rowCount)*100.0);
				}else{
					barDataSet.addValue((double)((double)entry.getValue()/(double)rowCount)*100.0, selectedGroupByOption,entry.getKey());
				}
			}
		}
		if(chartType.equalsIgnoreCase("pie")){
			chart = ChartFactory.createPieChart("Pie Chart for number of "+selectedTable+"."+chartColumn+" by "+selectedGroupByOption, pieDataset);
			generatedChart = new File(filePath+"/"+username+"_"+chartColumn+"_piechart.png");
			pieChartPath = "/images/"+username+"_"+chartColumn+"_piechart.png";
			//System.out.println("ChartsBean.generatePieChart() file path is "+pieChartPath);
		}else{
			chart = ChartFactory.createBarChart3D("Bar Chart for number of "+selectedTable+"."+chartColumn+" by "+selectedGroupByOption, chartColumn, "Count", barDataSet);
			generatedChart = new File(filePath+"/"+username+"_"+chartColumn+"_barchart.png");
			barChartPath = "/images/"+username+"_"+chartColumn+"_barchart.png";
			//System.out.println("ChartsBean.generatePieChart() file path is "+barChartPath);
		}
		
		if(generatedChart.exists()){
			generatedChart.delete();
		}
		try {
			ChartUtilities.saveChartAsPNG(generatedChart, chart, 800, 800);
			status = "SUCCESS";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = e.getMessage();
		}
		return status;
	}

	//Generates quantitative bar/pie chart
	public String generateQuantitativeChart(DescriptiveAnalysisBean dABean, List<Double> dataList, String chartType,String chartColumn,String username,String selectedTable) {
		sortedMap = new HashMap<>();
		String category = "";
		status = "FAIL";
		if(chartType.equalsIgnoreCase("pie")){
			if(pieDataset!=null){
				pieDataset.clear();
			}else{
				pieDataset = new DefaultPieDataset();
			}
		}else if (chartType.equalsIgnoreCase("bar")){
			if(barDataSet!=null){
				barDataSet.clear();
			}else{
				barDataSet = new DefaultCategoryDataset(); 
			}
		}
		displayCount = 0;
		percentage =0.0;
		if(dataList!=null && dataList.size()>0){
			for(int i=0;i<dataList.size();i++){
				if(dABean!=null){
					if(dataList.get(i)>1.5*dABean.getInterQuartileRange()+dABean.getThirdQuartile()){
						category = "UpperOutlier";
						insertIntoSortedMap(category);
					}else if(dataList.get(i)>=dABean.getThirdQuartile() && dataList.get(i)<1.5*dABean.getInterQuartileRange()+dABean.getThirdQuartile()){
						category = "AboveThirdQuartile";
						insertIntoSortedMap(category);
					}else if(dataList.get(i)<dABean.getThirdQuartile() && dataList.get(i)>=dABean.getMedian()){
						category = "AboveSecondQuartile";
						insertIntoSortedMap(category);
					}else if(dataList.get(i)<dABean.getMean() && dataList.get(i)>=dABean.getFirstQuartile()){
						category = "AboveFirstQuartile";
						insertIntoSortedMap(category);
					}else if(dataList.get(i)<dABean.getFirstQuartile() && dataList.get(i)>dABean.getFirstQuartile()-1.5*dABean.getInterQuartileRange()+dABean.getFirstQuartile()){
						category = "BelowFirstQuartile";
						insertIntoSortedMap(category);
					}else if(dataList.get(i)>dABean.getMean() && dataList.get(i)<=dABean.getFirstQuartile()){
						category = "LowerOutlier";
						insertIntoSortedMap(category);
					}
				}
			}
			if(sortedMap!=null && sortedMap.size()>0){
				for (Entry<String,Integer> entry : sortedMap.entrySet()){
				    //System.out.println("ChartsBean.generateQuantitativeChart() entry key "+entry.getKey()+"/"+entry.getValue());
				    displayCount++;
				    if(displayCount>20){
				    	if(chartType.equalsIgnoreCase("pie")){
				    		pieDataset.setValue("Others",100-percentage);
						}else{
							barDataSet.addValue(100-percentage, "Others", "Count");
						}
				    	break;
				    }
				    //System.out.println("ChartsBean.generatePieChart() row percent is "+(double)entry.getValue()/(double)dataList.size());
				    percentage = percentage+(double)((double)entry.getValue()/(double)dataList.size())*100;
				    //System.out.println("ChartsBean.generatePieChart() percentage is "+percentage);
				    if(chartType.equalsIgnoreCase("pie")){
				    	pieDataset.setValue(entry.getKey(),(double)((double)entry.getValue()/(double)dataList.size())*100.0);
					}else{
						barDataSet.addValue((double)((double)entry.getValue()/(double)dataList.size())*100.0, chartColumn, entry.getKey());
					}
				}
			}
			if(chartType.equalsIgnoreCase("pie")){
				chart = ChartFactory.createPieChart("Pie Chart for grouping of "+selectedTable+"."+chartColumn, pieDataset);
				generatedChart = new File(filePath+"/"+username+"_"+chartColumn+"_piechart.png");
				pieChartPath = "/images/"+username+"_"+chartColumn+"_piechart.png";
				//System.out.println("ChartsBean.generatePieChart() file path is "+pieChartPath);
			}else{
				chart = ChartFactory.createBarChart3D("Bar Chart for grouping of "+selectedTable+"."+chartColumn,chartColumn, "Count", barDataSet);
				generatedChart = new File(filePath+"/"+username+"_"+chartColumn+"_barchart.png");
				barChartPath = "/images/"+username+"_"+chartColumn+"_barchart.png";
				//System.out.println("ChartsBean.generatePieChart() file path is "+barChartPath);
			}
			
			if(generatedChart.exists()){
				generatedChart.delete();
			}
			try {
				ChartUtilities.saveChartAsPNG(generatedChart, chart, 800, 800);
				status = "SUCCESS";
			} catch (IOException e) {
				e.printStackTrace();
				status = e.getMessage();
			}
		}
		return status;
	}

	//logic to insert number of values within each category into a map with category as the key
	private void insertIntoSortedMap(String category) {
		if (sortedMap.containsKey(category)) {
			sortedMap.put(category, sortedMap.get(category) + 1);
	    }else{
	    	sortedMap.put(category,1);
	    }
	}
}
