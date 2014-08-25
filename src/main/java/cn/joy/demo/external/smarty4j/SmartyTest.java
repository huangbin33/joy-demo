package cn.joy.demo.external.smarty4j;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.Engine;
import org.lilystudio.smarty4j.Template;

public class SmartyTest {
	public static void main(String[] args) throws Exception{
		Engine engine = new Engine();//加载模板引擎
		engine.setTemplatePath("src/");

		Template template = engine.getTemplate("test.tpl"); //打开模板文件
		Context context = new Context(); // 生成数据容器对象

		//这里需要设置数据的值
		List list = new ArrayList();
		list.add(1);
		list.add(3);
		list.add(5);
		context.set("aaa",123);
		context.set("list",list);
		ByteArrayOutputStream out = new ByteArrayOutputStream(); //设置接收模板数据的输出流
		template.merge(context, out); // 处理生成结果
		System.out.println(out.toString());
	}
}
