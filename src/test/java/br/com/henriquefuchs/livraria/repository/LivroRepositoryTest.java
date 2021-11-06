package br.com.henriquefuchs.livraria.repository;

import br.com.henriquefuchs.livraria.dto.RelatorioLivroPorAutorDto;
import br.com.henriquefuchs.livraria.model.Autor;
import br.com.henriquefuchs.livraria.model.Livro;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LivroRepositoryTest {

  @Autowired
  private LivroRepository livroRepository;
  @Autowired
  private TestEntityManager testEntityManager;

  private Autor getAutor() {
    return new Autor("Autor 01", "autor@email.com", LocalDate.parse("2000-01-01"), "Escrevo livros");
  }

  @Test
  void deveriaCadastrarUmLivro() {
    Autor autor = getAutor();
    testEntityManager.persist(autor);
    Livro livro = new Livro("Livro teste", LocalDate.now(), 300, autor);
    testEntityManager.persist(livro);

    Assertions.assertThat(livroRepository.findAll().contains(livro)).isTrue();
  }

  @Test
  void deveriaListarLivros() {
    Autor autor = getAutor();
    testEntityManager.persist(autor);

    Livro livro1 = new Livro("Livro teste 1", LocalDate.parse("2000-01-01"), 300, autor);
    testEntityManager.persist(livro1);
    Livro livro2 = new Livro("Livro teste 2", LocalDate.parse("2000-02-02"), 301, autor);
    testEntityManager.persist(livro2);
    Livro livro3 = new Livro("Livro teste 3", LocalDate.parse("2000-03-03"), 302, autor);
    testEntityManager.persist(livro3);

    List<Livro> livros = livroRepository.findAll();
    Assertions.assertThat(livros).hasSize(3)
      .extracting(Livro::getTitulo, Livro::getDataDeLancamento, Livro::getNumeroDePaginas, Livro::getAutor)
      .containsExactlyInAnyOrder(
        Assertions.tuple("Livro teste 1", LocalDate.parse("2000-01-01"), 300, autor),
        Assertions.tuple("Livro teste 2", LocalDate.parse("2000-02-02"), 301, autor),
        Assertions.tuple("Livro teste 3", LocalDate.parse("2000-03-03"), 302, autor)
      );
  }

  @Test
  void deveriaRetornarRelatorioLivrosPorAutor() {
    Autor autor1 = new Autor("Autor 01", "autor01@email.com", LocalDate.parse("1900-01-01"), "Escrevo livros 01");
    testEntityManager.persist(autor1);
    Autor autor2 = new Autor("Autor 02", "autor02@email.com", LocalDate.parse("1900-01-02"), "Escrevo livros 02");
    testEntityManager.persist(autor2);
    Autor autor3 = new Autor("Autor 03", "autor03@email.com", LocalDate.parse("1900-01-03"), "Escrevo livros 03");
    testEntityManager.persist(autor3);

    Livro livro1 = new Livro("Livro teste 1", LocalDate.parse("2000-01-01"), 300, autor1);
    testEntityManager.persist(livro1);
    Livro livro2 = new Livro("Livro teste 2", LocalDate.parse("2000-02-02"), 301, autor2);
    testEntityManager.persist(livro2);
    Livro livro3 = new Livro("Livro teste 3", LocalDate.parse("2000-03-03"), 302, autor3);
    testEntityManager.persist(livro3);
    Livro livro4 = new Livro("Livro teste 4", LocalDate.parse("2000-04-04"), 303, autor1);
    testEntityManager.persist(livro4);

    List<RelatorioLivroPorAutorDto> relatorioLivroPorAutorDto = livroRepository.relatorioLivroPorAutor();
    Assertions.assertThat(relatorioLivroPorAutorDto).hasSize(3)
      .extracting(RelatorioLivroPorAutorDto::getNomeDoAutor, RelatorioLivroPorAutorDto::getQuantidadeDeLivros, RelatorioLivroPorAutorDto::getPercentual)
      .containsExactlyInAnyOrder(
        Assertions.tuple("Autor 01", 2L, new BigDecimal("0.005")),
        Assertions.tuple("Autor 02", 1L, new BigDecimal("0.0025")),
        Assertions.tuple("Autor 03", 1L, new BigDecimal("0.0025"))
      );
  }

  @Test
  void deveriaAtualizarUmLivro() {
    Autor autor = getAutor();
    testEntityManager.persist(autor);

    Livro livro = new Livro("Livro teste", LocalDate.parse("2000-01-01"), 300, autor);
    testEntityManager.persist(livro);

    livro.atualizarInformacoes("Livro editado teste", LocalDate.parse("2000-02-01"), 350, autor);
    testEntityManager.merge(livro);

    List<Livro> livros = livroRepository.findAll();
    Assertions.assertThat(livros).hasSize(1).extracting(Livro::getTitulo, Livro::getDataDeLancamento, Livro::getNumeroDePaginas, Livro::getAutor)
      .containsExactlyInAnyOrder(Assertions.tuple("Livro editado teste", LocalDate.parse("2000-02-01"), 350, autor));

  }

  @Test
  void deveriaRemoverUmLivro() {
    Autor autor = getAutor();
    testEntityManager.persist(autor);

    Livro livro = new Livro("Livro teste", LocalDate.parse("2000-01-01"), 300, autor);
    testEntityManager.persist(livro);

    testEntityManager.remove(livro);

    Assertions.assertThat(livroRepository.findAll()).isEmpty();
  }

  @Test
  void deveriaEncontrarUmLivroPorId() {
    Autor autor = getAutor();
    testEntityManager.persist(autor);

    Livro livro = new Livro("Livro teste", LocalDate.parse("2000-01-01"), 300, autor);
    Long livroId = testEntityManager.persistAndGetId(livro, Long.class);

    Assertions.assertThat(livroRepository.getById(livroId)).isNotNull();
  }

}
