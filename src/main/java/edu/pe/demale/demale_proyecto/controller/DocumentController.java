package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.DocumentLookupResponse;
import edu.pe.demale.demale_proyecto.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/consultar")
    public Mono<ResponseEntity<DocumentLookupResponse>> consultarDocumento(@RequestParam String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            DocumentLookupResponse errorResponse = new DocumentLookupResponse();
            errorResponse.setSuccess(false);
            errorResponse.setErrorMessage("El número de documento no puede estar vacío.");
            // Retorna una respuesta 400 Bad Request
            return Mono.just(ResponseEntity.badRequest().body(errorResponse));
        }

        return documentService.lookupDocument(numero)
                .map(response -> {
                    if (response.isSuccess()) {
                        return ResponseEntity.ok(response);
                    } else {
                        if (response.getErrorMessage() != null &&
                                (response.getErrorMessage().contains("No se encontró") ||
                                        response.getErrorMessage().contains("no existe") ||
                                        response.getErrorMessage().contains("no válido"))) {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                        }
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                    }
                })
                .defaultIfEmpty(
                        ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new DocumentLookupResponse() {
                            {
                                setSuccess(false);
                                setErrorMessage(
                                        "El servicio externo no respondió o hubo un error inesperado en el backend.");
                            }
                        }))
                .onErrorResume(e -> {
                    DocumentLookupResponse errorResponse = new DocumentLookupResponse();
                    errorResponse.setSuccess(false);
                    errorResponse
                            .setErrorMessage("Error interno del servidor al procesar la solicitud: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
                });
    }
}