package com.seplag.serverseplag.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Long> {
  
  UsuarioModel findByLogin(String login);
}
