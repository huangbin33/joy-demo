package cn.joy.demo.external.loan;

import java.util.ArrayList;
import java.util.List;

import cn.joy.demo.external.chart.jfreechart.ChartKit;
import cn.joy.demo.external.chart.jfreechart.creator.AbstractChartCreator;
import cn.joy.framework.core.JoyParam;

import com.jfinal.plugin.activerecord.Record;

public class LoanChart {
	public static void outArray(Object[] arr){
		for(Object o:arr){
			if(o instanceof Object[])
				outArray((Object[])o);
			else
				System.out.print(o+",");
		}
		System.out.println();
	}
	public static void main(String[] args) throws Exception{
		AbstractChartCreator.baseDir = "D:/loan";
		
		int huankuanshuStart = 3;
		int huankuanshuStep = 3;
		int huankuanshuMax = 30;
		
		int yihuanqishuStart = 12;
		int yihuanqishuStep = 3;
		int yihuanqishuMax = 18;
		
		Object[] rows = new Object[(yihuanqishuMax-yihuanqishuStart)/yihuanqishuStep+1];
		for(int i=0;i<rows.length;i++){
			rows[i] = yihuanqishuStart + i*yihuanqishuStep;
		}
		outArray(rows);
		
		Object[] cols = new Object[(huankuanshuMax-huankuanshuStart)/huankuanshuStep+1];
		for(int i=0;i<cols.length;i++){
			cols[i] = huankuanshuStart + i*huankuanshuStep;
		}
		outArray(cols);
		
		Object[][] datas = new Object[rows.length*cols.length][3];
		
		for(int i=0;i<rows.length;i++){
			for(int j=0;j<cols.length;j++){
				datas[i*cols.length+j] = new Object[]{rows[i], cols[j], 
						LoanCaculator.caculateJieShengLiXi(1360000d, 6.55/100, 20*12, (Integer)rows[i], (Integer)cols[j]*10000)};
			}
		}
		
		List<Record> records = new ArrayList<Record>();
		for(int i=0;i<datas.length;i++){
			Record record = new Record();
			record.set("rowField", datas[i][0]);
			record.set("colField", datas[i][1]);
			record.set("valueField", datas[i][2]);
			records.add(record);
		}
		
		JoyParam dataParam = ChartKit.statDatas(records, "sum", "rowField", "", "colField", "", "valueField");
		
		String path = ChartKit.factory(ChartKit.Type.LINE).config(
				JoyParam.create().put("title", "提前还贷计算图")
					.put("categoryLabel", "提前还款额").put("valueLabel", "节省利息")
					.put("3D", false)
					.put("width", 2000).put("height", 900)
			).data(
				dataParam
			).create().toFile();
	
		System.out.println(path);
		
	}
}
