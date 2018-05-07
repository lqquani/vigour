package org.qql.vigour.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import lombok.extern.slf4j.Slf4j;

/**
 * Properties文件载入工具类. 可载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的值，但以System的Property优先.
 * 
 * @author kevin
 */
@Slf4j
public class PropertiesUtils extends PropertyPlaceholderConfigurer{

	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	private  Properties properties;

	public PropertiesUtils(String... resourcesPaths) {
		properties = loadProperties(resourcesPaths);
	}
	
	public PropertiesUtils(){
	}

	public Properties getProperties() {
		return properties;
	}

	/**
	 * 取出Property。
	 */
	private String getValue(String key) {
		String systemProperty = System.getProperty(key);
		if (systemProperty != null) {
			return systemProperty;
		}
		String springProperty= map.get(key); 
		if( springProperty!=null){
			return springProperty; 
		}
		return properties.getProperty(key);
	}

	/**
	 * 取出String类型的Property,如果都為Null则抛出异常.
	 */
	public String getProperty(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		
		return value;
	}

	/**
	 * 取出String类型的Property.如果都為Null則返回Default值.
	 */
	public String getProperty(String key, String defaultValue) {
		String value = getValue(key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 取出Integer类型的Property.如果都為Null或内容错误则抛出异常.
	 */
	public Integer getInteger(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Integer.valueOf(value);
	}

	/**
	 * 取出Integer类型的Property.如果都為Null則返回Default值，如果内容错误则抛出异常
	 */
	public Integer getInteger(String key, Integer defaultValue) {
		String value = getValue(key);
		return value != null ? Integer.valueOf(value) : defaultValue;
	}

	/**
	 * 取出Double类型的Property.如果都為Null或内容错误则抛出异常.
	 */
	public Double getDouble(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Double.valueOf(value);
	}

	/**
	 * 取出Double类型的Property.如果都為Null則返回Default值，如果内容错误则抛出异常
	 */
	public Double getDouble(String key, Integer defaultValue) {
		String value = getValue(key);
		return value != null ? Double.valueOf(value) : defaultValue;
	}

	/**
	 * 取出Boolean类型的Property.如果都為Null抛出异常,如果内容不是true/false则返回false.
	 */
	public Boolean getBoolean(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Boolean.valueOf(value);
	}

	/**
	 * 取出Boolean类型的Propert.如果都為Null則返回Default值,如果内容不为true/false则返回false.
	 */
	public Boolean getBoolean(String key, boolean defaultValue) {
		String value = getValue(key);
		return value != null ? Boolean.valueOf(value) : defaultValue;
	}

	/**
	 * 载入多个文件, 文件路径使用Spring Resource格式.
	 */
	private Properties loadProperties(String... resourcesPaths) {
		Properties props = new Properties();

		for (String location : resourcesPaths) {

			log.debug("Loading properties file from:" + location);

			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				props.load(is);
			} catch (IOException ex) {
				log.info("Could not load properties from path:" + location + ", " + ex.getMessage());
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return props;
	}
	
	private static Map<String, String> map = new HashMap<String, String>() ;   
	  
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) {  
        super.processProperties(beanFactory, props) ;  
        for (Object key : props.keySet()) {  
            String keyStr = key.toString() ;  
            String value = props.getProperty(keyStr) ;  
            map.put(keyStr, value) ;  
        }  
    }  
    
    public static String getProperty2(String key){
		return map.get(key);
    }
    
    public static <T> T getProperty(String key, Class<T> requiredType){
		return getProperty(key,null,requiredType);
    	
    }
    
    public static <T> T getProperty(String key, T defaultValue,Class<T> requiredType){
    	return map.get(key)!=null&&!"".equals(map.get(key))? (T)map.get(key):defaultValue;
    }
    
}
