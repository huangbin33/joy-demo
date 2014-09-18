package cn.joy.demo.external.chart.jfreechart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.joy.demo.external.chart.jfreechart.creator.AbstractChartCreator;
import cn.joy.demo.external.chart.jfreechart.creator.BarChartCreator;
import cn.joy.demo.external.chart.jfreechart.creator.LineChartCreator;
import cn.joy.demo.external.chart.jfreechart.creator.PieChartCreator;
import cn.joy.framework.core.JoyParam;
import cn.joy.framework.kits.JsonKit;
import cn.joy.framework.kits.NumberKit;

import com.jfinal.plugin.activerecord.Record;

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
		
		Object[][] datas = {
			{"苹果", "鹤壁", 1230},
			{"苹果", "西安", 1110},
			{"苹果", "深圳", 1120},
			{"苹果", "北京", 1210},
			{"香蕉", "鹤壁", 720},
			{"香蕉", "西安", 750},
			{"香蕉", "深圳", 860},
			{"香蕉", "北京", 800},
			{"橘子", "鹤壁", 830},
			{"橘子", "西安", 780},
			{"橘子", "深圳", 790},
			{"梨子", "北京", 450},
			{"橘子", "北京", 700},
			{"梨子", "鹤壁", 400},
			{"梨子", "西安", 380},
			{"梨子", "深圳", 390},
			{"梨子", "北京", 450}
		};
		
		List<Record> records = new ArrayList();
		for(int i=0;i<datas.length;i++){
			Record record = new Record();
			record.set("rowField", datas[i][0]);
			record.set("colField", datas[i][1]);
			record.set("valueField", datas[i][2]);
			records.add(record);
		}
		
		String statWays = "sum";	//sum count
		List<String> rowKeyList = new ArrayList();
		List<String> colKeyList = new ArrayList();
		Map<String, Double> datasMap = new HashMap<String, Double>();
		
		for(Record record:records){
			String rowKey = record.getStr("rowField");
			if(!rowKeyList.contains(rowKey))
				rowKeyList.add(rowKey);
			
			String colKey = record.getStr("colField");
			if(!colKeyList.contains(colKey))
				colKeyList.add(colKey);
			
			String datasMapKey = rowKey+"__DSP__"+colKey;
			double val = NumberKit.getDouble(datasMap.get(datasMapKey), 0d);
			if("sum".equals(statWays)){
				Double value = NumberKit.getDouble(record.get("valueField"));
				if(value==null)
					continue;
				datasMap.put(datasMapKey, val+value);
			}else if("count".equals(statWays))
				datasMap.put(datasMapKey, val+1);
		}
		
		String[] rowKeys = rowKeyList.toArray(new String[0]);
		String[] columnKeys = colKeyList.toArray(new String[0]);
		
		double[][] data = new double[rowKeys.length][columnKeys.length];
		for(int i=0;i<rowKeys.length;i++){
			for(int j=0;j<columnKeys.length;j++){
				data[i][j] = NumberKit.getDouble(datasMap.get(rowKeys[i]+"__DSP__"+columnKeys[j]), 0d);
			}
		}
		
		//double[][] data = new double[][] {{1230,1110,1120,1210}, {720,750,860,800}, {830,780,790,700,}, {400,380,390,450}};
		//String[] rowKeys = {"苹果", "香蕉", "橘子", "梨子"};
		//String[] columnKeys = {"鹤壁","西安","深圳","北京"};
		
		//Bar
		String path = ChartKit.factory(ChartKit.Type.BAR).config(
				JoyParam.create().put("title", "水果销量统计图")
					.put("categoryLabel", "水果").put("valueLabel", "销量")
			).data(
				JoyParam.create().put("rowKeys", rowKeys).put("columnKeys", columnKeys)
					.put("data", data)
			).create().toFile();
		System.out.println(path);
		
		
	}
}
