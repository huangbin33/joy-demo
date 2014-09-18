package cn.joy.demo.external.chart.jfreechart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.joy.demo.external.chart.jfreechart.creator.AbstractChartCreator;
import cn.joy.demo.external.chart.jfreechart.creator.BarChartCreator;
import cn.joy.demo.external.chart.jfreechart.creator.LineChartCreator;
import cn.joy.demo.external.chart.jfreechart.creator.PieChartCreator;
import cn.joy.framework.core.JoyParam;
import cn.joy.framework.kits.DateKit;
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
	
	public static String handleKey(String key, String handleWay){
		if(handleWay.startsWith("d_")){
			String dateStr = DateKit.fillDateStr(key);
			try {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				
				if("d_year".equals(handleWay)){
					return String.format("%4d年", year);
				}else if("d_season".equals(handleWay)){
					return String.format("%4d年%d季度", year, (month-1)/3+1);
				}else if("d_month".equals(handleWay)){
					return String.format("%4d年%d月", year, month);
				}else if("d_day".equals(handleWay)){
					return new SimpleDateFormat("yyyy-MM-dd").format(date);
				}
			} catch (Exception e) {
				return "INVALID";
			}
		}
			
		return key;
	}
	
	public static void main(String[] args) throws Exception{
		AbstractChartCreator.baseDir = "D:/charts";
		
		Object[][] datas = {
			{"苹果", "2014-01-12 11:22:33", 1230},
			{"苹果", "2014-02-12 11:22", 1110},
			{"苹果", "2014-05-12 11", 1120},
			{"香蕉", "2014-06-12", 700},
			{"苹果", "2014-09-12", 1210},
			{"香蕉", "2014-01-12 11:22:33", 720},
			{"香蕉", "2014-02-12 11:22", 750},
			{"香蕉", "2014-05-12 11", 860},
			{"香蕉", "2014-09-12", 800},
			{"橘子", "2014-01-12 11:22:33", 830},
			{"橘子", "2014-02-12 11:22", 780},
			{"橘子", "2014-06-12 11:22", 580},
			{"橘子", "2014-05-12 11", 790},
			{"梨子", "2014-09-12", 450},
			{"橘子", "2014-09-12", 700},
			{"梨子", "2014-01-12 11:22:33", 400},
			{"梨子", "2014-02-12 11:22", 380},
			{"梨子", "2014-05-12 11", 390},
			{"梨子", "2014-09-12", 450},
			{"梨子", "2014-11-12", 350}
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
			String rowKey = handleKey(record.getStr("rowField"), "");
			if(!rowKeyList.contains(rowKey))
				rowKeyList.add(rowKey);
			
			String colKey = handleKey(record.getStr("colField"), "d_month");
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
					.put("width", 1000)
			).data(
				JoyParam.create().put("rowKeys", rowKeys).put("columnKeys", columnKeys)
					.put("data", data)
			).create().toFile();
		System.out.println(path);
		
		
	}
}
