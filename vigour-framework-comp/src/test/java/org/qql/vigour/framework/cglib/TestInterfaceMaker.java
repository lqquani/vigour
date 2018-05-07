package org.qql.vigour.framework.cglib;

import java.lang.reflect.InvocationTargetException;  
import java.lang.reflect.Method;  
  
import net.sf.cglib.proxy.Enhancer;  
import net.sf.cglib.proxy.InterfaceMaker;  
import net.sf.cglib.proxy.MethodInterceptor;  
import net.sf.cglib.proxy.MethodProxy;  
 
/**
 * 
 * 
6.接口生成器InterfaceMaker

一、作用：

InterfaceMaker会动态生成一个接口，该接口包含指定类定义的所有方法


Thread context class loader存在的目的主要是为了解决parent delegation机制下无法干净的解决的问题。假如有下述委派链：
ClassLoader A -> System class loader -> Extension class loader -> Bootstrap class loader

那么委派链左边的ClassLoader就可以很自然的使用右边的ClassLoader所加载的类。

但如果情况要反过来，是右边的ClassLoader所加载的代码需要反过来去找委派链靠左边的ClassLoader去加载东西怎么办呢？没辙，parent delegation是单向的，没办法反过来从右边找左边。

这种情况下就可以把某个位于委派链左边的ClassLoader设置为线程的context class loader，这样就给机会让代码不受parent delegation的委派方向的限制而加载到类了。


 * @author kevin
 *
 */
public class TestInterfaceMaker {  
  
    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {  
        InterfaceMaker interfaceMaker =new InterfaceMaker();  
        //抽取某个类的方法生成接口方法  
        interfaceMaker.add(TargetObject.class);  
        Class<?> targetInterface=interfaceMaker.create();  
        for(Method method : targetInterface.getMethods()){  
            System.out.println(method.getName());  
        }  
        //接口代理并设置代理接口方法拦截  
        Object object = Enhancer.create(Object.class, new Class[]{targetInterface}, new MethodInterceptor(){  
            @Override  
            public Object intercept(Object obj, Method method, Object[] args,  
                    MethodProxy methodProxy) throws Throwable {  
                if(method.getName().equals("method1")){  
                    System.out.println("filter method1 ");  
                    return "mmmmmmmmm";  
                }  
                if(method.getName().equals("method2")){  
                    System.out.println("filter method2 ");  
                    return 1111111;  
                }  
                if(method.getName().equals("method3")){  
                    System.out.println("filter method3 ");  
                    return 3333;  
                }  
                return "default";  
            }});  
        Method targetMethod1=object.getClass().getMethod("method3",new Class[]{int.class});  
        int i=(int)targetMethod1.invoke(object, new Object[]{33});  
        Method targetMethod=object.getClass().getMethod("method1",new Class[]{String.class});  
        System.out.println(targetMethod.invoke(object, new Object[]{"sdfs"}));  
    }  
  
}  
