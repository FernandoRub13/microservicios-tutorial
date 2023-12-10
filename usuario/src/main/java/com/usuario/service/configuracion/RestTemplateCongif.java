package com.usuario.service.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateCongif {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}