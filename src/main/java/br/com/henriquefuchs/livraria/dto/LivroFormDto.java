package br.com.henriquefuchs.livraria.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LivroFormDto {

  @NotBlank
  @Size(min = 10)
  private String titulo;

  @JsonAlias("data-de-lancamento")
  @JsonFormat(pattern = "dd/MM/yyyy")
  @PastOrPresent
  @NotNull
  private LocalDate dataDeLancamento;

  @JsonAlias("numero-de-paginas")
  @Min(100)
  @NotNull
  private Integer numeroDePaginas;

  @JsonAlias("autor-id")
  @NotNull
  private Long autorId;

}
