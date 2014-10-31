package cn.joy.demo.external.chart.jfreechart;

import java.io.IOException;

import cn.joy.framework.core.JoyParam;

public interface ChartCreator {
	ChartCreator config(JoyParam configs);

	ChartCreator data(JoyParam datas);

	ChartCreator create();

	String toFile() throws IOException;
}
