package fr.codesbuster.solidstock.api.controller;


import fr.codesbuster.solidstock.api.entity.*;
import fr.codesbuster.solidstock.api.payload.dto.ProductDto;
import fr.codesbuster.solidstock.api.repository.*;
import fr.codesbuster.solidstock.api.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VATRepository vatRepository;

    @Autowired
    private QuantityTypeRepository quantityTypeRepository;

    @Autowired
    private ProductFamilyRepository productFamilyRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductDto productDto) {

        if (productDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (productDto.getName() == null || productDto.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        SupplierEntity supplierEntity = supplierRepository.findById((long) productDto.getSupplierId()).orElse(null);

        if (supplierEntity == null) {
            return ResponseEntity.badRequest().build();
        }

        VATEntity vatEntity = vatRepository.findById((long) productDto.getVatId()).orElse(null);

        if (vatEntity == null) {
            return ResponseEntity.badRequest().build();
        }

        QuantityTypeEntity quantityTypeEntity = quantityTypeRepository.findById((long) productDto.getQuantityTypeId()).orElse(null);

        if (quantityTypeEntity == null) {
            return ResponseEntity.badRequest().build();
        }

        ProductFamilyEntity productFamilyEntity = productFamilyRepository.findById((long) productDto.getProductFamilyId()).orElse(null);

        if (productFamilyEntity == null) {
            return ResponseEntity.badRequest().build();
        }

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setBarCode(productDto.getBarCode());
        productEntity.setBuyPrice(productDto.getBuyPrice());
        productEntity.setSellPrice(productDto.getSellPrice());
        productEntity.setMinimumStockQuantity(productDto.getMinimumStockQuantity());
        productEntity.setSupplier(supplierEntity);
        productEntity.setVat(vatEntity);
        productEntity.setQuantityType(quantityTypeEntity);
        productEntity.setProductFamily(productFamilyEntity);

        log.info("ProductEntity: " + productEntity);

        productEntity = productService.createProduct(productEntity);

        return ResponseEntity.ok(productEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<ProductEntity>> getAllProduct() {
        Iterable<ProductEntity> productEntities = productService.getProducts();
        return ResponseEntity.ok(productEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable Long id) {
        ProductEntity productEntity = productService.getProduct(id);
        return ResponseEntity.ok(productEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        ProductEntity productEntity = productService.getProduct(id);
        productEntity.setDeleted(true);
        productRepository.save(productEntity);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> enableProduct(@PathVariable Long id) {
        ProductEntity productEntity = productService.getProduct(id);
        productEntity.setDeleted(false);
        productRepository.save(productEntity);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        VATEntity vatEntity = vatRepository.findById(id).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "VAT cannot be null"));
        SupplierEntity supplierEntity = supplierRepository.findById(id).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Supplier cannot be null"));
        QuantityTypeEntity quantityType = quantityTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Quantity type cannot be null"));
        ProductFamilyEntity productFamilyEntity = productFamilyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Product family cannot be null"));

        ProductEntity productEntity = productService.getProduct(id);
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setBuyPrice(productDto.getBuyPrice());
        productEntity.setSellPrice(productDto.getSellPrice());
        productEntity.setMinimumStockQuantity(productDto.getMinimumStockQuantity());
        productEntity.setSupplier(supplierEntity);
        productEntity.setVat(vatEntity);
        productEntity.setQuantityType(quantityType);
        productEntity.setProductFamily(productFamilyEntity);
        productEntity.setDeleted(productEntity.isDeleted());
        productEntity = productService.updateProduct(productEntity);
        return ResponseEntity.ok(productEntity);
    }

}
