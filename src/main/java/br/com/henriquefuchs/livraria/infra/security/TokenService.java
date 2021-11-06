package br.com.henriquefuchs.livraria.infra.security;

import br.com.henriquefuchs.livraria.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  @Value("${jjwt.secret}")
  public String secret;

  public String gerarToken(Authentication authentication) {
    Usuario usuario = (Usuario) authentication.getPrincipal();

    return Jwts.builder().setSubject(usuario.getId().toString()).signWith(SignatureAlgorithm.HS256, secret).compact();
  }

  public boolean isValido(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Long extrarIdUsuario(String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    return Long.parseLong(claims.getSubject());
  }

}
