package br.com.henriquefuchs.livraria.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SpringFoxSwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
      .paths(PathSelectors.any()).build()
      .globalRequestParameters(List.of(
        new RequestParameterBuilder()
          .name("Authorization")
          .description("Bearer Token")
          .required(false)
          .in("header")
          .build()
      ))
      .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo("REST API Livraria", "REST API para gestão de uma livraria online", "1.0",
      "Termos de uso", new Contact("Henrique", "www.henrique.com", "henrique@email.com"),
      "API License", "API License URL", Collections.emptyList());
  }

}
