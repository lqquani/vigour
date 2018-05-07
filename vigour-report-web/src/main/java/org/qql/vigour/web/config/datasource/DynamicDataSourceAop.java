package org.qql.vigour.web.config.datasource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.qql.vigour.framework.annotation.Dbsource;
import org.qql.vigour.framework.holder.DataSourceHolder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class DynamicDataSourceAop implements MethodInterceptor, InitializingBean {  
	  
//	@Pointcut("execution(* org.qql.vigour.*.*(..))")
	@Pointcut(value="@annotation(org.qql.vigour.framework.annotation.Dbsource)")
    public void aspect() {
    }
	
    @Around("aspect()")
    public Object methodAspect(ProceedingJoinPoint pjp) throws Throwable {
    	log.info("123---");
        String methodName = pjp.getSignature().getName();
        return pjp.proceed();
    }
    
    @Before("aspect()")
    public void doBefore(final JoinPoint joinPoint) {
        try {
        	log.info("msg-before");
        	Class<?> clazz = joinPoint.getTarget().getClass();  
        	boolean clzHasAnno = clazz.isAnnotationPresent(Dbsource.class);  
        	
        	if (clzHasAnno) {  
                // 获取类上的注解  
        		Dbsource annotation = clazz.getAnnotation(Dbsource.class);  
                // 输出注解上的属性  
        		DataSourceHolder.setDataSourceId(annotation.value());  
                System.out.println(annotation.value());  
            }  
        	 // 解析字段上是否有注解  
            // ps：getDeclaredFields会返回类所有声明的字段，包括private、protected、public，但是不包括父类的  
            // getFields:则会返回包括父类的所有的public字段，和getMethods()一样  
            Field[] fields = clazz.getDeclaredFields();  
            for(Field field : fields){  
                boolean fieldHasAnno = field.isAnnotationPresent(Dbsource.class);  
                if(fieldHasAnno){  
                	Dbsource fieldAnno = field.getAnnotation(Dbsource.class);  
                    //输出注解属性  
                	DataSourceHolder.setDataSourceId(fieldAnno.value());  
                    System.out.println(fieldAnno.value());  
                }  
            }  
            //解析方法上的注解  
            Method[] methods = clazz.getDeclaredMethods();  
            for(Method method : methods){  
                boolean methodHasAnno = method.isAnnotationPresent(Dbsource.class);  
                if(methodHasAnno){  
                    //得到注解  
                	Dbsource methodAnno = method.getAnnotation(Dbsource.class);  
                    //输出注解属性  
                    System.out.println(method.getName() + " desc = " + methodAnno.value()); 
                    DataSourceHolder.setDataSourceId(methodAnno.value()); 
                }  
            }  
//	        Dbsource classDataSource = AnnotationUtils.getAnnotation(clazz, Dbsource.class);  
//	        if(classDataSource != null){  
//	        	DataSourceHolder.setDataSourceId(classDataSource.value());  
//	        }else {  
//	        	DataSourceHolder.clear();  
//	        } 
        } catch (final Exception e) {
            log.error("异常信息:{}", e);
        }
    }
    
    @After("aspect()")
    public void doAfter(final JoinPoint joinPoint) {
        try {
        	log.info("msg-after");
        } catch (final Exception e) {
            log.error("异常信息:{}", e);
        }
    }

    @AfterThrowing(pointcut = "aspect()", throwing = "e")
    public void doAfterThrowing(final JoinPoint joinPoint, final Throwable e) {
        try {
        	log.info("msg----");
        } catch (final Exception ex) {
            log.error("异常信息:{}", ex.getMessage());
        }
    }

    
    @Override  
    public Object invoke(MethodInvocation invocation) throws Throwable {  
        Class<?> clazz = invocation.getThis().getClass();  
        String className = clazz.getName();  
        if (ClassUtils.isAssignable(clazz, Proxy.class)) {  
            className = invocation.getMethod().getDeclaringClass().getName();  
        }  
        String methodName = invocation.getMethod().getName();  
        Object[] arguments = invocation.getArguments();  
        log.trace("execute {}.{}({})", className, methodName, arguments);  
  
        invocation.getMethod();  
        Dbsource classDataSource = AnnotationUtils.getAnnotation(clazz, Dbsource.class);  
        if(classDataSource != null){  
        	DataSourceHolder.setDataSourceId(classDataSource.value());  
        }else {  
        	DataSourceHolder.clear();  
        }  
  
        Object result = invocation.proceed();  
        return result;  
    }  
  
    @Override  
    public void afterPropertiesSet() throws Exception {  
    }  
    
    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     * 
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent((Class<? extends Annotation>) Dbsource.class)) {
                Dbsource source = clazz.getAnnotation(Dbsource.class);
                DataSourceHolder.setDataSourceId(source.value());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(Dbsource.class)) {
                Dbsource source = m.getAnnotation(Dbsource.class);
                DataSourceHolder.setDataSourceId(source.value());
            }
        } catch (Exception e) {
            System.out.println(clazz + ":" + e.getMessage());
        }
    }
}  
