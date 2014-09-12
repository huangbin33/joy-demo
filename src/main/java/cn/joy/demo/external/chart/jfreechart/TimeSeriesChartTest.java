package cn.joy.demo.external.chart.jfreechart;

import java.awt.Font;
import java.io.File;
import java.util.Calendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

public class TimeSeriesChartTest {
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
		
		TimeSeries timeSeries = new TimeSeries("某网站访问量统计");

		// 时间曲线数据集合
		TimeSeriesCollection lineDataset = new TimeSeriesCollection();

		// 构造数据集合
		timeSeries.add(new Month(1, 2010), 1100);
		timeSeries.add(new Month(2, 2010), 1200);
		timeSeries.add(new Month(3, 2010), 1000);
		timeSeries.add(new Month(4, 2010), 900);
		timeSeries.add(new Month(5, 2010), 1000);
		timeSeries.add(new Month(6, 2010), 1200);
		timeSeries.add(new Month(7, 2010), 1300);
		timeSeries.add(new Month(8, 2010), 1400);
		timeSeries.add(new Month(9, 2010), 1200);
		timeSeries.add(new Month(10, 2010), 1500);
		timeSeries.add(new Month(11, 2010), 1600);
		timeSeries.add(new Month(12, 2010), 1300);
		lineDataset.addSeries(timeSeries);

		JFreeChart chart = ChartFactory.createTimeSeriesChart("访问量统计时间线", "月份", "访问量", lineDataset, true, true, true);
		// 设置主标题
		chart.setTitle(new TextTitle("某网站访问量统计", new Font("隶书", Font.ITALIC, 15)));
		// 设置子标题
		TextTitle subtitle = new TextTitle("2010年度", new Font("黑体", Font.BOLD, 12));
		chart.addSubtitle(subtitle);
		chart.setAntiAlias(true);

		XYPlot plot = (XYPlot) chart.getPlot();
		// 设置时间轴的范围。
		DateAxis dateaxis = (DateAxis) plot.getDomainAxis();
		// dateaxis.setDateFormatOverride(new java.text.SimpleDateFormat("M月"));
		dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 1, new java.text.SimpleDateFormat("M月")));

		Calendar date = Calendar.getInstance();
		date.set(2009, 11, 1);

		Calendar mdate = Calendar.getInstance();
		mdate.set(2010, 11, 30);

		dateaxis.setRange(date.getTime(), mdate.getTime());

		// 设置最大坐标范围
		ValueAxis axis = plot.getRangeAxis();
		axis.setRange(800, 1800);
		plot.setRangeAxis(axis);
		// 设置曲线图与xy轴的距离 [上,左,下,右]
		plot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 12D));
		// 设置曲线是否显示数据点
		XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) plot.getRenderer();
		xylinerenderer.setBaseShapesVisible(true);
		// 设置曲线显示各数据点的值
		XYItemRenderer xyitem = plot.getRenderer();
		xyitem.setBaseItemLabelsVisible(true);
		xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
				TextAnchor.BASELINE_CENTER));
		xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 12));
		plot.setRenderer(xyitem);

		ChartUtilities.saveChartAsPNG(new File("D:/timeseries.png"), chart, 650, 400);
	}
}
