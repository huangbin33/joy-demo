package cn.joy.demo.external.chart.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

public class LineChartTest {
	private static JFreeChart createLineChart(CategoryDataset dataset) {
		// 通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createLineChart("公司销售额分布图", "月份", "销售额", dataset, PlotOrientation.VERTICAL, true, true, false);
		// 使用CategoryPlot设置各种参数。以下设置可以省略。      
        CategoryPlot plot = (CategoryPlot) chart.getPlot();      
        // 背景色 透明度      
        plot.setBackgroundAlpha(0.5f);      
        // 前景色 透明度      
        plot.setForegroundAlpha(0.5f);      
        // 其他设置 参考 CategoryPlot类      
        plot.setBackgroundPaint(Color.white);
        /*
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis(); 
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); 
        // 是否显示零点 
        rangeAxis.setAutoRangeIncludesZero(false); 
        // 数据轴上（右）边距 
        rangeAxis.setUpperMargin(0.02); 
        // Y轴名称的倾斜角度 
        rangeAxis.setLabelAngle(0.3); 
        // 设置数据轴的数据范围 
        rangeAxis.setRange(new Range(0, 1000)); 
        // 设置刻度波动值(步长) 
        rangeAxis.setTickUnit(new NumberTickUnit(102)); */

        // 拆线线设置 
        // 显示数据 
        /*LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer(); 
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator()); 
        // 设置曲线样式 
        renderer.setPaint(Color.GREEN); 
        renderer.setItemLabelsVisible(true); 
        renderer.setShapesVisible(true); */
        
		return chart;
	}
	
	private static JFreeChart createLine3DChart(CategoryDataset dataset) {
		// 通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createLineChart3D("公司销售额分布图", "月份", "销售额", dataset, PlotOrientation.VERTICAL, true, true, false);
        
		return chart;
	}
	
	public static void main(String[] args) throws Exception{
		// 创建主题样式
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);

		// 设置数据集
		String[] rowKeys = { "腾讯", "阿里巴巴", "百度" };      
        String[] colKeys = { "1月", "2月", "3月", "4月", "5月", "6月" };      
     
        double[][] data = { { 50, 20, 30, 25, 35, 40 }, { 20, 10D, 40D, 32, 42, 55 },      
                { 40, 30.0008D, 38.24D, 22, 31.5, 39 }};  
        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
        
		ChartUtilities.saveChartAsPNG(new File("D:/line.png"), createLineChart(dataset), 650, 400);
		ChartUtilities.saveChartAsPNG(new File("D:/line_3D.png"), createLine3DChart(dataset), 650, 400);
		
	}
}
