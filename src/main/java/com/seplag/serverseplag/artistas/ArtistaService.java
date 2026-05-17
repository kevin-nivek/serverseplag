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

  public ArtistaModel buscarArtista(String id){
    var artista = this.artistaRepository.findById(Long.parseLong(id));
    if(artista.isPresent()){
      return artista.get();
    }
    else{
      return new ArtistaModel();
    }
  }

  public ArtistaModel criarArtista(ArtistaModel artista){
    return this.artistaRepository.save(artista);
  }

  public ArtistaModel atualizarArtista(ArtistaModel artista){
    var existsArtista = this.artistaRepository.findById(artista.getId());
    if(existsArtista != null && !existsArtista.isEmpty()){
      ArtistaModel artistaUpdt = this.artistaRepository.save(artista);
      return artistaUpdt;
    }
    else{
      return new ArtistaModel();
    }
  }

  public void deleteArtista(Long id){
    var artista = this.artistaRepository.findById(id);
    if(artista.isPresent()){
      this.artistaRepository.delete(artista.get());
    }
  }
  
}
