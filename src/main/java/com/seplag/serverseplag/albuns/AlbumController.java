package com.seplag.serverseplag.albuns;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RequestMapping("/albuns")
@RestController
public class AlbumController {
  
  @Autowired
  private AlbumService albumService;

  //Buscar todos os albuns
  @GetMapping("")
  public ResponseEntity getMethodName() {
      List<AlbumModel> albuns = albumService.buscarTodosAlbuns();
      return ResponseEntity.status(202).body(albuns);
  }
  
  //Criar um novo album
  @PostMapping("")
  public ResponseEntity criarAlbum(@RequestBody AlbumModel album) {
    var newAlbum = this.albumService.criarAlbum(album);
    return ResponseEntity.status(201).body(newAlbum);
  }

  //Atualizar um album existente
  @PutMapping("{id}")
  public ResponseEntity atualizarAlbum(@PathVariable Long id, @RequestBody AlbumModel album) {
      AlbumModel updtAlbum = this.albumService.atualizarAlbum(album);
      if(updtAlbum.getId() != null){
        return ResponseEntity.status(200).body(updtAlbum);
      }
      else{
        return ResponseEntity.status(404).body("Album não encontrado ");
      }
  }
  
  //Deletar um album
  @DeleteMapping("/{id}")
  public ResponseEntity deleteAlbum(@PathVariable Long id){
    this.albumService.deleteAlbum(id);
    return  ResponseEntity.status(200).body("Album "+id+" excluido com sucesso");
  }



}
