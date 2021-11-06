package br.com.henriquefuchs.livraria.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "LIVROS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "TITULO")
  private String titulo;

  @Column(name = "DATA_LANCAMENTO")
  private LocalDate dataDeLancamento;

  @Column(name = "NUMERO_PAGINAS")
  private Integer numeroDePaginas;

  @ManyToOne
  @JoinColumn(name = "AUTOR")
  private Autor autor;

  public Livro(String titulo, LocalDate dataDeLancamento, Integer numeroDePaginas, Autor autor) {
    this.titulo = titulo;
    this.dataDeLancamento = dataDeLancamento;
    this.numeroDePaginas = numeroDePaginas;
    this.autor = autor;
  }

  public void atualizarInformacoes(String titulo, LocalDate dataDeLancamento, Integer numeroDePaginas, Autor autor) {
    this.titulo = titulo;
    this.dataDeLancamento = dataDeLancamento;
    this.numeroDePaginas = numeroDePaginas;
    this.autor = autor;
  }

}
