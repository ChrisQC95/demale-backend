package edu.pe.demale.demale_proyecto.controller; // Ajusta este paquete si es diferente

import edu.pe.demale.demale_proyecto.dto.ClienteRegistroRequest;
import edu.pe.demale.demale_proyecto.dto.ClienteResponse;
import edu.pe.demale.demale_proyecto.service.ClienteService;

import edu.pe.demale.demale_proyecto.service.TipoDocumentoService;
import edu.pe.demale.demale_proyecto.service.TipoClienteService;
import edu.pe.demale.demale_proyecto.service.DistritoService;
import edu.pe.demale.demale_proyecto.service.TipoViaService;
import edu.pe.demale.demale_proyecto.models.TipoDocumento; // Asume la ubicación de tu entidad
import edu.pe.demale.demale_proyecto.models.TipoCliente; // Asume la ubicación de tu entidad
import edu.pe.demale.demale_proyecto.models.Distrito; // Asume la ubicación de tu entidad
import edu.pe.demale.demale_proyecto.models.TipoVia;

import jakarta.validation.Valid; // Importante para activar la validación en el DTO

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:4200") // Opcional si ya tienes CORS
// global en application.properties
public class ClienteController {

    private final ClienteService clienteService; // Inyección de dependencia del servicio
    private final TipoDocumentoService tipoDocumentoService;
    private final TipoClienteService tipoClienteService;
    private final DistritoService distritoService;
    private final TipoViaService tipoViaService;

    // Constructor para inyectar el ClienteService
    public ClienteController(ClienteService clienteService,
            TipoDocumentoService tipoDocumentoService,
            TipoClienteService tipoClienteService,
            DistritoService distritoService,
            TipoViaService tipoViaService) {
        this.clienteService = clienteService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.tipoClienteService = tipoClienteService;
        this.distritoService = distritoService;
        this.tipoViaService = tipoViaService;
    }

    /**
     * Endpoint para registrar un nuevo cliente.
     * Se accede a través de POST /api/clientes/registrar
     *
     * @param request El DTO ClienteRegistroRequest con los datos del nuevo cliente.
     * @return ResponseEntity con ClienteResponse si el registro es exitoso, o un
     *         error.
     */
    @PostMapping("/registrar")
    public ResponseEntity<ClienteResponse> registrarCliente(@Valid @RequestBody ClienteRegistroRequest request) {
        try {
            ClienteResponse response = clienteService.registrarCliente(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            ClienteResponse errorResponse = new ClienteResponse();

            errorResponse.setNombres(null); // Limpiamos campos para indicar error
            errorResponse.setApellidos(null);
            errorResponse.setNumeroDocumento(null);

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tipos-documento")
    public ResponseEntity<List<TipoDocumento>> getAllTiposDocumento() {
        List<TipoDocumento> tipos = tipoDocumentoService.findAll(); // O el método que uses para traer todos
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @GetMapping("/tipos-cliente")
    public ResponseEntity<List<TipoCliente>> getAllTiposCliente() {
        List<TipoCliente> tipos = tipoClienteService.findAll(); // O el método que uses para traer todos
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @GetMapping("/distritos")
    public ResponseEntity<List<Distrito>> getAllDistritos() {
        List<Distrito> distritos = distritoService.findAll(); // O el método que uses para traer todos
        return new ResponseEntity<>(distritos, HttpStatus.OK);
    }

    @GetMapping("/tipos-via")
    public ResponseEntity<List<TipoVia>> getAllTiposVia() {
        List<TipoVia> tipos = tipoViaService.findAll(); // O el método que uses para traer todos
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }
    // Puedes añadir más endpoints aquí, por ejemplo:
    // @GetMapping("/{id}")
    // public ResponseEntity<ClienteResponse> obtenerClientePorId(@PathVariable Long
    // id) { ... }
}