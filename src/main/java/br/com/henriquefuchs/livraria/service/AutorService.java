package br.com.henriquefuchs.livraria.service;

import br.com.henriquefuchs.livraria.dto.AtualizacaoAutorInputDto;
import br.com.henriquefuchs.livraria.dto.AutorDetalhadoDto;
import br.com.henriquefuchs.livraria.dto.AutorDto;
import br.com.henriquefuchs.livraria.dto.AutorInputDto;
import br.com.henriquefuchs.livraria.model.Autor;
import br.com.henriquefuchs.livraria.repository.AutorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class AutorService {

  private final AutorRepository autorRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public AutorService(AutorRepository autorRepository, ModelMapper modelMapper) {
    this.autorRepository = autorRepository;
    this.modelMapper = modelMapper;
  }

  public Page<AutorDto> listar(@PageableDefault(size = 20) Pageable pageable) {
    Page<Autor> autores = autorRepository.findAll(pageable);
    return autores.map(a -> modelMapper.map(a, AutorDto.class));
  }

  @Transactional
  public AutorDto cadastrar(AutorInputDto autorInputDto) {
    Autor autor = modelMapper.map(autorInputDto, Autor.class);
    autorRepository.save(autor);
    return modelMapper.map(autor, AutorDto.class);
  }

  @Transactional
  public AutorDto atualizar(AtualizacaoAutorInputDto dto) {
    try {
      Autor autor = autorRepository.getById(dto.getId());
      autor.atualizarInformacoes(dto.getNome(), dto.getEmail(), dto.getDataDeNascimento(), dto.getMiniCurriculo());

      return modelMapper.map(autor, AutorDto.class);

    } catch (EntityNotFoundException e) {
      throw new IllegalArgumentException();
    }
  }

  @Transactional
  public void remover(Long id) {
    autorRepository.deleteById(id);
  }

  public AutorDetalhadoDto detalhar(Long id) {
    Autor autor = autorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    return modelMapper.map(autor, AutorDetalhadoDto.class);
  }

}
