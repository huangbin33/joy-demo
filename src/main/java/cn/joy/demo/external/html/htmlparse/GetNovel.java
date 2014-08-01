package cn.joy.demo.external.html.htmlparse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.joy.framework.kits.FileKit;

public class GetNovel {
	private static String baseDir = "D:/novel/";
	private static String novelName = "超级强兵";

	public static void main(String[] args) throws Exception {
		String url = "http://dushuzhe.com/0/84/";

		Document doc = Jsoup.connect(url).get();

		Elements links = doc.select("a[href]");

		List<String> chapterUrls = new ArrayList();
		List<String> chapterTitles = new ArrayList();

		for (Element link : links) {
			String chapterUrl = link.attr("abs:href");
			if (Pattern.compile("http://dushuzhe.com/0/84/\\d+.html").matcher(chapterUrl).matches()) {
				chapterUrls.add(chapterUrl);
				chapterTitles.add(link.text());
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < chapterTitles.size(); i++) {
			getChapter(sb, chapterTitles.get(i), chapterUrls.get(i));
		}
		FileKit.writeInfoToFile(sb.toString(), baseDir+novelName+".txt");

		/*for (int i = 0; i < chapterTitles.size(); i++) {
			writeChapter(i+1, chapterTitles.get(i), chapterUrls.get(i));
		}*/
		
		
	}

	public static void writeChapter(int index, String title, String url) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(title).append("\r\n\r\n");

		Document doc = Jsoup.connect(url).get();

		Element contentDiv = doc.getElementById("content");
		contentDiv.child(0).remove();

		String content = contentDiv.html();
		content = content.replaceAll("<br />", "").replaceAll("&nbsp;", "");
		sb.append(content);
		//System.out.println(content);
		System.out.println("写入"+title+"......");
		FileKit.writeInfoToFile(sb.toString(), baseDir+"txt/"+novelName+String.format("%1$03d", index)+".txt");
	}
	
	public static void getChapter(StringBuilder sb, String title, String url) throws Exception {
		System.out.println("获取"+title+"......");
		sb.append(title).append("\r\n\r\n");

		Document doc = Jsoup.connect(url).get();

		Element contentDiv = doc.getElementById("content");
		contentDiv.child(0).remove();

		String content = contentDiv.html();
		content = content.replaceAll("<br />", "").replaceAll("&nbsp;", "");
		sb.append(content).append("\r\n\r\n");
	}

}
