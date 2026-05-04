package com.seplag.serverseplag.artistas;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "artistas")
public class ArtistaModel {
  
  @Id
  @GeneratedValue(generator = "increment")
  private Long id;

  private String name;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  private LocalDate dataExclusao;

  private Long idUsuarioInclusao;

  private Long idUsuarioAlteracao;
}
