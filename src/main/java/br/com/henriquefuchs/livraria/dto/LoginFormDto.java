package br.com.henriquefuchs.livraria.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginFormDto {

  @NotBlank
  public String login;
  @NotBlank
  public String senha;

}
