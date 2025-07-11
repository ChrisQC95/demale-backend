// src/main/java/edu/pe/demale/demale_proyecto/exception/DuplicateResourceException.java
package edu.pe.demale.demale_proyecto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to indicate a conflict due to a duplicate resource.
 * This exception will automatically result in an HTTP 409 Conflict status.
 */
@ResponseStatus(HttpStatus.CONFLICT) // This annotation tells Spring to return a 409 status when this exception is thrown
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}


