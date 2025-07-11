package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.TrabajadorDropdownDto;
import edu.pe.demale.demale_proyecto.dto.TrabajadorRequestDTO;
import edu.pe.demale.demale_proyecto.dto.TrabajadorResponseDTO;
import edu.pe.demale.demale_proyecto.models.Persona;
import edu.pe.demale.demale_proyecto.models.Rol;
import edu.pe.demale.demale_proyecto.models.Trabajador;
import edu.pe.demale.demale_proyecto.models.Distrito;
import edu.pe.demale.demale_proyecto.models.TipoVia;
import edu.pe.demale.demale_proyecto.models.TipoDocumento;
import edu.pe.demale.demale_proyecto.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final DistritoRepository distritoRepository;
    private final TipoViaRepository tipoViaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    
    
    @Autowired
    public TrabajadorService(TrabajadorRepository trabajadorRepository, PersonaRepository personaRepository,
            RolRepository rolRepository, DistritoRepository distritoRepository, TipoViaRepository tipoViaRepository,
            TipoDocumentoRepository tipoDocumentoRepository) {
        this.trabajadorRepository = trabajadorRepository;
        this.personaRepository = personaRepository;
        this.rolRepository = rolRepository;
        this.distritoRepository = distritoRepository;
        this.tipoViaRepository = tipoViaRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }


    //METODOSSSSSSSSSSSSS
    public List<TrabajadorDropdownDto> obtenerTrabajadoresPorRol(Integer idRol) {
        List<Trabajador> trabajadores = trabajadorRepository.findByRol_IdRol(idRol);
        return trabajadores.stream()
                .map(this::mapToDropdownDto)
                .collect(Collectors.toList());
    }

    private TrabajadorDropdownDto mapToDropdownDto(Trabajador trabajador) {
        TrabajadorDropdownDto dto = new TrabajadorDropdownDto();
        dto.setIdTrabajador(trabajador.getIdTrabajador());
        if (trabajador.getPersona() != null) {
            dto.setNombreCompleto(trabajador.getPersona().getNombres() + " " + trabajador.getPersona().getApellidos());
        } else {
            dto.setNombreCompleto("Nombre Desconocido");
        }
        return dto;
    }


    //METODOS QUE YO CREO QUE SON NECESARIOS PARA EL CRUD
    private TrabajadorResponseDTO convertToDto(Trabajador trabajador) {
        TrabajadorResponseDTO dto = new TrabajadorResponseDTO();
        dto.setIdTrabajador(trabajador.getIdTrabajador()); // El ID del trabajador (Integer)

        // Mapear datos de Persona (que está dentro de Trabajador)
        if (trabajador.getPersona() != null) {
            dto.setNombres(trabajador.getPersona().getNombres());
            dto.setApellidos(trabajador.getPersona().getApellidos());
            dto.setRazonSocial(trabajador.getPersona().getRazonSocial());
            dto.setNumeroDocumento(trabajador.getPersona().getNumeroDocumento());
            dto.setCorreo(trabajador.getPersona().getCorreo());
            dto.setTelefono(trabajador.getPersona().getTelefono());
            dto.setDireccion(trabajador.getPersona().getDireccion());
            dto.setNMunicipal(trabajador.getPersona().getNMunicipal());

            // Mapear información de las relaciones de Persona
            if (trabajador.getPersona().getDistrito() != null) {
                dto.setIdDistrito(trabajador.getPersona().getDistrito().getIdDistrito());
                dto.setNombreDistrito(trabajador.getPersona().getDistrito().getNombreDistrito());
            }
            if (trabajador.getPersona().getTipoVia() != null) {
                dto.setIdTipoVia(trabajador.getPersona().getTipoVia().getIdTipoVia());
                dto.setNombreTipoVia(trabajador.getPersona().getTipoVia().getNombreTipoVia());
            }
            if (trabajador.getPersona().getTipoDocumento() != null) {
                dto.setIdTipoDocumento(trabajador.getPersona().getTipoDocumento().getIdTipoDoc());
                dto.setNombreTipoDocumento(trabajador.getPersona().getTipoDocumento().getNombreDoc());
            }
        }

        // Mapear datos de Trabajador
        dto.setFechNacimiento(trabajador.getFechNacimiento());
        dto.setFoto(trabajador.getFoto());

        // Mapear información del Rol
        if (trabajador.getRol() != null) {
            dto.setIdRol(trabajador.getRol().getIdRol());
            dto.setNombreRol(trabajador.getRol().getNombreRol());
        }
        return dto;
    }

    // En edu.pe.demale.demale_proyecto.service.TrabajadorService.java
@Transactional
public TrabajadorResponseDTO createTrabajador(TrabajadorRequestDTO requestDTO) {
    try {
        // 1. Validar y obtener TipoDocumento
        if (requestDTO.getIdTipoDocumento() == null) {
            throw new RuntimeException("El ID de TipoDocumento es obligatorio para la Persona.");
        }
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(requestDTO.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("TipoDocumento no encontrado con ID: " + requestDTO.getIdTipoDocumento()));

        // 2. Crear y guardar la entidad Persona
        Persona persona = new Persona();
        persona.setNombres(requestDTO.getNombres());
        persona.setApellidos(requestDTO.getApellidos());
        persona.setRazonSocial(requestDTO.getRazonSocial());
        persona.setNumeroDocumento(requestDTO.getNumeroDocumento());
        persona.setCorreo(requestDTO.getCorreo());
        persona.setTelefono(requestDTO.getTelefono());
        persona.setDireccion(requestDTO.getDireccion());
        persona.setNMunicipal(requestDTO.getNMunicipal());
        persona.setTipoDocumento(tipoDocumento);

        if (requestDTO.getIdDistrito() != null) {
            Distrito distrito = distritoRepository.findById(requestDTO.getIdDistrito())
                    .orElseThrow(() -> new RuntimeException("Distrito no encontrado con ID: " + requestDTO.getIdDistrito()));
            persona.setDistrito(distrito);
        }
        if (requestDTO.getIdTipoVia() != null) {
            TipoVia tipoVia = tipoViaRepository.findById(requestDTO.getIdTipoVia())
                    .orElseThrow(() -> new RuntimeException("TipoVia no encontrado con ID: " + requestDTO.getIdTipoVia()));
            persona.setTipoVia(tipoVia);
        }

        Persona savedPersona = personaRepository.save(persona); // Guardamos la Persona para obtener su ID

        // 3. Validar y obtener el Rol
        if (requestDTO.getIdRol() == null) {
            throw new RuntimeException("El ID de Rol es obligatorio para un Trabajador.");
        }
        Rol rol = rolRepository.findById(requestDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + requestDTO.getIdRol()));

        // 4. Crear la entidad Trabajador y asignarle la Persona guardada
        Trabajador trabajador = new Trabajador();
        // **ELIMINA O COMENTA ESTA LÍNEA:**
        // trabajador.setIdTrabajador(savedPersona.getIdPersona()); 
        trabajador.setPersona(savedPersona); // Esto es lo único que necesitas para que @MapsId haga su trabajo.

        // 5. Mapear datos específicos de Trabajador desde el DTO
        if (requestDTO.getFechNacimiento() == null) {
            throw new RuntimeException("La Fecha de Nacimiento es obligatoria para un Trabajador.");
        }
        trabajador.setFechNacimiento(requestDTO.getFechNacimiento());
        trabajador.setFoto(requestDTO.getFoto());
        trabajador.setRol(rol);

        // 6. Guardar el trabajador
        Trabajador savedTrabajador = trabajadorRepository.save(trabajador);

        return convertToDto(savedTrabajador);

    } catch (Exception e) {
        System.err.println("Error al crear trabajador: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
}

    // Método para obtener todos los trabajadores
    @Transactional(readOnly = true)
    public List<TrabajadorResponseDTO> getAllTrabajadores() {
        return trabajadorRepository.findAll().stream()
                .map(this::convertToDto) // Convierte cada entidad a DTO
                .collect(Collectors.toList());
    }

    // Método para obtener un trabajador por su ID
    @Transactional(readOnly = true)
    public Optional<TrabajadorResponseDTO> getTrabajadorById(Integer id) { // id es Integer
        return trabajadorRepository.findById(id)
                .map(this::convertToDto); // Si lo encuentra, lo convierte a DTO
    }

    // Método para actualizar un trabajador existente
    @Transactional
    public TrabajadorResponseDTO updateTrabajador(Integer id, TrabajadorRequestDTO requestDTO) { // id es Integer
    try {    
        return trabajadorRepository.findById(id).map(existingTrabajador -> {
            // Obtener y actualizar la Persona asociada
            Persona existingPersona = existingTrabajador.getPersona();
            if (existingPersona == null) {
                throw new RuntimeException("Persona asociada al trabajador no encontrada.");
            }

            // Actualizar campos de Persona
            existingPersona.setNombres(requestDTO.getNombres());
            existingPersona.setApellidos(requestDTO.getApellidos());
            existingPersona.setRazonSocial(requestDTO.getRazonSocial());
            existingPersona.setNumeroDocumento(requestDTO.getNumeroDocumento());
            existingPersona.setCorreo(requestDTO.getCorreo());
            existingPersona.setTelefono(requestDTO.getTelefono());
            existingPersona.setDireccion(requestDTO.getDireccion());
            existingPersona.setNMunicipal(requestDTO.getNMunicipal());

            // Actualizar relaciones de Persona (si los IDs son diferentes o si se pasa null para desvincular)
            if (requestDTO.getIdDistrito() != null) {
                Distrito distrito = distritoRepository.findById(requestDTO.getIdDistrito())
                        .orElseThrow(() -> new RuntimeException("Distrito no encontrado con ID: " + requestDTO.getIdDistrito()));
                existingPersona.setDistrito(distrito);
            } else {
                existingPersona.setDistrito(null); // Si el ID es null, desvincular
            }

            if (requestDTO.getIdTipoVia() != null) {
                TipoVia tipoVia = tipoViaRepository.findById(requestDTO.getIdTipoVia())
                        .orElseThrow(() -> new RuntimeException("TipoVia no encontrado con ID: " + requestDTO.getIdTipoVia()));
                existingPersona.setTipoVia(tipoVia);
            } else {
                existingPersona.setTipoVia(null); // Si el ID es null, desvincular
            }

            // TipoDocumento es obligatorio para Persona
            if (requestDTO.getIdTipoDocumento() != null) {
                TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(requestDTO.getIdTipoDocumento())
                        .orElseThrow(() -> new RuntimeException("TipoDocumento no encontrado con ID: " + requestDTO.getIdTipoDocumento()));
                existingPersona.setTipoDocumento(tipoDocumento);
            } else {
                throw new RuntimeException("El ID de TipoDocumento es obligatorio para la Persona.");
            }

            personaRepository.save(existingPersona); // Guardar los cambios en Persona

            // Actualizar datos específicos de Trabajador
            if (requestDTO.getFechNacimiento() == null) {
                throw new RuntimeException("La Fecha de Nacimiento es obligatoria para un Trabajador.");
            }
            existingTrabajador.setFechNacimiento(requestDTO.getFechNacimiento());
            existingTrabajador.setFoto(requestDTO.getFoto());

            // Actualizar Rol (también es obligatorio para Trabajador)
            if (requestDTO.getIdRol() != null) {
                Rol rol = rolRepository.findById(requestDTO.getIdRol())
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + requestDTO.getIdRol()));
                existingTrabajador.setRol(rol);
            } else {
                throw new RuntimeException("El rol no puede ser nulo para un trabajador.");
            }

            Trabajador updatedTrabajador = trabajadorRepository.save(existingTrabajador);
            return convertToDto(updatedTrabajador);
        }).orElseThrow(() -> new RuntimeException("Trabajador no encontrado con ID: " + id)); // Si el trabajador no existe
        } catch (Exception e) { // Captura cualquier excepción
        System.err.println("Error al actualizar trabajador: " + e.getMessage()); // <-- IMPRIME EL MENSAJE DE ERROR
        e.printStackTrace(); // <-- IMPRIME EL STACK TRACE COMPLETO
        throw e; // Relanza la excepción
         }
    }

    // Método para eliminar un trabajador
    @Transactional
    public void deleteTrabajador(Integer id) { // id es Integer
        Optional<Trabajador> trabajadorOptional = trabajadorRepository.findById(id);
        if (trabajadorOptional.isEmpty()) {
            throw new RuntimeException("Trabajador no encontrado con ID: " + id);
        }

        Trabajador trabajador = trabajadorOptional.get();
        Persona persona = trabajador.getPersona();

        // Primero eliminar el trabajador.
        // Después, eliminar la Persona asociada, ya que el ID es el mismo.
        trabajadorRepository.delete(trabajador);
        personaRepository.delete(persona);
    }
}