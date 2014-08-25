package cn.joy.demo.external.proxy.jdk;

import cn.joy.demo.external.proxy.ITestHandler;
import cn.joy.demo.external.proxy.TestHandlerImpl;

public class JdkDynamicProxyTest {
	public static void main(String[] args) {
		JdkDynamicProxy proxy = new JdkDynamicProxy();
		ITestHandler handler = (ITestHandler)proxy.bind(new TestHandlerImpl());
		
		Long t1 = System.currentTimeMillis();
		for(int i=0;i<20;i++){
			handler.test1();
		}
		Long t2 = System.currentTimeMillis();
		
		System.out.println("cost time="+(t2-t1));
	}
}
