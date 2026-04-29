package com.seplag.serverseplag.testeServer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/testes-api")
public class TesteServerController {

  @GetMapping("")
  public String testeApi() {
      return "Hello There ⚔️ \n  Willkommen aus Seplag Server! \n  ";
  }
  
}
