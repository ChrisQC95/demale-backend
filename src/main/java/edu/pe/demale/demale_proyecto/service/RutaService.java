package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.RutaCreacionDto;
import edu.pe.demale.demale_proyecto.dto.RutaDropdownDto;
import edu.pe.demale.demale_proyecto.dto.RutaListadoDto;
import edu.pe.demale.demale_proyecto.dto.RutaUpdateDto;
import edu.pe.demale.demale_proyecto.models.Ruta;
import edu.pe.demale.demale_proyecto.repositories.RutaRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    @Autowired
    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    public List<RutaDropdownDto> obtenerTodasLasRutasParaDropdown() {
        List<Ruta> rutas = rutaRepository.findAll();
        return rutas.stream()
                .map(this::mapToDropdownDto)
                .collect(Collectors.toList());
    }

    private RutaDropdownDto mapToDropdownDto(Ruta ruta) {
        RutaDropdownDto dto = new RutaDropdownDto();
        dto.setIdRuta(ruta.getIdRuta());
        dto.setDescripcionRuta(ruta.getSerialRuta() + " - " + ruta.getNombreRuta());
        return dto;
    }

    public List<RutaListadoDto> obtenerTodasLasRutas() {
        List<Ruta> rutas = rutaRepository.findAll();
        return rutas.stream()
                .map(this::mapToRutaListadoDto)
                .collect(Collectors.toList());
    }

    public Optional<RutaUpdateDto> obtenerRutaPorId(Integer id) {
        return rutaRepository.findById(id)
                .map(this::mapToRutaUpdateDto);
    }

    @Transactional
    public RutaListadoDto crearRuta(RutaCreacionDto rutaCreacionDto) {
        // Opcional: Validar si SerialRuta ya existe antes de guardar
        if (rutaRepository.findBySerialRuta(rutaCreacionDto.getSerialRuta()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una ruta con este SerialRuta.");
        }
        Ruta ruta = new Ruta();
        ruta.setSerialRuta(rutaCreacionDto.getSerialRuta());
        ruta.setNombreRuta(rutaCreacionDto.getNombreRuta());
        ruta.setGlosa(rutaCreacionDto.getGlosa());

        Ruta rutaGuardada = rutaRepository.save(ruta);
        return mapToRutaListadoDto(rutaGuardada);
    }

    @Transactional
    public RutaListadoDto actualizarRuta(Integer id, RutaUpdateDto rutaUpdateDto) {
        Ruta rutaExistente = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta con ID " + id + " no encontrada."));

        // Validar si el SerialRuta ya existe en otra ruta (si se actualiza)
        if (!rutaExistente.getSerialRuta().equals(rutaUpdateDto.getSerialRuta()) &&
                rutaRepository.findBySerialRuta(rutaUpdateDto.getSerialRuta()).isPresent()) {
            throw new IllegalArgumentException("Ya existe otra ruta con este SerialRuta.");
        }

        rutaExistente.setSerialRuta(rutaUpdateDto.getSerialRuta());
        rutaExistente.setNombreRuta(rutaUpdateDto.getNombreRuta());
        rutaExistente.setGlosa(rutaUpdateDto.getGlosa());

        Ruta rutaActualizada = rutaRepository.save(rutaExistente);
        return mapToRutaListadoDto(rutaActualizada);
    }

    @Transactional
    public void eliminarRuta(Integer id) {
        if (!rutaRepository.existsById(id)) {
            throw new RuntimeException("Ruta con ID " + id + " no encontrada para eliminar.");
        }
        rutaRepository.deleteById(id);
    }

    private RutaListadoDto mapToRutaListadoDto(Ruta ruta) {
        RutaListadoDto dto = new RutaListadoDto();
        dto.setIdRuta(ruta.getIdRuta());
        dto.setSerialRuta(ruta.getSerialRuta());
        dto.setNombreRuta(ruta.getNombreRuta());
        dto.setGlosa(ruta.getGlosa());
        return dto;
    }

    private RutaUpdateDto mapToRutaUpdateDto(Ruta ruta) {
        RutaUpdateDto dto = new RutaUpdateDto();
        dto.setIdRuta(ruta.getIdRuta());
        dto.setSerialRuta(ruta.getSerialRuta());
        dto.setNombreRuta(ruta.getNombreRuta());
        dto.setGlosa(ruta.getGlosa());
        return dto;
    }
}