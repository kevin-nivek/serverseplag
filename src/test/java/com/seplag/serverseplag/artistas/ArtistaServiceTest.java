package com.seplag.serverseplag.artistas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.seplag.serverseplag.artistas.ArtistaModel;
import com.seplag.serverseplag.artistas.ArtistaService;
import com.seplag.serverseplag.artistas.IArtistaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Service de Artista")
public class ArtistaServiceTest {
  

    @Mock
    private IArtistaRepository artistaRepository;

    @InjectMocks
    private ArtistaService artistaService;

    private ArtistaModel artista;

    @BeforeEach
    void setUp() {
        artista = new ArtistaModel();
        artista.setId(1L);
        artista.setNome("Serj Tankian");
        artista.setCreatedAt(LocalDateTime.now());
        artista.setUpdatedAt(LocalDateTime.now());
        artista.setIdUsuarioInclusao(1L);
    }

    @Test
    @DisplayName("Deve criar um artista com sucesso")
    void deveCriarArtista() {
        when(artistaRepository.save(any(ArtistaModel.class))).thenReturn(artista);
        
        ArtistaModel resultado = artistaService.criarArtista(artista);
        
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Serj Tankian");
        verify(artistaRepository, times(1)).save(artista);
    }

    // @Test
    // @DisplayName("Deve buscar todos os artistas ativos")
    // void deveBuscarTodosArtistasAtivos() {
    //     List<ArtistaModel> artistas = List.of(artista);
    //     when(artistaRepository.findAllByDataExclusaoIsNull()).thenReturn(artistas);
        
    //     List<ArtistaModel> resultado = artistaService.buscarTodosAtivos();
        
    //     assertThat(resultado).hasSize(1);
    //     verify(artistaRepository, times(1)).findAllByDataExclusaoIsNull();
    // }

    @Test
    @DisplayName("Deve buscar artista por ID com sucesso")
    void deveBuscarArtistaPorId() {
        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artista));
        
        ArtistaModel resultado = artistaService.buscarArtista("1");
        
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Serj Tankian");
        verify(artistaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar artista inexistente")
    void deveLancarExcecaoAoBuscarArtistaInexistente() {
        when(artistaRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> artistaService.buscarArtista("999"))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Artista não encontrado");
    }

    @Test
    @DisplayName("Deve atualizar artista com sucesso")
    void deveAtualizarArtista() {
        ArtistaModel artistaAtualizado = new ArtistaModel();
        artistaAtualizado.setNome("Serj Tankian Atualizado");
        
        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artista));
        when(artistaRepository.save(any(ArtistaModel.class))).thenReturn(artistaAtualizado);
        
        ArtistaModel resultado = artistaService.atualizarArtista( artistaAtualizado);
        
        assertThat(resultado.getNome()).isEqualTo("Serj Tankian Atualizado");
        verify(artistaRepository, times(1)).save(any(ArtistaModel.class));
    }

    @Test
    @DisplayName("Deve realizar soft delete no artista")
    void deveRealizarSoftDelete() {
        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artista));
        
        artistaService.deleteArtista(1L);
        
        assertThat(artista.getDataExclusao()).isNotNull();
        verify(artistaRepository, times(1)).save(artista);
    }
}

