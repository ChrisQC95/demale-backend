package edu.pe.demale.demale_proyecto.controller; // ¡Ajusta este paquete si es diferente!

import edu.pe.demale.demale_proyecto.dto.DocumentLookupResponse;
import edu.pe.demale.demale_proyecto.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.CrossOrigin; // Importar CrossOrigin

@RestController // Marca esta clase como un controlador REST
@RequestMapping("/api/documentos") // Define la ruta base para todos los endpoints en este controlador
// @CrossOrigin permite solicitudes desde el frontend Angular.
// La configuración global en application.properties ya lo maneja, pero esta
// anotación
// puede ser usada para control más granular o como respaldo.
// ¡Asegúrate de que http://localhost:4200 sea el URL de tu frontend Angular!
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentController {

    private final DocumentService documentService; // Inyección de dependencia del servicio

    // Constructor para inyectar el DocumentService. Spring lo maneja
    // automáticamente.
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Endpoint para consultar información de un documento (DNI, RUC, CEE).
     * Se accede a través de GET /api/documentos/consultar?numero={numeroDocumento}
     *
     * @param numero El número de documento a consultar, recibido como RequestParam.
     * @return Un Mono que emitirá un ResponseEntity con DocumentLookupResponse y el
     *         estado HTTP.
     */
    @GetMapping("/consultar")
    public Mono<ResponseEntity<DocumentLookupResponse>> consultarDocumento(@RequestParam String numero) {
        // Validación inicial: verifica si el número de documento está vacío
        if (numero == null || numero.trim().isEmpty()) {
            DocumentLookupResponse errorResponse = new DocumentLookupResponse();
            errorResponse.setSuccess(false);
            errorResponse.setErrorMessage("El número de documento no puede estar vacío.");
            // Retorna una respuesta 400 Bad Request
            return Mono.just(ResponseEntity.badRequest().body(errorResponse));
        }

        // Llama al servicio para buscar la información del documento
        return documentService.lookupDocument(numero)
                .map(response -> {
                    // Si el servicio indica éxito, retorna 200 OK
                    if (response.isSuccess()) {
                        return ResponseEntity.ok(response);
                    } else {
                        // Si el servicio indica un error, decide el código de estado HTTP
                        // Si es un error de "no encontrado" de la API externa, retorna 404 Not Found
                        if (response.getErrorMessage() != null &&
                                (response.getErrorMessage().contains("No se encontró") ||
                                        response.getErrorMessage().contains("no existe") ||
                                        response.getErrorMessage().contains("no válido"))) {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                        }
                        // Para otros errores (ej. comunicación con la API externa, o validación de
                        // formato interno en el servicio)
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                    }
                })
                // Manejo si el Mono está vacío (raro con .map(), pero buena práctica con
                // reactivos)
                .defaultIfEmpty(
                        ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new DocumentLookupResponse() {
                            {
                                setSuccess(false);
                                setErrorMessage(
                                        "El servicio externo no respondió o hubo un error inesperado en el backend.");
                            }
                        }))
                // Manejo de cualquier excepción no capturada dentro del flujo reactivo
                .onErrorResume(e -> {
                    DocumentLookupResponse errorResponse = new DocumentLookupResponse();
                    errorResponse.setSuccess(false);
                    errorResponse
                            .setErrorMessage("Error interno del servidor al procesar la solicitud: " + e.getMessage());
                    // Retorna 500 Internal Server Error para excepciones no manejadas
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
                });
    }
}