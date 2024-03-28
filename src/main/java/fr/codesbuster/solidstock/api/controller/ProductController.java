package fr.codesbuster.solidstock.api.controller;


import fr.codesbuster.solidstock.api.entity.*;
import fr.codesbuster.solidstock.api.payload.dto.ProductDto;
import fr.codesbuster.solidstock.api.repository.ProductFamilyRepository;
import fr.codesbuster.solidstock.api.repository.QuantityTypeRepository;
import fr.codesbuster.solidstock.api.repository.SupplierRepository;
import fr.codesbuster.solidstock.api.repository.VATRepository;
import fr.codesbuster.solidstock.api.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
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

        if (productDto.getDescription() == null || productDto.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (productDto.getBarcode() == null || productDto.getBarcode().isEmpty()) {
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
        productEntity.setBarCode(productDto.getBarcode());
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

}
