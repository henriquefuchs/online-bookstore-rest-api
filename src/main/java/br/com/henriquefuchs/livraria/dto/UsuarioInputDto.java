package br.com.henriquefuchs.livraria.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UsuarioInputDto {

  @NotBlank
  private String nome;

  @NotBlank
  private String login;

  @NotBlank
  @Email
  private String email;

  @NotNull
  @JsonAlias("perfil-id")
  private Long perfilId;

}
