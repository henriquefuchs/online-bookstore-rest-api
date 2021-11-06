package br.com.henriquefuchs.livraria.infra;

import br.com.henriquefuchs.livraria.dto.Erro400Dto;
import br.com.henriquefuchs.livraria.dto.Erro500Dto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratamentoDeErros {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public List<Erro400Dto> tratarErro400(MethodArgumentNotValidException e) {
    return e.getFieldErrors().stream().map(ex -> new Erro400Dto(ex.getField(), ex.getDefaultMessage())).collect(Collectors.toList());
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(code = HttpStatus.FORBIDDEN)
  public String tratarErro403(AccessDeniedException e) {
    return e.getMessage();
  }

  @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public void tratarErro404() {
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public Erro500Dto tratarErro500(Exception e, HttpServletRequest request) {
    return new Erro500Dto(LocalDateTime.now(), e.getClass().getName(), e.getMessage(), request.getRequestURI());
  }

}
