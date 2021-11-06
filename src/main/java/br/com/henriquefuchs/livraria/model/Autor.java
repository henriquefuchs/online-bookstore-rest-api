package br.com.henriquefuchs.livraria.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "AUTORES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Autor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "NOME")
  private String nome;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "DATA_NASCIMENTO")
  private LocalDate dataDeNascimento;

  @Column(name = "MINI_CURRICULO")
  private String miniCurriculo;

  public Autor(String nome, String email, LocalDate dataDeNascimento, String miniCurriculo) {
    this.nome = nome;
    this.email = email;
    this.dataDeNascimento = dataDeNascimento;
    this.miniCurriculo = miniCurriculo;
  }

  public void atualizarInformacoes(String nome, String email, LocalDate dataDeNascimento, String miniCurriculo) {
    this.nome = nome;
    this.email = email;
    this.dataDeNascimento = dataDeNascimento;
    this.miniCurriculo = miniCurriculo;
  }

}
