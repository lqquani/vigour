package org.qql.vigour.framework.cglib;

import java.lang.reflect.Method; 

import net.sf.cglib.proxy.MethodInterceptor;  
import net.sf.cglib.proxy.MethodProxy;  
/** 
 * 定义一个拦截器。在调用目标方法时，CGLib会回调MethodInterceptor接口方法拦截，来实现你自己的代理逻辑，类似于JDK中的InvocationHandler接口。
 * 目标对象拦截器，实现MethodInterceptor 
 * 
 */  
public class TargetInterceptor implements MethodInterceptor{  
  
    /** 
     * 重写方法拦截在方法前和方法后加入业务 
     * Object obj为目标对象 
     * Method method为目标方法 
     * Object[] params 为参数， 
     * MethodProxy proxy CGlib方法代理对象 
     */  
    @Override  
    public Object intercept(Object obj, Method method, Object[] params,  
            MethodProxy proxy) throws Throwable {  
        System.out.println("调用前....");  
        Object result = proxy.invokeSuper(obj, params);  
        System.out.println(" 调用后"+result);  
        return result;  
    }  
  
  
}  
