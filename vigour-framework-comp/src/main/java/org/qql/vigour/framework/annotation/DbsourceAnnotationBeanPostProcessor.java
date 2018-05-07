/**
 * 
 */
package org.qql.vigour.framework.annotation;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


import org.qql.vigour.framework.common.DynamicDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <context:component-scan base-package="*.*" />
 * 	 实现接口BeanPostProcessor 处理bean类
	 该配置隐式注册了多个对注解进行解析的处理器，如：
	 AutowiredAnnotationBeanPostProcessor     
	 CommonAnnotationBeanPostProcessor
	 PersistenceAnnotationBeanPostProcessor   
	 RequiredAnnotationBeanPostProcessor 
 * @author kevin
 *
 */
@Slf4j
public class DbsourceAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter 
		implements MergedBeanDefinitionPostProcessor, PriorityOrdered, BeanFactoryAware{

	private transient final Map<String, InjectionMetadata> injectionMetadataCache =
			new ConcurrentHashMap<String, InjectionMetadata>(256);
	private static Class<? extends Annotation> dbsourceRefClass = null;
	
	private ConfigurableListableBeanFactory beanFactory;
	
	private int order = Ordered.LOWEST_PRECEDENCE - 2;
	
	static {
		try {
			@SuppressWarnings("unchecked")
			Class<? extends Annotation> clazz = (Class<? extends Annotation>)
					ClassUtils.forName("org.qql.vigour.framework.annotation.Dbsource", CommonAnnotationBeanPostProcessor.class.getClassLoader());
			dbsourceRefClass = clazz;
		}
		catch (ClassNotFoundException ex) {
			dbsourceRefClass = null;
		}
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
			throw new IllegalArgumentException(
					"AutowiredAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
		}
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}
	
	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean,
			String beanName) throws BeansException {
		InjectionMetadata metadata = findDbsourceMetadata(beanName, bean.getClass(), pvs);
		try {
			metadata.inject(bean, beanName, pvs);
		}
		catch (Throwable ex) {
			throw new BeanCreationException(beanName, "Injection of resource dependencies failed", ex);
		}
		return pvs;
	}
	
	private InjectionMetadata findDbsourceMetadata(String beanName, final Class<?> clazz, PropertyValues pvs) {
		String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
		InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
		if (InjectionMetadata.needsRefresh(metadata, clazz)) {
			synchronized (this.injectionMetadataCache) {
				metadata = this.injectionMetadataCache.get(cacheKey);
				if (InjectionMetadata.needsRefresh(metadata, clazz)) {
					if (metadata != null) {
						metadata.clear(pvs);
					}
					try {
						metadata = buildDbsourceMetadata(clazz);
						this.injectionMetadataCache.put(cacheKey, metadata);
					}
					catch (NoClassDefFoundError err) {
						throw new IllegalStateException("Failed to introspect bean class [" + clazz.getName() +
								"] for resource metadata: could not find class that it depends on", err);
					}
				}
			}
		}
		return metadata;
	}
	
	private InjectionMetadata buildDbsourceMetadata(final Class<?> clazz) {
		LinkedList<InjectionMetadata.InjectedElement> elements = new LinkedList<InjectionMetadata.InjectedElement>();
		Class<?> targetClass = clazz;

		do {
			final LinkedList<InjectionMetadata.InjectedElement> currElements =
					new LinkedList<InjectionMetadata.InjectedElement>();

			ReflectionUtils.doWithLocalFields(targetClass, new ReflectionUtils.FieldCallback() {
				@Override
				public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
					
					if(dbsourceRefClass != null && field.getDeclaredAnnotation(Dbsource.class)!=null){
						log.info("处理类中包含Dbsource注解的属性....");
						if (Modifier.isStatic(field.getModifiers())) {
							throw new IllegalStateException("@WebServiceRef annotation is not supported on static fields");
						}
						
						currElements.add(new AutowiredFieldElement(field, true));
					}
					if (dbsourceRefClass != null && field.isAnnotationPresent(dbsourceRefClass)) {
						if (Modifier.isStatic(field.getModifiers())) {
							throw new IllegalStateException("@WebServiceRef annotation is not supported on static fields");
						}
					}
				}
			});

			ReflectionUtils.doWithLocalMethods(targetClass, new ReflectionUtils.MethodCallback() {
				@Override
				public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
					Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
					if (!BridgeMethodResolver.isVisibilityBridgeMethodPair(method, bridgedMethod)) {
						return;
					}
					if (method.equals(ClassUtils.getMostSpecificMethod(method, clazz))) {
						if (dbsourceRefClass != null && bridgedMethod.isAnnotationPresent(dbsourceRefClass)) {
							if (Modifier.isStatic(method.getModifiers())) {
								throw new IllegalStateException("@WebServiceRef annotation is not supported on static methods");
							}
							if (method.getParameterTypes().length != 1) {
								throw new IllegalStateException("@WebServiceRef annotation requires a single-arg method: " + method);
							}
							PropertyDescriptor pd = BeanUtils.findPropertyForMethod(bridgedMethod, clazz);
						}
					}
				}
			});
			elements.addAll(0, currElements);
			targetClass = targetClass.getSuperclass();
		}
		while (targetClass != null && targetClass != Object.class);

		return new InjectionMetadata(clazz, elements);
	}


	private class AutowiredFieldElement extends InjectionMetadata.InjectedElement {

		private final boolean required;

		private volatile boolean cached = false;

		private volatile Object cachedFieldValue;

		public AutowiredFieldElement(Field field, boolean required) {
			super(field, null);
			this.required = required;
		}

		@Override
		protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {
			Field field = (Field) this.member;
			Object value;
			if (this.cached) {
				value = resolvedCachedArgument(beanName, this.cachedFieldValue);
			}
			else {
				DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
				desc.setContainingClass(bean.getClass());
				Set<String> autowiredBeanNames = new LinkedHashSet<String>(1);
				autowiredBeanNames.add("dynamicDataSource");
				TypeConverter typeConverter = beanFactory.getTypeConverter();
				try {
					value = beanFactory.getBean("dynamicDataSource");//.resolveDependency(desc, beanName, autowiredBeanNames, typeConverter);
				}
				catch (BeansException ex) {
					throw new UnsatisfiedDependencyException(null, beanName, new InjectionPoint(field), ex);
				}
				synchronized (this) {
					if (!this.cached) {
						if (value != null || this.required) {
							this.cachedFieldValue = desc;
							registerDependentBeans(beanName, autowiredBeanNames);
							if (autowiredBeanNames.size() == 1) {
								String autowiredBeanName = autowiredBeanNames.iterator().next();
								if (beanFactory.containsBean(autowiredBeanName)) {
									if (beanFactory.isTypeMatch(autowiredBeanName, field.getType())) {
										this.cachedFieldValue = new ShortcutDependencyDescriptor(
												desc, autowiredBeanName, field.getType());
									}
								}
							}
						}
						else {
							this.cachedFieldValue = null;
						}
						this.cached = true;
					}
				}
			}
			if (value != null) {
				ReflectionUtils.makeAccessible(field);
//				DynamicDataSource dynamicDataSource=SpringContextHolder.getBean("dynamicDataSource");
				//注入Bean到指
				value=((DynamicDataSource) value).getResolvedDataSources().get(field.getDeclaredAnnotation(Dbsource.class).value());
				field.set(bean, value);
			}
		}
	}
	
	private Object resolvedCachedArgument(String beanName, Object cachedArgument) {
		if (cachedArgument instanceof DependencyDescriptor) {
			DependencyDescriptor descriptor = (DependencyDescriptor) cachedArgument;
			return this.beanFactory.resolveDependency(descriptor, beanName, null, null);
		}
		else {
			return cachedArgument;
		}
	}

	private void registerDependentBeans(String beanName, Set<String> autowiredBeanNames) {
		if (beanName != null) {
			for (String autowiredBeanName : autowiredBeanNames) {
				if (this.beanFactory.containsBean(autowiredBeanName)) {
					this.beanFactory.registerDependentBean(autowiredBeanName, beanName);
				}
				if (log.isDebugEnabled()) {
					log.debug("Autowiring by type from bean name '" + beanName +
							"' to bean named '" + autowiredBeanName + "'");
				}
			}
		}
	}
	
	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	
	@SuppressWarnings("serial")
	private static class ShortcutDependencyDescriptor extends DependencyDescriptor {

		private final String shortcut;

		private final Class<?> requiredType;

		public ShortcutDependencyDescriptor(DependencyDescriptor original, String shortcut, Class<?> requiredType) {
			super(original);
			this.shortcut = shortcut;
			this.requiredType = requiredType;
		}

		@Override
		public Object resolveShortcut(BeanFactory beanFactory) {
			return resolveCandidate(this.shortcut, this.requiredType, beanFactory);
		}
	}
	
	
	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
		if (beanType != null) {
			InjectionMetadata metadata = findDbsourceMetadata(beanName, beanType, null);
			metadata.checkConfigMembers(beanDefinition);
		}
		
	}
	
//	@Override
//	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//		 System.out.println("DbsourceAnnotationBeanPostProcessor before->"+beanName);
//		return bean;
//	}
//
//	@Override
//	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//		return bean;
//	}

	
	
	
	
}
