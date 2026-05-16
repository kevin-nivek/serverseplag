package com.seplag.serverseplag.albuns;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAlbumRepository extends JpaRepository<AlbumModel, Long> {
  
}
