package cn.joy.demo.external.proxy;

import cn.joy.demo.external.proxy.jdk.JdkDynamicProxy;

public class DynamicProxyTest {
	public static void main(String[] args) {
		IDynamicProxy proxy = new JdkDynamicProxy();
		ITestHandler handler = (ITestHandler)proxy.bind(new TestHandlerImpl());
		
		Long t1 = System.currentTimeMillis();
		for(int i=0;i<20;i++){
			handler.test1();
		}
		Long t2 = System.currentTimeMillis();
		
		
		
		proxy = new JdkDynamicProxy();
		handler = (ITestHandler)proxy.bind(new TestHandlerImpl());
		
		Long t3 = System.currentTimeMillis();
		for(int i=0;i<20;i++){
			handler.test1();
		}
		Long t4 = System.currentTimeMillis();
		
		System.out.println("\njdk proxy, cost time="+(t2-t1));
		System.out.println("cglib proxy, cost time="+(t4-t3));
	}
}
