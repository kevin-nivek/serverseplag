package com.seplag.serverseplag.usuarios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Long> {
  
  // UsuarioModel findByLogin(String login);

  UserDetails findByLogin(String login);

  UsuarioModel findById(String login);

}
