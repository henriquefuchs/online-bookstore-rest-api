package br.com.henriquefuchs.livraria.controller;

import br.com.henriquefuchs.livraria.dto.AtualizacaoLivroFormDto;
import br.com.henriquefuchs.livraria.dto.LivroDetalhadoDto;
import br.com.henriquefuchs.livraria.dto.LivroDto;
import br.com.henriquefuchs.livraria.dto.LivroFormDto;
import br.com.henriquefuchs.livraria.service.LivroService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/livros")
public class LivroController {

  private final LivroService livroService;

  @Autowired
  public LivroController(LivroService livroService) {
    this.livroService = livroService;
  }

  @GetMapping
  @ApiOperation("Listar livros")
  public Page<LivroDto> listar(Pageable pageable) {
    return livroService.listar(pageable);
  }

  @PostMapping
  @ApiOperation("Cadastrar livro")
  public ResponseEntity<LivroDto> cadastrar(@RequestBody @Valid LivroFormDto livroFormDto, UriComponentsBuilder uriComponentsBuilder) {
    LivroDto livroDto = livroService.cadastrar(livroFormDto);
    URI uri = uriComponentsBuilder.path("/livros/{id}").buildAndExpand(livroDto.getId()).toUri();
    return ResponseEntity.created(uri).body(livroDto);
  }

  @PutMapping
  @ApiOperation("Atualizar livro")
  public ResponseEntity<LivroDto> atualizar(@RequestBody @Valid AtualizacaoLivroFormDto atualizacaoLivroFormDto) {
    LivroDto livroDto = livroService.atualizar(atualizacaoLivroFormDto);
    return ResponseEntity.ok(livroDto);
  }

  @DeleteMapping("/{id}")
  @ApiOperation("Remover livro")
  public ResponseEntity<LivroDto> remover(@PathVariable @NotNull Long id) {
    livroService.remover(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  @ApiOperation("Detalhar livro")
  public ResponseEntity<LivroDetalhadoDto> detalhar(@PathVariable @NotNull Long id) {
    LivroDetalhadoDto livroDetalhadoDto = livroService.detalhar(id);
    return ResponseEntity.ok(livroDetalhadoDto);
  }

}
