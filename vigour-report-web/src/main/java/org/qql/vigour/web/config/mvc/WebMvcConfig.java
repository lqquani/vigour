package org.qql.vigour.web.config.mvc;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.qql.vigour.framework.holder.SpringContextHolder;
import org.qql.vigour.framework.repository.mybatis.SqlSessionFactoryBeanExt;
import org.qql.vigour.web.config.AnnotationApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

/**
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(messageConverter());
    }

//    @Override
//    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
//    	
//    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        //max file size 10M
        multipartResolver.setMaxUploadSize(10 * 1024 * 1024);
        return multipartResolver;
    }

    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        //保存7天有效
        localeResolver.setCookieMaxAge(604800);
        localeResolver.setDefaultLocale(Locale.CHINA);
        localeResolver.setCookieName("locale");
        localeResolver.setCookiePath("/");
        return localeResolver;
    }

    
    @Bean
    public AnnotationApplicationContext applicationStartListener(){
        return new AnnotationApplicationContext();
    }
    
    @Bean
    public SpringContextHolder springContextHolder(){
    	return new SpringContextHolder();
    }
    
    @Bean
    public org.qql.vigour.framework.annotation.DbsourceAnnotationBeanPostProcessor dbsourceAnnotationBeanPostProcessor(){
    	return new org.qql.vigour.framework.annotation.DbsourceAnnotationBeanPostProcessor();
    }
    
    @Bean
    public SqlSessionFactoryBeanExt createSqlSessionFactoryBean(){
    	return   new SqlSessionFactoryBeanExt();
    }
}

