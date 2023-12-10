package com.usuario.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.modelos.Moto;

@FeignClient(name = "moto-service", url = "http://localhost:8002")
public interface MotoFeignClient {
    
    @PostMapping("/moto")
    public Moto save(@RequestBody Moto moto);

    @GetMapping("/moto/usuario/{id}")
    public List<Moto> getMotos(@PathVariable int id);
}
