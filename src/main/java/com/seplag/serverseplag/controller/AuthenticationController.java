package com.seplag.serverseplag.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seplag.serverseplag.service.TokenService;
import com.seplag.serverseplag.usuarios.AuthenticationDTO;
import com.seplag.serverseplag.usuarios.IUsuarioRepository;
import com.seplag.serverseplag.usuarios.UsuarioModel;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200") 
// @CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "https://meuapp.com"})
public class AuthenticationController {
  
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private IUsuarioRepository usuarioRepository;

  @Autowired
  TokenService tokenService;

  @PostMapping("login")
  public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authDTO) {
    
    var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((UsuarioModel) auth.getPrincipal());

    return ResponseEntity.status(200).body(token);
  }

  @PostMapping("register")
  public ResponseEntity register(@RequestBody @Valid UsuarioModel usuario) {

    if(this.usuarioRepository.findByLogin(usuario.getLogin()) != null) {
      return ResponseEntity.badRequest().body("Login already exists");
    }

    String originalPassword = usuario.getPassword();
    String encryptedPassword = new BCryptPasswordEncoder().encode(originalPassword);
    usuario.setPassword(encryptedPassword);

    this.usuarioRepository.save(usuario);

    AuthenticationDTO authDTO = new AuthenticationDTO(usuario.getLogin(), originalPassword);


    return login(authDTO);
  }
  
}
