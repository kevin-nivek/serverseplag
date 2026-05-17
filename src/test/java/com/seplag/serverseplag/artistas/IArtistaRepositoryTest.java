package com.seplag.serverseplag.artistas;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import com.seplag.serverseplag.artistas.ArtistaModel;
import com.seplag.serverseplag.artistas.IArtistaRepository;

@DataJpaTest
@DisplayName("Testes do Repository de Artista")
public class IArtistaRepositoryTest {
  
    @Autowired
    private IArtistaRepository artistaRepository;

    @Autowired
    private TestEntityManager entityManager;

    private ArtistaModel artista1;
    private ArtistaModel artista2;
    private ArtistaModel artista3;

    @BeforeEach
    void setUp() {
        artista1 = criarArtista("Serj Tankian");
        artista2 = criarArtista("Mike Shinoda");
        artista3 = criarArtista("Michel Teló");
        
        entityManager.persist(artista1);
        entityManager.persist(artista2);
        entityManager.persist(artista3);
        entityManager.flush();
    }

    private ArtistaModel criarArtista(String nome) {
        ArtistaModel artista = new ArtistaModel();
        artista.setNome(nome);
        artista.setCreatedAt(LocalDateTime.now());
        artista.setUpdatedAt(LocalDateTime.now());
        artista.setIdUsuarioInclusao(1L);
        return artista;
    }

    @Test
    @DisplayName("Deve salvar um artista")
    void deveSalvarArtista() {
        ArtistaModel novoArtista = criarArtista("Guns N' Roses");
        
        ArtistaModel saved = artistaRepository.save(novoArtista);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNome()).isEqualTo("Guns N' Roses");
    }

    @Test
    @DisplayName("Deve buscar artista por ID")
    void deveBuscarArtistaPorId() {
        Optional<ArtistaModel> found = artistaRepository.findById(artista1.getId());
        
        assertThat(found).isPresent();
        assertThat(found.get().getNome()).isEqualTo("Serj Tankian");
    }


    @Test
    @DisplayName("Deve retornar empty ao buscar artista inexistente")
    void deveRetornarEmptyParaArtistaInexistente() {
        Optional<ArtistaModel> found = artistaRepository.findById(999L);
        
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Deve verificar se artista existe por ID")
    void deveVerificarExistenciaPorId() {
        boolean exists = artistaRepository.existsById(artista1.getId());
        boolean notExists = artistaRepository.existsById(999L);
        
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}
