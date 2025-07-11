// src/main/java/edu/pe/demale/demale_proyecto/exception/ResourceNotFoundException.java
package edu.pe.demale.demale_proyecto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada para indicar que un recurso no fue encontrado.
 * Esto resultará automáticamente en un estado HTTP 404 Not Found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // Esta anotación le dice a Spring que devuelva un estado 404 cuando se lanza esta excepción
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}