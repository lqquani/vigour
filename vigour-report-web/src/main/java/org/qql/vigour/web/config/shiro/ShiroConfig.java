package org.qql.vigour.web.config.shiro;

import java.util.Map;

import javax.servlet.Filter;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.qql.vigour.framework.support.shiro.filter.AjaxFormAuthenticationFilter;
import org.qql.vigour.framework.support.shiro.filter.MembershipFilter;
import org.qql.vigour.framework.support.shiro.security.MyShiroRealm;
import org.qql.vigour.framework.support.shiro.security.RetryLimitHashedCredentialsMatcher;
import org.qql.vigour.web.config.properties.ConfigProperties;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class ShiroConfig {
    @Autowired
    private ConfigProperties configProperties;

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行 
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        final ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/home/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/401");

        final Map<String, Filter> filters = Maps.newHashMap();
        filters.put("authc", this.authcFilter());
        filters.put("membership", this.membershipFilter());
        shiroFilterFactoryBean.setFilters(filters);

        final Map<String, String> chains = Maps.newLinkedHashMap();
        chains.put("/member/logout", "logout");
        chains.put("/", this.configProperties.getShiro().getFilters());
        chains.put("/home/**", this.configProperties.getShiro().getFilters());
        chains.put("/views/**", this.configProperties.getShiro().getFilters());
        chains.put("/rest/**", this.configProperties.getShiro().getFilters());
        chains.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);

        return shiroFilterFactoryBean;
    }

    @Bean
    public AjaxFormAuthenticationFilter authcFilter() {
        final AjaxFormAuthenticationFilter authcFilter = new AjaxFormAuthenticationFilter();
        authcFilter.setUsernameParam("username");
        authcFilter.setPasswordParam("password");
        authcFilter.setRememberMeParam("rememberMe");
        authcFilter.setFailureKeyAttribute("shiroLoginFailure");
        return authcFilter;
    }

    @Bean
    public MembershipFilter membershipFilter() {
        return new MembershipFilter();
    }

    @Bean
    public SecurityManager securityManager() {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(cacheEhcacheShiroManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public RetryLimitHashedCredentialsMatcher credentialsMatcher() {
        final RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(
        		cacheEhcacheShiroManager());
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean
    public MyShiroRealm shiroRealm() {
        final MyShiroRealm realm = new MyShiroRealm();
        realm.setCredentialsMatcher(credentialsMatcher());
        return realm;
    }

    @Bean
    public SessionManager sessionManager() {
        final DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.getSessionIdCookie().setName("Vigour_JSESSIONID");
        return sessionManager;
    }

    /**
     * 根据Shiro官方的说法，虽然缓存在权限框架中非常重要，但是如果实现一套完整的缓存机制会使得shiro偏离了核心的功能（认证和授权）。
     * 因此，Shiro只提供了一个可以支持具体缓存实现（如：Hazelcast, Ehcache, OSCache, Terracotta, Coherence, GigaSpaces, JBossCache等）的抽象API接口，
     * 这样就允许Shiro用户根据自己的需求灵活地选择具体的CacheManager。
     * 当然，其实Shiro也自带了一个本地内存CacheManager：org.apache.shiro.cache.MemoryConstrainedCacheManager。
     * @return
     */
    @Bean
    public MemoryConstrainedCacheManager cacheShiroManager() {
        return new MemoryConstrainedCacheManager();
    }
    /**
     * 自定义实现ehcache缓存
     * @return
     */
    @Bean
    public EhCacheManager cacheEhcacheShiroManager() {
    	log.info("加载自定义配置shiro Ehcache....");
        return new EhCacheManager();
    }

    /**
     *  AOP式方法级权限检查 
     * @return
     */
    @DependsOn(value="lifecycleBeanPostProcessor")
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        final DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }
    
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        final AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager());
        return aasa;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        final SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        final CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.decode("ZUdsaGJuSmxibVI2ZHc9PQ=="));
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
    
}

