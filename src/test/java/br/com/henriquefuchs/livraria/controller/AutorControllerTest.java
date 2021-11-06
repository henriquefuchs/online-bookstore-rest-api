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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AutorControllerTest {

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
  void deveriaCadastrarUmAutorComDadosCompletos() throws Exception {
    String json = "{\"nome\":\"Autor\", \"email\":\"autor@email.com\", \"data-de-nascimento\":\"01/01/2000\", \"mini-curriculo\":\"Escrevo livros\"}";

    mockMvc.perform(post("/autores").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token))
      .andExpect(status().isCreated())
      .andExpect(header().exists("Location"))
      .andExpect(content().json(json));
  }

  @Test
  void naoDeveriaCadastrarUmAutorComDadosImcompletos() throws Exception {
    String json = "{}";

    mockMvc.perform(post("/autores").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token))
      .andExpect(status().isBadRequest());
  }

  @Test
  void naoDeveriaCadastrarUmAutorComDadosVazios() throws Exception {
    String json = "{\"nome\":\"\", \"email\":\"\", \"data-de-nascimento\":\"\", \"mini-curriculo\":\"\"}";

    mockMvc.perform(post("/autores").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token))
      .andExpect(status().isBadRequest());
  }

  @Test
  void deveriaCadastrarUmAutorComDataNoPassado() throws Exception {
    String dataNoPassado = LocalDate.now().minusYears(1L).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    String json = "{\"nome\":\"Autor\", \"email\":\"autor@email.com\", \"data-de-nascimento\":\"" + dataNoPassado + "\", \"mini-curriculo\":\"Escrevo livros\"}";

    mockMvc.perform(post("/autores").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token))
      .andExpect(status().isCreated())
      .andExpect(header().exists("Location"))
      .andExpect(content().json(json));
  }

  @Test
  void deveriaCadastrarUmAutorComDataNoPresente() throws Exception {
    String dataNoPresente = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    String json = "{\"nome\":\"Autor\", \"email\":\"autor@email.com\", \"data-de-nascimento\":\"" + dataNoPresente + "\", \"mini-curriculo\":\"Escrevo livros\"}";

    mockMvc.perform(post("/autores").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token))
      .andExpect(status().isCreated())
      .andExpect(header().exists("Location"))
      .andExpect(content().json(json));
  }

  @Test
  void naoDeveriaCadastrarUmAutorComDataDeNascimentoFutura() throws Exception {
    String dataNoFuturo = LocalDate.now().plusYears(1L).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    String json = "{\"nome\":\"Autor\", \"email\":\"autor@email.com\", \"data-de-nascimento\":\"" + dataNoFuturo + "\", \"mini-curriculo\":\"Escrevo livros\"}";

    mockMvc.perform(post("/autores").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token))
      .andExpect(status().isBadRequest());
  }

}
