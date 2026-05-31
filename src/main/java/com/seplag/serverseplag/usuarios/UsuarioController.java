package com.seplag.serverseplag.usuarios;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/users")
public class UsuarioController {
  
  @Autowired
  private IUsuarioRepository usuarioRepository;

  @PostMapping("/sign-in")
  public ResponseEntity postMethodName(@RequestBody UsuarioModel usuario) {
      var userLogin = this.usuarioRepository.findByLogin(usuario.getLogin());
      if(userLogin != null) {
        return ResponseEntity.badRequest().body("Login already exists");
      }

      var passHashred =  BCrypt.withDefaults().hashToString(12, usuario.getPassword().toCharArray());

      usuario.setPassword(passHashred);

      var userCreated = this.usuarioRepository.save(usuario);
      if(userCreated == null) {
        return ResponseEntity.internalServerError().body("Error creating user");
      }
      
      return ResponseEntity.status(201).body(" Usuario Criado com sucesso");
  }
  

}
