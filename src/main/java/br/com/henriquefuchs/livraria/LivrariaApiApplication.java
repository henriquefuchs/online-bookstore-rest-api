package br.com.henriquefuchs.livraria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LivrariaApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(LivrariaApiApplication.class, args);
  }

}
