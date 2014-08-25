package cn.joy.demo.external.xml.jdom2;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class ReadByXPath {
	public static void main(String[] args) {
	    SAXBuilder builder = new SAXBuilder();
	    try {
	        Document doc = builder.build(new File("D:/test.xml"));
	        Element rootEl = doc.getRootElement();
	        //获得所有子元素
	        List<Element> list = rootEl.getChildren();
	        for (Element el : list) {
	        	//XPathExpression<Element> startDateExpression = 
	        		    //XPathFactory.instance().compile("//property", Filters.element(), null, new Namespace[]{});
	        	XPathExpression<Element> startDateExpression = XPathFactory.instance().compile("//property", Filters.element());

	        	List<Element> startdates = startDateExpression.evaluate(doc);
	        	System.out.println(startdates.size());
	            //获取name属性值
	           /* String name = el.getAttributeValue("item");
	            //获取子元素capacity文本值
	            String capacity = el.getChildText("capacity");
	            //获取子元素directories文本值
	            String directories = el.getChildText("directories");
	            String files = el.getChildText("files");
	            System.out.println("磁盘信息:");
	            System.out.println("分区盘符:" + name);
	            System.out.println("分区容量:" + capacity);
	            System.out.println("目录数:" + directories);
	            System.out.println("文件数:" + files);
	            System.out.println("-----------------------------------");*/
	        }
	    } catch (JDOMException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
