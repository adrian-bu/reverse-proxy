package de.adrianbuch.reverse.proxy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration {
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Reverse Proxy")
                .description("High-security request interceptor using cloud-native reverse proxy technologies for detecting and preventing critical security incidents.")
                .contact(new Contact("Adrian Buch", "https://www.adrian-buch.de", "privat@adrian-buch.de"))
                .license("Apache License 2.0")
                .licenseUrl("https://github.com/adrian-bu/reverse-proxy/blob/main/LICENSE")
                .version("0.0.1-SNAPSHOT")
                .build();
    }
}
