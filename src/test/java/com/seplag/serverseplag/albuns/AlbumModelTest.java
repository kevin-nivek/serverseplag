package com.seplag.serverseplag.albuns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.seplag.serverseplag.artistas.ArtistaModel;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Testes da Entidade Álbum")
public class AlbumModelTest {

    private Validator validator;
    private AlbumModel album;
    private ArtistaModel artista;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        artista = new ArtistaModel();
        artista.setId(1L);
        artista.setNome("Michel Teló");
        
        album = new AlbumModel();
        album.setNome("Bem Sertanejo");
        album.setDataPublicacao(LocalDate.of(2020, 5, 15));
        album.setArtista(artista);
        album.setCreatedAt(LocalDateTime.now());
        album.setUpdatedAt(LocalDateTime.now());
        album.setIdUsuarioInclusao(1L);
    }

    @Test
    @DisplayName("Deve criar um álbum válido")
    void deveCriarAlbumValido() {
        Set<ConstraintViolation<AlbumModel>> violations = validator.validate(album);
        assertTrue(violations.isEmpty());
        
        assertNotNull(album.getCreatedAt());
        assertEquals("Bem Sertanejo", album.getNome());
        assertEquals(LocalDate.of(2020, 5, 15), album.getDataPublicacao());
        assertEquals(artista, album.getArtista());
    }

    @Test
    @DisplayName("Não deve permitir álbum sem nome")
    void naoDevePermitirAlbumSemNome() {
        album.setNome(null);
        
        Set<ConstraintViolation<AlbumModel>> violations = validator.validate(album);
        assertFalse(violations.isEmpty());
    }

    // @Test
    // @DisplayName("Deve permitir upload de múltiplas capas")
    // void devePermitirMultiplasCapas() {
    //     assertNull(album.getCapaUrl());
        
    //     album.setCapasObjectKeys(new ArrayList<>());
    //     album.getCapasObjectKeys().add("album/capa1.jpg");
    //     album.getCapasObjectKeys().add("album/capa2.jpg");
        
    //     assertNotNull(album.getCapasObjectKeys());
    //     assertEquals(2, album.getCapasObjectKeys().size());
    // }
}