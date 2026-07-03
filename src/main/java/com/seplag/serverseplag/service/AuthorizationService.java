package com.seplag.serverseplag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.seplag.serverseplag.usuarios.IUsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService {
  
  @Autowired
  private IUsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{

    return usuarioRepository.findByLogin(login);

  }
  
}
