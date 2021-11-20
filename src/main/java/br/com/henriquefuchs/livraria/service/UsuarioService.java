package br.com.henriquefuchs.livraria.service;

import br.com.henriquefuchs.livraria.dto.UsuarioDto;
import br.com.henriquefuchs.livraria.dto.UsuarioInputDto;
import br.com.henriquefuchs.livraria.infra.EnviadorDeEmail;
import br.com.henriquefuchs.livraria.model.Perfil;
import br.com.henriquefuchs.livraria.model.Usuario;
import br.com.henriquefuchs.livraria.repository.PerfilRepository;
import br.com.henriquefuchs.livraria.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
public class UsuarioService {

  private final ModelMapper modelMapper;
  private final UsuarioRepository usuarioRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final PerfilRepository perfilRepository;

  @Autowired
  private EnviadorDeEmail enviadorDeEmail;

  public UsuarioService(ModelMapper modelMapper, UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PerfilRepository perfilRepository) {
    this.modelMapper = modelMapper;
    this.usuarioRepository = usuarioRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.perfilRepository = perfilRepository;
  }

  public Page<UsuarioDto> listar(Pageable pageable) {
    Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
    return usuarios.map(u -> modelMapper.map(u, UsuarioDto.class));
  }

  @Transactional
  public UsuarioDto cadastrar(UsuarioInputDto dto) {
    Usuario usuario = modelMapper.map(dto, Usuario.class);

    Perfil perfil = perfilRepository.getById(dto.getPerfilId());
    usuario.adicionarPerfil(perfil);

    String senha = new Random().nextInt(999999) + "";

    usuario.setId(null);
    usuario.setSenha(bCryptPasswordEncoder.encode(senha));

    usuarioRepository.save(usuario);

    String destinatario = usuario.getEmail();
    String assunto = "Livraria - Bem vindo(a)";
    String mensagem = String.format("Olá %s!\n\n" +
      "Segue seus dados de acesso ao sistema livraria" +
      "\nLogin: %s" +
      "\nSenha: %s", usuario.getNome(), usuario.getLogin(), senha);
    enviadorDeEmail.enviarEmail(destinatario, assunto, mensagem);

    return modelMapper.map(usuario, UsuarioDto.class);
  }

}
