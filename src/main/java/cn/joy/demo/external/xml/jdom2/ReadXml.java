package cn.joy.demo.external.xml.jdom2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ReadXml {
	public static void main(String[] args) {
		SAXBuilder builder = new SAXBuilder();
		try {
			Map<String, String> map = new HashMap<>();
			
			Document doc = builder.build(new File("D:/strings.xml"));
			Element rootEl = doc.getRootElement();
			// 获得所有子元素
			List<Element> list = rootEl.getChildren();
			for (Element el : list) {
				// 获取name属性值
				String key = el.getAttributeValue("name");
				System.out.println("key:" + key);
				System.out.println("value:" + el.getText());
				map.put(key, el.getText());
				System.out.println("-----------------------------------");
			}
			
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			Document newDoc = new Document();
			Element root=new Element("resources");
			newDoc.setRootElement(root);
			
			for(Entry<String, String> entry:map.entrySet()){
				Element el=new Element("string");
				el.setAttribute("name", entry.getKey());
				el.setText(entry.getValue());
				root.addContent(el);
			}
			
			outputter.output(newDoc, new FileOutputStream("D:/strings2.xml"));
			//outputter.output(newDoc, new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/strings2.xml"), "UTF-8")));
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
