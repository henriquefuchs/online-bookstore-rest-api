package br.com.henriquefuchs.livraria.service;

import br.com.henriquefuchs.livraria.dto.AutorDto;
import br.com.henriquefuchs.livraria.dto.AutorFormDto;
import br.com.henriquefuchs.livraria.model.Autor;
import br.com.henriquefuchs.livraria.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

  @Mock
  private AutorRepository autorRepository;
  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private AutorService autorService;

  @Test
  void deveriaCadastrarUmAutor() {
    AutorFormDto autorFormDto = new AutorFormDto("Autor", "autor@email.com", LocalDate.now(), "Escrevo livros");

    Autor autor = new Autor(autorFormDto.getNome(), autorFormDto.getEmail(), autorFormDto.getDataDeNascimento(), autorFormDto.getMiniCurriculo());

    Mockito.when(modelMapper.map(autorFormDto, Autor.class)).thenReturn(autor);

    Mockito.when(modelMapper.map(autor, AutorDto.class))
      .thenReturn(new AutorDto(autor.getNome(), autor.getEmail(), autor.getDataDeNascimento(), autor.getMiniCurriculo()));

    AutorDto autorDto = autorService.cadastrar(autorFormDto);

    Mockito.verify(autorRepository).save(Mockito.any());

    assertEquals(autorFormDto.getNome(), autorDto.getNome());
    assertEquals(autorFormDto.getEmail(), autorDto.getEmail());
    assertEquals(autorFormDto.getDataDeNascimento(), autorDto.getDataDeNascimento());
    assertEquals(autorFormDto.getMiniCurriculo(), autorDto.getMiniCurriculo());
  }

}
