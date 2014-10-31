package cn.joy.demo.external.chart.jfreechart.creator;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;

import cn.joy.demo.external.chart.jfreechart.ChartCreator;
import cn.joy.framework.core.JoyParam;
import cn.joy.framework.kits.PathKit;
import cn.joy.framework.kits.StringKit;

public abstract class AbstractChartCreator implements ChartCreator {
	protected String user;
	protected String company;
	protected boolean is3D = false;
	protected String title;
	protected String categoryLabel;
	protected String valueLabel;
	
	protected JFreeChart chart;
	protected int width = 600;
	protected int height = 400;
	
	public static String baseDir = PathKit.getWebRootPath()+"/WEB-INF/chart_temp";
	
	public ChartCreator config(JoyParam configs) {
		if(configs!=null){
			this.user = configs.getString("user");
			this.company = configs.getString("company");
			this.is3D = configs.getBoolean("3D", true);

			this.title = configs.getString("title");
			this.categoryLabel = configs.getString("categoryLabel");
			this.valueLabel = configs.getString("valueLabel");
			
			this.width = configs.getInt("width", 600);
			this.height = configs.getInt("height", 600);
			
			configOther(configs);
		}
		
		return this;
	}
	
	protected ChartCreator configOther(JoyParam configs) {
		return this;
	}

	public abstract ChartCreator data(JoyParam datas);

	public ChartCreator create() {
		setBeforeCreate();

		if (is3D)
			this.chart = create3D();
		else
			this.chart = create2D();
		return this;
	}

	protected void setBeforeCreate() {
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
	}

	protected abstract JFreeChart create2D();

	protected abstract JFreeChart create3D();

	public String toFile() throws IOException{
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH)+1;
		
		// yyyyMM/company/chart_time_xxxx.png
		String path = String.format("%4d%02d/%s/chart_%d_%04d.png", year, month, 
				StringKit.getString(this.company, "other"), today.getTimeInMillis(), 
				new Random().nextInt(10000));
		
		File file = new File(baseDir+"/"+path);
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		ChartUtilities.saveChartAsPNG(file, this.chart, this.width, this.height);
		return path;
	}

}
