package cn.joy.demo.external.velocity;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VmCheck {
	public static void main(String[] args) throws Exception {
		// 配置初始化参数
		Properties props = new Properties();
		props.setProperty(Velocity.INPUT_ENCODING, "GBK");
		// props.setProperty(Velocity.RESOURCE_LOADER, "class");
		// props.setProperty("class.resource.loader.class",
		// "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		props.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		props.setProperty("file.resource.loader.path", "src/");

		/*
		 * props.setProperty("file.resource.loader.description",
		 * "Velocity File Resource Loader"); props
		 * .setProperty("file.resource.loader.class"
		 * ,"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		 * props.setProperty("file.resource.loader.path",
		 * velocityTemplateVMPath);
		 * props.setProperty("file.resource.loader.cache", "true");
		 * props.setProperty
		 * ("file.resource.loader.modificationCheckInterval","1");
		 * props.setProperty("input.encoding", "GBK");
		 * props.setProperty("output.encoding", "GBK");
		 * props.setProperty("directive.foreach.counter.name", "velocityCount");
		 * props.setProperty("directive.foreach.counter.initial.value", "0");
		 * props.setProperty("velocimacro.library", "base.vm");
		 * props.setProperty("velocimacro.library.autoreload", "true");
		 * props.setProperty("velocimacro.permissions.allow.inline.local.scope",
		 */

		// 初始化并取得Velocity引擎
		VelocityEngine ve = new VelocityEngine();
		ve.init(props);

		// 取得velocity的模版
		Template template = ve.getTemplate("check.vm");

		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();

		// 把数据填入上下文
		// context.put("util", new
		// com.actiz.platform.platform.formdatafacility.vm2.VelocityUtil(""));
		context.put("owner", "Unmi");
		List list = new ArrayList();
		
		Map m = new HashMap();
		m.put("c", 3);
		m.put("b", "abc");
		context.put("list", list);
		context.put("lss", null);
		context.put("bill", new Object[] { true, 12 });
		context.put("type", "aas\ndfs");
		context.put("prop", 23423);
		context.put("mao", m);
		context.put("nn", null);
		context.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		// 输出流,你可以自由控制输出到哪，String、File、Socket 等
		Writer writer = new PrintWriter(System.out);

		// 转换输出
		template.merge(context, writer);
		writer.flush();
	}

}
