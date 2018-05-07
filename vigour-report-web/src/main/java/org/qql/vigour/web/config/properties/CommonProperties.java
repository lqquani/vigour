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
@ConfigurationProperties(prefix = "org.qql.vigour.common")
@PropertySource("classpath:conf/properties/common.properties")
public class CommonProperties {
    private String item1;
    private String item2;
}
