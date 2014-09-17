package cn.joy.demo.external.chart.jfreechart;

import cn.joy.demo.external.chart.jfreechart.creator.AbstractChartCreator;
import cn.joy.demo.external.chart.jfreechart.creator.BarChartCreator;
import cn.joy.demo.external.chart.jfreechart.creator.LineChartCreator;
import cn.joy.demo.external.chart.jfreechart.creator.PieChartCreator;
import cn.joy.framework.core.JoyParam;

public class ChartKit {
	public static enum Type{
		BAR, PIE, LINE
	}
	
	public static ChartCreator factory(Type type){
		return factory(type, null, null);
	}
	
	public static ChartCreator factory(Type type, JoyParam configs){
		return factory(type, configs, null);
	}
	
	public static ChartCreator factory(Type type, JoyParam configs, JoyParam datas){
		ChartCreator creator = null;
		switch(type){
			case BAR:
				creator =  new BarChartCreator();
				break;
			case PIE:
				creator =  new PieChartCreator();
				break;
			case LINE:
				creator =  new LineChartCreator();
				break;
			default:
				throw new RuntimeException("No ChartCreator for "+type);
		}
		
		return creator.config(configs).data(datas);
	}
	
	public static void main(String[] args) throws Exception{
		AbstractChartCreator.baseDir = "D:/charts";
		//Bar
		double[][] data = new double[][] {{1230,1110,1120,1210}, {720,750,860,800}, {830,780,790,700,}, {400,380,390,450}};
		String[] rowKeys = {"苹果", "香蕉", "橘子", "梨子"};
		String[] columnKeys = {"鹤壁","西安","深圳","北京"};
		
		String path = ChartKit.factory(ChartKit.Type.BAR).config(
				JoyParam.create().put("title", "水果销量统计图")
					.put("categoryLabel", "水果").put("valueLabel", "销量")
			).data(
				JoyParam.create().put("rowKeys", rowKeys).put("columnKeys", columnKeys)
					.put("data", data)
			).create().toFile();
		System.out.println(path);
		
		//Line
		rowKeys = new String[]{ "平板电视", "笔记本电脑", "手机" };      
		columnKeys = new String[]{ "1月", "2月", "3月", "4月", "5月", "6月" };      
     
        data = new double[][] { { 50, 20, 30, 25, 35, 40 }, { 20, 10D, 40D, 32, 42, 55 },      
                { 40, 35, 38, 22, 31, 39 }}; 
        
        path = ChartKit.factory(ChartKit.Type.LINE).config(
				JoyParam.create().put("title", "产品销量分布图")
					.put("categoryLabel", "月份").put("valueLabel", "销量")
					.put("3D", false)
			).data(
				JoyParam.create().put("rowKeys", rowKeys).put("columnKeys", columnKeys)
					.put("data", data)
			).create().toFile();
		System.out.println(path);
	}
}
