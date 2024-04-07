package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.QuantityTypeEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.dto.QuantityTypeDto;
import fr.codesbuster.solidstock.api.repository.QuantityTypeRepository;
import fr.codesbuster.solidstock.api.service.QuantityTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/quantity-type")
public class QuantityTypeController {
    @Autowired
    private QuantityTypeRepository quantityTypeRepository;

    @Autowired
    private QuantityTypeService quantityTypeService;

    @PostMapping("/add")
    public ResponseEntity<QuantityTypeEntity> addQuantityType(@RequestBody QuantityTypeDto quantityTypeDto) {

        if (quantityTypeDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (quantityTypeDto.getUnit() == null || quantityTypeDto.getUnit().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (quantityTypeDto.getDescription() == null || quantityTypeDto.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }


        QuantityTypeEntity quantityTypeEntity = new QuantityTypeEntity();
        quantityTypeEntity.setName(quantityTypeDto.getName());
        quantityTypeEntity.setDescription(quantityTypeDto.getDescription());
        quantityTypeEntity.setUnit(quantityTypeDto.getUnit());

        quantityTypeEntity = quantityTypeService.createQuantityType(quantityTypeEntity);

        return ResponseEntity.ok(quantityTypeEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<QuantityTypeEntity>> getAllQuantityType() {
        Iterable<QuantityTypeEntity> quantityTypeEntities = quantityTypeService.getQuantityTypes();
        return ResponseEntity.ok(quantityTypeEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuantityTypeEntity> getQuantityType(@PathVariable Long id) {
        QuantityTypeEntity quantityTypeEntity = quantityTypeService.getQuantityType(id);
        return ResponseEntity.ok(quantityTypeEntity);
    }
    
    @GetMapping("/unit/{unit}")
    public ResponseEntity<QuantityTypeEntity> getQuantityTypeByUnit(@PathVariable String unit) {
        QuantityTypeEntity quantityTypeEntity = quantityTypeRepository.findByUnit(unit).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Unit not found"));
        return ResponseEntity.ok(quantityTypeEntity);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuantityType(@PathVariable Long id) {
        quantityTypeService.deleteQuantityType(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuantityTypeEntity> updateQuantityType(@PathVariable Long id, @RequestBody QuantityTypeDto quantityTypeDto) {
        QuantityTypeEntity quantityEntity = quantityTypeService.getQuantityType(id);
        quantityEntity.setName(quantityTypeDto.getName());
        quantityEntity.setDescription(quantityTypeDto.getDescription());
        quantityEntity.setUnit(quantityTypeDto.getUnit());
        quantityEntity = quantityTypeService.updateQuantityType(quantityEntity);
        return ResponseEntity.ok(quantityEntity);
    }

}