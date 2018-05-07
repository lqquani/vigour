package org.qql.vigour.web.config.datasource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.qql.vigour.framework.common.DynamicDataSource;
import org.qql.vigour.framework.common.entity.DataSourceInfo;
import org.qql.vigour.framework.repository.mybatis.DynamicMyBatisRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;

//@Configuration
//@MapperScan(basePackages = DynamicDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "dynamicSqlSessionFactory",annotationClass=DynamicMyBatisRepository.class)
public class DynamicDataSourceConfig extends AbstractDataSourceConfig {
    static final String PACKAGE = "org.qql.vigour";//扫描basePackage下所有以@MyBatisRepository标识的接口
    static final String MAPPER_LOCATION = "classpath*:/**/mybatis/**/*Mapper.xml";
    
    @Bean(name = "dynamicTransactionManager")
    public DataSourceTransactionManager dynamicTransactionManager(@Qualifier("dynamicDataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }
    
    @Bean(name = "dynamicSqlSessionFactory")
    public SqlSessionFactory dynamicSqlSessionFactory(@Qualifier("dynamicDataSource") final DataSource dataSource)
        throws Exception {
        return this.createSqlSessionFactory(dataSource, MAPPER_LOCATION);
    }

    @Bean(name = "dynamicSqlSessionTemplate")
    public SqlSessionTemplate dynamicSqlSessionTemplate(@Qualifier("dynamicSqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
        throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "dynamicTransactionTemplate")
    public TransactionTemplate dynamicTransactionTemplate(@Qualifier("dynamicTransactionManager") final
                                                   DataSourceTransactionManager transactionManager)
        throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }
    
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource(){
    	DynamicDataSource dds=new DynamicDataSource();
    	DataSourceInfo dsi=new DataSourceInfo();
    	Map<String, DataSourceInfo> dsMap=new HashMap<String, DataSourceInfo>();
    	dsi.setDriverClass("org.h2.Driver");
    	dsi.setDriverType("com.alibaba.druid.pool.DruidDataSource");
    	dsi.setUrl("jdbc:h2:tcp://127.0.0.1/~/REPORT;MVCC=TRUE");
    	dsi.setUserName("sa");
    	dsi.setPassword("sa");
    	dsi.setValidationQuery("SELECT 1 FROM DUAL");
    	dsMap.put("1", dsi);
    	dsi=new DataSourceInfo();
    	dsi.setDriverClass("org.h2.Driver");
    	dsi.setDriverType("com.alibaba.druid.pool.DruidDataSource");
    	dsi.setUrl("jdbc:h2:tcp://127.0.0.1/~/REPORT2;MVCC=TRUE");
    	dsi.setUserName("sa");
    	dsi.setPassword("sa");
    	dsi.setValidationQuery("SELECT 1 FROM DUAL");
    	dsMap.put("2", dsi);
    	Iterator<Entry<String, DataSourceInfo>> iter = dsMap.entrySet().iterator();
		Map<Object, Object> targetDataSources = Maps.newHashMap();
		while (iter.hasNext()) {
			Entry<String, DataSourceInfo> entry = iter.next();
			String dsId = entry.getKey();
			DataSourceInfo dsInfo = entry.getValue();
//			Object dao;
//			try {
//					dao = Class.forName( dsInfo.getDriverClass() ).newInstance();
//			} catch (InstantiationException | IllegalAccessException
//					| ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				log.error( "实例化数据源[" + dsInfo.getId() + "]失败:" + e.getLocalizedMessage() );
//				ByteArrayOutputStream out = new ByteArrayOutputStream();
//				e.printStackTrace( new PrintStream( out ) );
//				log.error( out.toString() );
//				try {
//					out.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//				continue;
//			}
//		
				DruidDataSource ds = new DruidDataSource();
				ds.setDriverClassName( dsInfo.getDriverClass() );
				ds.setUrl(dsInfo.getUrl());
				ds.setUsername(dsInfo.getUserName());
				ds.setPassword(dsInfo.getPassword());
//				ds.setInitialSize( PropertiesUtils.getInt("ds_init_size",10 )  );
//				ds.setMinIdle(PropertiesUtils.getInt("ds_min_idle",5 ) );
//				ds.setMaxIdle(PropertiesUtils.getInt("ds_max_idle",20 ) );
//				ds.setMaxActive(PropertiesUtils.getInt("ds_max_active", 50 ) );
//				ds.setRemoveAbandoned(true);
//				ds.setMaxWait(PropertiesUtils.getInt("ds_max_wait", 10000 ) );
				ds.setTestOnBorrow(true);
				ds.setPoolPreparedStatements( true );
				ds.setMaxOpenPreparedStatements( 20 );
				ds.setValidationQuery(dsInfo.getValidationQuery());
				targetDataSources.put(dsId, ds);
				dds.setResolvedDataSources(targetDataSources);;
			}
    	dds.setTargetDataSources(targetDataSources);
//    	dds.setDefaultTargetDataSource(dataSource());
    	dds.afterPropertiesSet();
    	return dds;
    }
}
