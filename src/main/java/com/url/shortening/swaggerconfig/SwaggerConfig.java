package com.url.shortening.swaggerconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* <h1>Swagger configuration class.</h1>
*
* @author  Chirag Sheth
* @version 1.0
* @since   2020-07-29
*/

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.apiInfo(metadata())
    			.select()
    			.apis(RequestHandlerSelectors.basePackage("com.url"))
    			.build();
    }

    private ApiInfo metadata(){
    	return new ApiInfoBuilder()
    			.title("Shortening the Url")
    			.description("REST API reference for Url shortening.")
    			.version("1.0")
    			.build();
    }
}