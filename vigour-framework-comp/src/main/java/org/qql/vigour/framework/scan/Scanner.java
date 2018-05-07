package org.qql.vigour.framework.scan;

import java.util.Set;

import org.qql.vigour.framework.annotation.Dbsource;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public final class Scanner extends ClassPathBeanDefinitionScanner {

	public Scanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

//	public void registerDefaultFilters() {
//		this.addIncludeFilter(new AnnotationTypeFilter(Dbsource.class));
//	}

	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		for (BeanDefinitionHolder holder : beanDefinitions) {
			GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
			definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
			definition.setBeanClass(FactoryBeanExample.class);
			
			/*Class<?> clazz = definition.getBeanClass();  
	        DataSource classDataSource = AnnotationUtils.getAnnotation(clazz, DataSource.class);  
	        if(classDataSource != null){  
	        	DataSourceHolder.setDataSourceId(classDataSource.value());  
	        }else {  
	        	DataSourceHolder.clear();  
	        } */
	        
		}
		return beanDefinitions;
	}

	public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata()
				.hasAnnotation(Dbsource.class.getName());
	}

}


