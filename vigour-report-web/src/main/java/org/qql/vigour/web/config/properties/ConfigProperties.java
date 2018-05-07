package org.qql.vigour.web.config.properties;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

/**
 * 自定义配置类
 *
 **/
@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "org.qql.vigour")
@PropertySource("classpath:conf/properties/config.properties")
public class ConfigProperties {
    @Valid
    private final Shiro shiro = new Shiro();

    public static class Shiro {
        @NotEmpty
        private String filters;

        public String getFilters() {
            return this.filters;
        }

        public void setFilters(final String filters) {
            this.filters = filters;
        }
    }
}
