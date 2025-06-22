package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.ClienteRegistroRequest;
import edu.pe.demale.demale_proyecto.dto.ClienteResponse;
import edu.pe.demale.demale_proyecto.models.*; // Asegúrate de que este import apunte a tu carpeta 'models'
import edu.pe.demale.demale_proyecto.repositories.*; // Asegúrate de que este import apunte a tu carpeta 'repository'
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final PersonaRepository personaRepository;
    private final ClienteRepository clienteRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final TipoClienteRepository tipoClienteRepository;
    private final DistritoRepository distritoRepository;
    private final TipoViaRepository tipoViaRepository;

    @Autowired
    public ClienteService(PersonaRepository personaRepository,
            ClienteRepository clienteRepository,
            TipoDocumentoRepository tipoDocumentoRepository,
            TipoClienteRepository tipoClienteRepository,
            DistritoRepository distritoRepository,
            TipoViaRepository tipoViaRepository) {
        this.personaRepository = personaRepository;
        this.clienteRepository = clienteRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.tipoClienteRepository = tipoClienteRepository;
        this.distritoRepository = distritoRepository;
        this.tipoViaRepository = tipoViaRepository;
    }

    @Transactional
    public ClienteResponse registrarCliente(ClienteRegistroRequest request) {
        System.out.println("DEBUG: Numero de documento recibido en el servicio: " + request.getNumeroDocumento());
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(request.getIdTipoDoc())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de documento no encontrado con ID: " + request.getIdTipoDoc()));

        Optional<Persona> existingPersona = personaRepository.findByNumeroDocumento(request.getNumeroDocumento());
        if (existingPersona.isPresent()) {
            throw new RuntimeException(
                    "Ya existe una persona registrada con el número de documento: " + request.getNumeroDocumento());
        }

        Persona persona = new Persona();
        persona.setTipoDocumento(tipoDocumento);
        persona.setNumeroDocumento(request.getNumeroDocumento());
        persona.setTelefono(request.getTelefono());
        persona.setCorreo(request.getCorreo());

        if (tipoDocumento.getIdTipoDoc().equals(1) || tipoDocumento.getIdTipoDoc().equals(5)) {
            persona.setNombres(request.getNombres());
            persona.setApellidos(request.getApellidos());
            persona.setRazonSocial(null);
        } else if (tipoDocumento.getIdTipoDoc().equals(2)) {
            persona.setRazonSocial(request.getRazonSocial());
            persona.setNombres(null);
            persona.setApellidos(null);
        } else {
            throw new RuntimeException("Tipo de documento no soportado o ID desconocido para registro: "
                    + tipoDocumento.getNombreDoc() + " (ID: " + tipoDocumento.getIdTipoDoc() + ")");
        }

        if (request.getIdDistrito() != null) {
            Distrito distrito = distritoRepository.findById(request.getIdDistrito())
                    .orElseThrow(
                            () -> new RuntimeException("Distrito no encontrado con ID: " + request.getIdDistrito()));
            persona.setDistrito(distrito);
        } else {
            persona.setDistrito(null);
        }

        if (request.getIdTipoVia() != null) {
            TipoVia tipoVia = tipoViaRepository.findById(request.getIdTipoVia())
                    .orElseThrow(
                            () -> new RuntimeException("Tipo de vía no encontrado con ID: " + request.getIdTipoVia()));
            persona.setTipoVia(tipoVia);
        } else {
            persona.setTipoVia(null);
        }

        persona.setDireccion(request.getDireccion());
        persona.setNMunicipal(request.getNMunicipal());

        Persona personaGuardada = personaRepository.save(persona);

        TipoCliente tipoCliente = tipoClienteRepository.findById(request.getIdTipoCliente())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de cliente no encontrado con ID: " + request.getIdTipoCliente()));

        Cliente cliente = new Cliente();
        cliente.setPersona(personaGuardada);
        cliente.setTipoCliente(tipoCliente);
        cliente.setFechaRegistro(LocalDate.now());
        cliente.setEstado(true);

        Cliente clienteGuardado = clienteRepository.save(cliente);

        return mapClienteToClienteResponse(clienteGuardado);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> buscarClientes(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        List<Cliente> clientes = clienteRepository.searchClientes(query);
        return clientes.stream()
                .map(this::mapClienteToClienteResponse)
                .collect(Collectors.toList());
    }

    private ClienteResponse mapClienteToClienteResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setIdCliente(cliente.getIdCliente());

        if (cliente.getPersona().getTipoDocumento() != null) {
            response.setTipoDocumentoNombre(cliente.getPersona().getTipoDocumento().getNombreDoc());
        }

        if (cliente.getPersona().getRazonSocial() != null && !cliente.getPersona().getRazonSocial().trim().isEmpty()) {
            response.setRazonSocial(cliente.getPersona().getRazonSocial());
            response.setNombres(null);
            response.setApellidos(null);
            response.setNombreCompleto(cliente.getPersona().getRazonSocial());
        } else if (cliente.getPersona().getNombres() != null && !cliente.getPersona().getNombres().trim().isEmpty() &&
                cliente.getPersona().getApellidos() != null && !cliente.getPersona().getApellidos().trim().isEmpty()) {
            response.setNombres(cliente.getPersona().getNombres());
            response.setApellidos(cliente.getPersona().getApellidos());
            response.setRazonSocial(null);
            response.setNombreCompleto(cliente.getPersona().getNombres() + " " + cliente.getPersona().getApellidos());
        } else {
            response.setNombreCompleto("Nombre no disponible");
            response.setNombres(null);
            response.setApellidos(null);
            response.setRazonSocial(null);
        }

        response.setNumeroDocumento(cliente.getPersona().getNumeroDocumento());
        response.setTelefono(cliente.getPersona().getTelefono());
        response.setCorreo(cliente.getPersona().getCorreo());

        if (cliente.getPersona().getDistrito() != null) {
            response.setDistritoNombre(cliente.getPersona().getDistrito().getNombreDistrito());
            response.setIdDistrito(cliente.getPersona().getDistrito().getIdDistrito());
        } else {
            response.setDistritoNombre(null);
            response.setIdDistrito(null);
        }

        if (cliente.getPersona().getTipoVia() != null) {
            response.setTipoViaNombre(cliente.getPersona().getTipoVia().getNombreTipoVia());
            response.setIdTipoVia(cliente.getPersona().getTipoVia().getIdTipoVia());
        } else {
            response.setTipoViaNombre(null);
            response.setIdTipoVia(null);
        }

        response.setDireccion(cliente.getPersona().getDireccion());
        response.setNMunicipal(cliente.getPersona().getNMunicipal());
        response.setTipoClienteNombre(cliente.getTipoCliente().getTipoCliente());
        response.setIdTipoCliente(cliente.getTipoCliente().getIdTipoCliente());
        response.setFechaRegistro(cliente.getFechaRegistro());
        response.setEstado(cliente.getEstado());

        return response;
    }
}