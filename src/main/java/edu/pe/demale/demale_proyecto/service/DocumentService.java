package edu.pe.demale.demale_proyecto.service; // ¡Ajusta este paquete si es diferente!

import edu.pe.demale.demale_proyecto.dto.CeeResponse;
import edu.pe.demale.demale_proyecto.dto.DniResponse;
import edu.pe.demale.demale_proyecto.dto.DocumentLookupResponse;
import edu.pe.demale.demale_proyecto.dto.RucResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono; // Importante para la programación reactiva con WebClient

@Service // Marca esta clase como un componente de servicio de Spring
public class DocumentService {

    private final WebClient webClient; // Instancia de WebClient para hacer llamadas HTTP

    // Inyección de URLs y Tokens desde application.properties usando @Value
    @Value("${apis.net.pe.dni.url}")
    private String apisNetPeDniUrl;

    @Value("${apis.net.pe.ruc.url}")
    private String apisNetPeRucUrl;

    @Value("${apis.net.pe.token}")
    private String apisNetPeToken;

    @Value("${factiliza.cee.url}")
    private String factilizaCeeUrl;

    @Value("${factiliza.token}")
    private String factilizaToken;

    // Constructor que inyecta WebClient.Builder. Spring Boot configura esto
    // automáticamente.
    public DocumentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build(); // Construye una instancia de WebClient
    }

    /**
     * Método principal para buscar información de un documento (DNI, RUC, CEE).
     * Determina el tipo de documento basado en el formato y llama a la API
     * correspondiente.
     *
     * @param numeroDocumento El número del documento a consultar.
     * @return Un Mono que emitirá un DocumentLookupResponse con la información
     *         encontrada o un error.
     */
    public Mono<DocumentLookupResponse> lookupDocument(String numeroDocumento) {
        numeroDocumento = numeroDocumento.trim(); // Limpia espacios en blanco al inicio/final

        // Objeto de respuesta por defecto en caso de formato no reconocido o validación
        // inicial
        DocumentLookupResponse defaultErrorResponse = new DocumentLookupResponse();
        defaultErrorResponse.setNumber(numeroDocumento);
        defaultErrorResponse.setSuccess(false);

        // Determinar el tipo de documento basado en el patrón y longitud
        if (numeroDocumento.matches("^\\d{8}$")) { // Patrón para DNI: 8 dígitos numéricos
            return callDniApi(numeroDocumento);
        } else if (numeroDocumento.matches("^\\d{11}$")) { // Patrón para RUC: 11 dígitos numéricos
            return callRucApi(numeroDocumento);
        } else if (numeroDocumento.matches("^[a-zA-Z0-9]{8,15}$")) { // Patrón para CEE: Usualmente 8 a 15 caracteres
                                                                     // alfanuméricos
            // NOTA: El patrón de CEE es más genérico. Si necesitas una validación más
            // estricta
            // para CEE (ej. si siempre empieza con ciertas letras), ajústalo aquí.
            return callCeeApi(numeroDocumento);
        } else {
            defaultErrorResponse.setErrorMessage(
                    "Formato de número de documento no reconocido o inválido. Ingrese DNI (8 dígitos), RUC (11 dígitos) o Carnet de Extranjería (8-15 caracteres alfanuméricos).");
            return Mono.just(defaultErrorResponse); // Retorna un Mono con la respuesta de error de validación
        }
    }

    /**
     * Realiza la llamada a la API de DNI de apis.net.pe.
     */
    private Mono<DocumentLookupResponse> callDniApi(String dni) {
        return webClient.get()
                .uri(apisNetPeDniUrl + "?numero=" + dni + "&token=" + apisNetPeToken)
                .retrieve() // Ejecuta la solicitud HTTP y recupera la respuesta
                .bodyToMono(DniResponse.class) // Convierte el cuerpo de la respuesta JSON a un objeto DniResponse
                .map(dniRes -> { // Mapea el DniResponse a nuestro DTO unificado DocumentLookupResponse
                    DocumentLookupResponse docRes = new DocumentLookupResponse();
                    docRes.setType("DNI");
                    docRes.setNumber(dniRes.getNumeroDni() != null ? dniRes.getNumeroDni() : dni); // Usa el número de
                                                                                                   // la respuesta o el
                                                                                                   // original
                    // Lógica para determinar si la consulta fue exitosa (basado en 'success' o la
                    // presencia de datos)
                    docRes.setSuccess(dniRes.getSuccess() != null && dniRes.getSuccess()
                            || dniRes.getNombreCompleto() != null || dniRes.getNombres() != null);
                    if (docRes.isSuccess()) {
                        // Construye el nombre completo si no viene unificado
                        docRes.setFullName(dniRes.getNombreCompleto() != null ? dniRes.getNombreCompleto()
                                : (dniRes.getNombres() + " " + dniRes.getApellidoPaterno() + " "
                                        + dniRes.getApellidoMaterno()).trim());
                        docRes.setOtherInfo("Apellido Paterno: "
                                + (dniRes.getApellidoPaterno() != null ? dniRes.getApellidoPaterno() : "N/A") +
                                ", Apellido Materno: "
                                + (dniRes.getApellidoMaterno() != null ? dniRes.getApellidoMaterno() : "N/A"));
                    } else {
                        docRes.setErrorMessage(dniRes.getMessage() != null ? dniRes.getMessage()
                                : "No se encontró información para el DNI proporcionado.");
                    }
                    return docRes;
                })
                .onErrorResume(e -> { // Manejo de errores durante la llamada HTTP (ej. red, timeout, 4xx/5xx del
                                      // externo)
                    DocumentLookupResponse docRes = new DocumentLookupResponse();
                    docRes.setType("DNI");
                    docRes.setNumber(dni);
                    docRes.setSuccess(false);
                    docRes.setErrorMessage("Error de comunicación con la API de DNI: " + e.getMessage());
                    return Mono.just(docRes); // Retorna un Mono con la respuesta de error
                });
    }

    /**
     * Realiza la llamada a la API de RUC de apis.net.pe.
     */
    private Mono<DocumentLookupResponse> callRucApi(String ruc) {
        return webClient.get()
                .uri(apisNetPeRucUrl + "?numero=" + ruc + "&token=" + apisNetPeToken)
                .retrieve()
                .bodyToMono(RucResponse.class)
                .map(rucRes -> {
                    DocumentLookupResponse docRes = new DocumentLookupResponse();
                    docRes.setType("RUC");
                    docRes.setNumber(rucRes.getNumeroRuc() != null ? rucRes.getNumeroRuc() : ruc);
                    docRes.setSuccess(
                            rucRes.getSuccess() != null && rucRes.getSuccess() || rucRes.getRazonSocial() != null);
                    if (docRes.isSuccess()) {
                        docRes.setFullName(rucRes.getRazonSocial());
                        docRes.setOtherInfo("Dirección: "
                                + (rucRes.getDireccion() != null ? rucRes.getDireccion() : "N/A") +
                                ", Estado: " + (rucRes.getEstado() != null ? rucRes.getEstado() : "N/A") +
                                ", Condición: " + (rucRes.getCondicion() != null ? rucRes.getCondicion() : "N/A"));
                    } else {
                        docRes.setErrorMessage(rucRes.getMessage() != null ? rucRes.getMessage()
                                : "No se encontró información para el RUC proporcionado.");
                    }
                    return docRes;
                })
                .onErrorResume(e -> {
                    DocumentLookupResponse docRes = new DocumentLookupResponse();
                    docRes.setType("RUC");
                    docRes.setNumber(ruc);
                    docRes.setSuccess(false);
                    docRes.setErrorMessage("Error de comunicación con la API de RUC: " + e.getMessage());
                    return Mono.just(docRes);
                });
    }

    /**
     * Realiza la llamada a la API de Carnet de Extranjería de factiliza.com.
     */
    private Mono<DocumentLookupResponse> callCeeApi(String cee) {
        return webClient.get()
                .uri(factilizaCeeUrl + "?numero=" + cee + "&token=" + factilizaToken)
                .retrieve()
                .bodyToMono(CeeResponse.class)
                .map(ceeRes -> {
                    DocumentLookupResponse docRes = new DocumentLookupResponse();
                    docRes.setType("CEE");
                    docRes.setNumber(ceeRes.getNumeroCee() != null ? ceeRes.getNumeroCee() : cee);
                    docRes.setSuccess(
                            ceeRes.getSuccess() != null && ceeRes.getSuccess() || ceeRes.getNombres() != null);
                    if (docRes.isSuccess()) {
                        docRes.setFullName(ceeRes.getNombreCompleto() != null ? ceeRes.getNombreCompleto()
                                : (ceeRes.getNombres() + " " + ceeRes.getApellidoPaterno() + " "
                                        + ceeRes.getApellidoMaterno()).trim());
                        docRes.setOtherInfo("Apellidos: "
                                + (ceeRes.getApellidoPaterno() != null ? ceeRes.getApellidoPaterno() : "N/A") +
                                " " + (ceeRes.getApellidoMaterno() != null ? ceeRes.getApellidoMaterno() : "N/A"));
                    } else {
                        docRes.setErrorMessage(ceeRes.getMessage() != null ? ceeRes.getMessage()
                                : "No se encontró información para el Carnet de Extranjería proporcionado.");
                    }
                    return docRes;
                })
                .onErrorResume(e -> {
                    DocumentLookupResponse docRes = new DocumentLookupResponse();
                    docRes.setType("CEE");
                    docRes.setNumber(cee);
                    docRes.setSuccess(false);
                    docRes.setErrorMessage(
                            "Error de comunicación con la API de Carnet de Extranjería: " + e.getMessage());
                    return Mono.just(docRes);
                });
    }
}