package com.moto.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.moto.service.entidades.Moto;
import com.moto.service.repositorio.MotoRepository;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(MotoRepository motoRepository ) {

    return args -> {

        motoRepository.save(new Moto("Honda", "CBR", 1));
        motoRepository.save(new Moto("Honda", "CB", 1));
        motoRepository.save(new Moto("Honda", "CBX", 1));

        motoRepository.save(new Moto("Yamaha", "FZ", 2));
        motoRepository.save(new Moto("Yamaha", "FZS", 2));
        motoRepository.save(new Moto("Yamaha", "FZ16", 2));

        motoRepository.save(new Moto("Suzuki", "GSX", 3));
        motoRepository.save(new Moto("Suzuki", "GS", 3));
        motoRepository.save(new Moto("Suzuki", "GSXR", 3));

        motoRepository.findAll().forEach(order -> {
            log.info("Preloaded " + order);
        });
      
    };
  }
}