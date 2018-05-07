package org.qql.vigour.web.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "org.qql.vigour.env")
@PropertySource("classpath:conf/properties/env.properties")
public class EnvProperties {
    private String appName;
    private String name;
    private String version;
}
