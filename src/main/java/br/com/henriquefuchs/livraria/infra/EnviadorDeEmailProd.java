package br.com.henriquefuchs.livraria.infra;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class EnviadorDeEmailProd implements EnviadorDeEmail {

  public final JavaMailSender mailSender;

  public EnviadorDeEmailProd(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  @Async
  public void enviarEmail(String destinatario, String assunto, String mensagem) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(destinatario);
    email.setSubject(assunto);
    email.setText(mensagem);

    mailSender.send(email);
  }

}
