package com.seplag.serverseplag.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.seplag.serverseplag.usuarios.UsuarioModel;

@Service
public class TokenService {
  @Value("${api.secret.token.secret}")
  private String secret;
  
  public String generateToken(UsuarioModel usuario) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
          .withIssuer("serverseplag-api")
          .withSubject(usuario.getId().toString())
          .withExpiresAt(generateExpirationDate())
          .sign(algorithm);
      return token;
    } catch (JWTCreationException exeption) {
      // TODO: handle exception
      throw new RuntimeException("Erro ao gerar token JWT  ", exeption);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
      .withIssuer("serverseplag-api")
      .build()
      .verify(token)
      .getSubject();
    } catch (JWTVerificationException exeption) {
      // TODO: handle exception
      System.out.println("Invalid Token ");
      System.out.println(exeption);
      return "";

      // throw new RuntimeException("Erro ao validar token JWT", exeption);
    }

  }

  private Instant generateExpirationDate() {
    return Instant.now().plusSeconds(3600); // Token válido por 1 hora
    // return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00"));
  }
}
