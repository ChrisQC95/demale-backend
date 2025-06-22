package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.DemaleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IDemaleRepository extends JpaRepository<DemaleModel, Long> {

    List<DemaleModel> findByClientNameContainingIgnoreCaseOrProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
            String clientName, String productName, String category);

    List<DemaleModel> findByStatus(String status);

    List<DemaleModel> findByEntryDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}