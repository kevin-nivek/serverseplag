package com.seplag.serverseplag.testeServer;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "teste_server")
public class TesteServerModel {
  
  @Id
  @GeneratedValue(generator = "increment")
  private Long id;

  private String name;
  private String description;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
