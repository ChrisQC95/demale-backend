package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.Rol;
import edu.pe.demale.demale_proyecto.repositories.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }
}