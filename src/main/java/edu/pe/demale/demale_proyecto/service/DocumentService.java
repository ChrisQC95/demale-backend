package edu.pe.demale.demale_proyecto.service; // ¡Ajusta este paquete si es diferente!

import edu.pe.demale.demale_proyecto.dto.CeeResponse;
import edu.pe.demale.demale_proyecto.dto.DniResponse;
import edu.pe.demale.demale_proyecto.dto.DocumentLookupResponse;
import edu.pe.demale.demale_proyecto.dto.RucResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DocumentService {

    private final WebClient webClient;

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

    public DocumentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<DocumentLookupResponse> lookupDocument(String numeroDocumento) {
        numeroDocumento = numeroDocumento.trim();

        DocumentLookupResponse defaultErrorResponse = new DocumentLookupResponse();
        defaultErrorResponse.setNumber(numeroDocumento);
        defaultErrorResponse.setSuccess(false);

        if (numeroDocumento.matches("^\\d{8}$")) {
            return callDniApi(numeroDocumento);
        } else if (numeroDocumento.matches("^\\d{11}$")) {
            return callRucApi(numeroDocumento);
        } else if (numeroDocumento.matches("^[a-zA-Z0-9]{8,15}$")) {
            return callCeeApi(numeroDocumento);
        } else {
            defaultErrorResponse.setErrorMessage(
                    "Formato de número de documento no reconocido o inválido. Ingrese DNI (8 dígitos), RUC (11 dígitos) o Carnet de Extranjería (8-15 caracteres alfanuméricos).");
            return Mono.just(defaultErrorResponse);
        }
    }

    private Mono<DocumentLookupResponse> callDniApi(String dni) {
        return webClient.get()
                .uri(apisNetPeDniUrl + "?numero=" + dni + "&token=" + apisNetPeToken)
                .retrieve()
                .bodyToMono(DniResponse.class)
                .map(dniRes -> {
                    DocumentLookupResponse docRes = new DocumentLookupResponse();
                    docRes.setType("DNI");
                    docRes.setNumber(dniRes.getNumeroDni() != null ? dniRes.getNumeroDni() : dni);
                    docRes.setSuccess(dniRes.getSuccess() != null && dniRes.getSuccess()
                            || dniRes.getNombreCompleto() != null || dniRes.getNombres() != null);
                    if (docRes.isSuccess()) {
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
                .onErrorResume(e -> {
                    DocumentLookupResponse docRes = new DocumentLookupResponse();
                    docRes.setType("DNI");
                    docRes.setNumber(dni);
                    docRes.setSuccess(false);
                    docRes.setErrorMessage("Error de comunicación con la API de DNI: " + e.getMessage());
                    return Mono.just(docRes);
                });
    }

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