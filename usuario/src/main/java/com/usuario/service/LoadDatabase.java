package com.usuario.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.repository.UsuarioRepository;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(UsuarioRepository usuarioRepository ) {

    return args -> {

      usuarioRepository.save(new Usuario(1, "Juan Perez", "juan@mail.com"));
      usuarioRepository.save(new Usuario(2, "Maria Perez", "maria@mail.com"));
      usuarioRepository.save(new Usuario(3, "Pedro Perez", "pedro@mail.com"));

      usuarioRepository.findAll().forEach(order -> {
        log.info("Preloaded " + order);
      });
      
    };
  }
}