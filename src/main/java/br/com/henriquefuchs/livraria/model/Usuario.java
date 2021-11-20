package br.com.henriquefuchs.livraria.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USUARIOS")
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "NOME")
  private String nome;

  @Column(name = "LOGIN")
  private String login;

  @Column(name = "SENHA")
  private String senha;

  @Column(name = "EMAIL")
  private String email;

  @ManyToMany
  @JoinTable(name = "PERFIS_USUARIOS", joinColumns = @JoinColumn(name = "USUARIO_ID"), inverseJoinColumns = @JoinColumn(name = "PERFIL_ID"))
  private List<Perfil> perfis = new ArrayList<>();

  public Usuario(String nome, String login, String senha) {
    this.nome = nome;
    this.login = login;
    this.senha = senha;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.perfis;
  }

  @Override
  public String getPassword() {
    return this.senha;
  }

  @Override
  public String getUsername() {
    return this.login;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
      "id = " + id + ", " +
      "nome = " + nome + ", " +
      "login = " + login + ")";
  }

  public void adicionarPerfil(Perfil perfil) {
    this.perfis.add(perfil);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Usuario usuario = (Usuario) o;
    return id != null && Objects.equals(id, usuario.id);
  }

  @Override
  public int hashCode() {
    return 0;
  }

}
