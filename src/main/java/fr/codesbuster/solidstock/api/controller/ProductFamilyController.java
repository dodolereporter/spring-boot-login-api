package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.ProductFamilyEntity;
import fr.codesbuster.solidstock.api.payload.ProductFamilyDto;
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
    private ProductFamilyService productFamilyService;

    @PostMapping("/add")
    public ResponseEntity<ProductFamilyEntity> addProductFamily(@RequestBody ProductFamilyDto unitDto) {

        if (unitDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (unitDto.getName() == null || unitDto.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (unitDto.getDescription() == null || unitDto.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }


        ProductFamilyEntity productFamilyEntity = new ProductFamilyEntity();
        productFamilyEntity.setName(unitDto.getName());
        productFamilyEntity.setDescription(unitDto.getDescription());

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
        productFamilyService.deleteProductFamily(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductFamilyEntity> updateProductFamily(@PathVariable Long id, @RequestBody ProductFamilyDto unitDto) {
        ProductFamilyEntity productFamilyEntity = productFamilyService.getProductFamily(id);
        productFamilyEntity.setName(unitDto.getName());
        productFamilyEntity.setDescription(unitDto.getDescription());
        productFamilyEntity = productFamilyService.updateProductFamily(productFamilyEntity);
        return ResponseEntity.ok(productFamilyEntity);
    }

}
