package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.ClienteRegistroRequest;
import edu.pe.demale.demale_proyecto.dto.ClienteResponse;
import edu.pe.demale.demale_proyecto.models.*; // Asegúrate de que este import apunte a tu carpeta 'models'
import edu.pe.demale.demale_proyecto.repositories.*; // Asegúrate de que este import apunte a tu carpeta 'repository'
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service // Marca esta clase como un componente de servicio de Spring
public class ClienteService {

    // Inyección de dependencias de los repositorios
    private final PersonaRepository personaRepository;
    private final ClienteRepository clienteRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final TipoClienteRepository tipoClienteRepository;
    private final DistritoRepository distritoRepository;
    private final TipoViaRepository tipoViaRepository;

    @Autowired // Constructor para inyección de dependencias
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

    // Método para registrar un nuevo cliente
    @Transactional // Asegura que toda la operación sea atómica (commit o rollback total)
    public ClienteResponse registrarCliente(ClienteRegistroRequest request) {
        System.out.println("DEBUG: Numero de documento recibido en el servicio: " + request.getNumeroDocumento());
        // 1. Validar y obtener TipoDocumento
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(request.getIdTipoDoc())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de documento no encontrado con ID: " + request.getIdTipoDoc()));

        // 2. Validar que el número de documento no exista ya
        // Esta validación debe considerar si es persona natural o jurídica
        Optional<Persona> existingPersona = personaRepository.findByNumeroDocumento(request.getNumeroDocumento());
        if (existingPersona.isPresent()) {
            throw new RuntimeException(
                    "Ya existe una persona registrada con el número de documento: " + request.getNumeroDocumento());
        }

        // 3. Crear y guardar Persona
        Persona persona = new Persona();
        persona.setTipoDocumento(tipoDocumento);
        persona.setNumeroDocumento(request.getNumeroDocumento());
        persona.setTelefono(request.getTelefono());
        persona.setCorreo(request.getCorreo());

        // Lógica condicional para Nombres/Apellidos vs. Razón Social
        // Aquí se asume que:
        // ID 1 es para DNI (Persona Natural)
        // ID 2 es para RUC (Persona Jurídica)
        // Por favor, verifica que estos IDs coincidan con los de tu base de datos.
        if (tipoDocumento.getIdTipoDoc().equals(1) || tipoDocumento.getIdTipoDoc().equals(5)) { // Si es DNI (Persona
                                                                                                // Natural)
            persona.setNombres(request.getNombres());
            persona.setApellidos(request.getApellidos());
            persona.setRazonSocial(null); // Asegurarse de que sea nulo para personas naturales
        } else if (tipoDocumento.getIdTipoDoc().equals(2)) { // Si es RUC (Persona Jurídica)
            persona.setRazonSocial(request.getRazonSocial());
            persona.setNombres(null); // Asegurarse de que sea nulo para personas jurídicas
            persona.setApellidos(null); // Asegurarse de que sea nulo para personas jurídicas
        } else {
            throw new RuntimeException("Tipo de documento no soportado o ID desconocido para registro: "
                    + tipoDocumento.getNombreDoc() + " (ID: " + tipoDocumento.getIdTipoDoc() + ")");
        }

        // Manejar campos de dirección opcionales
        if (request.getIdDistrito() != null) {
            Distrito distrito = distritoRepository.findById(request.getIdDistrito())
                    .orElseThrow(
                            () -> new RuntimeException("Distrito no encontrado con ID: " + request.getIdDistrito()));
            persona.setDistrito(distrito);
        } else {
            persona.setDistrito(null); // Asegurarse de que sea null si no se envió ID de distrito
        }

        if (request.getIdTipoVia() != null) {
            TipoVia tipoVia = tipoViaRepository.findById(request.getIdTipoVia())
                    .orElseThrow(
                            () -> new RuntimeException("Tipo de vía no encontrado con ID: " + request.getIdTipoVia()));
            persona.setTipoVia(tipoVia);
        } else {
            persona.setTipoVia(null); // Asegurarse de que sea null si no se envió ID de tipo de vía
        }

        persona.setDireccion(request.getDireccion()); // Ahora se mapea directamente si viene o es null
        persona.setNMunicipal(request.getNMunicipal()); // Ahora se mapea directamente si viene o es null

        Persona personaGuardada = personaRepository.save(persona);

        // 4. Validar y obtener TipoCliente
        TipoCliente tipoCliente = tipoClienteRepository.findById(request.getIdTipoCliente())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de cliente no encontrado con ID: " + request.getIdTipoCliente()));

        // 5. Crear y guardar Cliente
        Cliente cliente = new Cliente();
        cliente.setPersona(personaGuardada);
        cliente.setTipoCliente(tipoCliente);
        cliente.setFechaRegistro(LocalDate.now()); // Fecha actual de registro
        cliente.setEstado(true); // Cliente activo por defecto

        Cliente clienteGuardado = clienteRepository.save(cliente);
        System.out.println("nMunicipal a guardar: " + persona.getNMunicipal());
        // 6. Construir y devolver la respuesta
        return mapClienteToClienteResponse(clienteGuardado);
    }

    // Método auxiliar para mapear Cliente a ClienteResponse
    private ClienteResponse mapClienteToClienteResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setIdCliente(cliente.getIdCliente());

        response.setTipoDocumentoNombre(cliente.getPersona().getTipoDocumento().getNombreDoc());

        if (cliente.getPersona().getTipoDocumento().getIdTipoDoc().equals(1)
                || cliente.getPersona().getTipoDocumento().getIdTipoDoc().equals(5)) { // DNI
            response.setNombres(cliente.getPersona().getNombres());
            response.setApellidos(cliente.getPersona().getApellidos());
            response.setRazonSocial(null); // Asegurarse de que sea nulo en la respuesta
        } else if (cliente.getPersona().getTipoDocumento().getIdTipoDoc().equals(2)) { // RUC
            response.setRazonSocial(cliente.getPersona().getRazonSocial());
            response.setNombres(null); // Asegurarse de que sea nulo en la respuesta
            response.setApellidos(null); // Asegurarse de que sea nulo en la respuesta
        } else {
            // Para otros tipos, podrías decidir qué mostrar o dejar ambos nulos/vacíos
            response.setNombres(null);
            response.setApellidos(null);
            response.setRazonSocial(null);
        }

        response.setNumeroDocumento(cliente.getPersona().getNumeroDocumento());
        response.setTelefono(cliente.getPersona().getTelefono());
        response.setCorreo(cliente.getPersona().getCorreo());

        // Manejo de campos de dirección que pueden ser nulos
        if (cliente.getPersona().getDistrito() != null) {
            response.setDistritoNombre(cliente.getPersona().getDistrito().getNombreDistrito());
            // <<--- AÑADIDO: Mapear el ID del Distrito --->>
            response.setIdDistrito(cliente.getPersona().getDistrito().getIdDistrito());
        } else {
            response.setDistritoNombre(null); // Opcional: asegurar que el nombre sea null si no hay distrito
            response.setIdDistrito(null); // Asegurar que el ID sea null si no hay distrito
        }

        if (cliente.getPersona().getTipoVia() != null) {
            response.setTipoViaNombre(cliente.getPersona().getTipoVia().getNombreTipoVia());
            // <<--- AÑADIDO: Mapear el ID del Tipo de Vía --->>
            response.setIdTipoVia(cliente.getPersona().getTipoVia().getIdTipoVia());
        } else {
            response.setTipoViaNombre(null); // Opcional: asegurar que el nombre sea null si no hay tipo de vía
            response.setIdTipoVia(null); // Asegurar que el ID sea null si no hay tipo de vía
        }

        // <<--- AÑADIDO: Mapear la Dirección y N° Municipal directos --->>
        response.setDireccion(cliente.getPersona().getDireccion());
        response.setNMunicipal(cliente.getPersona().getNMunicipal());

        // Construir dirección completa
        StringBuilder fullAddress = new StringBuilder();
        if (cliente.getPersona().getTipoVia() != null && cliente.getPersona().getTipoVia().getNombreTipoVia() != null
                && !cliente.getPersona().getTipoVia().getNombreTipoVia().isEmpty()) {
            fullAddress.append(cliente.getPersona().getTipoVia().getNombreTipoVia()).append(" ");
        }
        if (cliente.getPersona().getDireccion() != null && !cliente.getPersona().getDireccion().isEmpty()) {
            fullAddress.append(cliente.getPersona().getDireccion());
        }
        if (cliente.getPersona().getNMunicipal() != null && !cliente.getPersona().getNMunicipal().isEmpty()) {
            fullAddress.append(" N° ").append(cliente.getPersona().getNMunicipal());
        }
        response.setDireccionCompleta(fullAddress.toString().trim());

        response.setTipoClienteNombre(cliente.getTipoCliente().getTipoCliente());
        // <<--- AÑADIDO: Mapear el ID del Tipo de Cliente --->>
        response.setIdTipoCliente(cliente.getTipoCliente().getIdTipoCliente());

        response.setFechaRegistro(cliente.getFechaRegistro());
        response.setEstado(cliente.getEstado());
        return response;
    }

    // Puedes añadir más métodos al servicio (ej. buscarClientePorId,
    // actualizarCliente, etc.)
}