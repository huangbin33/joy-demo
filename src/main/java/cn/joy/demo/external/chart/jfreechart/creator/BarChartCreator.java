package cn.joy.demo.external.chart.jfreechart.creator;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.ui.TextAnchor;

public class BarChartCreator extends CategoryChartCreator {

	public JFreeChart create2D() {
		JFreeChart chart = ChartFactory.createBarChart(this.title, this.categoryLabel, this.valueLabel, 
				dataset, PlotOrientation.VERTICAL, true, false, false);
		
		CategoryPlot plot = chart.getCategoryPlot(); 
		//设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		//设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.blue);
		//设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		 
		//显示每个柱的数值，并修改该数值的字体属性
		BarRenderer renderer = new BarRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		 
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);
		 
		//设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0.4);
		plot.setRenderer(renderer);
		 
		return chart;
	}
	
	public JFreeChart create3D() {
		JFreeChart chart = ChartFactory.createBarChart3D(this.title, this.categoryLabel, this.valueLabel, 
				dataset, PlotOrientation.VERTICAL, true, false, false);
		
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
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);
		 
		//设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0.4);
		plot.setRenderer(renderer);
		 
		return chart;
	}

}
