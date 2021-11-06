package br.com.henriquefuchs.livraria.repository;

import br.com.henriquefuchs.livraria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  Optional<Usuario> findByLogin(String login);

  @Query("SELECT u FROM Usuario u JOIN FETCH u.perfis WHERE u.id = :id")
  Optional<Usuario> carregarPorIdComPerfis(Long id);

}
