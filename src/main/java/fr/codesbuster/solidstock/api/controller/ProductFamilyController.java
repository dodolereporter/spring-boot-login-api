package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.ProductFamilyEntity;
import fr.codesbuster.solidstock.api.payload.dto.ProductFamilyDto;
import fr.codesbuster.solidstock.api.repository.ProductFamilyRepository;
import fr.codesbuster.solidstock.api.service.ProductFamilyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/product-family")
public class ProductFamilyController {

    @Autowired
    private ProductFamilyRepository productFamilyRepository;

    @Autowired
    private ProductFamilyService productFamilyService;

    @PostMapping("/add")
    public ResponseEntity<ProductFamilyEntity> addProductFamily(@RequestBody ProductFamilyDto productFamilyDto) {

        if (productFamilyDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (productFamilyDto.getName() == null || productFamilyDto.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (productFamilyDto.getDescription() == null || productFamilyDto.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }


        ProductFamilyEntity productFamilyEntity = new ProductFamilyEntity();
        productFamilyEntity.setName(productFamilyDto.getName());
        productFamilyEntity.setDescription(productFamilyDto.getDescription());

        log.info("ProductFamilyEntity: " + productFamilyEntity);

        productFamilyEntity = productFamilyService.createProductFamily(productFamilyEntity);

        return ResponseEntity.ok(productFamilyEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<ProductFamilyEntity>> getAllProductFamily() {
        Iterable<ProductFamilyEntity> unitEntities = productFamilyService.getProductFamilies();
        return ResponseEntity.ok(unitEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductFamilyEntity> getProductFamily(@PathVariable Long id) {
        ProductFamilyEntity productFamilyEntity = productFamilyService.getProductFamily(id);
        return ResponseEntity.ok(productFamilyEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductFamily(@PathVariable Long id) {
        ProductFamilyEntity productFamilyEntity = productFamilyService.getProductFamily(id);
        productFamilyEntity.setDeleted(true);
        productFamilyEntity.getProducts().forEach(productEntity -> productEntity.setDeleted(true));
        productFamilyRepository.save(productFamilyEntity);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> enableProductFamily(@PathVariable Long id) {
        ProductFamilyEntity productFamilyEntity = productFamilyService.getProductFamily(id);
        productFamilyEntity.setDeleted(false);
        productFamilyEntity.getProducts().forEach(productEntity -> productEntity.setDeleted(false));
        productFamilyRepository.save(productFamilyEntity);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductFamilyEntity> updateProductFamily(@PathVariable Long id, @RequestBody ProductFamilyDto productFamilyDto) {
        ProductFamilyEntity productFamilyEntity = productFamilyService.getProductFamily(id);
        productFamilyEntity.setName(productFamilyDto.getName());
        productFamilyEntity.setDescription(productFamilyDto.getDescription());
        productFamilyEntity = productFamilyService.updateProductFamily(productFamilyEntity);
        return ResponseEntity.ok(productFamilyEntity);
    }

}