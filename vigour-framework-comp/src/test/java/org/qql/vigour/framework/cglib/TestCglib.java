package org.qql.vigour.framework.cglib;

import net.sf.cglib.proxy.Callback;  
import net.sf.cglib.proxy.CallbackFilter;  
import net.sf.cglib.proxy.Enhancer;  
import net.sf.cglib.proxy.NoOp;  

/**
 * 
 这里Enhancer类是CGLib中的一个字节码增强器，它可以方便的对你想要处理的类进行扩展，以后会经常看到它。

首先将被代理类TargetObject设置成父类，然后设置拦截器TargetInterceptor，最后执行enhancer.create()动态生成一个代理类，并从Object强制转型成父类型TargetObject。

最后，在代理类上调用方法.

 * @author kevin
 *
 */
public class TestCglib {  
    public static void main(String args[]) {  
        Enhancer enhancer =new Enhancer();  
        enhancer.setSuperclass(TargetObject.class);  
        enhancer.setCallback(new TargetInterceptor());  
        TargetObject targetObject2=(TargetObject)enhancer.create();  
        System.out.println("类:"+targetObject2);  
        System.out.println(targetObject2.method1("mmm1"));  
        System.out.println(targetObject2.method2(100));  
        System.out.println(targetObject2.method3(200));  
    }  
}  
