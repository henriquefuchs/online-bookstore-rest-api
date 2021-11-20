package br.com.henriquefuchs.livraria.repository;

import br.com.henriquefuchs.livraria.dto.RelatorioLivroPorAutorDto;
import br.com.henriquefuchs.livraria.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

  @Query("select new br.com.henriquefuchs.livraria.dto.RelatorioLivroPorAutorDto(" +
    "l.autor.nome, " +
    "count(*), " +
    "count(*) * 0.1 / (select count(*) from Livro l1) * 0.1)" +
    "from Livro l group by l.autor.nome")
  List<RelatorioLivroPorAutorDto> relatorioLivroPorAutor();

}
