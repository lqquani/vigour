package org.qql.vigour.framework.cglib;


/**
 * 
net.sf.cglib.core:底层字节码处理类，他们大部分与ASM有关系。

net.sf.cglib.transform:编译期或运行期类和类文件的转换

net.sf.cglib.proxy:实现创建代理和方法拦截器的类

net.sf.cglib.reflect:实现快速反射和C#风格代理的类

net.sf.cglib.util:集合排序等工具类

net.sf.cglib.beans:JavaBean相关的工具类

一、首先说一下JDK中的动态代理：

JDK中的动态代理是通过反射类Proxy以及InvocationHandler回调接口实现的，

但是，JDK中所要进行动态代理的类必须要实现一个接口，也就是说只能对该类所实现接口中定义的方法进行代理，这在实际编程中具有一定的局限性，而且使用反射的效率也并不是很高。

二、使用CGLib实现：

使用CGLib实现动态代理，完全不受代理类必须实现接口的限制，而且CGLib底层采用ASM字节码生成框架，使用字节码技术生成代理类，比使用Java反射效率要高。唯一需要注意的是，CGLib不能对声明为final的方法进行代理，因为CGLib原理是动态生成被代理类的子类。

下面，将通过一个实例介绍使用CGLib实现动态代理。


 */
/**
 * 没有实现接口，需要CGlib动态代理的目标类
 * @author kevin
 *
 */
public class TargetObject {  
    public String method1(String paramName) {  
        return paramName;  
    }  
  
    public int method2(int count) {  
        return count;  
    }  
  
    public int method3(int count) {  
        return count;  
    }  
  
    @Override  
    public String toString() {  
        return "TargetObject []"+ getClass();  
    }  
}
