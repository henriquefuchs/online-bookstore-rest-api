package br.com.henriquefuchs.livraria.infra;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "test"})
public class EnviadorDeEmailDev implements EnviadorDeEmail {

  @Override
  @Async
  public void enviarEmail(String destinatario, String assunto, String mensagem) {
    System.out.println("Enviando e-mail:");
    System.out.println("Destinatário: " + destinatario);
    System.out.println("Assunto: " + assunto);
    System.out.println("Mensagem: " + mensagem);
  }

}
