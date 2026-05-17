package com.seplag.serverseplag.artistas;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import com.seplag.serverseplag.artistas.ArtistaModel;

@DisplayName("Testes para a Entidade ArtistaModel")
public class ArtistaModelTest {

  private Validator validator;
  private ArtistaModel artista;

  @BeforeEach
  void setUp(){
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        artista = new ArtistaModel();
        artista.setNome("Serj Tankian");
        artista.setCreatedAt(LocalDateTime.now());
        artista.setUpdatedAt(LocalDateTime.now());
        artista.setIdUsuarioInclusao(1L);
  }

  @Test
  @DisplayName("Deve criar um artista válido")
  void deveCriarArtistaValido() {
      Set<ConstraintViolation<ArtistaModel>> violations = validator.validate(artista);
      assertTrue(violations.isEmpty(), "Não deve haver violações de validação");
      
      assertNotNull(artista.getCreatedAt());
      assertNotNull(artista.getUpdatedAt());
      assertEquals("Serj Tankian", artista.getNome());
  }

  @Test
  @DisplayName("Não deve permitir artista sem nome")
  void naoDevePermitirArtistaSemNome() {
      artista.setNome(null);
      
      Set<ConstraintViolation<ArtistaModel>> violations = validator.validate(artista);
      
      assertFalse(violations.isEmpty());
      assertTrue(violations.stream().anyMatch(v -> 
          v.getPropertyPath().toString().equals("nome")));
  }

  @Test
  @DisplayName("Deve permitir soft delete no artista")
  void devePermitirSoftDelete() {
      assertNull(artista.getDataExclusao());
      
      artista.setDataExclusao(LocalDate.now());
      assertNotNull(artista.getDataExclusao());
      assertEquals(LocalDate.now(), artista.getDataExclusao());
  }

  @Test
  @DisplayName("Deve permitir atualização de usuário")
  void devePermitirAtualizacaoUsuario() {
      artista.setIdUsuarioAlteracao(2L);
      
      assertEquals(2L, artista.getIdUsuarioAlteracao());
  }
}
  


