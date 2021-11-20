package br.com.henriquefuchs.livraria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoAutorInputDto extends AutorInputDto {

  public Long id;

}
