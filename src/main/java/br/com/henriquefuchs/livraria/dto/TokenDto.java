package br.com.henriquefuchs.livraria.dto;

import lombok.Getter;

@Getter
public class TokenDto {

  public String token;

  public TokenDto(String token) {
    this.token = token;
  }

}
