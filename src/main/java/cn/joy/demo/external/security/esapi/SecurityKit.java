package cn.joy.demo.external.security.esapi;

import org.owasp.esapi.ESAPI;

public class SecurityKit {
	public static String encode4Html(String str){
		return ESAPI.encoder().encodeForHTML(str);
	}
	
	public static void main(String[] args) {
		String html = "<img src='xxx' title='' onclick=''>";
		System.out.println(ESAPI.encoder().encodeForHTML(html));
	}
}
