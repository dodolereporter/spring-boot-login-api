package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.ProductFamilyEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.ProductFamilyRepository;
import fr.codesbuster.solidstock.api.service.ProductFamilyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductFamilyServiceImpl implements ProductFamilyService {

    @Autowired
    ProductFamilyRepository productFamilyRepository;

    @Override
    public ProductFamilyEntity createProductFamily(ProductFamilyEntity productFamilyEntity) {
        if (productFamilyEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "ProductFamily cannot be null");
        }

        if (productFamilyRepository.existsByName(productFamilyEntity.getName())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "ProductFamily already exists");
        }

        return productFamilyRepository.save(productFamilyEntity);
    }

    @Override
    public List<ProductFamilyEntity> getProductFamilies() {
        return productFamilyRepository.findAll();
    }

    @Override
    public void deleteProductFamily(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "ProductFamily id cannot be null");
        }

        if (!productFamilyRepository.existsById(id)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "ProductFamily does not exist");
        }

        productFamilyRepository.deleteById(id);
    }

    @Override
    public ProductFamilyEntity updateProductFamily(ProductFamilyEntity productFamilyEntity) {
        if (productFamilyEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "ProductFamily cannot be null");
        }

        if (!productFamilyRepository.existsById(productFamilyEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "ProductFamily does not exist");
        }

       return productFamilyRepository.save(productFamilyEntity);
    }

    @Override
    public ProductFamilyEntity getProductFamily(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "ProductFamily id cannot be null");
        }

        Optional<ProductFamilyEntity> productFamilyEntity = productFamilyRepository.findById(id);

        if (productFamilyEntity.isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST, "ProductFamily does not exist");
        }

        return productFamilyEntity.get();
    }
}