package fr.codesbuster.solidstock.api.repository;

import fr.codesbuster.solidstock.api.entity.estimate.EstimateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateRepository extends JpaRepository<EstimateEntity, Long> {
}
