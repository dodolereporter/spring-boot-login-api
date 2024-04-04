package fr.codesbuster.solidstock.api.controller;


import fr.codesbuster.solidstock.api.entity.*;
import fr.codesbuster.solidstock.api.payload.dto.ProductDto;
import fr.codesbuster.solidstock.api.repository.*;
import fr.codesbuster.solidstock.api.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

        VATEntity vatEntity = vatRepository.findById((long) productDto.getVatId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "VAT cannot be null"));
        SupplierEntity supplierEntity = supplierRepository.findById((long) productDto.getSupplierId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Supplier cannot be null"));
        QuantityTypeEntity quantityType = quantityTypeRepository.findById((long) productDto.getQuantityTypeId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Quantity type cannot be null"));
        ProductFamilyEntity productFamilyEntity = productFamilyRepository.findById((long) productDto.getProductFamilyId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Product family cannot be null"));

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setBuyPrice(Double.valueOf(productDto.getBuyPrice()));
        productEntity.setSellPrice(Double.valueOf(productDto.getSellPrice()));
        productEntity.setMinimumStockQuantity(productDto.getMinimumStockQuantity());
        productEntity.setSupplier(supplierEntity);
        productEntity.setVat(vatEntity);
        productEntity.setQuantityType(quantityType);
        productEntity.setProductFamily(productFamilyEntity);
        productEntity.setDeleted(productEntity.isDeleted());

        productEntity = productService.createProduct(productEntity);
        return ResponseEntity.ok(productEntity);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<ProductEntity> addImageProduct(@PathVariable Long id, @RequestParam("file")MultipartFile file) throws IOException {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found"));
        productEntity.setImage(file.getBytes());

        productEntity = productRepository.save(productEntity);
        return ResponseEntity.ok(productEntity);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImageProduct(@PathVariable Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found"));
        return ResponseEntity.ok(productEntity.getImage());
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
        VATEntity vatEntity = vatRepository.findById((long) productDto.getVatId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "VAT cannot be null"));
        SupplierEntity supplierEntity = supplierRepository.findById((long) productDto.getSupplierId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Supplier cannot be null"));
        QuantityTypeEntity quantityType = quantityTypeRepository.findById((long) productDto.getQuantityTypeId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Quantity type cannot be null"));
        ProductFamilyEntity productFamilyEntity = productFamilyRepository.findById((long) productDto.getProductFamilyId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Product family cannot be null"));

        ProductEntity productEntity = productService.getProduct(id);
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setBuyPrice(Double.valueOf(productDto.getBuyPrice()));
        productEntity.setSellPrice(Double.valueOf(productDto.getSellPrice()));
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
