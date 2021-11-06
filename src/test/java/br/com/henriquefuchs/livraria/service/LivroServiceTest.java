package br.com.henriquefuchs.livraria.service;

import br.com.henriquefuchs.livraria.dto.AutorDto;
import br.com.henriquefuchs.livraria.dto.LivroDto;
import br.com.henriquefuchs.livraria.dto.LivroFormDto;
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

  private LivroFormDto getTransacaoFormDto() {
    return new LivroFormDto("Livro de Teste", LocalDate.now(), 300, 1L);
  }

  @Test
  void deveriaCadastrarLivro() {
    LivroFormDto livroFormDto = getTransacaoFormDto();

    Autor autor = new Autor(1L, "Autor", "autor@email.com", LocalDate.now(), "Escrevo livros");
    Livro livro = new Livro(livroFormDto.getTitulo(), livroFormDto.getDataDeLancamento(), livroFormDto.getNumeroDePaginas(), autor);

    Mockito.when(modelMapper.map(livroFormDto, Livro.class)).thenReturn(livro);

    Mockito.when(modelMapper.map(livro, LivroDto.class))
      .thenReturn(new LivroDto(livro.getTitulo(), livro.getDataDeLancamento(), livro.getNumeroDePaginas(),
        new AutorDto(autor.getId(), autor.getNome(), autor.getEmail(), autor.getDataDeNascimento(), autor.getMiniCurriculo())));

    LivroDto livroDto = livroService.cadastrar(livroFormDto);

    Mockito.verify(livroRepository).save(Mockito.any());

    assertEquals(livroFormDto.getTitulo(), livroDto.getTitulo());
    assertEquals(livroFormDto.getDataDeLancamento(), livroDto.getDataDeLancamento());
    assertEquals(livroFormDto.getNumeroDePaginas(), livroDto.getNumeroDePaginas());
    assertEquals(livroFormDto.getAutorId(), livroDto.getAutor().getId());
  }

  @Test
  void naoDeveriaCadastrarLivroComAutorInexistente() {
    LivroFormDto livroFormDto = getTransacaoFormDto();
    Mockito.when(autorRepository.getById(livroFormDto.getAutorId())).thenThrow(EntityNotFoundException.class);
    assertThrows(IllegalArgumentException.class, () -> livroService.cadastrar(livroFormDto));
  }

}
