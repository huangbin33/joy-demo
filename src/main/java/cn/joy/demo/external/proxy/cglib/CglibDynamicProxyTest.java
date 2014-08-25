package cn.joy.demo.external.proxy.cglib;

import cn.joy.demo.external.proxy.ITestHandler;
import cn.joy.demo.external.proxy.TestHandlerImpl;

public class CglibDynamicProxyTest {
	public static void main(String[] args) {
		ITestHandler handler = new TestHandlerImpl();
		
		CglibDynamicProxy proxy = new CglibDynamicProxy();
		handler = (ITestHandler)proxy.bind(handler);
		
		Long t1 = System.currentTimeMillis();
		for(int i=0;i<20;i++){
			handler.test1();
		}
		Long t2 = System.currentTimeMillis();
		
		System.out.println("cost time="+(t2-t1));
	}
}
