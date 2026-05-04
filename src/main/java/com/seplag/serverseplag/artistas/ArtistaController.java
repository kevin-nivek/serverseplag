package com.seplag.serverseplag.artistas;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/artistas")
public class ArtistaController {
  
  @Autowired
  private ArtistaService artistaService;

  @GetMapping("")
  public ResponseEntity buscarTodosArtistas() {
    List<ArtistaModel> artistas = artistaService.buscarTodosArtistas();
      return ResponseEntity.status(202).body(artistas);
  }

  @PostMapping("")
  public ResponseEntity criarArtista(@RequestBody ArtistaModel artista) {
      //TODO: process POST request
      var newArtista = this.artistaService.criarArtista(artista);
      return ResponseEntity.status(201).body(newArtista);
  }

  @PostMapping("path")
  public String postMethodName(@RequestBody String entity) {
      //TODO: process POST request
      
      return entity;
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity deleteArtista(@RequestParam Long id){
    this.artistaService.deleteArtista(id);
    return ResponseEntity.status(200).body( id+" excluido com sucesso");

  }
  
  
}
