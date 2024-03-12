package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.VATEntity;
import fr.codesbuster.solidstock.api.payload.dto.VATDto;
import fr.codesbuster.solidstock.api.service.VATService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/vat")
public class VATController {

    @Autowired
    private VATService vatService;

    @PostMapping("/add")
    public ResponseEntity<VATEntity> addVAT(@RequestBody VATDto vatDto) {

        if (vatDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (vatDto.getPercentage() == null || vatDto.getPercentage().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (vatDto.getDescription() == null || vatDto.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (vatDto.getRate() == 0.0) {
            return ResponseEntity.badRequest().build();
        }

        VATEntity vatEntity = new VATEntity();
        vatEntity.setRate(vatDto.getRate());
        vatEntity.setDescription(vatDto.getDescription());
        vatEntity.setPercentage(vatDto.getPercentage());

        log.info("VATEntity: " + vatEntity);

        vatEntity = vatService.createVAT(vatEntity);

        return ResponseEntity.ok(vatEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<VATEntity>> getAllVAT() {
        Iterable<VATEntity> vatEntities = vatService.getVATs();
        return ResponseEntity.ok(vatEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VATEntity> getVAT(@PathVariable Long id) {
        VATEntity vatEntity = vatService.getVAT(id);
        return ResponseEntity.ok(vatEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVAT(@PathVariable Long id) {
        vatService.deleteVAT(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VATEntity> updateVAT(@PathVariable Long id, @RequestBody VATDto vatDto) {
        VATEntity vatEntity = vatService.getVAT(id);
        vatEntity.setRate(vatDto.getRate());
        vatEntity.setDescription(vatDto.getDescription());
        vatEntity.setPercentage(vatDto.getPercentage());
        vatEntity = vatService.updateVAT(vatEntity);
        return ResponseEntity.ok(vatEntity);
    }

}