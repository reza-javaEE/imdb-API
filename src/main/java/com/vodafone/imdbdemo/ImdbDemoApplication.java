package com.vodafone.imdbdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;

@EnableSwagger2
@SpringBootApplication
public class ImdbDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(ImdbDemoApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("imdb services")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vodafone.imdbdemo"))
                .paths(any())
                .build().apiInfo(new ApiInfo(" services",
                        "A set of services to provide data access to imdb interfaces",
                        "1.0.0",
                        null,
                        new Contact("vodafone co.", "info@vodafone.com", null),
                        null,
                        null));
    }

}
