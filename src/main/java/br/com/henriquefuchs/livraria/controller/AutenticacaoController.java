package br.com.henriquefuchs.livraria.controller;

import br.com.henriquefuchs.livraria.dto.LoginFormDto;
import br.com.henriquefuchs.livraria.dto.TokenDto;
import br.com.henriquefuchs.livraria.infra.security.AutenticacaoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

  private final AutenticacaoService autenticacaoService;

  public AutenticacaoController(AutenticacaoService autenticacaoService) {
    this.autenticacaoService = autenticacaoService;
  }

  @PostMapping
  @ApiOperation("Autenticar")
  public TokenDto autenticar(@RequestBody @Valid LoginFormDto dto) {
    return autenticacaoService.autenticar(dto);
  }

}
