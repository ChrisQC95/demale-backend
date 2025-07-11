// src/main/java/edu/pe/demale/demale_proyecto/config/CorsConfig.java
package edu.pe.demale.demale_proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica CORS a todos los endpoints de tu API
                        .allowedOrigins("http://localhost:4200") // Permite peticiones desde tu frontend de Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                        .allowedHeaders("*") // Permite todas las cabeceras
                        .allowCredentials(true) // Permite el envío de cookies o cabeceras de autorización
                        .maxAge(3600); // Duración en segundos que el resultado de la petición preflight puede ser cacheado
            }
        };
    }
}
