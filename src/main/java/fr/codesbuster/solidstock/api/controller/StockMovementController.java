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
        return ResponseEntity.ok(stockMovementService.getStockMovements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStockMovement(@PathVariable Long id) {
        return ResponseEntity.ok(stockMovementService.getStockMovement(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStockMovement(@PathVariable Long id) {
        stockMovementService.deleteStockMovement(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<StockMovementEntity> updateStockMovement(@RequestBody StockMovementDto stockMovementDto) {
        StockMovementEntity stockMovementEntity = new StockMovementEntity();
        stockMovementEntity.setId(stockMovementDto.getId());
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
