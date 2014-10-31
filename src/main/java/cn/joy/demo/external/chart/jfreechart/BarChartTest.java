package cn.joy.demo.external.chart.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

public class BarChartTest {
	private static JFreeChart createVerticalBar(CategoryDataset dataset){
		JFreeChart chart = ChartFactory.createBarChart3D("水果销量统计图", "水果", "销量", dataset, PlotOrientation.VERTICAL,
				true, false, false);
		
		CategoryPlot plot = chart.getCategoryPlot(); 
		//设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		//设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.blue);
		//设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		 
		//显示每个柱的数值，并修改该数值的字体属性
		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		 
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);
		 
		//设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0.4);
		plot.setRenderer(renderer);
		 
		//设置地区、销量的显示位置
		//将下方的“水果”放到上方
		//plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		//将默认放在左边的“销量”放到右方
		//plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		return chart;
	}
	
	private static JFreeChart createHorizontalBar(CategoryDataset dataset){
		JFreeChart chart = ChartFactory.createBarChart("水果销量统计图", "水果", "销量", dataset, PlotOrientation.HORIZONTAL,
				true, false, false);
		
		CategoryPlot plot = chart.getCategoryPlot(); 
		//设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		//设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		//设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		 
		//显示每个柱的数值，并修改该数值的字体属性
		BarRenderer renderer = new BarRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		 
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE4, TextAnchor.BOTTOM_RIGHT.BASELINE_RIGHT));
		renderer.setItemLabelAnchorOffset(30D);
		 
		plot.setRenderer(renderer);
		
		return chart;
	}
	
	public static void main(String[] args) throws Exception {
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

		/*DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(510, "深圳", "苹果");
		dataset.addValue(320, "深圳", "香蕉");
		dataset.addValue(580, "深圳", "橘子");
		dataset.addValue(390, "深圳", "梨子");
		JFreeChart chart = ChartFactory.createBarChart3D("水果销量统计图", "水果", "销量", dataset, PlotOrientation.VERTICAL,
				false, false, false);*/

		/*double[][] data = new double[][] { { 1320 }, { 720 }, { 830 }, { 400 } };
		String[] rowKeys = { "苹果", "香蕉", "橘子", "梨子" };
		String[] columnKeys = { "" };*/
		
		double[][] data = new double[][] {{1230,1110,1120,1210}, {720,750,860,800}, {830,780,790,700,}, {400,380,390,450}};
		String[] rowKeys = {"苹果", "香蕉", "橘子", "梨子"};
		String[] columnKeys = {"鹤壁","西安","深圳","北京"};
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);

		ChartUtilities.saveChartAsPNG(new File("D:/bar_v.png"), createVerticalBar(dataset), 420, 300);
		ChartUtilities.saveChartAsPNG(new File("D:/bar_h.png"), createHorizontalBar(dataset), 650, 400);
		
		// String filename = ServletUtilities.saveChartAsPNG(chart, 420, 300,
		// null, session);
		// String graphURL = request.getContextPath() +
		// "/DisplayChart?filename=" + filename;
	}
}
