package cn.joy.demo.external.chart.jfreechart.creator;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

public class LineChartCreator extends CategoryChartCreator {

	public JFreeChart create2D() {
		JFreeChart chart = ChartFactory.createLineChart(this.title,
				this.categoryLabel, this.valueLabel, dataset,
				PlotOrientation.VERTICAL, true, true, false);

		// 使用CategoryPlot设置各种参数。以下设置可以省略。
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		// 背景色 透明度
		plot.setBackgroundAlpha(0.5f);
		// 前景色 透明度
		plot.setForegroundAlpha(0.5f);
		// 其他设置 参考 CategoryPlot类
		plot.setBackgroundPaint(Color.white);
		/*
		 * NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		 * rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		 * // 是否显示零点 rangeAxis.setAutoRangeIncludesZero(false); // 数据轴上（右）边距
		 * rangeAxis.setUpperMargin(0.02); // Y轴名称的倾斜角度
		 * rangeAxis.setLabelAngle(0.3); // 设置数据轴的数据范围 rangeAxis.setRange(new
		 * Range(0, 1000)); // 设置刻度波动值(步长) rangeAxis.setTickUnit(new
		 * NumberTickUnit(102));
		 */

		// 拆线线设置
		// 显示数据

		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();
		renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator()); // 设置曲线样式
		renderer.setItemLabelsVisible(true);
		renderer.setShapesVisible(true);

		return chart;
	}

	public JFreeChart create3D() {
		JFreeChart chart = ChartFactory.createLineChart3D(this.title,
				this.categoryLabel, this.valueLabel, dataset,
				PlotOrientation.VERTICAL, true, true, false);

		// 使用CategoryPlot设置各种参数。以下设置可以省略。
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		// 背景色 透明度
		plot.setBackgroundAlpha(0.5f);
		// 前景色 透明度
		plot.setForegroundAlpha(0.5f);
		// 其他设置 参考 CategoryPlot类
		plot.setBackgroundPaint(Color.white);

		return chart;
	}

}
