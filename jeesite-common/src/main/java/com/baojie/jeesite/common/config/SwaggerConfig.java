package com.baojie.jeesite.common.config;

import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 * Swagger API配置
 * @author 冀保杰
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "jeesite", name = "swagger", havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger2 RESTful APIs")
                .termsOfServiceUrl("http://www.weepal.com/")
                .termsOfServiceUrl("localhost:8080/")
                .contact(new Contact("weepal", "http://localhost:8080/", "weepal.com"))
                .version("1.0")
                .build();
    }
}
