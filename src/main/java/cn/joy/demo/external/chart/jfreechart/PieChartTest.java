package cn.joy.demo.external.chart.jfreechart;

import java.awt.Font;
import java.io.File;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class PieChartTest {
	private static JFreeChart create2DChart(PieDataset dataset) {
		// 通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createPieChart("非正常死亡人数分布图", dataset, true, true, false);
		// 加个副标题
		chart.addSubtitle(new TextTitle("2010年度"));
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

	private static JFreeChart create3DChart(PieDataset dataset) {
		// 通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createPieChart3D("非正常死亡人数分布图", dataset, true, true, false);
		// 加个副标题
		chart.addSubtitle(new TextTitle("2010年度"));
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

		// 设置饼图数据集
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("黑心矿难", 720);
		dataset.setValue("醉酒驾驶", 530);
		dataset.setValue("城管强拆", 210);
		dataset.setValue("医疗事故", 91);
		dataset.setValue("其他", 66);

		ChartUtilities.saveChartAsPNG(new File("D:/pie.png"), create2DChart(dataset), 650, 400);
		ChartUtilities.saveChartAsPNG(new File("D:/pie_3D.png"), create3DChart(dataset), 650, 400);

	}
}
