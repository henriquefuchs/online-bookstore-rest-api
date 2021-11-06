package br.com.henriquefuchs.livraria.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutorFormDto {

  @NotBlank
  private String nome;

  @NotBlank
  private String email;

  @JsonAlias("data-de-nascimento")
  @JsonFormat(pattern = "dd/MM/yyyy")
  @PastOrPresent
  @NotNull
  private LocalDate dataDeNascimento;

  @JsonAlias("mini-curriculo")
  @NotBlank
  private String miniCurriculo;

}
