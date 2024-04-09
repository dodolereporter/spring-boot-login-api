package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.StockMovementEntity;
import fr.codesbuster.solidstock.api.entity.StockMovementType;
import fr.codesbuster.solidstock.api.payload.dto.StockMovementDto;
import fr.codesbuster.solidstock.api.service.LocationService;
import fr.codesbuster.solidstock.api.service.ProductService;
import fr.codesbuster.solidstock.api.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/stock-movement")
public class StockMovementController {

    @Autowired
    private StockMovementService stockMovementService;

    @Autowired
    private ProductService productService;

    @Autowired
    private LocationService locationService;


    @PostMapping("/add")
    public ResponseEntity<StockMovementEntity> createStockMovement(@RequestBody StockMovementDto stockMovementDto) {
        StockMovementEntity stockMovementEntity = new StockMovementEntity();
        stockMovementEntity.setQuantity(stockMovementDto.getQuantity());
        stockMovementEntity.setProduct(productService.getProduct(stockMovementDto.getProductId()));
        stockMovementEntity.setLocation(locationService.getLocation(stockMovementDto.getLocationId()));
        stockMovementEntity.setType(StockMovementType.valueOf(stockMovementDto.getType()));
        stockMovementEntity.setNote(stockMovementDto.getNote());
        stockMovementEntity.setBatchNumber(stockMovementDto.getBatchNumber());
        stockMovementEntity.setExpiredDate(LocalDate.parse(stockMovementDto.getExpiredDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return ResponseEntity.ok(stockMovementService.createStockMovement(stockMovementEntity));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getStockMovements() {
        Collections.reverse(stockMovementService.getStockMovements());
        return ResponseEntity.ok(stockMovementService.getStockMovements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStockMovement(@PathVariable Long id) {
        return ResponseEntity.ok(stockMovementService.getStockMovement(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStockMovement(@PathVariable Long id) {
        return stockMovementService.deleteStockMovement(id);
    }

    @PostMapping("/enable/{id}")
    public ResponseEntity<?> enableStockMovement(@PathVariable Long id) {
        return stockMovementService.enableStockMovement(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockMovementEntity> updateStockMovement(@PathVariable Long id, @RequestBody StockMovementDto stockMovementDto) {
        StockMovementEntity stockMovementEntity = stockMovementService.getStockMovement(id);
        stockMovementEntity.setQuantity(stockMovementDto.getQuantity());
        stockMovementEntity.setProduct(productService.getProduct(stockMovementDto.getProductId()));
        stockMovementEntity.setLocation(locationService.getLocation(stockMovementDto.getLocationId()));
        stockMovementEntity.setType(StockMovementType.valueOf(stockMovementDto.getType()));
        stockMovementEntity.setNote(stockMovementDto.getNote());
        stockMovementEntity.setBatchNumber(stockMovementDto.getBatchNumber());
        stockMovementEntity.setExpiredDate(LocalDate.parse(stockMovementDto.getExpiredDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return ResponseEntity.ok(stockMovementService.updateStockMovement(stockMovementEntity));
    }
}
