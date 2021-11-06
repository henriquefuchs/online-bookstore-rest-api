package br.com.henriquefuchs.livraria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Erro500Dto {

  private LocalDateTime timeStamp;
  private String erro;
  private String mensagem;
  private String path;

}
