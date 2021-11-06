package br.com.henriquefuchs.livraria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorDto {

  private Long id;

  private String nome;

  private String email;

  @JsonProperty("data-de-nascimento")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataDeNascimento;

  @JsonProperty("mini-curriculo")
  private String miniCurriculo;

  public AutorDto(String nome, String email, LocalDate dataDeNascimento, String miniCurriculo) {
    this.nome = nome;
    this.email = email;
    this.dataDeNascimento = dataDeNascimento;
    this.miniCurriculo = miniCurriculo;
  }

}
