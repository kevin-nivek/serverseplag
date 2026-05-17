package com.seplag.serverseplag.capaAlbum;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
@Entity(name = "capa_albums")
public class CapaAlbumModel {
  
  @Id
  @GeneratedValue(generator = "increment")
  private Long id;

  private String url;

  private Long idAlbum;

  private String nomeArquivo;

  private String tipoArquivo;

  private Long tamanhoArquivo;

  private Boolean principal = false;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  private Long idUsuarioInclusao;

  private Long idUsuarioAlteracao;

}
