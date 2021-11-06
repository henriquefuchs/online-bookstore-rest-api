package br.com.henriquefuchs.livraria.repository;

import br.com.henriquefuchs.livraria.model.Autor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AutorRepositoryTest {

  @Autowired
  private AutorRepository autorRepository;
  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  void deveriaCadastrarUmAutor() {
    Autor autor = new Autor("Autor", "autor@email.com", LocalDate.parse("2000-01-01"), "Escrevos livros");
    testEntityManager.persist(autor);
    Assertions.assertThat(autorRepository.findAll().contains(autor)).isTrue();
  }

  @Test
  void deveriaListarAutores() {
    Autor autor1 = new Autor("Autor01", "autor1@email.com", LocalDate.parse("2000-01-01"), "Escrevo livros 1");
    testEntityManager.persist(autor1);
    Autor autor2 = new Autor("Autor02", "autor2@email.com", LocalDate.parse("2000-02-02"), "Escrevo livros 2");
    testEntityManager.persist(autor2);
    Autor autor3 = new Autor("Autor03", "autor3@email.com", LocalDate.parse("2000-03-03"), "Escrevo livros 3");
    testEntityManager.persist(autor3);

    List<Autor> autores = autorRepository.findAll();
    Assertions.assertThat(autores).hasSize(3).
      extracting(Autor::getNome, Autor::getEmail, Autor::getDataDeNascimento, Autor::getMiniCurriculo)
      .containsExactlyInAnyOrder(
        Assertions.tuple("Autor01", "autor1@email.com", LocalDate.parse("2000-01-01"), "Escrevo livros 1"),
        Assertions.tuple("Autor02", "autor2@email.com", LocalDate.parse("2000-02-02"), "Escrevo livros 2"),
        Assertions.tuple("Autor03", "autor3@email.com", LocalDate.parse("2000-03-03"), "Escrevo livros 3")
      );
  }

  @Test
  void deveriaAtualizarUmAutor() {
    Autor autor = new Autor("Autor", "autor@email.com", LocalDate.parse("2000-01-01"), "Escrevo livros");
    testEntityManager.persist(autor);

    autor.atualizarInformacoes("Autor editado", "autoreditado@email.com", LocalDate.parse("2001-01-01"), "Escrevo livros editados");
    testEntityManager.merge(autor);

    List<Autor> autores = autorRepository.findAll();
    Assertions.assertThat(autores).hasSize(1).extracting(Autor::getNome, Autor::getEmail, Autor::getDataDeNascimento, Autor::getMiniCurriculo).
      contains(Assertions.tuple("Autor editado", "autoreditado@email.com", LocalDate.parse("2001-01-01"), "Escrevo livros editados"));
  }

  @Test
  void deveriaRemoverUmAutor() {
    Autor autor = new Autor("Autor", "autor@email.com", LocalDate.parse("2000-01-01"), "Escrevo livros");
    testEntityManager.persistAndGetId(autor);
    testEntityManager.remove(autor);
    Assertions.assertThat(autorRepository.findAll()).isEmpty();
  }

  @Test
  void deveriaEncontrarUmAutorPorId() {
    Autor autor = new Autor("Autor", "autor@email.com", LocalDate.parse("2000-01-01"), "Escrevo livros");
    Long autorId = testEntityManager.persistAndGetId(autor, Long.class);

    Assertions.assertThat(autorRepository.getById(autorId)).isNotNull();
  }


}
