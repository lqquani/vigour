package org.qql.vigour.web.config.cache;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Configuration
public class EhcacheConfig extends CachingConfigurerSupport{

	/**
     *  ehcache 主要的管理器
     * @param bean
     * @return
     */

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){

       log.info("正在加载CacheConfiguration.ehCacheCacheManager()");
       return new EhCacheCacheManager(bean.getObject());

    }


    /*
       * 据shared与否的设置,
       * Spring分别通过CacheManager.create()
       * 或new CacheManager()方式来创建一个ehcache基地.
       *
       * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
       *
       */

      @Bean
      public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        log.info("加载CacheConfiguration.ehCacheManagerFactoryBean()");
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
        //EhCache所需要的配置文件，只需要放到类路径下，Spring Boot会自动扫描。 
        //也可以通过在application.properties文件中，通过配置来指定EhCache配置文件的位置，如：
        //spring.cache.ehcache.config= #  ehcache配置文件地址
        //Spring Boot会自动为我们配置EhCacheCacheMannager的Bean。
        //如此，这个类可以不用配置。
        
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("conf/ehcache-common.xml"));

        cacheManagerFactoryBean.setShared(true);

        return cacheManagerFactoryBean;

      }
    
      
}
