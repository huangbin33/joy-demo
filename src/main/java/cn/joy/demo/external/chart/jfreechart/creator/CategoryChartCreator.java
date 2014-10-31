package cn.joy.demo.external.chart.jfreechart.creator;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import cn.joy.demo.external.chart.jfreechart.ChartCreator;
import cn.joy.framework.core.JoyParam;

public abstract class CategoryChartCreator extends AbstractChartCreator {
	protected CategoryDataset dataset;

	public ChartCreator data(JoyParam datas) {
		if(datas!=null){
			this.dataset = DatasetUtilities.createCategoryDataset(
					(String[])datas.get("rowKeys"), (String[])datas.get("columnKeys"),
					(double[][])datas.get("data"));
		}

		return this;
	}

}
