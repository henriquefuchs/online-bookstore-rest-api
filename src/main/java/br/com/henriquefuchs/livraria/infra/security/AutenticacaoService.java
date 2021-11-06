package br.com.henriquefuchs.livraria.infra.security;

import br.com.henriquefuchs.livraria.dto.LoginFormDto;
import br.com.henriquefuchs.livraria.dto.TokenDto;
import br.com.henriquefuchs.livraria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

  private final UsuarioRepository usuarioRepository;
  private final TokenService tokenService;

  @Autowired
  private AuthenticationManager authenticationManager;

  public AutenticacaoService(UsuarioRepository usuarioRepository, TokenService tokenService) {
    this.usuarioRepository = usuarioRepository;
    this.tokenService = tokenService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return usuarioRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(""));
  }

  public TokenDto autenticar(LoginFormDto dto) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());
    authentication = authenticationManager.authenticate(authentication);

    return new TokenDto(tokenService.gerarToken(authentication));
  }

}
