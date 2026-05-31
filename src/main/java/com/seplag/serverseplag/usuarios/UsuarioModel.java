package com.seplag.serverseplag.usuarios;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "usuarios")
public class UsuarioModel {

  @Id
  @GeneratedValue(generator = "increment")
  private Long id;

  private String email;

  private String nome;

  private String password;

  @Column(unique = true)
  private String login;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  
}
