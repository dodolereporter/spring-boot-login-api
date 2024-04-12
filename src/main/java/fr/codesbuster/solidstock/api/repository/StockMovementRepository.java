package fr.codesbuster.solidstock.api.repository;

import fr.codesbuster.solidstock.api.entity.StockMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovementEntity, Long> {
}
