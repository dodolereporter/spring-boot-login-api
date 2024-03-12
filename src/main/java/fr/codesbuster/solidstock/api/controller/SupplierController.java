package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.SupplierEntity;
import fr.codesbuster.solidstock.api.payload.dto.SupplierDto;
import fr.codesbuster.solidstock.api.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/add")
    public ResponseEntity<SupplierEntity> addSupplier(@RequestBody SupplierDto supplierDto) {
        if (supplierDto == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (supplierDto.getCompanyName() == null || supplierDto.getCompanyName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (supplierDto.getFirstName() == null || supplierDto.getFirstName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (supplierDto.getLastName() == null || supplierDto.getLastName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (supplierDto.getCity() == null || supplierDto.getCity().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (supplierDto.getZipCode() == null || supplierDto.getZipCode().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (supplierDto.getAddress() == null || supplierDto.getAddress().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (supplierDto.getStreetNumber() == null || supplierDto.getStreetNumber().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (supplierDto.getEmail() == null || !supplierDto.getEmail().contains("@")) {
            return ResponseEntity.badRequest().build();
        }

        if (supplierDto.getMobilePhone() == null || supplierDto.getMobilePhone().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (supplierDto.getCountry() == null || supplierDto.getCountry().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        SupplierEntity supplierEntity = new SupplierEntity();
        // Map SupplierDto to SupplierEntity

        supplierEntity = supplierService.createSupplier(supplierEntity);

        return ResponseEntity.ok(supplierEntity);
    }


    @GetMapping("/all")
    public ResponseEntity<Iterable<SupplierEntity>> getAllSuppliers() {
        Iterable<SupplierEntity> supplierEntities = supplierService.getSuppliers();
        return ResponseEntity.ok(supplierEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierEntity> getSupplier(@PathVariable Long id) {
        SupplierEntity supplierEntity = supplierService.getSupplier(id);
        return ResponseEntity.ok(supplierEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierEntity> updateSupplier(@PathVariable Long id, @RequestBody SupplierDto supplierDto) {
        SupplierEntity supplierEntity = supplierService.getSupplier(id);
        supplierEntity.setCompanyName(supplierDto.getCompanyName());
        supplierEntity.setFirstName(supplierDto.getFirstName());
        supplierEntity.setLastName(supplierDto.getLastName());
        supplierEntity.setCity(supplierDto.getCity());
        supplierEntity.setZipCode(supplierDto.getZipCode());
        supplierEntity.setAddress(supplierDto.getAddress());
        supplierEntity.setStreetNumber(supplierDto.getStreetNumber());
        supplierEntity.setEmail(supplierDto.getEmail());
        supplierEntity.setMobilePhone(supplierDto.getMobilePhone());
        supplierEntity.setHomePhone(supplierDto.getHomePhone());
        supplierEntity.setWorkPhone(supplierDto.getWorkPhone());
        supplierEntity.setFax(supplierDto.getFax());
        supplierEntity.setWebsite(supplierDto.getWebsite());
        supplierEntity.setCountry(supplierDto.getCountry());
        supplierEntity.setNote(supplierDto.getNote());
        supplierEntity = supplierService.updateSupplier(supplierEntity);
        return ResponseEntity.ok(supplierEntity);
    }

}