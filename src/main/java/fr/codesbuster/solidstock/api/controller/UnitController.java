package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.UnitEntity;
import fr.codesbuster.solidstock.api.payload.UnitDto;
import fr.codesbuster.solidstock.api.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @PostMapping("/add")
    public ResponseEntity<UnitEntity> addVAT(@RequestBody UnitDto unitDto) {

        if (unitDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (unitDto.getName() == null || unitDto.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (unitDto.getDescription() == null || unitDto.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (unitDto.getUnit() == null || unitDto.getUnit().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }


        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setName(unitDto.getName());
        unitEntity.setDescription(unitDto.getDescription());
        unitEntity.setUnit(unitDto.getUnit());

        log.info("UnitEntity: " + unitEntity);

        unitEntity = unitService.createUnit(unitEntity);

        return ResponseEntity.ok(unitEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<UnitEntity>> getAllUnit() {
        Iterable<UnitEntity> unitEntities = unitService.getUnits();
        return ResponseEntity.ok(unitEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitEntity> getUnit(@PathVariable Long id) {
        UnitEntity unitEntity = unitService.getUnit(id);
        return ResponseEntity.ok(unitEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitEntity> updateUnit(@PathVariable Long id, @RequestBody UnitDto unitDto) {
        UnitEntity unitEntity = unitService.getUnit(id);
        unitEntity.setName(unitDto.getName());
        unitEntity.setDescription(unitDto.getDescription());
        unitEntity.setUnit(unitDto.getUnit());
        unitEntity = unitService.updateUnit(unitEntity);
        return ResponseEntity.ok(unitEntity);
    }

}
