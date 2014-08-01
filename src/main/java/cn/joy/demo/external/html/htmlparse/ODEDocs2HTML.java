package cn.joy.demo.external.html.htmlparse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ODEDocs2HTML {
	private static String charSet = "GBK";
	private static String chapterReg = "h\\d+";
	private static Map<String, File> dirMap;
	private static File rootDir;
	private static File currentDir;
	private static Document currentDocument;
	private static Document indexDocument;
	
	static{
		dirMap = new HashMap();
		rootDir = new File("D:/ODE6.0 Docs/");
		currentDir = rootDir;

		indexDocument = Document.createShell("");
		indexDocument.title("Index");
		indexDocument.head().append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="+charSet+"\"/>")
			.append("<link rel=stylesheet type=text/css href=\"style/doc.css\"></link>");
		indexDocument.body().append("<div class='docIndex'><div style='border:none;border-bottom:solid #4F81BD 1.0pt;padding:0cm 0cm 4.0pt 0cm'>")
		.append("<p class=MsoTitleCxSpFirst style='text-indent:53.0pt'><span lang=EN-US>&nbsp;</span></p>")
		.append("<p class=MsoTitleCxSpMiddle style='text-indent:53.0pt'><span lang=EN-US>&nbsp;</span></p>")
		.append("<p class=MsoTitleCxSpMiddle style='text-indent:53.0pt'><span lang=EN-US>&nbsp;</span></p>")
		.append("<p class=MsoTitleCxSpMiddle style='text-indent:53.0pt'><span lang=EN-US>&nbsp;</span></p>")
		.append("<p class=MsoTitleCxSpLast style='text-indent:53.0pt'><span lang=EN-US>ODE.6.0 用户手册</span> </p></div>")
		.append("<p class=MsoNormal style='text-indent:22.0pt;line-height:20.0pt;layout-grid-mode:char'><span lang=EN-US>&nbsp;</span></p>")
		.append("<p class=MsoNormal style='text-indent:22.0pt;line-height:20.0pt;layout-grid-mode:char'><span lang=EN-US>&nbsp;</span></p>")
		.append("<p class=MsoNormal style='text-indent:21.0pt;line-height:20.0pt;layout-grid-mode:char'><span lang=EN-US>No part of this")
		.append("document may be reproduced, stored in any electronic retrieval system, or")
		.append("transmitted in any Form or by any means, mechanical, photocopying, recording,")
		.append("otherwise, without the written permission of the copyright owner. </span></p>");
		for(int i=0;i<7;i++){
			indexDocument.body().append("<p class=MsoNormal style='text-indent:22.0pt;line-height:20.0pt;layout-grid-mode:char'><span lang=EN-US>&nbsp;</span></p>");
		}
		indexDocument.body().append("<p class=MsoNormal align=center style='text-align:center;text-indent:20.0pt'><b><span lang=EN-US style='font-size:10.0pt;line-height:115%;'>COPYRIGHT 2012 by ShangHai Actiz Software Technical co.Ltd.</span></b></p>")
		.append("<p class=MsoNormal align=center style='text-align:center;text-indent:20.0pt'><b><span lang=EN-US style='font-size:10.0pt;line-height:115%;'>ALL RIGHTS RESERVED.</span></b></p>")
		.append("<b><span lang=EN-US style='font-size:11.0pt;line-height:115%;'><br clear=all style='page-break-before:always'>")
		.append("<br clear=all style='page-break-before:always'></span></b><p class=MsoNormal><b><span lang=EN-US>&nbsp;</span></b></p>")
		.append("<p class=MsoTocHeading><span>目录</span></p></div>");
	}
	
	public static void main(String[] args) throws Exception {
		File input = new File("D:/ODE用户手册.htm");
		Document doc = Jsoup.parse(input, "GBK");

		Elements els = doc.body().child(0).children();
		for (Element el : els) {
			String tagName = el.tagName();
			if(tagName.matches(chapterReg)){
				if(currentDocument!=null){
					createFile(currentDocument);
					currentDocument = null;
				}
				if(el.nextElementSibling()==null||el.nextElementSibling().tagName().matches(chapterReg))
					currentDir = createDir(el);
				else
					currentDocument = createDocument(el);
			}else{
				if(currentDocument!=null)
					currentDocument.body().appendChild(el);
			}
		}
		if(currentDocument!=null){
			createFile(currentDocument);
			currentDocument = null;
		}
		
		createIndexFile();
	}
	
	public static void createIndexFile(){
		try {
			String indexFilePath = rootDir.getAbsolutePath()+"/docIndex.html";
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(indexFilePath), charSet));
			writer.write(indexDocument.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createFile(Document doc){
		String[] textArr = doc.title().split("\\s+");
		String seqNum = textArr[0].trim().replace((char)160+"", "");
		if(seqNum.endsWith(".")){
			seqNum = seqNum.substring(0, seqNum.length()-1);
		}
		int idx = seqNum.lastIndexOf(".");
		File parentDir = null;
		if(idx!=-1){
			String parentSeqNum = seqNum.substring(0, idx);
			parentDir = dirMap.get(parentSeqNum);
		}
		if(parentDir==null)
			parentDir = rootDir;
		
		String fileName = doc.title().replace("/", "、");
		String filePath = parentDir+"/"+fileName+".html";
		try {
			rebuildDocument(doc);
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), charSet));
			writer.write(doc.toString());
			int dirLevel = getDirLevel(seqNum);
			indexDocument.body().append("<p class=\"MsoToc"+(dirLevel+1)+"\"><span lang=\"EN-US\"><a href=\""+filePath.replace("\\", "/").replace(rootDir.getAbsolutePath(), "")+"\">"+fileName+"</a></span></p>\n");
			out("创建HTML："+filePath);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//FileUtil.writeInfoToFile(doc.toString(), currentDir+"/"+doc.title()+".html");
	}
	
	public static File createDir(Element el){
		String[] textArr = el.text().split("\\s+");
		String seqNum = textArr[0].trim().replace((char)160+"", "");
		if(seqNum.endsWith(".")){
			seqNum = seqNum.substring(0, seqNum.length()-1);
		}
		String titleText = textArr[1].trim().replace((char)160+"", "").replace("/", "、");
		int idx = seqNum.lastIndexOf(".");
		File parentDir = null;
		if(idx!=-1){
			String parentSeqNum = seqNum.substring(0, idx);
			parentDir = dirMap.get(parentSeqNum);
		}
		if(parentDir==null)
			parentDir = rootDir;

		File dir = new File(parentDir.getAbsolutePath()+"/"+seqNum+" "+titleText);
		if(!dir.exists()){
			boolean result = dir.mkdir();
			int dirLevel = getDirLevel(seqNum);
			indexDocument.body().append("<p class=\"MsoToc"+(dirLevel+1)+"\"><span lang=\"EN-US\">"+dir.getName()+"</span></p>\n");
			out("创建目录："+dir.getAbsolutePath()+", result="+result);
		}
		dirMap.put(seqNum, dir);
		return dir;
	}
	
	public static Document createDocument(Element el){
		String[] textArr = el.text().split("\\s+");
		String seqNum = textArr[0].trim().replace((char)160+"", "");
		if(seqNum.endsWith(".")){
			seqNum = seqNum.substring(0, seqNum.length()-1);
		}
		String titleText = textArr[1].trim().replace((char)160+"", "");
		
		Document doc = Document.createShell("");
		doc.title(seqNum+" "+titleText);
		int dirLevel = getDirLevel(seqNum);
		String relativeDir = "";
		for(int i=0;i<dirLevel;i++){
			relativeDir += "../";
		}
		doc.head().append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="+charSet+"\"/>")
			.append("<link rel=stylesheet type=text/css href=\""+relativeDir+"style/doc.css\"></link>");
		return doc;
	}
	
	public static void rebuildDocument(Document doc){
		Elements imgs = doc.select("img[src]");
		for(Element img:imgs){
			String imgUrl = img.attr("src");
			int idx = imgUrl.lastIndexOf("/");
			if(idx!=-1){
				int dirLevel = getDirLevel(doc.title());
				String relativeDir = "";
				for(int i=0;i<dirLevel;i++){
					relativeDir += "../";
				}
				img.attr("src", relativeDir+"imgs/"+imgUrl.substring(idx+1));
				Element pEl = getParentElement(img, "p");
				if(pEl!=null&&pEl.child(0)==img.parent())
					pEl.addClass("p-img");
			}
		}
		
		Elements tables = doc.select("table");
		for(Element table:tables){
			table.attr("align", "center");
			table.attr("width", "100%");
			table.removeAttr("style");
		}
	}
	
	private static Element getParentElement(Element el, String tagName){
		if(el!=null){
			Element parent = el.parent();
			if(parent!=null&&parent!=el){
				if(tagName.equals(parent.tagName()))
					return parent;
				else
					return getParentElement(parent, tagName);
			}
		}
		return null;
	}
	
	private static int getDirLevel(String seqNum){
		seqNum = seqNum.split(" ")[0];
		return seqNum.split("\\.").length-1;
	}
	
	private static void out(Object obj){
		if(obj instanceof Object[]){
			for(Object o:(Object[])obj){
				System.out.print(o+",");
			}
			System.out.println();
		}else if(obj instanceof List){
			for(Object o:(List)obj){
				System.out.println(o+",");
			}
			System.out.println();
		}else
			System.out.println(obj);
	}
}
