package org.qql.vigour.framework.i18n;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 自定义的国际化Message资源加载类
 *
 **/

public class CustomResourceBundleMessageSource extends ResourceBundleMessageSource {

	public CustomResourceBundleMessageSource(){
		this.setBasename("i18n/messages");
	}
   
	
}
