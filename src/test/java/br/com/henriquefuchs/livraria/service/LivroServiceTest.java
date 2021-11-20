package br.com.henriquefuchs.livraria.service;

import br.com.henriquefuchs.livraria.dto.AutorDto;
import br.com.henriquefuchs.livraria.dto.LivroDto;
import br.com.henriquefuchs.livraria.dto.LivroInputDto;
import br.com.henriquefuchs.livraria.model.Autor;
import br.com.henriquefuchs.livraria.model.Livro;
import br.com.henriquefuchs.livraria.repository.AutorRepository;
import br.com.henriquefuchs.livraria.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

  @Mock
  private LivroRepository livroRepository;
  @Mock
  private AutorRepository autorRepository;
  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private LivroService livroService;

  private LivroInputDto getLivroInputDto() {
    return new LivroInputDto("Livro de Teste", LocalDate.now(), 300, 1L);
  }

  @Test
  void deveriaCadastrarLivro() {
    LivroInputDto livroInputDto = getLivroInputDto();

    Autor autor = new Autor(1L, "Autor", "autor@email.com", LocalDate.now(), "Escrevo livros");
    Livro livro = new Livro(livroInputDto.getTitulo(), livroInputDto.getDataDeLancamento(), livroInputDto.getNumeroDePaginas(), autor);

    Mockito.when(modelMapper.map(livroInputDto, Livro.class)).thenReturn(livro);

    Mockito.when(modelMapper.map(livro, LivroDto.class))
      .thenReturn(new LivroDto(livro.getTitulo(), livro.getDataDeLancamento(), livro.getNumeroDePaginas(),
        new AutorDto(autor.getId(), autor.getNome(), autor.getEmail(), autor.getDataDeNascimento(), autor.getMiniCurriculo())));

    LivroDto livroDto = livroService.cadastrar(livroInputDto);

    Mockito.verify(livroRepository).save(Mockito.any());

    assertEquals(livroInputDto.getTitulo(), livroDto.getTitulo());
    assertEquals(livroInputDto.getDataDeLancamento(), livroDto.getDataDeLancamento());
    assertEquals(livroInputDto.getNumeroDePaginas(), livroDto.getNumeroDePaginas());
    assertEquals(livroInputDto.getAutorId(), livroDto.getAutor().getId());
  }

  @Test
  void naoDeveriaCadastrarLivroComAutorInexistente() {
    LivroInputDto livroInputDto = getLivroInputDto();
    Mockito.when(autorRepository.getById(livroInputDto.getAutorId())).thenThrow(EntityNotFoundException.class);
    assertThrows(IllegalArgumentException.class, () -> livroService.cadastrar(livroInputDto));
  }

}
