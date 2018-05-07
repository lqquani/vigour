package org.qql.vigour.framework.study.designpatterns.singleton;

public class Emperor {

	private static final Emperor emperor=new Emperor();
	private Emperor() {
		
	}
	public static Emperor getInstance() {
		return emperor;
	}
	public static void say() {
		System.out.println("我就是唯一一个皇帝，再也不能多了!");
	}
}
