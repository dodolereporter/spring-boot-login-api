package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.StockMovementEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StockMovementService {
    StockMovementEntity createStockMovement(StockMovementEntity stockMovementEntity);

    List<StockMovementEntity> getStockMovements();

    ResponseEntity<StockMovementEntity> deleteStockMovement(Long id);
 
    ResponseEntity<StockMovementEntity> enableStockMovement(Long id);

    StockMovementEntity updateStockMovement(StockMovementEntity stockMovementEntity);

    StockMovementEntity getStockMovement(Long id);
}
