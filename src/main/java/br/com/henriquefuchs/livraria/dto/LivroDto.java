package br.com.henriquefuchs.livraria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LivroDto {

  @JsonProperty(index = 0)
  private Long id;

  @JsonProperty(index = 1)
  private String titulo;

  @JsonProperty(index = 2, value = "data-de-lancamento")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataDeLancamento;

  @JsonProperty(index = 3, value = "numero-de-paginas")
  private Integer numeroDePaginas;

  @JsonProperty(index = 4)
  private AutorDto autor;

  public LivroDto(String titulo, LocalDate dataDeLancamento, Integer numeroDePaginas, AutorDto autor) {
    this.titulo = titulo;
    this.dataDeLancamento = dataDeLancamento;
    this.numeroDePaginas = numeroDePaginas;
    this.autor = autor;
  }

}
