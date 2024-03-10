package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.QuantityTypeEntity;
import fr.codesbuster.solidstock.api.payload.QuantityTypeDto;
import fr.codesbuster.solidstock.api.service.QuantityTypeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/quantity-type")
public class QuantityTypeController {

    @Autowired
    private QuantityTypeService quantityTypeService;

    @PostMapping("/add")
    public ResponseEntity<QuantityTypeEntity> addQuantityType(@RequestBody QuantityTypeDto vatDto) {

        if (vatDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (vatDto.getUnit() == null || vatDto.getUnit().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (vatDto.getDescription() == null || vatDto.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }


        QuantityTypeEntity quantityTypeEntity = new QuantityTypeEntity();
        quantityTypeEntity.setName(vatDto.getName());
        quantityTypeEntity.setDescription(vatDto.getDescription());
        quantityTypeEntity.setUnit(vatDto.getUnit());

        log.info("QuantityTypeEntity: " + quantityTypeEntity.toString());

        quantityTypeEntity = quantityTypeService.createQuantityType(quantityTypeEntity);

        return ResponseEntity.ok(quantityTypeEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<QuantityTypeEntity>> getAllQuantityType() {
        Iterable<QuantityTypeEntity> vatEntities = quantityTypeService.getQuantityTypes();
        return ResponseEntity.ok(vatEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuantityTypeEntity> getQuantityType(@PathVariable Long id) {
        QuantityTypeEntity vatEntity = quantityTypeService.getQuantityType(id);
        return ResponseEntity.ok(vatEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuantityType(@PathVariable Long id) {
        quantityTypeService.deleteQuantityType(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuantityTypeEntity> updateQuantityType(@PathVariable Long id, @RequestBody QuantityTypeDto vatDto) {
        QuantityTypeEntity vatEntity = quantityTypeService.getQuantityType(id);
        vatEntity.setName(vatDto.getName());
        vatEntity.setDescription(vatDto.getDescription());
        vatEntity.setUnit(vatDto.getUnit());
        vatEntity = quantityTypeService.updateQuantityType(vatEntity);
        return ResponseEntity.ok(vatEntity);
    }

}
