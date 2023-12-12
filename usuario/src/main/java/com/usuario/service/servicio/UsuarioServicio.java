package com.usuario.service.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repository.UsuarioRepository;

@Service
public class UsuarioServicio {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    
    // public List<Carro> getCarros (int usuarioId){
    //     List<Carro> carros = carroFeignClient.getCarros(usuarioId);
    //     return carros;
    // } 

    // public List<Moto> getMotos (int usuarioId){
    //     List<Moto> motos = motoFeignClient.getMotos(usuarioId);
    //     return motos;
    // }


	public List<Carro> getCarros(int usuarioId) {
		List<Carro> carros = restTemplate.getForObject("http://carro-service/carro/usuario/"+usuarioId, List.class);
		return carros;
	}

	public List<Moto> getMotos(int usuarioId) {
		List<Moto> motos = restTemplate.getForObject("http://moto-service/moto/usuario/" + usuarioId, List.class);
		return motos;
	}


    public Carro saveCarro(int usuarioId, Carro carro){
        carro.setUsuarioId(usuarioId);
        Carro nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro;
    }

    public Moto saveMoto(int usuarioId, Moto moto){
        moto.setUsuarioId(usuarioId);
        Moto nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto;
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario getOne(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setEmail(usuario.getEmail());
        return usuarioRepository.save(usuarioExistente);
    }

    public Map<String, Object> getCarrosMotos(int id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        Map<String, Object> map = new HashMap<>();
        if (usuario == null) {
            return map;
        }
        map.put("usuario", usuario);
        List<Carro> carros = carroFeignClient.getCarros(id);
        if ( carros == null || carros.isEmpty()) {
            map.put("carros", "No hay carros");
        } else {
            map.put("carros", carros);
        }
        List<Moto> motos = motoFeignClient.getMotos(id);
        if (motos == null || motos.isEmpty()) {
            map.put("motos", "No hay motos");
        } else {
            map.put("motos", motos);
        }


        return map;
    } 
}
