package com.mkl.mkltest.spring;

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

@Configuration
@EnableSwagger2 
public class SpringFoxConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.mkl.mkltest.controller"))              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
    private ApiInfo apiEndPointsInfo() {

        return new ApiInfoBuilder().title("Anvui 20 REST API")
                .description("Transport Management REST API")
                .contact(new Contact("Le Bang", "banglv", "july147791@gmail.com"))
                .license("Apache 2.0")
                .termsOfServiceUrl("#")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("0.1-SNAPSHOT")
                .build();
    }
}