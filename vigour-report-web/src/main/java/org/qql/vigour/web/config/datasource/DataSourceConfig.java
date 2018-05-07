package org.qql.vigour.web.config.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("org.qql.vigour")
@Slf4j
public class DataSourceConfig {

   /* @Value("${spring.datasource.url}")  
    private String dbUrl;   
    @Value("${spring.datasource.username}")  
    private String username;  
    @Value("${spring.datasource.password}")  
    private String password;  
    @Value("${spring.datasource.driver-class-name}")  
    private String driverClassName;  
    @Value("${spring.datasource.initialSize}")  
    private int initialSize;   
    @Value("${spring.datasource.minIdle}")  
    private int minIdle;  
    @Value("${spring.datasource.maxActive}")  
    private int maxActive;  
    @Value("${spring.datasource.maxWait}")  
    private int maxWait;  
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")  
    private int timeBetweenEvictionRunsMillis;  
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")  
    private int minEvictableIdleTimeMillis;  
    @Value("${spring.datasource.validationQuery}")  
    private String validationQuery;   
    @Value("${spring.datasource.testWhileIdle}")  
    private boolean testWhileIdle;  
    @Value("${spring.datasource.testOnBorrow}")  
    private boolean testOnBorrow;  
    @Value("${spring.datasource.testOnReturn}")  
    private boolean testOnReturn;  
    @Value("${spring.datasource.poolPreparedStatements}")  
    private boolean poolPreparedStatements;   
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
    @Value("${spring.datasource.filters}")  
    private String filters;    
    @Value("{spring.datasource.connectionProperties}")  
    private String connectionProperties;  
    
    
    @Bean(value="dataSource")     //声明其为Bean实例  
//    @Primary  //在同样的DataSource中，首先使用被标注的DataSource  
    public DataSource dataSource(){  
        DruidDataSource datasource = new DruidDataSource();  
        datasource.setUrl(this.dbUrl);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        datasource.setDriverClassName(driverClassName);  
        //configuration  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            log.error("druid configuration initialization filter", e);  
        }  
        datasource.setConnectionProperties(connectionProperties);  
        return datasource;  
    }  */
    
    
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(@Qualifier("vigourDataSource") final DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
    
    @Bean
	public JdbcTemplate jdbcTemplate(@Qualifier("vigourDataSource") final DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}
    
    @Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("vigourDataSource") final DataSource dataSource){
		return new NamedParameterJdbcTemplate(dataSource);
	}
	/**
	 * <!-- 配置处理代理工厂 -->
	 * @return
	 */
//    @Bean
//   	public TransactionProxyFactoryBean engineConfigDAO(){
//    	TransactionProxyFactoryBean proxy=new TransactionProxyFactoryBean();
//    	proxy.setTransactionManager(transactionManager(dataSource()));
//    	Properties p=new Properties();
//    	p.setProperty("find*", "PROPAGATION_SUPPORTS,readOnly");
//    	p.setProperty("get*", "PROPAGATION_SUPPORTS,readOnly");
//    	p.setProperty("*", "PROPAGATION_REQUIRED");
//    	proxy.setTransactionAttributes(p);
//   		return proxy;
//   	}
//    
//    /**
//	 * <!-- 数据处理代理工厂-->
//	 * @return
//	 */
//    @Bean
//   	public TransactionProxyFactoryBean engineCalcDAO(){
//    	TransactionProxyFactoryBean proxy=new TransactionProxyFactoryBean();
//    	Properties p=new Properties();
//    	p.setProperty("find*", "PROPAGATION_SUPPORTS,readOnly");
//    	p.setProperty("get*", "PROPAGATION_SUPPORTS,readOnly");
//    	p.setProperty("*", "PROPAGATION_REQUIRED");
//    	proxy.setTransactionAttributes(p);
//   		return proxy;
//   	}
    
    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier("vigourDataSource") final DataSource dataSource){
    	log.info("加载JPA配置.....");
    	LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
    	factory.setDataSource(dataSource);
    	factory.setPackagesToScan("org.qql.vigour");
    	factory.setJpaVendorAdapter(jpaVendorAdapter());
    	factory.setPersistenceUnitName("vigour");
    	
//    	Map<String, Object> jpaProperties = new HashMap<String, Object>();
//        jpaProperties.put("hibernate.ejb.naming_strategy","org.hibernate.cfg.ImprovedNamingStrategy");
//        jpaProperties.put("hibernate.jdbc.batch_size",50);
//        jpaProperties.put("hibernate.show_sql",true);
//
//        factory.setJpaPropertyMap(jpaProperties);
//        factory.afterPropertiesSet();
        
    	return factory;
    	
    }
    
    @Bean
    public JpaTransactionManager  jpaTransactionManager(@Qualifier("entityManagerFactory") final EntityManagerFactory  factory){
        JpaTransactionManager  manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(factory);
        manager.setJpaDialect(new HibernateJpaDialect());
        return manager;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor  persistenceExceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
    @Bean(name="jpaVendorAdapter")
    public HibernateJpaVendorAdapter jpaVendorAdapter(){
    	HibernateJpaVendorAdapter hibernateJpaVendorAdapter=new HibernateJpaVendorAdapter();
    	hibernateJpaVendorAdapter.setShowSql(true);
    	
    	return hibernateJpaVendorAdapter;
    }
    
    
}
