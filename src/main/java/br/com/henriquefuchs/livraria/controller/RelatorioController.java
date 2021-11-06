package br.com.henriquefuchs.livraria.controller;

import br.com.henriquefuchs.livraria.dto.RelatorioLivroPorAutorDto;
import br.com.henriquefuchs.livraria.service.RelatorioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

  private final RelatorioService relatorioService;

  public RelatorioController(RelatorioService relatorioService) {
    this.relatorioService = relatorioService;
  }

  @GetMapping
  @ApiOperation("Exibir relatório de livros por autor")
  public List<RelatorioLivroPorAutorDto> relatorioLivroPorAutor() {
    return relatorioService.relatorioLivroPorAutor();
  }

}
