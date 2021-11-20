package br.com.henriquefuchs.livraria.service;

import br.com.henriquefuchs.livraria.dto.AtualizacaoLivroInputDto;
import br.com.henriquefuchs.livraria.dto.LivroDetalhadoDto;
import br.com.henriquefuchs.livraria.dto.LivroDto;
import br.com.henriquefuchs.livraria.dto.LivroInputDto;
import br.com.henriquefuchs.livraria.model.Autor;
import br.com.henriquefuchs.livraria.model.Livro;
import br.com.henriquefuchs.livraria.repository.AutorRepository;
import br.com.henriquefuchs.livraria.repository.LivroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class LivroService {

  private final LivroRepository livroRepository;
  private final AutorRepository autorRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, ModelMapper modelMapper) {
    this.livroRepository = livroRepository;
    this.autorRepository = autorRepository;
    this.modelMapper = modelMapper;
  }

  public Page<LivroDto> listar(@PageableDefault(size = 20) Pageable pageable) {
    Page<Livro> livros = livroRepository.findAll(pageable);
    return livros.map(l -> modelMapper.map(l, LivroDto.class));
  }

  @Transactional
  public LivroDto cadastrar(LivroInputDto livroInputDto) {
    Long autorId = livroInputDto.getAutorId();

    try {
      autorRepository.getById(autorId);

      Livro livro = modelMapper.map(livroInputDto, Livro.class);
      livro.setId(null);

      livroRepository.save(livro);
      return modelMapper.map(livro, LivroDto.class);

    } catch (EntityNotFoundException e) {
      throw new IllegalArgumentException();
    }
  }

  @Transactional
  public LivroDto atualizar(AtualizacaoLivroInputDto dto) {
    try {
      Autor autor = autorRepository.findById(dto.getAutorId()).orElseThrow(EntityNotFoundException::new);
      Livro livro = livroRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);

      livro.atualizarInformacoes(dto.getTitulo(), dto.getDataDeLancamento(), dto.getNumeroDePaginas(), autor);

      return modelMapper.map(livro, LivroDto.class);
    } catch (EntityNotFoundException e) {
      throw new IllegalArgumentException();
    }
  }

  @Transactional
  public void remover(Long id) {
    livroRepository.deleteById(id);
  }

  public LivroDetalhadoDto detalhar(Long id) {
    Livro livro = livroRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    return modelMapper.map(livro, LivroDetalhadoDto.class);
  }

}
