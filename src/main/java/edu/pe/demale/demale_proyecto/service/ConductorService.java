// src/main/java/edu/pe/demale/demale_proyecto/service/ConductorService.java
package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.ConductorDropdownDto;
import edu.pe.demale.demale_proyecto.dto.ConductorRequestDto;
import edu.pe.demale.demale_proyecto.dto.ConductorResponseDto;
import edu.pe.demale.demale_proyecto.exception.DuplicateResourceException;
import edu.pe.demale.demale_proyecto.exception.ResourceNotFoundException;
import edu.pe.demale.demale_proyecto.models.*;
import edu.pe.demale.demale_proyecto.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConductorService {

    private final ConductorRepository conductorRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final DistritoRepository distritoRepository;
    private final TipoViaRepository tipoViaRepository;

    @Autowired
    public ConductorService(
        ConductorRepository conductorRepository,
        PersonaRepository personaRepository,
        RolRepository rolRepository,
        TipoDocumentoRepository tipoDocumentoRepository,
        DistritoRepository distritoRepository,
        TipoViaRepository tipoViaRepository
    ) {
        this.conductorRepository = conductorRepository;
        this.personaRepository = personaRepository;
        this.rolRepository = rolRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.distritoRepository = distritoRepository;
        this.tipoViaRepository = tipoViaRepository;
    }

    public List<ConductorDropdownDto> obtenerTodosLosConductoresParaDropdown() {
        List<Conductor> conductores = conductorRepository.findAll();
        return conductores.stream()
                .map(this::mapToDropdownDto)
                .collect(Collectors.toList());
    }

    private ConductorDropdownDto mapToDropdownDto(Conductor conductor) {
        ConductorDropdownDto dto = new ConductorDropdownDto();
        dto.setIdConductor(conductor.getPersona().getIdPersona());

        Persona persona = conductor.getPersona();
        if (persona != null) {
            if (persona.getRazonSocial() != null && !persona.getRazonSocial().trim().isEmpty()) {
                dto.setNombreCompleto(persona.getRazonSocial());
            } else {
                String nombres = persona.getNombres() != null ? persona.getNombres() : "";
                String apellidos = persona.getApellidos() != null ? persona.getApellidos() : "";
                dto.setNombreCompleto((nombres + " " + apellidos).trim());
            }
        } else {
            dto.setNombreCompleto("Conductor Desconocido");
        }
        return dto;
    }

    /**
     * Registra un nuevo conductor y la persona asociada.
     * Lanza DuplicateResourceException si el DNI o la Licencia ya existen.
     * Lanza ResourceNotFoundException si TipoDocumento o Rol no se encuentran.
     * @param dto El ConductorRequestDto que contiene los datos para el nuevo conductor.
     * @throws DuplicateResourceException si se encuentra un DNI o Licencia duplicados.
     * @throws ResourceNotFoundException si TipoDocumento o Rol no se encuentran.
     */
    @Transactional
    public void registrarConductor(ConductorRequestDto dto) {
        // Verifica si hay un DNI duplicado antes de guardar la persona
        if (personaRepository.findByNumeroDocumento(dto.numeroDocumento).isPresent()) {
            throw new DuplicateResourceException("Ya existe una persona con el número de documento " + dto.numeroDocumento);
        }

        // Verifica si hay una Licencia duplicada antes de guardar el conductor
        if (conductorRepository.findByLicencia(dto.licencia).isPresent()) {
            throw new DuplicateResourceException("Ya existe un conductor con la licencia " + dto.licencia);
        }

        Persona persona = new Persona();
        persona.setNombres(dto.nombres);
        persona.setApellidos(dto.apellidos);
        persona.setRazonSocial(dto.razonSocial);
        persona.setNumeroDocumento(dto.numeroDocumento);
        persona.setTelefono(dto.telefono);
        persona.setCorreo(dto.correo);
        persona.setDireccion(dto.direccion);
        persona.setNMunicipal(dto.nMunicipal);

        // Usar ResourceNotFoundException para TipoDocumento
        persona.setTipoDocumento(tipoDocumentoRepository.findById(dto.idTipoDoc)
            .orElseThrow(() -> new ResourceNotFoundException("TipoDocumento no encontrado con ID: " + dto.idTipoDoc)));

        if (dto.idDistrito != null) {
            persona.setDistrito(distritoRepository.findById(dto.idDistrito)
                .orElseThrow(() -> new ResourceNotFoundException("Distrito no encontrado con ID: " + dto.idDistrito)));
        }

        if (dto.idTipoVia != null) {
            persona.setTipoVia(tipoViaRepository.findById(dto.idTipoVia)
                .orElseThrow(() -> new ResourceNotFoundException("TipoVia no encontrado con ID: " + dto.idTipoVia)));
        }

        Persona personaGuardada = personaRepository.save(persona);

        // Usar ResourceNotFoundException para Rol
        Rol rolConductor = rolRepository.findById(dto.idRol)
            .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + dto.idRol));

        Conductor conductor = new Conductor();
        conductor.setPersona(personaGuardada);
        conductor.setLicencia(dto.licencia);
        conductor.setRol(rolConductor);

        conductorRepository.save(conductor);
    }

    /**
     * Actualiza un conductor existente y la persona asociada.
     * Lanza DuplicateResourceException si el DNI o la Licencia ya existen para *otro* conductor.
     * Lanza ResourceNotFoundException si el conductor, TipoDocumento, Rol, Distrito o TipoVia no se encuentran.
     * @param id El ID del conductor a actualizar.
     * @param dto El ConductorRequestDto que contiene los datos actualizados.
     * @throws DuplicateResourceException si se encuentra un DNI o Licencia duplicados para otro registro.
     * @throws ResourceNotFoundException si el conductor, TipoDocumento, Rol, Distrito o TipoVia no se encuentran.
     */
    @Transactional
    public void actualizarConductor(Integer id, ConductorRequestDto dto) {
        Conductor conductor = conductorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado con ID: " + id));

        // Check for duplicate DNI on update, excluding the current persona's DNI
        Optional<Persona> existingPersona = personaRepository.findByNumeroDocumento(dto.numeroDocumento);
        if (existingPersona.isPresent() && !existingPersona.get().getIdPersona().equals(conductor.getPersona().getIdPersona())) {
            throw new DuplicateResourceException("Ya existe otra persona con el número de documento " + dto.numeroDocumento);
        }

        // Check for duplicate Licencia on update, excluding the current conductor's licencia
        Optional<Conductor> existingConductor = conductorRepository.findByLicencia(dto.licencia);
        if (existingConductor.isPresent() && !existingConductor.get().getId().equals(id)) {
            throw new DuplicateResourceException("Ya existe otro conductor con la licencia " + dto.licencia);
        }

        Persona persona = conductor.getPersona();
        persona.setNombres(dto.nombres);
        persona.setApellidos(dto.apellidos);
        persona.setNumeroDocumento(dto.numeroDocumento);
        persona.setTelefono(dto.telefono);
        persona.setCorreo(dto.correo);
        persona.setDireccion(dto.direccion);
        persona.setNMunicipal(dto.nMunicipal);
        persona.setRazonSocial(dto.razonSocial);

        persona.setTipoDocumento(tipoDocumentoRepository.findById(dto.idTipoDoc)
            .orElseThrow(() -> new ResourceNotFoundException("TipoDocumento no encontrado con ID: " + dto.idTipoDoc)));

        if (dto.idTipoVia != null) {
            persona.setTipoVia(tipoViaRepository.findById(dto.idTipoVia)
                .orElseThrow(() -> new ResourceNotFoundException("TipoVia no encontrado con ID: " + dto.idTipoVia)));
        } else {
            persona.setTipoVia(null);
        }

        if (dto.idDistrito != null) {
            persona.setDistrito(distritoRepository.findById(dto.idDistrito)
                .orElseThrow(() -> new ResourceNotFoundException("Distrito no encontrado con ID: " + dto.idDistrito)));
        } else {
            persona.setDistrito(null);
        }

        personaRepository.save(persona);

        conductor.setLicencia(dto.licencia);

        if (dto.idRol != null) {
            Rol nuevoRol = rolRepository.findById(dto.idRol)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + dto.idRol));
            conductor.setRol(nuevoRol);
        }

        conductorRepository.save(conductor);
    }

    /**
     * Elimina un conductor y su persona asociada por ID.
     * Lanza ResourceNotFoundException si el conductor no se encuentra.
     * @param id El ID del conductor a eliminar.
     * @throws ResourceNotFoundException si el conductor no se encuentra.
     */
    @Transactional
    public void eliminarConductor(Integer id) {
        Conductor conductor = conductorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado con ID: " + id));

        // Obtener la persona asociada antes de eliminar el conductor
        Persona persona = conductor.getPersona();

        // Eliminar el conductor
        conductorRepository.delete(conductor);

        // Eliminar la persona asociada (si no está asociada a otros registros, dependiendo de tu lógica de negocio)
        // Esto asume que la persona solo existe para este conductor.
        // Si una persona puede ser otras cosas (cliente, etc.), esta lógica debería ser más compleja.
        personaRepository.delete(persona);
    }

    public List<ConductorResponseDto> listarConductores() {
        List<Conductor> conductores = conductorRepository.findAll();

        return conductores.stream().map(conductor -> {
            Persona p = conductor.getPersona();
            ConductorResponseDto dto = new ConductorResponseDto();

            dto.idPersona = p.getIdPersona();
            dto.nombres = p.getNombres();
            dto.apellidos = p.getApellidos();
            dto.numeroDocumento = p.getNumeroDocumento();
            dto.tipoDocumento = p.getTipoDocumento().getNombreDoc();
            dto.idTipoDoc = p.getTipoDocumento().getIdTipoDoc();
            dto.celular = p.getTelefono();
            dto.correo = p.getCorreo();
            dto.licencia = conductor.getLicencia();
            dto.rol = conductor.getRol().getNombreRol();
            dto.idRol = conductor.getRol().getIdRol();
            dto.razonSocial = p.getRazonSocial();
            dto.direccion = p.getDireccion();
            dto.nMunicipal = p.getNMunicipal();

            if (p.getTipoVia() != null) {
                dto.tipoVia = p.getTipoVia().getNombreTipoVia();
                dto.idTipoVia = p.getTipoVia().getIdTipoVia();
            } else {
                dto.tipoVia = null;
                dto.idTipoVia = null;
            }

            if (p.getDistrito() != null) {
                dto.idDistrito = p.getDistrito().getIdDistrito();
            } else {
                dto.idDistrito = null;
            }

            return dto;
        }).collect(Collectors.toList());
    }
}
