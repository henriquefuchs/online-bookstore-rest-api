package br.com.henriquefuchs.livraria.controller;

import br.com.henriquefuchs.livraria.infra.security.TokenService;
import br.com.henriquefuchs.livraria.model.Perfil;
import br.com.henriquefuchs.livraria.model.Usuario;
import br.com.henriquefuchs.livraria.repository.PerfilRepository;
import br.com.henriquefuchs.livraria.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RelatorioControllerTest {

  public String token;

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TokenService tokenService;
  @Autowired
  private PerfilRepository perfilRepository;
  @Autowired
  private UsuarioRepository usuarioRepository;

  @BeforeEach
  public void gerarToken() {
    Usuario logado = new Usuario("Usuário", "usuario", "123");
    Perfil admin = perfilRepository.findById(1L).orElse(null);
    logado.adicionarPerfil(admin);
    usuarioRepository.save(logado);
    Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin());
    this.token = tokenService.gerarToken(authentication);
  }

  @Test
  void deveriaRetornarRelatorioComStatus200() throws Exception {
    mockMvc.perform(get("/relatorios").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
      .andExpect(status().isOk());
  }

}
