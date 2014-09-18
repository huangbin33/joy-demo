package cn.joy.demo.external.chart.jfreechart.creator;

import java.awt.Font;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import cn.joy.demo.external.chart.jfreechart.ChartCreator;
import cn.joy.framework.core.JoyParam;

public class PieChartCreator extends AbstractChartCreator {
	protected PieDataset dataset;

	public ChartCreator data(JoyParam datas) {
		if (datas != null) {
			CategoryDataset cd = DatasetUtilities.createCategoryDataset((String[]) datas.get("rowKeys"),
					(String[]) datas.get("columnKeys"), (double[][]) datas.get("data"));

			/*
			 * DefaultPieDataset dataset = new DefaultPieDataset();
			 * dataset.setValue("", 720);
			 */
			this.dataset = DatasetUtilities.createPieDatasetForRow(cd, 0);
		}

		return this;
	}

	protected JFreeChart create2D() {
		// 通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createPieChart(this.title, dataset, true, true, false);
		
		PiePlot pieplot = (PiePlot) chart.getPlot();
		pieplot.setLabelFont(new Font("宋体", 0, 11));
		// 设置饼图是圆的（true），还是椭圆的（false）；默认为true
		pieplot.setCircular(true);
		StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}:({1},{2})",
				NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
		pieplot.setLabelGenerator(standarPieIG);

		// 没有数据的时候显示的内容
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setLabelGap(0.02D);

		return chart;
	}

	protected JFreeChart create3D() {
		// 通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createPieChart3D(this.title, dataset, true, true, false);
		// 加个副标题
		// chart.addSubtitle(new TextTitle("2010年度"));
		PiePlot pieplot = (PiePlot) chart.getPlot();
		pieplot.setLabelFont(new Font("宋体", 0, 11));
		StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}:({1},{2})",
				NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
		pieplot.setLabelGenerator(standarPieIG);

		// 没有数据的时候显示的内容
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setLabelGap(0.02D);

		PiePlot3D pieplot3d = (PiePlot3D) chart.getPlot();
		// 设置开始角度
		pieplot3d.setStartAngle(120D);
		// 设置方向为”顺时针方向“
		pieplot3d.setDirection(Rotation.CLOCKWISE);
		// 设置透明度，0.5F为半透明，1为不透明，0为全透明
		pieplot3d.setForegroundAlpha(0.7F);
		return chart;
	}

}
