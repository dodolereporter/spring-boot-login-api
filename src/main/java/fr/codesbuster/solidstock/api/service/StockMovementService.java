package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.StockMovementEntity;

import java.util.List;

public interface StockMovementService {
    StockMovementEntity createStockMovement(StockMovementEntity stockMovementEntity);

    List<StockMovementEntity> getStockMovements();

    void deleteStockMovement(Long id);

    StockMovementEntity updateStockMovement(StockMovementEntity stockMovementEntity);

    StockMovementEntity getStockMovement(Long id);
}
