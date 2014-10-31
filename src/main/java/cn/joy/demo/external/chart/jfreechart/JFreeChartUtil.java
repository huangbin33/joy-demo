package cn.joy.demo.external.chart.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;

public class JFreeChartUtil {

	/* 折线图样式 */
	public static void timeSeriesStyle(JFreeChart chart) {
		XYPlot plot = chart.getXYPlot();
		/* 设置曲线显示各数据点的值 */
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) plot.getRenderer();
		// 设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		// 设置曲线图与xy轴的距离
		plot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 10D));
		// 设置曲线是否显示数据点
		xylineandshaperenderer.setBaseShapesVisible(true);
		// 设置曲线显示各数据点的值
		XYItemRenderer xyitem = plot.getRenderer();
		// xyitem.setBaseItemLabelsVisible(true);
		xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
				TextAnchor.BASELINE_LEFT));
		xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 14));
		plot.setRenderer(xyitem);

		// 中文处理乱码
		Font xfont = new Font("宋体", Font.PLAIN, 12);// X轴
		Font yfont = new Font("宋体", Font.PLAIN, 12);// Y轴
		// X 轴
		ValueAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(xfont);// 轴标题
		domainAxis.setTickLabelFont(xfont);// 轴数值
		domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色
		// Y 轴
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(yfont);
		rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色
		rangeAxis.setTickLabelFont(yfont);

		// 底部
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
	}

	/* 饼图样式 */
	public static void piePlot3DStyle(JFreeChart chart) {
		PiePlot3D plot = (PiePlot3D) chart.getPlot();

		plot.setLabelFont(new Font("宋体", 0, 12));
		// 图片中显示百分比:默认方式
		// plot.setLabelGenerator(new
		// StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		// 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
		// 设置背景色为白色
		chart.setBackgroundPaint(Color.white);

		// 指定图片的透明度(0.0-1.0)
		// plot.setForegroundAlpha(1.0f);
		// 设置透明度，0.5F为半透明，1为不透明，0为全透明
		plot.setForegroundAlpha(0.5F);
		// 指定显示的饼图上圆形(false)还椭圆形(true)
		plot.setCircular(true);
		// 设置开始角度
		plot.setStartAngle(40D);
		// 设置方向为”顺时针方向“
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setInteriorGap(0.0D);// [7]
		// 没有数据的时候显示的内容
		plot.setNoDataMessage("无数据显示");
		plot.setNoDataMessageFont(new Font("宋体", 0, 12));
		plot.setLabelGap(0.02D);
		// 设置饼图背景色
		plot.setBackgroundPaint(Color.white);

	}

	/* 柱状图样式 */
	public static void barChart3DStyle(JFreeChart chart) {
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		CategoryAxis domainAxis = plot.getDomainAxis();
		/*------设置X轴坐标上的文字-----------*/
		// domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 11));
		/*------设置X轴的标题文字------------*/
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));

		/*------设置Y轴坐标上的文字-----------*/
		// numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		numberaxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
		/*------设置Y轴的标题文字------------*/
		numberaxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));

		/*------这句代码解决了底部汉字乱码的问题-----------*/
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

		// 设置网格背景颜色
		plot.setBackgroundPaint(Color.white);

		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);

		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);

		// 显示每个柱的数值，并修改该数值的字体属性
		BarRenderer3D renderer = new BarRenderer3D();

		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		// 设置 底部分类 不显示
		renderer.setBaseSeriesVisibleInLegend(false);
		// 默认的数字显示在柱子中，通过如下两句可调整数字的显示
		// 注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题

		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
				TextAnchor.BASELINE_LEFT));

		renderer.setItemLabelAnchorOffset(10D);

		renderer.setItemLabelFont(new Font("宋体", Font.PLAIN, 12));

		renderer.setItemLabelsVisible(true);

		// 设置每个地区所包含的平行柱的之间距离

		renderer.setItemMargin(0.3);

		plot.setRenderer(renderer);

		// 设置地区、销量的显示位置
		// 将下方的“年”放到上方
		// plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		// 将默认放在左边的“人数”放到右方
		// plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
	}
}
