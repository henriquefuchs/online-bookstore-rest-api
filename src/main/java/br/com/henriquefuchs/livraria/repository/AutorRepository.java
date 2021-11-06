package br.com.henriquefuchs.livraria.repository;

import br.com.henriquefuchs.livraria.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {

}
