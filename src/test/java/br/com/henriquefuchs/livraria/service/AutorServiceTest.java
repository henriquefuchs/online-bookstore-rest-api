package br.com.henriquefuchs.livraria.service;

import br.com.henriquefuchs.livraria.dto.AutorDto;
import br.com.henriquefuchs.livraria.dto.AutorInputDto;
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
    AutorInputDto autorInputDto = new AutorInputDto("Autor", "autor@email.com", LocalDate.now(), "Escrevo livros");

    Autor autor = new Autor(autorInputDto.getNome(), autorInputDto.getEmail(), autorInputDto.getDataDeNascimento(), autorInputDto.getMiniCurriculo());

    Mockito.when(modelMapper.map(autorInputDto, Autor.class)).thenReturn(autor);

    Mockito.when(modelMapper.map(autor, AutorDto.class))
      .thenReturn(new AutorDto(autor.getNome(), autor.getEmail(), autor.getDataDeNascimento(), autor.getMiniCurriculo()));

    AutorDto autorDto = autorService.cadastrar(autorInputDto);

    Mockito.verify(autorRepository).save(Mockito.any());

    assertEquals(autorInputDto.getNome(), autorDto.getNome());
    assertEquals(autorInputDto.getEmail(), autorDto.getEmail());
    assertEquals(autorInputDto.getDataDeNascimento(), autorDto.getDataDeNascimento());
    assertEquals(autorInputDto.getMiniCurriculo(), autorDto.getMiniCurriculo());
  }

}
