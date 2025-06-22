package edu.pe.demale.demale_proyecto.controller; // Ajusta este paquete si es diferente

import edu.pe.demale.demale_proyecto.dto.ClienteRegistroRequest;
import edu.pe.demale.demale_proyecto.dto.ClienteResponse;
import edu.pe.demale.demale_proyecto.service.ClienteService;

import edu.pe.demale.demale_proyecto.service.TipoDocumentoService;
import edu.pe.demale.demale_proyecto.service.TipoClienteService;
import edu.pe.demale.demale_proyecto.service.DistritoService;
import edu.pe.demale.demale_proyecto.service.TipoViaService;
import edu.pe.demale.demale_proyecto.models.TipoDocumento;
import edu.pe.demale.demale_proyecto.models.TipoCliente;
import edu.pe.demale.demale_proyecto.models.Distrito;
import edu.pe.demale.demale_proyecto.models.TipoVia;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    private final ClienteService clienteService;
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
        List<TipoDocumento> tipos = tipoDocumentoService.findAll();
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @GetMapping("/tipos-cliente")
    public ResponseEntity<List<TipoCliente>> getAllTiposCliente() {
        List<TipoCliente> tipos = tipoClienteService.findAll();
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @GetMapping("/distritos")
    public ResponseEntity<List<Distrito>> getAllDistritos() {
        List<Distrito> distritos = distritoService.findAll();
        return new ResponseEntity<>(distritos, HttpStatus.OK);
    }

    @GetMapping("/tipos-via")
    public ResponseEntity<List<TipoVia>> getAllTiposVia() {
        List<TipoVia> tipos = tipoViaService.findAll();
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteResponse>> buscarClientes(@RequestParam String query) {
        try {
            List<ClienteResponse> clientes = clienteService.buscarClientes(query);
            return new ResponseEntity<>(clientes, HttpStatus.OK);
        } catch (Exception e) {
            // Un log del error aquí es útil
            System.err.println("Error al buscar clientes: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}