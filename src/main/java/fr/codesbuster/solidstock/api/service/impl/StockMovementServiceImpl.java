package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.StockMovementEntity;
import fr.codesbuster.solidstock.api.repository.StockMovementRepository;
import fr.codesbuster.solidstock.api.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    @Autowired
    StockMovementRepository stockMovementRepository;

    @Override
    public StockMovementEntity createStockMovement(StockMovementEntity stockMovementEntity) {
        if (stockMovementEntity == null) {
            throw new ResponseStatusException(BAD_REQUEST, "StockMovement cannot be null");
        }

        return stockMovementRepository.save(stockMovementEntity);
    }

    @Override
    public List<StockMovementEntity> getStockMovements() {
        return stockMovementRepository.findAll();
    }

    @Override
    public void deleteStockMovement(Long id) {
        if (id == null) {
            throw new ResponseStatusException(BAD_REQUEST, "StockMovement id cannot be null");
        }

        if (!stockMovementRepository.existsById(id)) {
            throw new ResponseStatusException(BAD_REQUEST, "StockMovement does not exist");
        }

        stockMovementRepository.deleteById(id);
    }

    @Override
    public StockMovementEntity updateStockMovement(StockMovementEntity stockMovementEntity) {
        if (stockMovementEntity == null) {
            throw new ResponseStatusException(BAD_REQUEST, "StockMovement cannot be null");
        }

        if (!stockMovementRepository.existsById(stockMovementEntity.getId())) {
            throw new ResponseStatusException(BAD_REQUEST, "StockMovement does not exist");
        }

        return stockMovementRepository.save(stockMovementEntity);
    }

    @Override
    public StockMovementEntity getStockMovement(Long id) {
        if (id == null) {
            throw new ResponseStatusException(BAD_REQUEST, "StockMovement id cannot be null");
        }

        Optional<StockMovementEntity> stockMovementEntity = stockMovementRepository.findById(id);

        if (stockMovementEntity.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "StockMovement does not exist");
        }

        return stockMovementEntity.get();
    }
}
