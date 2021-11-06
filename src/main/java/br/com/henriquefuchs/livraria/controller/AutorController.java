package br.com.henriquefuchs.livraria.controller;

import br.com.henriquefuchs.livraria.dto.AtualizacaoAutorFormDto;
import br.com.henriquefuchs.livraria.dto.AutorDetalhadoDto;
import br.com.henriquefuchs.livraria.dto.AutorDto;
import br.com.henriquefuchs.livraria.dto.AutorFormDto;
import br.com.henriquefuchs.livraria.service.AutorService;
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
@RequestMapping("/autores")
public class AutorController {

  private final AutorService autorService;

  @Autowired
  public AutorController(AutorService autorService) {
    this.autorService = autorService;
  }

  @GetMapping
  @ApiOperation("Listar autores")
  public Page<AutorDto> listar(Pageable pageable) {
    return autorService.listar(pageable);
  }

  @PostMapping
  @ApiOperation("Cadastrar autor")
  public ResponseEntity<AutorDto> cadastrar(@RequestBody @Valid AutorFormDto autorFormDto, UriComponentsBuilder uriComponentsBuilder) {
    AutorDto autorDtor = autorService.cadastrar(autorFormDto);
    URI uri = uriComponentsBuilder.path("/autores/{id}").buildAndExpand(autorDtor.getId()).toUri();
    return ResponseEntity.created(uri).body(autorDtor);
  }

  @PutMapping
  @ApiOperation("Atualizar autor")
  public ResponseEntity<AutorDto> atualizar(@RequestBody @Valid AtualizacaoAutorFormDto atualizacaoAutorFormDto) {
    AutorDto autorDto = autorService.atualizar(atualizacaoAutorFormDto);
    return ResponseEntity.ok(autorDto);
  }

  @DeleteMapping("/{id}")
  @ApiOperation("Remover autor")
  public ResponseEntity<AutorDto> remover(@PathVariable @NotNull Long id) {
    autorService.remover(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  @ApiOperation("Detalhar autor")
  public ResponseEntity<AutorDetalhadoDto> detalhar(@PathVariable @NotNull Long id) {
    AutorDetalhadoDto autorDetalhadoDto = autorService.detalhar(id);
    return ResponseEntity.ok(autorDetalhadoDto);
  }

}
