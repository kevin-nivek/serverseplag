package com.seplag.serverseplag.albuns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

  @Autowired
  private IAlbumRepository albumRepository;
  /**
   * Criar 
   * atualizar
   * Deletar
   * buscar
   */
  public List<AlbumModel> buscarTodosAlbuns(){
    return albumRepository.findAll();
  }

  public AlbumModel criarAlbum(AlbumModel album){
    return this.albumRepository.save(album);
  }

  public AlbumModel atualizarAlbum(AlbumModel album){
    var existsAlbum = this.albumRepository.findById(album.getId());
    if(existsAlbum != null && !existsAlbum.isEmpty()){
      AlbumModel albumUpdt = this.albumRepository.save(album);
      return albumUpdt;
    }
    else{
      return new AlbumModel();
    }
  }

  public void deleteAlbum(Long id){
    var album = this.albumRepository.findById(id);
    if(album.isPresent()){
      this.albumRepository.delete(album.get());
    }
  }
}
