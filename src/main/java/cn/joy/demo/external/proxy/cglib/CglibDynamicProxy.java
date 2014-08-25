package cn.joy.demo.external.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import cn.joy.demo.external.proxy.IDynamicProxy;

public class CglibDynamicProxy implements IDynamicProxy, MethodInterceptor {  
	private Object target;  
	  
    /** 
     * 创建代理对象 
     */  
    public Object bind(Object target) {  
        this.target = target;  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(this.target.getClass());  
        // 回调方法  
        enhancer.setCallback(this);  
        // 创建代理对象  
        return enhancer.create();  
    }  
  
    // 回调方法  
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {  
    	System.out.println("cglib proxy invoke before..."); 
        proxy.invokeSuper(obj, args);  
        System.out.println("cglib proxy invoke before...");
        return null;  
    }  
  
}
