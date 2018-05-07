package org.qql.vigour.framework.cglib;

import net.sf.cglib.proxy.Enhancer;  

/**
 * 
 * 
 * 5.延迟加载对象

一、作用：
说到延迟加载，应该经常接触到，尤其是使用Hibernate的时候，本篇将通过一个实例分析延迟加载的实现方式。
LazyLoader接口继承了Callback，因此也算是CGLib中的一种Callback类型。

另一种延迟加载接口Dispatcher。

Dispatcher接口同样继承于Callback，也是一种回调类型。

但是Dispatcher和LazyLoader的区别在于：LazyLoader只在第一次访问延迟加载属性时触发代理类回调方法，而Dispatcher在每次访问延迟加载属性时都会触发代理类回调方法。


 * 首先定义一个实体类LoaderBean，该Bean内有一个需要延迟加载的属性PropertyBean。
 * @author kevin
 *
 */
public class LazyBean {  
    private String name;  
    private int age;  
    private PropertyBean propertyBean;  
    private PropertyBean propertyBeanDispatcher;  
  
    public LazyBean(String name, int age) {  
        System.out.println("lazy bean init");  
        this.name = name;  
        this.age = age;  
        this.propertyBean = createPropertyBean();  
        this.propertyBeanDispatcher = createPropertyBeanDispatcher();  
    }  
  
      
  
    /** 
     * 只第一次懒加载 
     * @return 
     */  
    private PropertyBean createPropertyBean() {  
        /** 
         * 使用cglib进行懒加载 对需要延迟加载的对象添加代理，在获取该对象属性时先通过代理类回调方法进行对象初始化。 
         * 在不需要加载该对象时，只要不去获取该对象内属性，该对象就不会被初始化了（在CGLib的实现中只要去访问该对象内属性的getter方法， 
         * 就会自动触发代理类回调）。 
         */  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(PropertyBean.class);  
        PropertyBean pb = (PropertyBean) enhancer.create(PropertyBean.class,  
                new ConcreteClassLazyLoader());  
        return pb;  
    }  
    /** 
     * 每次都懒加载 
     * @return 
     */  
    private PropertyBean createPropertyBeanDispatcher() {  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(PropertyBean.class);  
        PropertyBean pb = (PropertyBean) enhancer.create(PropertyBean.class,  
                new ConcreteClassDispatcher());  
        return pb;  
    }  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public int getAge() {  
        return age;  
    }  
  
    public void setAge(int age) {  
        this.age = age;  
    }  
  
    public PropertyBean getPropertyBean() {  
        return propertyBean;  
    }  
  
    public void setPropertyBean(PropertyBean propertyBean) {  
        this.propertyBean = propertyBean;  
    }  
  
    public PropertyBean getPropertyBeanDispatcher() {  
        return propertyBeanDispatcher;  
    }  
  
    public void setPropertyBeanDispatcher(PropertyBean propertyBeanDispatcher) {  
        this.propertyBeanDispatcher = propertyBeanDispatcher;  
    }  
  
    @Override  
    public String toString() {  
        return "LazyBean [name=" + name + ", age=" + age + ", propertyBean="  
                + propertyBean + "]";  
    }  
}  
