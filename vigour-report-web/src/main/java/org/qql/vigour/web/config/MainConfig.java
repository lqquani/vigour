package org.qql.vigour.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 自动扫描基础包下类并且装入bean容器
 **/
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "org.qql.vigour" })
public class MainConfig {
}
