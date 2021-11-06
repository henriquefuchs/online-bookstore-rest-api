package br.com.henriquefuchs.livraria.infra.security;

import br.com.henriquefuchs.livraria.model.Usuario;
import br.com.henriquefuchs.livraria.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerificacaoTokenFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UsuarioRepository usuarioRepository;

  public VerificacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
    this.tokenService = tokenService;
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = request.getHeader("Authorization");

    if (token == null || token.isEmpty()) {
      filterChain.doFilter(request, response);
      return;
    }

    token = token.replace("Bearer", "");
    boolean tokenValido = tokenService.isValido(token);
    if (tokenValido) {
      Long idUsuario = tokenService.extrarIdUsuario(token);
      Usuario usuarioLogado = usuarioRepository.carregarPorIdComPerfis(idUsuario).get();

      Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioLogado, null, usuarioLogado.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

}
