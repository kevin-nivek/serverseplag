package com.seplag.serverseplag.albuns;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.seplag.serverseplag.artistas.ArtistaModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "albums")
public class AlbumModel {
  
  @Id
  @GeneratedValue(generator = "increment")
  private Long id;

  private String nome;

  private LocalDateTime dataPublicacao;

  @ManyToOne
  @JoinColumn(name = "id_artista")
  private ArtistaModel artista;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  private LocalDate dataExclusao;

  private Long idUsuarioInclusao;

  private Long idUsuarioAlteracao;
}
