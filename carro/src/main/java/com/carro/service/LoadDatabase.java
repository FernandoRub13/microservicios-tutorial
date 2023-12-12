package com.carro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.carro.service.entidades.Carro;
import com.carro.service.repositorio.CarroRepository;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(CarroRepository carroRepository ) {

    return args -> {

        carroRepository.save(new Carro("Ford", "Fiesta", 1));
        carroRepository.save(new Carro("Ford", "Focus", 1));
        carroRepository.save(new Carro("Ford", "Mustang", 1));

        carroRepository.save(new Carro("Chevrolet", "Spark", 2));
        carroRepository.save(new Carro("Chevrolet", "Sail", 2));
        carroRepository.save(new Carro("Chevrolet", "Camaro", 2));

        carroRepository.save(new Carro("Renault", "Clio", 3));
        carroRepository.save(new Carro("Renault", "Sandero", 3));
        carroRepository.save(new Carro("Renault", "Megane", 3));

        carroRepository.findAll().forEach(order -> {
            log.info("Preloaded " + order);
        });
      
    };
  }
}