package br.com.henriquefuchs.livraria.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginInputDto {

  @NotBlank
  public String login;
  @NotBlank
  public String senha;

}
