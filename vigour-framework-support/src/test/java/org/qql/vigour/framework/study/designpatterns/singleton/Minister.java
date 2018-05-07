package org.qql.vigour.framework.study.designpatterns.singleton;

/**
 * 臣子要出场了
 * @author kevin
 *
 */
public class Minister {

	public static void main(String[] args) {
		
		for(int day=0;day<5;day++) {
			Emperor emperor=Emperor.getInstance();
			emperor.say();
			
			/**
			 * 多线程执行，保证i输出累加
			 */
			new Thread() {
				public void run() {
					SingletonEnum.INSTANCE.otherMethods();
					try {
						sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}.start();
		}
		
		SingletonEnum.INSTANCE.otherMethods();
		SingletonEnum.INSTANCE.otherMethods();
		SingletonEnum.INSTANCE.otherMethods();
		SingletonEnum.INSTANCE.otherMethods();
		
		
		Color c=Color.RED;
		c=Enum.valueOf(Color.class, "RED");
		switch(c) {
		case RED:
			System.out.print(Color.getValueByKey(c.getKey()));
			break;
		case GREEN:
			System.out.print(Color.getValueByKey(c.getKey()));
			break;
			
			default:
				System.out.print(Color.getValueByKey(c.getKey()));
		}
	}

}
