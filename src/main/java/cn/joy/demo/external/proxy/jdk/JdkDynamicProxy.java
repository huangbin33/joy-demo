package cn.joy.demo.external.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.joy.demo.external.proxy.IDynamicProxy;

public class JdkDynamicProxy implements IDynamicProxy, InvocationHandler {
	private Object target;
	
	public Object bind(Object target) {  
        this.target = target;  
        //取得代理对象  
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), 
        		target.getClass().getInterfaces(), this);   //要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)  
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("jdk proxy invoke before...");
		method.invoke(target, args);
		System.out.println("jdk proxy invoke after...");
		return null;
	}

}
