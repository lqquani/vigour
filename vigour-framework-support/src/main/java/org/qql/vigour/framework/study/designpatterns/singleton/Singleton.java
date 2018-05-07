package org.qql.vigour.framework.study.designpatterns.singleton;

public class Singleton {

	private static Singleton singletion=null;
	private Singleton() {
		
	}
	/**
	 * 多线程线程不安全
	 * @return
	 */
	public static Singleton getSingleton() {
		if(singletion==null) {
			singletion=new Singleton();
		}
		return singletion;
	}
	/**
	 * 并发其实是一种特殊情况，大多时候这个锁占用的额外资源都浪费了，这种打补丁方式写出来的结构效率很低。
	 * @return
	 */
	public static synchronized Singleton getSingleton1() {
		if(singletion==null) {
			singletion=new Singleton();
		}
		return singletion;
	}
	
	public static Singleton getSingletion2() {
		return SingletonHolder.singletioner;
	}
	/**
	 * 内部类单例模式 静态内部类不会在单例加载时就加载，而是在调用getInstance()方法时才进行加载，达到了类似懒汉模式的效果，而这种方法又是线程安全的
	 * @author kevin
	 *
	 */
	private static class SingletonHolder{
		public static Singleton singletioner=new Singleton();
	}
	
	
}
/**
 * 
 * Effective Java作者Josh Bloch 提倡的方式，在我看来简直是来自神的写法。解决了以下三个问题：
(1)自由序列化。
(2)保证只有一个实例。
(3)线程安全。
 * @author kevin
 *
 */
enum SingletonEnum{
	
    INSTANCE;
	private int i=0;
    public void otherMethods(){
    	i++;
        System.out.println(i);
    }
}


 enum Color {  
	//如果打算自定义自己的方法，那么必须在enum实例序列的最后添加一个分号。而且 Java 要求必须先定义 enum 实例。 
    RED(1,"红色"), GREEN(2,"绿色"), BLANK(3,"白色"), YELLO(4,"黄色");  
    // 成员变量  
    private String value;  
    private int key;  
    // 构造方法  
    private Color(int key,String value) {  
        this.value = value;  
        this.key = key;  
    }  
    // 普通方法  
    public static String getValueByKey(int key) {  
        for (Color c : Color.values()) {  
            if (c.getKey() == key) {  
                return c.value;  
            }  
        }  
        return null;  
    }
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}  

    
}  

