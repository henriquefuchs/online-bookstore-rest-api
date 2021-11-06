package br.com.henriquefuchs.livraria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioLivroPorAutorDto {

  @JsonProperty(index = 1, value = "nome-do-autor")
  private String nomeDoAutor;
  @JsonProperty(index = 2, value = "quantidade-de-livros")
  private Long quantidadeDeLivros;
  @JsonProperty(index = 3)
  private BigDecimal percentual;

  public RelatorioLivroPorAutorDto(String nomeDoAutor, Long quantidadeDeLivros, Double percentual) {
    this.nomeDoAutor = nomeDoAutor;
    this.quantidadeDeLivros = quantidadeDeLivros;
    this.percentual = new BigDecimal(percentual.toString());
  }

}
