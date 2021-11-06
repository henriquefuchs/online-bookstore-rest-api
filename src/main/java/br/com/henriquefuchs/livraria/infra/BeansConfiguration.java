package br.com.henriquefuchs.livraria.infra;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeansConfiguration {

  @Bean
  public BCryptPasswordEncoder getPassword() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ModelMapper getModelMapper() {
    return new ModelMapper();
  }

}
