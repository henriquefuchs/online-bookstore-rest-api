package br.com.henriquefuchs.livraria.infra.security;

import br.com.henriquefuchs.livraria.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

  private final TokenService tokenService;
  private final UsuarioRepository usuarioRepository;
  private final AutenticacaoService autenticacaoService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public SecurityConfigurations(AutenticacaoService autenticacaoService, BCryptPasswordEncoder bCryptPasswordEncoder, TokenService tokenService, UsuarioRepository usuarioRepository) {
    this.autenticacaoService = autenticacaoService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.tokenService = tokenService;
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(autenticacaoService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers(HttpMethod.POST, "/auth").permitAll()
      .antMatchers("/usuarios/**").hasRole("ADMIN")
      .anyRequest().authenticated()
      .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and().csrf().disable()
      .addFilterBefore(new VerificacaoTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
      "/swagger-ui.html", "/webjars/**");
  }

}
