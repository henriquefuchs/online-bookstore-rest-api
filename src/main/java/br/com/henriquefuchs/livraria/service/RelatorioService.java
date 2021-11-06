package br.com.henriquefuchs.livraria.service;

import br.com.henriquefuchs.livraria.dto.RelatorioLivroPorAutorDto;
import br.com.henriquefuchs.livraria.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

  private final LivroRepository livroRepository;

  @Autowired
  public RelatorioService(LivroRepository livroRepository) {
    this.livroRepository = livroRepository;
  }

  public List<RelatorioLivroPorAutorDto> relatorioLivroPorAutor() {
    return livroRepository.relatorioLivroPorAutor();
  }

}
