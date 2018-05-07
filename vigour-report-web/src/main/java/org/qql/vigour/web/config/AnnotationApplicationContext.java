package org.qql.vigour.web.config;

import java.util.Map;

import org.qql.vigour.framework.annotation.Dbsource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class AnnotationApplicationContext implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Map<String, Object> beansWithAnnotationMap=event.getApplicationContext().getBeansWithAnnotation(Dbsource.class);
		   Class<? extends Object> clazz = null;  
	        for(Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()){  
	            clazz = entry.getValue().getClass();//获取到实例对象的class信息  
	            Class<? extends Object>  [] interfaces = clazz.getInterfaces();  
	            for(Class<? extends Object>  aInterface : interfaces){  
	                String aInterfaceName = aInterface.getName();//接口的完整名  
	            
	            }  
	        }
		System.out.println(beansWithAnnotationMap);
	}
	
	public  String getDefaultInstanceName(Class<?> clazz) {  
        if (clazz == null) {  
            return null;  
        }  
        String className = clazz.getSimpleName();  
        String firsrLowerChar = className.substring(0, 1).toLowerCase();  
        className = firsrLowerChar + className.substring(1);  
        return className;  
    }  

}
