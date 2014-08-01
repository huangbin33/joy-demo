package cn.joy.demo.external.freemarker;

import java.io.*;
import java.util.*;

import freemarker.template.*;

public class FreemarkerTest {
	public static void main(String[] args) throws Exception {
        
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("src/main/java/com/joy/demo/freemarker"));
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        Template temp = cfg.getTemplate("test.ftl");

        Map root = new HashMap();
        root.put("user", "Big Joe");
        Map latest = new HashMap();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "true");
        
        Map params = new HashMap();
        root.put("params", latest);

        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        out.flush();
    }

}
