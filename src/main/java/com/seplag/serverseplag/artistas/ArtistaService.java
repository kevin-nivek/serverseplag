package com.seplag.serverseplag.artistas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistaService {
  
  @Autowired
  private IArtistaRepository artistaRepository;

  public List<ArtistaModel> buscarTodosArtistas(){
      return artistaRepository.findAll();
  }

  public ArtistaModel criarArtista(ArtistaModel artista){
    return this.artistaRepository.save(artista);
  }

  public void deleteArtista(Long id){
    var artista = this.artistaRepository.findById(id);
    if(artista.isPresent()){
      this.artistaRepository.delete(artista.get());
    }
  }
  
}
