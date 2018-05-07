package org.qql.vigour.web.config.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @see <a href="http://springfox.github.io/springfox/docs/current/#getting-started">swagger</a>
 **/
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(this.apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("org.qql.vigour.web.controller"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Vigour")
            .description("Vigour A Sample and Easy to Use Web Reporting")
            .termsOfServiceUrl("http://www.Vigour.org")
            .contact(new Contact("vigoursoft", "http://www.vigour.com", "okex@vigour.com"))
            .version("1.0")
            .build();
    }
}
