package br.com.henriquefuchs.livraria.controller;

import br.com.henriquefuchs.livraria.dto.UsuarioDto;
import br.com.henriquefuchs.livraria.dto.UsuarioInputDto;
import br.com.henriquefuchs.livraria.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @GetMapping
  @ApiOperation("Listar usuários")
  public Page<UsuarioDto> listar(@PageableDefault(size = 20) Pageable pageable) {
    return usuarioService.listar(pageable);
  }

  @PostMapping
  @ApiOperation("Cadastrar usuários")
  public ResponseEntity<UsuarioDto> cadastrar(@RequestBody @Valid UsuarioInputDto dto, UriComponentsBuilder uriComponentsBuilder) {
    UsuarioDto usuarioDto = usuarioService.cadastrar(dto);
    URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuarioDto.getId()).toUri();

    return ResponseEntity.created(uri).body(usuarioDto);
  }

}
