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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




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

  @GetMapping("/{id}")
  public ResponseEntity getArtista(@PathVariable String id) {
      ArtistaModel artista = this.artistaService.buscarArtista(id);
      if(artista != null){
        return ResponseEntity.status(200).body(artista);
      }
      else{
        return ResponseEntity.status(404).body("Artista não encontrado ");
      }
  }
  

  @PutMapping("/{id}")
  public ResponseEntity atualizarArtista(@PathVariable String id, @RequestBody ArtistaModel artista) {
      ArtistaModel updtArtista = this.artistaService.atualizarArtista(artista);
      if(updtArtista.getId() != null){
        return ResponseEntity.status(200).body(updtArtista.getId());
      }
      else{
        return ResponseEntity.status(404).body("Artista não encontrado ");
      }
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity deleteArtista(@RequestParam Long id){
    this.artistaService.deleteArtista(id);
    return ResponseEntity.status(200).body( id+" excluido com sucesso");

  }
  
  
}
