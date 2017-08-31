package complex.statistics;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import utils.JSONUtil;
import utils.LogUtil;

public class JFreeChartEnabler {
	/**
	 * 
	 * @param filepath
	 * @param titleStr list
	 * @param dataStr  二维list
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public static void buildBarChart(String filepath,String titleStr,String dataStr) throws IOException{
		List<String> titleList=(List<String>) JSONUtil.parse(titleStr, List.class);
		List<List<String>> dataList=(List<List<String>>) JSONUtil.parse(dataStr,List.class);
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (List<String> list : dataList) {
        	if(list.size()<3){
        		LogUtil.p("list大小错误",list);
        		return;
        	}
        	double value=Double.valueOf(list.get(0)) ;
        	dataset.addValue(value, list.get(1) , list.get(2) );
		}
	    JFreeChart barChart = ChartFactory.createBarChart(
	    		  titleList.get(0), 
	    		  titleList.get(1), 
	    		  titleList.get(2), 
	         dataset,PlotOrientation.VERTICAL, 
	         true, true, false);
	         
	    int width = 640; /* Width of the image */
	    int height = 480; /* Height of the image */ 
	    File BarChart = new File(filepath); 
	    ChartUtilities.saveChartAsJPEG( BarChart , barChart , width , height );
	}
	public static void buildPieChart(String filepath,String titleStr,String dataStr) throws IOException{
		List<String> titleList=(List<String>) JSONUtil.parse(titleStr, List.class);
		List<List<String>> dataList=(List<List<String>>) JSONUtil.parse(dataStr,List.class);
		final DefaultPieDataset dataset = new DefaultPieDataset( );
		for (List<String> list : dataList) {
			if(list.size()<2){
				LogUtil.p("list大小错误",list);
				return;
			}
			double value=Double.valueOf(list.get(0)) ;
			dataset.setValue( list.get(1),value);
		}
	    JFreeChart chart = ChartFactory.createPieChart(
	    		  titleList.get(0), // chart title
	    	         dataset, // data
	    	         true, // include legend
	    	         true,
	    	         false);
		
		int width = 640; /* Width of the image */
		int height = 480; /* Height of the image */ 
		File pieChart = new File(filepath); 
		ChartUtilities.saveChartAsJPEG( pieChart , chart , width , height );
	}
	public static void buildLineChart(String filepath,String titleStr,String dataStr) throws IOException{
		List<String> titleList=(List<String>) JSONUtil.parse(titleStr, List.class);
		List<List<String>> dataList=(List<List<String>>) JSONUtil.parse(dataStr,List.class);
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		for (List<String> list : dataList) {
			if(list.size()<3){
				LogUtil.p("list大小错误",list);
				return;
			}
			double value=Double.valueOf(list.get(0)) ;
			dataset.addValue(value, list.get(1) , list.get(2) );
		}
		JFreeChart barChart = ChartFactory.createLineChart(
				titleList.get(0), 
				titleList.get(1), 
				titleList.get(2), 
				dataset,PlotOrientation.VERTICAL, 
				true, true, false);
		
		int width = 640; /* Width of the image */
		int height = 480; /* Height of the image */ 
		File chart = new File(filepath); 
		ChartUtilities.saveChartAsJPEG( chart , barChart , width , height );
	}
}
