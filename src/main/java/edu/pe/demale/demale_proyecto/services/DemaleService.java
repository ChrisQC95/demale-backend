package edu.pe.demale.demale_proyecto.services; 

import edu.pe.demale.demale_proyecto.models.DemaleModel; 
import edu.pe.demale.demale_proyecto.repositories.IDemaleRepository;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 

import java.time.LocalDateTime; 
import java.util.List; 

@Service 
public class DemaleService {

    @Autowired 
    private IDemaleRepository demaleRepository;

    public List<DemaleModel> getAllProductMovements() {
        return demaleRepository.findAll();
    }
 
    public List<DemaleModel> searchAndFilterProductMovements(String search, String status, LocalDateTime startDate, LocalDateTime endDate) {

        
        if (startDate != null && endDate != null) {
            return demaleRepository.findByEntryDateBetween(startDate, endDate);
        }

        if (status != null && !status.trim().isEmpty()) {
            return demaleRepository.findByStatus(status);
        }

        if (search != null && !search.trim().isEmpty()) {
            return demaleRepository.findByClientNameContainingIgnoreCaseOrProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(search, search, search);
        }

        return demaleRepository.findAll();
    }


    //DATOS DE PRUEBAAAAA
    public void addSampleData() {

        demaleRepository.save(new DemaleModel("C72483181", "Zapatos", "Ropa", LocalDateTime.of(2025, 5, 20, 10, 0), "Lima - S.J.L.", "Puno", "En Proceso", "Eduardo Quispe", "AS-5522"));
        demaleRepository.save(new DemaleModel("C72483181", "Lavadora", "Electrodomesticos", LocalDateTime.of(2025, 5, 15, 9, 30), "Lima - Ate", "Ica", "Finalizado", "Jose Perez", "BE-9946"));
        demaleRepository.save(new DemaleModel("C72483181", "Carros (3)", "Juguetes", LocalDateTime.of(2025, 5, 18, 14, 0), "Lima - Ate", "Arequipa", "Finalizado", "Carlos Romero", "35-6610"));
        demaleRepository.save(new DemaleModel("C71482641", "Snacks", "Comidas", LocalDateTime.of(2025, 5, 25, 11, 0), "Lima -Los Olivos", "Tacna", "En Almacén", "Diego Chavez", "QT-1437"));
        demaleRepository.save(new DemaleModel("C72483182", "Mesa de centro", "Muebles", LocalDateTime.of(2025, 5, 22, 16, 0), "Callao", "Cusco", "En Proceso", "Maria Fernandez", "XY-7890"));
        demaleRepository.save(new DemaleModel("C71482642", "Televisor 55", "Electrodomesticos", LocalDateTime.of(2025, 5, 10, 8, 0), "Lima - San Miguel", "Trujillo", "Finalizado", "Pedro Castillo", "GH-1234"));
    }

    
}