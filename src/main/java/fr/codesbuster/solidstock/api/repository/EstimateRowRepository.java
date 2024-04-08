package fr.codesbuster.solidstock.api.repository;

import fr.codesbuster.solidstock.api.entity.estimate.EstimateRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstimateRowRepository extends JpaRepository<EstimateRowEntity, Long> {
    List<EstimateRowEntity> findByEstimate_Id(long id);
}
