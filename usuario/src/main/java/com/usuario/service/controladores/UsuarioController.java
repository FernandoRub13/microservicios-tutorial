package com.usuario.service.controladores;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioServicio;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = usuarioServicio.getAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getOne(@PathVariable int id) {
        Usuario usuario = usuarioServicio.getOne(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioServicio.save(usuario));
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "getCarrosFallback")
    @GetMapping("/{id}/carros")
    public ResponseEntity<List<Carro>> getCarros(@PathVariable int id) {
        Usuario usuario = usuarioServicio.getOne(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Carro> carros = usuarioServicio.getCarros(id);
        if (carros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "getMotosFallback")
    @GetMapping("/{id}/motos")
    public ResponseEntity<List<Moto>> getMotos(@PathVariable int id) {
        Usuario usuario = usuarioServicio.getOne(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Moto> motos = usuarioServicio.getMotos(id);
        if (motos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "saveCarroFallback")
    @PostMapping("/{id}/carros")
    public ResponseEntity<Carro> saveCarro(@PathVariable int id, @RequestBody Carro carro) {
        Usuario usuario = usuarioServicio.getOne(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        Carro nuevoCarro = usuarioServicio.saveCarro(id, carro);
        return ResponseEntity.ok(nuevoCarro);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "saveMotoFallback")
    @PostMapping("/{id}/motos")
    public ResponseEntity<Moto> saveMoto(@PathVariable int id, @RequestBody Moto moto) {
        Usuario usuario = usuarioServicio.getOne(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        Moto nuevaMoto = usuarioServicio.saveMoto(id, moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @CircuitBreaker(name = "todosCB", fallbackMethod = "getCarrosMotosFallback")
    @GetMapping("/{id}/carros-motos")
    public ResponseEntity<Object> getCarrosMotos(@PathVariable int id) {
        Usuario usuario = usuarioServicio.getOne(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioServicio.getCarrosMotos(id));
    }

    private ResponseEntity<String> getCarrosFallback(@PathVariable int id, @RequestBody Carro body, Exception e) {
        return ResponseEntity.ok("El servicio de carros no está disponible");
    }

    private ResponseEntity<String> getMotosFallback(@PathVariable int id, @RequestBody Moto body, Exception e) {
        return ResponseEntity.ok("El servicio de motos no está disponible");
    }

    private ResponseEntity<String> saveCarroFallback(@PathVariable int id, @RequestBody Carro body, Exception e) {
        return ResponseEntity.ok("El servicio de carros no está disponible");
    }

    private ResponseEntity<String> saveMotoFallback(@PathVariable int id, @RequestBody Moto body, Exception e) {
        return ResponseEntity.ok("El servicio de motos no está disponible");
    }

    private ResponseEntity<String> getCarrosMotosFallback(@PathVariable int id, @RequestBody Moto body, Exception e) {
        return ResponseEntity.ok("El servicio de carros y motos no está disponible");
    }


}
