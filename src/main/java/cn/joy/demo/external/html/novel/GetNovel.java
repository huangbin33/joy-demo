package cn.joy.demo.external.html.novel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.joy.framework.kits.FileKit;
import cn.joy.framework.kits.HttpKit;

public class GetNovel {
	private static String baseDir = "D:/novel/";
	private static String novelName = "首席御医";
	private static StringBuilder errorInfo = new StringBuilder();
	
	public static void main(String[] args) {
		main3();
	}
	
	public static void main1() {
		String c = getChapterContent("http://tieba.baidu.com/p/1774387477");
		FileKit.writeInfoToFile(c, "D:/test.txt");
	}
	
	public static void main3() {
		Map<Integer, String[]> chapters = new HashMap();
		
		getChapters(chapters, "http://tieba.baidu.com/p/1774387477");
		
		//getChaptersInList(chapters, "http://tieba.baidu.com/f/good?kw=%B3%AC%BC%B6%C7%BF%B1%F8&cid=1&tp=0&pn=150");
		writeChapterUrls(chapters);
		
	}
	
	public static void main2() throws Exception{
		Map<Integer, String[]> chapters = new HashMap();
		
		getChapters(chapters, "http://tieba.baidu.com/p/1314402744");
		getChapters(chapters, "http://tieba.baidu.com/p/1439029644");
		getChapters(chapters, "http://tieba.baidu.com/p/1314402744?pn=2");
		getChapters(chapters, "http://tieba.baidu.com/p/1314402744?pn=3");
		
		getChaptersInList(chapters, "http://tieba.baidu.com/f/good?kw=%B3%AC%BC%B6%C7%BF%B1%F8&cid=1&tp=0&pn=150");
		getChaptersInList(chapters, "http://tieba.baidu.com/f/good?kw=%B3%AC%BC%B6%C7%BF%B1%F8&cid=1&tp=0&pn=100");
		getChaptersInList(chapters, "http://tieba.baidu.com/f/good?kw=%B3%AC%BC%B6%C7%BF%B1%F8&cid=1&tp=0&pn=50");
		getChaptersInList(chapters, "http://tieba.baidu.com/f/good?kw=%B3%AC%BC%B6%C7%BF%B1%F8&cid=1&tp=0&pn=0");
		
		/*for(int i=1;i<=749;i++){
			String[] chapterInfo = chapters.get(i);
			if(chapterInfo==null){
				System.out.println("缺少第"+i+"章");
				continue;
			}
		}*/
		//String content = getChapterContents(chapters).toString();
		//FileUtil.writeInfoToFile(content, dir+novelName+".txt");
		writeChapterContents(chapters);
		
		//FileUtil.writeInfoToFile(errorInfo.toString(), dir+novelName+"_err.txt");
	}
	
	public static void writeChapterUrls(Map<Integer, String[]> chapters){
		int maxNum = Integer.parseInt(chapters.get(-1)[0]);
		StringBuilder sb = new StringBuilder();
		for(int i=1;i<=maxNum;i++){
			String[] chapterInfo = chapters.get(i);
			sb.append("第"+i+"章  ");
			if(chapterInfo!=null){
				String chapterUrl = chapterInfo[1];
				sb.append(chapterUrl).append("\r\n");
			}
			FileKit.writeInfoToFile(sb.toString(), baseDir+"dir/"+novelName+".txt");
		}
	}
	
	public static void writeChapterContents(Map<Integer, String[]> chapters) throws IOException{
		int maxNum = Integer.parseInt(chapters.get(-1)[0]);
		for(int i=1;i<=maxNum;i++){
			StringBuilder sb = new StringBuilder();
			String[] chapterInfo = chapters.get(i);
			if(chapterInfo==null){
				System.out.println("缺少第"+i+"章");
				sb.append(FileKit.readFile(baseDir+"lost/"+i+".txt")); 
			}else{
				String chapterName = chapterInfo[0];
				String chapterUrl = chapterInfo[1];
				sb.append(chapterName).append("\r\n\r\n");
				String content = getChapterContent(chapterUrl);
				if("NONE".equals(content)){
					System.out.println("无效的url，缺少第"+i+"章");
					sb.append(FileKit.readFile(baseDir+"lost/"+i+".txt")); 
				}else
					sb.append(content).append("\r\n\r\n");
			}
			FileKit.writeInfoToFile(sb.toString(), baseDir+"txt/"+novelName+String.format("%1$03d", i)+".txt");
		}
	}
	
	public static StringBuilder getChapterContents(Map<Integer, String[]> chapters) throws IOException{
		StringBuilder sb = new StringBuilder();
		int maxNum = Integer.parseInt(chapters.get(-1)[0]);
		for(int i=1;i<=maxNum;i++){
			String[] chapterInfo = chapters.get(i);
			if(chapterInfo==null){
				System.out.println("缺少第"+i+"章");
				sb.append(FileKit.readFile(baseDir+"lost/"+i+".txt")); 
			}else{
				String chapterName = chapterInfo[0];
				String chapterUrl = chapterInfo[1];
				sb.append(chapterName).append("\r\n\r\n");
				String content = getChapterContent(chapterUrl);
				if("NONE".equals(content)){
					System.out.println("无效的url，缺少第"+i+"章");
					sb.append(FileKit.readFile(baseDir+"lost/"+i+".txt")); 
				}else
					sb.append(content).append("\r\n\r\n");
			}
		}
		return sb;
	}
	
	public static String getChapterContent(String chapterUrl){
		StringBuilder sb = new StringBuilder();
		String ret = HttpKit.get(chapterUrl+"?see_lz=1");
		//FileUtil.writeInfoToFile(ret, "D:/test_full.txt");
		if(ret.indexOf("很抱歉，你访问的贴子不存在")!=-1){
			return "NONE";
		}
		
		Pattern chapterPattern = Pattern.compile("class=\"d_post_content\"\\s*?>([\\S\\s]+?)</div>");
		Matcher chapterMatcher = chapterPattern.matcher(ret);
		while(chapterMatcher.find()){
			String content = chapterMatcher.group(1);
			if(content.length()<300){
				//errorInfo.append("ERR@"+chapterUrl+":"+content);
				continue;
			}
			content = content.replaceAll("<br>", "\r\n").replaceAll("<a .+?>.+?</a>", "");
			sb.append(content);
		}
		return sb.toString();
	}
	
	public static void getChapters(Map<Integer, String[]> chapters, String url){
		String ret = HttpKit.get(url);
		
		Pattern chapterPattern = Pattern.compile("(第(\\d+)章?.+?)<br>.+?href=\"(.+?)\"");
		Matcher chapterMatcher = chapterPattern.matcher(ret);
		int count = 0;
		int maxNum = 1;
		if(chapters.get(-1)!=null)
			maxNum = Integer.parseInt(chapters.get(-1)[0]);
		while(chapterMatcher.find()){
			count++;
			Integer chapterNum = Integer.parseInt(chapterMatcher.group(2));
			String chapterUrl = chapterMatcher.group(3);
			if(chapterUrl.indexOf("/p")==-1){
				System.out.println("URL:"+chapterNum+"--"+chapterUrl);
				continue;
			}
			if(chapters.get(chapterNum)!=null){
				if(!chapters.get(chapterNum)[1].equals(chapterUrl))
					System.out.println("chapterNum repeat:"+chapterNum);
			}
			if(chapterNum>maxNum)
				maxNum = chapterNum;
			chapters.put(chapterNum, new String[]{chapterMatcher.group(1), chapterMatcher.group(3)});
		}
		chapters.put(-1, new String[]{maxNum+""});
		//System.out.println("最大章数="+maxNum);
		System.out.println("章数="+chapters.size());
	}
	
	public static void getChaptersInList(Map<Integer, String[]> chapters, String url){
		String ret = HttpKit.get(url);
		
		Pattern chapterPattern = Pattern.compile("<a href=\"(/p.+?)\".+?>\\s*(第(\\d+)章.+?)</a>");
		Matcher chapterMatcher = chapterPattern.matcher(ret);
		int count = 0;
		int maxNum = 1;
		if(chapters.get(-1)!=null)
			maxNum = Integer.parseInt(chapters.get(-1)[0]);
		while(chapterMatcher.find()){
			count++;
			Integer chapterNum = Integer.parseInt(chapterMatcher.group(3));
			String chapterUrl = "http://tieba.baidu.com"+chapterMatcher.group(1);
			if(chapterUrl.indexOf("/p")==-1){
				System.out.println("URL in List:"+chapterNum+"--"+chapterUrl);
				continue;
			}
			if(chapters.get(chapterNum)!=null){
				if(!chapters.get(chapterNum)[1].equals(chapterUrl))
					System.out.println("chapterNum repeat:"+chapterNum);
			}
			if(chapterNum>maxNum)
				maxNum = chapterNum;
			chapters.put(chapterNum, new String[]{chapterMatcher.group(2), chapterUrl});
		}
		chapters.put(-1, new String[]{maxNum+""});
		//System.out.println("最大章数="+maxNum);
		System.out.println("章数="+chapters.size());
	}
}
